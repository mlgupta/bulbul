package bulbul.foff.studio.common;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * @description 
 * @version 1.0 04-Nov-2005
 * @author Sudheer V. Pujar
 */
public class MerchandiseViewBean  implements Serializable{
  private String merchandiseTblPk;
  private String merhandiseName;
  private String merchandiseDescription;
  private String merchandiseComment;
  private String materialDescription;
  private String deliveryNote;
  private String minimumQuantity;
  private byte[] imageData;
  private Hashtable color;

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
  public String getMerhandiseName() {
    return merhandiseName;
  }

  /**
   * 
   * @description 
   * @param merhandiseName
   */
  public void setMerhandiseName(String merhandiseName) {
    this.merhandiseName = merhandiseName;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMerchandiseDescription() {
    return merchandiseDescription;
  }

  /**
   * 
   * @description 
   * @param merchandiseDescription
   */
  public void setMerchandiseDescription(String merchandiseDescription) {
    this.merchandiseDescription = merchandiseDescription;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMerchandiseComment() {
    return merchandiseComment;
  }

  /**
   * 
   * @description 
   * @param merchandiseComment
   */
  public void setMerchandiseComment(String merchandiseComment) {
    this.merchandiseComment = merchandiseComment;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMaterialDescription() {
    return materialDescription;
  }

  /**
   * 
   * @description 
   * @param materialDescription
   */
  public void setMaterialDescription(String materialDescription) {
    this.materialDescription = materialDescription;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getDeliveryNote() {
    return deliveryNote;
  }

  /**
   * 
   * @description 
   * @param deliveryNote
   */
  public void setDeliveryNote(String deliveryNote) {
    this.deliveryNote = deliveryNote;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getMinimumQuantity() {
    return minimumQuantity;
  }

  /**
   * 
   * @description 
   * @param minimumQuantity
   */
  public void setMinimumQuantity(String minimumQuantity) {
    this.minimumQuantity = minimumQuantity;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public byte[] getImageData() {
    return imageData;
  }

  /**
   * 
   * @description 
   * @param imageData
   */
  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Hashtable getColor() {
    return color;
  }

  /**
   * 
   * @description 
   * @param color
   */
  public void setColor(Hashtable color) {
    this.color = color;
  }
 
}