package bulbul.boff.ca.merchand.actions;

import bulbul.boff.ca.merchand.actionforms.MerchandiseForm;
import bulbul.boff.ca.merchand.beans.Operations;
import bulbul.boff.general.BOConstants;

import java.io.IOException;

import java.sql.SQLException;

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


public class MerchandiseAddB4Action extends Action  {
  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    MerchandiseForm merchandiseForm =null;
    int merchandiseCategoryTblPk=-1;
    ArrayList size4List=null;
    ArrayList merchandiseDecorations=null;
    ArrayList printableAreas=null;
    DataSource dataSource=null;
    ServletContext context=null;
    HttpSession  httpSession=null;
    String parentCategory=null;
    try{
      logger.info("***Entering MerchandiseAddB4Action***");
      
      merchandiseForm =new MerchandiseForm();
      
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      
      if (request.getParameter("hdnSearchMerchandiseOrCategoryTblPk")!=null) {
        merchandiseCategoryTblPk=Integer.parseInt(request.getParameter("hdnSearchMerchandiseOrCategoryTblPk"));
      }
      
      logger.debug("***hdnSearchMerchandiseOrCategoryTblPk: "+merchandiseCategoryTblPk);
      
      merchandiseForm.setHdnMerchandiseCategoryTblPk(merchandiseCategoryTblPk);        
      parentCategory=Operations.getMerchandiseCategory(merchandiseCategoryTblPk,dataSource);
      httpSession=request.getSession(false);
      merchandiseForm.setTxtMerchandise("");
      merchandiseForm.setTxtParentCategory(parentCategory);
      merchandiseForm.setTxaMerchandiseDesc("");
      merchandiseForm.setTxaMerchandiseComm("");
      merchandiseForm.setTxaMaterialDetail("");
      merchandiseForm.setTxtMinimumQuantity(1);
      merchandiseForm.setTxaDeliveryNote("");
      merchandiseForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
    

      size4List=Operations.getSize(dataSource,merchandiseCategoryTblPk);
      merchandiseDecorations=Operations.getDecorations(dataSource);
      printableAreas=Operations.getActivePrintableAreas(dataSource);
    }catch(SQLException e){
      logger.error(e);
      return mapping.getInputForward();
    }catch(Exception e){
      logger.error(e);
      return mapping.getInputForward();
    }finally{
      logger.info("***Exiting MerchandiseAddB4Action***");
    }
    httpSession.setAttribute("size4List",size4List);
    httpSession.setAttribute("merchandiseDecorations",merchandiseDecorations);
    httpSession.setAttribute("printableAreas",printableAreas); 
    request.setAttribute(mapping.getAttribute(),merchandiseForm); 
    return mapping.findForward("success");
  }
}