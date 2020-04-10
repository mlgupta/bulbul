package bulbul.boff.login.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class LoginForm extends ValidatorForm  {
  private String txtLoginId;
  private String txtLoginPassword;

  public String getTxtLoginId() {
    return txtLoginId;
  }

  public void setTxtLoginId(String newTxtLoginId) {
    txtLoginId = newTxtLoginId;
  }

  public String getTxtLoginPassword() {
    return txtLoginPassword;
  }

  public void setTxtLoginPassword(String newTxtLoginPassword) {
    txtLoginPassword = newTxtLoginPassword;
  }
  
}