package studio.canvas;
import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.RunnableQueue;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

import studio.shape.SVGShape;

import studio.undoredo.SVGUndoRedoAction;
import studio.undoredo.SVGUndoRedoActionList;

public class SVGSelection  {

  /**
   * the ids of the module
   */
  final private String 	idselection="Selection", idselectall="SelectAll", 
  idlock="Lock", idunlock="UnLock", idregularmode="RegularMode";

  /**
   * the undo/redo labels
   */
  private String 	undoRedoSelect="";
  private String 	undoRedoDeselect="";
  private String 	undoRedoDeselectAll="";
  private String 	undoRedoSelectAll=""; 
  
  /**
   * the undo/redo list that will be added to the undo/redo stack
   */
  private SVGUndoRedoActionList undoRedoList=null;
    
  /**
   * the hashtable associating the name of a module to a linked list of nodes that are managed by the module
   */
  private Hashtable selectedItems=new Hashtable();
  
  /**
   * the map of the selected nodes associating a node to its type
   */
  private LinkedHashMap currentSelection=new LinkedHashMap();

  /**
   * the hashtable associating a node to the linked list of the selection square objects
   */
  private final Hashtable selectionsquares=new Hashtable();
          
  /**
   * tells whether the shift button is pressed or not
   */
  private boolean multiSelection=false;
  
  /**
   * the list of the listeners listening to a selection change
   */
  private LinkedList selectionListeners=new LinkedList();
  
  /**
   * the nodes that are currently selected in the current SVGFrame
   */
  private LinkedHashMap selectednodes=new LinkedHashMap();
    
  /**
   * the selectionMouseListener
   */
    private SelectionMouseListener selectionMouseListener=null;
  
  /**
   * used to convert numbers into a string
   */
  private DecimalFormat format;
  
  /**
   * a reference to the current object of this class
   */
  private final SVGSelection selection=this;
  
  /**
   * the scrollpane
   */
  private SVGScrollPane scrollPane;

  public SVGSelection(SVGScrollPane scrollPane) {
    this.scrollPane=scrollPane;
    //sets the format used to convert numbers into a string
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("######.#",symbols);
    final AWTEventListener keyListener=new AWTEventListener(){
      public void eventDispatched(AWTEvent e) {
        String value=e.paramString();
        //checks whic key has been pressed or released
        if(value.indexOf("keyCode=16")!=-1){
          //according to the type of the action
          if(value.indexOf("KEY_PRESSED")!=-1){
              setMultiSelectionEnabled(true);
          }else if(value.indexOf("KEY_RELEASED")!=-1){
              setMultiSelectionEnabled(false);
          }
        }
      }
    };
          
    Toolkit.getDefaultToolkit().addAWTEventListener(keyListener, AWTEvent.KEY_EVENT_MASK);
    //a listener that listens to the changes of the SVGFrames
        final ActionListener svgChangeListener=new ActionListener(){
          /**
           * a listener on the selection changes
           */
          private ActionListener selectionListener=null;
          
          /**
           * called when an event occurs
           */
          public void actionPerformed(ActionEvent e) {
              
              //deals with the state of the menu items
              
              if(selectionMouseListener!=null){
                try{
                  getScrollPane().getSVGCanvas().removeMouseListener((MouseListener)selectionMouseListener);
                  getScrollPane().getSVGCanvas().removeMouseMotionListener((MouseMotionListener)selectionMouseListener);
                  getScrollPane().getSVGCanvas().removeKeyListener((KeyListener)selectionMouseListener);
                }catch (Exception ex){
                  ex.printStackTrace();
                }
                      
              }
              selectionMouseListener=new SelectionMouseListener(getScrollPane());
              
              try{
                  ((SVGCanvas)getScrollPane().getSVGCanvas()).addMouseListener(selectionMouseListener);
                  ((SVGCanvas)getScrollPane().getSVGCanvas()).addMouseMotionListener(selectionMouseListener);
                  ((SVGCanvas)getScrollPane().getSVGCanvas()).addKeyListener(selectionMouseListener);
                  
              }catch (Exception ex){
                ex.printStackTrace();
              }
              manageSelection();
              //the listener to the selection changes
              selectionListener=new ActionListener(){
                  /**
                   * called when an event occurs
                   */
                  public void actionPerformed(ActionEvent e) {
                    manageSelection();
                  }
              };
              //adds the selection listener
              if(selectionListener!=null){
                  selection.addSelectionListener(selectionListener);
              }
          }
          
          /**
           * updates the selected items and the state of the menu items
           */
          protected void manageSelection(){
            //gets the currently selected nodes list 
            LinkedHashMap map=getSelection();
            selectednodes.clear();
            //refresh the selected nodes list
            if(map!=null)selectednodes.putAll(map);
          }
        };
        //adds the SVG change listener
        scrollPane.getSVGManager().addSVGChangedListener(svgChangeListener);  
  }
  
  public SVGScrollPane getScrollPane(){
    return this.scrollPane;
  }
  /**
   * adds a listener listening to a selection change
   * @param listener a listener listening to a selection change
   */
  public void addSelectionListener(ActionListener listener){
    selectionListeners.add(listener);
  }
    
  /**
   * removes a listener listening to a selection change
   * @param listener a listener listening to a selection change
   */		
  public void removeSelectionListener(ActionListener listener){
    selectionListeners.remove(listener);
  }
    
  /**
   * notifies the listeners that the selection has changed
   */
  public void selectionChanged(){
    Iterator it=selectionListeners.iterator();
    while(it.hasNext()){
        ((ActionListener)it.next()).actionPerformed(null);
    }
  }
  
  /**
   * tells if the multi-selection is enabled
   * @return true if the multi-selection is enabled
   */
  protected boolean isMultiSelectionEnabled(){
    return multiSelection;
  }
  
  /**
   * sets true if the multi-selection is enabled
   * @param multiSelection true if the multi-selection is enabled
   */
  protected synchronized void setMultiSelectionEnabled(boolean multiSelection){
    this.multiSelection=multiSelection;
  }
  
