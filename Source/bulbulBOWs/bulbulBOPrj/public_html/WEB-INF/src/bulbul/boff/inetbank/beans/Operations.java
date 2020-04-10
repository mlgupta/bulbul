package bulbul.boff.inetbank.beans;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.inetbank.actionforms.InetBankForm;

import bulbul.boff.inetbank.actionforms.InetBankListForm;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 *	Purpose: To Provide The Methods Used By The Actions.
 *  Info: This Class Contain The Methods Used By The Action Class To List,Add,Edit,Delete,Activte/Deactivate 
 *        And View The Internet Banks.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class


Operations  {
  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate inet_bank_list.jsp with Internet Banks from database depending upon the data from 
   *           the search field in inet_bank_list.jsp.
   * @return ArrayList 
   * @param inetListForm - An InetBankListForm object.
   * @param dataSource - A DataSource object.
   */  
 public static ArrayList getInetBanks(InetBankListForm inetListForm,DataSource dataSource,int numberOfRecords) throws SQLException, Exception{
    ArrayList inetBanks=new ArrayList();
    InetBankListBean inetBank=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query;

    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{  
      logger.info("Entering getInetBanks");

      if ((inetListForm.getHdnSearchPageNo()!=null) && (inetListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(inetListForm.getHdnSearchPageNo());
      }    

      query=new String("select * from inet_banking_tbl where 1=1 ");

      if(inetListForm.getTxtSearchBankName() != null && (inetListForm.getTxtSearchBankName().trim()).length() != 0 ){
        query = query + "and bank_name like '%"+inetListForm.getTxtSearchBankName()+"%'";
      }

      if(inetListForm.getTxtSearchBankAcNo() != null && (inetListForm.getTxtSearchBankAcNo().trim()).length() != 0 ){
        query = query + " and bank_account_number like'%"+inetListForm.getTxtSearchBankAcNo()+"%'";
      }
      
      if(inetListForm.getTxtSearchBankRoutingNo() != null && (inetListForm.getTxtSearchBankRoutingNo().trim()).length() != 0 ){
        query = query + " and bank_routing_number like'%"+inetListForm.getTxtSearchBankRoutingNo()+"%'";
      }
      
      if(inetListForm.getRadSearchStatus() != null && (inetListForm.getRadSearchStatus().trim()).length() != 0 && !inetListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+inetListForm.getRadSearchStatus();
      }
    
      query=query + " order by bank_name ";
      
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
          inetBank=new InetBankListBean();
          inetBank.setInetBankTblPk(rs.getString("inet_banking_tbl_pk"));
          inetBank.setBankName(rs.getString("bank_name"));
          inetBank.setBankAcNo(rs.getString("bank_account_number"));
          inetBank.setBankRoutingNo(rs.getString("bank_routing_number"));
          inetBank.setIsActive(rs.getString("is_active"));
          inetBank.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          logger.debug(inetBank.getIsActiveDisplay());
          inetBanks.add(inetBank);
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
      inetListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      inetListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger.info("Exiting getInetBanks");
    }
   return inetBanks;
 }

  /**
   * Purpose : To save inet_bank_add.jsp with the specified group data.
   * @param inetForm - An InetBankForm object.
   * @param dataSource - A DataSource object.
   */
  public static void addInetBank(InetBankForm inetForm,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    String sqlString;
    int returnInetBankTblPk;
    try{
      logger.info("Entering addInetBank ");
      dBConnection =  new  DBConnection(dataSource);
      sqlString = "{?=call sp_ins_inet_bank_tbl(?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,inetForm.getTxtBankName());
      cs.setString(3,inetForm.getTxtBankAcNo() );
      cs.setString(4,inetForm.getTxtBankRoutingNo());
      logger.debug("BankAcNo in inetBankAdd(): "+inetForm.getTxtBankAcNo());
      dBConnection.executeCallableStatement(cs);
      returnInetBankTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnInetBankTblPk);
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
      logger.info("Exiting addInetBank ");
    }
  }

  /**
   * Purpose : To populate inet_bank_view.jsp and inet_bank_edit.jsp with the InetBank data selected in the 
   *           inet_bank_list.jsp
   * @return InetBankForm          
   * @param inetBankTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static InetBankForm  viewInetBank(String inetBankTblPk,DataSource dataSource) throws SQLException,  Exception{
     DBConnection dBConnection =null;
     ResultSet rs= null;
     String sqlString=null;
     InetBankForm inetForm=null;
     try{
      logger.info("Entering viewInetBank");
      logger.debug("String value of outlet_tbl_pk is:"+inetBankTblPk); 
      sqlString="select inet_banking_tbl_pk,bank_name,bank_account_number,bank_routing_number from inet_banking_tbl where inet_banking_tbl_pk=";
      dBConnection= new  DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString + inetBankTblPk);
      if (rs.next()){
        inetForm = new InetBankForm();
        inetForm.setHdnInetBankTblPk(rs.getInt("inet_banking_tbl_pk"));
        inetForm.setTxtBankName(rs.getString("bank_name"));
        inetForm.setTxtBankAcNo(rs.getString("bank_account_number"));
        inetForm.setTxtBankRoutingNo(rs.getString("bank_routing_number"));
      }else{
        throw new Exception("inet_banking_tbl_pk not found");
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
      logger.info("Exiting viewInetBank");
    }
    return inetForm;
  }

  /**
   * Purpose : To save inet_bank_edit.jsp with the specified group data.
   * @param inetForm - An InetBankForm object.
   * @param dataSource - A DataSource object.
   */
  public static void editInetBank (InetBankForm inetForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    int returnInetBankTblPk;
    String sqlString =null;
    try{
      logger.info("Exiting editInetBank"); 
      sqlString = "{?=call sp_upd_inet_bank_tbl(?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,inetForm.getHdnInetBankTblPk());
      cs.setString(3,inetForm.getTxtBankName());
      cs.setString(4,inetForm.getTxtBankAcNo());
      cs.setString(5,inetForm.getTxtBankRoutingNo());
      dBConnection.executeCallableStatement(cs);
      returnInetBankTblPk=cs.getInt(1);
      logger.debug("updated inet_Banking_tbl_pk:"+returnInetBankTblPk);
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
      logger.info("Exiting editInetBank"); 
    }
  }

  /**
   * Purpose : To delete an Internet Bank selected in inet_bank_list.jsp.
   * @param inetBankTblPk - A String object.
   * @param dataSource - A DataSource object.
   */  
  public static void deleteInetBank(int inetBankTblPk,DataSource dataSource) throws SQLException,   Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnInetBankTblPk;
    try{
      logger.info("Entering deleteInetBank");
      sqlString = "{? = call sp_del_inet_bank_tbl(?)}";
      logger.debug("returnInetBankTblPk value: "+inetBankTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,inetBankTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnInetBankTblPk=cs.getInt(1);
      logger.debug("Deleted inet_banking_tbl_pk:"+returnInetBankTblPk);
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
      logger.info("Exiting deleteInetBank");
    }
  }

  /**
   * Purpose : To activate or deactivate an Internet Bank selected in inet_bank_list.jsp.
   * @param inetBankTblPk - A String object.
   * @param inetListForm - An InetBankListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactInetBank(int inetBankTblPk,InetBankListForm inetListForm,DataSource dataSource) throws SQLException,  Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnInetBankTblPk;
    
    try{
      logger.info("Entering actDeactInetBank");
      sqlString = "{? = call sp_act_deact_Inet_bank_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(inetListForm.getHdnSearchCurrentStatus());
      logger.debug("inet_banking_tbl_pk value : "+inetBankTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,inetBankTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnInetBankTblPk=cs.getInt(1);
      logger.debug("ActDeact inet_banking_tbl_pk after update:"+returnInetBankTblPk);
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
      logger.info("Exiting actDeactInetBank");
    }
  }

  /**
   * Purpose : To retrieve the InetBank Name of the selected record in inet_bank_list.jsp from the database.
   * @return String.
   * @param inetBankTblPk - A String object.
   * @param dataSource - A DataSource object.
   */ 
  public static String getBankName(int inetBankTblPk,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String bankName=null;
    try{
      logger.info("Entering getBankName");
      dBConnection= new DBConnection(dataSource);
      sqlString="select bank_name from inet_banking_tbl where inet_banking_tbl_pk=";
      rs=dBConnection.executeQuery(sqlString+inetBankTblPk);
      if(rs.next()){
       bankName=rs.getString("bank_name"); 
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
      logger.info("Exiting getBankName");
    }
    return bankName;
  }
}