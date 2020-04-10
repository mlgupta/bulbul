package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontForm;
import bulbul.boff.ca.font.beans.Operations;
import bulbul.boff.general.BOConstants;

import bulbul.boff.general.ErrorConstants;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 *	Purpose: To Add A New Font In The font_list.jsp.
 *  Info: This Class Will Add A New Font In The font_list.jsp.It Uses The addFont() Method Which 
 *        Takes The FontForm As One Of Its Parameter To Add A New Font. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 FontAddAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FontForm fontForm=null;
    String fontCategoryTblPk=null;
    ActionMessages messages=new ActionMessages();
    ServletContext context = null;
    DataSource dataSource = null;
    try{
      logger.info("Entering FontAddAction");
      fontForm=(FontForm)form;
      fontCategoryTblPk=Long.toString(fontForm.getHdnFontCategoryTblPk());         
      if(fontCategoryTblPk!=null){
        request.setAttribute("fontCategoryTblPk",fontCategoryTblPk);
      }
      request.setAttribute("hdnSearchPageNo",fontForm.getHdnSearchPageNo());
      if ( isCancelled(request) ){
        return mapping.findForward("success");
      }
      context = servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      Operations.addFont(fontForm,dataSource); 
    }catch(SQLException e){
      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",fontForm.getTxtFont());
        messages.add("message1" ,msg);
      }
      if((e.getMessage().indexOf("font_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Font.Unique.Append"," ");
        messages.add("message2" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontAddAction");
    }
    return mapping.findForward("success");    
  }
}