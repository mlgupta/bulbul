package bulbul.foff.common;

/**
 * 
 * @description 
 * @version 1.0 dd-Oct-2005
 * @author Sudheer V Pujar
 */
public final class ImageFormat  {
  
  //Tag Name 
  private final String format_name;

 //Private Contructor to use in this class only
  private ImageFormat(String format_name){
    this.format_name=format_name;
  }

   /**
	 * Purpose  : To Get the Format , the toString() method of Object Class is overloaded 
	 * @returns : Returns an Format Name
	 */
  public String toString(){
    return this.format_name;
  }

  /** Enum Formats  */
  public static final ImageFormat ALL = new  ImageFormat("all");
  public static final ImageFormat JPG = new  ImageFormat("jpg");
  public static final ImageFormat GIF = new  ImageFormat("gif");
  public static final ImageFormat PNG = new  ImageFormat("png");
  public static final ImageFormat SVG = new  ImageFormat("svg");
}