package bulbul.foff.studio.common;
import java.io.Serializable;

/**
 * 
 * @description 
 * @version 1.0 13-Dec-2005
 * @author Sudheer V Pujar
 */
public class PrintableAreaBean implements Serializable {
  private String merchandisePrintableAreaTblPk;
  private String name;
  private byte[] image;
  private int width;
  private int height;

  /**
   * 
   * @description 
   * @return 
   */
  public String getMerchandisePrintableAreaTblPk() {
    return merchandisePrintableAreaTblPk;
  }

  /**
   * 
   * @description 
   * @param merchandisePrintableAreaTblPk
   */
  public void setMerchandisePrintableAreaTblPk(String merchandisePrintableAreaTblPk) {
    this.merchandisePrintableAreaTblPk = merchandisePrintableAreaTblPk;
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

  public byte[] getImage() {
    return image;
  }

  /**
   * 
   * @description 
   * @param image
   */
  public void setImage(byte[] image) {
    this.image = image;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public int getWidth() {
    return width;
  }

  /**
   * 
   * @description 
   * @param width
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public int getHeight() {
    return height;
  }

  /**
   * 
   * @description 
   * @param height
   */
  public void setHeight(int height) {
    this.height = height;
  }
    
}