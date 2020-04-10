package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
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


public class MerchandisePrintableAreaAddAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseForm merchandiseForm=null;
    String merchandiseCategoryTblPk=null;
    try {
      logger.info("Entering MerchandisePrintableAreaAddAction");
      
      merchandiseForm=(MerchandiseForm)form;
      logger.debug("***getHdnMerchandiseCategoryTblPk(): "+merchandiseForm.getHdnMerchandiseCategoryTblPk());
      
      merchandiseCategoryTblPk=Long.toString(merchandiseForm.getHdnMerchandiseCategoryTblPk());
      if(merchandiseCategoryTblPk!=null){
        request.setAttribute("merchandiseCategoryTblPk",merchandiseCategoryTblPk);
      }
      request.setAttribute("hdnSearchPageNo",merchandiseForm.getHdnSearchPageNo());
      if ( isCancelled(request) ){
        return mapping.findForward("success");
      }
      
      
      ServletContext context = servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");
            
      Operations.addPrintableAreaDetals(merchandiseForm,dataSource);
      
      request.setAttribute(mapping.getAttribute(),merchandiseForm);
    }
    catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }
    finally {
      logger.info("Exiting MerchandisePrintableAreaAddAction");
    }
    return mapping.findForward("success");
  }
}