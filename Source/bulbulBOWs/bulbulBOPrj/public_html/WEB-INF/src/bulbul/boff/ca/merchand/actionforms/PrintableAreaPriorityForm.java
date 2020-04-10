package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class PrintableAreaPriorityForm extends ValidatorForm{
  private String[] printableAreaTblPk;
  private String[] printableAreaName;
  private String[] lstPrintableArea;
  private String hdnSearchPageNo;


  public void setPrintableAreaTblPk(String[] printableAreaTblPk) {
    this.printableAreaTblPk = printableAreaTblPk;
  }

  public String[] getPrintableAreaTblPk() {
    return printableAreaTblPk;
  }

  public void setPrintableAreaName(String[] printableAreaName) {
    this.printableAreaName = printableAreaName;
  }

  public String[] getPrintableAreaName() {
    return printableAreaName;
  }

  public void setHdnSearchPageNo(String hdnSearchPageNo) {
    this.hdnSearchPageNo = hdnSearchPageNo;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setLstPrintableArea(String[] lstPrintableArea) {
    this.lstPrintableArea = lstPrintableArea;
  }

  public String[] getLstPrintableArea() {
    return lstPrintableArea;
  }
}
