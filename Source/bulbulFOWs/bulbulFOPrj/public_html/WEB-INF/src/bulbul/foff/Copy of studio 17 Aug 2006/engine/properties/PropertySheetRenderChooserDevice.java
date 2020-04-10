package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 29-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetRenderChooserDevice extends SVGPropertyDevice {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetRenderChooserDevice(SVGPropertyItem propertyItem) {
    super(propertyItem);
		buildComponent();
  }
  
  /**
   * 
   * @description 
   */
  protected void buildComponent(){
    final Studio studio=propertyItem.getPropertySheet().getStudio();

		//the color represented by this string (if this string represents a color)
		Color color=studio.getColorChooser().getColor(propertyItem.getGeneralPropertyValue());
		
		final String label="Color";
    final String noneLabel="None";
    final String labelColorPicker="Color Picker";

		//the icons
		final ImageIcon 	colorPickerIcon=studio.getSvgResource().getIcon("colorPicker.png");
		
		//creates the panel that will contain the widgets
		final JPanel panel=new JPanel();
			
		//creates the color chooser//
			
		//the button used to preview the color
		final JButton previewColor=new JButton();
    Insets buttonInsets=new Insets(0, 0, 0, 0);
    previewColor.setMargin(buttonInsets);
    previewColor.setPreferredSize(new Dimension(22, 22));
		previewColor.setBorder(new LineBorder(new Color(247,161,90),1));
    
		final JPanel colorPanel=new JPanel();
		colorPanel.setPreferredSize(new Dimension(18, 18));
		colorPanel.setBackground(color==null?Color.BLACK:color);
		colorPanel.setBorder(new LineBorder(new Color(255,255,255,255), 1));
		previewColor.add(colorPanel);
		
		final JToggleButton colorPickerButton=new JToggleButton(colorPickerIcon);
		colorPickerButton.setMargin(new Insets(0,0,0,0));
		colorPickerButton.setToolTipText(labelColorPicker);
		colorPickerButton.setPreferredSize(new Dimension(22, 22));
    colorPickerButton.setBorder(new LineBorder(new Color(247,161,90),1));

		previewColor.setEnabled(true);
    colorPickerButton.setEnabled(true);
    colorPickerButton.setIcon(colorPickerIcon);
		
		//the listener to the preview color button
		final ActionListener previewColorListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
        //the listener to the ok button
        ActionListener okListener=new ActionListener(){
          public void actionPerformed(ActionEvent evt) {
            Color color=studio.getColorChooser().getColor();
            
            if(color!=null){
              String newColor=studio.getColorChooser().getColorString(color);
              propertyItem.changePropertyValue(newColor);
              colorPanel.setBackground(color);
              studio.getSvgColorManager().setCurrentColor(color);
            }
          }
        };
                
        //the dialog displaying a color chooser
        JDialog dialog=JColorChooser.createDialog(studio, "", true, studio.getColorChooser(), okListener, null);
        dialog.setVisible(true);
			}
		};
			
		previewColor.addActionListener(previewColorListener);
		
		//the listener to the color picker button
		final ActionListener colorPickerListener=new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        studio.cancelActions(false);
        
        //the listener to the events in the application
        AWTEventListener listener=new AWTEventListener(){
          public void eventDispatched(AWTEvent evt) {
            if(evt instanceof MouseEvent){
              MouseEvent mevt=(MouseEvent)evt;
              mevt.consume();
              Point point=mevt.getPoint();
              if(mevt.getID()==MouseEvent.MOUSE_PRESSED){
                //converting the point
                SwingUtilities.convertPointToScreen(point, (Component)mevt.getSource());
                
                //getting the color at the clicked point
                Color color=studio.getSvgToolkit().pickColor(point);
                
                if(color!=null){
                  //setting the new color value
                  String newColor=studio.getColorChooser().getColorString(color);
                  propertyItem.changePropertyValue(newColor);
                  colorPanel.setBackground(color);
                  studio.getSvgColorManager().setCurrentColor(color);
                }
              }else if(mevt.getID()==MouseEvent.MOUSE_RELEASED){
                //remove this listener and set the default state of the editor
                try {
                  studio.getToolkit().removeAWTEventListener(this);
                }catch (Exception e) {}
                colorPickerButton.setSelected(false);
                studio.cancelActions(true);
              }
            }
          } 
        };
        //adding the listener
        try {
          studio.getToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
        }catch (Exception e) {}
      }
		};
		
		//adds the listener to the colorPicker button
		colorPickerButton.addActionListener(colorPickerListener);
		//sets the layout
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,1,0));
		panel.add(previewColor);
    panel.add(colorPickerButton);
		
		
		component=panel;

		//creating the runnable to dipose the widget
		disposer=new Runnable(){
      public void run() {
        //removes the listeners//
        previewColor.removeActionListener(previewColorListener);
        colorPickerButton.removeActionListener(colorPickerListener);
      }
		};
  }
}