package bulbul.foff.customer.myaddress.actions;

import bulbul.foff.customer.myaddress.actionforms.ShippingAddressForm;
import bulbul.foff.customer.myaddress.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddressBookModifyB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ShippingAddressForm shippingAddressForm =null;
    String customerAddressBookTblPk=null;
    DataSource dataSource=null;
    try{
      logger.info("Entering AddressBookModifyB4Action");
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      customerAddressBookTblPk=request.getParameter("customerAddressBookTblPk");
      dataSource= getDataSource(request,"FOKey");
      shippingAddressForm=Operations.viewAddressBook(customerAddressBookTblPk,dataSource);
      request.setAttribute(mapping.getAttribute(),shippingAddressForm); 
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting AddressBookModifyB4Action");
    }
   return mapping.findForward("success");
  }
}