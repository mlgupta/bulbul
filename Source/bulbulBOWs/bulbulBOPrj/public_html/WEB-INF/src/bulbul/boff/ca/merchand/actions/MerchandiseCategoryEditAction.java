package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryForm;
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


public class MerchandiseCategoryEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   MerchandiseCategoryForm merchandiseCategoryForm=null;
   int merchandiseCategoryTblPk=-1;
   String merchandiseCategoryTblFk=null;
   ActionMessages messages=new ActionMessages();

   logger.info("Entering MerchandiseCategoryEditAction");
    try{
      HttpSession httpSession = request.getSession(false);
      merchandiseCategoryForm=(MerchandiseCategoryForm)form;
      merchandiseCategoryTblFk=Long.toString(merchandiseCategoryForm.getHdnMerchandiseCategoryTblFk()); 
      logger.debug("merchandiseCategoryTblFk: "+merchandiseCategoryTblFk);
      if(merchandiseCategoryTblFk!=null){
        if(merchandiseCategoryTblFk.equals("0")){
          merchandiseCategoryTblFk="-1";
        }
        request.setAttribute("merchandiseCategoryTblPk",merchandiseCategoryTblFk);
      }
      request.setAttribute("hdnSearchPageNo",merchandiseCategoryForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
     
      ServletContext context = servlet.getServletContext();
      DataSource dataSource = getDataSource(request,"BOKey");

      merchandiseCategoryTblPk=Operations.editMerchandiseCategory(merchandiseCategoryForm,dataSource);
      if (httpSession.getAttribute("merchandiseTreeView")!=null){
        Treeview merchandiseTreeView = (Treeview)httpSession.getAttribute("merchandiseTreeView");
        if(merchandiseTreeView!=null) {
          merchandiseTreeView.ifCategoryEdited(Integer.toString(merchandiseCategoryTblPk)); 
        }
      }
      httpSession.removeAttribute("SizeType"); 
    }catch(SQLException e){
     logger.error("Caught exception:" +e);
     if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",merchandiseCategoryForm.getTxtMerchandiseCategory());
        messages.add("message1" ,msg);
        //saveMessages(request,messages);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in MerchandiseCategorytEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MerchandiseCategoryEditAction");  
    }
    return mapping.findForward("success");
  }
}