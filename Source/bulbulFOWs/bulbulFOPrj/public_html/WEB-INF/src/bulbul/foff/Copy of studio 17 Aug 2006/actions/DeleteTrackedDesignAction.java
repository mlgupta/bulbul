package bulbul.foff.studio.actions;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.ErrorConstants;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class DeleteTrackedDesignAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String customerDesignTblPk=null;
    String outletId=null;
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo  customerInfo=null;
    ActionMessage msg=null;
    ActionMessages messages=null; 
    try{
      logger.info("Entering DeleteTrackedDesignAction");
      messages = new ActionMessages();
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerDesignTblPk=request.getParameter("customerDesignTblPk");
      dataSource = getDataSource(request,"FOKey");
      Operations.deleteTrackedDesign(customerDesignTblPk,dataSource);  
      //request.setAttribute("designName",customerInfo.getCustomerDesign().getDesignName());
      //request.setAttribute("designCode",customerInfo.getCustomerDesign().getDesignCode());
      //customerInfo.setCustomerDesign(null);
      

    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      if(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())>-1){
        msg=new ActionMessage("msg.DesignInOrder");
        messages.add("messageDesignInOrder",msg);
      }
      saveMessages(request,messages);
      //response.sendRedirect("trackB4Action.do?operation=design");
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      mapping.getInputForward();
    }finally{
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting DeleteTrackedDesignAction");
    }
    return mapping.findForward("success");
  }
}