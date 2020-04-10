package bulbul.boff.general;

import bulbul.boff.general.BOConstants;

import java.awt.Dimension;

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

import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.font.SVGFont;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import org.w3c.dom.svg.SVGDocument;


/**
   *	Purpose: To calculate the number of pages that will be formed depending upon the number
   *           of records per page.
   *  @author               Amit Mishra
   *  @version              1.0
   * 	Date of creation:     27-12-2004
   * 	Last Modfied by :     Saurabh Gupta
   * 	Last Modfied Date:    04-Oct-2006
   */
  public final class General  {
  
  private static  Logger logger=Logger.getLogger(BOConstants.LOGGER.toString());
  private static final PNGTranscoder pngTranscoder = new PNGTranscoder();
  private static final ArrayList imageCanvasPool=new ArrayList();
  
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
      
      WMFTranscoder wmfTranscoder =null ;
  
      byte[] svgContent=null;
      
      try{
      
        logger.info("Entering wmf2Svg ");
        
        bais=new ByteArrayInputStream(wmfContent);
        baos=new ByteArrayOutputStream();  
             
        ti = new TranscoderInput(bais); 
        to = new TranscoderOutput(new OutputStreamWriter(baos,"UTF8")); 
  
        wmfTranscoder = new WMFTranscoder();
        wmfTranscoder.transcode(ti,to);
  
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
      
      JPEGTranscoder jpegTranscoder =null ;
  
      byte[] jpgContent=null;
      
      try{
  
        logger.info("Entering svg2Jpg");
          
        bais=new ByteArrayInputStream(svgContent);
        baos=new ByteArrayOutputStream();  
  
        ti = new TranscoderInput(bais); 
        to = new TranscoderOutput(baos); 
  
        jpegTranscoder = new JPEGTranscoder();
        //transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.8)); 
        jpegTranscoder.transcode(ti,to);
  
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
//    public static byte[] svg2Png(byte[] svgContent) throws TranscoderException,Exception {
//      ByteArrayInputStream bais=null;
//      ByteArrayOutputStream baos=null;
//  
//      TranscoderInput ti=null; 
//      TranscoderOutput to=null; 
//      
//      PNGTranscoder transcoder =null ;
//  
//      byte[] pngContent=null;
//  
//      try{
//  
//        logger.info("Entering svg2Png");
//        
//        bais=new ByteArrayInputStream(svgContent);
//        baos=new ByteArrayOutputStream();  
//        ti = new TranscoderInput(bais); 
//        to = new TranscoderOutput(baos); 
//  
//        transcoder = new PNGTranscoder();
//        transcoder.transcode(ti,to);
//        
//        pngContent=baos.toByteArray();
//  
//      }catch(TranscoderException te ){
//        logger.error(te); 
//        throw te;
//      }catch(Exception e ){
//        logger.error(e); 
//        throw e;
//      }finally{
//        if(bais!=null){
//          logger.debug("Closing Byte Array Input Stream" );
//          bais.close();
//          logger.debug("Closing Byte Array Input Stream Closed" );        
//        }
//        if(baos!=null){
//          logger.debug("Closing and Flushing Byte Array Output Stream" );
//          baos.close();
//          logger.debug("Byte Array Output Stream Flushed" );        
//          baos.flush(); 
//          logger.debug("Byte Array Output Stream Closed" );  
//        }
//        logger.info("Exiting svg2Png"); 
//      }
//      return pngContent;
//    }
  
    /**
     *	Purpose: Converts wmf byte array into an svg byte array
    
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
  
    public static void svgPreview(FormFile fleImageFile, HttpServletResponse response) throws IOException, Exception{
      InputStream is = null;
      OutputStream os = null;
      try {
        logger.info("Entering svgPreview method....");
    
        int contentSize=fleImageFile.getFileData().length; 
        byte[] content= new byte[contentSize];
        
        is = fleImageFile.getInputStream(); 
        is.read(content,0,contentSize);
        content = svgToPNG(content,BOConstants.SVG_PREVIEW_SIZE);
        contentSize=content.length;
        
        response.setContentType("image/png");
        
        os = response.getOutputStream(); 
        os.write(content,0,contentSize); 
        
        response.flushBuffer(); 
      }
      catch (IOException ioe) {
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
        if(os!=null){
          os.close();
        }
        logger.info("Exiting svgPreview");
      }
    }
  
    public static byte[] svgToPNG(byte[] svgContent, Dimension imageSize) throws IOException, Exception{
      TranscoderInput ti = null;
      TranscoderOutput to = null;
      SVGImageCanvas imageCanvas= null;
      ByteArrayInputStream bais=null;
      ByteArrayOutputStream baos=null;
      byte[] pngContent=null;
      String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
      try{
        
        bais=new ByteArrayInputStream(svgContent);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        SVGDocument document = (SVGDocument)f.createDocument(svgNS,bais);
      
        for(int arrayIndex=0;arrayIndex<imageCanvasPool.size();arrayIndex++){
          imageCanvas=(SVGImageCanvas)imageCanvasPool.get(arrayIndex);
          if(imageCanvas!=null && imageCanvas.isCanvasFree()){
            break;
          }else{
            imageCanvas=null;
          }
        }      
      
        if(imageCanvas==null){
          imageCanvas= new SVGImageCanvas();
          imageCanvasPool.add(imageCanvas);
          imageCanvas.init();
        }
      
        imageCanvas.resetCanvas();
        imageCanvas.setPreferredSize(imageSize);
        imageCanvas.setDocumentSet(true);
        imageCanvas.setDocument(document);
        
        
            
        while(!imageCanvas.isDocumentResized() && !(imageCanvas.isErrorInResizing())){
          Thread.sleep(500);
        }
        
//              //logger.debug("svgURL : " + svgURL + " - " + imageCanvas.isErrorInResizing() + " - " + imageCanvas.isDocumentResized());
//              //Viewing changed document starts
//              SVGDocument document=imageCanvas.getSVGDocument();
//              if(document!=null){
//                SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
//                Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
//                FileOutputStream fos = null;
//                try {
//                  String fileName=System.getProperty("user.home")+File.separator+new Random().nextInt()+".svg";
//                  System.out.println("fileName " + fileName);
//                  fos=new FileOutputStream(fileName);
//                  
//                  svgGenerator.stream(svgRoot, new OutputStreamWriter(fos,"UTF-8"));
//                }catch (UnsupportedEncodingException ue){
//                  ue.printStackTrace();
//                }catch (SVGGraphics2DIOException se) {
//                  se.printStackTrace();
//                }finally{
//                  if (fos!=null){
//                    try {
//                      fos.flush();
//                      fos.close();
//                    }
//                    catch (Exception e) {
//                     e.printStackTrace();
//                    }
//                  }
//                }
//              }
//              
//              //Viewing changed document ends
          
          
        if(!imageCanvas.isErrorInResizing()){
          baos=new ByteArrayOutputStream();
          ti = new TranscoderInput(imageCanvas.getSVGDocument());
          to = new TranscoderOutput(baos);
          pngTranscoder.transcode(ti, to);
          pngContent=baos.toByteArray();
           baos.flush();
        }else{
          logger.debug("Error In Resizing ....   ...  "); 
        }
      
      }catch (IOException ioe) {
         ioe.printStackTrace();
         throw ioe;
       } catch (Exception e) {
         e.printStackTrace();;
         throw e;
       } finally {
         if(bais!=null){
           bais.close();
         }
         if(to!=null){
           to.getOutputStream().flush();
           to.getOutputStream().close();  
         }
         if(imageCanvas!=null){
           imageCanvas.setCanvasFree(true);
         }
         if(baos!=null){
           baos.flush();
           baos.close();
         }
         
         imageCanvas=null;
         ti=null;
         to=null;      
       }
      return pngContent;
    }
  
  
  }
