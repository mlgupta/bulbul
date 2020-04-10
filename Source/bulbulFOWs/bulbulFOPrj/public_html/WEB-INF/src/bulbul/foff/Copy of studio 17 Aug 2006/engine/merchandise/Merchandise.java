package bulbul.foff.studio.engine.merchandise;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JPanel;


/**
 * 
 * @description 
 * @version 1.0 dd-Oct-2005
 * @author Sudheer V. Pujar
 */
public class Merchandise implements SVGClassObject  {
  
  private final String  idMerchandise="Merchandise";
  
  private Studio studio;
  
  private final bulbul.foff.studio.engine.ui.Merchandise merchandisePanel; 
  
  private JPanel merchandiseFrame=null;
  
  /**
   * 
   * @description 
   */
  public Merchandise(Studio studio) {
    this.studio=studio;
    
    merchandiseFrame= getStudio().getPanel4Merchandise();
    merchandiseFrame.setLayout(new BorderLayout());
    merchandisePanel=new bulbul.foff.studio.engine.ui.Merchandise(studio);
    merchandiseFrame.add(merchandisePanel,BorderLayout.CENTER);


    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				if(svgTab!=null){
          merchandisePanel.initTree();
          merchandisePanel.setTreeInitialized(true);
        }else{
          
				}
			}	
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
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
   */
  public void cancelActions(){
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
   return idMerchandise;
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