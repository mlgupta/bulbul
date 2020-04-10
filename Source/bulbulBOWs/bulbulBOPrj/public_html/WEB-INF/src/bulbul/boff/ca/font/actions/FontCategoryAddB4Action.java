package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontCategoryForm;
import bulbul.boff.ca.font.beans.Operations;

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


/**
 *	Purpose: To Reset The Value Of All The FontCategoryForm Elements.
 *  Info: This Class Will Set The Value Of All FontCategoryForm Elements To Null By Using There Setter Methods.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontCategoryAddB4Action extends Action  {
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
    int fontCategoryTblFk=-1;
    try{
      logger.info("Entering FontCategoryAddB4Action");

      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");

      FontCategoryForm fontCategoryForm =new FontCategoryForm();
      if(request.getParameter("hdnSearchFontOrCategoryTblPk")!=null){
        fontCategoryTblFk= Integer.parseInt(request.getParameter("hdnSearchFontOrCategoryTblPk"));
      }
      
      logger.debug("hdnSearchFontOrCategoryTblPk: "+fontCategoryTblFk);
      fontCategoryForm.setHdnFontCategoryTblFk(fontCategoryTblFk);        
      if(fontCategoryTblFk!=-1){
        parentCategory=Operations.getFontCategory(fontCategoryTblFk,dataSource);  
      }else{
        parentCategory=BOConstants.ALL.toString();
      }
      fontCategoryForm.setTxtFontCategory("");
      fontCategoryForm.setTxtParentCategory(parentCategory);
      fontCategoryForm.setTxaFontCategoryDesc("");
      fontCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),fontCategoryForm);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting FontCategoryAddB4Action");  
    }
    return mapping.findForward("success");
  }
}