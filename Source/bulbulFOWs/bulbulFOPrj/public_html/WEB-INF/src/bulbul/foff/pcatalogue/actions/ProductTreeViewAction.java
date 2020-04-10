package bulbul.foff.pcatalogue.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.beans.ProductTreeView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProductTreeViewAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    String merchandiseCategoryTblPk;
    DataSource dataSource=null;
    try{
    
      logger.info("Entering ProductTreeViewAction");
      httpSession=request.getSession(false);
      logger.debug("***"+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        merchandiseCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("setting pk directly to -1");
        merchandiseCategoryTblPk="-1";
        logger.debug("merchandiseCategoryTblPk is: "+merchandiseCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",merchandiseCategoryTblPk);     
      request.setAttribute("treeOperation","merchandise_tree_view");     
    
      dataSource=getDataSource(request,"BOKey");
      logger.info("Getting Data Source in productTreeViewAction");
      
      if (httpSession.getAttribute("productTreeView")==null){
        ProductTreeView productTreeView = new ProductTreeView("MerchandiseCat",
                                    httpSession.getServletContext().getRealPath("/"),
                                    "images/",
                                    "admin", //((UserProfile)httpSession.getAttribute("userProfile")).getUserId(),
                                    httpSession.getId(),
                                    dataSource,
                                    1 //Integer.parseInt(((UserProfile)httpSession.getAttribute("userProfile")).getTreeviewLevel()),
                                    );
        httpSession.setAttribute("productTreeView",productTreeView);
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
          logger.info("Exiting ProduceTreeViewAction");
    }
    return mapping.findForward("success");
  }
}
