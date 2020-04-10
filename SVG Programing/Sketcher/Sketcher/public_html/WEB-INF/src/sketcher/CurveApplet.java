package sketcher;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class CurveApplet extends JApplet  {
  
  //Points for Quadratic Curve
  Point2D.Double startQ = new Point2D.Double(50,150);
  Point2D.Double endQ = new Point2D.Double(150,150);
  Point2D.Double control = new Point2D.Double(80,100);
  
  //Points for Cubic Curve
  Point2D.Double startC = new Point2D.Double(50,300);
  Point2D.Double endC = new Point2D.Double(150,300);
  Point2D.Double controlStart = new Point2D.Double(80,250);
  Point2D.Double controlEnd = new Point2D.Double(160,250);
  
  QuadCurve2D.Double quadCurve;
  CubicCurve2D.Double cubicCurve;
  CurvePane pane = new CurvePane();
  
  //Markers for control points
  Marker ctrlQuad = new Marker(control);
  Marker ctrlCubic1 = new Marker(controlStart);
  Marker ctrlCubic2 = new Marker(controlEnd);
  
  
  public void init(){
    pane = new CurvePane();
    Container content = getContentPane();
    content.add(pane); 
    
    MouseHandler handler = new MouseHandler();
    pane.addMouseListener(handler);
    pane.addMouseMotionListener(handler);
    
  }
  
  
  class CurvePane extends JComponent{
    public CurvePane(){
      quadCurve = new QuadCurve2D.Double(startQ.x,startQ.y,control.x,control.y,endQ.x,endQ.y);
      cubicCurve = new CubicCurve2D.Double(startC.x,startC.y,controlStart.x,controlStart.y,controlEnd.x,controlEnd.y,endC.x,endC.y);
    }
    
    public void paint(Graphics g){
      Graphics2D g2D = (Graphics2D)g;
      
      // Update the curves with the current control point posistions
      quadCurve.ctrlx = ctrlQuad.getCenter().x;
      quadCurve.ctrly = ctrlQuad.getCenter().y;
      
      cubicCurve.ctrlx1 = ctrlCubic1.getCenter().x;
      cubicCurve.ctrly1 = ctrlCubic1.getCenter().y;
      
      cubicCurve.ctrlx2 = ctrlCubic2.getCenter().x;
      cubicCurve.ctrly2 = ctrlCubic2.getCenter().y;
      
      g2D.setPaint(Color.blue); 
      g2D.draw(quadCurve);
      g2D.draw(cubicCurve);
      
      g2D.setPaint(Color.red); 
      ctrlQuad.draw(g2D);
      ctrlCubic1.draw(g2D);
      ctrlCubic2.draw(g2D);
      
      Line2D.Double tangent = new Line2D.Double(startQ, ctrlQuad.getCenter());
      g2D.draw(tangent);
      
      tangent = new Line2D.Double(endQ, ctrlQuad.getCenter());
      g2D.draw(tangent);
      
      tangent = new Line2D.Double(startC, ctrlCubic1.getCenter());
      g2D.draw(tangent);
      
      tangent = new Line2D.Double(endC, ctrlCubic1.getCenter());
      g2D.draw(tangent);
      
      tangent = new Line2D.Double(startC, ctrlCubic2.getCenter());
      g2D.draw(tangent);
      
      tangent = new Line2D.Double(endC, ctrlCubic2.getCenter());
      g2D.draw(tangent);
    }
    
  }
  
  
  
  class Marker {
    Ellipse2D.Double circle;
    Point2D.Double center;
    static final double radius=3;
    
    public Marker(Point2D.Double control){
      center = control;
      circle = new Ellipse2D.Double(control.x-radius,control.y-radius,2.0*radius,2.0*radius);
    }
    public void draw(Graphics2D g2D){
      g2D.draw(circle);
    }
    
    Point2D.Double getCenter(){
      return center;
    }
    
    public boolean contains(double x, double y){
      return circle.contains(x,y);
    }
    
    public void setLocation(double x, double y){
      center.x=x;
      center.y=y;
      circle.x=x-radius;
      circle.y=y-radius;
    }
    
  }
  
  class MouseHandler extends MouseInputAdapter{
      Marker selected=null;
      //MouseInputAdapter a ;
      
      public void mouseMoved(MouseEvent e){
        if(ctrlQuad.contains(e.getX(),e.getY())){
          pane.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));       
        }else if(ctrlCubic1.contains(e.getX(),e.getY())){
          pane.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));       
        }else if(ctrlCubic2.contains(e.getX(),e.getY())){
          pane.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));       
        }else{
          pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));       
        }
      }
      public void mousePressed(MouseEvent e){
        //check if the Cursor is inside any marker
        if(ctrlQuad.contains(e.getX(),e.getY())){
          selected=ctrlQuad;
        }else if(ctrlCubic1.contains(e.getX(),e.getY())){
          selected = ctrlCubic1;
        }else if(ctrlCubic2.contains(e.getX(),e.getY())){
          selected = ctrlCubic2;
        }
      }
      
      public void mouseReleased(MouseEvent e){
        selected = null;
      }
      
      public void mouseDragged(MouseEvent e){
        if(selected!=null){
          selected.setLocation(e.getX(),e.getY());
          pane.repaint();
        }
      }
  }
}