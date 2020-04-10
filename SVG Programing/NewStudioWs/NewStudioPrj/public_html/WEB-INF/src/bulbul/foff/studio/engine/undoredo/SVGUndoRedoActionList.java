package bulbul.foff.studio.engine.undoredo;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGUndoRedoActionList extends LinkedList {
  
  private String name="";
  /**
   * 
   * @description 
   */
  public SVGUndoRedoActionList(String name) {
    super();
    this.name=name;
  }


  /**
   * 
   * @description 
   * @see java.util.AbstractCollection#toString()
   * @return 
   */
  public synchronized String toString(){
     String toString=getName().concat("=[");
      for(Iterator it=iterator(); it.hasNext();){
          toString=toString.concat(it.next().toString().concat(", "));
      }
      toString=toString.concat("]");
      return toString;
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