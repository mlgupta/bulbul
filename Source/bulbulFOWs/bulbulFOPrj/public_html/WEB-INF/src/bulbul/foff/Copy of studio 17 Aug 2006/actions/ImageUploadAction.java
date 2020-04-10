package bulbul.foff.studio.actions;
import bulbul.foff.common.Base64;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * 
 * @description 
 * @version 1.0 27-Oct-2005
 * @author Sudheer V Pujar
 */
public class ImageUploadAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @description 
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  Logger logger= Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource=null;
    String customerEmailIdTblPk="-1";
    String fileTitle ="";
    String fileDescription ="";
    String contentType="";
    String category="";
    int contentSize=0;
    byte[] content=null;
    String contentString=null;
    
    if (request.getParameter("customerEmailIdTblPk")!=null){
      customerEmailIdTblPk=request.getParameter("customerEmailIdTblPk");
    }
    
    if (request.getParameter("fileTitle")!=null){
      fileTitle=request.getParameter("fileTitle");
    }
    
    if (request.getParameter("fileDescription")!=null){
      fileDescription=request.getParameter("fileDescription");
    }
    
    if (request.getParameter("contentType")!=null){
      contentType=request.getParameter("contentType");
    }
    
    if (request.getParameter("contentSize")!=null){
      contentSize=Integer.parseInt(request.getParameter("contentSize"));
    }
    
    if (request.getParameter("category")!=null){
      category=request.getParameter("category");
    }
    
    if (request.getParameter("content")!=null){
      contentString=request.getParameter("content");
    }
    
    try {
      boolean isImageUploaded=false;
      if (contentString!=null){
        content=Base64.decode(contentString);
        dataSource = getDataSource(request,"FOKey");
        isImageUploaded=Operations.uploadImage(dataSource,customerEmailIdTblPk,fileTitle,fileDescription,contentType,contentSize,content,category);
      }
      
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      logger.debug("Image Uploaded is " + isImageUploaded);
      objOutStream.writeBoolean(isImageUploaded);
      objOutStream.flush();
      objOutStream.close();
      
    }catch (SQLException e) {
     e.printStackTrace(); 
    }catch (Exception e) {
     e.printStackTrace(); 
    }
    return null;
  }
}