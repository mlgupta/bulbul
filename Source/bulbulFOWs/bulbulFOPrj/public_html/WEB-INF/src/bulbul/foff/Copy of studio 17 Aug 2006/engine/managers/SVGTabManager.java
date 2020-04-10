package bulbul.foff.studio.engine.managers;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @description 
 * @version 1.0 20-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGTabManager  {
  
  private Studio studio;
  private SVGTab currentSVGTab=null;
  private LinkedList svgTabChangedListeners=new LinkedList();
  private LinkedList svgTabs=new LinkedList();

  /**
   * 
   * @description 
   */
  public SVGTabManager(Studio studio) {
    this.studio=studio;
  }


  private int countName(String name){

		SVGTab current=null;
		int number=0;
		
		for(Iterator it=svgTabs.iterator(); it.hasNext();){
			current=((SVGTab)it.next());
			if(current.getName()!=null && current.getName().equals(name)){
			  number++;
			}
		}
		return number;
	}
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public synchronized SVGTab createSVGTab(String name,Image productImage){
    if(name!=null && ! name.equals("")){

			//checks if the name is correct
			//if another frame has the same name
			//a number is concatenated at the end of the name
			int count=countName(name);
      int number=0;
			
			while(count>0){
			    
				count=countName(name+" ("+new Integer(number+1)+")");
				number++;
			}
			
			if(number>0){
			  name+=" ("+new Integer(number)+")";
			}
			
			SVGTab svgTab=new SVGTab(studio,name,productImage);

			//adds the SVGFrame in the list
			svgTabs.addLast(svgTab);
			if(currentSVGTab!=null){
				//hides the current SVGFrame
				//currentSVGTab.moveToBack();         //check
			}
			
			currentSVGTab=svgTab;

      //check
			//svgTab.moveToFront();

			return svgTab;
		}
    return null;
  }
  
  /**
   * 
   * @description 
   * @param name
   */
  public void removeSVGTab(String name){
    SVGTab svgTab=getSVGTab(name);
    //svgTab.moveToBack(); //check
		//svgTab.removeFromDesktop(); //check
		svgTabs.remove(svgTab);

		currentSVGTab=null;
		
		//if it is not the last SVGFrame in the list, another SVGFrame is displayed
		if(svgTabs.size()>0){
	
  		setCurrentSVGTab(((SVGTab)svgTabs.getLast()).getName());
			
		}

		//notifies that the current frame has changed		
		svgTabChanged();
		svgTab.dispose();
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public SVGTab getSVGTab(String name){
    SVGTab current=null;
		for(Iterator it=svgTabs.iterator(); it.hasNext();){
			current=(SVGTab)it.next();
			if(current.getName()!=null && current.getName().equals(name)){
			  return current;
			}
		}
		return null;
  }
  
  
  
  /**
   * 
   * @description 
   * @param listener
   */
  public synchronized void addSVGTabChangedListener(ActionListener listener){
    svgTabChangedListeners.add(listener);
  }

  /**
   * 
   * @description 
   * @param listener
   */
  public synchronized void removeSVGTabChangedListener(ActionListener listener){
    svgTabChangedListeners.remove(listener);
  }
  
  
  /**
   * 
   * @description 
   */
  public synchronized void svgTabChanged(){
    ActionListener listener=null;
		
		for(Iterator it=svgTabChangedListeners.iterator(); it.hasNext();){

			try{
				listener=((ActionListener)it.next());
			}catch (Exception ex){listener=null;}
			
			if(listener!=null){
			    
				listener.actionPerformed(null);
			}
		}
  }
  
  public synchronized void changeName(String oldName, String newName){
		SVGTab svgTab=getSVGTab(oldName);
		
		if(newName!=null && ! newName.equals("")){
			//checks if the name is correct
			//if another frame has the same name,
			//a number is concatenated at the end of the name
			int count=countName(newName);
      int number=0;
			while(count>0){
				count=countName(newName+" ("+new Integer(number+1)+")");
				number++;
			}
			if(number>0){
			  newName=newName+" ("+new Integer(number)+")";
			}
			
			svgTab.setName(newName);
		}	
	}
  
  /**
   * 
   * @description 
   * @param name
   */
  public void setCurrentSVGTab(String name) {
    SVGTab svgTab=getSVGTab(name);
		
		if(svgTab!=null){
			
			if(currentSVGTab!=null){
			    
				//currentSVGTab.moveToBack(); //check
			}
			
			currentSVGTab=svgTab;
			svgTabs.remove(svgTab);
			svgTabs.addLast(svgTab);
			svgTab.setName(name); 
      //svgTab.moveToFront(); //check
			
			//notifies that the current frame has changed
			svgTabChanged();
		}
  }


  public int getSvgTabNumber(){
		return svgTabs.size();
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGTab getCurrentSVGTab() {
    return currentSVGTab;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public LinkedList getSvgTabs() {
    return svgTabs;
  }
  
}