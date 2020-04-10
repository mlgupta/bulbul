package bulbul.foff.pcatalogue.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.beans.ProductTreeView;

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

public class ProductTreeViewAppendAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    String merchandiseCategoryTblPk;
    String selectedMerchandiseCategoryTblPk;
    try{
      logger.info("Entering ProductTreeViewAppendAction");
      
      logger.debug("Current "+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        merchandiseCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("setting pk directly to -1");
        merchandiseCategoryTblPk="-1";
        logger.debug("merchandiseCategoryTblPk is: "+merchandiseCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",merchandiseCategoryTblPk);  
      
      request.setAttribute("treeOperation","merchandise_tree_view_append");     

      logger.debug("Selected "+(String)request.getParameter("selectedCategoryPk"));
      if(request.getParameter("selectedCategoryPk")!=null){
        selectedMerchandiseCategoryTblPk=(String)request.getParameter("selectedCategoryPk");
      }else{
        logger.debug("setting pk directly to -1");
        selectedMerchandiseCategoryTblPk="-1";
        logger.debug("Selected MerchandiseCategoryTblPk is: "+selectedMerchandiseCategoryTblPk);
      }
    
      DataSource dataSource= getDataSource(request,"BOKey");

      if (httpSession.getAttribute("productTreeView")!=null){
        ProductTreeView productTreeView = (ProductTreeView)httpSession.getAttribute("productTreeView");
        if(productTreeView!=null) {
          productTreeView.appendTree(selectedMerchandiseCategoryTblPk);
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ProductTreeViewAppendAction");
    }
    return mapping.findForward("success");
  }
}