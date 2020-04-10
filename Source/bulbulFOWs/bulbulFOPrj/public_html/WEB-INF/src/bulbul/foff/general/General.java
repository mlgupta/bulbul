package bulbul.foff.general;
/**
 *	Purpose: To calculate the number of pages that will be formed depending upon the number
 *           of records per page.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import bulbul.foff.common.ContentType;
import bulbul.foff.general.FOConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.lang.Runtime;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletResponse;

import org.apache.batik.svggen.font.SVGFont;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
public final class General  {

private static  Logger logger=Logger.getLogger(FOConstants.LOGGER.toString());

/**
 *	Purpose: Returns the value of pageCount of type integer.
 *  @param rs - A ResultSet Object.
 *  @param numberOfRecords - An Integer Object.
 *  @return integer
 */
public static int getPageCount(ResultSet rs, int numberOfRecords)throws SQLException{

    int recordCount;
    int pageCount=1;
    try{
      rs.last();
      recordCount=rs.getRow();
      if (recordCount!=0){
        pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
      }
      rs.beforeFirst(); 
    }catch(SQLException se){
      logger.error("***Exception in getPageCount() method"+se.getMessage());
      throw se;   
    }
    return pageCount;
}

/**
 *	Purpose: Returns the true or false for given file/folder to delete.
 *  @param file - A File Object.
 *  @return boolean
 */
public static boolean recurrsiveFolderDelete(File file) throws IOException, Exception{
    boolean deleted=true;
    try{
      File[] listOfFiles = file.listFiles();
      String fileName=null;
      for(int fileIndex=0; fileIndex<listOfFiles.length; fileIndex++){
       if (listOfFiles[fileIndex].listFiles()!=null){
        deleted=recurrsiveFolderDelete(listOfFiles[fileIndex]);
       }else{
          fileName=listOfFiles[fileIndex].getName();
          if(!listOfFiles[fileIndex].delete()){
            logger.debug(" Unable To Delete the File/Folder : " + fileName);
          }else{
            logger.debug(" Deleted the File/Folder : " + fileName + " Successfully");
          }
        }
      }
      if (deleted){
        fileName=file.getName();
        if(!(deleted=file.delete())){
          logger.debug(" Unable To Delete the File/Folder : " + fileName);
        }else{
          logger.debug(" Deleted the File/Folder : " + fileName + " Successfully");
        }
      }
    }catch(IOException e){
      logger.error(e);
      throw e;
    }catch(Exception e){
      logger.error(e);    
      throw e;
    }
    return deleted;
  }

/**
 *	Purpose: Gets the InStream  from fleImageFile a FormFile and  OutStreams to the response a Response Object
 *  @param fleImageFile - A FormFile Object.
 *  @param response - A HttpServletResponse Object
 */
  public static void imagePreview(FormFile fleImageFile, HttpServletResponse response) throws IOException, Exception{ 
    OutputStream os=null;
    InputStream is = null;
    try{
      logger.info("Entering imagePreview");

      int contentSize=fleImageFile.getFileData().length; 
      String contentType=fleImageFile.getContentType();
      byte[] content= new byte[contentSize]; 

      logger.debug("Initially Content Type : " + contentType);
      logger.debug("Initially Content Size : " + contentSize);
      long m1 = Runtime.getRuntime().totalMemory();
      long f1 = Runtime.getRuntime().freeMemory();
      
      is = fleImageFile.getInputStream(); 
      is.read(content,0,contentSize); 

      if (contentType.indexOf((ContentType.SVG.toString()))>-1){
        logger.debug("SVG Content Converting into JPG" );        
        content=svg2Jpg(content);
        logger.debug("SVG Content Converted to JPG" );        
        
        contentType=ContentType.JPG.toString();
        contentSize=content.length;
      }

      logger.debug("Finally Content Type : " + contentType);
      logger.debug("Finally Content Size : " + contentSize);

      response.setContentLength(contentSize); 
      response.setContentType(contentType); 
      
      os = response.getOutputStream(); 
      os.write(content,0,contentSize); 
      long m2 = Runtime.getRuntime().totalMemory();  
      long f2 = Runtime.getRuntime().freeMemory();

      logger.debug("M1 : " + m1/(1024*1024) + "  M2 : " + m2/(1024*1024) + " M2- M1: " + (m2-m1)/(1024*1024) );
      logger.debug("F1 : " + f1/(1024*1024) + "  F2 : " + f2/(1024*1024) + " F2- F1: " + (f2-f1)/(1024*1024) );
      
      response.flushBuffer(); 
    }catch(IOException ioe ){
      logger.error(ioe); 
      throw ioe;
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      if (is!=null){
        logger.debug("Closing Input Stream" );
        is.close(); 
        logger.debug("Input Stream Closed" );        
      }
      if (os!=null){
        logger.debug("Closing and Flushing Output Stream" );
        os.flush();
        logger.debug("Output Stream Flushed" );        
        os.close(); 
        logger.debug("Output Stream Closed" );        
      }
      
      logger.debug("At Last Total Memory: " + Runtime.getRuntime().totalMemory()/(1024*1024));
      
      logger.debug("At Last Free Memory: " + Runtime.getRuntime().freeMemory()/(1024*1024));
      
      
      logger.info("Exiting imagePreview");
    }
  }

