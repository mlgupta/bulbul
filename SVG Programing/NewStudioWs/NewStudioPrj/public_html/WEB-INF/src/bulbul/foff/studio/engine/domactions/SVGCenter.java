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
import java.awt.geom.Dimension2D;
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
public class SVGCenter implements SVGClassObject  {
  
  final private String idCenter="Center";
  final private String idCenterHorizontal="HorizontalCenter";
  final private String idCenterVertical="VerticalCenter";
	
	private String labelcenter="";
  private String labelcenterhorizontal="";
  private String labelcentervertical="";
	
	private String undoredocenterhorizontal="";
  private String undoredocentervertical="";
	
	private ActionListener centerHorizontalListener;
  private ActionListener centerVerticalListener;
	
	final private int HORIZONTAL_CENTER=0;
	final private int VERTICAL_CENTER=1;

	private JButton centerh;
  private JButton centerv;
	
	private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  
  /**
   * 
   * @description 
   */
  public SVGCenter(SVGApplet studio) {
    this.studio=studio; 
    
    centerh=getStudio().getMainToolBar().getMtbCenterHorizontal();
    centerv=getStudio().getMainToolBar().getMtbCenterVertical();
    
    //a listener that listens to the changes of the SVGFrames
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
				centerh.setEnabled(false);
				centerv.setEnabled(false);
				
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
				centerh.setEnabled(false);
				centerv.setEnabled(false);
				
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
					centerh.setEnabled(true);
					centerv.setEnabled(true);			
				}								
			}
			
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
		
		//adds the listeners
		
		centerHorizontalListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);
				if(selectednodes.size()>0){
					center(selectednodes,HORIZONTAL_CENTER);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};
		
		centerh.addActionListener(centerHorizontalListener);
		
		centerVerticalListener=new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
				getStudio().cancelActions(true);

				if(selectednodes.size()>0){
					center(selectednodes, VERTICAL_CENTER);
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		};

		centerv.addActionListener(centerVerticalListener);		
    
  }

  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idCenter, labelcenter, "");
		
		popupItems.add(subMenu);
		
		//creating the horizontal center popup item
		SVGPopupItem horizontalCenterItem=new SVGPopupItem(getSVGEditor(), idCenterHorizontal, labelcenterhorizontal, "HorizontalCenter"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(centerHorizontalListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the vertical center popup item
		SVGPopupItem verticalCenterItem=new SVGPopupItem(getSVGEditor(), idCenterVertical, labelcentervertical, "VerticalCenter"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(centerVerticalListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(horizontalCenterItem);
		subMenu.addPopupItem(verticalCenterItem);
		
		return popupItems;
	}
  */
  
  protected void center(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>0 && svgTab!=null){
			
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;
			RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			final Dimension2D canvasSize=svgTab.getScrollPane().getSvgCanvas().getSVGDocumentSize();
			
			if(canvasSize!=null){
			    
				//the map associating a node to its affine transform for this action
				final Hashtable transformMap=new Hashtable();
				
				Runnable runnable=new Runnable(){
				    
					public void run(){
								
						SVGTransformMatrix matrix=null;
						AffineTransform affineTransform=null;
						Rectangle2D rectangle=null;
						Node current=null;
						double e=0;
            double f=0;
								
						for(Iterator it=snodes.iterator(); it.hasNext();){
							try{
								current=(Node)it.next();
							}catch (Exception ex){current=null;}
						
							if(current!=null){

								rectangle=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
								
								if(rectangle!=null){
									e=0; f=0;
									if(finalType==HORIZONTAL_CENTER){
										e=(canvasSize.getWidth()-rectangle.getWidth())/2-rectangle.getX();
									}else if(finalType==VERTICAL_CENTER){
										f=(canvasSize.getHeight()-rectangle.getHeight())/2-rectangle.getY();
									}
									
									affineTransform=AffineTransform.getTranslateInstance(e, f);
									transformMap.put(current, affineTransform);
									
									//sets the transformation matrix
									if(affineTransform!=null && ! affineTransform.isIdentity()){
                    matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
                    matrix.concatenateTransform(affineTransform);
                    getStudio().getSvgToolkit().setTransformMatrix(current, matrix);
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

				//sets the name of the undo/redo action
				String actionName="";
				
				if(type==HORIZONTAL_CENTER){
					actionName=undoredocenterhorizontal;
				}else if(type==VERTICAL_CENTER){
					actionName=undoredocentervertical;
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
                      matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
                      try {matrix.concatenateTransform(affineTransform.createInverse());}catch (Exception ex){}
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
		return idCenter;
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