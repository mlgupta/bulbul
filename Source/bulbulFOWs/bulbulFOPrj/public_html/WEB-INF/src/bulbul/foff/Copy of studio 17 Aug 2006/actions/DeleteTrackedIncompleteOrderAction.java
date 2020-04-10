package bulbul.foff.studio.actions;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

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

public class DeleteTrackedIncompleteOrderAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String orderHeaderTblPk=null;
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo  customerInfo=null;
    String orderCode=null;
    String orderGenDate=null;
    try{
      logger.info("Entering DeleteTrackedIncompleteOrderAction");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      orderHeaderTblPk=request.getParameter("orderHeaderTblPk");
      orderCode=request.getParameter("orderCode");
      orderGenDate=request.getParameter("orderGenDate");
      dataSource = getDataSource(request,"BOKey");
      Operations.deleteTrackedIncompleteOrder(orderHeaderTblPk,dataSource);  
      request.setAttribute("orderCode",orderCode);
      request.setAttribute("orderGenDate",orderGenDate);
      customerInfo.setCurrentOrder(null);
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting DeleteTrackedIncompleteOrderAction");
    }
    return mapping.findForward("success");
  }
}