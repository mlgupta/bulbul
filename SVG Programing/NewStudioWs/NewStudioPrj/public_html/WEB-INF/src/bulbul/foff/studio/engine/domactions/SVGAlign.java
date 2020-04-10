package bulbul.foff.studio.engine.domactions;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
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
public class SVGAlign implements SVGClassObject  {
  
  final private String idalign="Align";
  final private String idleft="Left";
  final private String idright="Right";
  final private String idtop="Top";
  final private String idbottom="Bottom";
  final private String idcenter="Center";
  final private String idhorizontalcenter="HorizontalCenter";
  final private String idverticalcenter="VerticalCenter";
  
  private String labelalign="";
  private String labelalignleft="";
  private String labelalignright="";
  private String labelaligntop="";
  private String labelalignbottom="";
  private String labelaligncenter="";
  private String labelalignhorizontalcenter="";
  private String labelalignverticalcenter="";
  
  private String undoredoalignleft="";
  private String undoredoalignright="";
  private String undoredoaligntop="";
  private String undoredoalignbottom="";
  private String undoredoaligncenter="";
  private String undoredoalignhorizontalcenter="";
  private String undoredoalignverticalcenter="";
  
  public final int ALIGN_LEFT=0;
	public final int ALIGN_RIGHT=1;
	public final int ALIGN_TOP=2;
	public final int ALIGN_BOTTOM=3;
	public final int ALIGN_CENTER=4;
	public final int ALIGN_HORIZONTAL_CENTER=5;
	public final int ALIGN_VERTICAL_CENTER=6;
  
  private JButton alignLeft;
  private JButton alignRight;
  private JButton alignTop;
  private JButton alignBottom;
  private JButton alignCenter;
  private JButton alignHorizontalCenter;
  private JButton alignVerticalCenter;
  
  private LinkedList selectednodes=new LinkedList();
  
  private SVGApplet studio;
  /**
   * 
   * @description 
   */
  public SVGAlign(SVGApplet studio) {
    this.studio=studio;
    
    alignLeft=getStudio().getMainToolBar().getMtbAlignLeft();
    alignRight=getStudio().getMainToolBar().getMtbAlignRight();
    alignTop=getStudio().getMainToolBar().getMtbAlignTop();
    alignBottom=getStudio().getMainToolBar().getMtbAlignBottom();
    alignCenter=getStudio().getMainToolBar().getMtbAlignCenter();
    alignHorizontalCenter=getStudio().getMainToolBar().getMtbAlignCenterXAxis();
    alignVerticalCenter=getStudio().getMainToolBar().getMtbAlignCenterYAxis();
    
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
				alignLeft.setEnabled(false);
				alignRight.setEnabled(false);
				alignTop.setEnabled(false);
				alignBottom.setEnabled(false);
				alignCenter.setEnabled(false);
				alignHorizontalCenter.setEnabled(false);
				alignVerticalCenter.setEnabled(false);
				
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
			 * updates the selected items and the state of the tool items
			 */
			protected void manageSelection(){
			    
				//disables the menuitems							
				alignLeft.setEnabled(false);
				alignRight.setEnabled(false);
				alignTop.setEnabled(false);
				alignBottom.setEnabled(false);
				alignCenter.setEnabled(false);
				alignHorizontalCenter.setEnabled(false);
				alignVerticalCenter.setEnabled(false);
				
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
					alignLeft.setEnabled(true);
					alignRight.setEnabled(true);
					alignTop.setEnabled(true);
					alignBottom.setEnabled(true);
					alignCenter.setEnabled(true);
					alignHorizontalCenter.setEnabled(true);
					alignVerticalCenter.setEnabled(true);
				}								
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);	
    
