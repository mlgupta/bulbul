package bulbul.foff.customer.myprofile.actions;
import bulbul.foff.customer.myprofile.actionforms.ChangePasswordForm;
import bulbul.foff.general.FOConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int customerEmailIdTblPk=-1;
    

    ChangePasswordForm cpForm=new ChangePasswordForm();
    
    try{
      logger.info("***Entering ChangePasswordB4Action***");
      
      if(request.getParameter("hdnCustomerEmailIdTblPk")!=null){
        customerEmailIdTblPk=Integer.parseInt(request.getParameter("hdnCustomerEmailIdTblPk"));
        
        cpForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
        cpForm.setTxtOldPassword("");
        cpForm.setTxtNewPassword("");
        cpForm.setTxtConfirmPassword("");
        
        request.setAttribute(mapping.getAttribute(),cpForm);
      }
    }catch(Exception e){
        logger.error("Exception Caught: "+e);
    }finally{
      logger.info("***Exiting ChangePasswordB4Action***");  
    }

    return mapping.findForward("success");
  }
}