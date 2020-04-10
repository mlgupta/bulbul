package bulbul.foff.studio.engine.general;

/**
 * 
 * @description 
 * @version 1.0 22-Sep-2005
 * @author Sudheer V Pujar
 */
public class Customer  {
  private boolean customerNew=true;
  private boolean designNew=true;
  private boolean design2OverWrite=false;
  
  private String customerEmailIdTblPk;
  private String customerEmailId;
  private String customerDesignTblPk;
  private String customerDesignName;
  private String merchandiseTblPk;
  private String merchandiseColorTblPk;
  

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isCustomerNew() {
    return customerNew;
  }

  /**
   * 
   * @description 
   * @param customerNew
   */
  public void setCustomerNew(boolean customerNew) {
    this.customerNew = customerNew;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerEmailIdTblPk() {
    return customerEmailIdTblPk;
  }

  /**
   * 
   * @description 
   * @param customerEmailIdTblPk
   */
  public void setCustomerEmailIdTblPk(String customerEmailIdTblPk) {
    this.customerEmailIdTblPk = customerEmailIdTblPk;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerEmailId() {
    return customerEmailId;
  }

  /**
   * 
   * @description 
   * @param customerEmailId
   */
  public void setCustomerEmailId(String customerEmailId) {
    this.customerEmailId = customerEmailId;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerDesignTblPk() {
    return customerDesignTblPk;
  }

  /**
   * 
   * @description 
   * @param customerDesignTblPk
   */
  public void setCustomerDesignTblPk(String customerDesignTblPk) {
    this.customerDesignTblPk = customerDesignTblPk;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerDesignName() {
    return customerDesignName;
  }

  /**
   * 
   * @description 
   * @param customerDesignName
   */
  public void setCustomerDesignName(String customerDesignName) {
    this.customerDesignName = customerDesignName;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMerchandiseTblPk() {
    return merchandiseTblPk;
  }

  /**
   * 
   * @description 
   * @param merchandiseTblPk
   */
  public void setMerchandiseTblPk(String merchandiseTblPk) {
    this.merchandiseTblPk = merchandiseTblPk;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  /**
   * 
   * @description 
   * @param merchandiseColorTblPk
   */
  public void setMerchandiseColorTblPk(String merchandiseColorTblPk) {
    this.merchandiseColorTblPk = merchandiseColorTblPk;
  }


  /**
   * 
   * @description 
   * @param designNew
   */
  public void setDesignNew(boolean designNew) {
    this.designNew = designNew;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean isDesignNew() {
    return designNew;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isDesign2OverWrite() {
    return design2OverWrite;
  }

  /**
   * 
   * @description 
   * @param design2OverWrite
   */
  public void setDesign2OverWrite(boolean design2OverWrite) {
    this.design2OverWrite = design2OverWrite;
  }
  
}