package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontCategoryListForm;
import bulbul.boff.ca.font.beans.Operations;
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


public class FontCategoryMoveAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int srcCategoryTblPk=-1;
    int trgCategoryTblPk=-1;
    String fontCategory=null;
    HttpSession httpSession=null;
    
    ActionMessages messages=new ActionMessages();
    FontCategoryListForm fontCategoryListForm=null;

    logger.info("Entering FontCategoryMoveAction");

    ServletContext context=servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");

    try{
      
      httpSession=request.getSession(false);
      fontCategoryListForm=(FontCategoryListForm)form;
      
      if(request.getParameter("hdnSrcCategoryTblPk")!=null){
        srcCategoryTblPk=Integer.parseInt(request.getParameter("hdnSrcCategoryTblPk"));
      }
      
      if(request.getParameter("hdnTrgCategoryTblPk")!=null){
        trgCategoryTblPk=Integer.parseInt(request.getParameter("hdnTrgCategoryTblPk"));
      }

      logger.debug("srcCategoryTblPk"+srcCategoryTblPk);
      logger.debug("trgCategoryTblPk"+trgCategoryTblPk);

      Operations.moveFontCategory(srcCategoryTblPk,trgCategoryTblPk,dataSource);
      
      request.setAttribute("fontCategoryTblPk",Integer.toString(trgCategoryTblPk));

      if (httpSession.getAttribute("fontCategoryTreeView")!=null){
        Treeview fontCategoryTreeView = (Treeview)httpSession.getAttribute("fontCategoryTreeView");
        if(fontCategoryTreeView!=null) {
          fontCategoryTreeView.refresh();
        }
      }
      return mapping.findForward("success");
    }catch(SQLException e){

      try{
        request.setAttribute("fontCategoryTblPk",Integer.toString(Operations.getFontCategoryFk(srcCategoryTblPk,dataSource)));
      }catch(Exception ex){
        logger.error(e.getMessage());
        e.printStackTrace();
      }

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        try{
          fontCategory=Operations.getFontCategory(fontCategoryListForm.getHdnSrcCategoryTblPk(),dataSource);          
        }catch(Exception ex){
          logger.error(e.getMessage());
        }
        ActionMessage msg=new ActionMessage("msg.Unique",fontCategory);
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("font_category_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Font.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      if((e.getMessage().indexOf(ErrorConstants.CHILD_NODE_EXCEPTION.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.childNodeMove");
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