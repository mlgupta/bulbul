package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;
import bulbul.boff.size.beans.Operations;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class SizeDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int sizeTblPk=-1;
    String sizeId=null;
    ActionMessages messages=new ActionMessages();

    sizeTblPk=Integer.parseInt( request.getParameter("radSearchSelect"));
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info(" Entering SizeDeleteAction ");
    try{

      Operations.deleteSize(sizeTblPk,dataSource);  
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          sizeId=Operations.getSizeId(sizeTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error in getSizeId"+ex);
        }
        
        ActionMessage msg=new ActionMessage("msg.Refered",sizeId);
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);  
      
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      logger.info(" Exiting SizeDeleteAction ");
    }
    
    return mapping.findForward("success");
  }
 
}