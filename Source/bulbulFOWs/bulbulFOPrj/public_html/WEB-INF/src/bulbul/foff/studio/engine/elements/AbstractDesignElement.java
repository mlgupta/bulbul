package bulbul.foff.studio.engine.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.net.URL;
import java.net.URLEncoder;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import bulbul.foff.studio.engine.Studio;
import bulbul.foff.studio.common.Constants;
import bulbul.foff.studio.common.DesignElementProperty;
import bulbul.foff.studio.engine.handlers.DesignElementMouseHandler;

import java.util.Random;


/**
 * @author Sudher V. Pujar
 * @version 1.0 dd-mm-yyyy
 * @description
 */
public abstract class AbstractDesignElement extends JPanel {

  private BufferedImage bi;    
  private AffineTransform transform = null;
  private String baseURL = null;
  private boolean selected=false;
  
  private boolean elementNew=true;
  
  protected int elementId=-1;
  
  protected int elementType=-1;
  
  protected double scale=1.0;
  protected double lastScale=1.0;
  
  protected Studio studio = null;
  protected String actionURL = null;
  
  //Element Primary Properties
  protected final Rectangle elementBounds=new Rectangle();
  protected Hashtable style=new Hashtable(10);
  protected double rotate=0.0;
  
  //Element Secondary or Specific Properties
  protected final Hashtable specificProperties=new Hashtable(10);

  public static final Border SELECTED_BORDER=BorderFactory.createLineBorder(new Color(0, 0, 255), 1);
  public static final Border UNSELECTED_BORDER=BorderFactory.createEmptyBorder();

  public AbstractDesignElement(Studio studio){
    this.studio=studio;
    this.transform = AffineTransform.getTranslateInstance(0, 0);
    this.transform.concatenate(AffineTransform.getScaleInstance(1, 1));
    this.baseURL = studio.getBaseURL();
    this.elementId=createElementId();
    setOpaque(false);
    setBackground(new Color(0, 0, 0, 0));
    setBorder(SELECTED_BORDER);
    
    DesignElementMouseHandler mouseHandler = new DesignElementMouseHandler();
    addMouseMotionListener(mouseHandler);
    addMouseListener(mouseHandler);
  }
  
  private void setScaledLocation() {

    double x = (getLocation().x * (scale / lastScale));
    double y = (getLocation().y * (scale / lastScale));

    int margin = (int)(Constants.CANVAS_MARGIN * (scale / lastScale));

    if ((int)x < margin) {
      x = margin;
    }
    if ((int)y < margin) {
      y = margin;
    }
    setLocation((int)x, (int)y);
  }
  
  private void setStyle(String inStyle) {
    if(inStyle!=null && inStyle.trim().length()>0 ){
      String[] styleArray=inStyle.split(";");
      for(int i=0;i<styleArray.length;i++){
        String[] property=styleArray[i].split(":");
        String propertyName=property[0];
        String propertyValue=property[1];
        if(propertyValue.trim().length()>0){
          if(propertyValue.equals("default")){
            style.remove(propertyName);
          }
          else{
            style.put(propertyName,propertyValue); 
          }
        }
      }
    }
  }
  
  private final void setScale(double scale) {
    this.lastScale = this.scale;
    this.scale = scale;
    setScaledLocation();
  }
  
