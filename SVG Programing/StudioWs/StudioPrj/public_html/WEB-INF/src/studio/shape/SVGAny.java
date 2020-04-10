package studio.shape;
import studio.canvas.SVGScrollPane;

public class SVGAny  extends SVGShape {
  /**
	 * the reference of an object of this class
	 */
	private final SVGAny any=this;
  
  public SVGAny(SVGScrollPane scrollPane) {
    super(scrollPane);
    ids.put("id","any");
  }
}