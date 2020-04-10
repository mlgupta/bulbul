package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeListForm;
import bulbul.boff.size.beans.Operations;

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


public class SizeActDeactAction extends Action  {
  Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    SizeListForm sizeListForm=null; 
    int sizeTblPk=-1;

    sizeListForm=(SizeListForm)form;
    if(request.getParameter("radSearchSelect")!=null){
      sizeTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("Entering SizeActDeactAction");
    try{
      Operations.actDeactSize(sizeTblPk,sizeListForm,dataSource);  
    }catch(Exception e){
      logger.error("Caught Exception: "+e);
    }
    logger.info("Exiting SizeActDeactAction");
    return mapping.findForward("success");
  }

}