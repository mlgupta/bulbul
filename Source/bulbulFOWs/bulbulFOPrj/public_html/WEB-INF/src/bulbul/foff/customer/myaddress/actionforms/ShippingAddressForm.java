package bulbul.foff.customer.myaddress.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class ShippingAddressForm extends ValidatorForm  {
  private int hdnCustomerAddressBookTblPk;
  private int hdnCustomerEmailIdTblPk;
  private String txtFullName;
  private String txtAddressLine1;
  private String txtAddressLine2;
  private String txtCity;
  private String txtPincode;
  private String cboState;
  private String txtState;
  private String cboCountry;
  private String txtCountry;
  private String txtPhone;
  private String txtEmailId;
  private int hdnIsStateListed;
  private int hdnIsCountryListed;

  public int getHdnCustomerAddressBookTblPk() {
    return hdnCustomerAddressBookTblPk;
  }

  public void setHdnCustomerAddressBookTblPk(int hdnCustomerAddressBookTblPk) {
    this.hdnCustomerAddressBookTblPk = hdnCustomerAddressBookTblPk;
  }

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

  public String getTxtFullName() {
    return txtFullName;
  }

  public void setTxtFullName(String txtFullName) {
    this.txtFullName = txtFullName;
  }

  public String getTxtAddressLine1() {
    return txtAddressLine1;
  }

  public void setTxtAddressLine1(String txtAddressLine1) {
    this.txtAddressLine1 = txtAddressLine1;
  }

  public String getTxtAddressLine2() {
    return txtAddressLine2;
  }

  public void setTxtAddressLine2(String txtAddressLine2) {
    this.txtAddressLine2 = txtAddressLine2;
  }

  public String getTxtCity() {
    return txtCity;
  }

  public void setTxtCity(String txtCity) {
    this.txtCity = txtCity;
  }

  public String getTxtPincode() {
    return txtPincode;
  }

  public void setTxtPincode(String txtPincode) {
    this.txtPincode = txtPincode;
  }

  public String getCboState() {
    return cboState;
  }

  public void setCboState(String cboState) {
    this.cboState = cboState;
  }

  public String getTxtState() {
    return txtState;
  }

  public void setTxtState(String txtState) {
    this.txtState = txtState;
  }

  public String getCboCountry() {
    return cboCountry;
  }

  public void setCboCountry(String cboCountry) {
    this.cboCountry = cboCountry;
  }

  public String getTxtCountry() {
    return txtCountry;
  }

  public void setTxtCountry(String txtCountry) {
    this.txtCountry = txtCountry;
  }

  public String getTxtPhone() {
    return txtPhone;
  }

  public void setTxtPhone(String txtPhone) {
    this.txtPhone = txtPhone;
  }

  public String getTxtEmailId() {
    return txtEmailId;
  }

  public void setTxtEmailId(String txtEmailId) {
    this.txtEmailId = txtEmailId;
  }

  public int getHdnIsStateListed() {
    return hdnIsStateListed;
  }

  public void setHdnIsStateListed(int hdnIsStateListed) {
    this.hdnIsStateListed = hdnIsStateListed;
  }

  public int getHdnIsCountryListed() {
    return hdnIsCountryListed;
  }

  public void setHdnIsCountryListed(int hdnIsCountryListed) {
    this.hdnIsCountryListed = hdnIsCountryListed;
  }

}