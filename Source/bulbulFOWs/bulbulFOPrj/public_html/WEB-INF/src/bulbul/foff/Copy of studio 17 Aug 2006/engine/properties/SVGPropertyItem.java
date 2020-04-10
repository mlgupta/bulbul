package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.ui.SVGCanvas;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 26-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGPropertyItem {

  private String undoredoproperties="Properties Modification";
  
  private String propertyType;
  private String propertyName;
  private String propertyValueType;
  private String defaultPropertyValue="";
  private String propertyConstraint="";
  private String generalPropertyValue="";
	
  private String propertyLabel;
	
  private LinkedList nodeList;
  private LinkedHashMap propertyValues=new LinkedHashMap();
	private LinkedHashMap valuesMap;
	private LinkedHashMap valuesLabelMap;
  
  private HashMap propertySheetLabelMap =null;
  
  private SVGPropertySheet propertySheet=null;
  
  /**
   * 
   * @description 
   * @param valuesMap
   * @param propertyConstraint
   * @param defaultPropertyValue
   * @param propertyValueType
   * @param propertyName
   * @param propertyType
   * @param nodeList
   * @param propertySheet
   */
  public SVGPropertyItem(SVGPropertySheet propertySheet, LinkedList nodeList, String propertyType, String propertyName, String propertyValueType, String defaultPropertyValue, String propertyConstraint, LinkedHashMap valuesMap) {
    this.propertySheet=propertySheet;
		this.nodeList=nodeList;
		this.propertyType=propertyType;
		
		try{
			this.propertyName=propertyName.substring(propertyName.indexOf("_")+1, propertyName.length());
		}catch (Exception ex){this.propertyName=propertyName;}
		
		this.propertyValueType=propertyValueType;
		this.defaultPropertyValue=defaultPropertyValue;
		this.propertyConstraint=propertyConstraint;
		this.valuesMap=valuesMap;
    
    propertySheetLabelMap=propertySheet.getPropertySheetLabelMap();
    if(propertySheetLabelMap!=null){
      try{
        propertyLabel=(String)propertySheetLabelMap.get(propertyName);
      }catch (Exception ex){}
      if(propertyLabel==null || (propertyLabel!=null && propertyLabel.equals(""))){
        propertyLabel=this.propertyName;
      }
		}
		
		//fils the valuesLabelMap with the labels associated with each value name of a property if it exits
		if(valuesMap!=null && valuesMap.size()>0 && propertySheetLabelMap!=null){
		    
			valuesLabelMap=new LinkedHashMap();
			String name="";
      String label="";
			
			for(Iterator it=valuesMap.keySet().iterator(); it.hasNext();){
				try{
					name=(String)it.next();
				}catch (Exception ex){name=null;}
				
				if(name!=null && ! name.equals("")){
					try{
						label=(String)propertySheetLabelMap.get(name);
					}catch (Exception ex){}
					
					//if no label has been found, the label is set to the name
					if(label==null || (label!=null && label.equals(""))){
					  label=name;
					}
					
					valuesLabelMap.put(name, label);
				}
			}
		}

		//sets the value of the property taking it from the node attributes
		if(propertyType!=null && this.propertyName!=null){
			if(propertyType.equals("style")){
				generalPropertyValue=getStylePropertyValue(this.propertyName);
			}else if (propertyType.equals("attribute")){
				generalPropertyValue=getAttributeValue(this.propertyName);
			}else if (propertyType.equals("child")){
				generalPropertyValue=getChildValue(this.propertyName);
			}
		}	
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGPropertySheet getPropertySheet() {
		return propertySheet;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public Collection getNodeList(){
		return nodeList;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public String getPropertyType(){
		return propertyType;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public String getPropertyName(){
		return propertyName;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public String getPropertyValueType(){
		return propertyValueType;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public String getDefaultPropertyValue(){
		return defaultPropertyValue;
	}
	
  /**
   * 
   * @description 
   * @return 
   */
	public String getPropertyConstraint(){
		return propertyConstraint;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public String getGeneralPropertyValue(){
		return generalPropertyValue;
	}
	
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
	public String getPropertyValue(Node node){
	  String val="";
    if(node!=null){
      try{
          val=(String)propertyValues.get(node);
      }catch (Exception ex){}
    }
    return val;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public String getPropertyLabel(){
		return propertyLabel;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public LinkedHashMap getPropertyValuesMap(){
		return valuesMap;
	}
		
  /**
   * 
   * @description 
   * @return 
   */
	public LinkedHashMap getPropertyValuesLabelMap(){
		return valuesLabelMap;
	}
  
  /**
   * 
   * @description 
   * @param value
   */
  public void changePropertyValue(String value){
		
		if((value==null || (value!=null && value.equals("")) && (propertyConstraint!=null && propertyConstraint.equals("required")))){
			value=defaultPropertyValue;
		}
		
		//the current SVGTab
		final SVGTab svgTab=propertySheet.getStudio().getSvgTabManager().getCurrentSVGTab();
		
		if(svgTab!=null && propertyType!=null){

			//creates a new values map associating a node to the current widgetPropertyValue
			LinkedHashMap values=new LinkedHashMap();
			Node node=null;
			
			for(Iterator it=propertyValues.keySet().iterator(); it.hasNext();){
			    
				try{node=(Node)it.next();}catch(Exception ex){node=null;}
				
				if(node!=null){
				    
					values.put(node, value);
				}
			}
			
			//the maps that will be used for the undo/redo action
			final LinkedHashMap oldValues=new LinkedHashMap(propertyValues);
			final LinkedHashMap newValues=new LinkedHashMap(values);
			final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
			final LinkedHashMap finalValues=values;
			
			Runnable runnable=new Runnable(){
			    public void run(){
					//sets the new value taken from the widget and gets the new value once set from the node
					if(propertyType.equals("style")){
						setStylePropertyValue(propertyName, finalValues);
					}else if(propertyType.equals("attribute")){
						setAttributeValue(propertyName, finalValues);
					}else if(propertyType.equals("child")){
						setChildValue(propertyName, finalValues);
					}
					svgTab.getScrollPane().getSvgCanvas().delayedRepaint();	
				}
			};
				
			if(queue.getThread()!=Thread.currentThread()){
				queue.invokeLater(runnable);
			}else{
				runnable.run();
			}
			
			svgTab.setModified(true);

			//the undo/redo action that will be used to undo or redo the changes of the value for this property
			SVGUndoRedoAction action=new SVGUndoRedoAction(""){
				public void undo(){
					//sets the old value
					if(propertyType.equals("style")){
						setStylePropertyValue(propertyName, oldValues);
					}else if(propertyType.equals("attribute")){
						setAttributeValue(propertyName, oldValues);
					}else if(propertyType.equals("child")){
						setChildValue(propertyName, oldValues);
					}
					
					//redraws the window
					propertySheet.handleProperties(propertySheet.getSelectedNodes());
				}

				public void redo(){
					//sets the new value
					if(propertyType.equals("style")){
						setStylePropertyValue(propertyName, newValues);
					}else if(propertyType.equals("attribute")){
						setAttributeValue(propertyName, newValues);
					}else if(propertyType.equals("child")){
						setChildValue(propertyName, newValues);
					}

					//redraws the window
					propertySheet.handleProperties(propertySheet.getSelectedNodes());
				}
			};
			
			//creates the undo/redo list so that actions can be added to it
			SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredoproperties);
			actionlist.add(action);
			
			if(propertySheet.getStudio().getSvgUndoRedo()!=null){
				propertySheet.getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist); 
			}
		}
	}
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public String getStylePropertyValue(String name){
    //clears the map associating a node to its value
		propertyValues.clear();
		if(nodeList!=null){
			String value="";
			Element element=null;
			
			//for each node in the list
			for(Iterator it=nodeList.iterator(); it.hasNext();){
				try{
          element=(Element)it.next();
        }catch (Exception ex){element=null;}
			
				value=propertySheet.getStudio().getSvgToolkit().getStyleProperty(element, name);
				if(value==null || (value!=null && value.equals(""))){
				  value=defaultPropertyValue;
				}
				
				if(element!=null){
					propertyValues.put(element, value);
				}
			}
		}
		
		//the value that will be returned
		String returnedValue="";
		
		//if the list contains a single element, its value will be returned, otherwise the empty string is returned
		if(nodeList.size()==1){
			try{
				returnedValue=(String)propertyValues.get(nodeList.getFirst());
			}catch (Exception ex){returnedValue="";}
		}
		return returnedValue;
  }
  
  /**
   * 
   * @description 
   * @param values
   * @param name
   */
  public void setStylePropertyValue(String name, LinkedHashMap values){
    if(nodeList!=null){
			Element element=null;
			String value="";
      String oldValue="";
		
			//for each node in the list
			for(Iterator it=nodeList.iterator(); it.hasNext();){
				try{
					element=(Element)it.next();
					value=(String)values.get(element);
					oldValue=(String)propertyValues.get(element);
				}catch (Exception ex){element=null;value=null;oldValue="";}
			
				if(element!=null && name!=null && ! name.equals("") && value!=null && ! value.equals(oldValue)){
					propertySheet.getStudio().getSvgToolkit().setStyleProperty(element, name, value);
				}				
			}
		}
		generalPropertyValue=getStylePropertyValue(name);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public String getAttributeValue(String name){
    //clears the map associating a node to its value
		propertyValues.clear();
		
		String value="";
		Element element=null;
			
		//for each node in the list
		for(Iterator it=nodeList.iterator(); it.hasNext();){
			try{
				element=(Element)it.next();
			}catch (Exception ex){element=null;}
			
			if(element!=null && name!=null && ! name.equals("")){
				value=element.getAttribute(name);
			}
				
			if(value==null || (value!=null && value.equals(""))){
			  value=defaultPropertyValue;
			}
				
			if(element!=null){
				propertyValues.put(element, value);
			}
		}
		//the value that will be returned
		String returnedValue="";
			
		//if the list contains a single element, its value will be returned, otherwise the empty string is returned
		if(nodeList.size()==1){
			try{
				returnedValue=(String)propertyValues.get(nodeList.getFirst());
			}catch (Exception ex){returnedValue="";}
		}
		return returnedValue;
  }
  
  /**
   * 
   * @description 
   * @param values
   * @param name
   */
  public void setAttributeValue(String name, LinkedHashMap values){
    if(nodeList!=null){
			Element element=null;
			String value="";
      String oldValue="";
		
			//for each node in the list
			for(Iterator it=nodeList.iterator(); it.hasNext();){
				try{
					element=(Element)it.next();
					value=(String)values.get(element);
					oldValue=(String)propertyValues.get(element);
				}catch (Exception ex){element=null;value=null;oldValue="";}
			
				if(element!=null && name!=null && ! name.equals("") && value!=null && ! value.equals(oldValue)){
					//sets the value of the attribute
					element.setAttribute(name, value);
				}
			}
		}
		generalPropertyValue=getAttributeValue(name);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public String getChildValue(String name){
    //clears the map associating a node to its value
		propertyValues.clear();
		
		String value="";
		Node node=null;
    	
		//for each node in the list
		for(Iterator it=nodeList.iterator(); it.hasNext();){
			try{
        node=(Node)it.next();
      }catch (Exception ex){node=null;}
			
			if(node!=null && name!=null && ! name.equals("")){
				//for each child of the given element, tests if the name of these children is equals to the parameter string
				for(Node current=node.getFirstChild(); current!=null; current=current.getNextSibling()){
					if(current.getNodeName().equals(name)){
						value=current.getNodeValue();
						break;
					}
				}
			}
				
			if(value==null || (value!=null && value.equals(""))){
			  value=defaultPropertyValue;
			}

			if(node!=null){
				propertyValues.put(node, value);
			}
		}
		
		//the value that will be returned
		String returnedValue="";
			
		//if the list contains a single element, its value will be returned, otherwise the empty string is returned
		if(nodeList.size()==1){
			try{
        returnedValue=(String)propertyValues.get(nodeList.getFirst());
      }catch (Exception ex){returnedValue="";}
		}
		return returnedValue;
  }
  
  /**
   * 
   * @description 
   * @param values
   * @param name
   */
  public void setChildValue(String name, LinkedHashMap values){
    if(nodeList!=null){
			Element element=null;
			String value="";
      String oldValue="";
		
			//for each node in the list
			for(Iterator it=nodeList.iterator(); it.hasNext();){
				try{
					element=(Element)it.next();
					value=(String)values.get(element);
					oldValue=(String)propertyValues.get(element);
				}catch (Exception ex){element=null;value=null;oldValue="";}
			
				if(element!=null && name!=null && ! name.equals("") && value!=null && ! value.equals(oldValue)){
					//checks all the child nodes of the element to find the text node, if it is found, sets its value
					for(Node cur=element.getFirstChild(); cur!=null; cur=cur.getNextSibling()){
						if(cur.getNodeName().equals(name)){
							//sets the value of the node
							cur.setNodeValue(value);
							break;
						}
					}
				}
			}
		}
		generalPropertyValue=getChildValue(name);
  }
}