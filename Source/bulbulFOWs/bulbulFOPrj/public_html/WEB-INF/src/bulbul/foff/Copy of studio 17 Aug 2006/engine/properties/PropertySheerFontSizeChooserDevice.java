package bulbul.foff.studio.engine.properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * 
 * @description 
 * @version 1.0 27-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheerFontSizeChooserDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheerFontSizeChooserDevice(SVGPropertyItem propertyItem) {
		super(propertyItem);
		buildComponent();
	}
	
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
		
		//the value of the property
		String propertyValue=propertyItem.getGeneralPropertyValue();
		propertyValue=propertyValue.replaceAll("[pt]","");
		propertyValue=propertyValue.replaceAll("\\s","");
			
		//the list of the items that will be displayed in the combo box
		LinkedList itemList=new LinkedList();
		String[] items={"6","7","8","9","10","11","12","13","14","15","16","18","20","22","24","26","28","32","36","40","44","48","54","60","66","72","80","88","96"};

		for(int i=0;i<items.length;i++){
		  itemList.add(items[i]);
		}
		
		if(! itemList.contains(propertyValue)){
		  itemList.add(propertyValue);
		}
		
		Collections.sort(itemList);

		//the combo box
		final JComboBox combo=new JComboBox(items);
		combo.setEditable(true);
		
		//sets the selected item
		combo.setSelectedItem(propertyValue);
		
		final ActionListener listener=new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String value="";

				if(combo.getSelectedItem()!=null){
					try{
						value=(String)combo.getSelectedItem();
					}catch (Exception ex){value="";}
				}
					
				//modifies the widgetValue of the property item
				if(value!=null && !value.equals("")){
					propertyItem.changePropertyValue(value.concat("pt"));
				}
			}		
		};
		
		//adds a listener to the combo box
		combo.addActionListener(listener);

		//the panel that will be contained in the widget object
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(combo);
		
		component=panel;

		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        combo.removeActionListener(listener);
      }
		};
	}
}