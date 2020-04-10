package bulbul.boff.user.actionforms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To store and retrieve the values of the html controls of UserProfileForm
 *           in user_profile_add.jsp,user_profile_edit.jsp and user_profile_view.jsp.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     30-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class
UserProfileForm extends ValidatorForm  {
  private int hdnUserProfileTblPk;
  private String txtUserId;
  private String txtUserFirstName;
  private String txtUserLastName;
  private String txtUserPassword;
  private String cboUserCategory;
  private String[] hdnModules;
  private String[] hdnPermissions;
  private String[] hdnPermissionValues;
  private String[] hdnUserAccessTblPk;
  public ArrayList permissionValues=new ArrayList();
  public ArrayList userAccessTblPks=new ArrayList();
  private String hdnUserCategoryId;
  private String hdnSearchPageNo;

  /**
   * Purpose : Returns the primary key from the  User Profile Table.
   * @return long.
   */ 
  public int getHdnUserProfileTblPk() {
    return hdnUserProfileTblPk;
  }

  /**
   * Purpose : Sets the value of the hdnUserProfileTblPk
   * @param newHdnUserProfileTblPk - A long object.
   */
  public void setHdnUserProfileTblPk(int newHdnUserProfileTblPk) {
    hdnUserProfileTblPk = newHdnUserProfileTblPk;
  }

  /**
   * Purpose : Returns the Id of the user.
   * @return String.
   */
  public String getTxtUserId() {
    return txtUserId;
  }

  /**
   * Purpose : Sets the User Id.
   * @param newTxtUserId - A String object.
   */ 
  public void setTxtUserId(String newTxtUserId) {
    txtUserId = newTxtUserId;
  }

  /**
   * Purpose : Returns the first name of the user.
   * @return String.
   */
  public String getTxtUserFirstName() {
    return txtUserFirstName;
  }

  /**
   * Purpose : Sets the first name of the user.
   * @param newTxtUserFirstName - A String object.
   */
  public void setTxtUserFirstName(String newTxtUserFirstName) {
    txtUserFirstName = newTxtUserFirstName;
  }

  /**
   * Purpose : Returns the last name of the user.
   * @return String.
   */
  public String getTxtUserLastName() {
    return txtUserLastName;
  }

  /**
   * Purpose : Sets the last name of the user.
   * @param newTxtUserLastName - A String object.
   */
  public void setTxtUserLastName(String newTxtUserLastName) {
    txtUserLastName = newTxtUserLastName;
  }

  /**
   * Purpose : Returns the password of the user.
   * @return String.
   */
  public String getTxtUserPassword() {
    return txtUserPassword;
  }

  /**
   * Purpose : Sets the password for the user.
   * @param newTxtUserPassword - A String object.
   */
  public void setTxtUserPassword(String newTxtUserPassword) {
    txtUserPassword = newTxtUserPassword;
  }

  /**
   * Purpose : Returns the category of the user.
   * @return String.
   */
  public String getCboUserCategory() {
    return cboUserCategory;
  }

  /**
   * Purpose : Sets the user category.
   * @param newTxtUserId - A String object.
   */
  public void setCboUserCategory(String newCboUserCategory) {
    cboUserCategory = newCboUserCategory;
  }

  /**
   * Purpose : Returns an array of modules.
   * @return String[].
   */
  public String[] getHdnModules() {
    return hdnModules;
  }

  /**
   * Purpose : Sets hdnModules with an array of module ids.
   * @param newHdnModules - A String[] object.
   */
  public void setHdnModules(String[] newHdnModules) {
    hdnModules = newHdnModules;
  }

  /**
   * Purpose : Returns an array of permissions.
   * @return String[].
   */
  public String[] getHdnPermissions() {
    return hdnPermissions;
  }

  /**
   * Purpose : Sets hdnPermissions with an array of permission ids.
   * @param hdnPermissions - A String[] object.
   */
  public void setHdnPermissions(String[] newHdnPermissions) {
    hdnPermissions = newHdnPermissions;
  }

  /**
   * Purpose : Returns an array of permission values.
   * @return String[].
   */
  public String[] getHdnPermissionValues() {
    return hdnPermissionValues;
  }

  /**
   * Purpose : Sets hdnPermissionValues with an array of permission values.
   * @param newHdnPermissionValues - A String[] object.
   */
  public void setHdnPermissionValues(String[] newHdnPermissionValues) {
    hdnPermissionValues = newHdnPermissionValues;
  }

  /**
   * Purpose : Returns an array of primary keys from access table.
   * @return String[].
   */
  public String[] getHdnUserAccessTblPk() {
    return hdnUserAccessTblPk;
  }

  /**
   * Purpose : Sets hdnUserAccessTblPk with an array of primary keys from access table.
   * @param newHdnUserAccessTblPk - A String[] object.
   */
  public void setHdnUserAccessTblPk(String[] newHdnUserAccessTblPk) {
    hdnUserAccessTblPk = newHdnUserAccessTblPk;
  }

  public String getHdnUserCategoryId() {
    return hdnUserCategoryId;
  }

  public void setHdnUserCategoryId(String newHdnUserCategoryId) {
    hdnUserCategoryId = newHdnUserCategoryId;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }
  
}