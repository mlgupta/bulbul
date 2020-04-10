package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByDCForm extends ValidatorForm  {
  private String cboDCType;
  private String txtDCNumber;
  private String txtCVVCode4DC;
  private String cboExpMonth4DC;
  private String cboExpYear4DC;
  private String txtDCHolderName;
  private final int hdnPaymentMode=PaymentMode.DEBIT_CARD.KEY;

  public String getCboDCType() {
    return cboDCType;
  }

  public void setCboDCType(String cboDCType) {
    this.cboDCType = cboDCType;
  }

  public String getTxtDCNumber() {
    return txtDCNumber;
  }

  public void setTxtDCNumber(String txtDCNumber) {
    this.txtDCNumber = txtDCNumber;
  }

  public String getTxtCVVCode4DC() {
    return txtCVVCode4DC;
  }

  public void setTxtCVVCode4DC(String txtCVVCode4DC) {
    this.txtCVVCode4DC = txtCVVCode4DC;
  }

  public String getCboExpMonth4DC() {
    return cboExpMonth4DC;
  }

  public void setCboExpMonth4DC(String cboExpMonth4DC) {
    this.cboExpMonth4DC = cboExpMonth4DC;
  }

  public String getCboExpYear4DC() {
    return cboExpYear4DC;
  }

  public void setCboExpYear4DC(String cboExpYear4DC) {
    this.cboExpYear4DC = cboExpYear4DC;
  }

  public String getTxtDCHolderName() {
    return txtDCHolderName;
  }

  public void setTxtDCHolderName(String txtDCHolderName) {
    this.txtDCHolderName = txtDCHolderName;
  }

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }


}