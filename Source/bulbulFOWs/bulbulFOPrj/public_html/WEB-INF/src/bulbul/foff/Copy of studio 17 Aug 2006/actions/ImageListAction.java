package bulbul.foff.studio.actions;
import bulbul.foff.common.ImageFormat;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import bulbul.foff.studio.common.ImageBean;
import java.io.ObjectOutputStream;
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
 * @version 1.0 dd-Oct-2005
 * @author Sudheer V Pujar
 */
public class ImageListAction extends Action  {
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
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource=null;
    String customerEmailIdTblPk="-1";
    String format=ImageFormat.ALL.toString();
    ImageBean[] images=null;
    try {
      logger.info("Entering Image List Action");
      if (request.getParameter("customerEmailIdTblPk")!=null){
        customerEmailIdTblPk=request.getParameter("customerEmailIdTblPk");
      }
      
      if(request.getParameter("format")!=null){
        format=request.getParameter("format");
      }
      dataSource=getDataSource(request,"FOKey");
      logger.info("customerEmailIdTblPk is : " +customerEmailIdTblPk );
      images=Operations.getImages(dataSource,customerEmailIdTblPk,format);
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      logger.debug("Number Images " + images.length);
      objOutStream.writeObject(images);
      objOutStream.flush();
      objOutStream.close();
    }catch (Exception e){
      logger.error(e);
      e.printStackTrace();
    }finally{
      logger.info("Exiting Image List Action");    
    } 
    
    return null;
  }
}