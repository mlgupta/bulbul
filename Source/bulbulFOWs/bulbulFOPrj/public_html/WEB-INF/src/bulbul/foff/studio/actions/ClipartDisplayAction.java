package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

import bulbul.foff.studio.common.DesignElementProperty;

import java.awt.Dimension;

import java.io.File;
import java.io.IOException;

import java.net.InetAddress;
import java.net.URL;

import java.net.URLEncoder;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public final class ClipartDisplayAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    String dataSourceKey=null;
    String contentType=null;
    String contentSize=null;
    String contentOId=null;
    String clipartWidth=null;
    String clipartHeight=null;
    String style=null;
    float rotate=0.0f;
    double scale=1.0;
    Dimension clipartSize = new Dimension(70, 75);
    try{
      //logger.info("Entering Clipart Display Action ");
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
        clipartSize = new Dimension(Integer.parseInt(clipartWidth), Integer.parseInt(clipartHeight));
      }
      logger.debug("request.getHeader(\"REFERER\") : " + request.getHeader("REFERER"));
      logger.debug("request.getRequestURL() : "  + request.getRequestURL());
      logger.debug("request.getLocalName() : "  + request.getLocalName());
      logger.debug("request.getLocalAddr() : "  + request.getLocalAddr());
      logger.debug("request.getLocalPort() : "  +  request.getLocalPort());
      logger.debug("request.getContextPath() : " + request.getContextPath());
      logger.debug("request.getServerName() : " + request.getServerName());
      logger.debug("request.getServerPort() : " + request.getServerPort()); 
      logger.debug("InetAddress.getLocalHost().getHostAddress(): " + InetAddress.getLocalHost().getHostAddress()); 
      
//      String svgURL=request.getRequestURL().toString();
//      svgURL=svgURL.substring(0,svgURL.lastIndexOf("/")+1);
      
      
      String svgURL =  "http://";
      //svgURL +=request.getLocalAddr();
      svgURL +=InetAddress.getLocalHost().getHostAddress();
      svgURL +=":" + request.getLocalPort();
      svgURL +=request.getContextPath();
      svgURL +="/";
      svgURL +="svgDisplayAction.do?contentType="+URLEncoder.encode(contentType,"UTF-8");
      svgURL +="&contentSize="+URLEncoder.encode(contentSize,"UTF-8");
      svgURL +="&contentOId="+URLEncoder.encode(contentOId,"UTF-8");
      svgURL +="&dataSourceKey="+URLEncoder.encode(dataSourceKey,"UTF-8");
      
//      logger.degug("contentOId : "+ contentOId);
//      logger.degug("clipartWidth : "+ clipartWidth);
//      logger.degug("clipartHeight : "+ clipartHeight);
//      logger.degug("style : "+ style);
//      logger.degug("rotate : "+ rotate);
      
      Operations.svgToPNG(response,svgURL,clipartSize,style,rotate,scale,false); 
      
      
    }catch(Exception e ){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      //logger.info("Exiting Clipart Display Action ");
    }
    return null;
  }
  
  
}

