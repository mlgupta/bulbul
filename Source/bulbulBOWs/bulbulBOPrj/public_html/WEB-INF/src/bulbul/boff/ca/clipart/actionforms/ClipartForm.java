package bulbul.boff.ca.clipart.actionforms;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


public class ClipartForm extends ValidatorForm  {
  private int hdnClipartTblPk;
  private int hdnClipartCategoryTblPk;
  private String txtClipart;
  private String txaClipartKeywords;
  private FormFile imgFile;
  private String hdnImgOid;
  private String hdnContentType;
  private int hdnContentSize;
  private String hdnSearchPageNo;
  private String txtParentCategory;
  private String hdnStdImgOid;
  private String hdnStdContentType;
  private int hdnStdContentSize;

  public int getHdnClipartTblPk() {
    return hdnClipartTblPk;
  }

  public void setHdnClipartTblPk(int newHdnClipartTblPk) {
    hdnClipartTblPk = newHdnClipartTblPk;
  }

  public int getHdnClipartCategoryTblPk() {
    return hdnClipartCategoryTblPk;
  }

  public void setHdnClipartCategoryTblPk(int newHdnClipartCategoryTblPk) {
    hdnClipartCategoryTblPk = newHdnClipartCategoryTblPk;
  }

  public String getTxtClipart() {
    return txtClipart;
  }

  public void setTxtClipart(String newTxtClipart) {
    txtClipart = newTxtClipart;
  }

  public String getTxaClipartKeywords() {
    return txaClipartKeywords;
  }

  public void setTxaClipartKeywords(String newTxaClipartKeywords) {
    txaClipartKeywords = newTxaClipartKeywords;
  }



  public FormFile getImgFile() {
    return imgFile;
  }

  public void setImgFile(FormFile newImgFile) {
    imgFile = newImgFile;
  }

  public String getHdnImgOid() {
    return hdnImgOid;
  }

  public void setHdnImgOid(String newHdnImgOid) {
    hdnImgOid = newHdnImgOid;
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

  public void setHdnStdImgOid(String hdnStdImgOid) {
    this.hdnStdImgOid = hdnStdImgOid;
  }

  public String getHdnStdImgOid() {
    return hdnStdImgOid;
  }

  public void setHdnStdContentType(String hdnStdContentType) {
    this.hdnStdContentType = hdnStdContentType;
  }

  public String getHdnStdContentType() {
    return hdnStdContentType;
  }

  public void setHdnStdContentSize(int hdnStdContentSize) {
    this.hdnStdContentSize = hdnStdContentSize;
  }

  public int getHdnStdContentSize() {
    return hdnStdContentSize;
  }
}
