package bulbul.foff.order.beans;
import bulbul.foff.customer.myaddress.beans.ShippingAddressBean;
import java.util.ArrayList;

public class OrderHeaderBean  {
  private int orderHeaderTblPk;
  private int paymentMode;
  private String orderCode;
  private String orderGenDate;
  private String orderPlacedDate;
  private String orderAmount;
  private int orderStatus;
  private ShippingAddressBean shippingAddress;
  private ArrayList orderDetail;
  private String isMultipleAddress;

  public int getOrderHeaderTblPk() {
    return orderHeaderTblPk;
  }

  public void setOrderHeaderTblPk(int orderHeaderTblPk) {
    this.orderHeaderTblPk = orderHeaderTblPk;
  }

  public int getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(int paymentMode) {
    this.paymentMode = paymentMode;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
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

  public String getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }

  public int getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(int orderStatus) {
    this.orderStatus = orderStatus;
  }

  public ShippingAddressBean getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(ShippingAddressBean shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public ArrayList getOrderDetail() {
    return orderDetail;
  }

  public void setOrderDetail(ArrayList orderDetail) {
    this.orderDetail = orderDetail;
  }

  public String getIsMultipleAddress() {
    return isMultipleAddress;
  }

  public void setIsMultipleAddress(String isMultipleAddress) {
    this.isMultipleAddress = isMultipleAddress;
  }
}