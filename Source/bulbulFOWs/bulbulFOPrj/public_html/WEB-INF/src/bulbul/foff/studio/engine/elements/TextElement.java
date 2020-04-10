package bulbul.foff.studio.engine.elements;

import bulbul.foff.studio.engine.Studio;
import bulbul.foff.studio.common.Constants;

import bulbul.foff.studio.common.DesignElementProperty;
import bulbul.foff.studio.common.DesignElementType;

import java.util.Random;

public class TextElement extends AbstractDesignElement{
  private String text;
  public TextElement(Studio studio,String text) {
    super(studio);
    
    this.text=text;
    
    specificProperties.put("text",text);
    
    actionURL="textDisplayAction.do";
    elementType = DesignElementType.TEXT;
  }

  public void accessPropertySheet(boolean show) {
    Object[] args=null;
    if(show){
      args=new Object[3];
      args[0]=text;
      args[1]=getStyle();
      args[2]=Double.toString(getRotate());
      studio.getBrowserWindow().call("showTextPropertySheet",args);
    }else{
      studio.getBrowserWindow().call("hideTextPropertySheet",null);
    }
  }

  public void setText(String text) {
    this.text = text;
    specificProperties.put(DesignElementProperty.TEXT,text);
    scaleImage();
  }

  public String getText() {
    return text;
  }
}
