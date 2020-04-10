package bulbul.foff.studio.actions;
import bulbul.foff.general.FOConstants;

import bulbul.foff.studio.beans.Operations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProductCustomiseB4Action extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String merchandiseTblPk="";
    String merchandiseColorTblPk="";
    DataSource datasource; 
    try{
      logger.info("Entering Product Customize B4 Action");
      datasource=getDataSource(request,"BOKey");
      if(request.getParameter("merchandiseTblPk")!=null){
        merchandiseTblPk=request.getParameter("merchandiseTblPk");
      }else{
        merchandiseTblPk=Operations.getDefaultMerchandiseTblPk(datasource);
      }
      if(request.getParameter("merchandiseColorTblPk")!=null){
        merchandiseColorTblPk=request.getParameter("merchandiseColorTblPk");    
      }else{
        merchandiseColorTblPk=Operations.getDefaultMerchandiseColorTblPk(datasource,merchandiseTblPk);
      }

      logger.debug("merchandiseTblPk : " + merchandiseTblPk);
      logger.debug("merchandiseColorTblPk : " + merchandiseColorTblPk);

      request.setAttribute("merchandiseTblPk",merchandiseTblPk);
      request.setAttribute("merchandiseColorTblPk",merchandiseColorTblPk);
      
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting Product Customize B4 Action");
    }
    return mapping.findForward("success");
  }
}