  /**
   * tells whether a selection is active or not
   * @return true if a selection is active
   */
  protected boolean isMultipleSelected(){
      if(selectionMouseListener!=null)return selectionMouseListener.isMultipleSelected();
      else return false;
  }
    
  /**
   * adds selection squares to the map
   * @param table the table of selection squares
   */
  public void addSelectionSquares(Hashtable table){
    if(selectionMouseListener!=null)selectionMouseListener.addSelectionSquares(table);
  }
  
  /**
   * refreshes the selection
   */
  public void refreshSelection(){
    if(selectionMouseListener!=null)selectionMouseListener.refreshSelection();
  }
  
  /**
   * clears the selection
   */
  public void clearSelection(){
    if(selectionMouseListener!=null)selectionMouseListener.clearSelection();
  }
  
  /** adds or removes a node from the selected nodes 
   * @param node the node that will be treated
   */
  public void manageNode(Node node){
    if(selectionMouseListener!=null)selectionMouseListener.manageNode(node);
  }
  
  /**
   * adds an undo/redo action to the action list
   * @param action the action to be added
   */
  public void addUndoRedoAction(SVGUndoRedoAction action){
    if(selectionMouseListener!=null)selectionMouseListener.addUndoRedoAction(action);
  }
    
  /**
   * gets the list of the currently selected nodes
   * @return the list of the currently selected nodes
   */
  public LinkedList getCurrentSelection(){
    if(selectionMouseListener!=null)return selectionMouseListener.getCurrentSelection();
    else return null;
  }    
  
  /**
   * gets the list of the currently selected nodes
   * @return the list of the currently selected nodes
   */
  protected LinkedHashMap getSelection(){
    if(selectionMouseListener!=null)return selectionMouseListener.getSelection();
    else return null;
  }
  /**
   * selects all the children of the root element
   */
  public void selectAll(){
    if(selectionMouseListener!=null)selectionMouseListener.selectAll();
  }    
  
