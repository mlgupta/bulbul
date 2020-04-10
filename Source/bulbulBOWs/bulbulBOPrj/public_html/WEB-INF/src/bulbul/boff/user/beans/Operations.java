package bulbul.boff.user.beans;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.general.General;
import bulbul.boff.general.PgSQLArray;
import bulbul.boff.user.actionforms.UserPreferencesForm;
import bulbul.boff.user.actionforms.UserProfileForm;
import bulbul.boff.user.actionforms.UserProfileListForm;
import bulbul.boff.user.xml.UserAccess;
import bulbul.boff.user.xml.UserCategory;

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
 *        And View The User Profiles.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */


/**
 *	Purpose: To Provide The Methods Used By The Actions.
 *  Info: This Class Contain The Methods Used By The Action Class To List,Add,Edit,Delete,Activte/Deactivate 
 *        And View The User Profiles.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class


Operations  {
  private static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate user_profile_list.jsp with User Profiles from database depending upon the data from 
   *           the search field in user_profile_list.jsp.
   * @return ArrayList 
   * @param userListForm - An UserProfileListForm object.
   * @param dataSource - A DataSource object.
   */  
  public static ArrayList getUserProfiles(UserProfileListForm userListForm,DataSource dataSource, String userCategoryId, UserCategory userCategory,int numberOfRecords ) throws SQLException,Exception  {
    ArrayList userProfiles=new ArrayList();
    UserProfileListBean userProfile=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query=null;
    String addrQuery=null;
    
    int pageNumber=1;
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{      
      logger.info("Entering getUserProfiles");
      if ((userListForm.getHdnSearchPageNo()!=null) && (userListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(userListForm.getHdnSearchPageNo());
      }    
  
      query=new String("select * from user_profile_tbl where 1=1 ");

      if(userListForm.getTxtSearchUserId() != null && (userListForm.getTxtSearchUserId().trim()).length() != 0 ){
        query = query + "and user_id like '%"+userListForm.getTxtSearchUserId()+"%'";
      }

      if(!(userCategoryId.equals("-1") )){
        query = query + " and user_category_id ='"+ userCategoryId +"'";
      }

      if(userListForm.getTxtSearchUserFirstName()!= null && (userListForm.getTxtSearchUserFirstName().trim()).length() != 0 ){
        query = query + " and user_first_name like '%"+userListForm.getTxtSearchUserFirstName()+"%'";
      }

      if(userListForm.getTxtSearchUserLastName()!= null && (userListForm.getTxtSearchUserLastName().trim()).length() != 0 ){
        query = query + " and user_last_name like '%"+userListForm.getTxtSearchUserLastName()+"%'";
      }

      if(userListForm.getRadSearchStatus() != null && (userListForm.getRadSearchStatus().trim()).length() != 0 && !userListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        query = query + " and is_active="+userListForm.getRadSearchStatus();
      }
    
      query=query + " order by user_id ";
      logger.debug("QueryString For User Profile Listing: "+query);

      dBConnection= new  DBConnection(dataSource);  
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
          userProfile=new UserProfileListBean();
          userProfile.setUserProfileTblPk(rs.getString("user_profile_tbl_pk"));
          userProfile.setUserId(rs.getString("user_id"));
          userProfile.setUserFirstName(rs.getString("user_first_name"));
          userProfile.setUserLastName(rs.getString("user_last_name"));

          userProfile.setUserCategory(userCategory.getUserCategoryNameById( rs.getString("user_category_id")));

          userProfile.setIsActive(rs.getString("is_active"));
          userProfile.setIsActiveDisplay(rs.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
          userProfiles.add(userProfile);
          startIndex++;
          if (startIndex>endIndex){
            break;
          }
          
        }
      }
    }catch(SQLException e){
      logger.error("Exception: "+e);
    }catch(Exception e){
      logger.error("Exception: "+e);
    }finally{
      if(dBConnection!=null){     
        dBConnection.free(rs);
      }
      dBConnection=null;
      userListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      userListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      userListForm.setRadSearchSelect("");
      userListForm.setHdnUserCategoryId(userCategoryId);

      logger.info("Exiting getUserProfiles");
    } 
   return userProfiles;
  }

  /**
   * Purpose : To save user_profile_add.jsp with the specified group data.
   * @param userForm - An UserProfileForm object.
   * @param dataSource - A DataSource object.
   */
  public static void addUserProfile(UserProfileForm userForm,DataSource dataSource)throws SQLException,Exception{
    int returnUserProfileTblPk;
    String sqlString;
    DBConnection dBConnection=null;
    CallableStatement cs=null;
    String moduleIds=null;
    String permissionIds=null;
    String permissionValues=null;
    String[] moduleIdsArray=null;
    String[] permissionIdsArray=null;
    String[] permissionValuesArray=null;

    try{
      logger.info("Entering addUserProfile");      
      moduleIds="{";
      permissionIds="{";
      permissionValues="{";

      moduleIdsArray=userForm.getHdnModules();
      permissionIdsArray=userForm.getHdnPermissions();
      permissionValuesArray=userForm.getHdnPermissionValues();

      for(int moduleCount=0; moduleCount<moduleIdsArray.length; moduleCount++){

        moduleIds=moduleIds + moduleIdsArray[moduleCount]  ;
        permissionIds=permissionIds + permissionIdsArray[moduleCount] ;
        permissionValues=permissionValues +  permissionValuesArray[moduleCount];
        
        if(moduleCount<(moduleIdsArray.length-1)){ 
          moduleIds=moduleIds +  ",";
          permissionIds=permissionIds + ",";
          permissionValues=permissionValues + ",";
        }
      } 
      
      moduleIds=moduleIds + "}";
      permissionIds=permissionIds+"}";
      permissionValues=permissionValues+"}";  

      logger.debug("moduleIds :" + moduleIds);
      logger.debug("permissionIds :" + permissionIds);
      logger.debug("permissionValuess :" + permissionValues);
      
      dBConnection =  new  DBConnection(dataSource);
      sqlString = "{?=call sp_ins_user_profile_tbl(?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,userForm.getTxtUserId());
      cs.setString(3,userForm.getTxtUserFirstName());
      cs.setString(4,userForm.getTxtUserLastName());
      cs.setString(5,userForm.getTxtUserPassword());
      cs.setObject(6,userForm.getCboUserCategory(),Types.CHAR);
      cs.setArray(7,new PgSQLArray(moduleIds,Types.VARCHAR));
      cs.setArray(8,new PgSQLArray(permissionIds,Types.VARCHAR));
      cs.setArray(9,new PgSQLArray(permissionValues,Types.VARCHAR));      

      dBConnection.executeCallableStatement(cs);
      
      returnUserProfileTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnUserProfileTblPk);
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
      logger.info("Entering addUserProfile");      
    }
  }

  /**
   * Purpose : To populate user_profile_view.jsp and user_profile_edit.jsp with the UserProfile data selected in the 
   *           user_profile_list.jsp
   * @return UserProfileForm          
   * @param userProfileTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static UserProfileForm  viewUserProfile(String userProfileTblPk,DataSource dataSource,String xmlFilePhysicalPath) throws SQLException, Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    UserProfileForm userForm=null;
    ArrayList userRights=null; 
    ArrayList permissions=null;
    UserAccess userAccess = null;
    UserAccess.Module module=null;
    String permissionValues=null;
    try{
      logger.info("Entering viewUserProfile");
      logger.debug("String value of user_profile_tbl_pk is:"+userProfileTblPk); 
     
      sqlString="select user_profile_tbl_pk,user_id,user_password,user_first_name,user_last_name,user_category_id from user_profile_tbl where user_profile_tbl_pk=";
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString + userProfileTblPk);
      logger.debug("Query in viewUserProfile(): "+sqlString+userProfileTblPk);
      if (rs.next()){
        userForm = new UserProfileForm();
        userForm.setHdnUserProfileTblPk(rs.getInt("user_profile_tbl_pk"));
        userForm.setTxtUserId(rs.getString("user_id"));
        userForm.setTxtUserPassword(rs.getString("user_password"));
        userForm.setTxtUserFirstName(rs.getString("user_first_name"));
        userForm.setTxtUserLastName(rs.getString("user_last_name"));
        userForm.setCboUserCategory(rs.getString("user_category_id"));
        logger.debug("category is: "+userForm.getCboUserCategory());
      }else{
        throw new Exception("user_profile_tbl_pk not found");
      }
      
      if (userForm.getTxtUserId().equals("admin")){
        userAccess = new UserAccess(xmlFilePhysicalPath);
        userRights=userAccess.getModules();
        for(int moduleIndex=0; moduleIndex<userRights.size(); moduleIndex++){
          module=(UserAccess.Module)userRights.get(moduleIndex);
          permissions=module.getPermissions();
          permissionValues="";
          for(int permissionIndex=0; permissionIndex<permissions.size(); permissionIndex++){
            permissionValues+="1|";            
          }
          userForm.userAccessTblPks.add("-1");
          userForm.permissionValues.add(permissionValues);
        }
      }else{
        sqlString="select user_access_tbl_pk,permission_values from user_access_tbl where user_profile_tbl_pk=" + userProfileTblPk + " order by module_id";        
        rs=dBConnection.executeQuery(sqlString); 
        while(rs.next()){
          userForm.userAccessTblPks.add(Integer.toString(rs.getInt("user_access_tbl_pk")));
          userForm.permissionValues.add(rs.getString("permission_values"));
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
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting viewUserProfile");
    }
    return userForm;
  }

  /**
   * Purpose : To save user_profile_edit.jsp with the specified group data.
   * @param userForm - An UserProfileForm object.
   * @param dataSource - A DataSource object.
   */
  public static void editUserProfile (UserProfileForm userForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String moduleIds=null;
    String permissionIds=null;
    String permissionValues=null;
    String userAccessTblPk=null; 
    String[] userAccessTblPkArray=null;
    String[] moduleIdsArray=null;
    String[] permissionIdsArray=null;
    String[] permissionValuesArray=null;
    String sqlString = null;
    int returnUserProfileTblPk;
    
    try{
      logger.info("Entering editUserProfile");
      moduleIds="{";
      permissionIds="{";
      permissionValues="{";
      userAccessTblPk="{";
      
      userAccessTblPkArray=userForm.getHdnUserAccessTblPk();
      moduleIdsArray=userForm.getHdnModules();
      permissionIdsArray=userForm.getHdnPermissions();
      permissionValuesArray=userForm.getHdnPermissionValues();


      for(int intCount=0; intCount<userAccessTblPkArray.length; intCount++){

        userAccessTblPk=userAccessTblPk+userAccessTblPkArray[intCount];
        if(intCount<(moduleIdsArray.length)){
          moduleIds=moduleIds + moduleIdsArray[intCount];
          permissionIds=permissionIds + permissionIdsArray[intCount];
          permissionValues=permissionValues + permissionValuesArray[intCount];
        }
        if(intCount<(userAccessTblPkArray.length-1)){ 
          userAccessTblPk=userAccessTblPk + ",";
          if(intCount<(moduleIdsArray.length-1)){
            moduleIds=moduleIds +  ",";
            permissionIds=permissionIds + ",";
            permissionValues=permissionValues + ",";
          }
        }
      } 
      userAccessTblPk=userAccessTblPk+"}";
      moduleIds=moduleIds + "}";
      permissionIds=permissionIds+"}";
      permissionValues=permissionValues+"}"; 

      logger.debug("useraccess : " + userAccessTblPk);
      logger.debug("module : " + moduleIds);
      logger.debug("pid : " + permissionIds);
      logger.debug("pval : " + permissionValues);
      
      dBConnection = new DBConnection(dataSource);
      sqlString = "{?=call sp_upd_user_profile_tbl(?,?,?,?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,userForm.getHdnUserProfileTblPk());
      cs.setString(3,userForm.getTxtUserId());
      cs.setString(4,userForm.getTxtUserFirstName());
      cs.setString(5,userForm.getTxtUserLastName());
      cs.setString(6,userForm.getTxtUserPassword());
      cs.setObject(7,userForm.getCboUserCategory(),Types.CHAR);
      cs.setArray(8,new PgSQLArray(userAccessTblPk,Types.INTEGER));
      cs.setArray(9,new PgSQLArray(moduleIds,Types.VARCHAR));
      cs.setArray(10,new PgSQLArray(permissionIds,Types.VARCHAR));
      cs.setArray(11,new PgSQLArray(permissionValues,Types.VARCHAR));  
      logger.debug(sqlString);
      dBConnection.executeCallableStatement(cs);
      returnUserProfileTblPk=cs.getInt(1);
      logger.debug("updated user_profile_tbl_pk:"+returnUserProfileTblPk);
    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }catch(Exception e)  {
      logger.error("Exception: "+ e);
      throw e;
    }
    finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editUserProfile");
    }
  }

  /**
   * Purpose : To delete the User Profile selected in user_profile_list.jsp.
   * @param userProfileTblPk - A String object.
   * @param dataSource - A DataSource object.
   */ 
  public static void deleteUserProfile(int userProfileTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnUserProfileTblPk;
    try{
      logger.info("Entering deleteUserProfile");
      sqlString = "{? = call sp_del_user_profile_tbl(?)}";
      logger.debug("returnUserProfileTblPk value: "+userProfileTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,userProfileTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnUserProfileTblPk=cs.getInt(1);
      logger.debug("Deleted user_profile_tbl_pk:"+returnUserProfileTblPk);
    }catch(SQLException e){
      logger.error("Error in method deleteUserProfile():"+e);
      throw e;
    }catch(Exception e){
      logger.error("Error in method deleteUserProfile():"+e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting deleteUserProfile");
    }
  }

  /**
   * Purpose : To activate or deactivate a User selected in inet_bank_list.jsp.
   * @param userProfileTblPk - A String object.
   * @param userListForm - An UserProfileListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactUserProfile(int userProfileTblPk,UserProfileListForm userListForm,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag=0;
    int returnUserProfileTblPk;
    
    try{
      logger.info("Entering actDeactUserProfile");
      sqlString = "{? = call sp_act_deact_user_profile_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(userListForm.getHdnSearchCurrentStatus());
      logger.debug("UserProfileTblPk value: "+userProfileTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,userProfileTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnUserProfileTblPk=cs.getInt(1);
      logger.debug("ActDeact user_profile_tbl_pk:"+returnUserProfileTblPk);
    }catch(SQLException e){
      logger.error("SqlException caught while ActDeact:"+e);
      throw e;
    }catch(Exception e){
      logger.error("Exception caught while ActDeact:"+e);
      throw e;
    }
    finally{
      if(dBConnection!=null){
        dBConnection.free(cs); 
      } 
      dBConnection=null;
      logger.info("Exiting actDeactUserProfile");      
    }
  }

  /**
   * Purpose : To retrieve the User Id of the selected record in user_profile_list.jsp from the database.
   * @return String.
   * @param userProfileTblPk - A String object.
   * @param dataSource - A DataSource object.
   */ 
  public static String getUserId(int userProfileTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String userId=null;
    try{
      logger.info("Entering getUserId");
      dBConnection=new  DBConnection(dataSource);
      sqlString="select user_id from user_profile_tbl where user_profile_tbl_pk=";
      rs=dBConnection.executeQuery(sqlString+userProfileTblPk);
      if(rs.next()){
       userId=rs.getString("user_id"); 
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
      logger.info("Exiting getUserId");      
    }
    return userId;
 }

 public static UserPreferencesForm  viewUserPreferences(String userProfileTblPk,DataSource dataSource) throws SQLException, Exception{

    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    UserPreferencesForm userPreferencesForm=null;
    try{
      logger.info("Entering viewUserPreferences");
      logger.debug("String value of user_profile_tbl_pk is:"+userProfileTblPk); 
     
      sqlString="select * from user_profile_tbl where user_profile_tbl_pk=" + userProfileTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      logger.debug("Query in viewUserPreferences(): "+sqlString);
      if (rs.next()){
        userPreferencesForm = new UserPreferencesForm();
        userPreferencesForm.setHdnUserProfileTblPk(rs.getInt("user_profile_tbl_pk"));
        userPreferencesForm.setTxtUserId(rs.getString("user_id"));
        userPreferencesForm.setTxtUserFirstName(rs.getString("user_first_name"));
        userPreferencesForm.setTxtUserLastName(rs.getString("user_last_name"));
        userPreferencesForm.setCboUserCategory(rs.getString("user_category_id"));
        userPreferencesForm.setTxtTreeViewLevel(rs.getInt("treeview_level"));
        userPreferencesForm.setTxtNumberOfRecords(rs.getInt("number_of_records"));
      }else{
        throw new Exception("user_profile_tbl_pk not found");
      }
      //sqlString="select user_access_tbl_pk,permission_values from user_access_tbl where user_profile_tbl_pk=" + userProfileTblPk + " order by module_id";        
      //rs=dBConnection.executeQuery(sqlString); 
      //while(rs.next()){
       // userForm.userAccessTblPks.add(Integer.toString(rs.getInt("user_access_tbl_pk")));
        //userForm.permissionValues.add(rs.getString("permission_values"));
      //}
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
      logger.info("Entering viewUserPreferences");
    }
    return userPreferencesForm;
  }

  public static void editUserPreferences(UserPreferencesForm userPreferencesForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString=null;
    int returnUserProfileTblPk;
    
    try{
      logger.info("Entering editUserPreferences");
      
      dBConnection = new DBConnection(dataSource);
      sqlString = "{?=call sp_upd_user_preferences(?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,userPreferencesForm.getHdnUserProfileTblPk());
      cs.setInt(3,userPreferencesForm.getTxtTreeViewLevel());
      cs.setInt(4,userPreferencesForm.getTxtNumberOfRecords());
      logger.debug(sqlString);
      dBConnection.executeCallableStatement(cs);
      returnUserProfileTblPk=cs.getInt(1);
      logger.debug("updated user_profile_tbl_pk:"+returnUserProfileTblPk);
    }catch(SQLException e)  {
      logger.error("SqlException: "+ e);
      throw e;
    }catch(Exception e)  {
      logger.error("Exception: "+ e);
      throw e;
    }
    finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editUserPreferences");
    }
  }
  
} 