package bulbul.boff.general;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.General;
import bulbul.boff.general.LOOperations;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ImageDisplayAction extends Action  {
  private Logger logger =Logger.getLogger(BOConstants.LOGGER.toString());;
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String contentType=null;
    String contentSize=null;
    String imgOid=null;
    byte[] content=null;
    DataSource dataSource =null;
    try{
      logger.info("Entering Image Display Action ");
      
      ServletContext context = servlet.getServletContext();
      dataSource = getDataSource(request,"BOKey");
      
      imgOid=request.getParameter("imageOid") ;
      contentType=request.getParameter("imageContentType");
      contentSize=request.getParameter("imageContentSize");
      
      logger.info("imageOid : "+ imgOid);
      logger.info("contentSize : "+ contentSize);
      
      if (imgOid!=null){
        content=LOOperations.getLargeObjectContent(Integer.parseInt(imgOid),dataSource); 
        General.imageDisplay(content,contentType,Integer.parseInt(contentSize),response); 
      }
      
    }catch(Exception e ){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting Image Display Action ");
    }
    return null;
  }
}