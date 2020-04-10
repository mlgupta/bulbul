package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.ui.SVGToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

/**
 * @auther Sudheer V Pujar
 */
public class MainTabSet extends JTabbedPane  {
  private JPanel jpPropertySheet = new JPanel();
  private JPanel jpCliparts = new JPanel();
  private JPanel jpProductCatalogue = new JPanel();
  private JPanel jpSaveNdSaveAsDesign = new JPanel();
  private JPanel jpMyGraphics = new JPanel();
  private JPanel jpMyDesign = new JPanel();
  private SVGApplet studio;
  private BorderLayout layout4Shapes = new BorderLayout();
  
  /**
   * 
   * @param studio
   */
  public MainTabSet(SVGApplet studio)   {
    try {
      this.studio=studio;
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
    this.setTabPlacement(JTabbedPane.TOP);
    this.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    
    this.addTab("Property", jpPropertySheet);
    this.setToolTipTextAt(0,"Property of Selected Item");
    
    this.addTab("Cliparts", jpCliparts);
    this.setToolTipTextAt(1,"Draw Clipart");
    
    this.addTab("Catalogue", jpProductCatalogue);
    this.setToolTipTextAt(2,"Select Product");
    
    this.addTab("Save / Save-As", jpSaveNdSaveAsDesign);
    this.setToolTipTextAt(3,"Save Design");
    
    this.addTab("My Graphics", jpMyGraphics);
    
    this.setToolTipTextAt(4,"Add and Use Images");
    
    this.addTab("My Design", jpMyDesign);
    this.setToolTipTextAt(5,"My Designs");
  }
  
}