package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetFontChooserDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetFontChooserDevice(SVGPropertyItem propertyItem) {
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
			
		//builds the array of Integer that will be inserted into the combo box
		Integer[] obj=new Integer[fontList.size()];
		
		for(int i=0;i<fontList.size();i++){
			obj[i]=new Integer(i);
		}
			
		//creates the combo box
		final JComboBox combo=new JComboBox(obj);
			
		//creates and sets the renderer
		FontFamilyRenderer renderer=new FontFamilyRenderer();
		combo.setRenderer(renderer);

		//sets the selected item
		combo.setSelectedItem(fontFamilyList.contains(propertyValue)?new Integer(fontFamilyList.indexOf(propertyValue)):new Integer(0));
			
		final ActionListener listener=new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String value="";
				if(combo.getSelectedItem()!=null){
					try{
						value=(String)(fontFamilyList.get(((Integer)combo.getSelectedItem()).intValue()));
					}catch (Exception ex){value="";}
				}
						
				//modifies the widgetValue of the property item
				if(value!=null && !value.equals("")){
					propertyItem.changePropertyValue(value);
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
	
	
  /**
   * 
   * @description 
   * @version 1.0 27-Sep-2005
   * @author Sudheer V Pujar
   */
	protected class FontFamilyRenderer extends JLabel implements ListCellRenderer {
		
    /**
     * 
     * @description 
     */
		 protected FontFamilyRenderer() {
			 setOpaque(true);
			 setHorizontalAlignment(LEFT);
			 setVerticalAlignment(CENTER);
		 }

		 
    /**
     * 
     * @description 
     * @return 
     * @param cellHasFocus
     * @param isSelected
     * @param index
     * @param value
     * @param list
     */
		 public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			//gets the selected index
			int indexLocal= ((Integer)value).intValue();
			setBackground(list.getBackground());
			setForeground(list.getForeground());
			setText((String)fontFamilyList.get(indexLocal));
			setFont((Font)fontList.get(indexLocal));

			return this;
		 }
	}
}