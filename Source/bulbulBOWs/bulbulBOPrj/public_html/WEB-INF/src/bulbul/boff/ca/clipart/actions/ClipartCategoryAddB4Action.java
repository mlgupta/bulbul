package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartCategoryForm;
import bulbul.boff.ca.clipart.beans.Operations;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ClipartCategoryAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ServletContext context = null;
    DataSource dataSource = null;
    String parentCategory=null;
    ClipartCategoryForm clipartCategoryForm =null;
    int clipartCategoryTblFk=-1;
    try{
      logger.info("Entering ClipartCategoryAddB4Action");
      
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      
      clipartCategoryForm =new ClipartCategoryForm();
      
      if(request.getParameter("hdnSearchClipartOrCategoryTblPk")!=null){
        clipartCategoryTblFk=Integer.parseInt(request.getParameter("hdnSearchClipartOrCategoryTblPk"));
      }
      
      clipartCategoryForm.setTxtClipartCategory("");
      clipartCategoryForm.setTxaClipartCategoryDesc("");
      clipartCategoryForm.setHdnClipartCategoryTblFk(clipartCategoryTblFk);
      
      if(clipartCategoryTblFk!=-1){
        parentCategory=Operations.getClipartCategory(clipartCategoryTblFk,dataSource);  
      }else{
        parentCategory=BOConstants.ALL.toString();
      }
      clipartCategoryForm.setTxtParentCategory(parentCategory);
      clipartCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),clipartCategoryForm);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting ClipartCategoryAddB4Action");  
    }
    return mapping.findForward("success");
  }
}