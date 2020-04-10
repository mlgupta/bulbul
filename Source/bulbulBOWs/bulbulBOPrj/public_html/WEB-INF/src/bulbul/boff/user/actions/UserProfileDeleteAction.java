package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;

import bulbul.boff.user.beans.Operations;

import java.io.IOException;

import java.sql.SQLException;

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


/**
 *	Purpose: To Delete A User Profile From The user_profile_list.jsp.
 *  Info: This Class Will Delete The User Profile From The user_profile_list.jsp. It Uses deleteInetBank() Method and Passes It The 
 *        Primary Key Of The Selected User Profile Which Is To Be Deleted.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

UserProfileDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int userProfileTblPk=-1;
    String userId=null;
    ActionMessages messages=new ActionMessages();

    if(request.getParameter("radSearchSelect")!=null){
      userProfileTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("Entering UserProfileDeleteAction");
    try{

      Operations.deleteUserProfile(userProfileTblPk,dataSource);  
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          userId=Operations.getUserId(userProfileTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error in getUserId()"+ex);
        }
        
        ActionMessage msg=new ActionMessage("msg.Refered",userId);
        messages.add("message1" ,msg);
        //saveMessages(request,messages);
      }
      saveMessages(request,messages);  
      
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      logger.info("Exiting UserProfileDeleteAction");
    }
    
    return mapping.findForward("success");
  }
 
}