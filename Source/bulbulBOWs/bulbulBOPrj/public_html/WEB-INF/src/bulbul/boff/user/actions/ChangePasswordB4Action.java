package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.ChangePasswordForm;
import bulbul.boff.user.beans.UserProfile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ChangePasswordB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("***Entering ChangePasswordB4Action***");
    UserProfile userProfile=null;

    ChangePasswordForm cpForm=new ChangePasswordForm();
    userProfile=new UserProfile();

    HttpSession session=request.getSession(false);
    userProfile=(UserProfile)session.getAttribute("userProfile");
    logger.debug("***UserId in session is ***: "+userProfile.getUserId());
    
    try{
        logger.debug("***UserProfileTblPk is: "+userProfile.getUserProfileTblPk());
        
        cpForm.setHdnUserProfileTblPk(userProfile.getUserProfileTblPk());
        logger.debug("***HdnUserProfileTblPk is: "+cpForm.getHdnUserProfileTblPk());
        cpForm.setTxtOldPassword("");
        cpForm.setTxtNewPassword("");
        cpForm.setTxtConfirmPassword("");

    }catch(Exception e){
        logger.error("Exception Caught: "+e);
    }

    request.setAttribute(mapping.getAttribute(),cpForm);

    logger.info("***Exiting ChangePasswordB4Action***");
    return mapping.findForward("success");
  }
}