package bulbul.boff.ca.font.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class FontCategoryListForm extends ValidatorForm  {
  private int hdnSrcCategoryTblPk;
  private int hdnTrgCategoryTblPk;
  private int hdnSrcFontTblPk;
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

  public int getHdnSrcFontTblPk() {
    return hdnSrcFontTblPk;
  }

  public void setHdnSrcFontTblPk(int newHdnSrcFontTblPk) {
    hdnSrcFontTblPk = newHdnSrcFontTblPk;
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