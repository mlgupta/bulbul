package bulbul.boff.ca.clipart.actions;

import bulbul.boff.ca.clipart.actionforms.ClipartListForm;
import bulbul.boff.ca.clipart.beans.Operations;
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


public class ClipartListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    ArrayList cliparts=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    ClipartListForm clipartListForm= (ClipartListForm)form;
    int clipartCategoryTblPk;
    ServletContext context=null;
    DataSource dataSource=null;
    try{
      logger.info("Entering ClipartListAction");
      logger.debug("clipartOrCategoryTblPk : "+(String)request.getParameter("clipartOrCategoryTblPk"));
      if(request.getAttribute("clipartCategoryTblPk")!=null){
        logger.debug("setting pk from getAttribute");
        clipartCategoryTblPk=Integer.parseInt((String)request.getAttribute("clipartCategoryTblPk"));
      }else if(((String)request.getParameter("clipartOrCategoryTblPk"))!=null){
        logger.debug("setting pk from getParameter");
        clipartCategoryTblPk=Integer.parseInt((String)request.getParameter("clipartOrCategoryTblPk"));
      }else{
        logger.debug("setting pk from ClipartListForm");
        clipartCategoryTblPk=clipartListForm.getHdnSearchClipartOrCategoryTblPk();
      }

      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
   
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in ClipartListAction");
      if (request.getAttribute("hdnSearchPageNo")!=null){
        clipartListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
      }
      cliparts=Operations.getCliparts(clipartCategoryTblPk,clipartListForm,dataSource,numberOfRecords);
      clipartListForm.setRadSearchSelect("");
      request.setAttribute("cliparts", cliparts);
      if (httpSession.getAttribute("clipartTreeView")!=null){
        Treeview clipartTreeView = (Treeview)httpSession.getAttribute("clipartTreeView");
        if(clipartTreeView!=null) {
          clipartTreeView.forListNavigation(Integer.toString(clipartCategoryTblPk));
        }
      }
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting ClipartListAction");
    }
    return mapping.findForward("success");
  }
}