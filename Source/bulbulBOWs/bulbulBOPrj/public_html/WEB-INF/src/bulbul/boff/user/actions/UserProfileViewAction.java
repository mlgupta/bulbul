package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.UserProfileForm;
import bulbul.boff.user.beans.Operations;
import bulbul.boff.user.xml.UserAccess;

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
 *	Purpose: To View An User Profile Selected In user_profile_list.jsp.
 *  Info: This Class Will Use The viewUserProfile() Method To Populate The user_profile_view.jsp With The Specified Data. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 UserProfileViewAction extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  HttpSession httpSession= null;
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    UserProfileForm userForm=null;
    String userProfileTblPk=null;
    ServletContext context=null;
    DataSource dataSource=null;  
    ArrayList userCategories=null;
    ArrayList modules=null;
    String physicalPath=null;
    try{    
      logger.info("***Entering UserProfileViewAction***");
      userForm=(UserProfileForm)form;
      request.setAttribute("hdnSearchPageNo",userForm.getHdnSearchPageNo());
      if(isCancelled(request)){
       return mapping.getInputForward();
      }
      httpSession=request.getSession(false);
      physicalPath = httpSession.getServletContext().getRealPath("/");
      userProfileTblPk=request.getParameter("radSearchSelect");
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      userForm=Operations.viewUserProfile(userProfileTblPk,dataSource,physicalPath);
      userForm.setHdnUserCategoryId(request.getParameter("hdnUserCategoryId"));
      userForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),userForm); 

      
      logger.debug("Physical Path : " + physicalPath);
      UserCategory userCategory = new UserCategory(physicalPath);
      userCategories=userCategory.getUserCategories();
      request.setAttribute("userCategories", userCategories);

      UserAccess userAccess = new UserAccess(physicalPath);
      modules = userAccess.getModules();
      request.setAttribute("modules", modules);

      /* If the modules are added in the user_access.xml
       * the userAccessTblPk and permissionValue will be increased  
       * in order equalize the numbers.
       */
      
      int difference=modules.size()-userForm.userAccessTblPks.size();
      if(difference>0){
        for(int diffIndex=0; diffIndex<difference; diffIndex++){
          userForm.userAccessTblPks.add(new String("-1"));
          userForm.permissionValues.add(new String(""));
        }
      }
      
      httpSession.setAttribute("userAccessTblPks",userForm.userAccessTblPks);            
      httpSession.setAttribute("permissionValues",userForm.permissionValues);
      
      
    }catch(Exception e){
      logger.error("exception:"+e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting UserProfileViewAction***");
    }
    return mapping.findForward("success");    
  }
}