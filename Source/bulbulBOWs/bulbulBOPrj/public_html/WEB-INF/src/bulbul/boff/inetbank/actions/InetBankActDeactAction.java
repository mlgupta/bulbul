package bulbul.boff.inetbank.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.inetbank.actionforms.InetBankListForm;

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
 *	Purpose: To Activate Or Deactivate The Selected Bank In The inet_bank_list.jsp. 
 *  Info: This Class Will Take The Primary Key Of The Selected InetBAnk Record And Pass It To The actDeactInetBnak() Method
 *        Which Activates Or Deactivates That Record.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankActDeactAction extends Action  {
  Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    InetBankListForm inetListForm=null; 
    int inetBankTblPk=-1;

    inetListForm=(InetBankListForm)form;
    if(request.getParameter("radSearchSelect")!=null){
      inetBankTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("***Entering InetBankActDeactAction***");
    try{
      Operations.actDeactInetBank(inetBankTblPk,inetListForm,dataSource);  
    }catch(Exception e){
      logger.error("Caught Exception: "+e);
    }
    
    return mapping.findForward("success");
  }

}