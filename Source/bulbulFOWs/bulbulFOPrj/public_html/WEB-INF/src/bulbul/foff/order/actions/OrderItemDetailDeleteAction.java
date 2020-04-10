package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.beans.Operations;
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

public class OrderItemDetailDeleteAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int orderItemDetailTblPk=-1;
    DataSource dataSource=null;
    try{
      logger.info("Entering OrderItemDetailDeleteAction");
      logger.debug("orderItemDetailTblPk : "+request.getParameter("orderItemDetailTblPk"));
      dataSource=getDataSource(request,"BOKey");
      orderItemDetailTblPk=Integer.parseInt(request.getParameter("orderItemDetailTblPk"));
      Operations.deleteOrderItemDetail(orderItemDetailTblPk,dataSource);
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting OrderItemDetailDeleteAction");
    }
    return mapping.findForward("success");
  }
}