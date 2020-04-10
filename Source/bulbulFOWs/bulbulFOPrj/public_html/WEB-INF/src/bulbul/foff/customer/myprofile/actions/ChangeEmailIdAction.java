package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myprofile.actionforms.ChangeEmailIdForm;
import bulbul.foff.customer.myprofile.beans.Operations;
import bulbul.foff.general.ErrorConstants;
import bulbul.foff.general.FOConstants;

import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ChangeEmailIdAction extends Action  {
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
    ChangeEmailIdForm ceForm=null;
    int returnCustomerEmailIdTblPk;
    ServletContext context=null;
    CustomerInfo customerInfo=null;
    String smtphost=null;
    String emailid=null;
    String emailSubject=null;
    String emailMessage=null;
    DataSource dataSource  = null;
    HttpSession httpSession=null;
    try{
      logger.info("Entering ChangeEmailIdAction");
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      if (isCancelled(request)){
        return mapping.findForward("success");
      }      
      messages=new ActionMessages();
      ceForm=(ChangeEmailIdForm)form;
      dataSource=getDataSource(request,"FOKey");
      context=servlet.getServletContext();
      smtphost=(String)context.getAttribute("smtphost");
      emailid=(String)context.getAttribute("emailid");
      emailSubject="Your Email Id is changed in www.printoon.com ";
      emailMessage="You Emaild Changed @ www.pritoon.com";
      emailMessage+="<br> Your Mail Id : " + ceForm.getTxtNewEmailId() ;  
      emailMessage+="<br> Your Password remains same";  
      emailMessage+="<br><br><br> Regards,";
      emailMessage+="<br> Administrator,";
      emailMessage+="<br> Printoon,";
      emailMessage+="<br> Email : " + emailid;
      
      returnCustomerEmailIdTblPk=Operations.changeEmailId(ceForm,dataSource,customerInfo,smtphost,emailid,emailSubject,emailMessage);
      if(returnCustomerEmailIdTblPk==-1){
        msg=new ActionMessage("msg.WrongOldEmailId");
        messages.add("messagWrongOldEmailId",msg);
        saveMessages(request,messages);        
        return mapping.getInputForward();  
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      if(e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue())>-1){
        msg=new ActionMessage("msg.NewEmailIdAlreadyInUse",ceForm.getTxtNewEmailId());
        messages.add("messageEmailIdInUse" ,msg);
      }
            
      saveMessages(request,messages);
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      return mapping.getInputForward();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting ChangeEmailIdAction");
    }
    
    return mapping.findForward("success");
  }
}