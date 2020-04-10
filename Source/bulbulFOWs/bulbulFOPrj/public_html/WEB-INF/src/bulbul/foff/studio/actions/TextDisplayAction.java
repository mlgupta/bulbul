package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

import bulbul.foff.studio.common.DesignElementProperty;

import java.awt.Dimension;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URL;

import java.net.URLEncoder;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TextDisplayAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    String text=null;
    String style=null;
    float rotate=0.0f;
    double scale=1.0;
    Dimension textSize = new Dimension(80, 30);
    try{
//      logger.info("Entering Text Display Action ");
      
      text=request.getParameter(DesignElementProperty.TEXT) ;
      style=request.getParameter(DesignElementProperty.STYLE) ;
      scale=request.getParameter(DesignElementProperty.SCALE)!=null?Double.parseDouble(request.getParameter(DesignElementProperty.SCALE)):1.0;
      rotate=request.getParameter(DesignElementProperty.ROTATE)!=null?Float.parseFloat(request.getParameter(DesignElementProperty.ROTATE)):0.0f ;
      
      String svgURL =  "http://";
      svgURL +=InetAddress.getLocalHost().getHostAddress();
      svgURL +=":" + request.getLocalPort();
      svgURL +=request.getContextPath();
      svgURL +="/text2SVGDisplayAction.do";
      
      svgURL=new URL(svgURL).toExternalForm();
      svgURL+="?"+DesignElementProperty.TEXT+"="+URLEncoder.encode(text,"UTF-8");
      if(style!=null){
        svgURL+="&"+ DesignElementProperty.STYLE+ "="+URLEncoder.encode(style,"UTF-8");
      }
      svgURL+="&"+DesignElementProperty.ROTATE+""+URLEncoder.encode(Float.toString(rotate),"UTF-8");
      
//      logger.debug("text : "+ text);
//      logger.debug("style : "+ style);
//      logger.debug("rotate : "+ rotate);
      
      Operations.svgToPNG(response,svgURL,textSize,style,rotate,scale,true); 

    }catch(Exception e ){
      logger.error(e.getMessage());
      //e.printStackTrace();
    }finally{
//      logger.info("Exiting Text Display Action ");
    }
    return null;
  }
}
