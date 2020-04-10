package bulbul.foff.studio.actionforms;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class CustomImageUploadForm extends ValidatorActionForm {
  private FormFile customImage;

  public void setCustomImage(FormFile customImage) {
    this.customImage = customImage;
  }

  public FormFile getCustomImage() {
    return customImage;
  }
}
