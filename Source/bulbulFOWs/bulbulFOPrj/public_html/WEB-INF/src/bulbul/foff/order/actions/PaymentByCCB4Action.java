package bulbul.foff.order.actions;

import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.order.beans.Operations;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class PaymentByCCB4Action extends Action  {
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
    ArrayList years=null;
    try{
      logger.info("Entering PaymentByCCB4Action");
      dataSource=getDataSource(request,"BOKey");
      years=General.getYears4CCOrDC();
      request.setAttribute("years",years);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting PaymentByCCB4Action");
    }
    return mapping.findForward("success");
  }

}