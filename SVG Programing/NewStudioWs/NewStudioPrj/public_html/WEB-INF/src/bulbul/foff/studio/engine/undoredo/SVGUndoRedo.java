package bulbul.foff.studio.engine.undoredo;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import org.apache.batik.util.RunnableQueue;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGUndoRedo implements SVGClassObject {

  private Map  redoMap=Collections.synchronizedMap(new Hashtable());
  private Map  undoMap=Collections.synchronizedMap(new Hashtable());
  
  private final int stackDepth=30;
  
  private final String idundo="Undo";
  private final String idredo="Redo";
  private final String idundoredo="UndoRedo";
  
  private String labelundoredo="";
  private String labelundo="";
  private String labelredo="";
  
  private JButton undo;
  private JButton redo;
  
  private SVGApplet studio;
  
  /**
   * 
   * @description 
   */
  public SVGUndoRedo(SVGApplet studio) {
    this.studio=studio;
    
    undo = getStudio().getMainToolBar().getMtbUndo();
    redo = getStudio().getMainToolBar().getMtbRedo();
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				Collection undoFrames=new LinkedList(undoMap.keySet());
				Collection availableFrames=new LinkedList(getStudio().getSvgTabManager().getSvgTabs());

				SVGTab svgTab4Loop=null;
				
				for(Iterator it=undoFrames.iterator(); it.hasNext();){
					try{
            svgTab4Loop=(SVGTab)it.next();
          }catch (Exception ex){}
					
					if(svgTab4Loop!=null && ! availableFrames.contains(svgTab4Loop)){
						undoMap.remove(svgTab4Loop);
						redoMap.remove(svgTab4Loop);
					}
				}

				if(svgTab!=null){
				    
					//the undo and redo stacks associated with the current frame
					LinkedList undoList=null;
          LinkedList redoList=null;
					
					try{
						undoList=(LinkedList)undoMap.get(svgTab);
						redoList=(LinkedList)redoMap.get(svgTab);
					}catch (Exception ex){undoList=null; redoList=null;}
			
					if(undoList==null){
						undoList=new LinkedList();
						undoMap.put(svgTab, undoList);
					}
			
					if(redoList==null){
						redoList=new LinkedList();
						redoMap.put(svgTab, redoList);
					}
				
					//changes the labels in the menu items according to the current frame
					SVGUndoRedoActionList actionList=null;
				
					if(undoList.size()>0){
						try{
							actionList=(SVGUndoRedoActionList)undoList.getLast();
						}catch (Exception ex){actionList=null;}
					}
					
					//if the undo stack is not empty
					if(actionList!=null){
						//undo.setToolTipText(labelundo+" "+actionList.getName());
						undo.setEnabled(true);
					}else{
						//if the undo stack is empty, the tool item is disabled
						//undo.setToolTipText(labelundo);
						undo.setEnabled(false);
					}
				
					actionList=null;
				
					if(redoList.size()>0){
						try{
							actionList=(SVGUndoRedoActionList)redoList.getLast();
						}catch (Exception ex){actionList=null;}
					}
					//if the redo stack is not empty
					if(actionList!=null){
						//redo.setToolTipText(labelredo+" "+actionList.getName());
						redo.setEnabled(true);
					}else{
						//if the redo stack is empty, the menu item is disabled
						//redo.setToolTipText(labelredo);
						redo.setEnabled(false);
					}
					
				}else{
				    //as no frame is displayed, the menu items are disabled
					//undo.setToolTipText(labelundo);
					undo.setEnabled(false);
					
					//redo.setToolTipText(labelredo);
					redo.setEnabled(false);
				}
			}
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    //adds the undo listener to the menuItem
		undo.addActionListener(new ActionListener(){
			public synchronized void actionPerformed(ActionEvent e) {
				final SVGTab finalSvgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(finalSvgTab!=null){
					RunnableQueue queue=finalSvgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
					
					if(queue.getThread()!=Thread.currentThread()){
						queue.invokeLater(new Runnable(){
						    
							public void run(){
								undoLastAction(finalSvgTab);
							}
						});
					}else{
						undoLastAction(finalSvgTab);
					}
				}
			}
		});
		
		//adds the redo listener to the menuItem
		redo.addActionListener(new ActionListener(){
			public synchronized void actionPerformed(ActionEvent e) {
				final SVGTab finalSvgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(finalSvgTab!=null){
					RunnableQueue queue=finalSvgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
					
					if(queue.getThread()!=Thread.currentThread()){
						queue.invokeLater(new Runnable(){
						    
							public void run(){
								redoLastAction(finalSvgTab);
							}
						});
					}else{
						redoLastAction(finalSvgTab);
					}
				}
			}
		});
    
  }
  
  /*
  public Collection getPopupItems(){
		
		LinkedList popupItems=new LinkedList();
		
		//creating and adding the undo popup item
		SVGPopupItem undoItem=new SVGPopupItem(getSVGEditor(), idundo, labelundo, "Undo"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				//getting the current frame
				SVGFrame frame=editor.getFrameManager().getCurrentFrame();
				
				//the list of the undo actions
				LinkedList undoList=null;
				
				if(frame!=null){
					
					undoList=(LinkedList)undoMap.get(frame);
				}
				
				if(undoList!=null && undoList.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					menuItem.addActionListener(undoListener);
					
					//setting the label for the undo menu item
					menuItem.setText(labelundo+" "+((SVGUndoRedoActionList)undoList.getLast()).getName());
					
				}else{
					
					menuItem.setText(labelundo);
					menuItem.setEnabled(false);
				}

				return super.getPopupItem(nodes);
			}
		};
		
		popupItems.add(undoItem);
		
		//creating and adding the redo popup item
		SVGPopupItem redoItem=new SVGPopupItem(getSVGEditor(), idredo, labelredo, "Redo"){
		
			public JMenuItem getPopupItem(LinkedList nodes) {

				//getting the current frame
				SVGFrame frame=editor.getFrameManager().getCurrentFrame();
				
				//the list of the redo actions
				LinkedList redoList=null;
				
				if(frame!=null){
					
					redoList=(LinkedList)redoMap.get(frame);
				}
				
				if(redoList!=null && redoList.size()>0){
					
					menuItem.setEnabled(true);
					
					//adds the action listeners
					menuItem.addActionListener(redoListener);
					
					//setting the label for the redo menu item
					menuItem.setText(labelredo+" "+((SVGUndoRedoActionList)redoList.getLast()).getName());
					
				}else{
					
					menuItem.setText(labelredo);
					menuItem.setEnabled(false);
				}

				return super.getPopupItem(nodes);
			}
		};
		
		popupItems.add(redoItem);

		return popupItems;
	}
  */
  
  /**
   * 
   * @description 
   * @param actionList
   * @param svgTab
   */
  public void addActionList(SVGTab svgTab, SVGUndoRedoActionList actionList){
    if(svgTab!=null && actionList!=null){
			
			//undo.setToolTipText(labelundo+" "+actionList.getName());
			undo.setEnabled(true);
			
			//the undo and redo stacks associated with the current svgTab
			LinkedList undoList=null;
      LinkedList redoList=null;
			try{
				undoList=(LinkedList)undoMap.get(svgTab);
				redoList=(LinkedList)redoMap.get(svgTab);
			}catch (Exception ex){undoList=null; redoList=null;}
			
			if(undoList==null){
				undoList=new LinkedList();
				undoMap.put(svgTab, undoList);
			}
			
			if(redoList==null){
				redoList=new LinkedList();
				redoMap.put(svgTab, redoList);
			}
			
			if(undoList.size()>stackDepth){
			  undoList.removeFirst();
			}
			
			undoList.addLast(actionList);
			redoList.clear();
			//redo.setToolTipText(labelredo);
			redo.setEnabled(false);
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void redoLastAction(SVGTab svgTab){
    if(svgTab!=null){
			
			//sets that the document has been modified
			svgTab.setModified(true);
			
			//the undo and redo stacks associated with the current tab
			LinkedList undoList=null; 
      LinkedList redoList=null;
			
			try{
				undoList=(LinkedList)undoMap.get(svgTab);
				redoList=(LinkedList)redoMap.get(svgTab);
			}catch (Exception ex){undoList=null; redoList=null;}
				
			if(undoList==null){
				undoList=new LinkedList();
				undoMap.put(svgTab, undoList);
			}
				
			if(redoList==null){
				redoList=new LinkedList();
				redoMap.put(svgTab, redoList);
			}
			
			if(redoList.size()>0){
			    
				//gets the action list to call the redo methods
				SVGUndoRedoActionList actionList=(SVGUndoRedoActionList)redoList.getLast();
				//adds the action list to the undo stack
				undoList.addLast(actionList);
				//undo.setToolTipText(labelundo+" "+actionList.getName());
				if (!undo.isEnabled()){
          undo.setEnabled(true);
        }
				
				
				//removes the list from the redo stack
				redoList.removeLast();
				
				if(redoList.size()==0){
					//redo.setToolTipText(labelredo);
					redo.setEnabled(false);
				}else{
					//redo.setToolTipText(labelredo+" "+((SVGUndoRedoActionList)redoList.getLast()).getName());
				}
				
				//calls the redo method on each action of the action list
				SVGUndoRedoAction current=null;
				
				for(Iterator it=actionList.iterator(); it.hasNext();){
					try{
						current=(SVGUndoRedoAction)it.next();
					}catch (Exception ex){}
					
					if(current!=null){
						current.redo();
					}
				}
			}
			
			svgTab.getScrollPane().getSvgCanvas().delayedRepaint();
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void undoLastAction(SVGTab svgTab){
    if(svgTab!=null){
			//sets that the document has been modified
			svgTab.setModified(true);
			
			//the undo and redo stacks associated with the current tab
			LinkedList undoList=null;
      LinkedList redoList=null;
			
			try{
				undoList=(LinkedList)undoMap.get(svgTab);
				redoList=(LinkedList)redoMap.get(svgTab);
			}catch (Exception ex){undoList=null; redoList=null;}
			
			if(undoList==null){
				undoList=new LinkedList();
				undoMap.put(svgTab, undoList);
			}
			
			if(redoList==null){
				redoList=new LinkedList();
				redoMap.put(svgTab, redoList);
			}
		
			if(undoList.size()>0){
				//gets the action list to call the undo methods
				SVGUndoRedoActionList actionList=(SVGUndoRedoActionList)undoList.getLast();
				
				//adds the action list to the redo stack
				redoList.addLast(actionList);
				//redo.setToolTipText(labelredo+" "+actionList.getName());
				if(! redo.isEnabled()){
          redo.setEnabled(true);
        }
				
				//removes the list from the undo stack
				undoList.removeLast();
					
				if(undoList.size()==0){
					//undo.setToolTipText(labelundo);
					undo.setEnabled(false);
				}else{
					//undo.setToolTipText(labelundo+" "+((SVGUndoRedoActionList)undoList.getLast()).getName());
				}
				
				//calls the undo method on each action of the action list
				Iterator it=actionList.iterator();
				SVGUndoRedoAction current=null;

				for(int i=actionList.size()-1; i>=0; i--){
					try{
						current=(SVGUndoRedoAction)actionList.get(i);
					}catch (Exception ex){}
					
					if(current!=null){
						current.undo();
					}
				}
			}
			svgTab.getScrollPane().getSvgCanvas().delayedRepaint();
		}
  }
  
  public void cancelActions(){
	
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idundoredo;
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