package bulbul.foff.customer.myfavourite.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.customer.myfavourite.beans.Operations;
import bulbul.foff.general.FOConstants;
import java.io.IOException;

import java.sql.SQLException;
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

public class MyFavouriteListAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList products=null;
    ArrayList categories=null;
    int numberOfRecords=6;
    int pageNumber=1;
    String pageCount="1";
    int customerEmailIdTblPk=-1;
    String category="-1";
    HttpSession httpSession=null;
    CustomerInfo customerInfo=null;
    
    try{
      logger.info("Entering MyFavouriteListAction");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      customerEmailIdTblPk=customerInfo.getCustomerEmailIdTblPk();
      
      DataSource dataSource= getDataSource(request,"FOKey");

      logger.info("Getting Data Source in ProductListAction");
      
      if (request.getParameter("category")!=null){
        category=request.getParameter("category");
        logger.debug("category"+request.getParameter("category"));
      }
      
      if (request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
      
      products=Operations.getProducts(customerEmailIdTblPk,category,dataSource,numberOfRecords,pageNumber);
      pageCount=Operations.pageCountString;
      categories=Operations.getCategories(customerEmailIdTblPk,dataSource);
      
      request.setAttribute("products", products);
      request.setAttribute("categories",categories);
      request.setAttribute("category",category);
      request.setAttribute("pageNumber", Integer.toString(pageNumber));
      request.setAttribute("pageCount", pageCount);
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MyFavouriteListAction");
    }
    return mapping.findForward("success");
  }
  
}