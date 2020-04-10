package bulbul.foff.common;

public class ContentType   {

  //Content Type
  private final String contentType;

  //Private Constructor  To use inside the class only ..
  private ContentType(String contentType) {
    this.contentType=contentType;
  }

  /**
	 * Purpose  : To Get the Content Type , the toString() method of Object Class is overloaded 
	 * @returns : Returns an Content Type
	 */
  public String toString(){
    return this.contentType;
  }

  // Enumerated Content Type
  public static final ContentType JPG = new ContentType("image/jpeg");
  public static final ContentType PNG = new ContentType("image/png");
  public static final ContentType GIF = new ContentType("image/gif");
  public static final ContentType WMF = new ContentType("image/x-wmf");
  public static final ContentType WMA = new ContentType("video/x-ms-wm");
  public static final ContentType SVG = new ContentType("image/svg");

}