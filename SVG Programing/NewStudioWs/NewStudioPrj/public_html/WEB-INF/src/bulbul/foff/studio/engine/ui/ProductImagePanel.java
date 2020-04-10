package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Sudheer V. Pujar
 */
public class ProductImagePanel extends JPanel  {
  
  private Thread paintManager=null;
  private SVGApplet studio ;
  private Image image;
  private AffineTransform affineTransform;
  private ProductImagePanel imagePanel=this;
  private LinkedList paintListeners=new LinkedList();
  
  private boolean shouldRepaint=false;
  private boolean repaintEnabled=true;
  
  private double scale=1.0;
  private double lastScale=1.0;
  /**
   * 
   * @param image
   * @param studio
   */
  public ProductImagePanel(SVGApplet studio,Image image) {
    this.studio=studio;
    this.image=image;
    setBorder(new LineBorder(new Color((float)0.5,(float)0.5,(float)0.5)));
    paintManager=new Thread(){
			public void run() {
				while(true){
					try{
						sleep((long)75);
					}catch (Exception ex){}
					if(shouldRepaint){
					    repaint();
						synchronized(imagePanel){
							shouldRepaint=false;
						}
					}
				}
			}
		};
		paintManager.start();
  }
  
  /**
   * 
   * @description 
   * @param g
   */
  protected synchronized void notifyPaintListeners(Graphics g){
    if(isRepaintEnabled()){
      Iterator it=paintListeners.iterator();
      while(it.hasNext()){
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }
	  }
  }
  
  /**
   * 
   * @description 
   * @param g
   */
  public void paintComponent(Graphics g){
  	super.paintComponent(g);
		notifyPaintListeners(g);
  }
  
  public synchronized void addPaintListener(SVGPaintListener paintListener, boolean makeRepaint){
		if(paintListener!=null && paintListener instanceof SVGPaintListener){
			paintListeners.add(paintListener);
			if(makeRepaint)shouldRepaint=true;
		}
	}
  
  public synchronized void removePaintListener(SVGPaintListener paintListener, boolean makeRepaint){
		paintListeners.remove(paintListener);
		if(makeRepaint)shouldRepaint=true;
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isRepaintEnabled() {
    return repaintEnabled;
  }

  /**
   * 
   * @description 
   */
	public void repaintImagePanel(){
	  synchronized(this){shouldRepaint=true;}
	}

  /**
   * 
   * @description 
   */
	public void delayedRepaint(){
		synchronized(this){shouldRepaint=true;}
	}
  
  /**
   * 
   * @param affineTransform
   */
  public void setAffineTransform(AffineTransform affineTransform) {
    this.affineTransform = affineTransform;
  }

  /**
   * 
   * @return 
   */
  public Image getImage() {
    return image;
  }

  /**
   * 
   * @return 
   */
  public SVGApplet getStudio() {
    return studio;
  }
  
  /**
   * 
   * @param g
   */
  public void paint(Graphics g){
    Graphics2D g2D = (Graphics2D)g; 
    if(affineTransform!=null) {
      g2D.drawImage(this.image,affineTransform,this);
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Dimension getScaledImagePanelSize(){
    if (this.image!=null){
      ImageIcon imageIcon=new ImageIcon(this.image);
      if(imageIcon!=null){
          int width=0, height=0;
          width=(int)(imageIcon.getIconWidth()*scale);
          height=(int)(imageIcon.getIconHeight()*scale);
          return new Dimension(width, height);
      }
    }
		return new Dimension(0,0);
  }


  /**
   * 
   * @description 
   * @param scale
   */
  public void setScale(double scale) {
    this.lastScale=this.scale;
    this.scale = scale;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public double getScale() {
    return scale;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public double getLastScale() {
    return lastScale;
  }
  
   

}