package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontCategoryForm;
import bulbul.boff.ca.font.beans.Operations;

import bulbul.boff.general.BOConstants;

import java.io.IOException;

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
 *	Purpose: To View The Font Category Selected In font_list.jsp.
 *  Info: This Class Will Use The viewFontCategory() Method To Populate The font_category_view.jsp With The Specified Data. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontCategoryViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FontCategoryForm fontCategoryForm =null;
    String fontCategoryTblPk=null;
    String fontCategoryTblFk=null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering FontCategoryViewAction");
      fontCategoryForm=(FontCategoryForm)form;
      fontCategoryTblFk=Long.toString(fontCategoryForm.getHdnFontCategoryTblFk());         
      if(fontCategoryTblFk!=null){
        request.setAttribute("fontCategoryTblPk",fontCategoryTblFk);
      }
      request.setAttribute("hdnSearchPageNo",fontCategoryForm.getHdnSearchPageNo());
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      fontCategoryTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      fontCategoryForm=Operations.viewFontCategory(fontCategoryTblPk,dataSource);
      fontCategoryForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),fontCategoryForm); 
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }
    logger.info("Exiting FontCategoryViewAction");
    return mapping.findForward("success");
  }
}