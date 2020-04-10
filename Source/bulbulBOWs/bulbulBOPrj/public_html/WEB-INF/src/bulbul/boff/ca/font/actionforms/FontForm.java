package bulbul.boff.ca.font.actionforms;

import org.apache.struts.upload.FormFile;
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

FontForm extends ValidatorForm  {
  private int hdnFontTblPk;
  private int hdnFontCategoryTblPk;
  private String txtFont;
  private String txaFontDesc;
  private String hdnSearchPageNo;
  private FormFile imgFile;
  private String hdnContentType;
  private int hdnContentSize;
  private String hdnImgOid;
  private String txtParentCategory;

  /**
   * Purpose : To Get The Primary Key Of The Selected Radio Button.
   * @return long.
   */
  public int getHdnFontTblPk() {
    return hdnFontTblPk;
  }

  /**
   * Purpose : Sets the value of the hdnFontTblPk.
   * @param newHdnFontTblPk - A long object.
   */ 
  public void setHdnFontTblPk(int newHdnFontTblPk) {
    hdnFontTblPk = newHdnFontTblPk;
  }

  /**
   * Purpose : Returns the primary key of font_category_tbl.
   * @return long.
   */  
  public int getHdnFontCategoryTblPk() {
    return hdnFontCategoryTblPk;
  }

  /**
   * Purpose : Sets the value of hdnFontCategoryTblPk.
   * @param newHdnFontCategoryTblPk - A String object.
   */  
  public void setHdnFontCategoryTblPk(int newHdnFontCategoryTblPk) {
    hdnFontCategoryTblPk = newHdnFontCategoryTblPk;
  }

  /**
   * Purpose : Returns the name of the font.
   * @return String.
   */  
  public String getTxtFont() {
    return txtFont;
  }

  /**
   * Purpose : Sets the name of the font.
   * @param newTxtFont - A String object.
   */  
  public void setTxtFont(String newTxtFont) {
    txtFont = newTxtFont;
  }

  /**
   * Purpose : Returns the description about the font.
   * @return String.
   */
  public String getTxaFontDesc() {
    return txaFontDesc;
  }

  /**
   * Purpose : Sets the description about the font.
   * @param newTxaFontDesc - A String object.
   */  
  public void setTxaFontDesc(String newTxaFontDesc) {
    txaFontDesc = newTxaFontDesc;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public FormFile getImgFile() {
    return imgFile;
  }

  public void setImgFile(FormFile newImgFile) {
    imgFile = newImgFile;
  }

  public String getHdnContentType() {
    return hdnContentType;
  }

  public void setHdnContentType(String newHdnContentType) {
    hdnContentType = newHdnContentType;
  }

  public int getHdnContentSize() {
    return hdnContentSize;
  }

  public void setHdnContentSize(int newHdnContentSize) {
    hdnContentSize = newHdnContentSize;
  }

  public String getHdnImgOid() {
    return hdnImgOid;
  }

  public void setHdnImgOid(String newHdnImgOid) {
    hdnImgOid = newHdnImgOid;
  }

  public String getTxtParentCategory() {
    return txtParentCategory;
  }

  public void setTxtParentCategory(String txtParentCategory) {
    this.txtParentCategory = txtParentCategory;
  }



}