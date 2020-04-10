package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myprofile.actionforms.CustomerProfileForm;
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

public class CustomerProfileB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    CustomerProfileForm customerProfileForm=null;
    int customerEmailIdTblPk=-1;
    HttpSession httpSession=null;
    DataSource dataSource=null;
    CustomerInfo customerInfo=null;
    try{
      logger.info("Entering CustomerProfileB4Action");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      logger.info("customerEmailIdTblPk: " + customerEmailIdTblPk );
            
      dataSource=getDataSource(request,"FOKey");
      customerProfileForm=Operations.viewProfile(customerEmailIdTblPk,dataSource);
      if(customerProfileForm==null){
        customerProfileForm=new CustomerProfileForm();
        customerProfileForm.setHdnIsNewProfile(FOConstants.YES.toString());
        customerProfileForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
        customerProfileForm.setCboCustomerTitle("");
        customerProfileForm.setTxtFirstName("");
        customerProfileForm.setTxtLastName("");
        customerProfileForm.setTxtAge("");
        customerProfileForm.setCboGender("");
        customerProfileForm.setHdnImgOId("");
        customerProfileForm.setHdnContentType("");
        customerProfileForm.setHdnContentSize(-1);
        customerProfileForm.setTxtAddress1("");
        customerProfileForm.setTxtAddress2("");
        customerProfileForm.setTxtAddress3("");
        customerProfileForm.setTxtCity("");
        customerProfileForm.setTxtPincode("");
        customerProfileForm.setCboState("");  
        customerProfileForm.setTxtState("");
        customerProfileForm.setHdnIsStateListed(0);
        customerProfileForm.setCboCountry("");  
        customerProfileForm.setTxtCountry("");
        customerProfileForm.setHdnIsCountryListed(0);
        customerProfileForm.setTxtPhone1("");
        customerProfileForm.setTxtPhone2("");
        customerProfileForm.setTxtMobile("");
        customerProfileForm.setChkUseAddress4Shipping(0);
        if(Operations.isNewsLetterSelected(customerEmailIdTblPk,dataSource)){
          customerProfileForm.setChkNewsLetter(1);  
        }else{
          customerProfileForm.setChkNewsLetter(0);
        }        
      }
      request.setAttribute(mapping.getAttribute(),customerProfileForm);
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      mapping.getInputForward();
    }finally{
      logger.info("Exiting CustomerProfileB4Action");
    }
    return mapping.findForward("success");
  }
}