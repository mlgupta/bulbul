package bulbul.foff.pcatalogue.actions;

//import bulbul.boff.ca.merchand.actionforms.MerchandiseListForm;
//import bulbul.boff.ca.merchand.beans.Operations;
//import bulbul.boff.general.BOConstants;
//import bulbul.boff.general.Treeview;
//import bulbul.boff.user.beans.UserProfile;

import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.beans.ProductTreeView;
import bulbul.foff.pcatalogue.beans.Operations;

import java.io.IOException;

import java.lang.Integer;

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


public class ProductListAction extends Action  {
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    ArrayList products=null;
    ArrayList catNavList=null;
    int numberOfRecords=6;
    int pageNumber=1;
    String pageCount="1";
    int merchandiseCategoryTblPk=-1;
    String morcFlag="1";
    DataSource dataSource=null;
    try{
      logger.info("Entering ProductListAction");
      dataSource= getDataSource(request,"BOKey");
      httpSession=request.getSession(false);
      logger.info("Getting Data Source in ProductListAction");
      
      if (request.getParameter("morcTblPk")!=null){
        merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("morcTblPk"));
        logger.debug("morcTblPk : "+request.getParameter("morcTblPk"));
      }
      if (request.getParameter("morcFlag")!=null){
        morcFlag=request.getParameter("morcFlag");
        logger.debug("morcFlag : "+request.getParameter("morcFlag"));
      }
      if (request.getParameter("pageNumber")!=null){
        pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        logger.debug("pageNumber : "+request.getParameter("pageNumber"));
      }
      
      logger.debug("M Or C Flag Value: "+morcFlag);
      request.setAttribute("morcFlag",morcFlag);
      products=Operations.getProducts(merchandiseCategoryTblPk,dataSource,numberOfRecords,morcFlag,pageNumber);
      pageCount=Operations.pageCountString;
      catNavList=Operations.getCategoryNavigationList(merchandiseCategoryTblPk,dataSource);      
      request.setAttribute("merchandiseCategoryTblPk",Integer.toString(merchandiseCategoryTblPk));
      request.setAttribute("products", products);
      request.setAttribute("catNavList", catNavList);
      request.setAttribute("pageNumber", Integer.toString(pageNumber));
      request.setAttribute("pageCount", pageCount);
      if (httpSession.getAttribute("productTreeView")!=null){
        ProductTreeView productTreeView = (ProductTreeView)httpSession.getAttribute("productTreeView");
        if(productTreeView!=null) {
          productTreeView.forListNavigation(Integer.toString(merchandiseCategoryTblPk));
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
          logger.info("Exiting ProductListAction");
    }
    return mapping.findForward("success");
  }
  
}

