package bulbul.boff.user.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class UserPreferencesForm extends ValidatorForm  {
  private String txtUserId;
  private String cboUserCategory;
  private String txtUserFirstName;
  private String txtUserLastName;
  private int txtTreeViewLevel;
  private int txtNumberOfRecords;
  private int hdnUserProfileTblPk;

  public String getTxtUserId() {
    return txtUserId;
  }

  public void setTxtUserId(String newTxtUserId) {
    txtUserId = newTxtUserId;
  }

  public String getCboUserCategory() {
    return cboUserCategory;
  }

  public void setCboUserCategory(String newCboUserCategory) {
    cboUserCategory = newCboUserCategory;
  }

  public String getTxtUserFirstName() {
    return txtUserFirstName;
  }

  public void setTxtUserFirstName(String newTxtUserFirstName) {
    txtUserFirstName = newTxtUserFirstName;
  }

  public String getTxtUserLastName() {
    return txtUserLastName;
  }

  public void setTxtUserLastName(String newTxtUserLastName) {
    txtUserLastName = newTxtUserLastName;
  }

  public int getTxtTreeViewLevel() {
    return txtTreeViewLevel;
  }

  public void setTxtTreeViewLevel(int newTxtTreeViewLevel) {
    txtTreeViewLevel = newTxtTreeViewLevel;
  }

  public int getTxtNumberOfRecords() {
    return txtNumberOfRecords;
  }

  public void setTxtNumberOfRecords(int newTxtNumberOfRecords) {
    txtNumberOfRecords = newTxtNumberOfRecords;
  }

  public int getHdnUserProfileTblPk() {
    return hdnUserProfileTblPk;
  }

  public void setHdnUserProfileTblPk(int newHdnUserProfileTblPk) {
    hdnUserProfileTblPk = newHdnUserProfileTblPk;
  }
  
}