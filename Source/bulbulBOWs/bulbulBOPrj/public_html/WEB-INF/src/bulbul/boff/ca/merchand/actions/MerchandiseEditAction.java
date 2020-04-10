package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.ErrorConstants;

import java.io.IOException;

import java.sql.SQLException;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class MerchandiseEditAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseForm merchandiseForm=null;
    String merchandiseCategoryTblPk=null;
    ActionMessages messages=new ActionMessages();
    HttpSession httpSession=null; 
    ServletContext context=null;
    DataSource dataSource = null;
    logger.info("Entering MerchandiseEditAction");
    try{
      httpSession=request.getSession(false);
      merchandiseForm=(MerchandiseForm)form;
      merchandiseCategoryTblPk=Long.toString(merchandiseForm.getHdnMerchandiseCategoryTblPk());         
      if(merchandiseCategoryTblPk!=null){
        request.setAttribute("merchandiseCategoryTblPk",merchandiseCategoryTblPk);
      }
      request.setAttribute("hdnSearchPageNo",merchandiseForm.getHdnSearchPageNo());
      if (isCancelled(request)){
        httpSession.removeAttribute("size4List");
        httpSession.removeAttribute("merchandiseDecorations");
        httpSession.removeAttribute("hdnMerchandiseColorTblPk"); 
        httpSession.removeAttribute("hdnMerchandiseColorOperation"); 
        httpSession.removeAttribute("hdnColorOneValue"); 
        httpSession.removeAttribute("hdnColorTwoValue"); 
        httpSession.removeAttribute("hdnColorOneName"); 
        httpSession.removeAttribute("hdnColorTwoName");
        httpSession.removeAttribute("hdnColorIsActive");
        httpSession.removeAttribute("hdnColorIsActiveDisplay"); 
        httpSession.removeAttribute("hdnMerchandiseSizeTblPk");                               
        httpSession.removeAttribute("hdnMerchandiseSizeOperation"); 
        httpSession.removeAttribute("hdnSizeValue");                               
        httpSession.removeAttribute("hdnSizeText");                               
        httpSession.removeAttribute("hdnPriceValue");                               
        httpSession.removeAttribute("hdnSizeIsActive"); 
        httpSession.removeAttribute("hdnSizeIsActiveDisplay"); 
        httpSession.removeAttribute("hdnColorSNo"); 
        return mapping.findForward("cancel");
      }
     
      context = servlet.getServletContext();
      
      dataSource = getDataSource(request,"BOKey");
      Operations.editMerchandise(merchandiseForm,dataSource);

      httpSession.removeAttribute("size4List");
      httpSession.removeAttribute("hdnMerchandiseColorTblPk"); 
      httpSession.removeAttribute("hdnMerchandiseColorOperation"); 
      httpSession.removeAttribute("hdnColorOneValue"); 
      httpSession.removeAttribute("hdnColorTwoValue"); 
      httpSession.removeAttribute("hdnColorOneName"); 
      httpSession.removeAttribute("hdnColorTwoName");
      httpSession.removeAttribute("hdnColorIsActive");
      httpSession.removeAttribute("hdnColorIsActiveDisplay"); 
      httpSession.removeAttribute("hdnMerchandiseSizeTblPk");                               
      httpSession.removeAttribute("hdnMerchandiseSizeOperation"); 
      httpSession.removeAttribute("hdnSizeValue");                               
      httpSession.removeAttribute("hdnSizeText");                               
      httpSession.removeAttribute("hdnPriceValue");                               
      httpSession.removeAttribute("hdnSizeIsActive"); 
      httpSession.removeAttribute("hdnSizeIsActiveDisplay"); 
      httpSession.removeAttribute("hdnColorSNo"); 

    }catch(SQLException e){
     logger.error("Caught exception:" +e);
     if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
        ActionMessage msg=new ActionMessage("msg.Unique",merchandiseForm.getTxtMerchandise());
        messages.add("message1" ,msg);
        //saveMessages(request,messages);
      }
      
      saveMessages(request,messages);
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      e.printStackTrace();
      logger.error("Exception Caught in MerchandiseEditAction: "+e.getMessage());
      return mapping.getInputForward();
    }
    logger.info("Exiting MerchandiseEditAction");
    return mapping.findForward("success");
  }
}