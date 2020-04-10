package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetClipartsAction extends Action {
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
    int clipartCategoryTblPk=-1;
    String searchKeywords="";
    boolean search=false;
    int pageNumber=1;
    DataSource dataSource=null;
    logger.info("Entering GetClipartsAction");
    try{
      if(request.getParameter("clipartCategoryTblPk")!=null){
        clipartCategoryTblPk=Integer.parseInt(request.getParameter("clipartCategoryTblPk"));  
      }
      if(request.getParameter("searchKeywords")!=null){
        searchKeywords=request.getParameter("searchKeywords");  
      }
      if(request.getParameter("search")!=null){
        search=Boolean.getBoolean(request.getParameter("search"));  
      }
      if(request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));  
      }
      
      dataSource= getDataSource(request,"BOKey");
      xmlStringOutput=Operations.getClipartXMLString(clipartCategoryTblPk,dataSource,searchKeywords,search,pageNumber);
      logger.debug(" xmlStringOutput : " + xmlStringOutput);
    }catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    response.setContentType("text/xml");
    //response.setHeader("Cache-Control", "no-cache");    
    response.getWriter().write(xmlStringOutput);
    logger.info("Exiting GetClipartsAction");
    return null;
    
  }
}
