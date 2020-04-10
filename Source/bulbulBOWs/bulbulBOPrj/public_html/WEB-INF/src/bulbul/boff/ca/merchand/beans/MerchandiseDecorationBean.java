package bulbul.boff.ca.merchand.beans;

public class MerchandiseDecorationBean  {
  private String merchandiseDecorationTblPk;
  private String decorationName;
  private String decorationDescription;

  public String getMerchandiseDecorationTblPk() {
    return merchandiseDecorationTblPk;
  }

  public void setMerchandiseDecorationTblPk(String merchandiseDecorationTblPk) {
    this.merchandiseDecorationTblPk = merchandiseDecorationTblPk;
  }

  public String getDecorationName() {
    return decorationName;
  }

  public void setDecorationName(String decorationName) {
    this.decorationName = decorationName;
  }

  public String getDecorationDescription() {
    return decorationDescription;
  }

  public void setDecorationDescription(String decorationDescription) {
    this.decorationDescription = decorationDescription;
  }
}