package bulbul.foff.customer.mygraphics.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class MyGraphicsModifyForm extends ValidatorForm  {
  private int hdnCutomerGraphicsTblPk;
  private String txtGraphicsTitle;
  private String txaGraphicsDescription;
  private int hdnContentOId;
  private String hdnContentType;
  private int hdnContentSize;

  public int getHdnCutomerGraphicsTblPk() {
    return hdnCutomerGraphicsTblPk;
  }

  public void setHdnCutomerGraphicsTblPk(int hdnCutomerGraphicsTblPk) {
    this.hdnCutomerGraphicsTblPk = hdnCutomerGraphicsTblPk;
  }

  public String getTxtGraphicsTitle() {
    return txtGraphicsTitle;
  }

  public void setTxtGraphicsTitle(String txtGraphicsTitle) {
    this.txtGraphicsTitle = txtGraphicsTitle;
  }

  public String getTxaGraphicsDescription() {
    return txaGraphicsDescription;
  }

  public void setTxaGraphicsDescription(String txaGraphicsDescription) {
    this.txaGraphicsDescription = txaGraphicsDescription;
  }

  public int getHdnContentOId() {
    return hdnContentOId;
  }

  public void setHdnContentOId(int hdnContentOId) {
    this.hdnContentOId = hdnContentOId;
  }

  public String getHdnContentType() {
    return hdnContentType;
  }

  public void setHdnContentType(String hdnContentType) {
    this.hdnContentType = hdnContentType;
  }

  public int getHdnContentSize() {
    return hdnContentSize;
  }

  public void setHdnContentSize(int hdnContentSize) {
    this.hdnContentSize = hdnContentSize;
  }



}