package bulbul.foff.studio.beans;

import bulbul.foff.common.Base64;
import bulbul.foff.common.ContentType;
import bulbul.foff.common.ImageFormat;
import bulbul.foff.customer.beans.CustomerInfo;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.general.LOOperations;
import bulbul.foff.general.OrderStatus;
import bulbul.foff.general.SVGImageCanvas;
import bulbul.foff.studio.actionforms.TrackDesignForm;
import bulbul.foff.studio.actionforms.TrackOrderForm;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import javax.swing.ImageIcon;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGDocument;


public final class Operations {

  private static final Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  private static final PNGTranscoder transcoder = new PNGTranscoder();
  private static final ArrayList imageCanvasPool=new ArrayList();
  
   public static  void svgToPNG(HttpServletResponse response, String svgURL,Dimension imageSize,
                     String style,float rotate,double scale,boolean isForTextElement) throws IOException, Exception {
     
     
     TranscoderInput ti = null;
     TranscoderOutput to = null;
     SVGImageCanvas imageCanvas= null;
     try {
   //      logger.info("Entering svgToPNG");
       //logger.debug("clipartName : "+ svgURL);
       response.setContentType("image/png");
       
       for(int arrayIndex=0;arrayIndex<imageCanvasPool.size();arrayIndex++){
         imageCanvas=(SVGImageCanvas)imageCanvasPool.get(arrayIndex);
         if(imageCanvas!=null && imageCanvas.isCanvasFree()){
           break;
         }else{
           imageCanvas=null;
         }
       }

       if(imageCanvas==null){
         imageCanvas= new SVGImageCanvas();
         imageCanvasPool.add(imageCanvas);
         imageCanvas.init();
       }
       

       logger.debug("svgURL in SVG TO PNG : " + svgURL);
       
       imageCanvas.resetCanvas();
       imageCanvas.setStyle(style);
       imageCanvas.setRotate(rotate);
       imageCanvas.setPreferredSize(imageSize);
       imageCanvas.setScale(scale);
       imageCanvas.setForTextElement(isForTextElement);
       imageCanvas.setURI(svgURL);
       
       while(!imageCanvas.isDocumentResized() && !(imageCanvas.isErrorInResizing())){
         Thread.sleep(500);
       }
       
   //      logger.debug("svgURL : " + svgURL + " - " + imageCanvas.isErrorInResizing() + " - " + imageCanvas.isDocumentResized());
   //      //Viewing changed document starts
   //      SVGDocument document=imageCanvas.getSVGDocument();
   //      if(document!=null){
   //        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
   //        Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
   //        FileOutputStream fos = null;
   //        try {
   //          String fileName=System.getProperty("user.home")+File.separator+new Random().nextInt()+".svg";
   //          System.out.println("fileName " + fileName);
   //          fos=new FileOutputStream(fileName);
   //          svgGenerator.stream(svgRoot, new OutputStreamWriter(fos,"UTF-8"));
   //        }catch (UnsupportedEncodingException ue){
   //          ue.printStackTrace();
   //        }catch (SVGGraphics2DIOException se) {
   //          se.printStackTrace();
   //        }finally{
   //          if (fos!=null){
   //            try {
   //              fos.flush();
   //              fos.close();
   //            }
   //            catch (Exception e) {
   //             e.printStackTrace(); 
   //            }
   //          }
   //        }
   //      }
   //      //Viewing changed document ends
         
         
       if(!imageCanvas.isErrorInResizing()){
         ti = new TranscoderInput(imageCanvas.getSVGDocument());
         to = new TranscoderOutput(response.getOutputStream());
         transcoder.transcode(ti, to);
         response.flushBuffer();
         
       }else{
         logger.debug("Error In Resizing ....   ...  "); 
       }
       
       //System.gc(); 
   //      logger.debug("SVG Content Converted to PNG");
     } catch (IOException ioe) {
       ioe.printStackTrace();
       throw ioe;
     } catch (Exception e) {
       e.printStackTrace();;
       throw e;
     } finally {
       if(to!=null){
         to.getOutputStream().flush();
         to.getOutputStream().close();  
         
       }
       if(imageCanvas!=null){
         imageCanvas.setCanvasFree(true);
       }
       
       imageCanvas=null;
       ti=null;
       to=null;      
   //      logger.info("Exiting svgToPNG");
     }
   }
   
   
   public static void text2SVGDisplay(HttpServletResponse response,String text, String style) throws IOException, Exception {
     
     try {
       //logger.info("Entering text2SVGDisplay");
       float fontSize=20.0f;
       DecimalFormatSymbols symbols = new DecimalFormatSymbols();
       symbols.setDecimalSeparator('.');
       DecimalFormat format = new DecimalFormat("######.#", symbols);
  
       DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
       SVGDocument document =
         (SVGDocument)domImpl.createDocument(SVGConstants.SVG_NAMESPACE_URI, SVGConstants.SVG_SVG_TAG, null);
  
       SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
  
       Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
       
       //Extracting fontsize from style;  
       if(style!=null && style.trim().length()>0 ){
         String[] styleArray=style.split(";");
         for(int i=0;i<styleArray.length;i++){
           String[] property=styleArray[i].split(":");
           String propertyName=property[0];
           String propertyValue=property[1];
           if(propertyName.equals("font-size") && propertyValue.trim().length()>0){
             fontSize=Float.parseFloat(propertyValue.trim());
           }
         }
       }
       
       String[] textArray=text.split("\n");
       for(int i=0;i<textArray.length;i++){        
         Element textElement = document.createElementNS(document.getDocumentElement().getNamespaceURI(), "text");
         Text textValue=document.createTextNode(textArray[i]);
         textElement.appendChild(textValue);
         textElement.setAttributeNS(null,"x", format.format(0));
         textElement.setAttributeNS(null,"y", format.format(i*fontSize));
         if(style!=null && style.trim().length()>0 ){
           textElement.setAttributeNS(null, "style", style);
         }
         svgRoot.appendChild(textElement);
       }
     
       response.setContentType("image/svg");
       svgGenerator.stream(svgRoot, new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
       response.flushBuffer();
   
     } catch (Exception e) {
       logger.error(e.getMessage());
       e.printStackTrace();
       throw e;
     }finally{
       //      logger.info("Exiting text2SVGDisplay");
     }
  }
     
  public static ProductBean getProductDetails(String merchandiseTblPk,
                                              DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String sqlString;
    ProductBean productBean = null;
    try {
      logger.info("Entering getProductDetails() method");

      dBConnection = new DBConnection(dataSource);

      sqlString = "SELECT merchandise_tbl_Pk, ";
      sqlString += "\n merchandise_name, ";
      sqlString += "\n merchandise_design_image, ";
      sqlString += "\n design_image_content_type, ";
      sqlString += "\n design_image_content_size ";
      sqlString += "\n from merchandise_tbl where merchandise_tbl_pk=" + merchandiseTblPk;
      logger.debug("SQL String is " + sqlString);
      rs = dBConnection.executeQuery(sqlString);
      if (rs.next()) {
        productBean = new ProductBean();
        productBean.setMerchandiseTblPk(rs.getString("merchandise_tbl_Pk"));
        productBean.setProductOId(rs.getString("merchandise_design_image"));
        productBean.setProductContentType(rs.getString("design_image_content_type"));
        productBean.setProductContentSize(rs.getString("design_image_content_size"));
        productBean.setProductName(rs.getString("merchandise_name"));
      }

    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw e;
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getProductDetails() method");
    }
    return productBean;
  }

  public static boolean getDesign(TrackDesignForm trackDesignForm, DataSource dataSource,
                                  CustomerInfo customerInfo) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    String yourEmailId = null;
    String designCode = null;
    boolean isValid = false;
    DesignBean designBean = null;
    try {
      logger.info("Entering getDesign");
      yourEmailId = trackDesignForm.getTxtYourEmailId();
      designCode = trackDesignForm.getTxtDesignCode();

      query = "\n select ";
      query += "\n cdt.customer_design_tbl_pk, ";
      query += "\n cdt.customer_design_name, ";
      query += "\n cdt.design_4_char_code, ";
      query += "\n cdt.customer_design, ";
      query += "\n cdt.design_content_size, ";
      query += "\n cdt.design_content_type, ";
      query += "\n cet.customer_email_id_tbl_pk, ";
      query += "\n cet.customer_email_id, ";
      query += "\n ptbl.merchandise_name, ";
      query += "\n ptbl.merchandise_color_tbl_pk ";
      query += "\n from customer_design_tbl cdt, ";
      query += "\n customer_email_id_tbl cet, ";
      query += "\n dblink( 'dbname=bulbulbo', ";
      query +=
        "\n 'select mtbl.merchandise_name, mct.merchandise_color_tbl_pk from bulbul.merchandise_color_tbl mct, ";
      query +=
        "\n bulbul.merchandise_tbl mtbl where mct.merchandise_tbl_pk=mtbl.merchandise_tbl_pk') as ptbl (merchandise_name varchar,merchandise_color_tbl_pk int) ";
      query += "\n where cdt.customer_email_id_tbl_pk=cet.customer_email_id_tbl_pk ";
      query += "\n and ptbl.merchandise_color_tbl_pk=cdt.merchandise_color_tbl_pk ";
      query += "\n and cet.customer_email_id='" + yourEmailId + "'";
      query += "\n and cdt.design_4_char_code='" + designCode + "'";
      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);
      if (rs.next()) {
        customerInfo.setCustomerEmailIdTblPk(rs.getInt("customer_email_id_tbl_pk"));
        customerInfo.setCustomerEmailId(rs.getString("customer_email_id"));
        designBean = new DesignBean();
        designBean.setCustomerDesignTblPk(rs.getString("customer_design_tbl_pk"));
        designBean.setDesignName(rs.getString("customer_design_name"));
        designBean.setDesignCode(rs.getString("design_4_char_code"));
        designBean.setProductName("merchandise_name");
        designBean.setMerchandiseColorTblPk("merchandise_color_tbl_pk");
        //customerInfo.setCustomerDesign(designBean);
        isValid = true;
      }
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getDesign");
    }
    logger.debug("Design isValid : " + isValid);
    return isValid;
  }

  public static void deleteTrackedDesign(String customerDesignTblPk,
                                         DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnCustomerDesignTblPk;
    try {
      logger.info("Entering deleteCustomerDesigns() method");
      sqlString = "{? = call sp_del_customer_design_tbl(?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1, Types.INTEGER);
      cs.setString(2, customerDesignTblPk);

      dBConnection.executeCallableStatement(cs);
      returnCustomerDesignTblPk = cs.getInt(1);
      logger.debug("Deleted customer_design_tbl_pk : " + returnCustomerDesignTblPk);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteCustomerDesign() method");
    }
  }

  public static void deleteTrackedIncompleteOrder(String orderHeaderTblPk,
                                                  DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnOrderHeaderTblPk;
    try {
      logger.info("Entering deleteTrackedIncompleteOrder() method");
      sqlString = "{? = call sp_order_header_remove(?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1, Types.INTEGER);
      cs.setString(2, orderHeaderTblPk);

      dBConnection.executeCallableStatement(cs);
      returnOrderHeaderTblPk = cs.getInt(1);
      logger.debug("Deleted order_header_tbl_pk : " + returnOrderHeaderTblPk);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteTrackedIncompleteOrder() method");
    }
  }

  public static OrderBean getOrder(TrackOrderForm trackOrderForm, DataSource dataSource,
                                   CustomerInfo customerInfo) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    String orderCode = null;
    String yourEmailId = null;
    OrderBean orderBean = null;
    SimpleDateFormat formatter = null;
    String dateString = null;
    DecimalFormat priceFormatter = null;
    try {
      logger.info("Entering getDesign");
      formatter = new SimpleDateFormat("dd MMM yyyy");
      priceFormatter = new DecimalFormat("Rs ##,##,##0.00");

      yourEmailId = trackOrderForm.getTxtYourEmailId().trim();
      orderCode = trackOrderForm.getTxtOrderCode().trim();
      orderCode = (orderCode.length() > 0) ? orderCode : "-1";

      query = "\n select ";
      query += "\n cet.customer_email_id_tbl_pk, ";
      query += "\n cet.customer_email_id, ";
      query += "\n oht.order_header_tbl_pk, ";
      query += "\n oht.order_gen_date, ";
      query += "\n oht.order_placed_date, ";
      query += "\n oht.order_code, ";
      query +=
        "\n sum(sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) * oit.quantity) as order_amount, ";
      query += "\n oht.order_status, ";
      query += "\n count(odt.order_detail_tbl_pk) as items ";
      query += "\n from  ";
      query += "\n order_header_tbl oht, ";
      query += "\n order_detail_tbl odt, ";
      query += "\n order_item_detail_tbl oit, ";
      query += "\n merchandise_size_tbl mst, ";
      query +=
        "\n dblink('dbname=bulbulfo','select customer_email_id_tbl_pk,customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
      query +=
        "\n as cdt ( customer_email_id_tbl_pk integer,customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer), ";
      query +=
        "\n dblink('dbname=bulbulfo','select customer_email_id_tbl_pk,customer_email_id from bulbul.customer_email_id_tbl' ) ";
      query += "\n as cet ( customer_email_id_tbl_pk integer,customer_email_id varchar) ";
      query += "\n where ";
      query += "\n oht.order_header_tbl_pk=odt.order_header_tbl_pk ";
      query += "\n and odt.order_detail_tbl_pk=oit.order_detail_tbl_pk ";
      query += "\n and mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
      query += "\n and cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
      query += "\n and cdt.customer_email_id_tbl_pk=cet.customer_email_id_tbl_pk ";
      query += "\n and oht.order_code=" + orderCode;
      query += "\n and cet.customer_email_id='" + yourEmailId + "'";
      query += "\n group by  oht.order_header_tbl_pk, ";
      query += "\n oht.order_code, ";
      query += "\n oht.order_gen_date, ";
      query += "\n oht.order_placed_date, ";
      query += "\n oht.order_status, ";
      query += "\n cet.customer_email_id_tbl_pk, ";
      query += "\n cet.customer_email_id ";

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);

      if (rs.next()) {
        customerInfo.setCustomerEmailIdTblPk(rs.getInt("customer_email_id_tbl_pk"));
        customerInfo.setCustomerEmailId(rs.getString("customer_email_id"));
        orderBean = new OrderBean();
        orderBean.setOrderHeaderTblPk(rs.getString("order_header_tbl_pk"));
        orderBean.setOrderCode(rs.getString("order_code"));
        dateString = formatter.format(rs.getDate("order_gen_date"));
        orderBean.setOrderGenDate(dateString);

        if (rs.getDate("order_placed_date") != null) {
          dateString = formatter.format(rs.getDate("order_placed_date"));
        } else {
          dateString = "--";
        }
        orderBean.setOrderPlacedDate(dateString);
        if (rs.getString("order_amount") != null) {
          orderBean.setOrderAmount(priceFormatter.format(rs.getFloat("order_amount")));
        } else {
          orderBean.setOrderAmount("--");
        }
        orderBean.setOrderStatus(rs.getString("order_status"));
        orderBean.setOrderStatusString(OrderStatus.getValue(Integer.parseInt(rs.getString("order_status"))));
        orderBean.setOrderItems(rs.getString("items"));
      }
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getDesign");
    }
    return orderBean;
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param customerEmailId
   * @param customerDesignName
   * @param dataSource
   */
  public static boolean designNameExists(DataSource dataSource, String customerDesignName,
                                         String customerEmailId) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;

    boolean isExisting = false;
    try {
      logger.info("Entering designNameExists");

      query = "\n select ";
      query += "\n cdt.customer_design_tbl_pk ";
      query += "\n from customer_design_tbl cdt, ";
      query += "\n customer_email_id_tbl cet ";
      query += "\n where cdt.customer_email_id_tbl_pk=cet.customer_email_id_tbl_pk ";
      query += "\n and cet.customer_email_id='" + customerEmailId + "'";
      query += "\n and cdt.customer_design_name='" + customerDesignName + "'";
      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);
      if (rs.next()) {
        isExisting = true;
      }
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting designNameExists");
    }
    logger.debug("Design Exists is : " + isExisting);
    return isExisting;
  }


  public static String getClipartXMLString(int clipartCategoryTblPk,DataSource dataSource,String searchKeywords,boolean search,int pageNumber)throws SQLException,Exception{
    DBConnection dBConnection=null;
    
    ResultSet rsCategory=null;
    ResultSet rsClipart=null;
    String queryForCategory;
    String queryForClipart;
    
    String clipartXMLString="<?xml version=\"1.0\" ?>\n";
    clipartXMLString+="<clipart>\n";
    
    int clipartRecordCount=0;
    
    int numberOfRecords=20;
    int recordCount=0;

    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getClipartXMLString");
      logger.debug("***pageNumber: "+pageNumber);

      dBConnection= new  DBConnection(dataSource);  
      if (!search){
        //Query For Clipart Category
        queryForCategory="select * from clipart_category_tbl where 1=1 and clipart_category_tbl_fk ";
  
        if (clipartCategoryTblPk==-1){
          queryForCategory=queryForCategory+"is null ";
        }else{
          queryForCategory=queryForCategory+"="+clipartCategoryTblPk;
        }
        queryForCategory = queryForCategory + " and is_active=1";
        queryForCategory=queryForCategory + " order by clipart_category ";
        rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        
        logger.debug("queryForCategory: "+queryForCategory);
      }

      //Query For Clipart
      queryForClipart="select * from clipart_tbl where 1=1 ";
      if(!search){
        queryForClipart+=" and clipart_category_tbl_pk="+clipartCategoryTblPk;
      }
      
      if( search && searchKeywords!= null && (searchKeywords.trim()).length() != 0 ){
          queryForClipart = queryForClipart + " and upper(clipart_keywords) like '%"+searchKeywords.toUpperCase()+"%'";
      }
      
      queryForClipart = queryForClipart + " and is_active=1";
      
      queryForClipart=queryForClipart+" order by clipart_name ";
      
      rsClipart=dBConnection.executeQuery(queryForClipart,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      
      logger.debug("queryForClipart: "+queryForClipart);
 
      //Filling Category Records
      if (rsCategory!=null){
        while(rsCategory.next()){
          clipartXMLString+="<name isClipart=\"false\"" ;
          clipartXMLString+=" id=\"" ;
          clipartXMLString+= rsCategory.getString("clipart_category_tbl_pk") ;
          clipartXMLString+="\" parentId=\"";
          if(rsCategory.getString("clipart_category_tbl_fk")!=null){
            clipartXMLString+=rsCategory.getString("clipart_category_tbl_fk"); 
          }else{
            clipartXMLString+="-1"; 
          }
          clipartXMLString+="\" >";
          clipartXMLString+=rsCategory.getString("clipart_category");
          clipartXMLString+="</name>\n";
        }
        
      }

      //Filling Clipart Records
      
      if(rsClipart!=null){
       rsClipart.last();
       clipartRecordCount=rsClipart.getRow();
       rsClipart.beforeFirst();
      }
      logger.debug("Clipart count : " + clipartRecordCount );
      recordCount=clipartRecordCount;
      if(recordCount!=0){
       pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }
      
      if (pageNumber>pageCount) {
       pageNumber=pageCount;
      }
      
      startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
      endIndex=(startIndex + numberOfRecords)-1;
      
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);
       
      if(rsClipart !=null){
        if (startIndex<=endIndex){
          rsClipart.relative(startIndex);
          while(rsClipart.next()){
            clipartXMLString+="<name isClipart=\"true\"" ;
            clipartXMLString+=" id=\"" ;
            clipartXMLString+= rsClipart.getString("clipart_tbl_pk");
            clipartXMLString+="\" parentId=\"";
            if(rsClipart.getString("clipart_category_tbl_pk")!=null){
              clipartXMLString+=rsClipart.getString("clipart_category_tbl_pk"); 
            }else{
              clipartXMLString+="-1"; 
            }
            clipartXMLString+="\" oId=\"";
            clipartXMLString+=rsClipart.getString("clipart_image"); 
            
            clipartXMLString+="\" type=\"";
            clipartXMLString+=rsClipart.getString("content_type"); 
            
            clipartXMLString+="\" size=\"";
            clipartXMLString+=rsClipart.getString("content_size"); 
            
            clipartXMLString+="\" stdOId=\"";
            clipartXMLString+=rsClipart.getString("clipart_std_image"); 
            
            clipartXMLString+="\" stdType=\"";
            clipartXMLString+=rsClipart.getString("std_content_type"); 
            
            clipartXMLString+="\" stdSize=\"";
            clipartXMLString+=rsClipart.getString("std_content_size"); 
            
            clipartXMLString+="\" >";
            clipartXMLString+=rsClipart.getString("clipart_name");
            clipartXMLString+="</name>\n";
            startIndex++;
            logger.debug("***startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }              
        }
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rsCategory);
        dBConnection.freeResultSet(rsClipart);
        dBConnection.free(); 
      }
      dBConnection=null;
      
      clipartXMLString+="<meta"  ;
      clipartXMLString+=" pageNumber=\"";
      clipartXMLString+=pageNumber; 
      
      clipartXMLString+="\" pageCount=\"";
      clipartXMLString+=pageCount; 
      
      clipartXMLString+="\" clipartCategoryTblPk=\"";
      clipartXMLString+=clipartCategoryTblPk; 

      clipartXMLString+="\" search=\"";
      clipartXMLString+=search; 
      
      clipartXMLString+="\" searchKeywords=\"";
      clipartXMLString+=searchKeywords; 
      
      
      clipartXMLString+="\" ></meta>";  
      
      logger.info("Exiting getClipartXMLString");
    }
    clipartXMLString+="</clipart>\n";
    return clipartXMLString;
  }
 
 
  public static String getFontXMLString(int fontCategoryTblPk,DataSource dataSource,int pageNumber)throws SQLException,Exception{
    DBConnection dBConnection=null;
    
    ResultSet rsCategory=null;
    ResultSet rsFont=null;
    String queryForCategory;
    String queryForFont;
    
    String fontXMLString="<?xml version=\"1.0\" ?>\n";
    fontXMLString+="<font>\n";
    
    int fontRecordCount=0;
    
    int numberOfRecords=20;
    int recordCount=0;

    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getFontXMLString");
      logger.debug("***pageNumber: "+pageNumber);

      dBConnection= new  DBConnection(dataSource);  
      
      //Query For Font Category
      queryForCategory="select * from font_category_tbl where 1=1 and font_category_tbl_fk ";

      if (fontCategoryTblPk==-1){
        queryForCategory=queryForCategory+"is null ";
      }else{
        queryForCategory=queryForCategory+"="+fontCategoryTblPk;
      }
      queryForCategory = queryForCategory + " and is_active=1";
      queryForCategory=queryForCategory + " order by font_category ";
      rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      
      logger.debug("queryForCategory: "+queryForCategory);
      

      //Query For Font
      queryForFont="select * from font_tbl where 1=1 ";
      queryForFont+=" and font_category_tbl_pk="+fontCategoryTblPk;
      queryForFont = queryForFont + " and is_active=1";
      queryForFont=queryForFont+" order by font_name ";
      rsFont=dBConnection.executeQuery(queryForFont,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      
      logger.debug("queryForFont: "+queryForFont);
  
      //Filling Category Records
      if (rsCategory!=null){
        while(rsCategory.next()){
          fontXMLString+="<name isFont=\"false\"" ;
          fontXMLString+=" id=\"" ;
          fontXMLString+= rsCategory.getString("font_category_tbl_pk") ;
          fontXMLString+="\" parentId=\"";
          if(rsCategory.getString("font_category_tbl_fk")!=null){
            fontXMLString+=rsCategory.getString("font_category_tbl_fk"); 
          }else{
            fontXMLString+="-1"; 
          }
          fontXMLString+="\" >";
          fontXMLString+=rsCategory.getString("font_category");
          fontXMLString+="</name>\n";
        }
        
      }

      //Filling Font Records
      
      if(rsFont!=null){
       rsFont.last();
       fontRecordCount=rsFont.getRow();
       rsFont.beforeFirst();
      }
      logger.debug("Font count : " + fontRecordCount );
      recordCount=fontRecordCount;
      if(recordCount!=0){
       pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }
      
      if (pageNumber>pageCount) {
       pageNumber=pageCount;
      }
      
      startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
      endIndex=(startIndex + numberOfRecords)-1;
      
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);
       
      if(rsFont !=null){
        if (startIndex<=endIndex){
          rsFont.relative(startIndex);
          while(rsFont.next()){
            fontXMLString+="<name isFont=\"true\"" ;
            fontXMLString+=" id=\"" ;
            fontXMLString+= rsFont.getString("font_tbl_pk");
            fontXMLString+="\" parentId=\"";
            if(rsFont.getString("font_category_tbl_pk")!=null){
              fontXMLString+=rsFont.getString("font_category_tbl_pk"); 
            }else{
              fontXMLString+="-1"; 
            }
            fontXMLString+="\" >";
            fontXMLString+=rsFont.getString("font_name");
            fontXMLString+="</name>\n";
            startIndex++;
            logger.debug("***startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }              
        }
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rsCategory);
        dBConnection.freeResultSet(rsFont);
        dBConnection.free(); 
      }
      dBConnection=null;
      
      fontXMLString+="<meta"  ;
      fontXMLString+=" pageNumber=\"";
      fontXMLString+=pageNumber; 
      
      fontXMLString+="\" pageCount=\"";
      fontXMLString+=pageCount; 
      
      fontXMLString+="\" fontCategoryTblPk=\"";
      fontXMLString+=fontCategoryTblPk; 

      fontXMLString+="\" ></meta>";  
      
      logger.info("Exiting getFontXMLString");
    }
    fontXMLString+="</font>\n";
    return fontXMLString;
  }

  /**
   *	Purpose: OutStreams the byte array into the response a Response Object
   *  @param content - An Array of byte 
   *  @param contentType - A String Object Defined by content type
   *  @param contentSize - An int data type Defined by content size
   *  @param response - A HttpServletResponse Object
   */
  public static void svgDisplay(byte[] content, String contentType, int contentSize,
                                HttpServletResponse response) throws IOException, Exception {
    OutputStream os = null;
    try {
      logger.info("Entering svgDisplay");

      logger.debug("Content Type : " + contentType);
      logger.debug("Content Size : " + contentSize);

      response.setContentLength(contentSize);
      response.setContentType(contentType);


      os = response.getOutputStream();
      os.write(content, 0, contentSize);

      response.flushBuffer();

    } catch (IOException ioe) {
      logger.error(ioe);
      throw ioe;
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
      if (os != null) {
        logger.debug("Closing and Flushing Output Stream");
        os.flush();
        logger.debug("Output Stream Flushed");
        os.close();
        logger.debug("Output Stream Closed");
      }
      logger.info("Exiting svgDisplay");
    }
  }

  
  public static String customImageUpload(int customerEmailIdTblPk, FormFile imageFile,
                                    DataSource dataSource) throws SQLException, IOException, Exception {
    String graphicsCategory = null;
    String sqlString = null;
    String contentType = null;
    int contentSize;
    int returnCustomerGraphicsTblPk = -1;
    int oid;
    byte[] content = null;
    InputStream is = null;
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    ResultSet rs = null;
    String result=null;
    try {
      logger.info("Entering uploadGraphics");

      dBConnection = new DBConnection(dataSource);

      graphicsCategory = getGraphicsCategory(imageFile.getFileName());

      contentType = imageFile.getContentType();
      contentSize = imageFile.getFileData().length;
      logger.info("****contentType : " + contentType);
      oid = LOOperations.getLargeObjectId(dataSource);

      content = new byte[contentSize];
      is = imageFile.getInputStream();
      is.read(content);

      LOOperations.putLargeObjectContent(oid, content, dataSource);
      sqlString = "{?=call sp_upload_customer_graphics(?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1, Types.INTEGER);
      cs.setInt(2, customerEmailIdTblPk);
      cs.setString(3, null);
      cs.setString(4, null);
      cs.setInt(5, oid);
      cs.setString(6, contentType);
      cs.setInt(7, contentSize);
      cs.setString(8, graphicsCategory);

      dBConnection.executeCallableStatement(cs);
      returnCustomerGraphicsTblPk = cs.getInt(1);
      is.close();
      
      result=String.valueOf(returnCustomerGraphicsTblPk);
      result+="~"+oid;
      result+="~"+contentType;
      result+="~"+contentSize;
      
      logger.debug("Returned PK is:" + returnCustomerGraphicsTblPk);
    } catch (SQLException e) {
      logger.info(e);
      throw e;
    } catch (IOException e) {
      logger.info(e);
      throw e;
    } catch (Exception e) {
      logger.info(e);
      throw e;
    } finally {
      if (is != null) {
        is.close();
      }
      if (dBConnection != null) {
        dBConnection.freeResultSet(rs);
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting uploadGraphics");
      
      return result;
    }
  }
  
  public static String getGraphicsCategory(String GraphicsFileName) throws Exception {
    StringTokenizer token = null;
    String graphicsCategory = null;
    try {
      logger.info("Entering getGraphicsCategory() method");
      token = new StringTokenizer(GraphicsFileName, ".");
      while (token.hasMoreTokens()) {
        graphicsCategory = token.nextToken();
      }
      logger.info("graphicsCategory :" + graphicsCategory);
    } catch (Exception e) {
      e.printStackTrace();
      logger.info(e.getMessage());
    } finally {
      logger.info("Exiting getGraphicsCategory() method");
    }
    return graphicsCategory;
  }
  
  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param format
   * @param customerEmailIdTblPk
   * @param dataSource
   */
//  public static ImageBean[] getImages(DataSource dataSource, String customerEmailIdTblPk,
//                                      String format) throws SQLException, Exception {
//    DBConnection dBConnection = null;
//    ResultSet rs = null;
//    String query = null;
//    ImageBean[] images = null;
//    ImageBean image = null;
//    try {
//      logger.info("Entering getImages");
//      query = "\n select ";
//      query += "\n customer_graphics_tbl_pk, ";
//      query += "\n graphics_title, ";
//      query += "\n graphics_description, ";
//      query += "\n content_oid, ";
//      query += "\n content_type, ";
//      query += "\n content_size ";
//      query += "\n from customer_graphics_tbl where 1=1 ";
//
//      if (format.equals(ImageFormat.JPG.toString())) {
//        query += "\n and lower(graphics_category)=lower('jpg')";
//      }
//      if (format.equals(ImageFormat.GIF.toString())) {
//        query += "\n and lower(graphics_category)=lower('gif')";
//      }
//      if (format.equals(ImageFormat.PNG.toString())) {
//        query += "\n and lower(graphics_category)=lower('png')";
//      }
//      if (format.equals(ImageFormat.SVG.toString())) {
//        query += "\n and lower(graphics_category)=lower('svg')";
//      }
//      query += "\n and customer_email_id_tbl_pk=" + customerEmailIdTblPk + " order by uploaded_on desc";
//
//      logger.debug("query : " + query);
//
//      dBConnection = new DBConnection(dataSource);
//      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//      rs.last();
//      images = new ImageBean[rs.getRow()];
//      rs.beforeFirst();
//      int index = 0;
//      while (rs.next()) {
//        image = new ImageBean();
//        image.setPk(rs.getString("customer_graphics_tbl_pk"));
//        image.setTitle(rs.getString("graphics_title"));
//        image.setDescription(rs.getString("graphics_description"));
//        image.setContentOId(Integer.parseInt(rs.getString("content_oid")));
//        image.setContentType(rs.getString("content_type"));
//        image.setContentSize(Integer.parseInt(rs.getString("content_size")));
//        images[index++] = image;
//      }
//    } catch (SQLException e) {
//      logger.error("SqlException: " + e);
//      throw e;
//    } catch (Exception e) {
//      logger.error("Exception: " + e);
//      throw e;
//    } finally {
//      if (dBConnection != null) {
//        dBConnection.free(rs);
//      }
//      dBConnection = null;
//      logger.info("Exiting getImages");
//    }
//    return images;
 // }

  /**
   *	Purpose: OutStreams the byte array into the response a Response Object
   *  @param content - An Array of byte 
   *  @param contentType - A String Object Defined by content type
   *  @param response - A HttpServletResponse Object
   */
  public static void svgImageDisplay(HttpServletResponse response, byte[] content, String contentType, 
                      Dimension imageSize,String style,float rotate,double scale
                                     ) throws IOException, Exception {
    TranscoderInput ti = null;
    TranscoderOutput to = null;
    SVGImageCanvas imageCanvas= null;
    try {
      logger.info("Entering svgImageDisplay");

      //Converting gif(if any ) to png 
      if (contentType.indexOf((ContentType.GIF.toString())) > -1) {
        ByteArrayInputStream gifBais = null;
        ByteArrayOutputStream pngBaos = null;
        BufferedImage bufferedImage = null;
        try {
          gifBais = new ByteArrayInputStream(content);

          if (gifBais != null) {
            bufferedImage = ImageIO.read(gifBais);
          }

          if (bufferedImage != null) {
            pngBaos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, ImageFormat.PNG.toString(), pngBaos);
            bufferedImage.flush();
            contentType = ContentType.PNG.toString();
            content = pngBaos.toByteArray();
            pngBaos.flush();
          }
        } catch (Exception e) {
          ;
        } finally {
          if (gifBais != null) {
            gifBais.close();
          }
          if (pngBaos != null) {
            pngBaos.close();
          }
        }
      }

      ImageIcon imageIcon = new ImageIcon(content);
      double imageWidth = imageIcon.getIconWidth();
      double imageHeight = imageIcon.getIconHeight();

      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      DecimalFormat format = new DecimalFormat("######.#", symbols);

      DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
      SVGDocument document =
        (SVGDocument)domImpl.createDocument(SVGConstants.SVG_NAMESPACE_URI, SVGConstants.SVG_SVG_TAG, null);

      SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

      Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
      svgRoot
      .setAttributeNS(null, SVGConstants.SVG_VIEW_BOX_ATTRIBUTE, "" + format.format(0) + " " + format.format(0) + " " +
                             format.format(imageWidth) + " " + format.format(imageHeight));
      svgRoot.setAttributeNS(null, SVGConstants.SVG_WIDTH_ATTRIBUTE, format.format(imageWidth));
      svgRoot.setAttributeNS(null, SVGConstants.SVG_HEIGHT_ATTRIBUTE, format.format(imageHeight));

      final Element image = document.createElementNS(document.getDocumentElement().getNamespaceURI(), "image");

      image
      .setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "data:" + contentType + ";base64," + Base64.encodeBytes(content));
      image.setAttributeNS(null, "x", format.format(0));
      image.setAttributeNS(null, "y", format.format(0));
      image.setAttributeNS(null, "width", format.format(imageWidth == 0 ? 1 : imageWidth));
      image.setAttributeNS(null, "height", format.format(imageHeight == 0 ? 1 : imageHeight));

      svgRoot.appendChild(image);
      response.setContentType("image/png");
      for(int arrayIndex=0;arrayIndex<imageCanvasPool.size();arrayIndex++){
        imageCanvas=(SVGImageCanvas)imageCanvasPool.get(arrayIndex);
        if(imageCanvas!=null && imageCanvas.isCanvasFree()){
          break;
        }else{
          imageCanvas=null;
        }
      }

      if(imageCanvas==null){
        imageCanvas= new SVGImageCanvas();
        imageCanvasPool.add(imageCanvas);
        imageCanvas.init();
      }
      
      imageCanvas.resetCanvas();
      imageCanvas.setStyle(style);
      imageCanvas.setRotate(rotate);
      imageCanvas.setPreferredSize(imageSize);
      imageCanvas.setScale(scale);
      imageCanvas.setDocumentSet(true);
      imageCanvas.setDocument(document);
      
      while(!imageCanvas.isDocumentResized() && !(imageCanvas.isErrorInResizing())){
        Thread.sleep(500);
      }
      
//            //Viewing changed tempDocument starts
//            SVGDocument tempDocument=imageCanvas.getSVGDocument();
//            if(tempDocument!=null){
//              svgGenerator = new SVGGraphics2D(tempDocument);
//              svgRoot = svgGenerator.getRoot(tempDocument.getDocumentElement());
//              FileOutputStream fos = null;
//              try {
//                String fileName=System.getProperty("user.home")+File.separator+new Random().nextInt()+".svg";
//                System.out.println("fileName " + fileName);
//                fos=new FileOutputStream(fileName);
//                svgGenerator.stream(svgRoot, new OutputStreamWriter(fos,"UTF-8"));
//              }catch (Exception se) {
//                se.printStackTrace();
//              }finally{
//                if (fos!=null){
//                  try {
//                    fos.flush();
//                    fos.close();
//                  }
//                  catch (Exception e) {
//                   e.printStackTrace();
//                  }
//                }
//              }
//            }
//            //Viewing changed tempDocument ends
        
        
      if(!imageCanvas.isErrorInResizing()){
        ti = new TranscoderInput(imageCanvas.getSVGDocument());
        to = new TranscoderOutput(response.getOutputStream());
        transcoder.transcode(ti, to);
        response.flushBuffer();
        
      }else{
        logger.debug("Error In Resizing ....   ...  "); 
      }
    } catch (IOException ioe) {
      logger.error(ioe);
      ioe.printStackTrace();
      throw ioe;
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      throw e;
    } finally {
      if(to!=null){
          to.getOutputStream().flush();
          to.getOutputStream().close();  
          
      }
      
      if(imageCanvas!=null){
          imageCanvas.setCanvasFree(true);
      }
        
      imageCanvas=null;
      ti=null;
      to=null;
      logger.info("Exiting svgImageDisplay");
    }
  }

  
  public static String getMerchandiseCategoryListXML(int merchandiseCategoryTblPk,DataSource dataSource)throws SQLException,Exception{
    DBConnection dBConnection=null;
    
    ResultSet rsCategory=null;
    
    String queryForCategory;

    String merchandiseXMLString="<?xml version=\"1.0\" ?>\n";
    merchandiseXMLString+="<merchandise_category>\n";
    
    try{
      logger.info("Entering getMerchandiseCategoryListXML");
      dBConnection= new  DBConnection(dataSource);  
      
      //Query For Font Category
      queryForCategory="\n select ";
      queryForCategory+="\n mct.merchandise_category_tbl_pk, ";
      queryForCategory+="\n mct.merchandise_category_tbl_fk, ";
      queryForCategory+="\n mct.merchandise_category, ";
      queryForCategory+="\n mct.merchandise_category_description, ";
      queryForCategory+="\n mct.m_or_c, ";
      queryForCategory+="\n mct.merchandise_category_image, ";
      queryForCategory+="\n mct.content_type, ";
      queryForCategory+="\n mct.content_size ";
      queryForCategory+="\n from merchandise_category_tbl mct ";
      queryForCategory+="\n where mct.is_active=1 ";
      queryForCategory+="\n and mct.merchandise_category_tbl_fk ";
      

      if (merchandiseCategoryTblPk==-1){
        queryForCategory+="is null ";
      }else{
        queryForCategory+="="+merchandiseCategoryTblPk;
      }
      queryForCategory+="\n order by merchandise_category ";
      
      rsCategory=dBConnection.executeQuery(queryForCategory);
      
      logger.debug("queryForCategory: "+queryForCategory);

      //Filling Category Records
      if (rsCategory!=null){
        while(rsCategory.next()){
          merchandiseXMLString+="<category>\n";
          merchandiseXMLString +="\t\t<pk>";
          merchandiseXMLString +=rsCategory.getString("merchandise_category_tbl_pk");
          merchandiseXMLString +="</pk>\n";
          if(rsCategory.getString("merchandise_category_tbl_fk")!=null){
          merchandiseXMLString +="\t\t<fk>";
          merchandiseXMLString +=rsCategory.getString("merchandise_category_tbl_fk");
          merchandiseXMLString +="</fk>\n";
          }else{
            merchandiseXMLString +="\t\t<fk>";
            merchandiseXMLString +="-1";
            merchandiseXMLString +="</fk>\n";
          }
          
          merchandiseXMLString +="\t\t<name>";
          merchandiseXMLString +=rsCategory.getString("merchandise_category");
          merchandiseXMLString +="</name>\n";
          merchandiseXMLString +="\t\t<desc>";
          merchandiseXMLString +=rsCategory.getString("merchandise_category_description");
          merchandiseXMLString +="</desc>\n";
          merchandiseXMLString +="\t\t<morc>";
          merchandiseXMLString +=rsCategory.getString("m_or_c");
          merchandiseXMLString +="</morc>\n";
          merchandiseXMLString +="\t\t<contentid>";
          merchandiseXMLString +=rsCategory.getString("merchandise_category_image");
          merchandiseXMLString +="</contentid>\n";
          merchandiseXMLString +="\t\t<contenttype>";
          merchandiseXMLString +=rsCategory.getString("content_type");
          merchandiseXMLString +="</contenttype>\n";
          merchandiseXMLString +="\t\t<contentsize>";
          merchandiseXMLString +=rsCategory.getString("content_size");
          merchandiseXMLString +="</contentsize>\n";
          merchandiseXMLString+="</category>\n";
        }        
      }

    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rsCategory);
        dBConnection.free(); 
      }
      dBConnection=null;
      logger.info("Exiting getMerchandiseCategoryListXML");
    }
    merchandiseXMLString+="</merchandise_category>\n";
    return merchandiseXMLString;
  }

 

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param inMerchandiseTblPk
   * @param dataSource
   */
  public static String getMerchandiseListXMLString(int inMerchandiseTblPk,int merchandiseCategoryTblPk,DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    
    String query = null;
    int merchandiseColorTblPk = -1;
    int merchandiseTblPk = -1;
    String merchandiseXMLString="<?xml version=\"1.0\" ?>\n";
    merchandiseXMLString+="<products>\n";
    try {
    
      
      logger.info("Entering getMerchandiseListXMLString");

      query = "\n select ";
      query += "\n (select max(merchandise_category_tbl_pk) ";
      query += "\n from merchandise_parents_tbl mptbl ";
      query += "\n where mptbl.is_active=1 ";
      query += "\n and merchandise_tbl_pk=mtbl.merchandise_tbl_pk) as merchandise_category_tbl_pk, ";
      query += "\n mtbl.merchandise_tbl_pk, ";
      query += "\n mtbl.merchandise_name, ";
      query += "\n mtbl.merchandise_description, ";
      query += "\n mtbl.merchandise_comment, ";
      query += "\n mtbl.material_description, ";
      query += "\n mtbl.delivery_note, ";
      query += "\n mtbl.threshold_quantity, ";
      query += "\n mtbl.merchandise_image, ";
      query += "\n mtbl.content_type, ";
      query += "\n mtbl.content_size, ";
      query += "\n mctbl.merchandise_color_tbl_pk, ";
      query += "\n mctbl.color_one_name, ";
      query += "\n mctbl.color_two_name, ";
      query += "\n mctbl.color_one_value, ";
      query += "\n mctbl.color_two_value, ";
      query += "\n mstbl.merchandise_size_tbl_pk, ";
      query += "\n stbl.size_id, ";
      query += "\n stbl.size_description, ";
      query += "\n sttbl.size_type_id, ";
      query += "\n sttbl.size_type_description ";
      query += "\n from ";
      query += "\n merchandise_tbl as mtbl, ";
      query += "\n merchandise_color_tbl as mctbl, ";
      query += "\n merchandise_size_tbl as mstbl, ";
      query += "\n size_tbl as stbl, ";
      query += "\n size_type_tbl as sttbl ";
      query += "\n where mtbl.merchandise_tbl_pk=mctbl.merchandise_tbl_pk ";
      query += "\n and mctbl.merchandise_color_tbl_pk=mstbl.merchandise_color_tbl_pk ";
      query += "\n and mstbl.size_tbl_pk=stbl.size_tbl_pk ";
      query += "\n and stbl.size_type_tbl_pk=sttbl.size_type_tbl_pk ";
      query += "\n and mctbl.is_active=1 ";
      query += "\n and stbl.is_active=1 ";
      query += "\n and sttbl.is_active=1 ";
      query += "\n and mtbl.merchandise_tbl_pk in ( " ;
      query += "\n select merchandise_tbl_pk " ;
      query += "\n from merchandise_parents_tbl mptbl " ;
      query += "\n where mptbl.is_active=1 ";
      
      if(merchandiseCategoryTblPk==-1){
        query += "\n and merchandise_category_tbl_pk in (" ;
        query += "\n select merchandise_category_tbl_pk " ;
        query += "\n from merchandise_parents_tbl " ;
        if(inMerchandiseTblPk==-1){
          query += "\n where merchandise_tbl_pk in (" ;
          query += "\n select max(merchandise_tbl_pk) as merchandise_tbl_pk" ;
          query += "\n from merchandise_tbl" ;
          query += "\n )"   ;
        }else{
          query += "\n where merchandise_tbl_pk=" + inMerchandiseTblPk;  
        }
        query += "\n )";
      }else{
        query += "\n and merchandise_category_tbl_pk=" + merchandiseCategoryTblPk; 
      }
      query += "\n )";
      
      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);

      rs = dBConnection.executeQuery(query);
      if (rs !=null) {      
        while (rs.next()) {
          if (!(merchandiseTblPk==rs.getInt("merchandise_tbl_pk"))) {
            
            if(merchandiseTblPk!=-1){
              merchandiseXMLString+="\t\t</color>\n";
              merchandiseXMLString+="\t</merchandise>\n";
            }
            
            merchandiseTblPk = rs.getInt("merchandise_tbl_pk");
            merchandiseXMLString+="\t<merchandise>\n";
            merchandiseXMLString+="\t\t<pk>";
            merchandiseXMLString+=merchandiseTblPk;
            merchandiseXMLString+="</pk>\n";
            merchandiseXMLString+="\t\t<categorypk>";
            merchandiseXMLString+=rs.getString("merchandise_category_tbl_pk");
            merchandiseXMLString+="</categorypk>\n";
            merchandiseXMLString+="\t\t<name>";
            merchandiseXMLString+=rs.getString("merchandise_name");
            merchandiseXMLString+="</name>\n";
            merchandiseXMLString+="\t\t<desc>";
            merchandiseXMLString+=rs.getString("merchandise_description");
            merchandiseXMLString+="</desc>\n";
            merchandiseXMLString+="\t\t<comment>";
            merchandiseXMLString+=rs.getString("merchandise_comment");
            merchandiseXMLString+="</comment>\n";
            merchandiseXMLString+="\t\t<material>";
            merchandiseXMLString+=rs.getString("material_description");
            merchandiseXMLString+="</material>\n";
            merchandiseXMLString+="\t\t<qty>";
            merchandiseXMLString+=rs.getString("threshold_quantity");
            merchandiseXMLString+="</qty>\n";
            merchandiseXMLString+="\t\t<note>";
            merchandiseXMLString+=rs.getString("delivery_note");
            merchandiseXMLString+="</note>\n";
            merchandiseXMLString+="\t\t<contentid>";
            merchandiseXMLString+=rs.getString("merchandise_image");
            merchandiseXMLString+="</contentid>\n";
            merchandiseXMLString+="\t\t<contenttype>";
            merchandiseXMLString+=rs.getString("content_type");
            merchandiseXMLString+="</contenttype>\n";
            merchandiseXMLString+="\t\t<contentsize>";
            merchandiseXMLString+=rs.getString("content_size");
            merchandiseXMLString+="</contentsize>\n";
            merchandiseColorTblPk=-1;
          
          }
  
          if (!(merchandiseColorTblPk==rs.getInt("merchandise_color_tbl_pk"))) {
  
            if(merchandiseColorTblPk!=-1){
              merchandiseXMLString+="\t\t</color>\n";
            }
            
            merchandiseColorTblPk = rs.getInt("merchandise_color_tbl_pk");
            merchandiseXMLString+="\t\t<color>\n";
            merchandiseXMLString+="\t\t\t<pk>";
            merchandiseXMLString+=merchandiseColorTblPk;
            merchandiseXMLString+="</pk>\n";
            merchandiseXMLString+="\t\t\t<one name=\"";
            merchandiseXMLString+=rs.getString("color_one_name").trim();
            merchandiseXMLString+="\" value=\"";
            merchandiseXMLString+=rs.getString("color_one_value").trim();
            merchandiseXMLString+="\" >";
            merchandiseXMLString+="</one>\n";
            
            merchandiseXMLString+="\t\t\t<two name=\"";
            merchandiseXMLString+=rs.getString("color_two_name").trim();
            merchandiseXMLString+="\" value=\"";
            merchandiseXMLString+=rs.getString("color_two_value").trim();
            merchandiseXMLString+="\" >";
            merchandiseXMLString+="</two>\n";
          }
          merchandiseXMLString+="\t\t\t<size>\n";
          merchandiseXMLString+="\t\t\t\t<id>";
          merchandiseXMLString+=rs.getString("size_id");
          merchandiseXMLString+="</id>\n";
          
          merchandiseXMLString+="\t\t\t\t<desc>";
          merchandiseXMLString+=rs.getString("size_description");
          merchandiseXMLString+="</desc>\n";
          
          merchandiseXMLString+="\t\t\t\t<typeid>";
          merchandiseXMLString+=rs.getString("size_type_id");
          merchandiseXMLString+="</typeid>\n";
          
          merchandiseXMLString+="\t\t\t\t<typedesc>";
          merchandiseXMLString+=rs.getString("size_type_description");
          merchandiseXMLString+="</typedesc>\n";
          merchandiseXMLString+="\t\t\t</size>\n"; 
          if(rs.isLast()){
            merchandiseXMLString+="\t\t</color>\n";
            merchandiseXMLString+="\t</merchandise>\n";
          }
        }
      }
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getMerchandiseListXMLString");
      merchandiseXMLString+="</products>\n";
    }
    return merchandiseXMLString;
  }


  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param merchandiseColorTblPk
   * @param dataSource
   */
  public static String getPrintableAreasXMLString(DataSource dataSource,
                                                      int merchandiseColorTblPk) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    String printableAreaXMLString="<?xml version=\"1.0\" ?>\n";
    printableAreaXMLString+="<printableareas>\n";
    try {
      logger.info("Entering getPrintableAreas");
      query = "\n select ";
      query += "\n mptbl.merchandise_printable_area_tbl_pk, ";
      query += "\n ptbl.printable_area_tbl_pk, ";
      query += "\n ptbl.printable_area_name, ";
      query += "\n mptbl.design_image_content, ";
      query += "\n mptbl.content_type, ";
      query += "\n mptbl.content_size, ";
      query += "\n mptbl.area_width, ";
      query += "\n mptbl.area_height ";
      query += "\n from merchandise_printable_area_tbl mptbl,printable_area_tbl ptbl where 1=1 ";
      query += "\n and ptbl.printable_area_tbl_pk=mptbl.printable_area_tbl_pk";
      query += "\n and mptbl.merchandise_color_tbl_pk=" + merchandiseColorTblPk;
      query += "\n order by ptbl.printable_area_tbl_pk";

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);
      
      while (rs.next()) {
        printableAreaXMLString+="<printablearea>\n";
        printableAreaXMLString +="\t\t<papk>";
        printableAreaXMLString +=rs.getString("printable_area_tbl_pk");
        printableAreaXMLString +="</papk>\n";
        printableAreaXMLString +="\t\t<name>";
        printableAreaXMLString +=rs.getString("printable_area_name");
        printableAreaXMLString +="</name>\n";
        printableAreaXMLString +="\t\t<contentid>";
        printableAreaXMLString +=rs.getString("design_image_content");
        printableAreaXMLString +="</contentid>\n";
        printableAreaXMLString +="\t\t<contenttype>";
        printableAreaXMLString +=rs.getString("content_type");
        printableAreaXMLString +="</contenttype>\n";
        printableAreaXMLString +="\t\t<contentsize>";
        printableAreaXMLString +=rs.getString("content_size");
        printableAreaXMLString +="</contentsize>\n";
        printableAreaXMLString +="\t\t<width>";
        printableAreaXMLString +=rs.getString("area_width");
        printableAreaXMLString +="</width>\n";
        printableAreaXMLString +="\t\t<height>";
        printableAreaXMLString +=rs.getString("area_height");
        printableAreaXMLString +="</height>\n";
        printableAreaXMLString +="\t\t<mpapk>";
        printableAreaXMLString +=rs.getString("merchandise_printable_area_tbl_pk");
        printableAreaXMLString +="</mpapk>\n";
        printableAreaXMLString+="</printablearea>\n";
      }
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getPrintableAreas");
    }
    printableAreaXMLString+="</printableareas>\n";
    return printableAreaXMLString;
  }

  public static CustomerInfo productCustomise(DataSource dataSource, 
                                              ServletContext context,
                                              String customerEmailId,
                                              String customerDesignName,
                                              CustomerInfo customerInfo,
                                              int customerDesignTblPk,
                                              int selectedMerchandiseTblPk,
                                              int selectedMerchandiseColorTblPk,
                                              String uniqueNumberString,
                                              String merchandisePrintableAreaPkString,
                                              String customerDesignDetailPkString,
                                              String elementIdString,
                                              String elementTypeString,
                                              String posXString,
                                              String posYString,
                                              String widthString,
                                              String heightString,
                                              String styleString,
                                              String rotateString,
                                              String propertyNameString,
                                              String propertyValueString,
                                              String elementNewString,
                                              String printableAreaDelimiter,
                                              String elementWiseDelimiter,
                                              String propertyWiseDelimiter){


    DBConnection dBConnection = null;
    CallableStatement cs = null;
    boolean sendMail=false;
    
    try {
      String sqlString=null;
      String designCode=null;
      int customerEmailIdTblPk=-1;
      dBConnection = new DBConnection(dataSource);
      if (customerDesignTblPk==-1){
        // Inserting in customer_design_tbl
        sqlString = "{?=call sp_ins_customer_design_tbl(?,?,?)}";
        cs = dBConnection.prepareCall(sqlString);

        cs.registerOutParameter(1, Types.CHAR);
        cs.setString(2,customerEmailId);
        cs.setInt(3,selectedMerchandiseColorTblPk);
        cs.setString(4,customerDesignName);
        dBConnection.executeCallableStatement(cs);
        String returnString=cs.getString(1);
        
        logger.debug("returnString : " + returnString);
       
        if (!(returnString.equals("-1"))){
          //taking results out of a comma seperated string
          designCode=returnString.split(",")[0];
          customerEmailIdTblPk=Integer.parseInt(returnString.split(",")[1]);
          customerDesignTblPk=Integer.parseInt(returnString.split(",")[2]);
          customerInfo.setCustomerDesignTblPk(customerDesignTblPk);
          customerInfo.setCustomerEmailIdTblPk(customerEmailIdTblPk);
          customerInfo.setCustomerEmailId(customerEmailId);
          customerInfo.setCustomerDesignCode(designCode);
          customerInfo.setCustomerDesignName(customerDesignName);
          
          sendMail=true;
        }
        dBConnection.freeCallableSatement(cs);
      }
      customerInfo.getCustomerDesignDetailTblPkTable().clear();
      if(customerDesignTblPk!=-1){
        String[] merchandisePrintableAreaPkStringArray=merchandisePrintableAreaPkString.split(printableAreaDelimiter);
        String[] customerDesignDetailPkStringArray=customerDesignDetailPkString.split(printableAreaDelimiter);
        String[] uniqueNumberStringArray=uniqueNumberString.split(printableAreaDelimiter);
        
        for(int printableAreaIndex=0;printableAreaIndex<merchandisePrintableAreaPkStringArray.length;printableAreaIndex++){
          
          int merchandisePrintableAreaTblPk=Integer.parseInt(merchandisePrintableAreaPkStringArray[printableAreaIndex]);
          int customerDesignDetailTblPk=Integer.parseInt(customerDesignDetailPkStringArray[printableAreaIndex]);
          String uniqueNumber=uniqueNumberStringArray[printableAreaIndex];
          
          logger.debug("merchandisePrintableAreaPk : " + merchandisePrintableAreaTblPk);
          logger.debug("customerDesignDetailTblPk : " + customerDesignDetailTblPk);
          
          if(customerDesignDetailTblPk==-1){
            // Inserting in customer_design_detail_tbl 
            sqlString = "{?=call sp_ins_customer_design_detail_tbl(?,?)}";
            cs = dBConnection.prepareCall(sqlString);
  
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2,customerDesignTblPk);
            cs.setInt(3,merchandisePrintableAreaTblPk);
            dBConnection.executeCallableStatement(cs);
            customerDesignDetailTblPk=cs.getInt(1);
            dBConnection.freeCallableSatement(cs);
          }else{
            // Updating in customer_design_detail_tbl 
            sqlString = "{?=call sp_upd_customer_design_detail_tbl(?,?)}";
            cs = dBConnection.prepareCall(sqlString);

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2,customerDesignDetailTblPk);
            cs.setInt(3,merchandisePrintableAreaTblPk);
            dBConnection.executeCallableStatement(cs);
            customerDesignDetailTblPk=cs.getInt(1);
            dBConnection.freeCallableSatement(cs);
          }
          
          logger.debug("customerDesignDetailTblPk : " + customerDesignDetailTblPk);
          
          customerInfo.getCustomerDesignDetailTblPkTable().put(uniqueNumber,Integer.toString(customerDesignDetailTblPk));
          
          
          if (elementIdString.split(printableAreaDelimiter).length>0) {
            String[] elementIdStringArray=elementIdString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] elementTypeStringArray=elementTypeString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] posXStringArray=posXString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] posYStringArray=posYString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] widthStringArray=widthString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] heightStringArray=heightString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] styleStringArray=styleString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] rotateStringArray=rotateString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            String[] elementNewStringArray=elementNewString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter);
            
            for(int elementIndex=0; elementIndex<elementIdStringArray.length;elementIndex++ ){
              if (elementIdStringArray[elementIndex].trim().length()>0) {
                int elementId=Integer.parseInt(elementIdStringArray[elementIndex]);
                int elementType=Integer.parseInt(elementTypeStringArray[elementIndex]);
                float posX=Float.parseFloat(posXStringArray[elementIndex]);
                float posY=Float.parseFloat(posYStringArray[elementIndex]);
                float width=Float.parseFloat(widthStringArray[elementIndex]);
                float height=Float.parseFloat(heightStringArray[elementIndex]);
                String style=styleStringArray[elementIndex];
                float rotate=Float.parseFloat(rotateStringArray[elementIndex]); 
                boolean elementNew=(Boolean.valueOf(elementNewStringArray[elementIndex])).booleanValue();
                
                logger.debug("\telementId : " + elementId);
                logger.debug("\telementType : " + elementType);
                logger.debug("\tposX : " + posX);
                logger.debug("\tposY : " + posY);
                logger.debug("\twidth : " + width);
                logger.debug("\theight : " + height);
                logger.debug("\tstyle : " + style);
                logger.debug("\trotate : " + rotate);
                logger.debug("\telementNew : " + elementNew);
                
                int customerDesignElementTblPk=-1;
                
                if(elementNew){
                // Inserting in customer_design_element_tbl
                  sqlString = "{?=call sp_ins_customer_design_element_tbl(?,?,?,?,?,?,?,?,?)}";
                  cs = dBConnection.prepareCall(sqlString);
                  cs.registerOutParameter(1, Types.INTEGER);
                  cs.setInt(2,customerDesignDetailTblPk);
                  cs.setInt(3,elementId);
                  cs.setInt(4,elementType);
                  cs.setFloat(5,posX);
                  cs.setFloat(6,posY);
                  cs.setFloat(7,width);
                  cs.setFloat(8,height);
                  cs.setString(9,style);
                  cs.setFloat(10,rotate);
                  dBConnection.executeCallableStatement(cs);
                  customerDesignElementTblPk=cs.getInt(1);
                  dBConnection.freeCallableSatement(cs);
                }else{
                  // Updating in customer_design_element_tbl
                  sqlString = "{?=call sp_upd_customer_design_element_tbl(?,?,?,?,?,?,?,?)}";
                  cs = dBConnection.prepareCall(sqlString);
                  cs.registerOutParameter(1, Types.INTEGER);
                  cs.setInt(2,customerDesignDetailTblPk);
                  cs.setInt(3,elementId);
                  cs.setFloat(4,posX);
                  cs.setFloat(5,posY);
                  cs.setFloat(6,width);
                  cs.setFloat(7,height);
                  cs.setString(8,style);
                  cs.setFloat(9,rotate);
                  dBConnection.executeCallableStatement(cs);
                  customerDesignElementTblPk=cs.getInt(1);
                  dBConnection.freeCallableSatement(cs);
                }
    
                String[] propertyNameStringArray=propertyNameString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter)[elementIndex].split(propertyWiseDelimiter);
                String[] propertyValueStringArray=propertyValueString.split(printableAreaDelimiter)[printableAreaIndex].split(elementWiseDelimiter)[elementIndex].split(propertyWiseDelimiter);
                
                //deleting froom customer_design_element_property_tbl
                sqlString = "{?=call sp_del_customer_design_element_property_tbl(?)}";
                cs = dBConnection.prepareCall(sqlString);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setInt(2,customerDesignElementTblPk);
                dBConnection.executeCallableStatement(cs);
                dBConnection.freeCallableSatement(cs);
                
                for(int propertyIndex=0;propertyIndex<propertyNameStringArray.length;propertyIndex++){
                  
                  String propertyName=propertyNameStringArray[propertyIndex];
                  String propertyValue=propertyValueStringArray[propertyIndex];
                  
                  logger.debug("\t\tpropertyName : " + propertyName);
                  logger.debug("\t\tpropertyValue : " + propertyValue);
                  
                  //Inserting in customer_design_element_property_tbl
                  sqlString = "{?=call sp_ins_customer_design_element_property_tbl(?,?,?)}";
                  cs = dBConnection.prepareCall(sqlString);
                  cs.registerOutParameter(1, Types.BOOLEAN);
                  cs.setString(2,propertyName);
                  cs.setString(3,propertyValue);
                  cs.setInt(4,customerDesignElementTblPk);
                  dBConnection.executeCallableStatement(cs);
                  dBConnection.freeCallableSatement(cs);
                    
                }
              }
              
            }
          }
        }
      }
      
      try {
        if (sendMail) {
          final String finalCustomerEmailId=customerEmailId;
          final String smtphost=(String)context.getAttribute("smtphost");
          final String emailid=(String)context.getAttribute("emailid");
          final String emailSubject="Your Design saved in www.printoon.com ";
          final StringBuffer emailMessage= new StringBuffer("You Design saved @ www.printoon.com");
          emailMessage.append("<br> You Have Choosen The Product : " + selectedMerchandiseTblPk);        
          emailMessage.append("<br> Your Design Name : " + customerDesignName);
          emailMessage.append("<br> Your Mail Id : " + finalCustomerEmailId);
          emailMessage.append("<br> Your Design Code : " + designCode);
          emailMessage.append("<br><br><br> Regards,");
          emailMessage.append("<br> Administrator,");
          emailMessage.append("<br> Printoon,");
          emailMessage.append("<br> Email : " + emailid);
          
          Thread mailThread = new Thread(){
            public void run(){
              try {
                General.postMail(finalCustomerEmailId,emailSubject,emailMessage.toString(),emailid,smtphost);  
              }
              catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
              }
            }
          };
          
          mailThread.start();
  
        }
      }
      catch (Exception e) {
        logger.error(e);
        e.printStackTrace();
      }
    }catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    }finally{
      if (dBConnection != null) {
        dBConnection.free(cs);
      }
      dBConnection = null;
    }
    
    return customerInfo;
  }
  
  public static String getClipartCategoryNavigationListXML(int clipartCategoryTblPk,DataSource dataSource )throws SQLException,Exception{
    String categoriesXMLString=null;
    ArrayList tempCategories=null;
    DBConnection dBConnection=null;
    String sqlString=null;
    CallableStatement cs=null;
    String categoryNavList=null;
    String[]  categoryArray=null;
    int intMaxCharsLength=80;
    int intCurrentCharsLength=0;
    try{
      logger.info("Entering getClipartCategoryNavigationListXML method");
      dBConnection=new DBConnection(dataSource);
      sqlString="{?=call sp_get_clipart_cat_nav_list(?)}";
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.VARCHAR);
      cs.setInt(2,clipartCategoryTblPk);
      dBConnection.executeCallableStatement(cs);
      categoryNavList=cs.getString(1);
      categoryArray=categoryNavList.split("~"); //Splits the String into Group of PK and Category
      
      tempCategories = new ArrayList();
      for (int catCount=categoryArray.length-1;catCount>=0;catCount--){
        intCurrentCharsLength+=(categoryArray[catCount].split(","))[1].length();
        if (intCurrentCharsLength<=intMaxCharsLength){
          tempCategories.add(categoryArray[catCount].split(","));
        }
        logger.debug(" Categories : " + catCount + " - " + categoryArray[catCount]);
        logger.debug(" Char Length : " + intCurrentCharsLength);
      }
      
      categoriesXMLString="<?xml version=\"1.0\" ?>\n";
      categoriesXMLString+="<clipart_category>\n";
      for (int catCount=tempCategories.size()-1;catCount>=0;catCount--){
        categoriesXMLString+="<name" ;
        categoriesXMLString+=" id=\"" ;
        categoriesXMLString+= ((String[])tempCategories.get(catCount))[0];
        categoriesXMLString+="\" >";
        categoriesXMLString+=((String[])tempCategories.get(catCount))[1];
        categoriesXMLString+="</name>\n";
      }
      categoriesXMLString+="</clipart_category>\n";
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting getClipartCategoryNavigationListXML method");
    }
    return categoriesXMLString;
  }
  
  
  public static String getMerchandiseCategoryNavigationListXML(int merchandiseCategoryTblPk,DataSource dataSource )throws SQLException,Exception{
    String categoriesXMLString=null;
    ArrayList tempCategories=null;
    DBConnection dBConnection=null;
    String sqlString=null;
    CallableStatement cs=null;
    String categoryNavList=null;
    String[]  categoryArray=null;
    int intMaxCharsLength=80;
    int intCurrentCharsLength=0;
    try{
      logger.info("Entering getMerchandiseCategoryNavigationListXML method");
      dBConnection=new DBConnection(dataSource);
      sqlString="{?=call sp_get_m_cat_nav_list(?)}";
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.VARCHAR);
      cs.setInt(2,merchandiseCategoryTblPk);
      dBConnection.executeCallableStatement(cs);
      categoryNavList=cs.getString(1);
      categoryArray=categoryNavList.split("~"); //Splits the String into Group of PK and Category
      
      tempCategories = new ArrayList();
      for (int catCount=categoryArray.length-1;catCount>=0;catCount--){
        intCurrentCharsLength+=(categoryArray[catCount].split(","))[1].length();
        if (intCurrentCharsLength<=intMaxCharsLength){
          tempCategories.add(categoryArray[catCount].split(","));
        }
        logger.debug(" Categories : " + catCount + " - " + categoryArray[catCount]);
        logger.debug(" Char Length : " + intCurrentCharsLength);
      }
      
      categoriesXMLString="<?xml version=\"1.0\" ?>\n";
      categoriesXMLString+="<merchandise_category>\n";
      for (int catCount=tempCategories.size()-1;catCount>=0;catCount--){
        categoriesXMLString+="<name" ;
        categoriesXMLString+=" id=\"" ;
        categoriesXMLString+= ((String[])tempCategories.get(catCount))[0];
        categoriesXMLString+="\" morc=\"" ;
        categoriesXMLString+= ((String[])tempCategories.get(catCount))[2];
        categoriesXMLString+="\" >";
        categoriesXMLString+=((String[])tempCategories.get(catCount))[1];
        categoriesXMLString+="</name>\n";
      }
      categoriesXMLString+="</merchandise_category>\n";
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting getMerchandiseCategoryNavigationListXML method");
    }
    return categoriesXMLString;
  }
  public static String getFontCategoryNavigationListXML(int fontCategoryTblPk,DataSource dataSource )throws SQLException,Exception{
    String categoriesXMLString=null;
    ArrayList tempCategories=null;
    DBConnection dBConnection=null;
    String sqlString=null;
    CallableStatement cs=null;
    String categoryNavList=null;
    String[]  categoryArray=null;
    int intMaxCharsLength=80;
    int intCurrentCharsLength=0;
    try{
      logger.info("Entering getFontCategoryNavigationListXML method");
      dBConnection=new DBConnection(dataSource);
      sqlString="{?=call sp_get_font_cat_nav_list(?)}";
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.VARCHAR);
      cs.setInt(2,fontCategoryTblPk);
      dBConnection.executeCallableStatement(cs);
      categoryNavList=cs.getString(1);
      categoryArray=categoryNavList.split("~"); //Splits the String into Group of PK and Category
      
      tempCategories = new ArrayList();
      for (int catCount=categoryArray.length-1;catCount>=0;catCount--){
        intCurrentCharsLength+=(categoryArray[catCount].split(","))[1].length();
        if (intCurrentCharsLength<=intMaxCharsLength){
          tempCategories.add(categoryArray[catCount].split(","));
        }
        logger.debug(" Categories : " + catCount + " - " + categoryArray[catCount]);
        logger.debug(" Char Length : " + intCurrentCharsLength);
      }
      
      categoriesXMLString="<?xml version=\"1.0\" ?>\n";
      categoriesXMLString+="<font_category>\n";
      for (int catCount=tempCategories.size()-1;catCount>=0;catCount--){
        categoriesXMLString+="<name" ;
        categoriesXMLString+=" id=\"" ;
        categoriesXMLString+= ((String[])tempCategories.get(catCount))[0];
        categoriesXMLString+="\" >";
        categoriesXMLString+=((String[])tempCategories.get(catCount))[1];
        categoriesXMLString+="</name>\n";
      }
      categoriesXMLString+="</font_category>\n";
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting getFontCategoryNavigationListXML method");
    }
    return categoriesXMLString;
  }
  
  public static String getCurrentDesign(DataSource dataSourceBO,DataSource dataSourceFO,String merchandiseColorTblPk,String customerDesignTblPk)throws SQLException,Exception{
    DBConnection dBConnection = null;
    ResultSet rs=null;
    String currentDesignXMLString=null;
    String sqlString=null;
    try {
      logger.info("Entering getCurrentDesign");
      currentDesignXMLString="<?xml version=\"1.0\" ?>\n";
      currentDesignXMLString+="<currentDesign>\n";
      
      if (!customerDesignTblPk.equals("-1")) {
        sqlString="\n select ";
        sqlString+="\n cdt.customer_design_name, ";
        sqlString+="\n cdt.design_4_char_code, ";
        sqlString+="\n cet.customer_email_id, ";
        sqlString+="\n ptbl.merchandise_name, ";
        sqlString+="\n ptbl.threshold_quantity, ";
        sqlString+="\n ptbl.decoration_name ";
        sqlString+="\n from customer_design_tbl cdt, ";
        sqlString+="\n customer_email_id_tbl cet, ";
        sqlString+="\n dblink('dbname=bulbulbo', ";
        sqlString+="\n 'select ";
        sqlString+="\n mtbl.merchandise_name, ";
        sqlString+="\n mtbl.threshold_quantity, ";
        sqlString+="\n mdt.decoration_name, ";
        sqlString+="\n mct.merchandise_color_tbl_pk ";
        sqlString+="\n from ";
        sqlString+="\n bulbul.merchandise_color_tbl mct, ";
        sqlString+="\n bulbul.merchandise_tbl mtbl, ";
        sqlString+="\n bulbul.merchandise_decoration_tbl mdt ";
        sqlString+="\n where mct.merchandise_tbl_pk=mtbl.merchandise_tbl_pk ";
        sqlString+="\n and mdt.merchandise_decoration_tbl_pk=mtbl.merchandise_decoration_tbl_pk ";
        sqlString+="\n ') ";
        sqlString+="\n as ";
        sqlString+="\n ptbl( ";
        sqlString+="\n merchandise_name varchar, ";
        sqlString+="\n threshold_quantity int, ";
        sqlString+="\n decoration_name varchar, ";
        sqlString+="\n merchandise_color_tbl_pk int) ";
        sqlString+="\n where cdt.customer_email_id_tbl_pk=cet.customer_email_id_tbl_pk ";
        sqlString+="\n and ptbl.merchandise_color_tbl_pk=cdt.merchandise_color_tbl_pk ";
        sqlString+="\n and cdt.customer_design_tbl_pk=" + customerDesignTblPk;
        
        dBConnection = new DBConnection(dataSourceFO);
      }else {
        sqlString="\n select ";
        sqlString+="\n 'untitled' as customer_design_name, ";
        sqlString+="\n '*' as design_4_char_code, ";
        sqlString+="\n '*' as customer_email_id, ";
        sqlString+="\n mtbl.merchandise_name, ";
        sqlString+="\n mtbl.threshold_quantity, ";
        sqlString+="\n mdt.decoration_name ";
        sqlString+="\n from merchandise_tbl mtbl, ";
        sqlString+="\n merchandise_color_tbl mct, ";
        sqlString+="\n merchandise_decoration_tbl mdt ";
        sqlString+="\n where mtbl.merchandise_tbl_pk=mct.merchandise_tbl_pk ";
        sqlString+="\n and mdt.merchandise_decoration_tbl_pk=mtbl.merchandise_decoration_tbl_pk ";
        sqlString+="\n and mct.merchandise_color_tbl_pk=" + merchandiseColorTblPk; 
        
        dBConnection = new DBConnection(dataSourceBO);
      }
      logger.debug("sqlString : " + sqlString);
      rs=dBConnection.executeQuery(sqlString);
      if(rs!=null){
        while(rs.next()){
          currentDesignXMLString+="<design>";
          currentDesignXMLString+=rs.getString("customer_design_name");
          currentDesignXMLString+="</design>\n";
          currentDesignXMLString+="<code>";
          currentDesignXMLString+=rs.getString("design_4_char_code");
          currentDesignXMLString+="</code>\n";
          currentDesignXMLString+="<email>";
          currentDesignXMLString+=rs.getString("customer_email_id");
          currentDesignXMLString+="</email>\n";
          currentDesignXMLString+="<merchandise>";
          currentDesignXMLString+=rs.getString("merchandise_name");
          currentDesignXMLString+="</merchandise>\n";
          currentDesignXMLString+="<qty>";
          currentDesignXMLString+=rs.getString("threshold_quantity");
          currentDesignXMLString+="</qty>\n";
          currentDesignXMLString+="<decoration>";
          currentDesignXMLString+=rs.getString("decoration_name");
          currentDesignXMLString+="</decoration>\n";
        }
      }

    } catch (SQLException e) {
      logger.info(e);
      throw e;
    } catch (Exception e) {
      logger.info(e);
      throw e;
    } finally {
      currentDesignXMLString+="</currentDesign>\n";
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getCurrentDesign");
    }
    return currentDesignXMLString ;
  }
  
  public static String  deleteDesignElement(DataSource dataSource, int customerDesignDetailTblPk,int elementId)throws SQLException,Exception{
    DBConnection dBConnection=null;
    String sqlString=null;
    CallableStatement cs=null;
    String resultXMLString=null;
    int returnCustomerDesignDetailTblPk=-1;
    try{
      logger.info("Entering deleteDesignElement method");
      dBConnection=new DBConnection(dataSource);
      sqlString="{?=call sp_del_customer_design_element_on_delete(?,?)}";
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,customerDesignDetailTblPk);
      cs.setInt(3,elementId);
      dBConnection.executeCallableStatement(cs);
      returnCustomerDesignDetailTblPk=cs.getInt(1);
      logger.debug("returnCustomerDesignDetailTblPk : " + returnCustomerDesignDetailTblPk);
      resultXMLString="<?xml version=\"1.0\" ?>\n";
      resultXMLString+="<delete_element>\n";
      resultXMLString+="<result>";
      resultXMLString+=(returnCustomerDesignDetailTblPk==-1)?false:true;
      resultXMLString+="</result>\n";
      resultXMLString+="</delete_element>\n";
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting deleteDesignElement method");
    }
    return resultXMLString;
  }
  
  public static String  openDesign(DataSource dataSource,String customerDesignCode,String customerDesignName,String customerEmailId)throws SQLException,Exception{
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    String resultXMLString="<?xml version=\"1.0\" ?>\n";
    
    try {
      logger.info("Entering openDesign");
      resultXMLString+="<design>";

      query = "\n select ";
      query += "\n cdt.customer_design_tbl_pk, ";
      query += "\n cdt.merchandise_color_tbl_pk, ";
      query += "\n cdt.customer_design_name, ";
      query += "\n cdt.design_4_char_code, ";
      query += "\n cet.customer_email_id ";
      query += "\n from customer_design_tbl cdt, ";
      query += "\n customer_email_id_tbl cet ";
      query += "\n where ";
      query += "\n cdt.customer_email_id_tbl_pk=cet.customer_email_id_tbl_pk ";
      if(customerDesignCode!=null){
        query += "\n and cdt.design_4_char_code='" + customerDesignCode + "'";
      }else{
        query += "\n and cet.customer_email_id='" + customerEmailId + "'";
        query += "\n and cdt.customer_design_name='" + customerDesignName + "'";
      }
      
      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);
      if(rs.next()){
        resultXMLString+="<pk>";
        resultXMLString+=rs.getString("customer_design_tbl_pk");
        resultXMLString+="</pk>\n";
        resultXMLString+="<mcolorpk>";
        resultXMLString+=rs.getString("merchandise_color_tbl_pk");
        resultXMLString+="</mcolorpk>\n";
        resultXMLString+="<name>";
        resultXMLString+=rs.getString("customer_design_name");
        resultXMLString+="</name>\n";
        resultXMLString+="<email>";
        resultXMLString+=rs.getString("customer_email_id");
        resultXMLString+="</email>\n";
        resultXMLString+="<code>";
        resultXMLString+=rs.getString("design_4_char_code");
        resultXMLString+="</code>\n";
      }
      
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      resultXMLString+="</design>";
      logger.info("Exiting openDesign");
    }
    return resultXMLString;
  }
  
  public static String  populateDesign(DataSource dataSource,int customerDesignTblPk)throws SQLException,Exception{
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    String elementId=null;
    String resultXMLString="<?xml version=\"1.0\" ?>\n";
    
    int customerDesignDetailTblPk=-1;
    int customerDesignElementTblPk=-1;
    
    try {
      logger.info("Entering openDesign");
      resultXMLString+="<design>\n";

      query="\n select ";
      query+="\n cddt.customer_design_detail_tbl_pk, ";
      query+="\n cddt.merchandise_printable_area_tbl_pk, ";
      query+="\n cdet.customer_design_element_tbl_pk, ";
      query+="\n cdet.element_id, ";
      query+="\n cdet.element_type, ";
      query+="\n cdet.width, ";
      query+="\n cdet.height, ";
      query+="\n cdet.posx, ";
      query+="\n cdet.posy, ";
      query+="\n cdet.style, ";
      query+="\n cdet.rotate, ";
      query+="\n cdept.property_name, ";
      query+="\n cdept.property_value ";
      query+="\n from ";
      query+="\n customer_design_detail_tbl cddt ";
      query+="\n left join  customer_design_element_tbl cdet on cddt.customer_design_detail_tbl_pk=cdet.customer_design_detail_tbl_pk ";
      query+="\n left join  customer_design_element_property_tbl cdept on cdet.customer_design_element_tbl_pk=cdept.customer_design_element_tbl_pk ";
      query+="\n where 1=1 ";
      query+="\n and cddt.customer_design_tbl_pk=" + customerDesignTblPk;
      
      
      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query);
      while(rs.next()){
        if(customerDesignDetailTblPk!=rs.getInt("customer_design_detail_tbl_pk")){
          if(customerDesignDetailTblPk!=-1){            
            if(elementId!=null){
              resultXMLString+="\t\t</element>\n";
            }
            resultXMLString+="\t</detail>\n";
          }
          
          customerDesignDetailTblPk=rs.getInt("customer_design_detail_tbl_pk");
          
          resultXMLString+="\t<detail>\n";
          resultXMLString+="\t\t<detailpk>";
          resultXMLString+=customerDesignDetailTblPk;
          resultXMLString+="</detailpk>\n";
          resultXMLString+="\t\t<mptblpk>";
          resultXMLString+=rs.getString("merchandise_printable_area_tbl_pk");
          resultXMLString+="</mptblpk>\n";
          
          customerDesignElementTblPk=-1;
          
        }
        elementId=rs.getString("element_id");
        if (elementId!=null) {
          if(customerDesignElementTblPk!=rs.getInt("customer_design_element_tbl_pk")){
           if(customerDesignElementTblPk!=-1){            
             resultXMLString+="\t\t</element>\n";
           }
           
           customerDesignElementTblPk=rs.getInt("customer_design_element_tbl_pk");
           
           resultXMLString+="\t\t<element>\n";
           resultXMLString+="\t\t\t<id>";
           resultXMLString+=elementId;
           resultXMLString+="</id>\n";
           resultXMLString+="\t\t\t<type>";
           resultXMLString+=rs.getString("element_type");
           resultXMLString+="</type>\n";
           resultXMLString+="\t\t\t<width>";
           resultXMLString+=rs.getString("width");
           resultXMLString+="</width>\n";
           resultXMLString+="\t\t\t<height>";
           resultXMLString+=rs.getString("height");
           resultXMLString+="</height>\n";
           resultXMLString+="\t\t\t<posx>";
           resultXMLString+=rs.getString("posx");
           resultXMLString+="</posx>\n";
           resultXMLString+="\t\t\t<posy>";
           resultXMLString+=rs.getString("posy");
           resultXMLString+="</posy>\n";
           resultXMLString+="\t\t\t<style>";
           resultXMLString+=rs.getString("style");
           resultXMLString+="</style>\n";
           resultXMLString+="\t\t\t<rotate>";
           resultXMLString+=rs.getString("rotate");
           resultXMLString+="</rotate>\n";           
          }
          
          resultXMLString+="\t\t\t<property>\n";  
          resultXMLString+="\t\t\t\t<name>";
          resultXMLString+=rs.getString("property_name");
          resultXMLString+="</name>\n";
          resultXMLString+="\t\t\t\t<value>";
          resultXMLString+=rs.getString("property_value");
          resultXMLString+="</value>\n";
          resultXMLString+="\t\t\t</property>\n"; 
        }
        
        if(rs.isLast()){
          if (elementId!=null) {
            resultXMLString+="\t\t</element>\n";
          }
          resultXMLString+="\t</detail>\n";
        }
      }
      
    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      resultXMLString+="</design>";
      logger.info("Exiting openDesign");
    }
    return resultXMLString;
  }
  
}
