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
 *	Purpose: To View An Internet Bank Selected In inet_bank_list.jsp.
 *  Info: This Class Will Use The viewInetBank() Method To Populate The inet_bank_view.jsp With The Specified Data. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 InetBankViewAction extends Action  {
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
    String inetBankTblPk=null;
    ServletContext context=null;
    DataSource dataSource=null;  
    try{   
      logger.info("Entering InetBankViewAction");
      inetForm=(InetBankForm)form;
      request.setAttribute("hdnSearchPageNo",inetForm.getHdnSearchPageNo());
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
    }finally{
      logger.info("Exiting InetBankViewAction");
    }
    return mapping.findForward("success");    
  }
}