package bulbul.foff.studio.actionforms;
import org.apache.struts.validator.ValidatorActionForm;

public class DesignSaveForm extends ValidatorActionForm  {
  private String txtDesignName;
  private String txtYourEmailId;
  private String hdnMerchandiseColorTblPk;
  private String hdnDesignOId;
  private String hdnDesignContentType;
  private String hdnDesignContentSize;
  private String hdnProductName;

  public String getTxtDesignName() {
    return txtDesignName;
  }

  public void setTxtDesignName(String txtDesignName) {
    this.txtDesignName = txtDesignName;
  }

  public String getTxtYourEmailId() {
    return txtYourEmailId;
  }

  public void setTxtYourEmailId(String txtYourEmailId) {
    this.txtYourEmailId = txtYourEmailId;
  }

  public String getHdnMerchandiseColorTblPk() {
    return hdnMerchandiseColorTblPk;
  }

  public void setHdnMerchandiseColorTblPk(String hdnMerchandiseColorTblPk) {
    this.hdnMerchandiseColorTblPk = hdnMerchandiseColorTblPk;
  }

  public String getHdnDesignOId() {
    return hdnDesignOId;
  }

  public void setHdnDesignOId(String hdnDesignOId) {
    this.hdnDesignOId = hdnDesignOId;
  }

  public String getHdnDesignContentType() {
    return hdnDesignContentType;
  }

  public void setHdnDesignContentType(String hdnDesignContentType) {
    this.hdnDesignContentType = hdnDesignContentType;
  }

  public String getHdnDesignContentSize() {
    return hdnDesignContentSize;
  }

  public void setHdnDesignContentSize(String hdnDesignContentSize) {
    this.hdnDesignContentSize = hdnDesignContentSize;
  }

  public String getHdnProductName() {
    return hdnProductName;
  }

  public void setHdnProductName(String hdnProductName) {
    this.hdnProductName = hdnProductName;
  }
}