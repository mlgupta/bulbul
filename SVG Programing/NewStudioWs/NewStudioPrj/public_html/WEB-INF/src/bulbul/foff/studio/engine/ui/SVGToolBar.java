package bulbul.foff.studio.engine.ui;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @description 
 * @version 1.0 dd-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGToolBar extends JToolBar  {
  
  private Dimension buttonSize = new Dimension(22,22);
  
  private FlowLayout layout4This = new FlowLayout();
  
  private JToggleButton stbRegular = new JToggleButton();
  private JToggleButton stbText = new JToggleButton();
  private JToggleButton stbLine = new JToggleButton();
  private JToggleButton stbRectangle = new JToggleButton();
  private JToggleButton stbCircle = new JToggleButton();
  private JToggleButton stbEllipse = new JToggleButton();
  private JToggleButton stbPolyLine = new JToggleButton();
  private JToggleButton stbPolygon = new JToggleButton();
  private JToggleButton stbQBCurve = new JToggleButton();
  private JToggleButton stbCBCurve = new JToggleButton();
  private JToggleButton stbImage = new JToggleButton();
  
  private SVGApplet studio;
  
  private Border mouseExitedBorder=BorderFactory.createEmptyBorder(0, 0, 0, 0);
  private Border mouseEnteredBorder=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  private Border toolBarBorder=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  
  private MouseAdapter mtbMouseListener;
  

  /**
   * 
   * @description 
   */
  public SVGToolBar(SVGApplet studio) {
    try {
      this.studio=studio;
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setFocusable(false);
    setOrientation(JToolBar.VERTICAL); 
    setBorder(toolBarBorder);
    
    layout4This.setAlignment(0);
    layout4This.setHgap(2);
    layout4This.setVgap(2);
    
    setLayout(layout4This);

    mtbMouseListener=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        mtbMouseEntered(e);
      }

      public void mouseExited(MouseEvent e) {
        mtbMouseExited(e);
      }        
    };
    
    //stbRegular.setText("A");
    stbRegular.setSize(buttonSize);
    stbRegular.setPreferredSize(buttonSize);
    stbRegular.setMaximumSize(buttonSize);
    stbRegular.setMinimumSize(buttonSize);
    stbRegular.setToolTipText("Regular");
    stbRegular.setEnabled(false);
    stbRegular.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/regularMode.png")));
    stbRegular.setBorder(mouseExitedBorder);
    stbRegular.addMouseListener(mtbMouseListener);
    
    //stbText.setText("T");
    stbText.setSize(buttonSize);
    stbText.setPreferredSize(buttonSize);
    stbText.setMaximumSize(buttonSize);
    stbText.setMinimumSize(buttonSize);
    stbText.setToolTipText("Draw Text");
    stbText.setEnabled(false);
    stbText.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/text.png")));
    stbText.setBorder(mouseExitedBorder);
    stbText.addMouseListener(mtbMouseListener);
    
    //stbLine.setText("L");
    stbLine.setSize(buttonSize);
    stbLine.setPreferredSize(buttonSize);
    stbLine.setMaximumSize(buttonSize);
    stbLine.setMinimumSize(buttonSize);
    stbLine.setToolTipText("Draw Line");
    stbLine.setEnabled(false);
    stbLine.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/line.png")));
    stbLine.setBorder(mouseExitedBorder);
    stbLine.addMouseListener(mtbMouseListener);
    
    //stbRectangle.setText("R");
    stbRectangle.setSize(buttonSize);
    stbRectangle.setPreferredSize(buttonSize);
    stbRectangle.setMaximumSize(buttonSize);
    stbRectangle.setMinimumSize(buttonSize);
    stbRectangle.setToolTipText("Draw Rectangle");
    stbRectangle.setEnabled(false);
    stbRectangle.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/rectangle.png")));
    stbRectangle.setBorder(mouseExitedBorder);
    stbRectangle.addMouseListener(mtbMouseListener);
    
    //stbCircle.setText("C");
    stbCircle.setSize(buttonSize);
    stbCircle.setPreferredSize(buttonSize);
    stbCircle.setMaximumSize(buttonSize);
    stbCircle.setMinimumSize(buttonSize);
    stbCircle.setToolTipText("Draw Circle");
    stbCircle.setEnabled(false);
    stbCircle.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/circle.png")));
    stbCircle.setBorder(mouseExitedBorder);
    stbCircle.addMouseListener(mtbMouseListener);
    
    //stbEllipse.setText("E");
    stbEllipse.setSize(buttonSize);
    stbEllipse.setPreferredSize(buttonSize);
    stbEllipse.setMaximumSize(buttonSize);
    stbEllipse.setMinimumSize(buttonSize);
    stbEllipse.setToolTipText("Draw Ellispe");
    stbEllipse.setEnabled(false);
    stbEllipse.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/ellipse.png")));
    stbEllipse.setBorder(mouseExitedBorder);
    stbEllipse.addMouseListener(mtbMouseListener);
    
    //stbPolyLine.setText("PL");
    stbPolyLine.setSize(buttonSize);
    stbPolyLine.setPreferredSize(buttonSize);
    stbPolyLine.setMaximumSize(buttonSize);
    stbPolyLine.setMinimumSize(buttonSize);
    stbPolyLine.setToolTipText("Draw Polyline");
    stbPolyLine.setEnabled(false);
    stbPolyLine.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/polyline.png")));
    stbPolyLine.setBorder(mouseExitedBorder);
    stbPolyLine.addMouseListener(mtbMouseListener);
    
    //stbPolygon.setText("PG");
    stbPolygon.setSize(buttonSize);
    stbPolygon.setPreferredSize(buttonSize);
    stbPolygon.setMaximumSize(buttonSize);
    stbPolygon.setMinimumSize(buttonSize);
    stbPolygon.setToolTipText("Draw Polygon");
    stbPolygon.setEnabled(false);
    stbPolygon.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/polygon.png")));
    stbPolygon.setBorder(mouseExitedBorder);
    stbPolygon.addMouseListener(mtbMouseListener);
    
    //stbQBCurve.setText("QB");
    stbQBCurve.setSize(buttonSize);
    stbQBCurve.setPreferredSize(buttonSize);
    stbQBCurve.setMaximumSize(buttonSize);
    stbQBCurve.setMinimumSize(buttonSize);
    stbQBCurve.setToolTipText("Draw Bezier Curve I");
    stbQBCurve.setEnabled(false);
    stbQBCurve.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/bezierQ.png")));
    stbQBCurve.setBorder(mouseExitedBorder);
    stbQBCurve.addMouseListener(mtbMouseListener);
    
    //stbCBCurve.setText("CB");
    stbCBCurve.setSize(buttonSize);
    stbCBCurve.setPreferredSize(buttonSize);
    stbCBCurve.setMaximumSize(buttonSize);
    stbCBCurve.setMinimumSize(buttonSize);
    stbCBCurve.setToolTipText("Draw Bezier Curve II");
    stbCBCurve.setEnabled(false);
    stbCBCurve.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/bezierC.png")));
    stbCBCurve.setBorder(mouseExitedBorder);
    stbCBCurve.addMouseListener(mtbMouseListener);
    
    //stbImage.setText("I");
    stbImage.setSize(buttonSize);
    stbImage.setPreferredSize(buttonSize);
    stbImage.setMaximumSize(buttonSize);
    stbImage.setMinimumSize(buttonSize);
    stbImage.setToolTipText("Draw Image");
    stbImage.setEnabled(false);
    stbImage.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/image.png")));
    stbImage.setBorder(mouseExitedBorder);
    stbImage.addMouseListener(mtbMouseListener);
    
    this.add(stbRegular, null);
    this.add(stbText, null);
    this.add(stbLine, null);
    this.add(stbRectangle, null);
    this.add(stbCircle, null);
    this.add(stbEllipse, null);
    this.add(stbPolyLine, null);
    this.add(stbPolygon, null);
    this.add(stbQBCurve, null);
    this.add(stbCBCurve, null);
    this.add(stbImage, null);
    
  }

  /**
   * 
   * @description 
   * @param e
   */
  private void mtbMouseEntered(MouseEvent e) {
    JComponent srcButton = (JComponent)e.getSource();
    if(srcButton.isEnabled()){
      srcButton.setBorder(mouseEnteredBorder);
    }
  }

  /**
   * 
   * @description 
   * @param e
   */
  private void mtbMouseExited(MouseEvent e) {
    JComponent srcButton = (JComponent)e.getSource();
    if(srcButton.isEnabled()){
      srcButton.setBorder(mouseExitedBorder);
    }
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbRegular() {
    return stbRegular;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbLine() {
    return stbLine;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbCircle() {
    return stbCircle;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbRectangle() {
    return stbRectangle;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbEllipse() {
    return stbEllipse;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbPolyLine() {
    return stbPolyLine;
  }


  public JToggleButton getStbPolygon() {
    return stbPolygon;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbQBCurve() {
    return stbQBCurve;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbCBCurve() {
    return stbCBCurve;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbText() {
    return stbText;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getStbImage() {
    return stbImage;
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