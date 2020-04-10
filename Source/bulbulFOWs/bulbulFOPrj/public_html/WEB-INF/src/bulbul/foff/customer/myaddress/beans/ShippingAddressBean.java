package bulbul.foff.customer.myaddress.beans;

public class ShippingAddressBean  {
  private String customerAddressBookTblPk;
  private String fullName;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String pincode;
  private String state;
  private String isStateSelected;
  private String country;
  private String isCountrySelected;
  private String emailId;
  private String phoneNumber;

  public String getCustomerAddressBookTblPk() {
    return customerAddressBookTblPk;
  }

  public void setCustomerAddressBookTblPk(String customerAddressBookTblPk) {
    this.customerAddressBookTblPk = customerAddressBookTblPk;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getIsStateSelected() {
    return isStateSelected;
  }

  public void setIsStateSelected(String isStateSelected) {
    this.isStateSelected = isStateSelected;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getIsCountrySelected() {
    return isCountrySelected;
  }

  public void setIsCountrySelected(String isCountrySelected) {
    this.isCountrySelected = isCountrySelected;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}