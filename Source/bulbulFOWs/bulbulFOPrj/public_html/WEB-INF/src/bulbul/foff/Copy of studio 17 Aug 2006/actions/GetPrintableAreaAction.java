package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import bulbul.foff.studio.common.PrintableAreaBean;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.sql.DataSource;
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
import org.apache.log4j.Logger;
/**
 * 
 * @description 
 * @version 1.0 13-Dec-2005
 * @author Sudheer V Pujar
 */
 
public class GetPrintableAreaAction extends Action  {
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
    String merchandiseTblPk="-1";
    PrintableAreaBean[] printableAreas=null;
    try {
      logger.info("Entering Get Printable Area Action");
      if (request.getParameter("merchandiseTblPk")!=null){
        merchandiseTblPk=request.getParameter("merchandiseTblPk");
      }
      
      dataSource=getDataSource(request,"BOKey");
      logger.info("merchandiseTblPk is : " +merchandiseTblPk );
      printableAreas=Operations.getPrintableAreas(dataSource,merchandiseTblPk);
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      logger.debug("Number of Printable Areas " + printableAreas.length);
      objOutStream.writeObject(printableAreas);
      objOutStream.flush();
      objOutStream.close();
    }catch (Exception e){
      logger.error(e);
      e.printStackTrace();
    }finally{
      logger.info("Exiting Get Printable Area Action");    
    } 
    return null;
  }
}