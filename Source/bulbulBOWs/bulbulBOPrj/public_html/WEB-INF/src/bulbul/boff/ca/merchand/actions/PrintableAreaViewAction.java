package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PrintableAreaForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;

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


public class PrintableAreaViewAction extends Action  {
  
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintableAreaForm printableAreaForm=null;
    int printableAreaTblPk=-1;
    ServletContext context=null;
    DataSource dataSource=null;  
    try{
      logger.info("Entering PrintableAreaViewAction***");
      printableAreaForm=(PrintableAreaForm)form;
      request.setAttribute("hdnSearchPageNo",printableAreaForm.getHdnSearchPageNo());
      if(isCancelled(request)){
       return mapping.getInputForward();
      }
      if (request.getParameter("radSearchSelect")!=null) {
        printableAreaTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      printableAreaForm=Operations.viewPrintableArea(printableAreaTblPk,dataSource);
      printableAreaForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),printableAreaForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PrintableAreaViewAction***");
    }
    return mapping.findForward("success");    
  }
}