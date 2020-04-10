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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGRectangle extends SVGShape  {

  private String shaperectanglelabel="Rectangle";
  
  private String shaperectangleundoredocreate="Create Rectangle";
  private String shaperectangleundoredotranslate="Translate Rectangle";
  private String shaperectangleundoredoresize="Resize Rectangle";
  private String shaperectangleundoredorotate="Rotate Rectangle";
  
  private String shaperectanglehelpcreate="Draw a rectangle";

  private DecimalFormat  format;
  private final SVGRectangle  svgRectangle=this;
  private RectangleActionListener rectangleAction;
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGRectangle(Studio studio) {
    super(studio);
    ids.put("id","rect");
    
    try{
      labels.put("label", shaperectanglelabel);
      labels.put("undoredocreate", shaperectangleundoredocreate);
      labels.put("undoredotranslate", shaperectangleundoredotranslate);
      labels.put("undoredoresize", shaperectangleundoredoresize);
      labels.put("undoredorotate", shaperectangleundoredorotate);
      labels.put("helpcreate", shaperectanglehelpcreate);
    }catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    
    rectangleAction=new RectangleActionListener();
    
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
        rectangleAction.reset();
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbRectangle();
    svgToolItem.addActionListener(rectangleAction);
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param g2D
   * @param bounds
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Rectangle2D.Double bounds){
    if(g2D!=null && svgTab!=null && bounds!=null){
			g2D.setColor(GHOST_COLOR);
			g2D.setXORMode(Color.white);
			g2D.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
			svgTab.getStudio().getMainStatusBar().setWidth(format.format(bounds.width));
			svgTab.getStudio().getMainStatusBar().setHeight(format.format(bounds.height));
		}		
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param bounds
   */
  protected void drawRectangle(SVGTab svgTab, Rectangle bounds){
    if(svgTab!=null && bounds!=null){
		    
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			
			//normalizing the bounds of the element
			if(bounds.width<=0){
			  bounds.width=1;
			}
			
			if(bounds.height<=0){
			  bounds.height=1;
			}
			
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					// creates the rectangle
					final Element rectangle = document.createElementNS(document.getDocumentElement().getNamespaceURI(),"rect");
					
					rectangle.setAttributeNS(null,"x", format.format(bounds.x));
					rectangle.setAttributeNS(null,"y", format.format(bounds.y));
					rectangle.setAttributeNS(null,"width", format.format(bounds.width==0?1:bounds.width));
					rectangle.setAttributeNS(null,"height", format.format(bounds.height==0?1:bounds.height));
					String colorString=getStudio().getColorChooser().getColorString(getStudio().getSvgColorManager().getCurrentColor());
					rectangle.setAttributeNS(null, "style", "fill:".concat(colorString.concat(";")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
			
					//creates final variables
					
					//attaches the element to the svg parent element	
					parent.appendChild(rectangle);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){
							  parent.removeChild(rectangle);
							}
							public void redo(){
							  parent.appendChild(rectangle);
							}
						};
						SVGSelection selection=getStudio().getSvgSelection();
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, rectangle);
							selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction((String)labels.get("undoredocreate")){});
							selection.refreshSelection(svgTab);
						}else{
							SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredocreate"));
							actionlist.add(action);
							getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(rectangleAction!=null){
		  rectangleAction.cancelActions();
		}
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class RectangleActionListener implements ActionListener{
  
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final RectangleActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected RectangleActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("rectangle");
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
				Object mouseListener=null;
				
				//removes all the motion adapters from the frames
				for(it=new LinkedList(mouseAdapterSvgTabs.keySet()).iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! svgTabs.contains(svgTab)){
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.remove(svgTab);
					}
				}

				RectangleMouseListener rectangleMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						rectangleMouseListener=new RectangleMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(rectangleMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(rectangleMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, rectangleMouseListener);
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
				Object mouseListener=null;
				//removes all the motion adapters from the frames
				for(it=new LinkedList(mouseAdapterSvgTabs.keySet()).iterator(); it.hasNext();){
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
							if(mouseListener!=null && ((RectangleMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((RectangleMouseListener)mouseListener).paintListener, true);
							}
						}catch (Exception ex){}
						mouseAdapterSvgTabs.remove(svgTab);
					}
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
        SVGTab frm=null;
        
        //adds the new mouse adapters
        for(it=frames.iterator(); it.hasNext();){
            
          try{frm=(SVGTab)it.next();}catch (Exception ex){frm=null;}
      
          if(frm!=null){

            RectangleMouseListener rml=new RectangleMouseListener(frm);

            try{
              frm.getScrollPane().getSvgCanvas().addMouseMotionListener(rml);
              frm.getScrollPane().getSvgCanvas().addMouseListener(rml);
              frm.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            
            mouseAdapterSvgTabs.put(frm, rml);
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
  protected class RectangleMouseListener extends MouseAdapter implements  MouseMotionListener {
  
    Point  point1=null;
    Point  point2=null;
    SVGTab  svgTab;
    SVGPaintListener  paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public RectangleMouseListener(SVGTab svgTab){
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
            svgRectangle.drawGhost(finalSvgTab, (Graphics2D)g, computedBounds);
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
              svgRectangle.drawRectangle(svgTab, rectangle);
            }
          });
        }else{
          svgRectangle.drawRectangle(svgTab, rectangle);
        }
      }

      getStudio().cancelActions(true);
      point1=null;
      point2=null;
    }
  }
}