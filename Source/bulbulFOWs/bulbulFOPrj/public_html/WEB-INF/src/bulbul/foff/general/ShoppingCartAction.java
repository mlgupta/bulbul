package bulbul.foff.general;

import bulbul.foff.customer.beans.CustomerInfo;
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

public class ShoppingCartAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing. 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session=null;
    CustomerInfo customerInfo=null;
    
    try{
      logger.info("Entering ShoppingCartAction");
      session=request.getSession(false);
      customerInfo=(CustomerInfo)session.getAttribute("customerInfo");
      if(customerInfo.getCustomerEmailIdTblPk()==-1){
        return mapping.getInputForward();
      }
       
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting ShoppingCartAction");
    }
    return mapping.findForward("success");
  }
}