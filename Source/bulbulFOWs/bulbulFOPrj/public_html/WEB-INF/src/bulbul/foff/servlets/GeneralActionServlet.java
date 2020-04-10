package bulbul.foff.servlets;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;


import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.action.ActionServlet;

public class GeneralActionServlet extends ActionServlet  {

  private ServletContext  context = null;
  private CustomerInfo customerInfo =null;
  private HttpSession httpSession=null;
  private String requestAction=null;
  private String[] requestActionArray=null;
  private String requestQueryString=null;
  private static ArrayList loginRequiredActionList = new ArrayList();
  static {
      loginRequiredActionList.add("customerProfileB4Action.do");
      loginRequiredActionList.add("myGraphicsListAction.do");
      loginRequiredActionList.add("myFavouriteListAction.do");
      loginRequiredActionList.add("myShippingAddressBookB4Action.do");
      loginRequiredActionList.add("myCreativityListAction.do");
  }

  
  public void init(ServletConfig config) throws ServletException {

    try{
        super.init(config);
        log("Initializing Logger...");
        context = config.getServletContext();
        String prefix =  context.getRealPath("/");
        String logFile = getInitParameter("log4j-init-file");
        String smtpHost = getInitParameter("smtphost");
        String emailid = getInitParameter("emailid");
        context.setAttribute("smtphost",smtpHost);
        context.setAttribute("emailid",emailid);
        if(logFile != null) {
            PropertyConfigurator.configure(prefix + logFile);
            log("The log4j-initialization-file (with prefix): " + prefix + logFile);
        }else{
            log("Unable to find log4j-initialization-file : " + logFile);
        }
        log("Logger initialized successfully");
        System.out.println(prefix + logFile);
      }catch(Exception e){
            log(" Unable to initialize logger : " + e.toString());
      }
    }
    
    public void process(HttpServletRequest request,HttpServletResponse response) {
      Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
      try{
          httpSession=request.getSession(true);
          logger.info(" Request URL " + request.getRequestURL());
          logger.info(" Request URL's Query String " + request.getQueryString());
          customerInfo = (CustomerInfo) httpSession.getAttribute("customerInfo");
          
          requestActionArray=request.getRequestURI().split("/");
          requestAction=requestActionArray[requestActionArray.length-1];
          requestQueryString=request.getQueryString();
          if (loginRequiredActionList.contains(requestAction)){
            if (customerInfo.getCustomerRegistered().equals(FOConstants.NO_VAL.toString())){
              requestQueryString=((requestQueryString==null)?"":"?"+requestQueryString);
              customerInfo.setTargetUrl(requestAction+requestQueryString);
              response.sendRedirect("customerRegisterLoginAction.do?operation=login");
            }else{
              super.process(request,response);
            }
          }else{
            logger.info("Normal Execution of Process Method of GeneralActionServlet Before super.process");
            logger.info(" Request URI before super.process" + request.getRequestURI());
            super.process(request,response);
            logger.info(" Request URI after super.process" + request.getRequestURI());
            logger.info("Normal Execution of Process Method of GeneralActionServlet After super.process");
          }  
      }catch(Exception e){
        e.printStackTrace();
        logger.error(e);
      }
    }

  }