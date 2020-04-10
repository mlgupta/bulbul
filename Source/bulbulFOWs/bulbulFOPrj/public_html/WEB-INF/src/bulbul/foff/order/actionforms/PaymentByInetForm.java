package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByInetForm extends ValidatorForm  {
  private String lstInetBank;
  private final int hdnPaymentMode=PaymentMode.INET_BANK.KEY;

  public String getLstInetBank() {
    return lstInetBank;
  }

  public void setLstInetBank(String lstInetBank) {
    this.lstInetBank = lstInetBank;
  }

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }


}