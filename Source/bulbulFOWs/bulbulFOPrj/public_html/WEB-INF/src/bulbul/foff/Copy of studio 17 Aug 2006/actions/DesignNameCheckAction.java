package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
 * @version 1.0 21-Sep-2005
 * @author Sudheer V Pujar
 */
public class DesignNameCheckAction extends Action  {
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
    String customerDesignName="";
    String customerEmailId="";
    boolean result=false;
    DataSource dataSource=null;
    
    try {
      logger.info("Entering Design Name Check Action");
      customerDesignName=request.getParameter("customerDesignName");
      customerEmailId=request.getParameter("customerEmailId");
      dataSource=getDataSource(request,"FOKey");
      logger.info("DesignName is : " +customerDesignName );
      if(Operations.designNameExists(dataSource,customerDesignName,customerEmailId)){
        result=true;
      }
      logger.debug("result : " + result);
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      objOutStream.writeBoolean(result);
      objOutStream.flush();
      objOutStream.close();
    }catch (Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting Design Name Check Action");
    }
    
    return null;
  }
}