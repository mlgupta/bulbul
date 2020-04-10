package bulbul.boff.general;


import bulbul.boff.general.BOConstants;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.JSVGComponent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherAdapter;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherEvent;
import org.apache.batik.util.SVGConstants;

import org.apache.log4j.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;


/**
 * @author Sudheer V Pujar
 * @version 1.0
 * @Date of creation: 07-Oct-2005
 * @Last Modfied by : Saurabh Gupta
 * @Last Modfied Date: 04-Oct-2006
 */
public final class SVGImageCanvas extends JSVGCanvas {
  private SVGImageCanvas svgImageCanvas = this;
  private SVGToolkit svgToolkit = new SVGToolkit();
  
  private String style=null;
  
  private float rotate=0.0f;
  private double scale=1.0;
  
  private boolean documentSet=false;
  private boolean documentResized = false;
  private boolean forTextElement=false;
  private boolean errorInResizing=false;
  private boolean canvasFree=false;
  private static final Logger logger = Logger.getLogger(BOConstants.LOGGER.toString());
  
  /**
   * 
   * @description 
   */
  public SVGImageCanvas() {
    super();
    setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
    doubleBufferedRendering = false;
    disableInteractions = true;
    selectableText = false;
    setRecenterOnResize(false);
    setProgressivePaint(true);
    userAgent = new SVGIconCanvasUserAgent(userAgent);
  }


  private void normalizeSVGDocument() {
    try {
      SVGDocument document = getSVGDocument();
      //logger.debug("SVGDocument is " + document);
      if (document != null) {
        SVGSVGElement root = document.getRootElement();
        if (root != null) {

          //Normalizing the Nodes of Document Starts 

          //getting the map associating the id of a resource to the resource node
          LinkedList resourceNames = new LinkedList();
          resourceNames.add("linearGradient");
          resourceNames.add("radialGradient");
          resourceNames.add("pattern");
          resourceNames.add("marker");

          svgToolkit.getResourcesFromDefs(document, resourceNames);


          //applying modifications on the dom
          String namespace = root.getNamespaceURI();

          //normalizes the nodes
          for (NodeIterator nit = new NodeIterator(root); nit.hasNext(); ) {
            Node node = null;
            try {
              node = (Node)nit.next();
            } catch (Exception ex) {
              node = null;
            }
            if (node != null && namespace.equals(node.getNamespaceURI()) /*&& node instanceof Element*/) {

              //normalizing the node
              svgToolkit.normalizeNode(node);
            }
          }

          //normalizes the group nodes
          for (NodeIterator nit = new NodeIterator(root); nit.hasNext(); ) {
            Node node = null;
            try {
              node = (Node)nit.next();
            } catch (Exception ex) {
              node = null;
            }
            if (node != null && node.getNodeName().equals("g") && namespace.equals(node.getNamespaceURI()) /*&&
                node instanceof Element*/) {
              //normalizing the Group node
              svgToolkit.normalizeGroupNode((Element)node);
            }
          }

          //Normalizing the Nodes of Documents ends


          //Un-Grouping Nodes Starts
          LinkedList gNodesToBeRemoved = new LinkedList();
          for (NodeIterator nit = new NodeIterator(root); nit.hasNext(); ) {
            Node gNode = null;
            try {
              gNode = (Node)nit.next();
            } catch (Exception ex) {
              gNode = null;
            }

            if (gNode != null && !gNode.getNodeName().equals("defs") && !gNode.getNodeName().equals("metadata") &&
                gNode.getNodeName().equals("g")) {

              //logger.debug(gNode.getNodeName());
              LinkedList gchildren = new LinkedList();
              for (Node current = gNode.getFirstChild(); current != null; current = current.getNextSibling()) {
                //logger.debug(current.getNodeName());
                gchildren.add(current);
              }

              for (Iterator it2 = gchildren.iterator(); it2.hasNext(); ) {
                Node current = null;
                try {
                  current = (Node)it2.next();
                } catch (Exception ex) {
                  current = null;
                }

                if (current != null) {
                  try {
                    gNode.removeChild(current);
                    root.appendChild(current);
                  } catch (Exception ex) {
                    ;
                  }
                }
              }
              gNodesToBeRemoved.add(gNode);
            }
          }

          //Removing g Nodes  from Documents
          for (Iterator it = gNodesToBeRemoved.iterator(); it.hasNext(); ) {
            Node nodeTobeRemoved = null;
            try {
              nodeTobeRemoved = (Node)it.next();
            } catch (Exception ex) {
              nodeTobeRemoved = null;
            }
            if (nodeTobeRemoved != null) {
              try {
                root.removeChild(nodeTobeRemoved);
              } catch (Exception ex) {
                ;
              }
            }
          }

          //Un-Grouping Nodes Ends

          //Grouping Node Starts
          
//          logger.debug("Style : " + style);  
           
          LinkedList nodesToGrouped = new LinkedList();
          
          
          for (Node current = root.getFirstChild(); current != null; current = current.getNextSibling()) {
            if (current instanceof Element && !current.getNodeName().equals("defs") &&
                !current.getNodeName().equals("metadata")) {

              //Styling Nodes
              
              if(style!=null && style.trim().length()>0 ){
                String[] styleArray=style.split(";");
                for(int i=0;i<styleArray.length;i++){
                  String[] property=styleArray[i].split(":");
                  String propertyName=property[0];
                  String propertyValue=property[1];
                  if(propertyValue.trim().length()>0){
                    svgToolkit.setStyleProperty((Element)current,propertyName,propertyValue);
                  }
                }
              }
                            
              nodesToGrouped.add(current);
            }
          }

          Element gElement = document.createElementNS(document.getDocumentElement().getNamespaceURI(), "g");
          String id = svgToolkit.getId(document, "");
          gElement.setAttributeNS(null, "id", id);

          for (Iterator it = nodesToGrouped.iterator(); it.hasNext(); ) {
            Node current = null;
            try {
              current = (Node)it.next();
            } catch (Exception ex) {
              current = null;
            }

            if (current != null) {
              try {
                root.removeChild(current);
              } catch (Exception ex) {
                ;
              }
              gElement.appendChild(current);
            }
          }

          //appends the g element to the root element
          root.appendChild(gElement);
          //Grouping Node Ends
        }
      }
    } catch (Exception e) {
      errorInResizing=true;
      logger.error(e.getMessage());
      ;//e.printStackTrace();
      
    }
  }

