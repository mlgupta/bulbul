package bulbul.foff.studio.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ShowOrderAction extends Action  {
  public static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing. 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    String orderHeaderTblPk=null; 
    DataSource dataSource=null;
    ActionMessages messages=null;
    ActionMessage msg=null;
    OrderHeaderBean orderHeaderBean=null;
    try{
      logger.info("Entering ShowOrderAction");
      httpSession=request.getSession(false);
      dataSource=getDataSource(request,"BOKey");
      orderHeaderTblPk=request.getParameter("orderHeaderTblPk");
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      orderHeaderBean = new OrderHeaderBean();
      orderHeaderBean.setOrderHeaderTblPk(orderHeaderTblPk);
      customerInfo.setCurrentOrder(orderHeaderBean);
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting ShowOrderAction");
    }
    return mapping.findForward("success");
  }
}