package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.ca.merchand.beans.PrintableAreaDetailBean;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import java.util.ArrayList;

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


public class MerchandisePrintableAreaAddB4Action extends Action  {
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
    ArrayList printableAreaDetails=null;
    
    try {
      logger.info("Entering MerchandisePrintableAreaAddB4Action");
      
      merchandiseForm=(MerchandiseForm)form;
      
      ServletContext context = servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");
            
      printableAreaDetails=Operations.getSelectedPrintableArea(merchandiseForm,dataSource);
      request.setAttribute("printableAreaDetails",printableAreaDetails);
      request.setAttribute(mapping.getAttribute(),merchandiseForm);
    }
    catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }
    finally {
      logger.info("Exiting MerchandisePrintableAreaAddB4Action");
    }
    return mapping.findForward("success");
  }
}