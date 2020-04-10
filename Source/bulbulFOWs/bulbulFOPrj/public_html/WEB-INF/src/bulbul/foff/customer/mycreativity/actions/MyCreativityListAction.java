package bulbul.foff.customer.mycreativity.actions;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.mycreativity.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;

public class MyCreativityListAction extends Action  {
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
    ArrayList designs=null;
    int numberOfRecords=6;
    int pageNumber=1;
    String pageCount="1";
    DataSource dataSource= null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    try{
    
      logger.info("Entering MyCreativityListAction");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      if (request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
      
      dataSource= getDataSource(request,"FOKey");
      designs=Operations.getDesigns(customerEmailIdTblPk,dataSource,numberOfRecords,pageNumber);
      pageCount=Operations.pageCountString;
      request.setAttribute("designs", designs);
      request.setAttribute("pageNumber", Integer.toString(pageNumber));
      request.setAttribute("pageCount", pageCount);
    }
    catch(Exception e){
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MyCreativityListAction");
    }
    return mapping.findForward("success");
  }
  
}