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


public class FontMoveAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int srcFontTblPk=-1;
    int trgCategoryTblPk=-1;
    String fontName=null;
    HttpSession httpSession=null;
    ServletContext context=null;
    DataSource dataSource=null;
    ActionMessages messages=new ActionMessages();
    FontCategoryListForm fontCategoryListForm=null;
    try{
      logger.info("Entering FontMoveAction");
      context=servlet.getServletContext();
      dataSource=getDataSource(request,"BOKey");
      
      httpSession=request.getSession(false);
      fontCategoryListForm=(FontCategoryListForm)form;
      
      if(request.getParameter("hdnSrcFontTblPk")!=null){
        srcFontTblPk=Integer.parseInt(request.getParameter("hdnSrcFontTblPk"));
      }
      
      if(request.getParameter("hdnTrgCategoryTblPk")!=null){
        trgCategoryTblPk=Integer.parseInt(request.getParameter("hdnTrgCategoryTblPk"));
      }

      logger.debug("srcFontTblPk"+srcFontTblPk);
      logger.debug("trgCategoryTblPk"+trgCategoryTblPk);

      Operations.moveFont(srcFontTblPk,trgCategoryTblPk,dataSource);
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
       request.setAttribute("fontCategoryTblPk",Operations.getFontCategoryPk(srcFontTblPk,dataSource));
      }catch(Exception ex){
        logger.error(e.getMessage());
        e.printStackTrace();
      }

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        try{
          fontName=Operations.getFontName(fontCategoryListForm.getHdnSrcFontTblPk(),dataSource);          
        }catch(Exception ex){
          logger.error(e.getMessage());
        }
        ActionMessage msg=new ActionMessage("msg.Unique",fontName);
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("font_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Font.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Entering FontMoveAction");
    }
    
  }
}