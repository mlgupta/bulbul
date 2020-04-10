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

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;


/**
 *	Purpose: To Read user-category.xml. 
 *  Info: This Class will read the data from user-category.xml and will be used in action class
 *        to fill the list and fill user category list and listboxes 
 *  @author               Sudheer Pujar
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class

 UserCategory{

  private ArrayList categories =null; //
  private String[] categoryIds;
  private String[] categoryNames;
  private String[] categoryDescriptions;
  private HashMap categoryNameById =null;
  private HashMap categoryDescriptionsById =null;
  private Logger logger = null;

  /**
   * Purpose : Contructs a UserCategory Object 
   * @param xmlFilePhysicalPath - A String object representing Physical path for 
   *                              accessing the XML (user-category.xml) File                              
   */
  public UserCategory(String xmlFilePhysicalPath) throws Exception {
    logger = Logger.getLogger(BOConstants.LOGGER.toString());
    Document document=null;
    DocumentBuilderFactory documentBuilderFactory = null;
    DocumentBuilder documentBuilder= null;
    Element  rootElement=null;
    NodeList categoryNodeList=null;
    Category category=null;
    File theXmlFile=null; 
    try{ 
      logger.info("Entering Constructor of User Category");
      documentBuilderFactory = DocumentBuilderFactory.newInstance() ;
      documentBuilder= documentBuilderFactory.newDocumentBuilder();
      theXmlFile=new File(xmlFilePhysicalPath + "WEB-INF/xmls/user/user_category.xml");
      if(theXmlFile!=null ){
        logger.debug(theXmlFile.getAbsolutePath() + " XML File Read Successfully");
        document = documentBuilder.parse(theXmlFile);
        rootElement=document.getDocumentElement();
        categoryNodeList=rootElement.getElementsByTagName("user-category");
        
        categoryIds= new String[categoryNodeList.getLength()];
        categoryNames=new String[categoryNodeList.getLength()];
        categoryDescriptions=new String[categoryNodeList.getLength()];

        categories= new ArrayList();
        categoryNameById = new HashMap();
        categoryDescriptionsById =new HashMap();

        logger.debug(" Filling Arrays, ArrayList");
        
        for (int nodeCount=0; nodeCount<categoryNodeList.getLength(); nodeCount++){

          category = new Category();
          
          category.setId(categoryNodeList.item(nodeCount).getAttributes().getNamedItem("id").getNodeValue());
          category.setName(categoryNodeList.item(nodeCount).getAttributes().getNamedItem("name").getNodeValue());
          category.setDescription(categoryNodeList.item(nodeCount).getAttributes().getNamedItem("description").getNodeValue());

          
          categoryIds[nodeCount]=category.getId();
          categoryNames[nodeCount]=category.getName();
          categoryDescriptions[nodeCount]=category.getDescription();

          categoryNameById.put(category.getId(),category.getName());
          categoryDescriptionsById.put(category.getId(),category.getDescription());
         
          this.categories.add(category);
        }

        logger.debug(" Sucessfully Filled Arrays, ArrayList");
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

  /**
   * Purpose : To Get the User Categories  the "Category" bean.
   * @return ArrayList Object of Category
   */
  public ArrayList getUserCategories(){
    return this.categories;
  }

  /**
   * Purpose : To Get the User Category Ids
   * @return String Array Object of User Category Id
   */
  public String[] getUserCategoryIds(){
    return this.categoryIds;
  }

  /**
   * Purpose : To Get the User Category Names
   * @return String Array Object of User Category Name
   */
  public String[] getUserCategoryNames(){
    return this.categoryNames;
  }
  
  /**
   * Purpose : To Get the User Category Descriptions 
   * @return String Array Object of User Category Descriptions
   */
  public String[] getUserCategoryDescriptions(){
    return this.categoryDescriptions;
  }

  /**
   * Purpose : To Get the User Category Name using User Category Id 
   * @param id a String Object
   * @return String Object of User Category Name
   */
  public String getUserCategoryNameById(String id){
    return (String)this.categoryNameById.get(id);
  }
  
  /**
   * Purpose : To Get the User Category Description using User Category Id 
   * @param  id a String Object
   * @return String Object of User Category Description
   */
  public String getUserCategoryDescriptionById(String id){
    return (String)this.categoryDescriptionsById.get(id);
  }

  /**
   *	Purpose: To Store User Category.
   *  Info: This Class will Store the user category in the form of bean.
   *        
   *  @author              Sudheer Pujar
   *  @version             1.0
   * 	Date of creation:   27-12-2004
   * 	Last Modfied by :     
   * 	Last Modfied Date:    
   */
  public class Category{
    private String id=null;  
    private String name=null;
    private String description=null;


  /**
   * Purpose : To Get the User Category Id out of bean
   * @return String Object of User Category Id
   */    
    public String getId(){
      return this.id;
    }

  /**
   * Purpose : To Get the User Category Name out of bean
   * @return String Object of User Category Name
   */    
    public String getName(){
      return this.name;
    }

  /**
   * Purpose : To Get the User Category Description out of bean
   * @return String Object of User Category Description
   */    
    public String getDescription(){
      return this.description;
    }

  /**
   * Purpose : To Set the User Category Id into of bean
   * @param id  A String Object of User Category Id
   */    
    public void setId(String id){
      this.id=id;
    }

  /**
   * Purpose : To Set the User Category Name into of bean
   * @param name  A String Object of User Category Name
   */    
    public void setName(String name){
      this.name=name;
    }

  /**
   * Purpose : To Set the User Category Description into of bean
   * @param description  A String Object of User Category Description
   */    
    public void setDescription(String description){
      this.description=description;
    }
  }  
  
}