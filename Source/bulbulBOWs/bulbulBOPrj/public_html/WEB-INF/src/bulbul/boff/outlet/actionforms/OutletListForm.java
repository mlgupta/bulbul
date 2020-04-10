package bulbul.boff.outlet.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To Store And Retrieve The Value Of The Properties Used In JSP. 
 *  Info: This Class Will Store And Return The JSP's Property Values By Its Mutators And Accessors. 
 *        Most Of The Properties From This Class Are Used In Search Operation.     
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

 OutletListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String txtSearchOutletId;
  private String txtSearchOutletName;
  private String txtSearchAddress;
  private String txtSearchCity;
  private String cboSearchState;
  private String cboSearchCountry;
  private String txtSearchPincode;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  
  /**
   * Purpose : To Get The Primary Key Of The Selected Radio Button.
   * @return Primary Key Of Type String
   */
  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  /**
   * Purpose : To Set The Hidden  Primary Key Of The Selected Radio Button.
   * @param newHdnSearchOperation - Primary Key Of The Outlet Table Of Type String
   */
  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }
  
  /**
   * Purpose : To Get The Outlet Id From The Search Form.
   * @return Outlet Id Of Type String
   */
  public String getTxtSearchOutletId() {
    return txtSearchOutletId;
  }

  /**
   * Purpose : To Set The Outlet Id Used In Search Operation.
   * @param newTxtSearchOutletId - Outlet Id Of  Of Type String
   */
  public void setTxtSearchOutletId(String newTxtSearchOutletId) {
    txtSearchOutletId = newTxtSearchOutletId;
  }
  
  /**
   * Purpose : To Get The Outlet Name From The Search Form.
   * @return Outlet Name Of Type String
   */
  public String getTxtSearchOutletName() {
    return txtSearchOutletName;
  }

  /**
   * Purpose : To Set The Outlet Name Used In Search Opertion.
   * @param newTxtSearchOutletName - Outlet Name Of Type String
   */
  public void setTxtSearchOutletName(String newTxtSearchOutletName) {
    txtSearchOutletName = newTxtSearchOutletName;
  }
  
  /**
   * Purpose : To Get The Address From The Search Form.
   * @return Address Of Type String
   */
  public String getTxtSearchAddress() {
    return txtSearchAddress;
  }

  /**
   * Purpose : To Set The Address Used In Search Opertion.
   * @param newTxtSearchAddress - Address Of Type String
   */
  public void setTxtSearchAddress(String newTxtSearchAddress) {
    txtSearchAddress = newTxtSearchAddress;
  }
  
  /**
   * Purpose : To Get The City From The Search Form.
   * @return City Of Type String
   */
  public String getTxtSearchCity() {
    return txtSearchCity;
  }

  /**
   * Purpose : To Set The City Used In Search Opertion.
   * @param newTxtSearchCity - City Of Type String
   */
  public void setTxtSearchCity(String newTxtSearchCity) {
    txtSearchCity = newTxtSearchCity;
  }
  
  /**
   * Purpose : To Get The State From The Search Form.
   * @return State Of Type String
   */
  public String getCboSearchState() {
    return cboSearchState;
  }

  /**
   * Purpose : To Set The State Used In Search Opertion.
   * @param newCboSearchState - State Of Type String
   */
  public void setCboSearchState(String newCboSearchState) {
    cboSearchState = newCboSearchState;
  }
  
  /**
   * Purpose : To Get The Country From The Search Form.
   * @return Country Of Type String
   */
  public String getCboSearchCountry() {
    return cboSearchCountry;
  }

  /**
   * Purpose : To Set The Country Used In Search Opertion.
   * @param newCboSearchCountry - Country Of Type String
   */
  public void setCboSearchCountry(String newCboSearchCountry) {
    cboSearchCountry = newCboSearchCountry;
  }
  
  /**
   * Purpose : To Get The Pincode From The Search Form.
   * @return Pincode Of Type String
   */
  public String getTxtSearchPincode() {
    return txtSearchPincode;
  }

  /**
   * Purpose : To Set The Pincode Used In Search Opertion.
   * @param newTxtSearchPincode - Pincode Of Type String.
   */
  public void setTxtSearchPincode(String newTxtSearchPincode) {
    txtSearchPincode = newTxtSearchPincode;
  }
  
  /**
   * Purpose : To Get The Status From The Search Form.
   * @return Status Of Type String
   */
  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  /**
   * Purpose : To Set The Activate/Deactivate Status Used In Search Opertion.
   * @param newRadSearchStatus - Activate/Deactivate Status Of Type String.
   */
  public void setRadSearchStatus(String newRadSearchStatus) {
    radSearchStatus = newRadSearchStatus;
  }
  
  /**
   * Purpose : To Get The Value Of The Primary Key Of The Selected Radio Button.
   * @return Primary Key Of Type String.
   */
  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  /**
   * Purpose : To Set The Value Of The Primary Key Of The Selected Radio Button.
   * @param newRadSearchSelect -  Type String.
   */
  public void setRadSearchSelect(String newRadSearchSelect) {
    radSearchSelect = newRadSearchSelect;
  }
  
  /**
   * Purpose : To Get The Page Number.
   * @return Page Number Of Type String.
   */
  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  /**
   * Purpose : To Set The Page No. Used In Navigation.
   * @param newHdnSearchPageNo - Page No. Of Type String.
   */
  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }
  
  /**
   * Purpose : To Get The Status Of The Listed Records.
   * @return Status Of Type String
   */  
  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  /**
   * Purpose : To Set The Status Of The  Records.
   * @param newHdnSearchStatus - Status Of Type String
   */
  public void setHdnSearchStatus(String newHdnSearchStatus) {
    hdnSearchStatus = newHdnSearchStatus;
  }
  
  /**
   * Purpose : To Get The Status Of The Selected Record.
   * @return status Of Type String.
   */
  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  /**
   * Purpose : To Set The Status Of The  Selected Record.
   * @param newHdnSearchCurrentStatus - Status Of Type String
   */
  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;

  }  

  
  /**
   * Purpose : To Get The Value Of The Number Of Pages.
   * @return Page Counter Of Type String.
   */
  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  /**
   * Purpose : To Set The Value Of The Number Of Pages.
   * @param newHdnSearchPageCount - Page Counter Of Type String.
   */
  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }

}