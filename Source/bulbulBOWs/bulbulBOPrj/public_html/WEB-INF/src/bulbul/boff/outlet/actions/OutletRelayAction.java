package bulbul.boff.outlet.actions;

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

/**
 *	Purpose: It looks for an "operation" parameter and uses it to find a forward
 *           in the mapping and then uses it.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

OutletRelayAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  String operation= new String("null") ;
  operation=request.getParameter("hdnSearchOperation");
  logger.info("Entering RelayAction.. OPERATION:"+"***"+operation+"***");
  if (operation==null)
  {
     return mapping.getInputForward();
  }
  if (mapping.findForward(operation)==null)
  {
    return mapping.getInputForward();
  }

  logger.info("***Exiting RelayAction***");
  return mapping.findForward(operation);
  
  }
}