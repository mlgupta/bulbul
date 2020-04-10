package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.listeners.SVGZoomActionListener;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * 
 * @description 
 * @version 1.0 26-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGZoom  implements SVGClassObject {
  
  private final String  idzoom11="Zoom 1:1";
  private final String  idzoom="Zoom";
  private final String  idzoomin="Zoom +";
  private final String  idzoomout="Zoom -";
  
  private SVGApplet  studio;
  
  private JButton zoomIn;
  private JButton zoomOut;
  private JComboBox cboZoom;
  
  private final String[]  labels=new String[13];
  private final double[]  factors=new double[13];
  
  /**
   * 
   * @description 
   */
  public SVGZoom(SVGApplet studio) {
    
    this.studio=studio;
    zoomIn=getStudio().getMainToolBar().getMtbZoomIn();
    zoomOut=getStudio().getMainToolBar().getMtbZoomOut();
    cboZoom=getStudio().getMainToolBar().getCboZoom();
    
  //defines the labels for the menuitems contained in the zoom menu
		labels[0]="5 %";
		labels[1]="10 %";
		labels[2]="20 %";
		labels[3]="50 %";
		labels[4]="75 %";
		labels[5]="100 %";
		labels[6]="125 %";
		labels[7]="150 %";
		labels[8]="200 %";
		labels[9]="400 %";
		labels[10]="500 %";
		labels[11]="800 %";
		labels[12]="1000 %";
		
		//the zoom factors
		factors[0]=0.05;
		factors[1]=0.1;
		factors[2]=0.2;
		factors[3]=0.5;
		factors[4]=0.75;
		factors[5]=1.0;
		factors[6]=1.25;
		factors[7]=1.5;
		factors[8]=2.0;
		factors[9]=4.0;
		factors[10]=5.0;
		factors[11]=8.0;
		factors[12]=10.0;
    
    //a listener that listens to the changes of the SVGTabs
		final ActionListener svgTabChangedListener=new ActionListener(){
			
			/**
			 * called when an event occurs
			 */
			public void actionPerformed(ActionEvent e) {
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
          cboZoom.setEnabled(true); 
          zoomIn.setEnabled(true); 
          zoomOut.setEnabled(true); 
          cboZoom.setSelectedIndex(getFactorIndex(svgTab.getScrollPane().getSvgCanvas().getScale()));
        }else{
				  cboZoom.setEnabled(false); 
          zoomIn.setEnabled(false); 
          zoomOut.setEnabled(false); 
				}
			}	
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    for(int i=0;i<labels.length;i++){
		  cboZoom.addItem(labels[i]);
		}
    
    cboZoom.addActionListener(new SVGZoomActionListener(studio){
        public void actionPerformed(ActionEvent evt) {
          int index = cboZoom.getSelectedIndex();
          setScale(factors[index]);
          super.actionPerformed(evt);
        }
      });
      
    zoomIn.addActionListener(new SVGZoomActionListener(studio){
      public void actionPerformed(ActionEvent evt) {
        setScale(getFactor(true));
        super.actionPerformed(evt);
      }
    });
    
    zoomOut.addActionListener(new SVGZoomActionListener(studio){
      public void actionPerformed(ActionEvent evt) {
        setScale(getFactor(false));
        super.actionPerformed(evt);
      }
    });
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param isZoomIn
   */
  private double getFactor(boolean isZoomIn){
    //gets the index of the current scale
    SVGTab currenSvgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
		double scale=currenSvgTab.getScrollPane().getSvgCanvas().getScale();
		int currentFactor=getFactorIndex(scale);
		int newFactor=(isZoomIn)?currentFactor+1:currentFactor-1;
    newFactor=(newFactor<labels.length && newFactor>=0)?newFactor:currentFactor; 
    cboZoom.setSelectedIndex(newFactor); 
    return factors[newFactor];
  }
  
  private int getFactorIndex(double scale){
    int factorIndex=0;
    for(int i=0;i<labels.length;i++){
			if(factors[i]==scale){
				factorIndex=i;
				break;
			}
		}
    return factorIndex;
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
   return idzoom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGApplet getStudio() {
    return studio;
  }
}