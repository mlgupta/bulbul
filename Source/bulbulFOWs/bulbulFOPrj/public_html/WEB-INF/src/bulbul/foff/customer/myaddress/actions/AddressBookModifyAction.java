package bulbul.foff.customer.myaddress.actions;

import bulbul.foff.customer.myaddress.actionforms.ShippingAddressForm;
import bulbul.foff.customer.myaddress.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddressBookModifyAction extends Action  {
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
    DataSource dataSource=null;
    try{
      logger.info("Entering AddressBookModifyAction");
      shippingAddressForm=(ShippingAddressForm)form;
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      dataSource = getDataSource(request,"FOKey");
      Operations.modifyAddressBook(shippingAddressForm,dataSource);
    }catch(SQLException e){
      logger.error("Caught exception:" +e);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting AddressBookModifyAction");
    }
    return mapping.findForward("success");
  }
}