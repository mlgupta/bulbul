package studio.canvas;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.SwingConstants;

public class SVGStatusBar extends JPanel  {
  private GridLayout thisLayout = new GridLayout();
  private GridLayout coordinatePanelLayout = new GridLayout();
  private GridLayout sizePanelLayout = new GridLayout();
  private JPanel coordinatePanel = new JPanel();
  private JPanel sizePanel = new JPanel();
  private JLabel lblName = new JLabel();
  private JLabel lblZoom = new JLabel();
  private JLabel lblInfo = new JLabel();
  private JLabel lblX = new JLabel();
  private JLabel lblY = new JLabel();
  private JLabel lblWidth = new JLabel();
  private JLabel lblHeight = new JLabel();
  
  private String caption4X=""; 
  private String caption4Y=""; 
  private String caption4Width=""; 
  private String caption4Height="";

  public SVGStatusBar() {
    try {
      //sets the labels 
      caption4X="Left(X) : "; 
      caption4Y="Top(Y) : "; 
      caption4Width="Width : "; 
      caption4Height="Height : ";
      
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(thisLayout);    
    this.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    thisLayout.setColumns(5);
    thisLayout.setHgap(2);
    thisLayout.setVgap(2);
    lblName.setBorder(BorderFactory.createEtchedBorder());
    lblName.setHorizontalAlignment(SwingConstants.CENTER);
    lblZoom.setBorder(BorderFactory.createEtchedBorder());
    lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
    coordinatePanel.setLayout(coordinatePanelLayout);
    coordinatePanel.setBorder(BorderFactory.createEtchedBorder());
    coordinatePanelLayout.setColumns(2);
    coordinatePanelLayout.setHgap(5);
    sizePanel.setLayout(sizePanelLayout);
    sizePanel.setBorder(BorderFactory.createEtchedBorder());
    sizePanelLayout.setColumns(2);
    sizePanelLayout.setHgap(5);
    lblInfo.setBorder(BorderFactory.createEtchedBorder());
    lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
    lblX.setHorizontalAlignment(SwingConstants.CENTER);
    lblY.setHorizontalAlignment(SwingConstants.CENTER);
    lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(lblName, null);
    this.add(lblZoom, null);
    coordinatePanel.add(lblX, null);
    coordinatePanel.add(lblY, null);
    this.add(coordinatePanel, null);
    sizePanel.add(lblWidth, null);
    sizePanel.add(lblHeight, null);
    this.add(sizePanel, null);
    this.add(lblInfo, null);
  }
  /**
		 * sets the name of the SVG picture
		 * @param nm the name of the SVG picture
		 */
		public void setSVGName(String name){
			lblName.setText(name);
		}

		/**
		 * sets a string representation of the zoom scale of the SVG picture
		 * @param zm a string representation of the zoom scale of the SVG picture
		 */		
		public void setSVGZoom(String zoom){
			lblZoom.setText(zoom);
		}	
			
		/**
		 * sets a string representation of the x position of the mouse on the canvas
		 * @param sx a string representation of the x position of the mouse on the canvas
		 */				
		public void setSVGX(String newX){
			if(newX!=null && !newX.equals("")){
				lblX.setText(caption4X+newX);
			}else{
				lblX.setText("");
			}
		}
		
		/**
		 * sets a string representation of the y position of the mouse on the canvas
		 * @param sy a string representation of the y position of the mouse on the canvas
		 */				
		public void setSVGY(String newY){
			if(newY!=null && !newY.equals("")){
				lblY.setText(caption4Y+newY);
			}else{
				lblY.setText("");
			}
			
		}
		
		/**
		 * sets a string representation of the width of a shape
		 * @param sw a string representation of the width of a shape
		 */				
		public void setSVGW(String newWidth){
			if(newWidth!=null && !newWidth.equals("")){
				lblWidth.setText(caption4Width+newWidth);
			}else{
				lblWidth.setText("");
			}
		}
		
		/**
		 * sets a string representation of the height of a shape
		 * @param sh a string representation of the height of a shape
		 */				
		public void setSVGH(String newHeight){
			if(newHeight!=null && !newHeight.equals("")){
				lblHeight.setText(caption4Height+newHeight);
			}else{
				lblHeight.setText("");
			}
			
		}
		
		/**
		 * sets the string representing information to be displayed
		 * @param in a string representing information to be displayed
		 */
		public void setSVGInfos(String info){
			lblInfo.setText(info);
		}		
}