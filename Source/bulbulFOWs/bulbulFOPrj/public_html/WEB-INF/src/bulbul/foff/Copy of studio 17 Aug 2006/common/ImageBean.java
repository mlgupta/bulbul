package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 24-Oct-2005
 * @author Sudheer V. Pujar
 */
public class ImageBean implements Serializable {
  private String pk;
  private String title;
  private String description;
  private int contentOId;
  private String contentType;
  private int contentSize;

  /**
   * 
   * @description 
   * @return 
   */
  public String getPk() {
    return pk;
  }

  /**
   * 
   * @description 
   * @param pk
   */
  public void setPk(String pk) {
    this.pk = pk;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getTitle() {
    return title;
  }

  /**
   * 
   * @description 
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getDescription() {
    return description;
  }

  /**
   * 
   * @description 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public int getContentOId() {
    return contentOId;
  }

  /**
   * 
   * @description 
   * @param contentOid
   */
  public void setContentOId(int contentOId) {
    this.contentOId = contentOId;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * 
   * @description 
   * @param contentType
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public int getContentSize() {
    return contentSize;
  }

  /**
   * 
   * @description 
   * @param contentSize
   */
  public void setContentSize(int contentSize) {
    this.contentSize = contentSize;
  }


  
}