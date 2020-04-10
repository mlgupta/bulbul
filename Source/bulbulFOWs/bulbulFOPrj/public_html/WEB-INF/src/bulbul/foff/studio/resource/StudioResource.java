package bulbul.foff.studio.resource;

import java.util.HashSet;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import bulbul.foff.general.NodeIterator;

public final class StudioResource {
  /**
   * computes the path of a resource given its name
   * @param resource the name of a resource 
   * @return the full path of a resource
   * 
   */
  private static final Hashtable cachedXMLDocuments=new Hashtable();
  private static final HashSet styleProperties=new HashSet();
  
  public   String getPath(String resource){
      String path="";      
      try{
          path=getClass().getResource(resource).toExternalForm();
      }catch (Exception ex){path="";}
      return path;
  }
  
  private  Document getXMLDocument(String name){
    
    Document document=null;
    try {
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
            path=getPath("xml/".concat(name));
            document=documentBuilder.parse(path);
            
          }catch (Exception ex){      
            document=null;
          }
          if(document!=null){
            cachedXMLDocuments.put(name, document);
          }
        }
      }
    }
    catch (Exception e) {
      ;
    }
    return document;
  }
  
  public  HashSet getAttributesToTranslate(){
    try {
      if(styleProperties.size()<=0){
        Document document=null;
        document=getXMLDocument("properties.xml");
        
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
    }
    catch (Exception e) {
      ;
    }
    return styleProperties;
  }
  
}
