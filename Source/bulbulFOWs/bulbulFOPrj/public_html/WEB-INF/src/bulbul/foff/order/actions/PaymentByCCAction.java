package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.actionforms.PaymentByCCForm;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PaymentByCCAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString()) ;
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PaymentByCCForm pform=null;
    String paymentMode=null;
    try{
      logger.info("Entering PaymentByCCAction");
      pform = (PaymentByCCForm)form;
      paymentMode=Integer.toString( pform.getHdnPaymentMode());
      request.setAttribute("paymentMode",paymentMode);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting PaymentByCCAction");
    }
    return mapping.findForward("success");
  }
}