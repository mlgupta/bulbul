package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartForm;
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


public class ClipartAddB4Action extends Action  {
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
    int clipartCategoryTblPk=-1;
    try{
      logger.info("Entering ClipartAddB4Action");
      
      ClipartForm clipartForm =new ClipartForm();
      
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      if(request.getParameter("hdnSearchClipartOrCategoryTblPk")!=null){
        clipartCategoryTblPk=Integer.parseInt(request.getParameter("hdnSearchClipartOrCategoryTblPk"));
      }
      clipartForm.setHdnClipartCategoryTblPk(clipartCategoryTblPk);        
      
      parentCategory=Operations.getClipartCategory(clipartCategoryTblPk,dataSource);
      clipartForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));        
      clipartForm.setTxtClipart("");
      clipartForm.setTxtParentCategory(parentCategory);
      clipartForm.setTxaClipartKeywords("");
      request.setAttribute(mapping.getAttribute(),clipartForm);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting ClipartAddB4Action");  
    }
    return mapping.findForward("success");
  }
}