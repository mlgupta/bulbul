package bulbul.foff.customer.mygraphics.actionforms;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class MyGraphicsUploadForm extends ValidatorForm  {
  private String txtGraphicsTitle;
  private String txaGraphicsDescription;
  private FormFile fleGraphics;
  private Map mapGraphicsTitle=new HashMap();
  private Map mapGraphicsDescription=new HashMap();
  private Map mapGraphics=new HashMap();

  public String getTxtGraphicsTitle(int index) {
    return (String) mapGraphicsTitle.get(new Integer(index));
  }
   
  public String[] getTxtGraphicsTitle() {
    return (String[]) mapGraphicsTitle.values().toArray(new String[mapGraphicsTitle.size()]);
  }

  public void setTxtGraphicsTitle(int index,String txtGraphicsTitle) {
    mapGraphicsTitle.put(new Integer (index),txtGraphicsTitle);
  }

  public String getTxaGraphicsDescription(int index) {
    return (String) mapGraphicsDescription.get(new Integer(index));
  }
  
  public String[] getTxaGraphicsDescription() {
    return (String[]) mapGraphicsDescription.values().toArray(new String[mapGraphicsDescription.size()]);
  }

  public void setTxaGraphicsDescription(int index,String txaGraphicsDescription) {
    mapGraphicsDescription.put(new Integer (index),txaGraphicsDescription);
  }

  public FormFile getFleGraphics(int index) {
    return (FormFile) mapGraphics.get(new Integer(index));
  }
  
  public FormFile[] getFleGraphics() {
    return (FormFile[]) mapGraphics.values().toArray(new FormFile[mapGraphics.size()]);
  }

  public void setFleGraphics(int index,FormFile fleGraphics) {
    mapGraphics.put(new Integer (index),fleGraphics);
  }

}