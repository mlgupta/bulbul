package bulbul.boff.listeners;

import bulbul.boff.general.BOConstants;
import bulbul.boff.general.General;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 *	Purpose: This class is used to  dispose blocked resources in correct manner.
 *           This is a listener class whose contextInitialized() and contextDestroyed() methods are 
 *           called corrospondingly whenever the servlet context is loaded into the memory and destroyed.
 * 
 * @author              Sudheer Pujar
 * @version             1.0
 * 	Date of creation:   22-1-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 
public class ContextListener implements ServletContextListener
{
   ServletContext servletContext;
   Logger logger= Logger.getLogger(BOConstants.LOGGER.toString());

   /**
     * Purpose : To provide definition for contextInitialized(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is loaded.
     * @param  : sce - ServletContextEvent 
     * 
     */
   public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing Servlet Context");
        servletContext = sce.getServletContext();
        logger.info("Initializing Servlet Context Complete");
   }

   /**
     * Purpose : To provide definition for contextDestroyed(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is destroyed.
     *           Here the blocked resources are disposed off as soon as servlet context is destroyed.
     * @param  : sce - ServletContextEvent 
     * 
     */ 
   public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroying Servlet Context...");
        servletContext = sce.getServletContext();
        String tempFolderPath="temp/";
        File tempFolder2BDeleted=null;
        File[] filesInTempFolder=null;
        try{
          tempFolderPath=servletContext.getRealPath("/")+tempFolderPath;
          tempFolder2BDeleted=new File(tempFolderPath);
          if ((tempFolder2BDeleted.exists())){
            General.recurrsiveFolderDelete(tempFolder2BDeleted);
          }else{
            this.logger.error("Unable to Find "+ tempFolderPath + " Directory"); 
          }
        }catch(Exception e){
            logger.error(e);
        }                  
        logger.info("Destroying Servlet Context complete");
   }
}
