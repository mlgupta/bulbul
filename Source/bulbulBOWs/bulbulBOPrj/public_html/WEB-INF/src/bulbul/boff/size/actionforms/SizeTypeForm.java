package bulbul.boff.size.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class SizeTypeForm extends ValidatorForm  {
  private int hdnSizeTypeTblPk;
  private String txtSizeTypeId;
  private String txaSizeTypeDesc;
  private String hdnSearchPageNo;

  public int getHdnSizeTypeTblPk() {
    return hdnSizeTypeTblPk;
  }

  public void setHdnSizeTypeTblPk(int newHdnSizeTypeTblPk) {
    hdnSizeTypeTblPk = newHdnSizeTypeTblPk;
  }

  public String getTxtSizeTypeId() {
    return txtSizeTypeId;
  }

  public void setTxtSizeTypeId(String newTxtSizeTypeId) {
    txtSizeTypeId = newTxtSizeTypeId;
  }

  public String getTxaSizeTypeDesc() {
    return txaSizeTypeDesc;
  }

  public void setTxaSizeTypeDesc(String newTxaSizeTypeDesc) {
    txaSizeTypeDesc = newTxaSizeTypeDesc;
  }





  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }
}