package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 * 
 * @description 
 * @version 1.0 dd-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGGrid implements SVGClassObject {
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public SVGGrid(Studio studio) {
    this.studio=studio;
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			
			/**
			 * called when an event occurs
			 */
			public void actionPerformed(ActionEvent e) {
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
          getStudio().getMainToolBar().getMtbGrid().setEnabled(true);
          getStudio().getMainToolBar().getMtbGrid().setSelected(svgTab.getScrollPane().isGridVisible()); 
				}else{
				  getStudio().getMainToolBar().getMtbGrid().setEnabled(false);				
				}
			}	
		};
    
    //adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
     getStudio().getMainToolBar().getMtbGrid().addActionListener(new ActionListener(){

			/**
			 * the method called when the action is done
			 */
			public void actionPerformed(ActionEvent evt) {
			    
				SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
				    
					if(! svgTab.getScrollPane().isGridVisible()){
						svgTab.getScrollPane().setGridVisible(true);
					}else{
						svgTab.getScrollPane().setGridVisible(false);
					}
				}
			}
		});
  }


  /**
   * 
   * @description 
   */
  public void cancelActions(){
	}
  public Collection getPopupMenuItems(){
		return null;
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