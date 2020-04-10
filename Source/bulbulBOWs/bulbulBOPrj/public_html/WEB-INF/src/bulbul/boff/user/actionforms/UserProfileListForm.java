package bulbul.boff.user.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To store and retrieve the values of the html controls of UserProfileListForm
 *           in user_profile_list.jsp.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     30-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class
UserProfileListForm extends ValidatorForm  {
  private String txtSearchUserId;
  private String hdnSearchOperation;
  private String txtSearchUserFirstName;
  private String txtSearchUserLastName;
  private String cboSearchUserCategory;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private String hdnUserCategoryId;

  /**
   * Purpose : Returns the Id of the user.
   * @return String.
   */
  public String getTxtSearchUserId() {
    return txtSearchUserId;
  }

  /**
   * Purpose : Sets the Id of the user.
   * @param newTxtSearchUserId - A String object.
   */
  public void setTxtSearchUserId(String newTxtSearchUserId) {
    txtSearchUserId = newTxtSearchUserId;
  }

  /**
   * Purpose : Returns the operation performed in user_profile_list.jsp.
   * @return String.
   */
  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  /**
   * Purpose : Sets the value of operation.
   * @param newHdnSearchOperation - A String object.
   */
  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  /**
   * Purpose : Returns the first name of the user.
   * @return String.
   */
  public String getTxtSearchUserFirstName() {
    return txtSearchUserFirstName;
  }

  /**
   * Purpose : Sets the first name of the user.
   * @param newTxtSearchUserFirstName - A String object.
   */
  public void setTxtSearchUserFirstName(String newTxtSearchUserFirstName) {
    txtSearchUserFirstName = newTxtSearchUserFirstName;
  }

  /**
   * Purpose : Returns the last name of the user.
   * @return String.
   */
  public String getTxtSearchUserLastName() {
    return txtSearchUserLastName;
  }

  /**
   * Purpose : Sets the last name of the user.
   * @param newTxtSearchUserLastName - A String object.
   */
  public void setTxtSearchUserLastName(String newTxtSearchUserLastName) {
    txtSearchUserLastName = newTxtSearchUserLastName;
  }

  /**
   * Purpose : Returns the category of the user.
   * @return String.
   */
  public String getCboSearchUserCategory() {
    return cboSearchUserCategory;
  }

  /**
   * Purpose : Sets the category of the user.
   * @param newCboSearchUserCategory - A String object.
   */
  public void setCboSearchUserCategory(String newCboSearchUserCategory) {
    cboSearchUserCategory = newCboSearchUserCategory;
  }

  /**
   * Purpose : Returns the status of the user.
   * @return String.
   */
  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  /**
   * Purpose : Sets the value of radSearchStatus.
   * @param newRadSearchStatus - A String object.
   */
  public void setRadSearchStatus(String newRadSearchStatus) {
    radSearchStatus = newRadSearchStatus;
  }

  /**
   * Purpose : Returns the value of radSearchSelect.
   * @return String.
   */
  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  /**
   * Purpose : Sets the value of radSearchSelect.
   * @param newRadSearchSelect - A String object.
   */
  public void setRadSearchSelect(String newRadSearchSelect) {
    radSearchSelect = newRadSearchSelect;
  }

  /**
   * Purpose : Returns the page number.
   * @return String.
   */
  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  /**
   * Purpose : Sets the value of hdnSearchPageNo.
   * @param newHdnSearchPageNo - A String object.
   */
  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  /**
   * Purpose : Returns the status of the user.
   * @return String.
   */
  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  /**
   * Purpose : Sets the status of the user.
   * @param newHdnSearchStatus - A String object.
   */
  public void setHdnSearchStatus(String newHdnSearchStatus) {
    hdnSearchStatus = newHdnSearchStatus;
  }

  /**
   * Purpose : Returns the status of the user selected in user_profile_list.jsp.
   * @return String.
   */
  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  /**
   * Purpose : Sets the status of the user selected in user_profile_list.jsp.
   * @param newHdnSearchOperation - A String object.
   */
  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;
  }

  /**
   * Purpose : Returns the value of page counter.
   * @return String.
   */
  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  /**
   * Purpose : Sets the value of page counter.
   * @param newHdnSearchOperation - A String object.
   */
  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }

  public String getHdnUserCategoryId() {
    return hdnUserCategoryId;
  }

  public void setHdnUserCategoryId(String newHdnUserCategoryId) {
    hdnUserCategoryId = newHdnUserCategoryId;
  }
  
}