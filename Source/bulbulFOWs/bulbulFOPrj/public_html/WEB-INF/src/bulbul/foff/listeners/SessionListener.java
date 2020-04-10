package bulbul.foff.listeners;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.pcatalogue.beans.ProductTreeView;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 *	Purpose: This class is used to  dispose off user specific resources whenever his/her 
 *           session is expired/invalidated.
 *           This is a session listener class whose sessionCreated() and sessionDestroyed() methods are 
 *           called corrospondingly whenever the user,s http session is created and destroyed.
 * 
 * @author              Sudheer Pujar
 * @version             1.0
 * 	Date of creation:   21-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SessionListener  implements  HttpSessionListener{

  private CustomerInfo customerInfo = null;
  private HttpSession httpSession = null;
  private ProductTreeView productTreeView =null;

  private String customerEmailId=null;
  private Logger logger= Logger.getLogger(FOConstants.LOGGER.toString());
 
  /* Methods for the HttpSessionListener */
  public void sessionCreated(HttpSessionEvent hse){
    logger.info("Entering sesstionCreated");
    try{
      httpSession=hse.getSession();
      logger.info("Http Session created with Session Id :" + httpSession.getId());
      customerInfo=new CustomerInfo();
      customerInfo.setCustomerRegistered(FOConstants.NO_VAL.toString());
      httpSession.setAttribute("customerInfo",customerInfo); 
      logger.info("Customer Info Bean Created and Loaded with Blank Data");
    }catch(Exception e){
      logger.error(e);    
    }finally{
      logger.info("Exiting sesstionCreated");
    }
  }

  /**
   * Purpose : To provide definition for sessionDestroyed(HttpSessionEvent) function in HttpSessionListener
   *           interface.This function is called whenever user session is destroyed.
   *           Here the resources/files related to his/her treeview are freed
   *           and all other user specific session data is also cleaned up.
   * @param  : hse - HttpSessionEvent 
   * 
   */ 
  public void sessionDestroyed(HttpSessionEvent hse){
    logger.info("Entering sesstionDestroyed");
    try{
      logger.info("Session invalidation called.");
      logger.info("Starting Http Session cleanup.");                  
      httpSession = hse.getSession();
      long  start = httpSession.getCreationTime();
      long  end = httpSession.getLastAccessedTime();
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      if(customerInfo!=null){
        customerEmailId=customerInfo.getCustomerEmailId();
      }
      if (customerEmailId==null){
        customerEmailId="General Surfer";
      }
      productTreeView =  (ProductTreeView)httpSession.getAttribute("productTreeView");
      if (productTreeView!=null){
        try{
          productTreeView.free();
          logger.info("ProductTreeView removed.");
        }catch(Exception e){
          logger.fatal(e); 
        }
      }
      httpSession.removeAttribute("customerInfo");
      logger.info("Time for which user '"+customerEmailId+"' stayed connected: "+((end - start)/60)+" seconds");
      logger.info("Http session for user '"+customerEmailId+"' invalidated");
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Existing sesstionDestroyed");
    }
  }
}


 