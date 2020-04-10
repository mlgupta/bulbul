package bulbul.boff.ca.merchand.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.Treeview;

import java.io.IOException;

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


public class MerchandiseCategoryTreeViewAppendAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    String merchandiseCategoryTblPk;
    String selectedMerchandiseCategoryTblPk;
    try{
      logger.info("Entering MerchandiseCategoryTreeViewAppendAction");
      
      logger.debug("Current "+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        merchandiseCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("***setting pk directly to -1");
        merchandiseCategoryTblPk="-1";
        logger.debug("***merchandiseCategoryTblPk is: "+merchandiseCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",merchandiseCategoryTblPk);  
      
      request.setAttribute("treeOperation","merchandise_category_tree_view_append");     

      logger.debug("Selected "+(String)request.getParameter("selectedCategoryPk"));
      if(request.getParameter("selectedCategoryPk")!=null){
        selectedMerchandiseCategoryTblPk=(String)request.getParameter("selectedCategoryPk");
      }else{
        selectedMerchandiseCategoryTblPk="-1";
      }
    
      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      logger.info("Getting Data Source in MerchandiseCategoryTreeViewAction");

      
      if (httpSession.getAttribute("merchandiseCategoryTreeView")!=null){
        Treeview merchandiseCategoryTreeView = (Treeview)httpSession.getAttribute("merchandiseCategoryTreeView");
        if(merchandiseCategoryTreeView!=null) {
          merchandiseCategoryTreeView.appendTree(selectedMerchandiseCategoryTblPk);
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MerchandiseCategoryTreeViewAppendAction");
    }
    return mapping.findForward("success");
  }
}