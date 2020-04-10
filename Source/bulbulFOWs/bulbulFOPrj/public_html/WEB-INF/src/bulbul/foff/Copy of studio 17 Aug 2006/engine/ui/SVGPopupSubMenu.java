package bulbul.foff.studio.engine.ui;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @description 
 * @version 1.0 25-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGPopupSubMenu  extends SVGPopupMenuItem {
  
  private LinkedList popupSubMenuItem=new LinkedList();
  private boolean isEnabled=false;
  /**
   * 
   * @description 
   */
  public SVGPopupSubMenu(Studio studio, String id, String label, Icon icon) {
    super(studio, id, label, icon);
    menuItem=new JMenu(label);
    if(icon!=null){
			menuItem.setIcon(icon);
		}
  }
  
  public JMenuItem getPopupMenuItem(LinkedList nodes){
		//removes all the items  from the menu
		((JMenu)menuItem).removeAll();
		
		//adding all the menu items that lie in this sub menu
		SVGPopupMenuItem item=null;
		JMenuItem popupMenuItem=null;
		isEnabled=false;
		
		for(Iterator it=popupSubMenuItem.iterator(); it.hasNext();){
			item=(SVGPopupMenuItem)it.next();
			if(item!=null){
				popupMenuItem=item.getPopupMenuItem(nodes);
				if(popupMenuItem!=null){
					((JMenu)menuItem).add(popupMenuItem);
					isEnabled=isEnabled || popupMenuItem.isEnabled();
				}
			}
		}
		return menuItem;
	}
	
	/**
	 * adds a popup item to this popup sub menu item
	 * @param item a popup item
	 */
	public void addPopupMenuItem(SVGPopupMenuItem item){
		if(item!=null){
			popupSubMenuItem.add(item);
		}
	}
	
	/**
	 * restores the popup items initial state
	 */
	public void setToInitialState() {
		SVGPopupMenuItem item=null;
		for(Iterator it=popupSubMenuItem.iterator(); it.hasNext();){
			item=(SVGPopupMenuItem)it.next();
			if(item!=null){
				item.setToInitialState();
			}
		}
	}
	
	public boolean isEnabled(){
		return isEnabled;
	}
}