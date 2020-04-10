package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.Studio;
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
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGCircle extends SVGShape  {
  
  private String shapecirclelabel="Circle";
  
  private String shapecircleundoredocreate="Create Circle";
  private String shapecircleundoredotranslate="Translate Circle";
  private String shapecircleundoredoresize="Resize Circle";
  private String shapecircleundoredorotate="Rotate Circle";
  
  private String shapecirclehelpcreate="Draw a circle";

  private final SVGCircle  svgCircle=this;
  private DecimalFormat  format;
  private CircleActionListener circleAction=null;
  
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGCircle(Studio studio){
    super(studio);
    ids.put("id","circle");
    
    try{
      labels.put("label", shapecirclelabel);
      labels.put("undoredocreate", shapecircleundoredocreate);
      labels.put("undoredotranslate", shapecircleundoredotranslate);
      labels.put("undoredoresize", shapecircleundoredoresize);
      labels.put("undoredorotate", shapecircleundoredorotate);
      labels.put("helpcreate", shapecirclehelpcreate);
    }catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    circleAction=new CircleActionListener();
    
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
				circleAction.reset();
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbCircle();
    svgToolItem.addActionListener(circleAction);
  }
  
  /**
   * 
   * @description 
   * @param bounds
   * @param svgTab
   */
  protected void drawCircle(SVGTab svgTab, Rectangle bounds){
    if(svgTab!=null && bounds!=null){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					// creates the circle
					final Element circle=document.createElementNS(document.getDocumentElement().getNamespaceURI(),"circle");
					circle.setAttributeNS(null,"cx", format.format(bounds.x+bounds.width/2));
					circle.setAttributeNS(null,"cy", format.format(bounds.y+bounds.height/2));
					circle.setAttributeNS(null,"r", format.format(bounds.width/2));
					String colorString=getStudio().getColorChooser().getColorString(getStudio().getSvgColorManager().getCurrentColor());
					circle.setAttributeNS(null, "style", "fill:".concat(colorString.concat(";")));

					//sets that the svg has been modified
					svgTab.setModified(true);
			
					//attaches the element to the svg root element	
					parent.appendChild(circle);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){	
							  parent.removeChild(circle);
							}

							public void redo(){	
							  parent.appendChild(circle);
							}
						};
				
						SVGSelection selection=getStudio().getSvgSelection();
				
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, circle);
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
   * @param bounds
   * @param g2D
   * @param svgTab
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Rectangle2D.Double bounds){
    if(svgTab!=null && g2D!=null && bounds!=null){
			//computes and draws the new awt circle to be displayed
			g2D.setXORMode(Color.white);
			g2D.setColor(GHOST_COLOR);
			g2D.drawOval((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
			svgTab.getStudio().getMainStatusBar().setWidth(format.format(bounds.width));
			svgTab.getStudio().getMainStatusBar().setHeight(format.format(bounds.height));
		}		
  }
  
  public void cancelActions(){
    if(circleAction!=null){
      circleAction.cancelActions();
		}
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class CircleActionListener   implements ActionListener {
  
    final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    final CircleActionListener  action=this;
    Cursor  createCursor;
    Object  source=null;
    boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected CircleActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("circle");
    }  
    /**
     * 
     * @description 
     */
    protected void reset(){
      if(isActive){
				Collection frames=getStudio().getSvgTabManager().getSvgTabs();
				Iterator it;
				SVGTab svgTab=null;
				LinkedList toBeRemoved=new LinkedList();
				Object mouseListener=null;
				
				//removes all the motion adapters from the frames
				for(it=mouseAdapterSvgTabs.keySet().iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! frames.contains(svgTab)){
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

				CircleMouseListener circleMouseListener=null;
				
				//adds the new motion adapters
				for(it=frames.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						circleMouseListener=new CircleMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(circleMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(circleMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, circleMouseListener);
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
							if(mouseListener!=null && ((CircleMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((CircleMouseListener)mouseListener).paintListener, true);
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
        Collection frames=getStudio().getSvgTabManager().getSvgTabs();
        Iterator it;
        SVGTab svgTab=null;
        CircleMouseListener circleMouseListener=null;
        
        //adds the new motion adapters
        for(it=frames.iterator(); it.hasNext();){
            
          try{svgTab=(SVGTab)it.next();}catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){

            circleMouseListener=new CircleMouseListener(svgTab);

            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(circleMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(circleMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);	
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, circleMouseListener);
          }
        }
      }
    }    
  }
  
  protected class CircleMouseListener  extends MouseAdapter implements  MouseMotionListener{
  
    Point  point1=null;
    Point  point2=null;
    SVGTab svgTab;
    SVGPaintListener  paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public CircleMouseListener(SVGTab svgTab){
      this.svgTab=svgTab;
      final SVGTab finalSvgTab=svgTab;
      
      //adds a paint listener
      paintListener=new SVGPaintListener(){
        public void paintToBeDone(Graphics g) {
          if(point1!=null && point2!=null){
            Rectangle rectangle2D=getStudio().getSvgToolkit().getComputedSquare(new Point(point1), new Point(point2));
            Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.x, rectangle2D.y, rectangle2D.width, rectangle2D.height);
            Rectangle2D.Double computedBounds=getStudio().getSvgToolkit().getScaledRectangle(finalSvgTab, rectangle2DD, false);
            
            //draws the shape of the element that will be created if the user released the mouse button
            svgCircle.drawGhost(finalSvgTab, (Graphics2D)g, computedBounds);
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
					final Rectangle rectangle=getStudio().getSvgToolkit().getComputedSquare(new Point(point1), new Point(point));
					final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
					if(queue.getThread()!=Thread.currentThread()){
						queue.invokeLater(new Runnable(){
							public void run(){
								svgCircle.drawCircle(svgTab, rectangle);
							}
						});
					}else{
						svgCircle.drawCircle(svgTab, rectangle);
					}
				}
				getStudio().cancelActions(true);
				point1=null;
				point2=null;
    }
  }

}