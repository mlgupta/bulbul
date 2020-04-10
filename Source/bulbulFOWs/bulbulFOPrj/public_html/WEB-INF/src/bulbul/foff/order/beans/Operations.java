package bulbul.foff.order.beans;

import bulbul.foff.customer.myaddress.actionforms.ShippingAddressForm;
import bulbul.foff.customer.myaddress.beans.ShippingAddressBean;
import bulbul.foff.general.DBConnection;
import bulbul.foff.general.FOConstants;

import bulbul.foff.general.OrderStatus;
import bulbul.foff.general.PgSQLArray;
import bulbul.foff.order.actionforms.MultipleAddressForm;
import bulbul.foff.order.actionforms.OrderItemForm;
import bulbul.foff.order.beans.PreOrderBean;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public final class Operations{
  static Logger logger = Logger.getLogger(FOConstants.LOGGER.toString());
  
  public static OrderHeaderBean listOrder(int orderHeaderTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rsForHeader=null;
    ResultSet rsForDetail=null;
    ResultSet rsForItemDetail=null;
    String queryForHeader=null;
    String queryForDetail=null;
    String queryForItemDetail=null;
    OrderHeaderBean orderHeaderBean=null;
    OrderDetailBean orderDetailBean=null;
    OrderItemDetailBean orderItemDetailBean=null; 
    Format formatter=null;
    ArrayList orderDetailArrayList=null;
    ArrayList orderItemDetailArrayList=null;
    DecimalFormat discountFormatter=null;
    DecimalFormat priceFormatter=null;
    int quantity=0;
    float discountedPricePerQty=0;
    float pricePerQty=0;
    float lessPerQty=0;
    float discount=0;
    float less=0;
    float amount=0;
    float total=0;

    try{
      logger.info("Entering method listOrder()");
      
      priceFormatter=new DecimalFormat("Rs ##,##,##0.00");
      discountFormatter=new DecimalFormat("-#0.00");
      
      dbConnection = new DBConnection(dataSource);
   
      queryForHeader="\n select ";
      queryForHeader+="\n oht.order_header_tbl_pk, ";
      queryForHeader+="\n oht.order_code, ";
      queryForHeader+="\n oht.order_gen_date, ";
      queryForHeader+="\n round(sum(sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) * oit.quantity),0)as amount ";
      queryForHeader+="\n from ";
      queryForHeader+="\n order_header_tbl oht, ";
      queryForHeader+="\n order_detail_tbl odt, ";
      queryForHeader+="\n order_item_detail_tbl oit, ";
      queryForHeader+="\n merchandise_size_tbl mst, ";
      queryForHeader+="\n dblink('dbname=bulbulfo','select customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
      queryForHeader+="\n as cdt ( customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer) ";
      queryForHeader+="\n where ";
      queryForHeader+="\n oht.order_header_tbl_pk=odt.order_header_tbl_pk ";
      queryForHeader+="\n and odt.order_detail_tbl_pk=oit.order_detail_tbl_pk ";
      queryForHeader+="\n and mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
      queryForHeader+="\n and cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
      queryForHeader+="\n and oht.order_header_tbl_pk= " + orderHeaderTblPk;
      queryForHeader+="\n group by  oht.order_header_tbl_pk, ";
      queryForHeader+="\n oht.order_code, ";
      queryForHeader+="\n oht.order_gen_date ";
 
      logger.debug(" queryForHeader : " + queryForHeader);
      formatter=new SimpleDateFormat("dd-MMM-yyyy");
      orderHeaderBean=new OrderHeaderBean();      
    
      rsForHeader=dbConnection.executeQuery(queryForHeader);
      if (rsForHeader.next()){
        logger.debug("Fill Order Header Here");
        orderHeaderBean.setOrderHeaderTblPk(rsForHeader.getInt("order_header_tbl_pk"));
        orderHeaderBean.setOrderCode(rsForHeader.getString("order_code"));
        orderHeaderBean.setOrderGenDate(formatter.format(rsForHeader.getDate("order_gen_date")));
        orderHeaderBean.setOrderAmount(priceFormatter.format(rsForHeader.getFloat("amount")));
        
    
        queryForDetail="\n select ";
        queryForDetail+="\n odt.order_header_tbl_pk, ";
        queryForDetail+="\n odt.order_detail_tbl_pk, ";
        queryForDetail+="\n sum(sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) * oit.quantity) as amount, ";        
        queryForDetail+="\n cdt.customer_design_name, ";
        queryForDetail+="\n mtbl.merchandise_name, ";
        queryForDetail+="\n mtbl.merchandise_description, ";
        queryForDetail+="\n mct.color_one_name, ";
        queryForDetail+="\n mct.color_two_name, ";
        queryForDetail+="\n mct.color_one_value, ";
        queryForDetail+="\n mct.color_two_value ";
        queryForDetail+="\n from ";
        queryForDetail+="\n order_detail_tbl odt, ";
        queryForDetail+="\n merchandise_tbl mtbl, ";
        queryForDetail+="\n merchandise_color_tbl mct, ";
        queryForDetail+="\n order_item_detail_tbl oit, ";
        queryForDetail+="\n merchandise_size_tbl mst, ";
        queryForDetail+="\n dblink('dbname=bulbulfo','select customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
        queryForDetail+="\n as cdt ( customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer) ";
        queryForDetail+="\n where ";
        queryForDetail+="\n cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
        queryForDetail+="\n and odt.order_detail_tbl_pk=oit.order_detail_tbl_pk ";
        queryForDetail+="\n and mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
        queryForDetail+="\n and cdt.merchandise_color_tbl_pk=mct.merchandise_color_tbl_pk ";
        queryForDetail+="\n and mct.merchandise_tbl_pk=mtbl.merchandise_tbl_pk ";
        queryForDetail+="\n and odt.order_header_tbl_pk=" + rsForHeader.getString("order_header_tbl_pk");
        queryForDetail+="\n group by ";
        queryForDetail+="\n odt.order_header_tbl_pk, ";
        queryForDetail+="\n odt.order_detail_tbl_pk, ";
        queryForDetail+="\n cdt.customer_design_name, ";
        queryForDetail+="\n mtbl.merchandise_name, ";
        queryForDetail+="\n mtbl.merchandise_description, ";
        queryForDetail+="\n mct.color_one_name, ";
        queryForDetail+="\n mct.color_two_name, ";
        queryForDetail+="\n mct.color_one_value, ";
        queryForDetail+="\n mct.color_two_value ";


        logger.debug(" queryForDetail : " + queryForDetail);
      
        orderDetailArrayList=new ArrayList();
        rsForDetail=dbConnection.executeQuery(queryForDetail);
        while(rsForDetail.next()){
          logger.debug("Fill Order Detail Here");
          orderDetailBean=new OrderDetailBean();
          orderDetailBean.setOrderDetailTblPk(rsForDetail.getString("order_detail_tbl_pk"));
          orderDetailBean.setOrderDetailTotal(priceFormatter.format(rsForDetail.getFloat("amount")));
          orderDetailBean.setColor1Name(rsForDetail.getString("color_one_name"));
          orderDetailBean.setColor2Name(rsForDetail.getString("color_two_name"));
          orderDetailBean.setColor1Value(rsForDetail.getString("color_one_value"));
          orderDetailBean.setColor2Value(rsForDetail.getString("color_two_value"));
          orderDetailBean.setDesignName(rsForDetail.getString("customer_design_name"));
          orderDetailBean.setMerchandiseDesc(rsForDetail.getString("merchandise_description"));
          orderDetailBean.setMerchandiseName(rsForDetail.getString("merchandise_name"));
          orderDetailBean.setOrderDetailTblPk(rsForDetail.getString("order_detail_tbl_pk"));
            
          queryForItemDetail="\n select ";
          queryForItemDetail+="\n oit.order_detail_tbl_pk, ";
          queryForItemDetail+="\n oit.order_item_detail_tbl_pk, ";
          queryForItemDetail+="\n stt.size_type_id, ";
          queryForItemDetail+="\n stbl.size_id, ";
          queryForItemDetail+="\n oit.quantity, ";
          queryForItemDetail+="\n mst.merchandise_price_per_qty,";
          queryForItemDetail+="\n sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) as discounted_price_per_qty ";
          queryForItemDetail+="\n from ";
          queryForItemDetail+="\n order_item_detail_tbl oit, ";
          queryForItemDetail+="\n merchandise_size_tbl mst, ";
          queryForItemDetail+="\n size_tbl stbl, ";
          queryForItemDetail+="\n size_type_tbl stt ";
          queryForItemDetail+="\n where ";
          queryForItemDetail+="\n mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
          queryForItemDetail+="\n and mst.size_tbl_pk=stbl.size_tbl_pk ";
          queryForItemDetail+="\n and stbl.size_type_tbl_pk=stt.size_type_tbl_pk ";
          queryForItemDetail+="\n and oit.order_detail_tbl_pk= " + rsForDetail.getString("order_detail_tbl_pk");
          
          logger.debug(" queryForItemDetail : " + queryForItemDetail);
          
          rsForItemDetail=dbConnection.executeQuery(queryForItemDetail);
          orderItemDetailArrayList=new ArrayList();                    
          while(rsForItemDetail.next()){
            logger.debug("Fill Order Item Detail Here");        
            orderItemDetailBean = new OrderItemDetailBean();
            orderItemDetailBean.setOrderItemDetailTblPk(rsForItemDetail.getString("order_item_detail_tbl_pk"));
            orderItemDetailBean.setOrderDetailTblPk(rsForItemDetail.getString("order_detail_tbl_pk"));
            orderItemDetailBean.setSizeId(rsForItemDetail.getString("size_id"));
            orderItemDetailBean.setSizeTypeId(rsForItemDetail.getString("size_type_id"));
            orderItemDetailBean.setQuantity(rsForItemDetail.getString("quantity"));
            
            
            /*Calculation for Discount Price Starts*/
            quantity=rsForItemDetail.getInt("quantity");
            discountedPricePerQty=rsForItemDetail.getFloat("discounted_price_per_qty");
            pricePerQty=rsForItemDetail.getFloat("merchandise_price_per_qty");
            lessPerQty=(pricePerQty-discountedPricePerQty);
            discount=(lessPerQty*100)/pricePerQty;
            less=(lessPerQty*quantity);
            amount=(pricePerQty*quantity);
            total=(amount-less);
            
            
            /*Calculation for Discount Price Ends*/
            
            orderItemDetailBean.setDiscount(discountFormatter.format(discount));
            orderItemDetailBean.setPrice(priceFormatter.format(pricePerQty));
            orderItemDetailBean.setLess(priceFormatter.format(less));
            orderItemDetailBean.setAmount(priceFormatter.format(amount));
            orderItemDetailBean.setTotal(priceFormatter.format(total));
            
            orderItemDetailArrayList.add(orderItemDetailBean);
          }
          orderDetailArrayList.add(orderDetailBean);
          orderDetailBean.setOrderItemDetail(orderItemDetailArrayList);        
          dbConnection.freeResultSet(rsForItemDetail);
        }
        orderHeaderBean.setOrderDetail(orderDetailArrayList);
      }else{
        orderHeaderBean.setOrderHeaderTblPk(-1);
        orderHeaderBean.setOrderCode("-");
        orderHeaderBean.setOrderGenDate("-");
        orderHeaderBean.setOrderAmount("-");
      }
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.freeResultSet(rsForHeader);
        dbConnection.freeResultSet(rsForDetail);
        dbConnection.freeResultSet(rsForItemDetail);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting method listOrder()");
    }
    return orderHeaderBean;
  }

  public static PreOrderBean createOrderB4(String customerDesignTblPk,DataSource dataSourceFO,DataSource dataSourceBO) throws SQLException,Exception{
    DBConnection dbConnectionForFO=null;
    DBConnection dbConnectionForBO=null;
    ResultSet rsForFO=null;
    ResultSet rsForBO=null;
    String queryForFO=null;
    String queryForBO=null;
    PreOrderBean preOrderBean=null;
    PreOrderSizeBean preOrderSizeBean=null;
    ArrayList sizeArrayList=null;
    
    try{
      logger.info("Entering createOrderB4() method");
      
      sizeArrayList=new ArrayList();
      
//      queryForFO="\n SELECT cdt.customer_design_tbl_pk,cdt.customer_email_id_tbl_pk,cdt.customer_design_name,";
//      queryForFO+="\n cdt.customer_design,cdt.design_content_type,cdt.design_content_size,";
//      queryForFO+="\n mct.merchandise_color_tbl_pk,mct.color_one_value,mct.color_two_value,";
//      queryForFO+="\n mct.color_one_name,mct.color_two_name,";
//      queryForFO+="\n merchandise_name,merchandise_description,threshold_quantity ";
//      queryForFO+="\n from customer_design_tbl cdt,";
//      queryForFO+="\n dblink('dbname=bulbulbo','select merchandise_color_tbl_pk,";
//      queryForFO+="\n color_one_value, color_two_value,color_one_name,color_two_name,mt.merchandise_name,";
//      queryForFO+="\n mt.merchandise_description,mt.threshold_quantity from ";
//      queryForFO+="\n bulbul.merchandise_color_tbl mct join bulbul.merchandise_tbl mt ";
//      queryForFO+="\n on (mct.merchandise_tbl_pk=mt.merchandise_tbl_pk)') as mct ";
//      queryForFO+="\n (merchandise_color_tbl_pk integer,color_one_value char,color_two_value ";
//      queryForFO+="\n char,color_one_name varchar,color_two_name varchar,merchandise_name varchar,merchandise_description varchar,";
//      queryForFO+="\n threshold_quantity integer) where cdt.customer_design_tbl_pk="+customerDesignTblPk+" and ";
//      queryForFO+="\n mct.merchandise_color_tbl_pk=(select merchandise_color_tbl_pk from ";
//      queryForFO+="\n customer_design_tbl where customer_design_tbl_pk="+customerDesignTblPk+")";
      
      queryForFO="\n SELECT ";
      queryForFO+="\n cdt.customer_design_tbl_pk, ";
      queryForFO+="\n cdt.customer_email_id_tbl_pk, ";
      queryForFO+="\n cdt.customer_design_name, ";
      queryForFO+="\n mct.merchandise_image, ";
      queryForFO+="\n mct.content_type, ";
      queryForFO+="\n mct.content_size, ";
      queryForFO+="\n mct.merchandise_color_tbl_pk, ";
      queryForFO+="\n mct.color_one_value, ";
      queryForFO+="\n mct.color_two_value, ";
      queryForFO+="\n mct.color_one_name,mct.color_two_name, ";
      queryForFO+="\n mct.merchandise_name, ";
      queryForFO+="\n mct.merchandise_description, ";
      queryForFO+="\n mct.threshold_quantity ";
      queryForFO+="\n from customer_design_tbl cdt, ";
      queryForFO+="\n dblink('dbname=bulbulbo','select ";
      queryForFO+="\n merchandise_color_tbl_pk, ";
      queryForFO+="\n color_one_value, ";
      queryForFO+="\n color_two_value, ";
      queryForFO+="\n color_one_name, ";
      queryForFO+="\n color_two_name, ";
      queryForFO+="\n mt.merchandise_image, ";
      queryForFO+="\n mt.content_type, ";
      queryForFO+="\n mt.content_size, ";
      queryForFO+="\n mt.merchandise_name, ";
      queryForFO+="\n mt.merchandise_description, ";
      queryForFO+="\n mt.threshold_quantity ";
      queryForFO+="\n from ";
      queryForFO+="\n bulbul.merchandise_color_tbl mct join bulbul.merchandise_tbl mt ";
      queryForFO+="\n on (mct.merchandise_tbl_pk=mt.merchandise_tbl_pk)') as mct ";
      queryForFO+="\n (merchandise_color_tbl_pk integer, ";
      queryForFO+="\n color_one_value varchar, ";
      queryForFO+="\n color_two_value varchar, ";
      queryForFO+="\n color_one_name varchar, ";
      queryForFO+="\n color_two_name varchar, ";
      queryForFO+="\n merchandise_image oid, ";
      queryForFO+="\n content_type varchar, ";
      queryForFO+="\n content_size integer, ";
      queryForFO+="\n merchandise_name varchar, ";
      queryForFO+="\n merchandise_description varchar, ";
      queryForFO+="\n threshold_quantity integer) ";
      queryForFO+="\n where cdt.customer_design_tbl_pk="+customerDesignTblPk;
      queryForFO+="\n and mct.merchandise_color_tbl_pk=(select ";
      queryForFO+="\n merchandise_color_tbl_pk from ";
      queryForFO+="\n customer_design_tbl ";
      queryForFO+="\n where customer_design_tbl_pk="+customerDesignTblPk+") ";
      
      logger.debug("queryForFO: "+queryForFO);
      dbConnectionForFO=new DBConnection(dataSourceFO);
      dbConnectionForBO=new DBConnection(dataSourceBO);
      
      rsForFO=dbConnectionForFO.executeQuery(queryForFO);
      
      if(rsForFO.next()){
        preOrderBean=new PreOrderBean();
        preOrderBean.setCustomerDesignTblPk(rsForFO.getString("customer_design_tbl_pk"));
        preOrderBean.setCustomerEmailIdTblPk(rsForFO.getString("customer_email_id_tbl_pk"));
        preOrderBean.setDesignName(rsForFO.getString("customer_design_name"));
        preOrderBean.setDesignOId(rsForFO.getString("merchandise_image"));
        preOrderBean.setDesignContentType(rsForFO.getString("content_type"));
        preOrderBean.setDesignContentSize(rsForFO.getString("content_size"));
        preOrderBean.setProductName(rsForFO.getString("merchandise_name"));
        preOrderBean.setProductDescription(rsForFO.getString("merchandise_description"));
        preOrderBean.setMinQuantity(rsForFO.getString("threshold_quantity"));
        preOrderBean.setColor1Value(rsForFO.getString("color_one_value"));
        preOrderBean.setColor2Value(rsForFO.getString("color_two_value"));
        preOrderBean.setColor1Name(rsForFO.getString("color_one_name"));
        preOrderBean.setColor2Name(rsForFO.getString("color_two_name"));
        
        queryForBO="\n select "; 
        queryForBO+="\n mst.merchandise_size_tbl_pk, ";
        queryForBO+="\n st.size_id, ";
        queryForBO+="\n stt.size_type_id, "; 
        queryForBO+="\n mst.merchandise_price_per_qty "; 
        queryForBO+="\n from "; 
        queryForBO+="\n merchandise_size_tbl mst, ";
        queryForBO+="\n size_tbl st, ";
        queryForBO+="\n size_type_tbl stt ";
        queryForBO+="\n where  mst.is_active=1";
        queryForBO+="\n and mst.size_tbl_pk=st.size_tbl_pk ";
        queryForBO+="\n and st.size_type_tbl_pk=stt.size_type_tbl_pk ";
        queryForBO+="\n and merchandise_color_tbl_pk = " +rsForFO.getString("merchandise_color_tbl_pk");
        
        logger.debug("queryForBO: "+queryForBO); 
        rsForBO=dbConnectionForBO.executeQuery(queryForBO);
        while(rsForBO.next()){
          preOrderSizeBean=new PreOrderSizeBean();
          
          preOrderSizeBean.setMerchandiseSizeTblPk(rsForBO.getString("merchandise_size_tbl_pk"));
          preOrderSizeBean.setSizeTypeId(rsForBO.getString("size_type_id"));
          preOrderSizeBean.setSizeId(rsForBO.getString("size_id"));
          sizeArrayList.add(preOrderSizeBean);
        }
        preOrderBean.setSizeArrayList(sizeArrayList);
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dbConnectionForFO!=null){
        dbConnectionForFO.freeResultSet(rsForFO);
        dbConnectionForFO.free();
      }
      dbConnectionForFO=null;
      if(dbConnectionForBO!=null){
        dbConnectionForBO.freeResultSet(rsForBO);
        dbConnectionForBO.free();
      }
      dbConnectionForBO=null;
      logger.info("Exiting createOrderB4() method");
    }
    
    return preOrderBean;
  }
  
  public static int createOrder(int orderHeaderTblPk,OrderItemForm orderItemForm,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderHeaderTblPk;
    String[] merchandiseSizeTblPkArray=null;
    String merchandiseSizeTblPkString=null;
    String[] quantityArray=null;
    String quantityString=null;
    try{
      logger.info("Entering method createOrder()");
      
      sqlString = "{?=call sp_order_create(?,?,?,?,?,?)}";
      dbConnection = new DBConnection(dataSource);
      

      //preparing array of merchandiseSizeTblPk to pass as an arg in the pl/pgsql
      merchandiseSizeTblPkArray=orderItemForm.getHdnMerchandiseSizeTblPk();
      merchandiseSizeTblPkString="{";
      for(int index=0;index<merchandiseSizeTblPkArray.length;index++){
        merchandiseSizeTblPkString=merchandiseSizeTblPkString+merchandiseSizeTblPkArray[index];
        if(index<(merchandiseSizeTblPkArray.length-1)){ 
          merchandiseSizeTblPkString=merchandiseSizeTblPkString+",";
        }
      }
      merchandiseSizeTblPkString=merchandiseSizeTblPkString+"}";
      
      //preparing array of quantity to pass as an arg in the pl/pgsql 
      quantityArray=orderItemForm.getTxtQty();
      quantityString="{";
      for(int index=0;index<quantityArray.length;index++){
        if(!quantityArray[index].equals(null) && (quantityArray[index].trim()).length()!=0){
          quantityString=quantityString+quantityArray[index];  
        }else{
          quantityString=quantityString+"0";
        }
        
        if(index<(quantityArray.length-1)){ 
          quantityString=quantityString+",";
        }
      }
      quantityString=quantityString+"}";
      logger.debug("quantityString"+quantityString);
      
      cs = dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderHeaderTblPk);
      cs.setInt(3,orderItemForm.getHdnCustomerEmailIdTblPk());
      cs.setInt(4,OrderStatus.INCOMPLETE.KEY);
      cs.setInt(5,orderItemForm.getHdnCustomerDesignTblPk());
      cs.setArray(6,new PgSQLArray(merchandiseSizeTblPkString,Types.INTEGER));
      cs.setArray(7,new PgSQLArray(quantityString,Types.INTEGER));
      
      dbConnection.executeCallableStatement(cs);
      
      returnOrderHeaderTblPk=cs.getInt(1);
      
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.free(cs);
      }
      dbConnection=null;
      logger.info("Exiting method createOrder()");
    }
    return returnOrderHeaderTblPk;
  }
  
  public static PreOrderBean modifyOrderB4(String orderDetailTblPk,DataSource dataSource) throws SQLException,Exception{
    //DBConnection dbConnectionForFO=null;
    //DBConnection dbConnectionForBO=null;
    DBConnection dbConnection=null;
    ResultSet rsForDesign=null;
    ResultSet rsForSize=null;
    String queryForDesign=null;
    String queryForSize=null;
    PreOrderBean preOrderBean=null;
    PreOrderSizeBean preOrderSizeBean=null;
    ArrayList sizeArrayList=null;
    
    try{
      logger.info("Entering modifyOrderB4() method");
      
      sizeArrayList=new ArrayList();
      
      queryForDesign="select ";
      queryForDesign+="odt.order_detail_tbl_pk, ";
      queryForDesign+="cdt.customer_design_tbl_pk, ";
      queryForDesign+="cdt.merchandise_color_tbl_pk, ";
      queryForDesign+="cdt.customer_design_name, ";
      queryForDesign+="cdt.customer_design, ";
      queryForDesign+="cdt.design_content_type, ";
      queryForDesign+="cdt.design_content_size, ";
      queryForDesign+="mtbl.merchandise_name, ";
      queryForDesign+="mtbl.merchandise_description, ";
      queryForDesign+="mct.color_one_value, ";
      queryForDesign+="mct.color_two_value, ";
      queryForDesign+="mct.color_one_name, ";
      queryForDesign+="mct.color_two_name, ";
      queryForDesign+="mtbl.threshold_quantity ";
      queryForDesign+="from ";
      queryForDesign+="merchandise_tbl mtbl, ";
      queryForDesign+="merchandise_color_tbl mct, ";
      queryForDesign+="order_detail_tbl odt, ";
      queryForDesign+="dblink('dbname=bulbulfo','select ";
      queryForDesign+="customer_design_tbl_pk, ";
      queryForDesign+="merchandise_color_tbl_pk, ";
      queryForDesign+="customer_design_name, ";
      queryForDesign+="customer_design, ";
      queryForDesign+="design_content_type, ";
      queryForDesign+="design_content_size ";
      queryForDesign+="from bulbul.customer_design_tbl' ) ";
      queryForDesign+="as cdt ";
      queryForDesign+="(customer_design_tbl_pk integer, ";
      queryForDesign+="merchandise_color_tbl_pk integer, ";
      queryForDesign+="customer_design_name varchar, ";
      queryForDesign+="customer_design oid, ";
      queryForDesign+="design_content_type varchar, ";
      queryForDesign+="design_content_size integer ";
      queryForDesign+=") ";
      queryForDesign+="where ";
      queryForDesign+="cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
      queryForDesign+="and mct.merchandise_color_tbl_pk=cdt.merchandise_color_tbl_pk ";
      queryForDesign+="and mtbl.merchandise_tbl_pk=mct.merchandise_tbl_pk ";
      queryForDesign+="and odt.order_detail_tbl_pk="+orderDetailTblPk;
      
      
      dbConnection=new DBConnection(dataSource);
      
      rsForDesign=dbConnection.executeQuery(queryForDesign);
      
      if(rsForDesign.next()){
        preOrderBean=new PreOrderBean();
        preOrderBean.setOrderDetailTblPk(rsForDesign.getString("order_detail_tbl_pk"));
        preOrderBean.setCustomerDesignTblPk(rsForDesign.getString("customer_design_tbl_pk"));
        preOrderBean.setDesignName(rsForDesign.getString("customer_design_name"));
        preOrderBean.setDesignOId(rsForDesign.getString("customer_design"));
        preOrderBean.setDesignContentType(rsForDesign.getString("design_content_type"));
        preOrderBean.setDesignContentSize(rsForDesign.getString("design_content_size"));
        preOrderBean.setProductName(rsForDesign.getString("merchandise_name"));
        preOrderBean.setProductDescription(rsForDesign.getString("merchandise_description"));
        preOrderBean.setMinQuantity(rsForDesign.getString("threshold_quantity"));
        preOrderBean.setColor1Value(rsForDesign.getString("color_one_value"));
        preOrderBean.setColor2Value(rsForDesign.getString("color_two_value"));
        preOrderBean.setColor1Name(rsForDesign.getString("color_one_name"));
        preOrderBean.setColor2Name(rsForDesign.getString("color_two_name"));
        

        queryForSize="select ";
        queryForSize+="oit.order_item_detail_tbl_pk, ";
        queryForSize+="mst.merchandise_size_tbl_pk, ";
        queryForSize+="stt.size_type_id, ";
        queryForSize+="stbl.size_id, ";
        queryForSize+="oit.quantity ";
        queryForSize+="from ";
        queryForSize+="size_tbl stbl, ";
        queryForSize+="size_type_tbl stt, ";
        queryForSize+="merchandise_size_tbl mst left outer join  order_item_detail_tbl oit ";
        queryForSize+="on(mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
        queryForSize+="and oit.order_detail_tbl_pk="+orderDetailTblPk+") ";
        queryForSize+="where mst.is_active=1 ";
        queryForSize+="and mst.size_tbl_pk=stbl.size_tbl_pk ";
        queryForSize+="and stbl.size_type_tbl_pk=stt.size_type_tbl_pk ";
        queryForSize+="and mst.merchandise_color_tbl_pk= ";
        queryForSize+="(select ";
        queryForSize+="cdt.merchandise_color_tbl_pk ";
        queryForSize+="from order_detail_tbl odt, ";
        queryForSize+="dblink('dbname=bulbulfo','select customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
        queryForSize+="as cdt ( customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer) ";
        queryForSize+="where ";
        queryForSize+="cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
        queryForSize+="and odt.order_detail_tbl_pk="+orderDetailTblPk;
        queryForSize+=") ";
        
        rsForSize=dbConnection.executeQuery(queryForSize);
        while(rsForSize.next()){
          preOrderSizeBean=new PreOrderSizeBean();
          
          preOrderSizeBean.setMerchandiseSizeTblPk(rsForSize.getString("merchandise_size_tbl_pk"));
          preOrderSizeBean.setOrderItemDetailTblPk(rsForSize.getString("order_item_detail_tbl_pk")==null? "-1" : rsForSize.getString("order_item_detail_tbl_pk"));
          preOrderSizeBean.setSizeTypeId(rsForSize.getString("size_type_id"));
          preOrderSizeBean.setSizeId(rsForSize.getString("size_id"));
          preOrderSizeBean.setQuantity(rsForSize.getString("quantity"));
          
          sizeArrayList.add(preOrderSizeBean);
        }
        preOrderBean.setSizeArrayList(sizeArrayList);
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw e;
    }catch(Exception e){
      logger.error(e.getMessage());
      throw e;
    }finally{
      if(dbConnection!=null){
        dbConnection.freeResultSet(rsForDesign);
        dbConnection.freeResultSet(rsForSize);
        dbConnection.free();
      }
      dbConnection=null;
      
      logger.info("Exiting modifyOrderB4() method");
    }
    
    return preOrderBean;
  }
  
  public static void modifyOrder(OrderItemForm orderItemForm,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderDetailTblPk;
    String[] merchandiseSizeTblPkArray=null;
    String merchandiseSizeTblPkString=null;
    String[] orderItemDetailTblPkArray=null;
    String orderItemDetailTblPkString=null;
    String[] quantityArray=null;
    String quantityString=null;
    try{
      logger.info("Entering method createOrder()");
      
      sqlString = "{?=call sp_order_modify(?,?,?,?)}";
      dbConnection = new DBConnection(dataSource);
      

      //preparing array of merchandiseSizeTblPk to pass as an arg in the pl/pgsql
      merchandiseSizeTblPkArray=orderItemForm.getHdnMerchandiseSizeTblPk();
      merchandiseSizeTblPkString="{";
      for(int index=0;index<merchandiseSizeTblPkArray.length;index++){
        merchandiseSizeTblPkString=merchandiseSizeTblPkString+merchandiseSizeTblPkArray[index];
        if(index<(merchandiseSizeTblPkArray.length-1)){ 
          merchandiseSizeTblPkString=merchandiseSizeTblPkString+",";
        }
      }
      merchandiseSizeTblPkString=merchandiseSizeTblPkString+"}";
      
      //preparing array of orderItemDetailTblPk to pass as an arg in the pl/pgsql
      orderItemDetailTblPkArray=orderItemForm.getHdnOrderItemDetailTblPk();
      orderItemDetailTblPkString="{";
      for(int index=0;index<orderItemDetailTblPkArray.length;index++){
        orderItemDetailTblPkString=orderItemDetailTblPkString+orderItemDetailTblPkArray[index];
        if(index<(orderItemDetailTblPkArray.length-1)){ 
          orderItemDetailTblPkString=orderItemDetailTblPkString+",";
        }
      }
      orderItemDetailTblPkString=orderItemDetailTblPkString+"}";
      
      //preparing array of quantity to pass as an arg in the pl/pgsql 
      quantityArray=orderItemForm.getTxtQty();
      quantityString="{";
      for(int index=0;index<quantityArray.length;index++){
        if(!quantityArray[index].equals(null) && (quantityArray[index].trim()).length()!=0){
          quantityString=quantityString+quantityArray[index];  
        }else{
          quantityString=quantityString+"0";
        }
        
        if(index<(quantityArray.length-1)){ 
          quantityString=quantityString+",";
        }
      }
      quantityString=quantityString+"}";
      logger.debug("merchandiseSizeTblPkString : "+merchandiseSizeTblPkString);
      logger.debug("orderItemDetailTblPkString : "+orderItemDetailTblPkString);
      logger.debug("quantityString : "+quantityString);
      
      cs = dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderItemForm.getHdnOrderDetailTblPk());
      cs.setArray(3,new PgSQLArray(orderItemDetailTblPkString,Types.INTEGER));
      cs.setArray(4,new PgSQLArray(merchandiseSizeTblPkString,Types.INTEGER));
      cs.setArray(5,new PgSQLArray(quantityString,Types.INTEGER));
      
      dbConnection.executeCallableStatement(cs);
      
      returnOrderDetailTblPk=cs.getInt(1);
      
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.free(cs);
      }
      dbConnection=null;
      logger.info("Exiting method createOrder()");
    }
    
  }
  
  
  public static void deleteOrderItemDetail(int orderItemDetailTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderItemDetailTblPk;
    
    try{
      logger.info("Entering method deleteOrderItemDetail()");
      
      sqlString = "{?=call sp_order_item_detail_remove(?)}";
      dbConnection = new DBConnection(dataSource);
      
      cs = dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderItemDetailTblPk);
      
      dbConnection.executeCallableStatement(cs);
      
      returnOrderItemDetailTblPk=cs.getInt(1);
      
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.free(cs);
      }
      dbConnection=null;
      logger.info("Exiting method deleteOrderItemDetail()");
    }
    
  }
  
  public static void deleteOrderDetail(int orderDetailTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderDetailTblPk;
    
    try{
      logger.info("Entering method deleteOrderDetail()");
      
      sqlString = "{?=call sp_order_detail_remove(?)}";
      dbConnection = new DBConnection(dataSource);
      
      cs = dbConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderDetailTblPk);
      
      dbConnection.executeCallableStatement(cs);
      
      returnOrderDetailTblPk=cs.getInt(1);
      
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.free(cs);
      }
      dbConnection=null;
      logger.info("Exiting method deleteOrderDetail()");
    }
  }
  public static void updateOrderWithShippingAddress(DataSource dataSource, ShippingAddressForm shippingAddressForm, int orderHeaderTblPk) throws SQLException, Exception{
    DBConnection dBConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderHeaderTblPk=0;
    try{
      logger.info("Entering updateOrderWithShippingAddress()");
      sqlString="{?=call sp_upd_order_ship_address(?,?,?,?,?,?,?,?,?,?,?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderHeaderTblPk);
      cs.setString(3,shippingAddressForm.getTxtFullName());
      cs.setString(4,shippingAddressForm.getTxtAddressLine1());
      cs.setString(5,shippingAddressForm.getTxtAddressLine2());
      cs.setString(6,shippingAddressForm.getTxtCity());
      cs.setString(7,shippingAddressForm.getTxtPincode());
      logger.info("cboState : " + shippingAddressForm.getCboState());
      logger.info("txtState : " + shippingAddressForm.getTxtState());
      if(!shippingAddressForm.getCboState().equals("0")){
        cs.setString(8,shippingAddressForm.getCboState());  
      }else if(shippingAddressForm.getTxtState().trim().length()!=0){
        cs.setString(8,shippingAddressForm.getTxtState());
      }
      cs.setInt(9,shippingAddressForm.getHdnIsStateListed());
      
      if(!shippingAddressForm.getCboCountry().equals("0")){
        cs.setString(10,shippingAddressForm.getCboCountry());  
      }else if(shippingAddressForm.getTxtCountry().trim().length()!=0){
        cs.setString(10,shippingAddressForm.getTxtCountry());
      }
      
      cs.setInt(11,shippingAddressForm.getHdnIsCountryListed());
      cs.setString(12,shippingAddressForm.getTxtPhone());
      logger.info("EmailId : "+ shippingAddressForm.getTxtEmailId());
      cs.setString(13,shippingAddressForm.getTxtEmailId());
      
      dBConnection.executeCallableStatement(cs);
      
      returnOrderHeaderTblPk=cs.getInt(1);
      logger.debug("Order Header Tbl Pk:"+returnOrderHeaderTblPk);
    }catch(SQLException e){
      logger.error(e);   
      throw e;
    }catch(Exception e){
      logger.error(e);   
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting updateOrderWithShippingAddress()");
    }
  }  
  public static void updateOrderWithAddressBook(DataSource dataSource,int orderHeaderTblPk, int customerAddressBookTblPk)throws SQLException, Exception{
    DBConnection dBConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int returnOrderHeaderTblPk=0;
    try{
      logger.info("Entering updateOrderWithAddressBook()");
      sqlString="{?=call sp_upd_order_ship_address_book(?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderHeaderTblPk);
      cs.setInt(3,customerAddressBookTblPk);
      dBConnection.executeCallableStatement(cs);
      returnOrderHeaderTblPk=cs.getInt(1);
      logger.debug("Order Header Tbl Pk:"+returnOrderHeaderTblPk);
    }catch(SQLException e){
      logger.error(e);   
      throw e;
    }catch(Exception e){
      logger.error(e);   
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting updateOrderWithAddressBook()");
    }
  } 
   public static void updateOrderWithMultipleAddress(DataSource dataSource,MultipleAddressForm multipleAddressForm,int orderHeaderTblPk)throws SQLException, Exception{
    DBConnection dBConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    String[] orderDetailTblPkArray=null;
    String[] customerShippingAddressBookTblPkArray=null;
    String orderDetailTblPkString=null;
    String customerShippingAddressBookTblPkString=null;
    int returnValue=0;
    try{
      logger.info("Entering updateOrderWithMultipleAddress()");
      orderDetailTblPkArray=multipleAddressForm.getHdnOrderDetailTblPk();
      customerShippingAddressBookTblPkArray=multipleAddressForm.getCboShippingAddress();  
      orderDetailTblPkString="{";
      customerShippingAddressBookTblPkString="{";
      for(int counter=0; counter<orderDetailTblPkArray.length;counter++){
        orderDetailTblPkString+=orderDetailTblPkArray[counter]+",";
        customerShippingAddressBookTblPkString+=customerShippingAddressBookTblPkArray[counter]+",";
      }
      //To Remove Last ","  
      orderDetailTblPkString=orderDetailTblPkString.substring(0,orderDetailTblPkString.length()-1);
      customerShippingAddressBookTblPkString=customerShippingAddressBookTblPkString.substring(0,customerShippingAddressBookTblPkString.length()-1);
      
      orderDetailTblPkString+="}";
      customerShippingAddressBookTblPkString+="}";
      
      logger.debug("orderDetailTblPkString : " +orderDetailTblPkString);
      logger.debug("customerShippingAddressBookTblPkString : " +customerShippingAddressBookTblPkString);
      
      sqlString="{?=call sp_upd_order_multi_ship_address(?,?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderHeaderTblPk);
      cs.setArray(3,new PgSQLArray(orderDetailTblPkString,Types.INTEGER));
      cs.setArray(4,new PgSQLArray(customerShippingAddressBookTblPkString,Types.INTEGER));
      dBConnection.executeCallableStatement(cs);
      returnValue=cs.getInt(1);
      logger.debug("Return Value :"+returnValue);
    }catch(SQLException e){
      logger.error(e);   
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      logger.error(e);   
      e.printStackTrace();
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting updateOrderWithMultipleAddress()");
    }
  } 
  public static ArrayList getAddresses(DataSource dataSource,int customerEmailIdTblPk) throws SQLException,Exception{
    ArrayList addresses=null;
    ShippingAddressBean address=null;
    DBConnection dbConnection=null;
    ResultSet rs=null;
    String query=null;
    try{
      logger.info("Entering getAddresses() method");
      addresses=new ArrayList();
      
      query="\n SELECT ";
      query+="\n customer_address_book_tbl_pk, ";
      query+="\n customer_email_id_tbl_pk, ";
      query+="\n full_name,address_line_1, ";
      query+="\n address_line_2, ";
      query+="\n city, ";
      query+="\n pincode, ";
      query+="\n state, ";
      query+="\n is_state_listed, ";
      query+="\n country,is_country_listed, ";
      query+="\n phone_number, ";
      query+="\n email_id ";
      query+="\n from customer_address_book_tbl ";
      query+="\n where customer_email_id_tbl_pk="+customerEmailIdTblPk;
      
      query=query + " order by full_name ";
      
      logger.debug("query: "+query);

      dbConnection=new DBConnection(dataSource);
      rs=dbConnection.executeQuery(query);  
      while(rs.next()){
        address=new ShippingAddressBean();
        address.setCustomerAddressBookTblPk(rs.getString("customer_address_book_tbl_pk"));
        address.setFullName(rs.getString("full_name"));
        address.setAddressLine1(rs.getString("address_line_1"));
        address.setAddressLine2(rs.getString("address_line_2"));
        address.setCity(rs.getString("city"));
        address.setPincode(rs.getString("pincode"));
        address.setState(rs.getString("state"));
        address.setIsStateSelected(rs.getString("is_state_listed"));
        address.setCountry(rs.getString("country"));
        address.setIsCountrySelected(rs.getString("is_country_listed"));
        address.setPhoneNumber(rs.getString("phone_number"));
        address.setEmailId(rs.getString("email_id"));
        addresses.add(address);
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
      logger.info("Exiting getAddresses() method");
      if(dbConnection!=null){
        dbConnection.free(rs);
        dbConnection.free();
      }
      dbConnection=null;
    }
    return addresses;
  }  
 public static OrderHeaderBean listOrderWishShippingAddress(int orderHeaderTblPk,DataSource dataSource) throws SQLException,Exception{
    DBConnection dbConnection=null;
    ResultSet rsForHeader=null;
    ResultSet rsForDetail=null;
    ResultSet rsForItemDetail=null;
    String queryForHeader=null;
    String queryForDetail=null;
    String queryForItemDetail=null;
    OrderHeaderBean orderHeaderBean=null;
    OrderDetailBean orderDetailBean=null;
    OrderItemDetailBean orderItemDetailBean=null; 
    ShippingAddressBean shippingAddressBean=null;
    Format formatter=null;
    ArrayList orderDetailArrayList=null;
    ArrayList orderItemDetailArrayList=null;
    DecimalFormat discountFormatter=null;
    DecimalFormat priceFormatter=null;
    int quantity=0;
    float discountedPricePerQty=0;
    float pricePerQty=0;
    float lessPerQty=0;
    float discount=0;
    float less=0;
    float amount=0;
    float total=0;

    try{
      logger.info("Entering method listOrderWishShippingAddress()");
      
      priceFormatter=new DecimalFormat("Rs ##,##,##0.00");
      discountFormatter=new DecimalFormat("-#0.00");
      
      dbConnection = new DBConnection(dataSource);

      queryForHeader="\n select ";
      queryForHeader+="\n oht.order_header_tbl_pk, ";
      queryForHeader+="\n oht.order_code, ";
      queryForHeader+="\n oht.order_gen_date, ";
      queryForHeader+="\n sum(sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) * oit.quantity) as amount, ";
      queryForHeader+="\n oht.customer_address_book_tbl_pk, ";
      queryForHeader+="\n (case when (oht.full_name is null) then cabt.full_name else oht.full_name end ) as full_name, ";
      queryForHeader+="\n (case when (oht.address_line_1 is null) then cabt.address_line_1 else oht.address_line_1 end ) as address_line_1, ";
      queryForHeader+="\n (case when (oht.address_line_2 is null) then cabt.address_line_2 else oht.address_line_2 end ) as address_line_2, ";
      queryForHeader+="\n (case when (oht.city is null) then cabt.city else oht.city end ) as city, ";
      queryForHeader+="\n (case when (oht.pincode is null) then cabt.pincode else oht.pincode end ) as pincode, ";
      queryForHeader+="\n (case when (oht.state is null) then cabt.state else oht.state end ) as state, ";
      queryForHeader+="\n (case when (oht.country is null) then cabt.country else oht.country end ) as country, ";
      queryForHeader+="\n (case when (oht.email_id is null) then cabt.email_id else oht.email_id end ) as email_id, ";
      queryForHeader+="\n (case when (oht.phone_number is null) then cabt.phone_number else oht.phone_number end ) as phone_number ";
      queryForHeader+="\n from ";
      queryForHeader+="\n order_header_tbl oht ";
      queryForHeader+="\n left outer join ";
      queryForHeader+="\n dblink('dbname=bulbulfo','select ";
      queryForHeader+="\n customer_address_book_tbl_pk, ";
      queryForHeader+="\n full_name, ";
      queryForHeader+="\n address_line_1, ";
      queryForHeader+="\n address_line_2, ";
      queryForHeader+="\n city, ";
      queryForHeader+="\n pincode, ";
      queryForHeader+="\n state, ";
      queryForHeader+="\n country, ";
      queryForHeader+="\n email_id, ";
      queryForHeader+="\n phone_number ";
      queryForHeader+="\n from bulbul.customer_address_book_tbl') as cabt ( ";
      queryForHeader+="\n customer_address_book_tbl_pk  integer, ";
      queryForHeader+="\n full_name  varchar, ";
      queryForHeader+="\n address_line_1  varchar, ";
      queryForHeader+="\n address_line_2 varchar, ";
      queryForHeader+="\n city varchar, ";
      queryForHeader+="\n pincode varchar, ";
      queryForHeader+="\n state  varchar, ";
      queryForHeader+="\n country varchar, ";
      queryForHeader+="\n email_id varchar, ";
      queryForHeader+="\n phone_number varchar ";
      queryForHeader+="\n ) ";
      queryForHeader+="\n on ";
      queryForHeader+="\n (oht.customer_address_book_tbl_pk=cabt.customer_address_book_tbl_pk), ";
      queryForHeader+="\n order_detail_tbl odt, ";
      queryForHeader+="\n order_item_detail_tbl oit, ";
      queryForHeader+="\n merchandise_size_tbl mst, ";
      queryForHeader+="\n dblink('dbname=bulbulfo','select customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
      queryForHeader+="\n as cdt ( customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer) ";
      queryForHeader+="\n where ";
      queryForHeader+="\n oht.order_header_tbl_pk=odt.order_header_tbl_pk ";
      queryForHeader+="\n and odt.order_detail_tbl_pk=oit.order_detail_tbl_pk ";
      queryForHeader+="\n and mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
      queryForHeader+="\n and cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
      queryForHeader+="\n and oht.order_header_tbl_pk= " + orderHeaderTblPk ;
      queryForHeader+="\n group by  oht.order_header_tbl_pk, ";
      queryForHeader+="\n oht.order_code, ";
      queryForHeader+="\n oht.order_gen_date, ";
      queryForHeader+="\n oht.customer_address_book_tbl_pk, ";
      queryForHeader+="\n cabt.full_name,oht.full_name, ";
      queryForHeader+="\n cabt.address_line_1,oht.address_line_1, ";
      queryForHeader+="\n cabt.address_line_2,oht.address_line_2, ";
      queryForHeader+="\n cabt.city,oht.city, ";
      queryForHeader+="\n cabt.pincode,oht.pincode , ";
      queryForHeader+="\n cabt.state,oht.state, ";
      queryForHeader+="\n cabt.country,oht.country, ";
      queryForHeader+="\n cabt.email_id,oht.email_id,  ";
      queryForHeader+="\n cabt.phone_number,oht.phone_number  ";
      logger.debug("queryForHeader : " +queryForHeader);   
 
      formatter=new SimpleDateFormat("dd-MMM-yyyy");
      orderHeaderBean=new OrderHeaderBean();      
      shippingAddressBean=new ShippingAddressBean();      
      rsForHeader=dbConnection.executeQuery(queryForHeader);
      if (rsForHeader.next()){
      
        logger.debug("Fill Order Header Here");        
        orderHeaderBean.setOrderHeaderTblPk(rsForHeader.getInt("order_header_tbl_pk"));
        orderHeaderBean.setOrderCode(rsForHeader.getString("order_code"));
        orderHeaderBean.setOrderGenDate(formatter.format(rsForHeader.getDate("order_gen_date")));
        orderHeaderBean.setOrderAmount(priceFormatter.format(rsForHeader.getFloat("amount")));
        if (rsForHeader.getString("full_name")==null){
            orderHeaderBean.setIsMultipleAddress(FOConstants.YES_VAL.toString());
        }else{
          orderHeaderBean.setIsMultipleAddress(FOConstants.NO_VAL.toString());
        }
        shippingAddressBean.setCustomerAddressBookTblPk(rsForHeader.getString("customer_address_book_tbl_pk")==null?"":rsForHeader.getString("customer_address_book_tbl_pk"));
        shippingAddressBean.setFullName(rsForHeader.getString("full_name")==null?"":rsForHeader.getString("full_name"));
        shippingAddressBean.setAddressLine1(rsForHeader.getString("address_line_1")==null?"":rsForHeader.getString("address_line_1"));
        shippingAddressBean.setAddressLine2(rsForHeader.getString("address_line_2")==null?"":rsForHeader.getString("address_line_2"));
        shippingAddressBean.setCity(rsForHeader.getString("city")==null?"":rsForHeader.getString("city"));
        shippingAddressBean.setPincode(rsForHeader.getString("pincode")==null?"":rsForHeader.getString("pincode")); 
        shippingAddressBean.setState(rsForHeader.getString("state")==null?"":rsForHeader.getString("state")); 
        shippingAddressBean.setCountry(rsForHeader.getString("country")==null?"":rsForHeader.getString("country"));
        shippingAddressBean.setEmailId(rsForHeader.getString("email_id")==null?"":rsForHeader.getString("email_id"));
        shippingAddressBean.setPhoneNumber(rsForHeader.getString("phone_number")==null?"":rsForHeader.getString("phone_number"));
        orderHeaderBean.setShippingAddress(shippingAddressBean);


  
        
        queryForDetail="\n select ";
        queryForDetail+="\n odt.order_header_tbl_pk, ";
        queryForDetail+="\n odt.order_detail_tbl_pk, ";
        queryForDetail+="\n sum(sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) * oit.quantity) as amount, ";
        queryForDetail+="\n cdt.customer_design_name, ";
        queryForDetail+="\n mtbl.merchandise_name, ";
        queryForDetail+="\n mtbl.merchandise_description, ";
        queryForDetail+="\n mct.color_one_name, ";
        queryForDetail+="\n mct.color_two_name, ";
        queryForDetail+="\n mct.color_one_value, ";
        queryForDetail+="\n mct.color_two_value, ";
        queryForDetail+="\n odt.customer_address_book_tbl_pk, ";
        queryForDetail+="\n cabt.full_name, ";
        queryForDetail+="\n cabt.address_line_1, ";
        queryForDetail+="\n cabt.address_line_2, ";
        queryForDetail+="\n cabt.city, ";
        queryForDetail+="\n cabt.pincode, ";
        queryForDetail+="\n cabt.state, ";
        queryForDetail+="\n cabt.country, ";
        queryForDetail+="\n cabt.email_id, ";
        queryForDetail+="\n cabt.phone_number, ";
        queryForDetail+="\n (select count(innerodt.customer_address_book_tbl_pk) ";
        queryForDetail+="\n from order_detail_tbl innerodt where ";
        queryForDetail+="\n innerodt.customer_address_book_tbl_pk=odt.customer_address_book_tbl_pk ";
        queryForDetail+="\n and innerodt.order_header_tbl_pk=odt.order_header_tbl_pk ";
        queryForDetail+="\n ) as shippingaddresscount ";
        queryForDetail+="\n from ";
        queryForDetail+="\n order_detail_tbl odt left outer join ";
        queryForDetail+="\n dblink('dbname=bulbulfo','select ";
        queryForDetail+="\n customer_address_book_tbl_pk, ";
        queryForDetail+="\n full_name, ";
        queryForDetail+="\n address_line_1, ";
        queryForDetail+="\n address_line_2, ";
        queryForDetail+="\n city, ";
        queryForDetail+="\n pincode, ";
        queryForDetail+="\n state, ";
        queryForDetail+="\n country, ";
        queryForDetail+="\n email_id, ";
        queryForDetail+="\n phone_number ";
        queryForDetail+="\n from bulbul.customer_address_book_tbl') as cabt ( ";
        queryForDetail+="\n customer_address_book_tbl_pk  integer, ";
        queryForDetail+="\n full_name  varchar, ";
        queryForDetail+="\n address_line_1  varchar, ";
        queryForDetail+="\n address_line_2 varchar, ";
        queryForDetail+="\n city varchar, ";
        queryForDetail+="\n pincode varchar, ";
        queryForDetail+="\n state  varchar, ";
        queryForDetail+="\n country varchar, ";
        queryForDetail+="\n email_id varchar, ";
        queryForDetail+="\n phone_number varchar ";
        queryForDetail+="\n ) on (odt.customer_address_book_tbl_pk=cabt.customer_address_book_tbl_pk), ";
        queryForDetail+="\n merchandise_tbl mtbl, ";
        queryForDetail+="\n merchandise_color_tbl mct, ";
        queryForDetail+="\n order_item_detail_tbl oit, ";
        queryForDetail+="\n merchandise_size_tbl mst, ";
        queryForDetail+="\n dblink('dbname=bulbulfo','select customer_design_tbl_pk,customer_design_name,merchandise_color_tbl_pk from bulbul.customer_design_tbl' ) ";
        queryForDetail+="\n as cdt ( customer_design_tbl_pk integer,customer_design_name varchar,merchandise_color_tbl_pk integer) ";
        queryForDetail+="\n where ";
        queryForDetail+="\n cdt.customer_design_tbl_pk=odt.customer_design_tbl_pk ";
        queryForDetail+="\n and odt.order_detail_tbl_pk=oit.order_detail_tbl_pk ";
        queryForDetail+="\n and mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
        queryForDetail+="\n and cdt.merchandise_color_tbl_pk=mct.merchandise_color_tbl_pk ";
        queryForDetail+="\n and mct.merchandise_tbl_pk=mtbl.merchandise_tbl_pk ";
        queryForDetail+="\n and odt.order_header_tbl_pk=" + rsForHeader.getString("order_header_tbl_pk");
        queryForDetail+="\n group by ";
        queryForDetail+="\n odt.order_header_tbl_pk, ";
        queryForDetail+="\n odt.order_detail_tbl_pk, ";
        queryForDetail+="\n cdt.customer_design_name, ";
        queryForDetail+="\n mtbl.merchandise_name, ";
        queryForDetail+="\n mtbl.merchandise_description, ";
        queryForDetail+="\n mct.color_one_name, ";
        queryForDetail+="\n mct.color_two_name, ";
        queryForDetail+="\n mct.color_one_value, ";
        queryForDetail+="\n mct.color_two_value, ";
        queryForDetail+="\n odt.customer_address_book_tbl_pk, ";
        queryForDetail+="\n cabt.full_name, ";
        queryForDetail+="\n cabt.address_line_1, ";
        queryForDetail+="\n cabt.address_line_2, ";
        queryForDetail+="\n cabt.city, ";
        queryForDetail+="\n cabt.pincode, ";
        queryForDetail+="\n cabt.state, ";
        queryForDetail+="\n cabt.country, ";
        queryForDetail+="\n cabt.email_id, ";
        queryForDetail+="\n cabt.phone_number ";
        queryForDetail+="\n order by odt.customer_address_book_tbl_pk";
        logger.debug("queryForDetail : " +queryForDetail);   
      
        orderDetailArrayList=new ArrayList();
        
        rsForDetail=dbConnection.executeQuery(queryForDetail);
        while(rsForDetail.next()){
          logger.debug("Fill Order Detail Here");
          orderDetailBean=new OrderDetailBean();
          orderDetailBean.setOrderDetailTblPk(rsForDetail.getString("order_detail_tbl_pk"));
          orderDetailBean.setOrderDetailTotal(priceFormatter.format(rsForDetail.getFloat("amount")));
          orderDetailBean.setColor1Name(rsForDetail.getString("color_one_name"));
          orderDetailBean.setColor2Name(rsForDetail.getString("color_two_name"));
          orderDetailBean.setColor1Value(rsForDetail.getString("color_one_value"));
          orderDetailBean.setColor2Value(rsForDetail.getString("color_two_value"));
          orderDetailBean.setDesignName(rsForDetail.getString("customer_design_name"));
          orderDetailBean.setMerchandiseDesc(rsForDetail.getString("merchandise_description"));
          orderDetailBean.setMerchandiseName(rsForDetail.getString("merchandise_name"));
          orderDetailBean.setOrderDetailTblPk(rsForDetail.getString("order_detail_tbl_pk"));
          orderDetailBean.setShippingAddressCount(rsForDetail.getString("shippingaddresscount")); 
          
          
          shippingAddressBean=new ShippingAddressBean();  
          shippingAddressBean.setCustomerAddressBookTblPk(rsForDetail.getString("customer_address_book_tbl_pk")==null?"":rsForDetail.getString("customer_address_book_tbl_pk"));
          shippingAddressBean.setFullName(rsForDetail.getString("full_name")==null?"":rsForDetail.getString("full_name"));
          shippingAddressBean.setAddressLine1(rsForDetail.getString("address_line_1")==null?"":rsForDetail.getString("address_line_1"));
          shippingAddressBean.setAddressLine2(rsForDetail.getString("address_line_2")==null?"":rsForDetail.getString("address_line_2"));
          shippingAddressBean.setCity(rsForDetail.getString("city")==null?"":rsForDetail.getString("city"));
          shippingAddressBean.setPincode(rsForDetail.getString("pincode")==null?"":rsForDetail.getString("pincode")); 
          shippingAddressBean.setState(rsForDetail.getString("state")==null?"":rsForDetail.getString("state")); 
          shippingAddressBean.setCountry(rsForDetail.getString("country")==null?"":rsForDetail.getString("country"));
          shippingAddressBean.setEmailId(rsForDetail.getString("email_id")==null?"":rsForDetail.getString("email_id"));
          shippingAddressBean.setPhoneNumber(rsForDetail.getString("phone_number")==null?"":rsForDetail.getString("phone_number"));
          orderDetailBean.setShippingAddress(shippingAddressBean);
          
          queryForItemDetail="\n select ";
          queryForItemDetail+="\n oit.order_detail_tbl_pk, ";
          queryForItemDetail+="\n oit.order_item_detail_tbl_pk, ";
          queryForItemDetail+="\n stt.size_type_id, ";
          queryForItemDetail+="\n stbl.size_id, ";
          queryForItemDetail+="\n oit.quantity, ";
          queryForItemDetail+="\n mst.merchandise_price_per_qty,";
          queryForItemDetail+="\n sp_discount_price_per_qty(mst.merchandise_size_tbl_pk,mst.merchandise_price_per_qty,cast(now() as timestamp),oit.quantity) as discounted_price_per_qty ";
          queryForItemDetail+="\n from ";
          queryForItemDetail+="\n order_item_detail_tbl oit, ";
          queryForItemDetail+="\n merchandise_size_tbl mst, ";
          queryForItemDetail+="\n size_tbl stbl, ";
          queryForItemDetail+="\n size_type_tbl stt ";
          queryForItemDetail+="\n where ";
          queryForItemDetail+="\n mst.merchandise_size_tbl_pk=oit.merchandise_size_tbl_pk ";
          queryForItemDetail+="\n and mst.size_tbl_pk=stbl.size_tbl_pk ";
          queryForItemDetail+="\n and stbl.size_type_tbl_pk=stt.size_type_tbl_pk ";
          queryForItemDetail+="\n and oit.order_detail_tbl_pk= " + rsForDetail.getString("order_detail_tbl_pk");
          
          logger.debug("queryForItemDetail : " +queryForItemDetail);   
          rsForItemDetail=dbConnection.executeQuery(queryForItemDetail);
          orderItemDetailArrayList=new ArrayList();                    
          while(rsForItemDetail.next()){
            logger.debug("Fill Order Item Detail Here");        
            orderItemDetailBean = new OrderItemDetailBean();
            orderItemDetailBean.setOrderItemDetailTblPk(rsForItemDetail.getString("order_item_detail_tbl_pk"));
            orderItemDetailBean.setOrderDetailTblPk(rsForItemDetail.getString("order_detail_tbl_pk"));
            orderItemDetailBean.setSizeId(rsForItemDetail.getString("size_id"));
            orderItemDetailBean.setSizeTypeId(rsForItemDetail.getString("size_type_id"));
            orderItemDetailBean.setQuantity(rsForItemDetail.getString("quantity"));
            
            
            /*Calculation for Discount Price Starts*/
            quantity=rsForItemDetail.getInt("quantity");
            discountedPricePerQty=rsForItemDetail.getFloat("discounted_price_per_qty");
            pricePerQty=rsForItemDetail.getFloat("merchandise_price_per_qty");
            lessPerQty=(pricePerQty-discountedPricePerQty);
            discount=(lessPerQty*100)/pricePerQty;
            less=(lessPerQty*quantity);
            amount=(pricePerQty*quantity);
            total=(amount-less);
            
            
            /*Calculation for Discount Price Ends*/
            
            orderItemDetailBean.setDiscount(discountFormatter.format(discount));
            orderItemDetailBean.setPrice(priceFormatter.format(pricePerQty));
            orderItemDetailBean.setLess(priceFormatter.format(less));
            orderItemDetailBean.setAmount(priceFormatter.format(amount));
            orderItemDetailBean.setTotal(priceFormatter.format(total));
            
            orderItemDetailArrayList.add(orderItemDetailBean);
          }
          orderDetailArrayList.add(orderDetailBean);
          orderDetailBean.setOrderItemDetail(orderItemDetailArrayList);        
          dbConnection.freeResultSet(rsForItemDetail);
        }
        orderHeaderBean.setOrderDetail(orderDetailArrayList);
      }else{
        orderHeaderBean.setOrderHeaderTblPk(-1);
        orderHeaderBean.setOrderCode("-");
        orderHeaderBean.setOrderGenDate("-");
        orderHeaderBean.setOrderAmount("-");
      }
    }catch(SQLException e){
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      if (dbConnection!=null){
        dbConnection.freeResultSet(rsForHeader);
        dbConnection.freeResultSet(rsForDetail);
        dbConnection.freeResultSet(rsForItemDetail);
        dbConnection.free();
      }
      dbConnection=null;
      logger.info("Exiting method listOrderWishShippingAddress");
    }
    return orderHeaderBean;
  }
  
   public static ArrayList getInetBanks(DataSource datasource) {
    ArrayList inetBanks=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String[] inetBank=null;
    try{
      logger.info("Entering getInetBanks");
      inetBanks= new ArrayList();
      dBConnection= new DBConnection(datasource);
      
      sqlString="\n select ";
      sqlString+="\n ibt.inet_banking_tbl_pk, ";
      sqlString+="\n ibt.bank_name ";
      sqlString+="\n from inet_banking_tbl ibt ";
      sqlString+="\n where ibt.is_active=1 ";
      sqlString+="\n order by ibt.bank_name ";
      rs=dBConnection.executeQuery(sqlString);

      while (rs.next()){
        inetBank= new String[2];
        inetBank[0]=rs.getString("inet_banking_tbl_pk");
        inetBank[1]=rs.getString("bank_name");
        inetBanks.add(inetBank);
      }
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting getInetBanks");
      if(dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null;
    }
    return inetBanks;
  }
  
  public static ArrayList getOutletCities(DataSource datasource) {
    ArrayList outletCities=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    
    try{
      logger.info("Entering getOutletCities");
      outletCities= new ArrayList();
      dBConnection= new DBConnection(datasource);
      
      sqlString="\n select ";
      sqlString+="\n distinct otbl.city ";
      sqlString+="\n from ";
      sqlString+="\n outlet_tbl otbl ";
      sqlString+="\n where otbl.is_active=1 ";
      sqlString+="\n order by otbl.city ";

      rs=dBConnection.executeQuery(sqlString);

      while (rs.next()){
        outletCities.add(rs.getString("city"));
      }
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting getOutletCities");
      if(dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null;
    }
    return outletCities;
  }
  
  public static ArrayList getOutlets(DataSource datasource, String city) {
    ArrayList outlets=null;
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    OutletBean outlet=null;
    try{
      logger.info("Entering getOutlets");
      outlets= new ArrayList();
      dBConnection= new DBConnection(datasource);
      
      sqlString="\n select ";
      sqlString+="\n otbl.outlet_tbl_pk,  ";
      sqlString+="\n otbl.outlet_name,  ";
      sqlString+="\n otbl.address1,  ";
      sqlString+="\n otbl.address2,  ";
      sqlString+="\n otbl.address3,  ";
      sqlString+="\n otbl.city,  ";
      sqlString+="\n otbl.state,  ";
      sqlString+="\n otbl.country,  ";
      sqlString+="\n otbl.pincode  ";
      sqlString+="\n from  ";
      sqlString+="\n outlet_tbl otbl  ";
      sqlString+="\n where otbl.is_active=1  ";
      sqlString+="\n and otbl.city='" +city +"'";
      sqlString+="\n order by otbl.outlet_name";

      rs=dBConnection.executeQuery(sqlString);

      while (rs.next()){
        outlet=new OutletBean();
        outlet.setOutletTblPk(rs.getString("outlet_tbl_pk"));
        outlet.setOutletName(rs.getString("outlet_name"));
        outlet.setAddress1(rs.getString("address1"));
        outlet.setAddress2(rs.getString("address2"));
        outlet.setAddress3(rs.getString("address3"));
        outlet.setCity(rs.getString("city"));
        outlet.setState(rs.getString("state"));
        outlet.setCountry(rs.getString("country"));
        outlet.setPincode(rs.getString("pincode"));
        outlets.add(outlet);
      }
    }catch(SQLException e){
      logger.error(e);
    }catch(Exception e){
      logger.error(e);
    }finally{
      logger.info("Exiting getOutlets");
      if(dBConnection!=null) {
        dBConnection.free(rs);
      }
      dBConnection=null;
    }
    return outlets;
  }
  
  public static void completeOrder(DataSource dataSource,int orderHeaderTblPk,int paymentMode,int outletTblPk,int inetBankingTblPk)throws SQLException,Exception{
    DBConnection dBConnection=null;
    CallableStatement cs=null;
    String sqlString=null;
    int orderStatus;
    int returnValue=0;
    try{
      logger.info("Entering completeOrder()");
      logger.debug("orderHeaderTblPk : " +orderHeaderTblPk);
      logger.debug("paymentMode : " +paymentMode);
      orderStatus=OrderStatus.REGISTERED.KEY;
      sqlString="{?=call sp_upd_order_to_complete(?,?,?,?,?)}";
      dBConnection=new DBConnection(dataSource);
      cs=dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,orderHeaderTblPk);
      cs.setInt(3,paymentMode);
      cs.setInt(4,orderStatus);
      cs.setInt(5,outletTblPk);
      cs.setInt(6,inetBankingTblPk);
      dBConnection.executeCallableStatement(cs);
      returnValue=cs.getInt(1);
      logger.debug("Return Value :"+returnValue);
    }catch(SQLException e){
      logger.error(e);   
      e.printStackTrace();
      throw e;
    }catch(Exception e){
      logger.error(e);   
      e.printStackTrace();
      throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting completeOrder()");
    }
  }
}