/**
 *	Purpose: OutStreams the byte array into the response a Response Object
 *  @param content - An Array of byte 
 *  @param contentType - A String Object Defined by content type
 *  @param contentSize - An int data type Defined by content size
 *  @param response - A HttpServletResponse Object
 */
  public static void imageDisplay(byte[] content,String contentType, int contentSize, HttpServletResponse response) throws IOException, Exception{ 
    OutputStream os = null;
    try{
      logger.info("Entering imageDisplay");

      logger.debug("Initially Content Type : " + contentType);
      logger.debug("Initially Content Size : " + contentSize);

      if (contentType.indexOf((ContentType.SVG.toString()))>-1){
        logger.debug("SVG Content Converting into JPG" );        
        content=svg2Jpg(content);
        logger.debug("SVG Content Converted to JPG" );        
        
        contentType=ContentType.JPG.toString();
        contentSize=content.length;
      }

      logger.debug("Finally Content Type : " + contentType);
      logger.debug("Finally Content Size : " + contentSize);
      
      response.setContentLength(contentSize); 
      response.setContentType(contentType); 


      os = response.getOutputStream(); 
      os.write(content,0,contentSize); 
      
      response.flushBuffer(); 
      
    }catch(IOException ioe ){
      logger.error(ioe); 
      throw ioe;
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      if (os!=null){
        logger.debug("Closing and Flushing Output Stream" );
        os.flush();
        logger.debug("Output Stream Flushed" );        
        os.close(); 
        logger.debug("Output Stream Closed" );              
      }
      logger.info("Exiting imageDisplay");
    }
  }
  
  /**
   *	Purpose: Converts wmf byte array into an svg byte array
   *  @param wmfContent - An Array of byte defined by wmf content
   *  @return a byte array defined by svg conent
   */
  public static byte[] wmf2Svg(byte[] wmfContent) throws TranscoderException,Exception {
    ByteArrayInputStream bais=null;
    ByteArrayOutputStream baos=null;

    TranscoderInput ti=null; 
    TranscoderOutput to=null; 
    
    WMFTranscoder transcoder =null ;

    byte[] svgContent=null;
    
    try{
    
      logger.info("Entering wmf2Svg ");
      
      bais=new ByteArrayInputStream(wmfContent);
      baos=new ByteArrayOutputStream();  
           
      ti = new TranscoderInput(bais); 
      to = new TranscoderOutput(new OutputStreamWriter(baos,"UTF8")); 

      transcoder = new WMFTranscoder();
      transcoder.transcode(ti,to);

      svgContent=baos.toByteArray();
      logger.debug(" SVG Content Size " + svgContent.length);

    }catch(TranscoderException te ){
      logger.error(te); 
      throw te;
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      if(bais!=null){
        logger.debug("Closing Byte Array Input Stream" );
        bais.close();
        logger.debug("Closing Byte Array Input Stream Closed" );        
      }
      if(baos!=null){
        logger.debug("Closing and Flushing Byte Array Output Stream" );
        baos.close();
        logger.debug("Byte Array Output Stream Flushed" );        
        baos.flush(); 
        logger.debug("Byte Array Output Stream Closed" );  
      }
      logger.info("Exiting wmf2Svg ");
    }
    return svgContent;
  }  
  /**
   *	Purpose: Converts svg byte array into an jpg byte array
   *  @param svgContent - An Array of byte defined by svg content
   *  @return a byte array defined by jpg conent
   */
  public static byte[] svg2Jpg(byte[] svgContent) throws TranscoderException,Exception {
    ByteArrayInputStream bais=null;
    ByteArrayOutputStream baos=null;

    TranscoderInput ti=null; 
    TranscoderOutput to=null; 
    
    JPEGTranscoder transcoder =null ;

    byte[] jpgContent=null;
    
    try{

      logger.info("Entering svg2Jpg");
        
      bais=new ByteArrayInputStream(svgContent);
      baos=new ByteArrayOutputStream();  

      ti = new TranscoderInput(bais); 
      to = new TranscoderOutput(baos); 

      transcoder = new JPEGTranscoder();
      //transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.8)); 
      transcoder.transcode(ti,to);

      logger.debug(" JPG Content Size " + baos.size());
      jpgContent=baos.toByteArray();

      
      
    }catch(TranscoderException te ){
      logger.error(te); 
      throw te;
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      if(bais!=null){
        logger.debug("Closing Byte Array Input Stream" );
        bais.close();
        logger.debug("Closing Byte Array Input Stream Closed" );        
      }
      if(baos!=null){
        logger.debug("Closing and Flushing Byte Array Output Stream" );
        baos.close();
        logger.debug("Byte Array Output Stream Flushed" );        
        baos.flush(); 
        logger.debug("Byte Array Output Stream Closed" );  
      }      
      logger.info("Exiting svg2Jpg");
    }
    return jpgContent;
  }
  /**
   *	Purpose: Converts svg byte array into an png byte array
   *  @param svgContent - An Array of byte defined by svg content
   *  @return a byte array defined by png conent
   */
  public static byte[] svg2Png(byte[] svgContent) throws TranscoderException,Exception {
    ByteArrayInputStream bais=null;
    ByteArrayOutputStream baos=null;

    TranscoderInput ti=null; 
    TranscoderOutput to=null; 
    
    PNGTranscoder transcoder =null ;

    byte[] pngContent=null;

    try{

      logger.info("Entering svg2Png");
      
      bais=new ByteArrayInputStream(svgContent);
      baos=new ByteArrayOutputStream();  
      ti = new TranscoderInput(bais); 
      to = new TranscoderOutput(baos); 

      transcoder = new PNGTranscoder();
      transcoder.transcode(ti,to);
      
      pngContent=baos.toByteArray();

    }catch(TranscoderException te ){
      logger.error(te); 
      throw te;
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      if(bais!=null){
        logger.debug("Closing Byte Array Input Stream" );
        bais.close();
        logger.debug("Closing Byte Array Input Stream Closed" );        
      }
      if(baos!=null){
        logger.debug("Closing and Flushing Byte Array Output Stream" );
        baos.close();
        logger.debug("Byte Array Output Stream Flushed" );        
        baos.flush(); 
        logger.debug("Byte Array Output Stream Closed" );  
      }
      logger.info("Exiting svg2Png"); 
    }
    return pngContent;
  }

  /**
   *	Purpose: Converts wmf byte array into an svg byte array
   *  @param wmfContent - An Array of byte defined by wmf content
   *  @return a byte array defined by svg conent
   */
  public static String ttf2Svg(String fontPhysicalPath) throws Exception {
    String forceAscii= "-ascii";
    String outputArg = "-o";
    String testCard = "-testcard";
    String[] args=null;
    try{
      logger.info("Entering ttf2Svg ");
      args = new String[5];
      logger.debug("Font Path : " + fontPhysicalPath);
      args[0]=fontPhysicalPath;
      args[1]=forceAscii;
      args[2]=outputArg;
      fontPhysicalPath=fontPhysicalPath.replaceAll("ttf","svg");
      args[3]=fontPhysicalPath;
      args[4]=testCard;
      logger.debug("Font Path :" + fontPhysicalPath);
      SVGFont.main(args); 
    }catch(Exception e ){
      logger.error(e); 
      throw e;
    }finally{
      logger.info("Exiting ttf2Svg ");
    }
    return fontPhysicalPath;
  } 
  
  public static void postMail( String recipients, String subject, String message , String from,String smtphost) throws MessagingException  {
    boolean debug;
    
    try{    
      logger.info("Entering postMail() method");
      debug = false;
      
       //Set the host smtp address
       Properties props = new Properties();
       props.put("mail.smtp.host", smtphost);
      
      // create some properties and get the default Session
      Session session = Session.getDefaultInstance(props, null);
      session.setDebug(debug);
      
      // create a message
      Message msg = new MimeMessage(session);
      
      // set the from and to address
      InternetAddress addressFrom = new InternetAddress(from);
      msg.setFrom(addressFrom);
      
      InternetAddress addressTo = new InternetAddress(recipients); 
      
      msg.setRecipient(Message.RecipientType.TO, addressTo);
      
      
      // Optional : You can also set your custom headers in the Email if you Want
      msg.addHeader("MyHeaderName", "myHeaderValue");
      
      // Setting the Subject and Content Type
      msg.setSubject(subject);
      msg.setContent(message, "text/html");
      Transport.send(msg);
    }catch(MessagingException e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }catch(Exception e){
      logger.error(e.getMessage());
      e.printStackTrace();
    }finally{
      logger.info("Exiting postMail() method");
    }
  }
  
  public static ArrayList getYears4CCOrDC()throws Exception{
    Date currentDate =null;
    Calendar calendar = null;
    int currentYear=0;
    final int yearCount=50;
    ArrayList years=null;
    
    try{
      currentDate = new Date();
      calendar = new GregorianCalendar();
      calendar.setTime(currentDate); 
      currentYear=calendar.get(Calendar.YEAR);
      logger.debug("Current Year : " + currentYear);
      years= new ArrayList();
      for(int yearCounter=0; yearCounter<=yearCount;yearCounter++){
        years.add(Integer.toString(currentYear++));
        
      }
      logger.debug("Final Year : " + currentYear);
    }catch(Exception e){
      logger.error(e);
      throw e;
    }
    return years;
  }
}
