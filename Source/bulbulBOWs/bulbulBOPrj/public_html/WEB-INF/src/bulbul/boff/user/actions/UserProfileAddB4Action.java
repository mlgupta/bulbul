package bulbul.boff.user.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.actionforms.UserProfileForm;
import bulbul.boff.user.xml.UserAccess;

import bulbul.boff.user.xml.UserCategory;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *	Purpose: To Reset The Value Of All The UserProfileForm Elements.
 *  Info: This Class Will Set The Value Of All UserProfileForm Elements To Null By Using There Setter Methods.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

UserProfileAddB4Action extends Action  {
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
    UserProfileForm userForm =new UserProfileForm();
    ArrayList userCategories=null;
    ArrayList modules=null;

    
    try{
      logger.info("Entering UserProfileAddB4Action");
      userForm.setTxtUserId("");
      userForm.setTxtUserPassword("");
      userForm.setCboUserCategory("");
      userForm.setTxtUserFirstName("");
      userForm.setTxtUserLastName("");
      userForm.setHdnUserCategoryId(request.getParameter("hdnUserCategoryId"));
      userForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      request.setAttribute(mapping.getAttribute(),userForm); 

      httpSession=request.getSession(false);
      String physicalPath = httpSession.getServletContext().getRealPath("/");
      logger.debug("Physical Path : " + physicalPath);

      UserCategory userCategory = new UserCategory(physicalPath);
      userCategories = userCategory.getUserCategories();
      httpSession.setAttribute("userCategories", userCategories);

      UserAccess userAccess = new UserAccess(physicalPath);
      modules = userAccess.getModules();
      httpSession.setAttribute("modules", modules);
      
      /* If the modules are added in the user_access.xml
       * permissionValue will be increased  
       * in order equalize the numbers.
       */
      
      int difference=modules.size()-userForm.permissionValues.size();
      if(difference>0){
        for(int diffIndex=0; diffIndex<difference; diffIndex++){
          userForm.permissionValues.add(new String(""));
        }
      }
      httpSession.setAttribute("permissionValues",userForm.permissionValues);
      
    }catch(Exception e){
      return mapping.getInputForward();
    }finally{
    logger.info("***Exiting UserProfileAddB4Action***");
    }
    return mapping.findForward("success");
  }
}