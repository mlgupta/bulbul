package bulbul.foff.customer.myprofile.beans;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myprofile.actionforms.ChangeEmailIdForm;
import bulbul.foff.customer.myprofile.actionforms.ChangePasswordForm;
import bulbul.foff.customer.myprofile.actionforms.CustomerProfileForm;
import bulbul.foff.customer.myprofile.actionforms.CustomerRegisterForm;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.general.LOOperations;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public final class Operations  {
  static Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  
  public static void registerCustomer(DataSource dataSource,CustomerRegisterForm customerRegisterForm,CustomerInfo customerInfo,String smtphost,String emailid, String emailSubject ,String emailMessage) throws SQLException,Exception{
    DBConnection dbConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnCustomerEmailIdTblPk;
    int chkNewsLetter=0;
    String customerEmailId=null;
    String customerPassword=null;
    String confirmPassword=null;
    try{
      logger.debug("Date of Birth :" +  customerRegisterForm.getTxtDateOfBirth());
      logger.info("Entering method registerCustomer()");
      customerEmailId=customerRegisterForm.getTxtCustomerEmailId();
      sqlString = "{?=call sp_register_new_customer(?,?,?,?,?,?,?)}";
      dbConnection = new DBConnection(dataSource);
      chkNewsLetter=((customerRegisterForm.getChkNewsLetter()==null)?0:1);
      logger.info("***"+customerRegisterForm.getChkNewsLetter());
      cs = dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,customerRegisterForm.getTxtCustomerEmailId());
      cs.setString(3,customerRegisterForm.getTxtCustomerPassword());
      cs.setString(4,customerRegisterForm.getTxtDateOfBirth());
      cs.setString(5,customerRegisterForm.getCboPwdHintQuestion());
      cs.setString(6,customerRegisterForm.getTxtPwdHintAnswer());
      cs.setInt(7,chkNewsLetter);
      cs.setString(8,customerRegisterForm.getTxtDesignCode());
      dbConnection.executeCallableStatement(cs);
      returnCustomerEmailIdTblPk=cs.getInt(1) ;
      if(returnCustomerEmailIdTblPk!=-1){
        customerInfo.setCustomerRegistered(FOConstants.YES_VAL.toString());
        customerInfo.setCustomerEmailId(customerRegisterForm.getTxtCustomerEmailId());
        customerInfo.setCustomerEmailIdTblPk(returnCustomerEmailIdTblPk);
        General.postMail(customerRegisterForm.getTxtCustomerEmailId(),emailSubject,emailMessage,emailid,smtphost);
        logger.debug("Mail has been sent to "+customerRegisterForm.getTxtCustomerEmailId());
      }else{
        logger.debug("Mail has not been sent.");
      }
      
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.free(cs);
      }
      dbConnection=null;
      logger.info("Exiting method registerCustomer()");
    }
  }
  
  public static boolean validityCheck(String enteredEmailId,String enteredPassword,DataSource dataSource, CustomerInfo customerInfo )throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query=null;
    String password=null;
    boolean isValid=false;
    try{
      logger.info("Entering validityCheck");
            
      query="\n SELECT ";
      query+="\n cet.customer_email_id_tbl_pk, ";
      query+="\n cet.customer_email_id, ";
      query+="\n crt.password, ";
      query+="\n cet.is_registered, ";
      query+="\n cpt.first_name, ";
      query+="\n cpt.last_name ";
      query+="\n from ";
      query+="\n customer_email_id_tbl cet, ";
      query+="\n customer_registration_tbl crt ";
      query+="\n left outer join customer_profile_tbl cpt ";
      query+="\n on(crt.customer_registration_tbl_pk=cpt.customer_registration_tbl_pk) ";
      query+="\n where cet.customer_email_id_tbl_pk=crt.customer_email_id_tbl_pk ";
      query+="\n and cet.customer_email_id='"+enteredEmailId+"'";
      query+="\n and cet.is_active=1 ";

      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        password=rs.getString("password");
        if (password.equals(enteredPassword)){
          customerInfo.setCustomerEmailIdTblPk(rs.getInt("customer_email_id_tbl_pk"));
          customerInfo.setCustomerEmailId(rs.getString("customer_email_id"));
          if (rs.getString("first_name")!=null){
            customerInfo.setCustomerFirstName(rs.getString("first_name"));
          }else{
            customerInfo.setCustomerFirstName("");
          }
          if(rs.getString("last_name")!=null){
            customerInfo.setCustomerLastName(rs.getString("last_name"));
          }else{
            customerInfo.setCustomerLastName("");
          }
          customerInfo.setCustomerRegistered(rs.getString("is_registered"));
          isValid=true;
        }
      }
    }catch(SQLException e){
      logger.error("SqlException: "+e); 
      throw e;
    }catch(Exception e){
      logger.error("Exception: "+e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting validityCheck");
    }
    logger.debug("password isValid : "+isValid);
    return isValid;
  }
  
  public static boolean isEmailIdUnique(String customerEmailId,DataSource dataSource)throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    
    try{
      logger.info("Entering method isEmailIdUnique");
      logger.info(FOConstants.YES_VAL.toString());
      dbConnection = new DBConnection(dataSource);
      /*sqlString="SELECT ce.customer_email_id from customer_email_id_tbl ce join ";
      sqlString+="customer_registration_tbl cr on (ce.customer_email_id_tbl_pk=cr.customer_email_id_tbl_pk) ";
      sqlString+="where ce.customer_email_id='"+customerEmailId+"'";
      */
      sqlString="select * from customer_email_id_tbl where ";
      sqlString+="customer_email_id='"+customerEmailId+"' and ";
      sqlString+="is_registered="+FOConstants.YES_VAL.toString();
      logger.info("query is: "+sqlString);
      rs=dbConnection.executeQuery(sqlString);
      if(!rs.next()){
       return true; 
      }else{
        //throw new Exception("Email Id is not unique.");
        return false;
      }
    }catch(SQLException e){
      throw e;
    }catch(Exception e){
      throw e;
    }finally{
      if (dbConnection!=null) {
        dbConnection.free(rs);
      }
      dbConnection=null;
      logger.info("Exiting method isEmailIdUnique");
    }
  }
  
  public static void addProfile(CustomerProfileForm customerProfileForm,DataSource dataSource)throws SQLException, IOException, Exception{
    InputStream is = null;
    String sqlString =null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    FormFile imgFile=null;
    String contentType=null;
    byte[] content = null;
    int contentSize=-1;
    int returnCustomerProfileTblPk;
    int intAge=-1;
    int oid;

    try{
      logger.info("Entering addProfile() method");      
      dBConnection = new DBConnection(dataSource);
     
      oid=LOOperations.getLargeObjectId(dataSource);
      logger.debug("oid : "+oid);
      if(customerProfileForm.getFleProfileImage()!=null){
        imgFile=customerProfileForm.getFleProfileImage();
        contentType=imgFile.getContentType();
        contentSize=imgFile.getFileData().length;
        
        logger.debug("image file name: "+imgFile.getFileName());
        logger.debug("image file size: "+imgFile.getFileSize());
        
        content=new byte[contentSize];
        is=imgFile.getInputStream();
        is.read(content);
        
        LOOperations.putLargeObjectContent(oid,content,dataSource);  
      }
      

      sqlString = "{?=call sp_ins_customer_profile_tbl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,customerProfileForm.getHdnCustomerEmailIdTblPk());
      cs.setString(3,customerProfileForm.getCboCustomerTitle());
      cs.setString(4,customerProfileForm.getTxtFirstName());      
      cs.setString(5,customerProfileForm.getTxtLastName());
      
      if(customerProfileForm.getTxtAge().trim().length()!=0){
        intAge=Integer.parseInt(customerProfileForm.getTxtAge());
      }
      cs.setInt(6,intAge);
      cs.setString(7,customerProfileForm.getCboGender());
      cs.setInt(8,oid);
      cs.setString(9,contentType);
      logger.debug("Imagr Size: "+contentSize);
      cs.setInt(10,contentSize);
      cs.setString(11,customerProfileForm.getTxtAddress1());
      cs.setString(12,customerProfileForm.getTxtAddress2());
      cs.setString(13,customerProfileForm.getTxtAddress3());
      cs.setString(14,customerProfileForm.getTxtCity());
      logger.debug("HdnIsStateListed"+customerProfileForm.getHdnIsStateListed());
      if(customerProfileForm.getHdnIsStateListed()==1){
        cs.setString(15,customerProfileForm.getCboState());
      }else{
        cs.setString(15,customerProfileForm.getTxtState());  
      }
      cs.setInt(16,customerProfileForm.getHdnIsStateListed());
      logger.debug("HdnIsCountryListed"+customerProfileForm.getHdnIsCountryListed());
      if(customerProfileForm.getHdnIsCountryListed()==1){
        cs.setString(17,customerProfileForm.getCboCountry());
      }else{
        cs.setString(17,customerProfileForm.getTxtCountry());  
      }
      cs.setInt(18,customerProfileForm.getHdnIsCountryListed());
      cs.setString(19,customerProfileForm.getTxtPincode());
      cs.setString(20,customerProfileForm.getTxtPhone1());
      cs.setString(21,customerProfileForm.getTxtPhone2());
      cs.setString(22,customerProfileForm.getTxtMobile());
      logger.debug("getChkUseAddress4Shipping: "+customerProfileForm.getChkUseAddress4Shipping());
      if(customerProfileForm.getChkUseAddress4Shipping()!=1){
        cs.setInt(23,0);  
      }else{
        cs.setInt(23,customerProfileForm.getChkUseAddress4Shipping());  
      }
      if(customerProfileForm.getChkNewsLetter()!=1){
        cs.setInt(24,0);  
      }else{
        cs.setInt(24,customerProfileForm.getChkNewsLetter());  
      }
      
      dBConnection.executeCallableStatement(cs);
      returnCustomerProfileTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnCustomerProfileTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(IOException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.info(e);
      throw e;
    }finally{
      if (is!=null){
        is.close();
      }
      if(dBConnection!=null){
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting addProfile() method");      
    }
  }
  
  public static CustomerProfileForm viewProfile(int customerEmailIdTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    CustomerProfileForm customerProfileForm=null;
    try{
      logger.info("Entering viewProfile() method");
   
      sqlString="\n SELECT cpt.customer_profile_tbl_pk,cpt.customer_registration_tbl_pk, ";
      sqlString+="\n cpt.title,cpt.first_name,cpt.last_name,age,cpt.gender,cpt.profile_image, "; 
      sqlString+="\n cpt.content_type,cpt.content_size,cpt.address_line1,cpt.address_line2, "; 
      sqlString+="\n cpt.address_line3,cpt.city,cpt.pincode,cpt.state,cpt.is_state_listed,  ";
      sqlString+="\n cpt.country,cpt.is_country_listed,cpt.phone1,cpt.phone2,cpt.mobile,  ";
      sqlString+="\n cpt.use_address_for_shipping,crt.is_news_letter_selected from  ";
      sqlString+="\n customer_profile_tbl cpt, customer_registration_tbl crt ";
      sqlString+="\n where cpt.customer_registration_tbl_pk=crt.customer_registration_tbl_pk ";
      sqlString+="\n and crt.customer_email_id_tbl_pk= " + customerEmailIdTblPk;
      
      logger.debug("sqlString : "+ sqlString);
      
      dBConnection = new  DBConnection(dataSource);
       
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        customerProfileForm = new CustomerProfileForm();
        customerProfileForm.setHdnIsNewProfile(FOConstants.NO.toString());
        customerProfileForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
        customerProfileForm.setCboCustomerTitle(rs.getString("title"));
        customerProfileForm.setTxtFirstName(rs.getString("first_name"));
        customerProfileForm.setTxtLastName(rs.getString("last_name"));
        customerProfileForm.setTxtAge(rs.getString("age"));
        customerProfileForm.setCboGender(rs.getString("gender"));
        customerProfileForm.setHdnImgOId(rs.getString("profile_image"));
        customerProfileForm.setHdnContentType(rs.getString("content_type"));
        customerProfileForm.setHdnContentSize(rs.getInt("content_size"));
        customerProfileForm.setTxtAddress1(rs.getString("address_line1"));
        customerProfileForm.setTxtAddress2(rs.getString("address_line2"));
        customerProfileForm.setTxtAddress3(rs.getString("address_line3"));
        customerProfileForm.setTxtCity(rs.getString("city"));
        customerProfileForm.setTxtPincode(rs.getString("pincode"));
        if(rs.getString("is_state_listed").equals("1")){
          customerProfileForm.setCboState(rs.getString("state"));  
        }else{
          customerProfileForm.setTxtState(rs.getString("state"));
        }
        customerProfileForm.setHdnIsStateListed(rs.getInt("is_state_listed"));
        if(rs.getString("is_country_listed").equals("1")){
          customerProfileForm.setCboCountry(rs.getString("country"));  
        }else{
          customerProfileForm.setTxtCountry(rs.getString("country"));
        }
        customerProfileForm.setHdnIsCountryListed(rs.getInt("is_country_listed"));
        customerProfileForm.setTxtPhone1(rs.getString("phone1"));
        customerProfileForm.setTxtPhone2(rs.getString("phone2"));
        customerProfileForm.setTxtMobile(rs.getString("mobile"));
        logger.debug("ChkUseAddress4Shipping: "+rs.getString("use_address_for_shipping"));
        customerProfileForm.setChkUseAddress4Shipping(rs.getInt("use_address_for_shipping"));
        customerProfileForm.setChkNewsLetter(rs.getInt("is_news_letter_selected"));
        customerProfileForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
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
      logger.info("Exiting viewProfile() method");
    }
    return customerProfileForm;
  }
  
  public static void editProfile(CustomerProfileForm customerProfileForm,DataSource dataSource)throws SQLException,IOException, Exception{
    DBConnection dBConnection= null;
    
    CallableStatement cs = null;
    FormFile imgFile=null;
    InputStream is = null;
    String contentType=null;
    String sqlString = null;
    
    int contentSize;
    byte[] content=null;
    int returnCustomerProfileTblPk;
    int intAge=-1;
    int oid;

    try{
      logger.info("Entering editProfile() method ");
      sqlString = "{?=call sp_upd_customer_profile_tbl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      
      imgFile=customerProfileForm.getFleProfileImage();
      oid=Integer.parseInt(customerProfileForm.getHdnImgOId());
    
      logger.debug("**oid: "+oid);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,customerProfileForm.getHdnCustomerEmailIdTblPk());
      cs.setString(3,customerProfileForm.getCboCustomerTitle());
      cs.setString(4,customerProfileForm.getTxtFirstName());      
      cs.setString(5,customerProfileForm.getTxtLastName());
      if(customerProfileForm.getTxtAge().trim().length()!=0){
        intAge=Integer.parseInt(customerProfileForm.getTxtAge());
      }
      cs.setInt(6,intAge);
      cs.setString(7,customerProfileForm.getCboGender());
      cs.setString(8,customerProfileForm.getTxtAddress1());
      cs.setString(9,customerProfileForm.getTxtAddress2());
      cs.setString(10,customerProfileForm.getTxtAddress3());
      cs.setString(11,customerProfileForm.getTxtCity());
      logger.debug("HdnIsStateListed"+customerProfileForm.getHdnIsStateListed());
      if(customerProfileForm.getHdnIsStateListed()==1){
        cs.setString(12,customerProfileForm.getCboState());
      }else{
        cs.setString(12,customerProfileForm.getTxtState());  
      }
      cs.setInt(13,customerProfileForm.getHdnIsStateListed());
      logger.debug("HdnIsCountryListed"+customerProfileForm.getHdnIsCountryListed());
      if(customerProfileForm.getHdnIsCountryListed()==1){
        cs.setString(14,customerProfileForm.getCboCountry());
      }else{
        cs.setString(14,customerProfileForm.getTxtCountry());  
      }
      cs.setInt(15,customerProfileForm.getHdnIsCountryListed());
      cs.setString(16,customerProfileForm.getTxtPincode());
      cs.setString(17,customerProfileForm.getTxtPhone1());
      cs.setString(18,customerProfileForm.getTxtPhone2());
      cs.setString(19,customerProfileForm.getTxtMobile());
      logger.debug("getChkUseAddress4Shipping: "+customerProfileForm.getChkUseAddress4Shipping());
      if(customerProfileForm.getChkUseAddress4Shipping()!=1){
        cs.setInt(20,0);  
      }else{
        cs.setInt(20,customerProfileForm.getChkUseAddress4Shipping());  
      }
      if(imgFile!=null && imgFile.getFileSize()>0){
        contentType=imgFile.getContentType();
        contentSize=imgFile.getFileData().length;
        content=new byte[contentSize];
        is=imgFile.getInputStream();
        is.read(content);
        LOOperations.putLargeObjectContent(oid,content,dataSource);
        cs.setString(21,contentType);
        cs.setInt(22,contentSize);
      }else{
        cs.setString(21,customerProfileForm.getHdnContentType());
        cs.setInt(22,customerProfileForm.getHdnContentSize());
      }
      if(customerProfileForm.getChkNewsLetter()!=1){
        cs.setInt(23,0);  
      }else{
        cs.setInt(23,customerProfileForm.getChkNewsLetter());  
      }
      
      dBConnection.executeCallableStatement(cs);
      returnCustomerProfileTblPk=cs.getInt(1);
      logger.debug("updated customer_profile_tbl_pk:"+returnCustomerProfileTblPk);
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
        
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting editProfile() method ");
    }
  }
  
  /*public static boolean isNewProfile(String customerRegistrationTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;
    try{
      logger.info("Entering isNewProfile() method");
      
      query="Select customer_profile_tbl_pk from customer_profile_tbl where ";
      query+="customer_registration_tbl_pk="+customerRegistrationTblPk;
      
      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query);
      if(!rs.next()){
        return true;
      }else{
        return false;
      }
    }catch(SQLException e){
      throw e;
    }catch(Exception e){
      throw e;
    }finally{
      logger.info("Exiting isNewProfile() method");
    }
  }*/
  
  public static String getCustomerProfileTblPk(String customerEmailIdTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;
    String customerProfileTblPk=null;
    try{
      logger.info("Entering getCustomerProfileTblPk() method");
      
      query="Select customer_profile_tbl_pk from customer_profile_tbl cpt, customer_registration_tbl crt where ";
      query+="crt.customer_registration_tbl_pk=cpt.customer_registration_tbl_pk";
      query+="crt.customer_email_id_tbl_pk="+customerEmailIdTblPk;
      
      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query);
      if(rs.next()){
        customerProfileTblPk=rs.getString("customer_profile_tbl_pk");
      }
      return customerProfileTblPk;
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dbConnection!=null){
        dbConnection.freeResultSet(rs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting getCustomerProfileTblPk() method");
    }
  }
  
  public static boolean isNewsLetterSelected(int customerEmailIdTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;
    try{
      logger.info("Entering isNewsLetterSelected() method");
      
      query="Select is_news_letter_selected from customer_registration_tbl where ";
      query+="customer_email_id_tbl_pk="+customerEmailIdTblPk;
      
      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query);
      if(rs.next()){
        if(rs.getString("is_news_letter_selected").equals("1")){
          logger.info("isNewsLetterSelected() Returned : True");
          return true;
        }else{
          logger.info("isNewsLetterSelected() Returned : False");
          return false;
        }
      }else{
        logger.info("isNewsLetterSelected() Returned : False");
        return false;
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dbConnection!=null){
        dbConnection.freeResultSet(rs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting isNewsLetterSelected() method");
    }
  }
  
  public static int changePassword(ChangePasswordForm cpForm,DataSource dataSource)throws SQLException,Exception{
    
    CallableStatement cs=null;
    DBConnection dbConnection=null;
    int returnCustomerRegistrationTblPk;
    String sqlString=null;
    try{
      logger.info("Entering changePassword() method");
    
      dbConnection=new DBConnection(dataSource);
      
      sqlString="{?=call sp_change_password_customer(?,?,?)}";
      cs=dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,cpForm.getHdnCustomerEmailIdTblPk());
      cs.setString(3,cpForm.getTxtOldPassword());
      cs.setString(4,cpForm.getTxtNewPassword());
      cs.execute();
      returnCustomerRegistrationTblPk=cs.getInt(1);
      logger.debug("Returned CustomerRegistrationTblPk is: "+ returnCustomerRegistrationTblPk);
      return returnCustomerRegistrationTblPk;
    }catch(SQLException e){
      logger.error("SqlException Caught: "+e);
      throw e;
    }catch(Exception e){
      logger.error("Exception Caught: "+e);
      throw e;
    }
    finally{
      if(dbConnection!=null){
        
        dbConnection.freeCallableSatement(cs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting changePassword() method");
    }
  }
  
  public static int changeEmailId(ChangeEmailIdForm ceForm,DataSource dataSource,CustomerInfo customerInfo,String smtphost,String emailid, String emailSubject ,String emailMessage)throws SQLException,Exception{
    
    CallableStatement cs=null;
    DBConnection dbConnection=null;
    int returnCustomerEmailIdTblPk;
    String sqlString=null;
    
    try{
    
      logger.info("Entering changeEmailId() method");
      sqlString="{?=call sp_change_email_id(?,?,?)}";  

      dbConnection=new DBConnection(dataSource);
      cs=dbConnection.prepareCall(sqlString);
      
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,ceForm.getHdnCustomerEmailIdTblPk());
      cs.setString(3,ceForm.getTxtOldEmailId());
      cs.setString(4,ceForm.getTxtNewEmailId());

      cs.execute();
      returnCustomerEmailIdTblPk=cs.getInt(1);
      if (returnCustomerEmailIdTblPk!=-1){
        customerInfo.setCustomerEmailId(ceForm.getTxtNewEmailId());
        General.postMail(ceForm.getTxtNewEmailId(),emailSubject,emailMessage,emailid,smtphost);
        logger.debug("Mail has been sent to "+ceForm.getTxtNewEmailId());
      }else{
        logger.debug("Mail has not been sent.");
      }
      logger.debug("Returned CustomerEmailIdTblPk is: "+returnCustomerEmailIdTblPk);
      return returnCustomerEmailIdTblPk;
    }catch(SQLException e){
      logger.error("SqlException Caught: "+e);
      throw e;
    }catch(Exception e){
      logger.error("Exception Caught: "+e);
      throw e;
    }
    finally{
      if(dbConnection!=null){
        
        dbConnection.freeCallableSatement(cs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting changeEmailId() method");
    }
  }
  
}
