package bulbul.boff.ca.font.actions;

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


public class FontCategoryTreeViewAppendAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    String fontCategoryTblPk;
    String selectedFontCategoryTblPk;
    try{
      logger.info("Entering FontCategoryTreeViewAppendAction");
      
      logger.debug("Current "+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        fontCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("***setting pk directly to -1");
        fontCategoryTblPk="-1";
        logger.debug("***fontCategoryTblPk is: "+fontCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",fontCategoryTblPk);  
      
      request.setAttribute("treeOperation","font_category_tree_view_append");     

      logger.debug("Selected "+(String)request.getParameter("selectedCategoryPk"));
      if(request.getParameter("selectedCategoryPk")!=null){
        selectedFontCategoryTblPk=(String)request.getParameter("selectedCategoryPk");
      }else{
        logger.debug("***setting pk directly to -1");
        selectedFontCategoryTblPk="-1";
        logger.debug("***Selected FontCategoryTblPk is: "+selectedFontCategoryTblPk);
      }
    
      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      logger.info("Getting Data Source in FontCategoryTreeViewAction");

      
      if (httpSession.getAttribute("fontCategoryTreeView")!=null){
        Treeview fontCategoryTreeView = (Treeview)httpSession.getAttribute("fontCategoryTreeView");
        if(fontCategoryTreeView!=null) {
          fontCategoryTreeView.appendTree(selectedFontCategoryTblPk);
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontCategoryTreeViewAppendAction");
    }
    return mapping.findForward("success");
  }
}