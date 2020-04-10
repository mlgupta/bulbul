package bulbul.boff.user.beans;

/**
 *	Purpose: To Populate the User Profile List Table in user_profile_list.jsp
 *  @author              Amit Mishra
 *  @version             1.0
 * 	Date of creation:   29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
public class UserProfileListBean  {
  private String userProfileTblPk;
  private String userId;
  private String userFirstName;
  private String userLastName;
  private String userCategory;
  private String isActive;
  private String isActiveDisplay;
 
  /**
   * Purpose : Returns UserProfile table Primary Key.
   * @return String.
   */
  public String getUserProfileTblPk() {
    return userProfileTblPk;
  }
 
  /**
   * Purpose : Sets UserProfile Table Primary Key.
   * @param newUserProfileTblPk - A String Object.
   */
  public void setUserProfileTblPk(String newUserProfileTblPk) {
    userProfileTblPk = newUserProfileTblPk;
  }
 
  /**
   * Purpose : Returns userId.
   * @return String.
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Purpose : Sets The Value Of userId.
   * @param newUserId - A String Object.
   */
  public void setUserId(String newUserId) {
    userId = newUserId;
  }

  /**
   * Purpose : Returns userFirstName.
   * @return String.
   */
  public String getUserFirstName() {
    return userFirstName;
  }

  /**
   * Purpose : Sets The Value Of userFirstName.
   * @param newUserFirstName - A String Object.
   */
  public void setUserFirstName(String newUserFirstName) {
    userFirstName = newUserFirstName;
  }

  /**
   * Purpose : Returns userLastName.
   * @return String.
   */
  public String getUserLastName() {
    return userLastName;
  }

  /**
   * Purpose : Sets The Value Of userLastName.
   * @param newUserLastName - A String Object.
   */
  public void setUserLastName(String newUserLastName) {
    userLastName = newUserLastName;
  }

  /**
   * Purpose : Returns userCategory.
   * @return String.
   */
  public String getUserCategory() {
    return userCategory;
  }

  /**
   * Purpose : Sets The Value Of userCategory.
   * @param newUserCategory - A String Object.
   */
  public void setUserCategory(String newUserCategory) {
    userCategory = newUserCategory;
  }

  /**
   * Purpose : Returns isActive.
   * @return String.
   */
  public String getIsActive() {
    return isActive;
  }

  /**
   * Purpose : Sets The Value Of isActive.
   * @param newIsActive - A String Object.
   */
  public void setIsActive(String newIsActive) {
    isActive = newIsActive;
  }

  /**
   * Purpose : Returns isActiveDisplay.
   * @return String.
   */
  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }

  /**
   * Purpose : Sets The Value Of isActiveDisplay.
   * @param newIsActiveDisplay - A String Object.
   */
  public void setIsActiveDisplay(String newIsActiveDisplay) {
    isActiveDisplay = newIsActiveDisplay;
  }

}