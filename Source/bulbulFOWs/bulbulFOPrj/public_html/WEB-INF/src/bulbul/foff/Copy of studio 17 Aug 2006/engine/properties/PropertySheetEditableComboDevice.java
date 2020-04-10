package bulbul.foff.studio.engine.properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
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
public class PropertySheetEditableComboDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetEditableComboDevice(SVGPropertyItem propertyItem) {
    super(propertyItem);
		buildComponent();
	}
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
		LinkedList itemList=new LinkedList();
		PropertySheetComboItem item=null;
    PropertySheetComboItem selectedItem=null;
		String propertyValue=propertyItem.getGeneralPropertyValue();
		
		//builds the array of items for the combo
		for(Iterator it=propertyItem.getPropertyValuesMap().keySet().iterator(); it.hasNext();){
			try{
				String current=(String)it.next();
				String value=(String)propertyItem.getPropertyValuesMap().get(current);
				item=new PropertySheetComboItem(value, (String)propertyItem.getPropertyValuesLabelMap().get(current));
				//checks if the current possible value is equals to the current value of the property
				if(value!=null && propertyValue!=null && value.equals(propertyValue)){
				  selectedItem=item;
				}
				
			}catch (Exception ex){item=null;}
			
			if(item!=null){
			  itemList.add(item);
			}
		}
			
		if(selectedItem==null && propertyValue!=null){
			selectedItem=new PropertySheetComboItem(propertyValue, propertyValue);
			itemList.addFirst(selectedItem);
		}
		
		Object[] items=itemList.toArray();
		//the combo box
		final JComboBox combo=new JComboBox(items);
    combo.setEditable(true);
		
		//sets the selected item
		if(selectedItem!=null){
		  combo.setSelectedItem(selectedItem);
		}
			
		final ActionListener comboListener=new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String value="";

				if(combo.getSelectedItem()!=null){
					try{
						value=((PropertySheetComboItem)combo.getSelectedItem()).getValue();
					}catch (Exception ex){value="";}
						
					if(value==null || (value!=null && value.equals(""))){
						value=combo.getSelectedItem().toString();
					}
				}
				
				//modifies the widgetValue of the property item
				if(value!=null && !value.equals("")){
					propertyItem.changePropertyValue(value);
				}
			}
		};
			
		//adds a listener to the combo box
		combo.addActionListener(comboListener);
		
		//the panel that will be contained in the widget object
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(combo);
		
		component=panel;
			
		//creates the disposer
		disposer=new Runnable(){
      public void run() {
        combo.removeActionListener(comboListener);
      }
		};
	}
}