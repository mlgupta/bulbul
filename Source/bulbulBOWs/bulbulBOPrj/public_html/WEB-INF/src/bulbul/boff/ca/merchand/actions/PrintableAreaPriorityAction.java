package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PrintableAreaForm;
import bulbul.boff.ca.merchand.actionforms.PrintableAreaPriorityForm;
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
import org.apache.struts.action.ActionMessages;

public class PrintableAreaPriorityAction extends Action {
  
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintableAreaPriorityForm printableAreaPriorityForm=null;
    ActionMessages messages=new ActionMessages();
    ServletContext context = null; 
    DataSource dataSource = null;
    try{
      logger.info("Entering PrintableAreaPriorityAction");
      printableAreaPriorityForm=(PrintableAreaPriorityForm)form;
      request.setAttribute("hdnSearchPageNo",printableAreaPriorityForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");
      Operations.prioritisePrintableArea(printableAreaPriorityForm,dataSource);
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in PrintableAreaPriorityAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PrintableAreaPriorityAction");
    }
    return mapping.findForward("success");
  }
}
