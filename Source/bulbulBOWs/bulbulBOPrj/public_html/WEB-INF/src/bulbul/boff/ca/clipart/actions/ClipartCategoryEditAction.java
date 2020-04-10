package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartCategoryForm;
import bulbul.boff.ca.clipart.beans.Operations;
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


public class ClipartCategoryEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ClipartCategoryForm clipartCategoryForm=null;
    int clipartCategoryTblPk=-1;
    int clipartCategoryTblFk=-1;
    ActionMessages messages=null;
    HttpSession httpSession=null; 
    ServletContext context=null;
    DataSource dataSource=null;
    try{
      logger.info("Entering ClipartCategoryEditAction");
      httpSession = request.getSession(false);
      messages=new ActionMessages();
      clipartCategoryForm=(ClipartCategoryForm)form;
      clipartCategoryTblFk=clipartCategoryForm.getHdnClipartCategoryTblFk(); 
      logger.debug("clipartCategoryTblFk: "+clipartCategoryTblFk);
      request.setAttribute("clipartCategoryTblPk",Integer.toString(clipartCategoryTblFk));
      request.setAttribute("hdnSearchPageNo",clipartCategoryForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");
      clipartCategoryTblPk=Operations.editClipartCategory(clipartCategoryForm,dataSource);
      if (httpSession.getAttribute("clipartTreeView")!=null){
        Treeview clipartTreeView = (Treeview)httpSession.getAttribute("clipartTreeView");
        if(clipartTreeView!=null) {
          clipartTreeView.ifCategoryEdited(Integer.toString(clipartCategoryTblPk)); 
        }
      }
    }catch(SQLException e){
     logger.error("Caught exception:" +e);
     if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",clipartCategoryForm.getTxtClipartCategory());
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in ClipartCategorytEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartCategoryEditAction");
    }
    return mapping.findForward("success");
  }
}