package bulbul.boff.login.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.login.actionforms.LoginForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering LoginB4Action***");
    LoginForm loginForm=new LoginForm();
   
    loginForm.setTxtLoginId("");
    loginForm.setTxtLoginPassword("");

    request.setAttribute(mapping.getAttribute(),loginForm);
    logger.info("Exiting LoginB4Action***");
    return mapping.findForward("success");
  }
}