package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseListForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;

import java.io.IOException;

import java.sql.SQLException;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class MerchandiseActDeactAction extends Action  {
  Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseListForm merchandiseListForm=null; 
    int merchandiseTblPk=-1;
    int merchandiseCategoryTblPk=-1;
    ActionMessages messages=new ActionMessages();

    merchandiseListForm=(MerchandiseListForm)form;
    
    if (request.getParameter("radSearchSelect")!=null) {
      merchandiseTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    if (request.getParameter("hdnSearchMerchandiseOrCategoryTblPk")!=null) {
      merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("hdnSearchMerchandiseOrCategoryTblPk"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("***Entering MerchandiseActDeactAction***");
    try{
      Operations.actDeactMerchandise(merchandiseTblPk,merchandiseCategoryTblPk,merchandiseListForm,dataSource);  
    }catch(SQLException e){
      if((e.getMessage().indexOf(ErrorConstants.PRINTABLE_AREA_NOT_DEFINED.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Merchandise.PrintableArea.NotDefined");
        messages.add("message1" ,msg);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
      
    }catch(Exception e){
      logger.error("Caught Exception: "+e);
    }
    logger.info("***Exiting MerchandiseActDeactAction***");
    return mapping.findForward("success");
  }

}