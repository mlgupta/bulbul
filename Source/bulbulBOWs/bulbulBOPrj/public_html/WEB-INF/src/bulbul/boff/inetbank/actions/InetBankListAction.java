package bulbul.boff.inetbank.actions;

import bulbul.boff.general.BOConstants;
import bulbul.boff.inetbank.actionforms.InetBankListForm;
import bulbul.boff.inetbank.beans.Operations;

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
 *	Purpose: To Listout All The Internet Banks.
 *  Info: This Class Will Use The getInetBanks() Method To List All The Internet Banks Present In The Database.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankListAction extends Action  {
  Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList inetBanks=null;
    int numberOfRecords;
    UserProfile userProfile=null;
    HttpSession httpSession=null;
    ServletContext context=null;
    DataSource dataSource= null;
    InetBankListForm inetListForm=null;
    try{
      logger.info("Entering InetBankListAction");
      httpSession=request.getSession(false);
      userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords=userProfile.getNumberOfRecords();
      context=servlet.getServletContext();
      dataSource= getDataSource(request,"BOKey");
      logger.info("Getting Data Source in InetBankListAction");
      inetListForm=(InetBankListForm)form;
      if(request.getAttribute("hdnSearchPageNo")!=null){
        inetListForm.setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
      }
      inetBanks=Operations.getInetBanks(inetListForm,dataSource,numberOfRecords);
      inetListForm.setRadSearchSelect("");
      request.setAttribute("inetBanks", inetBanks);
    }
    catch(Exception e){
      return mapping.getInputForward();
    }finally{
      logger.info("Exiting InetBankListAction");
    }
    return mapping.findForward("success");
  }
}