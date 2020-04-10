package bulbul.foff.studio.common;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * 
 * @description 
 * @version 1.0 23-Sep-2005
 * @author Sudheer V Pujar
 */
public class SaveDesignBean implements Serializable {
  public static final int SAVE=0;
  public static final int SAVE_AS=1;
  
  private boolean designNew=true;
  private boolean customerNew=true;
  private boolean design2OverWrite=false;
  
  private String merchandiseTblPk;
  private String merchandiseColorTblPk;
  private String customerEmailIdTblPk;
  private String customerEmailId;
  private String customerDesignTblPk;
  private String customerDesignName;
  
  private Hashtable designContentTable;
  
  private int saveNdSaveAs;


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
  public Hashtable getDesignContentTable() {
    return designContentTable;
  }

  /**
   * 
   * @description 
   * @param designContentTable
   */
  public void setDesignContentTable(Hashtable designContentTable) {
    this.designContentTable = designContentTable;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public int getSaveNdSaveAs() {
    return saveNdSaveAs;
  }

  /**
   * 
   * @description 
   * @param saveNdSaveAs
   */
  public void setSaveNdSaveAs(int saveNdSaveAs) {
    this.saveNdSaveAs = saveNdSaveAs;
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