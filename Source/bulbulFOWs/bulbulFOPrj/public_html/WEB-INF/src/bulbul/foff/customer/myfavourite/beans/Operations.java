package bulbul.foff.customer.myfavourite.beans;

import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;

import bulbul.foff.general.General;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public final class Operations  {
  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  static public String pageCountString="1"; 
  
 public static ArrayList getProducts(int customerEmailIdTblPk,String category,DataSource dataSource,int numberOfRecords,int pageNumber) throws SQLException,Exception{
    ArrayList products=null;
    MyFavouriteListBean product=null;
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;    
    
    int pageCount=1;
    int startIndex=1;
    int endIndex=1;
    try{
      logger.info("Entering getProducts() method");
      products=new ArrayList();
      
      
      /*
      query="SELECT * from dblink('dbname=bulbulbo','select merchandise_tbl_pk,";
      query+="merchandise_name,merchandise_description,merchandise_design_image,";
      query+="design_image_content_type,design_image_content_size ";
      query+="from bulbul.merchandise_tbl') as t1(merchandise_tbl_pk int,";
      query+="merchandise_name varchar,merchandise_description varchar,merchandise_design_image oid,";
      query+="design_image_content_type varchar,design_image_content_size numeric) where t1.merchandise_tbl_pk in ";
      query+="(select merchandise_tbl_pk from customer_favourite_tbl where 1=1 ";
      query+="and customer_email_id_tbl_pk=7 and category ";
      */
      
      /*      
      query="\nSELECT * from customer_favourite_tbl cft join dblink('dbname=bulbulbo','select merchandise_tbl_pk,";
      query+="\n merchandise_name,merchandise_description,merchandise_display_image,";
      query+="\n display_image_content_type,display_image_content_size ";
      query+="\n from bulbul.merchandise_tbl') as t1(merchandise_tbl_pk int,";
      query+="\n merchandise_name varchar,merchandise_description varchar,merchandise_display_image oid,";
      query+="\n display_image_content_type varchar,display_image_content_size numeric) ";
      query+="\n on (cft.merchandise_tbl_pk=t1.merchandise_tbl_pk) ";
      query+="\n where customer_email_id_tbl_pk=7 and category ";
      */
      
      //Query For Fav Merchandise 
      query="\n select  ";
      query+="\n cft.customer_favourite_tbl_pk, ";
      query+="\n cft.merchandise_tbl_pk, ";
      query+="\n cft.customer_email_id_tbl_pk, ";
      query+="\n t1.merchandise_name, ";
      query+="\n t1.merchandise_description, ";
      query+="\n t1.merchandise_image, ";
      query+="\n t1.content_type, ";
      query+="\n t1.content_size, ";
      query+="\n t1.noofcolors ";
      query+="\n from customer_favourite_tbl cft, ";
      query+="\n dblink('dbname=bulbulbo','select mtbl.merchandise_tbl_pk, ";
      query+="\n mtbl.merchandise_name,mtbl.merchandise_description,mtbl.merchandise_image, ";
      query+="\n mtbl.content_type,mtbl.content_size, ";
      query+="\n bulbul.sp_no_of_colors_of_merchandise(mtbl.merchandise_tbl_pk) as noofcolors ";
      query+="\n from bulbul.merchandise_tbl as mtbl') as t1(merchandise_tbl_pk int, ";
      query+="\n merchandise_name varchar,merchandise_description varchar,merchandise_image oid, ";
      query+="\n content_type varchar,content_size numeric, noofcolors int ) ";
      query+="\n where cft.merchandise_tbl_pk=t1.merchandise_tbl_pk  ";
      query+="\n and customer_email_id_tbl_pk="+customerEmailIdTblPk;
      query+="\n and category ";
      if (category.equals("-1")){
        query=query+"is null ";
      }else{
        query=query+"='"+category+"'" ;
      }
      query=query + " order by t1.merchandise_name ";
      
      logger.debug("query: "+query);

      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  

      if (rs!=null){
        pageCount=General.getPageCount(rs,numberOfRecords);
        if (pageNumber>pageCount) {
          pageNumber=pageCount;
        }
        startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
        endIndex=(startIndex + numberOfRecords)-1;
        if (startIndex>0){
          rs.relative(startIndex);
        }
        while(rs.next()){
          product=new MyFavouriteListBean();
          product.setCustomerFavouriteTblPk(rs.getString("customer_favourite_tbl_pk"));
          product.setMorcTblPk(rs.getString("merchandise_tbl_pk"));
          product.setProductName(rs.getString("merchandise_name"));
          product.setNoOfColors(rs.getString("noofcolors"));
          product.setProductDescription(rs.getString("merchandise_description"));
          product.setProductOId(rs.getString("merchandise_image"));
          product.setProductContentType(rs.getString("content_type"));
          product.setProductContentSize(rs.getString("content_size"));
          products.add(product);
          startIndex++;
          logger.debug("startIndex: "+startIndex);
          if (startIndex>endIndex){
            break;
          }
        }    
      }
    }catch(SQLException e){
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }finally{      
      if(dbConnection!=null){
        dbConnection.free(rs);
        dbConnection.free();
      }
      dbConnection=null;
      logger.debug("At Last pageNumber: "+pageNumber);
      pageCountString=Integer.toString(pageCount);
      logger.info("Exiting getProducts() method");
    }
    return products;
  }  
  public static void deleteMyFavourite(int customerFavouriteTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnCustomerFavouriteTblPk;
    try{
      logger.info("Entering deleteMyFavourite() method");
      sqlString = "{? = call sp_del_customer_favourite_tbl(?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
     
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,customerFavouriteTblPk); 

      dBConnection.executeCallableStatement(cs);
      returnCustomerFavouriteTblPk=cs.getInt(1);
      logger.debug("Deleted customer_favourite_tbl_pk : "+returnCustomerFavouriteTblPk);
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      logger.info("Exiting deleteMyFavourite() method");
    }
  }
  
  public static ArrayList getCategories(int customerEmailIdTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    ArrayList categories=null;
    
    try{
      logger.info("Entering getCategories() method");
      
      sqlString="SELECT category from customer_favourite_tbl where customer_email_id_tbl_pk="+customerEmailIdTblPk+" and trim(category)!='' group by category";
      
      dBConnection=new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      categories=new ArrayList();
      while(rs.next()){
        categories.add(rs.getString("category"));
      }
      logger.debug("No of categories: "+categories.size());
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getCategories() method");
    }
    return categories;
  }
}