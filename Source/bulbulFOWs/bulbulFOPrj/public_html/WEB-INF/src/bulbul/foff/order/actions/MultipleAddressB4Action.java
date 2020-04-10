package bulbul.foff.order.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.order.beans.Operations;
import java.io.IOException;

import java.util.ArrayList;
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

public class MultipleAddressB4Action extends Action  {
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
    HttpSession  httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    ArrayList addresses=null;
    ActionMessages messages=null;
    ActionMessage msg=null;
    try{
      logger.info("Entering MultiAddressB4Action");
      messages=new ActionMessages();
      dataSource=getDataSource(request,"FOKey");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      addresses=Operations.getAddresses(dataSource,customerEmailIdTblPk);
      if (addresses.size()<=0){
        msg=new ActionMessage("msg.NoAddressInAddressBook");
        messages.add("messageNoAddressInAddressBook" ,msg);
        saveMessages(request,messages);
        return mapping.getInputForward();    
      }
      httpSession.setAttribute("addresses",addresses);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting MultiAddressB4Action");
    }
    return mapping.findForward("success");
  }
}