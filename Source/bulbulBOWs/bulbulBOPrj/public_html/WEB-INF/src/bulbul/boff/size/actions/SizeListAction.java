package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeListForm;
import bulbul.boff.size.beans.Operations;
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


public class SizeListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=request.getSession(false);
    ArrayList sizes=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    SizeListForm sizeListForm= (SizeListForm)form;
    int sizeTypeTblPk=-1;
    ServletContext context=null;
    DataSource dataSource=null;
    try{
      logger.info("Entering SizeListAction");
      if(request.getParameter("hdnSizeTypeTblPk")!=null){
        sizeTypeTblPk=Integer.parseInt(request.getParameter("hdnSizeTypeTblPk"));
      }
      
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in SizeListAction");
      if(request.getAttribute("hdnSearchPageNo")!=null){
        sizeListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo")); 
      }      
      sizes=Operations.getSizes(sizeTypeTblPk,sizeListForm,dataSource,numberOfRecords);
      sizeListForm.setRadSearchSelect("");
      
      if (request.getParameter("hdnSizeTypePageNo")!=null){
        sizeListForm.setHdnSizeTypePageNo(request.getParameter("hdnSizeTypePageNo"));
      }
      if(request.getAttribute("hdnSizeTypePageNo")!=null){
        sizeListForm.setHdnSizeTypePageNo((String)request.getAttribute("hdnSizeTypePageNo")); 
      }
      
      request.setAttribute("sizes", sizes);
    }
    catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting SizeListAction");
    }
    return mapping.findForward("success");
  }
}