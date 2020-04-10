package bulbul.foff.order.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.order.beans.Operations;
import bulbul.foff.order.beans.PreOrderBean;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OrderModifyB4Action extends Action  {
  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String orderDetailTblPk=null;
    String designOId=null;
    String designContentType=null;
    String designContentSize=null;
    PreOrderBean preOrderBean=null;
    DataSource dataSource=null;
    //OrderItemForm orderItemForm=null;
    //DataSource dataSourceBO=null;
    
    try{
      logger.info("Entering OrderModifyB4Action");
      
      //orderItemForm=new OrderItemForm();
      
      dataSource=getDataSource(request,"BOKey");
      //dataSourceBO=getDataSource(request,"BOKey");
      
      orderDetailTblPk=request.getParameter("orderDetailTblPk");
      
      preOrderBean=Operations.modifyOrderB4(orderDetailTblPk,dataSource);
      
      logger.debug("designOId :"+preOrderBean.getDesignOId());
      logger.debug("designContentType :"+preOrderBean.getDesignContentType());
      logger.debug("designContentSize :"+preOrderBean.getDesignContentSize());
      
      
      //request.setAttribute(mapping.getAttribute(),orderItemForm);
      request.setAttribute("preOrderBean",preOrderBean);
      
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
    }finally{
      logger.info("Exiting OrderModifyB4Action");
    }
    return mapping.findForward("success");
  }
}