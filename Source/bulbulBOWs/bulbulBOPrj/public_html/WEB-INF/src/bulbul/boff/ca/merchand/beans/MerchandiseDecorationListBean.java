package bulbul.boff.ca.merchand.beans;

public class MerchandiseDecorationListBean  {
  private String merchandiseDecorationTblPk;
  private String decorationName;
  private String decorationDescription;
  private String isActive;
  private String isActiveDisplay;

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

  public String getIsActive() {
    return isActive;
  }

  public void setIsActive(String isActive) {
    this.isActive = isActive;
  }

  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }

  public void setIsActiveDisplay(String isActiveDisplay) {
    this.isActiveDisplay = isActiveDisplay;
  }
}