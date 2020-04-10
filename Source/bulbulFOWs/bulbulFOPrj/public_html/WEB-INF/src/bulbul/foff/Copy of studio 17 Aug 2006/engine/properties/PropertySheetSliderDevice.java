package bulbul.foff.studio.engine.properties;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetSliderDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetSliderDevice(SVGPropertyItem propertyItem) {
		super(propertyItem);
		buildComponent();
	}
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
    //the panel that will contain the widgets
		final JPanel displayAndSlider=new JPanel();
			
		//the initial value
		int value=100;
			
		try{
      value=(int)(Double.parseDouble(propertyItem.getGeneralPropertyValue())*100);
    }catch (Exception ex){value=100;}

		final JSlider slider=new JSlider(0, 100, value);
    
		final JLabel displayedValue=new JLabel(value+" %");
    displayedValue.setPreferredSize(new Dimension(30, 20));	
		
		
		final MouseAdapter sliderListener=new MouseAdapter(){
			public void mouseReleased(MouseEvent evt) {
				//modifies the value of the property item
				propertyItem.changePropertyValue(format.format((double)(slider.getValue())/100));
			}
		};
		
		//adds a listener to the slider
		slider.addMouseListener(sliderListener);
		
		final ChangeListener sliderChangeListener=new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				displayedValue.setText(slider.getValue()+" %");
        propertyItem.changePropertyValue(format.format((double)(slider.getValue())/100));
			}
		};
		
		//adds a listener to the slider
		slider.addChangeListener(sliderChangeListener);

		displayAndSlider.setLayout(new BorderLayout(3, 0));
		displayAndSlider.add(displayedValue, BorderLayout.WEST);
    displayAndSlider.add(slider, BorderLayout.CENTER);
		
		
		component=displayAndSlider;

		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        slider.removeMouseListener(sliderListener);
        slider.removeChangeListener(sliderChangeListener);
      }
		};
	}
}