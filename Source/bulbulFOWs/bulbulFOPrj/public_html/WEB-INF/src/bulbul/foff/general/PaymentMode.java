package bulbul.foff.general;
import java.util.HashMap;

public final class PaymentMode  {

/*
Mode of Payment                           
1. Credit Card                                
2. Debit Card                                 
3. Internet Banking                                
4. Cheque Payment                                                       
5. Cash at Cash Points                              
6. Cash on Delivery
*/
  public final int KEY;
  public final String VALUE;
  private static HashMap map = new HashMap();
  private PaymentMode(int key,String value){
    this.KEY=key;
    this.VALUE=value;
    map.put(new Integer(this.KEY),this.VALUE);
  }    
  public static String getValue(int key){
    return (String)map.get(new Integer(key));
  }
  public static final PaymentMode CREDIT_CARD= new PaymentMode(1,"Credit Card");
  public static final PaymentMode DEBIT_CARD=new PaymentMode(2,"Debit Card");
  public static final PaymentMode INET_BANK=new PaymentMode(3,"Internet Bank");
  public static final PaymentMode CHEQUE_OR_DD_PAYMENT=new PaymentMode(4,"Cheque/DD");
  public static final PaymentMode CASH_AT_CASH_PONT=new PaymentMode(5,"Cash @ Cash Point");
  public static final PaymentMode CASH_ON_DELIVERY=new PaymentMode(6,"Cash On Delivery");

}