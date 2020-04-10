package bulbul.foff.studio.beans;

import java.util.ArrayList;

public class DesignBean  {
  private String merchandiseColorTblPk;
  private String productName;
  private String designName;
  private String designCode;
  private String customerDesignTblPk;
  private ArrayList designDetail;
  
  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  public void setMerchandiseColorTblPk(String merchandiseColorTblPk) {
    this.merchandiseColorTblPk = merchandiseColorTblPk;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDesignName() {
    return designName;
  }

  public void setDesignName(String designName) {
    this.designName = designName;
  }

  public String getDesignCode() {
    return designCode;
  }

  public void setDesignCode(String designCode) {
    this.designCode = designCode;
  }

  public String getCustomerDesignTblPk() {
    return customerDesignTblPk;
  }

  public void setCustomerDesignTblPk(String customerDesignTblPk) {
    this.customerDesignTblPk = customerDesignTblPk;
  }

  public void setDesignDetail(ArrayList designDetail) {
    this.designDetail = designDetail;
  }

  public ArrayList getDesignDetail() {
    return designDetail;
  }

}
