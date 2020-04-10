package bulbul.boff.ca.merchand.beans;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryForm;
import bulbul.boff.ca.merchand.actionforms.MerchandiseDecorationForm;
import bulbul.boff.ca.merchand.actionforms.MerchandiseDecorationListForm;
import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.actionforms.MerchandiseListForm;
import bulbul.boff.ca.merchand.actionforms.PricePromotionForm;
import bulbul.boff.ca.merchand.actionforms.PricePromotionListForm;
import bulbul.boff.ca.merchand.actionforms.PrintableAreaForm;
import bulbul.boff.ca.merchand.actionforms.PrintableAreaListForm;
import bulbul.boff.ca.merchand.actionforms.PrintableAreaPriorityForm;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.general.LOOperations;
import bulbul.boff.general.PgSQLArray;

import java.io.IOException;
import java.io.InputStream;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;


public final class Operations  {

  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

   public static ArrayList getMerchandises(int merchandiseCategoryTblPk,MerchandiseListForm merchandiseListForm,DataSource dataSource,int numberOfRecords){
    ArrayList merchandises=new ArrayList();
    MerchandiseListBean merchandise=null;
    DBConnection dbConnection=null;
    ResultSet rsCategory=null;
    ResultSet rsMerchandise=null;
    String queryForCategory;
    String queryForMerchandise;

    int pageNumber=1;
    int categoryRecordCount=0;
    int merchandiseRecordCount=0;
    int recordCount=0;

    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    if ((merchandiseListForm.getHdnSearchPageNo()!=null) && (merchandiseListForm.getHdnSearchPageNo().trim().length()>0)){
      pageNumber=Integer.parseInt(merchandiseListForm.getHdnSearchPageNo());
    }  

    logger.debug("pageNumber: "+pageNumber);

    try{

      //Query For Merchandise Category
      queryForCategory="select * from merchandise_category_tbl where 1=1 and merchandise_category_tbl_fk ";

      if (merchandiseCategoryTblPk==-1){
        queryForCategory=queryForCategory+"is null ";
      }else{
        queryForCategory=queryForCategory+"="+merchandiseCategoryTblPk;
      }
    
    
      if(merchandiseListForm.getRadSearchMerchandiseOrCategory() !=null && merchandiseListForm.getRadSearchMerchandiseOrCategory().equals("categorySelect")){
        if( merchandiseListForm.getTxtSearchMerchandiseCategory() != null && (merchandiseListForm.getTxtSearchMerchandiseCategory().trim()).length() != 0 ){
          queryForCategory = queryForCategory + " and merchandise_category like '%"+merchandiseListForm.getTxtSearchMerchandiseCategory()+"%'";
        }
        
      }
      if(merchandiseListForm.getRadSearchStatus() != null && (merchandiseListForm.getRadSearchStatus().trim()).length() != 0 && !merchandiseListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForCategory = queryForCategory + " and is_active="+merchandiseListForm.getRadSearchStatus();
      }

      queryForCategory=queryForCategory + " order by merchandise_category ";

      
      //Query For Merchandise
      queryForMerchandise="SELECT * from merchandise_tbl m inner join merchandise_parents_tbl as mp on (m.merchandise_tbl_pk=mp.merchandise_tbl_pk ) where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;

      if(merchandiseListForm.getRadSearchMerchandiseOrCategory() !=null && merchandiseListForm.getRadSearchMerchandiseOrCategory().equals("merchandiseSelect")){
        if( merchandiseListForm.getTxtSearchMerchandiseName() != null && (merchandiseListForm.getTxtSearchMerchandiseName().trim()).length() != 0 ){
          queryForMerchandise = queryForMerchandise + " and merchandise_name like '%"+merchandiseListForm.getTxtSearchMerchandiseName()+"%'";
        }
      }
      
      if(merchandiseListForm.getRadSearchStatus() != null && (merchandiseListForm.getRadSearchStatus().trim()).length() != 0 && !merchandiseListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForMerchandise = queryForMerchandise + " and is_active="+merchandiseListForm.getRadSearchStatus();
      }

      queryForMerchandise=queryForMerchandise+" order by merchandise_name ";


      dbConnection=new DBConnection(dataSource);
      
      if(merchandiseListForm.getRadSearchMerchandiseOrCategory()!=null && merchandiseListForm.getRadSearchMerchandiseOrCategory().equals("categorySelect") && (merchandiseListForm.getTxtSearchMerchandiseCategory() != null && (merchandiseListForm.getTxtSearchMerchandiseCategory().trim()).length() != 0)){
          rsCategory=dbConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
          logger.debug("Only queryForCategory executed: "+queryForCategory);
      }else if(merchandiseListForm.getRadSearchMerchandiseOrCategory()!=null && merchandiseListForm.getRadSearchMerchandiseOrCategory().equals("merchandiseSelect") && (merchandiseListForm.getTxtSearchMerchandiseName() != null && (merchandiseListForm.getTxtSearchMerchandiseName().trim()).length() != 0)){
          rsMerchandise=dbConnection.executeQuery(queryForMerchandise,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
          logger.debug("Only queryForMerchandise executed: "+queryForMerchandise);
      }else{
          rsCategory=dbConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
          rsMerchandise=dbConnection.executeQuery(queryForMerchandise,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
          logger.debug("Both query executed: ");
          logger.debug("queryForCategory: "+queryForCategory);
          logger.debug("queryForMerchandise: "+queryForMerchandise);
        }
     
      
      if (rsCategory!=null){
        rsCategory.last();
        categoryRecordCount=rsCategory.getRow();
        rsCategory.beforeFirst();
      }
    
      if(rsMerchandise!=null){
        rsMerchandise.last();
        merchandiseRecordCount=rsMerchandise.getRow();
        rsMerchandise.beforeFirst();
      }

      recordCount=categoryRecordCount+merchandiseRecordCount;
      if(recordCount!=0){
        pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }

      if (pageNumber>pageCount) {
        pageNumber=pageCount;
        logger.debug("pageNumber: "+pageNumber);
      }
    
      startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
      endIndex=(startIndex + numberOfRecords)-1;
    
      logger.debug("Category count : " + categoryRecordCount );
      logger.debug("Merchandise count : " + merchandiseRecordCount );
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
            merchandise=new MerchandiseListBean();
            merchandise.setMerchandiseOrCategoryTblPk(rsCategory.getString("merchandise_category_tbl_pk"));
            merchandise.setMerchandiseOrCategoryName(rsCategory.getString("merchandise_category"));
            merchandise.setMerchandiseOrCategoryDesc(rsCategory.getString("merchandise_category_description"));
            merchandise.setMerchandiseOrCategoryType(BOConstants.MERCHANDISECATEGORY.toString());
            merchandise.setFlag(rsCategory.getString("m_or_c").equals(BOConstants.MERCHANDISE_CATEGORY_ONLY_VAL.toString())?BOConstants.MERCHANDISE_CATEGORY_ONLY.toString():BOConstants.MERCHANDISE_ONLY.toString());
            merchandise.setIsActive(rsCategory.getString("is_active"));
            merchandise.setIsActiveDisplay(rsCategory.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
            merchandises.add(merchandise);
            startIndex++;
            logger.debug("startIndex: "+startIndex);
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
      if(rsMerchandise !=null){
        if (startIndex<=endIndex){
          if (startIndex>=categoryRecordCount){

            if(startIndex-categoryRecordCount>0){
              rsMerchandise.relative(startIndex-categoryRecordCount);
            }
          
            while(rsMerchandise.next()){
              merchandise=new MerchandiseListBean();
              merchandise.setMerchandiseOrCategoryTblPk(rsMerchandise.getString("merchandise_tbl_pk"));
              merchandise.setMerchandiseOrCategoryName(rsMerchandise.getString("merchandise_name"));
              merchandise.setMerchandiseOrCategoryDesc(rsMerchandise.getString("merchandise_description"));
              merchandise.setMerchandiseOrCategoryType(BOConstants.MERCHANDISE.toString());
              merchandise.setIsActive(rsMerchandise.getString("is_active"));
              merchandise.setIsActiveDisplay(rsMerchandise.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
              merchandises.add(merchandise);
              startIndex++;
              logger.debug("startIndex: "+startIndex);
              if (startIndex>endIndex){
                break;
              }
            }    
          }
        }
      }
    
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
    }finally{
      if(dbConnection!=null){
        dbConnection.free(rsCategory);
        dbConnection.free(rsMerchandise);
        dbConnection.free();
      }
      dbConnection=null;

      logger.debug("At Last pageNumber: "+pageNumber);
      
      merchandiseListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      merchandiseListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      merchandiseListForm.setHdnSearchMerchandiseOrCategoryTblPk(merchandiseCategoryTblPk);
    }
    return merchandises;
  }

  public static int addMerchandiseCategory(int userProfileTblPk,MerchandiseCategoryForm merchandiseCategoryForm,DataSource dataSource)throws SQLException,Exception{

    InputStream is=null;
    String sqlString;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    FormFile categoryImgFile=null;
    ResultSet rs=null;
    int returnMerchandiseCategoryTblPk=-1;
    int oid;
    String contentType=null;
    byte[] content=null;
    int contentSize;
    String query=null;
    String sizeTypeTblPkString=null;
    try{
      logger.info("Entering addMerchandiseCategory");
      dBConnection =  new  DBConnection(dataSource);

      query="select merchandise_category from merchandise_category_tbl ";
      query=query+" where merchandise_category = '"+merchandiseCategoryForm.getTxtMerchandiseCategory()+"'" ;
      if(merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk() == -1)
        query=query+" and merchandise_category_tbl_fk is null ";      
      else
        query=query+" and merchandise_category_tbl_fk= "+merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk();
      
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      categoryImgFile=merchandiseCategoryForm.getCategoryImgFile();
      contentType=categoryImgFile.getContentType();
      contentSize=categoryImgFile.getFileData().length;
      
      logger.debug("categoryImgFile file name: "+categoryImgFile.getFileName());
      logger.debug("categoryImgFile file size: "+categoryImgFile.getFileSize());
      
      oid=LOOperations.getLargeObjectId(dataSource);

      content=new byte[contentSize];
      is=categoryImgFile.getInputStream();
      is.read(content);
      sizeTypeTblPkString="{";
      if(merchandiseCategoryForm.getHdnSizeTypeTblPk().trim().length()>0){
        sizeTypeTblPkString=sizeTypeTblPkString+merchandiseCategoryForm.getHdnSizeTypeTblPk();
      }
      sizeTypeTblPkString=sizeTypeTblPkString+"}";
      logger.debug("sizeTypeTblPkString : " + sizeTypeTblPkString);
      LOOperations.putLargeObjectContent(oid,content,dataSource);
      sqlString = "{?=call sp_ins_merchandise_category_tbl(?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk());
      cs.setString(3,merchandiseCategoryForm.getTxtMerchandiseCategory().trim());
      cs.setString(4,merchandiseCategoryForm.getTxaMerchandiseCategoryDesc());
      cs.setInt(5,oid);
      cs.setInt(6,userProfileTblPk);
      cs.setString(7,contentType);
      cs.setInt(8,contentSize);
      cs.setInt(9,merchandiseCategoryForm.getRadMOrC());
      cs.setArray(10,new PgSQLArray(sizeTypeTblPkString,Types.INTEGER));
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseCategoryTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnMerchandiseCategoryTblPk);      
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
      logger.info("Exiting addMerchandiseCategory");
    }
    return returnMerchandiseCategoryTblPk;
  }

  public static MerchandiseCategoryForm viewMerchandiseCategory(int merchandiseCategoryTblPk,DataSource dataSource) throws SQLException, Exception{
    
    DBConnection dBConnection=null;
    ResultSet rsMerchandiseCategory= null;
    ResultSet rsMerchandiseCategoryCount= null;
    ResultSet rsMerchandiseCount= null;
    ResultSet rsMerchandiseCategorySizeType=null;
    String sqlString=null;
    MerchandiseCategoryForm merchandiseCategoryForm=null;
    int numberofMerchandiseCategory=0;
    int numberofMerchandise=0;
    String merchandiseSizeTypeTblPkString="";
    String sizeTypeTblPkString="";
    String sizeTypeIdString="";
    String sizeTypeOperationString="";
    ArrayList arrayListSizeType=null;
    String[] arraySizeType=null; 
    String parentCategory=null;
    try{    
      logger.info("Entering viewMerchandiseCategory");
      logger.debug("String value of merchandise_category_tbl_pk is:"+merchandiseCategoryTblPk); 

      sqlString="select merchandise_category_tbl_pk,merchandise_category_tbl_fk,";
      sqlString=sqlString + "merchandise_category,merchandise_category_description,";
      sqlString=sqlString+"merchandise_category_image,content_type,content_size,m_or_c from ";
      sqlString=sqlString+"merchandise_category_tbl where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;
      
      dBConnection=new DBConnection(dataSource);
      rsMerchandiseCategory=dBConnection.executeQuery(sqlString);
      if (rsMerchandiseCategory.next()){
        merchandiseCategoryForm = new MerchandiseCategoryForm();
        merchandiseCategoryForm.setHdnMerchandiseCategoryTblPk(rsMerchandiseCategory.getInt("merchandise_category_tbl_pk"));
        merchandiseCategoryForm.setHdnMerchandiseCategoryTblFk(rsMerchandiseCategory.getInt("merchandise_category_tbl_fk")==0?-1:rsMerchandiseCategory.getInt("merchandise_category_tbl_fk"));
        merchandiseCategoryForm.setTxtMerchandiseCategory(rsMerchandiseCategory.getString("merchandise_category"));
        if(merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk() != -1){
          parentCategory=Operations.getMerchandiseCategory(merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk(),dataSource);  
        }else{
          parentCategory=BOConstants.ALL.toString();
        }
        merchandiseCategoryForm.setTxtParentCategory(parentCategory);
        merchandiseCategoryForm.setTxaMerchandiseCategoryDesc(rsMerchandiseCategory.getString("merchandise_category_description"));
        merchandiseCategoryForm.setHdnCategoryImgOid(rsMerchandiseCategory.getString("merchandise_category_image"));
        merchandiseCategoryForm.setHdnCategoryImgContentType(rsMerchandiseCategory.getString("content_type"));
        merchandiseCategoryForm.setHdnCategoryImgContentSize(rsMerchandiseCategory.getInt("content_size"));     
        merchandiseCategoryForm.setRadMOrC(rsMerchandiseCategory.getInt("m_or_c"));
      }else{
        throw new Exception("merchandise_tbl_pk not found");
      }

      sqlString="select count(*) as merchandise_category_count from merchandise_category_tbl ";
      sqlString=sqlString+" where merchandise_category_tbl_fk=" + merchandiseCategoryTblPk;
      rsMerchandiseCategoryCount=dBConnection.executeQuery(sqlString);
      if (rsMerchandiseCategoryCount.next()){
        numberofMerchandiseCategory=rsMerchandiseCategoryCount.getInt("merchandise_category_count");
      }
      sqlString="select count(*) as merchandise_count from merchandise_parents_tbl ";
      sqlString=sqlString+" where merchandise_category_tbl_pk=" + merchandiseCategoryTblPk;
      rsMerchandiseCount=dBConnection.executeQuery(sqlString);
      if (rsMerchandiseCount.next()){
        numberofMerchandise=rsMerchandiseCount.getInt("merchandise_count");
      }
      if ((numberofMerchandiseCategory>0) || (numberofMerchandise>0)){
        merchandiseCategoryForm.setRadioMorCDisable("1");
      }else{
        merchandiseCategoryForm.setRadioMorCDisable("0");
      }
      
      sqlString="select stt.size_type_tbl_pk, ";
      sqlString+=" stt.size_type_id, ";
      sqlString+=" mcstt.merchandise_category_size_type_tbl_pk ";
      sqlString+=" from size_type_tbl stt, ";
      sqlString+=" merchandise_category_size_type_tbl mcstt ";
      sqlString+=" where mcstt.size_type_tbl_pk=stt.size_type_tbl_pk ";
      sqlString+=" and mcstt.merchandise_category_tbl_pk= " + merchandiseCategoryTblPk;
      sqlString+=" order by stt.size_type_id ";

      rsMerchandiseCategorySizeType=dBConnection.executeQuery(sqlString);
      arrayListSizeType=new ArrayList();
      while(rsMerchandiseCategorySizeType.next()){
        merchandiseSizeTypeTblPkString=merchandiseSizeTypeTblPkString+rsMerchandiseCategorySizeType.getString("merchandise_category_size_type_tbl_pk")+",";
        sizeTypeTblPkString=sizeTypeTblPkString+rsMerchandiseCategorySizeType.getString("size_type_tbl_pk")+",";
        sizeTypeIdString=sizeTypeIdString+rsMerchandiseCategorySizeType.getString("size_type_id")+",";
        sizeTypeOperationString=sizeTypeOperationString+BOConstants.UPD.toString()+",";
        arraySizeType = new String[2];
        arraySizeType[0] =rsMerchandiseCategorySizeType.getString("size_type_tbl_pk");
        arraySizeType[1] =rsMerchandiseCategorySizeType.getString("size_type_Id");
        arrayListSizeType.add(arraySizeType);
      }
      merchandiseCategoryForm.setHdnMerchandiseCategorySizeTypeTblPk(merchandiseSizeTypeTblPkString.substring(0,(merchandiseSizeTypeTblPkString.length()==0?0:(merchandiseSizeTypeTblPkString.length()-1))));
      merchandiseCategoryForm.setHdnSizeTypeTblPk(sizeTypeTblPkString.substring(0,(sizeTypeTblPkString.length()==0?0:(sizeTypeTblPkString.length()-1))));
      merchandiseCategoryForm.setHdnSizeTypeId(sizeTypeIdString.substring(0,(sizeTypeIdString.length()==0?0:(sizeTypeIdString.length()-1))));
      merchandiseCategoryForm.setHdnSizeTypeOperation(sizeTypeOperationString.substring(0,(sizeTypeOperationString.length()==0?0:(sizeTypeOperationString.length()-1))));
      merchandiseCategoryForm.setArrSizeType(arrayListSizeType); 
    }catch(SQLException e){
     logger.error(e);
     throw e;
    }catch(Exception e){
     logger.error(e);
     e.printStackTrace();
     throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.freeResultSet(rsMerchandiseCategory);
        dBConnection.freeResultSet(rsMerchandiseCategoryCount);
        dBConnection.freeResultSet(rsMerchandiseCount);
        dBConnection.freeResultSet(rsMerchandiseCategorySizeType);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting viewMerchandiseCategory");
    }
    return merchandiseCategoryForm;
  }

  public static int editMerchandiseCategory(MerchandiseCategoryForm merchandiseCategoryForm,DataSource dataSource)throws SQLException,Exception{
    InputStream is=null;
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    FormFile categoryImgFile=null;
    String sqlString = null;
    String query=null;
    ResultSet rs=null;
    String contentType=null;
    byte[] content=null;
    int returnMerchandiseCategoryTblPk=-1;
    int contentSize;
    int oid;
    String sizeTypeTblPkString=null;
    String sizeTypeOperationString=null;
    String merchandiseCategorySizeTypeTblPkString=null;
    try{
      logger.info("Entering editMerchandiseCategory");
      sqlString = "{?=call sp_upd_merchandise_category_tbl(?,?,?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      query="select merchandise_category from merchandise_category_tbl where merchandise_category = '";
      query=query+merchandiseCategoryForm.getTxtMerchandiseCategory();
      if(merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk() == -1)
        query=query+"' and merchandise_category_tbl_fk is null ";      
      else
        query=query+"' and merchandise_category_tbl_fk= "+merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk();
      query=query+" and merchandise_category_tbl_pk != "+merchandiseCategoryForm.getHdnMerchandiseCategoryTblPk();
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      categoryImgFile=merchandiseCategoryForm.getCategoryImgFile();
      oid=Integer.parseInt(merchandiseCategoryForm.getHdnCategoryImgOid());
      logger.debug("**oid: "+oid);
      sizeTypeTblPkString="{";
      if(merchandiseCategoryForm.getHdnSizeTypeTblPk().trim().length()>0){
        sizeTypeTblPkString=sizeTypeTblPkString+merchandiseCategoryForm.getHdnSizeTypeTblPk();
      }
      sizeTypeTblPkString=sizeTypeTblPkString+"}";
      logger.debug("sizeTypeTblPkString : " + sizeTypeTblPkString);

      sizeTypeOperationString="{";
      if(merchandiseCategoryForm.getHdnSizeTypeOperation().trim().length()>0){
        sizeTypeOperationString=sizeTypeOperationString+merchandiseCategoryForm.getHdnSizeTypeOperation();
      }
      sizeTypeOperationString=sizeTypeOperationString+"}";
      logger.debug("sizeTypeOperationString : " + sizeTypeOperationString);

      merchandiseCategorySizeTypeTblPkString="{";
      if(merchandiseCategoryForm.getHdnMerchandiseCategorySizeTypeTblPk().trim().length()>0){
        merchandiseCategorySizeTypeTblPkString=merchandiseCategorySizeTypeTblPkString+merchandiseCategoryForm.getHdnMerchandiseCategorySizeTypeTblPk();
      }
      merchandiseCategorySizeTypeTblPkString=merchandiseCategorySizeTypeTblPkString+"}";
      logger.debug("merchandiseCategorySizeTypeTblPkString : " + merchandiseCategorySizeTypeTblPkString);
      
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseCategoryForm.getHdnMerchandiseCategoryTblPk());
      cs.setInt(3,merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk());      
      cs.setString(4,merchandiseCategoryForm.getTxtMerchandiseCategory().trim());
      cs.setString(5,merchandiseCategoryForm.getTxaMerchandiseCategoryDesc());
      if(categoryImgFile!=null && categoryImgFile.getFileSize()>0){
        contentType=categoryImgFile.getContentType();
        contentSize=categoryImgFile.getFileData().length;
        content=new byte[contentSize];
        is=categoryImgFile.getInputStream();
        is.read(content);
        
        LOOperations.putLargeObjectContent(oid,content,dataSource);
        cs.setString(6,contentType);
        cs.setInt(7,contentSize);
      }else{
        cs.setString(6,merchandiseCategoryForm.getHdnCategoryImgContentType());
        cs.setInt(7,merchandiseCategoryForm.getHdnCategoryImgContentSize());
      }
      cs.setInt(8,merchandiseCategoryForm.getRadMOrC());
      cs.setArray(9,new PgSQLArray(sizeTypeTblPkString,Types.INTEGER));
      cs.setArray(10,new PgSQLArray(sizeTypeOperationString,Types.VARCHAR));
      cs.setArray(11,new PgSQLArray(merchandiseCategorySizeTypeTblPkString,Types.INTEGER));

      dBConnection.executeCallableStatement(cs);
      returnMerchandiseCategoryTblPk=cs.getInt(1);
      logger.debug("updated merchandise_category_tbl_pk:"+returnMerchandiseCategoryTblPk);
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
      logger.info("Exiting editMerchandiseCategory");
    }
    return returnMerchandiseCategoryTblPk;
  }

  public static int deleteMerchandiseCategory(int merchandiseCategoryTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnMerchandiseCategoryTblPk=-1;
    
    try{
      logger.info("Entering deleteMerchandiseCategory");
      sqlString = "{? = call sp_del_merchandise_category_tbl(?)}";    
      logger.debug("returnMerchandiseCategoryTblPk value: "+merchandiseCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseCategoryTblPk=cs.getInt(1);
      logger.debug("Deleted merchandise_category_tbl_pk:"+returnMerchandiseCategoryTblPk);
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
      logger.info("Exiting deleteMerchandiseCategory");      
    }
    return returnMerchandiseCategoryTblPk;
  }

  public static void actDeactMerchandiseCategory(int merchandiseCategoryTblPk,MerchandiseListForm merchandiseListForm,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnMerchandiseCategoryTblPk;

    try{
      logger.info("Entering actDeactMerchandiseCategory");
      sqlString = "{? = call sp_act_deact_merchandise_category_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(merchandiseListForm.getHdnSearchCurrentStatus());

      logger.debug("CategoryTblPk value: "+merchandiseCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection .prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseCategoryTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseCategoryTblPk=cs.getInt(1);
      logger.debug("ActDeact merchandise_tbl_pk:"+returnMerchandiseCategoryTblPk);
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
      logger.info("Exiting actDeactMerchandiseCategory");
    }
  }

  public static void moveMerchandiseCategory(int srcCategoryTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnMerchandiseCategoryTblPk;
    
    try{
      logger.info("Entering moveMerchandiseCategory");
      sqlString = "{? = call sp_mov_merchandise_category_tbl(?,?)}";

      dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcCategoryTblPk); 
      cs.setInt(3,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseCategoryTblPk=cs.getInt(1);
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
      logger.info("Exiting moveMerchandiseCategory");
    }
  }
 
  public static String getMerchandiseCategory(int merchandiseCategoryTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String merchandiseCategory=null;
    try{
      logger.info("Entering getMerchandiseCategory");
      dBConnection = new DBConnection(dataSource);
      sqlString="select merchandise_category from merchandise_category_tbl where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       merchandiseCategory=rs.getString("merchandise_category"); 
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
      logger.info("Exiting getMerchandiseCategory");
    }
    return merchandiseCategory;
  }
  public static int getFlag(int merchandiseCategoryTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    int flag=0;
    try{
      if(merchandiseCategoryTblPk==-1){
        flag=1;
        return flag;
      }
      logger.info("Entering getFlag");
      dBConnection = new DBConnection(dataSource);
      sqlString="select m_or_c from merchandise_category_tbl where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       flag=rs.getInt("m_or_c"); 
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
      logger.info("Exiting getFlag");
    }
    return flag;
  }
  
  public static int getMerchandiseCategoryFk(int merchandiseCategoryTblPk,DataSource dataSource)throws Exception{
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    int merchandiseCategoryTblFk=-1;
    try{
      dbConnection=new DBConnection(dataSource);
      sqlString="select merchandise_category_tbl_fk from merchandise_category_tbl where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;
      rs=dbConnection.executeQuery(sqlString);
      if(rs.next()){
       merchandiseCategoryTblFk=rs.getInt("merchandise_category_tbl_fk"); 
      }
    }catch(SQLException e){
      throw e;
    }finally{
      if(dbConnection!=null){
        dbConnection.free(rs);
      }
    }
    return merchandiseCategoryTblFk;
  }

  public static void addMerchandise(int userProfileTblPk,MerchandiseForm merchandiseForm,DataSource dataSource)throws SQLException, IOException, Exception{
    InputStream is = null;
    String sqlString =null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    ResultSet rs = null;
    FormFile displayImgFile=null;
    String displayImgContentType=null;
    String colorNameString=null;
    String colorValueString=null;
    String sizeValueString=null;
    String priceValueString=null;
    String[] stringArray=null;
    String[] printableAreaTblPkArray=null; 
    String printableAreaTblPkString=null;


    byte[] displayImgContent = null;
    int displayImgContentSize;
    int returnMerchandiseTblPk;
    int displayImgOid;
    

    try{
      logger.info("Entering addMerchandise");      
      dBConnection = new DBConnection(dataSource);
      String query="SELECT merchandise_name from merchandise_tbl mt join merchandise_parents_tbl mp on (mt.merchandise_tbl_pk=mp.merchandise_tbl_pk) where merchandise_name='"+merchandiseForm.getTxtMerchandise()+"' and merchandise_category_tbl_pk="+merchandiseForm.getHdnMerchandiseCategoryTblPk();
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      displayImgFile=merchandiseForm.getDisplayImgFile();
      displayImgContentType=displayImgFile.getContentType();
      displayImgContentSize=displayImgFile.getFileData().length;
      
      logger.debug("display image file name: "+displayImgFile.getFileName());
      logger.debug("display image file size: "+displayImgFile.getFileSize());
      
      displayImgOid=LOOperations.getLargeObjectId(dataSource);

      displayImgContent=new byte[displayImgContentSize];
      is=displayImgFile.getInputStream();
      is.read(displayImgContent);
      
      LOOperations.putLargeObjectContent(displayImgOid,displayImgContent,dataSource);
      
      
      sqlString = "{?=call sp_ins_merchandise_tbl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseForm.getHdnMerchandiseCategoryTblPk());
      cs.setString(3,merchandiseForm.getTxtMerchandise().trim());
      cs.setString(4,merchandiseForm.getTxaMerchandiseDesc());
      cs.setString(5,merchandiseForm.getTxaMerchandiseComm());
      cs.setString(6,merchandiseForm.getTxaMaterialDetail());
      cs.setInt(7,merchandiseForm.getTxtMinimumQuantity());
      cs.setInt(8,displayImgOid);
      cs.setString(9,merchandiseForm.getTxaDeliveryNote());
      cs.setInt(10,userProfileTblPk);
      cs.setString(11,displayImgContentType);
      cs.setInt(12,displayImgContentSize);
      
      //Color Name
      stringArray=merchandiseForm.getHdnColorOneName();
      colorNameString="{";
      for(int index=0;index<stringArray.length;index++){
        colorNameString=colorNameString+stringArray[index]+"/"+merchandiseForm.getHdnColorTwoName()[index]+"/";
        if(index<(stringArray.length-1)){ 
          colorNameString=colorNameString+",";
        }
      }
      colorNameString=colorNameString+"}";
      
      //Color Value
      stringArray=merchandiseForm.getHdnColorOneValue();      
      colorValueString="{";
      for(int index=0;index<stringArray.length;index++){
        colorValueString=colorValueString+stringArray[index]+"/"+merchandiseForm.getHdnColorTwoValue()[index]+"/";
        if(index<(stringArray.length-1)){ 
          colorValueString=colorValueString+",";
        }
      }
      colorValueString=colorValueString+"}";

      //Size Value
      stringArray=merchandiseForm.getHdnSizeValue();      
      sizeValueString="{";
      for(int index=0;index<stringArray.length;index++){
        sizeValueString=sizeValueString+stringArray[index];
        if(index<(stringArray.length-1)){ 
          sizeValueString=sizeValueString+",";
        }
      }
      sizeValueString=sizeValueString+"}";

      //Price Value
      stringArray=merchandiseForm.getHdnPriceValue();      
      priceValueString="{";
      for(int index=0;index<stringArray.length;index++){
        priceValueString=priceValueString+stringArray[index];
        if(index<(stringArray.length-1)){ 
          priceValueString=priceValueString+",";
        }
      }
      priceValueString=priceValueString+"}";
      logger.debug("Color Name String " + colorNameString);
      logger.debug("Color Value String " + colorValueString);
      logger.debug("Size Value String " + sizeValueString);
      logger.debug("Price Value String " + priceValueString);
      
      //Printable Area
      printableAreaTblPkArray=merchandiseForm.getChkPrintableArea();
      printableAreaTblPkString="{";
      for(int index=0;index<printableAreaTblPkArray.length;index++){
        printableAreaTblPkString=printableAreaTblPkString+printableAreaTblPkArray[index];
        if(index<(printableAreaTblPkArray.length-1)){ 
          printableAreaTblPkString=printableAreaTblPkString+",";
        }
      }
      printableAreaTblPkString=printableAreaTblPkString+"}";
      logger.debug("printableAreaTblPkString: "+printableAreaTblPkString);

      cs.setArray(13,new PgSQLArray(colorNameString,Types.VARCHAR)); //"{color1/color2/,color1/color2/,color1/color2/}"
      cs.setArray(14,new PgSQLArray(colorValueString,Types.VARCHAR)); //"{#ffddcc/#ddffcc/,#ff00ff/#ee1144/,#668833/#112233/}"
      cs.setArray(15,new PgSQLArray(sizeValueString,Types.VARCHAR)); //"{3/4/,3/4/7/,4/7/}"
      cs.setArray(16,new PgSQLArray(priceValueString,Types.VARCHAR)); //"{10.2/100.30/,102.35/340.00/300/,20/34/}"
      cs.setInt(17,merchandiseForm.getCboMerchandiseDecoration());
      cs.setArray(18,new PgSQLArray(printableAreaTblPkString,Types.INTEGER));
      
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      if( returnMerchandiseTblPk > -1 ){
        merchandiseForm.setHdnMerchandiseTblPk(returnMerchandiseTblPk);  
      }
      logger.debug("Returned PK is:" + returnMerchandiseTblPk);
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
      logger.info("Exiting addMerchandise");      
    }
  }

  public static MerchandiseForm  viewMerchandise(int merchandiseTblPk,int merchandiseCategoryTblPk,DataSource dataSource) throws Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    ResultSet rs4Color= null;
    ResultSet rs4SizePrice= null;
    String sqlString=null;
    String sqlString4Color=null;
    String sqlString4SizePrice=null;
    MerchandiseForm merchandiseForm=null;
    int colorArraySize;
    int colorArrayIndex;
    
    String[] merchandiseColorTblPk=null;
    String[] merchandiseColorOperation=null;
    String[] colorSNo=null;
    String[] colorOneValue=null;
    String[] colorOneName=null;
    String[] colorTwoValue=null;
    String[] colorTwoName=null;
    String[] colorIsActive=null;
    String[] colorIsActiveDisplay=null;
    String[] merchandiseSizeTblPk=null;
    String[] merchandiseSizeOperation=null;
    String[] sizeValue=null;
    String[] sizeText=null;
    String[] priceValue=null;
    String[] sizeIsActive=null;
    String[] sizeIsActiveDisplay=null;
    String merchandiseSizeTblPkString=null;
    String sizeValueString=null;
    String sizeTextString=null;
    String pricValueString=null;
    String sizeIsActiveString=null;
    String sizeIsActiveDisplayString=null;
    String merchandiseSizeOperationString=null;
    String parentCategory=null;

    try{
      logger.info("Entering viewMerchandise");
      logger.debug("String value of merchandise_tbl_pk is:"+merchandiseTblPk); 
      sqlString="select * from merchandise_tbl  where merchandise_tbl_pk="+merchandiseTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        merchandiseForm = new MerchandiseForm();
        merchandiseForm.setHdnMerchandiseTblPk(rs.getInt("merchandise_tbl_pk"));
        merchandiseForm.setHdnMerchandiseCategoryTblPk(merchandiseCategoryTblPk);
        merchandiseForm.setTxtMerchandise(rs.getString("merchandise_name"));
        parentCategory=Operations.getMerchandiseCategory(merchandiseForm.getHdnMerchandiseCategoryTblPk(),dataSource);
        merchandiseForm.setTxtParentCategory(parentCategory);
        merchandiseForm.setTxaMerchandiseDesc(rs.getString("merchandise_description"));
        merchandiseForm.setTxaMerchandiseComm(rs.getString("merchandise_comment"));
        merchandiseForm.setTxaMaterialDetail(rs.getString("material_description"));
        merchandiseForm.setTxtMinimumQuantity(rs.getInt("threshold_quantity"));
        merchandiseForm.setHdnDisplayImgOid(rs.getInt("merchandise_image"));
        merchandiseForm.setTxaDeliveryNote(rs.getString("delivery_note"));
        merchandiseForm.setHdnDisplayImgContentType(rs.getString("content_type"));
        merchandiseForm.setHdnDisplayImgContentSize(rs.getInt("content_size"));
        merchandiseForm.setCboMerchandiseDecoration(rs.getInt("merchandise_decoration_tbl_pk"));

        sqlString4Color=" select merchandise_color_tbl_pk,";
        sqlString4Color=sqlString4Color+" color_one_name,";
        sqlString4Color=sqlString4Color+" color_one_value,";
        sqlString4Color=sqlString4Color+" color_two_name,";
        sqlString4Color=sqlString4Color+" color_two_value,";
        sqlString4Color=sqlString4Color+" is_active ";
        sqlString4Color=sqlString4Color+" from merchandise_color_tbl ";
        sqlString4Color=sqlString4Color+" where merchandise_tbl_pk="+merchandiseTblPk;

        sqlString4SizePrice=" select ms.merchandise_size_tbl_pk, ";
        sqlString4SizePrice=sqlString4SizePrice+" ms.size_tbl_pk, ";
        sqlString4SizePrice=sqlString4SizePrice+" st.size_id, ";
        sqlString4SizePrice=sqlString4SizePrice+" ms.merchandise_price_per_qty, ";
        sqlString4SizePrice=sqlString4SizePrice+" ms.is_active  ";
        sqlString4SizePrice=sqlString4SizePrice+" from merchandise_size_tbl ms, ";
        sqlString4SizePrice=sqlString4SizePrice+" size_tbl st  ";
        sqlString4SizePrice=sqlString4SizePrice+" where st.size_tbl_pk=ms.size_tbl_pk  ";
        sqlString4SizePrice=sqlString4SizePrice+" and ms.merchandise_color_tbl_pk=";

        rs4Color=dBConnection.executeQuery(sqlString4Color,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
        rs4Color.last();
        colorArraySize=rs4Color.getRow();
        rs4Color.beforeFirst();
       
        merchandiseColorTblPk=new String[colorArraySize];
        merchandiseColorOperation=new String[colorArraySize];
        colorSNo=new String[colorArraySize]; 
        colorOneValue=new String[colorArraySize];
        colorOneName=new String[colorArraySize];
        colorTwoValue=new String[colorArraySize];
        colorTwoName=new String[colorArraySize];
        colorIsActive=new String[colorArraySize];
        colorIsActiveDisplay=new String[colorArraySize];
        merchandiseSizeTblPk=new String[colorArraySize];
        merchandiseSizeOperation=new String[colorArraySize];
        sizeValue=new String[colorArraySize];
        sizeText=new String[colorArraySize];
        priceValue=new String[colorArraySize];
        sizeIsActive=new String[colorArraySize];
        sizeIsActiveDisplay=new String[colorArraySize];
        colorArrayIndex=0;
       
        while (rs4Color.next()){
          merchandiseColorTblPk[colorArrayIndex]=rs4Color.getString("merchandise_color_tbl_pk");
          merchandiseColorOperation[colorArrayIndex]=BOConstants.UPD.toString(); 
          colorSNo[colorArrayIndex]=Integer.toString(colorArrayIndex+1);
          colorOneValue[colorArrayIndex]=rs4Color.getString("color_one_value");
          colorOneName[colorArrayIndex]=rs4Color.getString("color_one_name");
          colorTwoValue[colorArrayIndex]=rs4Color.getString("color_two_value");
          colorTwoName[colorArrayIndex]=rs4Color.getString("color_two_name");
          colorIsActive[colorArrayIndex]=rs4Color.getString("is_active");
          colorIsActiveDisplay[colorArrayIndex]=(((rs4Color.getString("is_active")).equals(BOConstants.ACTIVE_VAL.toString()))?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());

          merchandiseSizeTblPkString="";
          merchandiseSizeOperationString="";
          sizeValueString="";
          sizeTextString="";
          pricValueString="";
          sizeIsActiveString="";
          sizeIsActiveDisplayString="";
          
 
          rs4SizePrice=dBConnection.executeQuery(sqlString4SizePrice+rs4Color.getString("merchandise_color_tbl_pk"));
          while(rs4SizePrice.next()){
            merchandiseSizeTblPkString=merchandiseSizeTblPkString+rs4SizePrice.getString("merchandise_size_tbl_pk")+"/";
            merchandiseSizeOperationString=merchandiseSizeOperationString+BOConstants.UPD.toString()+"/";
            sizeValueString=sizeValueString+rs4SizePrice.getString("size_tbl_pk")+"/";
            sizeTextString=sizeTextString+rs4SizePrice.getString("size_id")+"/";
            pricValueString=pricValueString+rs4SizePrice.getString("merchandise_price_per_qty")+"/";
            sizeIsActiveString=sizeIsActiveString+rs4SizePrice.getString("is_active")+"/";
            sizeIsActiveDisplayString=sizeIsActiveDisplayString+(((rs4SizePrice.getString("is_active")).equals(BOConstants.ACTIVE_VAL.toString()))?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());  
          }
          dBConnection.freeResultSet(rs4SizePrice);
          merchandiseSizeTblPk[colorArrayIndex]=merchandiseSizeTblPkString;
          merchandiseSizeOperation[colorArrayIndex]=merchandiseSizeOperationString;
          sizeValue[colorArrayIndex]=sizeValueString;
          sizeText[colorArrayIndex]=sizeTextString;
          priceValue[colorArrayIndex]=pricValueString;
          sizeIsActive[colorArrayIndex]=sizeIsActiveString;
          sizeIsActiveDisplay[colorArrayIndex]=sizeIsActiveDisplayString;
          colorArrayIndex++;
          
        }
        merchandiseForm.setHdnMerchandiseColorTblPk(merchandiseColorTblPk); 
        merchandiseForm.setHdnMerchandiseColorOperation(merchandiseColorOperation);
        merchandiseForm.setHdnColorSNo(colorSNo);
        merchandiseForm.setHdnColorOneValue(colorOneValue);
        merchandiseForm.setHdnColorOneName(colorOneName);
        merchandiseForm.setHdnColorTwoValue(colorTwoValue);
        merchandiseForm.setHdnColorTwoName(colorTwoName);
        merchandiseForm.setHdnColorIsActive(colorIsActive); 
        merchandiseForm.setHdnColorIsActiveDisplay(colorIsActiveDisplay);
        merchandiseForm.setHdnMerchandiseSizeTblPk(merchandiseSizeTblPk);   
        merchandiseForm.setHdnMerchandiseSizeOperation(merchandiseSizeOperation);
        merchandiseForm.setHdnSizeValue(sizeValue);
        merchandiseForm.setHdnSizeText(sizeText);
        merchandiseForm.setHdnPriceValue(priceValue);
        merchandiseForm.setHdnSizeIsActive(sizeIsActive); 
        merchandiseForm.setHdnSizeIsActiveDisplay(sizeIsActiveDisplay); 
      }else{
        throw new Exception("Merchanise not found");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.freeResultSet(rs);
        dBConnection.freeResultSet(rs4Color);
        dBConnection.freeResultSet(rs4SizePrice);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting viewMerchandise");
    }
    return merchandiseForm;
  }

  public static void editMerchandise(MerchandiseForm merchandiseForm,DataSource dataSource)throws SQLException,IOException, Exception{
    DBConnection dBConnection= null;
    ResultSet rs = null;
    CallableStatement cs = null;
    InputStream is = null;
    String sqlString = null;
    String query=null;
    FormFile displayImgFile=null;
    String displayImgContentType=null;
    String merchandiseColorTblPkString=null;
    String colorNameString=null;
    String colorValueString=null;
    String colorIsActiveString=null;
    String merchandiseColorOperationString=null;
    String merchandiseSizeTblPkString=null;
    String sizeValueString=null;
    String priceValueString=null;
    String sizeIsActiveString=null;
    String merchandiseSizeOperationString=null;
    
    String[] stringArray=null;
    
    String printableAreaTblPkString=null;
    String printableAreaTblPkFlagString=null;

    byte[] displayImgContent = null;
    int displayImgContentSize;
    int returnMerchandiseTblPk;
    int displayImgOid;
    
    try{
      logger.info("Entering editMerchandise ");
      
      dBConnection = new DBConnection(dataSource);
      query="SELECT mt.merchandise_name from merchandise_tbl mt join merchandise_parents_tbl";
      query=query+" mp on (mt.merchandise_tbl_pk=mp.merchandise_tbl_pk) where ";
      query=query+" mt.merchandise_name='"+merchandiseForm.getTxtMerchandise()+"'";
      query=query+" and mp.merchandise_category_tbl_pk="+merchandiseForm.getHdnMerchandiseCategoryTblPk();
      query=query+" and mt.merchandise_tbl_pk != "+merchandiseForm.getHdnMerchandiseTblPk();
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      displayImgFile=merchandiseForm.getDisplayImgFile();
      displayImgOid=merchandiseForm.getHdnDisplayImgOid();
    
      sqlString = "{?=call sp_upd_merchandise_tbl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      logger.debug("**displayImgoid: "+displayImgOid);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseForm.getHdnMerchandiseTblPk());
      cs.setInt(3,merchandiseForm.getHdnMerchandiseCategoryTblPk());      
      cs.setString(4,merchandiseForm.getTxtMerchandise().trim());
      cs.setString(5,merchandiseForm.getTxaMerchandiseDesc());
      cs.setString(6,merchandiseForm.getTxaMerchandiseComm());
      cs.setString(7,merchandiseForm.getTxaMaterialDetail());
      cs.setInt(8,merchandiseForm.getTxtMinimumQuantity());
      cs.setString(9,merchandiseForm.getTxaDeliveryNote());
      if(displayImgFile!=null && displayImgFile.getFileSize()>0){
        displayImgContentType=displayImgFile.getContentType();
        displayImgContentSize=displayImgFile.getFileData().length;
        displayImgContent=new byte[displayImgContentSize];
        is=displayImgFile.getInputStream();
        is.read(displayImgContent);
        
        LOOperations.putLargeObjectContent(displayImgOid,displayImgContent,dataSource);
        cs.setString(10,displayImgContentType);
        cs.setInt(11,displayImgContentSize);
      }else{
        cs.setString(10,merchandiseForm.getHdnDisplayImgContentType());
        cs.setInt(11,merchandiseForm.getHdnDisplayImgContentSize());
      }
      
      stringArray=merchandiseForm.getHdnColorOneName();
      merchandiseColorTblPkString="{";
      colorNameString="{";
      colorValueString="{";
      colorIsActiveString="{";
      merchandiseColorOperationString="{";
      merchandiseSizeTblPkString="{";
      sizeValueString="{";
      priceValueString="{";
      sizeIsActiveString="{";
      merchandiseSizeOperationString="{";
      
      for(int index=0;index<stringArray.length;index++){
        merchandiseColorTblPkString=merchandiseColorTblPkString+merchandiseForm.getHdnMerchandiseColorTblPk()[index];
        colorNameString=colorNameString+stringArray[index].trim()+"/"+merchandiseForm.getHdnColorTwoName()[index].trim()+"/";
        colorValueString=colorValueString+merchandiseForm.getHdnColorOneValue()[index].trim()+"/"+merchandiseForm.getHdnColorTwoValue()[index].trim()+"/";
        colorIsActiveString=colorIsActiveString+merchandiseForm.getHdnColorIsActive()[index] ;
        merchandiseColorOperationString=merchandiseColorOperationString+merchandiseForm.getHdnMerchandiseColorOperation()[index];
        merchandiseSizeTblPkString=merchandiseSizeTblPkString+merchandiseForm.getHdnMerchandiseSizeTblPk()[index];
        sizeValueString=sizeValueString+merchandiseForm.getHdnSizeValue()[index];
        priceValueString=priceValueString+merchandiseForm.getHdnPriceValue()[index];      
        sizeIsActiveString=sizeIsActiveString+merchandiseForm.getHdnSizeIsActive()[index];
        merchandiseSizeOperationString=merchandiseSizeOperationString+merchandiseForm.getHdnMerchandiseSizeOperation()[index];

        if(index<(stringArray.length-1)){ 
          merchandiseColorTblPkString=merchandiseColorTblPkString+",";
          colorNameString=colorNameString+",";
          colorValueString=colorValueString+",";
          colorIsActiveString=colorIsActiveString+",";
          merchandiseColorOperationString=merchandiseColorOperationString+",";
          merchandiseSizeTblPkString=merchandiseSizeTblPkString+",";
          sizeValueString=sizeValueString+",";
          priceValueString=priceValueString+",";
          sizeIsActiveString=sizeIsActiveString+",";
          merchandiseSizeOperationString=merchandiseSizeOperationString+",";
        }
      }
      merchandiseColorTblPkString=merchandiseColorTblPkString+"}";
      colorNameString=colorNameString+"}";
      colorValueString=colorValueString+"}";
      colorIsActiveString=colorIsActiveString+"}";
      merchandiseColorOperationString=merchandiseColorOperationString+"}";
      merchandiseSizeTblPkString=merchandiseSizeTblPkString+"}";
      sizeValueString=sizeValueString+"}";
      priceValueString=priceValueString+"}";
      sizeIsActiveString=sizeIsActiveString+"}";
      merchandiseSizeOperationString=merchandiseSizeOperationString+"}";

    
      logger.debug("Merchandise Color Tbl Pk String" + merchandiseColorTblPkString);
      logger.debug("Color Name String " + colorNameString);
      logger.debug("Color Value String " + colorValueString);
      logger.debug("Color IsActive String " + colorIsActiveString);
      logger.debug("Color Operation String " + merchandiseColorOperationString);
      logger.debug("Merchandise Size Tbl Pk String " + merchandiseSizeTblPkString);
      logger.debug("Size Value String " + sizeValueString);
      logger.debug("Price Value String " + priceValueString);
      logger.debug("Size IsActive String " + sizeIsActiveString);
      logger.debug("Size Operation String " + merchandiseSizeOperationString);
      
      //For Printable Area
      logger.debug("B4 conversion printableAreaTblPkString :"+merchandiseForm.getHdnPrintableAreaTblPkString());
      logger.debug("B4 conversion printableAreaTblPkFlagString :"+merchandiseForm.getHdnPrintableAreaTblPkFlagString());
      
      printableAreaTblPkString="{";
      printableAreaTblPkString=printableAreaTblPkString+merchandiseForm.getHdnPrintableAreaTblPkString().replace('/',',');;
      printableAreaTblPkString=printableAreaTblPkString.substring(0,printableAreaTblPkString.length()-1);
      printableAreaTblPkString=printableAreaTblPkString+"}";
      
      printableAreaTblPkFlagString="{";
      printableAreaTblPkFlagString=printableAreaTblPkFlagString+merchandiseForm.getHdnPrintableAreaTblPkFlagString().replace('/',',');
      printableAreaTblPkFlagString=printableAreaTblPkFlagString.substring(0,printableAreaTblPkFlagString.length()-1);
      printableAreaTblPkFlagString=printableAreaTblPkFlagString+"}";
      
      logger.debug("After conversion printableAreaTblPkString: "+printableAreaTblPkString);
      logger.debug("After conversion printableAreaTblPkFlagString: "+printableAreaTblPkFlagString);

      cs.setArray(12,new PgSQLArray(merchandiseColorTblPkString,Types.INTEGER)); //Merchandise Color Tbl Pk;
      cs.setArray(13,new PgSQLArray(colorNameString,Types.VARCHAR)); //"{color1/color2/,color1/color2/,color1/color2/}"
      cs.setArray(14,new PgSQLArray(colorValueString,Types.VARCHAR)); //"{#ffddcc/#ddffcc/,#ff00ff/#ee1144/,#668833/#112233/}"
      cs.setArray(15,new PgSQLArray(colorIsActiveString,Types.VARCHAR)); //Color Active/Deactive flag
      cs.setArray(16,new PgSQLArray(merchandiseColorOperationString,Types.VARCHAR)); //Color Flag
      cs.setArray(17,new PgSQLArray(merchandiseSizeTblPkString,Types.VARCHAR)); //Merchandise Size Tbl Pk;
      cs.setArray(18,new PgSQLArray(sizeValueString,Types.VARCHAR)); //"{3/4/,3/4/7/,4/7/}"
      cs.setArray(19,new PgSQLArray(priceValueString,Types.VARCHAR)); //"{10.2/100.30/,102.35/340.00/300/,20/34/}"
      cs.setArray(20,new PgSQLArray(sizeIsActiveString,Types.VARCHAR)); //Size Active/Deactive flag
      cs.setArray(21,new PgSQLArray(merchandiseSizeOperationString,Types.VARCHAR)); //Size flag
      cs.setInt(22,merchandiseForm.getCboMerchandiseDecoration());
      cs.setArray(23,new PgSQLArray(printableAreaTblPkString,Types.INTEGER));
      cs.setArray(24,new PgSQLArray(printableAreaTblPkFlagString,Types.VARCHAR));
      
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      logger.debug("updated merchandise_tbl_pk:"+returnMerchandiseTblPk);
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
      logger.info("Exiting editMerchandise ");
    }
  }

  public static void deleteMerchandise(int merchandiseTblPk,int merchandiseCategoryTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnMerchandiseTblPk;

    try{
      logger.debug("Entering deleteMerchandise");
      sqlString = "{? = call sp_del_merchandise_tbl(?,?)}";
      logger.debug("returnMerchandiseTblPk value: "+merchandiseTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseTblPk);
      cs.setInt(3,merchandiseCategoryTblPk);
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      logger.debug("Deleted merchandise_tbl_pk:"+returnMerchandiseTblPk);
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
      logger.debug("Exiting deleteMerchandise");      
    }
  }

  public static void actDeactMerchandise(int merchandiseTblPk,int merchandiseCategoryTblPk,MerchandiseListForm merchandiseListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnMerchandiseTblPk;
    
    try{
      logger.info("Entering actDeactMerchandise");
      sqlString = "{? = call sp_act_deact_merchandise_parents_tbl(?,?,?)}";
      isActiveFlag=Integer.parseInt( merchandiseListForm.getHdnSearchCurrentStatus());
      logger.debug("MerchandiseTblPk value: "+merchandiseTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseTblPk); 
      cs.setInt(3,merchandiseCategoryTblPk);
      cs.setInt(4,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      logger.debug("ActDeact merchandise_tbl_pk:"+returnMerchandiseTblPk);
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
      logger.info("Exiting actDeactMerchandise");
    }
  }

  public static void moveMerchandise(int srcMerchandiseTblPk,int srcCategoryTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String query=null;
    String sqlString = null;
    ResultSet rs=null;
    int returnMerchandiseTblPk;
    
    try{
      logger.info("Entering moveMerchandise");
      
      dBConnection = new DBConnection(dataSource);
      //Check For Uniqueness Of Merchandise Name.
      query="SELECT merchandise_name from merchandise_tbl mt join ";
      query+="merchandise_parents_tbl mp on (mt.merchandise_tbl_pk=mp.merchandise_tbl_pk) ";
      query+="where merchandise_category_tbl_pk="+trgCategoryTblPk+" and ";
      query+="merchandise_name=(select merchandise_name from merchandise_tbl ";
      query+="where merchandise_tbl_pk="+srcMerchandiseTblPk+")";

      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      
      sqlString = "{? = call sp_mov_merchandise(?,?,?)}";

      //dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcMerchandiseTblPk); 
      cs.setInt(3,srcCategoryTblPk);
      cs.setInt(4,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      logger.debug("move returnMerchandiseTblPk:"+returnMerchandiseTblPk);
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
      logger.info("Exiting moveMerchandise");
    }
  }

  public static void copyMerchandise(int srcMerchandiseTblPk,int srcCategoryTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    String query = null;
    ResultSet rs=null;
    int returnMerchandiseTblPk;
    
    try{
      logger.info("Entering copyMerchandise");

      dBConnection = new DBConnection(dataSource);
      //Check For Uniqueness Of Merchandise Name.
      query="SELECT merchandise_name from merchandise_tbl mt join ";
      query+="merchandise_parents_tbl mp on (mt.merchandise_tbl_pk=mp.merchandise_tbl_pk) ";
      query+="where merchandise_category_tbl_pk="+trgCategoryTblPk+" and ";
      query+="merchandise_name=(select merchandise_name from merchandise_tbl ";
      query+="where merchandise_tbl_pk="+srcMerchandiseTblPk+")";

      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
      sqlString = "{? = call sp_cop_merchandise(?,?,?)}";


      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcMerchandiseTblPk); 
      cs.setInt(3,srcCategoryTblPk);
      cs.setInt(4,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseTblPk=cs.getInt(1);
      logger.debug("move returnMerchandiseTblPk:"+returnMerchandiseTblPk);
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
      logger.info("Exiting copyMerchandise");
    }
  }

/*  public static String getMerchandiseCategoryPk(String merchandiseTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String merchandiseCategoryTblPk=null;
    try{
      logger.error("Entering getMerchandiseCategoryPk");
      dBConnection=new DBConnection(dataSource);
      sqlString="select merchandise_category_tbl_pk from clipart_tbl where clipart_tbl_pk="+clipartTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       clipartCategoryTblPk=rs.getString("clipart_category_tbl_Pk"); 
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
  }*/

  public static String getMerchandiseName(int merchandiseTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String merchandiseName=null;
    try{
      logger.info("Entering getMerchandiseName");
      dBConnection = new DBConnection(dataSource);
      sqlString="select merchandise_name from merchandise_tbl where merchandise_tbl_pk="+merchandiseTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       merchandiseName=rs.getString("merchandise_name"); 
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
      logger.info("Exiting getMerchandiseName");
    }
    return merchandiseName;
  }

  
// For Price Promotion

  public static ArrayList getPricePromotions(PricePromotionListForm pricePromotionListForm,DataSource dataSource,int numberOfRecords) throws SQLException{
    ArrayList pricePromotions=new ArrayList();
    PricePromotionListBean pricePromotion=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query;
    
    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{  
      logger.info("Entering getPricePromotions");
      if ((pricePromotionListForm.getHdnSearchPageNo()!=null) && (pricePromotionListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(pricePromotionListForm.getHdnSearchPageNo());
      }    
  
      query=new String("select * from price_promotion_tbl where 1=1 ");

      if(pricePromotionListForm.getTxtSearchStartDate() != null && (pricePromotionListForm.getTxtSearchEndDate() == null || pricePromotionListForm.getTxtSearchEndDate().trim().length()==0) && (pricePromotionListForm.getTxtSearchStartDate().trim()).length() != 0 ){
        query = query + "and start_date >= '"+pricePromotionListForm.getTxtSearchStartDate()+"'";
      }
      if(pricePromotionListForm.getTxtSearchEndDate() != null && (pricePromotionListForm.getTxtSearchStartDate() == null || pricePromotionListForm.getTxtSearchStartDate().trim().length()==0) && (pricePromotionListForm.getTxtSearchEndDate().trim()).length() != 0 ){
        query = query + "and end_date <= '"+pricePromotionListForm.getTxtSearchEndDate()+"'";
      }
      if(
          pricePromotionListForm.getTxtSearchStartDate()!=null
          &&
          pricePromotionListForm.getTxtSearchEndDate()!=null
          &&
          pricePromotionListForm.getTxtSearchStartDate().trim().length()!=0
          &&
          pricePromotionListForm.getTxtSearchEndDate().trim().length()!=0
        ){
          query = query + "and start_date >= '"+pricePromotionListForm.getTxtSearchStartDate()+"'" +"and end_date <= '"+pricePromotionListForm.getTxtSearchEndDate()+"'";
        }
      if(pricePromotionListForm.getRadSearchExclusiveByPass() != null && (pricePromotionListForm.getRadSearchExclusiveByPass().trim()).length() != 0 && !pricePromotionListForm.getRadSearchExclusiveByPass().equals( BOConstants.BOTH_VAL.toString()) ){
          query = query + " and exclusive_flag="+pricePromotionListForm.getRadSearchExclusiveByPass();          
      }
      if(pricePromotionListForm.getRadSearchStatus() != null && (pricePromotionListForm.getRadSearchStatus().trim()).length() != 0 && !pricePromotionListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+pricePromotionListForm.getRadSearchStatus();
      }
      query=query + " and merchandise_size_tbl_pk=" + pricePromotionListForm.getHdnMerchandiseSizeTblPk();
      query=query + " order by price_promotion_tbl_pk";
      logger.info("Query is:"+query);

      dBConnection=new DBConnection(dataSource);
      rs= dBConnection.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
      
      if (rs!=null){
        pageCount=General.getPageCount(rs,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rs.relative(startIndex);
        }
        while(rs.next()){
          pricePromotion=new PricePromotionListBean();
          pricePromotion.setPricePromotionTblPk(rs.getString("price_promotion_tbl_pk"));
          
          SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");

          pricePromotion.setStartDate(sdf.format(rs.getDate("start_date")));
          pricePromotion.setEndDate(sdf.format(rs.getDate("end_date")));
          pricePromotion.setThresholdQty(rs.getString("threshold_quantity"));
          pricePromotion.setDiscount(rs.getString("discount"));
          pricePromotion.setExclusive(rs.getString("exclusive_flag").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.YES.toString():BOConstants.NO.toString());
          pricePromotion.setByPass(rs.getString("bypass_flag").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.YES.toString():BOConstants.NO.toString());
          pricePromotion.setIsActive(rs.getString("is_active"));
          pricePromotion.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          pricePromotions.add(pricePromotion);
          startIndex++;
          if (startIndex>endIndex){
            break;
          }
        }
      }
    }catch(Exception e){
     logger.error("Exception Caught:"+e.getMessage());
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      pricePromotionListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      pricePromotionListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getPricePromotions");
    } 
   return pricePromotions;
   
  }

  public static void addPricePromotion(PricePromotionForm pricePromotionForm,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    String sqlString;
    int returnPricePromotionTblPk;
    try{
      logger.info("Entering addPricePromotion method");
      dBConnection =  new DBConnection(dataSource);
      sqlString = "{?=call sp_ins_price_promotion_tbl(?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,pricePromotionForm.getHdnMerchandiseSizeTblPk());
      cs.setString(3,pricePromotionForm.getTxtStartDate());
      cs.setString(4,pricePromotionForm.getTxtEndDate());
      cs.setInt(5,pricePromotionForm.getTxtThresholdQty());
      cs.setFloat(6,pricePromotionForm.getTxtDiscount());
      logger.debug("flag value is:"+pricePromotionForm.getRadExclusiveByPass());
      if(pricePromotionForm.getRadExclusiveByPass()==1){
        cs.setInt(7,1);  
        cs.setInt(8,0);  
      }else{
        cs.setInt(7,0);  
        cs.setInt(8,1);  
      }
     
      dBConnection.executeCallableStatement(cs);
      returnPricePromotionTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnPricePromotionTblPk);
    }catch(PSQLException e){
      logger.error(e.getSQLState());
      throw e;
    }
    catch(SQLException e){
     throw e;
    }catch(Exception e){
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Entering addPricePromotion method");
    }

  }

  public static PricePromotionForm  viewPricePromotion(String pricePromotionTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    PricePromotionForm pricePromotionForm=null;
    try{
      logger.info("Entering viewPricePromotion");
      sqlString="select * from price_promotion_tbl where price_promotion_tbl_pk="+pricePromotionTblPk;
      dBConnection = new  DBConnection(dataSource);
      logger.debug("String value of price_promotion_tbl_pk is:"+pricePromotionTblPk); 
      rs=dBConnection.executeQuery(sqlString );
      if (rs.next()){
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
        pricePromotionForm = new PricePromotionForm();
        pricePromotionForm.setHdnPricePromotionTblPk(rs.getInt("price_promotion_tbl_pk"));
        pricePromotionForm.setTxtStartDate(sdf.format(rs.getDate("start_date")));
        pricePromotionForm.setTxtEndDate(sdf.format(rs.getDate("end_date")));
        pricePromotionForm.setTxtThresholdQty(rs.getInt("threshold_quantity"));
        pricePromotionForm.setTxtDiscount(rs.getFloat("discount"));
        pricePromotionForm.setRadExclusiveByPass(rs.getInt("exclusive_flag"));
      }else{
        throw new Exception("price_promotion_tbl_pk not found");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null; 
      logger.info("Exiting viewPricePromotion");
    }
    return pricePromotionForm;
  }

  public static void editPricePromotion (PricePromotionForm pricePromotionForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString =null;
    int returnPricePromotionTblPk;
    try{
      logger.info("Entering editPricePromotion");
      sqlString = "{?=call sp_upd_price_promotion_tbl(?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,pricePromotionForm.getHdnPricePromotionTblPk());
      cs.setInt(3,pricePromotionForm.getHdnMerchandiseSizeTblPk());
      cs.setString(4,pricePromotionForm.getTxtStartDate());
      cs.setString(5,pricePromotionForm.getTxtEndDate());
      cs.setInt(6,pricePromotionForm.getTxtThresholdQty());
      cs.setFloat(7,pricePromotionForm.getTxtDiscount());
      if(pricePromotionForm.getRadExclusiveByPass()==1){
        cs.setInt(8,1);
        cs.setInt(9,0);
      }else{
        cs.setInt(8,0);
        cs.setInt(9,1);
      }
      dBConnection.executeCallableStatement(cs);
      returnPricePromotionTblPk=cs.getInt(1);
      logger.debug("updated price_promotion_tbl_pk:"+returnPricePromotionTblPk);

    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }
    finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editPricePromotion");
    }
  }

  public static void deletePricePromotion(int pricePromotionTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnPricePromotionTblPk;
    try{
      logger.info("Entering deletePricePromotion");
      logger.debug("returnPricePromotionTblPk value: "+pricePromotionTblPk );
      sqlString = "{? = call sp_del_price_promotion_tbl(?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
     
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,pricePromotionTblPk); 

      dBConnection.executeCallableStatement(cs);
      returnPricePromotionTblPk=cs.getInt(1);
      logger.debug("Deleted price_promotion_tbl_pk:"+returnPricePromotionTblPk);
    }catch(SQLException e){
      logger.error("Error in method deletePricePromotion():"+e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      logger.info("Exiting deletePricePromotion");
    }
  }

  public static void actDeactPricePromotion(int pricePromotionTblPk,PricePromotionListForm pricePromotionListForm,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;

    int returnPricePromotionTblPk;
    try{
      logger.info("Entering actDeactPricePromotion");
      sqlString = "{? = call sp_act_deact_price_promotion_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(pricePromotionListForm.getHdnSearchCurrentStatus());

       logger.debug("PricePromotionTblPk value: "+pricePromotionTblPk );
       dBConnection = new DBConnection(dataSource);
       cs = dBConnection.prepareCall(sqlString);
       cs.registerOutParameter(1,Types.INTEGER);
       cs.setInt(2,pricePromotionTblPk); 
       cs.setInt(3,isActiveFlag); 
       dBConnection.executeCallableStatement(cs);
       returnPricePromotionTblPk=cs.getInt(1);
       logger.debug("ActDeact price_promotion_tbl_pk:"+returnPricePromotionTblPk);
    }catch(SQLException e){
       logger.error("SqlException caught while ActDeact:"+e);
       throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting actDeactPricePromotion");
    }
    
  }

  public static ArrayList getSize(DataSource dataSource, int MerchandiseCategoryTblPk) throws SQLException,Exception{
    ArrayList sizeArrayList=null;
    DBConnection dBConnection = null;
    ResultSet resultSet=null;
    String sqlString=null;
    SizeBean4List sizeBean4List=null;
    try{
    
      sqlString="select st.size_tbl_pk ,";
      sqlString+=" stt.size_type_id ,";
      sqlString+=" st.size_id ,";
      sqlString+=" st.size_description ";
      sqlString+=" from size_tbl st,";
      sqlString+=" size_type_tbl stt"; 
      sqlString+=" where st.size_type_tbl_pk=stt.size_type_tbl_pk ";
      sqlString+=" and stt.size_type_tbl_pk in";
      sqlString+=" ( select size_type_tbl_pk from ";
      sqlString+=" merchandise_category_size_type_tbl ";
      sqlString+=" where merchandise_category_tbl_pk=" + MerchandiseCategoryTblPk;
      sqlString+=" )";

      dBConnection = new DBConnection(dataSource);
      resultSet=dBConnection.executeQuery(sqlString);
      sizeArrayList  = new ArrayList(); 
      while(resultSet.next()){
        sizeBean4List= new SizeBean4List();
        sizeBean4List.setSizeValue(resultSet.getString("size_tbl_pk"));
        sizeBean4List.setSizeTypeSizeText(resultSet.getString("size_type_id") + " - " + resultSet.getString("size_id"));
        sizeBean4List.setSizeText(resultSet.getString("size_id"));
        sizeBean4List.setSizeDescription(resultSet.getString("size_description"));
        sizeArrayList.add(sizeBean4List);
      }
    }catch(SQLException e ){
      logger.error(e);
      throw e;
    }catch(Exception e ){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      } 
    }
    return sizeArrayList;
  }

  public static ArrayList getMerchandiseSizeColor(int merchandiseTblPk, DataSource dataSource)throws SQLException, Exception{
    String sqlString4MerchandColor=null;
    String sqlString4MerchandSize=null;
    String merchandiseColorTblPk=null;
    DBConnection  dBConnection=null;
    ResultSet resultSet4MerchandColor=null;
    ResultSet resultSet4MerchandSize=null;
    ArrayList merchandiseSizeColor=null;
    ArrayList merchandiseSize=null;
    MerchandiseColorBean merchandiseColorBean=null;
    MerchandiseSizeBean merchandiseSizeBean=null;
    try{
      logger.info("Entering getMerchandiseSizeColor()");
      sqlString4MerchandColor=" select * from merchandise_color_tbl"; 
      sqlString4MerchandColor=sqlString4MerchandColor+" where merchandise_tbl_pk=" + merchandiseTblPk ;

      dBConnection= new DBConnection(dataSource);
      resultSet4MerchandColor=dBConnection.executeQuery(sqlString4MerchandColor);
      merchandiseSizeColor = new ArrayList();
      while(resultSet4MerchandColor.next()){
        merchandiseColorTblPk=resultSet4MerchandColor.getString("merchandise_color_tbl_pk");
        merchandiseColorBean=new MerchandiseColorBean();
        merchandiseColorBean.setMerchandiseColorTblPk(merchandiseColorTblPk);
        merchandiseColorBean.setColor1Name(resultSet4MerchandColor.getString("color_one_name"));
        merchandiseColorBean.setColor1Value(resultSet4MerchandColor.getString("color_one_value"));
        merchandiseColorBean.setColor2Name(resultSet4MerchandColor.getString("color_two_name"));
        merchandiseColorBean.setColor2Value(resultSet4MerchandColor.getString("color_two_value"));

        sqlString4MerchandSize=" select mst.merchandise_size_tbl_pk, ";
        sqlString4MerchandSize=sqlString4MerchandSize+" mst.size_tbl_pk, ";
        sqlString4MerchandSize=sqlString4MerchandSize+" st.size_id, ";
        sqlString4MerchandSize=sqlString4MerchandSize+" st.size_description, ";
        sqlString4MerchandSize=sqlString4MerchandSize+" mst.merchandise_price_per_qty ";
        sqlString4MerchandSize=sqlString4MerchandSize+" from  merchandise_size_tbl mst, "; 
        sqlString4MerchandSize=sqlString4MerchandSize+" size_tbl st ";
        sqlString4MerchandSize=sqlString4MerchandSize+" where st.size_tbl_pk=mst.size_tbl_pk ";
        sqlString4MerchandSize=sqlString4MerchandSize+" and mst.merchandise_color_tbl_pk="+merchandiseColorTblPk;

        resultSet4MerchandSize=dBConnection.executeQuery(sqlString4MerchandSize);
        merchandiseSize = new ArrayList();
        while(resultSet4MerchandSize.next()){
          merchandiseSizeBean=new  MerchandiseSizeBean();
          merchandiseSizeBean.setMerchandiseSizeTblPk(resultSet4MerchandSize.getString("merchandise_size_tbl_pk"));
          merchandiseSizeBean.setSizeTblPk(resultSet4MerchandSize.getString("size_tbl_pk"));
          merchandiseSizeBean.setSizeId(resultSet4MerchandSize.getString("size_id"));
          merchandiseSizeBean.setSizeDescription(resultSet4MerchandSize.getString("size_description"));
          merchandiseSizeBean.setPrice(resultSet4MerchandSize.getString("merchandise_price_per_qty"));
          merchandiseSize.add(merchandiseSizeBean);
        } 
        resultSet4MerchandSize.close();
        merchandiseColorBean.setMerchandiseSizes(merchandiseSize);
        merchandiseSizeColor.add(merchandiseColorBean);
      }
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(resultSet4MerchandColor);
        dBConnection.freeResultSet(resultSet4MerchandSize);
        dBConnection.free();
      }
      logger.info("Exiting getMerchandiseSizeColor()");
    }        
    return merchandiseSizeColor;
  }
  
  public static ArrayList getDecorations(DataSource dataSource) throws SQLException,Exception{
    ArrayList decorationArrayList=null;
    DBConnection dBConnection = null;
    ResultSet resultSet=null;
    String sqlString=null;
    MerchandiseDecorationBean  merchandiseDecorationBean=null;
    try{
      logger.info("Entering getDecorations()");
      sqlString="\n select ";
      sqlString+="\n mdt.merchandise_decoration_tbl_pk, ";
      sqlString+="\n mdt.decoration_name, ";
      sqlString+="\n mdt.decoration_description ";
      sqlString+="\n from merchandise_decoration_tbl mdt  ";
      sqlString+="\n where mdt.is_active=1";
      logger.debug("sqlString : "+sqlString);
      dBConnection = new DBConnection(dataSource);
      resultSet=dBConnection.executeQuery(sqlString);
      decorationArrayList  = new ArrayList(); 
      while(resultSet.next()){
        merchandiseDecorationBean= new MerchandiseDecorationBean();
        merchandiseDecorationBean.setMerchandiseDecorationTblPk(resultSet.getString("merchandise_decoration_tbl_pk"));
        merchandiseDecorationBean.setDecorationName(resultSet.getString("decoration_name"));        
        merchandiseDecorationBean.setDecorationDescription(resultSet.getString("decoration_description"));        
        decorationArrayList.add(merchandiseDecorationBean);
      }
    }catch(SQLException e ){
      logger.error(e);
      throw e;
    }catch(Exception e ){
      logger.error(e);
      throw e;
    }finally{
      logger.info("Exiting getDecorations()");
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      } 
    }
    return decorationArrayList;
  }
  
  public static ArrayList getMerchandiseDecorations(MerchandiseDecorationListForm merchandiseDecorationListForm,DataSource dataSource,int numberOfRecords) throws SQLException, Exception{
    ArrayList decorations=new ArrayList();
    MerchandiseDecorationListBean decoration=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query;

    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{  
      logger.info("Entering getMerchandiseDecorations() method");

      if ((merchandiseDecorationListForm.getHdnSearchPageNo()!=null) && (merchandiseDecorationListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(merchandiseDecorationListForm.getHdnSearchPageNo());
      }    

      query=new String("select * from merchandise_decoration_tbl where 1=1 ");

      if(merchandiseDecorationListForm.getTxtSearchDecorationName() != null && (merchandiseDecorationListForm.getTxtSearchDecorationName().trim()).length() != 0 ){
        query = query + "and decoration_name like '%"+merchandiseDecorationListForm.getTxtSearchDecorationName()+"%'";
      }

      if(merchandiseDecorationListForm.getTxaSearchDecorationDescription() != null && (merchandiseDecorationListForm.getTxaSearchDecorationDescription().trim()).length() != 0 ){
        query = query + " and decoration_description like'%"+merchandiseDecorationListForm.getTxaSearchDecorationDescription()+"%'";
      }
      
      if(merchandiseDecorationListForm.getRadSearchStatus() != null && (merchandiseDecorationListForm.getRadSearchStatus().trim()).length() != 0 && !merchandiseDecorationListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+merchandiseDecorationListForm.getRadSearchStatus();
      }
    
      query=query + " order by decoration_name ";
      
      logger.debug("query" + query);

      dBConnection = new DBConnection(dataSource);
      rs=dBConnection.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if (rs!=null){
        pageCount=General.getPageCount(rs,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rs.relative(startIndex);
        }
        while(rs.next()){
          decoration=new MerchandiseDecorationListBean();
          decoration.setMerchandiseDecorationTblPk(rs.getString("merchandise_decoration_tbl_pk"));
          decoration.setDecorationName(rs.getString("decoration_name"));
          decoration.setDecorationDescription(rs.getString("decoration_description"));
          decoration.setIsActive(rs.getString("is_active"));
          decoration.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          logger.debug(decoration.getIsActiveDisplay());
          decorations.add(decoration);
          startIndex++;
          if (startIndex>endIndex){
            break;
          }
        }
      }
    }catch(SQLException e){
     logger.error("Exception Caught:"+e.getMessage());
     throw e;
    }catch(Exception e){
     logger.error("Exception Caught:"+e.getMessage());
     throw e;
    }finally{ 
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      merchandiseDecorationListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      merchandiseDecorationListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getMerchandiseDecorations() method");
    }
   return decorations;
 }
 public static void addDecoration(MerchandiseDecorationForm merchandiseDecorationForm,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    String sqlString;
    int returnMerchandiseDecorationTblPk;
    try{
      logger.info("Entering addDecoration ");
      dBConnection =  new  DBConnection(dataSource);
      sqlString = "{?=call sp_ins_merchandise_decoration_tbl(?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,merchandiseDecorationForm.getTxtDecorationName().trim());
      cs.setString(3,merchandiseDecorationForm.getTxaDecorationDescription() );
      
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseDecorationTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnMerchandiseDecorationTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null) {
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting addDecoration ");
    }
  }
  
  public static MerchandiseDecorationForm viewDecoration(int merchandiseDecorationTblPk,DataSource dataSource) throws SQLException,  Exception{
     DBConnection dBConnection =null;
     ResultSet rs= null;
     String sqlString=null;
     MerchandiseDecorationForm merchandiseDecorationForm=null;
     try{
      logger.info("Entering viewDecoration");
       
      sqlString="select merchandise_decoration_tbl_pk,decoration_name,decoration_description from merchandise_decoration_tbl where merchandise_decoration_tbl_pk=";
      dBConnection= new  DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString + merchandiseDecorationTblPk);
      if (rs.next()){
        merchandiseDecorationForm = new MerchandiseDecorationForm();
        merchandiseDecorationForm.setHdnMerchandiseDecorationTblPk(rs.getInt("merchandise_decoration_tbl_pk"));
        merchandiseDecorationForm.setTxtDecorationName(rs.getString("decoration_name"));
        merchandiseDecorationForm.setTxaDecorationDescription(rs.getString("decoration_description"));
      }else{
        throw new Exception("merchandise_decoration_tbl_pk not found");
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
      logger.info("Exiting viewDecoration");
    }
    return merchandiseDecorationForm;
  }
  
  public static void editDecoration(MerchandiseDecorationForm merchandiseDecorationForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    int returnMerchandiseDecorationTblPk;
    String sqlString =null;
    try{
      logger.info("Exiting editDecoration"); 
      sqlString = "{?=call sp_upd_merchandise_decoration_tbl(?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseDecorationForm.getHdnMerchandiseDecorationTblPk());
      cs.setString(3,merchandiseDecorationForm.getTxtDecorationName().trim());
      cs.setString(4,merchandiseDecorationForm.getTxaDecorationDescription());
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseDecorationTblPk=cs.getInt(1);
      logger.debug("updated merchandise_decoration_tbl_pk:"+returnMerchandiseDecorationTblPk);
    }catch(SQLException e)  {
      logger.error("SqlException:"+ e);
      throw e;
    }catch(Exception e)  {
      logger.error("Exception:"+ e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting editDecoration"); 
    }
  }
  
  public static void deleteDecoration(int merchandiseDecorationTblPk,DataSource dataSource) throws SQLException,   Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnMerchandiseDecorationTblPk;
    try{
      logger.info("Entering deleteDecoration");
      sqlString = "{? = call sp_del_merchandise_decoration_tbl(?)}";
      
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseDecorationTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseDecorationTblPk=cs.getInt(1);
      logger.debug("Deleted merchandise_decoration_tbl_pk:"+returnMerchandiseDecorationTblPk);
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
      logger.info("Exiting deleteDecoration");
    }
  }
  
  public static void actDeactDecoration(int merchandiseDecorationTblPk,MerchandiseDecorationListForm merchandiseDecorationListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnMerchandiseDecorationTblPk;
    
    try{
      logger.info("Entering actDeactDecoration");
      sqlString = "{? = call sp_act_deact_merchandise_decoration_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(merchandiseDecorationListForm.getHdnSearchCurrentStatus());
      
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,merchandiseDecorationTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnMerchandiseDecorationTblPk=cs.getInt(1);
      
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
      logger.info("Exiting actDeactDecoration");
    }
  }

  
  public static String getDecorationName(int merchandiseDecorationTblPk,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String decorationName=null;
    try{
      logger.info("Entering getDecorationName");
      dBConnection= new DBConnection(dataSource);
      sqlString="select decoration_name from merchandise_decoration_tbl where merchandise_decoration_tbl_pk=";
      rs=dBConnection.executeQuery(sqlString+merchandiseDecorationTblPk);
      if(rs.next()){
       decorationName=rs.getString("decoration_name"); 
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
      logger.info("Exiting getDecorationName");
    }
    return decorationName;
  }
  public static ArrayList getPrintableAreas(PrintableAreaListForm printableAreaListForm,DataSource dataSource,int numberOfRecords) throws SQLException{
    ArrayList printableAreas=new ArrayList();
    PrintableAreaListBean printableArea=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query;
   
    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{  
      logger.info("Entering getPrintableAreas() method");
      if ((printableAreaListForm.getHdnSearchPageNo()!=null) && (printableAreaListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(printableAreaListForm.getHdnSearchPageNo());
      }    
  
      query=new String("select * from printable_area_tbl where 1=1 ");

      if(printableAreaListForm.getTxtSearchPrintableAreaName() != null && (printableAreaListForm.getTxtSearchPrintableAreaName().trim()).length() != 0 ){
        query = query + "and printable_area_name like '%"+printableAreaListForm.getTxtSearchPrintableAreaName()+"%'";
      }

      if(printableAreaListForm.getRadSearchStatus() != null && (printableAreaListForm.getRadSearchStatus().trim()).length() != 0 && !printableAreaListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+printableAreaListForm.getRadSearchStatus();
      }
    
      query=query + " order by printable_area_priority";

      dBConnection=new DBConnection(dataSource);
      rs= dBConnection.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
      
      if (rs!=null){
        pageCount=General.getPageCount(rs,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rs.relative(startIndex);
        }
        while(rs.next()){
          printableArea=new PrintableAreaListBean();
          printableArea.setPrintableAreaTblPk(rs.getString("printable_area_tbl_pk"));
          printableArea.setPrintableAreaName(rs.getString("printable_area_name"));
          printableArea.setIsActive(rs.getString("is_active"));
          printableArea.setPrintableAreaPriority(rs.getString("printable_area_priority"));
          printableArea.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          printableAreas.add(printableArea);
          startIndex++;
          if (startIndex>endIndex){
            break;
          }
        }
      }
    }catch(Exception e){
     logger.error("Exception Caught:"+e.getMessage());
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      printableAreaListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      printableAreaListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getPrintableAreas() method");
    } 
   return printableAreas;
   
  } 
  public static ArrayList getActivePrintableAreas(DataSource dataSource) throws SQLException,Exception{
    ArrayList printableAreaArrayList=null;
    DBConnection dBConnection = null;
    ResultSet resultSet=null;
    String sqlString=null;
    PrintableAreaListBean  printableAreaListBean=null;
    try{
      logger.info("Entering getActivePrintableAreas()");
      sqlString="\n select ";
      sqlString+="\n printable_area_tbl_pk, ";
      sqlString+="\n printable_area_name ";
      sqlString+="\n from printable_area_tbl ";
      sqlString+="\n where is_active=1 order by printable_area_Priority";
     
      logger.debug("sqlString : "+sqlString);
      dBConnection = new DBConnection(dataSource);
      resultSet=dBConnection.executeQuery(sqlString);
      printableAreaArrayList=new ArrayList(); 
      while(resultSet.next()){
        printableAreaListBean=new PrintableAreaListBean();
        printableAreaListBean.setPrintableAreaTblPk(resultSet.getString("printable_area_tbl_pk"));
        printableAreaListBean.setPrintableAreaName(resultSet.getString("printable_area_name"));
        printableAreaArrayList.add(printableAreaListBean);
        
      }
    }catch(SQLException e ){
      logger.error(e);
      throw e;
    }catch(Exception e ){
      logger.error(e);
      throw e;
    }finally{
      logger.info("Exiting getActivePrintableAreas()");
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      } 
    }
    return printableAreaArrayList;
  }
  public static PrintableAreaPriorityForm getPrintableArea4Priority(PrintableAreaPriorityForm printableAreaPriorityForm, DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection = null;
    ResultSet resultSet=null;
    String sqlString=null;
    int printableAreaTblSize;
    try{
      logger.info("Entering in to getPrintableArea4Priority");
      dBConnection = new DBConnection(dataSource);
        
      sqlString="\n select ";
      sqlString+="\n printable_area_tbl_pk, ";
      sqlString+="\n printable_area_name ";
      sqlString+="\n from printable_area_tbl ";
      sqlString+="\n order by printable_area_priority";
    
      resultSet=dBConnection.executeQuery(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if (resultSet!=null) {
        resultSet.last();
        printableAreaTblSize=resultSet.getRow();
        resultSet.beforeFirst(); 
        
        String[] printableAreaTblPk;
        String[] printableAreaName;
        printableAreaTblPk = new String[printableAreaTblSize];
        printableAreaName = new String[printableAreaTblSize];
        int index=0;        
        while(resultSet.next()){
          printableAreaTblPk[index]=resultSet.getString("printable_area_tbl_pk");
          printableAreaName[index]=resultSet.getString("printable_area_name");
          index++;
        }
        
      printableAreaPriorityForm.setPrintableAreaTblPk(printableAreaTblPk);
      printableAreaPriorityForm.setPrintableAreaName(printableAreaName);
      }
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      logger.info("Exiting from getPrintableArea4Priority");
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      } 
    }
    return printableAreaPriorityForm;
  }
  public static ArrayList getSelectedActivePrintableAreas(int merchandiseTblPk, DataSource dataSource) throws SQLException,Exception{
    ArrayList printableAreaArrayList=null;
    DBConnection dBConnection = null;
    ResultSet resultSet=null;
    String sqlString=null;
    PrintableAreaListBean  printableAreaListBean=null;
    try{
      

        sqlString="\n select";
        sqlString+="\n patbl.printable_area_tbl_pk,";
        sqlString+="\n patbl.printable_area_name,";
        sqlString+="\n (";
        sqlString+="\n select count(*)";
        sqlString+="\n from merchandise_printable_area_tbl mpatbl";
        sqlString+="\n where mpatbl.printable_area_tbl_pk=patbl.printable_area_tbl_pk";
        sqlString+="\n and mpatbl.merchandise_color_tbl_pk in"; 
        sqlString+="\n (select mctbl.merchandise_color_tbl_pk"; 
        sqlString+="\n from merchandise_color_tbl mctbl"; 
        sqlString+="\n where mctbl.merchandise_tbl_pk=" + merchandiseTblPk +")";
        sqlString+="\n ) as areacount";
        sqlString+="\n from printable_area_tbl patbl";
        sqlString+="\n where patbl.is_active=1 order by patbl.printable_area_priority";
 

      logger.debug("sqlString : "+sqlString);
      dBConnection = new DBConnection(dataSource);
      resultSet=dBConnection.executeQuery(sqlString);
      printableAreaArrayList=new ArrayList(); 
      while(resultSet.next()){
        printableAreaListBean=new PrintableAreaListBean();
        printableAreaListBean.setPrintableAreaTblPk(resultSet.getString("printable_area_tbl_pk"));
        printableAreaListBean.setPrintableAreaName(resultSet.getString("printable_area_name"));
        if(resultSet.getInt("areacount")>0){
          printableAreaListBean.setPreviouslyChecked("Yes");
        }else{
          printableAreaListBean.setPreviouslyChecked("No");
        }
        
        printableAreaArrayList.add(printableAreaListBean);
        
      }
    }catch(SQLException e ){
      logger.error(e);
      throw e;
    }catch(Exception e ){
      logger.error(e);
      throw e;
    }finally{
      logger.info("Exiting getActivePrintableAreas()");
      if(dBConnection!=null){
        dBConnection.free(resultSet);
      } 
    }
    return printableAreaArrayList;
  }
  public static void prioritisePrintableArea(PrintableAreaPriorityForm printableAreaPriorityForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString =null;
    String lstPrintableAreaPriority=null;
    String printableAreaTblPkString=null;
    String[] lstPriorityArray=null;
    boolean returnPrintableAreaTblPk;
    try{
      logger.info("Entering updateOrderingPrintableArea() method");

      //printable area Priority
      lstPriorityArray=printableAreaPriorityForm.getLstPrintableArea();
      lstPrintableAreaPriority="{";
      printableAreaTblPkString="{";
      for(int index=0;index<lstPriorityArray.length;index++){
        printableAreaTblPkString+=lstPriorityArray[index];
        lstPrintableAreaPriority+=index+1;
        if(index<(lstPriorityArray.length-1)){ 
          printableAreaTblPkString+=",";
          lstPrintableAreaPriority+=",";
        }
      }
      printableAreaTblPkString+="}";
      lstPrintableAreaPriority+="}";
      
      logger.debug("lstPrintableAreaPriority"+lstPrintableAreaPriority);
      logger.debug("printableAreaTblPkString: "+printableAreaTblPkString);
      
      
      sqlString = "{?=call sp_upd_printable_area_priority(?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.BOOLEAN);
      cs.setArray(2,new PgSQLArray(printableAreaTblPkString,Types.INTEGER));
      cs.setArray(3,new PgSQLArray(lstPrintableAreaPriority,Types.INTEGER));
      
      dBConnection.executeCallableStatement(cs);
      returnPrintableAreaTblPk=cs.getBoolean(1);
      logger.debug("updated printable_area_tbl_pk:"+returnPrintableAreaTblPk);
    }catch(Exception e){
      logger.error("SqlException: "+ e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting updateOrderingPrintableArea");
    }
  }
  public static void addPrintableArea(PrintableAreaForm printableAreaForm,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    String sqlString;
    int returnPrintableAreaTblPk;
    try{
      logger.info("Entering addPrintableArea() method");
      dBConnection =  new DBConnection(dataSource);
      sqlString = "{?=call sp_ins_printable_area_tbl(?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,printableAreaForm.getTxtPrintableAreaName().trim());
      dBConnection.executeCallableStatement(cs);
      returnPrintableAreaTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnPrintableAreaTblPk);
    }catch(PSQLException e){
      logger.error(e.getSQLState());
      logger.error(PSQLState.DATA_ERROR);
      throw e;
    }
    catch(SQLException e){
     throw e;
    }catch(Exception e){
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Entering addPrintableArea method");
    }
  }
  public static PrintableAreaForm  viewPrintableArea(int printableAreaTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    PrintableAreaForm printableAreaForm=null;
    try{
      logger.info("Entering viewPrintableArea() method");
      sqlString="select printable_area_tbl_pk,printable_area_name from printable_area_tbl where printable_area_tbl_pk=";
      dBConnection = new  DBConnection(dataSource);
      logger.debug("String value of printable_area_tbl_pk is:"+printableAreaTblPk); 
      rs=dBConnection.executeQuery(sqlString + printableAreaTblPk);
      if (rs.next()){
        printableAreaForm = new PrintableAreaForm();
        printableAreaForm.setHdnPrintableAreaTblPk(rs.getInt("printable_area_tbl_pk"));
        printableAreaForm.setTxtPrintableAreaName(rs.getString("printable_area_name"));
      }else{
        throw new Exception("printable_area_tbl_pk not found");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null; 
      logger.info("Exiting viewPrintableArea() method");
    }
    return printableAreaForm;
  }
  public static void editPrintableArea (PrintableAreaForm printableAreaForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString =null;
    int returnPrintableAreaTblPk;
    try{
      logger.info("Entering editPrintableArea() method");
      sqlString = "{?=call sp_upd_printable_area_tbl(?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,printableAreaForm.getHdnPrintableAreaTblPk());
      cs.setString(3,printableAreaForm.getTxtPrintableAreaName().trim());
      
      dBConnection.executeCallableStatement(cs);
      returnPrintableAreaTblPk=cs.getInt(1);
      logger.debug("updated printable_area_tbl_pk:"+returnPrintableAreaTblPk);

    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }
    finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editPrintableArea");
    }
  }  
  
  public static void deletePrintableArea(int printableAreaTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnPrintableAreaTblPk;
    try{
      logger.info("Entering deletePrintableArea() method");
      logger.debug("returnPrintableAreaTblPk value: "+printableAreaTblPk );
      sqlString = "{? = call sp_del_printable_area_tbl(?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
     
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,printableAreaTblPk); 

      dBConnection.executeCallableStatement(cs);
      returnPrintableAreaTblPk=cs.getInt(1);
      logger.debug("Deleted printable_area_tbl_pk:"+returnPrintableAreaTblPk);
    }catch(SQLException e){
      logger.error("Error in method deletePrintableArea():"+e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      logger.info("Exiting deletePrintableArea() method");
    }
  }

  /**
   * Purpose : To activate or deactivate an printableArea selected in printableArea_list.jsp.
   * @param printableAreaTblPk - A String object.
   * @param printableAreaListForm - An PrintableAreaListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactPrintableArea(int printableAreaTblPk,PrintableAreaListForm printableAreaListForm,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;

    int returnPrintableAreaTblPk;
    try{
      logger.info("Entering actDeactPrintableArea() method");
      sqlString = "{? = call sp_act_deact_printable_area_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(printableAreaListForm.getHdnSearchCurrentStatus());

       logger.debug("PrintableAreaTblPk value: "+printableAreaTblPk );
       dBConnection = new DBConnection(dataSource);
       cs = dBConnection.prepareCall(sqlString);
       cs.registerOutParameter(1,Types.INTEGER);
       cs.setInt(2,printableAreaTblPk); 
       cs.setInt(3,isActiveFlag); 
       dBConnection.executeCallableStatement(cs);
       returnPrintableAreaTblPk=cs.getInt(1);
       logger.debug("ActDeact printable_area_tbl_pk:"+returnPrintableAreaTblPk);
    }catch(SQLException e){
       logger.error("SqlException caught while ActDeact:"+e);
       throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting actDeactPrintableArea() method");
    }
  }
  
  public static String getPrintableAreaName(int printableAreaTblPk,DataSource dataSource)throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String printableAreaName=null;
    try{
      logger.info("Entering getPrintableAreaName() method");
      dBConnection=new  DBConnection(dataSource);
      sqlString="select printable_area_name from printable_area_tbl where printable_area_tbl_pk=";
      rs=dBConnection.executeQuery(sqlString+printableAreaTblPk);
      if(rs.next()){
       printableAreaName=rs.getString("printable_area_name"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getPrintableAreaName() method");
    }
    return printableAreaName;
  }
  
  public static ArrayList getSelectedPrintableArea(MerchandiseForm merchandiseForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    ArrayList printableAreaDetails=null;
    PrintableAreaDetailBean printableAreaDetail=null;
    String[] printableAreaTblPkArray=null;
    String printableAreaTblPkString=null;
    
    
    try{
      logger.info("Entering getPrintableAreaDetails() method");
      
      printableAreaTblPkArray=merchandiseForm.getChkPrintableArea();
      printableAreaTblPkString="";
      for(int index=0;index<printableAreaTblPkArray.length;index++){
        printableAreaTblPkString+=printableAreaTblPkArray[index];
        if(index<(printableAreaTblPkArray.length-1)){ 
          printableAreaTblPkString+=",";
        }
      }
      logger.debug("printableAreaTblPkString: "+printableAreaTblPkString);
      
      dBConnection=new  DBConnection(dataSource);

//      sqlString="select mpat.merchandise_printable_area_tbl_pk,mpat.printable_area_tbl_pk, ";
//      sqlString+="mpat.design_image_content,mpat.content_type, ";
//      sqlString+="mpat.content_size,mpat.area_width,mpat.area_height,pat.printable_area_name from ";
//      sqlString+="merchandise_printable_area_tbl mpat join  printable_area_tbl pat ";
//      sqlString+="on(pat.printable_area_tbl_pk=mpat.printable_area_tbl_pk) ";
//      sqlString+="where mpat.printable_area_tbl_pk in ("+printableAreaTblPkString+") ";
//      sqlString+="and mpat.merchandise_tbl_pk= "+merchandiseForm.getHdnMerchandiseTblPk();
//      sqlString+=" order by pat.printable_area_name";

       sqlString=" select";
       sqlString+="\n mpat.merchandise_printable_area_tbl_pk,";
       sqlString+="\n mpat.printable_area_tbl_pk,";
       sqlString+="\n mpat.design_image_content,";
       sqlString+="\n mpat.content_type,";
       sqlString+="\n mpat.content_size,";
       sqlString+="\n mpat.area_width,";
       sqlString+="\n mpat.area_height,";
       sqlString+="\n mct.merchandise_color_tbl_pk,";
       sqlString+="\n mct.color_one_name,";
       sqlString+="\n mct.color_one_value,";
       sqlString+="\n mct.color_two_name,";
       sqlString+="\n mct.color_two_value,";
       sqlString+="\n pat.printable_area_name from";
       sqlString+="\n merchandise_printable_area_tbl mpat join  printable_area_tbl pat";
       sqlString+="\n on(pat.printable_area_tbl_pk=mpat.printable_area_tbl_pk) join merchandise_color_tbl mct";
       sqlString+="\n on(mct.merchandise_color_tbl_pk=mpat.merchandise_color_tbl_pk)";
       sqlString+="\n where mpat.printable_area_tbl_pk in ("+printableAreaTblPkString+")";
       sqlString+="\n and mct.merchandise_tbl_pk="+merchandiseForm.getHdnMerchandiseTblPk();
       sqlString+="\n order by mct.merchandise_color_tbl_pk,mpat.printable_area_tbl_pk ";
      
      logger.debug("sqlString: "+sqlString);
      rs=dBConnection.executeQuery(sqlString);

      printableAreaDetails=new ArrayList();
      int index=0;
      while(rs.next()){
        
        printableAreaDetail=new PrintableAreaDetailBean();
        printableAreaDetail.setMerchandiseColorTblPk(rs.getString("merchandise_color_tbl_pk"));
        printableAreaDetail.setColor1Name(rs.getString("color_one_name"));
        printableAreaDetail.setColor1Value(rs.getString("color_one_value"));
        printableAreaDetail.setColor2Name(rs.getString("color_two_name"));
        printableAreaDetail.setColor2Value(rs.getString("color_two_value"));        
        printableAreaDetail.setMerchandisePrintableAreaTblPk(rs.getString("merchandise_printable_area_tbl_pk"));
        printableAreaDetail.setPrintableAreaTblPk(rs.getString("printable_area_tbl_pk"));
        if(rs.getString("design_image_content")!=null && rs.getString("design_image_content").length()!=0){
          printableAreaDetail.setImageOid(rs.getString("design_image_content"));
          printableAreaDetail.setContentType(rs.getString("content_type"));
          printableAreaDetail.setContentSize(rs.getString("content_size"));
          printableAreaDetail.setPrintableAreaWidth(rs.getString("area_width"));
          printableAreaDetail.setPrintableAreaHeight(rs.getString("area_height"));
          merchandiseForm.setTxtPrintableAreaHeight(index,rs.getString("area_height"));
          merchandiseForm.setTxtPrintableAreaWidth(index,rs.getString("area_width"));
          merchandiseForm.setHdnImageOid(index,rs.getString("design_image_content"));
          merchandiseForm.setHdnContentType(index,rs.getString("content_type"));
          merchandiseForm.setHdnContentSize(index,rs.getString("content_size"));
          
        }
        logger.debug("imageOid: "+rs.getString("design_image_content")+" contentType: "+rs.getString("content_type")+" contentSize: "+rs.getString("content_size"));
        
        printableAreaDetail.setPrintableAreaName(rs.getString("printable_area_name"));
        logger.debug("merchandise_printable_area_tbl_pk: "+printableAreaDetail.getMerchandisePrintableAreaTblPk());
        printableAreaDetails.add(printableAreaDetail);
        index++;
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getPrintableAreaDetails() method");
    }
    return printableAreaDetails;
  }
  
  public static void addPrintableAreaDetals(MerchandiseForm merchandiseForm,DataSource dataSource)throws SQLException, IOException, Exception{
    String[] merchandisePrintableAreaTblPkArray=null;
    FormFile[] printableAreaImgFileArray=null;
    String[] printableAreaHeightArray=null;
    String[] printableAreaWidthArray=null;
    String sqlString =null;
    String contentType=null;
    int contentSize;
    int oid;
    byte[] content = null;
    InputStream is = null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    ResultSet rs = null;

    try{
      logger.info("Entering addPrintableAreaDetals() method");
      
      dBConnection = new DBConnection(dataSource);
      
      merchandisePrintableAreaTblPkArray=merchandiseForm.getHdnMerchandisePrintableAreaTblPk();
      printableAreaImgFileArray=merchandiseForm.getPrintableAreaImgFile();
      printableAreaHeightArray=merchandiseForm.getTxtPrintableAreaHeight();
      printableAreaWidthArray=merchandiseForm.getTxtPrintableAreaWidth();
      
      logger.info("File length:"+printableAreaImgFileArray.length);
      
      for(int fileCount=0; fileCount<printableAreaImgFileArray.length; fileCount++){ 
        
         
        logger.info("File Name" + fileCount + " : " + printableAreaImgFileArray[fileCount].getFileName());
        logger.info("File Height"  + fileCount + " : " + printableAreaHeightArray[fileCount]);
        logger.info("File Width"  + fileCount + " : " + printableAreaWidthArray[fileCount]);
        logger.debug("file "+fileCount+": "+merchandiseForm.getPrintableAreaImgFile(fileCount));
        contentType=printableAreaImgFileArray[fileCount].getContentType();
        
        contentSize=printableAreaImgFileArray[fileCount].getFileData().length;
        logger.info("contentType : "+contentType);
        oid=LOOperations.getLargeObjectId(dataSource);
  
        content=new byte[contentSize];
        is=printableAreaImgFileArray[fileCount].getInputStream();
        is.read(content);
        
        LOOperations.putLargeObjectContent(oid,content,dataSource);
        sqlString = "{?=call sp_upd_merchandise_printable_area_design_detail(?,?,?,?,?,?,?)}";
        cs = dBConnection.prepareCall(sqlString);
  
        cs.registerOutParameter(1,Types.INTEGER);
        cs.setInt(2,Integer.parseInt(merchandisePrintableAreaTblPkArray[fileCount]));
        cs.setInt(3,merchandiseForm.getHdnMerchandiseCategoryTblPk());
        cs.setInt(4,oid);
        cs.setString(5,contentType);
        cs.setInt(6,contentSize);
        cs.setInt(7,Integer.parseInt(printableAreaWidthArray[fileCount]));
        cs.setInt(8,Integer.parseInt(printableAreaHeightArray[fileCount]));
          
        dBConnection.executeCallableStatement(cs);
        is.close();
      }
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
      logger.info("Exiting addPrintableAreaDetals() method");
    }
  }
  
  public static void editPrintableAreaDetals(MerchandiseForm merchandiseForm,DataSource dataSource)throws SQLException, IOException, Exception{
    String[] merchandisePrintableAreaTblPkArray=null;
    
    FormFile[] printableAreaImgFileArray=null;
    String[] oidArray=null;
    String[] contentTypeArray=null;
    String[] contentSizeArray=null;
    String[] printableAreaHeightArray=null;
    String[] printableAreaWidthArray=null;
    String sqlString =null;
    String contentType=null;
    int contentSize;
    int oid;
    byte[] content = null;
    InputStream is = null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    ResultSet rs = null;

    try{
      logger.info("Entering editPrintableAreaDetals() method");
      
      dBConnection = new DBConnection(dataSource);
      merchandisePrintableAreaTblPkArray=merchandiseForm.getHdnMerchandisePrintableAreaTblPk();
      printableAreaImgFileArray=merchandiseForm.getPrintableAreaImgFile();
      oidArray=merchandiseForm.getHdnImageOid();
      contentTypeArray=merchandiseForm.getHdnContentType();
      contentSizeArray=merchandiseForm.getHdnContentSize();
      printableAreaHeightArray=merchandiseForm.getTxtPrintableAreaHeight();
      printableAreaWidthArray=merchandiseForm.getTxtPrintableAreaWidth();
      
      logger.debug("contentTypeArray: "+contentTypeArray.toString());
      logger.debug("contentSizeArray: "+contentSizeArray.toString());
      
      logger.info("File length:"+printableAreaImgFileArray.length);
      
      for(int fileCount=0; fileCount<merchandisePrintableAreaTblPkArray.length; fileCount++){ 
        
        sqlString = "{?=call sp_upd_merchandise_printable_area_design_detail(?,?,?,?,?,?,?)}";
        cs = dBConnection.prepareCall(sqlString);
  
        cs.registerOutParameter(1,Types.INTEGER);
        cs.setInt(2,Integer.parseInt(merchandisePrintableAreaTblPkArray[fileCount]));
        cs.setInt(3,merchandiseForm.getHdnMerchandiseCategoryTblPk());
        
        //Setting the oid
        if(oidArray[fileCount]!=null && oidArray[fileCount].trim().length()!=0){
          oid=Integer.parseInt(oidArray[fileCount]);
        }else{
          oid=LOOperations.getLargeObjectId(dataSource);
        }
        cs.setInt(4,oid);
        
        if(printableAreaImgFileArray[fileCount]!=null && printableAreaImgFileArray[fileCount].getFileSize()>0){
          contentType=printableAreaImgFileArray[fileCount].getContentType();
          contentSize=printableAreaImgFileArray[fileCount].getFileData().length;
          content=new byte[contentSize];
          is=printableAreaImgFileArray[fileCount].getInputStream();
          is.read(content);
          LOOperations.putLargeObjectContent(oid,content,dataSource);
          cs.setString(5,contentType);
          cs.setInt(6,contentSize);
          is.close();
          logger.debug("File Name:"+printableAreaImgFileArray[fileCount].getFileName());
          logger.debug("content type: "+contentType);
          logger.debug("content size: "+contentSize);
        }else{
          contentType=contentTypeArray[fileCount];
          contentSize=Integer.parseInt(contentSizeArray[fileCount]);
          cs.setString(5,contentType);
          cs.setInt(6,contentSize);
          logger.debug("File Name: Old");
          logger.debug("content type: "+contentTypeArray[fileCount]);
          logger.debug("content size: "+contentSizeArray[fileCount]);
        }
        cs.setInt(7,Integer.parseInt(printableAreaWidthArray[fileCount]));
        cs.setInt(8,Integer.parseInt(printableAreaHeightArray[fileCount]));
          
        dBConnection.executeCallableStatement(cs);
        
      }
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
      logger.info("Exiting editPrintableAreaDetals() method");
    }
  }

  
} 