package bulbul.boff.ca.clipart.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class ClipartCategoryForm extends ValidatorForm  {
  private int hdnClipartCategoryTblPk;
  private int hdnClipartCategoryTblFk;
  private String txtClipartCategory;
  private String txaClipartCategoryDesc;
  String hdnSearchPageNo;
  private String txtParentCategory;

  public int getHdnClipartCategoryTblPk() {
    return hdnClipartCategoryTblPk;
  }

  public void setHdnClipartCategoryTblPk(int newHdnClipartCategoryTblPk) {
    hdnClipartCategoryTblPk = newHdnClipartCategoryTblPk;
  }

  public int getHdnClipartCategoryTblFk() {
    return hdnClipartCategoryTblFk;
  }

  public void setHdnClipartCategoryTblFk(int newHdnClipartCategoryTblFk) {
    hdnClipartCategoryTblFk = newHdnClipartCategoryTblFk;
  }

  public String getTxtClipartCategory() {
    return txtClipartCategory;
  }

  public void setTxtClipartCategory(String newTxtClipartCategory) {
    txtClipartCategory = newTxtClipartCategory;
  }

  public String getTxaClipartCategoryDesc() {
    return txaClipartCategoryDesc;
  }

  public void setTxaClipartCategoryDesc(String newTxaClipartCategoryDesc) {
    txaClipartCategoryDesc = newTxaClipartCategoryDesc;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getTxtParentCategory() {
    return txtParentCategory;
  }

  public void setTxtParentCategory(String txtParentCategory) {
    this.txtParentCategory = txtParentCategory;
  }
 
}