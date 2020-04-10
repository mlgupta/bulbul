package bulbul.foff.pcatalogue.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;

import bulbul.foff.pcatalogue.actionforms.AddToFavouriteForm;
import bulbul.foff.pcatalogue.beans.Operations;
import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddToFavouriteB4Action extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    AddToFavouriteForm addToFavouriteForm=null;
    ArrayList categories=null;
    DataSource dataSource=null;
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    int customerEmailIdTblPk=-1;
    try{
      logger.info("Entering AddToFavouriteB4Action ");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      addToFavouriteForm=new AddToFavouriteForm();
      dataSource=getDataSource(request,"FOKey");
      logger.debug("merchandiseTblPk : "+request.getParameter("merchandiseTblPk"));

      if(request.getParameter("merchandiseTblPk")!=null){
        addToFavouriteForm.setHdnMerchandiseTblPk(Integer.parseInt(request.getParameter("merchandiseTblPk")));
      }
      addToFavouriteForm.setCboCategory("");
      addToFavouriteForm.setTxtCategory("");
      addToFavouriteForm.setHdnCustomerEmailIdTblPk(customerEmailIdTblPk);
      categories=Operations.getCategories(addToFavouriteForm,dataSource);
      request.setAttribute("categories",categories);
      request.setAttribute(mapping.getAttribute(),addToFavouriteForm);
      
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting AddToFavouriteB4Action ");
    }
    return mapping.findForward("success");
  }
}