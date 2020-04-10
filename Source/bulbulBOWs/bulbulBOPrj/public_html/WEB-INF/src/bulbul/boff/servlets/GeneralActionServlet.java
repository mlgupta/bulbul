package bulbul.boff.servlets;

import bulbul.boff.general.BOConstants;
import bulbul.boff.user.beans.UserProfile;

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
  

  public void init(ServletConfig config) throws ServletException {

    try{
        super.init(config);
        log("Initializing Logger...");
        context = config.getServletContext();
        String prefix =  context.getRealPath("/");
        String logFile = getInitParameter("log4j-init-file");
        if(logFile != null) {
            PropertyConfigurator.configure(prefix + logFile);
            log("The log4j-initialization-file (with prefix): " + prefix + logFile);
        }else{
            log("Unable to find log4j-initialization-file : " + logFile);
        }
        log("Logger initialized successfully");
      }catch(Exception e){
            log(" Unable to initialize logger : " + e.toString());
      }
    }
    
    public void process(HttpServletRequest request,HttpServletResponse response) {
      Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
      HttpSession httpSession=request.getSession(true);
      UserProfile userProfile=(UserProfile)httpSession.getAttribute("userProfile");
      try{
        if (userProfile==null 
            &&  !(request.getRequestURI().endsWith("loginAction.do")) 
            &&  !(request.getRequestURI().endsWith("loginB4Action.do")) 
              && !(request.getRequestURI().endsWith("RelayAction.do")) 
                ){
                
          logger.debug("Serving : logout_admin.jsp");
          response.sendRedirect("logout_admin.jsp?sessionExpired=true");  
          
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