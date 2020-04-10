package bulbul.foff.studio.engine.merchandise;
import bulbul.foff.studio.common.MerchandiseBean;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.ui.Studio;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

/**
 * 
 * @description 
 * @version 1.0 03-Nov-2005
 * @author Sudheer V. Pujar
 */
public class MerchandiseNode  {
  
  private String pk;
  private String name;
  private String description;
  private MerchandiseNode parent;
  private Studio studio;
  private boolean leaf;
  private boolean category;
  private boolean categoryOnly;
  
  
  /**
   * 
   * @description 
   */
  public MerchandiseNode(Studio studio,MerchandiseNode parent, MerchandiseBean child){
    this.pk=child.getPk();
    this.name=child.getName();
    this.description=child.getDescription();
    this.parent=parent;
    this.category=child.isCategory();
    this.categoryOnly=child.isCategoryOnly();
    this.leaf=!(child.isChildrenAvailable());
    this.studio=studio;
  }

  /**
   * 
   * @description 
   * @return 
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
   * @description 
   * @return 
   */
  public MerchandiseNode getParent() {
    return parent;
  }

  /**
   * 
   * @description 
   * @param parent
   */
  public void setParent(MerchandiseNode parent) {
    this.parent = parent;
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
  public Studio getStudio() {
    return studio;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isCategory() {
    return category;
  }

  /**
   * 
   * @description 
   * @param category
   */
  public void setCategory(boolean category) {
    this.category = category;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isCategoryOnly() {
    return categoryOnly;
  }

  /**
   * 
   * @description 
   * @param categoryOnly
   */
  public void setCategoryOnly(boolean categoryOnly) {
    this.categoryOnly = categoryOnly;
  }
  
    /**
   * 
   * @description 
   * @return 
   */
  public MerchandiseBean[] list() {
    HttpBrowser newBrowser = null;
    ObjectInputStream objInStream=null; 
    MerchandiseBean[] merchandise=null;
    //System.out.println("Inside List ");
    try {
      studio.getMainStatusBar().setInfo("Connecting .......");
      studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
      newBrowser = new HttpBrowser(studio, "merchandiseListAction.do");
      Properties args = new Properties();
      args.put("merchandiseCategoryTblPk",this.pk);
      objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
      merchandise=(MerchandiseBean[])objInStream.readObject();
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
    return merchandise;
  }
}