package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.PrintableAreaListForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;
import bulbul.boff.user.beans.UserProfile;

import java.io.IOException;

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


public class PrintableAreaListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList printableAreas=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    HttpSession httpSession=null;
    ServletContext context=null;
    DataSource dataSource= null;
    PrintableAreaListForm printableAreaListForm=null;
    try{
      logger.info("Entering PrintableAreaListAction Action");
      httpSession=request.getSession(false);

      context=servlet.getServletContext();
      
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in Outlet List Action");
      
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      printableAreaListForm=(PrintableAreaListForm)form;
      if(request.getAttribute("hdnSearchPageNo")!=null){
        printableAreaListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo")); 
      }
      printableAreas=Operations.getPrintableAreas(printableAreaListForm,dataSource,numberOfRecords);
      printableAreaListForm.setRadSearchSelect("");
      request.setAttribute("printableAreas", printableAreas);
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting PrintableAreaListAction Action");
    }
    return mapping.findForward("success");
  }
}