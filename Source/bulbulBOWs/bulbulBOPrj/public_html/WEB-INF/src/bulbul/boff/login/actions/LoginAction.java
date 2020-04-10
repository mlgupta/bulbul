package bulbul.boff.login.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;
import bulbul.boff.login.actionforms.LoginForm;
import bulbul.boff.user.beans.UserProfile;
import bulbul.boff.user.xml.UserAccess;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class LoginAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ActionMessages messages= new ActionMessages();
    LoginForm loginForm=null;
    UserProfile userProfile=null;
    ServletContext context = null;
    DataSource dataSource = null;
    HttpSession httpSession=null;
    String physicalPath = null;
    String userId=null;
    String userPassword=null;

    try{
      logger.info("Entering Login Action");

      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      loginForm=(LoginForm)form;
      userId=loginForm.getTxtLoginId();
      userPassword=loginForm.getTxtLoginPassword();
      httpSession= request.getSession(true);
      physicalPath = httpSession.getServletContext().getRealPath("/");
      userProfile=new UserProfile();
      
      if(validityCheck(userId,userPassword,dataSource,userProfile,physicalPath)){
        
        logger.debug("User Name : "+userProfile.getUserName());

        httpSession.setAttribute("userProfile",userProfile);

        logger.info("Exiting Login Action");  
        return mapping.findForward("success");  
      }else{
        ActionMessage msg=new ActionMessage("msg.LoginFailed");
        messages.add("message1",msg);
      }
    }catch(SQLException e){
      logger.error("SqlException Caught: "+e);
    }catch(Exception e){
      logger.error("Exception Caught: "+e);
    }
    saveMessages(request,messages);
    logger.info("Exiting Login Action");  
    return mapping.getInputForward(); 
  }
  public boolean validityCheck(String userId,String userPassword,DataSource dataSource, UserProfile userProfile, String physicalPath  )throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String query=null;
    String password=null;
    String isActive=null;
    boolean isValid=false;
    try{
      logger.info("Entering validityCheck");
      query="select * from user_profile_tbl where user_id='"+userId+"'";
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        password=rs.getString("user_password");
        isActive=rs.getString("is_active");
        if (password.equals(userPassword) && isActive.equals("1")){
          userProfile.setUserProfileTblPk(rs.getInt("user_profile_tbl_pk"));
          userProfile.setUserId(rs.getString("user_id"));
          userProfile.setUserName(rs.getString("user_first_name")+" "+rs.getString("user_last_name"));
          userProfile.setTreeviewLevel(rs.getInt("treeview_level"));
          userProfile.setNumberOfRecords(rs.getInt("number_of_records"));
          userProfile.setUserRights(getUserRights(userProfile.getUserProfileTblPk(),userProfile.getUserId(),dataSource,physicalPath));
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
    return isValid;
  }

  public ArrayList getUserRights(int userProfileTblPk, String userId,DataSource dataSource,String xmlFilePhysicalPath)throws SQLException,Exception {
    ArrayList userRights=null; 
    ArrayList permissions=null;
    UserAccess userAccess = null;
    UserAccess.Module module=null;
    UserAccess.Permission permission =null;
    
    DBConnection  dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;

    String pipedPermissionIds=null;
    String pipedPermissionValues=null;
    String[] permissionIdArray=null;
    String[] permissionValueArray=null;
    
    try{
      logger.info("Entering getUserRights");
      userAccess = new UserAccess(xmlFilePhysicalPath);
      if(userId.equals("admin")){  //All rights Irrespective of the Modules the permission values set "1";
        logger.info("Admin Rights");
        userRights=userAccess.getModules();
        for(int moduleIndex=0; moduleIndex<userRights.size(); moduleIndex++){
          module=(UserAccess.Module)userRights.get(moduleIndex);
          permissions=module.getPermissions();
          for(int permissionIndex=0; permissionIndex<permissions.size(); permissionIndex++){
            permission=(UserAccess.Permission)permissions.get(permissionIndex);
            permission.setValue("1");
          }
        }
      }else{
        logger.info("Other User Rights");
        sqlString="select module_id, permission_ids, permission_values from user_access_tbl";
        sqlString= sqlString + " where user_profile_tbl_pk = " + userProfileTblPk;
        
        logger.debug("sqlString : " + sqlString);
        
        dBConnection= new DBConnection(dataSource);
        rs=dBConnection.executeQuery(sqlString);
        userRights = new ArrayList();
        while (rs.next()){
          module = new UserAccess.Module();
          module.setId(rs.getString("module_id"));
          module.setName(userAccess.getModuleNameById(module.getId()));

          permissionIdArray=null;
          permissionValueArray=null;

          pipedPermissionIds=new String(rs.getString("permission_ids"));
          pipedPermissionValues=new String(rs.getString("permission_values"));
        
          pipedPermissionIds=pipedPermissionIds.replace('|',',');
          pipedPermissionValues=pipedPermissionValues.replace('|',',');

          permissionIdArray=pipedPermissionIds.trim().split(",");
          permissionValueArray=pipedPermissionValues.trim().split(",");
 
          permissions = new ArrayList(); 
          for(int permissionIndex=0 ; permissionIndex<permissionIdArray.length; permissionIndex++){
            permission= new UserAccess.Permission();
            permission.setId(permissionIdArray[permissionIndex]);
            permission.setName(userAccess.getPermissionNameById(module.getId(),permission.getId()));
            permission.setValue(permissionValueArray[permissionIndex]);
            permissions.add(permission);          
          }

          module.setPermissions(permissions);
          userRights.add(module);
        }
      }
    }catch(SQLException e){
      logger.error("SqlException: "+e); 
      throw e;
    }
    catch(Exception e){
      logger.error("Exception: "+e);
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Entering getUserRights");
    }
    return userRights; 
  }
}