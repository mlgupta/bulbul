package bulbul.foff.studio.engine.properties;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetNumberChooserDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetNumberChooserDevice(SVGPropertyItem propertyItem) {
		super(propertyItem);
		buildComponent();
	}
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
		String valueType=propertyItem.getPropertyType();
    SpinnerNumberModel spinnerModel=null;
    
    double value=0;

    try{
        value=Double.parseDouble(propertyItem.getGeneralPropertyValue());
    }catch(Exception ex){value=0;}

    if(valueType.equals("positivenumberchooser")){
      if(value<0){
        value=0;
      }
      spinnerModel=new SpinnerNumberModel(value, 0, 10000000, 1);
    }else{
      spinnerModel=new SpinnerNumberModel(value, -10000000, 10000000, 1);
    }
    
    //the spinner
    final JSpinner spinner=new JSpinner(spinnerModel);
    
    //the listener to the changes
    final ChangeListener changeListener=new ChangeListener(){
      public void stateChanged(ChangeEvent evt) {
        propertyItem.changePropertyValue(spinner.getModel().getValue()+"");
      } 
    };
    
    //adding the listener
    spinner.addChangeListener(changeListener);
    
    //the panel that will be returned
    JPanel panel=new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.add(spinner);
	    
		component=panel;

		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        spinner.removeChangeListener(changeListener);
      }
		};
	}
}