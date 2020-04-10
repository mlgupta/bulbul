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
 *	Purpose: To Add A New Outlet In The outlet_list.jsp. 
 *  Info: This Class Will Add A New Outlet In The outlet_list.jsp.It Uses The addOutlet() Method Which 
 *        Takes The outletForm As One Of Its Parameter To Add A New Outlet. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class OutletAddAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
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
      logger.info("Entering OutletAddAction");
      oForm=(OutletForm)form;
      request.setAttribute("hdnSearchPageNo",oForm.getHdnSearchPageNo());
      if ( isCancelled(request) ){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      Operations.addOutlet(oForm,dataSource); 
    }catch(SQLException e){

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",oForm.getTxtOutletId());
        messages.add("message1" ,msg);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting OutletAddAction");
    }
    return mapping.findForward("success");
  }

}