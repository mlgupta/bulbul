package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseDecorationForm;
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


public class MerchandiseDecorationViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseDecorationForm merchandiseDecorationForm=null;
    int merchandiseDecorationTblPk=-1;
    ServletContext context=null;
    DataSource dataSource=null;  
    try{   
      logger.info("Entering MerchandiseDecorationViewAction");
      merchandiseDecorationForm=(MerchandiseDecorationForm)form;
      request.setAttribute("hdnSearchPageNo",merchandiseDecorationForm.getHdnSearchPageNo());
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      if (request.getParameter("radSearchSelect")!=null) {
        merchandiseDecorationTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      merchandiseDecorationForm=Operations.viewDecoration(merchandiseDecorationTblPk,dataSource);
      merchandiseDecorationForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),merchandiseDecorationForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MerchandiseDecorationViewAction");
    }
    return mapping.findForward("success");    
  }
}