  protected void scaleImage() {
    Object[] args=null;
    try {
      args=new Object[1];
      
      //Tracking changes to Elements in the Cavanas  - Setting Processing Label
      args[0]=Boolean.valueOf(true);
      studio.getBrowserWindow().call("studioProcessing",args);
      String imageURL =(new URL(baseURL + actionURL)).toExternalForm();
      imageURL+= "?" + DesignElementProperty.WIDTH + "=" + elementBounds.getSize().width ;
      imageURL+= "&" + DesignElementProperty.HEIGHT + "=" + elementBounds.getSize().height;
      imageURL+= "&" + DesignElementProperty.ROTATE + "=" + rotate;
      imageURL+= "&" + DesignElementProperty.SCALE + "=" + scale;
      String styleString=getStyle();
      if(styleString!=null){
        imageURL+= "&" + DesignElementProperty.STYLE + "="+URLEncoder.encode(styleString,"UTF-8");
      }
     
      Enumeration keys=specificProperties.keys();
      
      while(keys.hasMoreElements()){
        String propertyName=(String)keys.nextElement();
        String propertyValue=(String)specificProperties.get(propertyName);
        System.out.println("propertyName : " + propertyName);
        System.out.println("propertyValue : " + propertyValue);
        if(propertyValue!=null && propertyValue.trim().length()>0){
          imageURL+= "&" + URLEncoder.encode(propertyName,"UTF-8") + "="+URLEncoder.encode(propertyValue,"UTF-8");
        }
      }
      System.out.println("imageURL : "+imageURL);
      ImageIcon imageicon = new ImageIcon(new URL(imageURL));
      Image image = imageicon.getImage();
      MediaTracker mt = new MediaTracker(this);
      mt.addImage(image, 1);
      try {
        mt.waitForAll();
      } catch (Exception e) {
        System.out.println("Exception while loading image.");
      }

      if (image.getWidth(this) == -1) {
        System.out.println("no  file");        
      }else{
        Dimension imageSize=new Dimension(image.getWidth(this), image.getHeight(this));
        setSize(imageSize);
        setPreferredSize(imageSize);
        setMaximumSize(imageSize);
        setMinimumSize(imageSize);
  
        bi = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_ARGB);
  
        Graphics2D g2D = bi.createGraphics();
  
        if (transform != null) {
          g2D.drawImage(image, transform, this);
        } else {
          g2D.drawImage(image, g2D.getTransform(), this);
        }
      }
      
      //Tracking changes to Elements in the Cavanas  - Clearing Part      
      args[0]=Boolean.valueOf(false);
      studio.getBrowserWindow().call("studioProcessing",args);
       
    } catch (Exception e) {
      e.printStackTrace();
      ;
    }
  }
  
  public String getStyle() {
    String outStyle="";
    Enumeration styleKeys=style.keys();
    if(styleKeys!=null){
      while(styleKeys.hasMoreElements()){
        String propertyName=(String)styleKeys.nextElement();
        String propertyValue=(String)style.get(propertyName);
        if(propertyValue.trim().length()>0){
          outStyle+=propertyName+":"+propertyValue+";";
        }
      }
    }
    outStyle=outStyle.trim();
    if(outStyle.length()<=0){
      outStyle=" ";
    }
    return outStyle;
  }
  
  public double getRotate() {
    return rotate;
  }
  
  
  
  public abstract void accessPropertySheet(boolean show);
  
  public final void paint(Graphics g) {
    super.paintComponent(g);
    if (bi != null) {
      Graphics2D g2D = (Graphics2D)g;
      if (transform != null) {
        g2D.drawImage(bi, transform, this);
      } else {
        g2D.drawImage(bi, g2D.getTransform(), this);
      }
    }
    paintBorder(g);
  }
  
  public final void createElement(int width, int height, double scale,String style) {
    setBorder(SELECTED_BORDER);
    elementBounds.setSize(new Dimension(width, height));
    setScale(scale);
    setStyle(style);
    scaleImage();    
  }
  
  public final void createElement(double scale,String style) {
    setBorder(SELECTED_BORDER);
    setScale(scale);
    setStyle(style);
    scaleImage();
  }
  
  public final void setProperties(int width, int height ) {
    this.elementBounds.setSize(new Dimension(width, height));   
    scaleImage();
  }
  
  public final void setProperties(String style,double rotate ) {
    this.rotate=rotate;
    setStyle(style);           
    scaleImage();
  }
  
  public final void setProperties(String style) {
    setStyle(style);           
    scaleImage();
  }
  
  public final void setRotate(double rotate ) {
    this.rotate=rotate;  
    scaleImage();
  }
  
  public final void renderZoom(double scale){
    setScale(scale);
    scaleImage();
  }
  
  public final double getScale() {
    return scale;
  }

  public final double getLastScale() {
    return lastScale;
  }

  public final Studio getStudio() {
    return studio;
  }

  public final Rectangle getElementBounds() {
    return elementBounds;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }
  
  public Hashtable getSpecificProperties(){
   return specificProperties;
  }

  public void setElementId(int elementId){
    this.elementId = elementId;
  }
  
  public int getElementId() {
    return elementId;
  }

  public int getElementType() {
    return elementType;
  }

  public void setElementNew(boolean elementNew) {
    this.elementNew = elementNew;
  }

  public boolean isElementNew() {
    return elementNew;
  }
  
  public static int createElementId(){
    return (new Random()).nextInt();  
  }
}
