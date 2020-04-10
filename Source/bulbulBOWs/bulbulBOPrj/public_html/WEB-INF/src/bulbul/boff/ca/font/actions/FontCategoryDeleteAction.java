package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontListForm;
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
 *	Purpose: To Delete A Font Category From The font_category_list.jsp.
 *  Info: This Class Will Delete A Font Category From The font_category_list.jsp. It Uses deleteFontCategory() Method and Passes It The 
 *        Primary Key Of The Selected Category Which Is To Be Deleted.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 FontCategoryDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int fontCategoryTblPk=-1;
    String fontCategory=null;
    int deletedFontCategoryTblPk=-1;
    String parentFontCategoryTblPk="-1"; 
    ActionMessages messages=new ActionMessages();
    FontListForm fontListForm =(FontListForm)form;
    
    if(request.getParameter("radSearchSelect")!=null){
      fontCategoryTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info("*** Entering FontCategoryDeleteAction ***");
    try{
      HttpSession httpSession=request.getSession(false);

      if(fontListForm.getHdnSearchFontOrCategoryTblPk() !=null){
        logger.debug("***setting pk from FontListForm");
        parentFontCategoryTblPk=fontListForm.getHdnSearchFontOrCategoryTblPk();
      }
     
      deletedFontCategoryTblPk=Operations.deleteFontCategory(fontCategoryTblPk,dataSource);  

      if (httpSession.getAttribute("fontTreeView")!=null){
        Treeview fontTreeView = (Treeview)httpSession.getAttribute("fontTreeView");
        if(fontTreeView!=null) {
          fontTreeView.ifCategoryDeleted(Integer.toString(deletedFontCategoryTblPk),parentFontCategoryTblPk); 
        }
      }
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          fontCategory=Operations.getFontCategory(fontCategoryTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error getFontCategory"+ex);
        }
        
        ActionMessage msg=new ActionMessage("msg.Font.Category.NotEmpty",fontCategory);
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);  
      
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      logger.info("Exiting FontCategoryDeleteAction");
    }
    
    return mapping.findForward("success");
  }
 
}