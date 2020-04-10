package bulbul.boff.outlet.beans;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.outlet.actionforms.OutletForm;

import bulbul.boff.outlet.actionforms.OutletListForm;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;


/**
 *	Purpose: To Provide The Methods Used By The Actions.
 *  Info: This Class Contain The Methods Used By The Action Class To List,Add,Edit,Delete,Activte/Deactivate 
 *        And View The Outlets.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class Operations  {
  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate outlet_list.jsp with outlets from database depending upon the data from 
   *           the search field in outlet_list.jsp.
   * @return ArrayList 
   * @param outletListForm - An OutletListForm object.
   * @param dataSource - A DataSource object.
   */
  public static ArrayList getOutlets(OutletListForm outletListForm,DataSource dataSource,int numberOfRecords) throws SQLException{
    ArrayList outlets=new ArrayList();
    OutletListBean outlet=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query;
    
   
    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{  
      logger.info("Entering getOutkets");
      if ((outletListForm.getHdnSearchPageNo()!=null) && (outletListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(outletListForm.getHdnSearchPageNo());
      }    
  
      query=new String("select * from outlet_tbl where 1=1 ");

      if(outletListForm.getTxtSearchOutletId() != null && (outletListForm.getTxtSearchOutletId().trim()).length() != 0 ){
        query = query + "and outlet_id like '%"+outletListForm.getTxtSearchOutletId()+"%'";
      }

      if(outletListForm.getTxtSearchOutletName() != null && (outletListForm.getTxtSearchOutletName().trim()).length() != 0 ){
        query = query + " and outlet_name like'%"+outletListForm.getTxtSearchOutletName()+"%'";
      }

      if(outletListForm.getTxtSearchAddress() != null &&(outletListForm.getTxtSearchAddress().trim()).length() != 0){
        query = query +"and address1||address2||address3 like '%" + outletListForm.getTxtSearchAddress() + "%'";
      }

      if(outletListForm.getTxtSearchCity()!= null && (outletListForm.getTxtSearchCity().trim()).length() != 0 ){
        query = query + " and city like '%"+outletListForm.getTxtSearchCity()+"%'";
      }

      if(outletListForm.getCboSearchState() != null && (outletListForm.getCboSearchState().trim()).length() != 0 ){
        query = query + " and state like '%"+outletListForm.getCboSearchState()+"%'";
      }

      if(outletListForm.getCboSearchCountry() != null && (outletListForm.getCboSearchCountry().trim()).length() != 0 ){
        query = query + " and country like '%"+outletListForm.getCboSearchCountry()+"%'";
      }

      if(outletListForm.getTxtSearchPincode() != null && (outletListForm.getTxtSearchPincode().trim()).length() != 0 ){
        query = query + " and pincode like '%"+outletListForm.getTxtSearchPincode()+"%'";
      }

      if(outletListForm.getRadSearchStatus() != null && (outletListForm.getRadSearchStatus().trim()).length() != 0 && !outletListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+outletListForm.getRadSearchStatus();
      }
    
      query=query + " order by outlet_id";

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
          outlet=new OutletListBean();
          outlet.setOutletTblPk(rs.getString("outlet_tbl_pk"));
          outlet.setOutletId(rs.getString("outlet_id"));
          outlet.setOutletName(rs.getString("outlet_name"));
          outlet.setAddress1(rs.getString("address1"));
          outlet.setAddress2(rs.getString("address2"));
          outlet.setAddress3(rs.getString("address3"));
          outlet.setCity(rs.getString("city"));
          outlet.setIsActive(rs.getString("is_active"));
          outlet.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          outlets.add(outlet);
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
      outletListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      outletListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getOutkets");
    } 
   return outlets;
   
  }

  /**
   * Purpose : To save outlet_add.jsp with the specified group data.
   * @param oForm - An OutletForm object.
   * @param dataSource - A DataSource object.
   */
  public static void addOutlet(OutletForm oForm,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    String sqlString;
    int returnOutletTblPk;
    try{
      logger.info("Entering addOutlet method");
      dBConnection =  new DBConnection(dataSource);
      sqlString = "{?=call sp_ins_outlet_tbl(?,?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,oForm.getTxtOutletId());
      cs.setString(3,oForm.getTxtOutletName());
      cs.setString(4,oForm.getTxaOutletDesc());
      cs.setString(5,oForm.getTxtAddress1());
      cs.setString(6,oForm.getTxtAddress2());
      cs.setString(7,oForm.getTxtAddress3());
      cs.setString(8,oForm.getTxtCity());
      cs.setString(9,oForm.getCboState());
      cs.setString(10,oForm.getCboCountry());
      cs.setString(11,oForm.getTxtPincode());
      dBConnection.executeCallableStatement(cs);
      returnOutletTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnOutletTblPk);
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
      logger.info("Entering addOutlet method");
    }

  }

  /**
   * Purpose : To populate outlet_view.jsp and outlet_edit.jsp with the outlet data selected in the 
   *           outlet_list.jsp
   * @return OutletForm          
   * @param outletTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static OutletForm  viewOutlet(String outletTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    OutletForm oForm=null;
    try{
      logger.info("Entering viewOutlet");
      sqlString="select outlet_tbl_pk,outlet_id,outlet_name,outlet_description,address1,address2,address3,city,state,country,pincode from outlet_tbl where outlet_tbl_pk=";
      dBConnection = new  DBConnection(dataSource);
      logger.debug("String value of outlet_tbl_pk is:"+outletTblPk); 
      rs=dBConnection.executeQuery(sqlString + outletTblPk);
      if (rs.next()){
        oForm = new OutletForm();
        oForm.setHdnOutletTblPk(rs.getInt("outlet_tbl_pk"));
        oForm.setTxtOutletId(rs.getString("outlet_id"));
        oForm.setTxtOutletName(rs.getString("outlet_name"));
        oForm.setTxaOutletDesc(rs.getString("outlet_description"));
        oForm.setTxtAddress1(rs.getString("address1"));
        oForm.setTxtAddress2(rs.getString("address2"));
        oForm.setTxtAddress3(rs.getString("address3"));
        oForm.setTxtCity(rs.getString("city"));
        oForm.setCboState(rs.getString("state"));
        oForm.setCboCountry(rs.getString("country"));
        oForm.setTxtPincode(rs.getString("pincode"));
      }else{
        throw new Exception("outlet_tbl_pk not found");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null; 
      logger.info("Exiting viewOutlet");
    }
    return oForm;
  }

  /**
   * Purpose : To save outlet_edit.jsp with the specified group data.
   * @param oForm - An OutletForm object.
   * @param dataSource - A DataSource object.
   */
  public static void editOutlet (OutletForm oForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString =null;
    int returnOutletTblPk;
    try{
      logger.info("Entering editOulet");
      sqlString = "{?=call sp_upd_outlet_tbl(?,?,?,?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,oForm.getHdnOutletTblPk());
      cs.setString(3,oForm.getTxtOutletId());
      cs.setString(4,oForm.getTxtOutletName());
      cs.setString(5,oForm.getTxaOutletDesc());
      cs.setString(6,oForm.getTxtAddress1());
      cs.setString(7,oForm.getTxtAddress2());
      cs.setString(8,oForm.getTxtAddress3());
      cs.setString(9,oForm.getTxtCity());
      cs.setString(10,oForm.getCboState());
      cs.setString(11,oForm.getCboCountry());
      cs.setString(12,oForm.getTxtPincode());

      dBConnection.executeCallableStatement(cs);
      returnOutletTblPk=cs.getInt(1);
      logger.debug("updated outlet_tbl_pk:"+returnOutletTblPk);

    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }
    finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editOulet");
    }
  }


  /**
   * Purpose : To delete an outlet selected in outlet_list.jsp.
   * @param outletTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static void deleteOutlet(int outletTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnOutletTblPk;
    try{
      logger.info("Entering deleteOutlet");
      logger.debug("returnOutletTblPk value: "+outletTblPk );
      sqlString = "{? = call sp_del_outlet_tbl(?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
     
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,outletTblPk); 

      dBConnection.executeCallableStatement(cs);
      returnOutletTblPk=cs.getInt(1);
      logger.debug("Deleted outlet_tbl_pk:"+returnOutletTblPk);
    }catch(SQLException e){
      logger.error("Error in method deleteOutlet():"+e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteOutlet");
    }
  }

  /**
   * Purpose : To activate or deactivate an outlet selected in outlet_list.jsp.
   * @param outletTblPk - A String object.
   * @param outletListForm - An OutletListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactOutlet(int outletTblPk,OutletListForm outletListForm,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag=0;

    int returnOutletTblPk;
    try{
      logger.info("Entering actDeactOutlet");
      sqlString = "{? = call sp_act_deact_outlet_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(outletListForm.getHdnSearchCurrentStatus());
      logger.debug("OutletTblPk value: "+outletTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,outletTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnOutletTblPk=cs.getInt(1);
      logger.debug("ActDeact outlet_tbl_pk:"+returnOutletTblPk);
    }catch(SQLException e){
       logger.error("SqlException caught while ActDeact:"+e);
       throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting actDeactOutlet");
    }
    
  }

  /**
   * Purpose : To retrieve the outlet id of the selected record in outlet_list.jsp from the database.
   * @return String.
   * @param outletTblPk - A String object.
   * @param dataSource - A DataSource object.
   */  
  public static String getOutletId(int outletTblPk,DataSource dataSource)throws Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String outletId=null;
    try{
      logger.info("Entering getOutletId");
      dBConnection=new  DBConnection(dataSource);
      sqlString="select outlet_id from outlet_tbl where outlet_tbl_pk=";
      rs=dBConnection.executeQuery(sqlString+outletTblPk);
      if(rs.next()){
       outletId=rs.getString("outlet_id"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getOutletId");
    }
    return outletId;
  }
}