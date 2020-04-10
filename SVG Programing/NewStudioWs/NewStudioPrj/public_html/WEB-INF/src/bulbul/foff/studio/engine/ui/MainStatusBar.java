package bulbul.foff.studio.engine.ui;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * @author Sudheer V. Pujar
 */
public class MainStatusBar extends JPanel  {
  private GridLayout layout4This = new GridLayout();
  private GridLayout layout4CoordinatePanel = new GridLayout();
  private GridLayout layout4SizePanel = new GridLayout();

  private JPanel panel4Coordinates = new JPanel();
  private JPanel panel4Size = new JPanel();
  
  private String caption4X=""; 
  private String caption4Y=""; 
  private String caption4Width=""; 
  private String caption4Height="";

  private JLabel lblName = new JLabel();
  private JLabel lblZoom = new JLabel("100 %");
  private JLabel lblX = new JLabel();
  private JLabel lblY = new JLabel();
  private JLabel lblWidth = new JLabel();
  private JLabel lblHeight = new JLabel();
  private JLabel lblInfo = new JLabel();
  
  private SVGApplet studio;


  /**
   * 
   * @param studio
   */
  public MainStatusBar(SVGApplet studio) {
    try {
      this.studio=studio;
      
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

  /**
   * 
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    layout4This.setColumns(5);
    layout4This.setHgap(2);

    this.setLayout(layout4This);
    //this.setBorder(BorderFactory.createEtchedBorder());
   
    lblName.setBorder(BorderFactory.createEtchedBorder());
    lblName.setHorizontalAlignment(SwingConstants.CENTER);
    lblName.setToolTipText("Design Name");
    
    lblZoom.setBorder(BorderFactory.createEtchedBorder());
    lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
    lblZoom.setToolTipText("Zoom %");

    lblX.setHorizontalAlignment(SwingConstants.CENTER);
    lblY.setHorizontalAlignment(SwingConstants.CENTER);
    
    lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
    
    lblInfo.setBorder(BorderFactory.createEtchedBorder());
    lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
    lblInfo.setToolTipText("Information");
   
    layout4CoordinatePanel.setColumns(2);
    layout4CoordinatePanel.setHgap(5);
    panel4Coordinates.setLayout(layout4CoordinatePanel);
    panel4Coordinates.setBorder(BorderFactory.createEtchedBorder());
    panel4Coordinates.setToolTipText("Mouse Coordinates - (X,Y)");
    
    panel4Coordinates.add(lblX, null);
    panel4Coordinates.add(lblY, null);

    layout4SizePanel.setColumns(2);
    layout4SizePanel.setHgap(5);
    panel4Size.setLayout(layout4SizePanel);
    panel4Size.setBorder(BorderFactory.createEtchedBorder());
    panel4Size.setToolTipText("Selection Size - (Width, Height)");
    
    panel4Size.add(lblWidth, null);
    panel4Size.add(lblHeight, null);

    this.add(lblName, null);
    
    this.add(lblZoom, null);
    
    this.add(panel4Coordinates, null);
    
    this.add(panel4Size, null);
    
    this.add(lblInfo, null);
  }
  
  /**
   * 
   * @param name
   */
  public void setName(String name){
    this.lblName.setText(name);
  }
  
  /**
   * 
   * @param zoom
   */
  public void setZoom(String zoom){
    this.lblZoom.setText(zoom);
  }
  
  /**
   * 
   * @param x
   */
  public void setX(String x){
    lblX.setText((x!=null && !x.equals(""))?caption4X+x:"");    
  }
  
  /**
   * 
   * @param y
   */
  public void setY(String y){
    lblY.setText((y!=null && !y.equals(""))?caption4Y+y:"");    
  }
  
  /**
   * 
   * @param width
   */
  public void setWidth(String width){
    lblWidth.setText((width!=null && !width.equals(""))?caption4Width+width:"");    
  }
  
  /**
   * 
   * @param height
   */
  public void setHeight(String height){
    lblHeight.setText((height!=null && !height.equals(""))?caption4Height+height:"");    
  }
  /**
   * 
   * @param info
   */
  public void setInfo(String info){
    lblInfo.setText(info); 
  }
  
}