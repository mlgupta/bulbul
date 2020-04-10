package bulbul.foff.studio.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;
import bulbul.foff.studio.common.SaveDesignBean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ProductCustomiseAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  Logger logger = Logger.getLogger(FOConstants.LOGGER.toString()); 
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    DataSource dataSource=null;
    
    
    HttpSession httpSession=null;
    CustomerInfo customerInfo =null;
    
    ServletContext context=null;
    
    
    ObjectInputStream objectInStream=null; 
    ObjectOutputStream objectOutStream=null;
    try{
      logger.info("Entering Product Customize Action");
      
      httpSession=request.getSession(false);
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      
      dataSource=getDataSource(request,"FOKey");
      context=servlet.getServletContext();
      
      objectInStream = new ObjectInputStream(request.getInputStream());
      SaveDesignBean saveDesignObject = (SaveDesignBean)objectInStream.readObject();
      
      customerInfo=Operations.productCustomise(dataSource,context,saveDesignObject,customerInfo);
      
      objectOutStream = new ObjectOutputStream(response.getOutputStream());
      objectOutStream.writeInt(Integer.parseInt(customerInfo.getCusotmerDesignTblPk()));
      objectOutStream.flush();
      
      httpSession.setAttribute("customerInfo",customerInfo); 
        
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);
    }finally{
      try {
        if(objectInStream!=null){
          objectInStream.close();
        }
        
        if(objectOutStream!=null){
          objectOutStream.close();    
        }
      }catch (Exception e) {;}
      
      logger.info("Exiting Product Customize Action");
    }
    return null;
  }
}