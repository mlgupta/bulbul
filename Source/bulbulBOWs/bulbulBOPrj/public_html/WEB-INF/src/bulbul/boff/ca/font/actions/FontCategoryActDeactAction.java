package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontListForm;
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
 *	Purpose: To Activate Or Deactivate The Selected Font Category In The font_list.jsp. 
 *  Info: This Class Will Take The Primary Key Of The Selected Font Category Record And Pass It To The actDeactFontCategory() Method
 *        Which Activates Or Deactivates That Record.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontCategoryActDeactAction extends Action  {
  Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FontListForm fontListForm=null; 
    int fontCategoryTblPk=-1;

    fontListForm=(FontListForm)form;
    if(request.getParameter("radSearchSelect")!=null){
      fontCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("***Entering FontCategoryActDeactAction***");
    try{
      Operations.actDeactFontCategory(fontCategoryTblPk,fontListForm,dataSource);  
    }catch(Exception e){
      logger.error("Caught Exception: "+e);
    }
    logger.info("***Exiting FontCategoryActDeactAction***");
    return mapping.findForward("success");
  }

}