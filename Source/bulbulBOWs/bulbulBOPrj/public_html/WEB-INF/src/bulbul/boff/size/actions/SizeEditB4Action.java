package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeForm;
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


public class SizeEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    SizeForm sizeForm =null;
    int sizeTblPk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    if(isCancelled(request)){
     return mapping.getInputForward();
    }
    try{
      logger.info("Entering SizeEditB4Action");
      if(request.getParameter("radSearchSelect")!=null){
        sizeTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      sizeForm=Operations.viewSize(sizeTblPk,dataSource);
      sizeForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      sizeForm.setHdnSizeTypePageNo(request.getParameter("hdnSizeTypePageNo"));
      request.setAttribute(mapping.getAttribute(),sizeForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting SizeEditB4Action");
    }
   return mapping.findForward("success");
  }
}