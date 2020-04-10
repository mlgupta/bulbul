package bulbul.foff.studio.engine.comm;
import bulbul.foff.studio.engine.ui.Studio;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class HttpBrowser  {
  
  private Studio studio;
  private URL theUrl=null;
  
  /**
   * 
   * @description 
   * @param semiUrl
   * @param studio
   */
  public HttpBrowser(Studio studio, String semiUrl) {
    this.studio=studio;
    try {
      theUrl= new URL(getStudio().getSvgResource().getContextPath() + semiUrl ); 
    }catch (MalformedURLException e) {
     e.printStackTrace();
    }
  }

  public static String toEncodedString(Properties args){
    StringBuffer buffer = new StringBuffer();
    Enumeration names=args.propertyNames();
    try {
      while(names.hasMoreElements()){
        String name  = (String) names.nextElement();
        String value = (String) args.getProperty(name);
        buffer.append(URLEncoder.encode(name,"UTF-8") + "=" + URLEncoder.encode(value,"UTF-8"));
        if(names.hasMoreElements()) buffer.append("&");
      }
    }catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return buffer.toString();
  }
  
  public InputStream sendGetMessage() throws IOException{
    return sendGetMessage(null);
  }
  
  public InputStream sendGetMessage(Properties args ) throws IOException{
    String argString="";
    if (args!=null){
      argString= "?" + toEncodedString(args);
    }
    
    URL url = new URL (theUrl.toExternalForm()+argString);
    URLConnection urlConnection=url.openConnection();
    urlConnection.setUseCaches(false);
    
    return urlConnection.getInputStream();
  }
  
  public InputStream sendPostMessage(Properties args) throws IOException{
    String argString = "";
    if (args!=null){
      argString= toEncodedString(args);
    }
    URLConnection urlConnection=theUrl.openConnection();
    urlConnection.setDoInput(true);   
    urlConnection.setDoOutput(true);
    urlConnection.setUseCaches(false);
    urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
    OutputStream out = urlConnection.getOutputStream();
    out.write(argString.getBytes());
    out.flush();
    out.close(); 
    
    return urlConnection.getInputStream();
  }
  
  public InputStream sendPostMessage(Serializable obj) throws IOException {
    URLConnection urlConnection=theUrl.openConnection();
    urlConnection.setDoInput(true);   
    urlConnection.setDoOutput(true);
    urlConnection.setUseCaches(false);
    urlConnection.setRequestProperty("Content-Type","java-internal/" + obj.getClass().getName());
    ObjectOutputStream out = new ObjectOutputStream(urlConnection.getOutputStream());
    out.writeObject(obj);
    out.flush();
    out.close(); 
    return urlConnection.getInputStream();
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
}