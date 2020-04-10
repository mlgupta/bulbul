package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 06-Oct-2005
 * @author Sudheer V Pujar
 */
public class ClipartBean implements Serializable {
  private String pk;
  private String name;
  private String keywords;
  private String contentType;
  private int contentSize;
  private int contentOId;

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
  public String getName() {
    return name;
  }

  /**
   * 
   * @description 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getKeywords() {
    return keywords;
  }

  /**
   * 
   * @description 
   * @param keywords
   */
  public void setKeywords(String keywords) {
    this.keywords = keywords;
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
   * @param contentOId
   */
  public void setContentOId(int contentOId) {
    this.contentOId = contentOId;
  }
  
}