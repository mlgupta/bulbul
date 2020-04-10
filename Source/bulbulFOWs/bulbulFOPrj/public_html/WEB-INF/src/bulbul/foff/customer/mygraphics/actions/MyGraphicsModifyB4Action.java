package bulbul.foff.customer.mygraphics.actions;

import bulbul.foff.customer.mygraphics.actionforms.MyGraphicsModifyForm;
import bulbul.foff.customer.mygraphics.beans.Operations;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyGraphicsModifyB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MyGraphicsModifyForm myGraphicsModifyForm =null;
    int customerGraphicsTblPk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering MyGraphicsModifyB4Action");

      customerGraphicsTblPk=Integer.parseInt(request.getParameter("customerGraphicsTblPk"));
      logger.debug("customerGraphicsTblPk :"+customerGraphicsTblPk);
      context=servlet.getServletContext();

      dataSource = getDataSource(request,"FOKey");
      myGraphicsModifyForm=Operations.getGraphicsData(customerGraphicsTblPk,dataSource);
      logger.info("format : "+ (String)request.getAttribute("format"));

      request.setAttribute(mapping.getAttribute(),myGraphicsModifyForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MyGraphicsModifyB4Action");
    }
   return mapping.findForward("success");
  }
  
}