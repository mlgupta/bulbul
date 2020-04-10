package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PricePromotionForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;

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


public class PricePromotionEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   PricePromotionForm pricePromotionForm=null;
   ActionMessages messages=new ActionMessages();
  try{
    logger.info("Entering PricePromotionEditAction");
    pricePromotionForm=(PricePromotionForm)form;
    request.setAttribute("hdnSearchPageNo",pricePromotionForm.getHdnSearchPageNo());
    if (isCancelled(request)){
      return mapping.findForward("success");
    }
    ServletContext context = servlet.getServletContext();
    DataSource dataSource = getDataSource(request,"BOKey");

    Operations.editPricePromotion(pricePromotionForm,dataSource);
    
   }catch(SQLException e){
     logger.error("Caught exception:" +e);
     if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique","Price Definition");
        messages.add("message1" ,msg);
      }
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in PricePromotion EditAction: "+e.getMessage());
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PricePromotionEditAction");
    }
    return mapping.findForward("success");
  }
}