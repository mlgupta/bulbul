package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * 
 * @description 
 * @version 1.0 27-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetValidatedEntryDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetValidatedEntryDevice(SVGPropertyItem propertyItem) {
		super(propertyItem);
		buildComponent();
	}
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
		
		//the text field in which the value will be entered
		final JTextField textField=new JTextField(propertyItem.getGeneralPropertyValue());
		textField.moveCaretPosition(0);
		
		//adds a key listener to the textfield
		final KeyAdapter keyListener=new KeyAdapter(){
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode()==KeyEvent.VK_ENTER){
					//modifies the widgetValue of the property item
					propertyItem.changePropertyValue(textField.getText());
				}
			}
		};
		
		textField.addKeyListener(keyListener);
			
		final JButton okButton=new JButton();
		okButton.setText("Ok");
    okButton.setMargin(new Insets(1, 1, 1, 1));

			
		final ActionListener listener=new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//modifies the widgetValue of the property item
				propertyItem.changePropertyValue(textField.getText());
			}
		};
			
		//adds a listener to the button
		okButton.addActionListener(listener);
		
		//creates the component that will be returned
		JPanel validatedPanel=new JPanel();

		validatedPanel.setLayout(new BorderLayout());
		validatedPanel.add(textField, BorderLayout.CENTER);
		validatedPanel.add(okButton, BorderLayout.EAST);
		
		component=validatedPanel;

		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        textField.removeKeyListener(keyListener);
        okButton.removeActionListener(listener);
      }
		};
	}
}