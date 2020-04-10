package bulbul.foff.studio.engine.images;
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
 * @version 1.0 23-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGImages implements SVGClassObject  {
  private final String  idimages="Images";
  
  private Studio studio;
  
  private final bulbul.foff.studio.engine.ui.SVGImages imagesPanel; 
  
  private JPanel imagesFrame=null;
  
  /**
   * 
   * @description 
   */
  public SVGImages(Studio studio) {
    this.studio=studio;
    
    imagesFrame= getStudio().getPanel4Images();
    imagesFrame.setLayout(new BorderLayout());
    imagesPanel=new bulbul.foff.studio.engine.ui.SVGImages(studio);
    imagesFrame.add(imagesPanel,BorderLayout.CENTER);


    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				if(svgTab!=null){
          imagesPanel.getBtnAll().setEnabled(true);
          imagesPanel.getBtnJpeg().setEnabled(true);
          imagesPanel.getBtnGif().setEnabled(true);
          imagesPanel.getBtnPng().setEnabled(true);
          imagesPanel.getBtnSvg().setEnabled(true);
          imagesPanel.getBtnBrowse().setEnabled(true);
          imagesPanel.getBtnUpload().setEnabled(true);
          imagesPanel.getBtnClear().setEnabled(true);
        }else{
          imagesPanel.getBtnAll().setEnabled(false);
          imagesPanel.getBtnJpeg().setEnabled(false);
          imagesPanel.getBtnGif().setEnabled(false);
          imagesPanel.getBtnPng().setEnabled(false);
          imagesPanel.getBtnSvg().setEnabled(false);
          imagesPanel.getBtnBrowse().setEnabled(false);
          imagesPanel.getBtnUpload().setEnabled(false);
          imagesPanel.getBtnClear().setEnabled(false);
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
   return idimages;
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