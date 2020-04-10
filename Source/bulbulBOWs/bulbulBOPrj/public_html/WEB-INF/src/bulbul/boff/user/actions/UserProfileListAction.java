package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.UserProfileListForm;
import bulbul.boff.user.beans.Operations;
import bulbul.boff.user.beans.UserProfile;

import bulbul.boff.user.xml.UserCategory;

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
 *	Purpose: To Listout All User Profiles.
 *  Info: This Class Will Use The getUserProfiles() Method To List All The Users Present In The Database.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 UserProfileListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  HttpSession httpSession= null;
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList userProfiles=null;
    ArrayList userCategories=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    logger.info("Entering User Profile List Action");
    try{

      httpSession=request.getSession(false);
      String physicalPath = httpSession.getServletContext().getRealPath("/");
      logger.debug("Physical Path : " + physicalPath);

      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      
      UserCategory userCategory = new UserCategory(physicalPath);

      String userCategoryId = request.getParameter("hdnUserCategoryId");  
      
      ServletContext context=servlet.getServletContext();
      
      DataSource dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in User Profile List Action");
        

      UserProfileListForm userListForm= (UserProfileListForm)form;
      if(request.getAttribute("hdnSearchPageNo")!=null){
        userListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
      }
      userProfiles=Operations.getUserProfiles(userListForm,dataSource,userCategoryId,userCategory,numberOfRecords);
      
      request.setAttribute("userProfiles", userProfiles);
      
      userCategories=userCategory.getUserCategories();
      request.setAttribute("userCategories", userCategories);

      httpSession.removeAttribute("userAccessTblPks");            
      httpSession.removeAttribute("permissionValues");

      
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
          logger.info("Exiting User Profile List Action");
    }
    return mapping.findForward("success");
  }
}