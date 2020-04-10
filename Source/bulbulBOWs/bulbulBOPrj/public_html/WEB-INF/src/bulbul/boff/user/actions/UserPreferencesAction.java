package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.UserPreferencesForm;
import bulbul.boff.user.beans.Operations;
import bulbul.boff.user.beans.UserProfile;

import java.io.IOException;

import java.sql.SQLException;

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
import org.apache.struts.action.ActionMessages;


public class UserPreferencesAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   UserPreferencesForm userPreferencesForm=null;
   ActionMessages messages=new ActionMessages();
   UserProfile userProfile=null;

   logger.info("Entering UserPreferencesAction");
   try{
    if (isCancelled(request)){
      return mapping.findForward("success");
    }
    userPreferencesForm=(UserPreferencesForm)form;
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");

    Operations.editUserPreferences(userPreferencesForm,dataSource);
    
    //  Saving the changed preferences in this session
    HttpSession httpSession =request.getSession(false);
    userProfile=(UserProfile)httpSession.getAttribute("userProfile");
    userProfile.setNumberOfRecords(userPreferencesForm.getTxtNumberOfRecords());
    //httpSession.setAttribute("userProfile",userProfile);
    
   }catch(SQLException e){
     logger.error("Caught exception:" +e);
     /*if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",userForm.getTxtUserId());
        messages.add("message1" ,msg);
        //saveMessages(request,messages);
      }*/
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in UserPreferencesAction: "+e.getMessage());
      return mapping.getInputForward();
    }
    logger.info("Exiting UserPreferencesAction");
    return mapping.findForward("success");
  }
}