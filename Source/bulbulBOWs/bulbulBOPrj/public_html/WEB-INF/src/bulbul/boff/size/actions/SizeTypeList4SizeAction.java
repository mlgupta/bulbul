package bulbul.boff.size.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.size.actionforms.SizeTypeListForm;
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


public class SizeTypeList4SizeAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    ArrayList sizeTypes=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    SizeTypeListForm sizeTypeListForm= null;
    ServletContext context=null;
    DataSource dataSource=null;
    
    try{
      logger.info("Entering SizeTypeList4SizeAction");
      httpSession=request.getSession(false);
      sizeTypeListForm= (SizeTypeListForm)form;
      if (request.getParameter("hdnSizeTypePageNo")!=null){
        sizeTypeListForm.setHdnSearchPageNo(request.getParameter("hdnSizeTypePageNo"));
      }
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      context=servlet.getServletContext();
      dataSource=getDataSource(request,"BOKey");
      logger.info("Getting Data Source in SizeTypeList4SizeAction");
      sizeTypes=Operations.getSizeTypes(sizeTypeListForm,dataSource,numberOfRecords);
      request.setAttribute("sizeTypes", sizeTypes);
      if (request.getParameter("currentSizeTypeTblPk")!=null){
        request.setAttribute("currentSizeTypeTblPk",request.getParameter("currentSizeTypeTblPk"));
      }else{
        request.setAttribute("currentSizeTypeTblPk","-1");
      }
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting SizeTypeList4SizeAction");
    }
    return mapping.findForward("success");
  }
}