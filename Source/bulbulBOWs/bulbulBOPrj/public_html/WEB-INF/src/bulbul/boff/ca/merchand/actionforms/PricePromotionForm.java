package bulbul.boff.ca.merchand.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class PricePromotionForm extends ValidatorForm  {
  private int hdnPricePromotionTblPk;
  private String[] cboColor;
  private String[] cboSize;
  private float txtUnitPrice;
  private String txtStartDate;
  private String txtEndDate;
  private int txtThresholdQty;
  private float txtDiscount;
  private int radExclusiveByPass;
  private int hdnMerchandiseTblPk;
  private int hdnMerchandiseSizeTblPk;
  private int hdnMerchandiseCategoryTblPk;
  private String hdnSearchPageNo;

  public int getHdnPricePromotionTblPk() {
    return hdnPricePromotionTblPk;
  }

  public void setHdnPricePromotionTblPk(int newHdnPricePromotionTblPk) {
    hdnPricePromotionTblPk = newHdnPricePromotionTblPk;
  }

  public String[] getCboColor() {
    return cboColor;
  }

  public void setCboColor(String[] newCboColor) {
    cboColor = newCboColor;
  }

  public String[] getCboSize() {
    return cboSize;
  }

  public void setCboSize(String[] newCboSize) {
    cboSize = newCboSize;
  }

  public float getTxtUnitPrice() {
    return txtUnitPrice;
  }

  public void setTxtUnitPrice(float newTxtUnitPrice) {
    txtUnitPrice = newTxtUnitPrice;
  }

  public String getTxtStartDate() {
    return txtStartDate;
  }

  public void setTxtStartDate(String newTxtStartDate) {
    txtStartDate = newTxtStartDate;
  }

  public String getTxtEndDate() {
    return txtEndDate;
  }

  public void setTxtEndDate(String newTxtEndDate) {
    txtEndDate = newTxtEndDate;
  }

  public int getTxtThresholdQty() {
    return txtThresholdQty;
  }

  public void setTxtThresholdQty(int newTxtThresholdQty) {
    txtThresholdQty = newTxtThresholdQty;
  }

  public float getTxtDiscount() {
    return txtDiscount;
  }

  public void setTxtDiscount(float newTxtDiscount) {
    txtDiscount = newTxtDiscount;
  }

  public int getRadExclusiveByPass() {
    return radExclusiveByPass;
  }

  public void setRadExclusiveByPass(int newRadExclusiveByPass) {
    radExclusiveByPass = newRadExclusiveByPass;
  }

  public int getHdnMerchandiseTblPk() {
    return hdnMerchandiseTblPk;
  }

  public void setHdnMerchandiseTblPk(int newHdnMerchandiseTblPk) {
    hdnMerchandiseTblPk = newHdnMerchandiseTblPk;
  }

  public int getHdnMerchandiseSizeTblPk() {
    return hdnMerchandiseSizeTblPk;
  }

  public void setHdnMerchandiseSizeTblPk(int newHdnMerchandiseSizeTblPk) {
    hdnMerchandiseSizeTblPk = newHdnMerchandiseSizeTblPk;
  }

  public int getHdnMerchandiseCategoryTblPk() {
    return hdnMerchandiseCategoryTblPk;
  }

  public void setHdnMerchandiseCategoryTblPk(int newHdnMerchandiseCategoryTblPk) {
    hdnMerchandiseCategoryTblPk = newHdnMerchandiseCategoryTblPk;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }




  
}