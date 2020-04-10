package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class PricePromotionListForm extends ValidatorForm  {
  private String[] cboSearchColor;
  private String[] cboSearchSize;
  private String txtSearchStartDate;
  private String txtSearchEndDate;
  private String radSearchExclusiveByPass;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchOperation;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private int hdnMerchandiseTblPk;
  private int hdnMerchandiseSizeTblPk;
  private String hdnMerchandiseName;
  private int hdnMerchandiseCategoryTblPk;

  public String[] getCboSearchColor() {
    return cboSearchColor;
  }

  public void setCboSearchColor(String[] newCboSearchColor) {
    cboSearchColor = newCboSearchColor;
  }

  public String[] getCboSearchSize() {
    return cboSearchSize;
  }

  public void setCboSearchSize(String[] newCboSearchSize) {
    cboSearchSize = newCboSearchSize;
  }

  public String getTxtSearchStartDate() {
    return txtSearchStartDate;
  }

  public void setTxtSearchStartDate(String newTxtSearchStartDate) {
    txtSearchStartDate = newTxtSearchStartDate;
  }

  public String getTxtSearchEndDate() {
    return txtSearchEndDate;
  }

  public void setTxtSearchEndDate(String newTxtSearchEndDate) {
    txtSearchEndDate = newTxtSearchEndDate;
  }

  public String getRadSearchExclusiveByPass() {
    return radSearchExclusiveByPass;
  }

  public void setRadSearchExclusiveByPass(String newRadSearchExclusiveByPass) {
    radSearchExclusiveByPass = newRadSearchExclusiveByPass;
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

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
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

  public int getHdnMerchandiseTblPk() {
    return hdnMerchandiseTblPk;
  }

  public void setHdnMerchandiseTblPk(int newHdnMerchandiseTblPk) {
    hdnMerchandiseTblPk = newHdnMerchandiseTblPk;
  }

  public int getHdnMerchandiseSizeTblPk() {
    return hdnMerchandiseSizeTblPk;
  }

  public void setHdnMerchandiseSizeTblPk(int newHdnMerchandiseSizeTblPk) {
    hdnMerchandiseSizeTblPk = newHdnMerchandiseSizeTblPk;
  }

  public String getHdnMerchandiseName() {
    return hdnMerchandiseName;
  }

  public void setHdnMerchandiseName(String newHdnMerchandiseName) {
    hdnMerchandiseName = newHdnMerchandiseName;
  }

  public int getHdnMerchandiseCategoryTblPk() {
    return hdnMerchandiseCategoryTblPk;
  }

  public void setHdnMerchandiseCategoryTblPk(int newHdnMerchandiseCategoryTblPk) {
    hdnMerchandiseCategoryTblPk = newHdnMerchandiseCategoryTblPk;
  }

}