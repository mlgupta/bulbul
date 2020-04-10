package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByCashAtCashPtForm extends ValidatorForm  {
  private String lstCashPt;
  private final int hdnPaymentMode=PaymentMode.CASH_AT_CASH_PONT.KEY;

  public String getLstCashPt() {
    return lstCashPt;
  }

  public void setLstCashPt(String lstCashPt) {
    this.lstCashPt = lstCashPt;
  }

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }


}