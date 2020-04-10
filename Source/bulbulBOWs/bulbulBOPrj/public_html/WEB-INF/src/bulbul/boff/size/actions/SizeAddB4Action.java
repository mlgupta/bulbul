package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeForm;
import bulbul.boff.size.beans.Operations;

import java.io.IOException;

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


public class SizeAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    SizeForm sizeForm =new SizeForm();
    int sizeTypeTblPk=-1;
    ServletContext context=null;
    DataSource dataSource=null;
    try{
      logger.info("Entering SizeAddB4Action");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      if(request.getParameter("hdnSizeTypeTblPk")!=null){
        sizeTypeTblPk=Integer.parseInt(request.getParameter("hdnSizeTypeTblPk"));
      }
      sizeForm.setHdnSizeTypeTblPk(sizeTypeTblPk);
      sizeForm.setTxtSizeId("");
      sizeForm.setTxaSizeDesc("");
      sizeForm.setTxtSizeTypeId(Operations.getSizeTypeId(sizeTypeTblPk,dataSource));
      sizeForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      sizeForm.setHdnSizeTypePageNo(request.getParameter("hdnSizeTypePageNo"));
      request.setAttribute(mapping.getAttribute(),sizeForm); 
    }catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting SizeAddB4Action");
    }
    return mapping.findForward("success");
  }
}