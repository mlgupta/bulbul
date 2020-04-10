package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 26-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGText extends SVGShape  {
  
  private String shapetextlabel="Text";
  
  private String shapetextundoredocreate="Create Text";
  private String shapetextundoredotranslate="Translate Text";
  private String shapetextundoredoresize="Resize Text";
  private String shapetextundoredorotate="Rotate Text";
  
  private String shapetexthelpcreate="Draw a text";
  

  private DecimalFormat format;
  private final SVGText svgText=this;
  private TextActionListener textAction=null;
  
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGText(Studio studio) {
    super(studio);
    ids.put("id","text");
    
    try{
      labels.put("label", shapetextlabel);
      labels.put("undoredocreate", shapetextundoredocreate);
      labels.put("undoredotranslate", shapetextundoredotranslate);
      labels.put("undoredoresize", shapetextundoredoresize);
      labels.put("undoredorotate", shapetextundoredorotate);
      labels.put("helpcreate", shapetexthelpcreate);
		}catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("######.#",symbols);
    textAction=new TextActionListener();
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
					svgToolItem.setEnabled(true);
					//svgToolItem.setIcon(icon);
				}else{
					svgToolItem.setEnabled(false);
					//svgToolItem.setIcon(disabledIcon);
				}
				textAction.reset();
			}
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbText();
    svgToolItem.addActionListener(textAction);
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param point
   * @param value
   */
  protected void drawText(SVGTab svgTab, Point point, String value){
    if(svgTab!=null && point!=null){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					if(value!=null && !value.equals("")){
						
            // creates the text element
						final Element text = document.createElementNS(document.getDocumentElement().getNamespaceURI(),"text");
						Text textValue=document.createTextNode(value);
						text.appendChild(textValue);
			
						text.setAttributeNS(null,"x", format.format(point.x));
						text.setAttributeNS(null,"y", format.format(point.y));
						String colorString=getStudio().getColorChooser().getColorString(getStudio().getSvgColorManager().getCurrentColor());
						text.setAttributeNS(null, "style", "font-size:12pt;fill:".concat(colorString.concat(";")));
				
						//attaches the element to the svg root element	
						parent.appendChild(text);
			
						//sets that the svg has been modified
						svgTab.setModified(true);

						//create the undo/redo action and insert it into the undo/redo stack
						if(getStudio().getSvgUndoRedo()!=null){
							SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
								public void undo(){
								  parent.removeChild(text);
								}
								public void redo(){
								  parent.appendChild(text);
								}
							};
				
							SVGSelection selection=getStudio().getSvgSelection();
				
							if(selection!=null){
								selection.deselectAll(svgTab, false, true);
								selection.addUndoRedoAction(svgTab, action);
								selection.handleNodeSelection(svgTab, text);
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
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(textAction!=null){
      textAction.cancelActions();
    }
  }
  
  /**
   * 
   * @description 
   * @version 1.0 26-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class TextActionListener  implements    ActionListener {
    
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final TextActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected TextActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("text");
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

				TextMouseListener textMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						textMouseListener=new TextMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(textMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(textMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, textMouseListener);
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
        TextMouseListener textMouseListener=null;
        
        //adds the new motion adapters
        for(it=svgTabs.iterator(); it.hasNext();){
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){
            textMouseListener=new TextMouseListener(svgTab);
            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(textMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(textMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, textMouseListener);
          }
        }
      }
    }
  }
  
  /**
   * 
   * @description 
   * @version 1.0 26-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class TextMouseListener extends MouseAdapter implements MouseMotionListener{
  
    private Point  point=null;
    private SVGTab  svgTab;
    private JTextField textField;
    /**
     * 
     * @description 
     * @param svgTab
     */
    public TextMouseListener(SVGTab svgTab){
      this.svgTab=svgTab;
      final SVGTab finalSvgTab=svgTab;
      textField = new JTextField();
      textField.setOpaque(false);
      textField.setBorder(null);
      textField.setSize(100,20);
      textField.addFocusListener(new FocusListener(){
        public void focusLost(FocusEvent evt){
          insertText(finalSvgTab);
        }
        public void focusGained(FocusEvent evt){}
      });
      textField.addKeyListener(new KeyListener(){
        public void keyTyped(KeyEvent evt){}
        public void keyReleased(KeyEvent evt){}
        public void keyPressed(KeyEvent evt){
          if(evt.getKeyCode()==KeyEvent.VK_ENTER ){
            insertText(finalSvgTab);
          }
        }
      });
    }
  
    
    /**
     * 
     * @description 
     */
    private void insertText(SVGTab svgTab){
      if (textField!=null){
        final SVGTab finalSvgTab=svgTab;
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().remove(textField);    
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().immediateRepaint();
        final String textValue=textField.getText();
        final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
        // attaches the element to the svg root element	
        if(queue.getThread()!=Thread.currentThread()){
          queue.invokeLater(new Runnable(){
            public void run(){
              svgText.drawText(finalSvgTab, point, textValue);
            }
          });
        }else{
          svgText.drawText(finalSvgTab, point, textValue);
        }
        textField=null;
      }
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
        textField.setLocation(point);
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().add(textField);
        textField.requestFocus();
      }
      getStudio().cancelActions(true);
    }
  }
}