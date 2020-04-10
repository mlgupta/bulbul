package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseCategoryForm;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


public class MerchandiseCategoryPreviewAction extends Action  {
  Logger logger =Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering MerchandiseCategoryPreviewAction");  
    FormFile  categoryImgFile = ((MerchandiseCategoryForm)form).getCategoryImgFile();
    Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
    logger.debug("Form File" + categoryImgFile);
    if(categoryImgFile!=null){
      try{
        General.imagePreview(categoryImgFile,response);
      }catch(Exception e){
        logger.error(e);
      }finally{
        logger.info("Exiting MerchandiseCategoryPreviewAction");
      }
    }
    return null;
  }
}