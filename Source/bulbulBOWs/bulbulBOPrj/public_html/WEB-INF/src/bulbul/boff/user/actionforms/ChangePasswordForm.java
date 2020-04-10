package bulbul.boff.user.actionforms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


public class ChangePasswordForm extends ValidatorForm  {
  private int hdnUserProfileTblPk;
  private String txtOldPassword;
  private String txtNewPassword;
  private String txtConfirmPassword;

  public int getHdnUserProfileTblPk() {
    return hdnUserProfileTblPk;
  }

  public void setHdnUserProfileTblPk(int newHdnUserProfileTblPk) {
    hdnUserProfileTblPk = newHdnUserProfileTblPk;
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

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    super.reset(mapping, request);
  }
 
  
}