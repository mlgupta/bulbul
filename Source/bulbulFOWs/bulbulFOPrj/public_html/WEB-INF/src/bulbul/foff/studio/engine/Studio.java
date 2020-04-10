package bulbul.foff.studio.engine;

import bulbul.foff.studio.common.DesignElementType;
import bulbul.foff.studio.engine.elements.AbstractDesignElement;

import bulbul.foff.studio.engine.elements.ClipartElement;

import bulbul.foff.studio.engine.elements.ImageElement;

import bulbul.foff.studio.engine.elements.TextElement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.net.URL;
import java.net.URLEncoder;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;

import netscape.javascript.JSObject;


public class Studio extends JApplet {
  private JPanel centerPanel = new JPanel();
  
  private String selectedPrintableArea=null;
  private String lastSelectedPrintableArea=null;
  
  private Hashtable htprintableAreas=new Hashtable();
  private String baseURL=null;
  private JSObject browserWindow = null;
  
  private boolean designSaved=false;
  
  private void jbInit() throws Exception {
    browserWindow = JSObject.getWindow(this);
    
    baseURL=getDocumentBase().toString();
    baseURL=baseURL.substring(0,baseURL.toString().lastIndexOf("/")+1);
    
    System.out.println("baseURL in Studio : " + baseURL);
    
    centerPanel.setLayout(new BorderLayout());
    centerPanel.setOpaque(false);
    centerPanel.setBackground(new Color(0,0,0,0));
    
    this.addKeyListener(new StudioKeyListener());
    this.setBackground(new Color(255,255,255,255));
    this.getContentPane().setBackground(new Color(255,255,255,255));
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    
  }
  
