package bulbul.foff.studio.engine.properties;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGPropertySheet implements SVGClassObject  {
  
  private final String idproperties="Properties";
  private final LinkedList selectedNodes=new LinkedList();
	
  private String labelproperties="Properties";
  private String propertyTabId="";	
  
  private Rectangle propertySheetBounds=null;
  
  private JPanel propertysheetPanel=new JPanel();
	private SVGPropertyDevicePanel devicePanel=null;
  
  private Document propertySheetDocument=null;
  private HashMap propertySheetLabelMap = new HashMap();
  
  private JPanel propertySheetFrame=null;
  private Studio studio;
  /**
   * 
   * @description 
   */
  public SVGPropertySheet(Studio studio) {
    this.studio=studio;
    
    propertySheetLabelMap.put("property_empty_dialog_none","No item selected");
    propertySheetLabelMap.put("property_empty_dialog_one","No property available for this item");
    propertySheetLabelMap.put("property_empty_dialog_many","One of the selected nodes has no property");
    propertySheetLabelMap.put("property_errortitle","Error");
    
    
    propertySheetLabelMap.put("tab_stroke","Stroke");
    
    propertySheetLabelMap.put("property_stroke","Color");
    
    propertySheetLabelMap.put("property_stroke-width","Width");
    
    propertySheetLabelMap.put("property_stroke-linecap","Line Cap");
    propertySheetLabelMap.put("item_butt","Button");
    propertySheetLabelMap.put("item_round","Round");
    propertySheetLabelMap.put("item_square","Square");
    
    propertySheetLabelMap.put("property_stroke-linejoin","Line-join");
    propertySheetLabelMap.put("item_miter","Miter");
    propertySheetLabelMap.put("item_bevel","Bevel");
    
    propertySheetLabelMap.put("property_stroke-opacity","Opacity");
    
    propertySheetLabelMap.put("property_stroke-dasharray","Dash array");
    propertySheetLabelMap.put("item_none","None");
    propertySheetLabelMap.put("item_dasharray_avalue1","5");
    propertySheetLabelMap.put("item_dasharray_avalue2","5 10");
    propertySheetLabelMap.put("item_dasharray_avalue3","5 10 5");
    
    propertySheetLabelMap.put("property_stroke-dashoffset","Dash offset");
    
    propertySheetLabelMap.put("property_stroke-miterlimit","Miter limit");
    
    
    propertySheetLabelMap.put("tab_fill","Fill");
    
    propertySheetLabelMap.put("property_fill","Color");
    
    propertySheetLabelMap.put("property_fill-opacity","Opacity");
    
    propertySheetLabelMap.put("property_fill-rule","Rule");
    propertySheetLabelMap.put("item_nonzero","Nonzero");
    propertySheetLabelMap.put("item_evenodd","Evenodd");
    
    
    propertySheetLabelMap.put("tab_display","Display");
    
    propertySheetLabelMap.put("property_visibility","Visibility");
    propertySheetLabelMap.put("item_visible","Visible");
    propertySheetLabelMap.put("item_hidden","Hidden");
    
    propertySheetLabelMap.put("property_opacity","Opacity");
    
    
    propertySheetLabelMap.put("tab_geom","Geometry");
    
    propertySheetLabelMap.put("property_x","Horizontal position");
    
    propertySheetLabelMap.put("property_y","Vertical position");
    
    propertySheetLabelMap.put("property_x1","First point absciss");
    
    propertySheetLabelMap.put("property_y1","First point ordinate");
    
    propertySheetLabelMap.put("property_x2","Second point absciss");
    
    propertySheetLabelMap.put("property_y2","Second point ordinate");
    
    propertySheetLabelMap.put("property_dx","dx");
    
    propertySheetLabelMap.put("property_dy","dy");
    
    propertySheetLabelMap.put("property_cx","Center point absciss");
    
    propertySheetLabelMap.put("property_cy","Center point ordinate");
    
    propertySheetLabelMap.put("property_points","Points");
    
    propertySheetLabelMap.put("property_d","Path elements");
    
    propertySheetLabelMap.put("property_r","Radius");
    
    propertySheetLabelMap.put("property-rect_rx","X-axis radius for the round corner");
    
    propertySheetLabelMap.put("property-rect_ry","X-axis radius for the round corner");
    
    propertySheetLabelMap.put("property-ellipse_rx","X-axis radius");
    
    propertySheetLabelMap.put("property-ellipse_ry","Y-axis radius");
    
    propertySheetLabelMap.put("property_width","Width");
    
    propertySheetLabelMap.put("property_height","Height");
    
    propertySheetLabelMap.put("property_rotate","Glyph rotation");
    
    
    propertySheetLabelMap.put("tab_text","Text");
    
    propertySheetLabelMap.put("property_#text","Text");
    
    propertySheetLabelMap.put("property_font-family","Font family");
    
    propertySheetLabelMap.put("property_font-size","Font size");
    
    propertySheetLabelMap.put("property_font-weight","Font weight");
    propertySheetLabelMap.put("item_normal","Normal");
    propertySheetLabelMap.put("item_bold","Bold");
    propertySheetLabelMap.put("item_bolder","Bolder");
    propertySheetLabelMap.put("item_lighter","Lighter");
    
    propertySheetLabelMap.put("property_font-style","Font style");
    propertySheetLabelMap.put("item_italic","Italic");
    propertySheetLabelMap.put("item_oblique","oblique");
    
    
    propertySheetLabelMap.put("tab_decorations","Decoration");
    
    propertySheetLabelMap.put("property_text-decoration","Decoration");
    propertySheetLabelMap.put("item_underline","Underline");
    propertySheetLabelMap.put("item_overline","Overline");
    propertySheetLabelMap.put("item_line-through","Line through");
    
    propertySheetLabelMap.put("renderchooser_none","None");
    propertySheetLabelMap.put("renderchooser_color","Color");
    
    propertySheetFrame=getStudio().getPanel4PropertySheet();
    
    //a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabChangedListener=new ActionListener(){
			
			/**
			 * a listener on the selection changes
			 */
			private ActionListener selectionListener=null;
			
			/**
			 * the current selection module
			 */
			private SVGSelection selection=null;

			public void actionPerformed(ActionEvent e) {
				//removes the device panel
				if(devicePanel!=null){
				    propertysheetPanel.removeAll();
				    devicePanel.dispose();
				    devicePanel=null;
				}
				
				//clears the list of the selected items
				selectedNodes.clear();
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				//if a selection listener is already registered on a selection module, it is removed	
				if(selection!=null && selectionListener!=null){
					selection.removeSelectionListener(selectionListener);
				}

				//gets the current selection ClassObject
				if(svgTab!=null){
					selection=getStudio().getSvgSelection();
				}
				
				if(svgTab!=null && selection!=null){
					manageSelection();

					//the listener of the selection changes
					selectionListener=new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							manageSelection();
						}
					};
					
					//adds the selection listener
					if(selectionListener!=null){
						selection.addSelectionListener(selectionListener);
					}
				}else if(propertysheetPanel.isVisible()){
					handleProperties(null);
				}
			}	
			
			/**
			 * updates the selected items and the state of the menu items
			 */
			protected void manageSelection(){
				getStudio().getMainTabSet().setSelectedIndex(0);
        LinkedList list=null;
				
				//gets the currently selected nodes list 
				if(selection!=null){
					list=selection.getCurrentSelection(getStudio().getSvgTabManager().getCurrentSVGTab());
				}

				selectedNodes.clear();
				
				//refresh the selected nodes list
				if(list!=null){
				  selectedNodes.addAll(list);
				}
				
				if(selectedNodes.size()>=1){
					if(propertysheetPanel.isVisible()){
						handleProperties(selectedNodes);
					}
				}else if(propertysheetPanel.isVisible()){
					handleProperties(null);		
				}
			}
		};
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    propertySheetBounds=new Rectangle(0,0,245,600);
    propertySheetFrame.setLayout(new BorderLayout());
    propertysheetPanel.setPreferredSize(new Dimension(propertySheetBounds.width, propertySheetBounds.height));
    
    propertySheetFrame.setSize(new Dimension(propertySheetBounds.width, propertySheetBounds.height));
    propertySheetFrame.add(propertysheetPanel,BorderLayout.CENTER);
    
    //creating the property sheet document
    try {
      propertySheetDocument=getStudio().getSvgResource().getXMLDocument("properties.xml");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    propertySheetDocument=normalizePropertySheetDocument(propertySheetDocument);
  }

  
  /**
   * 
   * @description 
   * @param list
   */
  protected void handleProperties(LinkedList list){
    //removes the device panel
		if(devicePanel!=null){
      propertysheetPanel.removeAll();
      devicePanel.dispose();
      devicePanel=null;
		}
		
		final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();

		if(svgTab!=null && list!=null && list.size()>0){
			LinkedList snodes=new LinkedList(list);

			//gets the accurate subtree given the list of nodes
			Node propertySheetNode=getPropertySheetNode(snodes);
			
			if(propertySheetNode!=null){
				//the map associating a tab to its label and the map associating a tab to a list of property item objects
				LinkedHashMap tabMap=new LinkedHashMap();
        LinkedHashMap propertyItemsMap=new LinkedHashMap();
		
				if(propertySheetNode!=null){
					String name="";
          String label="";
					
					//builds the list of the tabs 
					for(Node current=propertySheetNode.getFirstChild(); current!=null; current=current.getNextSibling()){
						if(current.getNodeName().equals("tab")){
							name=((Element)current).getAttribute("name");
					
							if(name!=null && ! name.equals("")){
								//gets the label of the tab and puts it into the map
								if(propertySheetLabelMap!=null){
									try{
                    label=(String)propertySheetLabelMap.get(name);
                  }catch (Exception ex){label=null;}
								}
								
                if(label==null || (label!=null && label.equals(""))){
								  label=name;
								}

								tabMap.put(name, label);
				
								//gets the property items list and puts it into the map
								propertyItemsMap.put(name, getPropertyList(snodes, current));
							}
						}		
					}
		
					//creates the panel
					if(tabMap!=null && propertyItemsMap!=null && tabMap.size()>0 && propertyItemsMap.size()>0){
            if(devicePanel!=null){
              devicePanel.dispose();
            }
  
            devicePanel=new SVGPropertyDevicePanel(this, tabMap, propertyItemsMap);
					} 
					
					//adds the property panel into the container and displays it
					if(propertysheetPanel!=null && devicePanel!=null){
            propertysheetPanel.removeAll();
            propertysheetPanel.setLayout(new BorderLayout());
            propertysheetPanel.add(devicePanel,BorderLayout.CENTER);
            propertySheetFrame.validate();
            propertySheetFrame.repaint();
            
						return;
					}
				}
			}
		}
		
    //initializes the value of the last selected tab
    propertyTabId="";
    String message="";
    
    try{
      if(selectedNodes.size()<1){
        message=(String)propertySheetLabelMap.get("property_empty_dialog_none");
      }else if(selectedNodes.size()==1){
        message=(String)propertySheetLabelMap.get("property_empty_dialog_one");
      }else if(selectedNodes.size()>1){
        message=(String)propertySheetLabelMap.get("property_empty_dialog_many");
      }
    }catch (Exception ex){}

    JPanel panel=new JPanel();
    panel.setLayout(new FlowLayout());
    
    JLabel label=new JLabel(message);
    
    panel.add(label);

    propertysheetPanel.removeAll();
    propertysheetPanel.setLayout(new BorderLayout());
    propertysheetPanel.add(panel,BorderLayout.CENTER);
    propertySheetFrame.validate();
    propertySheetFrame.repaint();
		
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  protected LinkedList getSelectedNodes() {
    return selectedNodes;
  }


  /**
   * 
   * @description 
   * @param tabId
   */
  protected void setPropertyTabId(String propertyTabId) {
    this.propertyTabId = propertyTabId;
  }


  /**
   * 
   * @description 
   * @return 
   */
  protected String getPropertyTabId() {
    return propertyTabId;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param list
   */
  protected Node getPropertySheetNode(LinkedList list){
    Node propertySheetNode=null;

		if(list !=null && list.size()>0){
			Node current=null;
			String name="";
			Iterator it=list.iterator();
			
			try{
				current=(Node)it.next();
				propertySheetNode=getPropertySheetNode(current);
				name=current.getNodeName();
			}catch (Exception ex){return null;}
			
			while(it.hasNext()){
				try{
					current=(Node)it.next();
				}catch (Exception ex){current=null;}
				
				if(current!=null){
					if(name!=null && ! name.equals(current.getNodeName())){
					  propertySheetNode=intersectPropertySheetNodes(propertySheetNode, getPropertySheetNode(current));
					}
					name=current.getNodeName();
				}
			}
		}
		return propertySheetNode;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  protected Node getPropertySheetNode(Node node){
    if(propertySheetDocument!=null && node!=null){
			String name=node.getNodeName();
			
			if(name!=null && ! name.equals("")){
				Document clonedPropertySheetDocument=(Document)propertySheetDocument.cloneNode(true);
				Element root=clonedPropertySheetDocument.getDocumentElement();
				
				if(root!=null){
				  Node current=null;
					//for each root child, searches the one whom value of the "name" attribute is the type of the given node
					for(current=root.getFirstChild(); current!=null; current=current.getNextSibling()){
						if(current.getNodeName().equals("module")){
							String cname=((Element)current).getAttribute("name");
							
              if(cname!=null && cname.equals(name)){
							  break;
							}
						}
					}
					
					if(current!=null){
						return current;
					}
				}
			}
		}
		
		return null;
  }
  
  
  /**
   * 
   * @description 
   * @return 
   * @param node2
   * @param node1
   */
  protected Node intersectPropertySheetNodes(Node node1, Node node2){
    Node propertySheetNode=null;
		if(node1!=null && node2!=null){

			//clones the node
			propertySheetNode=node1.cloneNode(false);
			
			//removes all of its children
			while(propertySheetNode.hasChildNodes()){
			  propertySheetNode.removeChild(propertySheetNode.getFirstChild());
			}
			
			Node tab4Node2=null; 
			Node property4Node2=null;
      
			String name=null;
			
			for(Node tab4Node1=node1.getFirstChild(); tab4Node1!=null; tab4Node1=tab4Node1.getNextSibling()){

				if(tab4Node1.getNodeName().equals("tab")){
				    
					//for each tab in node1, tests if it is in node2
					if((tab4Node2=getPropertyTab(node2, tab4Node1))!=null){
					    
						//clones the node
						Node clonedTab=tab4Node1.cloneNode(false);
						
						//removes all of its children
						while(clonedTab.hasChildNodes()){
						  clonedTab.removeChild(clonedTab.getFirstChild());
						}
						
						propertySheetNode.appendChild(clonedTab);
						
						//for each property in the current tab in node1, tests if it is in node2
						for(Node property=tab4Node1.getFirstChild(); property!=null;property=property.getNextSibling()){
							if((property4Node2=getProperty(tab4Node2, property))!=null){
                Node clonedProperty=null;
								clonedProperty=property.cloneNode(true); //clones the node
								clonedTab.appendChild(clonedProperty);	//appends it to the tab
							}
						}
					}
				}
			}
		}
		return propertySheetNode;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param tab
   * @param node
   */
  protected Node getPropertyTab(Node node, Node tab){
    if(node!=null && tab!=null && tab.getNodeName().equals("tab")){
      
      String tabName="";
			
			//gets the value of the name attribute of the tab
			try{
				Element element=(Element)tab;
				tabName=element.getAttribute("name");
			}catch (Exception ex){tabName="";}
			
			if(tabName!=null && ! tabName.equals("")){
				
				String name="";

				//for each tab in node2, test if it has the same name as the given tab
				for(Node tab2=node.getFirstChild(); tab2!=null; tab2=tab2.getNextSibling()){
					
					if(tab2.getNodeName().equals("tab")){
					    
						//gets the value of the name attribute of the tab
						try{
							Element element=(Element)tab2;
							name=element.getAttribute("name");
						}catch (Exception ex){name="";}
						
						if(name!=null && name.equals(tabName)){
						  return tab2;
						}
					}
				}
			}
		}
		
		return null;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param property
   * @param tab
   */
  protected Node getProperty(Node tab, Node property){
    if(tab!=null && property!=null && property instanceof Element){
      String propertyName=""; 
      String propertyType=""; 
			//gets the name and type of the given property
			try{
				Element element=(Element)property;
				propertyName=element.getAttribute("name");
				propertyType=element.getAttribute("type");
			}catch (Exception ex){return null;}
			
			if(propertyName!=null && ! propertyName.equals("") && propertyType!=null && ! propertyType.equals("")){
				for(Node prop=tab.getFirstChild(); prop!=null; prop=prop.getNextSibling()){
					//if the node is a property node
					if(prop.getNodeName().equals("property")){
            String propertyName2=""; 
            String propertyType2="";
						
            //gets the name and type of the current node
						try{
							Element element=(Element)prop;
							propertyName2=element.getAttribute("name");
							propertyType2=element.getAttribute("type");
						}catch (Exception ex){propertyName2=null; propertyType2=null;}
						
						//tests if the current property name and type are equal to the gievn property name and type
						if(propertyName2!=null && propertyType2!=null 
							&& propertyName2.equals(propertyName) && propertyType2.equals(propertyType)){
							return prop;
						}
					}
				}
			}
		}
		return null;
  }

  
  /**
   * 
   * @description 
   * @return 
   * @param subTree
   * @param nodelist
   */
  protected LinkedList getPropertyList(LinkedList nodelist, Node propertySheetNode){
    LinkedList list=new LinkedList();
		if(propertySheetNode!=null){
			SVGPropertyItem item=null;

			//for each property node
			for(Node current=propertySheetNode.getFirstChild(); current!=null; current=current.getNextSibling()){
				//get the property item object
				if(current.getNodeName().equals("property")){
					//get the property item object
					item=getProperty(nodelist, current);
					//adds it to the list
					if(item!=null)list.add(item);
				}
			}
		}
		if(list!=null && list.size()>0){
		  return list;
		}
		
		return null;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param property
   * @param nodelist
   */
  protected SVGPropertyItem getProperty(LinkedList nodelist, Node property){
    SVGPropertyItem propertyItem=null;
		
    if(property!=null){
			
      //the attributes of the property node
			String type=((Element)property).getAttribute("type");
			String name=((Element)property).getAttribute("name");
			String valueType=((Element)property).getAttribute("valuetype");
			String defaultValue=((Element)property).getAttribute("defaultvalue");
			String constraint=((Element)property).getAttribute("constraint");
			
			LinkedHashMap values=null;

			if(property.hasChildNodes()){
				//fills the map with the attributes of each possible value for the property item
				values=new LinkedHashMap();
				String itemName="", itemValue=""; 

				for(Node current=property.getFirstChild(); current!=null; current=current.getNextSibling()){
					if(current.getNodeName().equals("item")){
						//the attributes of the item
						itemName=((Element)current).getAttribute("name");
						itemValue=((Element)current).getAttribute("value");
						
						if(itemName!=null && ! itemName.equals("") && itemValue!=null && !itemValue.equals("")){
							values.put(itemName, itemValue);
						}
					}
				}
				
				//if the map is empty, it is set to null
				if(values.size()<=0){
				  values=null;
				}
			}
			
			//creates the property item object
			if(type!=null && name!=null && valueType!=null && ! type.equals("") && ! name.equals("") && ! valueType.equals("")){
			    propertyItem=new SVGPropertyItem(this, nodelist, type, name, valueType, defaultValue, constraint, values);
			}
		}
		return propertyItem;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param document
   */
  public Document normalizePropertySheetDocument(Document document){
    //modifies the document to resolve the links within the dom
		//each tab node is appended to the node which have declared having a child whose type is like one of the predefined tabs
		if(document!=null){
			Node root=document.getDocumentElement();
		
			if(root!=null){
				//the map associating the name attributes of a tab to its tab node
				Hashtable tabMap=new Hashtable();
				
        //the list of the "define" nodes
				LinkedList defineList=new LinkedList();
			
				//finds the node that contains the predefined node tabs, and adds all its children to the tab map
				for(Node current=root.getFirstChild(); current!=null; current=current.getNextSibling()){
					if(current.getNodeName().equals("define")){
						//adds the "define" node to the list
						defineList.add(current);
					
						//adds all the predefined "tab" nodes to the map
						for(Node tab=current.getFirstChild(); tab!=null; tab=tab.getNextSibling()){
							if(tab.getNodeName().equals("tab")){
                String name="";
								try{
                  name=((Element)tab).getAttribute("name");
                }catch (Exception ex){name="";}
							
								if(name!=null && ! name.equals("")){
								  tabMap.put(name, tab.cloneNode(true));
								}
							}
						}
					}
				}
			
				//removes all the "define" nodes from the root node
				for(Iterator it=defineList.iterator(); it.hasNext();){
					try{
            root.removeChild((Node)it.next());
          }catch (Exception ex){}
				}
				
				defineList.clear();

				//the list of the "tab" nodes to add to the "module" node
				LinkedList tabList=new LinkedList();

				//appends the tab nodes to the node that define a link to a predefined tab
				for(Node current=root.getFirstChild(); current!=null; current=current.getNextSibling()){
					if(current.getNodeName().equals("module")){
						//gets the "use" nodes defined in the "module" node and the "tab" nodes that will be used to replace them
						for(Node use=current.getFirstChild(); use!=null; use=use.getNextSibling()){
							if(use.getNodeName().equals("use")){
								String name="";
                try{
                  name=((Element)use).getAttribute("name");
                }catch(Exception ex){name="";}
							
								//adds the tab node to the list
								if(name!=null && !name.equals("")){
									Node tab=(Node)tabMap.get(name);
									
									if(tab!=null){
									  tabList.add(tab.cloneNode(true));
									}
								}
							}else if(use.getNodeName().equals("tab")){
								tabList.add(use.cloneNode(true));
							}
						}
					
						//removes all the child nodes from the "module" node
						for(Node tab=current.getFirstChild(); tab!=null; tab=current.getFirstChild()){
							current.removeChild(tab);
						}
						
						//appends all the "tab" nodes to the "module" node
						for(Iterator it=tabList.iterator(); it.hasNext();){
							try{
                current.appendChild((Node)it.next());
              }catch (Exception ex){}
						}
					}
					//initializes the list of "tab" nodes
					tabList.clear();
				}
			}
		}
		return document;
  }
  
  
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
  
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idproperties;
	}


  /**
   * 
   * @description 
   * @return 
   */
  public Collection  getPopupMenuItems(){
    return null;
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
  public HashMap getPropertySheetLabelMap() {
    return propertySheetLabelMap;
  }
  
}