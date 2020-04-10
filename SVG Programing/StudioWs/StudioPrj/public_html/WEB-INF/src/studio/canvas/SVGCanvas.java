package studio.canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.JSVGComponent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

/**
* @author Sudheer V. Pujar
* the class of the canvas of a SVG file
 */
public class SVGCanvas extends JSVGCanvas  {
  /**
	 * the list of the recorded paint listeners
	 */
	private LinkedList paintListeners=new LinkedList();
  
  /**
	 * the boolean enabling the display
	 */
	private boolean enableErrorDisplay=true;
  
  /**
	 * the booleans used by the repaint manager
	 */
	private boolean shouldRepaint=false;
  
  /**
	 * the cursor used to show that the computer is busy,the default cursor and
   * set before the last change of the cursor
	 */
	
  private Cursor waitCursor; 
  private Cursor defaultCursor;
  private Cursor lastCursor=null;
	
	/**
	 * the boolean enabling or disabling the wait cursor
	 */
	private boolean enableWaitCursor=true;
  
  /**
	 * the canvas' current scale and last scale
	 */
	private double scale=1.0;
  private double lastScale=1.0;
	
  private SVGScrollPane scrollpane;
  
	/**
	 * gets the canvas' current scale
	 * @return the canvas' current scale
	 */
	 public double getScale(){
	 	return scale;
	 }
	 
	 /**
	  * @return the last scale 
	  */
	 public double getLastScale(){
	 	return lastScale;
	 }
	 
	 /**
	  * sets the scale of the canvas
	  * @param scale the scale of the canvas
	  */
	 public void setScale(double scale){
	 	this.lastScale=this.scale;
	 	this.scale=scale;
	 }
  
  final private SVGCanvas canvas=this;
  
  public SVGCanvas() {
    super();
    setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
    doubleBufferedRendering=true;
		disableInteractions=true;
    selectableText=false;
    userAgent=new SVGCanvasUserAgent(userAgent);
    
    //the paint manager
		Thread paintManager=new Thread(){
			public void run() {
				while(true){
					try{
						sleep((long)75);
					}catch (Exception ex){}
				
					if(shouldRepaint){
						repaint();
						synchronized(this){
							shouldRepaint=false;
						}
					}
				}
			}
		};
		paintManager.start();
  }
  
