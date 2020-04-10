package bulbul.foff.pcatalogue.beans;

public class ProductSizeBean  {
  private String merchandiseSizeTblPk;
  private String sizeTypeId;
  private String sizeId;
  private String price;

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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

}