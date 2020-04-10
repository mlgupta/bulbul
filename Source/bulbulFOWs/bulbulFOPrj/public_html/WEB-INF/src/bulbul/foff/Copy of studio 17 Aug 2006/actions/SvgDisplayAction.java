package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.LOOperations;
import bulbul.foff.studio.beans.Operations;
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
public class SvgDisplayAction extends Action  {
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
  private Logger logger =Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String contentType=null;
    String contentSize=null;
    String contentOId=null;
    String dataSourceKey=null;
    byte[] content=null;
    DataSource dataSource =null;
    try{
      logger.info("Entering SVG Display Action ");
      dataSourceKey=request.getParameter("dataSourceKey");
      contentOId=request.getParameter("contentOId") ;
      contentType=request.getParameter("contentType");
      contentSize=request.getParameter("contentSize");
      
      logger.info("contentOId : "+ contentOId);
      logger.info("contentSize : "+ contentSize);
      logger.info("dataSourceKey : "+ dataSourceKey);

      dataSource = getDataSource(request,dataSourceKey);
      if (contentOId!=null){
        content=LOOperations.getLargeObjectContent(Integer.parseInt(contentOId),dataSource); 
        Operations.svgDisplay(content,contentType,Integer.parseInt(contentSize),response); 
      }
      
    }catch(Exception e ){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting SVG Display Action ");
    }
    return null;
  }
}