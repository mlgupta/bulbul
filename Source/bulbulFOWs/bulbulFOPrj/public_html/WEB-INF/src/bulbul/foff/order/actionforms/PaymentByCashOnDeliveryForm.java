package bulbul.foff.order.actionforms;

import bulbul.foff.general.PaymentMode;
import org.apache.struts.validator.ValidatorForm;

public class PaymentByCashOnDeliveryForm extends ValidatorForm  {
  private final int hdnPaymentMode=PaymentMode.CASH_ON_DELIVERY.KEY;

  public int getHdnPaymentMode() {
    return hdnPaymentMode;
  }

}