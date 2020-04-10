package bulbul.boff.ca.merchand.beans;

public class MerchandiseListBean  {
  private String merchandiseOrCategoryTblPk;
  private String merchandiseOrCategoryName;
  private String merchandiseOrCategoryType;
  private String merchandiseOrCategoryDesc;
  private String isActive;
  private String isActiveDisplay;
  private String flag;

  public MerchandiseListBean() {
  }

  public String getMerchandiseOrCategoryTblPk() {
    return merchandiseOrCategoryTblPk;
  }

  public void setMerchandiseOrCategoryTblPk(String newMerchandiseOrCategoryTblPk) {
    merchandiseOrCategoryTblPk = newMerchandiseOrCategoryTblPk;
  }

  public String getMerchandiseOrCategoryName() {
    return merchandiseOrCategoryName;
  }

  public void setMerchandiseOrCategoryName(String newMerchandiseOrCategoryName) {
    merchandiseOrCategoryName = newMerchandiseOrCategoryName;
  }

  public String getMerchandiseOrCategoryType() {
    return merchandiseOrCategoryType;
  }

  public void setMerchandiseOrCategoryType(String newMerchandiseOrCategoryType) {
    merchandiseOrCategoryType = newMerchandiseOrCategoryType;
  }

  public String getMerchandiseOrCategoryDesc() {
    return merchandiseOrCategoryDesc;
  }

  public void setMerchandiseOrCategoryDesc(String newMerchandiseOrCategoryDesc) {
    merchandiseOrCategoryDesc = newMerchandiseOrCategoryDesc;
  }

  public String getIsActive() {
    return isActive;
  }

  public void setIsActive(String newIsActive) {
    isActive = newIsActive;
  }

  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }

  public void setIsActiveDisplay(String newIsActiveDisplay) {
    isActiveDisplay = newIsActiveDisplay;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String newFlag) {
    flag = newFlag;
  }
}