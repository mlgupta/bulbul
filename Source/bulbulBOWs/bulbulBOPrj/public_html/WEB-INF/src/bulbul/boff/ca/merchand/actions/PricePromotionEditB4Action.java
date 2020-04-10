package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PricePromotionForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

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


public class PricePromotionEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   logger.info("Inside editb4action");
   PricePromotionForm pricePromotionForm =null;
   String pricePromotionTblPk=null;
   DataSource dataSource=null;
   ServletContext context=null;

   if(isCancelled(request)){
     return mapping.getInputForward();
   }
   try{
    logger.info("Entering PricePromotionEditB4Action");
    pricePromotionTblPk=request.getParameter("radSearchSelect");
    context=servlet.getServletContext();
    dataSource= getDataSource(request,"BOKey");
    pricePromotionForm=Operations.viewPricePromotion(pricePromotionTblPk,dataSource);
    pricePromotionForm.setHdnMerchandiseTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseTblPk"))); 
    pricePromotionForm.setHdnMerchandiseSizeTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseSizeTblPk")));
    pricePromotionForm.setHdnMerchandiseCategoryTblPk(Integer.parseInt(request.getParameter("hdnMerchandiseCategoryTblPk")));
    pricePromotionForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    request.setAttribute(mapping.getAttribute(),pricePromotionForm); 
   }catch(Exception e){
     logger.error("exception:"+e);
     return mapping.getInputForward();
   }finally{
    logger.info("Exiting PricePromotionEditB4Action");
   }
   return mapping.findForward("success");
  }
}