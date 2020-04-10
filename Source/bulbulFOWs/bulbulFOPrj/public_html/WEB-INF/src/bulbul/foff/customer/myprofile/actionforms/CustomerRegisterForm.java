package bulbul.foff.customer.myprofile.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class CustomerRegisterForm extends ValidatorForm  {
  private String txtCustomerEmailId;
  private String txtCustomerPassword;
  private String txtDateOfBirth;
  private String cboPwdHintQuestion;
  private String txtPwdHintAnswer;
  private String chkNewsLetter;
  private String txtDesignCode;
  private String txtConfirmPassword;
  private String chkDesignCode;

  public String getTxtCustomerEmailId() {
    return txtCustomerEmailId;
  }

  public void setTxtCustomerEmailId(String newTxtCustomerEmailId) {
    txtCustomerEmailId = newTxtCustomerEmailId;
  }

  public String getTxtCustomerPassword() {
    return txtCustomerPassword;
  }

  public void setTxtCustomerPassword(String newTxtCustomerPassword) {
    txtCustomerPassword = newTxtCustomerPassword;
  }

  public String getTxtDateOfBirth() {
    return txtDateOfBirth;
  }

  public void setTxtDateOfBirth(String newTxtDateOfBirth) {
    txtDateOfBirth = newTxtDateOfBirth;
  }

  public String getCboPwdHintQuestion() {
    return cboPwdHintQuestion;
  }

  public void setCboPwdHintQuestion(String newCboPwdHintQuestion) {
    cboPwdHintQuestion = newCboPwdHintQuestion;
  }

  public String getTxtPwdHintAnswer() {
    return txtPwdHintAnswer;
  }

  public void setTxtPwdHintAnswer(String newTxtPwdHintAnswer) {
    txtPwdHintAnswer = newTxtPwdHintAnswer;
  }

  public String getChkNewsLetter() {
    return chkNewsLetter;
  }

  public void setChkNewsLetter(String newChkNewsLetter) {
    chkNewsLetter = newChkNewsLetter;
  }

  public String getTxtDesignCode() {
    return txtDesignCode;
  }

  public void setTxtDesignCode(String newTxtDesignCode) {
    txtDesignCode = newTxtDesignCode;
  }

  public String getTxtConfirmPassword()
  {
    return txtConfirmPassword;
  }

  public void setTxtConfirmPassword(String txtConfirmPassword)
  {
    this.txtConfirmPassword = txtConfirmPassword;
  }

  public String getChkDesignCode() {
    return chkDesignCode;
  }

  public void setChkDesignCode(String chkDesignCode) {
    this.chkDesignCode = chkDesignCode;
  }

}