package bulbul.foff.studio.engine.undoredo;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public abstract class SVGUndoRedoAction  {
  
  private String name="";
  
  /**
   * 
   * @description 
   */
  public SVGUndoRedoAction(String name){
    this.name=name;
  }

  /**
   * 
   * @description 
   */
  public void undo(){}
  
  
  /**
   * 
   * @description 
   */
  public void redo(){}
  
  /**
   * 
   * @description 
   * @see java.lang.Object#toString()
   * @return 
   */
  public String toString(){
    return getName();
  }

  /**
   * 
   * @description 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public String getName() {
    return name;
  }
}