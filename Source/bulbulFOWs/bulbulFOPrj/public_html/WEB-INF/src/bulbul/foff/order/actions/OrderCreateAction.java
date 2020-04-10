package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.order.actionforms.OrderItemForm;
import bulbul.foff.order.beans.Operations;
import bulbul.foff.order.beans.OrderHeaderBean;
import bulbul.foff.order.beans.PreOrderBean;

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

public class OrderCreateAction extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    OrderItemForm orderItemForm=null;
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int orderHeaderTblPk=-1;
    OrderHeaderBean currentOrder=null;
    try{
      logger.info("Entering OrderCreateAction");
      httpSession=request.getSession(false);
      customerInfo = (CustomerInfo)httpSession.getAttribute("customerInfo");
      
      if(isCancelled(request)){
        return mapping.findForward("success");
      }
      
      orderItemForm=(OrderItemForm)form;
      logger.debug("hdnCustomerDesignTblPk : "+orderItemForm.getHdnCustomerDesignTblPk());
      logger.debug("Quantity : "+orderItemForm.getTxtQty());
      dataSource=getDataSource(request,"BOKey");
      logger.debug("emailId: "+customerInfo.getCustomerEmailId());
      currentOrder=customerInfo.getCurrentOrder();
      if((currentOrder!=null)){
        orderHeaderTblPk=currentOrder.getOrderHeaderTblPk();  
      }else{
        orderHeaderTblPk=-1;
        currentOrder=new OrderHeaderBean();
      }
      
      orderHeaderTblPk=Operations.createOrder(orderHeaderTblPk,orderItemForm,dataSource);
      logger.debug("orderHeaderTblPk : "+ orderHeaderTblPk);
      currentOrder.setOrderHeaderTblPk(orderHeaderTblPk);
      customerInfo.setCurrentOrder(currentOrder);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting OrderCreateAction");
    }
    
    return mapping.findForward("success");
  }
}