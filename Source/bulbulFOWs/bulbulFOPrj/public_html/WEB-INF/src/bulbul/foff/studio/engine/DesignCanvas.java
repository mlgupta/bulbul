package bulbul.foff.studio.engine;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bulbul.foff.studio.engine.elements.AbstractDesignElement;
import bulbul.foff.studio.engine.elements.ClipartElement;
import bulbul.foff.studio.engine.elements.ImageElement;
import bulbul.foff.studio.engine.elements.TextElement;
import bulbul.foff.studio.engine.handlers.DesignCanvasMouseHandler;


public class DesignCanvas extends JPanel {
  private double scale = 1.0;

  private double lastScale = 1.0;

  private Dimension canvasSize;
  
  private AbstractDesignElement selectedElement = null;

  private Studio studio = null;

  public DesignCanvas(Studio studio) {
    setLayout(null);
    setOpaque(false);
    setBackground(new Color(0, 0, 0, 0));
    setBorder(BorderFactory.createEmptyBorder());
    this.studio = studio;
    this.canvasSize=new Dimension(0,0);
    DesignCanvasMouseHandler mouseHandler = new DesignCanvasMouseHandler();
    addMouseMotionListener(mouseHandler);
    addMouseListener(mouseHandler);
  }

  public void clearSelectedElement(AbstractDesignElement designElement) {
    for (int index = 0; index < getComponentCount(); index++) {
      AbstractDesignElement tempDesignElement =(AbstractDesignElement)getComponent(index);
      tempDesignElement.setBorder(BorderFactory.createEmptyBorder());
      tempDesignElement.setSelected(false);
    }

    if (selectedElement != null && !(selectedElement.equals(designElement))) {
      selectedElement.accessPropertySheet(false);
    }
    selectedElement = null;
  }

  public void deleteElement(String customerDesignDetailTblPk) {
    if (selectedElement != null) {
      if((!selectedElement.isElementNew()) && (!customerDesignDetailTblPk.equals("-1"))){
        int elementId=selectedElement.getElementId();
        Object[] args=null;
        args=new Object[2];
        args[0]=customerDesignDetailTblPk;
        args[1]=Integer.toString(elementId);
        studio.getBrowserWindow().call("deleteElementServer",args);
      }
      remove(selectedElement);
      clearSelectedElement(null);
      revalidate();
      repaint();
    }
  }
  
  public void setProperties(int width, int height) {
     if(selectedElement!=null){
       selectedElement.setProperties(width,height);
       selectedElement.accessPropertySheet(true);
       revalidate();
       repaint();    
     }
  }
  
  public void setProperties(String style) {
     if(selectedElement!=null){
       selectedElement.setProperties(style);
       selectedElement.accessPropertySheet(true);
       revalidate();
       repaint();    
     }
  }
  
  public void setRotate(double rotate) {
     if(selectedElement!=null){
       selectedElement.setRotate(rotate);
       selectedElement.accessPropertySheet(true);
       revalidate();
       repaint();    
     }
  }
  
  public void addClipartElement(String contentOId,String contentType,String contentSize,String dataSourceKey, int width, int height) {
    try {
      clearSelectedElement(null);
      ClipartElement clipartElement = new ClipartElement(studio);
      clipartElement.setClipartId(contentOId,contentType,contentSize,dataSourceKey);
      clipartElement.createElement(width, height,scale,null);
      add(clipartElement);
      setSelectedElement(clipartElement); 
      selectedElement.accessPropertySheet(true);

    } catch (Exception e) {
      ;//e.printStackTrace();
    }

    revalidate();
    repaint();
  }
  
  public void addImageElement(String customerGraphicsTblPk,String contentOId,String contentType,String contentSize,String dataSourceKey, int width, int height) {
    try {
      clearSelectedElement(null);
      ImageElement imageElement = new ImageElement(studio);
      imageElement.setCustomImageId(customerGraphicsTblPk,contentOId,contentType,contentSize,dataSourceKey);
      imageElement.createElement(width, height,scale,null);
      add(imageElement);
      setSelectedElement(imageElement); 
      selectedElement.accessPropertySheet(true);
    } catch (Exception e) {
      ;//e.printStackTrace();
    }

    revalidate();
    repaint();
  }
  
  public void addTextElement(String text,String style){
    try {
      clearSelectedElement(null);
      TextElement textElement = new TextElement(studio,text);
      textElement.createElement(scale,style);
      add(textElement);
      setSelectedElement(textElement);
      selectedElement.accessPropertySheet(true);

    } catch (Exception e) {
      ;//e.printStackTrace();
    }

    revalidate();
    repaint();
  }
  
  public void editTextElement(String text){
    if(selectedElement!=null && selectedElement instanceof TextElement ){
      ((TextElement)selectedElement).setText(text);
      selectedElement.accessPropertySheet(true);
      revalidate();
      repaint();    
    }
  }

  public Dimension getScaledCanvasSize() {
    double width = 0;
    double height = 0;
    width = canvasSize.getWidth() * scale;
    height = canvasSize.getHeight() * scale;
    return new Dimension((int)width, (int)height);
  }
  
  public void setCanvasSize(Dimension canvasSize) {
    this.canvasSize=canvasSize;
  }

  public void setScale(double scale) {
    this.lastScale = this.scale;
    this.scale = scale;
    for(int index=0; index<getComponentCount();index++){
       ((AbstractDesignElement)getComponent(index)).renderZoom(scale);
    }
  }
  
  
  public double getScale() {
    return scale;
  }

  public double getLastScale() {
    return lastScale;
  }
  public Studio getStudio() {
    return studio;
  }

  public void setSelectedElement(AbstractDesignElement selectedElement) {
    selectedElement.setSelected(true);
    this.selectedElement = selectedElement;    
  }

  public AbstractDesignElement getSelectedElement() {
    return selectedElement;
  }

 
}
