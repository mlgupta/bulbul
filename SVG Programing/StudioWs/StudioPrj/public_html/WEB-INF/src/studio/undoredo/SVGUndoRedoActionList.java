package studio.undoredo;

import java.util.LinkedList;

/**
 * @author Sudheer V. Pujar
 * the class of the list of SVGUndoRedoActions
 */
public class SVGUndoRedoActionList extends LinkedList {

	/**
	 * the name of the action list
	 */
	private String name="";
	
	/**
	 * the constructor of the class
	 * @param name  the name of the action list
	 */
	public SVGUndoRedoActionList(String name) {
		super();
		this.name=name;
	}
	
	/**
	 * @return the name of the action list
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * sets the name of the action list
	 * @param name the name of the action list
	 */
	public void setName(String name){
		this.name=name;
	}

}