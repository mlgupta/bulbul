package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartCategoryListForm;
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


public class ClipartMoveAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int srcClipartTblPk=-1;
    int trgCategoryTblPk=-1;
    String clipartName=null;
    HttpSession httpSession=null;
    
    ActionMessages messages=new ActionMessages();
    ClipartCategoryListForm clipartCategoryListForm=null;

    logger.info("Entering ClipartMoveAction");

    ServletContext context=servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");

    try{
      
      httpSession=request.getSession(false);
      clipartCategoryListForm=(ClipartCategoryListForm)form;
      
      if(request.getParameter("hdnSrcClipartTblPk")!=null){
        srcClipartTblPk=Integer.parseInt(request.getParameter("hdnSrcClipartTblPk"));
      }
      
      if(request.getParameter("hdnTrgCategoryTblPk")!=null){
        trgCategoryTblPk=Integer.parseInt(request.getParameter("hdnTrgCategoryTblPk"));
      }

      logger.debug("srcClipartTblPk"+srcClipartTblPk);
      logger.debug("trgCategoryTblPk"+trgCategoryTblPk);

      Operations.moveClipart(srcClipartTblPk,trgCategoryTblPk,dataSource);
      
      request.setAttribute("clipartCategoryTblPk",Integer.toString(trgCategoryTblPk));

      if (httpSession.getAttribute("clipartCategoryTreeView")!=null){
        Treeview clipartCategoryTreeView = (Treeview)httpSession.getAttribute("clipartCategoryTreeView");
        if(clipartCategoryTreeView!=null) {
          clipartCategoryTreeView.refresh();
        }
      }
  
      return mapping.findForward("success");
    }catch(SQLException e){

      try{
       request.setAttribute("clipartCategoryTblPk",Integer.toString(Operations.getClipartCategoryPk(srcClipartTblPk,dataSource)));
      }catch(Exception ex){
        logger.error(e.getMessage());
        e.printStackTrace();
      }

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        try{
          clipartName=Operations.getClipartName(clipartCategoryListForm.getHdnSrcClipartTblPk(),dataSource);          
        }catch(Exception ex){
          logger.error(e.getMessage());
        }
        ActionMessage msg=new ActionMessage("msg.Unique",clipartName);
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("clipart_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Clipart.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }
    
  }
}