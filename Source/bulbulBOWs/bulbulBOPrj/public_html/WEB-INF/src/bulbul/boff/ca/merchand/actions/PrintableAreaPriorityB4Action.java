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

public class PrintableAreaPriorityB4Action extends Action{
  
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      
    PrintableAreaPriorityForm printableAreaPriorityForm =null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering PrintableAreaPriorityB4Action");
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");

      printableAreaPriorityForm= new PrintableAreaPriorityForm();
      printableAreaPriorityForm = Operations.getPrintableArea4Priority(printableAreaPriorityForm,dataSource);
      printableAreaPriorityForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),printableAreaPriorityForm); 
      
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PrintableAreaPriorityB4Action");
    }
    return mapping.findForward("success");
  }
}
