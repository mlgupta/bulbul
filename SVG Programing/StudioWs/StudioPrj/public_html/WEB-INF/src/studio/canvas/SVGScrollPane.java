package studio.canvas;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import java.awt.geom.Point2D;
import java.lang.reflect.Method;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

import studio.SVGApplet;
import studio.manager.SVGClassManager;
import studio.general.SVGCursors;
import studio.manager.SVGManager;
import studio.undoredo.SVGUndoRedo;

public class SVGScrollPane extends JPanel  {
  private SVGApplet studio; 
  private JPanel northPanel = new JPanel();
  private BorderLayout svgScrollPaneLayout = new BorderLayout();
  private JPanel eastPanel = new JPanel();
  private JPanel southPanel = new JPanel();
  private JPanel westPanel = new JPanel();
  private JPanel svgPanel = new JPanel();
  private BorderLayout northPanelLayout = new BorderLayout();
  private BorderLayout eastPanelLayout = new BorderLayout();
  private BorderLayout southPanelLayout = new BorderLayout();
  private BorderLayout westPanelLayout = new BorderLayout();
  private JScrollBar vertical = new JScrollBar();
  private JScrollBar horizontal = new JScrollBar();
  private Component northEastCornerBox;
  private Component northWestCornerBox;
  private Component southWestCornerBox;
  private Component southEastCornerBox;
  private SVGCanvas canvas;
  private SVGToolkit toolkit;
  protected JPanel horizontalRuler; 
  protected JPanel verticalRuler;
  private final int COMPONENT_RESIZED=0;
	private final int CANVAS_TRANSFORM_CHANGED=1;
  private SVGClassManager svgClassManager;
  private SVGUndoRedo undoRedo;
  private SVGSelection svgSelection;
  
  /**
	 * the boolean set to true if the SVG picture has been modified 
	 */
	private boolean modified=false;
  
  /**
	 * the canvas' bounds the rendered rectangle and the border rectangle
	 */
	private final Rectangle canvasBounds=new Rectangle(0, 0, 0, 0); 
	private final Rectangle renderedRectangle=new Rectangle(0,0,0,0); 
  private final Rectangle borderRectangle=new Rectangle(0, 0, 0, 0);
  /**
	 * the translations values for the rendering transform and the painting transform
	 */
	private final Dimension	translateValues=new Dimension(0,0); 
	private final Dimension	paintingValues=new Dimension(0, 0);       
  
  /**
	 * true if the scroll changes should be ignored
	 */
	private boolean ignoreScrollChange=false;

  /**
	 * the scrollbar Listeners 
	 */
	private SBListener hsbListener;
	private SBListener vsbListener;
  
  /**
	 * true if the rulers are activated
	 */
	private boolean rulersEnabled=true;
	
	/**
	 * true to align the mouse points with the rulers
	 */
	private boolean alignWithRulersEnabled=false;
	
	/**
	 * used by the rulers
	 * a Double object representing a scale is associated to a Double representing a range
	 */
	private final Hashtable rulersRangesMap=new Hashtable();
  
  /**
	 * true if the grid is visible
	 */
	private boolean gridVisible=false;
	
	/**
	 * the paint listener for the grid
	 */
	private GridPainter gridPaintListener=null;
  
  /**
   * the position of the mouse
   */								
	private final Point mousePosition=new Point();
  
