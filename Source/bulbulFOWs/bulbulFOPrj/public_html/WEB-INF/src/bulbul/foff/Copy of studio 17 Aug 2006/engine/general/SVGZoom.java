package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.listeners.SVGZoomActionListener;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGPopupMenuItem;
import bulbul.foff.studio.engine.ui.SVGPopupSubMenu;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
  
  private String labelzoom="Zoom";
  private String labelzoomin="Zoom +";
  private String labelzoomout="Zoom -";
  private String labelzoom11="Zoom Original";
  
  private Studio  studio;
  
  private JButton zoom;
  private JButton zoomDrop;
  private JButton zoomIn;
  private JButton zoomOut;
  private JButton zoom11;
  private JMenu menuZoom;
  
  private final String[]  labels=new String[13];
  private final double[]  factors=new double[13];
  
  private ActionListener zoomInListener;
  private ActionListener zoomOutListener;
  private ActionListener zoom11Listener;
  
  /**
   * 
   * @description 
   */
  public SVGZoom(Studio studio) {
    
    this.studio=studio;
    zoom=getStudio().getMainToolBar().getMtbZoom();
    zoomDrop=getStudio().getMainToolBar().getMtbZoomDrop();
    zoomIn=getStudio().getMainToolBar().getMtbZoomIn();
    zoomOut=getStudio().getMainToolBar().getMtbZoomOut();
    zoom11=getStudio().getMainToolBar().getMtbZoom11();
    menuZoom=getStudio().getMainToolBar().getMenuZoom();
    
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
          zoom11.setEnabled(true); 
          zoomDrop.setEnabled(true); 
          zoomIn.setEnabled(true); 
          zoomOut.setEnabled(true); 
        }else{
          zoom11.setEnabled(false); 
          zoomDrop.setEnabled(false); 
          zoomIn.setEnabled(false); 
          zoomOut.setEnabled(false); 
				}
			}	
		};
		
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    for(int i=0;i<labels.length;i++){
      final int finalI=i;
      JMenuItem menuItemZoom=new JMenuItem(labels[i]);
      menuItemZoom.addActionListener(new SVGZoomActionListener(getStudio()){
        public void actionPerformed(ActionEvent evt) {
          setScale(factors[finalI]);
          super.actionPerformed(evt);
        }		
      });
      menuZoom.add(menuItemZoom);
		}
      
    zoom11Listener=new SVGZoomActionListener(getStudio()){
          public void actionPerformed(ActionEvent evt) {
            setScale(1.0);
            super.actionPerformed(evt);
          }     
        };
    zoom11.addActionListener(zoom11Listener);
    
    zoomInListener=new SVGZoomActionListener(getStudio()){
      public void actionPerformed(ActionEvent evt) {
        setScale(getFactor(true));
        super.actionPerformed(evt);
      }
    };
    zoomIn.addActionListener(zoomInListener);
    
    zoomOutListener=new SVGZoomActionListener(getStudio()){
      public void actionPerformed(ActionEvent evt) {
        setScale(getFactor(false));
        super.actionPerformed(evt);
      }
    };
    zoomOut.addActionListener(zoomOutListener);
  }
  
  public Collection getPopupMenuItems(){
		
		LinkedList popupMenuItems=new LinkedList();
		
		//creating the popup items//
		
		SVGPopupMenuItem zoomInItem=new SVGPopupMenuItem(getStudio(), idzoomin, labelzoomin, zoomIn.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				menuItem.addActionListener(zoomInListener);
				return super.getPopupMenuItem(nodes);
			}
		};
		
		SVGPopupMenuItem zoomOutItem=new SVGPopupMenuItem(getStudio(), idzoomout, labelzoomout, zoomOut.getIcon()){
			public JMenuItem getPopupMenuItem(LinkedList nodes) {
				menuItem.addActionListener(zoomOutListener);
				return super.getPopupMenuItem(nodes);
			}
		};
		
        SVGPopupMenuItem zoom11Item=new SVGPopupMenuItem(getStudio(), idzoom11, labelzoom11, zoom11.getIcon()){
	      public JMenuItem getPopupMenuItem(LinkedList nodes) {
	          menuItem.addActionListener(zoom11Listener);
	          return super.getPopupMenuItem(nodes);
	      }
	  };
      
		SVGPopupSubMenu zoomMenu=new SVGPopupSubMenu(getStudio(), idzoom, labelzoom, zoom.getIcon());
		
		//creates the popup items contained in this sub menu
		SVGPopupMenuItem item=null;
		for(int i=0;i<labels.length;i++){
      final int finalI=i;
      item=new SVGPopupMenuItem(getStudio(), i+"", labels[i], null){
      public JMenuItem getPopupMenuItem(LinkedList nodes) {
        menuItem.addActionListener(new SVGZoomActionListener(getStudio()){
          public void actionPerformed(ActionEvent evt) {
            setScale(factors[finalI]);
            super.actionPerformed(evt);
          }		
        });
        return super.getPopupMenuItem(nodes);
      }
     };
     zoomMenu.addPopupMenuItem(item);
		}
		
		popupMenuItems.add(zoomInItem);
		popupMenuItems.add(zoomOutItem);
        popupMenuItems.add(zoom11Item);
		popupMenuItems.add(zoomMenu);
	  

		return popupMenuItems;
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
  public Studio getStudio() {
    return studio;
  }
}