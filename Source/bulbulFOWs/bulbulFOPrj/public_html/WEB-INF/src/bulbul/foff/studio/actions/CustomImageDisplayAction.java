package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;

import bulbul.foff.general.LOOperations;
import bulbul.foff.studio.beans.Operations;

import bulbul.foff.studio.common.DesignElementProperty;

import java.awt.Dimension;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CustomImageDisplayAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSource =null;
    String dataSourceKey=null;
    String contentType=null;
    String contentSize=null;
    String contentOId=null;
    String clipartWidth=null;
    String clipartHeight=null;
    String style=null;
    float rotate=0.0f;
    double scale=1.0;
    Dimension imageSize = new Dimension(70, 75);
    try{
      //logger.info("Entering Custom Image Display Action ");
      
      dataSourceKey=request.getParameter(DesignElementProperty.DATASOURCE_KEY) ;
      contentOId=request.getParameter(DesignElementProperty.OID) ;
      contentType=request.getParameter(DesignElementProperty.CONTENT_TYPE);
      contentSize=request.getParameter(DesignElementProperty.CONTENT_SIZE);
      clipartWidth=request.getParameter(DesignElementProperty.WIDTH) ;
      clipartHeight=request.getParameter(DesignElementProperty.HEIGHT) ;
      style=request.getParameter(DesignElementProperty.STYLE) ;
      scale=request.getParameter(DesignElementProperty.SCALE)!=null?Double.parseDouble(request.getParameter(DesignElementProperty.SCALE)):1.0;
      rotate=request.getParameter(DesignElementProperty.ROTATE)!=null?Float.parseFloat(request.getParameter(DesignElementProperty.ROTATE)):0.0f ;
      
      if(clipartWidth!=null && clipartHeight!=null){
        imageSize = new Dimension(Integer.parseInt(clipartWidth), Integer.parseInt(clipartHeight));
      }
      dataSource = getDataSource(request,dataSourceKey);
      byte[] content = LOOperations.getLargeObjectContent(Integer.parseInt(contentOId.trim()), dataSource);
      
      Operations.svgImageDisplay(response,content,contentType,imageSize,style,rotate,scale);
      
    }catch(Exception e ){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      //logger.info("Exiting Custom Image Display Action ");
       return null;
    }
    
  }
}
