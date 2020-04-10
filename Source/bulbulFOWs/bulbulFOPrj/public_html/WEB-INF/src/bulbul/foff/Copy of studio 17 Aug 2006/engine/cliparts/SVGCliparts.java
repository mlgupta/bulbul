package bulbul.foff.studio.engine.cliparts;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGWindow;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @description 
 * @version 1.0 03-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGCliparts implements SVGClassObject {
  
  private final String  idcliparts="Cliparts";
  
  private Studio studio;
  
  private final bulbul.foff.studio.engine.ui.SVGCliparts clipartsPanel; 
  
  private JPanel clipartsFrame=null;
  
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGCliparts(Studio studio) {
    this.studio=studio;
    
    clipartsFrame= getStudio().getPanel4Cliparts();
    clipartsFrame.setLayout(new BorderLayout());
    clipartsPanel=new bulbul.foff.studio.engine.ui.SVGCliparts(studio);
    clipartsFrame.add(clipartsPanel,BorderLayout.CENTER);


    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				if(svgTab!=null){
          clipartsPanel.initTree();
          clipartsPanel.setTreeInitialized(true);
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
   return idcliparts;
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