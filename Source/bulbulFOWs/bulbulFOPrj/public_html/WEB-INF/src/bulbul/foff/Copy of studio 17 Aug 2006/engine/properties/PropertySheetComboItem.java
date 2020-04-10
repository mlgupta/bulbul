package bulbul.foff.studio.engine.properties;

/**
 * 
 * @description 
 * @version 1.0 27-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetComboItem  {
  
	private String value;
  private String label;
	
  /**
   * 
   * @description 
   * @param label
   * @param value
   */
	protected PropertySheetComboItem(String value, String label){
		this.value=value;
		this.label=label;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public String getValue(){
		return value;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public String toString(){
		return label;
	}
}