package bulbul.boff.login.actions;

import bulbul.boff.general.BOConstants;

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


public class LogoutAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     //Initialize logger
        Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
        logger.info("Entering : " + this.getClass().getName() + "." + "execute");
        
        HttpSession httpSession = null;        
        try{
            httpSession = request.getSession(false);            
            httpSession.invalidate();            
        }catch(Exception ex){
           logger.error(ex);
        }
        logger.info("Exitting : " + this.getClass().getName() + "." + "execute");
        return mapping.findForward("success");
    }
  }
