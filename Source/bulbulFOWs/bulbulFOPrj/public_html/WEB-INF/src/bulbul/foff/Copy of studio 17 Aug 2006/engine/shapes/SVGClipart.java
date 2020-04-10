package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGIconCanvas;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
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
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 15-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGClipart extends SVGShape {
  
  private DecimalFormat format;
  private final SVGClipart svgClipart=this;
  private final LinkedList clipartActions=new LinkedList();
  
  private String shapeclipartundoredocreate="Insert Clipart";
  private String shapeclipartlabel="Clipart";
  
  /**
   * 
   * @description 
   */
  public SVGClipart(Studio studio) {
    super(studio);
    ids.put("id","clipart");
    try{
      
      labels.put("label", shapeclipartlabel);
      labels.put("undoredocreate", shapeclipartundoredocreate);
      
		}catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("######.#",symbols);
    
    
    //a listener that listens to the changes of the SVGTabs
    final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
        for (Iterator it=clipartActions.iterator();it.hasNext();){
          ClipartActionListener clipartAction=null;
          try {
            clipartAction=(ClipartActionListener)it.next();
          }catch (Exception ex) {clipartAction=null;}
          if(clipartAction!=null){
            clipartAction.reset();
          }
        }
			}
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
  }
  
  protected void drawClipart(SVGTab svgTab, Point point, SVGIconCanvas svgIconCanvas){
    if(svgTab!=null && point!=null){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					if(svgIconCanvas!=null){
            // creates the clipart element
            SVGDocument clipartDocument=svgIconCanvas.getSVGDocument();
            
            if (clipartDocument!=null) {
              Element clipartRoot = clipartDocument.getDocumentElement();
              
              if (clipartRoot!=null) {
                Node clipart = null;
                Node defs = null;
                Node metadata=null;
                AffineTransform affineTransform=null;
                SVGTransformMatrix matrix;
                if(clipartRoot!=null){
                  NodeList clipartRootChildNodes=clipartRoot.getChildNodes();
                  for(int i=0; i<clipartRootChildNodes.getLength();i++){
                    Node node=clipartRootChildNodes.item(i);
                    if (node!=null ){
                      if(node.getNodeName().equals("defs")){
                        defs=node.cloneNode(true);
                      }else if( node.getNodeName().equals("metadata")){
                        metadata=node.cloneNode(true);
                      }else if( node.getNodeName().equals("g")){
                        if(node.getChildNodes().getLength()>0){
                          clipart=node.cloneNode(true);
                          affineTransform=AffineTransform.getTranslateInstance(point.x, point.y);
                          matrix=getStudio().getSvgToolkit().getTransformMatrix(clipart);
                          matrix.concatenateTransform(affineTransform);
                          getStudio().getSvgToolkit().setTransformMatrix(clipart, matrix);
                        }
                      }
                    }
                  }
                }
                
                if (clipart!=null){
                  final Element  finalDefs = (defs!=null)?(Element)document.importNode(defs,true):null;
                  final Element  finalMetadata = (metadata!=null)?(Element)document.importNode(metadata,true):null;
                  final Element finalClipart = (Element)document.importNode(clipart,true);
                  
                  //attaches the element to the svg root element	
                  
                  
                  if(finalDefs!=null){
                    parent.appendChild(finalDefs);
                  }
                  
                  if(finalMetadata!=null){
                    parent.appendChild(finalMetadata);
                  }
                  
                  parent.appendChild(finalClipart);
            
                  //sets that the svg has been modified
                  svgTab.setModified(true);
      
                  //create the undo/redo action and insert it into the undo/redo stack
                  if(getStudio().getSvgUndoRedo()!=null){
                    SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
                      public void undo(){
                        
                        if(finalDefs!=null){
                          parent.removeChild(finalDefs);
                        }
                        if(finalMetadata!=null){
                          parent.removeChild(finalMetadata);
                        }
                        parent.removeChild(finalClipart);
                      }
                      public void redo(){
                        
                        if(finalDefs!=null){
                          parent.appendChild(finalDefs);
                        }
                        if(finalMetadata!=null){
                          parent.appendChild(finalMetadata);
                        }
                        parent.appendChild(finalClipart);
                      }
                    };
              
                    SVGSelection selection=getStudio().getSvgSelection();
              
                    if(selection!=null){
                      selection.deselectAll(svgTab, false, true);
                      selection.addUndoRedoAction(svgTab, action);
                      selection.handleNodeSelection(svgTab, finalClipart);
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
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param svgClipartToolItem
   */
  public void addClipartAction(SVGIconCanvas svgClipartToolItem){
    ClipartActionListener clipartAction=new ClipartActionListener(svgClipartToolItem);
    svgClipartToolItem.addMouseListener(clipartAction);
    clipartActions.add(clipartAction);
  }
  
  /**
   * 
   * @description 
   */
  public void removeClipartActions(){
    getStudio().cancelActions(true);    
    clipartActions.clear();
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    for (Iterator it=clipartActions.iterator();it.hasNext();){
      ClipartActionListener clipartAction=null;
      try{
        clipartAction=(ClipartActionListener)it.next();
      }catch (Exception e) {clipartAction=null;}
      if(clipartAction!=null){
        clipartAction.cancelActions();
      }
    }
  }

  /**
   * 
   * @description 
   * @version 1.0 15-Oct-2005
   * @author Sudheer V Pujar
   */
  protected class ClipartActionListener  extends   MouseAdapter {
    
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final ClipartActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
    private SVGIconCanvas svgClipartToolItem=null;
  
    /**
     * 
     * @description 
     */
    protected ClipartActionListener(SVGIconCanvas svgClipartToolItem){
      createCursor=getStudio().getSvgCursors().getCursor("image");
      this.svgClipartToolItem=svgClipartToolItem;
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
					
				//removes the tabs that have been closed
				
				for(it=toBeRemoved.iterator(); it.hasNext();){
					try{
            mouseAdapterSvgTabs.remove(it.next());
          }catch (Exception ex){}
				}

				ClipartMouseListener clipartMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						clipartMouseListener=new ClipartMouseListener(svgTab,svgClipartToolItem);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(clipartMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(clipartMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, clipartMouseListener);
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
        svgClipartToolItem.setSelected(false); 
				isActive=false;	
			}
    }
  
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseClicked(MouseEvent evt){
      getStudio().cancelActions(false);
      
      if(getStudio().getSvgTabManager().getCurrentSVGTab()!=null){
    
        //the listener is active
        isActive=true;
        source=evt.getSource();

        Collection svgTabs=getStudio().getSvgTabManager().getSvgTabs();

        Iterator it;
        SVGTab svgTab=null;
        ClipartMouseListener clipartMouseListener=null;
        
        //adds the new motion adapters
        for(it=svgTabs.iterator(); it.hasNext();){
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){
            clipartMouseListener=new ClipartMouseListener(svgTab,svgClipartToolItem);
            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(clipartMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(clipartMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, clipartMouseListener);
          }
        }
      }
    }
  }

  protected class ClipartMouseListener extends MouseAdapter implements MouseMotionListener{
  
    private Point  point=null;
    private SVGTab  svgTab;
    private SVGIconCanvas svgClipartToolItem=null;
    /**
     * 
     * @description 
     * @param svgTab
     */
    public ClipartMouseListener(SVGTab svgTab,SVGIconCanvas svgClipartToolItem){
      this.svgTab=svgTab;
      this.svgClipartToolItem=svgClipartToolItem;
    }
  
   
    
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseDragged(MouseEvent evt){
      
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
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseReleased(MouseEvent evt){
      if(evt.getPoint()!=null){
        point=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
        final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
        // attaches the element to the svg root element	
        if(queue.getThread()!=Thread.currentThread()){
          queue.invokeLater(new Runnable(){
            public void run(){
              svgClipart.drawClipart(svgTab, point, svgClipartToolItem);
            }
          });
        }else{
          svgClipart.drawClipart(svgTab, point, svgClipartToolItem);
        }
      }
      getStudio().cancelActions(true);
    }
  }
}