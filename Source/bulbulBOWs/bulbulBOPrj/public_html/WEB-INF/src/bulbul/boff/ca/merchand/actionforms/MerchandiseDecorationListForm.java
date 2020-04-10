package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class MerchandiseDecorationListForm extends ValidatorForm  {
  private String txtSearchDecorationName;
  private String txaSearchDecorationDescription;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchOperation;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;

  public String getTxtSearchDecorationName() {
    return txtSearchDecorationName;
  }

  public void setTxtSearchDecorationName(String txtSearchDecorationName) {
    this.txtSearchDecorationName = txtSearchDecorationName;
  }

  public String getTxaSearchDecorationDescription() {
    return txaSearchDecorationDescription;
  }

  public void setTxaSearchDecorationDescription(String txaSearchDecorationDescription) {
    this.txaSearchDecorationDescription = txaSearchDecorationDescription;
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

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String hdnSearchPageNo) {
    this.hdnSearchPageNo = hdnSearchPageNo;
  }

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String hdnSearchOperation) {
    this.hdnSearchOperation = hdnSearchOperation;
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

}