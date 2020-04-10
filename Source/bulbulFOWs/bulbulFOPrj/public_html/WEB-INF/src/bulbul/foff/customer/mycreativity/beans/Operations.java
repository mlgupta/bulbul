package bulbul.foff.customer.mycreativity.beans;

import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;
import bulbul.foff.general.General;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public final class Operations {
  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());

  public static String pageCountString = "1";

  public static ArrayList getDesigns(int customerEmailIdTblPk, DataSource dataSource, int numberOfRecords,
                                     int pageNumber) throws SQLException, Exception {
    ArrayList desings = new ArrayList();
    MyCreativityListBean design = null;
    DBConnection dBConnection = null;
    ResultSet rs = null;
    String query = null;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    SimpleDateFormat formatter = null;
    String uploadedOn = null;
    try {
      logger.info("Entering getDesigns() method");

      formatter = new SimpleDateFormat("dd MMM yyyy");
      query = "\n select ";
      query += "\n cdt.customer_design_tbl_pk, ";
      query += "\n cdt.customer_design_name, ";
      query += "\n cdt.design_4_char_code, ";
      query += "\n cdt.customer_design, ";
      query += "\n cdt.design_content_type, ";
      query += "\n cdt.design_content_size, ";
      query += "\n cdt.created_on, ";
      query += "\n ptbl.merchandise_name ";
      query += "\n from ";
      query += "\n customer_design_tbl cdt, ";
      query += "\n dblink( 'dbname=bulbulbo', ";
      query +=
        "\n 'select mtbl.merchandise_name, mct.merchandise_color_tbl_pk from bulbul.merchandise_color_tbl mct, ";
      query += "\n bulbul.merchandise_tbl mtbl where mct.merchandise_tbl_pk=mtbl.merchandise_tbl_pk')  ";
      query += "\n as ptbl (merchandise_name varchar,merchandise_color_tbl_pk int) ";
      query += "\n where 1=1  ";
      query += "\n and ptbl.merchandise_color_tbl_pk=cdt.merchandise_color_tbl_pk  ";
      query += "\n and cdt.customer_email_id_tbl_pk=" + customerEmailIdTblPk;
      query += "\n order by cdt.created_on desc ";

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
          uploadedOn = formatter.format(rs.getDate("created_on"));
          design = new MyCreativityListBean();
          design.setCustomerDesignTblPk(rs.getString("customer_design_tbl_pk"));
          design.setDesignName(rs.getString("customer_design_name"));
          design.setDesignCode(rs.getString("design_4_char_code"));
          design.setCreatedOn(uploadedOn);
          design.setProductName(rs.getString("merchandise_name"));
          design.setDesignOId(rs.getString("customer_design"));
          design.setDesignContentType(rs.getString("design_content_type"));
          design.setDesignContentSize(rs.getString("design_content_size"));
          desings.add(design);
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
      logger.info("Exiting getDesigns");
    }
    return desings;
  }

  public static void deleteCustomerDesign(String customerDesignTblPk,
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
}
