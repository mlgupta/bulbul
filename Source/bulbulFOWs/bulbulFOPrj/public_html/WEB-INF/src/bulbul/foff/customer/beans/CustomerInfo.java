package bulbul.foff.customer.beans;
import bulbul.foff.order.beans.OrderHeaderBean;

import java.util.Hashtable;

public class CustomerInfo{
  private int customerEmailIdTblPk;
  private String customerFirstName;
  private String customerLastName;
  private String targetUrl;
  private String customerEmailId;
  private int customerDesignTblPk;
  private String customerDesignCode;
  private String customerDesignName;
  
  private String customerRegistered;
  private OrderHeaderBean currentOrder;
  private Hashtable customerDesignDetailTblPkTable=new Hashtable(10);

  public int getCustomerEmailIdTblPk(){
    return customerEmailIdTblPk;
  }

  public void setCustomerEmailIdTblPk(int customerEmailIdTblPk){
    this.customerEmailIdTblPk = customerEmailIdTblPk;
  }

  public String getCustomerFirstName(){
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName){
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName(){
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName){
    this.customerLastName = customerLastName;
  }

  public String getTargetUrl(){
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getCustomerEmailId() {
    return customerEmailId;
  }

  public void setCustomerEmailId(String customerEmailId) {
    this.customerEmailId = customerEmailId;
  }

  public int getCustomerDesignTblPk() {
    return customerDesignTblPk;
  }

  public void setCustomerDesignTblPk(int customerDesignTblPk) {
    this.customerDesignTblPk = customerDesignTblPk;
  }

  public String getCustomerRegistered() {
    return customerRegistered;
  }

  public void setCustomerRegistered(String customerRegistered) {
    this.customerRegistered = customerRegistered;
  }

  public OrderHeaderBean getCurrentOrder() {
    return currentOrder;
  }

  public void setCurrentOrder(OrderHeaderBean currentOrder) {
    this.currentOrder = currentOrder;
  }


  public Hashtable getCustomerDesignDetailTblPkTable() {
    return customerDesignDetailTblPkTable;
  }

  public void setCustomerDesignCode(String customerDesignCode) {
    this.customerDesignCode = customerDesignCode;
  }

  public String getCustomerDesignCode() {
    return customerDesignCode;
  }

  public void setCustomerDesignName(String customerDesignName) {
    this.customerDesignName = customerDesignName;
  }

  public String getCustomerDesignName() {
    return customerDesignName;
  }
}
