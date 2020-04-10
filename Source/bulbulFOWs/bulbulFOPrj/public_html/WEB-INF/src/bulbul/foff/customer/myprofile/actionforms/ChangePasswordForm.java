package bulbul.foff.customer.myprofile.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class ChangePasswordForm extends ValidatorForm  {
  private int hdnCustomerEmailIdTblPk;
  private String txtOldPassword;
  private String txtNewPassword;
  private String txtConfirmPassword;

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

  public String getTxtOldPassword() {
    return txtOldPassword;
  }

  public void setTxtOldPassword(String newTxtOldPassword) {
    txtOldPassword = newTxtOldPassword;
  }

  public String getTxtNewPassword() {
    return txtNewPassword;
  }

  public void setTxtNewPassword(String newTxtNewPassword) {
    txtNewPassword = newTxtNewPassword;
  }

  public String getTxtConfirmPassword() {
    return txtConfirmPassword;
  }

  public void setTxtConfirmPassword(String newTxtConfirmPassword) {
    txtConfirmPassword = newTxtConfirmPassword;
  }

}