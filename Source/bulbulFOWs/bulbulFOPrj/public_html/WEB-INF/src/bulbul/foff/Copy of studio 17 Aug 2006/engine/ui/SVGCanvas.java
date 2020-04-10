package bulbul.foff.studio.engine.ui;

import bulbul.foff.studio.engine.general.NodeIterator;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.border.LineBorder;

import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.JSVGComponent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

/**
 * 
 * @author Sudheer V. Pujar
 * @description 
 * @version 1.0 17-Aug-2005
 */
public class SVGCanvas extends JSVGCanvas {

  private Thread paintManager = null;

  private SVGCanvas svgCanvas = this;

  private SVGScrollPane scrollPane;

  private double scale = 1.0;

  private double lastScale = 1.0;

  private boolean shouldRepaint = false;

  private boolean waitCursorEnabled = true;

  private boolean errorDisplayEnabled = true;

  private boolean repaintEnabled = true;

  private boolean isDisposing = false;

  private final LinkedList bottomLayerPaintListeners = new LinkedList();

  private final LinkedList gridLayerPaintListeners = new LinkedList();

  private final LinkedList selectionLayerPaintListeners = new LinkedList();

  private final LinkedList drawLayerPaintListeners = new LinkedList();

  private final LinkedList topLayerPaintListeners = new LinkedList();

  private Cursor lastCursor = null;

  private Cursor waitCursor;

  private Cursor defaultCursor;

  private Hashtable usedResources = new Hashtable();

  /**
   * 
   * @description 
   */
  public SVGCanvas() {
    super();
    removeKeyListener(listener);
    removeMouseMotionListener(listener);
    removeMouseListener(listener);

    setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
    doubleBufferedRendering = false;
    disableInteractions = true;
    selectableText = false;
    setBorder(new LineBorder(new Color((float)0.5, (float)0.5, (float)0.5)));
    setOpaque(false);
    setBackground(new Color(0, 0, 0, 0));
    setRecenterOnResize(false);
    setProgressivePaint(true);

    userAgent = new SVGCanvasUserAgent(userAgent);
    //the paint manager
    paintManager = new Thread() {
        public void run() {
          while (!isDisposing) {
            try {
              sleep((long)75);
            } catch (Exception ex) {
              ;
            }
            if (shouldRepaint) {
              repaint();
              synchronized (svgCanvas) {
                shouldRepaint = false;
              }
            }
          }
        }
      }
    ;
    paintManager.start();
  }

  /**
   * 
   * @param studio
   * @param scrollPane 
   * @description 
   */
  public void init(Studio studio, SVGScrollPane scrollPane) {
    this.scrollPane = scrollPane;
    final SVGScrollPane finalScrollPane = scrollPane;
    final Studio finalStudio = studio;
    final SVGTab svgTab = finalScrollPane.getSvgTab();

    waitCursor = studio.getSvgCursors().getCursor("wait");
    defaultCursor = studio.getSvgCursors().getCursor("default");

    //adds the listeners

    final SVGDocumentLoaderAdapter documentLoaderAdapter = new SVGDocumentLoaderAdapter() {

        public void documentLoadingStarted(SVGDocumentLoaderEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Document Loading...");
        }

        public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Document Loaded.");

          //gets the document and the root element
          SVGDocument document = svgCanvas.getSVGDocument();

          if (document != null) {
            SVGSVGElement root = document.getRootElement();

            if (root != null && root.getAttribute("viewBox").equals("")) {
              Dimension size = getScaledCanvasSize();
              root.setAttributeNS(null, "viewBox", "0 0 " + (int)size.getWidth() + " " + (int)size.getHeight());
            }

            if (root != null) {

              //getting the map associating the id of a resource to the resource node
              LinkedList resourceNames = new LinkedList();
              resourceNames.add("linearGradient");
              resourceNames.add("radialGradient");
              resourceNames.add("pattern");
              resourceNames.add("marker");

              Hashtable resources =
                finalStudio.getSvgToolkit().getResourcesFromDefs(svgTab.getScrollPane().getSvgCanvas(), resourceNames);

              //applying modifications on the dom
              LinkedList nodesList = null;
              Node node = null;

              Iterator it;

              String namespace = root.getNamespaceURI();
              String id = "";
              String style = "";

              for (NodeIterator nit = new NodeIterator(root); nit.hasNext(); ) {

                try {
                  node = (Node)nit.next();
                } catch (Exception ex) {
                  node = null;
                }

                if (node != null && namespace.equals(node.getNamespaceURI()) && node instanceof Element) {
                  //normalizing the node
                  finalStudio.getSvgToolkit().normalizeNode(node);
                  //checking the color values consistency
                  finalStudio.getColorChooser().checkColorString(svgTab, (Element)node);
                  //getting the style attribute
                  style = ((Element)node).getAttribute("style");

                  if (style != null && !style.equals("")) {
                    //for each resource id, checks if it is contained in the style attribute
                    for (it = resources.keySet().iterator(); it.hasNext(); ) {
                      try {
                        id = (String)it.next();
                        nodesList = (LinkedList)usedResources.get(id);
                      } catch (Exception ex) {
                        id = null;
                        nodesList = null;
                      }

                      //adds the node in the used resource map
                      if (id != null && !id.equals("") && style.indexOf("#".concat(id)) != -1) {
                        if (nodesList == null) {
                          nodesList = new LinkedList();
                          usedResources.put(id, nodesList);
                        }

                        if (nodesList != null) {
                          nodesList.add(node);
                        }
                      }
                    }
                  }
                }
              }

              //normalizes the group nodes
              for (NodeIterator nit = new NodeIterator(root); nit.hasNext(); ) {
                try {
                  node = (Node)nit.next();
                } catch (Exception ex) {
                  node = null;
                }

                if (node != null && node.getNodeName().equals("g") && namespace.equals(node.getNamespaceURI()) &&
                    node instanceof Element) {
                  //normalizing the node
                  finalStudio.getSvgToolkit().normalizeGroupNode((Element)node);
                }
              }
            }
          }
        }
      }
    ;

