package bulbul.boff.ca.font.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To Store And Retrieve The Value Of The Properties Used In JSP. 
 *  Info: This Class Will Store And Return The JSP's Property Values By Its Mutators And Accessors. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     17-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FontCategoryForm extends ValidatorForm  {
  private int hdnFontCategoryTblPk;
  private int hdnFontCategoryTblFk;
  private String txtFontCategory;
  private String txaFontCategoryDesc;
  private String hdnSearchPageNo;
  private String txtParentCategory;

  /**
   * Purpose : To Get The Primary Key Of The Selected Radio Button.
   * @return long.
   */
  public int getHdnFontCategoryTblPk() {
    return hdnFontCategoryTblPk;
  }
  /**
   * Purpose : Sets the value of the hdnFontCategoryTblPk.
   * @param newHdnFontCategoryTblPk - A long object.
   */ 
  public void setHdnFontCategoryTblPk(int newHdnFontCategoryTblPk) {
    hdnFontCategoryTblPk = newHdnFontCategoryTblPk;
  }
  /**
   * Purpose : Returns the foreign key of font_category_tbl.
   * @return long.
   */  
  public int getHdnFontCategoryTblFk() {
    return hdnFontCategoryTblFk;
  }
  /**
   * Purpose : Sets the value of the hdnFontCategoryTblFk.
   * @param newHdnFontCategoryTblFk - A long object.
   */ 
  public void setHdnFontCategoryTblFk(int newHdnFontCategoryTblFk) {
    hdnFontCategoryTblFk = newHdnFontCategoryTblFk;
  }

  /**
   * Purpose : Returns the font category.
   * @return String.
   */  
  public String getTxtFontCategory() {
    return txtFontCategory;
  }

  /**
   * Purpose : Sets the value of txtFontCategory.
   * @param newTxtFontCategory - A String object.
   */  
  public void setTxtFontCategory(String newTxtFontCategory) {
    txtFontCategory = newTxtFontCategory;
  }

  /**
   * Purpose : Returns font description.
   * @return String.
   */  
  public String getTxaFontCategoryDesc() {
    return txaFontCategoryDesc;
  }

  /**
   * Purpose : Sets the value of txaFontCategoryDesc.
   * @param newTxaFontCategoryDesc - A String object.
   */  
  public void setTxaFontCategoryDesc(String newTxaFontCategoryDesc) {
    txaFontCategoryDesc = newTxaFontCategoryDesc;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getTxtParentCategory() {
    return txtParentCategory;
  }

  public void setTxtParentCategory(String txtParentCategory) {
    this.txtParentCategory = txtParentCategory;
  }


 
}