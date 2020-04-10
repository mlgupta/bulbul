package bulbul.foff.order.beans;
import java.util.ArrayList;

public class PreOrderBean  {
  private String orderDetailTblPk;
  private String customerDesignTblPk;
  private String merchandiseColorTblPk;
  private String designName;
  private String productName;
  private String color1Value;
  private String color2Value;
  private String color1Name;
  private String color2Name;
  private String minQuantity;
  private ArrayList sizeArrayList;
  private String designOId;
  private String designContentType;
  private String designContentSize;
  private String productDescription;
  private String customerEmailIdTblPk;

  public String getOrderDetailTblPk() {
    return orderDetailTblPk;
  }

  public void setOrderDetailTblPk(String orderDetailTblPk) {
    this.orderDetailTblPk = orderDetailTblPk;
  }

  public String getCustomerDesignTblPk() {
    return customerDesignTblPk;
  }

  public void setCustomerDesignTblPk(String customerDesignTblPk) {
    this.customerDesignTblPk = customerDesignTblPk;
  }

  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  public void setMerchandiseColorTblPk(String merchandiseColorTblPk) {
    this.merchandiseColorTblPk = merchandiseColorTblPk;
  }

  public String getDesignName() {
    return designName;
  }

  public void setDesignName(String designName) {
    this.designName = designName;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getColor1Value() {
    return color1Value;
  }

  public void setColor1Value(String color1Value) {
    this.color1Value = color1Value;
  }

  public String getColor2Value() {
    return color2Value;
  }

  public void setColor2Value(String color2Value) {
    this.color2Value = color2Value;
  }

  public String getColor1Name() {
    return color1Name;
  }

  public void setColor1Name(String color1Name) {
    this.color1Name = color1Name;
  }

  public String getColor2Name() {
    return color2Name;
  }

  public void setColor2Name(String color2Name) {
    this.color2Name = color2Name;
  }

  public String getMinQuantity() {
    return minQuantity;
  }

  public void setMinQuantity(String minQuantity) {
    this.minQuantity = minQuantity;
  }

  public ArrayList getSizeArrayList() {
    return sizeArrayList;
  }

  public void setSizeArrayList(ArrayList sizeArrayList) {
    this.sizeArrayList = sizeArrayList;
  }

  public String getDesignOId() {
    return designOId;
  }

  public void setDesignOId(String designOId) {
    this.designOId = designOId;
  }

  public String getDesignContentType() {
    return designContentType;
  }

  public void setDesignContentType(String designContentType) {
    this.designContentType = designContentType;
  }

  public String getDesignContentSize() {
    return designContentSize;
  }

  public void setDesignContentSize(String designContentSize) {
    this.designContentSize = designContentSize;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }

  public String getCustomerEmailIdTblPk() {
    return customerEmailIdTblPk;
  }

  public void setCustomerEmailIdTblPk(String customerEmailIdTblPk) {
    this.customerEmailIdTblPk = customerEmailIdTblPk;
  }
}