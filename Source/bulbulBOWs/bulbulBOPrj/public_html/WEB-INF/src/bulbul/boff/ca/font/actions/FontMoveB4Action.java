package bulbul.boff.ca.font.actions;

import bulbul.boff.ca.font.actionforms.FontCategoryListForm;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class FontMoveB4Action extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int srcFontTblPk=-1;
    FontCategoryListForm fontCategoryListForm=null;

    logger.info("Entering FontMoveB4Action");
    fontCategoryListForm=new FontCategoryListForm();
    
    if(request.getParameter("radSearchSelect")!=null){
      srcFontTblPk=Integer.parseInt(request.getParameter("radSearchSelect"));
    }
    
    logger.debug("srcFontTblPk"+srcFontTblPk);
    fontCategoryListForm.setHdnSrcFontTblPk(srcFontTblPk);
    fontCategoryListForm.setHdnOperation("font_move");
    fontCategoryListForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    request.setAttribute(mapping.getAttribute(),fontCategoryListForm);
    logger.info("Exiting FontMoveB4Action");
    return mapping.findForward("success");
  }
}