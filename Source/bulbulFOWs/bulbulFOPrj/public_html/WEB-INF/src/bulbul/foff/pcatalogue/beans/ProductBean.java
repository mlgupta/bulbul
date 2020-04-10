package bulbul.foff.pcatalogue.beans;
import java.util.ArrayList;

public class ProductBean  {
  private String merchandiseTblPk;
  private String name;
  private String description;
  private String comment;
  private String materialDetail;
  private String minQty;
  private String deliveryNote;
  private String noOfColors;
  private ArrayList colors;
  private String productOId;
  private String productContentType;
  private String productContentSize;

  public String getMerchandiseTblPk() {
    return merchandiseTblPk;
  }

  public void setMerchandiseTblPk(String merchandiseTblPk) {
    this.merchandiseTblPk = merchandiseTblPk;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getMaterialDetail() {
    return materialDetail;
  }

  public void setMaterialDetail(String materialDetail) {
    this.materialDetail = materialDetail;
  }

  public String getMinQty() {
    return minQty;
  }

  public void setMinQty(String minQty) {
    this.minQty = minQty;
  }

  public String getDeliveryNote() {
    return deliveryNote;
  }

  public void setDeliveryNote(String deliveryNote) {
    this.deliveryNote = deliveryNote;
  }

  public String getNoOfColors() {
    return noOfColors;
  }

  public void setNoOfColors(String noOfColors) {
    this.noOfColors = noOfColors;
  }

  public ArrayList getColors() {
    return colors;
  }

  public void setColors(ArrayList colors) {
    this.colors = colors;
  }

  public String getProductOId() {
    return productOId;
  }

  public void setProductOId(String productOId) {
    this.productOId = productOId;
  }

  public String getProductContentType() {
    return productContentType;
  }

  public void setProductContentType(String productContentType) {
    this.productContentType = productContentType;
  }

  public String getProductContentSize() {
    return productContentSize;
  }

  public void setProductContentSize(String productContentSize) {
    this.productContentSize = productContentSize;
  }

}