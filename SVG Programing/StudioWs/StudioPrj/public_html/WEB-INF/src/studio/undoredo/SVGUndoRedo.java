package studio.undoredo;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import studio.canvas.SVGScrollPane;

public class SVGUndoRedo  {
  /**
	 * the hashtable associating a frame to a stack of the undo action lists 
	 */
	private LinkedList undoList=new LinkedList();
	
	/**
	 *  the hashtable associating a frame to a stack of the redo action lists
	 */
	private LinkedList redoList=new LinkedList();
  
  /**
	 * the amount of action lists the undo/redo stacks can contain
	 */
	private int stackDepth=30;
  
  private SVGScrollPane scrollPane;
  
  public SVGUndoRedo(SVGScrollPane scrollPane) {
    this.scrollPane=scrollPane;
  }
  
  /**
	 * adds an action to the undo stack
	 * @param actionlist  the actionlist that describes what has to be done when calling the undo or redo action
	 */
	public void addAction(SVGUndoRedoActionList actionlist){
		
		if(actionlist!=null){
			//undo.setText(labelundo+" "+actionlist.getName());
			//undo.setEnabled(true);

			if(undoList.size()>stackDepth)undoList.removeFirst();
			undoList.addLast(actionlist);
			redoList.clear();

			//redo.setText(labelredo);
			//redo.setEnabled(false);
		}
	}
  
  /**
	 * undoes the last action added into the undo stack
	 */
	public void undoLastAction(){
		
		if(scrollPane!=null){
			
			//sets that the document has been modified
			scrollPane.setModified(true);
					
			if(undoList.size()>0){
				//gets the action list to call the undo methods
				SVGUndoRedoActionList actionList=(SVGUndoRedoActionList)undoList.getLast();
				//adds the action list to the redo stack
				redoList.addLast(actionList);
				//redo.setText(labelredo+" "+actionList.getName());
				//if(!redo.isEnabled())redo.setEnabled(true);
				
        //removes the list from the undo stack
				undoList.removeLast();
					
				if(undoList.size()==0){
					//undo.setText(labelundo);
					//undo.setEnabled(false);
				}else{
					//undo.setText(labelundo+" "+((SVGUndoRedoActionList)undolist.getLast()).getName());
				}
				//calls the undo method on each action of the action list
				Iterator it=actionList.iterator();
				SVGUndoRedoAction current=null;
				while(it.hasNext()){
					try{
						current=(SVGUndoRedoAction)it.next();
					}catch (Exception ex){}
					if(current!=null){
						current.undo();
					}
				}
			}
			scrollPane.getSVGCanvas().delayedRepaint();
		}
	}
  
  /**
	 * redoes the last action added into the redo stack
	 */	
	public void redoLastAction(){
		
		if(scrollPane!=null){
			
			//sets that the document has been modified
			scrollPane.setModified(true);
			
			
			if(redoList.size()>0){
				//gets the action list to call the redo methods
				SVGUndoRedoActionList actionList=(SVGUndoRedoActionList)redoList.getLast();
				//adds the action list to the undo stack
				undoList.addLast(actionList);
				//undo.setText(labelundo+" "+actionList.getName());
				//if(!undo.isEnabled())undo.setEnabled(true);
				
        //removes the list from the redo stack
				redoList.removeLast();
				if(redoList.size()==0){
					//redo.setText(labelredo);
					//redo.setEnabled(false);
				}else{
          //redo.setText(labelredo+" "+((SVGUndoRedoActionList)redolist.getLast()).getName());
				}
				//calls the redo method on each action of the action list
				Iterator it=actionList.iterator();
				SVGUndoRedoAction current=null;
				while(it.hasNext()){
					try{
						current=(SVGUndoRedoAction)it.next();
					}catch (Exception ex){}
					if(current!=null){
						current.redo();
					}
				}
			}
			scrollPane.getSVGCanvas().delayedRepaint();
		}
	}
}