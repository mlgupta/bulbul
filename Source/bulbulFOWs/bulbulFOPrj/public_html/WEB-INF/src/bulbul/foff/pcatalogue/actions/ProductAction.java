package bulbul.foff.pcatalogue.actions;

import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.beans.Operations;
import bulbul.foff.pcatalogue.beans.ProductBean;
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

public class ProductAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String merchandiseTblPk=null;
    String merchandiseCategoryTblPk=null;
    String morcFlag="1";
    String pageNumber="1";
    String category=null;
    ProductBean productDetail=null;
    DataSource dataSource= null;
    try{
      logger.info("Entering ProductAction");
      
      //ServletContext context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in ProductListAction");

      if (request.getParameter("merchandiseTblPk")!=null){
        merchandiseTblPk=request.getParameter("merchandiseTblPk");
        logger.debug("merchandiseTblPk: "+request.getParameter("merchandiseTblPk"));
      }
      if (request.getParameter("merchandiseCategoryTblPk")!=null){
        merchandiseCategoryTblPk=request.getParameter("merchandiseCategoryTblPk");
        logger.debug("merchandiseCategoryTblPk: "+request.getParameter("merchandiseCategoryTblPk"));
      }
      if (request.getParameter("category")!=null){
        category=request.getParameter("category");
        logger.debug("merchandiseCategoryTblPk: "+request.getParameter("merchandiseCategoryTblPk"));
      }
      if (request.getParameter("morcFlag")!=null){
        morcFlag=request.getParameter("morcFlag");
        logger.debug("morcFlag : "+request.getParameter("morcFlag"));
      }
      if (request.getParameter("pageNumber")!=null){
        pageNumber=request.getParameter("pageNumber");
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
      
      productDetail=Operations.getProductDetails(merchandiseTblPk,dataSource);
      request.setAttribute("productDetail",productDetail);
      request.setAttribute("merchandiseCategoryTblPk",merchandiseCategoryTblPk);
      request.setAttribute("category",category);
      request.setAttribute("morcFlag",morcFlag);
      request.setAttribute("pageNumber",pageNumber);
      
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ProductAction");
    }
    return mapping.findForward("success");
  }
}