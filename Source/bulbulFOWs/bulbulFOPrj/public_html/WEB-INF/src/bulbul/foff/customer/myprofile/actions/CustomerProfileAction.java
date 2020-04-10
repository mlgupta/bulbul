package bulbul.foff.customer.myprofile.actions;

import bulbul.foff.customer.myprofile.actionforms.CustomerProfileForm;
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

public class CustomerProfileAction extends Action  {
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
    DataSource dataSource = null;
    try{
      logger.info("Entering CustomerProfileAction");
      customerProfileForm=(CustomerProfileForm)form;
      
      if (isCancelled(request)){
        return mapping.findForward("success");
      }
      
      dataSource = getDataSource(request,"FOKey");
      if(request.getParameter("hdnIsNewProfile").equals(FOConstants.NO.toString())){
        Operations.editProfile(customerProfileForm,dataSource);
      }
      if(request.getParameter("hdnIsNewProfile").equals(FOConstants.YES.toString())){
        Operations.addProfile(customerProfileForm,dataSource);
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting CustomerProfileAction");
    }
    return mapping.findForward("success");
  }
}