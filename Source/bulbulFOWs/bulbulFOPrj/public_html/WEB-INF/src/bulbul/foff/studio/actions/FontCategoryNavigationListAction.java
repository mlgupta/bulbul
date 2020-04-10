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

public class FontCategoryNavigationListAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    String xmlStringOutput="";
    int fontCategoryTblPk=-1;
    DataSource dataSource=null;
    logger.info("Entering FontCategoryNavigationListAction");
    try{
      if(request.getParameter("fontCategoryTblPk")!=null){
        fontCategoryTblPk=Integer.parseInt(request.getParameter("fontCategoryTblPk"));  
      }
      dataSource= getDataSource(request,"BOKey");
      xmlStringOutput=Operations.getFontCategoryNavigationListXML(fontCategoryTblPk,dataSource);
      logger.debug(" xmlStringOutput : " + xmlStringOutput);
    }catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    response.setContentType("text/xml");
    //response.setHeader("Cache-Control", "no-cache");    
    response.getWriter().write(xmlStringOutput);
    logger.info("Exiting FontCategoryNavigationListAction");
    return null;
    
  }
}
