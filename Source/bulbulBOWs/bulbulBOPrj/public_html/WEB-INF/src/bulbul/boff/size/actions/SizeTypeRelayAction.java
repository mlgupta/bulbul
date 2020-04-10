package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SizeTypeRelayAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  String operation= new String("null") ;
  operation=request.getParameter("hdnSearchOperation");
  logger.info("Entering RelayAction.. OPERATION:"+"***"+operation+"***");
  if (operation==null)
  {
    logger.info("***Exiting RelayAction ***");
    return mapping.getInputForward();
  }
  if (mapping.findForward(operation)==null)
  {
    logger.info("*** Exiting RelayAction ***");
    return mapping.getInputForward();
  }

  logger.info("***Exiting RelayAction***");
  return mapping.findForward(operation);
  
  }
}