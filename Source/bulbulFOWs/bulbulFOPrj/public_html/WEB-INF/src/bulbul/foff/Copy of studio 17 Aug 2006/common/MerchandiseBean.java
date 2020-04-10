package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 03-Nov-2005
 * @author Sudheer V. Pujar
 */
public class MerchandiseBean implements Serializable  {
  private String pk;
  private String name;
  private String description;
  private boolean category;
  private boolean categoryOnly;
  private boolean childrenAvailable;

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
  public boolean isCategory() {
    return category;
  }

  /**
   * 
   * @description 
   * @param category
   */
  public void setCategory(boolean category) {
    this.category = category;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isCategoryOnly() {
    return categoryOnly;
  }

  /**
   * 
   * @description 
   * @param categoryOnly
   */
  public void setCategoryOnly(boolean categoryOnly) {
    this.categoryOnly = categoryOnly;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isChildrenAvailable() {
    return childrenAvailable;
  }

  /**
   * 
   * @description 
   * @param childrenAvailable
   */
  public void setChildrenAvailable(boolean childrenAvailable) {
    this.childrenAvailable = childrenAvailable;
  }
  
}