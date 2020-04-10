package bulbul.foff.studio.engine.domactions;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGSpacing implements SVGClassObject {
  
  
  final private String idspacing="Spacing";
  final private String idspacinghorizontal="HorizontalSpacing";
  final private String idspacingvertical="VerticalSpacing";
	
	private String labelspacing="";
  private String labelspacinghorizontal="";
  private String labelspacingvertical="";
	
	private String undoredospacinghorizontal="";
  private String undoredospacingvertical="";
	
	final private int HORIZONTAL_SPACING=0;
	final private int VERTICAL_SPACING=1;

	private JButton spacingh;
  private JButton spacingv;
	
	private ActionListener horizontalSpacingListener;
  private ActionListener verticalSpacingListener;
	
  private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  
  /**
   * 
   * @description 
   */
  public SVGSpacing(SVGApplet studio){
    this.studio=studio;
    
    spacingh=getStudio().getMainToolBar().getMtbSpaceHorizontal();
    spacingv=getStudio().getMainToolBar().getMtbSpaceVertical();  
    
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
				spacingh.setEnabled(false);
				spacingv.setEnabled(false);
				
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
				spacingh.setEnabled(false);
				spacingv.setEnabled(false);
				
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
				
				if(selectednodes.size()>1){
					spacingh.setEnabled(true);
					spacingv.setEnabled(true);		
				}								
			}
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
		
		//adds the listeners
		horizontalSpacingListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>1){
					spacing(selectednodes,HORIZONTAL_SPACING);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		spacingh.addActionListener(horizontalSpacingListener);

		verticalSpacingListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>1){
					spacing(selectednodes,VERTICAL_SPACING);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};

		spacingv.addActionListener(verticalSpacingListener);
    
  }
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idspacing, labelspacing, "");
		
		popupItems.add(subMenu);
		
		//creating the horizontal spacing popup item
		SVGPopupItem horizontalSpacingItem=new SVGPopupItem(getSVGEditor(), idspacinghorizontal, labelspacinghorizontal, "HorizontalSpacing"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(horizontalSpacingListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the vertical spacing popup item
		SVGPopupItem verticalSpacingItem=new SVGPopupItem(getSVGEditor(), idspacingvertical, labelspacingvertical, "VerticalSpacing"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(verticalSpacingListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(horizontalSpacingItem);
		subMenu.addPopupItem(verticalSpacingItem);
		
		return popupItems;
	}
	*/
	
  /**
   * 
   * @description 
   * @param type
   * @param list
   */
	protected void spacing(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			
			RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;

			//the map associating a node to an affine transform
			final Hashtable transformMap=new Hashtable();
			final LinkedList ordered=new LinkedList();
			Node current=null;
      Node orderedCurrent=null;
			Rectangle2D rectangle=null;
			double widthSum=0;
      double heightSum=0;
			
      //orders the nodes given their position
			for(Iterator it=list.iterator(); it.hasNext();){
				try{
					current=(Node)it.next();
				}catch (Exception e){current=null;}
				
				if(current!=null){
					rectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
					
					if(rectangle!=null){
						widthSum+=rectangle.getWidth();
						heightSum+=rectangle.getHeight();
					}

					if(ordered.size()>0){
						for(int i=0;i<ordered.size();i++){
							try{
								orderedCurrent=(Node)ordered.get(i);	
							}catch(Exception ex){orderedCurrent=null;}
							
							if(orderedCurrent!=null){
								if(isGreaterThan(orderedCurrent,current,type)){
									if(i>0){
										ordered.add(i,current);
									}else {
										ordered.addFirst(current);
									} 
									break;
								}else if(i==ordered.size()-1){
									ordered.addLast(current);
									break;
								}
							}
						}
					}else{
						ordered.add(current);
					}	
				}
			}
			
			final Rectangle2D finalRectangle=rectangle;
			final double finalWidthSum=widthSum;
			final double finalHeightSum=heightSum;
			
			Runnable runnable=new Runnable(){
			    
				public void run() {

					double widthTotal=0;
          double heightTotal=0;
          
          double widthSum=finalWidthSum;
          double heightSum=finalHeightSum;
					
          Node current=null;
					Rectangle2D rectangle1=null;
          Rectangle2D rectangle2=null;
          Rectangle2D rectangle=finalRectangle;
					
					if(ordered.size()>1 && ordered.get(0)!=null && ordered.get(ordered.size()-1)!=null){
					    
						try{
							rectangle1=getStudio().getSvgToolkit().getNodeBounds(svgTab, (Node)ordered.get(0));
							rectangle2=getStudio().getSvgToolkit().getNodeBounds(svgTab, (Node)ordered.get(ordered.size()-1));
						}catch (Exception ex){rectangle1=null; rectangle2=null;}
						
						if(rectangle1!=null && rectangle2!=null){
						    
							//computes the space between the first and the last node
							widthTotal=rectangle2.getWidth()+rectangle2.getX()-rectangle1.getX();
							heightTotal=rectangle.getHeight()+rectangle2.getY()-rectangle1.getY();
							
							if(widthTotal>0 && heightTotal>0){
							    
								//computes the space that will be set between each node
								double spacew=(widthTotal-widthSum)/(ordered.size()-1), spaceh=(heightTotal-heightSum)/(ordered.size()-1), e=0, f=0;
								Node lastnode=null;
								Rectangle2D lastRectangle=null;
								SVGTransformMatrix matrix=null;
								AffineTransform affineTransform=null;
								
								//computes the new matrix for each node
								for(Iterator it=ordered.iterator(); it.hasNext();){
								    
									try{
										current=(Node)it.next();
									}catch (Exception ex){current=null;}
									
									if(current!=null){
										rectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
										
										if(lastnode!=null){
										  lastRectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, lastnode);
										}else{
										  lastRectangle=null;
										}
										
										e=0; f=0;

										//computes the translation values
										if(rectangle!=null && lastRectangle!=null){
											if(finalType==HORIZONTAL_SPACING){
												e=(lastRectangle==null?0:-rectangle.getX()+lastRectangle.getX()+lastRectangle.getWidth()+spacew);
											}else if(finalType==VERTICAL_SPACING){
												f=(lastRectangle==null?0:-rectangle.getY()+lastRectangle.getY()+lastRectangle.getHeight()+spaceh);
											}

											affineTransform=AffineTransform.getTranslateInstance(e, f);
											transformMap.put(current, affineTransform);
											
											//sets the transformation matrix
											if(! affineTransform.isIdentity()){
                        //gets, modifies and sets the transform matrix
												matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
												matrix.concatenateTransform(affineTransform);
												getStudio().getSvgToolkit().setTransformMatrix(current, matrix);		
											}
										}
										
										lastnode=current;
									}
								}
								
							    svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
						
								//creates the undo/redo action and insert it into the undo/redo stack
								if(getStudio().getSvgUndoRedo()!=null){
							
									//sets the name of the undo/redo action
									String actionName="";
									
									if(finalType==HORIZONTAL_SPACING){
										actionName=undoredospacinghorizontal;
									}else if(finalType==VERTICAL_SPACING){
										actionName=undoredospacingvertical;
									}
						
									SVGUndoRedoAction action=new SVGUndoRedoAction(actionName){

										public void undo(){
										    
											//sets the nodes transformation matrix
											if(transformMap.size()>0){

												Node current=null;
												SVGTransformMatrix matrix=null;
												AffineTransform affineTransform=null;
												
												for(Iterator it=transformMap.keySet().iterator(); it.hasNext();){
													try{
														current=(Node)it.next();
													}catch (Exception ex){current=null;}
													
													if(current!=null){
													    
														try{
															affineTransform=(AffineTransform)transformMap.get(current);
														}catch (Exception ex){affineTransform=null;}
											
														if(affineTransform!=null && ! affineTransform.isIdentity()){
															//gets, modifies and sets the transform matrix
															matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
															try{matrix.concatenateTransform(affineTransform.createInverse());}catch (Exception ex){}
															getStudio().getSvgToolkit().setTransformMatrix(current, matrix);	
														}
													}
												}
											  svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
											}
										}

										public void redo(){
										    
											//sets the nodes transformation matrix
											if(transformMap.size()>0){

												Node current=null;
												SVGTransformMatrix matrix=null;
												AffineTransform affineTransform=null;
												
												for(Iterator it=transformMap.keySet().iterator(); it.hasNext();){
													try{
														current=(Node)it.next();
													}catch (Exception ex){current=null;}
													
													if(current!=null){
														try{
															affineTransform=(AffineTransform)transformMap.get(current);
														}catch (Exception ex){affineTransform=null;}
											
														if(affineTransform!=null && ! affineTransform.isIdentity()){
															//gets, modifies and sets the transform matrix
															matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
															matrix.concatenateTransform(affineTransform);
															getStudio().getSvgToolkit().setTransformMatrix(current, matrix);	
														}
													}
												}
											  svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
											}
										}
									};
				
									//gets or creates the undo/redo list and adds the action into it
									SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(actionName);
									actionlist.add(action);
									getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
								}
							}
						}
					}
				}
			};
			
			if(queue.getThread()!=Thread.currentThread()){
				queue.invokeLater(runnable);
			}else{
				runnable.run();
			}	
		}
	}
	

  /**
   * 
   * @description 
   * @return 
   * @param type
   * @param node2
   * @param node1
   */
  protected boolean isGreaterThan(Node node1, Node node2, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();		
		Rectangle2D rectangle1=getStudio().getSvgToolkit().getNodeBounds(svgTab, node1);
    Rectangle2D rectangle2=getStudio().getSvgToolkit().getNodeBounds(svgTab, node2);
		
		if(rectangle1!=null && rectangle2!=null){
			if(type==HORIZONTAL_SPACING && rectangle1.getX()>=rectangle2.getX()){
				return true;
			}else if(type==VERTICAL_SPACING && rectangle1.getY()>=rectangle2.getY()){
			  return true;
			}
		}
		return false;
	}

  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idspacing;
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