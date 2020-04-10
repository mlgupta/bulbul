package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseListForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.Treeview;
import bulbul.boff.user.beans.UserProfile;

import java.io.IOException;

import java.lang.Integer;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class MerchandiseListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    ArrayList merchandises=null;
    int numberOfRecords;
    int flag=0;
    UserProfile userProfile=null;
    MerchandiseListForm merchandiseListForm= (MerchandiseListForm)form;
    int merchandiseCategoryTblPk=-1;
    
    logger.info("Entering MerchandiseListAction");

    if(request.getAttribute("merchandiseCategoryTblPk")!=null){
      logger.debug("setting pk from getAttribute");
      merchandiseCategoryTblPk=Integer.parseInt((String)request.getAttribute("merchandiseCategoryTblPk"));
    }else if(((String)request.getParameter("merchandiseOrCategoryTblPk"))!=null){
      logger.debug("setting pk from getParameter");
      merchandiseCategoryTblPk=Integer.parseInt((String)request.getParameter("merchandiseOrCategoryTblPk"));
    }else{
      logger.debug("setting pk from merchandiseListForm");
      merchandiseCategoryTblPk=merchandiseListForm.getHdnSearchMerchandiseOrCategoryTblPk();
    }
    try{
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
    
      ServletContext context=servlet.getServletContext();
      DataSource dataSource= getDataSource(request,"BOKey");

      logger.info("Getting Data Source in MerchandiseListAction");
      if(request.getAttribute("hdnSearchPageNo")!=null){
        merchandiseListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
      }
      flag=Operations.getFlag(merchandiseCategoryTblPk,dataSource);
      logger.debug("Flag Value: "+flag);
      merchandiseListForm.setHdnSearchFlag(flag);
      merchandises=Operations.getMerchandises(merchandiseCategoryTblPk,merchandiseListForm,dataSource,numberOfRecords);

      merchandiseListForm.setRadSearchSelect("");
      request.setAttribute("merchandises", merchandises);
      
      if (httpSession.getAttribute("merchandiseTreeView")!=null){
        Treeview merchandiseTreeView = (Treeview)httpSession.getAttribute("merchandiseTreeView");
        if(merchandiseTreeView!=null) {
          merchandiseTreeView.forListNavigation(Integer.toString(merchandiseCategoryTblPk));
        }
      }

    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
          logger.info("Exiting MerchandiseListAction");
    }
    return mapping.findForward("success");
  }
}