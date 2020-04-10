package bulbul.foff.studio.engine.selection;
import java.awt.Cursor;
import java.awt.geom.Rectangle2D;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGSelectionSquare  {

  private Rectangle2D.Double  rectangle;
  private Cursor cursor;
  private String type;
  private Node node;
  
  /**
   * 
   * @description 
   */
  public SVGSelectionSquare(Node node, String type, Rectangle2D.Double rectangle, Cursor cursor) {
    this.node=node;
    this.type=type;
    this.rectangle=rectangle;
    this.cursor=cursor;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Rectangle2D.Double getRectangle() {
    return rectangle;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Cursor getCursor() {
    return cursor;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public String getType() {
    return type;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Node getNode() {
    return node;
  }
  
  
}