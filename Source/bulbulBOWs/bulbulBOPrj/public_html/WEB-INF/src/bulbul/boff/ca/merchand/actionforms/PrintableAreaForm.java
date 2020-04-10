package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class PrintableAreaForm extends ValidatorForm  {
  private int hdnPrintableAreaTblPk;
  private String txtPrintableAreaName;
  private String hdnSearchPageNo;

  public int getHdnPrintableAreaTblPk() {
    return hdnPrintableAreaTblPk;
  }

  public void setHdnPrintableAreaTblPk(int hdnPrintableAreaTblPk) {
    this.hdnPrintableAreaTblPk = hdnPrintableAreaTblPk;
  }

  public String getTxtPrintableAreaName() {
    return txtPrintableAreaName;
  }

  public void setTxtPrintableAreaName(String txtPrintableAreaName) {
    this.txtPrintableAreaName = txtPrintableAreaName;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String hdnSearchPageNo) {
    this.hdnSearchPageNo = hdnSearchPageNo;
  }
  
}