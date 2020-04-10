package bulbul.boff.general;

import bulbul.boff.general.beans.TreeItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.sql.ResultSet;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class Treeview  {
  private Logger logger=null;
  private String treeId=null;
  private String physicalPath=null;
  private String iconPath=null;
  private String userId=null;
  private String sessionId=null;
  private int treeLevel=1;
  private String tempUserFolderPathContext=null;
  private String tempFolderPath="temp/";
  private String tempUserFolderPath=null;
  private String jsFileName=null;
  private String jsFileLinks=null;
  private String jsFileName2Create=null;
  private boolean categoryOnly=true;
  private TreeItem treeItem=null;
  private TreeItem treeItem4Category=null;
  private DataSource dataSource=null;
  private ArrayList persistentCategories;                //An ArrayList stores the data of Categories Persisting in .js File
  private final static int CATEGORYPK=0;                //      Array Index Constants for the array object of length 3 (0,1,2) containted in the  ----
  private final static int PARENT=1;                  //  --  ArrayList named persistentCategories CATEGORYPK=0 is for the Category Pk;               ----
  private final static int JSFILE=2;                  //  --  PARENT=1 Parent of the Category ; JSFILE=2  contained Js File
  private ArrayList persistentScriptTags;             //An ArrayList stores the data of script tag Persisting in .jsp File
  private ArrayList jsFiles2BDeleted;                 //An ArrayList stores the js File that r to be refreshed  and to delete       
  private ArrayList ancesstorCategories;                     //An ArrayList stores ancesstors  for List Navigation

  public Treeview(String treeId, 
                  String physicalPath, 
                  String iconPath, 
                  String userId, 
                  String sessionId,
                  DataSource dataSource, 
                  TreeItem treeItem4Category,
                  TreeItem treeItem,
                  int treeLevel, 
                  boolean categoryOnly)throws IOException,Exception{
                  
    logger = Logger.getLogger(BOConstants.LOGGER.toString());
    try{
      logger.info("Tree View Constructor Enters ");
    
      this.treeId=treeId;
      this.physicalPath=physicalPath;
      this.iconPath=iconPath;
      this.userId=userId;
      this.sessionId=sessionId;
      this.treeLevel=treeLevel;
      this.treeItem4Category=treeItem4Category;
      this.treeItem=treeItem;
      this.dataSource=dataSource;
      this.persistentCategories = new ArrayList();
      this.persistentScriptTags = new ArrayList();
      this.jsFiles2BDeleted = new ArrayList();
      this.ancesstorCategories = new ArrayList();

      this.tempUserFolderPathContext=this.tempFolderPath + this.userId + this.sessionId +this.treeId + "/";

      logger.debug("User Temp Folder Path (Context) " + this.tempUserFolderPathContext);
      
      this.jsFileName = this.tempUserFolderPathContext + this.userId + ".js";
      logger.debug("Master Js File Name (Context)" + this.jsFileName);

      this.tempFolderPath =  this.physicalPath+this.tempFolderPath ;
      logger.debug("Temp Folder Path (Physical) " + this.tempFolderPath);

      this.tempUserFolderPath= this.physicalPath+this.tempUserFolderPathContext;
      logger.debug("User Temp Folder Path (Physical)" + this.tempUserFolderPath);

      this.jsFileName2Create = this.physicalPath+this.jsFileName;
      logger.debug("Master Js File Name (Physical)" + this.jsFileName2Create);
      
      this.categoryOnly=categoryOnly;

      initTree();
    }catch(Exception e){

      this.logger.info("Exception Caught In Contructor of Treeview.java");

      this.logger.fatal(e.getMessage());

      throw e;

    }finally{
      logger.info("Tree View Constructor Exits ");
    }
  }

  public void initTree()throws IOException,Exception{
    this.logger.info("Treeview.initTree() Starts");
    DBConnection  dBConnection=null;
    ResultSet rs=null;
    try{
      StringBuffer initTreeString = new StringBuffer();
      initTreeString.append("USETEXTLINKS = 1 \n");  
      initTreeString.append("STARTALLOPEN = 0" + "\n");
      initTreeString.append("USEFRAMES = 0" + "\n");
      initTreeString.append("HIGHLIGHT = 1" + "\n");
      initTreeString.append("PERSERVESTATE = 1" + "\n");
      initTreeString.append("ICONPATH = '" + this.iconPath + "'" + "\n");
      initTreeString.append("foldersTree = gFld(\"All\");" + "\n");
      initTreeString.append("foldersTree.treeID = \""+ this.treeId + "\";" + "\n");
      initTreeString.append("foldersTree.xID = \"" + "-1" + "\";"  + "\n");
      initTreeString.append("foldersTree.isChildExist = true;"  + "\n");
      initTreeString.append("foldersTree.isNextLevel = true;"  + "\n");

     //Creates the temp dir
      if (!(new File(this.tempFolderPath).exists())){
        if (!(new File(this.tempFolderPath).mkdir())){    
          this.logger.error("Unable to Create "+ tempFolderPath + " Directory"); 
        }else{
          this.logger.info("Temp Directory " + tempFolderPath + " Created");
        }
      }

      //Creates the <user> dir
      if (!(new File(this.tempUserFolderPath).exists())){ 
        if (!(new File(this.tempUserFolderPath).mkdir())){    
          this.logger.error("Unable to Create "+ tempUserFolderPath + " Directory"); 
        }else{
          this.logger.info("User On Session Temp Directory "+ tempUserFolderPath + " Created"); 
        }
      }

      //Creating Resultset 
      dBConnection = new DBConnection(this.dataSource);
      String sqlString;
      sqlString=" \nselect";
      sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
      sqlString=sqlString + " , \n" +  treeItem4Category.getNameColumn() ;
      sqlString=sqlString + " , \n" +  treeItem4Category.getDescriptionColumn();
      sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
      sqlString=sqlString + " \nwhere 1=1" ;
      sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
      sqlString=sqlString + " is null ";
      sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;

      logger.debug("Sql String is " + sqlString );
      
      rs = dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 
      initTreeString.append(getTree(dBConnection,rs,"foldersTree",1,this.userId));

      //Creates .js File 
      if (!(writeToFile(initTreeString,this.jsFileName2Create,false))){
        this.logger.error("Unable to Create " +  this.jsFileName2Create + " File"); 
      }else{
        this.logger.info("Master Js File " + this.jsFileName2Create + " Created"); 
      }

    }catch(Exception e){
      this.logger.info("Exception Caught In initTree() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      e.printStackTrace();  
      throw e;

    }finally{
      this.logger.info("Treeview.initTree() Ends");
      if (dBConnection !=null){
        dBConnection.free(rs);
      }
    }
  }


/**
 * Purpose : Creates Tree String for Categories .
 * @param dBConnection - A DBConnection object which is having database connection
 * @param rs - A Resultset Object 
 * @param parent - A String object defined by concatation of "obj" and category pk 
 * @param level - A long data defined by the current level running in the Tree
 * @param jsFileName4Category - A String object defined by the jsFile which contains the Category Pk
 * @return A StringBuffer object  containing Tree String of Categories
 */

  private StringBuffer getTree(DBConnection dBConnection, ResultSet rs, String parent, long level, String jsFileName4Category) throws IOException,Exception{
    StringBuffer treeOfItems = new StringBuffer();
    ResultSet oldRs;
    String child; 
    boolean isCategoryPersists=false;
    try{
        while(rs.next()){
          if (this.categoryOnly ){
            String categoryPk=rs.getString(treeItem4Category.getPkColumn());
            String categoryName=rs.getString(treeItem4Category.getNameColumn());

            for(int j=0; j<persistentCategories.size(); j++){
              if (isCategoryPersists=((((String[])persistentCategories.get(j))[CATEGORYPK]).equals(categoryPk))){
                break;
              }
            }
            
            if (!(isCategoryPersists)) {  
              persistentCategories.add(
                new String[] {
                  categoryPk,
                  ((!(parent.equals("foldersTree")))? parent.substring(3):"-1"),
                  jsFileName4Category
                  }
              );
              /* To Check Child of Category Starts*/
              String sqlString;
              ResultSet childRs;
              boolean childExists=false;
              sqlString=" \nselect";
              sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
              sqlString=sqlString + " , \n" +  treeItem4Category.getNameColumn() ;
              sqlString=sqlString + " , \n" +  treeItem4Category.getDescriptionColumn();
              sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
              sqlString=sqlString + " \nwhere 1=1" ;
              sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
              sqlString=sqlString + " = " + categoryPk;
              sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;

              childRs=dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY  );
              if(childRs!=null){
                childRs.last();
                childExists=(childRs.getRow()>0);
              }
              dBConnection.freeResultSet(childRs);
              /* To Check Child of Category Ends*/
                            
              child="obj" + categoryPk;
              treeOfItems.append("\n");
              treeOfItems.append(child + "= gFld(\"" + categoryName + "\");");
              treeOfItems.append("\n");
              treeOfItems.append(child + ".xID=\""+ categoryPk + "\";");
              treeOfItems.append("\n");
              treeOfItems.append(child + ".isChildExist="+ childExists + ";");
              treeOfItems.append("\n");
              treeOfItems.append(child + ".isNextLevel="+ (level < this.treeLevel) + ";");
              treeOfItems.append("\n");
              //treeOfItems.append(child + ".path=\""+ "a/b/c" + "\";");
              //treeOfItems.append("\n");
              treeOfItems.append("insFld(" + parent + "," + child + ");"  + "\n" );
            }
          }else{
            child="obj" + rs.getString(treeItem4Category.getPkColumn());
            treeOfItems.append("\n");
            treeOfItems.append(child + "= gLnk(\"S\",\"" + rs.getString(treeItem4Category.getNameColumn()) + "\"," + "true" + ");");
            treeOfItems.append("\n");
            treeOfItems.append(child + ".xID=\""+ rs.getString(treeItem4Category.getPkColumn()) + "\";");
            treeOfItems.append("\n");
           // treeOfItems.append(child + ".path=\""+  "a/b/c" + "\";");
           // treeOfItems.append("\n");
            treeOfItems.append("insDoc(" + parent + "," + child + ");"  + "\n" );
          }
        }
        
        rs.beforeFirst();
        
        if ( level < this.treeLevel) {
          while (rs.next()){
            String sqlString;
            String pk=rs.getString(treeItem4Category.getPkColumn());
            sqlString=" \nselect";
            sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
            sqlString=sqlString + " , \n" +  treeItem4Category.getNameColumn() ;
            sqlString=sqlString + " , \n" +  treeItem4Category.getDescriptionColumn();
            sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
            sqlString=sqlString + " \nwhere 1=1" ;
            sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
            sqlString=sqlString + " = " + pk;
            sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;

            oldRs = rs;
            rs=dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            treeOfItems.append(getTree(dBConnection,rs,"obj" + pk,level+1,jsFileName4Category));                
            dBConnection.freeResultSet(rs);
            rs=oldRs;            
          }
        }
        
        return treeOfItems;
        
      }catch(Exception e){
        this.logger.info("Exception Caught In gettreeOfItems() of Treeview.java");
        this.logger.fatal(e.getMessage()); 
        e.printStackTrace();
        throw e;
      }
  }

/**
 * Purpose : To append the tree with child folders .
 * @param categoryPk - A String object defines the pk of a category
 * @return void
 */  

  public void appendTree(String categoryPk)throws IOException,Exception{
    StringBuffer nextLevelFoldersString ;
    DBConnection dBConnection=null; 
    ResultSet rs=null;

    boolean isScriptTagPersists=false;
    try{
      this.logger.info("Treeview.appendTree() Starts");
      
      String sqlString;
      
      dBConnection = new DBConnection(this.dataSource);
      sqlString=" \nselect";
      sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
      sqlString=sqlString + " , \n" +  treeItem4Category.getNameColumn() ;
      sqlString=sqlString + " , \n" +  treeItem4Category.getDescriptionColumn();
      sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
      sqlString=sqlString + " \nwhere 1=1" ;
      sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
      sqlString=sqlString + " = " + categoryPk;
      sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;
      logger.debug("Sql String is " + sqlString );
      
      rs = dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      nextLevelFoldersString=getTree(dBConnection, rs, "obj" + categoryPk,1,categoryPk) ;      

      // nextLevelFoldersString is empty do not create the .js file  
      if (nextLevelFoldersString.toString().trim().length() > 0) {

        // Creates Folder wise .js Files
        if (!(writeToFile(nextLevelFoldersString,this.tempUserFolderPath+categoryPk+".js",false))){  
          this.logger.error("Unable to Create " +this.tempUserFolderPath+categoryPk+".js" + " File"); 
        }else{         
          this.logger.info("File" + this.tempUserFolderPath+categoryPk+".js" + " Created"); 
        }

        // Checks the Persistance of the script tag 
        for(int i=0; i<persistentScriptTags.size(); i++){
          if (isScriptTagPersists=(persistentScriptTags.get(i).equals(categoryPk))){
            break;
          }
        }

        // Adds scripttags to .jsp Files
        if (!(isScriptTagPersists)){  
          persistentScriptTags.add(categoryPk);
        }
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In appendTree() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
       if (dBConnection !=null){
        dBConnection.free(rs);
      }
    }
    this.logger.info("Treeview.appendTree() Ends");
  }

  /**
   * Purpose : Finds the Child Categories of a Deleted Category up to last Level, and Stores correspondign jsfiles in arraylist jsFiles2BDeleted
   * @param categoryPk - A String object defined by the Pk of Category to be Delete
   * @return void
   */
  private void jsFileSearch4CategoryDeletion(String categoryPk)throws Exception{
    String jsFilesOfChildOfDeletedCategory;
    boolean isJsFileExists = false;
    try{
      for (int i=0; i<this.persistentCategories.size();i++){
        jsFilesOfChildOfDeletedCategory = null;

        //JsFile Capturing for Childs Of Deleted Category
        if (((String[])this.persistentCategories.get(i))[PARENT].equals(categoryPk)){   //Checks If Files Is Parent File for any Child
          jsFilesOfChildOfDeletedCategory=((String[])this.persistentCategories.get(i))[JSFILE] ;  //Stores Js File   
        }

        //Adding Js File Name to an Array jsFiles2BDeleted
        if(jsFilesOfChildOfDeletedCategory!=null){
          for (int j=0; j< jsFiles2BDeleted.size();j++){
            if (isJsFileExists=(((String)jsFiles2BDeleted.get(j)).equals(jsFilesOfChildOfDeletedCategory))){
              break;
            }
          }

          if (!isJsFileExists){
            jsFiles2BDeleted.add(jsFilesOfChildOfDeletedCategory);       
          }
        }
      }

      //Recursive Call for jsFileSearch4CategoryDeletion(String categoryPk)

      for (int i=0; i<this.persistentCategories.size();i++){
        if (((String[])this.persistentCategories.get(i))[PARENT].equals(categoryPk)){
          jsFileSearch4CategoryDeletion(((String[])this.persistentCategories.get(i))[CATEGORYPK]);
        }
      }    
    }catch(Exception e){
      this.logger.info("Exception Caught In jsFileSearch4CategoryDeletion() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }                  
  }


  /**
   * Purpose : Finds the Category Deleted, Deletes The  respective Child Categories(s) 
   * @param categoryPk - A String object defined by the Pk of the Category to be Delete
   * @paran parentCategoryPk A String object defined by the Pk of the Parent Category of Category to be Delete
   * @return void
   */
  public void ifCategoryDeleted(String categoryPk, String parentCategoryPk)throws IOException,Exception{
    String jsFile2BRecreated = null;
    String siblingOrParentCategoryPk = null;
    DBConnection dBConnection=null; 
    ResultSet rs=null;

    int counter=0;
    try{
      this.logger.info("Treeview.ifCategoryDeleted() Starts");
      this.logger.info("Deleted Category Pk : " + categoryPk );
      
      //1. Finds Sibling  Or Parent Category (If no siblings available) Of the Deleted Category
      String sqlString;      
      dBConnection = new DBConnection(this.dataSource);
      sqlString=" \nselect";
      sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
      sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
      sqlString=sqlString + " \nwhere 1=1" ;
      sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
      if(parentCategoryPk.equals("-1")){
        sqlString=sqlString + " is null " ;
      }else{
        sqlString=sqlString + " = " + parentCategoryPk;
      }
      sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;
      logger.debug("Sql String is " + sqlString );
      
      rs = dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      while (rs.next()){
        siblingOrParentCategoryPk=rs.getString(treeItem4Category.getPkColumn());
        break;
      }
      
      if (siblingOrParentCategoryPk==null){
        siblingOrParentCategoryPk=parentCategoryPk;
      } 
      
      this.logger.info("Sibling Or Parent  Of Deleted Category : " + siblingOrParentCategoryPk );  

      //2. JsFile Capturing To be Recreated
      for (int i=0; i<this.persistentCategories.size() ;i++){
        if (((String[])this.persistentCategories.get(i))[CATEGORYPK].equals(siblingOrParentCategoryPk)){
          jsFile2BRecreated = ((String[])this.persistentCategories.get(i))[JSFILE];
          break;
        }  
      }   
      
      if(jsFile2BRecreated==null && siblingOrParentCategoryPk.equals("-1")){
        jsFile2BRecreated=this.userId;
      }
      
      if (jsFile2BRecreated!=null){
        this.logger.info("jsFile of Deleted Category is : " + jsFile2BRecreated);

        //3. Searchs the jsFiles of deleted Categories and Stores The File Names in files2BDeleted ArrayList
        jsFileSearch4CategoryDeletion(categoryPk);

        //4. Remove Category Pks contained in the .js File From "persistentCategories" Arraylist
        while(counter<this.persistentCategories.size()){
          if (((String[])this.persistentCategories.get(counter))[JSFILE].equals(jsFile2BRecreated)){
            this.persistentCategories.remove(counter);
          }else{  
            counter++;
          }  
        }    

        for (int i=0; i< this.jsFiles2BDeleted.size();i++){

          //5. Deleting the jsFiles of Child Categories of the Deleted Category
          this.logger.info("Delete : " + this.jsFiles2BDeleted.get(i) + ".js");
          if (new File(this.tempUserFolderPath+this.jsFiles2BDeleted.get(i)+".js").exists()){
            new File(this.tempUserFolderPath+this.jsFiles2BDeleted.get(i)+".js").delete();
          }

          //6. Remove Child Ids of the Deleted From "persistentCategories" Arraylist          
          counter=0;
          while(counter<this.persistentCategories.size()){
            if (((String[])this.persistentCategories.get(counter))[PARENT].equals(this.jsFiles2BDeleted.get(i))){
              this.persistentCategories.remove(counter);
            }else{  
              counter++;
            }  
          }    

          //7. Remove the Child Ids of the Deleted From "persistentScriptTags" Arraylist
          counter=0;
          while(counter<this.persistentScriptTags.size()){
            if (this.persistentScriptTags.get(counter).equals(this.jsFiles2BDeleted.get(i))){
              this.persistentScriptTags.remove(counter);
            }else{  
              counter++;
            }  
          }     
            
        }

        //8. Recreating the Js File of the Deleted Category
        if (jsFile2BRecreated.equals(this.userId)){
          initTree();
        }else{
            new File(this.tempUserFolderPath+jsFile2BRecreated+".js").delete();
            appendTree(jsFile2BRecreated);
        }   
          
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In ifCategoryDeleted() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{    
      if(dBConnection!=null) {
        dBConnection.free(rs);
      }
      this.logger.info("Treeview.ifCategoryDeleted() Ends");
    }
  }


  /**
   * Purpose : Finds js files containing the Sibling Categories of an Added Category and recreates the same js File
   *           If Sibling categories doesn't exist the jsFile that has to recreated will the js File containing parent will be recreated
   * @param categoryPk - A String object defined by the pk of the Category Added
   * @param parentCategoryPk - A String object defined by the pk of the parent Category of the Category Added
   * @return String object defined by the jsFile Name
   */
  private String jsFileSearch4CategoryAddition(String categoryPk, String parentCategoryPk)throws Exception{
  
    String jsFileOfAddedCategory=null;
    String siblingOrParentCategoryPk=null;
    DBConnection dBConnection=null; 
    ResultSet rs=null;
    

    this.logger.info("Treeview.jsFileSearch4CategoryAddition() Starts");
    try{
      //1. Finds Sibling  Or Parent Category Pk (If no siblings available) Of the Added Category
      String sqlString;
      dBConnection = new DBConnection(this.dataSource);
      sqlString=" \nselect";
      sqlString=sqlString + " \n" +  treeItem4Category.getPkColumn() ;
      sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
      sqlString=sqlString + " \nwhere 1=1" ;
      sqlString=sqlString + " \nand " +  treeItem4Category.getFkColumn() ;
      if(parentCategoryPk.equals("-1")){
        sqlString=sqlString + " is null " ;
      }else{
        sqlString=sqlString + " = " + parentCategoryPk;
      }
      sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;
      logger.debug("Sql String is " + sqlString );
      rs = dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      while (rs.next()){
        siblingOrParentCategoryPk=rs.getString(treeItem4Category.getPkColumn());
        break;
      }
      
      if (siblingOrParentCategoryPk==null){
        siblingOrParentCategoryPk=parentCategoryPk;
      } 
      
      this.logger.info("Sibling Or Parent  Of Add Category : " + siblingOrParentCategoryPk );  

     //JsFile Capturing for Added Category
      for (int i=0; i<this.persistentCategories.size() ;i++){
        if (((String[])this.persistentCategories.get(i))[CATEGORYPK].equals(siblingOrParentCategoryPk)){
          jsFileOfAddedCategory=((String[])this.persistentCategories.get(i))[JSFILE];
          break;
        }  
      } 

      if (jsFileOfAddedCategory==null && siblingOrParentCategoryPk.equals(categoryPk)){
        jsFileOfAddedCategory=this.userId;
      }

      this.logger.info("Js File Name Of AddedCategory : " + jsFileOfAddedCategory );  

    }catch(Exception e){
      this.logger.info("Exception Caught In jsFileSearch4CategoryAddition() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      if(dBConnection!=null) {
        dBConnection.free(rs);
      }
      this.logger.info("Treeview.jsFileSearch4CategoryAddition() Ends");   
    }
    return jsFileOfAddedCategory;  
  }

  /**
   * Purpose : To Add the Category and its Children to Tree 
   * @param categoryPk - A String object defined by the Pk of the Category to be Added
   * @param parentCategoryPk - A String object defined by the Pk of the Parent Category of the Category Added
   * @return void
   */

  public void ifCategoryAdded(String categoryPk, String parentCategoryPk) throws IOException,Exception{
    String jsFile4AddedCategory=null;
    int counter=0;
    try{
      this.logger.info("Treeview.ifCategoryAdded() Starts");
      this.logger.info("Added Category Pk : " + categoryPk);

      //Searchs Files for Recreation
      jsFile4AddedCategory=jsFileSearch4CategoryAddition(categoryPk,parentCategoryPk);
      if (jsFile4AddedCategory!=null){  
        this.logger.info("jsFile for Added Category is : " + jsFile4AddedCategory);

        //Remove Category Pk contained in the .js File contained by siblings of Category Added From "persistentCategories" Arraylist
        while(counter<this.persistentCategories.size()){
          if (((String[])this.persistentCategories.get(counter))[JSFILE].equals(jsFile4AddedCategory)){
            this.persistentCategories.remove(counter);
          }else{
            counter++;
          }
        }

        //Recreating the Js File of siblings of Added Category
        if (jsFile4AddedCategory.equals(this.userId)){
          initTree();
        }else{
          new File(this.tempUserFolderPath+jsFile4AddedCategory+".js").delete();
          appendTree(jsFile4AddedCategory);         
        }
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In ifCategoryAdded() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      this.logger.info("Treeview.ifCategoryAdded() Ends");
    }
  }

    /**
   * Purpose : To Recreate the respective Js File contained by the Category that Renamed
   * @param categoryPk - A String object defined by the pk of the Category Edited
   * @return void
   */
  public void ifCategoryEdited(String categoryPk)throws Exception{
    String jsFileOfEditedCategory=null;
    int counter=0;
    try{
      this.logger.info("Treeview.ifCategoryEdited Starts");
      this.logger.info("Edited Category Pk : " + categoryPk);

      //JsFile Capturing for Edited Category                 
      for (int i=0; i<this.persistentCategories.size() ;i++){
        if (((String[])this.persistentCategories.get(i))[CATEGORYPK].equals(categoryPk)){
          jsFileOfEditedCategory = ((String[])this.persistentCategories.get(i))[JSFILE];
          break;
        }  
      }

      if (jsFileOfEditedCategory!=null){
        this.logger.info("jsFile for Edited Category is : " + jsFileOfEditedCategory);

        //Remove Category Pks contained in the .js File contained by Category Edited From "persistentCategories" Arraylist
        while(counter<this.persistentCategories.size()){
          if (((String[])this.persistentCategories.get(counter))[JSFILE].equals(jsFileOfEditedCategory)){
            this.persistentCategories.remove(counter);
          }else{  
            counter++;
          }
        }

        //Recreating the Js File of the Edited Category
        if (jsFileOfEditedCategory.equals(this.userId)){
          initTree();
        }else{
          new File(this.tempUserFolderPath+jsFileOfEditedCategory+".js").delete();
          appendTree(jsFileOfEditedCategory);
        }
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In ifCategoryEdited() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      this.logger.info("Treeview.ifCategoryEdited Ends");
    }
  }

  /**
   * Purpose : To  delete the temp folder pertaining to the user  
   * @return void
   */
  private void freeTempFolder()throws IOException,Exception{
    try{  
      this.logger.info("Entering freeTempFolder() of Treeview.java");
      File tempFolder2BDeleted=new File(this.tempUserFolderPath);
      if ((tempFolder2BDeleted.exists())){

        // Deleting the files from the temp Folder
        File[] filesInTempFolder=tempFolder2BDeleted.listFiles();
        for(int index=0;index<filesInTempFolder.length;index++){
          if (!(filesInTempFolder[index].delete())){
            this.logger.error("Unable to Delete "+ filesInTempFolder[index].getName() + " File"); 
          }
        }
        // Deleting the Temp Folder
        if (!(tempFolder2BDeleted.delete())){    
          this.logger.error("Unable to Delete "+ this.tempUserFolderPath + " Directory"); 
        }else{
          this.logger.info("User On Session Temp Directory "+ this.tempUserFolderPath + " Deleted");      
        }  
      }else{
        this.logger.error("Unable to Find "+ this.tempUserFolderPath + " Directory"); 
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In freeTempFolder() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      this.logger.info("Exiting freeTempFolder() of Treeview.java");
    }
  }

  /**
   * Purpose : To free up the objects created in the constructor and 
   * delete the temp folder pertaining to the user  
   * @return void
   */
  public void free()throws IOException,Exception{
    try{  
      this.dataSource=null;
      this.userId=null;
      this.persistentCategories = null;
      this.persistentScriptTags = null;
      this.jsFiles2BDeleted =null;
      this.freeTempFolder();
      this.treeItem=null;
      this.treeItem4Category=null;
    }catch(Exception e){
      this.logger.info("Exception Caught In free() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      this.logger.info("Instance of the Treeview is Freed");
      this.logger=null;
    }
  }

  /**
   * Purpose : To Refresh the treeview 
   * @return void
   */
  public void refresh() throws IOException,Exception {
    this.logger.info("refresh Starts ");
    ArrayList tempPersistentCategories = new ArrayList();
    ArrayList tempPersistentScriptTags = new ArrayList();
    try{
      //Copying the arraylists PersistentCatgories and persistentScriptTags in Temp arraylist
      tempPersistentCategories = (ArrayList)this.persistentCategories.clone();
      tempPersistentScriptTags = (ArrayList)this.persistentScriptTags.clone();

      //Clearing the arraylists PersistentFolders and persistentScriptTags
      this.persistentCategories.clear();
      this.persistentScriptTags.clear();

      this.logger.info("size of persistentCategories is : " + tempPersistentCategories.size());

      //Deleting the temp folders
      this.freeTempFolder();
      this.initTree();
      for (int i=0; i<tempPersistentCategories.size();i++){
        String categoryPk=null;
        categoryPk= ((String [])tempPersistentCategories.get(i))[CATEGORYPK];
        this.appendTree(categoryPk);
        this.logger.info("Category Pk : " + categoryPk);
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In refresh() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }finally{
      this.logger.info("refresh ends ");    
    }
  }

  
  /**
   * Purpose : To Find recursively whether the Js File is created for this id or not 
   * @param categoryPk - A String object defined by the Id of the current Category in the listNavigation
   * @return void
   */
  private void recursion4ListNavigation(String categoryPk)throws Exception{
    boolean isJsFileCreated4Category=false;
    DBConnection dBConnection=null;
    ResultSet resultSet=null;
    String sqlString=null;
    try{
      this.logger.info("recursion4ListNavigation Starts For Category Pk: " + categoryPk);    
      for (int i=0; i<this.persistentCategories.size(); i++){
        if(isJsFileCreated4Category=(((String[])persistentCategories.get(i))[CATEGORYPK].equals(categoryPk))){
          break;
        }
      }

      if(!isJsFileCreated4Category){ 
        dBConnection=new DBConnection(this.dataSource);
        sqlString=" \nselect";
        sqlString=sqlString + " \n" +  treeItem4Category.getFkColumn() ;
        sqlString=sqlString + " \nfrom \n" +  treeItem4Category.getTableName() ;
        sqlString=sqlString + " \nwhere 1=1" ;
        sqlString=sqlString + " \nand " +  treeItem4Category.getPkColumn() ;
        if(categoryPk.equals("-1")){
          sqlString=sqlString + " is null " ;
        }else{
          sqlString=sqlString + " = " + categoryPk;
        }
        sqlString=sqlString + " \norder by  " + treeItem4Category.getNameColumn() ;
        logger.debug("Sql String is " + sqlString );
      
        resultSet = dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        if(resultSet.next()){
          String ancesstorCategoryPk=resultSet.getString(treeItem4Category.getFkColumn()) ; 
          this.ancesstorCategories.add(ancesstorCategoryPk);
          if(dBConnection!=null){
            dBConnection.free(resultSet);
          }
          recursion4ListNavigation(ancesstorCategoryPk);
        }
      }      
    }catch(Exception e){
      this.logger.info("Exception Caught In recursion4ListNavigation() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
    }finally{
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      }
      this.logger.info("recursion4ListNavigation Ends For Category Pk: " + categoryPk);    
    }
  }

  /**
  * Purpose : To Refresh the Treeview as per the Navigation in the List
  * @param categoryPk - A String object defined by the Id of the current Category in the List
  * @return void
  */
  public void forListNavigation(String categoryPk)throws Exception{

    this.logger.info("Treeview.forListNavigation Starts");
    String ancesstorCategoryPk=null;
    try{
      this.logger.info("Current Category Pk in List : " + categoryPk);    
      this.recursion4ListNavigation(categoryPk);
      this.logger.info("Size After recursion4ListNavigation Call First Time : " + ancesstorCategories.size() );
      int j=this.ancesstorCategories.size()-1;
      while(j>=0){
        ancesstorCategoryPk=(String)this.ancesstorCategories.get(j);
        this.logger.info("Ancesstor Category Pk Is : " + ancesstorCategoryPk);
        this.appendTree(ancesstorCategoryPk);
        this.logger.info("Size Before Clear :" + this.ancesstorCategories.size() );
        this.ancesstorCategories.clear();
        this.logger.info("Size After Clear :" + this.ancesstorCategories.size() );
        this.recursion4ListNavigation(categoryPk);
        this.logger.info("Size After recursion4ListNavigation Call :" + this.ancesstorCategories.size() );
        j=this.ancesstorCategories.size()-1;
      }
    }catch(Exception e){
      this.logger.info("Exception Caught In forListNavigation() of Treeview.java");
      this.logger.fatal(e.getMessage()); 
      throw e;
    }
    finally{
      this.logger.info("Treeview.forListNavigation Ends");
    }

  }



  /**
  * Purpose : Creates given file with a given String
  * @param singleBuffer - A StringBuffer object defines the content for the file 
  * @param fileName - A String object defines fullpath with name of file
  * @param append - A boolean data defines whether to append the file or overwrite  
  * @return A booean data results file created or not
  */
  private boolean writeToFile(StringBuffer singleBuffer, String fileName, boolean append)throws IOException,Exception{
    FileOutputStream out; 
    PrintStream p; 
    String toFile = singleBuffer.toString();
    try{
      out = new FileOutputStream(fileName,append);
      p = new PrintStream( out );
      if(toFile.endsWith("\n"))
        p.println (toFile.substring(0,toFile.lastIndexOf("\n")));   
      else
        p.println (toFile);   
      p.close();
      return true;
    }catch(Exception e){
      this.logger.info("Exception Caught In writeToFile() of Treeview.java");
      this.logger.fatal(e.getMessage());            
      throw e;
    }
  } 


  



  /**
  * Purpose : Returns dynamically generated js file Name.
  * @return String object defines js File Name
  */
  public String getJsFileName(){
    return this.jsFileName;
  }

  /**
  * Purpose : Returns whether categoryOnly tree Or not.
  * @return boolean value true or false
  */
  public boolean isCateryOnly(){
    return this.categoryOnly;
  }

  /**
  * Purpose : Returns returns dynamically generated jsp file Name.
  * @return String object defines jsp File Name
  */

  public String getJsFileLinks(){
    this.jsFileLinks=new String("");
    try{
      logger.info("Entering getJsFileLinks");
      for(int i=0; i<this.persistentScriptTags.size();i++){
        this.jsFileLinks= this.jsFileLinks+"<script src=\"" + this.tempUserFolderPathContext + ((String)persistentScriptTags.get(i)).toString() + ".js" + "\"></script>\n"; 
      }
    }catch(Exception e){
      logger.fatal("Exception Occured "+e);
    }finally{
      logger.info("Exiting getJsFileLinks");
    }
    return this.jsFileLinks;
  }

}