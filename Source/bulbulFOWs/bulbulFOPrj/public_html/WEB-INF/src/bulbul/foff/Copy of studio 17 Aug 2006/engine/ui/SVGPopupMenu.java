package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.selection.SVGSelection;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGPopupMenu  {
  
  
  private final String idtoolbar="Popup";
	
  private final LinkedHashMap popupItemsDescription=new LinkedHashMap();
	
  private final HashMap popupItems=new HashMap();
	
  private JPopupMenu popupMenu=new JPopupMenu();
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGPopupMenu(Studio studio) {
    this.studio=studio;
    //adding a svgTab listener
    getStudio().getSvgTabManager().addSVGTabChangedListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt) {
        SVGPopupMenuItem popupItem=null;
        
        //restores the initial state of each popup Menu item
        for(Iterator it=popupItems.values().iterator(); it.hasNext();){
          popupItem=(SVGPopupMenuItem)it.next();
          
          if(popupItem!=null){
            popupItem.setToInitialState();
          }
        }
      }
		});
    
    //creating the listener to the popup menu
    popupMenu.addPopupMenuListener(new PopupMenuListener(){
			public void popupMenuCanceled(PopupMenuEvent evt) {
        hidePopup();
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent evt) {
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent evt) {
			}
		});
    //gets the order of the popup items from a xml file
    addPopupMenuItems();
		
    //gets the popup items from each module
    retrieveClassObjectPopupMenuItems();
    
  }

  /**
	 * retrieves all the popup items from the modules
	 */
	private void retrieveClassObjectPopupMenuItems(){

		//getting all the loaded modules
		Collection classObjects=getStudio().getSVGClassLoader().getClassObjects();
		
		SVGClassObject classObject=null;
		Collection itemsList=null;
		SVGPopupMenuItem item=null;
		Iterator it, it2;
		
		//for each module, gets the popup items linked with the given list of nodes
		for(it=classObjects.iterator(); it.hasNext();){
			classObject=(SVGClassObject)it.next();
			if(classObject!=null){
				//getting the list of the popup items of each module
				itemsList=classObject.getPopupMenuItems();
        if(itemsList!=null){
          for(it2=itemsList.iterator(); it2.hasNext();){
            item=(SVGPopupMenuItem)it2.next();
            if(item!=null){
              popupItems.put(item.getId(), item);
            }
          }
        }
			}
		}
	}
  
  /**
   * 
   * @description 
   */
	private void restorePopupItemsInitialState(){
		SVGPopupMenuItem item=null;
    for(Iterator it=popupItems.values().iterator(); it.hasNext();){
      item=(SVGPopupMenuItem)it.next();
      if(item!=null){
        item.setToInitialState();
      }
    }
	}
  
  /**
   * 
   * @description 
   */
  private void addPopupMenuItems(){
    
    String groupName="";
    String itemName="";
    LinkedList itemList=null;
    
    
    // Undo Redo
    groupName="undoRedo";
    itemList=new LinkedList();
    itemName="Undo";
    itemList.add(itemName);
    itemName="Redo";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    // Select
    groupName="select";
    itemList=new LinkedList();
    itemName="selectAll";
    itemList.add(itemName);
    itemName="DeselectAll";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    //Group
    groupName="group";
    itemList=new LinkedList();
    itemName="Group";
    itemList.add(itemName);
    itemName="UnGroup";
    itemList.add(itemName);
    itemName="GroupMenu";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    //Lock
    groupName="lock";
    itemList=new LinkedList();
    itemName="Lock";
    itemList.add(itemName);
    itemName="UnLock";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    //Transform
    groupName="transform";
    itemList=new LinkedList();
    itemName="Align";
    itemList.add(itemName);
    itemName="Same";
    itemList.add(itemName);
    itemName="Spacing";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    //Transform2
    groupName="transform2";
    itemList=new LinkedList();
    itemName="Center";
    itemList.add(itemName);
    itemName="Order";
    itemList.add(itemName);
    itemName="Rotate";
    itemList.add(itemName);
    itemName="Flip";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
    //Zoom
    groupName="zoom";
    itemList=new LinkedList();
    itemName="Zoom +";
    itemList.add(itemName);
    itemName="Zoom -";
    itemList.add(itemName);
    itemName="Zoom 1:1";
    itemList.add(itemName);
    itemName="Zoom";
    itemList.add(itemName);
    popupItemsDescription.put(groupName, itemList);
    
	}
  
  /**
   * 
   * @description 
   * @param location
   * @param svgTab
   */
  public void showPopup(SVGTab svgTab, Point location){
    if(svgTab!=null && location!=null && popupMenu!=null){
      SVGSelection selection=getStudio().getSvgSelection();
	        
	    if(selection!=null){

        LinkedList selectedNodes=selection.getCurrentSelection(svgTab);
  
        if(popupItems!=null && popupItems.size()>0){
          JMenuItem menuItem=null;
          SVGPopupMenuItem popupMenuItem=null;
          String itemName="";
          Collection itemNames=null;
          boolean hasAddedAnItem=false;
          boolean isGroupEnabled=false;
          LinkedList popupItemsToAdd=null;
          Iterator it2=null;
          
          //for each group of menu items, fills the pop up menu and put popup separators between each group of them
          for(Iterator it=popupItemsDescription.values().iterator(); it.hasNext();){
          
            try{
              itemNames=(Collection)it.next();
            }catch (Exception ex){itemNames=null;}
            
            if(itemNames!=null){
              hasAddedAnItem=false;
              isGroupEnabled=false;
              popupItemsToAdd=new LinkedList();
              
              for(it2=itemNames.iterator(); it2.hasNext();){
                try{
                    itemName=(String)it2.next();
                }catch (Exception ex){itemName=null;}
                
                //if the name of the menuitem of the popup model is contained 
                //in the list of the names of the menuitems that should be displayed
                if(itemName!=null && popupItems.containsKey(itemName)){
                  popupMenuItem=(SVGPopupMenuItem)popupItems.get(itemName);
                  
                  if(popupMenuItem!=null){
                    menuItem=popupMenuItem.getPopupMenuItem(selectedNodes);
                    
                    if(menuItem!=null){
                      //the menu item is added to a list
                      popupItemsToAdd.add(menuItem);
                      hasAddedAnItem=true;
                      isGroupEnabled=isGroupEnabled || popupMenuItem.isEnabled();
                    }
                  }
                }
              }
              if(isGroupEnabled){
                for(it2=popupItemsToAdd.iterator(); it2.hasNext();){
                  try{
                    menuItem=(JMenuItem)it2.next();
                  }catch (Exception ex){menuItem=null;}
                    
                  if(menuItem!=null){
                    //the menu item is added to the popup menu
                    popupMenu.add(menuItem);
                  }
                }
                
                popupItemsToAdd.clear();
                
                if(hasAddedAnItem && it.hasNext()){
                  //if other groups of menu items could be added and if menu items 
                  //from the previous group have been added, a new separator is added
                  popupMenu.addSeparator();
                }
              }
            }
          }
          popupMenu.show(svgTab.getScrollPane().getSvgCanvas(), location.x, location.y);
        }
	    }
	  }
	}
  
  /**
   * 
   * @description 
   */
  public void hidePopup(){
    popupMenu.removeAll();
    restorePopupItemsInitialState();
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