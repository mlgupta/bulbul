package bulbul.foff.studio.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class TrackDesignForm extends ValidatorForm  {
  private String txtYourEmailId;
  private String txtDesignCode;

  public String getTxtYourEmailId() {
    return txtYourEmailId;
  }

  public void setTxtYourEmailId(String txtYourEmailId) {
    this.txtYourEmailId = txtYourEmailId;
  }

  public String getTxtDesignCode() {
    return txtDesignCode;
  }

  public void setTxtDesignCode(String newTxtDesignCode) {
    txtDesignCode = newTxtDesignCode;
  }
}