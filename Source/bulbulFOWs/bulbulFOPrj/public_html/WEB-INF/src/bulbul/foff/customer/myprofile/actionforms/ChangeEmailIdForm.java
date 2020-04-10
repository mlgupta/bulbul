package bulbul.foff.customer.myprofile.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class ChangeEmailIdForm extends ValidatorForm  {
  private int hdnCustomerEmailIdTblPk;
  private String txtOldEmailId;
  private String txtNewEmailId;
  private String txtConfirmEmailId;

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

  public String getTxtOldEmailId() {
    return txtOldEmailId;
  }

  public void setTxtOldEmailId(String txtOldEmailId) {
    this.txtOldEmailId = txtOldEmailId;
  }

  public String getTxtNewEmailId() {
    return txtNewEmailId;
  }

  public void setTxtNewEmailId(String txtNewEmailId) {
    this.txtNewEmailId = txtNewEmailId;
  }

  public String getTxtConfirmEmailId() {
    return txtConfirmEmailId;
  }

  public void setTxtConfirmEmailId(String txtConfirmEmailId) {
    this.txtConfirmEmailId = txtConfirmEmailId;
  }

}
