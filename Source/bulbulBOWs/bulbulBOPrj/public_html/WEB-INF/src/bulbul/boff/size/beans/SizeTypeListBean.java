package bulbul.boff.size.beans;

public class SizeTypeListBean  {
  private String sizeTypeTblPk;
  private String sizeTypeId;
  private String sizeTypeDescription;
  private String isActive;
  private String isActiveDisplay;

  public SizeTypeListBean() {
  }

  public String getSizeTypeTblPk() {
    return sizeTypeTblPk;
  }

  public void setSizeTypeTblPk(String newSizeTypeTblPk) {
    sizeTypeTblPk = newSizeTypeTblPk;
  }

  public String getSizeTypeId() {
    return sizeTypeId;
  }

  public void setSizeTypeId(String newSizeTypeId) {
    sizeTypeId = newSizeTypeId;
  }

  public String getSizeTypeDescription() {
    return sizeTypeDescription;
  }

  public void setSizeTypeDescription(String newSizeTypeDescription) {
    sizeTypeDescription = newSizeTypeDescription;
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



}