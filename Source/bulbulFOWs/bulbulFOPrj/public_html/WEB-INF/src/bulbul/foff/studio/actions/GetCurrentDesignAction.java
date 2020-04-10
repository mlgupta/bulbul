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

public class GetCurrentDesignAction extends Action {
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString()); 
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
    DataSource dataSourceBO=null;
    DataSource dataSourceFO=null;
    String merchandiseColorTblPk=null;
    String customerDesignTblPk=null;
    String xmlStringOutput="";
    
    try {
      logger.info("Entering GetCurrentDesignAction");
      merchandiseColorTblPk=request.getParameter("merchandiseColorTblPk");
      customerDesignTblPk=request.getParameter("customerDesignTblPk");
      logger.debug("merchandiseColorTblPk : " + merchandiseColorTblPk);
      logger.debug("customerDesignTblPk : " + customerDesignTblPk);
      dataSourceBO=getDataSource(request,"BOKey");
      dataSourceFO=getDataSource(request,"FOKey");
      xmlStringOutput=Operations.getCurrentDesign(dataSourceBO,dataSourceFO,merchandiseColorTblPk,customerDesignTblPk);
      logger.debug("xmlStringOutput : " + xmlStringOutput);
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      logger.info("Exiting GetCurrentDesignAction");
      response.setContentType("text/xml");
      //response.setHeader("Cache-Control", "no-cache");    
      response.getWriter().write(xmlStringOutput);
    }
    return null;
  }
}