    addSVGDocumentLoaderListener(documentLoaderAdapter);

    final GVTTreeBuilderAdapter gvtTreeBuilderAdapter = new GVTTreeBuilderAdapter() {

        public void gvtBuildStarted(GVTTreeBuilderEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Build Started...");

        }

        public void gvtBuildCompleted(GVTTreeBuilderEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Build Done.");

          //notifies that the frames contained in the frame manager have changed
          getStudio().getSvgTabManager().svgTabChanged();

          //Need To Verify this line of code Later 
          getStudio().getSvgTabManager()
          .setCurrentSVGTab(((SVGTab)getStudio().getSvgTabSet().getSelectedComponent()).getName());


        }
      }
    ;

    addGVTTreeBuilderListener(gvtTreeBuilderAdapter);

    final GVTTreeRendererAdapter gvtTreeRendererAdapter = new GVTTreeRendererAdapter() {

        public void gvtRenderingPrepare(GVTTreeRendererEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("Rendering Started...");
        }

        public void gvtRenderingCompleted(GVTTreeRendererEvent e) {

          svgCanvas.getScrollPane().getStudio().getMainStatusBar().setInfo("");
        }
      }
    ;

    addGVTTreeRendererListener(gvtTreeRendererAdapter);

    final UpdateManagerAdapter updateManagerAdapter = new UpdateManagerAdapter() {

        public void updateStarted(UpdateManagerEvent e) {

          displayWaitCursor();
        }

        public void updateCompleted(UpdateManagerEvent e) {
          returnToLastCursor();
        }

        public void updateFailed(UpdateManagerEvent e) {

          returnToLastCursor();
        }
      }
    ;

    addUpdateManagerListener(updateManagerAdapter);


