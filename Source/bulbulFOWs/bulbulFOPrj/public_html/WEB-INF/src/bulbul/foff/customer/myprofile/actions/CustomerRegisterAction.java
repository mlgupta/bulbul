package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myprofile.actionforms.CustomerRegisterForm;
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

public class CustomerRegisterAction extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    CustomerRegisterForm customerRegisterForm=null;
    DataSource dataSource=null;
    ServletContext context=null;
    ActionMessages messages=null;
    ActionMessage msg=null;
    CustomerInfo customerInfo=null; 
    HttpSession  httpSession = null;
    String targetForward=null;
    String smtphost=null;
    String emailid=null;
    String emailSubject=null;
    String emailMessage=null;
    
    try{
      logger.info("Entering Action CustomerRegisterAction");
      
      httpSession=request.getSession(false);
      customerInfo = (CustomerInfo) httpSession.getAttribute("customerInfo");
      if ( isCancelled(request) ){
        return mapping.findForward("success");
      }
      messages=new ActionMessages();
      customerRegisterForm=(CustomerRegisterForm)form;
      context=servlet.getServletContext();
      smtphost=(String)context.getAttribute("smtphost");
      emailid=(String)context.getAttribute("emailid");
      emailSubject="You Got Registered in www.printoon.com ";
      emailMessage="Thank You For Registering @ www.pritoon.com";
      emailMessage+="<br> Your Mail Id : " + customerRegisterForm.getTxtCustomerEmailId() ;  
      emailMessage+="<br> Your Password : " + customerRegisterForm.getTxtCustomerPassword() ;  
      emailMessage+="<br><br><br> Regards,";
      emailMessage+="<br> Administrator,";
      emailMessage+="<br> Printoon,";
      emailMessage+="<br> Email : " + emailid;
      
      dataSource=getDataSource(request,"FOKey");
      Operations.registerCustomer(dataSource,customerRegisterForm,customerInfo,smtphost,emailid,emailSubject,emailMessage);    
      
      /* This Code, Redirect the based on targetForward value */       
      /* Keep This Code Allways at Bottom of this try block */       
      targetForward=customerInfo.getTargetUrl();
      logger.debug("targetForward : " + targetForward);
      customerInfo.setTargetUrl(null);      
      if (targetForward!=null){
        request.setAttribute("targetForward",targetForward);
      }else{
        request.setAttribute("targetForward","");
      }
    }catch(SQLException e){
      e.printStackTrace();
      if(e.getMessage().indexOf(ErrorConstants.ALREADY_REGISTERED.getErrorValue())>-1){
        msg=new ActionMessage("msg.EmailIdAlreadyRegistered",customerRegisterForm.getTxtCustomerEmailId());
        messages.add("messageEmailIdAlreadyRegistered" ,msg);
      }
      if(e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue())>-1){
        msg=new ActionMessage("msg.EmailIdAlreadyInUse",customerRegisterForm.getTxtCustomerEmailId());
        messages.add("messageEmailIdInUse" ,msg);
      }
      
      if(e.getMessage().indexOf(ErrorConstants.INVALID_DESIGN_CODE.getErrorValue())>-1){
        msg=new ActionMessage("msg.InvalidDesignCode");
        messages.add("messageInvalidDesignCode" ,msg);
      }
      
      saveMessages(request,messages);
      
      request.setAttribute("operation",request.getParameter("operation")); 
      logger.debug(" operation : " + request.getParameter("operation"));
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();      
      request.setAttribute("operation",request.getParameter("operation")); 
      logger.debug(" operation : " + request.getParameter("operation"));
      return mapping.getInputForward();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting Action CustomerRegisterAction");  
    }
    return mapping.findForward("success");
  }
  
  
}
