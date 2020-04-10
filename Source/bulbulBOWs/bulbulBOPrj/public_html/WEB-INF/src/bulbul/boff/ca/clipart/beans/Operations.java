package bulbul.boff.ca.clipart.beans;

import bulbul.boff.ca.clipart.actionforms.ClipartCategoryForm;
import bulbul.boff.ca.clipart.actionforms.ClipartForm;
import bulbul.boff.ca.clipart.actionforms.ClipartListForm;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.general.LOOperations;

import java.io.IOException;
import java.io.InputStream;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;


public final class Operations  {

  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate font_list.jsp with fonts and categories from database depending upon the data from 
   *           the search field in font_list.jsp.
   * @return ArrayList 
   * @param clipartCategoryTblPk - Integer.
   * @param clipartListForm - A FontListForm object.
   * @param dataSource - A DataSource object.
   */  
  public static ArrayList getCliparts(int clipartCategoryTblPk,ClipartListForm clipartListForm,DataSource dataSource,int numberOfRecords)throws SQLException,Exception{
    ArrayList cliparts=new ArrayList();
    ClipartListBean clipart=null;
    DBConnection dBConnection=null;
    ResultSet rsCategory=null;
    ResultSet rsClipart=null;
    String queryForCategory;
    String queryForClipart;

    int pageNumber=1;
    int categoryRecordCount=0;
    int clipartRecordCount=0;
    int recordCount=0;

    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getCliparts");
      if ((clipartListForm.getHdnSearchPageNo()!=null) && (clipartListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(clipartListForm.getHdnSearchPageNo());
      }  

      logger.debug("***pageNumber: "+pageNumber);

      //Query For Clipart Category
      queryForCategory="select * from clipart_category_tbl where 1=1 and clipart_category_tbl_fk ";

      if (clipartCategoryTblPk==-1){
        queryForCategory=queryForCategory+"is null ";
      }else{
        queryForCategory=queryForCategory+"="+clipartCategoryTblPk;
      }
    
      if(clipartListForm.getRadSearchClipartOrCategory() !=null && clipartListForm.getRadSearchClipartOrCategory().equals("categorySelect")){
        if( clipartListForm.getTxtSearchClipartCategory() != null && (clipartListForm.getTxtSearchClipartCategory().trim()).length() != 0 ){
          queryForCategory = queryForCategory + " and clipart_category like '%"+clipartListForm.getTxtSearchClipartCategory()+"%'";
        }
        if( clipartListForm.getTxtSearchCategoryDescription() != null && (clipartListForm.getTxtSearchCategoryDescription().trim()).length() != 0 ){
          queryForCategory = queryForCategory + " and clipart_category_description like '%"+clipartListForm.getTxtSearchCategoryDescription()+"%'";
        }
      }
      
      if(clipartListForm.getRadSearchStatus() != null && (clipartListForm.getRadSearchStatus().trim()).length() != 0 && !clipartListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForCategory = queryForCategory + " and is_active="+clipartListForm.getRadSearchStatus();
      }

      queryForCategory=queryForCategory + " order by clipart_category ";

      
      //Query For Clipart
      queryForClipart="select * from clipart_tbl where clipart_category_tbl_pk="+clipartCategoryTblPk;

      if(clipartListForm.getRadSearchClipartOrCategory() !=null && clipartListForm.getRadSearchClipartOrCategory().equals("clipartSelect")){
        if( clipartListForm.getTxtSearchClipartName() != null && (clipartListForm.getTxtSearchClipartName().trim()).length() != 0 ){
          queryForClipart = queryForClipart + " and clipart_name like '%"+clipartListForm.getTxtSearchClipartName()+"%'";
        }
        if( clipartListForm.getTxtSearchClipartKeywords() != null && (clipartListForm.getTxtSearchClipartKeywords().trim()).length() != 0 ){
          queryForClipart = queryForClipart + " and clipart_keywords like '%"+clipartListForm.getTxtSearchClipartKeywords()+"%'";
        }
      }
      if(clipartListForm.getRadSearchStatus() != null && (clipartListForm.getRadSearchStatus().trim()).length() != 0 && !clipartListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForClipart = queryForClipart + " and is_active="+clipartListForm.getRadSearchStatus();
      }

      queryForClipart=queryForClipart+" order by clipart_name ";

      dBConnection= new  DBConnection(dataSource);  
      
      if(clipartListForm.getRadSearchClipartOrCategory()!=null && clipartListForm.getRadSearchClipartOrCategory().equals("categorySelect") && (clipartListForm.getTxtSearchClipartCategory() != null && (clipartListForm.getTxtSearchClipartCategory().trim()).length() != 0)){
          rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
          logger.debug("Only queryForCategory executed: "+queryForCategory);
      }else if(clipartListForm.getRadSearchClipartOrCategory()!=null && clipartListForm.getRadSearchClipartOrCategory().equals("clipartSelect") && (clipartListForm.getTxtSearchClipartName() != null && (clipartListForm.getTxtSearchClipartName().trim()).length() != 0)){
          rsClipart=dBConnection.executeQuery(queryForClipart,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          logger.debug("Only queryForClipart executed: "+queryForClipart);
      }else{
          rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          rsClipart=dBConnection.executeQuery(queryForClipart,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          logger.debug("Both query executed: ");
          logger.debug("queryForCategory: "+queryForCategory);
          logger.debug("queryForClipart: "+queryForClipart);
      }
     
      if (rsCategory!=null){
        rsCategory.last();
        categoryRecordCount=rsCategory.getRow();
        rsCategory.beforeFirst();
      }
    
      if(rsClipart!=null){
        rsClipart.last();
        clipartRecordCount=rsClipart.getRow();
        rsClipart.beforeFirst();
      }

      recordCount=categoryRecordCount+clipartRecordCount;
      if(recordCount!=0){
        pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }

      if (pageNumber>pageCount) {
        pageNumber=pageCount;
      }
    
      startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
      endIndex=(startIndex + numberOfRecords)-1;
    
      logger.debug("Category count : " + categoryRecordCount );
      logger.debug("Clipart count : " + clipartRecordCount );
      logger.debug("Before Category");
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);

      //Filling Category Records
      if (rsCategory!=null){
        if (startIndex<=categoryRecordCount){
          if (startIndex>0){
            rsCategory.relative(startIndex);
          }

          while(rsCategory.next()){
            clipart=new ClipartListBean();
            clipart.setClipartOrCategoryTblPk(rsCategory.getString("clipart_category_tbl_pk"));
            clipart.setClipartOrCategoryName(rsCategory.getString("clipart_category"));
            clipart.setClipartOrCategoryDesc(rsCategory.getString("clipart_category_description"));
            clipart.setClipartOrCategoryType(BOConstants.CLIPARTCATEGORY.toString());
            clipart.setIsActive(rsCategory.getString("is_active"));
            clipart.setIsActiveDisplay(rsCategory.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
            cliparts.add(clipart);
            startIndex++;
            logger.debug("***startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }
        }
      }

      logger.debug("After Category");
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);
      logger.debug("categoryRecordCount :"+categoryRecordCount);

      //Filling Font Records
      if(rsClipart !=null){
        if (startIndex<=endIndex){
          if (startIndex>=categoryRecordCount){

            if(startIndex-categoryRecordCount>0){
              rsClipart.relative(startIndex-categoryRecordCount);
            }
          
            while(rsClipart.next()){
              clipart=new ClipartListBean();
              clipart.setClipartOrCategoryTblPk(rsClipart.getString("clipart_tbl_pk"));
              clipart.setClipartOrCategoryName(rsClipart.getString("clipart_name"));
              clipart.setClipartOrCategoryDesc(rsClipart.getString("clipart_keywords"));
              clipart.setClipartOrCategoryType(BOConstants.CLIPART.toString());
              clipart.setIsActive(rsClipart.getString("is_active"));
              clipart.setIsActiveDisplay(rsClipart.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
              cliparts.add(clipart);
              startIndex++;
              logger.debug("***startIndex: "+startIndex);
              if (startIndex>endIndex){
                break;
              }
            }    
          }
        }
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rsCategory);
        dBConnection.freeResultSet(rsClipart);
        dBConnection.free(); 
      }
      dBConnection=null;
      clipartListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      clipartListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      clipartListForm.setHdnSearchClipartOrCategoryTblPk(clipartCategoryTblPk);
      logger.info("Exiting getCliparts");
    }
    return cliparts;
  }

  public static void addClipart(ClipartForm clipartForm,DataSource dataSource)throws SQLException, IOException, Exception{
    InputStream is = null;
    String sqlString =null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    ResultSet rs = null;
    FormFile imgFile=null;
    String contentType=null;

    byte[] content = null;
    
    byte[] stdContent=null;
    int stdContentSize;
    String stdContentType=null;
    
    int contentSize;
    int returnClipartTblPk;
    int oid;
    int stdOid;

    try{
      logger.info("Entering addClipart");      
      dBConnection = new DBConnection(dataSource);
      String query="select clipart_name from clipart_tbl where clipart_name = '"+clipartForm.getTxtClipart()+"' and clipart_category_tbl_pk= "+clipartForm.getHdnClipartCategoryTblPk();
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      imgFile=clipartForm.getImgFile();
      contentType=imgFile.getContentType();
      contentSize=imgFile.getFileData().length;
      
      logger.debug("image file name: "+imgFile.getFileName());
      logger.debug("image file size: "+imgFile.getFileSize());
      
      oid=LOOperations.getLargeObjectId(dataSource);
      stdOid = LOOperations.getLargeObjectId(dataSource);
      
      content=new byte[contentSize];
      is=imgFile.getInputStream();
      is.read(content);
      
      stdContent = General.svgToPNG(content,BOConstants.SVG_STANDARD_SIZE);
      stdContentSize = stdContent.length;
      stdContentType = "image/png"; 
      
      LOOperations.putLargeObjectContent(oid,content,dataSource);
      LOOperations.putLargeObjectContent(stdOid,stdContent,dataSource);

      sqlString = "{?=call sp_ins_clipart_tbl(?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartForm.getHdnClipartCategoryTblPk());
      cs.setString(3,clipartForm.getTxtClipart().trim());
      cs.setString(4,clipartForm.getTxaClipartKeywords());
      cs.setInt(5,oid);
      cs.setString(6,contentType);
      cs.setInt(7,contentSize);
      cs.setInt(8,stdOid);
      cs.setString(9,stdContentType);
      cs.setInt(10,stdContentSize);

      dBConnection.executeCallableStatement(cs);
      returnClipartTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnClipartTblPk);
    }catch(SQLException e){
      logger.info(e);
      throw e;
    }catch(IOException e){
      logger.info(e);
      throw e;
    }catch(Exception e){
      logger.info(e);
      throw e;
    }finally{
      if (is!=null){
        is.close();
      }
      if(dBConnection!=null){
        dBConnection.freeResultSet(rs);
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting addClipart");      
    }
  }

  public static int addClipartCategory(ClipartCategoryForm clipartCategoryForm,DataSource dataSource)throws SQLException,Exception{

    String sqlString;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    int returnClipartCategoryTblPk=-1;
    
    try{
      logger.info("Entering addClipartCategory");
      dBConnection =  new  DBConnection(dataSource);
      sqlString = "{?=call sp_ins_clipart_category_tbl(?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartCategoryForm.getHdnClipartCategoryTblFk());
      cs.setString(3,clipartCategoryForm.getTxtClipartCategory().trim());
      cs.setString(4,clipartCategoryForm.getTxaClipartCategoryDesc());
      dBConnection.executeCallableStatement(cs);
      returnClipartCategoryTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnClipartCategoryTblPk);      
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);    
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting addClipartCategory");
    }
    return returnClipartCategoryTblPk;
  }

  public static ClipartForm  viewClipart(String clipartTblPk,DataSource dataSource) throws Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    ClipartForm clipartForm=null;
    String parentCategory=null;
    try{
      logger.info("Entering viewClipart");
      logger.debug("String value of clipart_tbl_pk is:"+clipartTblPk); 
      sqlString="select clipart_tbl_pk,clipart_category_tbl_pk,clipart_name,clipart_keywords,clipart_image,content_type,content_size,clipart_std_image,std_content_type,std_content_size from clipart_tbl where clipart_tbl_pk="+clipartTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        clipartForm = new ClipartForm();
        clipartForm.setHdnClipartTblPk(rs.getInt("clipart_tbl_pk"));
        clipartForm.setHdnClipartCategoryTblPk(rs.getInt("clipart_category_tbl_pk"));
        clipartForm.setTxtClipart(rs.getString("clipart_name"));
        parentCategory=Operations.getClipartCategory(clipartForm.getHdnClipartCategoryTblPk(),dataSource);
        clipartForm.setTxtParentCategory(parentCategory);
        clipartForm.setTxaClipartKeywords(rs.getString("clipart_keywords"));
        clipartForm.setHdnImgOid(rs.getString("clipart_image"));
        clipartForm.setHdnContentType(rs.getString("content_type"));
        clipartForm.setHdnContentSize(rs.getInt("content_size"));
        clipartForm.setHdnStdImgOid(rs.getString("clipart_std_image"));
        clipartForm.setHdnStdContentType(rs.getString("std_content_type"));
        clipartForm.setHdnStdContentSize(rs.getInt("std_content_size"));
      }
      else{
        throw new Exception("clipart_tbl_pk not found");
      }
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting viewClipart");
    }
    return clipartForm;
  }

   public static void editClipart(ClipartForm clipartForm,DataSource dataSource)throws SQLException,IOException, Exception{
    DBConnection dBConnection= null;
    ResultSet rs = null;
    CallableStatement cs = null;
    FormFile imgFile=null;
    InputStream is = null;
    String contentType=null;
    String stdContentType=null;
    String sqlString = null;
    
    int contentSize;
    int stdContentSize;
    byte[] content=null;
    byte[] stdContent=null;
    int returnClipartTblPk;
    int oid;
    int stdOid=0;

    try{
      logger.info("Entering edilClipart ");
      sqlString = "{?=call sp_upd_clipart_tbl(?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      String query="select clipart_name from clipart_tbl where clipart_name = '"+clipartForm.getTxtClipart()+"' and clipart_category_tbl_pk= "+clipartForm.getHdnClipartCategoryTblPk()+" and clipart_tbl_pk!="+clipartForm.getHdnClipartTblPk() ;
      logger.debug("***query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      imgFile=clipartForm.getImgFile();
      oid=Integer.parseInt(clipartForm.getHdnImgOid());
      stdOid = Integer.parseInt(clipartForm.getHdnStdImgOid());
      
      logger.debug("**oid: "+oid);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartForm.getHdnClipartTblPk());
      cs.setInt(3,clipartForm.getHdnClipartCategoryTblPk());      
      cs.setString(4,clipartForm.getTxtClipart().trim());
      cs.setString(5,clipartForm.getTxaClipartKeywords());
      if(imgFile!=null && imgFile.getFileSize()>0){
        contentType=imgFile.getContentType();
        contentSize=imgFile.getFileData().length;
        content=new byte[contentSize];
        is=imgFile.getInputStream();
        is.read(content);
        
        stdContent = General.svgToPNG(content,BOConstants.SVG_STANDARD_SIZE);
        stdContentType = "image/png";
        stdContentSize = stdContent.length;
        
        LOOperations.putLargeObjectContent(oid,content,dataSource);
        cs.setString(6,contentType);
        cs.setInt(7,contentSize);
        
        LOOperations.putLargeObjectContent(stdOid,stdContent,dataSource);
        cs.setString(8,stdContentType);
        cs.setInt(9,stdContentSize);
      }else{
        cs.setString(6,clipartForm.getHdnContentType());
        cs.setInt(7,clipartForm.getHdnContentSize());
        cs.setString(8,clipartForm.getHdnStdContentType());
        cs.setInt(9,clipartForm.getHdnStdContentSize());
      }
      dBConnection.executeCallableStatement(cs);
      returnClipartTblPk=cs.getInt(1);
      logger.debug("updated clipart_tbl_pk:"+returnClipartTblPk);
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(IOException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if(is!=null){
        is.close();
      }
      if(dBConnection!=null){
        dBConnection.freeResultSet(rs);
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting edilClipart ");
    }
  }

  public static ClipartCategoryForm  viewClipartCategory(int clipartCategoryTblPk,DataSource dataSource) throws SQLException, Exception{
    
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    ClipartCategoryForm clipartCategoryForm=null;
    String parentCategory=null;
    
    try{    
      logger.info("Entering viewClipartCategory");
      logger.debug("String value of clipart_category_tbl_pk is:"+clipartCategoryTblPk); 

      sqlString="select clipart_category_tbl_pk,clipart_category_tbl_fk,clipart_category,clipart_category_description from clipart_category_tbl where clipart_category_tbl_pk="+clipartCategoryTblPk;
      dBConnection=new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        
        clipartCategoryForm = new ClipartCategoryForm();
        clipartCategoryForm.setHdnClipartCategoryTblPk(rs.getInt("clipart_category_tbl_pk"));
        clipartCategoryForm.setHdnClipartCategoryTblFk((rs.getInt("clipart_category_tbl_fk")==0)?-1:rs.getInt("clipart_category_tbl_fk"));
        clipartCategoryForm.setTxtClipartCategory(rs.getString("clipart_category"));
        if(clipartCategoryForm.getHdnClipartCategoryTblFk()!=-1){
          parentCategory=Operations.getClipartCategory(clipartCategoryForm.getHdnClipartCategoryTblFk(),dataSource);  
        }else{
          parentCategory=BOConstants.ALL.toString();
        }
        clipartCategoryForm.setTxtParentCategory(parentCategory);
        clipartCategoryForm.setTxaClipartCategoryDesc(rs.getString("clipart_category_description"));
      }else{
        throw new Exception("clipart_tbl_pk not found");
      }
    }catch(SQLException e){
     logger.error(e);
     throw e;
    }catch(Exception e){
     logger.error(e);
     throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting viewClipartCategory");
    }
    return clipartCategoryForm;
  }

  public static int editClipartCategory(ClipartCategoryForm clipartCategoryForm,DataSource dataSource)throws SQLException,  Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnClipartCategoryTblPk=-1;
    try{
      logger.info("Entering editClipartCategory");
      sqlString = "{?=call sp_upd_clipart_category_tbl(?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartCategoryForm.getHdnClipartCategoryTblPk());
      cs.setInt(3,clipartCategoryForm.getHdnClipartCategoryTblFk());      
      cs.setString(4,clipartCategoryForm.getTxtClipartCategory().trim());
      cs.setString(5,clipartCategoryForm.getTxaClipartCategoryDesc());
      dBConnection.executeCallableStatement(cs);
      returnClipartCategoryTblPk=cs.getInt(1);
      logger.debug("updated clipart_category_tbl_pk:"+returnClipartCategoryTblPk);
    }catch(SQLException e)  {
      logger.error(e);
      throw e;
    }catch(Exception e)  {
      logger.error(e);
      throw e;
    }
    finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editClipartCategory");
    }
    return returnClipartCategoryTblPk;
  }

  public static void deleteClipart(int clipartTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnClipartTblPk;

    try{
      logger.debug("Entering deleteClipart");
      sqlString = "{? = call sp_del_clipart_tbl(?)}";
      logger.debug("returnClipartTblPk value: "+clipartTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnClipartTblPk=cs.getInt(1);
      logger.debug("Deleted clipart_tbl_pk:"+returnClipartTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.debug("Exiting deleteClipart");      
    }
  }

  public static int deleteClipartCategory(int clipartCategoryTblPk,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnClipartCategoryTblPk=-1;
    
    try{
      logger.info("Entering deleteClipartCategory");
      sqlString = "{? = call sp_del_clipart_category_tbl(?)}";    
      logger.debug("returnClipartCategoryTblPk value: "+clipartCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnClipartCategoryTblPk=cs.getInt(1);
      logger.debug("Deleted clipart_category_tbl_pk:"+returnClipartCategoryTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting deleteClipartCategory");      
    }
    return returnClipartCategoryTblPk;
  }

  public static void actDeactClipart(int clipartTblPk,ClipartListForm clipartListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnClipartTblPk;
    
    try{
      logger.info("Entering actDeactClipart");
      sqlString = "{? = call sp_act_deact_clipart_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(clipartListForm.getHdnSearchCurrentStatus());
      logger.debug("ClipartTblPk value: "+clipartTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnClipartTblPk=cs.getInt(1);
      logger.debug("ActDeact clipart_tbl_pk:"+returnClipartTblPk);
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting actDeactClipart");
    }
  }

  public static void actDeactClipartCategory(int clipartCategoryTblPk,ClipartListForm clipartListForm,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnClipartCategoryTblPk;

    try{
      logger.info("Entering actDeactClipartCategory");
      sqlString = "{? = call sp_act_deact_clipart_category_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(clipartListForm.getHdnSearchCurrentStatus());
      logger.debug("CategoryTblPk value: "+clipartCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection .prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,clipartCategoryTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnClipartCategoryTblPk=cs.getInt(1);
      logger.debug("ActDeact clipart_tbl_pk:"+returnClipartCategoryTblPk);
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting actDeactClipartCategory");
    }
  }

  public static String getClipartName(int clipartTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String clipartName=null;
    try{
      logger.info("Entering getClipartName");
      dBConnection = new DBConnection(dataSource);
      sqlString="select clipart_name from clipart_tbl where clipart_tbl_pk="+clipartTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       clipartName=rs.getString("clipart_name"); 
      }
    }catch(SQLException e){
      logger.error(e); 
      throw e;
    }catch(Exception e){
      logger.error(e); 
      throw e;
    }finally{
      if (dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getClipartName");
    }
    return clipartName;
  }

  
  public static String getClipartCategory(int clipartCategoryTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String clipartCategory=null;
    try{
      logger.info("Entering getClipartCategory");
      dBConnection = new DBConnection(dataSource);
      sqlString="select clipart_category from clipart_category_tbl where clipart_category_tbl_pk="+clipartCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       clipartCategory=rs.getString("clipart_category"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getClipartCategory");
    }
    return clipartCategory;
  }

  public static int getClipartCategoryFk(int clipartCategoryTblPk,DataSource dataSource)throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    int clipartCategoryTblFk=-1;
    try{
      logger.info("Entering getClipartCategoryFk");
      dBConnection = new DBConnection(dataSource);
      sqlString="select clipart_category_tbl_fk from clipart_category_tbl where clipart_category_tbl_pk="+clipartCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       clipartCategoryTblFk=rs.getInt("clipart_category_tbl_fk"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getClipartCategoryFk");
    }
    return clipartCategoryTblFk;
  }

  public static int getClipartCategoryPk(int clipartTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    int clipartCategoryTblPk=-1;
    try{
      logger.error("Entering getClipartCategoryPk");
      dBConnection=new DBConnection(dataSource);
      sqlString="select clipart_category_tbl_pk from clipart_tbl where clipart_tbl_pk="+clipartTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       clipartCategoryTblPk=rs.getInt("clipart_category_tbl_Pk"); 
      }
    }catch(SQLException e){
      logger.error(e); 
      throw e;
    }catch(Exception e){
      logger.error(e); 
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.error("Exiting getClipartCategoryPk");
    }
    return clipartCategoryTblPk;
  }

  public static void moveClipartCategory(int srcCategoryTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnClipartCategoryTblPk;
    
    try{
      logger.info("Entering moveClipartCategory");
      sqlString = "{? = call sp_mov_clipart_category_tbl(?,?)}";

      dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcCategoryTblPk); 
      cs.setInt(3,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnClipartCategoryTblPk=cs.getInt(1);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting moveClipartCategory");
    }
  }

  public static void moveClipart(int srcClipartTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnClipartTblPk;
    
    try{
      logger.info("Entering moveClipart");
      sqlString = "{? = call sp_mov_clipart_tbl(?,?)}";

      dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcClipartTblPk); 
      cs.setInt(3,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnClipartTblPk=cs.getInt(1);
      logger.debug("move returnClipartTblPk:"+returnClipartTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting moveClipart");
    }
  }
}