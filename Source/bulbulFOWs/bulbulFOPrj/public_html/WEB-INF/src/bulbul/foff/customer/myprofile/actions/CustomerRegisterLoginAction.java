package bulbul.foff.customer.myprofile.actions;
import bulbul.foff.general.FOConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerRegisterLoginAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String operation  = null;
    try{
      logger.info("Entering  Customer Register Login Action");
      operation=request.getParameter("operation");
      request.setAttribute("operation",operation);
      
    }catch(Exception e){
      logger.debug(e.getMessage());
    }finally{
      logger.info("Exiting  Customer Register Login Action");
    }
    return mapping.findForward("success");
  }
}