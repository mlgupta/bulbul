package bulbul.boff.inetbank.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;
import bulbul.boff.inetbank.actionforms.InetBankForm;

import bulbul.boff.inetbank.beans.Operations;

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
 *	Purpose: To Edit An Internet Bank Selected In inet_bank_list.jsp.
 *  Info: This Class Will Edit An Internet Bank From The List. It Uses editInetBank() Method and Passes It The 
 *        Instance Of The InetBankForm Which Contains The Primary Key Of The Internet Bank Which
 *        Is To Be Edited.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    InetBankForm inetForm=null;
    ActionMessages messages=new ActionMessages();
    ServletContext context = null;
    DataSource dataSource = null;
    try{
      logger.info("Entering InetBankEditAction");
      inetForm=(InetBankForm)form;
      request.setAttribute("hdnSearchPageNo",inetForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");

      Operations.editInetBank(inetForm,dataSource);
    }catch(SQLException e){
      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",inetForm.getTxtBankName());
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      logger.error("SqlException Caught: "+e);
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting InetBankEditAction");
    }
    return mapping.findForward("success");
  }
}