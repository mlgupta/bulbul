package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.myprofile.actionforms.ChangePasswordForm;
import bulbul.foff.customer.myprofile.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ChangePasswordAction extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    ActionMessages messages=null;
    ActionMessage msg=null;
    ChangePasswordForm cpForm=null;
    int returnCustomerRegistrationTblPk;
    try{
      logger.info("Entering ChangePasswordAction");
      
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      messages=new ActionMessages();
      cpForm=(ChangePasswordForm)form;
      DataSource dataSource = getDataSource(request,"FOKey");
      returnCustomerRegistrationTblPk=Operations.changePassword(cpForm,dataSource);
      if(returnCustomerRegistrationTblPk==-1){        
        msg=new ActionMessage("msg.WrongOldPassword");
        messages.add("message1",msg);
        saveMessages(request,messages);
        
        return mapping.getInputForward();  
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ChangePasswordAction");
    }
    
    return mapping.findForward("success");
  }
}