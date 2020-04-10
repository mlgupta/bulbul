package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.beans.Operations;
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


public class FontUpAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int fontCategoryTblPk=-1;
    ServletContext context=null;
    DataSource dataSource= null;
    try{
      logger.info("Entering FontUpAction***");
      if(request.getParameter("fontOrCategoryTblPk")!=null){
        logger.debug("setting pk from getParameter");
        fontCategoryTblPk=Integer.parseInt(request.getParameter("fontOrCategoryTblPk"));
      }

      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      fontCategoryTblPk = Operations.getFontCategoryFk(fontCategoryTblPk,dataSource); 
      
      request.setAttribute("fontCategoryTblPk",Integer.toBinaryString(fontCategoryTblPk));
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontUpAction");
    }
    return mapping.findForward("success");
  }
}