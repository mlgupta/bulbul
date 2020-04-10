package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalInternalFrameUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * 
 * @description 
 * @version 1.0 04-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGWindow  {
  
  private final static Color inactiveColor=MetalLookAndFeel.getWindowTitleInactiveBackground();
  private final static Color activeColor=MetalLookAndFeel.getWindowTitleBackground();
  private final static Color borderShadowColor=MetalLookAndFeel.getSeparatorForeground();
  private final static Color borderHighlightColor=Color.white;
  
  private String id;
  private String label;
  
  private Rectangle bounds;
  
  private Container window;  
  
  private Studio studio;
  
  private SVGWindow svgWindow=this;
  
  protected JPanel windowPanel=new JPanel();
  
  /**
   * 
   * @description 
   */
  public SVGWindow(Studio studio, String id, String label, Rectangle bounds, JPanel windowPanel) {
  
    this.studio=studio;
    this.id=id;
    this.label=label;
    this.bounds=bounds;
    this.windowPanel=windowPanel;  
    
    windowPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, borderHighlightColor, borderShadowColor));
    
    final JInternalFrame internalFrame=new JInternalFrame();
    window=internalFrame;
    internalFrame.setFrameIcon(null);
    internalFrame.setResizable(false);
    internalFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    internalFrame.setBorder(BorderFactory.createEmptyBorder());
    internalFrame.getContentPane().setLayout(new BoxLayout(internalFrame.getContentPane(), BoxLayout.X_AXIS));
    internalFrame.getContentPane().add(windowPanel);

    bounds.height+=21; //Inclusion of title Height

    //creating the title panel
    final JPanel titlePanel=new JPanel(){
            	
      /**
       * the transparent color
       */
      private final Color transparent=new Color(255, 255, 255, 0);
  
      /**
       * the transparent white color
       */
      private final Color transparentWhite=new Color(255, 255, 255, 175);

      protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        GradientPaint gradient=new GradientPaint(0, 0, getBackground(), 0, getHeight()/2, transparent);
        ((Graphics2D)g).setPaint(gradient);
        g.fillRect(0, 0, getWidth(), getHeight()/2);
        
        gradient=new GradientPaint(0, 0, transparent, 0, getHeight(), getBackground());
        ((Graphics2D)g).setPaint(gradient);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        gradient=new GradientPaint(0, 0, transparent, getWidth()/2, 0, transparentWhite, true);
        ((Graphics2D)g).setPaint(gradient);
        g.fillRect(0, 0, getWidth(), getHeight());
      }
    };
    
    titlePanel.setLayout(new BorderLayout(0, 1));
    titlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, borderHighlightColor, borderShadowColor));
    titlePanel.setBackground(inactiveColor);

    //the listener to the focus changes
    internalFrame.addVetoableChangeListener(new VetoableChangeListener(){
      public void vetoableChange(PropertyChangeEvent evt)throws PropertyVetoException {
        if(	evt.getPropertyName()!=null && evt.getPropertyName().equals("selected") && ((Boolean)evt.getNewValue()).booleanValue()){
          titlePanel.setBackground(activeColor);
        }else if(	evt.getPropertyName()!=null && evt.getPropertyName().equals("selected") && ! ((Boolean)evt.getNewValue()).booleanValue()){
          titlePanel.setBackground(inactiveColor);
        }
      }
    });
    
    JLabel titleLabel=new JLabel(label, JLabel.CENTER);
    titleLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
    titleLabel.setFont(Studio.STUDIO_FONT);
    
    final ImageIcon	titleCloseIcon=studio.getSvgResource().getIcon("close.png");

    final JLabel iconLabel=new JLabel(titleCloseIcon);
    iconLabel.setBorder(new EmptyBorder(0, 0, 0, 1));
    
    titlePanel.add(titleLabel, BorderLayout.CENTER);
    titlePanel.add(iconLabel, BorderLayout.EAST);
    
    //adding a listener to the clicks on the icon label
    iconLabel.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent evt){
        handleContentVisibility();      
        if(window.isVisible()){
          window.setVisible(false);
        }
      }
    });
    
    //setting the title bar of the frame
    MetalInternalFrameUI toolBarFrameUI=new MetalInternalFrameUI(internalFrame){
      protected JComponent createNorthPane(JInternalFrame w){
        return titlePanel;
      }
    };
    internalFrame.setUI(toolBarFrameUI);
        
    internalFrame.setBounds(bounds);
      
    //adds the dialog to the desktop
    studio.getDesktop().add(internalFrame);
    
    //setting its z-index on the desktop 
    ((JDesktopPane)studio.getDesktop()).setLayer(internalFrame, 2, 2);
  }
  
  /**
   * 
   * @description 
   */
  protected void handleContentVisibility(){
          
  }
  
   
  /**
   * 
   * @description 
   * @param y
   * @param x
   */
  public void setLocation(int x, int y){
    window.setLocation(x, y);
  }
    
    
  /**
   * 
   * @description 
   * @param location
   */
  public void setLocation(Point location){
    if(location!=null){
      window.setLocation(location);
    }
  }
    
  /**
   * 
   * @description 
   * @param isVisible
   */
  public void setVisible(boolean isVisible){
    window.setVisible(isVisible);
  }
    
    
  /**
   * 
   * @description 
   */
  public void revalidate(){
    ((JInternalFrame)window).revalidate();
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