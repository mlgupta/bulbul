package bulbul.foff.customer.myprofile.actions;


import bulbul.foff.customer.myprofile.actionforms.CustomerProfileForm;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;

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

public class ImagePreviewAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    FormFile  imgFile = ((CustomerProfileForm)form).getFleProfileImage();
    Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
    logger.debug("Form File" + imgFile);
    if(imgFile!=null){
      try{
        General.imagePreview(imgFile,response);
      }catch(Exception e){
        logger.error(e);
      }
    }
    return null;
  }
}