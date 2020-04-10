package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.beans.Operations;
import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChangeOutletCityAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public static Logger  logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource  dataSource=null;
    ArrayList outlets=null;
    String city=null;
    try{
      logger.info("Entering ChangeOutletCityAction Action");
      dataSource=getDataSource(request,"BOKey");
      city=request.getParameter("city");
      outlets=Operations.getOutlets(dataSource,city);
      request.setAttribute("outlets",outlets);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting ChangeOutletCityAction Action");
    }
    return mapping.findForward("success");
  }
}