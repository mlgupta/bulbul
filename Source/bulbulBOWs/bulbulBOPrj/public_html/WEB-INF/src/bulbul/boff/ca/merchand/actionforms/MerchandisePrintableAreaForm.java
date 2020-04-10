package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


public class MerchandisePrintableAreaForm extends ValidatorForm  {
private long hdnMerchandiseTblPk;
  private long hdnMerchandiseCategoryTblPk;
  private String txtHeight;
  private String hdnSearchPageNo;
  private FormFile imgFile;
  private String hdnContentType;
  private String hdnContentSize;
  private String hdnImgOid;
  private String txtWidth;

  /**
   * Purpose : To Get The Primary Key Of The Selected Radio Button.
   * @return long.
   */
  public long getHdnMerchandiseTblPk() {
    return hdnMerchandiseTblPk;
  }

  /**
   * Purpose : Sets the value of the hdnFontTblPk.
   * @param newHdnFontTblPk - A long object.
   */ 
  public void setHdnMerchandiseTblPk(long hdnMerchandiseTblPk) {
    this.hdnMerchandiseTblPk = hdnMerchandiseTblPk;
  }

  /**
   * Purpose : Returns the primary key of font_category_tbl.
   * @return long.
   */  
  public long getHdnMerchandiseCategoryTblPk() {
    return hdnMerchandiseCategoryTblPk;
  }

  /**
   * Purpose : Sets the value of hdnFontCategoryTblPk.
   * @param newHdnFontCategoryTblPk - A String object.
   */  
  public void setHdnMerchandiseCategoryTblPk(long hdnMerchandiseCategoryTblPk) {
    this.hdnMerchandiseCategoryTblPk = hdnMerchandiseCategoryTblPk;
  }

  /**
   * Purpose : Returns the name of the font.
   * @return String.
   */  
  public String getTxtHeight() {
    return txtHeight;
  }

  /**
   * Purpose : Sets the name of the font.
   * @param newTxtFont - A String object.
   */  
  public void setTxtHeight(String txtHeight) {
    this.txtHeight = txtHeight;
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

  public String getHdnContentSize() {
    return hdnContentSize;
  }

  public void setHdnContentSize(String newHdnContentSize) {
    hdnContentSize = newHdnContentSize;
  }

  public String getHdnImgOid() {
    return hdnImgOid;
  }

  public void setHdnImgOid(String newHdnImgOid) {
    hdnImgOid = newHdnImgOid;
  }

  public String getTxtWidth() {
    return txtWidth;
  }

  public void setTxtWidth(String txtWidth) {
    this.txtWidth = txtWidth;
  }



}