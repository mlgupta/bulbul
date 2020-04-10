package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;

import bulbul.foff.studio.actionforms.CustomImageUploadForm;

import bulbul.foff.studio.beans.Operations;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class CustomImageUploadAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {

    try {
      DataSource dataSource =null;
      String dataSourceKey="FOKey";
      int customerEmailIdTblPk=-1;
      FormFile imgFile = ((CustomImageUploadForm)form).getCustomImage();
      logger.debug("Form File : " + imgFile);
      dataSource = getDataSource(request,dataSourceKey);
      if (imgFile != null) {
        try {
          String result=Operations.customImageUpload(customerEmailIdTblPk,imgFile,dataSource);
          
          if(result!=null){
            String[] resultArray=result.split("~");
            logger.debug("Image Exists");
            String outString="<html>\n";
            outString+="<head>\n";
            outString+="<title>\n";
            outString+="untitled\n";
            outString+="</title>\n";
            outString+="</head>\n";
            outString+="<body onload=\" return parent.addImage();\" >\n";
            outString+="<form>\n";
            outString+="<input id=\"hdnCustomerGraphicsTblPk\" name=\"hdnCustomerGraphicsTblPk\" type=\"hidden\" value=\" " + resultArray[0] + "\">\n";
            outString+="<input id=\"hdnContentOId\" name=\"hdnContentOId\" type=\"hidden\" value=\" " + resultArray[1] + "\">\n";
            outString+="<input id=\"hdnContentType\" name=\"hdnContentType\" type=\"hidden\" value=\" " + resultArray[2] + "\">\n";
            outString+="<input id=\"hdnContentSize\" name=\"hdnContentSize\" type=\"hidden\" value=\" " + resultArray[3] + "\">\n";
            outString+="</form>\n";
            outString+="</body>\n";
            outString+="</html>\n";
            response.getWriter().print(outString);
          }
         
        } catch (Exception e) {
          logger.error(e);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
