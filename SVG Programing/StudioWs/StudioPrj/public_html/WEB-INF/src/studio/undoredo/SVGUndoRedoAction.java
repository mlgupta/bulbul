package studio.undoredo;

/**
 * @author Sudheer V. Pujar
 * the class of the undo/redo actions
 */
public abstract class SVGUndoRedoAction {

	/**
	 * the action's name
	 */
	private String name="";
	
	/**
	 * the construcor of the class
	 * @param name a string
	 */
	public SVGUndoRedoAction(String name){
		this.name=name;	
	}
	
	/**
	 * @return the action's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param name the action's name
	 *
	 */
	public void setName(String name){
		this.name=name;
	}
	
	/**
	 * used to call all the actions that have to be done to undo an action
	 *
	 */
	public void undo(){}
	
	/**
	 * used to call all the actions that have to be done to redo an action
	 *
	 */
	public void redo(){}

}