  private void resizeDocument() {

    try {

      //logger.debug("in svgLoadEventDispatchStarted Update Manager is :  " + svgIconCanvas.getUpdateManager());

      SVGDocument document = getSVGDocument();

      //logger.debug("SVGDocument is " + document);

      if (document != null) {
        SVGSVGElement root = document.getRootElement();
        if (root != null) {

          double scaleX = 1;
          double scaleY = 1;

          double translateX = 0;
          double translateY = 0;

          AffineTransform affineTransform = null;

          Dimension newSize = svgImageCanvas.getPreferredSize();

          double newWidth = newSize.getWidth()*scale;
          double newHeight = newSize.getHeight()*scale;


//          logger.debug("Bounds Before Calculations ");
//          logger.debug("newWidth " + newWidth);
//          logger.debug("newHeight " + newHeight);
          
          NodeList rootChildNodes = root.getChildNodes();
          for (int i = 0; i < rootChildNodes.getLength(); i++) {
            Node node = rootChildNodes.item(i);
            if (node != null && node.getNodeName().equals("g")) {

              GraphicsNode gNode = svgImageCanvas.getUpdateManager().getBridgeContext().getGraphicsNode((Element)node);
              Rectangle2D bounds = new Rectangle();

              if (gNode != null) {
              
                //Rotating for an angle
                bounds=gNode.getTransformedBounds(new AffineTransform());
                affineTransform=AffineTransform.getRotateInstance(rotate*Math.PI/180.0f,bounds.getCenterX(),bounds.getCenterY());
                
                if (!affineTransform.isIdentity()) {
                 //gets, modifies and sets the matrix
                 SVGTransformMatrix matrix = svgToolkit.getTransformMatrix(node);
                 matrix.concatenateTransform(affineTransform);
                 if (matrix.isMatrixCorrect()) {
                   svgToolkit.setTransformMatrix(node, matrix);
                 }
                }
                
                
                //Sizing Veritically and Horizontaly
                
                bounds=gNode.getTransformedBounds(new AffineTransform());
                
                double sizeRatio = bounds.getWidth() / bounds.getHeight();
                
                if(isForTextElement()){
                    newWidth=bounds.getWidth()*scale;
                    newHeight=bounds.getHeight()*scale;
                    scaleX=scale;
                    scaleY=scale;
                }else{
                
                  double changedWidth=newWidth;
                  double changedHeight=newHeight;
                  
                  //logger.debug("sizeRatio " + sizeRatio);
                  if (sizeRatio > 1) {
                    changedHeight = changedHeight / sizeRatio;
                  } else {
                    changedWidth = changedWidth * sizeRatio;
                  }
                  
                  scaleX=changedWidth/bounds.getWidth();
                  scaleY=changedHeight/bounds.getHeight();
                
                }
                
                affineTransform=AffineTransform.getScaleInstance(scaleX, scaleY);
                
                if (!affineTransform.isIdentity()) {
                 //gets, modifies and sets the matrix
                 SVGTransformMatrix matrix = svgToolkit.getTransformMatrix(node);
                 matrix.concatenateTransform(affineTransform);
                 if (matrix.isMatrixCorrect()) {
                   svgToolkit.setTransformMatrix(node, matrix);
                 }
                }
                
                //Centering veritically and Horizontaly
                bounds=gNode.getTransformedBounds(new AffineTransform());
                translateX=(newWidth-bounds.getWidth())/2-bounds.getX();
                translateY=(newHeight-bounds.getHeight())/2-bounds.getY();
                
                affineTransform=AffineTransform.getTranslateInstance(translateX, translateY);
                
                if (!affineTransform.isIdentity()) {
                  //gets, modifies and sets the matrix
                  SVGTransformMatrix matrix = svgToolkit.getTransformMatrix(node);
                  matrix.concatenateTransform(affineTransform);
                  if (matrix.isMatrixCorrect()) {
                    svgToolkit.setTransformMatrix(node, matrix);
                  }
                }
                              
              }
            }
          }
        
          root.setAttributeNS(null, SVGConstants.SVG_VIEW_BOX_ATTRIBUTE, "0 0 " + newWidth + " " + newHeight);
          root.setAttributeNS(null, SVGConstants.SVG_WIDTH_ATTRIBUTE, Double.toString(newWidth));
          root.setAttributeNS(null, SVGConstants.SVG_HEIGHT_ATTRIBUTE, Double.toString(newHeight));
        
        }
      }

      


      //Viewing changed document starts
      //document=svgIconCanvas.getSVGDocument();
      //if(document!=null){
      //  SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
      //  Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
      //  try {
      //    svgGenerator.stream(svgRoot, new OutputStreamWriter(System.out,"UTF-8"));
      //  }catch (UnsupportedEncodingException ue) {

      //  }catch (SVGGraphics2DIOException se) {

      //  }
      //}
      //Viewing changed document ends
    } catch (Exception e) {
      errorInResizing=true;
      logger.error(e.getMessage());
      ;//e.printStackTrace();
    }

  }

