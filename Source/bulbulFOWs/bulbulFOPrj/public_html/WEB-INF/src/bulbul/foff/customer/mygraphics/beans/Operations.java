package bulbul.foff.customer.mygraphics.beans;

import bulbul.foff.common.ImageFormat;
import bulbul.foff.customer.mygraphics.actionforms.MyGraphicsModifyForm;
import bulbul.foff.customer.mygraphics.actionforms.MyGraphicsUploadForm;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;
import bulbul.foff.general.LOOperations;

import java.io.IOException;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public final class Operations {
  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());

  public static String pageCountString = "1";

  public static ArrayList getGraphics(int customerEmailIdTblPk, DataSource dataSource, int numberOfRecords,
                                      String graphicsCategory, int pageNumber) throws SQLException, Exception {
    ArrayList graphics = new ArrayList();
    MyGraphicsListBean graphic = null;
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    SimpleDateFormat formatter = null;
    String uploadedOn = null;
    try {
      logger.info("Entering getGraphics() method");

      formatter = new SimpleDateFormat("dd MMM yyyy");
      query = "select * from customer_graphics_tbl where 1=1";
      if (graphicsCategory.equals(ImageFormat.JPG.toString())) {
        query += " and lower(graphics_category)=lower('jpg')";
      }
      if (graphicsCategory.equals(ImageFormat.GIF.toString())) {
        query += " and lower(graphics_category)=lower('gif')";
      }
      if (graphicsCategory.equals(ImageFormat.PNG.toString())) {
        query += " and lower(graphics_category)=lower('png')";
      }
      if (graphicsCategory.equals(ImageFormat.SVG.toString())) {
        query += " and lower(graphics_category)=lower('svg')";
      }
      query += " and customer_email_id_tbl_pk=" + customerEmailIdTblPk + " order by uploaded_on desc";
      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (rs != null) {
        pageCount = General.getPageCount(rs, numberOfRecords);
        if (pageNumber > pageCount) {
          pageNumber = pageCount;
        }
        startIndex = (pageNumber * numberOfRecords) - numberOfRecords;
        endIndex = (startIndex + numberOfRecords) - 1;
        if (startIndex > 0) {
          rs.relative(startIndex);
        }
        while (rs.next()) {
          uploadedOn = formatter.format(rs.getDate("uploaded_on"));
          graphic = new MyGraphicsListBean();
          graphic.setCustomerGraphicsTblPk(rs.getString("customer_graphics_tbl_pk"));
          graphic.setGraphicsTitle(rs.getString("graphics_title"));
          graphic.setGraphicsDescription(rs.getString("graphics_description"));
          graphic.setCreatedOn(uploadedOn);
          graphic.setGraphicsCategory(rs.getString("graphics_category"));
          graphic.setGraphicsOid(rs.getString("content_oid"));
          graphic.setGraphicsContentType(rs.getString("content_type"));
          graphic.setGraphicsContentSize(rs.getString("content_size"));
          graphics.add(graphic);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Exception Caught:" + e.getMessage());
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.debug("At Last pageNumber: " + pageNumber);
      pageCountString = Integer.toString(pageCount);
      logger.info("Exiting getGraphics");
    }
    return graphics;
  }

  public static void uploadGraphics(int customerEmailIdTblPk, MyGraphicsUploadForm myGraphicsUploadForm,
                                    DataSource dataSource) throws SQLException, IOException, Exception {
    FormFile[] frmFileArray = null;
    String[] fileTitleArray = null;
    String[] fileDescArray = null;
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

    try {
      logger.info("Entering uploadGraphics");

      dBConnection = new DBConnection(dataSource);

      frmFileArray = myGraphicsUploadForm.getFleGraphics();
      fileTitleArray = myGraphicsUploadForm.getTxtGraphicsTitle();
      fileDescArray = myGraphicsUploadForm.getTxaGraphicsDescription();

      logger.info("File length:" + frmFileArray.length);

      for (int fileCount = 0; fileCount < frmFileArray.length; fileCount++) {


        logger.info("File Name" + fileCount + " : " + frmFileArray[fileCount].getFileName());
        logger.info("File Title : " + fileTitleArray[fileCount]);
        logger.info("File Description : " + fileDescArray[fileCount]);

        graphicsCategory = getGraphicsCategory(frmFileArray[fileCount].getFileName());

        contentType = frmFileArray[fileCount].getContentType();
        contentSize = frmFileArray[fileCount].getFileData().length;
        logger.info("****contentType : " + contentType);
        oid = LOOperations.getLargeObjectId(dataSource);

        content = new byte[contentSize];
        is = frmFileArray[fileCount].getInputStream();
        is.read(content);

        LOOperations.putLargeObjectContent(oid, content, dataSource);
        sqlString = "{?=call sp_upload_customer_graphics(?,?,?,?,?,?,?)}";
        cs = dBConnection.prepareCall(sqlString);

        cs.registerOutParameter(1, Types.INTEGER);
        cs.setInt(2, customerEmailIdTblPk);
        cs.setString(3, fileTitleArray[fileCount].trim());
        cs.setString(4, fileDescArray[fileCount]);
        cs.setInt(5, oid);
        cs.setString(6, contentType);
        cs.setInt(7, contentSize);
        cs.setString(8, graphicsCategory);

        dBConnection.executeCallableStatement(cs);
        returnCustomerGraphicsTblPk = cs.getInt(1);
        is.close();
      }
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

  public static void modifyGraphics(MyGraphicsModifyForm myGraphicsModifyForm,
                                    DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnCustomerGraphicsTblPk;
    try {
      logger.info("Entering modifyGraphics() method");
      sqlString = "{?=call sp_upd_customer_graphics_tbl(?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.setInt(2, myGraphicsModifyForm.getHdnCutomerGraphicsTblPk());
      cs.setString(3, myGraphicsModifyForm.getTxtGraphicsTitle());
      cs.setString(4, myGraphicsModifyForm.getTxaGraphicsDescription());

      dBConnection.executeCallableStatement(cs);
      returnCustomerGraphicsTblPk = cs.getInt(1);
      logger.debug("updated customer_graphics_tbl_pk:" + returnCustomerGraphicsTblPk);

    } catch (SQLException e) {
      logger.error("SqlException: " + e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(cs);
      }
      dBConnection = null;
      logger.info("Exiting modifyGraphics() method");
    }
  }

  public static void deleteGraphics(int customerGraphicsTblPk,
                                    DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnCustomerGraphicsTblPk;
    try {
      logger.info("Entering deleteGraphics() method");
      sqlString = "{? = call sp_del_customer_graphics_tbl(?)}";
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);

      cs.registerOutParameter(1, Types.INTEGER);
      cs.setInt(2, customerGraphicsTblPk);

      dBConnection.executeCallableStatement(cs);
      returnCustomerGraphicsTblPk = cs.getInt(1);
      logger.debug("Deleted customer_graphics_tbl_pk : " + returnCustomerGraphicsTblPk);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteGraphics() method");
    }
  }

  public static MyGraphicsModifyForm getGraphicsData(int customerGraphicsTblPk,
                                                     DataSource dataSource) throws SQLException, Exception {
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String sqlString = null;
    MyGraphicsModifyForm myGraphicsModifyForm = null;
    try {
      logger.info("Entering getGraphicsData() method");

      sqlString =
        "select graphics_title,graphics_description,content_oid,content_type,content_size from customer_graphics_tbl where customer_graphics_tbl_pk=" +
        customerGraphicsTblPk;
      dBConnection = new DBConnection(dataSource);
      rs = dBConnection.executeQuery(sqlString);

      if (rs.next()) {
        myGraphicsModifyForm = new MyGraphicsModifyForm();
        myGraphicsModifyForm.setHdnCutomerGraphicsTblPk(customerGraphicsTblPk);
        myGraphicsModifyForm.setTxtGraphicsTitle(rs.getString("graphics_title"));
        myGraphicsModifyForm.setTxaGraphicsDescription(rs.getString("graphics_description"));
        myGraphicsModifyForm.setHdnContentOId(rs.getInt("content_oid"));
        myGraphicsModifyForm.setHdnContentType(rs.getString("content_type"));
        myGraphicsModifyForm.setHdnContentSize(rs.getInt("content_size"));
      } else {
        throw new Exception("customer_graphics_tbl_pk not found");
      }
    } catch (SQLException e) {
      logger.error(e);
      throw e;
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
      if (dBConnection != null) {
        dBConnection.free(rs);
      }
      dBConnection = null;
      logger.info("Exiting getGraphicsData() method");
    }
    return myGraphicsModifyForm;
  }
}
