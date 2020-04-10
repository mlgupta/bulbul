package bulbul.foff.pcatalogue.actionforms;

import org.apache.struts.validator.ValidatorForm;

public class AddToFavouriteForm extends ValidatorForm   {
  private String cboCategory;
  private String txtCategory;
  private int hdnMerchandiseTblPk;
  private int hdnCustomerEmailIdTblPk;

  public String getCboCategory() {
    return cboCategory;
  }

  public void setCboCategory(String cboCategory) {
    this.cboCategory = cboCategory;
  }

  public String getTxtCategory() {
    return txtCategory;
  }

  public void setTxtCategory(String txtCategory) {
    this.txtCategory = txtCategory;
  }

  public int getHdnMerchandiseTblPk() {
    return hdnMerchandiseTblPk;
  }

  public void setHdnMerchandiseTblPk(int hdnMerchandiseTblPk) {
    this.hdnMerchandiseTblPk = hdnMerchandiseTblPk;
  }

  public int getHdnCustomerEmailIdTblPk() {
    return hdnCustomerEmailIdTblPk;
  }

  public void setHdnCustomerEmailIdTblPk(int hdnCustomerEmailIdTblPk) {
    this.hdnCustomerEmailIdTblPk = hdnCustomerEmailIdTblPk;
  }

}