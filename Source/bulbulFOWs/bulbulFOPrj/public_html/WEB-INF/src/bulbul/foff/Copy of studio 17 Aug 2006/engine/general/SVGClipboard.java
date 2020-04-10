package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGPopupMenuItem;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 03-Sep-2005
 * @author Sudheer V. Pujar
 */
public class SVGClipboard implements SVGClassObject {
  
  private final String idclipboard="Clipboard";
  private final String idcopy="Copy";
  private final String idpaste="Paste";
  private final String idcut="Cut";
  private final String iddelete="Delete";
  
  private String labelclipboard="Clipboard";
  private String labelcopy="Copy";
  private String labelpaste="Paste";
  private String labelcut="Cut";
  private String labeldelete="Delete";
  
  private String undoredocopy="Copy";
  private String undoredopaste="Paste";
  private String undoredocut="Cut";
  private String undoredodelete="Delete";
  
  private JButton delete=null;
  private JButton copy=null;
  private JButton cut=null;
  private JButton paste=null;
  
  
  private final LinkedList clipboardContent=new LinkedList();
  private final LinkedList selectedNodes=new LinkedList();
  
  private ActionListener copyListener=null;
  private ActionListener pasteListener=null;
  private ActionListener cutListener=null;
  private ActionListener deleteListener=null;
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGClipboard(Studio studio) {
    
    this.studio=studio;

    final ActionListener svgTabChangedListener=new ActionListener(){
			
			private ActionListener selectionListener=null;
			private SVGSelection selection=null;

			public void actionPerformed(ActionEvent e) {
				//clearing the selected nodes
				selectedNodes.clear();
				
				//disables the Buttons
				copy.setEnabled(false);
				cut.setEnabled(false);
				delete.setEnabled(false);
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				//if a selection listener is already registered on a selection module, it is removed	
				if(selection!=null && selectionListener!=null){
				    
					selection.removeSelectionListener(selectionListener);
				}

				//gets the current selection module	
				if(svgTab!=null){
					selection=getStudio().getSvgSelection();
					if(clipboardContent.size()>0){
					  paste.setEnabled(true);
					}
				}else{
					paste.setEnabled(false);
				}
				if(svgTab!=null && selection!=null){
					manageSelection();
					//the listener of the selection changes
					selectionListener=new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							manageSelection();
						}
					};
					
					//adds the selection listener
					if(selectionListener!=null){
						selection.addSelectionListener(selectionListener);
					}
				}
			}	
			
