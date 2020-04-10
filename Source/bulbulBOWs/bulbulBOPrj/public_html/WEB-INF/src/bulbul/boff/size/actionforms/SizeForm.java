package bulbul.boff.size.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class SizeForm extends ValidatorForm  {
  private int hdnSizeTblPk;
  private String txtSizeId;
  private String txaSizeDesc;
  private int hdnSizeTypeTblPk;
  private String hdnSearchPageNo;
  private String txtSizeTypeId;
  private String hdnSizeTypePageNo;

  public int getHdnSizeTblPk() {
    return hdnSizeTblPk;
  }

  public void setHdnSizeTblPk(int newHdnSizeTblPk) {
    hdnSizeTblPk = newHdnSizeTblPk;
  }

  public String getTxtSizeId() {
    return txtSizeId;
  }

  public void setTxtSizeId(String newTxtSizeId) {
    txtSizeId = newTxtSizeId;
  }

  public String getTxaSizeDesc() {
    return txaSizeDesc;
  }

  public void setTxaSizeDesc(String newTxaSizeDesc) {
    txaSizeDesc = newTxaSizeDesc;
  }



  public int getHdnSizeTypeTblPk() {
    return hdnSizeTypeTblPk;
  }

  public void setHdnSizeTypeTblPk(int newHdnSizeTypeTblPk) {
    hdnSizeTypeTblPk = newHdnSizeTypeTblPk;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getTxtSizeTypeId() {
    return txtSizeTypeId;
  }

  public void setTxtSizeTypeId(String newTxtSizeTypeId) {
    txtSizeTypeId = newTxtSizeTypeId;
  }

  public String getHdnSizeTypePageNo() {
    return hdnSizeTypePageNo;
  }

  public void setHdnSizeTypePageNo(String hdnSizeTypePageNo) {
    this.hdnSizeTypePageNo = hdnSizeTypePageNo;
  }


  
}