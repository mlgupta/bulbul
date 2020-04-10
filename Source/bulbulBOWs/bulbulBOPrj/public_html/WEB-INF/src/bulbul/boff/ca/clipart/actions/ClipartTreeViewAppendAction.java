package bulbul.boff.ca.clipart.actions;

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


public class ClipartTreeViewAppendAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    String clipartCategoryTblPk;
    String selectedClipartCategoryTblPk;
    try{
      logger.info("***Entering ClipartTreeViewAppendAction***");
      
      logger.debug("Current "+(String)request.getParameter("currentCategoryPk"));
      if(request.getParameter("currentCategoryPk")!=null){
        clipartCategoryTblPk=(String)request.getParameter("currentCategoryPk");
      }else{
        logger.debug("***setting pk directly to -1");
        clipartCategoryTblPk="-1";
        logger.debug("***clipartCategoryTblPk is: "+clipartCategoryTblPk);
      }
      request.setAttribute("currentCategoryPk",clipartCategoryTblPk);  
      
      request.setAttribute("treeOperation","clipart_tree_view_append");     

      logger.debug("Selected "+(String)request.getParameter("selectedCategoryPk"));
      if(request.getParameter("selectedCategoryPk")!=null){
        selectedClipartCategoryTblPk=(String)request.getParameter("selectedCategoryPk");
      }else{
        logger.debug("***setting pk directly to -1");
        selectedClipartCategoryTblPk="-1";
        logger.debug("***Selected ClipartCategoryTblPk is: "+selectedClipartCategoryTblPk);
      }
    
      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      logger.info("Getting Data Source in ClipartTreeViewAction");

      
      if (httpSession.getAttribute("clipartTreeView")!=null){
        Treeview clipartTreeView = (Treeview)httpSession.getAttribute("clipartTreeView");
        if(clipartTreeView!=null) {
          clipartTreeView.appendTree(selectedClipartCategoryTblPk);
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartTreeViewAppendAction");
    }
    return mapping.findForward("success");
  }
}