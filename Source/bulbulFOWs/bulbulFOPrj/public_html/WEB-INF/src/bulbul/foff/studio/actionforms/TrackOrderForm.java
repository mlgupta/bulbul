package bulbul.foff.studio.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class TrackOrderForm extends ValidatorForm  {
  private String txtOrderCode;
  private String txtYourEmailId;

  public String getTxtOrderCode() {
    return txtOrderCode;
  }

  public void setTxtOrderCode(String txtOrderCode) {
    this.txtOrderCode = txtOrderCode;
  }

  public String getTxtYourEmailId() {
    return txtYourEmailId;
  }

  public void setTxtYourEmailId(String txtYourEmailId) {
    this.txtYourEmailId = txtYourEmailId;
  }
}