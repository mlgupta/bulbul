package bulbul.foff.customer.myaddress.beans;

import bulbul.foff.customer.myaddress.actionforms.ShippingAddressForm;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public final class Operations  {
  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  static public String pageCountString="1";
  
 public static ArrayList getAddresses(int customerEmailIdTblPk,DataSource dataSource,int numberOfRecords,int pageNumber) throws SQLException,Exception{
    ArrayList addresses=null;
    ShippingAddressBean address=null;
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{
      logger.info("Entering getProducts() method");
      addresses=new ArrayList();
      
      query="\n SELECT customer_address_book_tbl_pk,customer_email_id_tbl_pk, ";
      query+="\n full_name,address_line_1,address_line_2,city,pincode,state, ";
      query+="\n is_state_listed,country,is_country_listed,phone_number,email_id ";
      query+="\n from customer_address_book_tbl ";
      query+="\n where customer_email_id_tbl_pk="+customerEmailIdTblPk;
      
      query=query + " order by full_name ";
      
      logger.debug("query: "+query);

      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  

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
          address=new ShippingAddressBean();
          address.setCustomerAddressBookTblPk(rs.getString("customer_address_book_tbl_pk"));
          address.setFullName(rs.getString("full_name"));
          address.setAddressLine1(rs.getString("address_line_1"));
          address.setAddressLine2(rs.getString("address_line_2"));
          address.setCity(rs.getString("city"));
          address.setPincode(rs.getString("pincode"));
          address.setState(rs.getString("state"));
          address.setIsStateSelected(rs.getString("is_state_listed"));
          address.setCountry(rs.getString("country"));
          address.setIsCountrySelected(rs.getString("is_country_listed"));
          address.setPhoneNumber(rs.getString("phone_number"));
          address.setEmailId(rs.getString("email_id"));
          addresses.add(address);
          startIndex++;
          logger.debug("startIndex: "+startIndex);
          if (startIndex>endIndex){
            break;
          }
        }    
      }
    }catch(SQLException e){
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }finally{
      logger.info("Exiting getAddresses() method");
      if(dbConnection!=null){
        dbConnection.free(rs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.debug("At Last pageNumber: "+pageNumber);
      pageCountString=Integer.toString(pageCount);
    }
    return addresses;
  }
  
  public static void addAddressBook(ShippingAddressForm shippingAddressForm,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    String sqlString=null;
    int returnCustomerAddressBookTblPk;
    CallableStatement cs=null;
    
    try{
      logger.info("Entering addAddressBook() method");
      
      sqlString="{?=call sp_ins_customer_address_book_tbl(?,?,?,?,?,?,?,?,?,?,?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,shippingAddressForm.getHdnCustomerEmailIdTblPk());
      cs.setString(3,shippingAddressForm.getTxtFullName());
      cs.setString(4,shippingAddressForm.getTxtAddressLine1());
      cs.setString(5,shippingAddressForm.getTxtAddressLine2());
      cs.setString(6,shippingAddressForm.getTxtCity());
      cs.setString(7,shippingAddressForm.getTxtPincode());
      logger.info("cboState : " + shippingAddressForm.getCboState());
      logger.info("txtState : " + shippingAddressForm.getTxtState());
      if(!shippingAddressForm.getCboState().equals("0")){
        cs.setString(8,shippingAddressForm.getCboState());  
      }else if(shippingAddressForm.getTxtState().trim().length()!=0){
        cs.setString(8,shippingAddressForm.getTxtState());
      }
      cs.setInt(9,shippingAddressForm.getHdnIsStateListed());
      
      if(!shippingAddressForm.getCboCountry().equals("0")){
        cs.setString(10,shippingAddressForm.getCboCountry());  
      }else if(shippingAddressForm.getTxtCountry().trim().length()!=0){
        cs.setString(10,shippingAddressForm.getTxtCountry());
      }
      
      cs.setInt(11,shippingAddressForm.getHdnIsCountryListed());
      cs.setString(12,shippingAddressForm.getTxtPhone());
      logger.info("EmailId : "+ shippingAddressForm.getTxtEmailId());
      cs.setString(13,shippingAddressForm.getTxtEmailId());
      
      dBConnection.executeCallableStatement(cs);
      
      returnCustomerAddressBookTblPk=cs.getInt(1);
      logger.debug("added customer_address_book_tbl_pk:"+returnCustomerAddressBookTblPk);

    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting addAddressBook() method");
    }
  }
  
  public static ShippingAddressForm viewAddressBook(String customerAddressBookTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    ShippingAddressForm shippingAddressForm=null;
    try{
      logger.info("Entering viewAddressBook() method");
      
      sqlString="\n SELECT customer_address_book_tbl_pk,customer_email_id_tbl_pk, ";
      sqlString+="\n full_name,address_line_1,address_line_2,city,pincode,state,is_state_listed, ";
      sqlString+="\n country,is_country_listed,phone_number,email_id ";
      sqlString+="\n from customer_address_book_tbl ";
      sqlString+="\n where customer_address_book_tbl_pk="+customerAddressBookTblPk;
      
      logger.debug("sqlString : "+ sqlString);
      
      dBConnection = new  DBConnection(dataSource);
      logger.debug("String value of customer_address_book_tbl_pk is:"+customerAddressBookTblPk); 
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        shippingAddressForm = new ShippingAddressForm();
        shippingAddressForm.setHdnCustomerAddressBookTblPk(rs.getInt("customer_address_book_tbl_pk"));
        shippingAddressForm.setTxtFullName(rs.getString("full_name"));
        shippingAddressForm.setTxtAddressLine1(rs.getString("address_line_1"));
        shippingAddressForm.setTxtAddressLine2(rs.getString("address_line_2"));
        shippingAddressForm.setTxtCity(rs.getString("city"));
        shippingAddressForm.setTxtPincode(rs.getString("pincode"));
        if(rs.getString("is_state_listed").equals("1")){
          shippingAddressForm.setCboState(rs.getString("state"));  
        }else{
          shippingAddressForm.setTxtState(rs.getString("state"));
        }
        shippingAddressForm.setHdnIsStateListed(rs.getInt("is_state_listed"));
        if(rs.getString("is_country_listed").equals("1")){
          shippingAddressForm.setCboCountry(rs.getString("country"));  
        }else{
          shippingAddressForm.setTxtCountry(rs.getString("country"));
        }
        shippingAddressForm.setHdnIsCountryListed(rs.getInt("is_country_listed"));
        shippingAddressForm.setTxtPhone(rs.getString("phone_number"));
        shippingAddressForm.setTxtEmailId(rs.getString("email_id"));
      }else{
        throw new Exception("customer_address_book_tbl_pk not found");
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
      logger.info("Exiting viewAddressBook() method");
    }
    return shippingAddressForm;
  }
  
  public static void modifyAddressBook (ShippingAddressForm shippingAddressForm,DataSource dataSource)throws Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString =null;
    int returnCustomerAddressBookTblPk;
    try{
      logger.info("Entering modifyAddressBook() method");
      sqlString = "{?=call sp_upd_customer_address_book_tbl(?,?,?,?,?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,shippingAddressForm.getHdnCustomerAddressBookTblPk());
      cs.setString(3,shippingAddressForm.getTxtFullName());
      cs.setString(4,shippingAddressForm.getTxtAddressLine1());
      cs.setString(5,shippingAddressForm.getTxtAddressLine2());
      cs.setString(6,shippingAddressForm.getTxtCity());
      cs.setString(7,shippingAddressForm.getTxtPincode());
      if(shippingAddressForm.getHdnIsStateListed()==1){
        cs.setString(8,shippingAddressForm.getCboState());
      }else{
        cs.setString(8,shippingAddressForm.getTxtState());  
      }
      cs.setInt(9,shippingAddressForm.getHdnIsStateListed());
      if(shippingAddressForm.getHdnIsCountryListed()==1){
        cs.setString(10,shippingAddressForm.getCboCountry());
      }else{
        cs.setString(10,shippingAddressForm.getTxtCountry());  
      }
      cs.setInt(11,shippingAddressForm.getHdnIsCountryListed());
      cs.setString(12,shippingAddressForm.getTxtPhone());
      cs.setString(13,shippingAddressForm.getTxtEmailId());

      dBConnection.executeCallableStatement(cs);
      returnCustomerAddressBookTblPk=cs.getInt(1);
      logger.debug("updated customer_address_book_tbl_pk:"+returnCustomerAddressBookTblPk);

    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }
    finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting modifyAddressBook() method");
    }
  }
  
  public static void deleteAddressBook(int customerAddressBookTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnCustomerAddressBookTblPk;
    try{
      logger.info("Entering deleteAddressBook() method");
      logger.debug("CustomerAddressBookTblPk value: "+customerAddressBookTblPk );
      sqlString = "{? = call sp_del_customer_address_book_tbl(?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
     
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,customerAddressBookTblPk); 

      dBConnection.executeCallableStatement(cs);
      returnCustomerAddressBookTblPk=cs.getInt(1);
      logger.debug("Deleted customer_address_book_tbl_pk:"+returnCustomerAddressBookTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteAddressBook() method");
    }
  }
}