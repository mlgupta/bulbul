package bulbul.boff.general;

import java.awt.Dimension;

/**
 *	Purpose: To generate the name for the constants used in program.
 *  Info: This class has one construstor whaich takes one string argument and initializes the 
 *        constant_name with that value.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public final class BOConstants  {

  //Tag Name 
  private final String constant_name;

 //Private Contructor to use in this class only
  private BOConstants(String constant_name){
    this.constant_name=constant_name;
  }

   /**
	 * Purpose  : To Get the Contant , the toString() method of Object Class is overloaded 
	 * @returns : Returns an Contant Name
	 */
  public String toString(){
    return this.constant_name;
  }

  /** Enum Constants  */
  public static final BOConstants ACTIVE = new  BOConstants("Active");
  public static final BOConstants ACTIVE_VAL = new  BOConstants("1");
  public static final BOConstants INACTIVE = new  BOConstants("In-Active");
  public static final BOConstants INACTIVE_VAL = new  BOConstants("0");
  public static final BOConstants BOTH = new  BOConstants("BOTH");
  public static final BOConstants BOTH_VAL = new  BOConstants("2");
  //public static final BOConstants NO_RECORDS_PER_PAGE = new  BOConstants("5");
  public static final BOConstants LOGGER = new  BOConstants("BBOffLogger");
  public static final BOConstants FONTCATEGORY = new  BOConstants("Category");
  public static final BOConstants FONT = new  BOConstants("Font");
  public static final BOConstants CLIPARTCATEGORY = new  BOConstants("Category");
  public static final BOConstants CLIPART = new  BOConstants("Clipart");
  public static final BOConstants MERCHANDISECATEGORY = new  BOConstants("Category");
  public static final BOConstants MERCHANDISE = new  BOConstants("Merchandise");
  public static final BOConstants YES = new  BOConstants("Yes");
  public static final BOConstants ALL = new  BOConstants("All");
  public static final BOConstants NO = new  BOConstants("No");
  public static final BOConstants INS = new  BOConstants("I");
  public static final BOConstants UPD = new  BOConstants("U");
  public static final BOConstants DEL = new  BOConstants("D");
  public static final BOConstants MERCHANDISE_CATEGORY_ONLY = new  BOConstants("Merchandise Category Only");
  public static final BOConstants MERCHANDISE_CATEGORY_ONLY_VAL = new  BOConstants("1");  
  public static final BOConstants MERCHANDISE_ONLY = new  BOConstants("Merchandise Only");  
  public static final BOConstants MERCHANDISE_ONLY_VAL = new  BOConstants("0");    
  
  public static final Dimension SVG_PREVIEW_SIZE = new Dimension(80,85);
  public static final Dimension SVG_STANDARD_SIZE = new Dimension(60,65);
}