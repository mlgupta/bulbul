package bulbul.boff.user.xml;

import bulbul.boff.general.BOConstants;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

/**
 *	Purpose: To Read user-access.xml. 
 *  Info: This Class will read the data from user-access.xml and will be used in action class
 *        to display in the view add and edit pages
 *  @author               Sudheer Pujar
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */


/**
 *	Purpose: To Read user-access.xml. 
 *  Info: This Class will read the data from user-access.xml and will be used in action class
 *        to display in the view add and edit pages
 *  @author               Sudheer Pujar
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class UserAccess  {
  private static Logger logger = null;
  private ArrayList modules = null;
  private HashMap moduleNameById=null;
  private HashMap moduleIdByName=null;
  private HashMap permissionsByModuleId=null;
  
  public UserAccess(String xmlFilePhysicalPath) throws Exception {
    logger = Logger.getLogger(BOConstants.LOGGER.toString());
    Document document=null;
    DocumentBuilderFactory documentBuilderFactory = null;
    DocumentBuilder documentBuilder= null;
    Element  rootElement=null;
    NodeList moduleNodeList=null;
    NodeList permissionNodeList=null;
    Module module=null;
    Permission permission=null;
    File theXmlFile=null; 
    
    
    try{ 
      logger.info("Entering Constructor of User Access");
      documentBuilderFactory = DocumentBuilderFactory.newInstance() ;
      documentBuilder= documentBuilderFactory.newDocumentBuilder();
      theXmlFile=new File(xmlFilePhysicalPath + "WEB-INF/xmls/user/user_access.xml");
      if(theXmlFile!=null ){
        logger.debug(theXmlFile.getAbsolutePath() + " XML File Read Successfully");
        
        document = documentBuilder.parse(theXmlFile);
        rootElement=document.getDocumentElement();
        moduleNodeList=rootElement.getElementsByTagName("module");

        modules = new ArrayList();
        moduleNameById = new HashMap();
        moduleIdByName = new HashMap();
        permissionsByModuleId= new HashMap();
        HashMap  permissionNameById=null; 
        Node permissionNode = null;
        String pipedPermissionIds = null;
        for(int moduleNodeCount=0; moduleNodeCount<moduleNodeList.getLength(); moduleNodeCount++){
          ArrayList permissions = new ArrayList();
          permissionNameById = new HashMap();
          pipedPermissionIds = new String("");
          permissionNodeList= moduleNodeList.item(moduleNodeCount).getChildNodes();
          for(int permissionsNodeCount=0; permissionsNodeCount<permissionNodeList.getLength();permissionsNodeCount++){
            permission = new Permission();
            permissionNode=permissionNodeList.item(permissionsNodeCount);
            if(permissionNode.getNodeType()==Node.ELEMENT_NODE){
              permission.setId(permissionNodeList.item(permissionsNodeCount).getAttributes().getNamedItem("id").getNodeValue());
              permission.setName(permissionNodeList.item(permissionsNodeCount).getAttributes().getNamedItem("name").getNodeValue());
              permissions.add(permission);
              permissionNameById.put(permission.getId(),permission.getName()) ;
              pipedPermissionIds=pipedPermissionIds + permission.getId() + "|"; 
            }
          }

          module = new Module();
          module.setId(moduleNodeList.item(moduleNodeCount).getAttributes().getNamedItem("id").getNodeValue());
          module.setName(moduleNodeList.item(moduleNodeCount).getAttributes().getNamedItem("name").getNodeValue());
          module.setPermissions(permissions);
          module.setPipedPermissionIds(pipedPermissionIds);
          moduleNameById.put(module.getId(), module.getName());
          moduleIdByName.put(module.getName(),module.getId());
          permissionsByModuleId.put(module.getId(),permissionNameById);
          modules.add(module);
        }
             
      }
    }catch(ParserConfigurationException pce){
      //pce.printStackTrace();
      logger.error(" ParserConfigurationException Exception Occured" ); 
      throw pce;
    }catch(IOException ioe){
      //ioe.printStackTrace();
      logger.error(" IOException Exception Occured" ); 
      throw ioe;
    }catch(SAXException se){
      //se.printStackTrace();
      logger.error(" SAXException Exception Occured" ); 
      throw se;
  
    }catch(Exception e){
      //e.printStackTrace();
      logger.error(" General Exception Occured" ); 
      throw e;
    }
    finally{
      theXmlFile=null;
      logger.info("Exiting Constructor of User Category");
    }
    
  }

  public ArrayList  getModules(){
    return this.modules;
  }
  
  public String getModuleNameById(String id){
    return (String)moduleNameById.get(id);
  }

  public String getModuleIdByName(String name){
    return (String)moduleIdByName.get(name);
  }

  public String getPermissionNameById(String moduleId, String permissionId){
    return (String)((HashMap)permissionsByModuleId.get(moduleId)).get(permissionId);
  }

  


  public static class  Module{

    private String id;
    private String name;
    private ArrayList permissions;
    private String pipedPermissionIds;

    public String getId(){
      return this.id;
    }

    public String getName(){
      return this.name;
    }

    public ArrayList getPermissions(){
      return this.permissions;
    }

    public String getPipedPermissionIds(){
      return this.pipedPermissionIds;
    }

    public void setId(String id){
      this.id=id;
    }

    public void setName(String name){
      this.name=name;
    }
    public void setPermissions(ArrayList permissions){
      this.permissions=permissions;
    }

    public void setPipedPermissionIds(String pipedPermissionIds){
      this.pipedPermissionIds=pipedPermissionIds;
    }
    
  }

  public static class Permission{

    private String id;
    private String name;
    private String value;

    public String getId(){
      return this.id;
    }

    public String getName(){
      return this.name;
    } 

    public String getValue(){
      return this.value;
    }

    public void setId(String id){
      this.id=id;
    }

    public void setName(String name){
      this.name=name;
    }

    public void setValue(String value){
      this.value=value;
    }
    
  }

  public static void main(String[] args){
    String physicalPath = "D:\\Sudheer\\Bulbul\\Source\\bulbulBOWs\\bulbulBOPrj\\public_html\\";
    System.out.println("Hello");
    try{
      UserAccess userAccess = new UserAccess(physicalPath);
      //System.out.println(userAccess.getPermissionNameById("001","002")); 
      System.out.println(userAccess.getModuleNameById("001"));
      
    }catch(Exception e ){
      e.printStackTrace(); 
    }

  }
  
}
  
