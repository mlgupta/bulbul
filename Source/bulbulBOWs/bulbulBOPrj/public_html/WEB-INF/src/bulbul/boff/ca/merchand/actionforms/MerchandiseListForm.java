package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class MerchandiseListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String radSearchMerchandiseOrCategory;
  private String txtSearchMerchandiseCategory;
  private String txtSearchMerchandiseName;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private int hdnSearchMerchandiseOrCategoryTblPk=-1;
  private String hdnSearchMerchandiseOrCategoryType;
  private int hdnSearchFlag=0;

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  public String getRadSearchMerchandiseOrCategory() {
    return radSearchMerchandiseOrCategory;
  }

  public void setRadSearchMerchandiseOrCategory(String newRadSearchMerchandiseOrCategory) {
    radSearchMerchandiseOrCategory = newRadSearchMerchandiseOrCategory;
  }

  public String getTxtSearchMerchandiseCategory() {
    return txtSearchMerchandiseCategory;
  }

  public void setTxtSearchMerchandiseCategory(String newTxtSearchMerchandiseCategory) {
    txtSearchMerchandiseCategory = newTxtSearchMerchandiseCategory;
  }

  public String getTxtSearchMerchandiseName() {
    return txtSearchMerchandiseName;
  }

  public void setTxtSearchMerchandiseName(String newTxtSearchMerchandiseName) {
    txtSearchMerchandiseName = newTxtSearchMerchandiseName;
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

  public int getHdnSearchMerchandiseOrCategoryTblPk() {
    return hdnSearchMerchandiseOrCategoryTblPk;
  }

  public void setHdnSearchMerchandiseOrCategoryTblPk(int newHdnSearchMerchandiseOrCategoryTblPk) {
    hdnSearchMerchandiseOrCategoryTblPk = newHdnSearchMerchandiseOrCategoryTblPk;
  }

  public String getHdnSearchMerchandiseOrCategoryType() {
    return hdnSearchMerchandiseOrCategoryType;
  }

  public void setHdnSearchMerchandiseOrCategoryType(String newHdnSearchMerchandiseOrCategoryType) {
    hdnSearchMerchandiseOrCategoryType = newHdnSearchMerchandiseOrCategoryType;
  }

  public int getHdnSearchFlag() {
    return hdnSearchFlag;
  }

  public void setHdnSearchFlag(int newHdnSearchFlag) {
    hdnSearchFlag = newHdnSearchFlag;
  }
  
}