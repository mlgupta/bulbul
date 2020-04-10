package bulbul.boff.ca.clipart.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class ClipartCategoryListForm extends ValidatorForm  {
  private int hdnSrcCategoryTblPk;
  private int hdnTrgCategoryTblPk;
  private int hdnSrcClipartTblPk;
  private String hdnOperation;
  private String hdnSearchPageNo;

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

  public int getHdnSrcClipartTblPk() {
    return hdnSrcClipartTblPk;
  }

  public void setHdnSrcClipartTblPk(int newHdnSrcClipartTblPk) {
    hdnSrcClipartTblPk = newHdnSrcClipartTblPk;
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


  
}