  public void init() {

    final SVGLoadEventDispatcherAdapter loadEventDispatcherAdapter = new SVGLoadEventDispatcherAdapter() {
        public void svgLoadEventDispatchStarted(SVGLoadEventDispatcherEvent e) {
          try {
            if (isDocumentSet()){
              normalizeSVGDocument();
            }

            resizeDocument();
            documentResized = true;
//            logger.debug("Event Dispatch Started");
          } catch (Exception ex) {
            errorInResizing=true;
            logger.error(ex.getMessage());
            ;//ex.printStackTrace();
          }
        }
        
        public void svgLoadEventDispatchCancelled(SVGLoadEventDispatcherEvent e){
          errorInResizing=true;
        }
        
        public void svgLoadEventDispatchFailed(SVGLoadEventDispatcherEvent e){
          errorInResizing=true;
        }
        
      };

    addSVGLoadEventDispatcherListener(loadEventDispatcherAdapter);

    final SVGDocumentLoaderAdapter documentLoaderAdapter = new SVGDocumentLoaderAdapter() {
        public void documentLoadingFailed(SVGDocumentLoaderEvent e) {
          logger.debug("Document Loading Failed");
          errorInResizing=true;
        }
        
        public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
//          logger.debug("Document Loaded.");
          try {
            if (!isDocumentSet()){
              normalizeSVGDocument();
            }
          } catch (Exception ex) {
            errorInResizing=true;
            ;//ex.printStackTrace();
          }
        }
      };
    
