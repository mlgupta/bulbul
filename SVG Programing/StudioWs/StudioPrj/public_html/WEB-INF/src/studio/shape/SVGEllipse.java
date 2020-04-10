package studio.shape;
import studio.canvas.SVGScrollPane;

public class SVGEllipse extends SVGShape  {
  /**
	 * the reference of an object of this class
	 */
	private final SVGEllipse svgEllipse=this;
  
  public SVGEllipse(SVGScrollPane scrollPane) {
    super(scrollPane);
    ids.put("id","ellipse");
  }
}