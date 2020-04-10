package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
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


public class MerchandiseDesignImgPreviewAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseForm merchandiseForm=null;
    merchandiseForm=(MerchandiseForm)form;
    FormFile  printableAreaImgFile=null;
    FormFile[] printableAreaImgFileArray=null;
    String index=null;
    index=request.getParameter("index");
    printableAreaImgFile=merchandiseForm.getPrintableAreaImgFile(Integer.parseInt(index));
    
    Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
    logger.debug("Form File" + printableAreaImgFile);
    if(printableAreaImgFile!=null){
      try{
        General.imagePreview(printableAreaImgFile,response);
      }catch(Exception e){
        logger.error(e);
      }
    }
    return null;
  }
}