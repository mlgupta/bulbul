package bulbul.foff.studio.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

import bulbul.foff.studio.common.DesignElementProperty;

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

public class Text2SVGDisplayAction extends Action {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    
    String style=null;;
    String text=null;
    try{
//      logger.info("Entering Text  To SVG Display Action ");
      text=request.getParameter(DesignElementProperty.TEXT);
      style=request.getParameter(DesignElementProperty.STYLE);
      
//      logger.debug("style : "+ style);
//      logger.debug(DesignElementProperty.TEXT + " :  " + text);
      
      Operations.text2SVGDisplay(response,text,style);       
      
    }catch(Exception e ){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
//      logger.info("Exiting Text To SVG  Display Action ");
    }
    return null;
  }
}
