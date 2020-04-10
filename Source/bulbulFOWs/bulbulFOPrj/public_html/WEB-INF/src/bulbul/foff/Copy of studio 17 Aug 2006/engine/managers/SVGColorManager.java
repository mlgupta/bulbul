package bulbul.foff.studio.engine.managers;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @description 
 * @version 1.0 29-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGColorManager  {
  
  private Color currentColor=new Color(170, 170, 170);
	
  private LinkedList colorListeners=new LinkedList();
    
	private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGColorManager(Studio studio) {
    this.studio=studio;
  }



  /**
   * 
   * @description 
   * @param colorListener
   */
	public synchronized void addColorListener(ColorListener colorListener){
    if(colorListener!=null){
      colorListeners.add(colorListener);
    }
	}
	
	
  /**
   * 
   * @description 
   * @param colorListener
   */
	public synchronized void removeColorListener(ColorListener colorListener){
    if(colorListener!=null){
      colorListeners.remove(colorListener);
    }
	}

  /**
   * 
   * @description 
   * @param currentColor
   */
  public void setCurrentColor(Color currentColor) {
    if(currentColor!=null){
			this.currentColor=currentColor;

			//notifies that the current color has changed
			ColorListener colorListener=null;
			
			for(Iterator it=colorListeners.iterator(); it.hasNext();){
        try{
          colorListener=(ColorListener)it.next();
        }catch(Exception ex){colorListener=null;}
        
        if(colorListener!=null){
          colorListener.colorChanged(currentColor);
        }
			}
	  }
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Color getCurrentColor() {
    return currentColor;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
  
  /**
   * 
   * @description 
   * @version 1.0 29-Sep-2005
   * @author Sudheer V Pujar
   */
  public interface ColorListener{
	  
    /**
     * 
     * @description 
     * @param color
     */
    public void colorChanged(Color color);
	}
  
}