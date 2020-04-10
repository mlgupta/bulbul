package bulbul.boff.size.beans;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.size.actionforms.SizeForm;
import bulbul.boff.size.actionforms.SizeListForm;
import bulbul.boff.size.actionforms.SizeTypeForm;
import bulbul.boff.size.actionforms.SizeTypeListForm;

import java.io.IOException;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public final class Operations  {

  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate font_list.jsp with fonts and categories from database depending upon the data from 
   *           the search field in font_list.jsp.
   * @return ArrayList 
   * @param sizeTypeListForm - A SizeTypeListForm object.
   * @param dataSource - A DataSource object.
   * @param numberOfRecords - Integer.
   */  
  public static ArrayList getSizeTypes(SizeTypeListForm sizeTypeListForm,DataSource dataSource,int numberOfRecords)throws SQLException,Exception{
    ArrayList sizeTypes=new ArrayList();
    SizeTypeListBean sizeType=null;
    DBConnection dBConnection=null;
    ResultSet rsSizeType=null;
    String queryForSizeType;

    int pageNumber=1;
    
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getSizeTypes");
      if ((sizeTypeListForm.getHdnSearchPageNo()!=null) && (sizeTypeListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(sizeTypeListForm.getHdnSearchPageNo());
      }  

      logger.debug("pageNumber: "+pageNumber);

      //Query For SizeType
      queryForSizeType="select * from size_type_tbl where 1=1 ";

      if( sizeTypeListForm.getTxtSearchSizeTypeId() != null && (sizeTypeListForm.getTxtSearchSizeTypeId().trim()).length() != 0 ){
        queryForSizeType = queryForSizeType + " and size_type_id like '%"+sizeTypeListForm.getTxtSearchSizeTypeId()+"%'";
      }
      if( sizeTypeListForm.getTxtSearchSizeTypeDesc() != null && (sizeTypeListForm.getTxtSearchSizeTypeDesc().trim()).length() != 0 ){
        queryForSizeType = queryForSizeType + " and size_type_description like '%"+sizeTypeListForm.getTxtSearchSizeTypeDesc()+"%'";
      }

      if(sizeTypeListForm.getRadSearchStatus() != null && (sizeTypeListForm.getRadSearchStatus().trim()).length() != 0 && !sizeTypeListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForSizeType = queryForSizeType + " and is_active="+sizeTypeListForm.getRadSearchStatus();
      }

      queryForSizeType=queryForSizeType+" order by size_type_id ";

      dBConnection= new  DBConnection(dataSource);  
      rsSizeType=dBConnection.executeQuery(queryForSizeType,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      logger.debug("queryForSizeType: "+queryForSizeType);

      if (rsSizeType!=null){
        logger.debug("rsSizeType is not null");
        pageCount=General.getPageCount(rsSizeType,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rsSizeType.relative(startIndex);
        }
        while(rsSizeType.next()){
          sizeType=new SizeTypeListBean();
          sizeType.setSizeTypeTblPk(rsSizeType.getString("size_type_tbl_pk"));
          sizeType.setSizeTypeId(rsSizeType.getString("size_type_id"));
          sizeType.setSizeTypeDescription(rsSizeType.getString("size_type_description"));
          sizeType.setIsActive(rsSizeType.getString("is_active"));
          sizeType.setIsActiveDisplay(rsSizeType.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          sizeTypes.add(sizeType);
          startIndex++;
          logger.debug("startIndex: "+startIndex);
          if (startIndex>endIndex){
            break;
          }
        }    
      }else{
        logger.debug("rsSizeType is null");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rsSizeType);
        dBConnection.free(); 
      }
      dBConnection=null;
      sizeTypeListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      sizeTypeListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getSizeTypes");
    }
    return sizeTypes;
  }

  public static void addSizeType(SizeTypeForm sizeTypeForm,DataSource dataSource)throws SQLException, IOException, Exception{
    String sqlString =null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    int returnSizeTypeTblPk;

    try{
      logger.info("Entering addSizeType");
      dBConnection = new DBConnection(dataSource);
      
      sqlString = "{?=call sp_ins_size_type_tbl(?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,sizeTypeForm.getTxtSizeTypeId().trim());
      cs.setString(3,sizeTypeForm.getTxaSizeTypeDesc());
      dBConnection.executeCallableStatement(cs);
      returnSizeTypeTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnSizeTypeTblPk);
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
      if(dBConnection!=null){
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting addSizeType");      
    }
  }

  public static SizeTypeForm  viewSizeType(int sizeTypeTblPk,DataSource dataSource) throws Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    SizeTypeForm sizeTypeForm=null;
    try{
      logger.info("Entering viewSizeType");
      logger.debug("String value of size_type_tbl_pk is:"+sizeTypeTblPk); 
      sqlString="select size_type_tbl_pk,size_type_id,size_type_description from size_type_tbl where size_type_tbl_pk="+sizeTypeTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        sizeTypeForm = new SizeTypeForm();
        sizeTypeForm.setHdnSizeTypeTblPk(rs.getInt("size_type_tbl_pk"));
        sizeTypeForm.setTxtSizeTypeId(rs.getString("size_type_id"));
        sizeTypeForm.setTxaSizeTypeDesc(rs.getString("size_type_description"));
      }
      else{
        throw new Exception("size_type_tbl_pk not found");
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
      logger.info("Exiting viewSizeType");
    }
    return sizeTypeForm;
  }

  public static void editSizeType(SizeTypeForm sizeTypeForm,DataSource dataSource)throws SQLException,IOException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnSizeTypeTblPk;

    try{
      logger.info("Entering edilSizeType ");
      sqlString = "{?=call sp_upd_size_type_tbl(?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeTypeForm.getHdnSizeTypeTblPk());
      cs.setString(3,sizeTypeForm.getTxtSizeTypeId().trim());
      cs.setString(4,sizeTypeForm.getTxaSizeTypeDesc());

      dBConnection.executeCallableStatement(cs);
      returnSizeTypeTblPk=cs.getInt(1);
      logger.debug("updated size_type_tbl_pk:"+returnSizeTypeTblPk);
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
      if(dBConnection!=null){
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting edilSizeType ");
    }
  }

  public static void deleteSizeType(int sizeTypeTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnSizeTypeTblPk;

    try{
      logger.debug("Entering deleteSizeType");
      sqlString = "{? = call sp_del_size_type_tbl(?)}";
      logger.debug("returnSizeTypeTblPk value: "+sizeTypeTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeTypeTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnSizeTypeTblPk=cs.getInt(1);
      logger.debug("Deleted size_type_tbl_pk:"+returnSizeTypeTblPk);
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
      logger.debug("Exiting deleteSizeType");      
    }
  }

  public static void actDeactSizeType(int sizeTypeTblPk,SizeTypeListForm sizeTypeListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnSizeTypeTblPk;
    
    try{
      logger.info("Entering actDeactSizeType");
      sqlString = "{? = call sp_act_deact_size_type_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(sizeTypeListForm.getHdnSearchCurrentStatus());
      logger.debug("SizeTypeTblPk value: "+sizeTypeTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeTypeTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnSizeTypeTblPk=cs.getInt(1);
      logger.debug("ActDeact size_type_tbl_pk:"+returnSizeTypeTblPk);
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
      logger.info("Exiting actDeactSizeType");
    }
  }

  public static String getSizeTypeId(int sizeTypeTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String sizeTypeId=null;
    try{
      logger.info("Entering getSizeTypeId");
      dBConnection = new DBConnection(dataSource);
      sqlString="select size_type_id from size_type_tbl where size_type_tbl_pk="+sizeTypeTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       sizeTypeId=rs.getString("size_type_id"); 
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
      logger.info("Exiting getSizeTypeId");
    }
    return sizeTypeId;
  }

  public static ArrayList getSizes(int sizeTypeTblPk,SizeListForm sizeListForm,DataSource dataSource,int numberOfRecords)throws SQLException,Exception{
    ArrayList sizes=new ArrayList();
    SizeListBean size=null;
    DBConnection dBConnection=null;
    ResultSet rsSize=null;
    String queryForSize;

    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getSizes");
      if ((sizeListForm.getHdnSearchPageNo()!=null) && (sizeListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(sizeListForm.getHdnSearchPageNo());
      }  

      logger.debug("***pageNumber: "+pageNumber);

       //Query For Size
      queryForSize="select * from size_tbl where size_type_tbl_pk="+sizeTypeTblPk;

      if( sizeListForm.getTxtSearchSizeId() != null && (sizeListForm.getTxtSearchSizeId().trim()).length() != 0 ){
        queryForSize = queryForSize + " and size_id like '%"+sizeListForm.getTxtSearchSizeId()+"%'";
      }

      if(sizeListForm.getRadSearchStatus() != null && (sizeListForm.getRadSearchStatus().trim()).length() != 0 && !sizeListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForSize = queryForSize + " and is_active="+sizeListForm.getRadSearchStatus();
      }

      queryForSize=queryForSize+" order by size_id ";

      dBConnection= new  DBConnection(dataSource);  

      rsSize=dBConnection.executeQuery(queryForSize,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      logger.debug("queryForSize: "+queryForSize);
      if (rsSize!=null){
        pageCount=General.getPageCount(rsSize,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rsSize.relative(startIndex);
        }
        while(rsSize.next()){
          size=new SizeListBean();
          size.setSizeTblPk(rsSize.getString("size_tbl_pk"));
          size.setSizeId(rsSize.getString("size_id"));
          size.setSizeDescription(rsSize.getString("size_description"));
          size.setIsActive(rsSize.getString("is_active"));
          size.setIsActiveDisplay(rsSize.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          sizes.add(size);
          startIndex++;
          //logger.debug("***startIndex: "+startIndex);
          if (startIndex>endIndex){
            break;
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
        dBConnection.freeResultSet(rsSize);
        dBConnection.free(); 
      }
      dBConnection=null;
      sizeListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      sizeListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      sizeListForm.setHdnSizeTypeTblPk(sizeTypeTblPk);
      logger.info("Exiting getSizeTypes");
    }
    return sizes;
  }

 
 
  public static void addSize(SizeForm sizeForm,DataSource dataSource)throws SQLException, IOException, Exception{
    String sqlString =null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    int returnSizeTblPk;

    try{
      logger.info("Entering addSize()");
      dBConnection = new DBConnection(dataSource);
      
      sqlString = "{?=call sp_ins_size_tbl(?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeForm.getHdnSizeTypeTblPk());
      cs.setString(3,sizeForm.getTxtSizeId().trim());
      cs.setString(4,sizeForm.getTxaSizeDesc());
      dBConnection.executeCallableStatement(cs);
      returnSizeTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnSizeTblPk);
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
      
      if(dBConnection!=null){
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting addSize");      
    }
  }

  public static SizeForm  viewSize(int sizeTblPk,DataSource dataSource) throws Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    SizeForm sizeForm=null;
    try{
      logger.info("Entering viewSize");
      logger.debug("String value of size_tbl_pk is:"+sizeTblPk); 
      sqlString="select size_tbl_pk,size_type_tbl_pk,size_id,size_description from size_tbl where size_tbl_pk="+sizeTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        sizeForm = new SizeForm();
        sizeForm.setHdnSizeTblPk(rs.getInt("size_tbl_pk"));
        sizeForm.setHdnSizeTypeTblPk(rs.getInt("size_type_tbl_pk"));
        sizeForm.setTxtSizeId(rs.getString("size_id"));
        sizeForm.setTxaSizeDesc(rs.getString("size_description"));
        sizeForm.setTxtSizeTypeId(Operations.getSizeTypeId(rs.getInt("size_type_tbl_pk"),dataSource));
      }
      else{
        throw new Exception("size_tbl_pk not found");
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
      logger.info("Exiting viewSize");
    }
    return sizeForm;
  }

  public static void editSize(SizeForm sizeForm,DataSource dataSource)throws SQLException,IOException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnSizeTblPk;

    try{
      logger.info("Entering edilSize() ");
      sqlString = "{?=call sp_upd_size_tbl(?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeForm.getHdnSizeTblPk());
      cs.setInt(3,sizeForm.getHdnSizeTypeTblPk());      
      cs.setString(4,sizeForm.getTxtSizeId().trim());
      cs.setString(5,sizeForm.getTxaSizeDesc());

      dBConnection.executeCallableStatement(cs);
      returnSizeTblPk=cs.getInt(1);
      logger.debug("updated size_tbl_pk:"+returnSizeTblPk);
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
      if(dBConnection!=null){
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting edilSize() ");
    }
  }

  public static void deleteSize(int sizeTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnSizeTblPk;

    try{
      logger.debug("Entering deleteSize");
      sqlString = "{? = call sp_del_size_tbl(?)}";
      logger.debug("returnSizeTblPk value: "+sizeTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnSizeTblPk=cs.getInt(1);
      logger.debug("Deleted size_tbl_pk:"+returnSizeTblPk);
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
      logger.debug("Exiting deleteSize");      
    }
  }

  public static void actDeactSize(int sizeTblPk,SizeListForm sizeListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnSizeTblPk;
    
    try{
      logger.info("Entering actDeactSize");
      sqlString = "{? = call sp_act_deact_size_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(sizeListForm.getHdnSearchCurrentStatus());
      logger.debug("SizeTblPk value: "+sizeTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,sizeTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnSizeTblPk=cs.getInt(1);
      logger.debug("ActDeact size_tbl_pk:"+returnSizeTblPk);
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
      logger.info("Exiting actDeactSize");
    }
  }

  public static String getSizeId(int sizeTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String sizeId=null;
    try{
      logger.info("Entering getSizeId");
      dBConnection = new DBConnection(dataSource);
      sqlString="select size_id from size_tbl where size_tbl_pk="+sizeTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       sizeId=rs.getString("size_id"); 
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
      logger.info("Exiting getSizeId");
    }
    return sizeId;
  }

}