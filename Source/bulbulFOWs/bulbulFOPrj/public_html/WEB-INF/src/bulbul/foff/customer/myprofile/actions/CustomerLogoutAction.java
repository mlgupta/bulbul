package bulbul.foff.customer.myprofile.actions;
import bulbul.foff.general.FOConstants;
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

public class CustomerLogoutAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession = null;
    try{
      logger.info("Entering Customer Logout Action");
      httpSession = request.getSession(false);
      httpSession.invalidate();
      httpSession =request.getSession(true);
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e);
    }finally{
      logger.info("Exiting Customer Logout Action");
    }
    return mapping.findForward("success");
  }
}