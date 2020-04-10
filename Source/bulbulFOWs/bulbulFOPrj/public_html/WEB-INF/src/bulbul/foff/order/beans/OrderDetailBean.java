package bulbul.foff.order.beans;
import java.util.ArrayList;
import bulbul.foff.customer.myaddress.beans.ShippingAddressBean;

public class OrderDetailBean  {
  private String orderDetailTblPk;
  private String designName;
  private String merchandiseName;
  private String merchandiseDesc;
  private String color1Name;
  private String color1Value;
  private String color2Name;
  private String color2Value;
  private ShippingAddressBean shippingAddress;
  private ArrayList orderItemDetail;
  private String shippingAddressCount;
  private String orderDetailTotal;

  public String getOrderDetailTblPk() {
    return orderDetailTblPk;
  }

  public void setOrderDetailTblPk(String orderDetailTblPk) {
    this.orderDetailTblPk = orderDetailTblPk;
  }

  public String getDesignName() {
    return designName;
  }

  public void setDesignName(String designName) {
    this.designName = designName;
  }

  public String getMerchandiseName() {
    return merchandiseName;
  }

  public void setMerchandiseName(String merchandiseName) {
    this.merchandiseName = merchandiseName;
  }

  public String getMerchandiseDesc() {
    return merchandiseDesc;
  }

  public void setMerchandiseDesc(String merchandiseDesc) {
    this.merchandiseDesc = merchandiseDesc;
  }

  public String getColor1Name() {
    return color1Name;
  }

  public void setColor1Name(String color1Name) {
    this.color1Name = color1Name;
  }

  public String getColor1Value() {
    return color1Value;
  }

  public void setColor1Value(String color1Value) {
    this.color1Value = color1Value;
  }

  public String getColor2Name() {
    return color2Name;
  }

  public void setColor2Name(String color2Name) {
    this.color2Name = color2Name;
  }

  public String getColor2Value() {
    return color2Value;
  }

  public void setColor2Value(String color2Value) {
    this.color2Value = color2Value;
  }

  public ShippingAddressBean getShippingAddress() {
    return shippingAddress;
  }
  public void setShippingAddress(ShippingAddressBean shippingAddress) {
    this.shippingAddress = shippingAddress;
  }
  public ArrayList getOrderItemDetail() {
    return orderItemDetail;
  }

  public void setOrderItemDetail(ArrayList orderItemDetail) {
    this.orderItemDetail = orderItemDetail;
  }

  public String getShippingAddressCount() {
    return shippingAddressCount;
  }

  public void setShippingAddressCount(String shippingAddressCount) {
    this.shippingAddressCount = shippingAddressCount;
  }

  public String getOrderDetailTotal() {
    return orderDetailTotal;
  }

  public void setOrderDetailTotal(String orderDetailTotal) {
    this.orderDetailTotal = orderDetailTotal;
  }


}