package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;

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


public class MerchandiseEditB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("***Entering MerchandiseEditB4Action***");
    
    ArrayList printableAreas=null;
    MerchandiseForm merchandiseForm =null;
    int merchandiseTblPk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    ArrayList size4List=null;
    ArrayList merchandiseDecorations=null;
    int merchandiseCategoryTblPk=-1;
    HttpSession httpSession=null;
    if(isCancelled(request)){
     return mapping.getInputForward();
    }
    try{
      if (request.getParameter("radSearchSelect")!=null) {
        merchandiseTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      if (request.getParameter("hdnSearchMerchandiseOrCategoryTblPk")!=null) {
        merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("hdnSearchMerchandiseOrCategoryTblPk"));
      }
      context=servlet.getServletContext();
      httpSession=request.getSession(false);
      dataSource= getDataSource(request,"BOKey");
      merchandiseForm=Operations.viewMerchandise(merchandiseTblPk,merchandiseCategoryTblPk,dataSource);
      merchandiseForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      size4List=Operations.getSize(dataSource,merchandiseForm.getHdnMerchandiseCategoryTblPk());
      merchandiseDecorations=Operations.getDecorations(dataSource);
      printableAreas=Operations.getSelectedActivePrintableAreas(merchandiseTblPk,dataSource);
      
      request.setAttribute(mapping.getAttribute(),merchandiseForm);
      
      logger.debug("size4List"+ size4List);
      logger.debug("merchandiseDecorations" + merchandiseDecorations);
      logger.debug("printableAreas" + printableAreas);
      
      logger.debug("size4List Size"+ size4List.size());
      logger.debug("merchandiseDecorations Size" + merchandiseDecorations.size());
      logger.debug("printableAreas Size" + printableAreas.size());
      
      httpSession.setAttribute("size4List",size4List);
      httpSession.setAttribute("merchandiseDecorations",merchandiseDecorations);
      httpSession.setAttribute("printableAreas",printableAreas);
      
      httpSession.setAttribute("hdnMerchandiseColorTblPk",merchandiseForm.getHdnMerchandiseColorTblPk()); 
      httpSession.setAttribute("hdnMerchandiseColorOperation",merchandiseForm.getHdnMerchandiseColorOperation()); 
      httpSession.setAttribute("hdnColorOneValue",merchandiseForm.getHdnColorOneValue()); 
      httpSession.setAttribute("hdnColorTwoValue",merchandiseForm.getHdnColorTwoValue()); 
      httpSession.setAttribute("hdnColorOneName",merchandiseForm.getHdnColorOneName()); 
      httpSession.setAttribute("hdnColorTwoName",merchandiseForm.getHdnColorTwoName());
      httpSession.setAttribute("hdnColorIsActive",merchandiseForm.getHdnColorIsActive());
      httpSession.setAttribute("hdnColorIsActiveDisplay",merchandiseForm.getHdnColorIsActiveDisplay()); 
      httpSession.setAttribute("hdnMerchandiseSizeTblPk",merchandiseForm.getHdnMerchandiseSizeTblPk());                               
      httpSession.setAttribute("hdnMerchandiseSizeOperation",merchandiseForm.getHdnMerchandiseSizeOperation()); 
      httpSession.setAttribute("hdnSizeValue",merchandiseForm.getHdnSizeValue());                               
      httpSession.setAttribute("hdnSizeText",merchandiseForm.getHdnSizeText());                               
      httpSession.setAttribute("hdnPriceValue",merchandiseForm.getHdnPriceValue());                               
      httpSession.setAttribute("hdnSizeIsActive",merchandiseForm.getHdnSizeIsActive()); 
      httpSession.setAttribute("hdnSizeIsActiveDisplay",merchandiseForm.getHdnSizeIsActiveDisplay()); 
      httpSession.setAttribute("hdnColorSNo",merchandiseForm.getHdnColorSNo()); 
      
    
    }catch(Exception e){
     logger.error("exception:"+e);
     return mapping.getInputForward();
    }
    logger.info("***Exiting MerchandiseEditB4Action***");
    return mapping.findForward("success");
  }
}