package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;
import bulbul.boff.user.beans.UserProfile;

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


public class MerchandiseAddAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseForm merchandiseForm=null;
    int merchandiseCategoryTblPk=-1;
    int userProfileTblPk=-1;
    ActionMessages messages=new ActionMessages();
    HttpSession httpSession=null;
    logger.info("***Entering MerchandiseAddAction***");

   try{
      httpSession=request.getSession(false);
      merchandiseForm=(MerchandiseForm)form;
      logger.debug("***getHdnMerchandiseCategoryTblPk(): "+merchandiseForm.getHdnMerchandiseCategoryTblPk());
      merchandiseCategoryTblPk=merchandiseForm.getHdnMerchandiseCategoryTblPk();
      request.setAttribute("merchandiseCategoryTblPk",Integer.toString(merchandiseCategoryTblPk));
      request.setAttribute("hdnSearchPageNo",merchandiseForm.getHdnSearchPageNo());
      if ( isCancelled(request) ){
        httpSession.removeAttribute("size4List");
        httpSession.removeAttribute("merchandiseDecorations");
        return mapping.findForward("cancel"); 
      }
   
      ServletContext context = servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");
      if(httpSession.getAttribute("userProfile")!=null){
        userProfileTblPk=((UserProfile)httpSession.getAttribute("userProfile")).getUserProfileTblPk();
        logger.info("userProfileTblPk is: "+userProfileTblPk);
      }
      
      Operations.addMerchandise(userProfileTblPk,merchandiseForm,dataSource); 
      httpSession.removeAttribute("size4List");
      httpSession.removeAttribute("merchandiseDecorations");
      
      request.setAttribute(mapping.getAttribute(),merchandiseForm);
      return mapping.findForward("success");
    }catch(SQLException e){

      if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",merchandiseForm.getTxtMerchandise());
        messages.add("message1" ,msg);
        //saveMessages(request,messages);
      }
      if((e.getMessage().indexOf("merchandise_tbl_ckey"))>-1){
        ActionMessage msg=new ActionMessage("msg.Merchandise.Unique.Append"," ");
        messages.add("message2" ,msg);
        //saveMessages(request,messages);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("***Exiting MerchandiseAddAction***");
    }
    
  }

}