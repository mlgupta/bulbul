package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import bulbul.foff.studio.common.ClipartBean;
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
 * @version 1.0 06-Oct-2005
 * @author Sudheer V Pujar
 */
public class ClipartListAction extends Action  {
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
    String clipartCategoryTblPk="-1";
    String searchKeywords="";
    boolean search=false;
    ClipartBean[] cliparts=null;
    try {
      logger.info("Entering Clipart List Action");
      if (request.getParameter("clipartCategoryTblPk")!=null){
        clipartCategoryTblPk=request.getParameter("clipartCategoryTblPk");
      }
      if(request.getParameter("search")!=null){
        search=new Boolean(request.getParameter("search")).booleanValue();
      }
      
      if(request.getParameter("searchKeywords")!=null){
        searchKeywords=request.getParameter("searchKeywords");
      }
      dataSource=getDataSource(request,"BOKey");
      logger.info("clipartCategoryTblPk is : " +clipartCategoryTblPk );
      cliparts=Operations.getCliparts(dataSource,clipartCategoryTblPk,search,searchKeywords);
      ObjectOutputStream objOutStream=new ObjectOutputStream(response.getOutputStream());
      logger.debug("Number Categories" + cliparts.length);
      objOutStream.writeObject(cliparts);
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