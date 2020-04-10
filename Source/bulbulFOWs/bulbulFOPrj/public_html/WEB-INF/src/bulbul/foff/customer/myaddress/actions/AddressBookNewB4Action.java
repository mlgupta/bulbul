package bulbul.foff.customer.myaddress.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myaddress.actionforms.ShippingAddressForm;
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

public class AddressBookNewB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ShippingAddressForm shippingAddressForm=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    
    try{
      logger.info("Entering AddressBookNewB4Action");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      shippingAddressForm=new ShippingAddressForm();
      
      shippingAddressForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
      shippingAddressForm.setTxtFullName("");
      shippingAddressForm.setTxtAddressLine1("");
      shippingAddressForm.setTxtAddressLine2("");
      shippingAddressForm.setTxtCity("");
      shippingAddressForm.setTxtState("");
      shippingAddressForm.setTxtCountry("");
      shippingAddressForm.setTxtPincode("");
      shippingAddressForm.setTxtPhone("");
      shippingAddressForm.setTxtEmailId("");
      
      request.setAttribute(mapping.getAttribute(),shippingAddressForm);
      
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      mapping.getInputForward();
    }finally{
      logger.info("Exiting AddressBookNewB4Action");
    }
    
    
    return mapping.findForward("success");
  }
}