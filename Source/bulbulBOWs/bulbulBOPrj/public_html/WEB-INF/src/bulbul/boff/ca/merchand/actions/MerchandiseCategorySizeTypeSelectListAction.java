package bulbul.boff.ca.merchand.actions;

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


public class MerchandiseCategorySizeTypeSelectListAction extends Action  {
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
      logger.info("Entering MerchandiseCategorySizeTypeSelectListAction");
      httpSession=request.getSession(false);
      sizeTypeListForm= (SizeTypeListForm)form;
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      context=servlet.getServletContext();
      dataSource=getDataSource(request,"BOKey");
      logger.info("Getting Data Source in SizeTypeSelectListAction");
      sizeTypes=Operations.getSizeTypes(sizeTypeListForm,dataSource,numberOfRecords);
      sizeTypeListForm.setRadSearchSelect("");
      request.setAttribute("hdnControlId4SizeTypeList",request.getParameter("hdnControlId4SizeTypeList")); 
      request.setAttribute("hdnControlId4SizeTypeTblPk",request.getParameter("hdnControlId4SizeTypeTblPk")); 
      request.setAttribute("hdnControlId4SizeTypeId",request.getParameter("hdnControlId4SizeTypeId")); 
      request.setAttribute("hdnControlId4MerchandiseCategorySizeTypeTblPk",request.getParameter("hdnControlId4MerchandiseCategorySizeTypeTblPk")); 
      request.setAttribute("hdnControlId4SizeTypeOperation",request.getParameter("hdnControlId4SizeTypeOperation")); 
      request.setAttribute("sizeTypes", sizeTypes);
      request.setAttribute("sizeTypeListForm",sizeTypeListForm); //TODO Check this replace with some other methode
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting MerchandiseCategorySizeTypeSelectListAction");
    }
    return mapping.findForward("success");
  }
}