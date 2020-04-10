package bulbul.foff.studio.engine.merchandise;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 * 
 * @description 
 * @version 1.0 05-Oct-2005
 * @author Sudheer V Pujar
 */
public class TreeModelSupport {
   private Vector vector = new Vector();

  /**
   * 
   * @description 
   * @param listener
   */
   public void addTreeModelListener( TreeModelListener listener ) {
      if ( listener != null && !vector.contains( listener ) ) {
         vector.addElement( listener );
      }
   }

  /**
   * 
   * @description 
   * @param listener
   */
   public void removeTreeModelListener( TreeModelListener listener ) {
      if ( listener != null ) {
         vector.removeElement( listener );
      }
   }

  /**
   * 
   * @description 
   * @param e
   */
   public void fireTreeNodesChanged( TreeModelEvent e ) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesChanged( e );
      }
   }

  /**
   * 
   * @description 
   * @param e
   */
   public void fireTreeNodesInserted( TreeModelEvent e ) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesInserted( e );
      }
   }

  /**
   * 
   * @description 
   * @param e
   */
   public void fireTreeNodesRemoved( TreeModelEvent e ) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeNodesRemoved( e );
      }
   }

  /**
   * 
   * @description 
   * @param e
   */
   public void fireTreeStructureChanged( TreeModelEvent e ) {
      Enumeration listeners = vector.elements();
      while ( listeners.hasMoreElements() ) {
         TreeModelListener listener = (TreeModelListener)listeners.nextElement();
         listener.treeStructureChanged( e );
      }
   }
}