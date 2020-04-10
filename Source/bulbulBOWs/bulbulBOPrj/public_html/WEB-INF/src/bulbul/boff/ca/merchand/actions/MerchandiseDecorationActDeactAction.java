package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseDecorationListForm;
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


public class MerchandiseDecorationActDeactAction extends Action  {
  Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseDecorationListForm merchandiseDecorationListForm=null; 
    int merchandiseDecorationTblPk=-1;
    try{
      logger.info("***Entering MerchandiseDecorationActDeactAction***");
      merchandiseDecorationListForm=(MerchandiseDecorationListForm)form;
      if (request.getParameter("radSearchSelect")!=null) {
        merchandiseDecorationTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      ServletContext context = servlet.getServletContext();
      DataSource dataSource = getDataSource(request,"BOKey");
      Operations.actDeactDecoration(merchandiseDecorationTblPk,merchandiseDecorationListForm,dataSource);  
    }catch(Exception e){
      logger.error("Caught Exception: "+e);
    }finally{
      logger.info("***Exiting MerchandiseDecorationActDeactAction***");
    }
    
    return mapping.findForward("success");
  }

}