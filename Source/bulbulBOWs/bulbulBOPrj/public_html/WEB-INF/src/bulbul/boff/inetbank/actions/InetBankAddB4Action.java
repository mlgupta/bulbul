package bulbul.boff.inetbank.actions;

import bulbul.boff.general.BOConstants;

import bulbul.boff.inetbank.actionforms.InetBankForm;

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
 *	Purpose: To Reset The Value Of All The InetBankForm Elements.
 *  Info: This Class Will Set The Value Of All InetBankForm Elements To Null By Using There Setter Methods.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering InetBanktAddB4Action");
    InetBankForm inetForm =new InetBankForm();
    inetForm.setTxtBankName("");
    inetForm.setTxtBankAcNo("");
    inetForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    request.setAttribute(mapping.getAttribute(),inetForm); 
    logger.info("Exiting InetBanktAddB4Action");
    return mapping.findForward("success");
  }
}