package bulbul.foff.studio.engine.properties;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class PropertySheetTwoRadioButtonsDevice extends SVGPropertyDevice  {
  /**
   * 
   * @description 
   * @param propertyItem
   */
  public PropertySheetTwoRadioButtonsDevice(SVGPropertyItem propertyItem) {
		super(propertyItem);
		buildComponent();
	}
	
  /**
   * 
   * @description 
   */
	protected void buildComponent(){
		
		//the panel that contains the radio buttons
		final JPanel radioPanel=new JPanel();
		final ButtonGroup group=new ButtonGroup();

		//gets the initial value of the property
		String value=propertyItem.getGeneralPropertyValue();
			
		if(propertyItem.getPropertyValuesMap().size()==2){

			//finds the labels and values for the radio buttons
			final String[] values=new String[2];
			final String[] labels=new String[2];
			String key="";
			int index=0;
			
			for(Iterator it=propertyItem.getPropertyValuesMap().keySet().iterator(); it.hasNext();){
				try{
					key=(String)it.next();
					values[index]=(String)propertyItem.getPropertyValuesMap().get(key);
					labels[index]=(String)propertyItem.getPropertyValuesLabelMap().get(key);
					index++;
				}catch (Exception ex){break;}
			}

			if(values[0]!=null && labels[0]!=null && values[1]!=null && labels[1]!=null){
			    
				//creates the radio buttons
				final JRadioButton buttons[]=new JRadioButton[2];
				final ActionListener[] actionListeners=new ActionListener[2];
				
				for (int i=0;i<2;i++){

					buttons[i]=new JRadioButton(labels[i]);
          group.add(buttons[i]);
					
					if(value!=null && values[i].equals(value)){
					  group.setSelected(buttons[i].getModel(), true);
					}
						
					final int finalI=i;
						
					actionListeners[i]=new ActionListener(){
						public void actionPerformed(ActionEvent evt) {
							group.setSelected(buttons[finalI].getModel(), true);
							propertyItem.changePropertyValue(values[finalI]);
						}
					};

					//adds the listeners
					buttons[i].addActionListener(actionListeners[i]);
				}
					
				//creates the component that will be returned
				radioPanel.setLayout(new GridLayout(1, buttons.length));
				
				for(int i=0;i<2;i++){
          radioPanel.add(buttons[i]);
				}
				
				component=radioPanel;

				//creates the disposer
				disposer=new Runnable(){
          public void run() {
            for(int i=0;i<2;i++){
              buttons[i].removeActionListener(actionListeners[i]);
            }
          }
				};
			}
		}
	}
}