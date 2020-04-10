package bulbul.foff.pcatalogue.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.actionforms.AddToFavouriteForm;

import bulbul.foff.pcatalogue.beans.Operations;
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

public class AddToFavouriteAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    AddToFavouriteForm addToFavouriteForm=null;
    DataSource dataSource=null;
    
    try{
      logger.info("Entering AddToFavouriteAction");
      
      addToFavouriteForm=(AddToFavouriteForm)form;
      dataSource=getDataSource(request,"FOKey");
      Operations.addFavourite(addToFavouriteForm,dataSource);
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting AddToFavouriteAction");
    }
    return mapping.findForward("success");
  }
}