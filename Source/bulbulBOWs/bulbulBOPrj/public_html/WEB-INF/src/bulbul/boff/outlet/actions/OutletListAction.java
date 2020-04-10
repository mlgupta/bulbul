package bulbul.boff.outlet.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.outlet.actionforms.OutletListForm;
import bulbul.boff.outlet.beans.Operations;

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


/**
 *	Purpose: To Listout All The Outlets.
 *  Info: This Class Will Use The getOutlets() Method To List All The Outlets Present In The Database.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

OutletListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList outlets=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    HttpSession httpSession=null;
    ServletContext context=null;
    DataSource dataSource= null;
    try{
      logger.info("Entering Outlet List Action");
      httpSession=request.getSession(false);

      context=servlet.getServletContext();
      
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in Outlet List Action");
      
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      OutletListForm outletListForm= (OutletListForm)form;
      if(request.getAttribute("hdnSearchPageNo")!=null){
        outletListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo")); 
      }
      outlets=Operations.getOutlets(outletListForm,dataSource,numberOfRecords);
      outletListForm.setRadSearchSelect("");
      request.setAttribute("outlets", outlets);
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting Outlet List Action");
    }
    return mapping.findForward("success");
  }
}