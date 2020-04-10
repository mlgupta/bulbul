package studio.shape;
import studio.canvas.SVGScrollPane;

public class SVGCircle extends SVGShape  {
  /**
	 * the reference of an object of this class
	 */
	private final SVGCircle svgCircle=this;
  public SVGCircle(SVGScrollPane scrollPane) {
    super(scrollPane);
    ids.put("id","circle");
  }
}