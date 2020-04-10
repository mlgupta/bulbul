package bulbul.boff.ca.merchand.beans;

public class MerchandiseSizeBean  {
  private String merchandiseSizeTblPk;
  private String sizeTblPk;
  private String sizeId;
  private String sizeDescription;
  private String price;

  public String getMerchandiseSizeTblPk() {
    return merchandiseSizeTblPk;
  }

  public void setMerchandiseSizeTblPk(String newMerchandiseSizeTblPk) {
    merchandiseSizeTblPk = newMerchandiseSizeTblPk;
  }

  public String getSizeTblPk() {
    return sizeTblPk;
  }

  public void setSizeTblPk(String newSizeTblPk) {
    sizeTblPk = newSizeTblPk;
  }

  public String getSizeId() {
    return sizeId;
  }

  public void setSizeId(String newSizeId) {
    sizeId = newSizeId;
  }

  public String getSizeDescription() {
    return sizeDescription;
  }

  public void setSizeDescription(String newSizeDescription) {
    sizeDescription = newSizeDescription;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String newPrice) {
    price = newPrice;
  }
}