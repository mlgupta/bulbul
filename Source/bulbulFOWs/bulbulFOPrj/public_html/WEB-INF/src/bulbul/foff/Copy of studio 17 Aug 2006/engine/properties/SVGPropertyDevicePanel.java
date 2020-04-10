package bulbul.foff.studio.engine.properties;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGPropertyDevicePanel extends JPanel  {
  
		private LinkedHashMap tabMap;
		private LinkedHashMap propertyItemsMap;
		
    private LinkedList devices=new LinkedList();
		
		private ChangeListener changeListener=null;

		private JTabbedPane tabPanel=null;
		
    private SVGPropertySheet propertySheet=null;
		private SVGPropertyDevicePanel devicePanel=this;
    
  /**
   * 
   * @description 
   */
  public SVGPropertyDevicePanel(SVGPropertySheet propertySheet, LinkedHashMap tabMap, LinkedHashMap propertyItemsMap) {
    this.propertySheet=propertySheet;
    this.tabMap=tabMap;
    this.propertyItemsMap=propertyItemsMap;
    
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    tabPanel=getTabbedPane();
    if(tabPanel!=null){
      //setting the listener for the changing of the tabs
      changeListener=new ChangeListener(){
        public void stateChanged(ChangeEvent arg0) {
          if(tabPanel!=null){
            devicePanel.propertySheet.setPropertyTabId(tabPanel.getTitleAt(tabPanel.getSelectedIndex()));
          }
        }
      };
      tabPanel.addChangeListener(changeListener);
      setSelectedTab(propertySheet.getPropertyTabId());
      add(tabPanel);
    }
    setBorder(new EmptyBorder(0, 0, 0, 0));
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  protected JTabbedPane getTabbedPane(){
    if(propertyItemsMap!=null && propertyItemsMap.size()>0 && tabMap!=null){
      //the tabbed pane
      JTabbedPane tabbedPane=new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

      String current=null;
      String label=null;
      LinkedList list=null;
      JPanel panel=null;
      JPanel tpanel=null;

      //for each tab
      for(Iterator it=new LinkedList(propertyItemsMap.keySet()).iterator(); it.hasNext();){
        try{
          //gets the name and the label of the tab, and the list of the property items linked with it
          current=(String)it.next();
          list=(LinkedList)propertyItemsMap.get(current);
          label=(String)tabMap.get(current);
        }catch (Exception ex){current=null; list=null; label=null;}
        
        if(label==null || (label!=null && label.equals(""))){
          label=current;
        }
        
        //creates the panel for the current tab and adds it to the tabbed pane
        if(current!=null && list!=null && label!=null){
          panel=getTabPanel(list);
          if(panel!=null){
            tpanel=new JPanel();
            tpanel.setLayout(new BorderLayout());
            tpanel.add(panel, BorderLayout.NORTH);
            tpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            tabbedPane.add(label, tpanel);
          }
        }
      }
      return tabbedPane;
    }
    return null;
  }
  
  protected JPanel getTabPanel(LinkedList propertyItemsList){
    if(propertyItemsList!=null){
      //the panel linked with a tab
      JPanel tabPanel=new JPanel();

      tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
      
      SVGPropertyItem current=null;
      SVGPropertyDevice device=null;
      JPanel container=null;
      
      //for each property item, the linked panel is added to the tab panel
      for(Iterator it=propertyItemsList.iterator(); it.hasNext();){
        try{
          current=(SVGPropertyItem)it.next();
        }catch (Exception ex){current=null;}
        
        if(current!=null){
          device=getPropertyComponents(current);
          
          if(device!=null){
            devices.add(device);
          }
          
          if(device!=null && device.getComponent()!=null && device.getLabel()!=null){
            container=new JPanel();
            container.setLayout(new GridBagLayout());
            container.setBorder(new EmptyBorder(2,1,2,1));
            
            
            Dimension labelSize=new Dimension(87,22);
            JLabel label= new JLabel();
            label.setPreferredSize(labelSize);
            label.setMaximumSize(labelSize);
            label.setMinimumSize(labelSize);
            label.setSize(labelSize);
            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setText(device.getLabel());
            label.setToolTipText(device.getLabel());
            
            Dimension deviceSize=new Dimension(147,24);
            device.getComponent().setBorder(new EmptyBorder(1,1,1,1));
            device.getComponent().setPreferredSize(deviceSize);
            device.getComponent().setMaximumSize(deviceSize);
            device.getComponent().setMinimumSize(deviceSize);
            device.getComponent().setSize(deviceSize);


            container.add(label,new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1,1,1,3), 0, 0));
            container.add(device.getComponent(),new GridBagConstraints(1, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 3), 0, 0));

            tabPanel.add(container);

            JSeparator containerSeparator=new JSeparator(JSeparator.HORIZONTAL);
            tabPanel.add(containerSeparator);
          }
        }
      }
      return tabPanel;
    }
    return null;
  }
  
  protected SVGPropertyDevice getPropertyComponents(SVGPropertyItem propertyItem){
    //the object that contains the widget and the label to be displayed
    SVGPropertyDevice device=null;

    if(propertyItem!=null){
      //the type of the widget that will be used
      String valueType=propertyItem.getPropertyValueType();
      if(valueType!=null && ! valueType.equals("")){
        if(valueType.equals("renderchooser")){                  //a color chooser device 
          device=new PropertySheetRenderChooserDevice(propertyItem);
        }else if(valueType.equals("combo")){                    //a combo box device
          device=new PropertySheetComboDevice(propertyItem);
        }else if(valueType.equals("editablecombo")){            //a combo box device
          device=new PropertySheetEditableComboDevice(propertyItem);
        }else if(valueType.equals("markerchooser")){            //a combo box device
          //device=new SVGPropertiesMarkerChooserWidget(propertyItem);
        }else if(valueType.equals("slider")){                   //a slider device
          device=new PropertySheetSliderDevice(propertyItem);
        }else if(valueType.equals("tworadiobuttons")){          //a radio button device
          //device=new PropertySheetTwoRadioButtonsDevice(propertyItem);
        }else if(valueType.equals("matrix")){                   //a panel that displays several widgets enabling to change values of the matrix transform
          //device=new SVGPropertiesMatrixWidget(propertyItem);
        }else if(valueType.equals("entry")){                    //an entry device         
          device=new PropertySheetEntryDevice(propertyItem);
        }else if(valueType.equals("validatedentry")){           //an entry widget with an ok button
          //device=new PropertySheetValidatedEntryDevice(propertyItem);
        }else if(valueType.equals("idmodifier")){               //an entry widget with an ok button to modify an id
          //device=new SVGPropertiesIdModifierWidget(propertyItem);
        }else if(valueType.equals("fontchooser")){              //a widget enabling to choose a font family
          device=new PropertySheetFontChooserDevice(propertyItem);
        }else if(valueType.equals("fontsizechooser")){          //a widget used to choose font size
          device=new PropertySheerFontSizeChooserDevice(propertyItem);
        }else if(valueType.equals("positivenumberchooser") || valueType.equals("numberchooser")){
          device=new PropertySheetNumberChooserDevice(propertyItem);
        }
      }
    }
    return device;
  }

  
  /**
   * 
   * @description 
   * @param name
   */
  protected void setSelectedTab(String name){
    if(tabPanel!=null){
      for(int i=0;i<tabPanel.getTabCount();i++){
        if(name!=null && name.equals(tabPanel.getTitleAt(i))){
          tabPanel.setSelectedIndex(i);
        }
      }
		}
  }

  /**
   * 
   * @description 
   */
  public void dispose(){
    if(tabPanel!=null && changeListener!=null){
      tabPanel.removeChangeListener(changeListener);
      SVGPropertyDevice device=null;
      
      //disposes the widgets
      for(Iterator it=devices.iterator(); it.hasNext();){
        try{
          device=(SVGPropertyDevice)it.next();
        }catch (Exception ex){}
        if(device!=null){
          device.dispose();
        }
      }

      //clears the maps
      tabMap.clear();
      propertyItemsMap.clear();
      devices.clear();
      tabPanel.removeAll();
      tabPanel=null;
    }
  }
  
}