package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGEllipse extends SVGShape  {
  
  private DecimalFormat format;
  private EllipseActionListener  ellipseAction=null;
  private final SVGEllipse  svgEllipse=this;
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGEllipse(SVGApplet studio) {
    super(studio);
    ids.put("id","ellipse");
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    ellipseAction=new EllipseActionListener();
    
    //a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
					svgToolItem.setEnabled(true);
					//svgToolItem.setIcon(icon);
				}else{
					svgToolItem.setEnabled(false);
					//svgToolItem.setIcon(disabledIcon);
				}
				ellipseAction.reset();
			}
		};
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbEllipse();
    svgToolItem.addActionListener(ellipseAction);
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param bounds
   */
  protected void drawEllipse(SVGTab svgTab, Rectangle bounds){
    if(svgTab!=null && bounds!=null){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					//creates the ellipse
					Element ellipse = document.createElementNS(document.getDocumentElement().getNamespaceURI(),"ellipse");
					ellipse.setAttributeNS(null,"cx", format.format(bounds.x+bounds.width/2));
					ellipse.setAttributeNS(null,"cy", format.format(bounds.y+bounds.height/2));
					ellipse.setAttributeNS(null,"rx", format.format(bounds.width/2));
					ellipse.setAttributeNS(null,"ry", format.format(bounds.height/2));
					String colorString="grey";//getStudio().getColorChooser().getColorString(frame, getSVGEditor().getSVGColorManager().getCurrentColor());
					ellipse.setAttributeNS(null, "style", "fill:".concat(colorString.concat(";")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
			
					//creates final variables
					final Node finalEllipse=ellipse;

					//attaches the ellipse to the svg root element
					parent.appendChild(finalEllipse);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){
							  parent.removeChild(finalEllipse);
							}
							public void redo(){
							  parent.appendChild(finalEllipse);
							}
						};
				
						SVGSelection selection=getStudio().getSvgSelection();
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, ellipse);
							selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction((String)labels.get("undoredocreate")){});
							selection.refreshSelection(svgTab);
						}else{
							SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredocreate"));
							actionlist.add(action);
							getStudio().getSvgUndoRedo().addActionList(svgTab,actionlist);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param g2D
   * @param bounds
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Rectangle2D.Double bounds){
    if(svgTab!=null && g2D!=null && bounds!=null){
			g2D.setXORMode(Color.white);			
			g2D.setColor(GHOST_COLOR);
			g2D.drawOval((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
			svgTab.getStudio().getMainStatusBar().setWidth(format.format(bounds.width));
			svgTab.getStudio().getMainStatusBar().setHeight(format.format(bounds.height));
		}		
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(ellipseAction!=null){
		  ellipseAction.cancelActions();
		}
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class EllipseActionListener implements ActionListener{
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final EllipseActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected EllipseActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("ellipse");
    }
  
    /**
     * 
     * @description 
     */
    protected void reset(){
      if(isActive){
				Collection svgTabs=getStudio().getSvgTabManager().getSvgTabs();
				Iterator it;
				SVGTab svgTab=null;
				LinkedList toBeRemoved=new LinkedList();
				Object mouseListener=null;
				
				//removes all the motion adapters from the frames
				for(it=mouseAdapterSvgTabs.keySet().iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! svgTabs.contains(svgTab)){
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
						}catch (Exception ex){}
						toBeRemoved.add(svgTab);
					}
				}
					
				//removes the frames that have been closed
				for(it=toBeRemoved.iterator(); it.hasNext();){
					try{
            mouseAdapterSvgTabs.remove(it.next());
          }catch (Exception ex){}
				}

				EllipseMouseListener ellipseMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
				    
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
			
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
				
						ellipseMouseListener=new EllipseMouseListener(svgTab);

						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(ellipseMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(ellipseMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						
						mouseAdapterSvgTabs.put(svgTab, ellipseMouseListener);
					}
				}				
			}
    }
    
    /**
     * 
     * @description 
     */
    protected void cancelActions(){
      	if(isActive){
				//removes the listeners
				Iterator it;
				SVGTab svgTab=null;
				LinkedList toBeRemoved=new LinkedList();
				Object mouseListener=null;
			
				//removes all the motion adapters from the frames
				for(it=mouseAdapterSvgTabs.keySet().iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
				
					if(svgTab!=null){
						//resets the information displayed
						svgTab.getStudio().getMainStatusBar().setWidth("");
						svgTab.getStudio().getMainStatusBar().setHeight("");
						svgTab.getScrollPane().getSvgCanvas().setSVGCursor(svgTab.getStudio().getSvgCursors().getCursor("default"));
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
							if(mouseListener!=null && ((EllipseMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((EllipseMouseListener)mouseListener).paintListener, true);
							}
						}catch (Exception ex){}
						toBeRemoved.add(svgTab);
					}
				}
				//removes the frames that have been closed
				for(it=toBeRemoved.iterator(); it.hasNext();){
					try{
            mouseAdapterSvgTabs.remove(it.next());
          }catch (Exception ex){}
				}
        svgToolItem.setSelected(false);
				isActive=false;	
			}
    }
  
    /**
     * 
     * @description 
     * @param evt
     */
    public void actionPerformed(ActionEvent evt){
      getStudio().cancelActions(false);
      if(getStudio().getSvgTabManager().getCurrentSVGTab()!=null){
        //the listener is active
        isActive=true;
        source=evt.getSource();
        Collection svgTabs=getStudio().getSvgTabManager().getSvgTabs();
        Iterator it;
        SVGTab svgTab=null;
        EllipseMouseListener ellipseMouseListener=null;
        
        //adds the new motion adapters
        for(it=svgTabs.iterator(); it.hasNext();){
          try{svgTab=(SVGTab)it.next();}catch (Exception ex){svgTab=null;}
          if(svgTab!=null){
            ellipseMouseListener=new EllipseMouseListener(svgTab);
            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(ellipseMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(ellipseMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, ellipseMouseListener);
          }
        }
      }
    }
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class EllipseMouseListener extends MouseAdapter implements MouseMotionListener{
    private Point  point1=null;
    private Point  point2=null;
    private SVGTab  svgTab;
    private SVGPaintListener  paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public EllipseMouseListener(SVGTab svgTab){
      this.svgTab=svgTab;
      final SVGTab finalSvgTab=svgTab;
      //adds a paint listener
      paintListener=new SVGPaintListener(){
        public void paintToBeDone(Graphics g) {
          if(point1!=null && point2!=null){
            Rectangle rectangle=getStudio().getSvgToolkit().getComputedRectangle(new Point(point1), new Point(point2));
            Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            Rectangle2D.Double computedBounds=getStudio().getSvgToolkit().getScaledRectangle(finalSvgTab, rectangle2DD, false);
            //draws the shape of the element that will be created if the user released the mouse button
            svgEllipse.drawGhost(finalSvgTab, (Graphics2D)g,computedBounds);
          }
        }
      };
      svgTab.getScrollPane().getSvgCanvas().addDrawLayerPaintListener(paintListener, false);
    }
  
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseDragged(MouseEvent evt){
      //sets the second point of the element
      point2=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      //asks the canvas to be repainted to draw the shape of the future element
      svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseMoved(MouseEvent evt){
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mousePressed(MouseEvent evt){
      //sets the first point of the area corresponding to the future element
				point1=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseReleased(MouseEvent evt){
      Point point=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      //creates the element in the SVG document
      if(point1!=null && point1.x>=0 && point1.y>=0 && point!=null){
        final Rectangle rectangle=getStudio().getSvgToolkit().getComputedRectangle(new Point(point1), new Point(point));
        final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
        if(queue.getThread()!=Thread.currentThread()){
          queue.invokeLater(new Runnable(){
            public void run(){
              svgEllipse.drawEllipse(svgTab, rectangle);
            }
          });
        }else{
          svgEllipse.drawEllipse(svgTab, rectangle);
        }
      }
      getStudio().cancelActions(true);
      point1=null;
      point2=null;
    }
  }
}