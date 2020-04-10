package bulbul.boff.outlet.beans;

/**
 *	Purpose: To Populate the Outlet List Table in outlet_list.jsp
 *  @author              Amit Mishra
 *  @version             1.0
 * 	Date of creation:   29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
public class OutletListBean  {
  private String outletTblPk;
  private String outletId;
  private String outletName;
  private String outletDescription;
  private String address1;
  private String address2;
  private String address3;
  private String city;
  private String state;
  private String country;
  private String pincode;
  private String isActive;
  private String isActiveDisplay;

  /**
   * Purpose : Returns Outlet Table Primary Key.
   * @return String
   */
  public String getOutletTblPk() {
    return outletTblPk;
  }
  
  /**
   * Purpose : Sets Outlet Table Primary Key
   * @param newOutletTblPk - A String Object.
   */
  public void setOutletTblPk(String newOutletTblPk) {
    outletTblPk = newOutletTblPk;
  }
  
  /**
   * Purpose : Returns Outlet Id .
   * @return String
   */
  public String getOutletId() {
    return outletId;
  }
  
  /**
   * Purpose : Sets The Id Of The Outlet.
   * @param newOutletId - A String Object.
   */
  public void setOutletId(String newOutletId) {
    outletId = newOutletId;
  }
  
  /**
   * Purpose : Returns Outlet Name.
   * @return String
   */
  public String getOutletName() {
    return outletName;
  }
  
  /**
   * Purpose : Sets The Name Of The Outlet.
   * @param newOutletName - A String Object.
   */
  public void setOutletName(String newOutletName) {
    outletName = newOutletName;
  }
  
  /**
   * Purpose : Returns Outlet Description.
   * @return String
   */
  public String getOutletDescription() {
    return outletDescription;
  }
  
  /**
   * Purpose : Sets The Description Of The Outlet.
   * @param newOutletDescription - A String Object.
   */
  public void setOutletDescription(String newOutletDescription) {
    outletDescription = newOutletDescription;
  }
  
  /**
   * Purpose : Returns First Address Of The Outlet.
   * @return String
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * Purpose : Sets The First Address Of The Outlet.
   * @param newAddress1 - A String Object.
   */
  public void setAddress1(String newAddress1) {
    address1 = newAddress1;
  }
  
  /**
   * Purpose : Returns Second Address Of The Outlet.
   * @return String
   */
  public String getAddress2() {
    return address2;
  }
  
  /**
   * Purpose : Sets The Second Address Of The Outlet.
   * @param newAddress2 - A String Object.
   */
  public void setAddress2(String newAddress2) {
    address2 = newAddress2;
  }
  
  /**
   * Purpose : Returns Third Address Of The Outlet.
   * @return String
   */
  public String getAddress3() {
    return address3;
  }
  
  /**
   * Purpose : Sets The Third Address Of The Outlet.
   * @param newAddress3 - A String Object.
   */
  public void setAddress3(String newAddress3) {
    address3 = newAddress3;
  }
  
  /**
   * Purpose : Returns City Of The Outlet.
   * @return String
   */
  public String getCity() {
    return city;
  }
  
  /**
   * Purpose : Sets The City Of The Outlet.
   * @param newCity - A String Object.
   */
  public void setCity(String newCity) {
    city = newCity;
  }
  
  /**
   * Purpose : Returns State Of The Outlet.
   * @return String
   */
  public String getState() {
    return state;
  }
  
  /**
   * Purpose : Sets The State Of The Outlet.
   * @param newState - A String Object.
   */
  public void setState(String newState) {
    state = newState;
  }
  
  /**
   * Purpose : Returns Country Of The Outlet.
   * @return String
   */
  public String getCountry() {
    return country;
  }
  
  /**
   * Purpose : Sets The Country Of The Outlet.
   * @param newCountry - A String Object.
   */
  public void setCountry(String newCountry) {
    country = newCountry;
  }
  
  /**
   * Purpose : Returns Pincode.
   * @return String
   */
  public String getPincode() {
    return pincode;
  }
  
  /**
   * Purpose : Sets The Pincode Of The Outlet.
   * @param newPincode - A String Object.
   */
  public void setPincode(String newPincode) {
    pincode = newPincode;
  }
  
  /**
   * Purpose : Returns Status Of The Outlet.
   * @return String
   */
  public String getIsActive() {
    return isActive;
  }
  
  /**
   * Purpose : Sets The Status Of The Outlet.
   * @param newIsActive - A String Object.
   */
  public void setIsActive(String newIsActive) {
    isActive = newIsActive;
  }
  
  /**
   * Purpose : Returns Status Of The Outlet In Form Of Constant.
   * @return String
   */
  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }
  
  /**
   * Purpose : Sets Status Of The Outlet In Form Of A Constant Name.
   * @param newIsActiveDisplay - A String Object.
   */
  public void setIsActiveDisplay(String newIsActiveDisplay) {
    isActiveDisplay = newIsActiveDisplay;
  }
}