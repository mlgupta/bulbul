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
import bulbul.foff.studio.actionforms.DesignSaveForm;
import bulbul.foff.studio.actionforms.TrackDesignForm;
import bulbul.foff.studio.actionforms.TrackOrderForm;
import bulbul.foff.studio.common.ClipartBean;
import bulbul.foff.studio.common.ClipartCategoryBean;
import bulbul.foff.studio.common.ColorBean;
import bulbul.foff.studio.common.ImageBean;
import bulbul.foff.studio.common.MerchandiseBean;

import bulbul.foff.studio.common.MerchandiseViewBean;
import bulbul.foff.studio.common.PrintableAreaBean;
import bulbul.foff.studio.common.SaveDesignBean;
import bulbul.foff.studio.common.SizeBean;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.Enumeration;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import javax.swing.ImageIcon;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.SVGConstants;
import org.apache.log4j.Logger;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

public final class Operations {

  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());

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
/*
  public static String saveDesign(DataSource dataSourceFO, DataSource dataSourceBO,
                                  DesignSaveForm designSaveForm) throws SQLException, Exception {
    DBConnection dBConnection = null;
    String sqlString;
    CallableStatement cs = null;
    String returnString = null;
    int oldOId = 0;
    int newOId = 0;
    byte[] content;
    try {
      logger.info("Entering saveDesign() method");
      sqlString = "{?=call sp_ins_customer_design_tbl(?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSourceFO);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1, Types.CHAR);
      cs.setString(2, designSaveForm.getTxtYourEmailId());
      cs.setString(3, designSaveForm.getHdnMerchandiseColorTblPk());
      cs.setString(4, designSaveForm.getTxtDesignName());
      newOId = LOOperations.getLargeObjectId(dataSourceFO);
      oldOId = Integer.parseInt(designSaveForm.getHdnDesignOId());
      content = LOOperations.getLargeObjectContent(oldOId, dataSourceBO);
      LOOperations.putLargeObjectContent(newOId, content, dataSourceFO);
      designSaveForm.setHdnDesignOId(Integer.toString(newOId));
      cs.setString(5, Integer.toString(newOId));
      cs.setString(6, designSaveForm.getHdnDesignContentType());
      cs.setString(7, designSaveForm.getHdnDesignContentSize());
      dBConnection.executeCallableStatement(cs);
      returnString = cs.getString(1);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      logger.debug("newOId" + newOId);
      LOOperations.removeLargeObjectId(dataSourceFO, newOId);
      designSaveForm.setHdnDesignOId(Integer.toString(oldOId));
      logger.debug("Inside SQL Exception of Save Design");
      throw e;
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting saveDesign() method");
    }
    return returnString;
  }
*/
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
        customerInfo.setCustomerEmailIdTblPk(rs.getString("customer_email_id_tbl_pk"));
        customerInfo.setCustomerEmailId(rs.getString("customer_email_id"));
        designBean = new DesignBean();
        designBean.setCustomerDesignTblPk(rs.getString("customer_design_tbl_pk"));
        designBean.setDesignName(rs.getString("customer_design_name"));
        designBean.setDesignCode(rs.getString("design_4_char_code"));
        //designBean.setDesignOId(rs.getString("customer_design"));
        //designBean.setDesignContentType(rs.getString("design_content_type"));
        //designBean.setDesignContentSize(rs.getString("design_content_size"));
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
        customerInfo.setCustomerEmailIdTblPk(rs.getString("customer_email_id_tbl_pk"));
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

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param clipartCategoryTblPk
   * @param dataSource
   */
  public static ClipartCategoryBean[] getClipartCategories(DataSource dataSource,
                                                           String clipartCategoryTblPk) throws SQLException,
                                                                                                                      Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    ClipartCategoryBean[] clipartCategories = null;
    ClipartCategoryBean clipartCategory = null;
    try {
      logger.info("Entering getClipartCategories");

      query = "\n select ";
      query += "\n outcct.clipart_category_tbl_pk, ";
      query += "\n outcct.clipart_category, ";
      query += "\n outcct.clipart_category_description, ";
      query += "\n (select ";
      query += "\n count(*) ";
      query += "\n from clipart_category_tbl incct  ";
      query += "\n where incct.is_active=1 ";
      query += "\n and incct.clipart_category_tbl_fk=outcct.clipart_category_tbl_pk)  ";
      query += "\n as childcount ";
      query += "\n from clipart_category_tbl as outcct ";
      query += "\n where outcct.is_active=1";
      query += "\n and( ";
      query += "\n ( ";
      query += "\n (select ";
      query += "\n count(*) ";
      query += "\n from clipart_category_tbl cct ";
      query += "\n where cct.is_active=1 ";
      query += "\n and cct.clipart_category_tbl_fk=outcct.clipart_category_tbl_pk)!=0 ";
      query += "\n ) or ( ";
      query += "\n (select ";
      query += "\n count(*) ";
      query += "\n from clipart_tbl ctbl ";
      query += "\n where ctbl.is_active=1 ";
      query += "\n and ctbl.clipart_category_tbl_pk=outcct.clipart_category_tbl_pk)!=0 ";
      query += "\n ) ";
      query += "\n ) ";
      if (clipartCategoryTblPk.equals("-1")) {
        query += "\n and outcct.clipart_category_tbl_fk is null ";
      } else {
        query += "\n and outcct.clipart_category_tbl_fk='" + clipartCategoryTblPk + "'";
      }

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs.last();
      clipartCategories = new ClipartCategoryBean[rs.getRow()];
      rs.beforeFirst();
      int index = 0;
      while (rs.next()) {
        clipartCategory = new ClipartCategoryBean();
        clipartCategory.setPk(rs.getString("clipart_category_tbl_pk"));
        clipartCategory.setName(rs.getString("clipart_category"));
        clipartCategory.setDescription(rs.getString("clipart_category_description"));
        clipartCategory.setSubCategoriesAvailable((rs.getInt("childcount") == 0 ? false : true));
        clipartCategories[index++] = clipartCategory;
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
      logger.info("Exiting getClipartCategories");
    }
    return clipartCategories;
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param searchKeywords
   * @param search
   * @param clipartCategoryTblPk
   * @param dataSource
   */
  public static ClipartBean[] getCliparts(DataSource dataSource, String clipartCategoryTblPk, boolean search,
                                          String searchKeywords) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    ClipartBean[] cliparts = null;
    ClipartBean clipart = null;
    try {
      logger.info("Entering getCliparts");
      query = "\n select ";
      query += "\n clipart_tbl_pk, ";
      query += "\n clipart_name, ";
      query += "\n clipart_keywords, ";
      query += "\n clipart_image, ";
      query += "\n content_type, ";
      query += "\n content_size ";
      query += "\n from clipart_tbl ";
      query += "\n where is_active=1 ";
      if (search) {
        query += "\n and clipart_keywords like '%" + searchKeywords + "%'";
      } else {
        query += "\n and clipart_category_tbl_pk=" + clipartCategoryTblPk;
      }

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs.last();
      cliparts = new ClipartBean[rs.getRow()];
      rs.beforeFirst();
      int index = 0;
      while (rs.next()) {
        clipart = new ClipartBean();
        clipart.setPk(rs.getString("clipart_tbl_pk"));
        clipart.setName(rs.getString("clipart_name"));
        clipart.setKeywords(rs.getString("clipart_keywords"));
        clipart.setContentOId(Integer.parseInt(rs.getString("clipart_image")));
        clipart.setContentType(rs.getString("content_type"));
        clipart.setContentSize(Integer.parseInt(rs.getString("content_size")));
        cliparts[index++] = clipart;
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
      logger.info("Exiting getCliparts");
    }
    return cliparts;
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
  public static ImageBean[] getImages(DataSource dataSource, String customerEmailIdTblPk,
                                      String format) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    ImageBean[] images = null;
    ImageBean image = null;
    try {
      logger.info("Entering getImages");
      query = "\n select ";
      query += "\n customer_graphics_tbl_pk, ";
      query += "\n graphics_title, ";
      query += "\n graphics_description, ";
      query += "\n content_oid, ";
      query += "\n content_type, ";
      query += "\n content_size ";
      query += "\n from customer_graphics_tbl where 1=1 ";

      if (format.equals(ImageFormat.JPG.toString())) {
        query += "\n and lower(graphics_category)=lower('jpg')";
      }
      if (format.equals(ImageFormat.GIF.toString())) {
        query += "\n and lower(graphics_category)=lower('gif')";
      }
      if (format.equals(ImageFormat.PNG.toString())) {
        query += "\n and lower(graphics_category)=lower('png')";
      }
      if (format.equals(ImageFormat.SVG.toString())) {
        query += "\n and lower(graphics_category)=lower('svg')";
      }
      query += "\n and customer_email_id_tbl_pk=" + customerEmailIdTblPk + " order by uploaded_on desc";

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs.last();
      images = new ImageBean[rs.getRow()];
      rs.beforeFirst();
      int index = 0;
      while (rs.next()) {
        image = new ImageBean();
        image.setPk(rs.getString("customer_graphics_tbl_pk"));
        image.setTitle(rs.getString("graphics_title"));
        image.setDescription(rs.getString("graphics_description"));
        image.setContentOId(Integer.parseInt(rs.getString("content_oid")));
        image.setContentType(rs.getString("content_type"));
        image.setContentSize(Integer.parseInt(rs.getString("content_size")));
        images[index++] = image;
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
      logger.info("Exiting getImages");
    }
    return images;
  }

  /**
   *	Purpose: OutStreams the byte array into the response a Response Object
   *  @param content - An Array of byte 
   *  @param contentType - A String Object Defined by content type
   *  @param contentSize - An int data type Defined by content size
   *  @param response - A HttpServletResponse Object
   */
  public static void svgImageDisplay(byte[] content, String contentType, int contentSize,
                                     HttpServletResponse response) throws IOException, Exception {
    OutputStream os = null;
    try {
      logger.info("Entering svgImageDisplay");

      logger.debug("Content Type : " + contentType);
      logger.debug("Content Size : " + contentSize);

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

      response.setContentType(ContentType.SVG.toString());
      os = response.getOutputStream();
      svgGenerator.stream(svgRoot, new OutputStreamWriter(os, "UTF-8"));
      response.flushBuffer();

    } catch (IOException ioe) {
      logger.error(ioe);
      ioe.printStackTrace();
      throw ioe;
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      throw e;
    } finally {
      if (os != null) {
        logger.debug("Closing and Flushing Output Stream");
        os.flush();
        logger.debug("Output Stream Flushed");
        os.close();
        logger.debug("Output Stream Closed");
      }
      logger.info("Exiting svgImageDisplay");
    }
  }

  public static boolean uploadImage(DataSource dataSource, String customerEmailIdTblPk, String fileTitle,
                                    String fileDesc, String contentType, int contentSize, byte[] content,
                                    String graphicsCategory) throws SQLException, IOException, Exception {

    String sqlString = null;
    int returnCustomerGraphicsTblPk = -1;
    int oid = 0;
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    ResultSet rs = null;
    boolean result = false;
    try {
      logger.info("Entering uploadImage");
      dBConnection = new DBConnection(dataSource);
      logger.info("File Title : " + fileTitle);
      logger.info("File Description : " + fileDesc);
      logger.info("Category : " + graphicsCategory);
      logger.info("contentType : " + contentType);
      oid = LOOperations.getLargeObjectId(dataSource);
      LOOperations.putLargeObjectContent(oid, content, dataSource);
      sqlString = "{?=call sp_upload_customer_graphics(?,?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1, Types.INTEGER);
      cs.setString(2, customerEmailIdTblPk);
      cs.setString(3, fileTitle);
      cs.setString(4, fileDesc);
      cs.setInt(5, oid);
      cs.setString(6, contentType);
      cs.setString(7, Integer.toString(contentSize));
      cs.setString(8, graphicsCategory);
      dBConnection.executeCallableStatement(cs);
      returnCustomerGraphicsTblPk = cs.getInt(1);


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
      if (returnCustomerGraphicsTblPk != -1) {
        result = true;
      }
      if (dBConnection != null) {
        dBConnection.freeResultSet(rs);
        dBConnection.freeCallableSatement(cs);
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting uploadImage");
    }
    return result;
  }

  /**
   * 
   * @param dataSource
   * @param merchandiseCategoryTblPk
   * @return 
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   * @description 
   */
  public static MerchandiseBean[] getMerchandiseArray(DataSource dataSource,
                                                      String merchandiseCategoryTblPk) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    MerchandiseBean[] merchandiseArray = null;
    MerchandiseBean merchandise = null;
    try {
      logger.info("Entering getMerchandise");

      query = "\n select ";
      query += "\n fk, ";
      query += "\n pk, ";
      query += "\n name, ";
      query += "\n description, ";
      query += "\n categoryonly, ";
      query += "\n iscategory, ";
      query += "\n childcount ";
      query += "\n from ";
      query += "\n (select ";
      query += "\n outmctbl.merchandise_category_tbl_fk as fk, ";
      query += "\n outmctbl.merchandise_category_tbl_pk as pk, ";
      query += "\n outmctbl.merchandise_category as name, ";
      query += "\n outmctbl.merchandise_category_description as description, ";
      query += "\n outmctbl.m_or_c as categoryonly, ";
      query += "\n 1 as iscategory, ";
      query += "\n (select count(*) from merchandise_category_tbl as inmctbl ";
      query += "\n where inmctbl.merchandise_category_tbl_fk=outmctbl.merchandise_category_tbl_pk ";
      query += "\n ) as childcount ";
      query += "\n from merchandise_category_tbl as outmctbl ";
      query += "\n where outmctbl.is_active=1 ";
      query += "\n and outmctbl.m_or_c=1 ";
      query += "\n and (select count(*) from merchandise_category_tbl as inmctbl ";
      query += "\n where inmctbl.merchandise_category_tbl_fk=outmctbl.merchandise_category_tbl_pk ";
      query += "\n )!=0 ";
      query += "\n union ";
      query += "\n select ";
      query += "\n outmctbl.merchandise_category_tbl_fk as fk, ";
      query += "\n outmctbl.merchandise_category_tbl_pk as pk, ";
      query += "\n outmctbl.merchandise_category as name, ";
      query += "\n outmctbl.merchandise_category_description as description, ";
      query += "\n outmctbl.m_or_c as categoryonly, ";
      query += "\n 1 as iscategory, ";
      query += "\n ( ";
      query += "\n select ";
      query += "\n count(*) ";
      query += "\n from merchandise_parents_tbl as mptbl, ";
      query += "\n merchandise_tbl as mtbl ";
      query += "\n where mtbl.merchandise_tbl_pk = mptbl.merchandise_tbl_pk ";
      query += "\n and mptbl.merchandise_category_tbl_pk=outmctbl.merchandise_category_tbl_pk ";
      query += "\n and mptbl.is_active=1 ";
      query += "\n ) as childcount ";
      query += "\n from merchandise_category_tbl as outmctbl ";
      query += "\n where outmctbl.is_active=1 ";
      query += "\n and outmctbl.m_or_c=0 ";
      query += "\n and ( ";
      query += "\n select ";
      query += "\n count(*) ";
      query += "\n from merchandise_parents_tbl as mptbl, ";
      query += "\n merchandise_tbl as mtbl ";
      query += "\n where mtbl.merchandise_tbl_pk = mptbl.merchandise_tbl_pk ";
      query += "\n and mptbl.merchandise_category_tbl_pk=outmctbl.merchandise_category_tbl_pk ";
      query += "\n and mptbl.is_active=1 ";
      query += "\n )!=0 ";
      query += "\n union ";
      query += "\n select ";
      query += "\n mptbl.merchandise_category_tbl_pk as fk, ";
      query += "\n mtbl.merchandise_tbl_pk as pk, ";
      query += "\n mtbl.merchandise_name as name, ";
      query += "\n mtbl.material_description as description, ";
      query += "\n 0 as categoryonly, ";
      query += "\n 0 as iscategory, ";
      query += "\n 0 as childcount ";
      query += "\n from merchandise_parents_tbl as mptbl, ";
      query += "\n merchandise_tbl as mtbl ";
      query += "\n where mtbl.merchandise_tbl_pk = mptbl.merchandise_tbl_pk ";
      query += "\n and mptbl.is_active=1 ";
      query += "\n ) as merchandise where 1=1 ";

      if (merchandiseCategoryTblPk.equals("-1")) {
        query += "\n and merchandise.fk is null ";
      } else {
        query += "\n and merchandise.fk='" + merchandiseCategoryTblPk + "'";
      }

      query += "\n order by merchandise.name";

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs.last();
      merchandiseArray = new MerchandiseBean[rs.getRow()];
      rs.beforeFirst();
      int index = 0;
      while (rs.next()) {
        merchandise = new MerchandiseBean();
        merchandise.setPk(rs.getString("pk"));
        merchandise.setName(rs.getString("name"));
        merchandise.setDescription(rs.getString("description"));
        merchandise.setCategory((rs.getInt("iscategory") == 0 ? false : true));
        merchandise.setCategoryOnly((rs.getInt("categoryonly") == 0 ? false : true));
        merchandise.setChildrenAvailable((rs.getInt("childcount") == 0 ? false : true));
        merchandiseArray[index++] = merchandise;
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
      logger.info("Exiting getMerchandise");
    }
    return merchandiseArray;
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param merchandiseTblPk
   * @param dataSource
   */
  public static MerchandiseViewBean getMerchandiseView(DataSource dataSource,
                                                       String merchandiseTblPk) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    MerchandiseViewBean merchandise = null;
    ColorBean color = null;
    SizeBean size = null;
    String merchandiseColorTblPk = "-1";
    boolean firstTime = true;
    Hashtable colorBeanTable = null;
    ArrayList sizeBeanArray = null;

    try {
      logger.info("Entering getMerchandiseView");

      query = "\n select ";
      query += "\n mtbl.merchandise_tbl_pk, ";
      query += "\n mtbl.merchandise_name, ";
      query += "\n mtbl.merchandise_description, ";
      query += "\n mtbl.merchandise_comment, ";
      query += "\n mtbl.material_description, ";
      query += "\n mtbl.delivery_note, ";
      query += "\n mtbl.threshold_quantity, ";
      query += "\n mtbl.merchandise_display_image, ";
      query += "\n mtbl.display_image_content_type, ";
      query += "\n mtbl.display_image_content_size, ";
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
      query += "\n and mtbl.merchandise_tbl_pk=" + merchandiseTblPk;

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);

      rs = dBConnection.executeQuery(query);


      while (rs.next()) {
        if (firstTime) {

          firstTime = false;
          merchandise = new MerchandiseViewBean();
          colorBeanTable = new Hashtable();
          merchandise.setMerchandiseTblPk(rs.getString("merchandise_tbl_pk"));
          merchandise.setMerhandiseName(rs.getString("merchandise_name"));
          merchandise.setMerchandiseDescription(rs.getString("merchandise_description"));
          merchandise.setMerchandiseComment(rs.getString("merchandise_comment"));
          merchandise.setMaterialDescription(rs.getString("material_description"));
          merchandise.setMinimumQuantity(rs.getString("threshold_quantity"));
          merchandise.setDeliveryNote(rs.getString("delivery_note"));
          merchandise
          .setImageData(LOOperations.getLargeObjectContent(Integer.parseInt(rs.getString("merchandise_display_image")),
                                                                      dataSource));
        }

        if (!(merchandiseColorTblPk.equals(rs.getString("merchandise_color_tbl_pk")))) {

          if (color != null) {
            color.setSize(sizeBeanArray);
            colorBeanTable.put(merchandiseColorTblPk, color);
          }

          sizeBeanArray = new ArrayList();
          merchandiseColorTblPk = rs.getString("merchandise_color_tbl_pk");
          color = new ColorBean();
          color.setMerchandiseColorTblPk(merchandiseColorTblPk);
          color.setColorOneName(rs.getString("color_one_name"));
          color.setColorTwoName(rs.getString("color_two_name"));
          color.setColorOneValue(rs.getString("color_one_value"));
          color.setColorTwoValue(rs.getString("color_two_value"));

        }
        size = new SizeBean();
        size.setSizeTypeId(rs.getString("size_type_id"));
        size.setSizeTypeDescription(rs.getString("size_type_description"));
        size.setSizeId(rs.getString("size_id"));
        size.setSizeDescription(rs.getString("size_description"));
        sizeBeanArray.add(size);
      }


      if (color != null) {
        color.setSize(sizeBeanArray);
        colorBeanTable.put(merchandiseColorTblPk, color);
      }

      if (merchandise != null) {
        merchandise.setColor(colorBeanTable);
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
      logger.info("Exiting getMerchandiseView");
    }
    return merchandise;
  }


  /**
   * 
   * @description 
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   * @return 
   * @param merchandiseTblPk
   * @param dataSource
   */
  public static PrintableAreaBean[] getPrintableAreas(DataSource dataSource,
                                                      String merchandiseTblPk) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    PrintableAreaBean[] printableAreas = null;
    PrintableAreaBean printableArea = null;
    try {
      logger.info("Entering getPrintableAreas");
      query = "\n select ";
      query += "\n mptbl.merchandise_printable_area_tbl_pk, ";
      query += "\n ptbl.printable_area_name, ";
      query += "\n mptbl.design_image_content, ";
      query += "\n mptbl.content_type, ";
      query += "\n mptbl.content_size, ";
      query += "\n mptbl.area_width, ";
      query += "\n mptbl.area_height ";
      query += "\n from merchandise_printable_area_tbl mptbl,printable_area_tbl ptbl where 1=1 ";
      query += "\n and ptbl.printable_area_tbl_pk=mptbl.printable_area_tbl_pk";
      query += "\n and mptbl.merchandise_tbl_pk=" + merchandiseTblPk;
      

      logger.debug("query : " + query);

      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs.last();
      printableAreas = new PrintableAreaBean[rs.getRow()];
      rs.beforeFirst();
      int index = 0;
      while (rs.next()) {
        printableArea = new PrintableAreaBean();
        printableArea.setMerchandisePrintableAreaTblPk(rs.getString("merchandise_printable_area_tbl_pk"));
        printableArea.setName(rs.getString("printable_area_name"));
        printableArea
        .setImage(LOOperations.getLargeObjectContent(Integer.parseInt(rs.getString("design_image_content")),
                                                                  dataSource));
        printableArea.setWidth(Integer.parseInt(rs.getString("area_width")));
        printableArea.setHeight(Integer.parseInt(rs.getString("area_height")));
        printableAreas[index++] = printableArea;
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
    return printableAreas;
  }

  /**
   * @param dataSource
   * @param saveDesignObject
   * @param customerInfo
   * @return
   * @description This Function is called when ever user clicks Save or SaveAs button and also during Auto Save.
   *              If Design is New Mode or SaveAs Mode a new record will be inserted in the database otherwise existing 
   *              data will be updated. For New Mode or SaveAs Mode LargeObjects are Created for each printable area of 
   *              merchandise and design data is stored in them correspondingly and same LargeObject Id is stored in the
   *              the corresponding record.For Other Mode the in existing Largeobjects  the modified design data is 
   *              stored.  
   */
  public static CustomerInfo productCustomise(DataSource dataSource, ServletContext context,SaveDesignBean saveDesignObject,
                                         CustomerInfo customerInfo) throws SQLException, Exception {
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    ResultSet rs=null;
    
    try {
      logger.info("Entering productCustomise");
      if (saveDesignObject != null) {
        String sqlString=null;
        String designCode=null;
        
        String oidString = null;
        String merchandisePrintableAreaTblPkString=null;
        String contentTypeString = null;
        String contentSizeString = null;
        
        boolean isDesignNew = saveDesignObject.isDesignNew();
        boolean isCustomerNew = saveDesignObject.isCustomerNew();
        boolean isDesign2OverWrite = saveDesignObject.isDesign2OverWrite();

        String customerEmailIdTblPk = saveDesignObject.getCustomerEmailIdTblPk();
        String customerEmailId = saveDesignObject.getCustomerEmailId();
        String customerDesignTblPk = saveDesignObject.getCustomerDesignTblPk();
        String customerDesignName = saveDesignObject.getCustomerDesignName();
        String merchandiseTblPk = saveDesignObject.getMerchandiseTblPk();
        String merchandiseColorTblPk = saveDesignObject.getMerchandiseColorTblPk();

        int saveNdSaveAsMode = saveDesignObject.getSaveNdSaveAs();

        Hashtable designContentTable = saveDesignObject.getDesignContentTable();

        logger.debug("Design New is " + isDesignNew);
        logger.debug("Customer New is " + isCustomerNew);
        logger.debug("Design OverWrite is " + isDesign2OverWrite);
        logger.debug("CustomerEmailIdTblPk is " + customerEmailIdTblPk);
        logger.debug("Email ID is " + customerEmailId);
        logger.debug("CustomerDesignTblPk is " + customerDesignTblPk);
        logger.debug("Customer Design Name is " + customerDesignName);
        logger.debug("MerchandiseTblPk is " + merchandiseTblPk);
        logger.debug("MerchandiseColorTblPk is " + merchandiseColorTblPk);
        logger.debug("Save Or Save As Mode is " + saveNdSaveAsMode);

        dBConnection = new DBConnection(dataSource);
        //If Design is new or If design is In Save As Mode, insert new record into database
        if (isDesignNew || (saveNdSaveAsMode == SaveDesignBean.SAVE_AS)) {
          logger.debug("First Time Savew or Save As");
          //Starting array bracket
          merchandisePrintableAreaTblPkString="{";
          oidString = "{";
          contentTypeString="{";
          contentSizeString="{";
          
          //Loop the hash table to get the designBytes for each merchandisePrintableAreaTblPk
          for (Enumeration designContentKeys = designContentTable.keys(); designContentKeys.hasMoreElements(); ) {
            String merchandisePrintableAreaTblPk = (String)designContentKeys.nextElement();
            byte[] designBytes = (byte[])designContentTable.get(merchandisePrintableAreaTblPk);
            int oid = LOOperations.getLargeObjectId(dataSource);
            String contentType=ContentType.SVG.toString();
            int contentSize=designBytes.length;
            
            LOOperations.putLargeObjectContent(oid, designBytes, dataSource);
            merchandisePrintableAreaTblPkString+=merchandisePrintableAreaTblPk+",";
            oidString+=oid + ",";
            contentTypeString+=contentType+",";
            contentSizeString+=contentSize+",";
          }
          
          //To remove last comma ","
          oidString=oidString.substring(0,oidString.lastIndexOf(","));
          merchandisePrintableAreaTblPkString=merchandisePrintableAreaTblPkString.substring(0,merchandisePrintableAreaTblPkString.lastIndexOf(","));
          contentTypeString=contentTypeString.substring(0,contentTypeString.lastIndexOf(","));
          contentSizeString=contentSizeString.substring(0,contentSizeString.lastIndexOf(","));
          
          //Ending array bracket
          merchandisePrintableAreaTblPkString+="}";
          oidString+= "}";
          contentTypeString+="}";
          contentSizeString+="}";
          
          logger.debug("merchandisePrintableAreaTblPkString : " + merchandisePrintableAreaTblPkString);
          logger.debug("oidString : " + oidString);
          logger.debug("contentTypeString : " + contentTypeString);
          logger.debug("contentSizeString : " + contentSizeString);
          
          sqlString = "{?=call sp_ins_customer_design_tbl(?,?,?,?,?,?,?)}";
          cs = dBConnection.prepareCall(sqlString);

          cs.registerOutParameter(1, Types.CHAR);
          cs.setString(2,customerEmailId);
          cs.setString(3,merchandiseColorTblPk);
          cs.setString(4,customerDesignName);
          cs.setString(5,merchandisePrintableAreaTblPkString);
          cs.setString(6,oidString);
          cs.setString(7,contentTypeString);
          cs.setString(8,contentSizeString);
          dBConnection.executeCallableStatement(cs);
          String returnString=cs.getString(1);
          
          logger.debug("returnString : " + returnString);
         
          if (!(returnString.equals("-1"))){
            //taking results out of a comma seperated string
            designCode=returnString.split(",")[0];
            customerEmailIdTblPk=returnString.split(",")[1];
            customerDesignTblPk=returnString.split(",")[2];
            customerInfo.setCusotmerDesignTblPk(customerDesignTblPk);
            customerInfo.setCustomerEmailIdTblPk(customerEmailIdTblPk);

            String smtphost=(String)context.getAttribute("smtphost");
            String emailid=(String)context.getAttribute("emailid");
            
            String emailSubject="Your Design saved in www.printoon.com ";
            String emailMessage="You Design saved @ www.pritoon.com";
            //emailMessage+="<br> You Have Choosen The Product : " + designSaveForm.getHdnProductName();        
            emailMessage+="<br> Your Design Name : " + customerDesignName;        
            emailMessage+="<br> Your Mail Id : " + customerEmailId ;  
            emailMessage+="<br> Your Design Code : " + designCode;  
            emailMessage+="<br><br><br> Regards,";
            emailMessage+="<br> Administrator,";
            emailMessage+="<br> Printoon,";
            emailMessage+="<br> Email : " + emailid;
            //General.postMail(customerEmailId,emailSubject,emailMessage,emailid,smtphost);  
            
          }
          
        } else {
          //If Design is not new and in Save Mode, update exsting record into database
          String oldMerchandiseColorTblPk=null;
          sqlString="select merchandise_color_tbl_pk from customer_design_tbl where customer_design_tbl_pk=" + customerDesignTblPk;
          rs=dBConnection.executeQuery(sqlString);
          
          if(rs!=null){
           if(rs.next()){
             oldMerchandiseColorTblPk=rs.getString("merchandise_color_tbl_pk");
           }
          }
          
          if (merchandiseColorTblPk.equals(oldMerchandiseColorTblPk)){
            //update            
             logger.debug("Save - Same Merchandise");
            //Starting array bracket
            merchandisePrintableAreaTblPkString="{";
            oidString = "{";
            contentTypeString="{";
            contentSizeString="{";
             
            //Loop the hash table to get the designBytes for each merchandisePrintableAreaTblPk
            for (Enumeration designContentKeys = designContentTable.keys(); designContentKeys.hasMoreElements(); ) {
              String merchandisePrintableAreaTblPk = (String)designContentKeys.nextElement();
              byte[] designBytes = (byte[])designContentTable.get(merchandisePrintableAreaTblPk);
              sqlString = "{?=call sp_get_customer_design_oid(?,?)}";
              cs = dBConnection.prepareCall(sqlString);
              cs.registerOutParameter(1, Types.INTEGER);
              cs.setString(2,customerDesignTblPk);
              cs.setString(3,merchandisePrintableAreaTblPk);
              dBConnection.executeCallableStatement(cs);
              int oid =cs.getInt(1);

              String contentType=ContentType.SVG.toString();
              int contentSize=designBytes.length;
              
              LOOperations.putLargeObjectContent(oid, designBytes, dataSource);
              merchandisePrintableAreaTblPkString+=merchandisePrintableAreaTblPk+",";
              oidString+=oid + ",";
              contentTypeString+=contentType+",";
              contentSizeString+=contentSize+",";
            }
             
            //To remove last comma ","
            oidString=oidString.substring(0,oidString.lastIndexOf(","));
            merchandisePrintableAreaTblPkString=merchandisePrintableAreaTblPkString.substring(0,merchandisePrintableAreaTblPkString.lastIndexOf(","));
            contentTypeString=contentTypeString.substring(0,contentTypeString.lastIndexOf(","));
            contentSizeString=contentSizeString.substring(0,contentSizeString.lastIndexOf(","));
            
            //Ending array bracket
            merchandisePrintableAreaTblPkString+="}";
            oidString+= "}";
            contentTypeString+="}";
            contentSizeString+="}";
            
            logger.debug("merchandisePrintableAreaTblPkString : " + merchandisePrintableAreaTblPkString);
            logger.debug("oidString : " + oidString);
            logger.debug("contentTypeString : " + contentTypeString);
            logger.debug("contentSizeString : " + contentSizeString);
            
            sqlString = "{?=call sp_upd_customer_design_detail_tbl(?,?,?)}";
            cs = dBConnection.prepareCall(sqlString);
            
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2,customerDesignTblPk);
            cs.setString(3,merchandisePrintableAreaTblPkString);
            cs.setString(4,contentSizeString);
            dBConnection.executeCallableStatement(cs);
            int returnInteger=cs.getInt(1);
            logger.debug("returnInteger : " + returnInteger);
          
          }else{
            logger.debug("Save - Different Merchandise");
            //delete existing records from the customer_design_detail_tbl. 
            sqlString = "{?=call sp_del_customer_design_detail_tbl(?)}";
            cs = dBConnection.prepareCall(sqlString);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2,customerDesignTblPk);
            dBConnection.executeCallableStatement(cs);
            //int returnDeleteInteger=cs.getInt(1);
            
            //update customer_design_tbl
            sqlString = "{?=call sp_upd_customer_design_tbl(?,?)}";
            cs = dBConnection.prepareCall(sqlString);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2,customerDesignTblPk);
            cs.setString(3,merchandiseColorTblPk);
            dBConnection.executeCallableStatement(cs);
            //int returnUpdateInteger=cs.getInt(1);
             
            //insert new records into customer_design_detail_tbl
            
            //Starting array bracket
            merchandisePrintableAreaTblPkString="{";
            oidString = "{";
            contentTypeString="{";
            contentSizeString="{";
            
            //Loop the hash table to get the designBytes for each merchandisePrintableAreaTblPk
            for (Enumeration designContentKeys = designContentTable.keys(); designContentKeys.hasMoreElements(); ) {
              String merchandisePrintableAreaTblPk = (String)designContentKeys.nextElement();
              byte[] designBytes = (byte[])designContentTable.get(merchandisePrintableAreaTblPk);
              int oid = LOOperations.getLargeObjectId(dataSource);
              String contentType=ContentType.SVG.toString();
              int contentSize=designBytes.length;
              
              LOOperations.putLargeObjectContent(oid, designBytes, dataSource);
              merchandisePrintableAreaTblPkString+=merchandisePrintableAreaTblPk+",";
              oidString+=oid + ",";
              contentTypeString+=contentType+",";
              contentSizeString+=contentSize+",";
            }
             
            //To remove last comma ","
            oidString=oidString.substring(0,oidString.lastIndexOf(","));
            merchandisePrintableAreaTblPkString=merchandisePrintableAreaTblPkString.substring(0,merchandisePrintableAreaTblPkString.lastIndexOf(","));
            contentTypeString=contentTypeString.substring(0,contentTypeString.lastIndexOf(","));
            contentSizeString=contentSizeString.substring(0,contentSizeString.lastIndexOf(","));
            
            //Ending array bracket
            merchandisePrintableAreaTblPkString+="}";
            oidString+= "}";
            contentTypeString+="}";
            contentSizeString+="}";
            
            logger.debug("merchandisePrintableAreaTblPkString : " + merchandisePrintableAreaTblPkString);
            logger.debug("oidString : " + oidString);
            logger.debug("contentTypeString : " + contentTypeString);
            logger.debug("contentSizeString : " + contentSizeString);
            
            sqlString = "{?=call sp_ins_customer_design_detail_tbl(?,?,?,?,?,?,?,?)}";
            cs = dBConnection.prepareCall(sqlString);
            
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2,customerDesignTblPk);
            cs.setString(3,merchandisePrintableAreaTblPkString);
            cs.setString(4,oidString);
            cs.setString(5,contentTypeString);
            cs.setString(6,contentSizeString);
            dBConnection.executeCallableStatement(cs);
            //int returnInsertIntger=cs.getInt(1);
          }
        }
      }
    } catch (SQLException e) {
      logger.info(e);
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      logger.info(e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.freeCallableSatement(cs);
        dBConnection.freeResultSet(rs);
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting productCustomise");
    }
    return customerInfo;
  }

  /**
   * @param dataSource
   * @return
   * @throws SQLException
   * @throws Exception
   * @description
   */
  public static String  getDefaultMerchandiseTblPk(DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    ResultSet rs=null;
    String defaultMerchandiseTblPk="-1";
    try {
      logger.info("Entering getDefaultMerchandiseTblPk");
      String sqlString="select max(merchandise_tbl_pk) as merchandise_tbl_pk from merchandise_tbl";
      dBConnection = new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if(rs!=null){
        if(rs.next()){
          defaultMerchandiseTblPk=rs.getString("merchandise_tbl_pk");
        }
      }
    } catch (SQLException e) {
      logger.info(e);
      throw e;
    } catch (Exception e) {
      logger.info(e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.freeResultSet(rs);
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting getDefaultMerchandiseTblPk");
    }
    return defaultMerchandiseTblPk;
  }
  
  public static String  getDefaultMerchandiseColorTblPk(DataSource dataSource,String merchandiseTblPk) throws SQLException, Exception{
    DBConnection dBConnection = null;
    ResultSet rs=null;
    String defaultMerchandiseColorTblPk="-1";
    try {
      logger.info("Entering getDefaultMerchandiseColorTblPk");
      String sqlString="select ";
      sqlString+="\nmax(merchandise_color_tbl_pk) as merchandise_color_tbl_pk ";
      sqlString+="\nfrom merchandise_color_tbl ";
      sqlString+="\nwhere merchandise_tbl_pk=" + merchandiseTblPk;
      
      dBConnection = new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if(rs!=null){
        if(rs.next()){
          defaultMerchandiseColorTblPk=rs.getString("merchandise_color_tbl_pk");
        }
      }
    } catch (SQLException e) {
      logger.info(e);
      throw e;
    } catch (Exception e) {
      logger.info(e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.freeResultSet(rs);
        dBConnection.free();
      }
      dBConnection = null;
      logger.info("Exiting getDefaultMerchandiseColorTblPk");
    }
    return defaultMerchandiseColorTblPk;
  }
}
