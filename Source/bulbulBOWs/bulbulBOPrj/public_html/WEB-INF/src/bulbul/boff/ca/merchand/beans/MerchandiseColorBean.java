package bulbul.boff.ca.merchand.beans;

import java.util.ArrayList;

public class MerchandiseColorBean  {
  private String merchandiseColorTblPk;
  private String color1Name;
  private String color1Value;
  private String color2Name;
  private String color2Value;
  private ArrayList merchandiseSizes;

  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  public void setMerchandiseColorTblPk(String newMerchandiseColorTblPk) {
    merchandiseColorTblPk = newMerchandiseColorTblPk;
  }

  public String getColor1Name() {
    return color1Name;
  }

  public void setColor1Name(String newColor1Name) {
    color1Name = newColor1Name;
  }

  public String getColor1Value() {
    return color1Value;
  }

  public void setColor1Value(String newColor1Value) {
    color1Value = newColor1Value;
  }

  public String getColor2Name() {
    return color2Name;
  }

  public void setColor2Name(String newColor2Name) {
    color2Name = newColor2Name;
  }

  public String getColor2Value() {
    return color2Value;
  }

  public void setColor2Value(String newColor2Value) {
    color2Value = newColor2Value;
  }

  public ArrayList getMerchandiseSizes() {
    return merchandiseSizes;
  }

  public void setMerchandiseSizes(ArrayList newMerchandiseSizes) {
    merchandiseSizes = newMerchandiseSizes;
  }
}