package bulbul.foff.studio.engine.ui;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
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
  private JLabel lblTabName = new JLabel();
  private JLabel lblZoom = new JLabel("100 %");
  private JLabel lblX = new JLabel();
  private JLabel lblY = new JLabel();
  private JLabel lblWidth = new JLabel();
  private JLabel lblHeight = new JLabel();
  private JLabel lblInfo = new JLabel();
  
  private Studio studio;


  /**
   * 
   * @param studio
   */
  public MainStatusBar(Studio studio) {
    try {
      this.studio=studio;
      
      //sets the labels 
      caption4X="X - "; 
      caption4Y="Y - "; 
      caption4Width="W - "; 
      caption4Height="H - ";
      
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
    layout4This.setColumns(6);
    layout4This.setHgap(2);

    setLayout(layout4This);
    setBackground(new Color(253,235,212));
    
    lblName.setBackground(new Color(253,235,212));
    lblName.setBorder(new LineBorder(new Color(247,161,90),1));
    lblName.setHorizontalAlignment(SwingConstants.CENTER);
    lblName.setToolTipText("Design Name");
    lblName.setFont(Studio.STUDIO_FONT);
    
    lblTabName.setBackground(new Color(253,235,212));
    lblTabName.setBorder(new LineBorder(new Color(247,161,90),1));
    lblTabName.setHorizontalAlignment(SwingConstants.CENTER);
    lblTabName.setToolTipText("Design Area Name");
    lblTabName.setFont(Studio.STUDIO_FONT);
    
    lblZoom.setBackground(new Color(253,235,212));
    lblZoom.setBorder(new LineBorder(new Color(247,161,90),1));
    lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
    lblZoom.setToolTipText("Zoom %");
    lblZoom.setFont(Studio.STUDIO_FONT);
    
    lblX.setHorizontalAlignment(SwingConstants.CENTER);
    lblX.setFont(Studio.STUDIO_FONT);
    lblX.setBackground(new Color(253,235,212));
    
    lblY.setHorizontalAlignment(SwingConstants.CENTER);
    lblY.setFont(Studio.STUDIO_FONT);
    lblY.setBackground(new Color(253,235,212));
    
    lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lblWidth.setFont(Studio.STUDIO_FONT);
    lblWidth.setBackground(new Color(253,235,212));
    
    lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
    lblHeight.setFont(Studio.STUDIO_FONT);
    lblHeight.setBackground(new Color(253,235,212));
    
    
    lblInfo.setBackground(new Color(253,235,212));
    lblInfo.setBorder(new LineBorder(new Color(247,161,90),1));
    lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
    lblInfo.setToolTipText("Information");
    lblInfo.setFont(Studio.STUDIO_FONT);

   
    layout4CoordinatePanel.setColumns(2);
    layout4CoordinatePanel.setHgap(5);
    panel4Coordinates.setLayout(layout4CoordinatePanel);
    panel4Coordinates.setBackground(new Color(253,235,212));
    panel4Coordinates.setBorder(new LineBorder(new Color(247,161,90),1));
    panel4Coordinates.setToolTipText("Mouse Coordinates - (X,Y)");
    
    panel4Coordinates.add(lblX, null);
    panel4Coordinates.add(lblY, null);

    layout4SizePanel.setColumns(2);
    layout4SizePanel.setHgap(5);
    panel4Size.setLayout(layout4SizePanel);
    panel4Size.setBackground(new Color(253,235,212));
    panel4Size.setBorder(new LineBorder(new Color(247,161,90),1));
    panel4Size.setToolTipText("Selection Size - (Width, Height)");
    
    panel4Size.add(lblWidth, null);
    panel4Size.add(lblHeight, null);

    this.add(lblName, null);
    
    this.add(lblTabName, null);
    
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
   * @param name
   */
  public void setTabName(String tabName){
    this.lblTabName.setText(tabName);
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