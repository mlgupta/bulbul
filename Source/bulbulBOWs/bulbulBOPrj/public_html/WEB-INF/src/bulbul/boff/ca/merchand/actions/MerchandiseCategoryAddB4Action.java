package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryForm;
import bulbul.boff.ca.merchand.beans.Operations;
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


public class MerchandiseCategoryAddB4Action extends Action  {
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
    int merchandiseCategoryTblFk=-1;
    try{
      logger.info("Entering MerchandiseCategoryAddB4Action");
      
      MerchandiseCategoryForm merchandiseCategoryForm =new MerchandiseCategoryForm();
      
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      
      if (request.getParameter("hdnSearchMerchandiseOrCategoryTblPk")!=null) {
        merchandiseCategoryTblFk=Integer.parseInt(request.getParameter("hdnSearchMerchandiseOrCategoryTblPk"));
      }
      logger.debug("hdnSearchMerchandiseOrCategoryTblPk: "+merchandiseCategoryTblFk);
      merchandiseCategoryForm.setHdnMerchandiseCategoryTblFk(merchandiseCategoryTblFk);
      
      merchandiseCategoryForm.setTxtMerchandiseCategory("");
      if(merchandiseCategoryTblFk!=-1){
        parentCategory=Operations.getMerchandiseCategory(merchandiseCategoryTblFk,dataSource);  
      }else{
        parentCategory=BOConstants.ALL.toString();
      }
      merchandiseCategoryForm.setTxtParentCategory(parentCategory);
      merchandiseCategoryForm.setTxaMerchandiseCategoryDesc("");
      merchandiseCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      merchandiseCategoryForm.setRadMOrC(Integer.parseInt(BOConstants.MERCHANDISE_CATEGORY_ONLY_VAL.toString()));
      request.setAttribute(mapping.getAttribute(),merchandiseCategoryForm);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting MerchandiseCategoryAddB4Action");  
    }
    return mapping.findForward("success");
  }
}