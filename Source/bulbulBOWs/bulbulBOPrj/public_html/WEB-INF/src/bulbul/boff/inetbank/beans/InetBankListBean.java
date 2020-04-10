package bulbul.boff.inetbank.beans;

/**
 *	Purpose: To Populate the InetBank List Table in inet_bank_list.jsp
 *  @author              Amit Mishra
 *  @version             1.0
 * 	Date of creation:   29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
public class InetBankListBean  {
  private String inetBankTblPk;
  private String bankName;
  private String bankAcNo;
  private String isActive;
  private String isActiveDisplay;
  private String bankRoutingNo;
  
  /**
   * Purpose : Returns InetBank Table Primary Key.
   * @return String.
   */
  public String getInetBankTblPk() {
    return inetBankTblPk;
  }
 
  /**
   * Purpose : Sets InetBank Table Primary Key.
   * @param newInetBankTblPk - A String Object.
   */
  public void setInetBankTblPk(String newInetBankTblPk) {
    inetBankTblPk = newInetBankTblPk;
  }
  
  /**
   * Purpose : Returns bankName.
   * @return String.
   */
  public String getBankName() {
    return bankName;
  }
 
  /**
   * Purpose : Sets The Value Of bankName.
   * @param newBankName - A String Object.
   */
  public void setBankName(String newBankName) {
    bankName = newBankName;
  }
  
  /**
   * Purpose : Returns bankAcNo.
   * @return String.
   */
  public String getBankAcNo() {
    return bankAcNo;
  }

  /**
   * Purpose : Sets The Value Of bankAcNo.
   * @param newBankAcNo - A String Object.
   */
  public void setBankAcNo(String newBankAcNo) {
    bankAcNo = newBankAcNo;
  }
  
  /**
   * Purpose : Returns isActive.
   * @return String.
   */
  public String getIsActive() {
    return isActive;
  }

  /**
   * Purpose : Sets The Value Of isActive.
   * @param newIsActive - A String Object.
   */
  public void setIsActive(String newIsActive) {
    isActive = newIsActive;
  }
  
  /**
   * Purpose : Returns isActiveDisplay.
   * @return String.
   */
  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }

  /**
   * Purpose : Sets The Value Of isActiveDisplay.
   * @param newIsActiveDisplay - A String Object.
   */
  public void setIsActiveDisplay(String newIsActiveDisplay) {
    isActiveDisplay = newIsActiveDisplay;
  }

  public String getBankRoutingNo() {
    return bankRoutingNo;
  }

  public void setBankRoutingNo(String bankRoutingNo) {
    this.bankRoutingNo = bankRoutingNo;
  }
  
}