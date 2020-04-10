package bulbul.boff.user.beans;

import java.util.ArrayList;

public class UserProfile  {
  private int userProfileTblPk;
  private String userId;
  private String userName;
  private ArrayList userRights;
  private int treeviewLevel;
  private int numberOfRecords;

  public int getUserProfileTblPk() {
    return userProfileTblPk;
  }

  public void setUserProfileTblPk(int newUserProfileTblPk) {
    userProfileTblPk = newUserProfileTblPk;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String newUserId) {
    userId = newUserId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String newUserName) {
    userName = newUserName;
  }

  public ArrayList getUserRights(){
    return this.userRights;
  }

  public void setUserRights(ArrayList userRights){
    this.userRights=userRights;
  }

  public int getTreeviewLevel() {
    return treeviewLevel;
  }

  public void setTreeviewLevel(int newTreeviewLevel) {
    treeviewLevel = newTreeviewLevel;
  }

  public int getNumberOfRecords() {
    return numberOfRecords;
  }

  public void setNumberOfRecords(int newNumberOfRecords) {
    numberOfRecords = newNumberOfRecords;
  }
}