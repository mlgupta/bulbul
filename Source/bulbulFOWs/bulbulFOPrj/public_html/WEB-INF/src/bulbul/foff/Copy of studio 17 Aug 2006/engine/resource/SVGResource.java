package bulbul.foff.studio.engine.resource;
import bulbul.foff.studio.engine.colors.SVGW3CColor;
import bulbul.foff.studio.engine.general.NodeIterator;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * 
 * @description 
 * @version 1.0 27-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGResource   {
  
  private final Hashtable cachedXMLDocuments=new Hashtable();
  
  private final Hashtable cachedIcons=new Hashtable();
  
  private final LinkedHashMap w3cColorsMap=new LinkedHashMap();
  
  private Studio studio;
  
  private final HashSet styleProperties=new HashSet();
  
  private Document xmlProperties=null;
  
  /**
   * 
   * @description 
   */
  public SVGResource(Studio studio) {
    this.studio=studio;
    
    retrieveW3CColors(getXMLDocument("svgcolors.xml"));
  }


  /**
   * 
   * @description 
   * @param svgColorsDocument
   */
  private void retrieveW3CColors(Document svgColorsDocument){
    if(svgColorsDocument!=null && svgColorsDocument.getDocumentElement()!=null){
        
      String id="";
      String value="";
      Color color=null;
      LinkedList colorsList=new LinkedList();
      
      //for each svg colors, gets the name and the value and adds them to the list
      for(Node current=svgColorsDocument.getDocumentElement().getFirstChild(); current!=null; current=current.getNextSibling()){
        if(current instanceof Element && current.getNodeName().equals("svgColor")){
          id=((Element)current).getAttribute("id");
          value=((Element)current).getAttribute("value");
          
          if(id!=null && ! id.equals("") && value!=null && ! value.equals("")){
            color=getColor(value);
            
            if(color!=null){
              color=new SVGW3CColor(id, color);
              colorsList.add(color);
            }
          }
        }
      }
      
      
      SVGW3CColor svgColor=null;
      
      //puts the colors and the ids to the map of the colors
      for(Iterator it=colorsList.iterator(); it.hasNext();){
        svgColor=(SVGW3CColor)it.next();
        if(svgColor!=null){
          w3cColorsMap.put(svgColor.getId(), svgColor);
        }
      }
    }
  }
    
    
  /**
   * 
   * @description 
   * @return 
   * @param colorString
   */
  private Color getColor(String colorString){
    Color color=null;
    
    if(colorString==null){
      colorString="";
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
   */
  public LinkedList getW3CColors(){
    return new LinkedList(w3cColorsMap.values());
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Map getW3CColorsMap(){
    return new HashMap(w3cColorsMap);
  }
    
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public String getXMLPath(){
    return getResourcePath()+"xml/"; 
  }
   
  /**
   * 
   * @description 
   * @return 
   */
  public String getContextPath(){
    String appletDocumentBase=getStudio().getDocumentBase().toString();
    return appletDocumentBase.substring(0,appletDocumentBase.toString().lastIndexOf("/")+1);
  }
  
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getResourcePath(){
    return getStudio().getCodeBase().toString() +"resource/"; 
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getIconPath(){
    return getResourcePath()+"icon/"; 
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public ImageIcon getIcon(String name){
    ImageIcon icon=null;
    if(name!=null && ! name.equals("")){
      if(cachedIcons.containsKey(name)){
        try{
          icon=(ImageIcon)cachedIcons.get(name);
        }catch(Exception ex){icon=null;}
      }else{
        try{
            icon=new ImageIcon(new URL(getIconPath().concat(name)));
        }catch (Exception ex){icon=null;}
    
        if(icon!=null){
          cachedIcons.put(name, icon);
        }
      }
    }
    return icon;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public Document getXMLDocument(String name){
    
    Document document=null;
    if(name!=null && ! name.equals("")){
      if(cachedXMLDocuments.containsKey(name)){
        try{
            document=(Document)cachedXMLDocuments.get(name);
        }catch (Exception ex){document=null;}
      }else{
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        String path="";
        try{
          //parses the XML file
          DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
          path=getXMLPath().concat(name);
          document=documentBuilder.parse(path);
          
        }catch (Exception ex){      
          document=null;
        }
        if(document!=null){
          cachedXMLDocuments.put(name, document);
        }
      }
    }
    return document;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public HashSet getAttributesToTranslate(){
    if(styleProperties.size()<=0){
      Document document=null;
      if(xmlProperties!=null){
        document=xmlProperties;
      }else{
        document=getXMLDocument("properties.xml");
      }
      
      if(document!=null){
        Node root=document.getDocumentElement();
        
        if(root!=null){
          //the node iterator
          Node node=null;
          String nodeName="";
          
          //for each property npde
          for(NodeIterator it=new NodeIterator(root); it.hasNext();){
            try{
              node=(Node)it.next();
            }catch (Exception ex){node=null;}
            
            if(node!=null && node instanceof Element && node.getNodeName().equals("property")){
              
              //tests if the node is a style property
              if(((Element)node).getAttribute("type")!=null && ((Element)node).getAttribute("type").equals("style")){
                nodeName=((Element)node).getAttribute("name");

                //gets the name of the stye property
                if(nodeName!=null && !nodeName.equals("")){
                  nodeName=nodeName.substring(9, nodeName.length());
                  styleProperties.add(nodeName);
                }
              }
            }
          }
        }
      }
    }
    return styleProperties;
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