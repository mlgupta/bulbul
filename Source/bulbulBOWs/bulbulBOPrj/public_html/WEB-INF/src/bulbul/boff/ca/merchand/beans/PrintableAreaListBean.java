package bulbul.boff.ca.merchand.beans;

public class PrintableAreaListBean {
  private String printableAreaTblPk;
  private String isActive;
  private String isActiveDisplay;
  private String printableAreaName;
  private String previouslyChecked;
  private String printableAreaPriority;

  public String getPrintableAreaTblPk() {
    return printableAreaTblPk;
  }

  public void setPrintableAreaTblPk(String printableAreaTblPk) {
    this.printableAreaTblPk = printableAreaTblPk;
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

  public String getPrintableAreaName() {
    return printableAreaName;
  }

  public void setPrintableAreaName(String printableAreaName) {
    this.printableAreaName = printableAreaName;
  }
  
  public String getPreviouslyChecked() {
    return previouslyChecked;
  }

  public void setPreviouslyChecked(String previouslyChecked) {
    this.previouslyChecked = previouslyChecked;
  }

  public void setPrintableAreaPriority(String printableAreaPriority) {
    this.printableAreaPriority = printableAreaPriority;
  }

  public String getPrintableAreaPriority() {
    return printableAreaPriority;
  }
}
