package bulbul.boff.general;

import java.sql.ResultSet;
import java.sql.Types;

import java.util.Map;


public class PgSQLArray implements java.sql.Array {
  private String arrayString="{}";
  private int baseType=0;
  private String baseTypeName=getBaseTypeName(baseType); 
  private static final String jdbc2TypeName[] = {
          "int2",
          "int4", 
          "int8",
          "numeric",
          "float4",
          "float8",
          "char", 
          "varchar",
          "bytea",
          "bool",
          "date",
          "time",
          "timestamp"
      };
      
    
    private static final int jdbc2Type[] = {
          Types.SMALLINT,
          Types.INTEGER, 
          Types.BIGINT,
          Types.NUMERIC,
          Types.REAL,
          Types.DOUBLE,
          Types.CHAR, 
          Types.VARCHAR,
          Types.BINARY,
          Types.BIT,
          Types.DATE,
          Types.TIME,
          Types.TIMESTAMP
      };
      
      
      
  public PgSQLArray(String arrayString, int type) {
    this.arrayString=arrayString;
    this.baseType=type;
    this.baseTypeName=getBaseTypeName(type);
  }
  
  private String getBaseTypeName(int type){
    String typeName="int2";
    for (int i=0; i<jdbc2Type.length; i++){
      if(jdbc2Type[i]==type){
        typeName=jdbc2TypeName[i];
      }
    }
    return typeName;
  }

  public String getBaseTypeName() {return baseTypeName;}

  public int getBaseType() {return baseType;}

  public Object getArray() {return null;}

  public Object getArray(Map map) {return null;}

  public Object getArray(long index, int count) {return null;}

  public Object getArray(long index, int count, Map map) {return null;}

  public ResultSet getResultSet() {return null;}

  public ResultSet getResultSet(Map map)  {return null;}
  
  public ResultSet getResultSet(long index, int count) {return  null;}
  
  public ResultSet getResultSet(long index, int count,  Map map) {return null;}

  public String toString() {
    return arrayString;
  } 
  
}
