package bulbul.boff.outlet.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.outlet.actionforms.OutletForm;

import bulbul.boff.outlet.beans.Operations;

import java.io.IOException;

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


/**
 *	Purpose: To Populate The OutletForm Before Editing.
 *  Info: This Class Will Retrieve The Outlet From The Database And Populates The OutletForm With The 
 *        Retrieved Values.It Uses viewOutlet() Method To Populate The OutletForm.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

OutletEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    OutletForm oForm =null;
    String outletTblPk=null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering OutletEditB4Action");
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      outletTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      oForm=Operations.viewOutlet(outletTblPk,dataSource);
      oForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),oForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting OutletEditB4Action");
    }
   return mapping.findForward("success");
  }
}