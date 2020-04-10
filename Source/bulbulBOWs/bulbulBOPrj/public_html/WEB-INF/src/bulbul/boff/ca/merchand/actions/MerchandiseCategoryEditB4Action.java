package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class MerchandiseCategoryEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   logger.info("Entering MerchandiseCategoryEditB4Action");
   MerchandiseCategoryForm merchandiseCategoryForm =null;
   int merchandiseCategoryTblPk=-1;
   DataSource dataSource=null;
   ServletContext context=null;
   HttpSession httpSession=null;
   try{
    if(isCancelled(request)){
     return mapping.getInputForward();
    }
    
    if(request.getParameter("radSearchSelect")!=null){
      merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    context=servlet.getServletContext();
    httpSession=request.getSession(false);
    dataSource= getDataSource(request,"BOKey");
    merchandiseCategoryForm=Operations.viewMerchandiseCategory(merchandiseCategoryTblPk,dataSource);
    merchandiseCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    httpSession.setAttribute("SizeType",merchandiseCategoryForm.getArrSizeType()); 
    request.setAttribute(mapping.getAttribute(),merchandiseCategoryForm); 
   }catch(Exception e){
     logger.error("exception:"+e);
     return mapping.getInputForward();
   }
   logger.info("Exiting MerchandiseCategoryEditB4Action");
   return mapping.findForward("success");
  }
}