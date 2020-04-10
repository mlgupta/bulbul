package bulbul.foff.customer.mycreativity.actions;
import bulbul.foff.customer.mycreativity.beans.Operations;
import bulbul.foff.general.ErrorConstants;
import bulbul.foff.general.FOConstants;
import java.sql.SQLException;
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

public class MyCreativityDeleteAction extends Action  {
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
    ActionMessage msg=null;
    ActionMessages messages=null;
    try{
      logger.info("Entering MyCreativityDeleteAction");
      messages=new ActionMessages();
      customerDesignTblPk=request.getParameter("customerDesignTblPk");
      dataSource = getDataSource(request,"FOKey");
      Operations.deleteCustomerDesign(customerDesignTblPk,dataSource);  
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      if(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())>-1){
        msg=new ActionMessage("msg.DesignInOrder");
        messages.add("messageDesignInOrder",msg);
      }
      saveMessages(request,messages);
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting MyCreativityDeleteAction");
    }
    return mapping.findForward("success");
  }
}