  /**
	 * initializes the canvas
	 * @param editor the editor object 
	 * @param scrollpane the scrollpane in which the canvas is inserted
	 */
	public void init(SVGScrollPane scrollpane){
		
		this.scrollpane=scrollpane;
		final SVGScrollPane scrpane=scrollpane;
		
		waitCursor=getScrollPane().getCursors().getCursor("wait");
		defaultCursor=getScrollPane().getCursors().getCursor("default");

		//adds the listeners
		this.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("Document Loading...");
			}
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("Document Loaded.");
				//gets the document and the root element
				SVGDocument doc=canvas.getSVGDocument();
				if(doc!=null){
					SVGSVGElement root=doc.getRootElement();
					if(root!=null && root.getAttribute("viewBox").equals("")){
						Dimension size=getScaledCanvasSize();
						root.setAttributeNS(null, "viewBox", "0 0 "+(int)size.getWidth()+" "+(int)size.getHeight());
					}
				}
			}
		});

		this.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("Build Started...");
			}
			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("Build Done.");
        getScrollPane().getSVGManager().svgChanged(); 
			}
		});

		this.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {	
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("Rendering Started...");	
			}
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				canvas.getScrollPane().getStudio().getStatusBar().setSVGInfos("");
			}
		});	
		
		addUpdateManagerListener(new UpdateManagerAdapter(){
			public void updateStarted(UpdateManagerEvent e) {
				displayWaitCursor();
			}
			public void updateCompleted(UpdateManagerEvent e) {
				returnToLastCursor();
			}
			public void updateFailed(UpdateManagerEvent e) {
				returnToLastCursor();
			}
		});	
	}
  
  public SVGScrollPane getScrollPane(){
    return this.scrollpane;
  }
  /**
	 * @return the current bridge context
	 */
	public BridgeContext getBridgeContext(){
		return bridgeContext;
	}
  /**
	 * sets the current cursor
	 * @param cursor the current cursor
	 */
	public void setSVGCursor(Cursor cursor){
		if(cursor!=null && ! cursor.equals(getCursor()) && ! waitCursor.equals(getCursor())){
			lastCursor=getCursor();
			setCursor(cursor);
		}
	}
	
	/**
	 * returns to the last cursor
	 */
	protected void returnToLastCursor(){
		if(enableWaitCursor){
			if(lastCursor==null || (lastCursor!=null && lastCursor.equals(waitCursor)))setCursor(defaultCursor);
			else if(lastCursor!=null)setCursor(lastCursor);
		}
	}
	
	/**
	 * displays the wait cursor
	 */
	protected void displayWaitCursor(){
		if(enableWaitCursor && waitCursor!=null && ! waitCursor.equals(getCursor())){
			lastCursor=getCursor();
			setCursor(waitCursor);
		}
	}
	
	/**
	 * enables or disables the display of the wait cursor
	 * @param enable true to enable the display of the wait cursor
	 */
	public void setEnableWaitCursor(boolean enable){
		enableWaitCursor=enable;
	}
  /**
	 * @return the scaled canvas' size
	 */
	public Dimension getScaledCanvasSize(){
		
		//gets the document and the root element
		SVGDocument document=canvas.getSVGDocument();
		if(document!=null){
			SVGSVGElement rootElement=document.getRootElement();
										
			if(rootElement!=null){
				int width=0, height=0;
				width=(int)(rootElement.getWidth().getBaseVal().getValue()*scale);
				height=(int)(rootElement.getHeight().getBaseVal().getValue()*scale);
				return new Dimension(width, height);
			}
		}
		
		return new Dimension(0,0);
	}
  
  /**
	 * adds a paint listener
	 * @param l the paint listeners to be added
	 * @param makeRepaint the boolean telling to make a repaint after the paint listener was added or not
	 */
	public synchronized void addPaintListener(PaintListener l, boolean makeRepaint){
	    
		if(l!=null && l instanceof PaintListener){
		    
			paintListeners.add(l);
			if(makeRepaint)shouldRepaint=true;
		}
	}
  
  /**
	 * removes a paint listener
	 * @param l the paint listener to be removed
	 * @param makeRepaint the boolean telling to make a repaint after the paint listener was removed or not
	 */
	public synchronized void removePaintListener(PaintListener l, boolean makeRepaint){
	    
		paintListeners.remove(l);
		if(makeRepaint)shouldRepaint=true;
	}
	
	/**
	 * notifies the paint listeners when a paint action is done
	 *@param g the graphics
	 */
	protected synchronized void notifyPaintListeners(Graphics g){
		
		Iterator it=paintListeners.iterator();
		while(it.hasNext()){
		    
			((PaintListener)it.next()).paintToBeDone(g);
		}
	}
	
	/**
	 * repaints the canvas 
	 */
	public void repaintCanvas(){
	    synchronized(this){shouldRepaint=true;}
	}
	
	/**
	 * asks the repaint manager to repaint the canvas 
	 */
	public void delayedRepaint(){
		synchronized(this){shouldRepaint=true;}

	}
	
	/**
	 * the paintComponent method
	 *@param g the graphics
	 */
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		notifyPaintListeners(g);
	}
  
   /**
	  * enables or disabled the display of errors
	  * @param enableErrorDisplay true to enable the display of errors
	  */
	 public void setEnableErrorDisplay(boolean enableErrorDisplay){
	 	this.enableErrorDisplay=enableErrorDisplay;
	 }
	 
	 /**
	  * @return true if the display of errors is enabled
	  */
	 public boolean getEnableErrorDisplay(){
	 	return enableErrorDisplay;
	 }

	 /**
	  * @author Jordi SUC
	  * the class defining  am modified implementation of a user agent
	  */
	 protected class SVGCanvasUserAgent extends JSVGComponent.BridgeUserAgentWrapper{
		
		 /**
		  * the constructor of the class
		  * @param userAgent the user agent
		  */
		 protected  SVGCanvasUserAgent(UserAgent userAgent){
			 super(userAgent);
			 canvas.userAgent=userAgent;
		 }
		
		 /**
		  * does nothing
		  * @param cursor
		  */
		 public void setSVGCursor(Cursor cursor) {
				
		 }
		 
		/**
		 * Used to display an error
		 * @param ex the exception to be displayed
		 */
		public void displayError(Exception ex) {
			if(getEnableErrorDisplay())super.displayError(ex);
		}
		
	 }
  /**
	  * 
	  * @author Sudheer V. Pujar
	  *
	  * The interface of the paint listener
	  */
	 public interface PaintListener{
	 	
	 	/**
	 	 * the action that has to be done
	 	 * @param g the graphics object
	 	 */
	 	public void paintToBeDone(Graphics g);
	 	
	 }
}