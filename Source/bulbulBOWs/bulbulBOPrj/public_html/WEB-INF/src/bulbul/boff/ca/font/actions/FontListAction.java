package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontListForm;
import bulbul.boff.ca.font.beans.Operations;
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


/**
 *	Purpose: To Listout All The Fonts.
 *  Info: This Class Will Use The getFonts() Method To List All The Fonts Present In The Database.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 FontListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession httpSession=null;
    ArrayList fonts=null;
    FontListForm fontListForm=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    ServletContext context=null;
    DataSource dataSource=null;
    int fontCategoryTblPk;
    int fontCategoryTblFk;
    try{
      logger.info("Entering FontListAction");
      fontListForm= (FontListForm)form;
      httpSession=request.getSession(false);
      logger.debug("FontCategoryTblPk"+(String)request.getParameter("fontOrCategoryTblPk"));
      if(request.getAttribute("fontCategoryTblPk")!=null){
        logger.debug("***setting pk from getAttribute");
        fontCategoryTblPk=Integer.parseInt((String)request.getAttribute("fontCategoryTblPk"));
      }else if(((String)request.getParameter("fontOrCategoryTblPk"))!=null){
        logger.debug("setting pk from getParameter");
        fontCategoryTblPk=Integer.parseInt((String)request.getParameter("fontOrCategoryTblPk"));
      }else if(fontListForm.getHdnSearchFontOrCategoryTblPk() !=null){
        logger.debug("setting pk from FontListForm");
        fontCategoryTblPk=Integer.parseInt(fontListForm.getHdnSearchFontOrCategoryTblPk());
      }else{
        logger.debug("setting pk from directly to -1");
        fontCategoryTblPk=-1;
        logger.debug("fontCategoryTblPk is: "+fontCategoryTblPk);
      }
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in FontListAction");
      if(request.getAttribute("hdnSearchPageNo")!=null){
        fontListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo")); 
      }
      fonts=Operations.getFonts(fontCategoryTblPk,fontListForm,dataSource,numberOfRecords);
      fontListForm.setRadSearchSelect("");
      request.setAttribute("fonts", fonts);
      if (httpSession.getAttribute("fontTreeView")!=null){
        Treeview fontTreeView = (Treeview)httpSession.getAttribute("fontTreeView");
        if(fontTreeView!=null) {
          fontTreeView.forListNavigation(Integer.toString(fontCategoryTblPk));
        }
      }
    }
    catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting FontListAction");
    }
    return mapping.findForward("success");
  }
}