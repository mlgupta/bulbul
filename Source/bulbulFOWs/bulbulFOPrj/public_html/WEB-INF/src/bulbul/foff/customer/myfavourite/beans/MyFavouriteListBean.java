package bulbul.foff.customer.myfavourite.beans;

public class MyFavouriteListBean  {
  private String morcTblPk;
  private String productDescription;
  String noOfColors;
  private String productName;
  private String productOId;
  private String productContentType;
  private String productContentSize;
  private String customerFavouriteTblPk;

  public MyFavouriteListBean() {
  }

  public String getMorcTblPk() {
    return morcTblPk;
  }

  public void setMorcTblPk(String morcTblPk) {
    this.morcTblPk = morcTblPk;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }

  public String getNoOfColors() {
    return noOfColors;
  }

  public void setNoOfColors(String noOfColors) {
    this.noOfColors = noOfColors;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductOId() {
    return productOId;
  }

  public void setProductOId(String productOId) {
    this.productOId = productOId;
  }

  public String getProductContentType() {
    return productContentType;
  }

  public void setProductContentType(String productContentType) {
    this.productContentType = productContentType;
  }

  public String getProductContentSize() {
    return productContentSize;
  }

  public void setProductContentSize(String productContentSize) {
    this.productContentSize = productContentSize;
  }

  public String getCustomerFavouriteTblPk() {
    return customerFavouriteTblPk;
  }

  public void setCustomerFavouriteTblPk(String customerFavouriteTblPk) {
    this.customerFavouriteTblPk = customerFavouriteTblPk;
  }
}