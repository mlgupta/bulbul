package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.ChangePasswordForm;

import java.io.IOException;

import java.lang.Long;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class ChangePasswordAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    logger.info("***Entering ChangePasswordAction***");
    ActionMessages messages=new ActionMessages();
    ChangePasswordForm cpForm=null;
    String userProfiloeTblPk=null;
    int returnUserProfileTblPk;
        
    cpForm=(ChangePasswordForm)form;
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource= getDataSource(request,"BOKey");    
    
    try{
     logger.debug("***Cancel button pressed: "+isCancelled(request));
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      
      returnUserProfileTblPk=changePassword(cpForm,dataSource);
      if(returnUserProfileTblPk==-1){
        
        ActionMessage msg=new ActionMessage("msg.WrongOldPassword");
        messages.add("message1",msg);
        saveMessages(request,messages);
        
        logger.info("Exiting ChangePasswordActionS");
        return mapping.getInputForward();  
      }
      
    }catch(SQLException e){
      logger.debug("***"+e.getMessage());
      
    }catch(Exception e){
      logger.error("Exception Caught: "+e);
      logger.info("***Exiting ChangePasswordAction***");
      return mapping.getInputForward();
    }
    logger.info("***Exiting ChangePasswordAction***");
    return mapping.findForward("success");
  }

  public int changePassword(ChangePasswordForm cpForm,DataSource dataSource)throws SQLException,Exception{
    Connection conn=null;
    CallableStatement cs=null;
    int returnUserProfiloeTblPk;
    String userProfiloeTblPk;
    String sqlString=null;

    userProfiloeTblPk=Long.toString(cpForm.getHdnUserProfileTblPk());
    logger.debug("userProfileTblPk is: "+userProfiloeTblPk);
    sqlString="{?=call sp_change_password_user(?,?,?)}";
    
    try{
      conn=dataSource.getConnection();
      cs=conn.prepareCall(sqlString);
      
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,Long.toString(cpForm.getHdnUserProfileTblPk()) );
      cs.setString(3,cpForm.getTxtOldPassword());
      cs.setString(4,cpForm.getTxtNewPassword());

      cs.execute();
      returnUserProfiloeTblPk=cs.getInt(1);
      logger.debug("Returned UserProfileTblPk is: "+returnUserProfiloeTblPk);
      return returnUserProfiloeTblPk;
    }catch(SQLException e){
      logger.error("SqlException Caught: "+e);
      throw e;
    }catch(Exception e){
      logger.error("Exception Caught: "+e);
      throw e;
    }
    finally{

      if (cs != null) {
        try{
          cs.close();
        }
        catch(SQLException sqle){
          logger.error(sqle.getMessage());
        }
        cs =  null;
      }

      if (conn != null) {
        try{
          conn.close();
        }
        catch(SQLException sqle){
          logger.error(sqle.getMessage());
        }
        conn =  null;
      }
    }
  }
}