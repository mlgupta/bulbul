package bulbul.boff.inetbank.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To store and retrieve the values of the html controls of InetBankListForm
 *           in inet_bank_list.jsp.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     29-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankListForm extends ValidatorForm  {
  private String txtSearchBankName;
  private String txtSearchBankAcNo;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchOperation;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;
  private String txtSearchBankRoutingNo;

  /**
   * Purpose : Returns the name of the internet bank.
   * @return String.
   */ 
  public String getTxtSearchBankName() {
    return txtSearchBankName;
  }

  /**
   * Purpose : Sets the name of the internet bank.
   * @param newTxtSearchBankName - A String object.
   */  
  public void setTxtSearchBankName(String newTxtSearchBankName) {
    txtSearchBankName = newTxtSearchBankName;
  }

  /**
   * Purpose : Returns the account number of the internet bank.
   * @return String.
   */ 
  public String getTxtSearchBankAcNo() {
    return txtSearchBankAcNo;
  }

  /**
   * Purpose : Sets the account number of the internet bank.
   * @param newTxtSearchBankAcNo - A String object.
   */  
  public void setTxtSearchBankAcNo(String newTxtSearchBankAcNo) {
    txtSearchBankAcNo = newTxtSearchBankAcNo;
  }

  /**
   * Purpose : Returns radSearchStatus.
   * @return String.
   */ 
  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  /**
   * Purpose : Sets the value of radSearchStatus.
   * @param newRadSearchStatus - A String object.
   */  
  public void setRadSearchStatus(String newRadSearchStatus) {
    radSearchStatus = newRadSearchStatus;
  }

  /**
   * Purpose : Returns radSearchSelect.
   * @return String.
   */ 
  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  /**
   * Purpose : Sets the value of radSearchSelect.
   * @param newRadSearchSelect - A String object.
   */  
  public void setRadSearchSelect(String newRadSearchSelect) {
    radSearchSelect = newRadSearchSelect;
  }

  /**
   * Purpose : Returns hdnSearchPageNo.
   * @return String.
   */ 
  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  /**
   * Purpose : Sets the value of hdnSearchPageNo.
   * @param newHdnSearchPageNo - A String object.
   */  
  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  /**
   * Purpose : Returns hdnSearchOperation.
   * @return String.
   */ 
  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  /**
   * Purpose : Sets the value of hdnSearchOperation.
   * @param newHdnSearchOperation - A String object.
   */  
  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  /**
   * Purpose : Returns hdnSearchCurrentStatus.
   * @return String.
   */ 
  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  /**
   * Purpose : Sets the value of hdnSearchCurrentStatus.
   * @param newHdnSearchCurrentStatus - A String object.
   */  
  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;
  }

  /**
   * Purpose : Returns hdnSearchPageCount.
   * @return String.
   */ 
  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  /**
   * Purpose : Sets the value of hdnSearchPageCount.
   * @param newHdnSearchPageCount - A String object.
   */  
  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }

  public String getTxtSearchBankRoutingNo() {
    return txtSearchBankRoutingNo;
  }

  public void setTxtSearchBankRoutingNo(String txtSearchBankRoutingNo) {
    this.txtSearchBankRoutingNo = txtSearchBankRoutingNo;
  }
}