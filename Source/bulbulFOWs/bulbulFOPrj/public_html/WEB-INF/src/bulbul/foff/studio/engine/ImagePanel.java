package bulbul.foff.studio.engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;


import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private BufferedImage bi=null;
    private Dimension imageSize=null;
    private AffineTransform affineTransform=null;
    private double scale=1.0;
    private double lastScale=1.0;
    
    public ImagePanel(){
      super();
    }
    
    public void setAffineTransform(AffineTransform affineTransform) {
      this.affineTransform = affineTransform;
    }

    public void setImage(Image image) {
      try {
        if (image!=null) {
          ImageIcon imageicon= new ImageIcon(image);
          
          
          MediaTracker mt = new MediaTracker(this);
          mt.addImage(image, 1); 
          
          try {
            mt.waitForAll();
          } catch (Exception e) {
            System.out.println("Exception while loading image.");
          }
    
          if (image.getWidth(this) == -1) {
            System.out.println("no gif file");          
          }else{
            imageSize=new Dimension(imageicon.getIconWidth(),imageicon.getIconHeight());
            bi = new BufferedImage(image.getWidth(this), image.getHeight(this),
                BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D g2D = bi.createGraphics();
            
            if(affineTransform!=null) {
              g2D.drawImage(image,affineTransform,this);
            }else{
              g2D.drawImage(image,g2D.getTransform(),this);
            }
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    public void paint(Graphics g){
      super.paintComponent(g);
      if(bi!=null){
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(bi, 0, 0, this);   
        if(affineTransform!=null) {
          g2D.drawImage(bi,affineTransform,this);
        }else{
          g2D.drawImage(bi,g2D.getTransform(),this);
        }
      }
    }
    
    public Dimension getScaledSize(){
      double width=0;
      double height=0;
      if (imageSize!=null){        
        width=imageSize.width*scale;
        height=imageSize.height*scale;
      }
      return new Dimension((int)width, (int)height);
    }

    public void setScale(double scale) {
      this.lastScale=this.scale;
      this.scale = scale;
    }

    public double getScale() {
      return scale;
    }
    
    public double getLastScale() {
      return lastScale;
    }
  
  }
