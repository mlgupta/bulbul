package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * 
 * @description 
 * @version 1.0 27-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetEntryDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetEntryDevice(SVGPropertyItem propertyItem) {
    super(propertyItem);
		buildComponent();
  }
  
  /**
   * 
   * @description 
   */
  protected void buildComponent(){
		
    //the text field in which the value will be entered
		final JTextField textField=new JTextField(propertyItem.getGeneralPropertyValue().trim());
		textField.moveCaretPosition(0);
		final CaretListener listener=new CaretListener(){
			public void caretUpdate(CaretEvent e) {
				//modifies the widgetValue of the property item
				propertyItem.changePropertyValue(textField.getText());
			}
		};
		//adds a listener to the text field
		textField.addCaretListener(listener);
		
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(textField);
		
		component=panel;

		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        textField.removeCaretListener(listener);
      }
		};
	}
}