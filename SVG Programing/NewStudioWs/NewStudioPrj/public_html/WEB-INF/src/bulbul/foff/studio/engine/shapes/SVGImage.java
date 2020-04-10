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
public class SVGImage extends SVGShape  {
  private ImageActionListener   imageAction=null;
  private DecimalFormat  format;
  private final SVGImage  svgImage=this;

  /**
   * 
   * @description 
   */
  public SVGImage(SVGApplet studio) {
    super(studio);
    ids.put("id","image");
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    imageAction=new ImageActionListener();
    
    final ActionListener svgTabChangedListener=new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
				    
					//menuitem.setEnabled(true);
					//menuitem.setIcon(icon);
					//toolItem.setEnabled(true);
					//toolItem.setIcon(icon);
					
				}else{
				    
					//menuitem.setEnabled(false);
					//menuitem.setIcon(disabledIcon);
					//toolItem.setEnabled(false);
					//toolItem.setIcon(disabledIcon);
				}
				
				imageAction.reset();
			}
		};
		
		//adds the SVGFrame change listener
		studio.getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
  }
  
  /**
   * 
   * @description 
   * @param imagePath
   * @param bounds
   * @param svgTab
   */
  protected void drawImage(SVGTab svgTab, Rectangle bounds, String imagePath){
    if(svgTab!=null && bounds!=null && imagePath!=null && ! imagePath.equals("")){

			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			
			if(getStudio().getSvgSelection()!=null && document!=null){
			    
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				
				if(parent!=null){

					// creates the image element
					final Element image = document.createElementNS(document.getDocumentElement().getNamespaceURI(), "image");
			
					image.setAttributeNS("http://www.w3.org/1999/xlink","xlink:href", imagePath);
					image.setAttributeNS(null,"x", format.format(bounds.x));
					image.setAttributeNS(null,"y", format.format(bounds.y));
					image.setAttributeNS(null,"width", format.format(bounds.width==0?1:bounds.width));
					image.setAttributeNS(null,"height", format.format(bounds.height==0?1:bounds.height));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
		
					//attaches the image to the svg root element
					parent.appendChild(image);
		
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){
							  parent.removeChild(image);
							}
							public void redo(){
							  parent.appendChild(image);
							}
						};
			
						SVGSelection selection=getStudio().getSvgSelection();
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, image);
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
   * @param bounds
   * @param g2D
   * @param svgTab
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Rectangle2D.Double bounds){
    if(svgTab!=null && g2D!=null && bounds!=null){
			//computes and draws the new awt rectangle to be displayed
			g2D.setXORMode(Color.white);
			g2D.setColor(GHOST_COLOR);
			g2D.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
			svgTab.getStudio().getMainStatusBar().setWidth(format.format(bounds.width));
			svgTab.getStudio().getMainStatusBar().setHeight(format.format(bounds.height));
		}		
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(imageAction!=null){
      imageAction.cancelActions();
		}
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class ImageActionListener implements ActionListener{
  
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final ImageActionListener action=this;
    private Cursor createCursor;
    private Object source=null;
    private boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected ImageActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("image");
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
				
				ImageMouseListener imageMouseListener=null;

				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
			
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						imageMouseListener=new ImageMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(imageMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(imageMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, imageMouseListener);
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
					try{svgTab=(SVGTab)it.next();}catch (Exception ex){svgTab=null;}
					if(svgTab!=null){
						//resets the information displayed
						svgTab.getStudio().getMainStatusBar().setWidth("");
						svgTab.getStudio().getMainStatusBar().setHeight("");
						svgTab.getScrollPane().getSvgCanvas().setSVGCursor(svgTab.getStudio().getSvgCursors().getCursor("default"));
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
							if(mouseListener!=null && ((ImageMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((ImageMouseListener)mouseListener).paintListener, true);
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
				isActive=false;
			}
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void actionPerformed(ActionEvent evt){
      /*
      if((e.getSource() instanceof JMenuItem && ! toolItem.isSelected()) || (e.getSource() instanceof JToggleButton)){
				getStudio().cancelActions(false);
				if(getStudio().getSvgTabManager().getCurrentSVGTab()!=null){
					toolItem.setSelected(true);
					//the listener is active
					isActive=true;
					source=e.getSource();

					Collection frames=getStudio().getSvgTabManager().getSvgTabs();
					Iterator it;
					SVGTab svgTab=null;
					ImageMouseListener imageMouseListener=null;
					    
					//adds the new motion adapters
					for(it=frames.iterator(); it.hasNext();){
						try{
              svgTab=(SVGTab)it.next();
            }catch (Exception ex){svgTab=null;}
					
						if(svgTab!=null){
	
							imageMouseListener=new ImageMouseListener(svgTab);
	
							try{
								svgTab.getScrollPane().getSvgCanvas().addMouseListener(imageMouseListener);
								svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(imageMouseListener);
								svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
							}catch (Exception ex){}
							
							mouseAdapterSvgTabs.put(svgTab, imageMouseListener);
						}
					}
				}
			}
    */
    }
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class ImageMouseListener extends MouseAdapter implements MouseMotionListener{
  
    Point  point1=null;
    Point  point2=null;
    SVGTab  svgTab;
    SVGPaintListener  paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public ImageMouseListener(SVGTab svgTab){
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
            svgImage.drawGhost(finalSvgTab, (Graphics2D)g, computedBounds);
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
      /*
       //the dialog box to specify the image file
				String uri;
				JFileChooser fileChooser=new JFileChooser();
				
				if(getSVGEditor().getResource().getCurrentDirectory()!=null){
				    
					fileChooser.setCurrentDirectory(getSVGEditor().getResource().getCurrentDirectory());
				}
				
				//the file filter
				fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter(){

					public boolean accept(File f) {
					    
					    String name=f.getName();
					    name=name.toLowerCase();
					    
						if(f.isDirectory() || name.endsWith(".svg") || name.endsWith(".png") || name.endsWith(".jpg")){
						    
						    return true;
						}
						
						else return false;
					}

					public String getDescription() {
					    
						return (String)labels.get("filefilter");
					}
				});
				
				fileChooser.setMultiSelectionEnabled(false); 
					
				int returnVal=fileChooser.showOpenDialog(frame.getSVGEditor().getMainFrame());
				
				if(returnVal==JFileChooser.APPROVE_OPTION) {
				    
					getSVGEditor().getResource().setCurrentDirectory(fileChooser.getCurrentDirectory());
					
					URI docUri=null;
					
					try{
					    docUri=new URI(frame.getScrollPane().getSVGCanvas().getURI());
					}catch (Exception ex){docUri=null;}
					
					if(docUri!=null){
					    
						//relativizes the uri
						URI rUri=null;
						URI u=fileChooser.getSelectedFile().toURI();
						
						try{
							File docFile=new File(docUri);
							docUri=docFile.getParentFile().toURI();
							rUri=docUri.relativize(u);
						}catch (Exception ex){}
						
						uri=rUri.toString();
						
					}else{
					    
						uri=fileChooser.getSelectedFile().toURI().toString();
					}

					Point point=getSVGEditor().getSVGToolkit().getAlignedWithRulersPoint(frame, evt.getPoint());

					//creates the image in the SVG document
					if(point1!=null && point1.x>=0 && point1.y>=0 && point!=null){
					
						final RunnableQueue queue=frame.getScrollPane().getSVGCanvas().getUpdateManager().getUpdateRunnableQueue();
						final Rectangle rect=getSVGEditor().getSVGToolkit().getComputedRectangle(new Point(point1), new Point(point));

						if(queue.getThread()!=Thread.currentThread()){
						    
							final String furi=new String(uri);

							queue.invokeLater(new Runnable(){
							    
								public void run(){
								    
									svgImage.drawImage(frame, rect, furi);
								}
							});
							
						}else{
						    
							svgImage.drawImage(frame, rect, uri);
						}
					}
				}
				
				getSVGEditor().cancelActions(true);
				point1=null;
				point2=null;
       */
    }
  
  }
  
}