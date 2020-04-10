package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.actionforms.OrderItemForm;
import bulbul.foff.order.beans.Operations;
import bulbul.foff.order.beans.PreOrderBean;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OrderCreateB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String customerDesignTblPk=null;
    String designOId=null;
    String designContentType=null;
    String designContentSize=null;
    PreOrderBean preOrderBean=null;
    DataSource dataSourceFO=null;
    HttpSession httpSession=null;
    DataSource dataSourceBO=null;
    
    try{
      logger.info("Entering OrderCreateB4Action");
      
      
      httpSession=request.getSession(false);
      dataSourceFO=getDataSource(request,"FOKey");
      dataSourceBO=getDataSource(request,"BOKey");
      
      customerDesignTblPk=request.getParameter("customerDesignTblPk");
      
      preOrderBean=Operations.createOrderB4(customerDesignTblPk,dataSourceFO,dataSourceBO);
      
      logger.debug("designOId :"+preOrderBean.getDesignOId());
      logger.debug("designContentType :"+preOrderBean.getDesignContentType());
      logger.debug("designContentSize :"+preOrderBean.getDesignContentSize());
      
      
      httpSession.removeAttribute("productBean");
      request.setAttribute("preOrderBean",preOrderBean);
      
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
    }finally{
      logger.info("Exiting OrderCreateB4Action");
    }
    return mapping.findForward("success");
  }
}