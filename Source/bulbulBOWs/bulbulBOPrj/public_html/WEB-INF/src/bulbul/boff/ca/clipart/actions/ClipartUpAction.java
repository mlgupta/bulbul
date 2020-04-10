package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.beans.Operations;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import java.lang.Integer;

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


public class ClipartUpAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int clipartCategoryTblPk=-1;
    try{
      logger.info("***Entering ClipartUpAction***");
      if(((String)request.getParameter("clipartOrCategoryTblPk"))!=null){
        logger.debug("***setting pk from getParameter");
        clipartCategoryTblPk=Integer.parseInt(request.getParameter("clipartOrCategoryTblPk"));
      }

      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      clipartCategoryTblPk = Operations.getClipartCategoryFk(clipartCategoryTblPk,dataSource); 
      
      request.setAttribute("clipartCategoryTblPk",Integer.toString(clipartCategoryTblPk));
      logger.info("Getting Data Source in ClipartUpAction");
      
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartUpAction");
    }
    return mapping.findForward("success");
  }
}