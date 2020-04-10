package bulbul.foff.studio.engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;


import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class OrientationPanel extends JPanel{
  
  private final Rectangle backgroundPanelBounds=new Rectangle(0, 0, 0, 0); 
  private final Rectangle canvasBounds=new Rectangle(0, 0, 0, 0); 
  
  private double scale=1.0;
  private double lastScale=1.0;
  private JPanel centerPanel=new JPanel();
  private ImagePanel backgroundPanel; 
  private DesignCanvas canvas;
  private Studio studio=null;
  public OrientationPanel(Studio studio){
    super(); 
    this.studio=studio;
    jbinit();
    changeBounds();
  }
  
  private void jbinit(){
    backgroundPanel= new ImagePanel();
    canvas= new DesignCanvas(studio);
    
    centerPanel.setLayout(null);
    centerPanel.setSize(new Dimension(getSize().width, getSize().height));
    centerPanel.setOpaque(false);
    centerPanel.setBackground(new Color(0,0,0,0));
    centerPanel.add(canvas);
    centerPanel.add(backgroundPanel);
    centerPanel.addComponentListener(new ComponentAdapter(){
          public void componentResized(ComponentEvent evt){
            changeBounds();
          }
    });
    
    this.setOpaque(false);
    this.setBackground(new Color(0,0,0,0));
    this.setLayout(new BorderLayout()); 
    this.add(centerPanel,BorderLayout.CENTER); 
  }
   
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    backgroundPanel.setBounds(backgroundPanelBounds);
    canvas.setBounds(canvasBounds);
  }
  
  public void setProductImage(Image productImage){
    backgroundPanel.setImage(productImage);
  }
  
  private synchronized void changeBounds(){
    
    int centerPanelWidth=centerPanel.getSize().width;
    int centerPanelHeight=centerPanel.getSize().height;
    
    System.out.println("centerPanelWidth,centerPanelHeight : ("+ centerPanelWidth + "," + centerPanelHeight + ")");
    
    int bgPanelX=0;
    int bgPanelY=0;
    int bgPanelWidth=backgroundPanel.getScaledSize().width;
    int bgPanelHeight=backgroundPanel.getScaledSize().height;
    
    System.out.println("bgPanelWidth,bgPanelHeight : ("+ bgPanelWidth + "," +bgPanelHeight + ")" );
    
    bgPanelX=centerPanelWidth/2-bgPanelWidth/2;
    bgPanelY=centerPanelHeight/2-bgPanelHeight/2;
    
    System.out.println("bgPanelX,bgPanelY : ("+ bgPanelX + "," +bgPanelY + ")" );
    
    backgroundPanelBounds.x=bgPanelX;
    backgroundPanelBounds.y=bgPanelY;
    backgroundPanelBounds.width=bgPanelWidth;
    backgroundPanelBounds.height=bgPanelHeight;
    
    backgroundPanel.setBounds(backgroundPanelBounds);

    
    centerPanel.revalidate();
    
    //sets the rendering transform
    AffineTransform affineTransform4BgPanel=AffineTransform.getTranslateInstance(0, 0);
    affineTransform4BgPanel.concatenate(AffineTransform.getScaleInstance(backgroundPanel.getScale(), backgroundPanel.getScale()));
    backgroundPanel.setAffineTransform(affineTransform4BgPanel);
    
    int canvasX=0;
    int canvasY=0;
    int canvasWidth=canvas.getScaledCanvasSize().width;
    int canvasHeight=canvas.getScaledCanvasSize().height;
    
    System.out.println("canvasWidth,canvasHeight : ("+ canvasWidth + "," +canvasHeight + ")" );
    
    canvasX=centerPanelWidth/2-canvasWidth/2;
    canvasY=centerPanelHeight/2-canvasHeight/2;
    
    System.out.println("canvasX,canvasY : ("+ canvasX + "," +canvasY + ")" );
    
    canvasBounds.x=canvasX;
    canvasBounds.y=canvasY;
    canvasBounds.width=canvasWidth;
    canvasBounds.height=canvasHeight;
    
    canvas.setBounds(canvasBounds);
    
    centerPanel.revalidate();
  }
  
  public void renderZoom(double scale){
    this.lastScale=this.scale;
    this.scale = scale;
    backgroundPanel.setScale(scale);
    canvas.setScale(scale);
    changeBounds();
    backgroundPanel.repaint();
    backgroundPanel.revalidate();
  }  
  
  public void deleteElement(String customerDesignDetailTblPk){
    canvas.deleteElement(customerDesignDetailTblPk);
  }
  
  public void addClipartElement(String contentOId,String contentType,String contentSize,String dataSourceKey,int width,int height){
    canvas.addClipartElement(contentOId,contentType,contentSize,dataSourceKey,width,height);
  }
  
  public void addImageElement(String customerGraphicsTblPk,String contentOId,String contentType,String contentSize,String dataSourceKey,int width,int height){
    canvas.addImageElement(customerGraphicsTblPk,contentOId,contentType,contentSize,dataSourceKey,width,height);
  }
  
  public void setProperties(int width, int height){
    canvas.setProperties(width, height);
  }
   
  public void setProperties(String style){
    canvas.setProperties(style);
  }
  
  public void setRotate(double rotate){
    canvas.setRotate(rotate);
  }
  
  public void addText(String text,String style){
    canvas.addTextElement(text,style);
  }
  public void editText(String text){
    canvas.editTextElement(text);
  }
  public double getScale() {
    return scale;
  }

  public double getLastScale() {
    return lastScale;
  }
  
  public void refresh(){
    canvas.clearSelectedElement(null);
  }

  public DesignCanvas getCanvas() {
    return canvas;
  }
  
  public void setCanvasSize(Dimension canvasSize) {
    canvas.setCanvasSize(canvasSize);
  }
}
