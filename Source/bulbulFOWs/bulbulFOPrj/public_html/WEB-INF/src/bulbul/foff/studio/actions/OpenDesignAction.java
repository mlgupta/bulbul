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

public class OpenDesignAction extends Action {
   /**
    * This is the main action called from the Struts framework.
    * @param mapping The ActionMapping used to select this instance.
    * @param form The optional ActionForm bean for this request.
    * @param request The HTTP Request we are processing.
    * @param response The HTTP Response we are processing.
    * @description 
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
    * @return 
    */
   Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     String xmlStringOutput="";
     String customerDesignName="";
     String customerEmailId="";
     String customerDesignCode="";
     DataSource dataSource=null;
     
     try {
       logger.info("Entering Open Design Action");
       customerDesignCode=request.getParameter("customerDesignCode");
       customerDesignName=request.getParameter("customerDesignName");
       customerEmailId=request.getParameter("customerEmailId");
       dataSource=getDataSource(request,"FOKey");
       logger.info("DesignName is : " +customerDesignName );
       xmlStringOutput=Operations.openDesign(dataSource,customerDesignCode,customerDesignName,customerEmailId);
       logger.debug("xmlStringOutput : \n" + xmlStringOutput);
       
     }catch (Exception e){
       logger.error(e);
     }finally{
       response.setContentType("text/xml");
       //response.setHeader("Cache-Control", "no-cache");    
       response.getWriter().write(xmlStringOutput);
       logger.info("Exiting Open Design Action");
       return null;
     }
   }
}
