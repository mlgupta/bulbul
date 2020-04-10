package bulbul.foff.studio.engine.domactions;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGPopupMenuItem;
import bulbul.foff.studio.engine.ui.SVGPopupSubMenu;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 17-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGFlip  implements SVGClassObject {
  
  final private String idflip="Flip";
  final private String idfliphorizontal="HorizontalFlip";
  final private String idflipVertical="VerticalFlip";
	
	private String labelflip="Flip";
  private String labelfliphorizontal="Horizontal";
  private String labelflipvertical="Vertical";
	
	private String undoredofliphorizontal="Horizontal Flip";
  private String undoredoflipvertical="Vertical Flip";
  
 	private ActionListener horizontalFlipListener;
  private ActionListener verticalFlipListener;
	
	final private int HORIZONTAL_FLIP=0;
	final private int VERTICAL_FLIP=1;

	private JButton fliph;
  private JButton flipv;
	
  private LinkedList selectednodes=new LinkedList();
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGFlip(Studio studio) {
    this.studio=studio;
    
    fliph=getStudio().getMainToolBar().getMtbFlipHorizontal() ;
    flipv=getStudio().getMainToolBar().getMtbFlipVertical() ;
    
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
				fliph.setEnabled(false);
				flipv.setEnabled(false);
				
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
				fliph.setEnabled(false);
				flipv.setEnabled(false);
				
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
					fliph.setEnabled(true);
					flipv.setEnabled(true);			
				}								
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);

		//adds the listeners
		horizontalFlipListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					flip(selectednodes,HORIZONTAL_FLIP);
					
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		fliph.addActionListener(horizontalFlipListener);

		verticalFlipListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>0){
					flip(selectednodes, VERTICAL_FLIP);
					
          //sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		flipv.addActionListener(verticalFlipListener);	
    
  }
  
  
  public Collection getPopupMenuItems(){
		LinkedList popupMenuItems=new LinkedList();
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getStudio(), idflip, labelflip, null);
		popupMenuItems.add(subMenu);
		
		//creating the horizontal flip popup item
		SVGPopupMenuItem horizontalFlipItem=new SVGPopupMenuItem(getStudio(), idfliphorizontal, labelfliphorizontal, fliph.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
					menuItem.setEnabled(true);
					if(menuItem.isEnabled()){
						menuItem.addActionListener(horizontalFlipListener);
					}
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		//creating the vertical flip popup item
		SVGPopupMenuItem verticalFlipItem=new SVGPopupMenuItem(getStudio(), idflipVertical, labelflipvertical, flipv.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				if(nodes!=null && nodes.size()>0){
					menuItem.setEnabled(true);
					if(menuItem.isEnabled()){
						menuItem.addActionListener(verticalFlipListener);
					}
				}else{
					menuItem.setEnabled(false);
				}
				return super.getPopupMenuItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupMenuItem(horizontalFlipItem);
		subMenu.addPopupMenuItem(verticalFlipItem);
		
		return popupMenuItems;
	}
  
  
  /**
   * 
   * @description 
   * @param type
   * @param list
   */
  protected void flip(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			
			final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;
			
			//the map associating a node to an affine transform
			final Hashtable transformMap=new Hashtable();

			Runnable runnable=new Runnable(){
			    
				public void run(){
				
					Node current=null;
					SVGTransformMatrix matrix=null;
					AffineTransform affineTransform=null;
					Rectangle2D rectangle=null;
					double cx=0;
          double cy=0;
				
					//for each selected node
					for(Iterator it=snodes.iterator(); it.hasNext();){
					    
						try{current=(Node)it.next();}catch(Exception ex){current=null;}
						
						if(current!=null){
					
							rectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
						
							//computes the translation values
							cx=0;
							cy=0;
							
							if(rectangle!=null){
								if(finalType==HORIZONTAL_FLIP){
									cx=2*rectangle.getX()+rectangle.getWidth();
								}else if(finalType==VERTICAL_FLIP){
									cy=2*rectangle.getY()+rectangle.getHeight();
								}
							}
	
							//the affine transform
							if(finalType==HORIZONTAL_FLIP){
							  affineTransform=AffineTransform.getScaleInstance(-1, 1);
							}else if(finalType==VERTICAL_FLIP){
							  affineTransform=AffineTransform.getScaleInstance(1, -1);
							}

							affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));
							transformMap.put(current, affineTransform);
						
							//getting, modifying and setting the transform matrix
							matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
							matrix.concatenateTransform(affineTransform);
							getStudio().getSvgToolkit().setTransformMatrix(current, matrix);	
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
			
			//creates the undo/redo action and insert it into the undo/redo stack
			if(getStudio().getSvgUndoRedo()!=null){
						
				//sets the name of the undo/redo action
				String actionName="";
				
				if(type==HORIZONTAL_FLIP){
					actionName=undoredofliphorizontal;
				}else if(type==VERTICAL_FLIP){
					actionName=undoredoflipvertical;
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
										//getting, modifying and setting the transform matrix
										matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
										try{
                      matrix.concatenateTransform(affineTransform.createInverse());
                    }catch (Exception ex){}
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
										//getting, modifying and setting the transform matrix
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
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idflip;
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