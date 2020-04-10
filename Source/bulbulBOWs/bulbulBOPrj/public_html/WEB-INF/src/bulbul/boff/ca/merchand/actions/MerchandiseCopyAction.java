package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryListForm;
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


public class MerchandiseCopyAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int srcMerchandiseTblPk=-1;
    int srcCategoryTblPk=-1;
    int trgCategoryTblPk=-1;
    String merchandiseName=null;
    HttpSession httpSession=null;
    
    ActionMessages messages=new ActionMessages();
    MerchandiseCategoryListForm merchandiseCategoryListForm=null;

    logger.info("Entering MerchandiseCopyAction");

    ServletContext context=servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");

    try{
      
      httpSession=request.getSession(false);
      merchandiseCategoryListForm=(MerchandiseCategoryListForm)form;
      if (request.getParameter("hdnSrcMerchandiseTblPk")!=null) {
        srcMerchandiseTblPk=Integer.parseInt(request.getParameter("hdnSrcMerchandiseTblPk"));
      }
      srcCategoryTblPk=merchandiseCategoryListForm.getHdnSrcCategoryTblPk();
      if (request.getParameter("hdnTrgCategoryTblPk")!=null) {
        trgCategoryTblPk=Integer.parseInt(request.getParameter("hdnTrgCategoryTblPk"));
      }

      logger.debug("srcMerchandiseTblPk"+srcMerchandiseTblPk);
      logger.debug("trgCategoryTblPk"+trgCategoryTblPk);
      
      Operations.copyMerchandise(srcMerchandiseTblPk,srcCategoryTblPk,trgCategoryTblPk,dataSource);
      request.setAttribute("merchandiseCategoryTblPk",Integer.toString(trgCategoryTblPk));

      if (httpSession.getAttribute("merchandiseCategoryTreeView")!=null){
        Treeview merchandiseCategoryTreeView = (Treeview)httpSession.getAttribute("merchandiseCategoryTreeView");
        if(merchandiseCategoryTreeView!=null) {
          merchandiseCategoryTreeView.refresh();
        }
      }
  
      return mapping.findForward("success");
    }catch(SQLException e){

      try{
       request.setAttribute("merchandiseCategoryTblPk",Integer.toString(srcCategoryTblPk));
      }catch(Exception ex){
        logger.error(e.getMessage());
        e.printStackTrace();
      }

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        try{
          merchandiseName=Operations.getMerchandiseName(merchandiseCategoryListForm.getHdnSrcMerchandiseTblPk(),dataSource);          
        }catch(Exception ex){
          logger.error(e.getMessage());
        }
        ActionMessage msg=new ActionMessage("msg.Unique",merchandiseName);
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("merchandise_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Merchandise.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      if((e.getMessage().indexOf("merchandise_only"))>-1){
        ActionMessage msg=new ActionMessage("msg.MerchandiseCopyToCategory");
        messages.add("message2" ,msg);
      }
      if((e.getMessage().indexOf("Incompatible merchandise category"))>-1){
        ActionMessage msg=new ActionMessage("msg.MerchandiseCategory.Incompatible");
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