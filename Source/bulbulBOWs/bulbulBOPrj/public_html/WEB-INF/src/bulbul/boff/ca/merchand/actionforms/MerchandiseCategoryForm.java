package bulbul.boff.ca.merchand.actionforms;

import java.util.ArrayList;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


public class MerchandiseCategoryForm extends ValidatorForm  {
  private int hdnMerchandiseCategoryTblPk;
  private int hdnMerchandiseCategoryTblFk;
  private String txtMerchandiseCategory;
  private String txaMerchandiseCategoryDesc;
  private String hdnCategoryImgOid;
  private int hdnCreatedBy;
  private FormFile categoryImgFile;
  private String hdnCategoryImgContentType;
  private int hdnCategoryImgContentSize;
  private String hdnSearchPageNo;
  private int radMOrC;
  private String hdnSizeTypeTblPk;
  private String hdnMerchandiseCategorySizeTypeTblPk;
  private String hdnSizeTypeOperation;
  private ArrayList arrSizeType;
  private String radioMorCDisable;
  private String hdnSizeTypeId;
  private String txtParentCategory;

  public int getHdnMerchandiseCategoryTblPk() {
    return hdnMerchandiseCategoryTblPk;
  }

  public void setHdnMerchandiseCategoryTblPk(int newHdnMerchandiseCategoryTblPk) {
    hdnMerchandiseCategoryTblPk = newHdnMerchandiseCategoryTblPk;
  }

  public int getHdnMerchandiseCategoryTblFk() {
    return hdnMerchandiseCategoryTblFk;
  }

  public void setHdnMerchandiseCategoryTblFk(int newHdnMerchandiseCategoryTblFk) {
    hdnMerchandiseCategoryTblFk = newHdnMerchandiseCategoryTblFk;
  }

  public String getTxtMerchandiseCategory() {
    return txtMerchandiseCategory;
  }

  public void setTxtMerchandiseCategory(String newTxtMerchandiseCategory) {
    txtMerchandiseCategory = newTxtMerchandiseCategory;
  }

  public String getTxaMerchandiseCategoryDesc() {
    return txaMerchandiseCategoryDesc;
  }

  public void setTxaMerchandiseCategoryDesc(String newTxaMerchandiseCategoryDesc) {
    txaMerchandiseCategoryDesc = newTxaMerchandiseCategoryDesc;
  }

  public String getHdnCategoryImgOid() {
    return hdnCategoryImgOid;
  }

  public void setHdnCategoryImgOid(String newHdnCategoryImgOid) {
    hdnCategoryImgOid = newHdnCategoryImgOid;
  }

  public int getHdnCreatedBy() {
    return hdnCreatedBy;
  }

  public void setHdnCreatedBy(int newHdnCreatedBy) {
    hdnCreatedBy = newHdnCreatedBy;
  }

  public FormFile getCategoryImgFile() {
    return categoryImgFile;
  }

  public void setCategoryImgFile(FormFile newCategoryImgFile) {
    categoryImgFile = newCategoryImgFile;
  }

  public String getHdnCategoryImgContentType() {
    return hdnCategoryImgContentType;
  }

  public void setHdnCategoryImgContentType(String newHdnCategoryImgContentType) {
    hdnCategoryImgContentType = newHdnCategoryImgContentType;
  }

  public int getHdnCategoryImgContentSize() {
    return hdnCategoryImgContentSize;
  }

  public void setHdnCategoryImgContentSize(int newHdnCategoryImgContentSize) {
    hdnCategoryImgContentSize = newHdnCategoryImgContentSize;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public int getRadMOrC() {
    return radMOrC;
  }

  public void setRadMOrC(int newRadMOrC) {
    radMOrC = newRadMOrC;
  }

  public String getHdnSizeTypeTblPk() {
    return hdnSizeTypeTblPk;
  }

  public void setHdnSizeTypeTblPk(String newHdnSizeTypeTblPk) {
    hdnSizeTypeTblPk = newHdnSizeTypeTblPk;
  }

  public String getHdnMerchandiseCategorySizeTypeTblPk() {
    return hdnMerchandiseCategorySizeTypeTblPk;
  }

  public void setHdnMerchandiseCategorySizeTypeTblPk(String newHdnMerchandiseCategorySizeTypeTblPk) {
    hdnMerchandiseCategorySizeTypeTblPk = newHdnMerchandiseCategorySizeTypeTblPk;
  }

  public String getHdnSizeTypeOperation() {
    return hdnSizeTypeOperation;
  }

  public void setHdnSizeTypeOperation(String newHdnSizeTypeOperation) {
    hdnSizeTypeOperation = newHdnSizeTypeOperation;
  }



  public ArrayList getArrSizeType() {
    return arrSizeType;
  }

  public void setArrSizeType(ArrayList newArrSizeType) {
    arrSizeType = newArrSizeType;
  }

  public String getRadioMorCDisable() {
    return radioMorCDisable;
  }

  public void setRadioMorCDisable(String newRadioMorCDisable) {
    radioMorCDisable = newRadioMorCDisable;
  }

  public String getHdnSizeTypeId() {
    return hdnSizeTypeId;
  }

  public void setHdnSizeTypeId(String newHdnSizeTypeId) {
    hdnSizeTypeId = newHdnSizeTypeId;
  }

  public String getTxtParentCategory() {
    return txtParentCategory;
  }

  public void setTxtParentCategory(String txtParentCategory) {
    this.txtParentCategory = txtParentCategory;
  }
}