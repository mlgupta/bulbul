package bulbul.boff.outlet.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;
import bulbul.boff.outlet.actionforms.OutletForm;

import bulbul.boff.outlet.beans.Operations;

import java.io.IOException;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 *	Purpose: To Edit An Outlet Selected In outlet_list.jsp.
 *  Info: This Class Will Edit An Outlet From The List. It Uses editOutlet() Method and Passes It The 
 *        Instance Of The OutletForm Which Contains The Primary Key Of The Outlet Which
 *        Is To Be Edited.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 OutletEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    OutletForm oForm=null;
    ActionMessages messages=new ActionMessages();
    ServletContext context = null; 
    DataSource dataSource = null;
    try{
      logger.info("Entering OutletEditAction");
      oForm=(OutletForm)form;
      request.setAttribute("hdnSearchPageNo",oForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");
      Operations.editOutlet(oForm,dataSource);
    }catch(SQLException e){
      logger.error("Caught exception:" +e);
      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",oForm.getTxtOutletId());
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in OutletEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting OutletEditAction");
    }
    return mapping.findForward("success");
  }
}