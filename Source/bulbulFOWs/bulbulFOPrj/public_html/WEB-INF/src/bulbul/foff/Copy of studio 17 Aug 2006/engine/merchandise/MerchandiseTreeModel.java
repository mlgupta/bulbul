package bulbul.foff.studio.engine.merchandise;
import bulbul.foff.studio.common.MerchandiseBean;
import bulbul.foff.studio.engine.ui.Studio;

import java.io.Serializable;

import javax.swing.tree.TreePath;

/**
 * 
 * @description 
 * @version 1.0 03-Nov-2005
 * @author Sudheer V. Pujar
 */
public class MerchandiseTreeModel extends AbstractTreeModel implements Serializable {
  
  
  private Studio studio;
  
  /**
   * 
   * @description 
   */
  public MerchandiseTreeModel(Studio studio){
    this.studio=studio;
  }
  
   /**
   * 
   * @description 
   * @return 
   */
  public Object getRoot() {
    //System.out.println("getRoot Called");
    MerchandiseBean merchandise = new MerchandiseBean();
    merchandise.setPk("-1");
    merchandise.setName("Merchandise Categories ");
    merchandise.setDescription("Merchandise Categories");
    merchandise.setCategory(true);
    merchandise.setCategoryOnly(true);
    merchandise.setChildrenAvailable(true);
    return new MerchandiseNode(studio,null,merchandise);
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
    MerchandiseNode merchandise = (MerchandiseNode)parent;
    MerchandiseBean[] merchandiseChildren = merchandise.list();
    return new MerchandiseNode(studio,merchandise, merchandiseChildren[index] );
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param parent
   */
  public int getChildCount( Object parent ) {
    //System.out.println("getChildCount Called");
    MerchandiseNode merchandise = (MerchandiseNode)parent;
    MerchandiseBean[] merchandiseChildren = merchandise.list();
    return merchandiseChildren.length;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  public boolean isLeaf( Object node ) {
    //System.out.println("isLeaf Called");
    return ((MerchandiseNode)node).isLeaf();
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
    MerchandiseNode merchandise = (MerchandiseNode)parent;
    MerchandiseNode merchadiseChild = (MerchandiseNode)child;
    MerchandiseBean[] merchandiseChildren = merchandise.list();
    int index = -1;

    for ( int i = 0; i < merchandiseChildren.length; ++i ) {
      if ( merchadiseChild.getPk().equals( merchandiseChildren[i].getPk() ) ) {
          index = i;
          break;
      }
    }
    return index;
  }
 
}