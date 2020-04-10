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

public class DeleteDesignElementAction extends Action {
  /**This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
   Logger logger = Logger.getLogger(FOConstants.LOGGER.toString()); 
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws IOException, ServletException {
     DataSource dataSource=null;
     
     int customerDesignDetailTblPk=-1;
     int elementId=-1;
     String xmlStringOutput="";
     
     try {
       logger.info("Entering DeleteDesignElementAction");
       customerDesignDetailTblPk=Integer.parseInt(request.getParameter("customerDesignDetailTblPk"));
       elementId=Integer.parseInt(request.getParameter("elementId"));
       logger.debug("customerDesignDetailTblPk : " + customerDesignDetailTblPk);
       logger.debug("elementId : " + elementId);
       dataSource=getDataSource(request,"FOKey");
       xmlStringOutput=Operations.deleteDesignElement(dataSource,customerDesignDetailTblPk,elementId);
       logger.debug("xmlStringOutput : " + xmlStringOutput);
     }catch (Exception e) {
       e.printStackTrace();
     }finally{
       logger.info("Exiting DeleteDesignElementAction");
       response.setContentType("text/xml");
       //response.setHeader("Cache-Control", "no-cache");    
       response.getWriter().write(xmlStringOutput);
     }
     return null;
   }
}
