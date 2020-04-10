package bulbul.boff.outlet.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To Store And Retrieve The Value Of The Properties Used In JSP. 
 *  Info: This Class Will Store And Return The JSP's Property Values By Its Mutators And Accessors. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class OutletForm extends ValidatorForm  {

  private int hdnOutletTblPk;
  private String txtOutletId;
  private String txtOutletName;
  private String txaOutletDesc;
  private String txtAddress1;
  private String txtAddress2;
  private String txtAddress3;
  private String txtCity;
  private String cboState;
  private String cboCountry;
  private String txtPincode;
  private String hdnSearchPageNo;

  /**
   * Purpose : To Get The Primary Key Of The Selected Radio Button.
   * @return Primary Key Of Type long
   */
  public int getHdnOutletTblPk() {
      return hdnOutletTblPk;

  }

  /**
   * Purpose : To Set The Primary Key Of The Selected Radio Button.
   * @param newHdnOutletTblPk - Primary Key Of The Outlet Table Of Type Long
   */
  public void setHdnOutletTblPk(int newHdnOutletTblPk) {
      hdnOutletTblPk = newHdnOutletTblPk;

  }

  /**
   * Purpose : To Get The Outlet Id Entered Into The Form.
   * @return txtOutletId Of Type String.
   */
  public String getTxtOutletId() {
    return txtOutletId;
  }

  /**
   * Purpose : To Set The Outlet Id In Form.
   * @param newTxtOutletId - Outlet Id Of Type Long
   */
  public void setTxtOutletId(String newTxtOutletId) {
    txtOutletId = newTxtOutletId;
  }
  
  /**
   * Purpose : To Get The Outlet Name Entered Into The Form.
   * @return txtOutletName Of Type String.
   */
  public String getTxtOutletName() {
    return txtOutletName;
  }

  /**
   * Purpose : To Set The Outlet Name In Form.
   * @param newTxtOutletName - Outlet Name Of Type String
   */
  public void setTxtOutletName(String newTxtOutletName) {
    txtOutletName = newTxtOutletName;
  }

  /**
   * Purpose : To Get The Outlet Description Entered Into The Form.
   * @return txaOutletDesc Of Type String.
   */
  public String getTxaOutletDesc() {
    return txaOutletDesc;
  }

  /**
   * Purpose : To Set The Outlet Description In Form.
   * @param newTxaOutletDesc - Outlet Description Of Type String
   */
  public void setTxaOutletDesc(String newTxaOutletDesc) {
    txaOutletDesc = newTxaOutletDesc;
  }

  /**
   * Purpose : To Get The Outlet Address Entered Into The Form.
   * @return txtAddress1 Of Type String.
   */
  public String getTxtAddress1() {
    return txtAddress1;
  }

  /**
   * Purpose : To Set The Outlet Address In Form.
   * @param newTxtAddress1 - Outlet Address Of Type String.
   */
  public void setTxtAddress1(String newTxtAddress1) {
    txtAddress1 = newTxtAddress1;
  }

  /**
   * Purpose : To Get The Outlet Address Entered Into The Form.
   * @return txtAddress2 Of Type String.
   */
  public String getTxtAddress2() {
    return txtAddress2;
  }

  /**
   * Purpose : To Set The Outlet Address In Form.
   * @param newTxtAddress2 - Outlet Address Of Type String.
   */
  public void setTxtAddress2(String newTxtAddress2) {
    txtAddress2 = newTxtAddress2;
  }

  /**
   * Purpose : To Get The Outlet Address Entered Into The Form.
   * @return txtAddress3 Of Type String.
   */
  public String getTxtAddress3() {
    return txtAddress3;
  }

  /**
   * Purpose : To Set The Outlet Address In Form.
   * @param newTxtAddress3 - Outlet Address Of Type String.
   */
  public void setTxtAddress3(String newTxtAddress3) {
    txtAddress3 = newTxtAddress3;
  }

  /**
   * Purpose : To Get The Outlet City Entered Into The Form.
   * @return txtCity Of Type String.
   */
  public String getTxtCity() {
    return txtCity;
  }

  /**
   * Purpose : To Set The Outlet City In Form.
   * @param newTxtCity - Outlet City Of Type String
   */
  public void setTxtCity(String newTxtCity) {
    txtCity = newTxtCity;
  }

  /**
   * Purpose : To Get The Outlet State Entered Into The Form.
   * @return txtState Of Type String.
   */
  public String getCboState() {
    return cboState;
  }

  /**
   * Purpose : To Set The Outlet State In Form.
   * @param newCboState - Outlet State Of Type String
   */
  public void setCboState(String newCboState) {
    cboState = newCboState;
  }

  /**
   * Purpose : To Get The Outlet Country Entered Into The Form.
   * @return cboCountry Of Type String.
   */
  public String getCboCountry() {
    return cboCountry;
  }

  /**
   * Purpose : To Set The Outlet Country In Form.
   * @param newCboCountry - Outlet Country Of Type String
   */
  public void setCboCountry(String newCboCountry) {
    cboCountry = newCboCountry;
  }

  /**
   * Purpose : To Get The Outlet Pincode Entered Into The Form.
   * @return txtPincode Of Type String.
   */
  public String getTxtPincode() {
    return txtPincode;
  }

  /**
   * Purpose : To Set The Outlet Pincode In Form.
   * @param newTxtPincode - Outlet Pincode Of Type String
   */
  public void setTxtPincode(String newTxtPincode) {
    txtPincode = newTxtPincode;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

}