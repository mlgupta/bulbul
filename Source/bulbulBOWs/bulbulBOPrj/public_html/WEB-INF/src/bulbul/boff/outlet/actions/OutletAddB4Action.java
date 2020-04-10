package bulbul.boff.outlet.actions;

import bulbul.boff.general.BOConstants;

import bulbul.boff.outlet.actionforms.OutletForm;

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
 *	Purpose: To Reset The Value Of All The Form Elements.
 *  Info: This Class Will Set The Value Of All outletForm Elements To Null By Using There Setter Methods.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 OutletAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    OutletForm oForm =new OutletForm();
    logger.info("Entering OutletAddB4Action");
    oForm.setTxtOutletId("");
    oForm.setTxtOutletName("");
    oForm.setTxaOutletDesc("");
    oForm.setTxtAddress1("");
    oForm.setTxtAddress2("");
    oForm.setTxtAddress3("");
    oForm.setTxtCity("");
    oForm.setTxtPincode("");
    oForm.setCboState("");
    oForm.setCboCountry("");
    oForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    request.setAttribute(mapping.getAttribute(),oForm); 
    logger.info("Exiting OutletAddB4Action");
    return mapping.findForward("success");
  }
}