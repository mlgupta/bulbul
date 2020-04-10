package bulbul.boff.inetbank.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.inetbank.actionforms.InetBankForm;

import bulbul.boff.inetbank.beans.Operations;

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
 *	Purpose: To Populate The InetBankForm Before Editing.
 *  Info: This Class Will Retrieve The Internet Bank From The Database And Populates The InetBankForm With The 
 *        Retrieved Values.It Uses viewInetBank() Method To Populate The InetBankForm.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class


InetBankEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Inside InetBankEditB4Ation");
    InetBankForm inetForm =null;
    String inetBankTblPk=null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      if(isCancelled(request)){
       return mapping.getInputForward();
      }
      inetBankTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      inetForm=Operations.viewInetBank(inetBankTblPk,dataSource);
      inetForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),inetForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }
   return mapping.findForward("success");
  }
}