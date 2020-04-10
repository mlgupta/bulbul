package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PricePromotionListForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.user.beans.UserProfile;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PricePromotionListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList pricePromotions=null;
    ArrayList merchandiseSizeColors=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    HttpSession httpSession=null;
    ServletContext context=null;
    DataSource dataSource=null;
    PricePromotionListForm pricePromotionListForm=null;
    logger.info("Entering PricePromotion List Action");
    int merchandiseTblPk=-1;
    String merchandiseName=null;
    int merchandiseSizeTblPk=-1;
    int merchandiseCategoryTblPk=-1;

    try{
      httpSession=request.getSession(false);
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
          
      context=servlet.getServletContext();
      logger.info("Getting Data Source in PricePromotion List Action");
      dataSource= getDataSource(request,"BOKey");
      
      pricePromotionListForm= (PricePromotionListForm)form;


      if (request.getParameter("hdnMerchandiseTblPk")!=null) {
        merchandiseTblPk=Integer.parseInt(request.getParameter("hdnMerchandiseTblPk"));
      }
      merchandiseName=Operations.getMerchandiseName(merchandiseTblPk,dataSource);
      if (request.getParameter("hdnMerchandiseCategoryTblPk")!=null) {
        merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("hdnMerchandiseCategoryTblPk"));
      }
      if (request.getParameter("hdnMerchandiseSizeTblPk")!=null) {
        merchandiseSizeTblPk=Integer.parseInt(request.getParameter("hdnMerchandiseSizeTblPk"));
      }
      
      logger.debug("merchandiseTblPk " + merchandiseTblPk);
      logger.debug("merchandiseName " + merchandiseName);
      
      pricePromotionListForm.setHdnMerchandiseTblPk(merchandiseTblPk);    
      pricePromotionListForm.setHdnMerchandiseName(merchandiseName);    
      pricePromotionListForm.setHdnMerchandiseSizeTblPk(merchandiseSizeTblPk);    
      pricePromotionListForm.setHdnMerchandiseCategoryTblPk(merchandiseCategoryTblPk);   
      if(request.getAttribute("hdnSearchPageNo")!=null){
        pricePromotionListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
      }
      pricePromotions=Operations.getPricePromotions(pricePromotionListForm,dataSource,numberOfRecords);
      pricePromotionListForm.setRadSearchSelect("");
      
      merchandiseSizeColors=Operations.getMerchandiseSizeColor(merchandiseTblPk,dataSource);
      request.setAttribute("merchandiseSizeColors", merchandiseSizeColors);
      request.setAttribute("pricePromotions", pricePromotions);
    }
    catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PricePromotion List Action");
    }
    return mapping.findForward("success");
  }
}