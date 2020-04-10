package bulbul.foff.order.beans;

public class PreOrderSizeBean  {
  private String orderItemDetailTblPk;
  private String merchandiseSizeTblPk;
  private String sizeTypeId;
  private String sizeId;
  private String quantity;

  public String getOrderItemDetailTblPk() {
    return orderItemDetailTblPk;
  }

  public void setOrderItemDetailTblPk(String orderItemDetailTblPk) {
    this.orderItemDetailTblPk = orderItemDetailTblPk;
  }

  public String getMerchandiseSizeTblPk() {
    return merchandiseSizeTblPk;
  }

  public void setMerchandiseSizeTblPk(String merchandiseSizeTblPk) {
    this.merchandiseSizeTblPk = merchandiseSizeTblPk;
  }

  public String getSizeTypeId() {
    return sizeTypeId;
  }

  public void setSizeTypeId(String sizeTypeId) {
    this.sizeTypeId = sizeTypeId;
  }

  public String getSizeId() {
    return sizeId;
  }

  public void setSizeId(String sizeId) {
    this.sizeId = sizeId;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }
}