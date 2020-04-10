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
 *	Purpose: To Add A New Font Category In The font_list.jsp.
 *  Info: This Class Will Add A New Font Category In The font_category_list.jsp.It Uses The addFontCategory() Method Which 
 *        Takes The FontCategoryForm As One Of Its Parameter To Add A New Category. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 FontCategoryAddAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FontCategoryForm fontCategoryForm=null;
    String fontCategoryTblFk=null;
    int newFontCategoryTblFk=-1;
    HttpSession httpSession=null;
    ServletContext context = null;
    DataSource dataSource = null;
    ActionMessages messages=new ActionMessages();
    try{
      logger.info("Entering FontCategoryAddAction");
      httpSession=request.getSession(false);
      fontCategoryForm=(FontCategoryForm)form;
      fontCategoryTblFk=Long.toString(fontCategoryForm.getHdnFontCategoryTblFk());         
      if(fontCategoryTblFk!=null){
        request.setAttribute("fontCategoryTblPk",fontCategoryTblFk);
      }
      request.setAttribute("hdnSearchPageNo",fontCategoryForm.getHdnSearchPageNo());
      if ( isCancelled(request) ){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      newFontCategoryTblFk=Operations.addFontCategory(fontCategoryForm,dataSource); 
      if (httpSession.getAttribute("fontTreeView")!=null){
        Treeview fontTreeView = (Treeview)httpSession.getAttribute("fontTreeView");
        if(fontTreeView!=null) {
          fontTreeView.ifCategoryAdded(Integer.toString(newFontCategoryTblFk),fontCategoryTblFk); 
        }
      }
    }catch(SQLException e){
      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",fontCategoryForm.getTxtFontCategory());
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("font_category_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Font.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      if((e.getMessage().indexOf("cat2 already exists."))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",fontCategoryForm.getTxtFontCategory());
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontCategoryAddAction***");
    }
    return mapping.findForward("success");
  }
}