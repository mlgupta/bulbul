package bulbul.foff.customer.mygraphics.actions;

import bulbul.foff.customer.mygraphics.actionforms.MyGraphicsModifyForm;
import bulbul.foff.customer.mygraphics.beans.Operations;
import bulbul.foff.general.FOConstants;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyGraphicsModifyAction extends Action  {
  Logger logger= Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MyGraphicsModifyForm myGraphicsModifyForm=null;
    ServletContext context = null; 
    DataSource dataSource = null;
    try{
      logger.info("Entering MyGraphicsModifyAction");
      myGraphicsModifyForm=(MyGraphicsModifyForm)form;
      
      
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      
      dataSource = getDataSource(request,"FOKey");
      Operations.modifyGraphics(myGraphicsModifyForm,dataSource);
    }catch(SQLException e){
      logger.error("Caught exception:" +e);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in OutletEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MyGraphicsModifyAction");
    }
    return mapping.findForward("success");
  }
  
  
}