  public void deselectAll(Boolean makeRepaint){
    //dummy
  }    
  
  
  /**
   * draws a rectangle representing the selected area while dragging
   * @param gr a graphics element
   * @param bounds the bounds of the rectangle to be drawned
   */
  public void drawSelectionGhost(SVGStatusBar statusBar, Graphics gr, Rectangle2D.Double bounds){
      Graphics2D g=(Graphics2D)gr;
      
      if(g!=null && bounds!=null){
          
          //draws the new awt rectangle to be displayed
          g.setColor(Color.white);
          g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
          g.setColor(Color.black);
          Stroke stroke=g.getStroke();
          g.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{5,5},0.0f));
          g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
          g.setStroke(stroke);
          statusBar.setSVGW(format.format(bounds.width));
          statusBar.setSVGH(format.format(bounds.height));
      }				
  }

  /**
   * @author Sudheer V. Pujar
   * the class listening to mouse events for selecting nodes on the canvas
   */
  protected class SelectionMouseListener extends MouseAdapter implements MouseMotionListener, KeyListener{
      
    private final int NODE_ADDED=0;
    private final int NODE_REMOVED=1;
    private final int NODE_NOT_ADDED=2;
    
    private final int REGULAR=0;
    private final int SELECTION_SQUARE=1;
    private final int SELECT_ACTION=2;
    private final int HAS_DRAGGED=3;
    
    /**
     * the SVGScrollPane containing the canvas
     */
    private SVGScrollPane scrollPane=null;
      
    /**
     * the node on which a drag action has been done
     */	
    private Node dragNode=null;
    
    /**
     * the stat eof the listener
     */
    private int state=0;
    
    /**
     * the boolean telling if the mouse is pressed or not
     */
    private boolean isPressed=false;

    /**
     * the list of the locked nodes
     */
    private LinkedList lockednodes=new LinkedList();

    /**
     * the points used to record the drag area
     */
    private Point originPoint=null, baseScaleOriginPoint=null;
    
    /**
     * the two points used for the selection 
     */
    private Point point1=null, point2=null;
    
    /**
     * the current selection square
     */
    private SelectionSquare currentSquare=null;
        
    /**
     * the boolean used to refresh the selection
     */
    private boolean shouldRefresh=false;
    
    /**
     * the current cursor
     */
    private Cursor currentCursor=null;


    
    protected SelectionMouseListener(SVGScrollPane scrollPane){
      this.scrollPane=scrollPane; 
      
      final SVGScrollPane theScrollPane=scrollPane;
      
      SVGCanvas.PaintListener paintListener=new SVGCanvas.PaintListener(){
        public void paintToBeDone(Graphics g) {
          if(state==SELECT_ACTION && point1!=null && point2!=null){
            Rectangle rect=theScrollPane.getSVGToolkit().getComputedRectangle(new Point(point1), new Point(point2));
            Rectangle2D.Double bounds=new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
            bounds=theScrollPane.getSVGToolkit().getScaledRectangle(bounds, false);
              
            drawSelectionGhost(theScrollPane.getStudio().getStatusBar(), g, bounds);
          }
        }
      };	
      
      try{
        scrollPane.getSVGCanvas().addPaintListener(paintListener, false);
      }catch (Exception ex){
        ex.printStackTrace();
      }
      
      //the paint manager
      Thread refreshManager=new Thread(){
        public void run() {
          while(true){
            try{
              sleep((long)200);
            }catch (Exception ex){
              ex.printStackTrace();
            }
            if(shouldRefresh){
              redrawSelectionMethod();
              synchronized(this){
                shouldRefresh=false;
              }
            }
          }
        }
      };      
      refreshManager.start();	
    }
    
    /**
      * @return the SVGFrame containing the canvas
      */
    protected SVGScrollPane getScrollPane(){
        return scrollPane;
    }
    /**
     * sets the state of the listener
     * @param state the state
     */
    protected void setState(int state){
        this.state=state;
    }
    /**
     * gets the nodes given a point
     * @param point the point on which a mouse event has been done
     * @return the node on which a mouse event has been done
     */
    protected Node getSelectedNode(Point point){
      if(point!=null){
        final SVGCanvas canvas=scrollPane.getSVGCanvas();
        BridgeContext ctxt=canvas.getBridgeContext();
        SVGDocument doc=canvas.getSVGDocument();
        if(ctxt!=null && doc!=null){
          Element root=doc.getRootElement();
          if(root!=null){
            if(root.hasChildNodes()){
              Node current=root.getLastChild();
              GraphicsNode gnode=null;
              AffineTransform af=null;
              Point2D pt0=point, pt1=null;
              while(current!=null){
                pt1=pt0;
                //gets the graphics node linked with the current node
                gnode=(current instanceof Element)?ctxt.getGraphicsNode((Element)current):null;
                if(gnode!=null){
                  //the tranform of the current node
                  af=gnode.getTransform();
                  if(af!=null){
                    try{
                      //puts the pt point in the plan of the untransformed nodes
                      pt1=af.inverseTransform(point,null);
                    }catch (Exception e){
                      e.printStackTrace();
                    }
                  }
                  if(gnode.contains(pt1))return current;
                }
                af=null;
                gnode=null;
                current=current.getPreviousSibling();
              }
            }
          }
        }
      }
        
      return null;
    }    
    /**
     * called when a mouse pressed event on the canvas has been received
     * @param evt the event
     */
    public void mousePressed(MouseEvent evt){
      
      if(!isPressed){
        Point point=getScrollPane().getSVGToolkit().getAlignedWithRulersPoint(evt.getPoint());

        //the point corresponding to the point returned by the event for a 1.0 scale
        Point2D.Double baseScalePoint2D=getScrollPane().getSVGToolkit().getScaledPoint(	
                new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
        
        Point baseScalePoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
        
        //checks if the click corresponds to a selection square
        currentSquare=getSquare(baseScalePoint);
        
        setState((currentSquare!=null)?SELECTION_SQUARE:REGULAR);
        if(state==REGULAR){
          changeCursor(point);
          dragNode=getSelectedNode(point);
          if(dragNode!=null){
            //if the node that will be dragged is not null, the position of the mouse click is recorded
            originPoint=point;
            baseScaleOriginPoint=baseScalePoint;                        
          }
          //the points for the selection 
          point1=point;
          point2=null;                
        }else if(state==SELECTION_SQUARE){
          //if the click has been done over a selection square
          originPoint=point;
          baseScaleOriginPoint=baseScalePoint;
        }
      }
      
      //the mouse button is pressed
      synchronized(this){
        isPressed=true;
      }        
    }     
 
   /**
    * called when a mouse released event on the canvas has been received 
    * @param evt the event
    */
    public void mouseReleased(MouseEvent evt){
      
      Point point=getScrollPane().getSVGToolkit().getAlignedWithRulersPoint(evt.getPoint());
      
      //the point corresponding to the point returned by the event for a 1.0 scale
      Point2D.Double baseScalePoint2D=getScrollPane().getSVGToolkit().getScaledPoint(	
              new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      
      Point baseScalePoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
      
      RunnableQueue queue=getScrollPane().getSVGCanvas().getUpdateManager().getUpdateRunnableQueue();
      
      //if it is the end of the translation drag
      if(state==HAS_DRAGGED && originPoint!=null &&point!=null){
        //the values of the translation
        final Point tValues=new Point(point.x-originPoint.x, point.y-originPoint.y);
        
        //invokes the "doActions" method on the group node to translate it
        Runnable runnable=new Runnable(){
          public void run(){
            Iterator it=selectedItems.keySet().iterator(), it2;
            LinkedList list=null, notLockedNodes=null;
            SVGShape shape=null;
            String name="";
            Node node=null;
              
            while(it.hasNext()){
              try{
                name=(String)it.next();
              }catch (Exception ex){
                ex.printStackTrace();
                name=null;
              }
              if(name!=null && ! name.equals("")){
                //gets the module
                try{
                  shape=(SVGShape)getScrollPane().getStaticClassObject(name);
                  if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
                }catch (Exception ex){
                  ex.printStackTrace();
                  shape=null;
                }
              }
              if(shape!=null){
                try{
                    list=(LinkedList)selectedItems.get(name);
                }catch (Exception ex){
                  ex.printStackTrace();
                  list=null;
                }
                if(list!=null && list.size()>0){
                  notLockedNodes=new LinkedList();
                  it2=list.iterator();
                  node=null;
                  //creates the list that does not contain the locked nodes
                  while(it2.hasNext()){
                      try{
                          node=(Node)it2.next();
                      }catch (Exception ex){
                        ex.printStackTrace();
                        node=null;
                      }
                      
                      if(node!=null && ! lockednodes.contains(node)){
                          notLockedNodes.add(node);
                      }
                  }
                  if(notLockedNodes!=null && notLockedNodes.size()>0){
                    Object[] args={notLockedNodes, tValues};
                    //invokes the "doActions" method on the group node to translate it
                    shape.doActions("level1", SVGShape.VALIDATE_TRANSLATE_ACTION,args);
                    selectionChanged();
                  }
                }
              }
            }
            //notifies that the selection has changed
            selectionChanged();
          }
        };
            
        if(queue.getThread()!=Thread.currentThread()){
          queue.invokeLater(runnable);
        }else{
          runnable.run();
        }
        //reinitializes the drag values
        dragNode=null;
        state=REGULAR;
      }else if(state==REGULAR){
        changeCursor(point);
        //selects the node on which a mouse click has been done
        Node node=getSelectedNode(point);
        
        //if the multiple selection is not activated or if the node is null, all the selected nodes is deselected
        if((! isMultiSelectionEnabled() && node!=null && !( isSelected(node) && getCurrentSelection().size()==1)) || node==null){
          clearSelection();
          //refreshes the hashtable of the selected items, and draws the selections
          if(node==null){
          refreshSelection();
          }
        }
          
        if(node!=null && ( !( isSelected(node) && getCurrentSelection().size()==1) || isMultiSelectionEnabled())){
          //adds or removes the node
          manageNode(node);
          //refreshes the hashtable of the selected items, and draws the selections
          refreshSelection();
        }else if(! isMultiSelectionEnabled() && isSelected(node) && getCurrentSelection().size()==1){
          //gets the type of the selection
          String type="";
          type=(String)currentSelection.get(node);
          currentSelection.remove(node);
          if(type!=null){
            //gets the module linked with the given node
            SVGShape shape=null;
            try{
                shape=(SVGShape)getScrollPane().getStaticClassObject(node.getNodeName());
                if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
            }catch (Exception ex){
              ex.printStackTrace();
              shape=null;
            }

            if(shape!=null){
                currentSelection.put(node,shape.getNextLevel(type));
            }else{
                currentSelection.put(node,"level1");
            }
            redrawSelection();
          }
        }
        //reinitializes the drag values
        dragNode=null;
      }else if(state==SELECT_ACTION){
        //selects all the nodes that lies in the rectangle drawn by the mouse
        if(point1!=null && point2!=null){
          getScrollPane().getSVGCanvas().displayWaitCursor();
          
          select(getScrollPane().getSVGToolkit().getComputedRectangle(new Point(point1), new Point(point)));
          
          point1=null;
          point2=null;
          getScrollPane().getStudio().getStatusBar().setSVGW("");
          getScrollPane().getStudio().getStatusBar().setSVGH("");
          getScrollPane().getSVGCanvas().repaintCanvas();
          getScrollPane().getSVGCanvas().returnToLastCursor();
          setState(REGULAR);
        }
      }else if(state==SELECTION_SQUARE){
        //gets the type of the selection
        String type=(String)currentSelection.get(currentSquare.getNode());
        if(type!=null && ! type.equals("lock") && originPoint!=null && baseScaleOriginPoint!=null){
          //if the click has been done over a selection square
          String name=currentSquare.getNode().getNodeName();
          SVGShape shape=null;
          try{
              shape=(SVGShape)getScrollPane().getStaticClassObject(currentSquare.getNode().getNodeName());
              if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
          }catch (Exception ex){
            ex.printStackTrace();
            shape=null;
          }
          if(shape!=null){
            final Object[] args={	currentSquare, new Point(originPoint.x, originPoint.y), new Point(point.x, point.y), 
                new Point(baseScaleOriginPoint.x, baseScaleOriginPoint.y), new Point(baseScalePoint.x, baseScalePoint.y)};
              
            //invokes the "doActions" method to validate the actions
            if(queue.getThread()!=Thread.currentThread()){
              final SVGShape fshape=shape;
              queue.invokeLater(new Runnable(){
                public void run(){
                  fshape.doActions((String)currentSelection.get(currentSquare.getNode()), SVGShape.VALIDATE_ACTION, args);
                  selectionChanged();
                }
              });
            }else{
              shape.doActions((String)currentSelection.get(currentSquare.getNode()), SVGShape.VALIDATE_ACTION, args);
              selectionChanged();
            }
          }
        }
      }
      originPoint=null;
      baseScaleOriginPoint=null;
      //the mouse button is no more pressed
      synchronized(this){
          isPressed=false;
      }
      point1=null;
      point2=null;
    }
    
    /**
     * called when a mouse dragged event on the canvas has been received 
     * @param evt the event
     */
    public void mouseDragged(MouseEvent evt){
      Point point=getScrollPane().getSVGToolkit().getAlignedWithRulersPoint(evt.getPoint());
      //the point corresponding to the point returned by the event for a 1.0 scale
      Point2D.Double baseScalePoint2D=getScrollPane().getSVGToolkit().getScaledPoint(	
              new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
      Point baseScalePoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
      //if the select action has not been called and the click was not done over a selection square
      if(state==REGULAR || state==HAS_DRAGGED){
        //if the drag node is not null and it is selected and the current point is not null, the translation can be done
        if(dragNode!=null && isSelected((Node)dragNode) && originPoint!=null){
          state=HAS_DRAGGED;
          //the values of the translation
          Point tValues=new Point(point.x-originPoint.x, point.y-originPoint.y);
          Iterator it=selectedItems.keySet().iterator(), it2;
          LinkedList list=null, notLockedNodes=null;
          SVGShape shape=null;
          String name="";
          Node node=null;
          while(it.hasNext()){
            try{
                name=(String)it.next();
            }catch (Exception ex){
              ex.printStackTrace();
              name=null;
            }
            if(name!=null && ! name.equals("")){
              try{
                shape=(SVGShape)getScrollPane().getStaticClassObject(name);
                if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
              }catch (Exception ex){
                ex.printStackTrace();
                shape=null;
              }
            }
            if(shape!=null){
              try{
                list=(LinkedList)selectedItems.get(name);
              }catch (Exception ex){
                ex.printStackTrace();
                list=null;
              }
              if(list!=null && list.size()>0){
                notLockedNodes=new LinkedList();
                it2=list.iterator();
                node=null;
                //creates the list that does not contain the locked nodes
                while(it2.hasNext()){
                  try{
                    node=(Node)it2.next();
                  }catch (Exception ex){
                    ex.printStackTrace();
                    node=null;
                  }
                  if(node!=null && ! lockednodes.contains(node)){
                    notLockedNodes.add(node);
                  }
                }
                if(notLockedNodes!=null && notLockedNodes.size()>0){
                  Object[] args={notLockedNodes, tValues};
                  //invokes the "doActions" method on the group node to translate it
                  shape.doActions("level1", SVGShape.DO_TRANSLATE_ACTION, args);
                }
              }
            }
          }
        }else{
          setState(SELECT_ACTION);
          point2=point;
          getScrollPane().getSVGCanvas().repaintCanvas();
        }
      }else if(state==SELECT_ACTION){
          point2=point;
          getScrollPane().getSVGCanvas().repaintCanvas();
      }else if(state==SELECTION_SQUARE){
        //gets the type of the selection
        String type=(String)currentSelection.get(currentSquare.getNode());
        if(type!=null && ! type.equals("lock") ){
          //if the click has been done over a selection square
          String name=currentSquare.getNode().getNodeName();
          SVGShape shape=null;
          try{
            shape=(SVGShape)getScrollPane().getStaticClassObject(currentSquare.getNode().getNodeName());
            if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
          }catch (Exception ex){
            ex.printStackTrace();
            shape=null;
          }
          if(shape!=null && currentSquare!=null && originPoint!=null && baseScaleOriginPoint!=null){
            final Object[] args={	currentSquare, new Point(originPoint.x, originPoint.y), new Point(point.x, point.y), 
                    new Point(baseScaleOriginPoint.x, baseScaleOriginPoint.y), new Point(baseScalePoint.x, baseScalePoint.y)};
            //calls the "doActions" method to ask the module to executed the accurate action given the type of the selection
            shape.doActions((String)currentSelection.get(currentSquare.getNode()), SVGShape.DO_ACTION,args);
          }
        }
      }
    }
    

    /**
     * called when a mouse moved event on the canvas has been received
     * @param evt the event
     */
    public void mouseMoved(java.awt.event.MouseEvent evt){
      if(state!=SELECT_ACTION){
        Point2D.Double baseScalePoint2D=getScrollPane().getSVGToolkit().getScaledPoint(	
                new Point2D.Double(evt.getPoint().x, evt.getPoint().y), true);
        Point baseScalePoint=new Point((int)baseScalePoint2D.x, (int)baseScalePoint2D.y);
        
        changeCursor(baseScalePoint);
      }
    }
    
    /**
     * @param evt the event
     */
    public void mouseEntered(MouseEvent evt) {
    }
    
    /**
     * @param evt the event
     */
    public void mouseExited(MouseEvent evt) {
    }
    
    /**
     * @param evt the event
     */
    public void keyTyped(KeyEvent evt){ 
    }
    
    /**
     * @param evt the event
     */
    public void keyReleased(KeyEvent evt){
    }
    
    /**
     * @param evt the event
     */
    public void keyPressed(KeyEvent evt){
      RunnableQueue queue=getScrollPane().getSVGCanvas().getUpdateManager().getUpdateRunnableQueue();
      Point range=getScrollPane().getRulersRange(); 
      Point tVal=null;
      if(range!=null && evt.getModifiers()==0){
        if(evt.getKeyCode()==KeyEvent.VK_UP){
          tVal=new Point(0, -range.y);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
          tVal=new Point(0, range.y);
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
          tVal=new Point(-range.x, 0);
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
          tVal=new Point(range.x, 0);
        }
        if(tVal!=null){
          //the values of the translation
          final Point tValues=tVal;
          //invokes the "doActions" method on the group node to translate it
          Runnable runnable=new Runnable(){
            public void run(){
              Iterator it=selectedItems.keySet().iterator(), it2;
              LinkedList list=null, notLockedNodes=null;
              SVGShape shape=null;
              String name="";
              Node node=null;
              while(it.hasNext()){
                try{
                    name=(String)it.next();
                }catch (Exception ex){
                  ex.printStackTrace();
                  name=null;
                }
                if(name!=null && ! name.equals("")){
                  //gets the module
                  try{
                    shape=(SVGShape)getScrollPane().getStaticClassObject(name);
                    if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
                  }catch (Exception ex){
                    ex.printStackTrace();
                    shape=null;
                  }
                }
                if(shape!=null){
                  try{
                    list=(LinkedList)selectedItems.get(name);
                  }catch (Exception ex){
                  ex.printStackTrace();
                  list=null;
                  }
                  if(list!=null && list.size()>0){
                    notLockedNodes=new LinkedList();
                    it2=list.iterator();
                    node=null;
                    //creates the list that does not contain the locked nodes
                    while(it2.hasNext()){
                      try{
                        node=(Node)it2.next();
                      }catch (Exception ex){
                        ex.printStackTrace();
                        node=null;
                      }
                      if(node!=null && ! lockednodes.contains(node)){
                        notLockedNodes.add(node);
                      }
                    }
                    if(notLockedNodes!=null && notLockedNodes.size()>0){
                      Object[] args={notLockedNodes, tValues};
                      //invokes the "doActions" method on the group node to translate it
                      shape.doActions("level1", SVGShape.VALIDATE_TRANSLATE_ACTION,args);
                      selectionChanged();
                    }
                  }
                }
              }
              //notifies that the selection has changed
              selectionChanged();
            }
          };          
          if(queue.getThread()!=Thread.currentThread()){
            queue.invokeLater(runnable);
          }else{
            runnable.run();
          }		 
        }
      }
    }    
    /**
     * changes the cursor
     * @param point the point on which the mouse is
     */
    protected void changeCursor(Point point){
      
      SelectionSquare square=getSquare(point);
      SVGCanvas canvas=getScrollPane().getSVGCanvas();
      if(square==null){
        //if the square is null, if the node on which the mouse event has been done, is selected, sets the cursor with the new cursor, otherwise sets the default cursor
        Node node=getSelectedNode(point);
        currentCursor=(node!=null && isSelected(node) && ! lockednodes.contains(node))?currentCursor=getScrollPane().getCursors().getCursor("translate"):getScrollPane().getCursors().getCursor("default");
        canvas.setSVGCursor(currentCursor);
      }else{
        //if the square is not null sets the canvas cursor with the cursor associated with the square
        currentCursor=square.getCursor();
        canvas.setSVGCursor(currentCursor);
      }			
    }
    
    /**
     * adds the selection squares contained in the map to the selection squares
     * @param table the map associating a node to selection squares
     */
    public void addSelectionSquares(Hashtable table){
        
        if(table!=null){
            selectionsquares.putAll(table);
        }
    }
    /**
     * adds an undo/redo action to the action list
     * @param action the action to be added
     */
    public void addUndoRedoAction(SVGUndoRedoAction action){
      if(action!=null && getScrollPane().getUndoRedo()!=null){
        if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(action.getName());
        undoRedoList.addLast(action);
        undoRedoList.setName(action.getName());
      }
    }
    /** adds or removes a node from the selected nodes 
     * @param node the node that will be treated
     */
    public void manageNode(Node node){
      if(node!=null && ( !( isSelected(node) && getCurrentSelection().size()==1) || isMultiSelectionEnabled())){
        final Node finalNode=node;
        int state=addToSelection(node);
        if(state==NODE_ADDED){
          //create the undo/redo action and insert it into the undo/redo stack
          if(getScrollPane().getUndoRedo()!=null){
            SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoSelect){
              /**
               * used to call all the actions that have to be done to undo an action
               */
              public void undo() {
                //deselects the node
                deselect(finalNode);
                refreshSelection();
              }
              /**
               * used to call all the actions that have to be done to redo an action
               */
              public void redo(){		
                //selects the node
                select(finalNode);
                refreshSelection();
              }
            };
            //creates a undoredo list into which the action will be inserted
            if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(undoRedoSelect);
            undoRedoList.add(action);
            undoRedoList.setName(undoRedoSelect);
          }
        }else if(state==NODE_REMOVED){
          //create the undo/redo action and insert it into the undo/redo stack
          if(getScrollPane().getUndoRedo()!=null){
            SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoDeselect){
              /**
               * used to call all the actions that have to be done to undo an action
               */
              public void undo() {
                //selects the node							
                select(finalNode);
                refreshSelection();
              }
              /**
               * used to call all the actions that have to be done to redo an action
               */
              public void redo(){	
                //deselects the node								
                deselect(finalNode);
                refreshSelection();
              }
            };
            //creates a undoredo list into which the action will be inserted						
            if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(undoRedoDeselect);
            undoRedoList.addLast(action);
            undoRedoList.setName(undoRedoDeselect);
            
          }
        }
      }
    }
        
    /**
     * refreshes the hashtable of the selected items, and draws the selections
     */	 
    public void refreshSelection(){
      //redraws the selections of the selected nodes
      redrawSelection();
      //adds the current undo/redo list into the undo/redo stack
      if(scrollPane.getUndoRedo()!=null && undoRedoList!=null && undoRedoList.size()>0){
        SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoDeselect){
          /**
           * used to call all the actions that have to be done to undo an action
           */
          public void undo() {
            selectionChanged();
          }
          /**
           * used to call all the actions that have to be done to redo an action
           */
          public void redo(){	
            selectionChanged();
          }
        };
        
        undoRedoList.addLast(action);
        getScrollPane().getUndoRedo().addAction(undoRedoList);	
        undoRedoList=null;
      }
      //notifies that the selection has changed
      selectionChanged();	
    }

    
    /**
     * notifies the refresh manager that a refresh should be done
     */
    protected void redrawSelection(){
      synchronized(this){shouldRefresh=true;}
    }
    
    /**
     * redraws the selections over the nodes that have been selected
     */
    protected void redrawSelectionMethod(){
      clearSelectionSquares();
      Hashtable selItems=new Hashtable(selectedItems);
      LinkedHashMap cselection=new LinkedHashMap(currentSelection);
      Iterator it=selItems.keySet().iterator(), it2;
      LinkedList list=null;
      String current=null;
      Object obj=null, current2=null;
      Hashtable table=null;
      Hashtable sitems=null;
      
      //invokes on each module the "select" method passing the linked list containing the nodes to be selected for each module as an argument
      while(it.hasNext()){
        current=(String)it.next();
        if(current!=null){
          list=(LinkedList)selItems.get(current);
        }
        if(list!=null && list.size()>0){
          sitems=new Hashtable();
          it2=list.iterator();
          while(it2.hasNext()){
            try{
              current2=it2.next();
            }catch(Exception ex){
              ex.printStackTrace();
              current2=null;
            }
            if(current2!=null){
              String type="";
              type=(String)cselection.get(current2);
              if(type!=null){
                sitems.put(current2,type);
              }
            }
          }
          obj=getScrollPane().getStaticClassObject(current);
          if(obj==null)obj=getScrollPane().getStaticClassObject("any");
          try{
            Class[] carg1={Hashtable.class};
            Object[] arg1={sitems};
            //invokes the "select" method 
            
            obj.getClass().getMethod("select",carg1).invoke(obj,arg1);
          }catch (Exception ex){
            ex.printStackTrace();
          }
          
          try{
            table=(Hashtable)obj.getClass().getMethod("getSelectionSquares",null).invoke(obj,null);
            if(table!=null){
                selectionsquares.putAll(table);
            }
          }catch (Exception ex){
            ex.printStackTrace();
          }
        }
      }      
      if(selItems.size()==0)scrollPane.getSVGCanvas().delayedRepaint();
    }  
    /**
     * deselects a node
     * @param node the node to be deselected
     */
    protected void deselect(Node node){
      Set keys=selectedItems.keySet();
      Iterator it=keys.iterator();
      LinkedList list=null;
      String current=null;
      
      //finds and removes the node from the selected nodes
      while(it.hasNext()){
        current=(String)it.next();
        if(current!=null){
          list=(LinkedList)selectedItems.get(current);
        }
        if(list!=null && list.size()>0){
          if(list.contains(node)){
            list.remove(node);
            currentSelection.remove(node);
            if(list.size()<=0)selectedItems.remove(current);
            break;
          }
        }else selectedItems.remove(current);
      }
    }
    
    /**
     * selects a node
     * @param node the node to be selected
     */
    protected void select(Node node){
      if(node!=null){
        //the name of the node corresponds to the name of the module managing the node
        String name=node.getNodeName();
        //gets the list of the selected nodes managed by one module
        LinkedList list=(selectedItems.containsKey(name))?(LinkedList)selectedItems.get(name):new LinkedList();
        //if the list does not contain the node, the node is added
        if(!list.contains(node)){
            list.add(node);
            if(! lockednodes.contains(node))currentSelection.put(node,"level1");
            else currentSelection.put(node,"lock");
            selectedItems.put(name,list);
        }	
      }
    }
    
    /**
     * selects all the nodes included in the given rectangle
     * @param rect a rectangle
     */
    protected void select(Rectangle rect){
      if(! isMultiSelectionEnabled()) clearSelection();
      SVGDocument doc=getScrollPane().getSVGCanvas().getSVGDocument();
      if(rect!=null && doc!=null){
        Element root=doc.getRootElement();
        if(root!=null){
          final LinkedList nodesToBeSelected=new LinkedList();
          if(root!=null && root.hasChildNodes()){
            Node current=root.getFirstChild();
            Rectangle r2=null;
            while(current!=null){
              r2=getScrollPane().getSVGToolkit().getNodeBounds(current);
              if(r2!=null){
                Rectangle2D.Double r3=new Rectangle2D.Double(r2.x, r2.y, r2.width+1, r2.height+1);
                if(r3!=null){
                  //if the node is contained in the rectangle, it is selected
                  if(r3!=null && rect.contains(r3)){
                      addToSelection(current);
                      nodesToBeSelected.add(current);
                  }
                }
              }
              current=current.getNextSibling();
            }
                
            //create the undo/redo action and insert it into the undo/redo stack
            if(getScrollPane().getUndoRedo()!=null){
              SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoSelect){
                /**
                 * used to call all the actions that have to be done to undo an action
                 */
                public void undo() {
                  Node current=null;
                  Iterator it=nodesToBeSelected.iterator();
                  while(it.hasNext()){
                    try{
                      current=(Node)it.next();
                    }catch (Exception ex){
                      ex.printStackTrace();
                      current=null;
                    }
                    
                    if(current!=null){
                      deselect(current);
                    }
                  }
                  redrawSelection();
                }
                /**
                 * used to call all the actions that have to be done to redo an action
                 */
                public void redo(){		
                  Node current=null;
                  Iterator it=nodesToBeSelected.iterator();
                  while(it.hasNext()){
                    try{
                      current=(Node)it.next();
                    }catch (Exception ex){
                      ex.printStackTrace();
                      current=null;
                    }
                    
                    if(current!=null){
                      addToSelection(current);
                    }
                  }
                  redrawSelection();
                }
              };
              //creates a undoredo list into which the action will be inserted
              if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(undoRedoSelect);
              undoRedoList.add(action);
              undoRedoList.setName(undoRedoSelect);
            }
          }
          refreshSelection();
        }
      }
    }
        
    /**
     * tells whether a node is selected or not
     * @param node 
     * @return true if the given node is selected
     */
    protected boolean isSelected(Node node){
      return currentSelection.containsKey(node);
    }
        
    /**
     * tells if one or more nodes are selected
     * @return true if one or more nodes are selected
     */
    protected boolean isMultipleSelected(){
      if(currentSelection.size()>0 && currentSelection.keySet().size()>0){
        Set keys=currentSelection.keySet();
        Iterator it=keys.iterator();
        if(it.hasNext() && it.next()!=null)return true;
      }	
      return false;
    }	
    
    /**
     * clears the hashtable of the selected items
     */
    public void clearSelection(){
      //creates the undo/redo action and insert it into the undo/redo stack
      if(getScrollPane().getUndoRedo()!=null && isMultipleSelected()){
        //creates a copy of the hashtable of the selected items
        final LinkedList copyOfSelectedItems=new LinkedList(currentSelection.keySet());
        
        SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoDeselectAll){
          /**
           * used to call all the actions that have to be done to undo an action
           */
          public void undo() {
            currentSelection.clear();
            //reselects all the nodes that have been deselected
            Iterator it=copyOfSelectedItems.iterator();
            Node current=null;
            while(it.hasNext()){
              try{
                current=(Node)it.next();
              }catch (Exception e){
                e.printStackTrace();
              }
              if(current!=null){
                addToSelection(current);
              }
            }
            redrawSelection();
          }
          
          /**
           * used to call all the actions that have to be done to redo an action
           */
          public void redo(){
            deselectAll(new Boolean(true));
            selectionsquares.clear();
          }
        };
        //creates or gets the current undo/redo list and adds the new action into it
        if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(undoRedoDeselectAll);
        undoRedoList.addLast(action);
        undoRedoList.setName(undoRedoDeselectAll);
      }      
      deselectAll(new Boolean(true));
      selectionsquares.clear();
    }
    
    /**
     *deselects all the nodes 
     *@param makeRepaint the boolean telling to repaint or not
     */
    protected void deselectAll(Boolean makeRepaint){
      selectedItems.clear();
      currentSelection.clear();
      Class[] carg={Boolean.class};
      Object[] arg={makeRepaint};
      getScrollPane().invokeOnModules("deselectAll",carg,arg);
    }
    
    /**
     * selects all the nodes of the dom 
     */
    protected void selectAll(){
      SVGDocument doc=getScrollPane().getSVGCanvas().getSVGDocument();
      Element root=doc.getRootElement();
      final LinkedList nodesThatWereSelected=new LinkedList(currentSelection.keySet());
      deselectAll(new Boolean(false));
      final LinkedList nodesToBeSelected=new LinkedList();
      if(root!=null && root.hasChildNodes()){
        Node current=root.getFirstChild();
        while(current!=null){
          addToSelection(current);
          nodesToBeSelected.add(current);
          current=current.getNextSibling();
        }
        //create the undo/redo action and insert it into the undo/redo stack
        if(getScrollPane().getUndoRedo()!=null){
          SVGUndoRedoAction action=new SVGUndoRedoAction(undoRedoSelect){
          /**
           * used to call all the actions that have to be done to undo an action
           */
            public void undo() {
              deselectAll(new Boolean(false));
              Node current=null;
              Iterator it=nodesThatWereSelected.iterator();
              while(it.hasNext()){
                try{
                    current=(Node)it.next();
                }catch (Exception ex){
                  ex.printStackTrace();
                  current=null;
                }
                
                if(current!=null){
                    addToSelection(current);
                }
              }
              redrawSelection();
            }
            /**
             * used to call all the actions that have to be done to redo an action
             */
              public void redo(){
                  
                  deselectAll(new Boolean(false));
                  
                  Node current=null;
                  Iterator it=nodesToBeSelected.iterator();
                  while(it.hasNext()){
                      try{
                          current=(Node)it.next();
                      }catch (Exception ex){
                        ex.printStackTrace();
                        current=null;
                      }
                      
                      if(current!=null){
                          addToSelection(current);
                      }
                  }
                  redrawSelection();
              }
            };
            //creates a undoredo list into which the action will be inserted
            if(undoRedoList==null)undoRedoList=new SVGUndoRedoActionList(undoRedoSelect);
            undoRedoList.addLast(action);
            undoRedoList.setName(undoRedoSelect);
          }
      }
      refreshSelection();
    }
    
    /**
     * gets the list of the selected nodes
     * @return the list of the selected nodes
     */
    protected LinkedList getCurrentSelection(){
      LinkedList list=new LinkedList();
      Set set=currentSelection.keySet();
      Iterator it=set.iterator();
      Node current=null;
      while(it.hasNext()){
        try{
          current=(Node)it.next();
        }catch (Exception ex){
          ex.printStackTrace();
          current=null;
        }
        if(current!=null){
          String type="";
          type=(String)currentSelection.get(current);
          if(type!=null && ! type.equals("lock")){
            list.add(current);
          }
        }
      }
      return list;
    }
    
    /**
     * 
     * @return the map of the current selection
     * @param frame the current frame
     */
    protected LinkedHashMap getSelection(){
      return currentSelection;
    }
    
    /**
     * adds the current node to the selection
     * @param node the node to be added
     * @return the type of what has been done to the node
     */
      protected int addToSelection(Node node){
        //the name of the node corresponds to the name of the module managing the node
        String name=node.getNodeName();
        //gets the list of the selected nodes managed by one module
        LinkedList list=null;
        list=(selectedItems.containsKey(name))?(LinkedList)selectedItems.get(name):new LinkedList();
          
        //if the list does not contain a node, the node is added, otherwise, it is removed
        if(!list.contains(node)){
          list.add(node);
          selectedItems.put(name,list);
          
          //gets the default first selection level for the node
          SVGShape shape=null;
          try{
            shape=(SVGShape)getScrollPane().getStaticClassObject(node.getNodeName());
            if(shape==null)shape=(SVGShape)getScrollPane().getStaticClassObject("any");
              
          }catch (Exception ex){
            ex.printStackTrace();
            shape=null;
          }
          String type=(shape!=null)?shape.getNextLevel("default"):"level1";
          
          //sets the selection level
          if(!lockednodes.contains(node))currentSelection.put(node, type);
          else currentSelection.put(node, "lock");

          return NODE_ADDED;
        }else if(isMultiSelectionEnabled()){
          list.remove(node);
          currentSelection.remove(node);
          return NODE_REMOVED;
        }
        return NODE_NOT_ADDED;
      }
        
    /**
     * clears the selectionsquares hashtable
     */
    protected void clearSelectionSquares(){
      Iterator it=selectionsquares.keySet().iterator();
      LinkedList currentList=null;
      while(it.hasNext()){
        currentList=(LinkedList)selectionsquares.get(it.next());
        if(currentList!=null)currentList.clear();
      }
      selectionsquares.clear();
    }
    /**
     * gets the SelectionSquare object on which a mouse event has been done
     * @param point the point on the canvas with a 1.0 scale
     * @return the SelectionSquare object on which a mouse event has been done
     * null if the mouse event has not been done on the representation of a SelectionSquare object
     */
    public SelectionSquare getSquare(Point point){
      Point2D.Double pt=getScrollPane().getSVGToolkit().getScaledPoint(new Point2D.Double(point.x, point.y), false);
      Iterator it=selectionsquares.keySet().iterator(), it2=null;
      LinkedList currentList=null;
      SelectionSquare square=null;
      Rectangle2D.Double rect=null;
      while(it.hasNext()){
        currentList=(LinkedList)selectionsquares.get(it.next());
        if(currentList!=null){
          it2=currentList.iterator();
          while(it2.hasNext()){
            square=(SelectionSquare)it2.next();
            rect=square.getRectangle();
            if(rect!=null){
              if(rect.contains(pt))return square;
            }
          }
        }
      }
      return null; 
    }
    
  } 
  
  /**
     * gets the name of the module
     * @return the name of the module
     */
    public String getName(){
        return idselection;
    }
  

  /**
   * @author Sudheer V. Pujar
   *
   * the class representing one of the squares displayed when a node is selected
   */
  public class SelectionSquare{
    private Node node;
    private String type;
    private Rectangle2D.Double rect;
    private Cursor cursor;
    
    /**
     * the constructor of the class
     * @param node the selected node
     * @param type the type of the selection
     * @param rect the position and size of the square
     * @param cursor the cursor associated with this square
     */
    public SelectionSquare(Node node, String type, Rectangle2D.Double rect, Cursor cursor){
      this.node=node;
      this.type=type;
      this.rect=rect;
      this.cursor=cursor;
    }
    
    /**
     * 
     * @return the selected node
     */
    public Node getNode(){
      return node;
    }
    
    /**
     * 
     * @return the type of the selection
     */
    public String getType(){
      return type;
    }
    
    /**
     * 
     * @return the position and size of the square
     */
    public Rectangle2D.Double getRectangle(){
      return rect;
    }
    
    /**
     * 
     * @return the cursor associated with the square
     */
    public Cursor getCursor(){
      return cursor;
    }
  }
}