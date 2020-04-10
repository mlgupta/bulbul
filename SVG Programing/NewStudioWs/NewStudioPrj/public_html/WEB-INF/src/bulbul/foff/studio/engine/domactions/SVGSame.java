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
 * @version 1.0 17-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGSame implements SVGClassObject  {
  
  final private String idsame="Same";
  final private String idsamewidth="SameWidth";
  final private String idsameheight="SameHeight";
  final private String idsamesize="SameSize";
	
	private String labelsame="";
  private String labelsamewidth="";
  private String labelsameheight="";
  private String labelsamesize="";
	
	private String undoredosamewidth="";
  private String undoredosameheight="";
  private String undoredosamesize="";
	
	final private int SAME_WIDTH=0;
	final private int SAME_HEIGHT=1;
	final private int SAME_SIZE=2;

	private JButton samewidth;
  private JButton sameheight;
  private JButton samesize;
	
	private ActionListener sameWidthListener;
  private ActionListener sameHeightListener;
  private ActionListener sameSizeListener;
	
  private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  
  
  /**
   * 
   * @description 
   */
  public SVGSame(SVGApplet studio) {
    this.studio=studio;
    	
    samewidth=getStudio().getMainToolBar().getMtbSet2SameWidth();
    sameheight=getStudio().getMainToolBar().getMtbSet2SameHeight();
    samesize=getStudio().getMainToolBar().getMtbSet2SameSize();
    
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
				
				//disables the toolitems
				samewidth.setEnabled(false);
				sameheight.setEnabled(false);
				samesize.setEnabled(false);
				
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
				samewidth.setEnabled(false);
				sameheight.setEnabled(false);
				samesize.setEnabled(false);
				
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
					samewidth.setEnabled(true);
					sameheight.setEnabled(true);
					samesize.setEnabled(true);	
				}								
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);

		//adds the listeners
		sameWidthListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>1){
					same(selectednodes,SAME_WIDTH);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		samewidth.addActionListener(sameWidthListener);

		sameHeightListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>1){
					same(selectednodes,SAME_HEIGHT);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		sameheight.addActionListener(sameHeightListener);
		
		sameSizeListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>1){
					same(selectednodes,SAME_SIZE);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		samesize.addActionListener(sameSizeListener);	
  }
  
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idsame, labelsame, "");
		
		popupItems.add(subMenu);
		
		//creating the same width popup item
		SVGPopupItem sameWidthItem=new SVGPopupItem(getSVGEditor(), idsamewidth, labelsamewidth, "SameWidth"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(sameWidthListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the same height popup item
		SVGPopupItem sameHeightItem=new SVGPopupItem(getSVGEditor(), idsameheight, labelsameheight, "SameHeight"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(sameHeightListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the same size popup item
		SVGPopupItem sameSizeItem=new SVGPopupItem(getSVGEditor(), idsamesize, labelsamesize, "SameSize"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(sameSizeListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(sameWidthItem);
		subMenu.addPopupItem(sameHeightItem);
		subMenu.addPopupItem(sameSizeItem);
		
		return popupItems;
	}
  */
  
  protected void same(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>1 && svgTab!=null){
			
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;
			
			RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			
			Node reference=null;
			
			try{
				reference=(Node)snodes.getFirst();
			}catch (Exception e){reference=null;}
			
			//gets the bounds of the first node in the selected list, that will be used to resize the other nodes
			final Rectangle2D referenceRectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, reference);
			
			if(reference!=null && referenceRectangle!=null){
				
				//the map associating a node to a transform map
				final Hashtable transformMap=new Hashtable();

				//for each node of the list, the scale and translators factors are 
				//computed and the transform matrix is modified
				Runnable runnable=new Runnable(){
				    
					public void run(){
					    
						Iterator it=snodes.iterator();
						
						//the first node of the list is not handled
						if(it.hasNext()){
						    
						    it.next();
						}
	
						SVGTransformMatrix matrix=null;
						AffineTransform affineTransform=null;
						Rectangle2D rectangle=null;
						Node current=null;
						double a=1;
            double d=1;
            double tx=0;
            double ty=0;
						boolean isCorrect;
								
						while(it.hasNext()){
							try{
								current=(Node)it.next();
							}catch (Exception ex){current=null;}
						
							if(current!=null){
								//computes the bounds of the current node
								rectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
								
                a=1; d=1;
								
								//computes the transform factors
								if(rectangle!=null){
									a=referenceRectangle.getWidth()/rectangle.getWidth();
									d=referenceRectangle.getHeight()/rectangle.getHeight();
									tx=rectangle.getX()*(1-a);
									ty=rectangle.getY()*(1-d);
								}
							
								if(finalType==SAME_WIDTH){
									d=1;
									ty=0;
								}else if(finalType==SAME_HEIGHT){
									a=1;
									tx=0;
								}
								
								//the transform
								affineTransform=AffineTransform.getScaleInstance(a, d);
								affineTransform.preConcatenate(AffineTransform.getTranslateInstance(tx, ty));
								
								transformMap.put(current, affineTransform);
							
								//gets, modifies and sets the transformation matrix
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
				};
				
				if(queue.getThread()!=Thread.currentThread()){
					queue.invokeLater(runnable);
				}else{
					runnable.run();
				}

				//sets the name of the undo/redo action
				String actionName="";
				
				if(type==SAME_WIDTH){
					actionName=undoredosamewidth;
				}else if(type==SAME_HEIGHT){
					actionName=undoredosameheight;
				}else if(type==SAME_SIZE){
					actionName=undoredosamesize;
				}
		
				//creates the undo/redo action and insert it into the undo/redo stack
				if(getStudio().getSvgUndoRedo()!=null){
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
						
										if(affineTransform!=null){
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
						
										if(affineTransform!=null){
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
	
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idsame;
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