package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartListForm;
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


public class ClipartCategoryDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int clipartCategoryTblPk=-1;
    String clipartCategory=null;
    HttpSession httpSession=null;
    int deletedClipartCategoryTblPk=-1;
    int parentClipartCategoryTblPk=-1; 
    ActionMessages messages=null;
    ClipartListForm clipartListForm=null;
    ServletContext context = null;
    DataSource dataSource = null;
    try{
      logger.info("Entering ClipartCategoryDeleteAction");
      messages=new ActionMessages();
      httpSession=request.getSession(false);
      clipartListForm =(ClipartListForm)form;
      if(request.getParameter("radSearchSelect")!=null){
        clipartCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");
      parentClipartCategoryTblPk=clipartListForm.getHdnSearchClipartOrCategoryTblPk();
      
      deletedClipartCategoryTblPk=Operations.deleteClipartCategory(clipartCategoryTblPk,dataSource);  
      if (httpSession.getAttribute("clipartTreeView")!=null){
        Treeview clipartTreeView = (Treeview)httpSession.getAttribute("clipartTreeView");
        if(clipartTreeView!=null) {
          clipartTreeView.ifCategoryDeleted(Integer.toString(deletedClipartCategoryTblPk),Integer.toString(parentClipartCategoryTblPk)); 
        }
      }
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          clipartCategory=Operations.getClipartCategory(clipartCategoryTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error getClipartCategory"+ex);
        }
        ActionMessage msg=new ActionMessage("msg.Clipart.Category.NotEmpty",clipartCategory);
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);  
    }catch(Exception e){
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartCategoryDeleteAction");
    }
    return mapping.findForward("success");
  } 
}