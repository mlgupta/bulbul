package bulbul.boff.inetbank.actionforms;

import org.apache.struts.validator.ValidatorForm;


/**
 *	Purpose: To store and retrieve the values of the html controls of InetBankForm
 *           in inet_bank_add.jsp,inet_bank_edit.jsp and inet_bank_view.jsp.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

InetBankForm extends ValidatorForm  {
  private int hdnInetBankTblPk;
  private String txtBankName;
  private String txtBankAcNo;
  private String hdnSearchPageNo;
  private String txtBankRoutingNo;

  /**
   * Purpose : Returns the primary key from the  Internet Bank Table.
   * @return long.
   */  
  public int getHdnInetBankTblPk() {
    return hdnInetBankTblPk;
  }

  /**
   * Purpose : Sets the value of the hdnInetBankTblPk
   * @param newHdnInetBankTblPk - A long object.
   */  
  public void setHdnInetBankTblPk(int newHdnInetBankTblPk) {
    hdnInetBankTblPk = newHdnInetBankTblPk;
  }

  /**
   * Purpose : Returns the name of the internet bank.
   * @return String.
   */  
  public String getTxtBankName() {
    return txtBankName;
  }

  /**
   * Purpose : Sets the name of the internet bank.
   * @param newTxtBankName - A String object.
   */  
  public void setTxtBankName(String newTxtBankName) {
    txtBankName = newTxtBankName;
  }

  /**
   * Purpose : Returns the account number of the internet bank.
   * @return String.
   */  
  public String getTxtBankAcNo() {
    return txtBankAcNo;
  }

  /**
   * Purpose : Sets the account number of the internet bank.
   * @param newTxtBankAcNo - A String object.
   */  
  public void setTxtBankAcNo(String newTxtBankAcNo) {
    txtBankAcNo = newTxtBankAcNo;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getTxtBankRoutingNo() {
    return txtBankRoutingNo;
  }

  public void setTxtBankRoutingNo(String txtBankRoutingNo) {
    this.txtBankRoutingNo = txtBankRoutingNo;
  }
}