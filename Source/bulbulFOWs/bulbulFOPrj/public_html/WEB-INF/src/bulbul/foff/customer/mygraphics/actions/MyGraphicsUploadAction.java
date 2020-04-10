package bulbul.foff.customer.mygraphics.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.mygraphics.actionforms.MyGraphicsUploadForm;
import bulbul.foff.customer.mygraphics.beans.Operations;
import bulbul.foff.general.FOConstants;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletContext;
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

public class MyGraphicsUploadAction extends Action  {
  Logger logger= Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ServletContext context=null;
    DataSource dataSource=null;
    MyGraphicsUploadForm myGraphicsUploadForm=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    try{
      logger.info("Entering MyGraphicsUpload Action");
      
      if(isCancelled(request)){
        return mapping.findForward("success");
      }
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      myGraphicsUploadForm=(MyGraphicsUploadForm)form;
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"FOKey");
      Operations.uploadGraphics(customerEmailIdTblPk,myGraphicsUploadForm,dataSource);
    }catch(SQLException e){
      e.printStackTrace();
      logger.info(e.getMessage());
    }catch(Exception e){
      e.printStackTrace();
      logger.info(e.getMessage());
    }finally{
      logger.info("Exiting MyGraphicsUpload Action");
    }
    return mapping.findForward("success");
  }
  
  
}