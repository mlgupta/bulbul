package bulbul.foff.studio.engine.selection;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.SwingUtilities;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGSelectionListener extends  MouseAdapter implements  MouseMotionListener,  KeyListener {
  
  private static final int NO_ACTION=-1;
  private static final int REGULAR=0;
  private static final int SELECTION_SQUARE=1;
  private static final int SELECT=2;
  private static final int HAS_DRAGGED=3;
  private static final int TRANSLATION=4;
  
  private int selectionState=NO_ACTION;
  
  private boolean isSelectedNode=false;
  
  private Point point1=null;
  private Point point2=null;
  private Point baseScaledOriginPoint=null;
  private Point originPoint=null ;
  
  private SVGSelectionManager selectionManager;
  private SVGSelection selection=null;
  private SVGTab svgTab=null;
  private SVGSelectionSquare selectionSquare=null;
  
  private Node currentNode=null;

  
  /**
   * 
   * @description 
   */
  public SVGSelectionListener(SVGSelectionManager selectionManager) {
    this.selectionManager=selectionManager;
    this.svgTab=selectionManager.getSVGTab();
    this.selection=selectionManager.getSvgSelection();
    
    //creating a paint listener
    SVGPaintListener paintListener=new SVGPaintListener(){
      public void paintToBeDone(Graphics g) {
        if(selectionState==SELECT && point1!=null && point2!=null){
          Rectangle rect=selection.getStudio().getSvgToolkit().getComputedRectangle(new Point(point1), new Point(point2));
          Rectangle2D.Double bounds=new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
          bounds=selection.getStudio().getSvgToolkit().getScaledRectangle(svgTab, bounds, false);
          selection.drawSelectionGhost(svgTab, g, bounds);
        }
      }
    };
    svgTab.getScrollPane().getSvgCanvas().addDrawLayerPaintListener(paintListener, false);
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  private SVGSelectionManager getSelectionManager(){
    return selectionManager;
  }
  
  /**
   * 
   * @description 
   * @param selectionState
   */
  private void setSelectionState(int selectionState){
    this.selectionState=selectionState;
  }
  
 
 /**
   * 
   * @description 
   * @param evt
   */
  public void mouseDragged(MouseEvent evt){
    //whether this event is a popup event
    boolean isPopupEvent=(evt.isPopupTrigger() || SwingUtilities.isRightMouseButton(evt));
    if(selection.isSelectionEnabled() && ! isPopupEvent){
      Point point=selection.getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      //the point corresponding to the point returned by the event for a 1.0 scale
      Point2D.Double baseScalePoint2D=selection.getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      Point baseScaledPoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
      if((selectionState==REGULAR && ! point.equals(originPoint)) || selectionState==TRANSLATION){
        //translating the selected nodes
        getSelectionManager().translateSelectedNodes(new Dimension(point.x-originPoint.x, point.y-originPoint.y));
        setSelectionState(TRANSLATION);
      }else if(selectionState==SELECT){
        point2=point;
        svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
      }else if(selectionState==SELECTION_SQUARE){
        getSelectionManager().doActions(selectionSquare, originPoint, point, baseScaledOriginPoint, baseScaledPoint);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void mousePressed(MouseEvent evt){
    //whether this event is a popup event
    boolean isPopupEvent=(evt.isPopupTrigger() || SwingUtilities.isRightMouseButton(evt));
    if(selection.isSelectionEnabled() && ! isPopupEvent){
      Point point=selection.getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());

      //the point corresponding to the point returned by the event for a 1.0 scale
      Point2D.Double baseScalePoint2D=selection.getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      Point baseScaledPoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);

      //checks if the clicked point corresponds to a selection square
      SVGSelectionSquare selSquare=getSelectionManager().getSelectionSquare(baseScaledPoint);

      if(selSquare!=null){
      //sets the state of the action
        setSelectionState(SELECTION_SQUARE);
        selectionSquare=selSquare;
        
        //sets the initial value for the points
        originPoint=point;
        baseScaledOriginPoint=baseScaledPoint;
      }else{
        getSelectionManager().setCursor(point);
        Node node=selection.getStudio().getSvgToolkit().getNodeAt(svgTab, selectionManager.getParentElement(), point);
        if(! evt.isControlDown() && node!=null && node instanceof Element){
            //whether the node is selected or not
            isSelectedNode=getSelectionManager().isSelected(node);
            //if the multiple selection is not activated, and the node is not selected, all the selected nodes are deselected
            if(! isSelectedNode && ! evt.isShiftDown()){
              getSelectionManager().deselectAll(false, false);
            }

            //adds or removes the node
            if(! isSelectedNode || (isSelectedNode && evt.isShiftDown())){
              getSelectionManager().handleNodeSelection((Element)node, evt.isShiftDown(), true, false);
            }

            //sets the state of the action
            setSelectionState(REGULAR);
            
            //sets the initial value for the points
            originPoint=point;
            baseScaledOriginPoint=baseScaledPoint;
            
            //sets the current node
            currentNode=node;
        }else{
            getSelectionManager().deselectAll(false, false);

            //sets the state
            setSelectionState(SELECT);
            
            //set the value of the first point
            point1=point;
            
            //refreshes the hashtable of the selected items, and draws the selections
            getSelectionManager().refreshSelection();
        }
      }
    }
      
    if(selection.isSelectionEnabled() && isPopupEvent){
      //shows a popup
      selection.getStudio().getPopupManager().showPopup(svgTab, evt.getPoint());
    }
  }
  
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void mouseReleased(MouseEvent evt){
    //whether this event is a popup event
    boolean isPopupEvent=(evt.isPopupTrigger() || SwingUtilities.isRightMouseButton(evt));
    
    if(selection.isSelectionEnabled()){
        
      Point point=selection.getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());

      //the point corresponding to the point returned by the event for a 1.0 scale index
      Point2D.Double baseScalePoint2D=selection.getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      Point baseScaledPoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
        
      if(selectionState==REGULAR && ! isPopupEvent){
        if(isSelectedNode && ! evt.isShiftDown()){
          getSelectionManager().handleNodeSelection((Element)currentNode, evt.isShiftDown(), true, false);
        }
        //refreshes the hashtable of the selected items, and draws the selections
        getSelectionManager().refreshSelection();

        //reinitializing the used variables
        originPoint=null;
        baseScaledPoint=null;
        currentNode=null;
      }else if(selectionState==TRANSLATION){
        //computing the values of the translation and validating the translation
        final Dimension translationValues=new Dimension(point.x-originPoint.x, point.y-originPoint.y);
        getSelectionManager().validateTranslateSelectedNodes(translationValues);
      }else if(selectionState==SELECT){
        //selects all the nodes that lies in the rectangle drawn by the mouse
        point2=point;
        svgTab.getScrollPane().getSvgCanvas().displayWaitCursor();
        getSelectionManager().select(selection.getStudio().getSvgToolkit().getComputedRectangle(new Point(point1), new Point(point)), getSelectionManager().getParentElement(), evt.isShiftDown());
  
        //reinitializing the used variables
        point1=null;
        point2=null;
        svgTab.getStudio().getMainStatusBar().setWidth("");
        svgTab.getStudio().getMainStatusBar().setHeight("");
        svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
        svgTab.getScrollPane().getSvgCanvas().returnToLastCursor();
      }else if(selectionState==SELECTION_SQUARE){
        getSelectionManager().validateDoActions(selectionSquare, originPoint, point, baseScaledOriginPoint, baseScaledPoint);
        //reinitializing the used variables
        originPoint=null;
        baseScaledPoint=null;
        selectionSquare=null;
      }
      setSelectionState(NO_ACTION);
    }
  }
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void mouseEntered(MouseEvent evt){
  
  }
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void mouseExited(MouseEvent evt){
    
  }
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void mouseMoved(java.awt.event.MouseEvent evt){
    if(selection.isSelectionEnabled() && selectionState!=SELECT){
      Point2D.Double baseScPointD=selection.getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      Point baseScPoint=new Point((int)baseScPointD.x, (int)baseScPointD.y);
      getSelectionManager().setCursor(baseScPoint);
    } 
  }
  
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void keyReleased(KeyEvent evt){
    
  }
  
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void keyTyped(KeyEvent evt){
    
  }
  
  /**
   * 
   * @description 
   * @param evt
   */
  public void keyPressed(KeyEvent evt){
    if(selection.isSelectionEnabled()){
      RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
      Point range=svgTab.getScrollPane().getRulersRange();
      Dimension translationValues=null;
      if(range!=null && evt.getModifiers()==0){
        if(evt.getKeyCode()==KeyEvent.VK_UP){
          translationValues=new Dimension(0, -range.y);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
          translationValues=new Dimension(0, range.y);
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
          translationValues=new Dimension(-range.x, 0);
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
          translationValues=new Dimension(range.x, 0);
        }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbDelete().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_EQUALS || evt.getKeyCode()==KeyEvent.VK_ADD ){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbZoomIn().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_MINUS  || evt.getKeyCode()==KeyEvent.VK_SUBTRACT){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbZoomOut().doClick();
        }
        if(translationValues!=null){
          getSelectionManager().validateTranslateSelectedNodes(translationValues);
        }
      }else if (evt.getModifiers()==KeyEvent.CTRL_MASK ){
        if(evt.getKeyCode()==KeyEvent.VK_C){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbCopy().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_V){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbPaste().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_X){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbCut().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_A){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbSelectAll().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_D){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbDeselectAll().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_G){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbGroup().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_U){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbUnGroup().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_Y){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbRedo().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_Z){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbUndo().doClick();
        }
      }else if(evt.getModifiers()==KeyEvent.SHIFT_MASK ){
        if(evt.getKeyCode()==KeyEvent.VK_EQUALS){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbZoomIn().doClick();
        }else if(evt.getKeyCode()==KeyEvent.VK_MINUS){
          getSelectionManager().getSvgSelection().getStudio().getMainToolBar().getMtbZoomOut().doClick();
        }
        
      }else if(evt.getModifiers()==(KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK) ){
        
      }
    }  
  }
}