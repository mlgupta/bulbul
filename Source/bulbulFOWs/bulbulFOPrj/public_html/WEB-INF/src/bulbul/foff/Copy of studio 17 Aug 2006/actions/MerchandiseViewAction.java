package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import bulbul.foff.studio.common.MerchandiseViewBean;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @description 
 * @version 1.0 04-Nov-2005
 * @author Sudheer V. Pujar
 */
public class MerchandiseViewAction extends Action  {
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
  private static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    DataSource dataSource=null;
    String merchandiseTblPk="-1";
    MerchandiseViewBean merchandise=null;
    try {
      logger.info("Entering Merchandise View Action");
      if (request.getParameter("merchandiseTblPk")!=null){
        merchandiseTblPk=request.getParameter("merchandiseTblPk");
      }
      dataSource=getDataSource(request,"BOKey");
      logger.info("merchandiseTblPk is : " +merchandiseTblPk );
      merchandise=Operations.getMerchandiseView(dataSource,merchandiseTblPk);
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      logger.debug("Merchandise Name : " + merchandise.getMerhandiseName());
      objOutStream.writeObject(merchandise);
      objOutStream.flush();
      objOutStream.close();
    }catch (Exception e){
      logger.error(e);
      e.printStackTrace();
    }finally{
      logger.info("Exiting Clipart List Action");    
    }
    return null;
  }
}