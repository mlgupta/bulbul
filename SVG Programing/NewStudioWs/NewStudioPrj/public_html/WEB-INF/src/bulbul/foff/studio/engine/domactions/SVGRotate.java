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
import java.awt.geom.Point2D;
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
public class SVGRotate implements SVGClassObject   {
  
  final private String idrotate="Rotate";
  final private String idrotate90="Rotate90";
  final private String idrotatem90="Rotatem90";
  final private String idrotate180="Rotate180";
	
	private String labelrotate="";
  private String labelrotate90="";
  private String labelrotate180="";
  private String labelrotate270="";
	
	private String undoredorotate="";
	
	final private int ROTATE_90=0;
	final private int ROTATE_180=1;
  final private int ROTATE_270=2;

	private JButton rotate90;
  private JButton rotate180;
  private JButton rotate270;
	
	private ActionListener rotate90Listener;
  private ActionListener rotate180Listener;
  private ActionListener rotate270Listener;

  private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  
  /**
   * 
   * @description 
   */
  public SVGRotate(SVGApplet studio) {
    this.studio=studio;
    
    rotate90=getStudio().getMainToolBar().getMtbRotate90();
    rotate180=getStudio().getMainToolBar().getMtbRotate180();
    rotate270=getStudio().getMainToolBar().getMtbRotate270();
    
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
				rotate90.setEnabled(false);
				rotate180.setEnabled(false);
        rotate270.setEnabled(false);

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
				rotate90.setEnabled(false);
				rotate180.setEnabled(false);
        rotate270.setEnabled(false);
				
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
					rotate90.setEnabled(true);
					rotate180.setEnabled(true);
          rotate270.setEnabled(true);
				}								
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    //adds the listeners
		rotate90Listener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>0){
					rotate(selectednodes,ROTATE_90);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		rotate90.addActionListener(rotate90Listener);

		rotate180Listener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					rotate(selectednodes,ROTATE_180);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		rotate180.addActionListener(rotate180Listener);

    rotate270Listener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>0){
					rotate(selectednodes,ROTATE_270);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		rotate270.addActionListener(rotate270Listener);    

  }
  
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idrotate, labelrotate, "");
		
		popupItems.add(subMenu);
		
		//creating the rotate90 popup item
		SVGPopupItem rotate90Item=new SVGPopupItem(getSVGEditor(), idrotate90, labelrotate90, "Rotatem90"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(rotate90Listener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the rotatem90 popup item
		SVGPopupItem rotatem90Item=new SVGPopupItem(getSVGEditor(), idrotatem90, labelrotatem90, "Rotatem90"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(rotatem90Listener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the rotatem180 popup item
		SVGPopupItem rotatem180Item=new SVGPopupItem(getSVGEditor(), idrotate180, labelrotate180, "Rotate180"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(rotate180Listener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(rotate90Item);
		subMenu.addPopupItem(rotatem90Item);
		subMenu.addPopupItem(rotatem180Item);
		
		return popupItems;
	}
  */
  protected void rotate(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;
			RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			
			//the map associating a node to its affine transform
			final Hashtable transformMap=new Hashtable();
			
			Runnable runnable=new Runnable(){
				public void run(){
					Node current=null;
					SVGTransformMatrix matrix=null;
					AffineTransform affineTransform=null;
					Rectangle2D rect=null;
					double cx=0;
          double cy=0;
				
					//for each selected node
					for(Iterator it=snodes.iterator(); it.hasNext();){
					    
						try{
							current=(Node)it.next();
						}catch(Exception ex){current=null;}
						
						if(current!=null){
						
							rect=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
							
							if(rect!=null){
							    
								Point2D.Double centerpoint=new Point2D.Double(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
								affineTransform=AffineTransform.getTranslateInstance(-centerpoint.x, -centerpoint.y);
								
								if(finalType==ROTATE_90){
								    affineTransform.preConcatenate(AffineTransform.getRotateInstance(Math.PI/2));
								}else if(finalType==ROTATE_180){
								    affineTransform.preConcatenate(AffineTransform.getRotateInstance(Math.PI));
								}else if(finalType==ROTATE_270){
								    affineTransform.preConcatenate(AffineTransform.getRotateInstance(-Math.PI/2));
								}
								
								affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerpoint.x, centerpoint.y));

								transformMap.put(current, affineTransform);

								//gets, modifies and sets the transform matrix
								matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
								matrix.concatenateTransform(affineTransform);
								
								if(matrix.isMatrixCorrect()){
									getStudio().getSvgToolkit().setTransformMatrix(current, matrix);	

									//creates the undo/redo action and insert it into the undo/redo stack
									if(getStudio().getSvgUndoRedo()!=null){
										SVGUndoRedoAction action=new SVGUndoRedoAction(undoredorotate){

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
										SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredorotate);
										actionlist.add(action);
										getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
									}
								}
							}
						}
					}
				  svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
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
   */
	public String getName(){
		return idrotate;
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