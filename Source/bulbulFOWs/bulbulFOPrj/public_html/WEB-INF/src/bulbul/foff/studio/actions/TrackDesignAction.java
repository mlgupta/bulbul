package bulbul.foff.studio.actions;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.actionforms.TrackDesignForm;
import bulbul.foff.studio.beans.Operations;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class TrackDesignAction extends Action  {
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
    CustomerInfo customerInfo=null; 
    HttpSession  httpSession = null;
    DataSource dataSource=null;
    TrackDesignForm trackDesignForm=null;
    
    try{
      logger.info("Entering  Track Design Action");
      messages=new ActionMessages();
      httpSession=request.getSession(false);
      customerInfo = (CustomerInfo) httpSession.getAttribute("customerInfo");
      
      dataSource=getDataSource(request,"FOKey");
      trackDesignForm=(TrackDesignForm)form;
      if(Operations.getDesign(trackDesignForm,dataSource,customerInfo)){
        httpSession.setAttribute("customerInfo",customerInfo);
        return mapping.findForward("success");
      }else{
        msg=new ActionMessage("msg.NoDesign",trackDesignForm.getTxtDesignCode(),trackDesignForm.getTxtYourEmailId());
        messages.add("messageNoDesign",msg);
        request.setAttribute("operation",request.getParameter("operation")); 
        logger.debug(" operation : " + request.getParameter("operation"));
      }      
    }catch(SQLException e){
        logger.error(e);
        e.printStackTrace();
    }catch(Exception e){
        logger.error(e);
        e.printStackTrace();
    }finally{      
      httpSession.setAttribute("customerInfo",customerInfo);
      logger.info("Exiting  Track Design Action");
    }
    saveMessages(request,messages);
    return mapping.getInputForward();
  }
}