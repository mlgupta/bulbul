package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 05-Oct-2005
 * @author Sudheer V Pujar
 */
public class ClipartCategoryBean implements Serializable  {
  
  private String pk;
  private String name;
  private String description;
  private boolean subCategoriesAvailable;

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
  public boolean areSubCategoriesAvailable() {
    return subCategoriesAvailable;
  }

  /**
   * 
   * @description 
   * @param subcategoriesAvailable
   */
  public void setSubCategoriesAvailable(boolean subCategoriesAvailable) {
    this.subCategoriesAvailable = subCategoriesAvailable;
  }

}