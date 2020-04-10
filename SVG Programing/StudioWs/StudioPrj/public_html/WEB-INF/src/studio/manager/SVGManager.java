package studio.manager;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import studio.canvas.SVGScrollPane;

public class SVGManager  {
/**
	 * the list of the listeners on the changes of the current SVGFrame
	 */
	private LinkedList svgChangedListeners=new LinkedList();
  
  private SVGScrollPane scrollPane;
  public SVGManager(SVGScrollPane scrollPane) {
    this.scrollPane=scrollPane;
  
  }
  
  public SVGScrollPane getScrollPane(){
    return this.scrollPane;
  } 
  
  /**
	 * adds a listener called when the current SVG changes
	 * @param listener the listener to be added
	 */
	public synchronized void addSVGChangedListener(ActionListener listener){
		svgChangedListeners.add(listener);
	}
	
	/**
	 * removes a listener called when the current SVGFrame changes
	 * @param listener the listener to be removed
	 */
	public synchronized void removeSVGChangedListener(ActionListener listener){
		svgChangedListeners.remove(listener);
	}
  
  /**
	 * notifies the listeners when the SVG is changed
	 */
	public synchronized void svgChanged(){
		
		Iterator it=svgChangedListeners.iterator();
		ActionListener listener=null;
		
		while(it.hasNext()){
			
			listener=null;
			try{
				listener=((ActionListener)it.next());
			}catch (Exception ex){}
			
			if(listener!=null){
				listener.actionPerformed(null);
			}
		}
	}
}