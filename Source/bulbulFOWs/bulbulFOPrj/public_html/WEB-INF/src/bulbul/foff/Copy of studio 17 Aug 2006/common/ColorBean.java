package bulbul.foff.studio.common;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @description 
 * @version 1.0 04-Nov-2005
 * @author Sudheer V. Pujar
 */
public class ColorBean implements Serializable{
  private String colorOneName;
  private String colorTwoName;
  private String colorOneValue;
  private String colorTwoValue;
  private ArrayList size;
  private String merchandiseColorTblPk;

  /**
   * 
   * @description 
   * @return 
   */
  public String getColorOneName() {
    return colorOneName;
  }

  /**
   * 
   * @description 
   * @param colorOneName
   */
  public void setColorOneName(String colorOneName) {
    this.colorOneName = colorOneName;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getColorTwoName() {
    return colorTwoName;
  }

  /**
   * 
   * @description 
   * @param colorTwoName
   */
  public void setColorTwoName(String colorTwoName) {
    this.colorTwoName = colorTwoName;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getColorOneValue() {
    return colorOneValue;
  }

  /**
   * 
   * @description 
   * @param colorOneValue
   */
  public void setColorOneValue(String colorOneValue) {
    this.colorOneValue = colorOneValue;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getColorTwoValue() {
    return colorTwoValue;
  }

  /**
   * 
   * @description 
   * @param colorTwoValue
   */
  public void setColorTwoValue(String colorTwoValue) {
    this.colorTwoValue = colorTwoValue;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public ArrayList getSize() {
    return size;
  }

  /**
   * 
   * @description 
   * @param size
   */
  public void setSize(ArrayList size) {
    this.size = size;
  }

  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  public void setMerchandiseColorTblPk(String merchandiseColorTblPk) {
    this.merchandiseColorTblPk = merchandiseColorTblPk;
  }
  
}