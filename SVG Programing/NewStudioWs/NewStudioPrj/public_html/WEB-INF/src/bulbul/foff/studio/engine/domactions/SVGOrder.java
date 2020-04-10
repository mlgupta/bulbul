package bulbul.foff.studio.engine.domactions;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 17-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGOrder implements SVGClassObject  {
  
  private final int TO_TOP=0;
	private final int TO_UP=1;
	private final int TO_DOWN=2;
	private final int TO_BOTTOM=3;
  
  final private String idorder="Order", idordertop="OrderTop", idorderup="OrderUp", idorderdown="OrderDown", idorderbottom="OrderBottom";
  
  private String labelorder="";
  private String labelordertop="";
  private String labelorderup="";
  private String labelorderdown="";
  private String labelorderbottom="";
  
  private String undoredoordertop="";
  private String undoredoorderup="";
  private String undoredoorderdown="";
  private String undoredoorderbottom="";
  
  private JButton toTop;
  private JButton toUp;
  private JButton toDown;
  private JButton toBottom;
  
  private ActionListener toTopListener;
  private ActionListener toUpListener;
  private ActionListener toDownListener;
  private ActionListener toBottomListener;
  
  private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  
  /**
   * 
   * @description 
   */
  public SVGOrder(SVGApplet studio) {
    this.studio=studio;
    
    toTop=getStudio().getMainToolBar().getMtbOrder2Top();
    toUp=getStudio().getMainToolBar().getMtbOrder2Up();
    toDown=getStudio().getMainToolBar().getMtbOrder2Down();
    toBottom=getStudio().getMainToolBar().getMtbOrder2Bottom();
    
    //a listener that listens to the changes of the SVGTab
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
				toTop.setEnabled(false);
				toUp.setEnabled(false);
				toDown.setEnabled(false);
				toBottom.setEnabled(false);
				
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
				//disables the toolitems							
				toTop.setEnabled(false);
				toUp.setEnabled(false);
				toDown.setEnabled(false);
				toBottom.setEnabled(false);
				
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
					toTop.setEnabled(true);
					toUp.setEnabled(true);
					toDown.setEnabled(true);
					toBottom.setEnabled(true);
				}								
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    //adds the listeners
		toTopListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					order(selectednodes, TO_TOP);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		toTop.addActionListener(toTopListener);
		
		toUpListener=new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>0){
					order(selectednodes, TO_UP);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		toUp.addActionListener(toUpListener);
		
		toDownListener=new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>0){
					order(selectednodes, TO_DOWN);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		toDown.addActionListener(toDownListener);
		
		toBottomListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					order(selectednodes, TO_BOTTOM);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		toBottom.addActionListener(toBottomListener);		
    
  }
  
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idorder, labelorder, "");
		
		popupItems.add(subMenu);
		
		//creating the top popup item
		SVGPopupItem toTopItem=new SVGPopupItem(getSVGEditor(), idordertop, labelordertop, "OrderTop"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(toTopListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the up popup item
		SVGPopupItem toUpItem=new SVGPopupItem(getSVGEditor(), idorderup, labelorderup, "OrderUp"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(toUpListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the down popup item
		SVGPopupItem toDownItem=new SVGPopupItem(getSVGEditor(), idorderdown, labelorderdown, "OrderDown"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(toDownListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the bottom popup item
		SVGPopupItem toBottomItem=new SVGPopupItem(getSVGEditor(), idorderbottom, labelorderbottom, "OrderBottom"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(toBottomListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(toTopItem);
		subMenu.addPopupItem(toUpItem);
		subMenu.addPopupItem(toDownItem);
		subMenu.addPopupItem(toBottomItem);
		
		return popupItems;
	}
  */
  
  /**
   * 
   * @description 
   * @param type
   * @param list
   */
  protected void order(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;
			final SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			
			//getting the finalParent node
			Node parent=null;

			try{
        parent=((Node)snodes.getFirst()).getParentNode();
      }catch (Exception ex){parent=null;}
			
			final Node finalParent=parent;
			
			if(document!=null && finalParent!=null){

				final NodeList children=finalParent.getChildNodes();
				final LinkedList childrenList=new LinkedList();
				final LinkedList orderedSelectedNodes=new LinkedList();

				Node current=null;
				
				//orders the selected nodes according to their place in the dom
				for(int i=0;i<children.getLength();i++){
					current=children.item(i);
					if(current!=null){
						childrenList.add(current);
						if(snodes.contains(current)){
						  orderedSelectedNodes.add(current);
						}
					}
				}
				
				//put the selected nodes in the proper place
				Runnable runnable=new Runnable(){
					public void run(){
						Node current=null;
						Iterator it;

						if(finalType==TO_BOTTOM){
							Node firstChild=null;
							for(it=orderedSelectedNodes.iterator(); it.hasNext();){
								try{
									current=(Node)it.next();
								}catch (Exception ex){current=null;}
                
								if(current!=null){
									if(firstChild==null){
                    firstChild=finalParent.getFirstChild();
                    if(	firstChild!=null && firstChild.getNodeName().equals("defs")){
                      firstChild=firstChild.getNextSibling();
                    }
									}
								
                	if(!current.equals(firstChild)){
									  finalParent.insertBefore(current, firstChild);
										firstChild=current.getNextSibling();
									}
								}
							}
						}else if(finalType==TO_DOWN){
							for(it=orderedSelectedNodes.iterator(); it.hasNext();){
								try{
									current=(Node)it.next();
								}catch (Exception ex){current=null;}
			
								if(current!=null && current.getPreviousSibling()!=null && 
								        ! current.getPreviousSibling().getNodeName().equals("defs")){
								  finalParent.insertBefore(current, current.getPreviousSibling());
								}
							}
						}else if(finalType==TO_UP){
							for(it=orderedSelectedNodes.iterator(); it.hasNext();){
								try{
									current=(Node)it.next();
								}catch (Exception ex){current=null;}
			
								if(current!=null && current.getNextSibling()!=null){
								  finalParent.insertBefore(current.getNextSibling(), current);
								}
							}
						}else if(finalType==TO_TOP){
							Node lastChild=null;
							for(it=orderedSelectedNodes.iterator(); it.hasNext();){
								try{
									current=(Node)it.next();
								}catch (Exception ex){current=null;}
			
								if(current!=null){
									if(lastChild==null){
									  lastChild=children.item(children.getLength()-1);
									}
									if(!current.equals(lastChild)){
                    finalParent.insertBefore(current, lastChild);
                    finalParent.insertBefore(lastChild, current);
										lastChild=current;
									}
								}
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
				
				//sets the label for the undo/redo action
				String undoredo="";
				
				if(type==TO_TOP){
					undoredo=undoredoordertop;
				}else if(type==TO_UP){
					undoredo=undoredoorderup;
				}else if(type==TO_DOWN){
					undoredo=undoredoorderdown;
				}else if(type==TO_BOTTOM){
					undoredo=undoredoorderbottom;
				}
				
				//creates the undo/redo action and insert it into the undo/redo stack
				if(getStudio().getSvgUndoRedo()!=null){

					SVGUndoRedoAction action=new SVGUndoRedoAction(undoredo){
						public void undo(){
							NodeList newChildren=finalParent.getChildNodes();
							Node current=null;

							for(int i=0;i<newChildren.getLength();i++){
								try{
                  current=newChildren.item(i);
                  if(current!=null){
                    finalParent.removeChild(current);
                  }
								}catch (Exception ex){current=null;}
							}

							for(int i=0;i<childrenList.size();i++){
								try{
                  current=(Node)childrenList.get(i);
								}catch (Exception ex){current=null;}

								if(current!=null){
								    finalParent.appendChild(current);
								}
							}
						}

						public void redo(){
						  Iterator it;
							Node current=null;
				
							if(finalType==TO_BOTTOM){
								Node firstChild=null;
								for(it=orderedSelectedNodes.iterator(); it.hasNext();){
									try{
										current=(Node)it.next();
									}catch (Exception ex){current=null;}
					
									if(current!=null){
										if(firstChild==null){
										  firstChild=finalParent.getFirstChild();
										}
										if(!current.equals(firstChild)){
										  finalParent.insertBefore(current, firstChild);
											firstChild=current.getNextSibling();
										}
									}
								}
							}else if(finalType==TO_DOWN){
								for(it=orderedSelectedNodes.iterator(); it.hasNext();){
									try{
										current=(Node)it.next();
									}catch (Exception ex){current=null;}
					
									if(current!=null&& current.getPreviousSibling()!=null){
									  finalParent.insertBefore(current, current.getPreviousSibling());
									}
								}
							}else if(finalType==TO_UP){
							  for(it=orderedSelectedNodes.iterator(); it.hasNext();){
									try{
										current=(Node)it.next();
									}catch (Exception ex){current=null;}
						
									if(current!=null && current.getNextSibling()!=null){
									    finalParent.insertBefore(current.getNextSibling(), current);
									}
								}

							}else if(finalType==TO_TOP){
								Node lastChild=null;
								for(it=orderedSelectedNodes.iterator(); it.hasNext();){
									try{
										current=(Node)it.next();
									}catch (Exception ex){current=null;}
					
									if(current!=null){
										if(lastChild==null){
										  lastChild=children.item(children.getLength()-1);
										}
										
										if(!current.equals(lastChild)){
                      finalParent.insertBefore(current, lastChild);
                      finalParent.insertBefore(lastChild, current);
											lastChild=current;
										}
									}
								}
							}
						}
					};
					
					//gets or creates the undo/redo list and adds the action into it
					SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredo);
					actionlist.add(action);
					getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
				}

			}
		}
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idorder;
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
  public SVGApplet getStudio() {
    return studio;
  }
}