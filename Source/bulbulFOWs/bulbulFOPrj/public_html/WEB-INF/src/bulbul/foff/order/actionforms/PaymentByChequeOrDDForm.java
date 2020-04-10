package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByChequeOrDDForm extends ValidatorForm  {
  private final int hdnPaymentMode=PaymentMode.CHEQUE_OR_DD_PAYMENT.KEY;

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }

}