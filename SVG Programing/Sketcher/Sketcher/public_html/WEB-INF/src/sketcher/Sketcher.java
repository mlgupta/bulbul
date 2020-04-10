package sketcher;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observer;
import javax.swing.JApplet;

public class Sketcher extends JApplet  {
  private static SketchFrame window;
  private static Sketcher theApp;
  private SketchModel sketch;
  private SketchView view;
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
    window = new SketchFrame("Sketcher",this);
    Toolkit theKit = window.getToolkit();
    Dimension wndSize = theKit.getScreenSize();
    
    //Set the position to screen center & size to half screen size
    window.setBounds(wndSize.width/6, wndSize.height/6, 2*wndSize.width/3, 2*wndSize.height/3);
    window.addWindowListener(new WindowHandler());
    sketch= new SketchModel();
    view = new SketchView(this);
    sketch.addObserver((Observer)view);
    window.getContentPane().add(view,BorderLayout.CENTER);
    
    window.setVisible(true);
    //window.pack();
  }
  
  //Handler class for window events
  class WindowHandler extends WindowAdapter{
    public void windowClosing(WindowEvent e){
      window.dispose();
      System.exit(0);
    }
  }
  
  public SketchFrame getWindow(){
    return window;
  }
  
  public SketchModel getModel(){
    return sketch;
  }
  
  public SketchView getVeiw(){
    return view;
  }
}