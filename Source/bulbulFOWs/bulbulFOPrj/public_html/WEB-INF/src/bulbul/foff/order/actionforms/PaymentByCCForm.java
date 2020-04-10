package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByCCForm extends ValidatorForm  {
  private String cboCCType;
  private String txtCCNumber;
  private String txtCVVCode4CC;
  private String cboExpMonth4CC;
  private String cboExpYear4CC;
  private String txtCCHolderName;
  private final int hdnPaymentMode=PaymentMode.CREDIT_CARD.KEY;

  public String getCboCCType() {
    return cboCCType;
  }

  public void setCboCCType(String cboCCType) {
    this.cboCCType = cboCCType;
  }

  public String getTxtCCNumber() {
    return txtCCNumber;
  }

  public void setTxtCCNumber(String txtCCNumber) {
    this.txtCCNumber = txtCCNumber;
  }

  public String getTxtCVVCode4CC() {
    return txtCVVCode4CC;
  }

  public void setTxtCVVCode4CC(String txtCVVCode4CC) {
    this.txtCVVCode4CC = txtCVVCode4CC;
  }

  public String getCboExpMonth4CC() {
    return cboExpMonth4CC;
  }

  public void setCboExpMonth4CC(String cboExpMonth4CC) {
    this.cboExpMonth4CC = cboExpMonth4CC;
  }

  public String getCboExpYear4CC() {
    return cboExpYear4CC;
  }

  public void setCboExpYear4CC(String cboExpYear4CC) {
    this.cboExpYear4CC = cboExpYear4CC;
  }

  public String getTxtCCHolderName() {
    return txtCCHolderName;
  }

  public void setTxtCCHolderName(String txtCCHolderName) {
    this.txtCCHolderName = txtCCHolderName;
  }

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }


}