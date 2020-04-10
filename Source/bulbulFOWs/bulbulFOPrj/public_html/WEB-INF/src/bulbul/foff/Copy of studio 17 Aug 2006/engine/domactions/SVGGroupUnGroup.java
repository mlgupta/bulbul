package bulbul.foff.studio.engine.domactions;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 13-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGGroupUnGroup implements SVGClassObject  {
  
  final private String idgroupungroup="GroupUnGroup";
  final private String idgroupug="Group";
  final private String idgungroup="UnGroup";
  
  private String labelgroupug="Group";
  private String labelgungroup="UnGroup";
  
  private String undoredogroupug="Group";
  private String undoredogungroup="UnGroup";
  
  private JButton group;
  private JButton ungroup;
  
  private LinkedList selectednodes=new LinkedList();
  
  private ActionListener groupListener=null;
  private ActionListener ungroupListener=null;
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGGroupUnGroup(Studio studio){
    
    this.studio=studio;
    
    group=getStudio().getMainToolBar().getMtbGroup();
    ungroup=getStudio().getMainToolBar().getMtbUnGroup();
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			
			/**
			 * a listener on the selection changes
			 */
			private ActionListener selectionListener=null;
			
			/**
			 * the current selection module
			 */
			private SVGSelection selection=null;

			public void actionPerformed(ActionEvent e) {
				
				//clears the list of the selected items
				selectednodes.clear();
				
				//disables the menuitems
				group.setEnabled(false);
				ungroup.setEnabled(false);
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				//if a selection listener is already registered on a selection module, it is removed	
				if(selection!=null && selectionListener!=null){
					selection.removeSelectionListener(selectionListener);
				}

				//gets the current selection module	
				if(svgTab!=null){
					selection=getStudio().getSvgSelection();
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
				group.setEnabled(false);
				ungroup.setEnabled(false);
				
				LinkedList list=null;
				
				//gets the currently selected nodes list 
				if(selection!=null){
					list=selection.getCurrentSelection(getStudio().getSvgTabManager().getCurrentSVGTab());
				}
				
				selectednodes.clear();
				
				//refresh the selected nodes list
				if(list!=null){
				  selectednodes.addAll(list);
				}
				
				if(selectednodes.size()>0){
          group.setEnabled(true);
          //checks if all the selected nodes are g elements to ungroup them all
          LinkedList snodes=new LinkedList(selectednodes);
					Node current=null;
					boolean canBeEnabled=true;
					
					for(Iterator it=snodes.iterator(); it.hasNext();){
						try{
							current=(Node)it.next();
						}catch (Exception ex){current=null;}
						
						if(current!=null){
							canBeEnabled=canBeEnabled && current.getNodeName().equals("g");
						}							
					}
					
					if(canBeEnabled){
					  ungroup.setEnabled(true);
					}
				}					
			}
		};
    
    getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    //creating and adding the group menu item listener
		groupListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>0){
					group(selectednodes);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
    group.addActionListener(groupListener);
		
		//creating and adding the ungroup menu item listener
		ungroupListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>0){
					ungroup(selectednodes);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
    ungroup.addActionListener(ungroupListener);
    
  }

  
  public Collection getPopupMenuItems(){
		
		LinkedList popupMenuItems=new LinkedList();
		
		//creating the group popup item
		SVGPopupMenuItem item=new SVGPopupMenuItem(getStudio(), idgroupug, labelgroupug, group.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
					menuItem.setEnabled(true);
					menuItem.addActionListener(groupListener);
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		popupMenuItems.add(item);
		
		//creating the ungroup popup item
		item=new SVGPopupMenuItem(getStudio(), idgungroup, labelgungroup, ungroup.getIcon() ){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
				    //checks if all the selected nodes are g elements to ungroup them all
					Node current=null;

					boolean canBeEnabled=true;
					for(Iterator it=nodes.iterator(); it.hasNext();){
						try{current=(Node)it.next();}catch (Exception ex){current=null;}
						if(current!=null){
							canBeEnabled=canBeEnabled && current.getNodeName().equals("g");
						}							
					}
					
					if(canBeEnabled){
						menuItem.setEnabled(true);
						menuItem.addActionListener(ungroupListener);
					}else{
						menuItem.setEnabled(false);
					}
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
   * @param list
   */
  protected void group(LinkedList list){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			
			final SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			
			//the selected nodes
			final LinkedList finalSelectedNodes=new LinkedList(list);
			
			//getting the parent node
			Node parent=null;
			
			try{
        parent=((Node)finalSelectedNodes.getFirst()).getParentNode();
      }catch (Exception ex){parent=null;}
			
			final Node finalParent=parent;
			
			if(document!=null && finalParent!=null){

				//the list of the nodes to be grouped
				final LinkedList toBeGrouped=new LinkedList();
				
				//orders the selected nodes
				Node current=null;

				for(current=finalParent.getFirstChild(); current!=null; current=current.getNextSibling()){
          if(finalSelectedNodes.contains(current)){
            toBeGrouped.add(current);
          }
				}

				//creates the g element
				final Element gElement=document.createElementNS(document.getDocumentElement().getNamespaceURI(),"g");
				String id=getStudio().getSvgToolkit().getId(svgTab.getScrollPane().getSvgCanvas(), "");
				gElement.setAttributeNS(null,"id",id);
				
				//appends the selected nodes as children of the g element
				Runnable runnable=new Runnable(){
				    
					public void run(){
						Node current=null;
						for(Iterator it=toBeGrouped.iterator(); it.hasNext();){
							try{
                current=(Node)it.next();
              }catch (Exception ex){current=null;}
							
							if(current!=null){
								try{
									finalParent.removeChild(current);
								}catch(Exception ex){}
								gElement.appendChild(current);					
							}
						}
						
						//appends the g element to the root element
						finalParent.appendChild(gElement);
						svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
					}
				};
				
				RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
					
				if(queue.getThread()!=Thread.currentThread()){
					queue.invokeLater(runnable);
				}else{
					runnable.run();
				}
			
				//creates the undo/redo action and insert it into the undo/redo stack
				if(getStudio().getSvgUndoRedo()!=null){
					SVGUndoRedoAction action=new SVGUndoRedoAction(undoredogroupug){
						public void undo(){
							//removes the g element and appends the children of the g element to the root element
							Node current=null;
							for(Iterator it=toBeGrouped.iterator(); it.hasNext();){
								try{
                  current=(Node)it.next();
                }catch (Exception ex){current=null;}
								
								if(current!=null){
									try{
                    gElement.removeChild(current);
                  }catch(Exception ex){}		
									finalParent.appendChild(current);		
								}
							}
							finalParent.removeChild(gElement);
						}

						public void redo(){
							//appends the g element to the root element
						  finalParent.appendChild(gElement);
							Node current=null;
							
							for(Iterator it=toBeGrouped.iterator(); it.hasNext();){
								try{
                  current=(Node)it.next();
                }catch (Exception ex){current=null;}
								
								//removes the selected nodes from the root element and adds them to the g element
								if(current!=null){
									try{
                    finalParent.removeChild(current);
                  }catch(Exception ex){}
									gElement.appendChild(current);					
								}
							}
							svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
						}
					};
			
					SVGSelection selection=getStudio().getSvgSelection();
			
					if(selection!=null){
						//manages the selections and the undo/redo action list
						selection.deselectAll(svgTab, false, false);
						selection.addUndoRedoAction(svgTab, action);
						selection.handleNodeSelection(svgTab, gElement);
						selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction(undoredogroupug){});
						selection.refreshSelection(svgTab);
					}else{
						SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredogroupug);
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
   * @param list
   */
	protected void ungroup(LinkedList list){
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		if(list!=null && list.size()>0 && svgTab!=null){
			final SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
		    //the selected nodes
			final LinkedList snodes=new LinkedList(list);
			
			//getting the parent node
			Node parent=null;
			
			try{
        parent=((Node)snodes.getFirst()).getParentNode();
      }catch (Exception ex){parent=null;}
			
			final Node finalParent=parent;
			
			if(document!=null && finalParent!=null){

				//the list of the g nodes
				final LinkedList groupNodes=new LinkedList();
				
				//orders the selected nodes
				Node current=null;

				for(current=finalParent.getFirstChild(); current!=null; current=current.getNextSibling()){
          if(snodes.contains(current)){
            groupNodes.add(current);
          }
				}
				
				//the map associating a group node to the list its children before the ungroup
				final LinkedHashMap gOldNodes=new LinkedHashMap();
				//the map assocating a g element to the list of its linked nodes
				final LinkedHashMap gNewNodes=new LinkedHashMap();
				//the action
				SVGUndoRedoAction urAction=null;

				//create the undo/redo action
				if(getStudio().getSvgUndoRedo()!=null){
          urAction=new SVGUndoRedoAction(undoredogungroup){
						public void undo(){
              Iterator it=null;
              Iterator it2=null;
              Node gNode=null;
              Node child=null;
              LinkedList children=null;
              LinkedList newChildren=null;

              for(it=gOldNodes.keySet().iterator(); it.hasNext();){
                try{
                  gNode=(Node)it.next();
                  children=(LinkedList)gOldNodes.get(gNode);
                  newChildren=(LinkedList)gNewNodes.get(gNode);
                }catch (Exception ex){
                  gNode=null; 
                  children=null; 
                  newChildren=null;
                }
                
                if(gNode!=null && children!=null && newChildren!=null){
                  //removes all the children from the g element
                  while(gNode.hasChildNodes()){
                    gNode.removeChild(gNode.getFirstChild());
                  }
                  
                  //appends all the children to the g element
                  for(it2=newChildren.iterator(); it2.hasNext();){
                    try{
                      child=(Node)it2.next();
                    }catch (Exception ex){child=null;}
                    
                    if(child!=null){
                      finalParent.removeChild(child);
                    }
                  }
                  
                  //appends all the children to the g element
                  for(it2=children.iterator(); it2.hasNext();){
                    try{
                      child=(Node)it2.next();
                    }catch (Exception ex){child=null;}
                    
                    if(child!=null){
                      gNode.appendChild(child);
                    }
                  }
                  
                  finalParent.appendChild(gNode);
                }
              }
						}
						
						/**
						* used to call all the actions that have to be done to redo an action
						*/
						public void redo(){

              Iterator it=null;
              Iterator it2=null;
              Node gNode=null;
              Node child=null;
              LinkedList children=null;

              for(it=gNewNodes.keySet().iterator(); it.hasNext();){
                try{
                  gNode=(Node)it.next();
                  children=(LinkedList)gNewNodes.get(gNode);
                }catch (Exception ex){
                  gNode=null; 
                  children=null;
                }
                
                if(gNode!=null && children!=null){
                  //removes all the children from the g element
                  while(gNode.hasChildNodes()){
                    gNode.removeChild(gNode.getFirstChild());
                  }
                  
                  //appends all the children to the root element
                  for(it2=children.iterator(); it2.hasNext();){
                    try{
                      child=(Node)it2.next();
                    }catch (Exception ex){child=null;}
                    
                    if(child!=null){
                      finalParent.appendChild(child);
                    }
                  }
                  finalParent.removeChild(gNode);
                }
              }
						}
					};
				}
				
				final SVGUndoRedoAction action=urAction;
				
				//ungroups the g elements
				Runnable runnable=new Runnable(){
					public void run(){
						Iterator it=null;
            Iterator it2=null;
						Node gNode=null;
            Node current=null;
						//the list of the children of the g element
						LinkedList gchildren=null;
						//the list of the cloned children of the g element
						LinkedList clonedChildren=null;

						for(it=groupNodes.iterator(); it.hasNext();){
              try{
                gNode=(Node)it.next();
              }catch (Exception ex){gNode=null;}
              
              if(gNode!=null && gNode.getNodeName().equals("g")){
                gchildren=new LinkedList();
                clonedChildren=new LinkedList();
                    
                for(current=gNode.getFirstChild(); current!=null; current=current.getNextSibling()){
                  gchildren.add(current);
                  clonedChildren.add(current.cloneNode(true));
                }
                
                gOldNodes.put(gNode, clonedChildren);
                gNewNodes.put(gNode, gchildren);
  
                it2=gchildren.iterator();
                
                while(it2.hasNext()){
                  try{
                    current=(Node)it2.next();
                  }catch (Exception ex){current=null;}
                  
                  if(current!=null){
                    try{
                      gNode.removeChild(current);
                      finalParent.appendChild(current);
                    }catch(Exception ex){}	
                  }
                }
              }
              finalParent.removeChild(gNode);
						}
						
						//inserts the undo/redo action into the undo/redo stack
						if(getStudio().getSvgUndoRedo()!=null && action!=null){
							SVGSelection selection=getStudio().getSvgSelection();

							if(selection!=null){
								//manages the selections and the undo/redo action list
								selection.deselectAll(svgTab, false, false);
								selection.addUndoRedoAction(svgTab, action);
								
								//selects the nodes that were before children of the g elements
							    Element child=null;
							    LinkedList children=null;

							    for(it=gNewNodes.keySet().iterator(); it.hasNext();){
                    try{
                      gNode=(Node)it.next();
                      children=(LinkedList)gNewNodes.get(gNode);
                    }catch (Exception ex){gNode=null; children=null;}
                    
                    if(gNode!=null && children!=null){
                      //selects the nodes
                      for(it2=children.iterator(); it2.hasNext();){
                        try{
                            child=(Element)it2.next();
                        }catch (Exception ex){child=null;}
                        
                        if(child!=null){
                            selection.handleNodeSelection(svgTab, child);
                        }
                      }
							      }
							    }
								selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction(undoredogungroup){});
								selection.refreshSelection(svgTab);
							}else{
								SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredogungroup);
								actionlist.add(action);
								getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
							}
						}
					}
				};

				RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
				if(queue.getThread()!=Thread.currentThread()){
					queue.invokeLater(runnable);
				}else{
					runnable.run();
				}
			}
		}
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
  public String getName(){
		return idgroupungroup;
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