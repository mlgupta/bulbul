package bulbul.boff.size.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class SizeListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String txtSearchSizeId;
  private String txaSearchSizeDesc;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private int hdnSizeTypeTblPk;
  private String hdnSizeTypePageNo;

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  public String getTxtSearchSizeId() {
    return txtSearchSizeId;
  }

  public void setTxtSearchSizeId(String newTxtSearchSizeId) {
    txtSearchSizeId = newTxtSearchSizeId;
  }

  public String getTxaSearchSizeDesc() {
    return txaSearchSizeDesc;
  }

  public void setTxaSearchSizeDesc(String newTxaSearchSizeDesc) {
    txaSearchSizeDesc = newTxaSearchSizeDesc;
  }



  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  public void setRadSearchStatus(String newRadSearchStatus) {
    radSearchStatus = newRadSearchStatus;
  }

  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  public void setRadSearchSelect(String newRadSearchSelect) {
    radSearchSelect = newRadSearchSelect;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  public void setHdnSearchStatus(String newHdnSearchStatus) {
    hdnSearchStatus = newHdnSearchStatus;
  }

  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;
  }

  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }


  public int getHdnSizeTypeTblPk() {
    return hdnSizeTypeTblPk;
  }

  public void setHdnSizeTypeTblPk(int newHdnSizeTypeTblPk) {
    hdnSizeTypeTblPk = newHdnSizeTypeTblPk;
  }

  public String getHdnSizeTypePageNo() {
    return hdnSizeTypePageNo;
  }

  public void setHdnSizeTypePageNo(String hdnSizeTypePageNo) {
    this.hdnSizeTypePageNo = hdnSizeTypePageNo;
  }


  
}