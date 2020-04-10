package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeTypeForm;
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


public class SizeTypeViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    SizeTypeForm sizeTypeForm =null;
    int sizeTypeTblPk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering SizeTypeViewAction");
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      if(request.getParameter("radSearchSelect")!=null){
        sizeTypeTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      sizeTypeForm=Operations.viewSizeType(sizeTypeTblPk,dataSource);
      sizeTypeForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),sizeTypeForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting SizeTypeViewAction");
    }
    return mapping.findForward("success");
  }
}