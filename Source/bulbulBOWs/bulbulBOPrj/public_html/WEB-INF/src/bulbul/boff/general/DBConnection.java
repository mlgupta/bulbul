package bulbul.boff.general;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class DBConnection  {
  private DataSource dataSource=null;
  private Connection connection=null;
  private Statement statement=null;
  private Logger logger = null;
  public DBConnection(DataSource dataSource) throws SQLException , Exception {
    logger=Logger.getLogger(BOConstants.LOGGER.toString());
    logger.info("Inside DBConnection Contructor");
    try{
      this.dataSource=dataSource;
      this.connection = this.dataSource.getConnection(); 
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting DBConnection Contructor");
    }
  }
  public ResultSet executeQuery(String sqlString)throws SQLException, Exception{
    ResultSet resultSet=null; 
    try{
      logger.info("Entering executeQuery");
      this.statement = this.connection.createStatement();
      resultSet=this.statement.executeQuery(sqlString);
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting executeQuery");
    }
    return resultSet;
  }

    public ResultSet executeQuery(String sqlString,int resultSetType,int resultSetConcurrency)throws SQLException, Exception{
    ResultSet resultSet=null; 
    try{
      logger.info("Entering executeQuery");
      this.statement = this.connection.createStatement(resultSetType,resultSetConcurrency);      
      resultSet=this.statement.executeQuery(sqlString);
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting executeQuery");
    }
    return resultSet;
  }

  public ResultSet executeQuery(String sqlString,int resultSetType,int resultSetConcurrency, int resultSetHoldability)throws SQLException, Exception{
    ResultSet resultSet=null; 
    try{
      logger.info("Entering executeQuery");
      this.statement = this.connection.createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);      
      resultSet=this.statement.executeQuery(sqlString);
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting executeQuery");
    }
    return resultSet;
  }


  public CallableStatement prepareCall(String sqlString)throws SQLException, Exception{
    CallableStatement callableStatement=null;
    try{
      logger.info("Entering prepareCall ");
        callableStatement = this.connection.prepareCall(sqlString);
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting prepareCall");
    }
    return callableStatement;
  }

  public CallableStatement prepareCall(String sqlString,int resultSetType,int resultSetConcurrency)throws SQLException, Exception{
    CallableStatement callableStatement=null;
    try{
      logger.info("Entering prepareCall ");
        callableStatement = this.connection.prepareCall(sqlString, resultSetType,resultSetConcurrency);      
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting prepareCall");
    }
    return callableStatement;
  }

  public CallableStatement prepareCall(String sqlString,int resultSetType,int resultSetConcurrency, int resultSetHoldability)throws SQLException, Exception{
    CallableStatement callableStatement=null;
    try{
      logger.info("Entering prepareCall ");
      callableStatement = this.connection.prepareCall(sqlString, resultSetType,resultSetConcurrency, resultSetHoldability);      
    }catch(SQLException se ){
      throw se;
    }catch(Exception e ){
      throw e;
    }finally{
      logger.info("Exiting prepareCall");
    }
    return callableStatement;
  }

  public boolean executeCallableStatement(CallableStatement callableStatement) throws SQLException{
    try{
      logger.info("Entering executeCallableStatement");
      return callableStatement.execute();
    }catch(SQLException sqle){
      logger.error(sqle);
      throw sqle;
    }finally{
      logger.info("Exiting executeCallableStatement");
    }
  }


  public void freeCallableSatement(CallableStatement callableStatement){
    // Closing Callable Statement 
    if (callableStatement !=null){
      try{
        callableStatement.close(); 
      }catch(SQLException sqle){
        logger.error(sqle.getMessage());
      }finally{
        callableStatement=null;
      }
    }
  }

  public void free(CallableStatement callableStatement){
    // Closing Callable Statement 
    freeCallableSatement(callableStatement); 

    // Closing Statement and Connection
    free();
  }

  
  public void freeResultSet(ResultSet resultSet){
    //Closing ResultSet 
    if (resultSet != null) {
      try{
        resultSet.close();
      }
      catch(SQLException sqle){
        logger.error(sqle.getMessage());
      }finally{
        resultSet =  null;
      }
    }

  }
  
  public void free(ResultSet resultSet){
    //Closing ResultSet 
    freeResultSet(resultSet);

    // Closing Statement and Connection
    free();
  }

  public void free(){
  
    // Closing Statement
    if (this.statement != null) {
      try{
        this.statement.close();
      }
      catch(SQLException sqle){
        logger.error(sqle.getMessage());
      }finally{
        this.statement =  null;
      }
    }

    //Closing Connection
    if (this.connection != null) {
      try{
        this.connection.close();
      }
      catch(SQLException sqle){
        logger.error(sqle.getMessage());
      }finally{
        this.connection =  null;
      }
    }
  }
}