package bulbul.boff.ca.font.beans;

import bulbul.boff.ca.font.actionforms.FontCategoryForm;
import bulbul.boff.ca.font.actionforms.FontForm;
import bulbul.boff.ca.font.actionforms.FontListForm;
import bulbul.boff.general.BOConstants;
import bulbul.boff.general.DBConnection;

import bulbul.boff.general.LOOperations;

import java.io.InputStream;

import java.lang.Integer;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;


/**
 *	Purpose: To Provide The Methods Used By The Actions.
 *  Info: This Class Contain The Methods Used By The Action Class To List,Add,Edit,Delete,Activte/Deactivate 
 *        And View The Fonts And Categories.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class

 Operations  {
  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());

  /**
   * Purpose : To Populate font_list.jsp with fonts and categories from database depending upon the data from 
   *           the search field in font_list.jsp.
   * @return ArrayList 
   * @param fontCategoryTblPk - Integer.
   * @param fontListForm - A FontListForm object.
   * @param dataSource - A DataSource object.
   */  
  public static ArrayList getFonts(int fontCategoryTblPk,FontListForm fontListForm,DataSource dataSource,int numberOfRecords)throws SQLException, Exception {
    ArrayList fonts=new ArrayList();
    FontListBean font=null;
    DBConnection  dBConnection=null;
    ResultSet rsCategory=null;
    ResultSet rsFont=null;
    String queryForCategory;
    String queryForFont;

    int pageNumber=1;
    int categoryRecordCount=0;
    int fontRecordCount=0;
    int recordCount=0;

    int pageCount=1;
    int startIndex=1;
    int endIndex=1;

    try{
      logger.info("Entering getFonts");
      if ((fontListForm.getHdnSearchPageNo()!=null) && (fontListForm.getHdnSearchPageNo().trim().length()>0)){
        pageNumber=Integer.parseInt(fontListForm.getHdnSearchPageNo());
      }  
      
      //Query For Font Category
      queryForCategory="select * from font_category_tbl where 1=1 and font_category_tbl_fk ";

      if (fontCategoryTblPk==-1){
        queryForCategory=queryForCategory+"is null ";
      }else{
        queryForCategory=queryForCategory+"="+fontCategoryTblPk;
      }
    
      if(fontListForm.getRadSearchFontOrCategory() !=null && fontListForm.getRadSearchFontOrCategory().equals("categorySelect")){
        if( fontListForm.getTxtSearchFontCategory() != null && (fontListForm.getTxtSearchFontCategory().trim()).length() != 0 ){
          queryForCategory = queryForCategory + " and font_category like '%"+fontListForm.getTxtSearchFontCategory()+"%'";
        }
      }
      if(fontListForm.getRadSearchStatus() != null && (fontListForm.getRadSearchStatus().trim()).length() != 0 && !fontListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForCategory = queryForCategory + " and is_active="+fontListForm.getRadSearchStatus();
      }

      queryForCategory=queryForCategory + " order by font_category";

      
      //Query For Font
      queryForFont="select * from font_tbl where font_category_tbl_pk="+fontCategoryTblPk;

      if(fontListForm.getRadSearchFontOrCategory() !=null && fontListForm.getRadSearchFontOrCategory().equals("fontSelect")){
        if( fontListForm.getTxtSearchFontName() != null && (fontListForm.getTxtSearchFontName().trim()).length() != 0 ){
          queryForFont = queryForFont + " and font_name like '%"+fontListForm.getTxtSearchFontName()+"%'";
        }
      }
      if(fontListForm.getRadSearchStatus() != null && (fontListForm.getRadSearchStatus().trim()).length() != 0 && !fontListForm.getRadSearchStatus().equals( BOConstants.BOTH_VAL.toString()) ){
        queryForFont = queryForFont + " and is_active="+fontListForm.getRadSearchStatus();
      }

      queryForFont=queryForFont+" order by font_name ";

      dBConnection=new  DBConnection(dataSource);  
      if(fontListForm.getRadSearchFontOrCategory()!=null && fontListForm.getRadSearchFontOrCategory().equals("categorySelect") && (fontListForm.getTxtSearchFontCategory() != null && (fontListForm.getTxtSearchFontCategory().trim()).length() != 0)){
        rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  
        logger.debug("Only queryForCategory executed: "+queryForCategory);
      }else if(fontListForm.getRadSearchFontOrCategory()!=null && fontListForm.getRadSearchFontOrCategory().equals("fontSelect") && (fontListForm.getTxtSearchFontName() != null && (fontListForm.getTxtSearchFontName().trim()).length() != 0)){
        rsFont=dBConnection.executeQuery(queryForFont,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        logger.debug("Only queryForFont executed: "+queryForFont);
      }else{
        rsCategory=dBConnection.executeQuery(queryForCategory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        rsFont=dBConnection.executeQuery(queryForFont,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        logger.debug("Both query executed: ");
        logger.debug("queryForCategory: "+queryForCategory);
        logger.debug("queryForFont: "+queryForFont);
      }
    
      if (rsCategory!=null){
        rsCategory.last();
        categoryRecordCount=rsCategory.getRow();
        rsCategory.beforeFirst();
      }
    
      if(rsFont!=null){
        rsFont.last();
        fontRecordCount=rsFont.getRow();
        rsFont.beforeFirst();
      }

      recordCount=categoryRecordCount+fontRecordCount;
      if(recordCount!=0){
        pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }

      if (pageNumber>pageCount) {
        pageNumber=pageCount;
      }
    
      startIndex=(pageNumber*numberOfRecords) - numberOfRecords;
      endIndex=(startIndex + numberOfRecords)-1;
    
      logger.debug("Category count : " + categoryRecordCount );
      logger.debug("Font count : " + fontRecordCount );
      logger.debug("Before Category");
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);

      //Filling Category Records
      if (rsCategory!=null){
        if (startIndex<=categoryRecordCount){
          if (startIndex>0){
            rsCategory.relative(startIndex);
          }

          while(rsCategory.next()){
            font=new FontListBean();
            font.setFontOrCategoryTblPk(rsCategory.getString("font_category_tbl_pk"));
            font.setFontOrCategoryName(rsCategory.getString("font_category"));
            font.setFontOrCategoryDesc(rsCategory.getString("font_category_description"));
            font.setFontOrCategoryType(BOConstants.FONTCATEGORY.toString());
            font.setIsActive(rsCategory.getString("is_active"));
            font.setIsActiveDisplay(rsCategory.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
            fonts.add(font);
            startIndex++;
            logger.debug("startIndex: "+startIndex);
            if (startIndex>endIndex){
              break;
            }
          }
        }
      }

      logger.debug("After Category");
      logger.debug("Start Index : " + startIndex);
      logger.debug("End Index : " + endIndex);
      logger.debug("categoryRecordCount :"+categoryRecordCount);

      //Filling Font Records
      if(rsFont !=null){
        if (startIndex<=endIndex){
          if (startIndex>=categoryRecordCount){

            if(startIndex-categoryRecordCount>0){
              rsFont.relative(startIndex-categoryRecordCount);
            }
          
            while(rsFont.next()){
              font=new FontListBean();
              font.setFontOrCategoryTblPk(rsFont.getString("font_tbl_pk"));
              font.setFontOrCategoryName(rsFont.getString("font_name"));
              font.setFontOrCategoryDesc(rsFont.getString("font_description"));
              font.setFontOrCategoryType(BOConstants.FONT.toString());
              font.setIsActive(rsFont.getString("is_active"));
              font.setIsActiveDisplay(rsFont.getString("is_active").equals(BOConstants.ACTIVE_VAL.toString())?BOConstants.ACTIVE.toString():BOConstants.INACTIVE.toString());
              fonts.add(font);
              startIndex++;
              logger.debug("startIndex: "+startIndex);
              if (startIndex>endIndex){
                break;
              }
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
      if (dBConnection!=null){
        dBConnection.freeResultSet(rsCategory);
        dBConnection.freeResultSet(rsFont);
        dBConnection.free();        
      }
      dBConnection=null;
      fontListForm.setHdnSearchPageNo(Integer.toString(pageNumber) ) ;
      fontListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      fontListForm.setHdnSearchFontOrCategoryTblPk(Integer.toString(fontCategoryTblPk));
      logger.info("Exiting getFonts");
    }
    return fonts;
  } 

  /**
   * Purpose : To save font_add.jsp with the specified group data.
   * @param fontForm - A FontForm object.
   * @param dataSource - A DataSource object.
   */ 
  public static void addFont(FontForm fontForm,DataSource dataSource)throws SQLException,Exception{
    String sqlString=null;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    int returnFontTblPk;
    ResultSet rs = null;
//    FormFile imgFile=null;
//    String contentType=null;
//    byte[] content = null;
//    int contentSize;
    int oid=0;
//    InputStream is = null;

    try{
      logger.info("Entering addFont");
      dBConnection =  new DBConnection(dataSource);
      String query="select font_name from font_tbl where font_name = '"+fontForm.getTxtFont()+"' and font_category_tbl_pk= "+fontForm.getHdnFontCategoryTblPk();
      logger.debug("query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
//      imgFile=fontForm.getImgFile();
//      contentType=imgFile.getContentType();
//      contentSize=imgFile.getFileData().length;
//      
//      logger.debug("image file name: "+imgFile.getFileName());
//      logger.debug("image file size: "+imgFile.getFileSize());
//      
//      oid=LOOperations.getLargeObjectId(dataSource);
//
//      content=new byte[contentSize];
//      is=imgFile.getInputStream();
//      is.read(content);
      
//      LOOperations.putLargeObjectContent(oid,content,dataSource);
      sqlString = "{?=call sp_ins_font_tbl(?,?,?,?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontForm.getHdnFontCategoryTblPk());
      cs.setString(3,fontForm.getTxtFont().trim());
      cs.setString(4,fontForm.getTxaFontDesc());
      cs.setInt(5,oid);
      cs.setString(6,null);
      cs.setInt(7,0);
      
      dBConnection.executeCallableStatement(cs);
      returnFontTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnFontTblPk);
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
      logger.info("Entering addFont");
    }
  }

  /**
   * Purpose : To save font_category_add.jsp with the specified group data.
   * @param fontCategoryForm - A FontCategoryForm object.
   * @param dataSource - A DataSource object.
   */ 
  public static int addFontCategory(FontCategoryForm fontCategoryForm,DataSource dataSource)throws SQLException,Exception{
    String sqlString;
    DBConnection dBConnection = null;
    CallableStatement cs=null;
    int returnFontCategoryTblPk=-1;
    try{
      logger.info("Entering addFontCategory");
      dBConnection =  new DBConnection(dataSource);
      sqlString = "{?=call sp_ins_font_category_tbl(?,?,?)}";
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontCategoryForm.getHdnFontCategoryTblFk());
      cs.setString(3,fontCategoryForm.getTxtFontCategory().trim());
      cs.setString(4,fontCategoryForm.getTxaFontCategoryDesc());
      dBConnection.executeCallableStatement(cs);
      returnFontCategoryTblPk=cs.getInt(1);
      logger.debug("Returned PK is:" + returnFontCategoryTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting addFontCategory");
    }
    return returnFontCategoryTblPk;
  }

  /**
   * Purpose : To populate font_view.jsp and font_edit.jsp with the font data selected in the 
   *           font_list.jsp
   * @return FontForm          
   * @param fontTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static FontForm  viewFont(String fontTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    FontForm fontForm=null;
    String parentCategory=null;
    try{
      logger.info("Entering viewFont");
      logger.debug("String value of font_tbl_pk is:"+fontTblPk); 
      sqlString="select font_tbl_pk,font_category_tbl_pk,font_name,font_description,font_image,content_type,content_size from font_tbl where font_tbl_pk="+fontTblPk;
      dBConnection= new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        fontForm = new FontForm();
        fontForm.setHdnFontTblPk(rs.getInt("font_tbl_pk"));
        fontForm.setHdnFontCategoryTblPk(rs.getInt("font_category_tbl_pk"));
        fontForm.setTxtFont(rs.getString("font_name"));
        parentCategory=Operations.getFontCategory(fontForm.getHdnFontCategoryTblPk(),dataSource);
        fontForm.setTxtParentCategory(parentCategory);
        fontForm.setTxaFontDesc(rs.getString("font_description"));
//        fontForm.setHdnImgOid(rs.getString("font_image"));
//        fontForm.setHdnContentType(rs.getString("content_type"));
//        fontForm.setHdnContentSize(rs.getInt("content_size"));
      }
      else{
        throw new Exception("font_tbl_pk not found");
      }
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if (dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting viewFont");      
    }
    return fontForm;
  }
  /**
   * Purpose : To populate font_category_view.jsp and font_category_edit.jsp with the font category data selected in the 
   *           font_list.jsp
   * @return FontCategoryForm          
   * @param fontCategoryTblPk - A String object.
   * @param dataSource - A DataSource object.
   */
  public static FontCategoryForm  viewFontCategory(String fontCategoryTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs= null;
    String sqlString=null;
    FontCategoryForm fontCategoryForm=null;
    String parentCategory=null;
    
    try{    
      logger.info("Entering viewFontCategory");
      logger.debug("String value of font_category_tbl_pk is:"+fontCategoryTblPk); 
      sqlString="select font_category_tbl_pk,font_category_tbl_fk,font_category,font_category_description from font_category_tbl where font_category_tbl_pk="+fontCategoryTblPk;
      dBConnection = new DBConnection(dataSource);
      rs=dBConnection.executeQuery(sqlString);
      if (rs.next()){
        fontCategoryForm = new FontCategoryForm();
        fontCategoryForm.setHdnFontCategoryTblPk(rs.getInt("font_category_tbl_pk"));
        fontCategoryForm.setHdnFontCategoryTblFk(rs.getInt("font_category_tbl_fk")==0?-1:rs.getInt("font_category_tbl_fk"));
        fontCategoryForm.setTxtFontCategory(rs.getString("font_category"));
        if(fontCategoryForm.getHdnFontCategoryTblFk() != -1){
          parentCategory=Operations.getFontCategory(fontCategoryForm.getHdnFontCategoryTblFk(),dataSource);  
        }else{
          parentCategory=BOConstants.ALL.toString();
        }
        fontCategoryForm.setTxtParentCategory(parentCategory);
        fontCategoryForm.setTxaFontCategoryDesc(rs.getString("font_category_description"));
      }else{
        throw new Exception("font_tbl_pk not found");
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Entering viewFontCategory");
    }
    return fontCategoryForm;
  }

  /**
   * Purpose : To save font_edit.jsp with the specified group data.
   * @param fontForm - A FontForm object.
   * @param dataSource - A DataSource object.
   */
  public static void editFont(FontForm fontForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString = null;
    ResultSet rs = null;
    int returnFontTblPk;
//    FormFile imgFile=null;
//    InputStream is = null;
//    String contentType=null;
//    int contentSize;
//    byte[] content=null;
//    int oid=0;
    
    try{
      logger.info("Entering editFont");
      sqlString = "{?=call sp_upd_font_tbl(?,?,?,?,?,?)}";
      dBConnection = new DBConnection(dataSource);
      String query="select font_name from font_tbl where font_name = '"+fontForm.getTxtFont()+"' and font_category_tbl_pk= "+fontForm.getHdnFontCategoryTblPk()+" and font_tbl_pk!="+fontForm.getHdnFontTblPk() ;
      logger.debug("***query is: "+query);
      rs=dBConnection.executeQuery(query);
      if(rs.next()){
        throw new SQLException("can not insert duplicate unique key");
      }
//      imgFile=fontForm.getImgFile();
//      oid=Integer.parseInt(fontForm.getHdnImgOid());
//    
//      logger.debug("**oid: "+oid);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontForm.getHdnFontTblPk());
      cs.setInt(3,fontForm.getHdnFontCategoryTblPk());      
      cs.setString(4,fontForm.getTxtFont().trim());
      cs.setString(5,fontForm.getTxaFontDesc());
//      if(imgFile!=null && imgFile.getFileSize()>0){
//        contentType=imgFile.getContentType();
//        contentSize=imgFile.getFileData().length;
//        content=new byte[contentSize];
//        is=imgFile.getInputStream();
//        is.read(content);
//        
//        LOOperations.putLargeObjectContent(oid,content,dataSource);
//        cs.setString(6,contentType);
//        cs.setInt(7,contentSize);
//      }else{
//        cs.setString(6,fontForm.getHdnContentType());
//        cs.setInt(7,fontForm.getHdnContentSize());
//      }
      cs.setString(6,null);
      cs.setInt(7,0);
        
      dBConnection.executeCallableStatement(cs);
      returnFontTblPk=cs.getInt(1);
      logger.debug("updated font_tbl_pk:"+returnFontTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editFont");
    }
  }

  /**
   * Purpose : To save font_category_edit.jsp with the specified group data.
   * @param fontCategoryForm - A FontCategoryForm object.
   * @param dataSource - A DataSource object.
   */
  public static int editFontCategory(FontCategoryForm fontCategoryForm,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection= null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontCategoryTblPk=-1;

    try{
      logger.info("Entering editFontCategory");
      sqlString = "{?=call sp_upd_font_category_tbl(?,?,?,?)}";
      dBConnection = new  DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontCategoryForm.getHdnFontCategoryTblPk());
      cs.setInt(3,fontCategoryForm.getHdnFontCategoryTblFk());      
      cs.setString(4,fontCategoryForm.getTxtFontCategory().trim());
      cs.setString(5,fontCategoryForm.getTxaFontCategoryDesc());
      dBConnection.executeCallableStatement(cs);
      returnFontCategoryTblPk=cs.getInt(1);
      logger.debug("updated font_category_tbl_pk:"+returnFontCategoryTblPk);
    }catch(SQLException e)  {
      logger.error(e);
      throw e;
    }catch(Exception e)  {
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting editFontCategory");
    }
    return returnFontCategoryTblPk;
  }

  /**
   * Purpose : To delete a font selected in font_list.jsp.
   * @param fontTblPk - A String object.
   * @param dataSource - A DataSource object.
   */  
  public static void deleteFont(int fontTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontTblPk;

    try{
      logger.info("Entering deleteFont");
      sqlString = "{? = call sp_del_font_tbl(?)}";
      logger.debug("returnFontTblPk value: "+fontTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnFontTblPk=cs.getInt(1);
      logger.debug("Deleted font_tbl_pk:"+returnFontTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting deleteFont");
    }
  }

  /**
   * Purpose : To delete a font category selected in font_list.jsp.
   * @param fontCategoryTblPk - A String object.
   * @param dataSource - A DataSource object.
   */    
  public static int deleteFontCategory(int fontCategoryTblPk,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontCategoryTblPk=-1;
  
    try{
      logger.info("Entering deleteFontCategory");
      sqlString = "{? = call sp_del_font_category_tbl(?)}";
      logger.debug("returnFontCategoryTblPk value: "+fontCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnFontCategoryTblPk=cs.getInt(1);
      logger.debug("Deleted font_category_tbl_pk:"+returnFontCategoryTblPk);
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
      logger.info("Exiting deleteFontCategory");
    }
    return returnFontCategoryTblPk;
  }

  /**
   * Purpose : To activate or deactivate a font selected in font_list.jsp.
   * @param fontTblPk - A String object.
   * @param fontListForm - A FontListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactFont(int fontTblPk,FontListForm fontListForm,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontTblPk;
    int isActiveFlag;
    
    try{
      logger.info("Entering actDeactFont");
      sqlString = "{? = call sp_act_deact_font_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(fontListForm.getHdnSearchCurrentStatus());
      logger.debug("FontTblPk value: "+fontTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnFontTblPk=cs.getInt(1);
      logger.debug("ActDeact font_tbl_pk:"+returnFontTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting actDeactFont");
    }
  }

  /**
   * Purpose : To activate or deactivate a font category selected in font_list.jsp.
   * @param fontCategoryTblPk - A String object.
   * @param fontListForm - A FontListForm object.
   * @param dataSource - A DataSource object.
   */
  public static void actDeactFontCategory(int fontCategoryTblPk,FontListForm fontListForm,DataSource dataSource) throws SQLException, Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int isActiveFlag;
    int returnFontCategoryTblPk;
    
    try{
      logger.info("Entering actDeactFontCategory");
      sqlString = "{? = call sp_act_deact_font_category_tbl(?,?)}";
      isActiveFlag=Integer.parseInt(fontListForm.getHdnSearchCurrentStatus());
      logger.debug("CategoryTblPk value: "+fontCategoryTblPk );
      dBConnection = new DBConnection(dataSource);
      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,fontCategoryTblPk); 
      cs.setInt(3,isActiveFlag); 
      dBConnection.executeCallableStatement(cs);
      returnFontCategoryTblPk=cs.getInt(1);
      logger.debug("ActDeact font_tbl_pk:"+returnFontCategoryTblPk);
    }catch(SQLException e){
       logger.error(e);
       throw e;
    }catch(Exception e){
       logger.error(e);
       throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      }
      dBConnection=null;
      logger.info("Exiting actDeactFontCategory");
    }
  }

  /**
   * Purpose : To retrieve the Font Name of the selected record in font_list.jsp from the database.
   * @return String.
   * @param fontTblPk - A String object.
   * @param dataSource - A DataSource object.
   */ 
  public static String getFontName(int fontTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String fontName=null;
    try{
      logger.error("Entering getFontName");
      dBConnection=new DBConnection(dataSource);
      sqlString="select font_name from font_tbl where font_tbl_pk="+fontTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       fontName=rs.getString("font_name"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.error("Exiting getFontName");
    }
    return fontName;
  }

  public static String getFontCategory(int fontCategoryTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String fontCategory=null;
    try{
      logger.info("Entering getFontCategory");
      dBConnection=new DBConnection(dataSource);
      sqlString="select font_category from font_category_tbl where font_category_tbl_pk="+fontCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       fontCategory=rs.getString("font_category"); 
      }
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.info("Exiting getFontCategory");
    }
    return fontCategory;
  }

  public static int getFontCategoryFk(int fontCategoryTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    int fontCategoryTblFk=-1;
    try{
      logger.error("Entering getFontCategoryFk");
      dBConnection=new DBConnection(dataSource);
      sqlString="select font_category_tbl_fk from font_category_tbl where font_category_tbl_pk="+fontCategoryTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       fontCategoryTblFk=rs.getInt("font_category_tbl_fk"); 
      }
    }catch(SQLException e){
      logger.error(e); 
      throw e;
    }catch(Exception e){
      logger.error(e); 
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.error("Exiting getFontCategoryFk");
    }
    return fontCategoryTblFk;
  }

  public static String getFontCategoryPk(int fontTblPk,DataSource dataSource)throws SQLException, Exception{
    DBConnection dBConnection=null;
    ResultSet rs=null;
    String sqlString=null;
    String fontCategoryTblPk=null;
    try{
      logger.error("Entering getFontCategoryPk");
      dBConnection=new DBConnection(dataSource);
      sqlString="select font_category_tbl_pk from font_tbl where font_tbl_pk="+fontTblPk;
      rs=dBConnection.executeQuery(sqlString);
      if(rs.next()){
       fontCategoryTblPk=rs.getString("font_category_tbl_Pk"); 
      }
    }catch(SQLException e){
      logger.error(e); 
      throw e;
    }catch(Exception e){
      logger.error(e); 
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(rs);
      }
      dBConnection=null;
      logger.error("Exiting getFontCategoryPk");
    }
    return fontCategoryTblPk;
  }

  public static void moveFontCategory(int srcCategoryTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontCategoryTblPk;
    
    try{
      logger.info("Entering moveFontCategory");
      sqlString = "{? = call sp_mov_font_category_tbl(?,?)}";

      dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcCategoryTblPk); 
      cs.setInt(3,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnFontCategoryTblPk=cs.getInt(1);
      logger.debug("ActDeact font_category_tbl_pk:"+returnFontCategoryTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting moveFontCategory");
    }
  }

  public static void moveFont(int srcFontTblPk,int trgCategoryTblPk,DataSource dataSource) throws Exception{
    DBConnection dBConnection = null;
    CallableStatement cs = null;
    String sqlString = null;
    int returnFontTblPk;
    
    try{
      logger.info("Entering moveFont");
      sqlString = "{? = call sp_mov_font_tbl(?,?)}";

      dBConnection = new DBConnection(dataSource);

      cs = dBConnection.prepareCall(sqlString);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,srcFontTblPk); 
      cs.setInt(3,trgCategoryTblPk); 
      dBConnection.executeCallableStatement(cs);
      returnFontTblPk=cs.getInt(1);
      logger.debug("move returnFontTblPk:"+returnFontTblPk);
    }catch(SQLException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if(dBConnection!=null){
        dBConnection.free(cs);
      } 
      dBConnection=null;
      logger.info("Exiting moveFont");
    }
  }
}