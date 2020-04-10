package bulbul.foff.customer.myaddress.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myaddress.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.io.IOException;

import java.sql.SQLException;
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

public class ShippingAddressBookB4Action extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList addresses=null;
    int numberOfRecords=6;
    int pageNumber=1;
    String pageCount="1";
    int customerEmailIdTblPk=-1;
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    
    
    try{
      logger.info("Entering ShippingAddressBookB4Action");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      dataSource= getDataSource(request,"FOKey");
      logger.info("Getting Data Source in ProductListAction");
      
      if (request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
            
      addresses=Operations.getAddresses(customerEmailIdTblPk,dataSource,numberOfRecords,pageNumber);
      pageCount=Operations.pageCountString;
      request.setAttribute("addresses", addresses);
      request.setAttribute("pageNumber", Integer.toString(pageNumber));
      request.setAttribute("pageCount", pageCount);

    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ShippingAddressBookB4Action");
    }
    return mapping.findForward("success");
  }
  
}