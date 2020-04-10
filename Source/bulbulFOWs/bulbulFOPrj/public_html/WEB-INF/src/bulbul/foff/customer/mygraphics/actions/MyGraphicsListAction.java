package bulbul.foff.customer.mygraphics.actions;

import bulbul.foff.common.ImageFormat;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.mygraphics.beans.Operations;
import bulbul.foff.general.FOConstants;

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
public class MyGraphicsListAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList graphics=null;
    int numberOfRecords=6;
    int pageNumber=1;
    String pageCount="1";
    String format=ImageFormat.ALL.toString();
    DataSource dataSource= null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    try{
    
      logger.info("Entering GraphicsListAction");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      if (request.getParameter("format")!=null){
        format=request.getParameter("format");
        logger.debug("format : "+request.getParameter("format"));
      }
      
      if (request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
      
      dataSource= getDataSource(request,"FOKey");
      graphics=Operations.getGraphics(customerEmailIdTblPk,dataSource,numberOfRecords,format,pageNumber);
      pageCount=Operations.pageCountString;
      request.setAttribute("graphics", graphics);
      request.setAttribute("pageNumber", Integer.toString(pageNumber));
      request.setAttribute("pageCount", pageCount);
      request.setAttribute("format", format);
      
    }
    catch(Exception e){
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting GraphicsListAction");
    }
    return mapping.findForward("success");
  }
}