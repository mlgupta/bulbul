package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartCategoryForm;
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


public class ClipartCategoryViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ClipartCategoryForm clipartCategoryForm =null;
    int clipartCategoryTblPk=-1;
    int clipartCategoryTblFk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering ClipartCategoryViewAction");
      clipartCategoryForm=(ClipartCategoryForm)form;
      clipartCategoryTblFk=clipartCategoryForm.getHdnClipartCategoryTblFk();         
      request.setAttribute("clipartCategoryTblPk",Integer.toString(clipartCategoryTblFk));
      request.setAttribute("hdnSearchPageNo",clipartCategoryForm.getHdnSearchPageNo());
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      
      if(request.getParameter("radSearchSelect")!=null){
        clipartCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      clipartCategoryForm=Operations.viewClipartCategory(clipartCategoryTblPk,dataSource);
      clipartCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),clipartCategoryForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartCategoryViewAction");
    }
    return mapping.findForward("success");
  }
}