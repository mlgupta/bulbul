package bulbul.boff.general;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;


public final class LOOperations  {
  static Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  public static byte[] getLargeObjectContent(int oId, DataSource dataSource) throws ClassNotFoundException,SQLException, IOException,Exception   {

    Connection conn=null;
    InputStream is= null;
    BasicDataSource basicDataSource=null;
    LargeObjectManager lObjManager = null;
    LargeObject lObj = null;
    byte[] content=null;
    int contentSize ;

    try{
      basicDataSource=(BasicDataSource)dataSource;
      Class.forName(basicDataSource.getDriverClassName());
      conn=DriverManager.getConnection(basicDataSource.getUrl(),basicDataSource.getUsername(),basicDataSource.getPassword());
 
      conn.setAutoCommit(false);
      lObjManager = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
      lObj = lObjManager.open(oId,LargeObjectManager.READ);
      is = lObj.getInputStream();
      contentSize = lObj.size();
      content= new byte[contentSize]; 
      is.read(content); 
    }catch(ClassNotFoundException cnfe){
      logger.error(cnfe);
      throw cnfe;
    }catch(SQLException sqle){
      logger.error(sqle);
      throw sqle;
    }catch(IOException ioe){
      logger.error(ioe);
      throw ioe;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if (is!=null){
        is.close(); 
      }
      if (lObj!=null){
        lObj.close();
      }
      if (conn!=null){
        conn.close();   
      }
    }
    return content;
  }

  public static void putLargeObjectContent(int oId,byte[] content,DataSource dataSource)throws ClassNotFoundException,SQLException, Exception {

    Connection conn=null;
    BasicDataSource basicDataSource=null;
    LargeObjectManager lObjManager = null;
    LargeObject lObj = null;
    try{
      basicDataSource=(BasicDataSource)dataSource;
      Class.forName(basicDataSource.getDriverClassName());
      conn=DriverManager.getConnection(basicDataSource.getUrl(),basicDataSource.getUsername(),basicDataSource.getPassword());
      conn.setAutoCommit(false);
      lObjManager = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
      lObj = lObjManager.open(oId,LargeObjectManager.WRITE);
      lObj.write(content);
    }catch(ClassNotFoundException cnfe){
      logger.error(cnfe);
      throw cnfe;
    }catch(SQLException sqle){
      logger.error(sqle);
      throw sqle;
    }catch(Exception e){
      logger.error(e);
      throw e;
    }finally{
      if (lObj!=null){
        lObj.close();
      }
      conn.commit(); 
      if (conn!=null){
        conn.close();   
      }
    }
  }


  public static int getLargeObjectId(DataSource dataSource)throws ClassNotFoundException,SQLException,Exception {

    BasicDataSource basicDataSource=null;
    LargeObjectManager lObjManager=null;
    Connection conn=null;   
    int oId=-1;
    try{
      basicDataSource=(BasicDataSource)dataSource;
      logger.debug("URI: "+basicDataSource.getUrl());
      logger.debug("DB User Name: "+basicDataSource.getUsername());
    
      Class.forName(basicDataSource.getDriverClassName());    
      conn=DriverManager.getConnection(basicDataSource.getUrl(),basicDataSource.getUsername(),basicDataSource.getPassword());
       
      conn.setAutoCommit(false);
      lObjManager = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();      
      oId = lObjManager.create(LargeObjectManager.READ | LargeObjectManager.WRITE);
      conn.commit();
    }catch(ClassNotFoundException cnfe ){
      logger.error(cnfe);
      throw cnfe;
    }catch(SQLException sqle ){
      logger.error(sqle);
      throw sqle;
    }catch(Exception e ){
      logger.error(e);
      throw e;
    }finally{
      if(conn!=null){
        conn.close();
      }
    }     
    return oId;
  }
}