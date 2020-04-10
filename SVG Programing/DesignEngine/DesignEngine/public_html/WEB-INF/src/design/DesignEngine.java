//Sudheer ....

/**
 *	Purpose: An Applet To Create Edit Customer Design For any Merchandise
 *  Info: 
 *  @author  Sudheer Pujar
 *  @version 1.0
 * 	Date of creation:   01-11-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
package design;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException; 
import java.awt.event.*;
import java.io.*;

import org.apache.batik.swing.*;
import org.apache.batik.swing.svg.*;
import org.apache.batik.swing.gvt.*;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.wmf.tosvg.*;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.*;
import org.w3c.dom.svg.*;
import org.apache.batik.dom.svg.*;
import org.apache.batik.svggen.*;
import batik4bulbul.*;
import java.awt.geom.*;
import org.apache.batik.gvt.*;
import org.apache.batik.gvt.event.*;
import org.w3c.dom.events.*;
//import Point2D.Double;
import org.apache.batik.bridge.*;

public class DesignEngine extends JApplet {

  //GUI Global Variables
  private JApplet me ;
  private JPanel panelPallette ;
  private BJSVGCanvas jsvgcanvas ;
  private JTree jtree;
  private JPanel jscrollpaneRight;

  private JButton btnLoadDocument ;
  private JButton btnLoadFile ;
  private JButton btnLoadSVGFile ;

  //Graphics Global Variables
  private DOMImplementation domImpl ;
  private String theSVGNS ;
  private String theSVGTag ;
  private Document theSVGDocument ;
  private SVGGraphics2D theSVGGraphics2D;
  private AffineTransform at;

  int width =600; 
  int height =800;
    

  public void  init(){
      
    UIUnit();
    
    // Initialization of Graphics Global Variables
    domImpl =SVGDOMImplementation.getDOMImplementation();
    theSVGNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    theSVGTag = SVGConstants.SVG_SVG_TAG;
    theSVGDocument = domImpl.createDocument(theSVGNS, theSVGTag, null);
   
    
    btnLoadDocument.addActionListener(new ActionHandler4LoadDocument());
    btnLoadFile.addActionListener(new ActionHandler4LoadFile());
    btnLoadSVGFile.addActionListener(new ActionHandler4LoadSVGFile());
  
    jsvgcanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderHandler());
    jsvgcanvas.addGVTTreeBuilderListener(new GVTTreeBuilderHandler());    
    jsvgcanvas.addGVTTreeRendererListener(new GVTTreeRendererHandler() );
    jsvgcanvas.setDocumentState(JSVGComponent.ALWAYS_DYNAMIC);
    jsvgcanvas.addMouseListener(new InteractorHandler());
    jsvgcanvas.addMouseMotionListener(new InteractorHandler());

    at=AffineTransform.getTranslateInstance(0, 0);
    at.concatenate(AffineTransform.getScaleInstance(1.0, 1.0));
    jsvgcanvas.setRenderingTransform(at);
    repaint();
   
    Element svgRoot = ((SVGDocument)theSVGDocument).getRootElement(); 

    svgRoot.setAttributeNS(null, SVGConstants.SVG_VIEW_BOX_ATTRIBUTE,
                               "" + 0 + " " + 0 + " " +
                               0 + " " + 0 );
    svgRoot.setAttributeNS(null,SVGConstants.SVG_WIDTH_ATTRIBUTE,Integer.toString(width));                                                        
    svgRoot.setAttributeNS(null,SVGConstants.SVG_HEIGHT_ATTRIBUTE,Integer.toString(height));                         
    jsvgcanvas.setSVGDocument((SVGDocument)theSVGDocument);
  } 

  

  /**
   * Purpose : To Design UI of the Applet
   * @return Void
   */
  private void UIUnit(){
    // Initialation of Global Variable
    me = this;
    panelPallette = new JPanel();
    jsvgcanvas = new BJSVGCanvas();
    jtree = new JTree();
    
    
    btnLoadDocument = new JButton("Load Document");
    btnLoadFile = new JButton("Load File");
    btnLoadSVGFile = new JButton("Load SVG File");

    Container content = getContentPane();
    JTabbedPane jtabbedpane = new JTabbedPane();
    
    JPanel jpanelLeft = new JPanel(new BorderLayout());
    JPanel jpanelRight = new JPanel(new BorderLayout());
    JPanel panelTree = new JPanel();
    
    Box box = Box.createHorizontalBox();
    Box boxPallette= Box.createHorizontalBox();

    JSplitPane jsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JScrollPane jscrollpaneLeft = new JScrollPane(jtabbedpane);

    jscrollpaneRight = new JPanel();
    jscrollpaneRight.setLayout(null);
    //jscrollpaneRight.setSize(new Dimension(jpanelRight.getWidth(),jpanelRight.getHeight()));
    jscrollpaneRight.add(jsvgcanvas);

    jscrollpaneRight.addComponentListener(new ComponentAdapter(){
      public void componentResized(ComponentEvent e){
        jsvgcanvas.setSize(new Dimension(jscrollpaneRight.getWidth(),jscrollpaneRight.getHeight()));  
        jsvgcanvas.revalidate();
        jsvgcanvas.repaint();
      }
    });;

    boxPallette.add(btnLoadDocument);
    boxPallette.add(btnLoadFile);
    boxPallette.add(btnLoadSVGFile);

    panelPallette.add(boxPallette,BorderLayout.NORTH);
    
    jtabbedpane.addTab("Pallette", panelPallette);

    panelTree.setLayout(new BorderLayout());
    panelTree.add(jtree);
    jtabbedpane.addTab("Tree", panelTree);
    
    jpanelLeft.add(jscrollpaneLeft);
    jpanelRight.setLayout(new BorderLayout());
    jpanelRight.add(jscrollpaneRight,BorderLayout.CENTER);
    
    jsplitpane.add(jpanelLeft);
    jsplitpane.add(jpanelRight);
       
    box.add(jsplitpane);
    content.add(box,BorderLayout.CENTER);
  }  
  
  /**
   * Purpose : To Handle Click of Button Load Document  
   */
  private class ActionHandler4LoadDocument implements ActionListener{
    public void actionPerformed(ActionEvent ae) {
      JFileChooser fc = new JFileChooser(".");
      fc.setCurrentDirectory(new File("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Cliparts"));
      int choice = fc.showOpenDialog(panelPallette);
      if (choice == JFileChooser.APPROVE_OPTION) {
        File f = fc.getSelectedFile();
        try {
          Document theExistingDocument = jsvgcanvas.getSVGDocument();
          Document theDocument = WMF2SVGDocument(f);
          NodeList gNodeList = theDocument.getDocumentElement().getElementsByTagName(SVGConstants.SVG_G_TAG);
          for (int gNodeCount=0;gNodeCount<gNodeList.getLength();gNodeCount++){
            Node gNode=  gNodeList.item(gNodeCount);

            NamedNodeMap gNodeAttributes =gNode.getAttributes();
            
            if (gNodeAttributes !=null){
              Node gNodeAttr = gNodeAttributes.getNamedItem(SVGConstants.SVG_FILL_ATTRIBUTE);
              if (gNodeAttr!=null){
                gNodeAttr.setNodeValue("none");
              }
              gNodeAttr = gNodeAttributes.getNamedItem(SVGConstants.SVG_STROKE_ATTRIBUTE);
              if (gNodeAttr!=null){
                gNodeAttr.setNodeValue("none");
                ((Element)gNode).setAttributeNS(null,SVGConstants.SVG_TRANSFORM_ATTRIBUTE,"matrix( 0.162112906767942, 0.1003046661397699, -0.1040146396025528, 0.1582371613842956, 261.5207851921565, -2.668446561780854)");  
                Node newNode= theExistingDocument.importNode(gNode, true);
                theExistingDocument.getDocumentElement().appendChild(newNode);
              }
              
            }
          }
          
          jsvgcanvas.setSVGDocument((SVGDocument)theExistingDocument);
          theSVGGraphics2D = new SVGGraphics2D(theExistingDocument);
          //theSVGGraphics2D.stream(theExistingDocument.getDocumentElement()),new OutputStreamWriter(System.out));
          theSVGGraphics2D.stream(theExistingDocument.getDocumentElement(),new OutputStreamWriter(new FileOutputStream(new File("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Cliparts\\a.svg")),"UTF8"));
          
  
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
  
  /**
   * Purpose : To Handle Click of Button Load File  
   */  
  private class ActionHandler4LoadFile implements ActionListener{
    public void actionPerformed(ActionEvent ae) {
      JFileChooser fc = new JFileChooser(".");
      fc.setCurrentDirectory(new File("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Cliparts"));
      int choice = fc.showOpenDialog(panelPallette);
      if (choice == JFileChooser.APPROVE_OPTION) {
        File f = fc.getSelectedFile();
        try {
          jsvgcanvas.setURI(WMF2SVGFile(f).toURL().toString());
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  } 

  /**
   * Purpose : To Handle Click of Button Load SVG File  
   */  
  private class ActionHandler4LoadSVGFile implements ActionListener{
    public void actionPerformed(ActionEvent ae) {
      JFileChooser fc = new JFileChooser(".");
      fc.setCurrentDirectory(new File("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Cliparts"));
      int choice = fc.showOpenDialog(panelPallette);
      if (choice == JFileChooser.APPROVE_OPTION) {
        File f = fc.getSelectedFile();
        try {
          jsvgcanvas.setURI(f.toURL().toString());
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  } 

  /**
   * Purpose : To Handle Loading of an SVGDocument 
   */  
  private class SVGDocumentLoaderHandler extends SVGDocumentLoaderAdapter{
    public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
       me.showStatus("Document Loading...");
    }
    public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
      me.showStatus("Document Loaded.");
    }
  }

  /**
   * Purpose : To Handle Tree Building of an SVGDocument 
   */  
  private class GVTTreeBuilderHandler extends GVTTreeBuilderAdapter {
    public void gvtBuildStarted(GVTTreeBuilderEvent e) {
      me.showStatus("Build Started...");
    }
    public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
      me.showStatus("Build Done.");
    } 
  }
  /**
   * Purpose : To Handle Component Listening
   */  

 

  /**
   * Purpose : To Handle Tree Rendering of an SVGDocument 
   */
  private class GVTTreeRendererHandler extends GVTTreeRendererAdapter {
        public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
          me.showStatus("Rendering Started...");
        }
        public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
          
          me.showStatus("");
        }
      }

  /**
   * Purpose : To Convert WMF File to SVGDocument
   * @param theFile - File object
   * @return A Document object
   */
  private Document WMF2SVGDocument(File theFile){
    BulbulWMFTranscoder transcoder = new BulbulWMFTranscoder();
    try{ 
      TranscoderInput input = new TranscoderInput(theFile.toURL().toString());
      Document document = SVGDOMImplementation.getDOMImplementation().createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
      TranscoderOutput output =new TranscoderOutput(document);
    
      me.showStatus("Trascoding Started...");
      transcoder.transcode(input, output); 
      me.showStatus("Trascoding Ended...");
   
      return output.getDocument();
      

    }catch(TranscoderException ex){
      ex.printStackTrace();
      return null;
    }catch(MalformedURLException ex){
      ex.printStackTrace();
      return null;
    }catch(Exception ex){
      ex.printStackTrace();
      return null;
    }
  
  }

  /**
   * Purpose : To Convert WMF File to SVG File
   * @param theFile - File object
   * @return A File object
   */
  private File WMF2SVGFile(File theFile){
    BulbulWMFTranscoder transcoder = new BulbulWMFTranscoder();
    try{ 
      TranscoderInput input = new TranscoderInput(theFile.toURL().toString());
      String outputFileName =theFile.toString();
      outputFileName = outputFileName.substring(0, outputFileName.toLowerCase().indexOf(BulbulWMFTranscoder.WMF_EXTENSION)) + BulbulWMFTranscoder.SVG_EXTENSION;
      theFile = new File(outputFileName); 
      TranscoderOutput output =new TranscoderOutput(new OutputStreamWriter(new FileOutputStream(theFile),"UTF8") );
    
      me.showStatus("Trascoding Started...");
      transcoder.transcode(input, output); 
      me.showStatus("Trascoding Ended...");
   
      return theFile;

    }catch(TranscoderException ex){
      ex.printStackTrace();
      return null;
    }catch(MalformedURLException ex){
      ex.printStackTrace();
      return null;
    }catch(Exception ex){
      ex.printStackTrace();
      return null;
    }
  }

  private class InteractorHandler extends InteractorAdapter{
    private Color c ;
    private int mouseX;
    private int mouseY;
    private Rectangle2D graphicRectangle;
    private GraphicsNode theGraphicsNode;
    private BJSVGCanvas aJSVGCanvas;
    private InteractorHandler(){
      System.out.println("Interactor");
    }
    public void mouseClicked(java.awt.event.MouseEvent e){
      //System.out.println("Mouse Clicked");
    }

    public void mouseDragged(java.awt.event.MouseEvent e){
      
      //System.out.println("Mouse Dragged");
      theGraphicsNode=((JSVGCanvas)e.getSource()).getGraphicsNode();
      
      
    }

    public void mousePressed(java.awt.event.MouseEvent e){
      //System.out.println("Mouse Pressed");
      aJSVGCanvas=((BJSVGCanvas)e.getSource());
      Shape outline =null;
      //temp(aJSVGCanvas,e.getPoint());
      Node node = getSelectedNode(aJSVGCanvas,e.getPoint());
      Graphics2D theGraphics = (Graphics2D)aJSVGCanvas.getGraphics();
      GraphicsNode gNode = aJSVGCanvas.getBridgeContext().getGraphicsNode((Element)node);
      
      //System.out.println(node);
      if (gNode!=null){
        //System.out.println(node);
        outline = gNode.getOutline();
        theGraphics.setColor(Color.BLUE);
        
        AffineTransform atNew = gNode.getTransform();
        AffineTransform atOld = theGraphics.getTransform();
        if (atNew!=null){
          theGraphics.setTransform(atNew);
          System.out.println("Transform :  " + atNew); 
          System.out.println("Scale : " + atNew.getScaleX() + "," + atNew.getScaleY());
          System.out.println("Shear : " + atNew.getShearX() + "," + atNew.getShearY());
          System.out.println("Traslate : " + atNew.getTranslateX() + "," + atNew.getTranslateY());
        }   
        theGraphics.draw(outline);
        theGraphics.setTransform(atOld);
    }
  }

     public void mouseReleased(java.awt.event.MouseEvent e){
      //System.out.println("Mouse Released");
      //Graphics2D theGraphics = (Graphics2D)aJSVGCanvas.getGraphics();
      repaint();
    }

     private void drawSelection(Graphics gr , Rectangle2D.Double  bounds ){
      Graphics2D g = (Graphics2D)gr;
      
      if (g!=null && bounds!=null ){
        g.setColor(Color.white);
        g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
        g.setColor(Color.black);
        Stroke stroke=g.getStroke();
        g.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{5,5},0.0f));
        g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
        g.setStroke(stroke);
      }
    }
  }

  private class BJSVGCanvas extends JSVGCanvas{
    public BridgeContext getBridgeContext(){
      return bridgeContext;
    }
  
  }

  protected Node getSelectedNode(BJSVGCanvas canvas, Point point){
            
      if(point!=null){
                
          BridgeContext ctxt=canvas.getBridgeContext();
          SVGDocument doc=canvas.getSVGDocument();
                
          if(ctxt!=null && doc!=null){
                    
              Element root=doc.getRootElement();
                    
              if(root!=null){
                        
                  if(root.hasChildNodes()){
                            
                      Node current=root.getLastChild();
                      GraphicsNode gnode=null;
                      AffineTransform af=null;
                      Point2D pt0=point, pt1=null;
                            
                      while(current!=null){
                                
                          pt1=pt0;
                                
                          //gets the graphics node linked with the current node
                          if(current instanceof Element)gnode=ctxt.getGraphicsNode((Element)current);
                          else gnode=null;
                                
                          if(gnode!=null){
                              //the tranform of the current node
                              af=gnode.getTransform();
                                    
                              if(af!=null){
                                        
                                  try{
                                      //puts the pt point in the plan of the untransformed nodes
                                      pt1=af.inverseTransform(point,null);
                                  }catch (Exception e){}
                              }
                                    
                                    
                              if(gnode.contains(pt1))return current;
                              //System.out.println(gnode);
                              //System.out.println(pt1);
                          }
                                
                          af=null;
                          gnode=null;
                          current=current.getPreviousSibling();
                      }
                  }
              }
          }
      }
            
    return null;
  }

  public void temp(BJSVGCanvas canvas, Point point){
      Element ele = canvas.getSVGDocument().getRootElement(); 
      BridgeContext ctxt = canvas.getBridgeContext();
      Point2D.Double pt =new Point2D.Double(point.x, point.y);
      Point pt1=new Point((int)pt.x, (int)pt.y);
      Graphics2D gr = (Graphics2D)canvas.getGraphics();
      Rectangle2D rect = null;
      Ellipse2D circle = null;
      GraphicsNode gNode =null; 
      if (ele.hasChildNodes()){
        Node current = ele.getLastChild();
        while (current!=null){
          if(current instanceof Element){
            gNode = ctxt.getGraphicsNode((Element)current);
          }else{
            gNode = null;
          }
          //System.out.print(current.getNodeName()+ " - ");  
          if (gNode!=null){ 
         //   System.out.print(gNode.getTransform()+ " - ");
       //     System.out.print((gNode.contains(new Point2D.Float((float)pt1.getX(),(float)pt1.getY()))) + " - ");
     //       System.out.print(new Point2D.Float((float)pt1.getX(),(float)pt1.getY()) + " - ");
   //         System.out.print(gNode.getGeometryBounds());
            rect=gNode.getGeometryBounds();
            if (rect!=null){
              final double radius=3;
              circle=new Ellipse2D.Double(pt1.getX()-radius,pt1.getY()-radius,2.0*radius,2.0*radius);
          //    gr.setPaint(Color.blue);
          //    gr.draw(circle);
          //    gr.draw(rect);
          //    gr.drawString((new Boolean(gNode.contains(pt1))).toString() ,(int)pt1.getX(),(int)pt1.getY());
            //  gr.draw(gNode.getOutline());
            }
          }
 //         System.out.println("");
          current =current.getPreviousSibling();
        }
      }
  }
}