package bulbul.boff.ca.font.beans;

/**
 *	Purpose: To Populate the Font List Table in font_list.jsp
 *  @author              Amit Mishra
 *  @version             1.0
 * 	Date of creation:   17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class FontListBean  {
  private String fontOrCategoryTblPk;
  private String fontOrCategoryName;
  private String fontOrCategoryType;
  private String fontOrCategoryDesc;
  private String isActive;
  private String isActiveDisplay;
  
  /**
   * Purpose : Returns Font Or Category Table Primary Key.
   * @return String.
   */
  public String getFontOrCategoryTblPk() {
    return fontOrCategoryTblPk;
  }
 
  /**
   * Purpose : Sets the value of fontOrCategoryTblPk.
   * @param newFontOrCategoryTblPk - A String Object.
   */
  public void setFontOrCategoryTblPk(String newFontOrCategoryTblPk) {
    fontOrCategoryTblPk = newFontOrCategoryTblPk;
  }
 
  /**
   * Purpose : Returns fontOrCategoryName.
   * @return String.
   */
  public String getFontOrCategoryName() {
    return fontOrCategoryName;
  }

  /**
   * Purpose : Sets The Value Of fontOrCategoryName.
   * @param newFontOrCategoryName - A String Object.
   */
  public void setFontOrCategoryName(String newFontOrCategoryName) {
    fontOrCategoryName = newFontOrCategoryName;
  }
 
  /**
   * Purpose : Returns fontOrCategoryType.
   * @return String.
   */
  public String getFontOrCategoryType() {
    return fontOrCategoryType;
  }

  /**
   * Purpose : Sets The Value Of fontOrCategoryType.
   * @param newFontOrCategoryType - A String Object.
   */
  public void setFontOrCategoryType(String newFontOrCategoryType) {
    fontOrCategoryType = newFontOrCategoryType;
  }
 
  /**
   * Purpose : Returns fontOrCategoryDesc.
   * @return String.
   */
  public String getFontOrCategoryDesc() {
    return fontOrCategoryDesc;
  }

  /**
   * Purpose : Sets The Value Of fontOrCategoryDesc.
   * @param newFontOrCategoryDesc - A String Object.
   */
  public void setFontOrCategoryDesc(String newFontOrCategoryDesc) {
    fontOrCategoryDesc = newFontOrCategoryDesc;
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