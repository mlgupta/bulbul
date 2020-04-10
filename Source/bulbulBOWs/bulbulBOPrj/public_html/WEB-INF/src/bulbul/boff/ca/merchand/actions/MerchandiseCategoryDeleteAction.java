package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseListForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;
import bulbul.boff.general.Treeview;

import java.io.IOException;

import java.sql.SQLException;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class MerchandiseCategoryDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int merchandiseCategoryTblPk=-1;
    String merchandiseCategory=null;
    int deletedMerchandiseCategoryTblPk=-1;
    int parentMerchandiseCategoryTblPk=-1; 
    ActionMessages messages=new ActionMessages();
    MerchandiseListForm merchandiseListForm =(MerchandiseListForm)form;

    if (request.getParameter("radSearchSelect")!=null) {
      merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info(" Entering MerchandiseCategoryDeleteAction ");
    try{
      HttpSession httpSession=request.getSession(false);
      logger.debug("setting pk from MerchandiseListForm");
      parentMerchandiseCategoryTblPk=merchandiseListForm.getHdnSearchMerchandiseOrCategoryTblPk();
      deletedMerchandiseCategoryTblPk=Operations.deleteMerchandiseCategory(merchandiseCategoryTblPk,dataSource);  

      if (httpSession.getAttribute("merchandiseTreeView")!=null){
        Treeview merchandiseTreeView = (Treeview)httpSession.getAttribute("merchandiseTreeView");
        if(merchandiseTreeView!=null) {
          merchandiseTreeView.ifCategoryDeleted(Integer.toString(deletedMerchandiseCategoryTblPk),Integer.toString(parentMerchandiseCategoryTblPk)); 
        }
      }
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          merchandiseCategory=Operations.getMerchandiseCategory(merchandiseCategoryTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error getMerchandiseCategory"+ex);
        }
        
        ActionMessage msg=new ActionMessage("msg.Merchandise.Category.NotEmpty",merchandiseCategory);
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);  
      
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      logger.info("Exiting MerchandiseCategoryDeleteAction");
    }
    
    return mapping.findForward("success");
  }
 
}