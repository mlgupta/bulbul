package studio.canvas;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.AbstractGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

public class SVGToolkit  {
  private SVGScrollPane scrollPane;
  public SVGToolkit(SVGScrollPane scrollPane) {
    this.scrollPane=scrollPane;
  }
  
  public SVGScrollPane getScrollPane(){
    return this.scrollPane;
  }
  /**
   * gets a transformation matrix given a string containing a matrix transform
   * @param value the string containing a matrix transform
   * @return the corresponding transform matrix
   */
  public SVGTransformMatrix getTransformMatrix(String value){
    SVGTransformMatrix matrix=new SVGTransformMatrix(1,0,0,1,0,0);
    if(value!=null && ! value.equals("")){
      int range=value.indexOf("matrix");
      //computes the double values of the matrix in the transform attribute
      if(range>-1){
        int i=0;
        String subValue="";
        subValue=value.substring(range,value.length());
        subValue=subValue.substring(0,subValue.indexOf(")")+1);
        value=value.replaceAll("["+subValue+"]","");
        subValue=subValue.substring(subValue.indexOf("("),subValue.length());
        //cleans the string
        value=cleanTransformString(value);
        subValue=subValue.replaceAll("[(]","");
        subValue=subValue.replaceAll("[)]","");
        //the string containing the values of the matrix
        subValue=subValue.concat(",");
        i=subValue.indexOf(',');
        double[] matrixDb=new double[6];
        int j=0;
        while(i !=-1){
          try{
            matrixDb[j]=new Double(subValue.substring(0,i)).doubleValue();
          }catch (Exception ex){
            return new SVGTransformMatrix(1,0,0,1,0,0);
          }
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
   * gets a node's transformation matrix
   * @param frame the current frame
   * @param node the node from which to get the transformation matrix
   * @return the transformation matrix
   */
  public SVGTransformMatrix getTransformMatrix(Node node){
    SVGDocument doc=getScrollPane().getSVGCanvas().getSVGDocument();
    if(doc!=null){
      Element root=doc.getRootElement();
      if(root!=null){
        NamedNodeMap attributes=node.getAttributes();
        if(attributes!=null){
          //if the node has the transform atrribute
          Node att=attributes.getNamedItem("transform");
          if(att!=null){
            //gets the value of the transform attribute
            String value=att.getNodeValue();
            
            //creating the matrix transform
            return getTransformMatrix(value);
          }
        }
      }
    }
    //otherwise returns the identity matrix
    return new SVGTransformMatrix(1,0,0,1,0,0);
  }
  
  /**
   * sets the transform matrix of a node
   * @param node the given node
   * @param matrix the transformation matrix
   */
  public void setTransformMatrix(Node node, SVGTransformMatrix matrix){
    SVGDocument doc=getScrollPane().getSVGCanvas().getSVGDocument();
    if(doc!=null){
      Element root=doc.getRootElement();
      if(root!=null){
        NamedNodeMap attributes=node.getAttributes();
        if(attributes!=null){
          String value=matrix.getMatrixRepresentation();
          //if the node has the transform attribute					
          ((Element)node).setAttributeNS(null,"transform", value);
        }
      }
    }
  }
  /**
   * computes a rectangle given 2 the coordinates of two points
   * @param point1 the first point
   * @param point2 the second point
   * @return the correct rectangle
   */
  public Rectangle getComputedRectangle(Point point1, Point point2){
    if(point1!=null && point2!=null){
      
      int 	width=point2.x-point1.x, 
      height=point2.y-point1.y, 
      x=point1.x, 
      y=point1.y;
      
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
   * scales the given rectangle 
   * @param frame the current frame
   * @param rectangle the rectangle to scale
   * @param toBaseScale 	true to scale it to 100%
   * 											false to scale it at the current canvas scale
   * @return the scaled rectangle
   */
  public Rectangle2D.Double getScaledRectangle(Rectangle2D.Double rectangle, boolean toBaseScale){
    double scale=getScrollPane().getSVGCanvas().getScale();
    double tx=getScrollPane().getTranslateValues().width; 
    double ty=getScrollPane().getTranslateValues().height;
    double x;
    double y;
    double width;
    double height;
    if(toBaseScale){
      x=(rectangle.getX()-tx)/scale;
      y=(rectangle.getY()-ty)/scale;
      width=rectangle.getWidth()/scale;
      height=rectangle.getHeight()/scale;
    }else{
      x=rectangle.getX()*scale+tx;
      y=rectangle.getY()*scale+ty;
      width=rectangle.getWidth()*scale;
      height=rectangle.getHeight()*scale;			
    }
    return new Rectangle2D.Double(x,y,width,height);
  }
  /**
   * scales the given point 
   * @param frame the current frame
   * @param point the point to scale
   * @param toBaseScale 	true to scale it to 100%
   * 									false to scale it at the current canvas scale
   * @return the scaled point
   */
  public Point2D.Double getScaledPoint(Point2D.Double point, boolean toBaseScale){
    double scale=getScrollPane().getSVGCanvas().getScale();
    double tx=getScrollPane().getTranslateValues().width;
    double ty=getScrollPane().getTranslateValues().height;
    
    if(point!=null){
      double x=point.getX(), y=point.getY();
      if(toBaseScale){
        x=(point.getX()-tx)/scale;
        y=(point.getY()-ty)/scale;
      }else{
        x=point.getX()*scale+tx;
        y=point.getY()*scale+ty;	
      }                
      return new Point2D.Double(x,y);
    }else{
      return (point!=null)?new Point2D.Double(point.getX(),point.getY()):null;
    }
  }
  /**
   * the method used to get the point correponding to the given point when aligned with the rulers
   * @param point the point
   * @return the point correponding to the given point when aligned with the rulers
   */
  public Point getAlignedWithRulersPoint(Point point){        
    if(point!=null){
      return getScrollPane().getAlignedWithRulersPoint(point);
    }        
    return null;
  }
  
  /**
     * modifies the string to removes the extra whitespaces
     * @param value the string to be modified
     * @return the modified string
     */
    public String cleanTransformString(String value){
        String val=new String(value);
        val=val.replaceAll("[0]\\s","0,");
        val=val.replaceAll("[1]\\s","1,");
        val=val.replaceAll("[2]\\s","2,");
        val=val.replaceAll("[3]\\s","3,");
        val=val.replaceAll("[4]\\s","4,");
        val=val.replaceAll("[5]\\s","5,");
        val=val.replaceAll("[6]\\s","6,");
        val=val.replaceAll("[7]\\s","7,");
        val=val.replaceAll("[8]\\s","8,");
        val=val.replaceAll("[9]\\s","9,");
        val=val.replaceAll("\\s*[,]\\s*[,]\\s*",",");
        val=val.replaceAll("\\s+","");
        return val;
    }
    
  /**
   * computes the position and the size of a node on the canvas
   * @param frame the current frame
   * @param node the node whose position and size is to be computed
   * @return a rectangle representing the position and size of the given node
   */
  public Rectangle getNodeBounds(Node node){
    Rectangle bounds=null;
    if(node!=null){
      //the outline
      Shape outline=getOutline(node);

      //computes the transform matrix
      SVGTransformMatrix matrix=getTransformMatrix(node);
      
      //the transform
      AffineTransform af=new AffineTransform();
      
      if(matrix!=null && matrix.getTransform()!=null && outline!=null){
        af=matrix.getTransform();
        if(af!=null){
          bounds=af.createTransformedShape(outline).getBounds();
          return bounds;
        }
      }
    }
    return null;
  }
  
  /**
   * computes the outline of a node on the canvas
   * @param frame the current frame
   * @param node the node whose position and size is to be computed
   * @return the outline of the given node
   */
  public Shape getOutline(Node node){
    Shape outline=null;
    if(node!=null && node instanceof Element){
        //gets the bridge context of the canvas
        BridgeContext ctxt=getScrollPane().getSVGCanvas().getBridgeContext();
        if(ctxt!=null){
            //gets the graphics node correspondind to the given node
            GraphicsNode gnode=null;
            try{
                gnode=ctxt.getGraphicsNode((Element)node);
            }catch (Exception e){
                gnode=null;
            }
            if(gnode!=null){
                //gets the bounds of the graphics node
                outline=((AbstractGraphicsNode)gnode).getOutline();
            }
        }
    }
    return outline;
  }
}