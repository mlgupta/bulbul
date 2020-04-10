package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontForm;
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
 *	Purpose: To View The Font Selected In font_list.jsp.
 *  Info: This Class Will Use The viewFont() Method To Populate The font_view.jsp With The Specified Data. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FontForm fontForm =null;
    String fontTblPk=null;
    String fontCategoryTblPk=null;
    DataSource dataSource=null;
    ServletContext context=null;
    try{
      logger.info("Entering FontViewAction");
      fontForm=(FontForm)form;
      fontCategoryTblPk=Long.toString(fontForm.getHdnFontCategoryTblPk());         
      if(fontCategoryTblPk!=null){
       request.setAttribute("fontCategoryTblPk",fontCategoryTblPk);
      }
      request.setAttribute("hdnSearchPageNo",fontForm.getHdnSearchPageNo());
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      fontTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      fontForm=Operations.viewFont(fontTblPk,dataSource);
      fontForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),fontForm); 
    }catch(Exception e){
     logger.error("exception:"+e);
     return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontViewAction");
    }
    return mapping.findForward("success");
  }
}