    addSVGDocumentLoaderListener(documentLoaderAdapter);
    
    final GVTTreeBuilderAdapter gvtTreeBuilderAdapter=new GVTTreeBuilderAdapter() {        
      public void gvtBuildFailed(GVTTreeBuilderEvent e) {
        logger.debug("gvtBuildFailed");
        errorInResizing=true;
      }
      public void gvtBuildCancelled(GVTTreeBuilderEvent e) {
        logger.debug("gvtBuildCancelled");
        errorInResizing=true;
      }
    };
    
    addGVTTreeBuilderListener(gvtTreeBuilderAdapter);

    final GVTTreeRendererAdapter gvtTreeRendererAdapter=new GVTTreeRendererAdapter() {
        
      public void gvtRenderingFailed(GVTTreeRendererEvent e) {
        logger.debug("gvtRenderingFailed");
        errorInResizing=true;
      }
      public void gvtRenderingCancelled(GVTTreeRendererEvent e) {
        logger.debug("gvtRenderingCancelled");
        errorInResizing=true;
      }
    };
    
    addGVTTreeRendererListener(gvtTreeRendererAdapter);
    
    final UpdateManagerAdapter updateManagerAdapter=new UpdateManagerAdapter(){
      
      public void updateFailed(UpdateManagerEvent e) {
        logger.debug("updateFailed");
         errorInResizing=true;
      }
      
    };
    
    addUpdateManagerListener(updateManagerAdapter);
  }
  
  public void resetCanvas(){

    this.documentResized = false;
    this.forTextElement=false;
    this.errorInResizing=false;
    this.canvasFree=false;

    this.style=null;
    this.rotate=0.0f;
    this.scale=1.0;
  }

  public boolean isDocumentResized() {
    return documentResized;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public void setRotate(float rotate) {
    this.rotate = rotate;
  }

  public void setForTextElement(boolean forTextElement) {
    this.forTextElement = forTextElement;
  }

  public boolean isForTextElement() {
    return forTextElement;
  }

  public void setScale(double scale) {
    this.scale = scale;
  }

  public double getScale() {
    return scale;
  }

  public boolean isErrorInResizing() {
    return errorInResizing;
  }

  public void setCanvasFree(boolean canvasFree) {
    this.canvasFree = canvasFree;
  }

  public boolean isCanvasFree() {
    return canvasFree;
  }

  public void setDocumentSet(boolean documentSet) {
    this.documentSet = documentSet;
  }

  public boolean isDocumentSet() {
    return documentSet;
  }

  /**
   * 
   * @description 
   * @version 1.0 07-Oct-2005
   * @author Sudheer V. Pujar
   */
  protected class SVGIconCanvasUserAgent extends JSVGComponent.BridgeUserAgentWrapper {

    protected SVGIconCanvasUserAgent(UserAgent userAgent) {
      super(userAgent);
      svgImageCanvas.userAgent = userAgent;
    }

    public void setSVGCursor(Cursor cursor) {

    }

    public void displayError(Exception ex) {

    }
  }

}
