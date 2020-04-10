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


public class SizeTypeDeleteAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int sizeTypeTblPk=-1;
    String sizeTypeId=null;
    ActionMessages messages=new ActionMessages();
    
    if(request.getParameter("radSearchSelect")!=null){
      sizeTypeTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");
    logger.info(" Entering SizeTypeDeleteAction ");
    try{

      Operations.deleteSizeType(sizeTypeTblPk,dataSource);  
    }catch(SQLException e){
      logger.debug(ErrorConstants.REFERED.getErrorValue());
      logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
      if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
        try{
          sizeTypeId=Operations.getSizeTypeId(sizeTypeTblPk,dataSource);
        }catch(Exception ex){
          logger.error("Error in getSizeTypeId"+ex);
        }
        
        ActionMessage msg=new ActionMessage("msg.Refered",sizeTypeId);
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);  
      
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      logger.info(" Exiting SizeTypeDeleteAction ");
    }
    
    return mapping.findForward("success");
  }
 
}