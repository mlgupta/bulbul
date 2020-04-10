package bulbul.foff.general;

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

public final class FOConstants  {

  //Tag Name 
  private final String constant_name;

 //Private Contructor to use in this class only
  private FOConstants(String constant_name){
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
  public static final FOConstants LOGGER = new  FOConstants("BFOffLogger");
  public static final FOConstants ACTIVE = new  FOConstants("Active");
  public static final FOConstants ACTIVE_VAL = new  FOConstants("1");
  public static final FOConstants INACTIVE = new  FOConstants("In-Active");
  public static final FOConstants INACTIVE_VAL = new  FOConstants("0");
  public static final FOConstants YES = new  FOConstants("Yes");
  public static final FOConstants NO = new  FOConstants("No");
  public static final FOConstants YES_VAL = new  FOConstants("1");
  public static final FOConstants NO_VAL = new  FOConstants("0");
  public static final FOConstants CATEGORY_VAL = new  FOConstants("1");  
  public static final FOConstants PRODUCT_VAL = new  FOConstants("0");
  public static final FOConstants CATEGORY_ONLY_VAL = new  FOConstants("1");  
  public static final FOConstants MERCHANDISE_ONLY_VAL = new  FOConstants("0");
}