package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myprofile.actionforms.CustomerLoginForm;
import bulbul.foff.customer.myprofile.beans.Operations;
import bulbul.foff.general.FOConstants;

import java.io.IOException;

import java.sql.SQLException;

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

public class CustomerLoginAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ActionMessages messages=null;
    ActionMessage msg=null;
    String targetForward=null;
    CustomerInfo customerInfo=null; 
    HttpSession  httpSession = null;
    DataSource dataSource=null;
    CustomerLoginForm customerLoginForm=null;
    String enteredEmailId=null;
    String enteredPassword=null;
    
    try{
      logger.info("Entering  Login Action");
      messages=new ActionMessages();
      httpSession=request.getSession(false);
      customerInfo = (CustomerInfo) httpSession.getAttribute("customerInfo");
      
      dataSource=getDataSource(request,"FOKey");
      customerLoginForm=(CustomerLoginForm)form;
      enteredEmailId=customerLoginForm.getTxtLoginEmailId();
      enteredPassword=customerLoginForm.getTxtLoginPassword();
      if(Operations.validityCheck(enteredEmailId,enteredPassword,dataSource,customerInfo)){
        httpSession.setAttribute("customerInfo",customerInfo);
        /* This Code, Redirect the based on targetForward value */       
        /* Keep This Code Allways at Bottom of this try block */        
        targetForward=customerInfo.getTargetUrl();
        logger.debug("targetForward : " + targetForward);
        customerInfo.setTargetUrl(null);      
        if (targetForward!=null){
          response.sendRedirect(targetForward);
          return null;
        }        
        return mapping.findForward("success");
      }else{
        msg=new ActionMessage("msg.LoginFailed");
        messages.add("messageLoginFailed",msg);
        request.setAttribute("operation",request.getParameter("operation")); 
        logger.debug(" operation : " + request.getParameter("operation"));
      }
      
//

      /* This Code, Redirect the based on targetForward value */       
      /* Keep This Code Allways at Bottom of this try block */       
/*      targetForward=customerInfo.getTargetUrl();
      logger.debug("targetForward : " + targetForward);
      customerInfo.setTargetUrl(null);      
      if (targetForward!=null){
        response.sendRedirect(targetForward);
        return null;
      }
*/      
    }catch(SQLException e){
        logger.error(e);
        e.printStackTrace();
    }catch(Exception e){
        logger.error(e);
        e.printStackTrace();
    }finally{      
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting  Login Action");
    }
    saveMessages(request,messages);
    return mapping.getInputForward();
  }
}