package bulbul.foff.general;


import java.util.Iterator;

import org.w3c.dom.Node;
/**
 * 
 * @description 
 * @version 1.0 03-Sep-2005
 * @author Sudheer V. Pujar
 */
public final class  NodeIterator implements Iterator {
  
  private Node root;
  private Node current;
	
  /**
   * 
   * @description 
   */
  public NodeIterator(Node root) {
		this.root=root;
		if(root!=null && root.hasChildNodes()){
			current=root;
		}else{
		  current=null;
		}
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean hasNext(){
	  boolean hasNext=false;
    if(root!=null){
			//if the current node has children
			if(current!=null && current.hasChildNodes()){
				//the current node becomes the first child
				current=current.getFirstChild();
				hasNext=true;
			}else if(current!=null && current.getNextSibling()!=null){
				//if the current node has no child but a sibling the current becomes the next sibling
				current=current.getNextSibling();
				hasNext=true;
			}else if(current!=null){
				//otherwise we go up in the tree until we find a new sibling
				while(current!=null && current!=root && current.getNextSibling()==null){
					current=current.getParentNode();
				}
				//if the current node is not the root node
				if(current!=null && current!=root){
					current=current.getNextSibling();
					hasNext=true; 
				}else{
				  hasNext=false;
				}
			}else{
			  hasNext=false;
			}
    }else{
      hasNext=false;
    }
		return hasNext;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public Object next(){
		return current;
	}

  /**
   * 
   * @description 
   */
	public void remove(){
		
	}
}
