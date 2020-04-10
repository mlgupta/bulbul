package bulbul.boff.ca.clipart.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.Treeview;
import bulbul.boff.general.beans.TreeItem;
import bulbul.boff.user.beans.UserProfile;

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


public class ClipartCategoryTreeViewAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    String clipartCategoryTblPk;
    try{
    
      logger.info("Entering ClipartCategoryTreeViewAction");
      logger.debug("***"+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        clipartCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("setting pk directly to -1");
        clipartCategoryTblPk="-1";
        logger.debug("clipartCategoryTblPk is: "+clipartCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",clipartCategoryTblPk);     
      request.setAttribute("treeOperation","clipart_category_tree_view");     
    
      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      logger.info("Getting Data Source in ClipartCategoryTreeViewAction");

      
      TreeItem treeItem4Category = new TreeItem();
      treeItem4Category.setTableName("clipart_category_tbl");
      treeItem4Category.setPkColumn("clipart_category_tbl_pk");
      treeItem4Category.setNameColumn("clipart_category");
      treeItem4Category.setDescriptionColumn("clipart_category_description");
      treeItem4Category.setFkColumn("clipart_category_tbl_fk");
      if (httpSession.getAttribute("clipartCategoryTreeView")==null){
        Treeview clipartCategoryTreeView = new Treeview("ClipartCat",
                                    httpSession.getServletContext().getRealPath("/"),
                                    "images/",
                                    ((UserProfile)httpSession.getAttribute("userProfile")).getUserId(),
                                    httpSession.getId(),
                                    dataSource,
                                    treeItem4Category,
                                    null,
                                    ((UserProfile)httpSession.getAttribute("userProfile")).getTreeviewLevel(),
                                    true);
        httpSession.setAttribute("clipartCategoryTreeView",clipartCategoryTreeView);
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
          logger.info("Exiting ClipartCategoryTreeViewAction");
    }
    return mapping.findForward("success");
  }
}