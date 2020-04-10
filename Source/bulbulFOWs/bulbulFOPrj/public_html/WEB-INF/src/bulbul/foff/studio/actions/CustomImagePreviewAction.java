package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.studio.actionforms.CustomImageUploadForm;

import bulbul.foff.studio.beans.Operations;

import java.awt.Dimension;

import java.io.IOException;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class CustomImagePreviewAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    InputStream is = null;
    String style=null;
    float rotate=0.0f;
    double scale=1.0;
    Dimension imageSize = new Dimension(70, 75);
    try {
      FormFile imgFile = ((CustomImageUploadForm)form).getCustomImage();
      logger.debug("Form File : " + imgFile);
      if (imgFile != null) {
        try {
          int contentSize=imgFile.getFileData().length; 
          String contentType=imgFile.getContentType();
          byte[] content= new byte[contentSize];
          is = imgFile.getInputStream(); 
          is.read(content,0,contentSize); 
          Operations.svgImageDisplay(response,content,contentType,imageSize,style,rotate,scale);
          logger.debug("Image Exists");
        } catch (Exception e) {
          logger.error(e);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (is!=null){
        logger.debug("Closing Input Stream" );
        is.close(); 
        logger.debug("Input Stream Closed" );        
      }
      
      return null;
    }
    
  }
}
