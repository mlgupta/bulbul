package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 18-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGToolkit  {
  private Studio studio;
  /**
   * 
   * @description 
   */
  public SVGToolkit(Studio studio) {
    this.studio=studio;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  private SVGTransformMatrix getTransformMatrix(String value){
    SVGTransformMatrix matrix=new SVGTransformMatrix(1,0,0,1,0,0);
    if(value!=null && ! value.equals("")){
      int range=value.indexOf("matrix");
      //computes the double values of the matrix in the transform attribute
      if(range>-1){
        String subValue="";
        subValue=value.substring(range,value.length());
        subValue=subValue.substring(0,subValue.indexOf(")")+1);
        value=value.replaceAll("["+subValue+"]","");
        subValue=subValue.substring(subValue.indexOf("("),subValue.length());
        
        //cleans the string
        value=cleanTransformString(value);
        subValue=subValue.replaceAll("[(]","");
        subValue=subValue.replaceAll("[)]","");
        subValue=subValue.concat(",");
        
        int i=subValue.indexOf(','); 
        int j=0;
        double[] matrixDb=new double[6];
  
        while(i !=-1){
          try{
            matrixDb[j]=new Double(subValue.substring(0,i)).doubleValue();
          }catch (Exception ex){return new SVGTransformMatrix(1,0,0,1,0,0);}
          subValue=subValue.substring(subValue.indexOf(',')+1, subValue.length());
          i=subValue.indexOf(',');
          j++;
        }
        matrix=new SVGTransformMatrix(matrixDb[0], matrixDb[1], matrixDb[2], matrixDb[3], matrixDb[4], matrixDb[5]);
      }
    }
    return matrix;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   * @param svgTab
   */
  private Shape getGeometryOutline(SVGTab svgTab, Node node){
    Shape outline=new Rectangle();
    if(svgTab!=null && node!=null && node instanceof Element){
      //gets the bridge context 
      BridgeContext bridgeContext=null;
      if(svgTab.getScrollPane().getSvgCanvas().getUpdateManager()!=null){
        bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
      }
      if(bridgeContext!=null){
        //gets the graphics node corresponding to the given node
        GraphicsNode gnode=null;
        try{
          gnode=bridgeContext.getGraphicsNode((Element)node);
        }catch (Exception e){gnode=null;}
        if(gnode!=null){
          outline=gnode.getOutline();
        }
      }
    }
    return outline;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   * @param svgTab
   */
  private Shape getSensitiveArea(SVGTab svgTab, Node node){
    Shape shape=null;
    if(svgTab!=null && node!=null && node instanceof Element){
      //gets the bridge context 
      BridgeContext bridgeContext=null;
      if(svgTab.getScrollPane().getSvgCanvas().getUpdateManager()!=null){
        bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
      }
      if(bridgeContext!=null){
        //gets the graphics node corresponding to the given node
        GraphicsNode gnode=null;
        try{
          gnode=bridgeContext.getGraphicsNode((Element)node);
        }catch (Exception e){gnode=null;}
        if(gnode!=null){
          try{
            shape=gnode.getOutline();
          }catch (Exception ex){}
          if(shape!=null){
            AffineTransform affineTransform=gnode.getTransform();
            if(affineTransform==null){
              affineTransform=new AffineTransform();
            }
            shape=affineTransform.createTransformedShape(shape);
          }
        }
      }
    }
    return shape;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  private LinkedHashMap getPathSeg(Element node){
		NamedNodeMap attributes=node.getAttributes();
		if(attributes!=null){
			//if the node has the "d" attribute
			Node attribute=attributes.getNamedItem("d");
			if(attribute!=null){
				//gets the value of the "d" attribute
				String value=attribute.getNodeValue();
				value.replaceAll("[,]","");
				
        //removes the first space characters from the string
				if(!value.equals("") && value.charAt(0)==' '){
          value=value.replaceFirst("[\\s]+","");
        }
				
        //if the first character is not a 'M', the string value is not correct
				if(value.charAt(0)=='M' || value.charAt(0)=='m'){

					LinkedHashMap map=new LinkedHashMap();
					String commandString="";
					Double d=null;
					LinkedList list=null;
					int rg=0;
					
					//for each command in the value string
					while(! value.equals("") && Character.isLetter(value.charAt(0))){
					    
						//gets the command
						commandString=value.substring(0,1);
						value=value.substring(1,value.length());

						if(!value.equals("") && value.charAt(0)==' '){
						  value=value.replaceFirst("[\\s]+","");
						}
						
						//the list that will contains the values of the command (Double objects)
						list=new LinkedList();
						while( ! value.equals("") && ! Character.isLetter(value.charAt(0))){
						    
							//adds the Double values found in the value string in the list
							try{
								if(value.indexOf(' ')!=-1){
								  d=new Double(value.substring(0,value.indexOf(' ')));
								}else{
									d=new Double(value);
									value="";	
								}
							}catch (Exception ex){return null;}
							value=value.substring(value.indexOf(' ')+1,value.length());
							if(!value.equals("") && value.charAt(0)==' '){
							  value=value.replaceFirst("[\\s]+","");
							}
							list.add(d);
						}
						//put the command and its value to the map
						map.put(commandString+new Integer(rg++).toString(),list);
					}
					//converts the map made of Double values into a map that contains points and other values
					return convertPathValues(map);
				}
			}
		}
		return null;
	}
  
  /**
   * 
   * @description 
   * @param node
   */
  private void translateFromAttributesToStyle(Node node){
    if(node!=null && node instanceof Element){
      String style=((Element)node).getAttribute("style");
      
      if(style==null){
          style="";
      }

      //the list of the attributes to be removed and added to the style attribute
      HashSet styleProperties=getStudio().getSvgResource().getAttributesToTranslate();
      String name="";
      String value="";
      
      NamedNodeMap attributes=node.getAttributes();
      LinkedList attToBeRemoved=new LinkedList();
      Node attribute=null;

      for(int i=0;i<attributes.getLength();i++){
        attribute=attributes.item(i);

        if(attribute.getNodeName()!=null && styleProperties.contains(attribute.getNodeName())){
          name=attribute.getNodeName();
          value=attribute.getNodeValue();

          if(value!=null && ! value.equals("") && style.indexOf(name+":")==-1){
            //if the attribute is not in the style value, it is added to the style value
            if(style.length()>0 && ! style.endsWith(";")){
              style=style.concat(";");
            }
            
            style=style+name+":"+value+";";		
          }
          attToBeRemoved.add(new String(name));
        }
      }

      //removes the attributes that have been added to the style attribute
      String str="";
      
      for(Iterator it=attToBeRemoved.iterator(); it.hasNext();){
        try{
          str=(String)it.next();
        }catch (Exception ex){str=null;}
        
        if(str!=null && !str.equals("")){
          ((Element)node).removeAttribute(str);
        }
      }
      
      if(style.equals("")){
        //removes the style attribute
        ((Element)node).removeAttribute("style");
      }else{
        //modifies the style attribute
        ((Element)node).setAttribute("style", style);
      }
    }
  }
    
  /**
   * 
   * @description 
   * @param node
   */
  private void transformToMatrix(Node node){
    if(node!=null){
      NamedNodeMap attributes=node.getAttributes();
      if(attributes!=null){
        //if the node has the transform atrribute
        Node att=attributes.getNamedItem("transform");
        if(att!=null){
          //gets the value of the transform attribute
          String value=new String(att.getNodeValue());

          if(value!=null && ! value.equals("")){
            //converts the transforms contained in the string to a single matrix transform
            SVGTransformMatrix matrix=transformToMatrix(value);

            //if the matrix is not the identity matrix, it is set as the new transform matrix
            if(matrix!=null && ! matrix.isIdentity()){
              //if the node has the transform attribute					
              ((Element)node).setAttributeNS(null,"transform", matrix.getMatrixRepresentation());
            }
          }
        }
      }
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  private SVGTransformMatrix transformToMatrix(String value){
    //creates the matrix that will replace the transforms
    SVGTransformMatrix matrix=new SVGTransformMatrix(1,0,0,1,0,0);
  
    if(value!=null && ! value.equals("")){
      String transformName="";
      String transformValues="";
      
      //cleans the string
      value=cleanTransformString(value);
      
      AffineTransform af=new AffineTransform();

      //for each transform found in the value of the transform attribute
      while(value.length()!=0 && value.indexOf('(')!=-1){
        
        //the name of the transform
        transformName=value.substring(0, value.indexOf('('));
        transformValues=value.substring(0, value.indexOf(')'));
        
        //removes the current transform from the value of the transform attribute
        value=value.substring(transformValues.length()+1, value.length());
        
        //the numeric value of the transform
        transformValues=transformValues.substring(transformValues.indexOf('(')+1, transformValues.length());

        //for each kind of transform, gets the numeric values and concatenates the transform to the matrix
        if(transformName.equals("translate")){
          double e=0;
          double f=0;
          
          try{
            e=new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
            f=new Double(transformValues.substring(transformValues.indexOf(',')+1, transformValues.length())).doubleValue();
          }catch(Exception ex){e=0; f=0;}

          af.concatenate(AffineTransform.getTranslateInstance(e, f));
        }else if(transformName.equals("scale")){
          double a=0;
          double d=0;
          
          if(transformValues.indexOf(',')==-1){
            try{
              a=new Double(transformValues).doubleValue();
              d=a;
            }catch(Exception ex){a=0; d=0;	}
          }else{
            try{
              a=new Double(transformValues.substring(0,transformValues.indexOf(','))).doubleValue();
              d=new Double(transformValues.substring(transformValues.indexOf(',')+1,transformValues.length())).doubleValue();
            }catch(Exception ex){a=0; d=0;}
          }

          af.concatenate(AffineTransform.getScaleInstance(a , d));
        }else if(transformName.equals("rotate")){
          double angle=0;
          double e=0;
          double f=0;
          
          if(transformValues.indexOf(',')==-1){
            try{
                angle=new Double(transformValues).doubleValue();
            }catch(Exception ex){angle=0;}

            af.concatenate(AffineTransform.getRotateInstance(Math.toRadians(angle)));
          }else{
            try{
              angle=new Double(transformValues.substring(0,transformValues.indexOf(','))).doubleValue();
              transformValues=transformValues.substring(transformValues.indexOf(',')+1,transformValues.length());
              e=new Double(transformValues.substring(0,transformValues.indexOf(','))).doubleValue();
              transformValues=transformValues.substring(transformValues.indexOf(',')+1,transformValues.length());
              f=new Double(transformValues.substring(0,transformValues.indexOf(','))).doubleValue();
            }catch(Exception ex){angle=0; e=0; f=0;}

            af.concatenate(AffineTransform.getTranslateInstance(e, f));
            af.concatenate(AffineTransform.getRotateInstance(Math.toRadians(angle)));
            af.concatenate(AffineTransform.getTranslateInstance(-e, -f));
          }						
        }else if(transformName.equals("skewX")){
          double angle=0;

          try{
            angle=new Double(transformValues).doubleValue();
          }catch(Exception ex){angle=0;}
          
          af.concatenate(AffineTransform.getShearInstance(Math.tan(Math.toRadians(angle)), 0));
        }else if(transformName.equals("skewY")){
          double angle=0;

          try{
              angle=new Double(transformValues).doubleValue();
          }catch(Exception ex){angle=0;}
          
          af.concatenate(AffineTransform.getShearInstance(0, Math.tan(Math.toRadians(angle))));
        }else if(transformName.equals("matrix")){
          double[] m=new double[6];
          int j=0;
          int i=transformValues.indexOf(',');
          transformValues=transformValues.concat(",");
          
          while(i !=-1){
            try{
              m[j]=new Double(transformValues.substring(0,i)).doubleValue();
            }catch (Exception ex){}
            
            transformValues=transformValues.substring(transformValues.indexOf(',')+1, transformValues.length());

            i=transformValues.indexOf(',');
            
            j++;
          }
          
          af.concatenate(new AffineTransform(m[0], m[1], m[2], m[3], m[4], m[5]));
        }else{
          break;
        }
      }
      matrix.concatenateTransform(af);
    }
    return matrix;
  }  
  /**
   * 
   * @description 
   * @param node
   */
  private void removeTspans(Node node){
    if(node!=null && node.getNodeName().equals("text")){
      String value=getText(node);
      if(value==null){
        value="";
      }

      //removes all the text children from the node
      NodeList children=node.getChildNodes();
      for(int i=0; i<children.getLength(); i++){
        if(		children.item(i)!=null && 
              (children.item(i) instanceof Text || children.item(i).getNodeName().equals("tspan"))){
          node.removeChild(children.item(i));
        }
      }
      
      children=node.getChildNodes();
      for(int i=0; i<children.getLength(); i++){
        if(		children.item(i)!=null && 
                (children.item(i) instanceof Text || children.item(i).getNodeName().equals("tspan"))){
          node.removeChild(children.item(i));
        }
      }

      //adds a #text node
      Document doc=node.getOwnerDocument();
      if(doc!=null){
        Text txt=doc.createTextNode(value);
        node.appendChild(txt);
      }
    }
  }
    
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  private String getText(Node node){
    String value="";
    if(node!=null && (node.getNodeName().equals("text") || node.getNodeName().equals("tspan"))){
      //for each child of the given node, computes the text it contains and concatenates it to the current value
      for(Node cur=node.getFirstChild();cur!=null;cur=cur.getNextSibling()){
        if(cur.getNodeName().equals("#text")){
          value=value+cur.getNodeValue();
        }else if(cur.getNodeName().equals("tspan")){
          value=value+getText(cur);
        }
      }
    }
    value=normalizeTextNodeValue(value);
    return value;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  public String cleanTransformString(String value){
    String newValue=new String(value);
    newValue=newValue.replaceAll("0\\s","0,");
    newValue=newValue.replaceAll("1\\s","1,");
    newValue=newValue.replaceAll("2\\s","2,");
    newValue=newValue.replaceAll("3\\s","3,");
    newValue=newValue.replaceAll("4\\s","4,");
    newValue=newValue.replaceAll("5\\s","5,");
    newValue=newValue.replaceAll("6\\s","6,");
    newValue=newValue.replaceAll("7\\s","7,");
    newValue=newValue.replaceAll("8\\s","8,");
    newValue=newValue.replaceAll("9\\s","9,");
    newValue=newValue.replaceAll("\\s*[,]\\s*[,]\\s*",",");
    newValue=newValue.replaceAll("\\s+","");
    return newValue;
  }

  /**
   * 
   * @description 
   * @return 
   * @param toBaseScale
   * @param rectangle
   * @param svgTab
   */
  public Rectangle2D.Double getScaledRectangle(SVGTab svgTab, Rectangle2D.Double rectangle, boolean toBaseScale){
    if(svgTab!=null){
      double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
      double transformX=svgTab.getScrollPane().getTranslateValues().width; 
      double transformY=svgTab.getScrollPane().getTranslateValues().height;
      double x;
      double y;
      double width;
      double height;

      if(toBaseScale){
        x=(rectangle.getX()-transformX)/scale;
        y=(rectangle.getY()-transformY)/scale;
        width=rectangle.getWidth()/scale;
        height=rectangle.getHeight()/scale;
      }else{
        x=rectangle.getX()*scale+transformX;
        y=rectangle.getY()*scale+transformY;
        width=rectangle.getWidth()*scale;
        height=rectangle.getHeight()*scale;			
      }
      return new Rectangle2D.Double(x,y,width,height);
    }
    return null;
  }

  /**
   * 
   * @description 
   * @return 
   * @param point
   * @param parent
   * @param svgTab
   */
  public Node getNodeAt(SVGTab svgTab, Node parentNode, Point point){
    if(svgTab!=null && parentNode!=null && point!=null){
      Rectangle2D.Float rect=new Rectangle2D.Float(point.x-2, point.y-2, 4, 4);
      int width=0; 
      int height=0;
      Node currentNode=null;
      Shape shape=null;
      Rectangle bounds=null;
      for(currentNode=parentNode.getLastChild();currentNode!=null; currentNode=currentNode.getPreviousSibling()){
        //gets the graphics node linked with the current node
        if(currentNode instanceof Element && ! currentNode.getNodeName().equals("defs")){
          shape=getSensitiveArea(svgTab, currentNode);
          if(shape!=null){
            bounds=shape.getBounds();
            //tests if the width or the height of the area equals 0, and then sets it to 1
            if(bounds.width==0 || bounds.height==0){
              width=bounds.width;
              height=bounds.height;
              if(width==0)width=1;
              if(height==0)height=1;
              shape=new Rectangle2D.Double(bounds.x, bounds.y, width, height);
            }
            if(shape.intersects(rect)){
              return currentNode;
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
   * @param node
   * @param svgTab
   */
  public Rectangle2D getNodeBounds(SVGTab svgTab, Node node){
    Rectangle2D bounds=new Rectangle();
    if(svgTab!=null && node!=null){
      //gets the bridge context 
      BridgeContext bridgeContext=null;
      if(svgTab.getScrollPane().getSvgCanvas().getUpdateManager()!=null){
        bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
      }
      if(bridgeContext!=null){
        //gets the graphics node corresponding to the given node
        GraphicsNode gNode=null;
        try{
          gNode=bridgeContext.getGraphicsNode((Element)node);
        }catch (Exception e){gNode=null;}
        if(gNode!=null){
          bounds=gNode.getTransformedBounds(new AffineTransform());
        }
      }
    }
    return bounds;
  }

  /**
   * 
   * @description 
   * @return 
   * @param point2
   * @param point1
   */
  public Rectangle getComputedSquare(Point point1, Point point2){
    if(point1!=null && point2!=null){
      int width=point2.x-point1.x;
      int height=point2.y-point1.y;
      int x=point1.x;
      int y=point1.y;
      
      if(point1.x>point2.x && point1.y>point2.y){
        x=point2.x;
        y=point2.y;
        width=point1.x-point2.x;
        height=point1.y-point2.y;
        if(width<height){
          y=point2.y+(height-width);
          height=width;
        }else{
          x=point2.x+(width-height);
          width=height;
        }
      }else if(point1.x>point2.x && point1.y<=point2.y){
        width=point1.x-point2.x;
        height=point2.y-point1.y;
        x=point2.x;
        y=point1.y;
        if(width<height){
          height=width;
        }else{
          x=point2.x+(width-height);
          width=height;
        }
      }else if(point1.x<=point2.x && point1.y>point2.y){
        width=point2.x-point1.x;
        height=point1.y-point2.y;
        x=point1.x;
        y=point2.y;
        if(width<height){
          y=point2.y+(height-width);
          height=width;
        }else{
          width=height;
        }
      }else if(point1.x<=point2.x && point1.y<=point2.y){
        if(width<height){
          height=width;
        }else{
          width=height;
        }
      }
      return new Rectangle(x, y, width, height);	
    }
    return new Rectangle(0, 0, 0, 0);
  }

  /**
   * 
   * @description 
   * @return 
   * @param point2
   * @param point1
   */
  public Rectangle getComputedRectangle(Point point1, Point point2){
    if(point1!=null && point2!=null){
      int width=point2.x-point1.x; 
      int height=point2.y-point1.y; 
      int x=point1.x; 
      int y=point1.y;
      if(point1.x>point2.x && point1.y>point2.y){
        x=point2.x;
        y=point2.y;
        width=point1.x-point2.x;
        height=point1.y-point2.y;
      }else if(point1.x>point2.x && point1.y<point2.y){
        width=point1.x-point2.x;
        height=point2.y-point1.y;
        x=point2.x;
        y=point1.y;
      }else if(point1.x<point2.x && point1.y>point2.y){
        width=point2.x-point1.x;
        height=point1.y-point2.y;
        x=point1.x;
        y=point2.y;
      }
      return new Rectangle(x, y, width, height);	
    }
    return new Rectangle(0, 0, 0, 0);
  }

  /**
   * 
   * @description 
   * @return 
   * @param node
   * @param svgTab
   */
  public Shape getTransformedOutline(SVGTab svgTab, Node node){
    Shape outline=new Rectangle();
    if(svgTab!=null && node!=null){
      outline=getGeometryOutline(svgTab, node);
      //gets the bridge context 
      BridgeContext bridgeContext=null;
      if(svgTab.getScrollPane().getSvgCanvas().getUpdateManager()!=null){
        bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
      }
      if(bridgeContext!=null){
        //gets the graphics node corresponding to the given node
        GraphicsNode gNode=null;
        try{
          gNode=bridgeContext.getGraphicsNode((Element)node);
        }catch (Exception e){gNode=null;}
        if(gNode!=null){
          AffineTransform affineTransform=gNode.getTransform();
          if(affineTransform!=null){
            outline=affineTransform.createTransformedShape(outline);
          }
        }
      }
    }
    return outline;
  }

  
  /**
   * 
   * @description 
   * @param g
   */
  public void normalizeGroupNode(Element g){
    if(g!=null && g.getNodeName().equals("g")){
      SVGTransformMatrix gMatrix=getTransformMatrix(g), matrix=null;
      if(! gMatrix.isIdentity()){
        for(Node currentNode=g.getFirstChild(); currentNode!=null; currentNode=currentNode.getNextSibling()){
          if(currentNode instanceof Element){
            //getting, modifying and setting the matrix of this node
            matrix=getTransformMatrix(currentNode);
            matrix.concatenateMatrix(gMatrix);
            setTransformMatrix(currentNode, matrix);
          }
        }
        //setting the matrix of the g element to identity
        g.removeAttribute("transform");
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  public String normalizeTextNodeValue(String value){
    String textValue="";
    if(value!=null && !value.equals("")){
      textValue=new String(value);
      textValue=textValue.replaceAll("\\t+", " ");
      textValue=textValue.replaceAll("\\s+", " ");
      textValue.trim();
      if(! textValue.equals("") && textValue.charAt(0)==' '){
        textValue=textValue.substring(1, textValue.length());
      }
      if(! textValue.equals("") && textValue.charAt(textValue.length()-1)==' '){
        textValue=textValue.substring(0, textValue.length()-1);
      }
    }
    return textValue;
  }

  /**
   * 
   * @description 
   * @return 
   * @param toBaseScale
   * @param point
   * @param svgTab
   */
  public Point2D.Double getScaledPoint(SVGTab svgTab, Point2D.Double point, boolean toBaseScale){
    if(svgTab!=null){
      double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
      double transformX=svgTab.getScrollPane().getTranslateValues().width; 
      double transformY=svgTab.getScrollPane().getTranslateValues().height;
      if(point!=null){
        double x=point.getX(); 
        double y=point.getY();
        if(toBaseScale){
          x=(point.getX()-transformX)/scale;
          y=(point.getY()-transformY)/scale;
        }else{
          x=point.getX()*scale+transformX;
          y=point.getY()*scale+transformY;	
        }
        return new Point2D.Double(x,y);
      }else if(point!=null){
        return new Point2D.Double(point.getX(),point.getY());
      }
    }
    return null;
  }

  /**
   * 
   * @description 
   * @return 
   * @param point
   * @param svgTab
   */
  public Point getAlignedWithRulersPoint(SVGTab svgTab, Point point){
    if(svgTab!=null && point!=null){
      return svgTab.getScrollPane().getAlignedWithRulersPoint(point);
    }
    return null;
  }

  

  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  public SVGTransformMatrix getTransformMatrix(Node node){
    if(node!=null){
      NamedNodeMap attributes=node.getAttributes();
      if(attributes!=null){
        //if the node has the transform atrribute
        Node attribute=attributes.getNamedItem("transform");
        if(attribute!=null){
          //gets the value of the transform attribute
          String value=attribute.getNodeValue();
          //creating the matrix transform
          return getTransformMatrix(value);
        }
      }
    }
    //otherwise returns the identity matrix
    return new SVGTransformMatrix(1,0,0,1,0,0);
  }

  /**
   * 
   * @description 
   * @param matrix
   * @param node
   */
  public void setTransformMatrix(Node node, SVGTransformMatrix matrix){
    if(node!=null && node instanceof Element && matrix!=null){
      if(node.getNodeName().equals("g")){
        SVGTransformMatrix currentNodeMatrix=null;
        //for each child of the g element, gets its matrix, modifies and sets it
        for(Node currentNode=node.getFirstChild(); currentNode!=null; currentNode=currentNode.getNextSibling()){
          if(currentNode instanceof Element){
            currentNodeMatrix=getTransformMatrix(currentNode);
            currentNodeMatrix.concatenateMatrix(matrix);
            setTransformMatrix(currentNode, currentNodeMatrix);
          }
        }
        ((Element)node).removeAttribute("transform");
      }else{
        //if the node has the transform attribute					
        ((Element)node).setAttributeNS(null,"transform", matrix.getMatrixRepresentation());
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param str
   */
  public double getPixelledNumber(String string){
    double pixelledNumber=0;
    if(string!=null && ! string.equals("")){
      string=string.trim();
      if(! Character.isDigit(string.charAt(string.length()-1))){
        String unit=string.substring(string.length()-2, string.length());
        String number=string.substring(0, string.length()-2);
        
        try{
          pixelledNumber=Double.parseDouble(number);
        }catch (Exception ex){}
        
        if(unit.equals("pt")){
            pixelledNumber*=1.25;
        }else if(unit.equals("pc")){
            pixelledNumber*=15;
        }else if(unit.equals("mm")){
            pixelledNumber*=3.543307;
        }else if(unit.equals("cm")){
            pixelledNumber*=35.43307;
        }else if(unit.equals("in")){
            pixelledNumber*=90;
        }
      }else{
        try{
          pixelledNumber=Double.parseDouble(string);
        }catch (Exception ex){}
      }
    }
    return pixelledNumber;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param element
   */
  public boolean isElementAShape(Element element){
    if(element!=null){
      String name=element.getNodeName();
      return (name.equals("g") || name.equals("circle") || name.equals("ellipse") || name.equals("image") || name.equals("line")
            || name.equals("path") || name.equals("polygon") || name.equals("polyline") || name.equals("rect") || name.equals("text"));
    }
    return false;
  }

  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param element
   */
  public ExtendedGeneralPath getGeneralPath(SVGTab svgTab, Element element){
    ExtendedGeneralPath path=null;
    if(svgTab!=null && element!=null){
      Map segs=getPathSeg(element);
      Point2D.Double point1=null;
      Point2D.Double point2=null;
      Point2D.Double point3=null;
      
      double d1=0;
      double d2=0;
      double d3=0;

      int i1=0;
      int i2=0;
      
      String commandString=""; 
      char cmdCharacter=' ';
      java.util.List list=null;
      path=new ExtendedGeneralPath();
      
      for(Iterator it=segs.keySet().iterator(); it.hasNext();){
        try{
          commandString=(String)it.next();
          list=(java.util.List)segs.get(commandString);
          cmdCharacter=commandString.charAt(0);
        }catch (Exception ex){cmdCharacter=' '; list=null;}

        try{
          if(cmdCharacter!=' ' && list!=null){
            if(cmdCharacter=='Z'){
                path.closePath();
            }else if(cmdCharacter=='C'){
              point1=(Point2D.Double)list.get(0);
              point2=(Point2D.Double)list.get(1);
              point3=(Point2D.Double)list.get(2);
              path.curveTo((float)point1.x, (float)point1.y, (float)point2.x, (float)point2.y, (float)point3.x, (float)point3.y);
            }else if(cmdCharacter=='L'){
              point1=(Point2D.Double)list.get(0);
              path.lineTo((float)point1.x, (float)point1.y);
            }else if(cmdCharacter=='M'){
              point1=(Point2D.Double)list.get(0);
              path.moveTo((float)point1.x, (float)point1.y);
            }else if(cmdCharacter=='Q'){
              point1=(Point2D.Double)list.get(0);
              point2=(Point2D.Double)list.get(1);
              path.quadTo((float)point1.x, (float)point1.y, (float)point2.x, (float)point2.y);
            }else if(cmdCharacter=='A'){
              d1=((Double)list.get(0)).doubleValue();
              d2=((Double)list.get(1)).doubleValue();
              d3=((Double)list.get(2)).doubleValue();
              i1=((Integer)list.get(3)).intValue();
              i2=((Integer)list.get(4)).intValue();
              point1=(Point2D.Double)list.get(5);
              path.arcTo((float)d1, (float)d2, (float)d3, i1==0?false:true, i2==0?false:true, (float)point1.x, (float)point1.y);
            }
          }
        }catch (Exception ex){}
      }
      SVGTransformMatrix matrix=getTransformMatrix(element);
      if(matrix!=null){
        //transforms the path
        AffineTransform af=matrix.getTransform();
        path=new ExtendedGeneralPath(path.createTransformedShape(af));
      }
    }
    return path;
	}

  public LinkedHashMap convertPathValues(LinkedHashMap map){
		if(map.size()>0){
			//the map that will be returned
			LinkedHashMap map2=new LinkedHashMap();
			Iterator it2=null;
			LinkedList list=null, nlist=null;
			Point2D.Double lastPoint=null, point=null, beforeLastPoint=null;
			Double[] dtab=new Double[6];
			int rg=0, i;
			String command="";
			char cmd=' ';
      char lastCmd=' ';
								
			//for each command in the map
			for(Iterator it=map.keySet().iterator(); it.hasNext();){
									
				try{
          command=(String)it.next();
        }catch (Exception ex){return null;}
				
				//gets the list of the Double values
				if(! command.equals("")){
					try{
            list=(LinkedList)map.get(command);
          }catch (Exception ex){return null;}
				}
				
				if(list!=null){
				    
					cmd=command.charAt(0);
					it2=list.iterator();
										
					//according to the command
					if(cmd=='M' || cmd=='m' || cmd=='L' || cmd=='l'){

						while(it2.hasNext()){
							//the list of the points of the command
							nlist=new LinkedList();
							try{
								dtab[0]=(Double)it2.next();
								dtab[1]=(Double)it2.next();
							}catch (Exception ex){return null;}
												
							//adds the point created with the two double values to the list
							if(Character.isLowerCase(cmd)){
                if(lastPoint==null){
                  lastPoint=new Point2D.Double(0, 0);
                }
								point=new Point2D.Double(lastPoint.x+dtab[0].doubleValue(), lastPoint.y+dtab[1].doubleValue());
							}else{
								point=new Point2D.Double(dtab[0].doubleValue(),dtab[1].doubleValue());
							}
							
							//sets the value of the lastPoint
							beforeLastPoint=lastPoint;
							lastPoint=point;
							nlist.add(point);
							//adds the command to the list
							map2.put(Character.toUpperCase(cmd)+new Integer(rg++).toString(),nlist);
						}
					}else if(cmd=='T' || cmd=='t'){
						while(it2.hasNext()){
							
              //the list of the points of the command
							nlist=new LinkedList();
							
              //computing the control point 
							if(lastPoint==null){
							  lastPoint=new Point2D.Double(0, 0);
							}
							
							//adding the first control point
							if(lastCmd=='Q' && beforeLastPoint!=null){
								point=new Point2D.Double(2*lastPoint.x-beforeLastPoint.x, 2*lastPoint.y-beforeLastPoint.y);
							  nlist.add(point);
							  beforeLastPoint=lastPoint;
							  lastPoint=point;
							}else{
                nlist.add(lastPoint);
                beforeLastPoint=lastPoint;
							}

							//computing the new current point
							try{
								dtab[0]=(Double)it2.next();
								dtab[1]=(Double)it2.next();
							}catch (Exception ex){return null;}
												
							//adds the point created with the two double values to the list
							if(Character.isLowerCase(cmd)){
							    if(lastPoint==null){
                    lastPoint=new Point2D.Double(0, 0);
							    }
                  point=new Point2D.Double(lastPoint.x+dtab[0].doubleValue(), lastPoint.y+dtab[1].doubleValue());
							}else{
								point=new Point2D.Double(dtab[0].doubleValue(),dtab[1].doubleValue());
							}
							
							//sets the value of the lastPoint
							beforeLastPoint=lastPoint;
							lastPoint=point;
							nlist.add(point);
												
							//adds the command to the list
							cmd='Q';
							map2.put(cmd+new Integer(rg++).toString(),nlist);
						}
					}else if(cmd=='Q' || cmd=='q'){

						while(it2.hasNext()){
							//the list of the points of the command
							nlist=new LinkedList();
							try{
								dtab[0]=(Double)it2.next();
								dtab[1]=(Double)it2.next();
								dtab[2]=(Double)it2.next();
								dtab[3]=(Double)it2.next();
							}catch (Exception ex){return null;}

							for(i=0; i<4; i+=2){
								//adds the point created with the two double values to the list
								if(Character.isLowerCase(cmd)){
                  if(lastPoint==null){
                    lastPoint=new Point2D.Double(0, 0);
                  }
                  point=new Point2D.Double(lastPoint.x+dtab[i].doubleValue(), lastPoint.y+dtab[i+1].doubleValue());
								}else{
								  point=new Point2D.Double(dtab[i].doubleValue(),dtab[i+1].doubleValue());
								}
								
								//sets the value of the lastPoint
								beforeLastPoint=lastPoint;
								lastPoint=point;
								nlist.add(point);
							}
							//adds the command to the list
							cmd=Character.toUpperCase(cmd);
							map2.put(cmd+new Integer(rg++).toString(), nlist);
						}
					}else if(cmd=='C' || cmd=='c'){
						while(it2.hasNext()){
							//the list of the points of the command
							nlist=new LinkedList();
							try{
								dtab[0]=(Double)it2.next();
								dtab[1]=(Double)it2.next();
								dtab[2]=(Double)it2.next();
								dtab[3]=(Double)it2.next();
								dtab[4]=(Double)it2.next();
								dtab[5]=(Double)it2.next();
							}catch (Exception ex){return null;}
							
							for(i=0; i<6; i+=2){
								//adds the point created with the two double values to the list
								if(Character.isLowerCase(cmd)){
                  if(lastPoint==null){
                    lastPoint=new Point2D.Double(0, 0);
                  }
                  point=new Point2D.Double(lastPoint.x+dtab[i].doubleValue(), lastPoint.y+dtab[i+1].doubleValue());
								}else{
                  point=new Point2D.Double(dtab[i].doubleValue(),dtab[i+1].doubleValue());
								}
								//sets the value of the lastPoint
								beforeLastPoint=lastPoint;
								lastPoint=point;
								nlist.add(point);
							}
							//adds the command to the list
							cmd=Character.toUpperCase(cmd);
							map2.put(cmd+new Integer(rg++).toString(), nlist);
						}
					}else if(cmd=='S' || cmd=='s'){
						while(it2.hasNext()){
							//the list of the points of the command
							nlist=new LinkedList();
							
							//computing the control point 
							if(lastPoint==null){
							  lastPoint=new Point2D.Double(0, 0);
							}
							
							//adding the first control point
							if(lastCmd=='Q' && beforeLastPoint!=null){
								point=new Point2D.Double(2*lastPoint.x-beforeLastPoint.x, 2*lastPoint.y-beforeLastPoint.y);
                nlist.add(point);
                beforeLastPoint=lastPoint;
                lastPoint=point;
							}else{
                nlist.add(lastPoint);
                beforeLastPoint=lastPoint;
							}
								
							//computing the two next points
							try{
								dtab[0]=(Double)it2.next();
								dtab[1]=(Double)it2.next();
								dtab[2]=(Double)it2.next();
								dtab[3]=(Double)it2.next();
							}catch (Exception ex){return null;}
					
							for(i=0; i<4; i+=2){
								//adds the point created with the two double values to the list
								if(Character.isLowerCase(cmd)){
                  if(lastPoint==null){
                    lastPoint=new Point2D.Double(0, 0);
                  }
                  point=new Point2D.Double(lastPoint.x+dtab[i].doubleValue(), lastPoint.y+dtab[i+1].doubleValue());
								}else{
                  point=new Point2D.Double(dtab[i].doubleValue(),dtab[i+1].doubleValue());
								}
								
								//sets the value of the lastPoint
								beforeLastPoint=lastPoint;
								lastPoint=point;
								nlist.add(point);
							}
												
							//adds the command to the list
							cmd='C';
							map2.put(cmd+new Integer(rg++).toString(), nlist);
						}
					}else if(cmd=='H' || cmd=='h'){
						if(list!=null && list.size()>0){
							while(it2.hasNext()){
								//the list of the values of the command
								nlist=new LinkedList();
								try{
									dtab[0]=(Double)it2.next();
								}catch (Exception e){dtab[0]=null;}
												
								if(dtab[0]!=null){
									if(Character.isLowerCase(cmd)){
                    if(lastPoint==null){
                      lastPoint=new Point2D.Double(0, 0);
                    }
										point=new Point2D.Double(lastPoint.x+dtab[0].doubleValue(), lastPoint.y);
									}else{
										point=new Point2D.Double(dtab[0].doubleValue(), lastPoint.y);
									}
									
									beforeLastPoint=lastPoint;
									lastPoint=point;
									nlist.add(point);
								}

								//adds the command to the list
								cmd='L';
								map2.put(cmd+new Integer(rg++).toString(),nlist);
							}
						}
										
					}else if(cmd=='V' || cmd=='v'){

						if(list!=null && list.size()>0){
							while(it2.hasNext()){
								//the list of the values of the command
								nlist=new LinkedList();
								try{
									dtab[0]=(Double)it2.next();
								}catch (Exception e){dtab[0]=null;}
								if(dtab[0]!=null){
									if(Character.isLowerCase(cmd) && lastPoint!=null){
                    if(lastPoint==null){
                      lastPoint=new Point2D.Double(0, 0);
                    }
										point=new Point2D.Double(lastPoint.x, lastPoint.y+dtab[0].doubleValue());
									}else{
										point=new Point2D.Double(lastPoint.x, dtab[0].doubleValue());
									}
									beforeLastPoint=lastPoint;
									lastPoint=point;
									nlist.add(point);
								}
													
								//adds the command to the list
								cmd='L';
								map2.put(cmd+new Integer(rg++).toString(),nlist);
							}
						}

					}else if(cmd=='A' || cmd=='a'){

						if(list.size()%7==0){
							while(it2.hasNext()){
								//the list of the values of the command
								nlist=new LinkedList();
													
								//adds the values to the list
								nlist.add(it2.next());
								if(it2.hasNext()){
								  nlist.add(it2.next());
								}
								
								if(it2.hasNext()){
								  nlist.add(it2.next());
								}
													
								if(it2.hasNext()){
									dtab[0]=(Double)it2.next();
									if(dtab[0]!=null){
										nlist.add(new Integer((int)dtab[0].doubleValue()));
									}
								}
													
								if(it2.hasNext()){
									dtab[0]=(Double)it2.next();
									if(dtab[0]!=null){
										nlist.add(new Integer((int)dtab[0].doubleValue()));
									}
								}
													
								//creates the point with the two last double values
								if(it2.hasNext()){
									try{
										dtab[0]=(Double)it2.next();
										if(it2.hasNext())dtab[1]=(Double)it2.next();
									}catch (Exception ex){return null;}	
														
									if(dtab[0]!=null && dtab[1]!=null){
										if(Character.isLowerCase(cmd)){
                      if(lastPoint==null){
                        lastPoint=new Point2D.Double(0, 0);
                      }
											point=new Point2D.Double(dtab[0].doubleValue()+lastPoint.x, dtab[1].doubleValue()+lastPoint.y);
										}else{
											point=new Point2D.Double(dtab[0].doubleValue(), dtab[1].doubleValue());
										}
										beforeLastPoint=lastPoint;
										lastPoint=point;
										nlist.add(point);
									}
								}
													
								//adds the command to the list
								cmd='A';
								map2.put(cmd+new Integer(rg++).toString(),nlist);			
							}
						}
					}else if(cmd=='Z' || cmd=='z'){
						nlist=new LinkedList();
						nlist.add(new Point2D.Double());
						//adds the command to the list
						map2.put(cmd+new Integer(rg++).toString(),nlist);
					}else{
					    return null;
					}
          lastCmd=cmd;
        }				
			}
			return map2;
		}
		return null;
	}
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   */
  public Element getDefsElement(JSVGCanvas svgCanvas){
    Element defs=null;
    if(svgCanvas!=null){
        Document document=svgCanvas.getSVGDocument();
        if(document!=null){
          Element root=document.getDocumentElement();
          if(root!=null){
            NodeList defsNodes=document.getElementsByTagName("defs");
            if(defsNodes!=null && defsNodes.getLength()>0){
              try{
                defs=(Element)defsNodes.item(0);
              }catch (Exception ex){}
            }

            if(defs==null && svgCanvas.getUpdateManager()!=null && 
                  svgCanvas.getUpdateManager().getUpdateRunnableQueue()!=null){

              //adds a "defs" node to the svg document
              defs=document.createElementNS(null, "defs");
              RunnableQueue queue=svgCanvas.getUpdateManager().getUpdateRunnableQueue();                       
              
              final Node finalRoot=root;
              final Node finalDefs=defs;
              
              //the runnable that contains the code that will be executed to append the "defs" node to the root node
              Runnable runnable=new Runnable(){
                public void run() {
                  if(finalRoot.getFirstChild()!=null){
                    finalRoot.insertBefore(finalDefs, finalRoot.getFirstChild());
                  }else{
                    finalRoot.appendChild(finalDefs);
                  }
                }
              };
              if(queue.getThread()!=Thread.currentThread()){
                queue.invokeLater(runnable);
              }else{
                runnable.run();
              }
            }
          }
      }
    }
    return defs;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param baseString
   * @param svgTab
   */
  public String getId(JSVGCanvas svgCanvas, String baseString){
    if(svgCanvas!=null){
      SVGDocument doc=svgCanvas.getSVGDocument();
      if(doc!=null){
        LinkedList ids=new LinkedList();
        Node current=null;
        String attId="";
        
        //adds to the list all the ids found among the children of the root element
        for(NodeIterator it=new NodeIterator(doc.getDocumentElement()); it.hasNext();){
          try{
            current=(Node)it.next();
          }catch (Exception ex){current=null;}

          if(current!=null && current instanceof Element){
            attId=((Element)current).getAttribute("id");
            if(attId!=null && ! attId.equals("")){
              ids.add(attId);
            }
          }
        }
        
        int i=0;
        //tests for each integer string if it is already an id
        while(ids.contains(baseString.concat(i+""))){
          i++;
        }
        return new String(baseString.concat(i+""));
      }
    }
    return "";
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param id
   * @param svgTab
   */
  public boolean checkId(SVGTab svgTab, String id){
    if(svgTab!=null){
      SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
      if(document!=null){
        LinkedList ids=new LinkedList();
        Node current=null;
        String attributeId="";
        //adds to the list all the ids found among the children of the root element
        for(NodeIterator it=new NodeIterator(document.getDocumentElement()); it.hasNext();){
          try{current=(Node)it.next();}catch (Exception ex){current=null;}
          if(current!=null && current instanceof Element){
            attributeId=((Element)current).getAttribute("id");
            if(attributeId!=null && ! attributeId.equals("")){
              ids.add(attributeId);
            }
          }
        }
        
        //tests for each integer string if it is already an id
        if(ids.contains(id)){
          return false;
        }else{
          return true; 
        }
      }
    }
    return false;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param deep
   * @param element
   */
  public LinkedList getResourcesUsedByNode(Element element, boolean deep){
    LinkedList resources=new LinkedList();
    if(element!=null){
      //getting the defs element
      Element root=element.getOwnerDocument().getDocumentElement();
      Node currentNode=null;
      Element defs=null;
      
      for(currentNode=root.getFirstChild(); currentNode!=null; currentNode=currentNode.getNextSibling()){
        if(currentNode instanceof Element && currentNode.getNodeName().equals("defs")){
          defs=(Element)currentNode;
        }
      }
      
      //the string containing the ids of the resources needed
      String style=element.getAttribute("style");
      if(deep){
        for(NodeIterator it=new NodeIterator(element); it.hasNext();){
          currentNode=(Node)it.next();
          if(currentNode instanceof Element){
            style=style.concat(((Element)currentNode).getAttribute("style"));
          }
        }
      }

      if(defs!=null && style!=null && ! style.equals("")){
        String id="";
        //for each child of the "defs" element, adds it to the list if it is used by the given element
        for(currentNode=defs.getFirstChild(); currentNode!=null; currentNode=currentNode.getNextSibling()){
          if(currentNode instanceof Element){
            id=((Element)currentNode).getAttribute("id");
            //if the id of the resource is contained in the style attribute
            if(id!=null && style.indexOf("#".concat(id))!=-1){
              resources.add(currentNode);
            }
          }
        }
      }
    }
    return resources;
  }
   
  public void registerUsedResource(SVGTab svgTab, Node node){
    if(svgTab!=null && node!=null && node instanceof Element){
      //the map of the used resources
      Map usedResources=svgTab.getScrollPane().getSvgCanvas().getUsedResources();

    //getting the style attribute
      String style=((Element)node).getAttribute("style"), id="";
      if(style!=null && ! style.equals("")){
        LinkedList nodesList=null;
        //for each resource id, checks if it is contained in the style attribute
        for(Iterator it=usedResources.keySet().iterator(); it.hasNext();){
          try{
            id=(String)it.next();
            nodesList=(LinkedList)usedResources.get(id);
          }catch (Exception ex){id=null; nodesList=null;}
          
          //adds the node in the used resource map
          if(id!=null && ! id.equals("") && style.indexOf("#".concat(id))!=-1 && nodesList!=null && ! nodesList.contains(node)){
            svgTab.getScrollPane().getSvgCanvas().addNodeUsingResource(id, node);
          }
        }
      }
    }
  } 
  
  public void unregisterAllUsedResource(SVGTab svgTab, Node node){
    if(svgTab!=null && node!=null && node instanceof Element){
  
      //the map of the used resources
      Map usedResources=svgTab.getScrollPane().getSvgCanvas().getUsedResources();
  
      //getting the style attribute
      String style=((Element)node).getAttribute("style"), id="";
      
      if(style!=null && ! style.equals("")){
        LinkedList nodesList=null;
        //for each resource id, checks if it is contained in the style attribute
        for(Iterator it=usedResources.keySet().iterator(); it.hasNext();){
          try{
            id=(String)it.next();
            nodesList=(LinkedList)usedResources.get(id);
          }catch (Exception ex){id=null; nodesList=null;}
          
          //removes the node from the used resource map
          if(id!=null && ! id.equals("") && style.indexOf("#".concat(id))!=-1 && nodesList!=null && ! nodesList.contains(node)){
            svgTab.getScrollPane().getSvgCanvas().removeNodeUsingResource(id, node);
          }
        }
      }
    }
  }
  
  public String getStyleProperty(Element element, String name){
    String value="";
        
		if(element!=null && name!=null && ! name.equals("")){
			//gets the value of the style attribute
			String styleValue=element.getAttribute("style");
			styleValue=styleValue.replaceAll("\\s*[;]\\s*", ";");
			styleValue=styleValue.replaceAll("\\s*[:]\\s*", ":");

			int rg=styleValue.indexOf(";".concat(name.concat(":")));
			if(rg!=-1){
			  rg++;
			}

			if(rg==-1){
				rg=styleValue.indexOf(name.concat(":"));
				if(rg!=0){
				  rg=-1;
				}
			}
			
			//if the value of the style attribute contains the property
			if(styleValue!=null && ! styleValue.equals("") && rg!=-1){
				//computes the value of the property
				value=styleValue.substring(rg+name.length()+1, styleValue.length());
				rg=value.indexOf(";");
				value=value.substring(0, rg==-1?value.length():rg);
			}
		}
		return value;
  }
    
  public void setStyleProperty(Element element, String name, String value){
		if(element!=null && name!=null && ! name.equals("") && value!=null){
			
			//gets the value of the style attribute
			String styleValue=element.getAttribute("style");
			styleValue=styleValue.replaceAll("\\s*[;]\\s*", ";");
			styleValue=styleValue.replaceAll("\\s*[:]\\s*", ":");
	
			int rg=styleValue.indexOf(";".concat(name.concat(":")));
			
			if(rg!=-1){
        rg++;
      }

			if(rg==-1){
				rg=styleValue.indexOf(name.concat(":"));
				if(rg!=0)rg=-1;
			}
		
			//if the value contains the property
			if(styleValue!=null && ! styleValue.equals("") && rg!=-1){
			
				//removes the property from the style attribute value 
				String 	pValue=styleValue.substring(rg+name.length()+1, styleValue.length());
				String 	bValue=styleValue.substring(0,rg);
							
				int end=pValue.indexOf(";");
				
				if(end==-1){
				  end=pValue.length();
				}else{
				  end++;
				}
			
				String eValue=pValue.substring(end,pValue.length());
				
				//tests if the value of the style attribute, from which the current property has been removed, ends with a coma
				boolean endsWithComa=false;
			
				if(! eValue.equals("")){
					if(eValue.charAt(eValue.length()-1)!=';'){
					  endsWithComa=true;
					}
				}else if(! bValue.equals("")){
					if(bValue.charAt(bValue.length()-1)!=';'){
					  endsWithComa=true;
					}
				}
			
				//the new vlue of the style attribute
				styleValue=((bValue.concat(eValue)).concat(endsWithComa?";":"")).concat(name.concat(":"+value+";"));
			}else{
				if(styleValue.length()>0 && styleValue.charAt(styleValue.length()-1)!=';'){
				  styleValue=styleValue.concat(";");
				}
				styleValue=styleValue.concat(name.concat((":".concat(value)).concat(";")));
			}
		
			//sets the value of the style attribute
			if(styleValue!=null && ! styleValue.equals("")){
				element.setAttribute("style", styleValue);
			}
		}	
  }

  /**
   * 
   * @description 
   * @return 
   * @param point
   */
  public Color pickColor(Point point){
    Color color=new Color(255, 255, 255);
    if(point!=null){
      try{
        //getting the color at this point
        Robot robot=new Robot();
        color=robot.getPixelColor(point.x, point.y);
      }catch (Exception ex){}
    }
    return color;
  }
  
  /**
   * 
   * @description 
   * @param node
   */
  public void normalizeNode(Node node){
    removeTspans(node);
    transformToMatrix(node);
    translateFromAttributesToStyle(node);
  }

  
  public Hashtable getResourcesFromDefs(JSVGCanvas svgCanvas, LinkedList resourceTagNames){
    Hashtable idResources=new Hashtable();
    Element defs=getDefsElement(svgCanvas);
    if(defs!=null && resourceTagNames!=null && resourceTagNames.size()>0){
      String id="";
      //for each children of the "defs" element, adds its id to the map
      for(Node current=defs.getFirstChild(); current!=null; current=current.getNextSibling()){
        if(current instanceof Element && resourceTagNames.contains(current.getNodeName())){
          id=((Element)current).getAttribute("id");
          if(id!=null && ! id.equals("")){
            idResources.put(id, current);
          }
        }
      }
    }
    return idResources;
  }
  
  
  
    
  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
    
}