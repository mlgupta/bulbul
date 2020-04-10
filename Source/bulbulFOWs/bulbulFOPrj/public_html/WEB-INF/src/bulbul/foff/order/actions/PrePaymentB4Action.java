package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;

import bulbul.foff.order.beans.Operations;
import bulbul.foff.order.beans.OrderHeaderBean;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PrePaymentB4Action extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null; 
    OrderHeaderBean orderHeaderBean=null; 
    int orderHeaderTblPk=-1;
    try{
      logger.info("Entering PrePaymentB4Action");
      dataSource=getDataSource(request,"BOKey");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo"); 
      orderHeaderTblPk=customerInfo.getCurrentOrder().getOrderHeaderTblPk();
      orderHeaderBean=Operations.listOrderWishShippingAddress(orderHeaderTblPk,dataSource);
      customerInfo.setCurrentOrder(orderHeaderBean); 
      logger.debug(" Inside Try Is Multiple Address" + orderHeaderBean.getIsMultipleAddress()); 
    }catch(Exception e){
      logger.error(e);
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo); 
      logger.debug("In Side Finally Is Multiple Address" + ((CustomerInfo)httpSession.getAttribute("customerInfo")).getCurrentOrder().getIsMultipleAddress()); 
      logger.info("Exiting PrePaymentB4Action");
    }
    return mapping.findForward("success");
  }
}