package bulbul.foff.studio.beans;

public class ProductBean  {
  private String merchandiseTblPk;
  private String merchaniseColorTblPk;
  private String productName;
  private String productOId;
  private String productContentSize;
  private String productContentType;

  public String getMerchandiseTblPk() {
    return merchandiseTblPk;
  }

  public void setMerchandiseTblPk(String merchandiseTblPk) {
    this.merchandiseTblPk = merchandiseTblPk;
  }

  public String getMerchaniseColorTblPk() {
    return merchaniseColorTblPk;
  }

  public void setMerchaniseColorTblPk(String merchaniseColorTblPk) {
    this.merchaniseColorTblPk = merchaniseColorTblPk;
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

  public String getProductContentSize() {
    return productContentSize;
  }

  public void setProductContentSize(String productContentSize) {
    this.productContentSize = productContentSize;
  }

  public String getProductContentType() {
    return productContentType;
  }

  public void setProductContentType(String productContentType) {
    this.productContentType = productContentType;
  }
}