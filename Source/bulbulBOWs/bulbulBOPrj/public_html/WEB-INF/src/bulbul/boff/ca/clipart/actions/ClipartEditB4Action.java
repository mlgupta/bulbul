package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartForm;
import bulbul.boff.ca.clipart.beans.Operations;
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


public class ClipartEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ClipartForm clipartForm =null;
    String clipartTblPk=null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering ClipartEditB4Action");
      if(isCancelled(request)){
       return mapping.getInputForward();
      }
      clipartTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      clipartForm=Operations.viewClipart(clipartTblPk,dataSource);
      clipartForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),clipartForm); 
    }catch(Exception e){
       logger.error("exception:"+e);
       return mapping.getInputForward();
    }finally{
      logger.info("***Exiting ClipartEditB4Action***");
    }
    return mapping.findForward("success");
  }
}