package bulbul.foff.customer.myprofile.actionforms;
import org.apache.struts.validator.ValidatorForm;

public class CustomerLoginForm extends ValidatorForm  {
  private String txtLoginEmailId;
  private String txtLoginPassword;

  public String getTxtLoginEmailId() {
    return txtLoginEmailId;
  }

  public void setTxtLoginEmailId(String newTxtLoginEmailId) {
    txtLoginEmailId = newTxtLoginEmailId;
  }

  public String getTxtLoginPassword() {
    return txtLoginPassword;
  }

  public void setTxtLoginPassword(String newTxtLoginPassword) {
    txtLoginPassword = newTxtLoginPassword;
  }

}