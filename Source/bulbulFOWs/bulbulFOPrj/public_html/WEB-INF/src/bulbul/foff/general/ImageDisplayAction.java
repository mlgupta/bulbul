package bulbul.foff.general;

import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.general.LOOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ImageDisplayAction extends Action  {
  private Logger logger =Logger.getLogger(FOConstants.LOGGER.toString());;
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String contentType=null;
    String contentSize=null;
    String imgOid=null;
    String dataSourceKey=null;
    byte[] content=null;
    DataSource dataSource =null;
    try{
      logger.info("Entering Image Display Action ");
      dataSourceKey=request.getParameter("dataSourceKey");
      
      imgOid=request.getParameter("imageOid") ;
      contentType=request.getParameter("imageContentType");
      contentSize=request.getParameter("imageContentSize");
      
      logger.info("imageOid : "+ imgOid);
      logger.info("contentSize : "+ contentSize);
      logger.info("dataSourceKey : "+ dataSourceKey);

      dataSource = getDataSource(request,dataSourceKey);
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