package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.order.actionforms.MultipleAddressForm;
import bulbul.foff.order.beans.Operations;
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

public class MultipleAddressAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource = null; 
    MultipleAddressForm multipleAddressForm=null;
    int orderHeaderTblPk=-1;
    HttpSession  httpSession=null;
    CustomerInfo  customerInfo=null;
    try{
      logger.info("Entering Multiple Address Action");
      multipleAddressForm=(MultipleAddressForm)form;
      dataSource=getDataSource(request,"BOKey");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      orderHeaderTblPk=customerInfo.getCurrentOrder().getOrderHeaderTblPk();
      Operations.updateOrderWithMultipleAddress(dataSource,multipleAddressForm,orderHeaderTblPk);
    
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);      
    }finally{
      logger.info("Exiting Multiple Address Action");
    }
    return mapping.findForward("success");
  }
  
}