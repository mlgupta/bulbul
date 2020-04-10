package bulbul.foff.pcatalogue.beans;

import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;

import bulbul.foff.general.General;
import bulbul.foff.pcatalogue.actionforms.AddToFavouriteForm;
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
  
  public static ArrayList getProducts(int merchandiseCategoryTblPk,DataSource dataSource,int numberOfRecords, String morcFlag, int pageNumber){
    ArrayList products=null;
    ProductListBean product=null;
    DBConnection dbConnection=null;
    ResultSet rsCategory=null;
    ResultSet rsMerchandise=null;
    String queryForCategory;
    String queryForMerchandise;
   
    int startIndex=1;
    int endIndex=1;
    int pageCount=1;
    
    try{
      products=new ArrayList();
      dbConnection=new DBConnection(dataSource);
      
      if (morcFlag.equals(FOConstants.CATEGORY_ONLY_VAL.toString())){
      
        //Query For Merchandise Category
        queryForCategory="\n select";
        queryForCategory+=" \n merchandise_category_tbl_pk, ";
        queryForCategory+=" \n merchandise_category, ";
        queryForCategory+=" \n merchandise_category_description, ";
        queryForCategory+=" \n m_or_c, ";
        queryForCategory+=" \n merchandise_category_image, ";
        queryForCategory+=" \n content_type, ";
        queryForCategory+=" \n content_size, ";
        queryForCategory+=" \n sp_no_of_items_m_or_c(merchandise_category_tbl_pk,m_or_c) as no_of_itmes ";
        queryForCategory+=" \n from merchandise_category_tbl mctbl ";
        queryForCategory+=" \n where 1=1 ";
        queryForCategory+=" \n and sp_no_of_items_m_or_c(merchandise_category_tbl_pk,m_or_c)>0 ";
        queryForCategory+=" \n and is_active=1 ";
        if (merchandiseCategoryTblPk==-1){
          queryForCategory+=" \n and merchandise_category_tbl_fk is null ";
        }else{
          queryForCategory+=" \n and merchandise_category_tbl_fk="+merchandiseCategoryTblPk;
        }
        queryForCategory+=" \n order by merchandise_category ";
        
        rsCategory=dbConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
  
        logger.debug("queryForCategory: "+queryForCategory);
  
        //Filling Category Records
        if (rsCategory!=null){        
          pageCount=General.getPageCount(rsCategory,numberOfRecords);
          if (pageNumber>pageCount) {
            pageNumber=pageCount;
          }
          startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
          endIndex=(startIndex + numberOfRecords)-1;
          if (startIndex>0){
            rsCategory.relative(startIndex);
          }
         
          while(rsCategory.next()){
            product=new ProductListBean();
            product.setMorcTblPk(rsCategory.getString("merchandise_category_tbl_pk"));
            product.setProductName(rsCategory.getString("merchandise_category"));
            product.setNoOfItemsOrColors(Integer.toString(rsCategory.getInt("no_of_itmes")));
            product.setProductDescription(rsCategory.getString("merchandise_category_description"));
            product.setMorcFlag(rsCategory.getString("m_or_c"));
            product.setProductOId(rsCategory.getString("merchandise_category_image"));
            product.setProductContentType(rsCategory.getString("content_type"));
            product.setProductContentSize(rsCategory.getString("content_size"));
            product.setProductOrCategoryFlag(FOConstants.CATEGORY_VAL.toString());
            products.add(product);
            startIndex++;
            logger.debug("startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }
        }
        
      }else{
      
        //Query For Merchandise
        queryForMerchandise="\n select ";
        queryForMerchandise+="\n mtbl.merchandise_tbl_pk, ";
        queryForMerchandise+="\n mtbl.merchandise_name, ";
        queryForMerchandise+="\n mtbl.merchandise_description, ";
        queryForMerchandise+="\n mtbl.merchandise_image, ";
        queryForMerchandise+="\n mtbl.content_type, ";
        queryForMerchandise+="\n mtbl.content_size, ";
        queryForMerchandise+="\n sp_no_of_colors_of_merchandise(mtbl.merchandise_tbl_pk) as no_of_colors ";
        queryForMerchandise+="\n from merchandise_tbl mtbl, ";
        queryForMerchandise+="\n merchandise_parents_tbl mptbl ";
        queryForMerchandise+="\n where mtbl.merchandise_tbl_pk=mptbl.merchandise_tbl_pk ";
        queryForMerchandise+="\n and mptbl.is_active=1 ";
        queryForMerchandise+="\n and sp_no_of_colors_of_merchandise(mtbl.merchandise_tbl_pk)>0 ";
        queryForMerchandise+="\n and mptbl.merchandise_category_tbl_pk= "+merchandiseCategoryTblPk;
        queryForMerchandise+="\n order by mtbl.merchandise_name; ";
        
        rsMerchandise=dbConnection.executeQuery(queryForMerchandise,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
        
        logger.debug("queryForMerchandise: "+queryForMerchandise);
        
        //Filling Merchandise Records
        if(rsMerchandise !=null){
          pageCount=General.getPageCount(rsMerchandise,numberOfRecords);
          if (pageNumber>pageCount) {
            pageNumber=pageCount;
          }
          startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
          endIndex=(startIndex + numberOfRecords)-1;
          if (startIndex>0){
            rsMerchandise.relative(startIndex);
          }
          while(rsMerchandise.next()){
            product=new ProductListBean();
            product.setMorcTblPk(rsMerchandise.getString("merchandise_tbl_pk"));
            product.setProductName(rsMerchandise.getString("merchandise_name"));
            product.setProductDescription(rsMerchandise.getString("merchandise_description"));
            product.setNoOfItemsOrColors(Integer.toString(rsMerchandise.getInt("no_of_colors")));
            product.setProductOId(rsMerchandise.getString("merchandise_image"));
            product.setProductContentType(rsMerchandise.getString("content_type"));
            product.setProductContentSize(rsMerchandise.getString("content_size"));
            product.setProductOrCategoryFlag(FOConstants.PRODUCT_VAL.toString());
            products.add(product);
            startIndex++;
            logger.debug("startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }    
        }
      }    
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
    }finally{
      if(dbConnection!=null){
        dbConnection.free(rsCategory);
        dbConnection.free(rsMerchandise);
        dbConnection.free();
      }
      dbConnection=null;
      logger.debug("At Last pageNumber: "+pageNumber);
      logger.debug("pageCount: "+pageCount);
      pageCountString=Integer.toString(pageCount);
    }
    return products;
  }
  
  /*
  public static String getMOrCFlag(String merchandiseCategoryTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString;
    String productOrCategoryFlag=null;
    Statement stmt=null;
    
    try{
      logger.info("Entering getMOrCFlag() method");
      if(merchandiseCategoryTblPk.equals("-1")){
        return "1";
      }
      dBConnection=new DBConnection(dataSource);
      sqlString="select m_or_c from merchandise_category_tbl where merchandise_category_tbl_pk="+merchandiseCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
        productOrCategoryFlag=rs.getString("m_or_c");
      }
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
      logger.info("Exiting getMOrCFlag() method");
    }
    return productOrCategoryFlag;
  }
  */
  
  public static ProductBean getProductDetails(String merchandiseTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    ResultSet rsColor=null;
    ResultSet rsSize=null;
    String sqlString;
    String queryForSize=null;
    ArrayList productColors=null;
    ArrayList productSizes=null;
    ProductBean productDetail=null;
    try{
      logger.info("Entering getProductDetails() method");
      
      
      
      dBConnection=new DBConnection(dataSource);
      //DBConnection dBConnectionForColor=new DBConnection(dataSource);
      
      /*SELECT mt.merchandise_tbl_pk,mct.merchandise_color_tbl_pk,mst.merchandise_size_tbl_pk,st.size_id,stt.size_type_id 
        from ((((merchandise_tbl mt join merchandise_color_tbl mct
              on (mt.merchandise_tbl_pk=mct.merchandise_tbl_pk)) 
        join merchandise_size_tbl mst on (mct.merchandise_color_tbl_pk=mst.merchandise_color_tbl_pk))
        join size_tbl st on (mst.size_tbl_pk=st.size_tbl_pk))
        join size_type_tbl stt on (st.size_type_tbl_pk=stt.size_type_tbl_pk))where mt.merchandise_tbl_pk=109;*/
        
      sqlString="SELECT * from merchandise_tbl where merchandise_tbl_pk="+merchandiseTblPk;
      rs=dBConnection.executeQuery(sqlString);
      
      if(rs.next()){
        productDetail=new ProductBean();
        productDetail.setMerchandiseTblPk(rs.getString("merchandise_tbl_Pk"));
        productDetail.setProductOId(rs.getString("merchandise_image"));
        productDetail.setProductContentType(rs.getString("content_type"));
        productDetail.setProductContentSize(rs.getString("content_size"));
        productDetail.setName(rs.getString("merchandise_name"));
        productDetail.setDescription(rs.getString("merchandise_description"));
        productDetail.setMaterialDetail(rs.getString("material_description"));
        productDetail.setDeliveryNote(rs.getString("delivery_note"));
        productDetail.setMinQty(rs.getString("threshold_quantity"));
        productDetail.setComment(rs.getString("merchandise_comment"));
        
        String queryForColor="Select * from merchandise_color_tbl where is_active=1 and merchandise_tbl_pk="+merchandiseTblPk;
        rsColor=dBConnection.executeQuery(queryForColor);
        productColors=new ArrayList();
        while(rsColor.next()){
          ProductColorBean productColor=new ProductColorBean();
          productColor.setMerchandiseColorTblPk(rsColor.getString("merchandise_color_tbl_pk"));
          productColor.setColor1Name(rsColor.getString("color_one_name"));
          productColor.setColor2Name(rsColor.getString("color_two_name"));
          productColor.setColor1Value(rsColor.getString("color_one_value"));
          productColor.setColor2Value(rsColor.getString("color_two_value"));
          
          logger.debug("Color One Name : "+ rsColor.getString("color_one_name"));

/*          
          queryForSize="SELECT *  from (( ";
          queryForSize+="merchandise_size_tbl mst join size_tbl st ";
          queryForSize+="on (mst.size_tbl_pk=st.size_tbl_pk))	join size_type_tbl stt ";
          queryForSize+="on (st.size_type_tbl_pk=stt.size_type_tbl_pk)) ";
          queryForSize+="where mst.merchandise_color_tbl_pk="+rsColor.getString("merchandise_color_tbl_pk");
 */       
          queryForSize="select "; 
          queryForSize+="mst.merchandise_size_tbl_pk, ";
          queryForSize+="st.size_id, ";
          queryForSize+="stt.size_type_id, "; 
          queryForSize+="mst.merchandise_price_per_qty "; 
          queryForSize+="from "; 
          queryForSize+="merchandise_size_tbl mst, ";
          queryForSize+="size_tbl st, ";
          queryForSize+="size_type_tbl stt ";
          queryForSize+="where mst.is_active=1 ";
          queryForSize+="and mst.size_tbl_pk=st.size_tbl_pk ";
          queryForSize+="and st.size_type_tbl_pk=stt.size_type_tbl_pk ";
          queryForSize+="and merchandise_color_tbl_pk = " +rsColor.getString("merchandise_color_tbl_pk");
         
          rsSize=dBConnection.executeQuery(queryForSize);
          productSizes=new ArrayList();
          while(rsSize.next()){
            ProductSizeBean productSize= new ProductSizeBean();
            
            productSize.setMerchandiseSizeTblPk(rsSize.getString("merchandise_size_tbl_pk"));
            productSize.setSizeTypeId(rsSize.getString("size_type_id"));
            productSize.setSizeId(rsSize.getString("size_id"));
            productSize.setPrice(rsSize.getString("merchandise_price_per_qty"));
            logger.debug("\t\t Size : "+ rsSize.getString("size_type_id")+ "-" + rsSize.getString("size_id"));
            
            productSizes.add(productSize);
          }
          productColor.setSizes(productSizes);
          
          productColors.add(productColor);
          
        }
        productDetail.setColors(productColors);
      }
      
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.freeResultSet(rs);
        dBConnection.freeResultSet(rsColor);
        dBConnection.freeResultSet(rsSize);
        dBConnection.free();
      }
      dBConnection=null;
      logger.info("Exiting getProductDetails() method");
    }
    return productDetail;
  }
  
  public static void addFavourite(AddToFavouriteForm addToFavouriteForm,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    String sqlString=null;
    int returnCustomerFavouriteTblPk;
    CallableStatement cs=null;
    
    try{
      logger.info("Entering addFavourite() method");
      
      sqlString="{?=call sp_ins_customer_favourite_tbl(?,?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,addToFavouriteForm.getHdnCustomerEmailIdTblPk());
      cs.setInt(3,addToFavouriteForm.getHdnMerchandiseTblPk());
      if(addToFavouriteForm.getCboCategory().trim().length()!=0 && !addToFavouriteForm.getCboCategory().equals("0")){
        cs.setString(4,addToFavouriteForm.getCboCategory());  
      }else if(addToFavouriteForm.getTxtCategory().trim().length()!=0){
        cs.setString(4,addToFavouriteForm.getTxtCategory());
      }else{
        cs.setString(4,"");
      }
      
      dBConnection.executeCallableStatement(cs);
      
      returnCustomerFavouriteTblPk=cs.getInt(1);
      logger.debug("added customer_favourite_tbl_pk:"+returnCustomerFavouriteTblPk);

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
      logger.info("Exiting addFavourite() method");
    }
  }
  
  public static ArrayList getCategories(AddToFavouriteForm addToFavouriteForm,DataSource dataSource) throws SQLException,Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    ArrayList categories=null;
    
    try{
      logger.info("Entering getCategories() method");
      
      sqlString="SELECT category from customer_favourite_tbl where customer_email_id_tbl_pk="+addToFavouriteForm.getHdnCustomerEmailIdTblPk()+" and trim(category)!='' group by category";
      
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

  public static ArrayList getCategoryNavigationList(int merchandiseCategoryTblPk,DataSource dataSource )throws SQLException,Exception{
    ArrayList categories=null;
    ArrayList tempCategories=null;
    DBConnection dBConnection=null;
    String sqlString=null;
    CallableStatement cs=null;
    String categoryNavList=null;
    String[]  categoryArray=null;
    int intMaxCharsLength=80;
    int intCurrentCharsLength=0;
    try{
      logger.info("Entering getCategoryNavigationList method");
      dBConnection=new DBConnection(dataSource);
      sqlString="{?=call sp_get_m_cat_nav_list(?)}";
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.VARCHAR);
      cs.setInt(2,merchandiseCategoryTblPk);
      dBConnection.executeCallableStatement(cs);
      categoryNavList=cs.getString(1);
      categoryArray=categoryNavList.split("~"); //Splits the String into Group of PK and Category
      categories = new ArrayList();
      tempCategories = new ArrayList();
      for (int catCount=categoryArray.length-1;catCount>=0;catCount--){
        intCurrentCharsLength+=(categoryArray[catCount].split(","))[1].length();
        if (intCurrentCharsLength<=intMaxCharsLength){
          tempCategories.add(categoryArray[catCount].split(","));
        }
        logger.debug(" Categories : " + catCount + " - " + categoryArray[catCount]);
        logger.debug(" Char Length : " + intCurrentCharsLength);
      }
      for (int catCount=tempCategories.size()-1;catCount>=0;catCount--){
        categories.add(tempCategories.get(catCount));
      }
      
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
      logger.info("Exiting getCategoryNavigationList method");
    }
    return categories;
  }
}