    //adds a dispose runnable
    getScrollPane().getSvgTab().addDisposeRunnable(new Runnable() {
                                                     public void run() {
                                                       synchronized (svgCanvas) {
                                                         isDisposing = true;
                                                       }
                                                       svgCanvas
                                                       .removeSVGDocumentLoaderListener(documentLoaderAdapter);
                                                       svgCanvas.removeGVTTreeBuilderListener(gvtTreeBuilderAdapter);
                                                       svgCanvas.removeGVTTreeRendererListener(gvtTreeRendererAdapter);
                                                       svgCanvas.removeUpdateManagerListener(updateManagerAdapter);
                                                       removeAllPaintListeners();
                                                       svgCanvas.stopProcessing();
                                                       svgCanvas.bridgeContext.dispose();
                                                       svgCanvas.dispose();
                                                       svgCanvas.removeAll();
                                                       svgCanvas.userAgent = null;
                                                     }
                                                   }
    );
  }

  /**
   * 
   * @description 
   * @param g
   */
  protected synchronized void notifyPaintListeners(Graphics g) {
    if (isRepaintEnabled()) {
      Iterator it;

      for (it = gridLayerPaintListeners.iterator(); it.hasNext(); ) {
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }

      for (it = bottomLayerPaintListeners.iterator(); it.hasNext(); ) {
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }

      for (it = selectionLayerPaintListeners.iterator(); it.hasNext(); ) {
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }

      for (it = drawLayerPaintListeners.iterator(); it.hasNext(); ) {
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }

      for (it = topLayerPaintListeners.iterator(); it.hasNext(); ) {
        ((SVGPaintListener)it.next()).paintToBeDone(g);
      }
    }
  }

  /**
   * 
   * @description 
   * @param height
   * @param width
   */
  public void newDocument(int width, int height) {

    DOMImplementation implementation = SVGDOMImplementation.getDOMImplementation();
    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    Document document = implementation.createDocument(svgNS, "svg", null);
    //get the root element (the svg element)
    Element svgRoot = document.getDocumentElement();

    //set the width and height attribute on the root svg element
    svgRoot.setAttributeNS(null, "width", new Integer(width).toString());
    svgRoot.setAttributeNS(null, "height", new Integer(height).toString());
    svgRoot.setAttribute("viewBox", "0 0 " + width + " " + height);

    setSVGDocument((SVGDocument)document);

    scrollPane.initScrollPane();
    scrollPane.getSvgTab().setModified(true);
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Dimension getInitialCanvasSize() {
    //gets the document and the root element
    SVGDocument document = svgCanvas.getSVGDocument();
    if (document != null) {
      SVGSVGElement root = document.getRootElement();
      if (root != null) {
        int width = (int)getStudio().getSvgToolkit().getPixelledNumber(root.getAttributeNS(null, "width"));
        int height = (int)getStudio().getSvgToolkit().getPixelledNumber(root.getAttributeNS(null, "height"));
        return new Dimension(width, height);
      }
    }
    return new Dimension(0, 0);
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Dimension getScaledCanvasSize() {
    Dimension scaledSize = new Dimension(0, 0);
    //gets the document and the root element
    SVGDocument document = svgCanvas.getSVGDocument();
    if (document != null) {
      SVGSVGElement root = document.getRootElement();
      if (root != null) {
        double width = 0;
        double height = 0;
        try {
          width = getStudio().getSvgToolkit().getPixelledNumber(root.getAttributeNS(null, "width")) * scale;
          height = getStudio().getSvgToolkit().getPixelledNumber(root.getAttributeNS(null, "height")) * scale;
          scaledSize.width = (int)width;
          scaledSize.height = (int)height;
        } catch (Exception ex) {
          ;
        }
      }
    }
    return scaledSize;
  }

  /**
   * 
   * @description 
   */
  public void displayWaitCursor() {
    if (waitCursorEnabled && waitCursor != null && !waitCursor.equals(getCursor())) {
      lastCursor = getCursor();
      setCursor(waitCursor);
    }
  }

  /**
   * 
   * @description 
   */
  public void returnToLastCursor() {
    if (waitCursorEnabled) {
      if (lastCursor == null || (lastCursor != null && lastCursor.equals(waitCursor))) {
        setCursor(defaultCursor);
      } else if (lastCursor != null) {
        setCursor(lastCursor);
      }
    }
  }

  /**
   * 
   * @description 
   * @param waitCursorEnabled
   */
  public void setWaitCursorEnabled(boolean waitCursorEnabled) {
    this.waitCursorEnabled = waitCursorEnabled;
  }

  /**
   * 
   * @description 
   * @param cursor
   */
  public void setSVGCursor(Cursor cursor) {
    if (cursor != null && !cursor.equals(getCursor()) && !waitCursor.equals(getCursor())) {
      lastCursor = getCursor();
      setCursor(cursor);
    }
  }


  /**
   * 
   * @description 
   */
  public synchronized void repaintCanvas() {
    if (isRepaintEnabled()) {
      shouldRepaint = true;
    }
  }

  /**
   * 
   * @description 
   * @param repaintEnabled
   */
  public void setRepaintEnabled(boolean repaintEnabled) {
    this.repaintEnabled = repaintEnabled;
    if (repaintEnabled) {
      shouldRepaint = true;
    }
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
  public synchronized void delayedRepaint() {
    if (isRepaintEnabled()) {
      shouldRepaint = true;
    }
  }

  /**
   * 
   * @description 
   * @param makeRepaint
   * @param paintListener
   */
  public synchronized void addSelectionLayerPaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    if (paintListener != null && paintListener instanceof SVGPaintListener) {
      selectionLayerPaintListeners.add(paintListener);
      if (isRepaintEnabled() && makeRepaint) {
        shouldRepaint = true;
      }
    }
  }

  /**
   * 
   * @description 
   * @param makeRepaint
   * @param paintListener
   */
  public synchronized void addDrawLayerPaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    if (paintListener != null && paintListener instanceof SVGPaintListener) {
      drawLayerPaintListeners.add(paintListener);
      if (isRepaintEnabled() && makeRepaint) {
        shouldRepaint = true;
      }
    }
  }

  /**
   * 
   * @description 
   * @param makeRepaint
   * @param paintListener
   */
  public synchronized void addGridLayerPaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    if (paintListener != null && paintListener instanceof SVGPaintListener) {
      gridLayerPaintListeners.add(paintListener);
      if (isRepaintEnabled() && makeRepaint) {
        shouldRepaint = true;
      }
    }
  }

  /**
   * 
   * @param paintListener
   * @param makeRepaint
   * @description 
   */
  public synchronized void addTopLayerPaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    if (paintListener != null && paintListener instanceof SVGPaintListener) {
      topLayerPaintListeners.add(paintListener);
      if (isRepaintEnabled() && makeRepaint) {
        shouldRepaint = true;
      }
    }
  }

  /**
   * 
   * @description 
   * @param makeRepaint
   * @param paintListener
   */
  public synchronized void addBottomLayerPaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    if (paintListener != null && paintListener instanceof SVGPaintListener) {
      bottomLayerPaintListeners.add(paintListener);
      if (isRepaintEnabled() && makeRepaint) {
        shouldRepaint = true;
      }
    }
  }

  /**
   * 
   * @description 
   * @param makeRepaint
   * @param paintListener
   */
  public synchronized void removePaintListener(SVGPaintListener paintListener, boolean makeRepaint) {
    gridLayerPaintListeners.remove(paintListener);
    bottomLayerPaintListeners.remove(paintListener);
    selectionLayerPaintListeners.remove(paintListener);
    drawLayerPaintListeners.remove(paintListener);
    topLayerPaintListeners.remove(paintListener);
    if (isRepaintEnabled() && makeRepaint) {
      shouldRepaint = true;
    }
  }

  /**
   * 
   * @description 
   */
  public synchronized void removeAllPaintListeners() {
    gridLayerPaintListeners.clear();
    bottomLayerPaintListeners.clear();
    selectionLayerPaintListeners.clear();
    drawLayerPaintListeners.clear();
    topLayerPaintListeners.clear();
  }


  /**
   * 
   * @description 
   * @param g
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    notifyPaintListeners(g);
  }

  /**
   * 
   * @description 
   * @param node
   * @param resourceId
   */
  public synchronized void addNodeUsingResource(String resourceId, Node node) {
    if (resourceId != null && !resourceId.equals("") && node != null) {
      LinkedList nodesList = null;
      //checking if the id of the resource is contained in the map
      if (usedResources.containsKey(resourceId)) {
        try {
          //getting the associated list of nodes
          nodesList = (LinkedList)usedResources.get(resourceId);
        } catch (Exception ex) {
          ;
        }
      }
      if (nodesList == null) {
        //if the id was not contained in the map, creates a new list of nodes
        nodesList = new LinkedList();
        usedResources.put(resourceId, nodesList);
      }
      if (nodesList != null) {
        //adding the node to the list
        nodesList.add(node);
      }
    }
  }

  public synchronized void removeNodeUsingResource(String resourceId, Node node) {
    if (resourceId != null && !resourceId.equals("") && node != null) {
      LinkedList nodesList = null;
      //checking if the id of the resource is contained in the map
      if (usedResources.containsKey(resourceId)) {
        try {
          //getting the associated list of nodes
          nodesList = (LinkedList)usedResources.get(resourceId);
        } catch (Exception ex) {
          ;
        }
      }

      if (nodesList != null && nodesList.contains(node)) {
        //removing the node from the list
        nodesList.remove(node);
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   */
  public UserAgent getUserAgent() {
    return this.userAgent;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public SVGScrollPane getScrollPane() {
    return this.scrollPane;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return this.scrollPane.getStudio();
  }

  /**
   * 
   * @description 
   * @return 
   */
  public GraphicsNode getGVTRoot() {
    return gvtRoot;
  }

  /**
   * 
   * @description 
   * @param scale
   */
  public void setScale(double scale) {
    this.lastScale = this.scale;
    this.scale = scale;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public double getScale() {
    return scale;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public double getLastScale() {
    return lastScale;
  }


  /**
   * 
   * @description 
   * @param errorDisplayEnabled
   */
  public void setErrorDisplayEnabled(boolean errorDisplayEnabled) {
    this.errorDisplayEnabled = errorDisplayEnabled;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean isErrorDisplayEnabled() {
    return errorDisplayEnabled;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Hashtable getUsedResources() {
    return new Hashtable(usedResources);
  }


  /**
   * 
   * @description 
   * @version 1.0 17-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class SVGCanvasUserAgent extends JSVGComponent.BridgeUserAgentWrapper {

    protected SVGCanvasUserAgent(UserAgent userAgent) {
      super(userAgent);
      svgCanvas.userAgent = userAgent;
    }

    public void setSVGCursor(Cursor cursor) {

    }

    public void displayError(Exception ex) {

    }
  }
}
