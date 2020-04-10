package sketcher;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;

public class SketchView extends JComponent implements Observer  {
  private Sketcher theApp;
  public SketchView(Sketcher theApp) {
    this.theApp=theApp;
  }
  
  public void update(Observable o, Object rectangle){
    
  } 
  
  public void paint(Graphics g){
    Graphics2D g2D= (Graphics2D)g;
    g2D.setPaint(Color.red);
    
    /*
    g2D.draw3DRect(50,50,150,100,true);
    g2D.drawString("A Nice 3D Rectangle",60,100);
    */
    
    /*
    Point2D.Float p1 = new Point2D.Float(50.0f,10.0f);
    
    float width1=60;
    float height1=80;
    
    Rectangle2D.Float rect = new Rectangle2D.Float(p1.x,p1.y,width1,height1);
    g2D.draw(rect);
    
    Point2D.Float p2 = new Point2D.Float(150.0f,100.0f);
    float width2=width1+30;
    float height2=height1+40;
    
    g2D.draw(new Rectangle2D.Float((float)(p2.getX()),(float)(p2.getY()),width2,height2));
    
    g2D.setPaint(Color.blue);
    
    Line2D.Float line = new Line2D.Float(p1,p2);
    g2D.draw(line);
    
    p1.setLocation(p1.x+width1,p1.y);
    p2.setLocation(p2.x+width2,p2.y);
    g2D.draw(new Line2D.Float(p1,p2));
    
    p1.setLocation(p1.x,p1.y+height1);
    p2.setLocation(p2.x,p2.y+height2);
    g2D.draw(new Line2D.Float(p1,p2));
    
    p1.setLocation(p1.x-width1,p1.y);
    p2.setLocation(p2.x-width2,p2.y);
    g2D.draw(new Line2D.Float(p1,p2));
    
    p1.setLocation(p1.x,p1.y-height1);
    p2.setLocation(p2.x,p2.y-height2);
    g2D.draw(new Line2D.Float(p1,p2));
    
    g2D.drawString("Lines and Rectangles",60,250);
    */
    
    Point2D.Double position = new Point2D.Double(50,10);
    double width = 150;
    double height = 100;
    double start = 30;
    double extent = 120;
    double diameter = 40;
    
    Arc2D.Double top = new Arc2D.Double(position.x,position.y, width, height, start, extent, Arc2D.OPEN);
    
    Arc2D.Double bottom = new Arc2D.Double(position.x,position.y-height+diameter, width, height, start+180, extent, Arc2D.OPEN);

    Ellipse2D.Double circle1 = new Ellipse2D.Double(position.x+width/2-diameter/2,position.y,diameter,diameter);    
    
    Ellipse2D.Double circle2 = new Ellipse2D.Double(position.x+width/2-diameter/4,position.y+diameter/4,diameter/2,diameter/2);    
    
    g2D.setPaint(Color.black);
    g2D.draw(top);
    g2D.draw(bottom);
    
    g2D.setPaint(Color.blue);
    g2D.draw(circle1);
    g2D.draw(circle2);
    
    g2D.drawString("Arc and ellipse",80,100);
    
    
    
    
    
    
    
    
  } 
}