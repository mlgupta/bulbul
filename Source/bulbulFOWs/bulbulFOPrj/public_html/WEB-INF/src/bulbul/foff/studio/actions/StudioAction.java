package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;

public class StudioAction extends Action  {
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
    String studioFrameSrc=null;
    try{
      logger.info("Entering Studio Action");
      
      studioFrameSrc=request.getParameter("studioFrameSrc");
      request.setAttribute("studioFrameSrc",studioFrameSrc);
      logger.debug("studioFrameSrc : " + studioFrameSrc);
     
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting Studio Action");
    }
    return mapping.findForward("success");
  }
}