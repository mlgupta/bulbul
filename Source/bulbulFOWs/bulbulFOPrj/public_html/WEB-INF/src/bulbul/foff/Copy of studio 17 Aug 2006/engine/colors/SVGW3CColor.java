package bulbul.foff.studio.engine.colors;
import java.awt.Color;

/**
 * 
 * @description 
 * @version 1.0 29-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGW3CColor extends SVGColor  {
  /**
   * 
   * @description 
   * @param shownColor
   * @param w3cName
   */
  public SVGW3CColor(String w3cName, Color shownColor) {
    super(w3cName, shownColor);
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getStringRepresentation(){
    return id+" : rgb("+getRed()+", "+getGreen()+" ,"+getBlue()+")";
  }
}