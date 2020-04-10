package bulbul.boff.ca.font.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To store and retrieve the values of the html controls of FontListForm
 *           in font_list.jsp.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String radSearchFontOrCategory;
  private String txtSearchFontCategory;
  private String txtSearchFontName;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private String hdnSearchFontOrCategoryTblPk;
  private String hdnSearchFontOrCategoryType;

  /**
   * Purpose : Returns hdnSearchOperation.
   * @return String.
   */ 
  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  /**
   * Purpose : Sets the value of hdnSearchOperation.
   * @param newHdnSearchOperation - A String object.
   */  
  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  /**
   * Purpose : Returns either 'Font' or 'Category' depending on the radio button selected.
   * @return String.
   */ 
  public String getRadSearchFontOrCategory() {
    return radSearchFontOrCategory;
  }

  /**
   * Purpose : Sets the value of radSearchFontOrCategory.
   * @param newRadSearchFontOrCategory - A String object.
   */  
  public void setRadSearchFontOrCategory(String newRadSearchFontOrCategory) {
    radSearchFontOrCategory = newRadSearchFontOrCategory;
  }

  /**
   * Purpose : Returns the font category.
   * @return String.
   */ 
  public String getTxtSearchFontCategory() {
    return txtSearchFontCategory;
  }

  /**
   * Purpose : Sets the value of txtSearchFontCategory.
   * @param newTxtSearchFontCategory - A String object.
   */  
  public void setTxtSearchFontCategory(String newTxtSearchFontCategory) {
    txtSearchFontCategory = newTxtSearchFontCategory;
  }

  /**
   * Purpose : Returns the name of the font.
   * @return String.
   */ 
  public String getTxtSearchFontName() {
    return txtSearchFontName;
  }

  /**
   * Purpose : Sets the name of the font.
   * @param newTxtSearchFontName - A String object.
   */  
  public void setTxtSearchFontName(String newTxtSearchFontName) {
    txtSearchFontName = newTxtSearchFontName;
  }

  /**
   * Purpose : Returns the value of radSearchStatus which is 'ACTIVE' or 'INACTIVE'.
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
   * Purpose : Returns hdnSearchPageNo.
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
   * Purpose : Returns hdnSearchStatus.
   * @return String.
   */ 
  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  /**
   * Purpose : Sets the value of hdnSearchStatus.
   * @param newHdnSearchStatus - A String object.
   */  
  public void setHdnSearchStatus(String newHdnSearchStatus) {
    hdnSearchStatus = newHdnSearchStatus;
  }

  /**
   * Purpose : Returns hdnSearchCurrentStatus.
   * @return String.
   */ 
  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  /**
   * Purpose : Sets the value of hdnSearchCurrentStatus.
   * @param newHdnSearchCurrentStatus - A String object.
   */  
  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;
  }

  /**
   * Purpose : Returns hdnSearchPageCount.
   * @return String.
   */ 
  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  /**
   * Purpose : Sets the value of hdnSearchPageCount.
   * @param newHdnSearchPageCount - A String object.
   */  
  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }

  /**
   * Purpose : Returns hdnSearchFontOrCategoryTblPk.
   * @return String.
   */ 
  public String getHdnSearchFontOrCategoryTblPk() {
    return hdnSearchFontOrCategoryTblPk;
  }

  /**
   * Purpose : Sets the value of hdnSearchFontOrCategoryTblPk.
   * @param newHdnSearchFontOrCategoryTblPk - A String object.
   */  
  public void setHdnSearchFontOrCategoryTblPk(String newHdnSearchFontOrCategoryTblPk) {
    hdnSearchFontOrCategoryTblPk = newHdnSearchFontOrCategoryTblPk;
  }

  /**
   * Purpose : Returns hdnSearchFontOrCategoryType(Font or Category).
   * @return String.
   */ 
  public String getHdnSearchFontOrCategoryType() {
    return hdnSearchFontOrCategoryType;
  }

  /**
   * Purpose : Sets the value of hdnSearchFontOrCategoryType.
   * @param newHdnSearchFontOrCategoryType - A String object.
   */  
  public void setHdnSearchFontOrCategoryType(String newHdnSearchFontOrCategoryType) {
    hdnSearchFontOrCategoryType = newHdnSearchFontOrCategoryType;
  }


  
  
}