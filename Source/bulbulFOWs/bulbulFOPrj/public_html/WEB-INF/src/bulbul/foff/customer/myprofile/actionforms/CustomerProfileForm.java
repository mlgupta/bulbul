package bulbul.foff.customer.myprofile.actionforms;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.upload.FormFile;

public class CustomerProfileForm extends ValidatorForm  {
  private String cboCustomerTitle;
  private String txtFirstName;
  private String txtLastName;
  private String txtAge;
  private String cboGender;
  private FormFile fleProfileImage;
  private String txtAddress1;
  private String txtCity;
  private String cboState;
  private String txtState;
  private String cboCountry;
  private String txtCountry;
  private String txtPincode;
  private String txtPhone1;
  private String txtPhone2;
  private String txtMobile;
  private int chkUseAddress4Shipping;
  private int chkNewsLetter;
  private String txtAddress2;
  private String txtAddress3;
  private int hdnIsStateListed;
  private int hdnIsCountryListed;
  private String hdnCreatedOn;
  private String hdnContentType;
  private int hdnContentSize;
  private String hdnImgOId;
  private String hdnIsNewProfile;
  private int hdnCustomerEmailIdTblPk;





  public String getCboCustomerTitle() {
    return cboCustomerTitle;
  }

  public void setCboCustomerTitle(String cboCustomerTitle) {
    this.cboCustomerTitle = cboCustomerTitle;
  }

  public String getTxtFirstName() {
    return txtFirstName;
  }

  public void setTxtFirstName(String newTxtFirstName) {
    txtFirstName = newTxtFirstName;
  }

  public String getTxtLastName() {
    return txtLastName;
  }

  public void setTxtLastName(String newTxtLastName) {
    txtLastName = newTxtLastName;
  }

  public String getTxtAge() {
    return txtAge;
  }

  public void setTxtAge(String newTxtAge) {
    txtAge = newTxtAge;
  }

  public String getCboGender() {
    return cboGender;
  }

  public void setCboGender(String newCboGender) {
    cboGender = newCboGender;
  }

  public FormFile getFleProfileImage() {
    return fleProfileImage;
  }

  public void setFleProfileImage(FormFile newFleProfileImage) {
    fleProfileImage = newFleProfileImage;
  }

  public String getTxtAddress1() {
    return txtAddress1;
  }

  public void setTxtAddress1(String newTxtAddress1) {
    txtAddress1 = newTxtAddress1;
  }

  public String getTxtCity() {
    return txtCity;
  }

  public void setTxtCity(String newTxtCity) {
    txtCity = newTxtCity;
  }

  public String getCboState() {
    return cboState;
  }

  public void setCboState(String newCboState) {
    cboState = newCboState;
  }

  public String getTxtState() {
    return txtState;
  }

  public void setTxtState(String newTxtState) {
    txtState = newTxtState;
  }



  public String getCboCountry() {
    return cboCountry;
  }

  public void setCboCountry(String newCboCountry) {
    cboCountry = newCboCountry;
  }

  public String getTxtCountry() {
    return txtCountry;
  }

  public void setTxtCountry(String newTxtCountry) {
    txtCountry = newTxtCountry;
  }



  public String getTxtPincode() {
    return txtPincode;
  }

  public void setTxtPincode(String newTxtPincode) {
    txtPincode = newTxtPincode;
  }

  public String getTxtPhone1() {
    return txtPhone1;
  }

  public void setTxtPhone1(String newTxtPhone1) {
    txtPhone1 = newTxtPhone1;
  }

  public String getTxtPhone2() {
    return txtPhone2;
  }

  public void setTxtPhone2(String newTxtPhone2) {
    txtPhone2 = newTxtPhone2;
  }

  public String getTxtMobile() {
    return txtMobile;
  }

  public void setTxtMobile(String newTxtMobile) {
    txtMobile = newTxtMobile;
  }

  public int getChkUseAddress4Shipping() {
    return chkUseAddress4Shipping;
  }

  public void setChkUseAddress4Shipping(int newChkUseAddress4Shipping) {
    chkUseAddress4Shipping = newChkUseAddress4Shipping;
  }

  public int getChkNewsLetter() {
    return chkNewsLetter;
  }

  public void setChkNewsLetter(int newChkNewsLetter) {
    chkNewsLetter = newChkNewsLetter;
  }



  public String getTxtAddress2() {
    return txtAddress2;
  }

  public void setTxtAddress2(String newTxtAddress2) {
    txtAddress2 = newTxtAddress2;
  }

  public String getTxtAddress3() {
    return txtAddress3;
  }

  public void setTxtAddress3(String newTxtAddress3) {
    txtAddress3 = newTxtAddress3;
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

  public String getHdnCreatedOn() {
    return hdnCreatedOn;
  }

  public void setHdnCreatedOn(String hdnCreatedOn) {
    this.hdnCreatedOn = hdnCreatedOn;
  }

  public String getHdnContentType() {
    return hdnContentType;
  }

  public void setHdnContentType(String hdnContentType) {
    this.hdnContentType = hdnContentType;
  }

  public int getHdnContentSize() {
    return hdnContentSize;
  }

  public void setHdnContentSize(int hdnContentSize) {
    this.hdnContentSize = hdnContentSize;
  }

  public String getHdnImgOId() {
    return hdnImgOId;
  }

  public void setHdnImgOId(String hdnImgOId) {
    this.hdnImgOId = hdnImgOId;
  }

  public void setHdnIsCountryListed(int hdnIsCountryListed) {
    this.hdnIsCountryListed = hdnIsCountryListed;
  }

  public String getHdnIsNewProfile() {
    return hdnIsNewProfile;
  }

  public void setHdnIsNewProfile(String hdnIsNewProfile) {
    this.hdnIsNewProfile = hdnIsNewProfile;
  }

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

}