package bulbul.foff.studio.engine.properties;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 * 
 * @description 
 * @version 1.0 26-Sep-2005
 * @author Sudheer V Pujar
 */
public abstract class SVGPropertyDevice  {

	
  protected static final LinkedList fontList=new LinkedList();
	protected static final LinkedList fontFamilyList=new LinkedList();
  
  protected LinkedList nodesList=new LinkedList();
  
  protected JComponent component;
  
  protected SVGPropertyItem propertyItem=null;
  
  protected String name="";
  protected String label="";
  
  protected Runnable disposer=null;
	
  protected static DecimalFormat format;
    
  static{
    //sets the format used to convert numbers into a string
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("######.#",symbols);
  }

	static{
		//gets the graphics environment and the array of the platform available fonts 
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fontTab=ge.getAllFonts();
		
		//removes all the duplicated font families and creates the list of fonts
		Font cfont=null;
		
		for(int i=0;i<fontTab.length;i++){
			if(fontTab[i]!=null && ! fontFamilyList.contains(fontTab[i].getFamily())){
				cfont=fontTab[i].deriveFont((float)(11));
				if(cfont!=null){
					fontList.add(cfont);
					fontFamilyList.add(cfont.getFamily());
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   */
  public SVGPropertyDevice(SVGPropertyItem propertyItem) {
    this.propertyItem=propertyItem;
    name=new String(propertyItem.getPropertyName());
    label=new String(propertyItem.getPropertyLabel());
    nodesList.addAll(propertyItem.getNodeList());
  }
  
  /**
   * 
   * @description 
   */
  public void dispose(){
    if(disposer!=null){
    	disposer.run();
    }
  }
   
  /**
   * 
   * @description 
   * @return 
   */
  public JComponent getComponent() {
    return component;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getLabel() {
    return label;
  }
}