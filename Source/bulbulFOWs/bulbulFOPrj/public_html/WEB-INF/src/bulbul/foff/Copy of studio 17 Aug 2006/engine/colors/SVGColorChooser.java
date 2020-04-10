package bulbul.foff.studio.engine.colors;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import org.w3c.dom.Element;

/**
 * 
 * @description 
 * @version 1.0 29-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGColorChooser  extends JColorChooser {
    
  private DataFlavor colorFlavor=null;
  private DataFlavor w3cSVGColorFlavor=null;
  
  protected Studio studio=null;
    
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGColorChooser(Studio studio){
    this.studio=studio;
    
    //adds the w3c standard colors chooser panel
    SVGW3CColorChooserPanel w3cColorChooserPanel=new SVGW3CColorChooserPanel(studio);
    addChooserPanel(w3cColorChooserPanel);
    
    //Changing Preview Label
    final JLabel label = new JLabel("www.printoon.com", JLabel.CENTER);
    label.setFont(new Font("previewfont", Font.ROMAN_BASELINE, 48));
    label.setSize(label.getPreferredSize());
    setPreviewPanel(label);
    setFont(Studio.STUDIO_FONT);
    
    for(int i=0; i<getChooserPanels().length;i++){
      getChooserPanels()[i].setFont(Studio.STUDIO_FONT);
    }  
    
    
    //creating the color flavors
    try{
      colorFlavor=new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType+";class=java.awt.Color");
      w3cSVGColorFlavor=new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType+";class=bulbul.foff.studio.engine.colors.SVGW3CColor");
    }catch (Exception ex){colorFlavor=DataFlavor.stringFlavor; w3cSVGColorFlavor=DataFlavor.stringFlavor;}
  }


  /**
   * 
   * @description 
   * @return 
   * @param colorString
   */
  public Color getColor(String colorString){
    Color color=null;
    if(colorString==null){
      colorString="";
    }else if(getStudio().getSvgResource()!=null){
      //checking if the given string represents a w3c color
      color=(Color)getStudio().getSvgResource().getW3CColorsMap().get(colorString);
    }
    
    if(color==null){
      try{
        color=Color.getColor(colorString);
      }catch (Exception ex){color=null;}
      
      if(color==null && colorString.length()==7){
        int r=0, g=0, b=0;
        try{
          r=Integer.decode("#"+colorString.substring(1,3)).intValue();
          g=Integer.decode("#"+colorString.substring(3,5)).intValue();
          b=Integer.decode("#"+colorString.substring(5,7)).intValue();
          color=new Color(r,g,b);
        }catch (Exception ex){color=null;}
      }else if(color==null && colorString.indexOf("rgb(")!=-1){
        String tempColorString=colorString.replaceAll("\\s*[rgb(]\\s*", "");
        
        tempColorString=tempColorString.replaceAll("\\s*[)]\\s*", "");
        tempColorString=tempColorString.replaceAll("\\s+", ",");
        tempColorString=tempColorString.replaceAll("[,]+", ",");
        
        int r=0, g=0, b=0;

        try{
          r=new Integer(tempColorString.substring(0, tempColorString.indexOf(","))).intValue();

          tempColorString=tempColorString.substring(tempColorString.indexOf(",")+1, tempColorString.length());
          
          g=new Integer(tempColorString.substring(0, tempColorString.indexOf(","))).intValue();

          tempColorString=tempColorString.substring(tempColorString.indexOf(",")+1, tempColorString.length());
          
          b=new Integer(tempColorString).intValue();
          
          color=new Color(r,g,b);
        }catch (Exception ex){color=null;}
      }
    }
    return color;
  }
    
  /**
   * 
   * @description 
   * @return 
   * @param color
   */
  public String getColorString(Color color){
    if(color==null){
      color=Color.black;
    }
    
    if(color instanceof SVGW3CColor){
      return ((SVGW3CColor)color).getId();
    }
    
    String stringRed=Integer.toHexString(color.getRed());
    String stringGreen=Integer.toHexString(color.getGreen());
    String stringBlue=Integer.toHexString(color.getBlue());
    
    if(stringRed.length()==1){
      stringRed="0".concat(stringRed);
    }
    
    if(stringGreen.length()==1){
      stringGreen="0".concat(stringGreen);
    }
    
    if(stringBlue.length()==1){
      stringBlue="0".concat(stringBlue);
    }
    return (("#".concat(stringRed)).concat(stringGreen)).concat(stringBlue);
  }

  /**
   * 
   * @description 
   * @return 
   * @param color
   */
  public DataFlavor getColorFlavor(Color color){
    DataFlavor flavor=null;

    if(color!=null){
      if(color instanceof Color){
        flavor=colorFlavor;
      }else if(color instanceof SVGW3CColor){
        flavor=w3cSVGColorFlavor;
      }
    }
    return flavor;
  }
    
  /**
   * 
   * @description 
   * @return 
   * @param flavor
   */
  public boolean isColorDataFlavor(DataFlavor flavor){
    boolean isColorDataFlavor=false;
    
    if(flavor!=null){
      isColorDataFlavor=(flavor.isMimeTypeEqual(colorFlavor) || flavor.isMimeTypeEqual(w3cSVGColorFlavor));
    }
    return isColorDataFlavor;
  }
    
  /**
   * 
   * @description 
   * @return 
   */
  public Collection getColorDataFlavors(){
    LinkedList dataFlavors=new LinkedList();
    
    dataFlavors.add(colorFlavor);
    dataFlavors.add(w3cSVGColorFlavor);
    
    return dataFlavors;
  }    
  
  /**
   * 
   * @description 
   * @param element
   * @param svgTab
   */
  public void checkColorString(SVGTab svgTab, Element element){
        
  }
  
  /**
   * 
   * @description 
   * @param element
   */
  public void checkColorString(Element element){
        
  }
  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
  
  
}