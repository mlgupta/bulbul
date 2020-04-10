package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
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

public class OrderListAction extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource=null;
    HttpSession httpSession=null;
    int orderHeaderTblPk=-1;
    CustomerInfo customerInfo=null ;
    OrderHeaderBean currentOrder=null;
    try{
      logger.debug("Entering OrderListAction ");
      
      dataSource=getDataSource(request,"BOKey");
      
      httpSession=request.getSession(false);
      
      customerInfo = (CustomerInfo)httpSession.getAttribute("customerInfo");      
      
      currentOrder=customerInfo.getCurrentOrder();
      
      if (currentOrder!=null){
        orderHeaderTblPk=currentOrder.getOrderHeaderTblPk();
      }else{
        orderHeaderTblPk=-1;
      }
      
      currentOrder=Operations.listOrder(orderHeaderTblPk,dataSource);
      
      customerInfo.setCurrentOrder(currentOrder);      

    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.debug("Exiting OrderListAction ");
    }
    
    
    return mapping.findForward("success");
  }
}