  /**
   * 
   */
  public SVGScrollPane(SVGApplet studio) {
    try {
      this.studio=studio;
      this.toolkit=new SVGToolkit(this);
      this.svgClassManager=new SVGClassManager(this);
      this.svgClassManager.init();
      
    
      this.canvas=new SVGCanvas();
      jbInit();
      
      canvas.init(this);
      canvas.displayWaitCursor();

      init();
      try{
        undoRedo=(SVGUndoRedo)svgClassManager.getClassObject("UndoRedo");
      }catch (Exception ex){undoRedo=null;}
      
      try{
        svgSelection=(SVGSelection)svgClassManager.getClassObject("Selection");
      }catch (Exception ex){svgSelection=null;}
      
      canvas.returnToLastCursor();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(svgScrollPaneLayout);
    northPanel.setLayout(northPanelLayout);
    eastPanel.setLayout(eastPanelLayout);
    southPanel.setLayout(southPanelLayout);
    westPanel.setLayout(westPanelLayout);
    horizontal.setOrientation(JScrollBar.HORIZONTAL);
    horizontal.setValues(0,0,0,0);
    vertical.setOrientation(JScrollBar.VERTICAL);
    vertical.setValues(0,0,0,0);
    this.add(northPanel, BorderLayout.NORTH);
    eastPanel.add(vertical, BorderLayout.CENTER);
    this.add(eastPanel, BorderLayout.EAST);
    southPanel.add(horizontal, BorderLayout.CENTER);
    this.add(southPanel, BorderLayout.SOUTH);
    this.add(westPanel, BorderLayout.WEST);
    svgPanel.setLayout(null);
		svgPanel.setSize(new Dimension(getSize().width-2*vertical.getPreferredSize().width, getSize().height-2*horizontal.getPreferredSize().height));
    this.add(svgPanel, BorderLayout.CENTER);
  }
  
  /**
   * Runtime Init
   * @throws java.lang.Exception
   */
  private void init() throws Exception{
    northEastCornerBox=Box.createRigidArea(new Dimension(vertical.getPreferredSize().width, horizontal.getPreferredSize().height));
    northPanel.add(northEastCornerBox, BorderLayout.EAST);
    northWestCornerBox=Box.createRigidArea(new Dimension(vertical.getPreferredSize().width, horizontal.getPreferredSize().height));
    northPanel.add(northWestCornerBox, BorderLayout.WEST);
    southEastCornerBox=Box.createRigidArea(new Dimension(vertical.getPreferredSize().width, horizontal.getPreferredSize().height));
    southPanel.add(southEastCornerBox, BorderLayout.EAST);
    southWestCornerBox=Box.createRigidArea(new Dimension(vertical.getPreferredSize().width, horizontal.getPreferredSize().height));        
    southPanel.add(southWestCornerBox, BorderLayout.WEST);
    
    // listeners
		hsbListener=new SBListener(false);
		horizontal.addAdjustmentListener(hsbListener);
		
		vsbListener=new SBListener(true);
		vertical.addAdjustmentListener(vsbListener);
    
    canvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
      public void documentLoadingStarted(SVGDocumentLoaderEvent e){
      
      }
      
      public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
        initScrollPane4Canvas();
      }
    });
    
    //adds the listener of the resizement of the svgpanel
		svgPanel.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent evt){
				changeCanvasBounds(0, 0);
				resizeScrollBars(0, 0, COMPONENT_RESIZED);
			}
		});
    repaint();
  }
  
   
	/**
   * initializes the scrollpane properties when a document has been loaded
   */
  public void initScrollPane4Canvas(){	    
		svgPanel.add(this.canvas);	
    //adds listeners to display information about the canvas and the mouse position
		initCanvasInformationDisplay();
		changeCanvasBounds(0, 0);
		resizeScrollBars(0, 0, COMPONENT_RESIZED);
		repaint();
	}
  /**
	 * initializes the tools that will provide information about the canvas and the mouse position
	 */
	public void initCanvasInformationDisplay(){
    //creates the paint listener to draw the grid
		gridPaintListener=new GridPainter();
    
    studio.getStatusBar().setSVGZoom("100%");
    //creates the rulers
		horizontalRuler=new SVGRuler(false);
		verticalRuler=new SVGRuler(true);
		
		//sets the the properties and adds the rulers to the panels
		verticalRuler.setPreferredSize(vertical.getPreferredSize());
		horizontalRuler.setPreferredSize(horizontal.getPreferredSize());
		
		northPanel.add(horizontalRuler, BorderLayout.CENTER);
		westPanel.add(verticalRuler, BorderLayout.CENTER);
		
		//used to convert numbres to strings
		DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		final DecimalFormat format=new DecimalFormat("######",symbols);
    
    
		//sets the mouse listeners for the canvas
		canvas.addMouseMotionListener(new MouseMotionListener(){
			protected void displayCoordinates(double x ,double y){
				mousePosition.x=(int)x;
				mousePosition.y=(int)y;
				
				//updates the display of the mouse coordinates
				studio.getStatusBar().setSVGX(format.format(Math.floor((x-translateValues.width)/canvas.getScale())));
				studio.getStatusBar().setSVGY(format.format(Math.floor((y-translateValues.height)/canvas.getScale())));
        
        horizontalRuler.repaint();
				verticalRuler.repaint();	
			}
			
			public void mouseDragged(MouseEvent e){
				displayCoordinates(e.getX(),e.getY());
			}
			
			public void mouseMoved(MouseEvent e){
				displayCoordinates(e.getX(),e.getY());
			}
		});
		
		canvas.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent e){
        studio.getStatusBar().setSVGX("");
				studio.getStatusBar().setSVGY("");			    
			}
		});
		
		repaint();
	}
  /**
	 * makes the layout of the svgpanel
	 */
	public void makeSVGPanelLayout(){
		
		if(canvas!=null)canvas.setBounds(canvasBounds);
	}
  
  /**
	 * @return true if the SVG picture has been modified
	 */
	public boolean isModified(){
		return modified;
	}
	
	/**
	 * sets that the svg document has been modified
	 * @param modified must be true if the svg picture has been modified
	 */
	public void setModified(boolean modified){
		this.modified=modified;
		//setSVGFrameLabel(name);
	}
	
	/**
	 * the paintComponent method
	 * @param g the graphics object
	 */
	public void paintComponent(Graphics g){
	    
		makeSVGPanelLayout();
		super.paintComponent(g);
		
		canvas.setBorder(new MatteBorder(	borderRectangle.y, 
																	borderRectangle.x, 
																	borderRectangle.height, 
																	borderRectangle.width, 
																	new Color((float)0.5,(float)0.5,(float)0.5)));
	}
  /**
	 * changes the canvas bound
	 * @param tx the x value of the rendering transform
	 * @param ty the y value of the rendering transform
	 */
	protected void changeCanvasBounds(int tx, int ty){
	    
		
    int renderedWidth=0;
    int renderedHeight=0;
    
    int canvasLeft=0; 
    int canvasTop=0;
    int canvasWidth;
    int canvasHeight;
    
		//the values of the border
		int borderLeft; 
    int borderTop;
    int borderHeight;
    int borderWidth;
		
		canvasWidth=canvas.getScaledCanvasSize().width;
		canvasHeight=canvas.getScaledCanvasSize().height;
		
		//computes the bound of the canvas
		if(canvasWidth<=svgPanel.getSize().width){
		    
			canvasLeft=(int)(svgPanel.getSize().width/2-canvasWidth/2);
			renderedWidth=canvasWidth;
			tx=0;
			borderLeft=1;
			borderWidth=1;
			
		}else{
		    
			canvasLeft=0;
			renderedWidth=svgPanel.getSize().width;
			borderLeft=0;
			borderWidth=0;
		}
		
		if(canvasHeight<=svgPanel.getSize().height){
		    
			canvasTop=(int)(svgPanel.getSize().height/2-canvasHeight/2);
			renderedHeight=canvasHeight;
			ty=0;
			borderTop=1;
			borderHeight=1;
			
		}else{
		    
			canvasTop=0;
			renderedHeight=svgPanel.getSize().height;
			borderTop=0;
			borderHeight=0;
		}

		//changes the values of the border
		borderRectangle.x=borderLeft;
		borderRectangle.y=borderTop;
		borderRectangle.height=borderHeight;
		borderRectangle.width=borderWidth;
		
		//the rectangle to be rendered
		renderedRectangle.x=0;
		renderedRectangle.y=0;
		renderedRectangle.width=renderedWidth;
		renderedRectangle.height=renderedHeight;
		
		//the bounds of the canvas
		canvasBounds.x=canvasLeft;
		canvasBounds.y=canvasTop;
		canvasBounds.width=canvasWidth;
		canvasBounds.height=canvasHeight;
		
		translateValues.width=tx;
		translateValues.height=ty;
		
		//sets the values
		hsbListener.setStartValue(tx);
		vsbListener.setStartValue(ty);
			
		//sets the rendering transform
		AffineTransform at=AffineTransform.getTranslateInstance(translateValues.width, translateValues.height);
		at.concatenate(AffineTransform.getScaleInstance(canvas.getScale(), canvas.getScale()));
		canvas.setRenderingTransform(at);
		
    repaint();
	}
	
	/**
	 *	Compute the scrollbar extents, and determine if scrollbars should be visible.
	 *@param tx the position for the horizontal scroll
	 *@param ty the position for the vertical scroll
	 *@param type the type of the object calling this method
	 */
	protected void resizeScrollBars(int tx, int ty, int type){
		
    ignoreScrollChange = true;
    
		Dimension canvasSize=canvas.getScaledCanvasSize();

		if(type==COMPONENT_RESIZED){
			tx=0;
			ty=0;
		}
		
		//sets the values
		hsbListener.setStartValue(tx);
		vsbListener.setStartValue(ty);
		
		vertical.setValue(ty);
		horizontal.setValue(tx);

		vertical.setValues(ty,svgPanel.getSize().height,0,canvasSize.height);
		horizontal.setValues(tx, svgPanel.getSize().width,0,canvasSize.width);

		//enables or disables the scrollbars
    horizontal.setEnabled(canvasBounds.width>svgPanel.getSize().width);
    vertical.setEnabled(canvasBounds.height>svgPanel.getSize().height);
		
		//sets the increments
		vertical.setBlockIncrement((int)(0.9*canvasSize.width));
		horizontal.setBlockIncrement((int)(0.9*canvasSize.width));

		vertical. setUnitIncrement((int)(0.1*canvasSize.width));
		horizontal.setUnitIncrement((int)(0.1*canvasSize.width));

		ignoreScrollChange=false;
	}
  
  /**
	 * shows or hide the grid
	 * @param visible 	true to show the grid
	 * 								false to hide the grid
	 */
	public void setGridVisible(boolean visible){
		
		if(gridVisible && !visible){
			gridVisible=false;
			canvas.removePaintListener(gridPaintListener, true);
			canvas.repaint();
			
		}else if(!gridVisible && visible){
			gridVisible=true;
			canvas.addPaintListener(gridPaintListener, true);
			canvas.repaint();
		}
	}
  
  /**
   * 
   * @return SVGCanvas
   */
  public SVGCanvas getSVGCanvas(){
    return this.canvas;
  } 
  
  /**
   * 
   * @return SVGApplet
   */
  public SVGApplet getStudio(){
    return this.studio;
  }
  
  /**
   * 
   * @return SVGToolkit
   */
  public SVGToolkit getSVGToolkit(){
    return this.toolkit;
  }
  
  /**
	 * @param name the name of the module
	 * @return the module object
	 */
	public Object getStaticClassObject(String name){
		return svgClassManager.getClassObject(name);
	}
  /**
	 * @return the static undoRedo module
	 */
	public SVGUndoRedo getUndoRedo(){
		return undoRedo;
	}
  /**
	 * @return the static svgSelection class
	 */
	public SVGSelection getSVGSelection(){
		return svgSelection;
	}
  /**
	 * @return the translation values for the rendering transform
	 */
	public Dimension getTranslateValues(){
	    
		return new Dimension(-(-translateValues.width+paintingValues.width), -(-translateValues.height+paintingValues.height));
	}
  /**
	 * @return the range of the rulers along the x and y axis
	 */
	public Point getRulersRange(){
		double scale=getSVGCanvas().getScale();
		//the range of the rulers
		int range=5;
		Integer iRange=null;
		try{
			iRange=(Integer)rulersRangesMap.get(new Double(scale));
		}catch (Exception ex){iRange=null;}
		if(iRange!=null)range=iRange.intValue();
		return new Point(range, range);
	}
  /**
	 * sets the boolean to align the mouse points with the rulers
	 * @param enable true to align the mouse points with the rulers
	 */
	public void setAlignWithRulersEnabled(boolean enable){
		this.alignWithRulersEnabled=enable;
	}
	
	/**
	 * @return true tif the mouse points are aligned with the rulers
	 */
	public boolean isAlignWithRulersEnabled(){
		return alignWithRulersEnabled;
	}
	
	/**
	 * the method used to get the point correponding to the given point when aligned with the rulers
	 * @param point the point
	 * @return the point correponding to the given point when aligned with the rulers
	 */
	public Point getAlignedWithRulersPoint(Point point){
		if(point!=null){
			Point2D.Double pt=getSVGToolkit().getScaledPoint(new Point2D.Double(point.x, point.y), true);
			if(alignWithRulersEnabled){
				double scale=getSVGCanvas().getScale();

				//the range of the rulers
				int range=5;
				Integer iRange=null;

				try{
					iRange=(Integer)rulersRangesMap.get(new Double(scale));
				}catch (Exception ex){iRange=null;}
			
				if(iRange!=null)range=iRange.intValue();

				int x=0, y=0, qx=1, qy=1;
				//computes the new point values
				if(pt!=null && range!=0){
					x=(int)pt.x;
					y=(int)pt.y;
					qx=(int)Math.floor(x/range);
					qy=(int)Math.floor(y/range);
				
					Point2D.Double pt2=new Point2D.Double(qx*range, qy*range);
				
					if(pt2!=null){
						return new Point((int)pt2.x, (int)pt2.y);
					}
				}
				
			}else{
				return new Point((int)pt.x, (int)pt.y);
			}
		}
		
		return null;
	}
  
  /**
	 * @return the manager of the cursors
	 */
	public SVGCursors getCursors(){
		return svgClassManager.getCursors();
	}
  /**
	 * @return SVGManager the class that manages the different frames in the desktop pane
	 */
	public SVGManager getSVGManager(){
		return  svgClassManager.getSVGManager();
	}
  /**
	 * used to call the same method on each module
	 * @param method the method name
	 * @param cargs an array of Class objects
	 * @param args an array of objects
	 */
	public void invokeOnModules(String method, Class[] cargs, Object[] args){
		Collection modules=svgClassManager.getClassObjects();
		Iterator it=modules.iterator();
		Object current=null;
		while(it.hasNext()){
			current=it.next();
			try{
			  //invokes the method on each instance contained in the map
        (current.getClass().getMethod(method,cargs)).invoke(current,args);
			}catch (Exception ex){
        ex.printStackTrace();
      }			
		}		
	}
  
  /**
	 *	the scrollbar listener
	 */
	protected class SBListener implements AdjustmentListener{
		
		/**
		 * determines to which scrollbars to listener is linked
		 */
		private boolean isVertical;
		
		/**
		 * the initial value of the scrollbar
		 */
		private int startValue=0;
		
		/**
		 * true if the last adjustement was a dragged adjustement
		 */
		private boolean wasAdjusting=false;

		/**
		 * the constructor of the class
		 * @param vertical true for a vertical scrollbar
		 */
		protected SBListener(boolean vertical){
			isVertical = vertical;
		}

		/**
		 * the method called when the event occurs
		 * @param evt the event
		 */
		public void adjustmentValueChanged(AdjustmentEvent evt) {
			
			if(! ignoreScrollChange){
        boolean isAdjusting=false;
        isAdjusting=(isVertical)?vertical.getValueIsAdjusting():horizontal.getValueIsAdjusting();
				
        if((!wasAdjusting) || (!isAdjusting)){
					paintingValues.width=0;
					paintingValues.height=0;
				}
				
				wasAdjusting=isAdjusting;
				
				//the translation values for the rendering transform and the painting transform
				int	tx=translateValues.width;
        int ty=translateValues.height;
				int	px=paintingValues.width;
        int py=paintingValues.height;
			
				int newValue=evt.getValue();
				int diff=startValue-newValue;
			
				//computes the new translation values giving the variation of the value of the scrollbars
				if (isVertical) {
					if(diff<0){
						ty=ty-Math.abs(diff);
						py=py-Math.abs(diff);
					}else{
						 ty=ty+Math.abs(diff);
						py=py+Math.abs(diff);
					}
				} else {
					if(diff<0){
						tx=tx-Math.abs(diff);
						px=px-Math.abs(diff);
					}else{
						tx=tx+Math.abs(diff);
						px=px+Math.abs(diff);
					}
				}

				//if the scrollbars are dragged
				if(isAdjusting){
					//computes the painting transform
					paintingValues.width=px;
					paintingValues.height=py;
					translateValues.width=tx;
					translateValues.height=ty;
					canvas.setPaintingTransform(AffineTransform.getTranslateInstance(paintingValues.width, paintingValues.height));
				}else{
					//otherwise
					if(paintingValues.width!=0 && paintingValues.height!=0){
						//removes the painting transform
						paintingValues.width=0;
						paintingValues.height=0;
						canvas.setPaintingTransform(AffineTransform.getTranslateInstance(paintingValues.width, paintingValues.height));
					}
					//computes the rendering transform
					translateValues.width=tx;
					translateValues.height=ty;
					AffineTransform at=AffineTransform.getTranslateInstance(translateValues.width, translateValues.height);
					at.concatenate(AffineTransform.getScaleInstance(canvas.getScale(), canvas.getScale()));
					canvas.setRenderingTransform(at);
				}
				
        startValue=newValue;

				//repaints the rulers
				horizontalRuler.repaint();
				verticalRuler.repaint();
			}
		}
    
    /**
		 * sets the start value
		 * @param startValue the start value
		 */
		protected void setStartValue(int startValue){
			this.startValue=startValue;
		}
  }
  /**
	 * @author Sudheer V. Pujar
	 * the class of the rulers
	 */
	protected class SVGRuler extends JPanel{
		
		/**
		 * true if the ruler is a vertical one
		 */
		private boolean isVertical;
		
		/**
		 * the font for the numbers displayed in th rulers
		 */
		private Font rulersFont=new Font("myFont",Font.ROMAN_BASELINE,9);
		
		/**
		 * a Double object representing a scale is associated to an array list
		 */
		private Hashtable valuesToDiplayMap=new Hashtable();

		/**
		 * the constructor of the class
		 * @param isVertical true if the ruler is a vertical one
		 */		
		protected SVGRuler(boolean isVertical){
			this.isVertical=isVertical;
			
      setBorder(new LineBorder(Color.lightGray));
			
			//creates the maps for the range and the values to display
			ArrayList list=null;
			
			//scale 0.05
			rulersRangesMap.put(new Double(0.05), new Integer(100));
			list=new ArrayList();
			list.add(new Integer(1000));
			valuesToDiplayMap.put(new Double(0.05), list);
			
			//scale 0.1
			rulersRangesMap.put(new Double(0.1), new Integer(100));
			list=new ArrayList();
			list.add(new Integer(1000));
			valuesToDiplayMap.put(new Double(0.1), list);
			
			//scale 0.2
			rulersRangesMap.put(new Double(0.2), new Integer(50));
			list=new ArrayList();
			list.add(new Integer(200));
			valuesToDiplayMap.put(new Double(0.2), list);
			
			//scale 0.5
			rulersRangesMap.put(new Double(0.5), new Integer(10));
			list=new ArrayList();
			list.add(new Integer(100));
			valuesToDiplayMap.put(new Double(0.5), list);
			
			//scale 0.75
			rulersRangesMap.put(new Double(0.75), new Integer(10));
			list=new ArrayList();
			list.add(new Integer(100));
			valuesToDiplayMap.put(new Double(0.75), list);

			//scale 1.0
			rulersRangesMap.put(new Double(1.0), new Integer(5));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			valuesToDiplayMap.put(new Double(1.0), list);
			
			//scale 1.25
			rulersRangesMap.put(new Double(1.25), new Integer(5));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			valuesToDiplayMap.put(new Double(1.25), list);
			
			//scale 1.5
			rulersRangesMap.put(new Double(1.5), new Integer(5));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			valuesToDiplayMap.put(new Double(1.5), list);
						
			//scale 2.0
			rulersRangesMap.put(new Double(2.0), new Integer(2));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(20));
			valuesToDiplayMap.put(new Double(2.0), list);					

			//scale 4.0
			rulersRangesMap.put(new Double(4.0), new Integer(1));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			list.add(new Integer(10));
			valuesToDiplayMap.put(new Double(4.0), list);	
			
			//scale 5.0
			rulersRangesMap.put(new Double(5.0), new Integer(1));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			list.add(new Integer(10));
			valuesToDiplayMap.put(new Double(5.0), list);	
			
			//scale 8.0
			rulersRangesMap.put(new Double(8.0), new Integer(1));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			list.add(new Integer(10));
			list.add(new Integer(5));
			valuesToDiplayMap.put(new Double(8.0), list);
			
			//scale 10.0
			rulersRangesMap.put(new Double(10.0), new Integer(1));
			list=new ArrayList();
			list.add(new Integer(100));
			list.add(new Integer(50));
			list.add(new Integer(10));
			list.add(new Integer(5));
			valuesToDiplayMap.put(new Double(10.0), list);	
			
		}

		/**
		 * paints the panel
		 * @param g the graphics object
		 */
		public void paintComponent(Graphics g) {
			
			super.paintComponents(g);
			
			if(rulersEnabled){
				Graphics2D g2=(Graphics2D)g;
			
				//gets the values that will be used to draw the rulers
				double scale=canvas.getScale();
				Double dScale=new Double(scale);
				Integer iRange=null;
				ArrayList list=null;

				try{
					iRange=(Integer)rulersRangesMap.get(dScale);
					list=(ArrayList)valuesToDiplayMap.get(dScale);
				}catch (Exception ex){
          iRange=null; 
          list=null;
        }

				int range=5;
				int[] valuesToDisplay=new int[0];

				if(iRange!=null && list!=null){
					range=iRange.intValue();
					valuesToDisplay=new int[list.size()];
					for(int i=0;i<list.size();i++){
						try{
							valuesToDisplay[i]=((Integer)list.get(i)).intValue();
						}catch (Exception ex){}
					}
				}

				g.setColor(getBackground());
				g.fillRect(0,0,this.getWidth(), this.getHeight());
				g.setFont(rulersFont);

				int begin=0;
        int end=0;
        int valueMin=0;
        int valueMax=0;
        int mousePos=0;
        int valueMouse=0;

				if(isVertical){
				
					//computes the values for the vertical ruler
					begin=canvasBounds.y;
          if(canvasBounds.y!=0){
            end=getHeight();
            valueMin=0;
            valueMax=(int)(canvasBounds.height/scale);
            mousePos=mousePosition.y+canvasBounds.y;
            valueMouse=(int)(mousePosition.y/scale);
          }else{
            end=begin+canvasBounds.height;
            valueMin=(int)(-translateValues.height/scale);
            valueMax=(int)((svgPanel.getSize().height-translateValues.height)/scale);
            mousePos=mousePosition.y;
            valueMouse=(int)((mousePosition.y-translateValues.height)/scale);
          } 

					//draws the bounds and the line representing the position of the mouse
					g.setColor(Color.black);
					g.drawLine(0, begin, getWidth(), begin);
					g.drawLine(0, end, getWidth(), end);
			
					g.setColor(Color.red);
					g.drawLine(0, mousePos, getWidth(), mousePos);
				
					//the interval that corresponds to the space between the minValue and the first drawned line
					int before=(range-valueMin%range)%range;
					int pos=0;

					//draws the rulers
					g.setColor(Color.black);
					int realY=0;
					
          int i=0;			
					int j=0; 
          int k=0;
          int l=0;

					int ratio=6;
          int length=(int)(getWidth()/ratio);
					AttributedString as=null;
					String number="";
				
					while(pos<end){
						//draws each line of the ruler
						pos=begin+(int)Math.round(before*scale)+(int)Math.floor(i*range*scale);
						realY=(int)(((pos-begin)-translateValues.height)/scale);
						if(realY%range==1)pos=begin+(int)Math.round(before*scale)+(int)Math.round(i*range*scale-0.5);
						else if(realY%range==(range-1))pos=begin+(int)Math.round(before*scale)+(int)Math.floor(i*range*scale+0.5);
						realY=(int)(((pos-begin)-translateValues.height)/scale);
					
						//display a longer line according to the value the line is associated with
						for(j=0;j<valuesToDisplay.length;j++){
							if(realY%(valuesToDisplay[j])==0){

								number=""+realY;
								l=1;
								for(k=0;k<number.length();k++){
									g.drawString(""+number.charAt(k), 2, pos+l*rulersFont.getSize());
									l++;
								}

								break;
							}
						}
					
						i++;
					
						//draws the line
						g.drawLine(getWidth()-((ratio-j)+1)*length/2, pos, getWidth(), pos);
					}
				
				}else{
					
					//computes the values for the horizontal ruler
					begin=canvasBounds.x;
          if(canvasBounds.x!=0){
            end=begin+canvasBounds.width; 
            valueMin=0;
            valueMax=(int)(canvasBounds.width/scale);			
            mousePos=mousePosition.x+canvasBounds.x;
            valueMouse=(int)(mousePosition.x/scale);
          }else{
            end=getWidth(); 
            valueMin=(int)(-translateValues.width/scale);
            valueMax=(int)((svgPanel.getSize().width-translateValues.width)/scale);			
            mousePos=mousePosition.x;
            valueMouse=(int)((mousePosition.x-translateValues.width)/scale);
          }
			
					//draws the bounds and the line representing the position of the mouse
					g.setColor(Color.black);
					g.drawLine(begin, 0, begin, getHeight());
					g.drawLine(end, 0, end, getHeight());
			
					g.setColor(Color.red);
					g.drawLine(mousePos, 0, mousePos, getHeight());

					//the interval that corresponds to the space between the minValue and the first drawned line
					int before=(range-valueMin%range)%range;
					int pos=0;

					//draws the rulers
					g.setColor(Color.black);
					int realX=0;
          
					int i=0;			
					int j=0;
          
          int ratio=6;
          int length=(int)(getHeight()/ratio);

					while(pos<end){
						//draws each line of the ruler
						pos=begin+(int)Math.round(before*scale)+(int)Math.floor(i*range*scale);
						realX=(int)(((pos-begin)-translateValues.width)/scale);
						if(realX%range==1)pos=begin+(int)Math.round(before*scale)+(int)Math.round(i*range*scale-0.5);
						else if(realX%range==(range-1))pos=begin+(int)Math.round(before*scale)+(int)Math.floor(i*range*scale+0.5);
						realX=(int)(((pos-begin)-translateValues.width)/scale);
						
						//display a longer line according to the value the line is associated with
						for(j=0;j<valuesToDisplay.length;j++){
							if(realX%(valuesToDisplay[j])==0){
								g.drawString(""+realX, pos, 9);
								break;
							}
						}
					
						i++;
					
						//draws the line
						g.drawLine(pos, getHeight()-((ratio-j)+1)*length/2, pos, getHeight());
					}
				
				}
			}
		}
	}
  
  /**
	 * @author Sudheer V Pujar
	 *
	 * The class used to draw the grid
	 */
	protected class GridPainter implements SVGCanvas.PaintListener{
		
		/**
		 * a Double object representing a scale is associated to a Double representing a range along the x axis
		 */
		private Hashtable rangeXMap=new Hashtable();
		
		/**
		 * a Double object representing a scale is associated to a Double representing a range along the y axis
		 */
		private Hashtable rangeYMap=new Hashtable();
		
		/**
		 * the constructor of the class
		 */
		protected GridPainter(){
			
			//creates the maps for the range and the values to display
			
			ArrayList list=null;
			
			//scale 0.05
			rangeXMap.put(new Double(0.05), new Integer(1000));
			rangeYMap.put(new Double(0.05), new Integer(1000));
			
			//scale 0.1
			rangeXMap.put(new Double(0.1), new Integer(1000));
			rangeYMap.put(new Double(0.1), new Integer(1000));
			
			//scale 0.2
			rangeXMap.put(new Double(0.2), new Integer(500));
			rangeYMap.put(new Double(0.2), new Integer(500));
			
			//scale 0.5
			rangeXMap.put(new Double(0.5), new Integer(100));
			rangeYMap.put(new Double(0.5), new Integer(100));
			
			//scale 0.75
			rangeXMap.put(new Double(0.75), new Integer(100));
			rangeYMap.put(new Double(0.75), new Integer(100));

			//scale 1.0
			rangeXMap.put(new Double(1.0), new Integer(50));
			rangeYMap.put(new Double(1.0), new Integer(50));
			
			//scale 1.25
			rangeXMap.put(new Double(1.25), new Integer(50));
			rangeYMap.put(new Double(1.25), new Integer(50));
			
			//scale 1.5
			rangeXMap.put(new Double(1.5), new Integer(50));
			rangeYMap.put(new Double(1.5), new Integer(50));
						
			//scale 2.0
			rangeXMap.put(new Double(2.0), new Integer(20));
			rangeYMap.put(new Double(2.0), new Integer(20));					

			//scale 4.0
			rangeXMap.put(new Double(4.0), new Integer(10));
			rangeYMap.put(new Double(4.0), new Integer(10));
			
			//scale 5.0
			rangeXMap.put(new Double(5.0), new Integer(10));
			rangeYMap.put(new Double(5.0), new Integer(10));
			
			//scale 8.0
			rangeXMap.put(new Double(8.0), new Integer(10));
			rangeYMap.put(new Double(8.0), new Integer(10));
			
			//scale 10.0
			rangeXMap.put(new Double(10.0), new Integer(10));
			rangeYMap.put(new Double(10.0), new Integer(10));
			
			
		}
		
		/**
		 * the method called when the action is done
		 * @param g the graphics object
		 */
		public void paintToBeDone(Graphics g) {

			//computes the intervals between each line and draws the grid
			double scale=canvas.getScale();
			int width=canvasBounds.width;
      int height=canvasBounds.height;
			
			if(canvasBounds.x==0)width=svgPanel.getWidth();
			if(canvasBounds.y==0)height=svgPanel.getHeight();
			
			Double dScale=new Double(scale);
			Integer iRangeX=null;
      Integer iRangeY=null;

			try{
				iRangeX=(Integer)rangeXMap.get(dScale);
				iRangeY=(Integer)rangeYMap.get(dScale);
			}catch (Exception ex){iRangeX=null;iRangeY=null;}

			int rangeX=50;
      int rangeY=50;

			if(iRangeX!=null){
				rangeX=iRangeX.intValue();
			}
			
			if(iRangeY!=null){
				rangeY=iRangeY.intValue();
			}
			
			int initValueX=0;
      int initValueY=0;
			
			if(canvasBounds.x==0)initValueX=translateValues.width;
			if(canvasBounds.y==0)initValueY=translateValues.height;
			
			drawGrid(g, initValueX, initValueY, rangeX, rangeY, width, height);
		
		}
		
		/**
		 * draws the grid
		 * @param gr the graphics element
		 * @param initValueX the value from which to start drawing on the X axis
		 * @param initValueY the value from which to start drawing on the Y axis
		 * @param rangeX the space between each vertical line
		 * @param rangeY the space between each horizontal line
		 * @param width the width of the grid
		 * @param height the height of the grid
		 */
		protected void drawGrid(Graphics gr, int initValueX, int initValueY, int rangeX, int rangeY, int width, int height){
			Graphics2D g=(Graphics2D)gr;
			if(g!=null){
				double scale=canvas.getScale();
				
				Stroke stroke=g.getStroke();
				
				int realX=0;
				int i=0; 
        int pos=0;

				//draws the vertical lines
				while(pos<width){
					pos=initValueX+(int)Math.round(i*rangeX*scale+0.5);
					realX=(int)((pos-initValueX)/scale);
					if(realX%rangeX==1)pos=initValueX+(int)Math.round(i*rangeX*scale);
					else if(realX%rangeX==(rangeX-1))pos=initValueX+(int)Math.floor(i*rangeX*scale+1);
					realX=(int)((pos-initValueX)/scale);
					
					i++;
					
					//draws the line
					g.setColor(Color.darkGray);
					g.setStroke(stroke);
					g.drawLine(pos, 0, pos, height);
					
					g.setColor(Color.gray);
					g.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{2,2},0.0f));
					g.drawLine(pos, 0, pos, height);
				}
				
				int realY=0;
				i=0; 
        pos=0;
				
				//draws the horizontal lines
				while(pos<height){
					pos=initValueY+(int)Math.round(i*rangeY*scale+0.5);
					realY=(int)((pos-initValueY)/scale);
					if(realY%rangeY==1)pos=initValueY+(int)Math.round(i*rangeY*scale);
					else if(realY%rangeY==(rangeY-1))pos=initValueY+(int)Math.floor(i*rangeY*scale+1);
					realY=(int)((pos-initValueY)/scale);
					
					i++;
					
					//draws the line
					g.setColor(Color.darkGray);
					g.setStroke(stroke);
					g.drawLine(0, pos, width, pos);
					
					g.setColor(Color.gray);
					g.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{2,2},0.0f));
					g.drawLine(0, pos, width, pos);
				}
				
				g.setColor(Color.black);
				g.setStroke(stroke);
			}
		}
		
		/**
		 * modifies the ranges for a given scale
		 * @param scale a scale
		 * @param rangeX the range along the x axis
		 * @param rangeY the range along the y axis
		 */
		protected void setRanges(double scale, int rangeX, int rangeY){
			
			rangeXMap.put(new Double(scale), new Integer(rangeX));
			rangeYMap.put(new Double(scale), new Integer(rangeY));
			canvas.repaint();
		}
		
		/**
		 * @param scale a scale
		 * @return a Dimension object containig the rangs along the x-axis and the y-axis
		 */
		protected Dimension getRanges(double scale){
			
			int rangeX=50, rangeY=50;
			
			Double dScale=null;
			try{
				dScale=new Double(scale);
			}catch (Exception ex){dScale=null;}
			
			if(dScale!=null){
				Integer iRangeX=null, iRangeY=null;
	
				try{
					iRangeX=(Integer)rangeXMap.get(dScale);
					iRangeY=(Integer)rangeYMap.get(dScale);
				}catch (Exception ex){iRangeX=null;iRangeY=null;}
	
				if(iRangeX!=null){
					rangeX=iRangeX.intValue();
				}
				
				if(iRangeY!=null){
					rangeY=iRangeY.intValue();
				}
			}
			
			return new Dimension(rangeX, rangeY);
		}

	}
  
}