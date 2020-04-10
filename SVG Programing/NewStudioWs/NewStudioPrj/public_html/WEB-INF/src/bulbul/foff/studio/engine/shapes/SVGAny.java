package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.ui.SVGApplet;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGAny extends SVGShape  {
  private final SVGAny any=this;
  /**
   * 
   * @description 
   */
  public SVGAny(SVGApplet studio) {
    super(studio);
    ids.put("id","any");
  }
}