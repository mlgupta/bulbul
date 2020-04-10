package bulbul.foff.studio.engine.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * 
 * @description 
 * @version 1.0 24-Sep-2005
 * @author Sudheer V Pujar
 */
public abstract class SVGPopupMenuItem  {
  
  protected String id="";
  
  protected JMenuItem menuItem;
  
  protected String label="";
  
  protected Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGPopupMenuItem(Studio studio, String id, String label, Icon icon) {
    this.studio=studio;
		this.id=id;
		this.label=label;

    menuItem=new JMenuItem();
		menuItem.setText(label);
    
		if(icon!=null){
			menuItem.setIcon(icon);
		}
  }
  
  
  
  
  /**
   * 
   * @description 
   */
  public void setToInitialState(){
    ActionListener[] actionListeners=menuItem.getActionListeners();
		//removes all the action listeners from the menu item
		for(int i=0; i<actionListeners.length; i++){
			menuItem.removeActionListener(actionListeners[i]);
		}
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param nodes
   */
  public JMenuItem getPopupMenuItem(LinkedList nodes){
    //the listener the menuitem actions that hides the popup
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				getStudio().getPopupManager().hidePopup();
			}
		});

		return menuItem;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isEnabled(){
		return menuItem.isEnabled();
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getId() {
		return id;
	}


  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
}