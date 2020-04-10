package bulbul.foff.studio.actions;

import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.FOConstants;
import bulbul.foff.studio.beans.Operations;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Hashtable;

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
    ServletContext context = null;
    int customerDesignTblPk=-1;
    int selectedMerchandiseTblPk=-1;
    int selectedMerchandiseColorTblPk=-1;
    String uniqueNumberString=null;
    String merchandisePrintableAreaPkString=null;
    String customerDesignDetailPkString=null;
    String elementIdString=null;
    String elementTypeString=null;
    String posXString=null;
    String posYString=null;
    String widthString=null;
    String heightString=null;
    String styleString=null;
    String rotateString=null;
    String propertyNameString=null;
    String propertyValueString=null;
    String elementNewString=null;
    String printableAreaDelimiter=null;
    String elementWiseDelimiter=null;
    String propertyWiseDelimiter=null;
    String customerEmailId=null;
    String customerDesignName=null;
    try{
      logger.info("Entering Product Customize Action");
      httpSession=request.getSession(false);
      context=httpSession.getServletContext();
      customerInfo=(CustomerInfo)httpSession.getAttribute("customerInfo");
      
      customerEmailId=request.getParameter("customerEmailId");
      customerDesignName=request.getParameter("customerDesignName");
      customerDesignTblPk=Integer.parseInt(request.getParameter("customerDesignTblPk"));
      selectedMerchandiseTblPk=Integer.parseInt(request.getParameter("selectedMerchandiseTblPk"));
      selectedMerchandiseColorTblPk=Integer.parseInt(request.getParameter("selectedMerchandiseColorTblPk"));
      uniqueNumberString=request.getParameter("uniqueNumberString");
      merchandisePrintableAreaPkString=request.getParameter("merchandisePrintableAreaPkString");
      customerDesignDetailPkString=request.getParameter("customerDesignDetailPkString");
      elementIdString=request.getParameter("elementIdString");
      elementTypeString=request.getParameter("elementTypeString");
      posXString=request.getParameter("posXString");
      posYString=request.getParameter("posYString");
      widthString=request.getParameter("widthString");
      heightString=request.getParameter("heightString");
      styleString=request.getParameter("styleString");
      rotateString=request.getParameter("rotateString");
      propertyNameString=request.getParameter("propertyNameString");
      propertyValueString=request.getParameter("propertyValueString");
      elementNewString=request.getParameter("elementNewString");
      printableAreaDelimiter=request.getParameter("printableAreaDelimiter");
      elementWiseDelimiter=request.getParameter("elementWiseDelimiter");
      propertyWiseDelimiter=request.getParameter("propertyWiseDelimiter");
      
      dataSource=getDataSource(request,"FOKey");
      logger.debug("customerEmailId : " + customerEmailId);
      logger.debug("customerDesignName : " + customerDesignName);
      logger.debug("customerDesignTblPk : " + customerDesignTblPk);
      logger.debug("selectedMerchandiseTblPk : " + selectedMerchandiseTblPk);
      logger.debug("selectedMerchandiseColorTblPk : " + selectedMerchandiseColorTblPk);
      logger.debug("uniqueNumberString : " + uniqueNumberString);
      logger.debug("merchandisePrintableAreaPkString : " + merchandisePrintableAreaPkString);
      logger.debug("customerDesignDetailPkString : " + customerDesignDetailPkString);
      logger.debug("elementIdString : " + elementIdString);
      logger.debug("elementTypeString : " + elementTypeString);
      logger.debug("posXString : " + posXString);
      logger.debug("posYString : " + posYString);
      logger.debug("widthString : " + widthString);
      logger.debug("heightString : " + heightString);
      logger.debug("styleString : " + styleString);
      logger.debug("rotateString : " + rotateString);
      logger.debug("propertyNameString : " + propertyNameString);
      logger.debug("propertyValueString : " + propertyValueString);
      logger.debug("elementNewString : " + elementNewString);
      logger.debug("printableAreaDelimiter : " + printableAreaDelimiter);
      logger.debug("elementWiseDelimiter : " + elementWiseDelimiter);
      logger.debug("propertyWiseDelimiter : " + propertyWiseDelimiter);
      
      customerInfo=Operations.productCustomise( dataSource, 
                                                     context,
                                                     customerEmailId,
                                                     customerDesignName,
                                                     customerInfo,
                                                     customerDesignTblPk,
                                                     selectedMerchandiseTblPk,
                                                     selectedMerchandiseColorTblPk,
                                                     uniqueNumberString,
                                                     merchandisePrintableAreaPkString,
                                                     customerDesignDetailPkString,
                                                     elementIdString,
                                                     elementTypeString,
                                                     posXString,
                                                     posYString,
                                                     widthString,
                                                     heightString,
                                                     styleString,
                                                     rotateString,
                                                     propertyNameString,
                                                     propertyValueString,
                                                     elementNewString,
                                                     printableAreaDelimiter,
                                                     elementWiseDelimiter,
                                                     propertyWiseDelimiter);
      httpSession.setAttribute("customerInfo",customerInfo); 
      
      
        
//    }catch(SQLException e){
//      logger.error(e);
    }catch(Exception e){
      logger.error(e);
      e.printStackTrace();
    }finally{
      Hashtable customerDesignDetailTblPkTable= customerInfo.getCustomerDesignDetailTblPkTable();
      Enumeration keys=customerDesignDetailTblPkTable.keys();
      
      String xmlStringOutput="<?xml version=\"1.0\" ?>\n";
      xmlStringOutput+="<design>\n";
      xmlStringOutput+="<header>\n" ;
      xmlStringOutput+="<pk>" ;
      xmlStringOutput+=customerInfo.getCustomerDesignTblPk();
      xmlStringOutput+="</pk>\n";
      xmlStringOutput+="<name>" ;
      xmlStringOutput+=customerInfo.getCustomerDesignName();
      xmlStringOutput+="</name>\n";
      xmlStringOutput+="<email>" ;
      xmlStringOutput+=customerInfo.getCustomerEmailId();
      xmlStringOutput+="</email>\n";
      xmlStringOutput+="<code>" ;
      xmlStringOutput+=customerInfo.getCustomerDesignCode();
      xmlStringOutput+="</code>\n";
      xmlStringOutput+="</header>\n" ;
      xmlStringOutput+="<detail>\n";
      while(keys.hasMoreElements()){
        String uniqueKey=(String)keys.nextElement();
        xmlStringOutput+="<unique>";
        xmlStringOutput+=uniqueKey;
        xmlStringOutput+="</unique>\n";
        xmlStringOutput+="<pk>";
        xmlStringOutput+=customerDesignDetailTblPkTable.get(uniqueKey);
        xmlStringOutput+="</pk>\n";
      }
      xmlStringOutput+="</detail>\n";
      xmlStringOutput+="</design>\n";
      response.setContentType("text/xml");
      //response.setHeader("Cache-Control", "no-cache");    
      response.getWriter().write(xmlStringOutput);
      
      logger.debug("xmlStringOutput  " + xmlStringOutput);
      logger.info("Exiting Design Name Check Action");
      return null;
    }
  }
}