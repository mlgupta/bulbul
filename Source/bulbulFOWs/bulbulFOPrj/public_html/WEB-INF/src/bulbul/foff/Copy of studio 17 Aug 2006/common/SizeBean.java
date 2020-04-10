package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 04-Nov-2005
 * @author Sudheer V. Pujar
 */
public class SizeBean  implements Serializable{
  private String sizeTypeId;
  private String sizeTypeDescription;
  private String sizeId;
  private String sizeDescription;

  /**
   * 
   * @description 
   * @return 
   */
  public String getSizeTypeId() {
    return sizeTypeId;
  }

  /**
   * 
   * @description 
   * @param sizeTypeId
   */
  public void setSizeTypeId(String sizeTypeId) {
    this.sizeTypeId = sizeTypeId;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getSizeTypeDescription() {
    return sizeTypeDescription;
  }

  /**
   * 
   * @description 
   * @param sizeTypeDescription
   */
  public void setSizeTypeDescription(String sizeTypeDescription) {
    this.sizeTypeDescription = sizeTypeDescription;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getSizeId() {
    return sizeId;
  }

  /**
   * 
   * @description 
   * @param sizeId
   */
  public void setSizeId(String sizeId) {
    this.sizeId = sizeId;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getSizeDescription() {
    return sizeDescription;
  }

  /**
   * 
   * @description 
   * @param sizeDescription
   */
  public void setSizeDescription(String sizeDescription) {
    this.sizeDescription = sizeDescription;
  }
  
}