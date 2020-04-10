package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class PrintableAreaListForm extends ValidatorForm  {
  private String hdnSearchPageNo;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private String txtSearchPrintableAreaName;
  private String hdnSearchOperation;

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String hdnSearchPageNo) {
    this.hdnSearchPageNo = hdnSearchPageNo;
  }

  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  public void setRadSearchStatus(String radSearchStatus) {
    this.radSearchStatus = radSearchStatus;
  }

  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  public void setRadSearchSelect(String radSearchSelect) {
    this.radSearchSelect = radSearchSelect;
  }

  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  public void setHdnSearchStatus(String hdnSearchStatus) {
    this.hdnSearchStatus = hdnSearchStatus;
  }

  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  public void setHdnSearchCurrentStatus(String hdnSearchCurrentStatus) {
    this.hdnSearchCurrentStatus = hdnSearchCurrentStatus;
  }

  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  public void setHdnSearchPageCount(String hdnSearchPageCount) {
    this.hdnSearchPageCount = hdnSearchPageCount;
  }

  public String getTxtSearchPrintableAreaName() {
    return txtSearchPrintableAreaName;
  }

  public void setTxtSearchPrintableAreaName(String txtSearchPrintableAreaName) {
    this.txtSearchPrintableAreaName = txtSearchPrintableAreaName;
  }

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String hdnSearchOperation) {
    this.hdnSearchOperation = hdnSearchOperation;
  }
}