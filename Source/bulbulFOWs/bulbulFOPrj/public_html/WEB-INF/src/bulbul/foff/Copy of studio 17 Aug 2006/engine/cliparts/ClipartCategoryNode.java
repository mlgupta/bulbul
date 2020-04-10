package bulbul.foff.studio.engine.cliparts;
import bulbul.foff.studio.common.ClipartCategoryBean;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.ui.Studio;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.Properties;

/**
 * 
 * @description 
 * @version 1.0 05-Oct-2005
 * @author Sudheer V Pujar
 */
public class ClipartCategoryNode  {
  private String pk;
  private String name;
  private String description;
  private ClipartCategoryNode parent;
  private boolean leaf;
  private Studio studio;
    
  /**
   * 
   * @description 
   * @param child
   * @param parent
   */
  public ClipartCategoryNode(Studio studio,ClipartCategoryNode parent, ClipartCategoryBean child){
    this.pk=child.getPk();
    this.name=child.getName();
    this.description=child.getDescription();
    this.parent=parent;
    this.leaf=!(child.areSubCategoriesAvailable());
    this.studio=studio;
    //System.out.println("ClipartCategoryNode() is Called ");
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getPk() {
    return pk;
  }

  /**
   * 
   * @description 
   * @param pk
   */
  public void setPk(String pk) {
    this.pk = pk;
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

  /**
   * 
   * @return 
   * @description 
   */
  public String getDescription() {
    return description;
  }

  /**
   * 
   * @description 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public ClipartCategoryNode getParent() {
    return parent;
  }

  /**
   * 
   * @description 
   * @param parent
   */
  public void setParent(ClipartCategoryNode parent) {
    this.parent = parent;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isLeaf() {
    return leaf;
  }

  /**
   * 
   * @description 
   * @param leaf
   */
  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public ClipartCategoryBean[] list() {
    HttpBrowser newBrowser = null;
    ObjectInputStream objInStream=null; 
    ClipartCategoryBean[] clipartCategories=null;
    //System.out.println("Inside List ");
    try {
      studio.getMainStatusBar().setInfo("Connecting .......");
      studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
      newBrowser = new HttpBrowser(studio, "clipartCategoryListAction.do");
      Properties args = new Properties();
      args.put("clipartCategoryTblPk",this.pk);
      objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
      clipartCategories=(ClipartCategoryBean[])objInStream.readObject();
    }catch (IOException e) {
      e.printStackTrace();
    }catch (ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      try {
        if(objInStream!=null){ 
          objInStream.close();
        }
      }catch (IOException  ioe) {
        ioe.printStackTrace();
      }finally{
        studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
        studio.getMainStatusBar().setInfo("");
      }
    }
    return clipartCategories;
  }
}