package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.beans.Operations;
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


public class MerchandiseUpAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int merchandiseCategoryTblPk=-1;
    try{
      logger.info("Entering MerchandiseUpAction");
      if(request.getParameter("merchandiseOrCategoryTblPk")!=null){
        logger.debug("***setting pk from getParameter");
        merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("merchandiseOrCategoryTblPk"));
      }

      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      merchandiseCategoryTblPk = Operations.getMerchandiseCategoryFk(merchandiseCategoryTblPk,dataSource); 
      
      request.setAttribute("merchandiseCategoryTblPk",Integer.toString(merchandiseCategoryTblPk));
      logger.info("Getting Data Source in MerchandiseUpAction");
      
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MerchandiseUpAction");
    }
    return mapping.findForward("success");
  }
}