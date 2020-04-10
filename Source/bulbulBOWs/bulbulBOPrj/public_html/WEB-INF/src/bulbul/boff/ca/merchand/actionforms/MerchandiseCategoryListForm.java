package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class MerchandiseCategoryListForm extends ValidatorForm  {
  private int hdnSrcCategoryTblPk;
  private int hdnTrgCategoryTblPk;
  private String hdnOperation;
  private String hdnSearchPageNo;
  private int hdnSrcMerchandiseTblPk;

  public int getHdnSrcCategoryTblPk() {
    return hdnSrcCategoryTblPk;
  }

  public void setHdnSrcCategoryTblPk(int newHdnSrcCategoryTblPk) {
    hdnSrcCategoryTblPk = newHdnSrcCategoryTblPk;
  }

  public int getHdnTrgCategoryTblPk() {
    return hdnTrgCategoryTblPk;
  }

  public void setHdnTrgCategoryTblPk(int newHdnTrgCategoryTblPk) {
    hdnTrgCategoryTblPk = newHdnTrgCategoryTblPk;
  }

  public String getHdnOperation() {
    return hdnOperation;
  }

  public void setHdnOperation(String newHdnOperation) {
    hdnOperation = newHdnOperation;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public int getHdnSrcMerchandiseTblPk() {
    return hdnSrcMerchandiseTblPk;
  }

  public void setHdnSrcMerchandiseTblPk(int newHdnSrcMerchandiseTblPk) {
    hdnSrcMerchandiseTblPk = newHdnSrcMerchandiseTblPk;
  }






  
}