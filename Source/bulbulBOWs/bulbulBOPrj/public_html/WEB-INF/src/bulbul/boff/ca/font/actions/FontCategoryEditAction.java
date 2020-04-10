package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontCategoryForm;
import bulbul.boff.ca.font.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;

import bulbul.boff.general.Treeview;

import java.io.IOException;

import java.sql.SQLException;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 *	Purpose: To Edit A Font Category Selected In font_list.jsp.
 *  Info: This Class Will Edit A Font Category From The List. It Uses editFontCategory() Method and Passes It The 
 *        Instance Of The FontCategoryForm Which Contains The Primary Key Of The Font Category Which
 *        Is To Be Edited.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 FontCategoryEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   FontCategoryForm fontCategoryForm=null;
   int fontCategoryTblPk=-1;
   String fontCategoryTblFk=null;
   ActionMessages messages=new ActionMessages();
   HttpSession httpSession = null;
   ServletContext context = null;
   DataSource dataSource = null;
    try{
      logger.info("Entering FontCategoryEditAction");
      httpSession = request.getSession(false);
      fontCategoryForm=(FontCategoryForm)form;
      fontCategoryTblFk=Long.toString(fontCategoryForm.getHdnFontCategoryTblFk()); 
      logger.debug("fontCategoryTblFk: "+fontCategoryTblFk);
      if(fontCategoryTblFk!=null){
        request.setAttribute("fontCategoryTblPk",fontCategoryTblFk);
      }
      request.setAttribute("hdnSearchPageNo",fontCategoryForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
     
      context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");

      fontCategoryTblPk=Operations.editFontCategory(fontCategoryForm,dataSource);
      if (httpSession.getAttribute("fontTreeView")!=null){
        Treeview fontTreeView = (Treeview)httpSession.getAttribute("fontTreeView");
        if(fontTreeView!=null) {
          fontTreeView.ifCategoryEdited(Integer.toString(fontCategoryTblPk)); 
        }
      }
    
    }catch(SQLException e){
     logger.error("Caught exception:" +e);
     if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",fontCategoryForm.getTxtFontCategory());
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in FontCategorytEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontCategoryEditAction");
    }
    return mapping.findForward("success");
  }
}