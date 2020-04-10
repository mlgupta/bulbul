package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.general.NodeIterator;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
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
import org.apache.batik.util.RunnableQueue;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

/**
 * 
 * @description 
 * @version 1.0 07-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGIconCanvas extends JSVGCanvas  {
  private Thread paintManager=null;
  private SVGIconCanvas svgIconCanvas=this;
  private Studio studio;
  
  private Border mouseEnteredBorder=BorderFactory.createEtchedBorder();
  private Border mouseExitedBorder=BorderFactory.createLineBorder(new Color(247,161,90), 1);
  private Border mouseClickedBorder=BorderFactory.createLineBorder(new Color(255, 0, 0), 1);
  
  private Hashtable usedResources=new Hashtable();
  
  private boolean shouldRepaint=false;
  private boolean repaintEnabled=true;
  
  private MouseAdapter mouseAdapter;
  private boolean documentSet=false;
  /**
   * 
   * @description 
   */
  public SVGIconCanvas(Studio studio) {
    super();
    
    this.studio=studio;
    setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
    doubleBufferedRendering=false;
		disableInteractions=true;
		selectableText=false;
    setRecenterOnResize(false);
		setProgressivePaint(true);
    userAgent=new SVGIconCanvasUserAgent(userAgent);
    
    //the paint manager
		paintManager=new Thread(){
			public void run() {
				while(true){
					try{
						sleep((long)75);
					}catch (Exception ex){}
					if(shouldRepaint){
					    repaint();
						synchronized(svgIconCanvas){
							shouldRepaint=false;
						}
					}
				}
			}
		};
		paintManager.start();
  }
  
  
  private void normalizeSVGDocument(){
    SVGDocument document=getSVGDocument();
    //System.out.println("SVGDocument is " + document);
    if(document!=null){
      SVGSVGElement root=document.getRootElement();
      if(root!=null){ 
        
        AffineTransform affineTransform=null;
        
        Dimension newSize=svgIconCanvas.getPreferredSize();

        final String filterStrings = "emexpxptpccmmmin"; //String of Measuring units
       
       //Getting  Old Width & Height  and  Setting New Width & Height Starts
        
        String oldWidthString=root.getAttribute(SVGConstants.SVG_WIDTH_ATTRIBUTE);
        String oldHeightString=root.getAttribute(SVGConstants.SVG_HEIGHT_ATTRIBUTE);
        String oldViewBox=root.getAttribute(SVGConstants.SVG_VIEW_BOX_ATTRIBUTE); 
  
        int filterIndex=0;
        String filterString="";
        int stringIndex=0;
        
        
        //System.out.println("before");
        //System.out.println("oldWidthString" + oldWidthString);
        //System.out.println("oldHeightString" + oldHeightString);
        
        
        while(filterIndex<filterStrings.length()){
          filterString=filterStrings.substring(filterIndex,filterIndex+=2);  
          
          stringIndex=oldWidthString.indexOf(filterString);
          if(stringIndex>-1){
            oldWidthString=oldWidthString.substring(0,stringIndex) ;
          }
          
          stringIndex=oldHeightString.indexOf(filterString);
          if(stringIndex>-1){
            oldHeightString=oldHeightString.substring(0,stringIndex) ;
            break;
          }
        }
        
        
        //Removing % if any
        filterString="%";  
        stringIndex=oldWidthString.indexOf(filterString);
        if(stringIndex>-1){
          oldWidthString=oldWidthString.substring(0,stringIndex) ;
        }
        
        stringIndex=oldHeightString.indexOf(filterString);
        if(stringIndex>-1){
          oldHeightString=oldHeightString.substring(0,stringIndex) ;
        }
        
        //If String is empty
        if(oldWidthString.trim().length()<=0){
          oldWidthString="0.00";
        }
        
        if(oldHeightString.trim().length()<=0){
          oldHeightString="0.00";
        }
        
        
        //System.out.println("after");
        //System.out.println("oldWidthString" + oldWidthString);
        //System.out.println("oldHeightString" + oldHeightString);
                      
        
        double oldWidth=Double.parseDouble(oldWidthString);
        double oldHeight=Double.parseDouble(oldHeightString);
        
        double newWidth=newSize.getWidth(); 
        double newHeight=newSize.getHeight(); 
        
        
        root.setAttributeNS(null, SVGConstants.SVG_VIEW_BOX_ATTRIBUTE, "0 0 "+newWidth+" "+newHeight);
        root.setAttributeNS(null, SVGConstants.SVG_WIDTH_ATTRIBUTE, Double.toString(newWidth));
        root.setAttributeNS(null, SVGConstants.SVG_HEIGHT_ATTRIBUTE, Double.toString(newHeight));
       
        //Getting  Old Width & Height  and  Setting New Width & Height ends
      
        //Normalizing the Nodes of Document Starts 
        
        //getting the map associating the id of a resource to the resource node
        LinkedList resourceNames=new LinkedList();
        resourceNames.add("linearGradient");
        resourceNames.add("radialGradient");
        resourceNames.add("pattern");
        resourceNames.add("marker");
            
        Hashtable resources=getStudio().getSvgToolkit().getResourcesFromDefs(svgIconCanvas, resourceNames);
  
        //applying modifications on the dom
        String namespace=root.getNamespaceURI();
        
        for(NodeIterator nit=new NodeIterator(root); nit.hasNext();){
          Node node=null;
          
          try{
            node=(Node)nit.next();
          }catch (Exception ex){node=null;}
          
          if(node!=null && namespace.equals(node.getNamespaceURI()) && node instanceof Element){
            String style="";
            //normalizing the node
            getStudio().getSvgToolkit().normalizeNode(node);
            //checking the color values consistency
            getStudio().getColorChooser().checkColorString((Element)node);
            //getting the style attribute
            style=((Element)node).getAttribute("style");
            
            if(style!=null && ! style.equals("")){
              //for each resource id, checks if it is contained in the style attribute
              for(Iterator it=resources.keySet().iterator(); it.hasNext();){
                LinkedList nodesList=null;
                String id="";
                try{
                    id=(String)it.next();
                    nodesList=(LinkedList)usedResources.get(id);
                }catch (Exception ex){id=null; nodesList=null;}
                
                //adds the node in the used resource map
                if(id!=null && ! id.equals("") && style.indexOf("#".concat(id))!=-1){
                  if(nodesList==null){
                    nodesList=new LinkedList();
                    usedResources.put(id, nodesList);
                  }
                  
                  if(nodesList!=null){
                    nodesList.add(node);
                  }
                }
              }
            }
          }
        }
        
        //normalizes the group nodes
        for(NodeIterator nit=new NodeIterator(root); nit.hasNext();){
          Node node=null;
          try{
            node=(Node)nit.next();
          }catch (Exception ex){node=null;}
          
          if(node!=null && node.getNodeName().equals("g") && namespace.equals(node.getNamespaceURI()) && node instanceof Element){
            //normalizing the node
            getStudio().getSvgToolkit().normalizeGroupNode((Element)node);
          }
        }
        //Normalizing the Nodes of Documents ends
            
        //Un-Grouping Nodes Starts
        LinkedList gNodesToBeRemoved=new LinkedList();
        for(NodeIterator nit=new NodeIterator(root); nit.hasNext();){
          Node gNode=null;
          try{
            gNode=(Node)nit.next();
          }catch (Exception ex){gNode=null;}
          
          if(gNode!=null && ! gNode.getNodeName().equals("defs") && ! gNode.getNodeName().equals("metadata") && gNode.getNodeName().equals("g") ){
            
            //System.out.println(gNode.getNodeName());
            LinkedList gchildren=new LinkedList();
            for(Node current=gNode.getFirstChild(); current!=null; current=current.getNextSibling()){
              //System.out.println(current.getNodeName());
              gchildren.add(current);
            }
            
            for(Iterator it2=gchildren.iterator();it2.hasNext();){
              Node current=null;
              try{
                current=(Node)it2.next();
              }catch (Exception ex){current=null;}
              
              if(current!=null){
                try{
                  gNode.removeChild(current);
                  root.appendChild(current);
                }catch(Exception ex){}	
              }
            }
            gNodesToBeRemoved.add(gNode);
          }
        }
          
        //Removing g Nodes  from Documents
        for(Iterator it=gNodesToBeRemoved.iterator(); it.hasNext();){
          Node nodeTobeRemoved=null;
          try {
            nodeTobeRemoved=(Node)it.next();
          }catch (Exception ex) {nodeTobeRemoved=null;}
          if(nodeTobeRemoved!=null){
            try {
              root.removeChild(nodeTobeRemoved);
            }catch (Exception ex) {}
          }
        }
      
        //Un-Grouping Nodes Ends
        
        //Grouping Node Starts
        LinkedList nodesToGrouped=new LinkedList();
        
        for(Node current=root.getFirstChild(); current!=null; current=current.getNextSibling()){
          if(current instanceof Element && ! current.getNodeName().equals("defs")&& ! current.getNodeName().equals("metadata")){
            nodesToGrouped.add(current);
          }
        }
        
        Element gElement=document.createElementNS(document.getDocumentElement().getNamespaceURI(),"g");
        String id=getStudio().getSvgToolkit().getId(svgIconCanvas, "");
        gElement.setAttributeNS(null,"id",id);
    
        for(Iterator it=nodesToGrouped.iterator(); it.hasNext();){
          Node current=null;
          try{
            current=(Node)it.next();
          }catch (Exception ex){current=null;}
          
          if(current!=null){
            try{
              root.removeChild(current);
            }catch(Exception ex){}
            gElement.appendChild(current);					
          }
        }
        
        //appends the g element to the root element
      root.appendChild(gElement);
        //Grouping Node Ends
      }
    }
  }
  
  private void resizeDocument(){
    
    //System.out.println("in svgLoadEventDispatchStarted Update Manager is :  " + svgIconCanvas.getUpdateManager());
    
    SVGDocument document=getSVGDocument();
    
    //System.out.println("SVGDocument is " + document);
    
    if(document!=null){
      SVGSVGElement root=document.getRootElement();
      if(root!=null){ 
       
        double scaleX=1;
        double scaleY=1;
        
        double translateX=0;
        double translateY=0;
        
        double marginLeft=5.0;
        double marginTop=5.0;
        double marginRight=10.0;
        double marginBottom=10.0;
        
        AffineTransform affineTransform=null;
        
        Dimension newSize=svgIconCanvas.getPreferredSize();
        
        double newWidth=newSize.getWidth(); 
        double newHeight=newSize.getHeight(); 
       
        newWidth=newWidth-marginRight; 
        newHeight=newHeight-marginBottom;
        
        double newX=marginLeft;
        double newY=marginTop;
        
        //System.out.println("marginLeft " + marginLeft);
        //System.out.println("marginTop " + marginTop);
        //System.out.println("marginRight " + marginRight);
        //System.out.println("marginBottom " + marginBottom);
        
        //System.out.println("Bounds Before Calculations ");
        //System.out.println("newWidth " + newWidth);
        //System.out.println("newHeight " + newHeight);
        //System.out.println("newX " + newX);
        //System.out.println("newY " + newY);
        
        NodeList rootChildNodes=root.getChildNodes();
        for(int i=0; i<rootChildNodes.getLength();i++){
          Node node=rootChildNodes.item(i);
          if(node!=null && node.getNodeName().equals("g")){
            
            GraphicsNode gNode=svgIconCanvas.getUpdateManager().getBridgeContext().getGraphicsNode((Element)node);              
            Rectangle2D bounds=new Rectangle();
    
            if (gNode!=null){
              
              
              //NW
              bounds=gNode.getTransformedBounds(new AffineTransform());
              if (bounds !=null) {
              
              double sizeRatio= bounds.getWidth()/bounds.getHeight();
              
              //System.out.println("sizeRatio " + sizeRatio);
              
              if(sizeRatio>1) {
                newHeight=newHeight/sizeRatio;
              }else{
                newWidth=newWidth*sizeRatio;
              }
              
              //System.out.println("Bounds After Calculations ");
              //System.out.println("newWidth " + newWidth);
              //System.out.println("newHeight " + newHeight);
              //System.out.println("newX " + newX);
              //System.out.println("newY " + newY);
              
              //System.out.println("North West");
              //System.out.println("Width " + bounds.getWidth());
              //System.out.println("Height " + bounds.getHeight());
              //System.out.println("X " + bounds.getX());
              //System.out.println("Y " + bounds.getY());
              
              Point2D.Double difference=new Point2D.Double(newX-bounds.getX(), newY-bounds.getY());
              
              //correcting the resize action values
              if(difference.x==bounds.getWidth()){
                difference.x--;
              }else if(difference.x==-bounds.getWidth()){
                difference.x++;
              }
      
              if(difference.y==bounds.getHeight()){
                difference.y--;
              }else if(difference.y==-bounds.getHeight()){
                difference.y++;
              }
    
              scaleX=1-difference.x/bounds.getWidth();
              scaleY=1-difference.y/bounds.getHeight();
              
              translateX=(bounds.getX()+bounds.getWidth())*(1-scaleX);
              translateY=(bounds.getY()+bounds.getHeight())*(1-scaleY);
    
              affineTransform=AffineTransform.getScaleInstance(scaleX, scaleY);
              affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateX,translateY));                
              
              if(!affineTransform.isIdentity()){
                SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
                matrix.concatenateTransform(affineTransform);
                if(matrix.isMatrixCorrect()){
                  getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
                }
              }
            }
            
            
            //SE
            bounds=gNode.getTransformedBounds(new AffineTransform());
            if (bounds !=null) {
              
              //System.out.println("South East");
              //System.out.println("Width " + bounds.getWidth());
              //System.out.println("Height " + bounds.getHeight());
              //System.out.println("X " + bounds.getX());
              //System.out.println("Y " + bounds.getY());
              
              Point2D.Double difference=new Point2D.Double(newWidth-bounds.getWidth(), newHeight-bounds.getHeight());
              
              //correcting the resize action values
              if(difference.x==bounds.getWidth()){
                difference.x--;
              }else if(difference.x==-bounds.getWidth()){
                difference.x++;
              }
      
              if(difference.y==bounds.getHeight()){
                difference.y--;
              }else if(difference.y==-bounds.getHeight()){
                difference.y++;
              }
    
              scaleX=1+difference.x/bounds.getWidth();
              scaleY=1+difference.y/bounds.getHeight();
              
              translateX=bounds.getX()*(1-scaleX);
              translateY=bounds.getY()*(1-scaleY);
    
              affineTransform=AffineTransform.getScaleInstance(scaleX, scaleY);
              affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateX,translateY));                
    
              if(!affineTransform.isIdentity()){
                //gets, modifies and sets the matrix
                SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
                
                matrix.concatenateTransform(affineTransform);
    
                if(matrix.isMatrixCorrect()){
                  getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
                }
              }
            }
            
            //bounds=gNode.getTransformedBounds(new AffineTransform());
            //if (bounds !=null) {
            //  System.out.println("Final Values ");
            //  System.out.println("Width " + bounds.getWidth());
            //  System.out.println("Height " + bounds.getHeight());
            //  System.out.println("X " + bounds.getX());
            //  System.out.println("Y " + bounds.getY());
            //}
            
          }
        }
      }
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
    
    setSelected(false);
  }
  
  public void init(){

    //adds the listeners
    
    final SVGLoadEventDispatcherAdapter loadEventDispatcherAdapter= new SVGLoadEventDispatcherAdapter() {
      public void svgLoadEventDispatchStarted(SVGLoadEventDispatcherEvent e) {
        if (isDocumentSet()){
          normalizeSVGDocument();
        }
        resizeDocument();
      }
    };
    
    addSVGLoadEventDispatcherListener(loadEventDispatcherAdapter);
                
   	final SVGDocumentLoaderAdapter documentLoaderAdapter=new SVGDocumentLoaderAdapter() {
		    
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				//svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Document Loading...");
			}
			
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
        //svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Document Completed.");
        if (!isDocumentSet()){
          normalizeSVGDocument();
        }
      }
		};
    
		addSVGDocumentLoaderListener(documentLoaderAdapter);
    
    final GVTTreeBuilderAdapter gvtTreeBuilderAdapter=new GVTTreeBuilderAdapter() {
		    
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				//svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Build Started...");
			}
			
			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				//svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Build Done.");
			}
		};
		
		addGVTTreeBuilderListener(gvtTreeBuilderAdapter);

		final GVTTreeRendererAdapter gvtTreeRendererAdapter=new GVTTreeRendererAdapter() {
		    
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {	
				//svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Rendering Started...");	
			}
			
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				//svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("");
			}
		};
		
		addGVTTreeRendererListener(gvtTreeRendererAdapter);
		
		final UpdateManagerAdapter updateManagerAdapter=new UpdateManagerAdapter(){
			
			public void updateStarted(UpdateManagerEvent e) {
				//displayWaitCursor();
			}

			public void updateCompleted(UpdateManagerEvent e) {
				//returnToLastCursor();
			}

			public void updateFailed(UpdateManagerEvent e) {
				//returnToLastCursor();
			}
		};
		
		addUpdateManagerListener(updateManagerAdapter);
    
    mouseAdapter=new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
        setBorder(mouseEnteredBorder);
      }
      
      public void mouseExited(MouseEvent e){
        setBorder(mouseExitedBorder);
      }
		};
    
  }
 
  /**
   * 
   * @description 
   * @param selected
   */
  public void setSelected(boolean selected){
    if(selected){
      setBorder(mouseClickedBorder);
      removeMouseListener(mouseAdapter);
    }else{
      setBorder(mouseExitedBorder);
      addMouseListener(mouseAdapter);
    }
  } 
  
  /**
   * 
   * @description 
   */
  public synchronized void repaintCanvas(){
    if(isRepaintEnabled()){
	    shouldRepaint=true;
	  }
  }
  
  /**
   * 
   * @description 
   * @param repaintEnabled
   */
  public void setRepaintEnabled(boolean repaintEnabled) {
    this.repaintEnabled = repaintEnabled;
    if(repaintEnabled){
      shouldRepaint=true;
    }
  }
  
  /**
   * 
   * @description 
   * @param g
   */
  public void paintComponent(Graphics g){
  	super.paintComponent(g);
	}

  /**
   * 
   * @description 
   * @return 
   */
  public boolean isRepaintEnabled() {
    return repaintEnabled;
  }
  /**
   * 
   * @description 
   */
  public synchronized void delayedRepaint(){
    if(isRepaintEnabled()){
	    shouldRepaint=true;
	  }
  }
  
  /**
   * 
   * @description 
   * @param documentSet
   */
  public void setDocumentSet(boolean documentSet) {
    this.documentSet = documentSet;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean isDocumentSet() {
    return documentSet;
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
   * @version 1.0 07-Oct-2005
   * @author Sudheer V. Pujar
   */
  protected class SVGIconCanvasUserAgent  extends    JSVGComponent.BridgeUserAgentWrapper{
  
    protected  SVGIconCanvasUserAgent(UserAgent userAgent){
      super(userAgent);
			svgIconCanvas.userAgent=userAgent;
    }
  
    public void setSVGCursor(Cursor cursor){
    
    }
    
    public void displayError(Exception ex){
    
    }
  }

}