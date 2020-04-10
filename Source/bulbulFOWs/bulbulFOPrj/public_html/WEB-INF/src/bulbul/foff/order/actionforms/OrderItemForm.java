package bulbul.foff.order.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class OrderItemForm extends ValidatorForm  {
  private String[] txtQty;
  private String[] hdnMerchandiseSizeTblPk;
  private String[] hdnOrderItemDetailTblPk;
  private int hdnCustomerDesignTblPk;
  private int hdnCustomerEmailIdTblPk;
  private int hdnOrderDetailTblPk;

  public String[] getTxtQty() {
    return txtQty;
  }

  public void setTxtQty(String[] txtQty) {
    this.txtQty = txtQty;
  }

  public String[] getHdnMerchandiseSizeTblPk() {
    return hdnMerchandiseSizeTblPk;
  }

  public void setHdnMerchandiseSizeTblPk(String[] hdnMerchandiseSizeTblPk) {
    this.hdnMerchandiseSizeTblPk = hdnMerchandiseSizeTblPk;
  }

  public String[] getHdnOrderItemDetailTblPk() {
    return hdnOrderItemDetailTblPk;
  }

  public void setHdnOrderItemDetailTblPk(String[] hdnOrderItemDetailTblPk) {
    this.hdnOrderItemDetailTblPk = hdnOrderItemDetailTblPk;
  }

  public int getHdnCustomerDesignTblPk() {
    return hdnCustomerDesignTblPk;
  }

  public void setHdnCustomerDesignTblPk(int hdnCustomerDesignTblPk) {
    this.hdnCustomerDesignTblPk = hdnCustomerDesignTblPk;
  }

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

  public int getHdnOrderDetailTblPk() {
    return hdnOrderDetailTblPk;
  }

  public void setHdnOrderDetailTblPk(int hdnOrderDetailTblPk) {
    this.hdnOrderDetailTblPk = hdnOrderDetailTblPk;
  }

}