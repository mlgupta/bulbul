package bulbul.foff.studio.beans;

public class OrderBean  {
  private String orderHeaderTblPk;
  private String orderGenDate;
  private String orderPlacedDate;
  private String orderCode;
  private String orderAmount;
  private String orderStatusString;
  private String orderItems;
  private String orderStatus;

  public String getOrderHeaderTblPk() {
    return orderHeaderTblPk;
  }

  public void setOrderHeaderTblPk(String orderHeaderTblPk) {
    this.orderHeaderTblPk = orderHeaderTblPk;
  }

  public String getOrderGenDate() {
    return orderGenDate;
  }

  public void setOrderGenDate(String orderGenDate) {
    this.orderGenDate = orderGenDate;
  }

  public String getOrderPlacedDate() {
    return orderPlacedDate;
  }

  public void setOrderPlacedDate(String orderPlacedDate) {
    this.orderPlacedDate = orderPlacedDate;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }

  public String getOrderStatusString() {
    return orderStatusString;
  }

  public void setOrderStatusString(String orderStatusString) {
    this.orderStatusString = orderStatusString;
  }

  public String getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(String orderItems) {
    this.orderItems = orderItems;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }
}