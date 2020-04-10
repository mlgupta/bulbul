package sketcher;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JApplet;

public class Sketcher extends JApplet  {
  private static SketchFrame window;
  private static Sketcher theApp;
  public Sketcher() {
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    theApp = new Sketcher();
    theApp.init();
  }
  
  public void init(){
    window = new SketchFrame("Sketcher");
    Toolkit theKit = window.getToolkit();
    Dimension wndSize = theKit.getScreenSize();
    
    //Set the position to screen center & size to half screen size
    window.setBounds(wndSize.width/4, wndSize.height/4, wndSize.width/2, wndSize.height/2);
    
    window.addWindowListener(new WindowHandler());
    
    window.setVisible(true);
  }
  
  //Handler class for window events
  class WindowHandler extends WindowAdapter{
    public void windowClosing(WindowEvent e){
      window.dispose();
      System.exit(0);
    }
  }
}