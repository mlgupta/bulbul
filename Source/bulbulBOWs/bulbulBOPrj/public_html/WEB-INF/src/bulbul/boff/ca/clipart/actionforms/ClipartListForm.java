package bulbul.boff.ca.clipart.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class ClipartListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String radSearchClipartOrCategory;
  private String txtSearchClipartCategory;
  private String txtSearchClipartName;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private int hdnSearchClipartOrCategoryTblPk;
  private String hdnSearchClipartOrCategoryType;
  private String txtSearchCategoryDescription;
  private String txtSearchClipartKeywords;

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  public String getRadSearchClipartOrCategory() {
    return radSearchClipartOrCategory;
  }

  public void setRadSearchClipartOrCategory(String newRadSearchClipartOrCategory) {
    radSearchClipartOrCategory = newRadSearchClipartOrCategory;
  }

  public String getTxtSearchClipartCategory() {
    return txtSearchClipartCategory;
  }

  public void setTxtSearchClipartCategory(String newTxtSearchClipartCategory) {
    txtSearchClipartCategory = newTxtSearchClipartCategory;
  }

  public String getTxtSearchClipartName() {
    return txtSearchClipartName;
  }

  public void setTxtSearchClipartName(String newTxtSearchClipartName) {
    txtSearchClipartName = newTxtSearchClipartName;
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

  public int getHdnSearchClipartOrCategoryTblPk() {
    return hdnSearchClipartOrCategoryTblPk;
  }

  public void setHdnSearchClipartOrCategoryTblPk(int newHdnSearchClipartOrCategoryTblPk) {
    hdnSearchClipartOrCategoryTblPk = newHdnSearchClipartOrCategoryTblPk;
  }

  public String getHdnSearchClipartOrCategoryType() {
    return hdnSearchClipartOrCategoryType;
  }

  public void setHdnSearchClipartOrCategoryType(String newHdnSearchClipartOrCategoryType) {
    hdnSearchClipartOrCategoryType = newHdnSearchClipartOrCategoryType;
  }

  public String getTxtSearchCategoryDescription() {
    return txtSearchCategoryDescription;
  }

  public void setTxtSearchCategoryDescription(String txtSearchCategoryDescription) {
    this.txtSearchCategoryDescription = txtSearchCategoryDescription;
  }

  public String getTxtSearchClipartKeywords() {
    return txtSearchClipartKeywords;
  }

  public void setTxtSearchClipartKeywords(String txtSearchClipartKeywords) {
    this.txtSearchClipartKeywords = txtSearchClipartKeywords;
  }

}