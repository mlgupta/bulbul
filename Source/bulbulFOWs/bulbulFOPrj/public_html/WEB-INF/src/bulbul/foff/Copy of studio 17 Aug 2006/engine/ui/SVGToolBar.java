package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.managers.SVGColorManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

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
  private JToggleButton stbColor=new JToggleButton();
	private JPanel colorPanel=new JPanel();
  
  private Studio studio;
  
  private Border mouseExitedBorder=BorderFactory.createEmptyBorder(0, 0, 0, 0);
  private Border mouseEnteredBorder=new LineBorder(new Color(247,161,90),1);
  private Border toolBarBorder=new LineBorder(new Color(247,161,90),1);
  
  private MouseAdapter mtbMouseListener;
  

  /**
   * 
   * @description 
   */
  public SVGToolBar(Studio studio) {
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
    stbRegular.setIcon(getIcon("regularMode.png"));
    stbRegular.setBorder(mouseExitedBorder);
    stbRegular.addMouseListener(mtbMouseListener);
    
    //stbText.setText("T");
    stbText.setSize(buttonSize);
    stbText.setPreferredSize(buttonSize);
    stbText.setMaximumSize(buttonSize);
    stbText.setMinimumSize(buttonSize);
    stbText.setToolTipText("Draw Text");
    stbText.setEnabled(false);
    stbText.setIcon(getIcon("text.png"));
    stbText.setBorder(mouseExitedBorder);
    stbText.addMouseListener(mtbMouseListener);
    
    //stbLine.setText("L");
    stbLine.setSize(buttonSize);
    stbLine.setPreferredSize(buttonSize);
    stbLine.setMaximumSize(buttonSize);
    stbLine.setMinimumSize(buttonSize);
    stbLine.setToolTipText("Draw Line");
    stbLine.setEnabled(false);
    stbLine.setIcon(getIcon("line.png"));
    stbLine.setBorder(mouseExitedBorder);
    stbLine.addMouseListener(mtbMouseListener);
    
    //stbRectangle.setText("R");
    stbRectangle.setSize(buttonSize);
    stbRectangle.setPreferredSize(buttonSize);
    stbRectangle.setMaximumSize(buttonSize);
    stbRectangle.setMinimumSize(buttonSize);
    stbRectangle.setToolTipText("Draw Rectangle");
    stbRectangle.setEnabled(false);
    stbRectangle.setIcon(getIcon("rectangle.png"));
    stbRectangle.setBorder(mouseExitedBorder);
    stbRectangle.addMouseListener(mtbMouseListener);
    
    //stbCircle.setText("C");
    stbCircle.setSize(buttonSize);
    stbCircle.setPreferredSize(buttonSize);
    stbCircle.setMaximumSize(buttonSize);
    stbCircle.setMinimumSize(buttonSize);
    stbCircle.setToolTipText("Draw Circle");
    stbCircle.setEnabled(false);
    stbCircle.setIcon(getIcon("circle.png"));
    stbCircle.setBorder(mouseExitedBorder);
    stbCircle.addMouseListener(mtbMouseListener);
    
    //stbEllipse.setText("E");
    stbEllipse.setSize(buttonSize);
    stbEllipse.setPreferredSize(buttonSize);
    stbEllipse.setMaximumSize(buttonSize);
    stbEllipse.setMinimumSize(buttonSize);
    stbEllipse.setToolTipText("Draw Ellispe");
    stbEllipse.setEnabled(false);
    stbEllipse.setIcon(getIcon("ellipse.png"));
    stbEllipse.setBorder(mouseExitedBorder);
    stbEllipse.addMouseListener(mtbMouseListener);
    
    //stbPolyLine.setText("PL");
    stbPolyLine.setSize(buttonSize);
    stbPolyLine.setPreferredSize(buttonSize);
    stbPolyLine.setMaximumSize(buttonSize);
    stbPolyLine.setMinimumSize(buttonSize);
    stbPolyLine.setToolTipText("Draw Polyline");
    stbPolyLine.setEnabled(false);
    stbPolyLine.setIcon(getIcon("polyline.png"));
    stbPolyLine.setBorder(mouseExitedBorder);
    stbPolyLine.addMouseListener(mtbMouseListener);
    
    //stbPolygon.setText("PG");
    stbPolygon.setSize(buttonSize);
    stbPolygon.setPreferredSize(buttonSize);
    stbPolygon.setMaximumSize(buttonSize);
    stbPolygon.setMinimumSize(buttonSize);
    stbPolygon.setToolTipText("Draw Polygon");
    stbPolygon.setEnabled(false);
    stbPolygon.setIcon(getIcon("polygon.png"));
    stbPolygon.setBorder(mouseExitedBorder);
    stbPolygon.addMouseListener(mtbMouseListener);
    
    //stbQBCurve.setText("QB");
    stbQBCurve.setSize(buttonSize);
    stbQBCurve.setPreferredSize(buttonSize);
    stbQBCurve.setMaximumSize(buttonSize);
    stbQBCurve.setMinimumSize(buttonSize);
    stbQBCurve.setToolTipText("Draw Bezier Curve I");
    stbQBCurve.setEnabled(false);
    stbQBCurve.setIcon(getIcon("bezierQ.png"));
    stbQBCurve.setBorder(mouseExitedBorder);
    stbQBCurve.addMouseListener(mtbMouseListener);
    
    //stbCBCurve.setText("CB");
    stbCBCurve.setSize(buttonSize);
    stbCBCurve.setPreferredSize(buttonSize);
    stbCBCurve.setMaximumSize(buttonSize);
    stbCBCurve.setMinimumSize(buttonSize);
    stbCBCurve.setToolTipText("Draw Bezier Curve II");
    stbCBCurve.setEnabled(false);
    stbCBCurve.setIcon(getIcon("bezierC.png"));
    stbCBCurve.setBorder(mouseExitedBorder);
    stbCBCurve.addMouseListener(mtbMouseListener);
    
    
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
    this.add(stbColor, null);
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
   * @param name
   */
  private ImageIcon getIcon(String name){
    return getStudio().getSvgResource().getIcon(name);
  }

  
  public void init(){
  
    //Color Picking
    stbColor.setMargin(new Insets(0, 0, 0, 0));
    stbColor.setPreferredSize(buttonSize);
    stbColor.setMaximumSize(buttonSize);
    stbColor.setMinimumSize(buttonSize);
    stbColor.setSize(buttonSize);
    stbColor.setToolTipText("Pick Color and Draw Shapes");  
    stbColor.setBorder(mouseEnteredBorder);
    
    colorPanel.setBackground(getStudio().getSvgColorManager().getCurrentColor());
    colorPanel.setPreferredSize(new Dimension((int)buttonSize.getWidth()-4,(int)buttonSize.getHeight()-4));
    colorPanel.setMaximumSize(new Dimension((int)buttonSize.getWidth()-4,(int)buttonSize.getHeight()-4));
    colorPanel.setMinimumSize(new Dimension((int)buttonSize.getWidth()-4,(int)buttonSize.getHeight()-4));
    colorPanel.setSize(new Dimension((int)buttonSize.getWidth()-4,(int)buttonSize.getHeight()-4));
    
		stbColor.add(colorPanel);
		
		//the listener to the color button actions
		final ActionListener buttonListener=new ActionListener(){
      public void actionPerformed(ActionEvent evt) {
        //the listener to the ok button
        ActionListener okListener=new ActionListener(){
          public void actionPerformed(ActionEvent evt) {
            Color color=getStudio().getColorChooser().getColor();
            if(color!=null){
              getStudio().getSvgColorManager().setCurrentColor(color);
            }
            stbColor.setSelected(false);
          }
        };
        
        //the dialog displaying a color chooser
        JDialog dialog=JColorChooser.createDialog(getStudio(), "", true, getStudio().getColorChooser(), okListener, null);
        dialog.setVisible(true);
	    }
		};
		
		stbColor.addActionListener(buttonListener);
		
		//adding the listener to the changes of the current color
		getStudio().getSvgColorManager().addColorListener(new SVGColorManager.ColorListener(){
      public void colorChanged(Color color) {
      colorPanel.setBackground(color);
      } 
		});
    repaint();
  
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
  public Studio getStudio() {
    return studio;
  }
}