package bulbul.foff.studio.engine.elements;

import bulbul.foff.studio.engine.Studio;

import bulbul.foff.studio.common.Constants;

import bulbul.foff.studio.common.DesignElementProperty;
import bulbul.foff.studio.common.DesignElementType;

import java.util.Random;

public class ClipartElement extends AbstractDesignElement {
  
  public ClipartElement(Studio studio) {
    super(studio);
    actionURL="clipartDisplayAction.do";
    elementType = DesignElementType.CLIPART;
  }

  public void accessPropertySheet(boolean show) {
    Object[] args=null;
    if(show){
      args=new Object[4];
      args[0]=Double.toString(getElementBounds().getSize().getWidth());
      args[1]=Double.toString(getElementBounds().getSize().getHeight());
      args[2]=getStyle();
      args[3]=Double.toString(getRotate());
      studio.getBrowserWindow().call("showClipartPropertySheet",args);
    }else{
      studio.getBrowserWindow().call("hideClipartPropertySheet",args);
    }
  }

  public void setClipartId(String contentOId,String contentType,String contentSize,String dataSourceKey) {
    specificProperties.put(DesignElementProperty.OID ,contentOId);
    specificProperties.put(DesignElementProperty.CONTENT_TYPE,contentType);
    specificProperties.put(DesignElementProperty.CONTENT_SIZE,contentSize);
    specificProperties.put(DesignElementProperty.DATASOURCE_KEY,dataSourceKey);
  }

}