    //adds the listeners
		alignLeft.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					
					align(selectednodes,ALIGN_LEFT);
					
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});

		
		
		alignRight.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){
					
					align(selectednodes,ALIGN_RIGHT);
					
					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
		
		alignTop.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){

					align(selectednodes,ALIGN_TOP);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
		
		alignBottom.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){

					align(selectednodes,ALIGN_BOTTOM);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
		
		alignCenter.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){

					align(selectednodes,ALIGN_CENTER);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
		
		alignHorizontalCenter.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){

					align(selectednodes, ALIGN_HORIZONTAL_CENTER);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
		
		alignVerticalCenter.addActionListener(new ActionListener(){
		    
			public void actionPerformed(ActionEvent e) {
			    
				getStudio().cancelActions(true);
				
				if(selectednodes.size()>0){

					align(selectednodes, ALIGN_VERTICAL_CENTER);

					//sets that the svg has been modified
					getStudio().getSvgTabManager().getCurrentSVGTab().setModified(true);
				}
			}
		});
  
  }
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		SVGPopupSubMenu subMenu=new SVGPopupSubMenu(getSVGEditor(), idalign, labelalign, "");
		
		popupItems.add(subMenu);
		
		//creating the align left popup item
		SVGPopupItem alignLeftItem=new SVGPopupItem(getSVGEditor(), idleft, labelalignleft, "AlignLeft"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignLeftListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the align bottom popup item
		SVGPopupItem alignRightItem=new SVGPopupItem(getSVGEditor(), idright, labelalignright, "AlignRight"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignRightListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the align top popup item
		SVGPopupItem alignTopItem=new SVGPopupItem(getSVGEditor(), idtop, labelaligntop, "AlignTop"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignTopListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};

		//creating the align bottom popup item
		SVGPopupItem alignBottomItem=new SVGPopupItem(getSVGEditor(), idbottom, labelalignbottom, "AlignBottom"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignBottomListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the align center popup item
		SVGPopupItem alignCenterItem=new SVGPopupItem(getSVGEditor(), idcenter, labelaligncenter, "AlignCenter"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignCenterListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the horizontal align center popup item
		SVGPopupItem horizontalAlignCenterItem=new SVGPopupItem(getSVGEditor(), idhorizontalcenter, labelalignhorizontalcenter, "AlignHorizontalCenter"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignHorizontalCenterListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//creating the vertical align center popup item
		SVGPopupItem verticalAlignCenterItem=new SVGPopupItem(getSVGEditor(), idverticalcenter, labelalignverticalcenter, "AlignVerticalCenter"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				if(nodes!=null && nodes.size()>1){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					if(menuItem.isEnabled()){
						
						menuItem.addActionListener(alignVerticalCenterListener);
					}
					
				}else{
					
					menuItem.setEnabled(false);
				}
				
				return super.getPopupItem(nodes);
			}
		};
		
		//adding the popup items to the sub menu
		subMenu.addPopupItem(alignLeftItem);
		subMenu.addPopupItem(alignRightItem);
		subMenu.addPopupItem(alignTopItem);
		subMenu.addPopupItem(alignBottomItem);
		subMenu.addPopupItem(alignCenterItem);
		subMenu.addPopupItem(horizontalAlignCenterItem);
		subMenu.addPopupItem(verticalAlignCenterItem);
		
		return popupItems;
	}
  */
  
  /**
   * 
   * @description 
   * @param type
   * @param list
   */
  protected void align(LinkedList list, int type){
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(list!=null && list.size()>1 && svgTab!=null){
			
			RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			final LinkedList snodes=new LinkedList(list);
			final int finalType=type;

			//the map associating a node to its transform for the alignment
			final Hashtable transformMap=new Hashtable();
			
			//computing the union of all the outlines of the selected nodes
			Node current=null;
			final Area area=new Area();
			Shape outline=null;

			for(Iterator it=snodes.iterator(); it.hasNext();){
			    
				try{current=(Node)it.next();}catch (Exception e){current=null;}
				
				if(current!=null){	
				    
					outline=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
					
					if(outline!=null){
					    
						area.add(new Area(outline));
					}
				}
			}
			
			//computes the values of the translation depending on the type of the alignment
			Runnable runnable=new Runnable(){
			    
				public void run(){

					AffineTransform affineTransform=null;
					SVGTransformMatrix matrix=null;
					Rectangle2D rect;
          Rectangle2D  rect0=area.getBounds();
					Node current=null;
					double e=0;
          double f=0;
					
					for(Iterator it=snodes.iterator(); it.hasNext();){
					    
						try{
              current=(Node)it.next();
            }catch (Exception ex){current=null;}
						
						if(current!=null){
							rect=getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
						
							if(finalType==ALIGN_LEFT){
								if(rect0!=null && rect!=null){
									e=rect0.getX()-rect.getX();
								}
							}else if(finalType==ALIGN_RIGHT){
								if(rect0!=null && rect!=null){
									e=(rect0.getX()+rect0.getWidth())-(rect.getX()+rect.getWidth());
								}
							}else if(finalType==ALIGN_TOP){
								if(rect0!=null && rect!=null){
									f=rect0.getY()-rect.getY();
								}
							}else if(finalType==ALIGN_BOTTOM){
								if(rect0!=null && rect!=null){
									f=(rect0.getY()+rect0.getHeight())-(rect.getY()+rect.getHeight());
								}
							}else if(finalType==ALIGN_CENTER){
								if(rect0!=null && rect!=null){
                  e=(rect0.getX()+(rect0.getWidth()-rect.getWidth())/2)-rect.getX();
                  f=rect0.getY()+(rect0.getHeight()-rect.getHeight())/2-rect.getY();
								}
							}else if(finalType==ALIGN_HORIZONTAL_CENTER){
								if(rect0!=null && rect!=null){
								  e=(rect0.getX()+(rect0.getWidth()-rect.getWidth())/2)-rect.getX();
								}
							}else if(finalType==ALIGN_VERTICAL_CENTER){
								if(rect0!=null && rect!=null){
								  f=rect0.getY()+(rect0.getHeight()-rect.getHeight())/2-rect.getY();
								}
							}else return;	

							affineTransform=AffineTransform.getTranslateInstance(e, f);
							transformMap.put(current, affineTransform);
							if(! affineTransform.isIdentity()){
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
			if(type==ALIGN_LEFT){
				actionName=undoredoalignleft;
			}else if(type==ALIGN_RIGHT){
				actionName=undoredoalignright;
			}else if(type==ALIGN_TOP){
				actionName=undoredoaligntop;
			}else if(type==ALIGN_BOTTOM){
				actionName=undoredoalignbottom;
			}else if(type==ALIGN_CENTER){
				actionName=undoredoaligncenter;
			}else if(type==ALIGN_HORIZONTAL_CENTER){
				actionName=undoredoalignhorizontalcenter;
			}else if(type==ALIGN_VERTICAL_CENTER){
				actionName=undoredoalignverticalcenter;
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
									if(affineTransform!=null && ! affineTransform.isIdentity()){
                    matrix=getStudio().getSvgToolkit().getTransformMatrix(current);
                    try{
                      matrix.concatenateTransform(affineTransform.createInverse());
                    }catch(Exception ex){System.out.println(ex);}
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

  /**
   * 
   * @description 
   * @return 
   */
	public String getName(){
		return idalign;
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