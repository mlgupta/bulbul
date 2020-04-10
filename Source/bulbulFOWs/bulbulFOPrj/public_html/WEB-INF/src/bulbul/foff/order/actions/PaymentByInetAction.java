package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.actionforms.PaymentByDCForm;
import bulbul.foff.order.actionforms.PaymentByInetForm;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PaymentByInetAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString()) ; 
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PaymentByInetForm pform=null;
    String paymentMode=null;
    String inetBankingTblPk=null;
    try{
      logger.info("Entering PaymentByInetAction");
      pform = (PaymentByInetForm)form;
      paymentMode=Integer.toString( pform.getHdnPaymentMode());
      inetBankingTblPk=pform.getLstInetBank();
      request.setAttribute("paymentMode",paymentMode);
      request.setAttribute("inetBankingTblPk",inetBankingTblPk);
      
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting PaymentByInetAction");
    }
    return mapping.findForward("success");
  }
}