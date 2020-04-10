package bulbul.foff.studio.engine.ui;
import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.apache.batik.util.RunnableQueue;


/**
 * 
 * @description 
 * @version 1.0 17-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGTab extends JPanel  {
  
  private SVGApplet studio;
  private SVGScrollPane scrollPane;
  private SVGTab svgTab = this;
  
  private LinkedList disposeRunnables=new LinkedList();
  
  private String name;
  
  private boolean modified;
  
  /**
   * 
   * @param studio
   * @param name
   * @description 
   */
  public SVGTab(SVGApplet studio,String name,Image productImage) {
    super(); 
    this.studio=studio;
    setLayout(new BorderLayout());
    scrollPane=new SVGScrollPane(studio,this,productImage);
		add(scrollPane, BorderLayout.CENTER);
    getStudio().getSvgTabSet().add(name,this);
    setName(name);
    setBorder(BorderFactory.createEtchedBorder());
  }

  /**
   * 
   * @description 
   * @param runnable
   */
  public void enqueue(Runnable runnable){
    if(runnable!=null && getScrollPane().getSvgCanvas().getUpdateManager()!=null){
      final RunnableQueue queue=getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			if(queue.getThread()!=Thread.currentThread()){
				queue.invokeLater(runnable);
			}else{
				runnable.run();
			}
	  }
  }
  /**
   * 
   * @description 
   * @param runnable
   */
  public void addDisposeRunnable(Runnable runnable){
    if(runnable!=null){
        disposeRunnables.add(runnable);
    }
  }
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isSelected(){
    SVGTab currentSvgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		if(currentSvgTab!=null && currentSvgTab.equals(this)){
			return true;
		}
		return false;
  }
  
  /**
   * 
   * @description 
   */
  public void dispose(){
    //run the dispose runnables
		Runnable runnable=null;
		for(Iterator it=disposeRunnables.iterator(); it.hasNext();){
		    try{
		        runnable=(Runnable)it.next();
		    }catch (Exception ex){runnable=null;}

			if(runnable!=null){
			    
				runnable.run();
			}
		}

		disposeRunnables.clear();

		svgTab.removeAll();
		scrollPane.removeAll();
  }
  
  protected void setSVGTabLabel(String name){
		String label="";
		if( name!=null && ! name.equals("")){
			label=name;	
		}
		if(modified){
			label=label.concat("*");
		}
		getStudio().getMainStatusBar().setName(label);
	}

  /**
   * 
   * @return 
   * @description 
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @param name
   * @description 
   */
  public void setName(String name) {
    this.name = name;
    setSVGTabLabel(name);
  }

  /**
   * 
   * @return 
   * @description 
   */
  public boolean isModified() {
    return modified;
  }

  /**
   * 
   * @param modified
   * @description 
   */
  public void setModified(boolean modified) {
    this.modified = modified;
    setSVGTabLabel(name);
  }

  /**
   * 
   * @return 
   * @description 
   */
  public SVGScrollPane getScrollPane() {
    return scrollPane;
  }

  /**
   * 
   * @return 
   * @description 
   */
  public SVGApplet getStudio() {
    return studio;
  }
  
}