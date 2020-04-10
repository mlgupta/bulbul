package bulbul.foff.order.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class MultipleAddressForm extends ValidatorForm  {
  private String[] cboShippingAddress;
  private String[] hdnOrderDetailTblPk;

  public String[] getCboShippingAddress() {
    return cboShippingAddress;
  }

  public void setCboShippingAddress(String[] cboShippingAddress) {
    this.cboShippingAddress = cboShippingAddress;
  }

  public String[] getHdnOrderDetailTblPk() {
    return hdnOrderDetailTblPk;
  }

  public void setHdnOrderDetailTblPk(String[] hdnOrderDetailTblPk) {
    this.hdnOrderDetailTblPk = hdnOrderDetailTblPk;
  }
}