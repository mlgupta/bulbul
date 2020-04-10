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

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class MerchandiseViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("***Entering MerchandiseViewAction***");
    ArrayList printableAreas=null;
    
    MerchandiseForm merchandiseForm =null;
    int merchandiseTblPk=-1;
    int merchandiseCategoryTblPk=-1;
    DataSource dataSource=null;
    ServletContext context=null;
    ArrayList merchandiseDecorations=null;

    try{
      merchandiseForm=(MerchandiseForm)form;
      merchandiseCategoryTblPk=merchandiseForm.getHdnMerchandiseCategoryTblPk();         
      request.setAttribute("merchandiseCategoryTblPk",Integer.toString(merchandiseCategoryTblPk));
      request.setAttribute("hdnSearchPageNo",merchandiseForm.getHdnSearchPageNo());
      if(isCancelled(request)){
        return mapping.getInputForward();
      }
      if (request.getParameter("radSearchSelect")!=null) {
        merchandiseTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
      }
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      merchandiseForm=Operations.viewMerchandise(merchandiseTblPk,merchandiseCategoryTblPk,dataSource);
      merchandiseForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      merchandiseDecorations=Operations.getDecorations(dataSource);
      printableAreas=Operations.getSelectedActivePrintableAreas(merchandiseTblPk,dataSource);
      
      
      request.setAttribute(mapping.getAttribute(),merchandiseForm);
      
      request.setAttribute("printableAreas",printableAreas);
      request.setAttribute("merchandiseDecorations",merchandiseDecorations);
      request.setAttribute("hdnMerchandiseColorTblPk",merchandiseForm.getHdnMerchandiseColorTblPk()); 
      request.setAttribute("hdnMerchandiseColorOperation",merchandiseForm.getHdnMerchandiseColorOperation()); 
      request.setAttribute("hdnColorOneValue",merchandiseForm.getHdnColorOneValue()); 
      request.setAttribute("hdnColorTwoValue",merchandiseForm.getHdnColorTwoValue()); 
      request.setAttribute("hdnColorOneName",merchandiseForm.getHdnColorOneName()); 
      request.setAttribute("hdnColorTwoName",merchandiseForm.getHdnColorTwoName());
      request.setAttribute("hdnColorIsActive",merchandiseForm.getHdnColorIsActive());
      request.setAttribute("hdnColorIsActiveDisplay",merchandiseForm.getHdnColorIsActiveDisplay()); 
      request.setAttribute("hdnMerchandiseSizeTblPk",merchandiseForm.getHdnMerchandiseSizeTblPk());                               
      request.setAttribute("hdnMerchandiseSizeOperation",merchandiseForm.getHdnMerchandiseSizeOperation()); 
      request.setAttribute("hdnSizeValue",merchandiseForm.getHdnSizeValue());                               
      request.setAttribute("hdnSizeText",merchandiseForm.getHdnSizeText());                               
      request.setAttribute("hdnPriceValue",merchandiseForm.getHdnPriceValue());                               
      request.setAttribute("hdnSizeIsActive",merchandiseForm.getHdnSizeIsActive()); 
      request.setAttribute("hdnSizeIsActiveDisplay",merchandiseForm.getHdnSizeIsActiveDisplay()); 
      request.setAttribute("hdnColorSNo",merchandiseForm.getHdnColorSNo()); 

   }catch(Exception e){
     logger.error("exception:"+e);
     return mapping.getInputForward();
   }
   logger.info("***Exiting merchandiseViewAction***");
   return mapping.findForward("success");
  }
}