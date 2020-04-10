package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PricePromotionForm;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PricePromotionAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try{
      logger.info("Entering PricePromotionAddB4Action");
      
      PricePromotionForm pricePromotionForm =new PricePromotionForm();
      pricePromotionForm.setTxtStartDate("");
      pricePromotionForm.setTxtEndDate("");
      pricePromotionForm.setTxtThresholdQty(0);
      pricePromotionForm.setTxtDiscount(0);
      pricePromotionForm.setRadExclusiveByPass(1);
      pricePromotionForm.setHdnMerchandiseTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseTblPk"))); 
      pricePromotionForm.setHdnMerchandiseSizeTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseSizeTblPk"))); 
      pricePromotionForm.setHdnMerchandiseCategoryTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseCategoryTblPk")));
      pricePromotionForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),pricePromotionForm); 
      
      return mapping.findForward("success");
    }catch(Exception e){
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PricePromotionAddB4Action");
    }
  }
}