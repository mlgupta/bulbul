package bulbul.foff.general;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class OrderStatus  {
    /*
    1. Incomplete
    2. Registered
    3. Redesign
    4. Assigned To Printer (Approved)
    5. Acknowldege by Printer (Under Production)
    6. Production  (Under Production)
    7. Assigned To Merchandiser (Under Production)
    8. Acknowldege by Merchandiser (Under Production)
    9. Shipped
    10. Delivered
    11. Cancelled
    */
    
    public int KEY;
    public String VALUE;
    private static HashMap map = new HashMap();
  
    private OrderStatus(int key,String value){
      this.KEY=key;
      this.VALUE=value;
      map.put(new Integer(this.KEY),this.VALUE);
    }
    
    public static String getValue(int key){
      return (String)map.get(new Integer(key));
    }
    
    public static final OrderStatus INCOMPLETE=new  OrderStatus(1,"Incomplete");
    public static final OrderStatus REGISTERED=new  OrderStatus(2,"Registered");
    public static final OrderStatus REDESIGN=new  OrderStatus(3,"Redesign");
    public static final OrderStatus ASSIGNED_TO_PRINTER=new  OrderStatus(4,"Assigned To Printer");
    public static final OrderStatus ACK_BY_PRINTER=new  OrderStatus(5,"Acknowledge By Printer");
    public static final OrderStatus PRODUCTION=new  OrderStatus(6,"Production");
    public static final OrderStatus ASSIGNED_TO_MERCHANDISER=new  OrderStatus(7,"Assigned To Merchandiser");
    public static final OrderStatus ACK_BY_MERCHANDISER=new  OrderStatus(8,"Acknowledge By Merchandiser");
    public static final OrderStatus SHIPPED=new  OrderStatus(9,"Shipped");
    public static final OrderStatus DELIVERED=new  OrderStatus(10,"Delivered");
    public static final OrderStatus CANCELLED=new  OrderStatus(11,"Cancelled");

}