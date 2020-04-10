package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.PaymentMode;
import bulbul.foff.order.beans.Operations;
import bulbul.foff.order.beans.OrderHeaderBean;

import java.io.IOException;

import java.sql.SQLException;

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

public class PostPaymentB4Action extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    OrderHeaderBean orderHeaderBean=null;
    DataSource dataSource=null;
    int orderHeaderTblPk=-1;
    int paymentMode=-1;
    int outletTblPk=-1;
    int inetBankingTblPk=-1;
    try{
      logger.info("Entering PostPaymentB4Action");
      dataSource=getDataSource(request,"BOKey");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      orderHeaderBean=customerInfo.getCurrentOrder();
      paymentMode=Integer.parseInt((String)request.getAttribute("paymentMode"));
      if(request.getAttribute("outletTblPk")!=null){
        outletTblPk=Integer.parseInt((String)request.getAttribute("outletTblPk"));
      }
      if(request.getAttribute("inetBankingTblPk")!=null){
        inetBankingTblPk=Integer.parseInt((String)request.getAttribute("inetBankingTblPk"));
      }
      
      orderHeaderTblPk=orderHeaderBean.getOrderHeaderTblPk();
      Operations.completeOrder(dataSource,orderHeaderTblPk,paymentMode,outletTblPk,inetBankingTblPk);
      request.setAttribute("orderHeaderBean",orderHeaderBean);
      request.setAttribute("paymentMode",PaymentMode.getValue(paymentMode));
      customerInfo.setCurrentOrder(null);
    }catch(SQLException e){
      logger.error(e);   
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting PostPaymentB4Action");
      httpSession.setAttribute("customerInfo",customerInfo);
    }
    return mapping.findForward("success");
  }
  
}