package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 * 
 * @description 
 * @version 1.0 31-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGRulers  implements SVGClassObject {
  
  private Studio studio;
  /**
   * 
   * @description 
   */
  public SVGRulers(Studio studio) {
    this.studio=studio;
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			
			/**
			 * called when an event occurs
			 */
			public void actionPerformed(ActionEvent e) {
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
          getStudio().getMainToolBar().getMtbRulers().setEnabled(true);
          getStudio().getMainToolBar().getMtbRulers().setSelected(svgTab.getScrollPane().areRulersEnabled()); 
				}else{
				  getStudio().getMainToolBar().getMtbRulers().setEnabled(true);  					
				}
			}	
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    getStudio().getMainToolBar().getMtbRulers().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evt) {
				
				SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
				  if(!svgTab.getScrollPane().areRulersEnabled()){
						svgTab.getScrollPane().setRulersEnabled(true);
					}else{
						svgTab.getScrollPane().setRulersEnabled(false) ;
						svgTab.getScrollPane().setAlignWithRulersEnabled(false);
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
  
  /**
   * 
   * @description 
   * @return 
   */
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