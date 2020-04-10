package bulbul.foff.pcatalogue.beans;
import java.util.ArrayList;

public class ProductColorBean  {
  private String merchandiseColorTblPk;
  private String color1Name;
  private String color2Name;
  private String color1Value;
  private String color2Value;
  private String noOfSizes;
  private ArrayList sizes;

  public String getMerchandiseColorTblPk() {
    return merchandiseColorTblPk;
  }

  public void setMerchandiseColorTblPk(String merchandiseColorTblPk) {
    this.merchandiseColorTblPk = merchandiseColorTblPk;
  }

  public String getColor1Name() {
    return color1Name;
  }

  public void setColor1Name(String color1Name) {
    this.color1Name = color1Name;
  }

  public String getColor2Name() {
    return color2Name;
  }

  public void setColor2Name(String color2Name) {
    this.color2Name = color2Name;
  }

  public String getColor1Value() {
    return color1Value;
  }

  public void setColor1Value(String color1Value) {
    this.color1Value = color1Value;
  }

  public String getColor2Value() {
    return color2Value;
  }

  public void setColor2Value(String color2Value) {
    this.color2Value = color2Value;
  }

  public String getNoOfSizes() {
    return noOfSizes;
  }

  public void setNoOfSizes(String noOfSizes) {
    this.noOfSizes = noOfSizes;
  }

  public ArrayList getSizes() {
    return sizes;
  }

  public void setSizes(ArrayList sizes) {
    this.sizes = sizes;
  }
}