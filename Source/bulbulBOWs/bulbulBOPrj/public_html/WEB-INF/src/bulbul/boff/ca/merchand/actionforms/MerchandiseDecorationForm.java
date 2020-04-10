package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class MerchandiseDecorationForm extends ValidatorForm {
  private long merchandiseDecorationTblPk;
  private String txtDecorationName;
  private String txaDecorationDescription;
  private String hdnSearchPageNo;
  private int hdnMerchandiseDecorationTblPk;

  public long getMerchandiseDecorationTblPk() {
    return merchandiseDecorationTblPk;
  }

  public void setMerchandiseDecorationTblPk(long merchandiseDecorationTblPk) {
    this.merchandiseDecorationTblPk = merchandiseDecorationTblPk;
  }

  public String getTxtDecorationName() {
    return txtDecorationName;
  }

  public void setTxtDecorationName(String txtDecorationName) {
    this.txtDecorationName = txtDecorationName;
  }

  public String getTxaDecorationDescription() {
    return txaDecorationDescription;
  }

  public void setTxaDecorationDescription(String txaDecorationDescription) {
    this.txaDecorationDescription = txaDecorationDescription;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String hdnSearchPageNo) {
    this.hdnSearchPageNo = hdnSearchPageNo;
  }

  public int getHdnMerchandiseDecorationTblPk() {
    return hdnMerchandiseDecorationTblPk;
  }

  public void setHdnMerchandiseDecorationTblPk(int hdnMerchandiseDecorationTblPk) {
    this.hdnMerchandiseDecorationTblPk = hdnMerchandiseDecorationTblPk;
  }
 
}