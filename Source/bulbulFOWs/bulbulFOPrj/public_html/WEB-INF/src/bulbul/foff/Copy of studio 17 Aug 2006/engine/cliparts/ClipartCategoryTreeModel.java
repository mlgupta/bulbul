package bulbul.foff.studio.engine.cliparts;
import bulbul.foff.studio.common.ClipartCategoryBean;
import bulbul.foff.studio.engine.ui.Studio;
import java.io.Serializable;
import javax.swing.tree.TreePath;

/**
 * 
 * @description 
 * @version 1.0 05-Oct-2005
 * @author Sudheer V Pujar
 */
public class ClipartCategoryTreeModel extends AbstractTreeModel implements Serializable  {
  
  private Studio studio;
  
  public ClipartCategoryTreeModel(Studio studio){
    this.studio=studio;
  }
  /**
   * 
   * @description 
   * @return 
   */
  public Object getRoot() {
    //System.out.println("getRoot Called");
    ClipartCategoryBean clipartCategory = new ClipartCategoryBean();
    clipartCategory.setPk("-1");
    clipartCategory.setName("Clipart Categories ");
    clipartCategory.setDescription("Clipart Categories");
    clipartCategory.setSubCategoriesAvailable(true);
    
    return new ClipartCategoryNode(studio,null,clipartCategory);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param index
   * @param parent
   */
  public Object getChild( Object parent, int index ) {
    //System.out.println("getChild Called");
    ClipartCategoryNode category = (ClipartCategoryNode)parent;
    ClipartCategoryBean[] subCategories = category.list();
    return new ClipartCategoryNode(studio,category, subCategories[index] );
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param parent
   */
  public int getChildCount( Object parent ) {
    //System.out.println("getChildCount Called");
    ClipartCategoryNode category = (ClipartCategoryNode)parent;
    ClipartCategoryBean[] subCategories = category.list();
    return subCategories.length;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  public boolean isLeaf( Object node ) {
    //System.out.println("isLeaf Called");
    return ((ClipartCategoryNode)node).isLeaf();
  }

  /**
   * 
   * @description 
   * @param newValue
   * @param path
   */
  public void valueForPathChanged( TreePath path, Object newValue ) {
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param child
   * @param parent
   */
  public int getIndexOfChild( Object parent, Object child ) {
    //System.out.println("getIndexOfChild Called");
    ClipartCategoryNode parentCategory = (ClipartCategoryNode)parent;
    ClipartCategoryNode childCategory = (ClipartCategoryNode)child;
    ClipartCategoryBean[] children = parentCategory.list();
    int index = -1;

    for ( int i = 0; i < children.length; ++i ) {
      if ( childCategory.getPk().equals( children[i].getPk() ) ) {
          index = i;
          break;
      }
    }
    return index;
  }
  
}