  public void init() {
    try {
      jbInit();
      getBrowserWindow().call("initializeProductList",null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void updatePrintableArea(String uniqueNumberString,
                                  String customerDesignDetailPkString,
                                  String detailDelimiter){
      
    try {
//      System.out.println("uniqueNumberString : " + uniqueNumberString); 
//      System.out.println("customerDesignDetailPkString : " + customerDesignDetailPkString); 
//      System.out.println("detailDelimiter : " + detailDelimiter); 
      
      String[] uniqueNumberArray=uniqueNumberString.split(detailDelimiter);
      String[] customerDesignDetailPkArray=customerDesignDetailPkString.split(detailDelimiter);
      
      System.out.println("Array length : " + uniqueNumberArray.length);
      
      for (int index=0; index<uniqueNumberArray.length; index++){
        String uniqueKey=uniqueNumberArray[index];
        PrintableArea   printableArea = (PrintableArea)htprintableAreas.get(uniqueKey);
        if(printableArea!=null){
          printableArea.setCustomerDesignDetailTblPk(customerDesignDetailPkArray[index]);
          OrientationPanel orientationPanel = printableArea.getPrintableArea();
          DesignCanvas canvas = orientationPanel.getCanvas();
          for(int componentIndex=0; componentIndex<canvas.getComponentCount();componentIndex++){
            AbstractDesignElement designElement =(AbstractDesignElement)canvas.getComponent(componentIndex);
            designElement.setElementNew(false);
          }
        }      
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void generatePrintableArea(String uniqueNumber,String printableAreaPk,
              String printableAreaName,
              String contentOId, 
              String contentType, 
              String contentSize, 
              String width,
              String height,
              String merchandisePrintableAreaPk,
              String customerDesignDetailPk,
              String stringSeperator,
              String dataSourceKey
              ){
    try {
    
//      System.out.println("uniqueNumber : " + uniqueNumber); 
//      System.out.println("printableAreaPk : " + printableAreaPk);
//      System.out.println("printableAreaName : " + printableAreaName);
//      System.out.println("contentOId : " + contentOId);
//      System.out.println("contentType : " + contentType);
//      System.out.println("contentSize : " + contentSize);
//      System.out.println("width : " + width);
//      System.out.println("height : " + height);
//      System.out.println("merchandisePrintableAreaPk : " + merchandisePrintableAreaPk);
//      System.out.println("customerDesignDetailPk : " + customerDesignDetailPk);
      
    
      String[] uniqueNumberArray=uniqueNumber.split(stringSeperator);
      String[] printableAreaPkArray=printableAreaPk.split(stringSeperator);
      String[] printableAreaNameArray=printableAreaName.split(stringSeperator);
      String[] contentOIdArray=contentOId.split(stringSeperator);
      String[] contentTypeArray=contentType.split(stringSeperator);
      String[] contentSizeArray=contentSize.split(stringSeperator);
      String[] widthArray=width.split(stringSeperator);
      String[] heightArray=height.split(stringSeperator);
      String[] merchandisePrintableAreaPkArray=merchandisePrintableAreaPk.split(stringSeperator);
      String[] customerDesignDetailPkArray=customerDesignDetailPk.split(stringSeperator);
      
      
      System.out.println("Array length : " + uniqueNumberArray.length);
      
      for (int index=0; index<uniqueNumberArray.length; index++){
        String uniqueKey=uniqueNumberArray[index];
        if(index==0) {
          selectedPrintableArea=uniqueKey;
          lastSelectedPrintableArea=selectedPrintableArea;
        }
        PrintableArea   printableArea = (PrintableArea)htprintableAreas.get(uniqueKey);
        OrientationPanel orientationPanel = null;
        
        if(printableArea==null){
          printableArea=new PrintableArea();
          orientationPanel = new OrientationPanel(this);
        }else{
          orientationPanel = printableArea.getPrintableArea();
        }
        
        printableArea.setPrintableAreaTblPk(printableAreaPkArray[index]);
        printableArea.setPrintableAreaName(printableAreaNameArray[index]);
        printableArea.setContentOId(contentOIdArray[index]);
        printableArea.setContentType(contentTypeArray[index]);
        printableArea.setContentSize(contentSizeArray[index]);
        printableArea.setPrintableAreaSize(new Dimension(Integer.parseInt(widthArray[index]),Integer.parseInt(heightArray[index])));        
        printableArea.setMerchandisePrintableAreaTblPk(merchandisePrintableAreaPkArray[index]);
        printableArea.setCustomerDesignDetailTblPk(customerDesignDetailPkArray[index]);
        
        System.out.println("printableArea.getContentSize() : " +  printableArea.getContentSize()); 
        String actionURL="imageDisplayAction.do";
        String imageURL =(new URL(baseURL + actionURL)).toExternalForm();
        imageURL+= "?dataSourceKey=" + URLEncoder.encode(dataSourceKey,"UTF-8");
        imageURL+= "&imageOid=" + URLEncoder.encode(printableArea.getContentOId(),"UTF-8");
        imageURL+= "&imageContentType=" + URLEncoder.encode(printableArea.getContentType(),"UTF-8");
        imageURL+= "&imageContentSize=" + URLEncoder.encode(printableArea.getContentSize(),"UTF-8");
        
        orientationPanel.setProductImage(new ImageIcon(new URL(imageURL)).getImage());
        orientationPanel.setCanvasSize(printableArea.getPrintableAreaSize());
        DesignCanvas canvas = orientationPanel.getCanvas();
        for(int componentIndex=0; componentIndex<canvas.getComponentCount();componentIndex++){
          AbstractDesignElement designElement =(AbstractDesignElement)canvas.getComponent(componentIndex);
          designElement.setElementNew(true);
        }
        printableArea.setPrintableArea(orientationPanel);
        htprintableAreas.put(uniqueKey,printableArea);
      }
      
      //clearing other printable areas
      Enumeration printableAreaKeys=htprintableAreas.keys();
      while(printableAreaKeys.hasMoreElements()){
        boolean exist=false;
        String nextKey=(String)printableAreaKeys.nextElement();
        for (int index=0; index<uniqueNumberArray.length; index++){
         if(nextKey.equals(uniqueNumberArray[index])){
           exist=true;
           break;
         }
        }
        if(!exist){
          htprintableAreas.remove(nextKey);
        }
      }
      centerPanel.removeAll();
      centerPanel.add(((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea(), BorderLayout.CENTER);
      validate();
      repaint();
      setDesignSaved(false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void renderZoom(double scale){
    OrientationPanel orientationPanel = ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    orientationPanel.renderZoom(scale);
  }
  
  public void deleteElement(){
    PrintableArea printableArea =(PrintableArea)htprintableAreas.get(selectedPrintableArea);
    printableArea.getPrintableArea().deleteElement(printableArea.getCustomerDesignDetailTblPk());
  }
  
  public void selectePrintableArea(String selectedPrintableArea){
    this.lastSelectedPrintableArea=this.selectedPrintableArea;
    this.selectedPrintableArea=selectedPrintableArea;
    
    OrientationPanel newPrintableArea =  ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    OrientationPanel oldPrintableArea =  ((PrintableArea)htprintableAreas.get(lastSelectedPrintableArea)).getPrintableArea();
     
    oldPrintableArea.refresh();
    centerPanel.remove(oldPrintableArea);
    centerPanel.add(newPrintableArea,BorderLayout.CENTER);
    centerPanel.revalidate();
    centerPanel.repaint();    
  }
  
  public double getZoomScale(String selectedPrintableArea){
    OrientationPanel printableArea = ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    return printableArea.getScale();
  }

  public void saveDesign(String saveAs){
    try {
      String printableAreaDelimiter="~~~";
      String elementWiseDelimiter="~~";
      String propertyWiseDelimiter="~";
      
      String uniqueNumberString="";
      String merchandisePrintableAreaPkString="";
      String customerDesignDetailPkString="";
      String elementIdString="";
      String elementTypeString="";
      String posXString="";
      String posYString="";
      String widthString="";
      String heightString="";
      String styleString="";
      String rotateString="";
      String elementNewString="";

      String propertyNameString="";
      String propertyValueString="";
      
      boolean blnSaveAs=Boolean.valueOf(saveAs).booleanValue();
    
      System.out.println("Inside Save Design");
      
      Enumeration keys=htprintableAreas.keys();
      while(keys.hasMoreElements()){
        String uniqueKey=(String)keys.nextElement();
        PrintableArea printableAreaBean = (PrintableArea)htprintableAreas.get(uniqueKey);
        String merchandisePrintableAreaPk=printableAreaBean.getMerchandisePrintableAreaTblPk();
        String customerDesignDetailPk=printableAreaBean.getCustomerDesignDetailTblPk();
        uniqueNumberString+=uniqueKey;
        merchandisePrintableAreaPkString+=merchandisePrintableAreaPk;
        customerDesignDetailPkString+=(blnSaveAs)?"-1":customerDesignDetailPk;
        System.out.println(" merchandisePrintableAreaPk : " + merchandisePrintableAreaPk);
        OrientationPanel printableArea = printableAreaBean.getPrintableArea();
        DesignCanvas canvas = printableArea.getCanvas();
        for(int index=0; index<canvas.getComponentCount();index++){
          AbstractDesignElement designElement =(AbstractDesignElement)canvas.getComponent(index);
          Rectangle elementBounds=designElement.getElementBounds();
          
          System.out.println(" element id  : " + designElement.getElementId());
          System.out.println(" element type : " + designElement.getElementType());
          System.out.println(" X : " + elementBounds.getX());
          System.out.println(" Y : " + elementBounds.getY());
          System.out.println(" Width : " + elementBounds.getWidth());
          System.out.println(" Height : " + elementBounds.getHeight());
          System.out.println(" Style : " + designElement.getStyle()); 
          System.out.println(" Rotate : " + designElement.getRotate()); 
          
          elementIdString+=designElement.getElementId();
          elementTypeString+=designElement.getElementType();
          posXString+=elementBounds.getX();
          posYString+=elementBounds.getY();
          widthString+=elementBounds.getWidth();
          heightString+=elementBounds.getHeight();
          styleString+=designElement.getStyle();
          rotateString+=designElement.getRotate();
          elementNewString+=(blnSaveAs)?true:designElement.isElementNew();
          
          Hashtable specificProperties = designElement.getSpecificProperties();     
          Enumeration spkeys=specificProperties.keys();
          
          while(spkeys.hasMoreElements()){
            String propertyName=(String)spkeys.nextElement();
            String propertyValue=(String)specificProperties.get(propertyName);
            System.out.println(propertyName + " : " + propertyValue);
            propertyNameString+=propertyName;
            propertyValueString+=propertyValue;
            
            //Concatation of delimiter - propertyWiseDelimiter
            if(spkeys.hasMoreElements()){
              propertyNameString+=propertyWiseDelimiter;
              propertyValueString+=propertyWiseDelimiter;
            }
          }
          
          //Concatation of delimiter - elementWiseDelimiter
          if(index<(canvas.getComponentCount()-1)){
            propertyNameString+=elementWiseDelimiter;
            propertyValueString+=elementWiseDelimiter;
            elementIdString+=elementWiseDelimiter;
            elementTypeString+=elementWiseDelimiter;
            posXString+=elementWiseDelimiter;
            posYString+=elementWiseDelimiter;
            widthString+=elementWiseDelimiter;
            heightString+=elementWiseDelimiter;
            styleString+=elementWiseDelimiter;
            rotateString+=elementWiseDelimiter;
            elementNewString+=elementWiseDelimiter;
          }
          
        }
        
        //Concatation of delimiter - printableAreaDelimiter
        if(keys.hasMoreElements()){
          propertyNameString+=printableAreaDelimiter;
          propertyValueString+=printableAreaDelimiter;
          elementIdString+=printableAreaDelimiter;
          elementTypeString+=printableAreaDelimiter;
          posXString+=printableAreaDelimiter;
          posYString+=printableAreaDelimiter;
          widthString+=printableAreaDelimiter;
          heightString+=printableAreaDelimiter;
          styleString+=printableAreaDelimiter;
          rotateString+=printableAreaDelimiter;
          elementNewString+=printableAreaDelimiter;
          customerDesignDetailPkString+=printableAreaDelimiter;
          merchandisePrintableAreaPkString+=printableAreaDelimiter;
          uniqueNumberString+=printableAreaDelimiter;
          
        }
      }

      System.out.println("uniqueNumberString : " + uniqueNumberString); 
      System.out.println("customerDesignDetailPkString : " + customerDesignDetailPkString); 
      System.out.println("merchandisePrintableAreaPkString : " + merchandisePrintableAreaPkString); 
      System.out.println("elementIdString : " + elementIdString); 
      System.out.println("elementTypeString : " + elementTypeString); 
      System.out.println("posXString : " + posXString); 
      System.out.println("posYString : " + posYString); 
      System.out.println("widthString : " + widthString); 
      System.out.println("heightString : " + heightString); 
      System.out.println("styleString : " + styleString); 
      System.out.println("rotateString : " + rotateString); 
      System.out.println("elementNewString : " + elementNewString); 
      System.out.println("printableAreaDelimiter : " + printableAreaDelimiter);
      System.out.println("elementWiseDelimiter : " + elementWiseDelimiter);
      System.out.println("propertyWiseDelimiter : " + propertyWiseDelimiter);
      
      Object[] args=null;
      args=new Object[17];
      args[0]=uniqueNumberString;
      args[1]=merchandisePrintableAreaPkString;
      args[2]=customerDesignDetailPkString;
      args[3]=elementIdString;
      args[4]=elementTypeString;
      args[5]=posXString;
      args[6]=posYString;
      args[7]=widthString;
      args[8]=heightString;
      args[9]=styleString;
      args[10]=rotateString;
      args[11]=propertyNameString;
      args[12]=propertyValueString;
      args[13]=elementNewString;
      args[14]=printableAreaDelimiter;
      args[15]=elementWiseDelimiter;
      args[16]=propertyWiseDelimiter;
      getBrowserWindow().call("saveDesign",args);
     
      
    }catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  public void populateDesign(      
      String merchandisePrintableAreaPkString,
      String customerDesignDetailPkString,
      String elementIdString,
      String elementTypeString,
      String posXString,
      String posYString,
      String widthString,
      String heightString,
      String styleString,
      String rotateString,
      String propertyNameString,
      String propertyValueString,
      String elementNewString,
      String printableAreaDelimiter,
      String elementWiseDelimiter,
      String propertyWiseDelimiter){
      
    try {
      
      System.out.println("merchandisePrintableAreaPkString : " + merchandisePrintableAreaPkString);
      System.out.println("customerDesignDetailPkString : " + customerDesignDetailPkString);
      System.out.println("elementIdString : " + elementIdString);
      System.out.println("elementTypeString : " + elementTypeString);
      System.out.println("posXString : " + posXString);
      System.out.println("posYString : " + posYString);
      System.out.println("widthString : " + widthString);
      System.out.println("heightString : " + heightString);
      System.out.println("styleString : " + styleString);
      System.out.println("rotateString :" + rotateString);
      System.out.println("propertyNameString :  " + propertyNameString);
      System.out.println("propertyValueString : " + propertyValueString);
      System.out.println("elementNewString : " + elementNewString);
      System.out.println("printableAreaDelimiter : " + printableAreaDelimiter);
      System.out.println("elementWiseDelimiter : " + elementWiseDelimiter);
      System.out.println("propertyWiseDelimiter : " + propertyWiseDelimiter);
      
      String[] merchandisePrintableAreaPkStringArray=merchandisePrintableAreaPkString.split(printableAreaDelimiter);
      String[] customerDesignDetailPkStringArray=customerDesignDetailPkString.split(printableAreaDelimiter);
      Enumeration keys=htprintableAreas.keys();
      
      
      for(int printableAreaIndex=0;printableAreaIndex<merchandisePrintableAreaPkStringArray.length;printableAreaIndex++){
        int merchandisePrintableAreaTblPk=Integer.parseInt(merchandisePrintableAreaPkStringArray[printableAreaIndex]);
        int customerDesignDetailTblPk=Integer.parseInt(customerDesignDetailPkStringArray[printableAreaIndex]);
        System.out.println("merchandisePrintableAreaPk : " + merchandisePrintableAreaTblPk);
        System.out.println("customerDesignDetailTblPk : " + customerDesignDetailTblPk);
        OrientationPanel printableArea = null;
        while(keys.hasMoreElements()){
          PrintableArea printableAreaBean = (PrintableArea)htprintableAreas.get(keys.nextElement());
          if(Integer.parseInt(printableAreaBean.getMerchandisePrintableAreaTblPk())==merchandisePrintableAreaTblPk){
            printableAreaBean.setCustomerDesignDetailTblPk(Integer.toString(customerDesignDetailTblPk));
            printableArea = printableAreaBean.getPrintableArea();
            break;
          }
        }
        
        
        if (elementIdString.split(printableAreaDelimiter).length>0) {
          String[] elementIdStringArray=elementIdString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] elementTypeStringArray=elementTypeString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] posXStringArray=posXString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] posYStringArray=posYString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] widthStringArray=widthString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] heightStringArray=heightString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] styleStringArray=styleString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] rotateStringArray=rotateString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          String[] elementNewStringArray=elementNewString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
          
          for(int elementIndex=0; elementIndex<elementIdStringArray.length;elementIndex++ ){
            if (elementIdStringArray[elementIndex].trim().length()>0) {
            
              int elementId=Integer.parseInt(elementIdStringArray[elementIndex]);
              int elementType=Integer.parseInt(elementTypeStringArray[elementIndex]);
              int posX=Integer.parseInt(posXStringArray[elementIndex]);
              int posY=Integer.parseInt(posYStringArray[elementIndex]);
              int width=Integer.parseInt(widthStringArray[elementIndex]);
              int height=Integer.parseInt(heightStringArray[elementIndex]);
              String style=styleStringArray[elementIndex];
              float rotate=Float.parseFloat(rotateStringArray[elementIndex]); 
              boolean elementNew=(Boolean.valueOf(elementNewStringArray[elementIndex])).booleanValue();
              
              System.out.println("\telementId : " + elementId);
              System.out.println("\telementType : " + elementType);
              System.out.println("\tposX : " + posX);
              System.out.println("\tposY : " + posY);
              System.out.println("\twidth : " + width);
              System.out.println("\theight : " + height);
              System.out.println("\tstyle : " + style);
              System.out.println("\trotate : " + rotate);
              System.out.println("\telementNew : " + elementNew);
              
              AbstractDesignElement designElement = null;
              
              switch (elementType){
              case DesignElementType.CLIPART :
                   designElement = new ClipartElement(this);
                  break;
              case DesignElementType.IMAGE :
                designElement = new ImageElement(this);
                  break;
              case DesignElementType.TEXT :
                designElement = new TextElement(this,"");
                  break;
              }
              
              String[] propertyNameStringArray=propertyNameString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter)[elementIndex].split(propertyWiseDelimiter);
              String[] propertyValueStringArray=propertyValueString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter)[elementIndex].split(propertyWiseDelimiter);
              for(int propertyIndex=0;propertyIndex<propertyNameStringArray.length;propertyIndex++){
                String propertyName=propertyNameStringArray[propertyIndex];
                String propertyValue=propertyValueStringArray[propertyIndex];
                
                System.out.println("\t\tpropertyName : " + propertyName);
                System.out.println("\t\tpropertyValue : " + propertyValue);
                if(designElement!=null){
                  designElement.getSpecificProperties().put(propertyName,propertyValue);
                }
              }
              if(designElement!=null){
                designElement.setElementId(elementId);
                designElement.setElementNew(elementNew);
                designElement.getElementBounds().setLocation(new Point(posX,posY));
                designElement.setLocation(designElement.getElementBounds().getLocation());
                designElement.setRotate(rotate);
                designElement.createElement(width, height,1.0,style);
                if(printableArea!=null){
                  printableArea.getCanvas().add(designElement);
                }
              }
            }
          }
        }
        if (printableArea!=null) {
          printableArea.getCanvas().revalidate();
          printableArea.getCanvas().repaint();
          printableArea.refresh();
        }
      }
      
    }catch(Exception e){     
     e.printStackTrace(); 
    }
  }
  
  
  public void refreshPrintableArea(){
    ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea().refresh();
  }

  public void addClipart(String contentOId,String contentType,String contentSize,String dataSourceKey,int width,int height ){
    ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea().addClipartElement(contentOId,contentType,contentSize,dataSourceKey,width,height);
    setDesignSaved(false);
  }
  
  public void addImage(String customerGraphicsTblPk,String contentOId,String contentType,String contentSize,String dataSourceKey,int width,int height ){
    ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea().addImageElement(customerGraphicsTblPk,contentOId,contentType,contentSize,dataSourceKey,width,height);
    setDesignSaved(false);
  }
  
  public void setProperties(int width, int height){
    OrientationPanel printableArea = ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    printableArea.setProperties(width, height);
    setDesignSaved(false);
  }
  
  public void setProperties(String style){
    OrientationPanel printableArea = ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    printableArea.setProperties(style);
    setDesignSaved(false);
  }
  
  public void setRotate(double rotate){
    OrientationPanel printableArea = ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea();
    printableArea.setRotate(rotate);
    setDesignSaved(false);
  }
  
  public void addText(String text,String style){
    ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea().addText(text,style);
    setDesignSaved(false);
  }
  
  public void editText(String text){
    ((PrintableArea)htprintableAreas.get(selectedPrintableArea)).getPrintableArea().editText(text);
    setDesignSaved(false);
  }
  
  public String getBaseURL() {
    return baseURL;
  }
  
  public JSObject getBrowserWindow() {
    return browserWindow;
  }

  public void setDesignSaved(boolean designSaved) {
    this.designSaved = designSaved;
    
    //To add Asteric if false and to remove asteric if true
    Object[] args=null;
    args=new Object[1];
    args[0]=Boolean.valueOf(designSaved);
    getBrowserWindow().call("setDesignStatus",args);
  }

  public void setDesignSaved(String designSaved) {
    setDesignSaved(Boolean.valueOf(designSaved).booleanValue());
  }
  
  public boolean isDesignSaved() {
    return designSaved;
  }

  //This Class should be private Don't make this class public because this class 
  //is using local functions
  //
  private class StudioKeyListener implements KeyListener{

    /**
      * 
      * @description 
      * @param evt
      */
     public void keyReleased(KeyEvent evt){
       
     }
     
     /**
      * 
      * @description 
      * @param evt
      */
     public void keyTyped(KeyEvent evt){
       
     }
     
     /**
      * 
      * @description 
      * @param evt
      */
     public void keyPressed(KeyEvent evt){
       if(evt.getModifiers()==0){
         if(evt.getKeyCode()==KeyEvent.VK_UP){
           
         }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
           
         }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
           
         }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
           
         }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
           deleteElement();
         }else if(evt.getKeyCode()==KeyEvent.VK_EQUALS || evt.getKeyCode()==KeyEvent.VK_ADD ){
           
         }else if(evt.getKeyCode()==KeyEvent.VK_MINUS  || evt.getKeyCode()==KeyEvent.VK_SUBTRACT){
         
         }

       }else if (evt.getModifiers()==KeyEvent.CTRL_MASK ){
       
       }else if(evt.getModifiers()==KeyEvent.SHIFT_MASK ){
       
       }else if(evt.getModifiers()==(KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK) ){
       
       }
       
     }
  }
  
  private class PrintableArea {

  private String printableAreaTblPk;
  private String printableAreaName;
  private String contentOId;
  private String contentType;
  private String contentSize;
  private String merchandisePrintableAreaTblPk;
  private Dimension printableAreaSize;
  private OrientationPanel printableArea;
  private String customerDesignDetailTblPk="-1";
  public String getPrintableAreaTblPk(){
    return printableAreaTblPk;
  }
  
  public void setPrintableAreaTblPk(String printableAreaTblPk){
    this.printableAreaTblPk=printableAreaTblPk;
  }
  
  public String getPrintableAreaName(){
   return printableAreaName;
  }
  
  public void setPrintableAreaName(String printableAreaName){
    this.printableAreaName=printableAreaName;
  }
  
  public String getContentOId(){
    return contentOId;
  }
  public void setContentOId(String contentOId){
    this.contentOId=contentOId;
  }
  
  public String getContentType(){
    return contentType;
  }
  
  public void setContentType(String contentType){
    this.contentType=contentType;
  }
  
  public String getContentSize(){
    return contentSize;
  }
  
  public void setContentSize(String contentSize){
    this.contentSize=contentSize;
  }
  
  public Dimension getPrintableAreaSize(){
    return printableAreaSize;
  }
  
  public void setPrintableAreaSize(Dimension printableAreaSize){
    this.printableAreaSize=printableAreaSize;
  }
  
  public String getMerchandisePrintableAreaTblPk(){
    return merchandisePrintableAreaTblPk;
  }
  
  public void setMerchandisePrintableAreaTblPk(String merchandisePrintableAreaTblPk){
    this.merchandisePrintableAreaTblPk=merchandisePrintableAreaTblPk;
  }
  
  public OrientationPanel getPrintableArea(){
    return printableArea;
  }
  
  public void setPrintableArea(OrientationPanel printableArea){
    this.printableArea=printableArea;
  }
  
  public String getCustomerDesignDetailTblPk(){
    return customerDesignDetailTblPk;
  }
  
  public void setCustomerDesignDetailTblPk(String customerDesignDetailTblPk){
    this.customerDesignDetailTblPk=customerDesignDetailTblPk;
  }
  
}




}