			/**
			 * updates the selected items and the state of the menu items
			 */
			protected void manageSelection(){
			    
				//disables the menuitems							
				copy.setEnabled(false);
				cut.setEnabled(false);
				delete.setEnabled(false);
				LinkedList list=null;
				
				//gets the currently selected nodes list 
				if(selection!=null){
					list=selection.getCurrentSelection(getStudio().getSvgTabManager().getCurrentSVGTab());
				}
				
				selectedNodes.clear();
				
				//refresh the selected nodes list
				if(list!=null){
				  selectedNodes.addAll(list);
				}
				
				if(selectedNodes.size()>0){
					copy.setEnabled(true);
					cut.setEnabled(true);
					delete.setEnabled(true);
				}								
			}
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    delete=getStudio().getMainToolBar().getMtbDelete();
		deleteListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getStudio().cancelActions(true);
				//sets that the svg document has been modified
				getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				delete(true);
			}
		};
    delete.addActionListener(deleteListener);
    
    copy=getStudio().getMainToolBar().getMtbCopy();
    copyListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getStudio().cancelActions(true);
				copy();
				paste.setEnabled(true);
			}
		};
    copy.addActionListener(copyListener);
    
		
    
    cut=getStudio().getMainToolBar().getMtbCut();
    cutListener=new ActionListener(){
		    
			public void actionPerformed(ActionEvent e){
			    
				getStudio().cancelActions(true);
				
				//sets that the svg document has been modified
				getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				
				cut();
				paste.setEnabled(true);
			}
		};
    cut.addActionListener(cutListener);
    
    paste=getStudio().getMainToolBar().getMtbPaste();
    pasteListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getStudio().cancelActions(true);
				//sets that the svg document has been modified
				getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				paste();
			}
		};
    paste.addActionListener(pasteListener);
  }


  /**
   * 
   * @description 
   */
  public synchronized void copy(){
    clipboardContent.clear();
    Node currentNode=null;
		//orders the nodes in the list and clones them
		for(Iterator it=new LinkedList(selectedNodes).iterator(); it.hasNext();){
      try{
        currentNode=(Node)it.next();
      }catch (Exception ex){currentNode=null;}
			if(currentNode!=null){
			  clipboardContent.add(currentNode.cloneNode(true));
			}
		}
	}
	
  /**
   * 
   * @description 
   */
	public void paste(){
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		if(clipboardContent.size()>0 && svgTab!=null){
			final SVGSelection selection=getStudio().getSvgSelection();
			Element parentElement=null;

			if(selection!=null){
			  parentElement=selection.getCurrentParentElement(svgTab);
			}

			if(parentElement!=null){
        
        //the defs node
        final Element defs=getStudio().getSvgToolkit().getDefsElement(svgTab.getScrollPane().getSvgCanvas());
        final SVGDocument doc=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
      
        //the list of the pasted nodes
        final LinkedList pastedNodes=new LinkedList();
        
				//clones the nodes
				final LinkedList pasteList=new LinkedList(clipboardContent);
				Node current=null;
				String sid="";
				for(Iterator it=pasteList.iterator(); it.hasNext();){
					current=(Node)it.next();
					if(current!=null && current instanceof Element){
						sid=getStudio().getSvgToolkit().getId(svgTab.getScrollPane().getSvgCanvas(), "");
						((Element)current).setAttribute("id", sid);
					}
				}
				
				final Element finalParentElement=parentElement;
				
				RunnableQueue queue=getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();

				//the runnable
				Runnable runnable=new Runnable(){
					public void run(){
            //the list of the resources used by the pasted nodes
            LinkedList resNodes=new LinkedList();
            //the list of the resources imported into the current document
            final LinkedList usedResourceNodes=new LinkedList();
						Iterator it, it2;
						Element current=null;
						LinkedList resourceNodes=null;
			      Element res=null;

						for(it=pasteList.iterator(); it.hasNext();){
							try{
								current=(Element)it.next();
							}catch (Exception ex){current=null;}
							if(current!=null){
                //getting all the resource nodes used by this node
                resourceNodes=getStudio().getSvgToolkit().getResourcesUsedByNode((Element)current, true);
                
                //if the copied node does not belong to this svg document
                if(! current.getOwnerDocument().equals(doc)){
                  //for each resource node, check if it is contained in the list of the resource used by the copied nodes
                  for(it2=resourceNodes.iterator(); it2.hasNext();){
                    try{
                      res=(Element)it2.next();
                    }catch (Exception ex){res=null;}
                    if(res!=null && ! resNodes.contains(res)){
                      resNodes.add(res);
                    }
                  }
                }
							    
								current=(Element)doc.importNode(current, true);
								pastedNodes.add(current);
							}
						}
						
						String resId="", newId="", style="";
						
						//adds the resource nodes to the defs element
						for(it=resNodes.iterator(); it.hasNext();){
						    
						    try{
						        res=(Element)doc.importNode((Element)it.next(), true);
						        resId=res.getAttribute("id");
						    }catch (Exception ex){res=null; resId=null;}
						    
						    if(res!=null && resId!=null){

						        if(! getStudio().getSvgToolkit().checkId(svgTab, resId)){
						            
						            //creating the new id
						            newId=getStudio().getSvgToolkit().getId(svgTab.getScrollPane().getSvgCanvas(), resId);
						            
						            //modifying the id of the resource
						            res.setAttribute("id", newId);
						            
						            //for each pasted node, modifies the name of the resource they use
						            for(it2=pastedNodes.iterator(); it2.hasNext();){
						                
						                try{
						                    current=(Element)it2.next();
						                }catch (Exception ex){current=null;}
						                
						                if(current!=null){
						                    
						                    style=current.getAttribute("style");
						                    
						                    if(style!=null && style.indexOf("#".concat(resId))!=-1){
						                        
						                        style=style.replaceAll("#".concat(resId), "#".concat(newId));
						                        current.setAttribute("style", style);
						                    }
						                }
						            }
						        }
						        
						        //adding the resource node to the defs element and the list of the resource nodes
						        defs.appendChild(res);
						        usedResourceNodes.add(res);
						    }
						}
						
						//appends the nodes to their parent and registers those which use resources
						for(it=pastedNodes.iterator(); it.hasNext();){
						    
						    try{
						      current=(Element)it.next();
						    }catch (Exception ex){current=null;}
						    
						    if(current!=null){
                  finalParentElement.appendChild(current);
						      getStudio().getSvgToolkit().registerUsedResource(svgTab, current);
						    }
						}
						
						//adds the undo/redo action list
						if(getStudio().getSvgUndoRedo()!=null){
							SVGUndoRedoAction action=new SVGUndoRedoAction(undoredopaste){
								public void undo(){
									Iterator it;
									Node current=null;
									
									//removes the added children from the parent node
									for(it=pastedNodes.iterator(); it.hasNext();){
										try{
                      current=(Node)it.next();
                    }catch (Exception e){current=null;}

										if(current!=null){
										  finalParentElement.removeChild(current);
											//unregister the current node to the used resources map if it uses a resource
											getStudio().getSvgToolkit().unregisterAllUsedResource(svgTab, current);
										}
									}
									
									//removes the added resources
									for(it=usedResourceNodes.iterator(); it.hasNext();){
										try{
                      current=(Node)it.next();
                    }catch (Exception e){current=null;}
										if(current!=null){
										  defs.removeChild(current);
										}
									}
								}

								public void redo(){
									Iterator it;
									Node current=null;
									//appends the resources
									for(it=usedResourceNodes.iterator(); it.hasNext();){
										try{
                      current=(Node)it.next();
                    }catch (Exception e){current=null;}
										
										if(current!=null){
										    defs.appendChild(current);
										}
									}
									
									//appends the children
									for(it=pastedNodes.iterator(); it.hasNext();){
										try{
                      current=(Node)it.next();
                    }catch (Exception e){current=null;}
										if(current!=null){
										    finalParentElement.appendChild(current);
											//registers the current node to the used resources map if it uses a resource
											getStudio().getSvgToolkit().registerUsedResource(svgTab, current);
										}
									}
								}
							};
						
							if(selection!=null){
							  selection.deselectAll(svgTab, false, true);
								selection.addUndoRedoAction(svgTab, action);
								
								Node node=null;
								
								for(it=pastedNodes.iterator(); it.hasNext();){
                  try{
                    node=(Node)it.next();
                  }catch (Exception ex){node=null;}
                
                  if(node!=null && node instanceof Element){
                    selection.handleNodeSelection(svgTab, (Element)node);	
                  }			
								}
								selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction(undoredopaste){});
								selection.refreshSelection(svgTab);
							}else{
								//creates the undo/redo list and adds the action to it					
								SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredopaste);
								actionlist.add(action);
								getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
							}			
						}
					}
				};
				
				//appends the nodes to the root element with the correct id
				if(queue.getThread()!=Thread.currentThread()){
					queue.invokeLater(runnable);
				}else{
				  runnable.run();
				}
			}
		}
	}
	
	/**
	 * cuts the current selection
	 */
	public void cut(){
	    
		//copies the nodes
		copy();
		
		//removes the nodes
		delete(false);
	}
	
	/**
	 * deletes the selected nodes
	 * @param isDelete true if it is used in the delete action
	 * 								false if it is used in the cut action
	 */
	public void delete(boolean isDelete){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(svgTab!=null && selectedNodes.size()>0){
			
			final LinkedList deletedlist=new LinkedList(selectedNodes);
			
			SVGSelection selection=getStudio().getSvgSelection();
			Node parent=null;
			
			if(selection!=null){
				parent=selection.getCurrentParentElement(svgTab);
			}
			
			SVGDocument doc=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			final Node fparent=parent;
			if(fparent!=null){

				Node current=null;
				RunnableQueue queue=getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
		
				Runnable runnable=new Runnable(){
					public void run(){
						Node current=null;
						for(Iterator it=deletedlist.iterator(); it.hasNext();){
							try{
								current=(Node)it.next();
							}catch (Exception e){current=null;}
							
							if(current!=null && current.getParentNode()!=null && current.getParentNode().equals(fparent)){
							  fparent.removeChild(current);
								//register the current node to the used resources map if it uses a resource
								getStudio().getSvgToolkit().registerUsedResource(svgTab, current);
							}
						}
					}
				};
				
				if(queue.getThread()!=Thread.currentThread()){
				    
					queue.invokeLater(runnable);
					
				}else{
					
					runnable.run();
				}

				//adds the undo/redo action list			
				if(getStudio().getSvgUndoRedo()!=null){
			
					String undoredo="";
					if(isDelete){
					  undoredo=undoredodelete;
					}else{
					  undoredo=undoredocut;
					}

					SVGUndoRedoAction action=new SVGUndoRedoAction(undoredo){
						public void undo(){
							//appends the children to the root element						
							Node current=null;
							for(Iterator it=deletedlist.iterator(); it.hasNext();){
								try{
                  current=(Node)it.next();
                }catch (Exception e){current=null;}
								if(current!=null){
								    fparent.appendChild(current);
									//unregisters the current node to the used resources map if it uses a resource
									getStudio().getSvgToolkit().unregisterAllUsedResource(svgTab, current);
								}
							}
						}

						public void redo(){
							//removes the children from the root element						
							Node current=null;
							for(Iterator it=deletedlist.iterator(); it.hasNext();){
							    
								try{current=(Node)it.next();}catch (Exception e){current=null;}
								
								if(current!=null && current.getParentNode()!=null && current.getParentNode().equals(fparent)){
								    
								    fparent.removeChild(current);
									
									//register the current node to the used resources map if it uses a resource
									getStudio().getSvgToolkit().registerUsedResource(svgTab, current);
								}
							}
						}
					};
			
					if(selection!=null){
					  selection.deselectAll(svgTab, false, true);
						selection.addUndoRedoAction(svgTab, action);
						selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction(undoredo){});
						selection.refreshSelection(svgTab);
					}else{
						//creates the undo/redo list and adds the action to it					
						SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredo);
						actionlist.add(action);
						getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
					}
				}
			}
		}
	}

  /**
   * 
   * @description 
   * @return 
   */
  public Collection getPopupMenuItems(){
		LinkedList popupMenuItems=new LinkedList();
		
		//creating the copy popup item
		SVGPopupMenuItem item=new SVGPopupMenuItem(getStudio(), idcopy, labelcopy, copy.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
					menuItem.setEnabled(true);
					menuItem.addActionListener(copyListener);
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		popupMenuItems.add(item);
		
		//creating the paste popup item
		item=new SVGPopupMenuItem(getStudio(), idpaste, labelpaste, paste.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(clipboardContent.size()>0){
					menuItem.setEnabled(true);
					menuItem.addActionListener(pasteListener);
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		popupMenuItems.add(item);

		//creating the cut popup item
		item=new SVGPopupMenuItem(getStudio(), idcut, labelcut, cut.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
					menuItem.setEnabled(true);
					menuItem.addActionListener(cutListener);
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		popupMenuItems.add(item);
		
		//creating the delete popup item
		item=new SVGPopupMenuItem(getStudio(), iddelete, labeldelete, delete.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
          menuItem.setEnabled(true);
          menuItem.addActionListener(deleteListener);
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		popupMenuItems.add(item);

		return popupMenuItems;
	}
  /**
   * 
   * @description 
   * @return 
   */
	public String getName(){
		return idclipboard;
	}
	
  /**
   * 
   * @description 
   */
	public void cancelActions(){

	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
}