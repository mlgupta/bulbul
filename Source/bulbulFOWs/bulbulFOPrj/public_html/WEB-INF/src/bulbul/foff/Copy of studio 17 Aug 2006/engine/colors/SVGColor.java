package bulbul.foff.studio.engine.colors;
import java.awt.Color;

/**
 * 
 * @description 
 * @version 1.0 29-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGColor extends Color {
   
   protected String id="";
   
  /**
   * 
   * @description 
   * @param color
   * @param id
   */
  public SVGColor(String id, Color color) {
    super(color.getRed(), color.getGreen(), color.getBlue());
    this.id=id;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getId() {
      return id;
  }
}