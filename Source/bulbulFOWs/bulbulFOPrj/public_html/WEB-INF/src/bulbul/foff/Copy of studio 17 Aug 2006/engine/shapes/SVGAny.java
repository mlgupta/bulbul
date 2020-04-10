package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.ui.Studio;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGAny extends SVGShape  {
  
  private String shapeanyundoredotranslate="Translate";
  private String shapeanyundoredoresize="Resize";
  private String shapeanyundoredorotate="Rotate";
  
  private final SVGAny any=this;
  
  /**
   * 
   * @description 
   */
  public SVGAny(Studio studio) {
    super(studio);
    ids.put("id","any");
    try{
				labels.put("undoredotranslate", shapeanyundoredotranslate);
				labels.put("undoredoresize", shapeanyundoredoresize);
				labels.put("undoredorotate", shapeanyundoredorotate);
			}catch (Exception ex){}
  }
}