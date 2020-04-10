package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.engine.listeners.GridPaintListener;
import bulbul.foff.studio.engine.listeners.SBListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.w3c.dom.svg.SVGDocument;
 




/**
 * 
 * @author Sudheer V. Pujar
 * @version 1.0 17-Aug-2005
 * @description 
 */
public class SVGScrollPane extends JPanel  {
  private BorderLayout layout4This = new BorderLayout();
  private BorderLayout layout4Panel4HorizontalRuler = new BorderLayout();
  private BorderLayout layout4Panel4VerticalRuler = new BorderLayout();
  private BorderLayout layout4Panel4HorizontalScrollBar = new BorderLayout();
  private BorderLayout layout4Panel4VerticalScrollBar = new BorderLayout();
  private LayoutManager layout4Panel4ImagePanel = null;
  
  
  private JPanel panel4HorizontalRuler = new JPanel();
  private JPanel panel4VerticalRuler = new JPanel();
  private JPanel panel4VerticalScrollBar = new JPanel();
  private JPanel panel4HorizontalScrollBar = new JPanel();
  private JPanel panel4ImagePanel = new JPanel();
  
  private final HashTable  rulersRangesMap=new HashTable();
  
  private SVGRuler horizontalRuler=new SVGRuler(this,SVGRuler.HORIZONTAL );
  private SVGRuler verticalRuler=new SVGRuler(this,SVGRuler.VERTICAL);
  
  private JScrollBar verticalScrollBar = new JScrollBar();
  private JScrollBar horizontalScrollBar = new JScrollBar();
  
  private Component northEastCornerBox;
  private Component northWestCornerBox;
  private Component southWestCornerBox;
  private Component southEastCornerBox;
  
  private Studio studio;
  private SVGTab svgTab;
  private SVGCanvas svgCanvas;
  private ProductImagePanel imagePanel;
  private SVGScrollPane scrollPane=this;
  
  private SBListener  vsbListener;
  private SBListener  hsbListener;
  
  private GridPaintListener gridPaintListener ;
  
  private UpdateRenderingRunnable lastUpdateRenderingRunnable=null;
  
  private final Dimension imageTranslateValues=new Dimension(0,0); // Image Translate Values
  private final Dimension canvasTranslateValues=new Dimension(0,0); // canvas Translate Values
  private final Dimension  paintingValues=new Dimension(0,0);
  
  private final Rectangle renderedBounds=new Rectangle(0,0,0,0);
  private final Rectangle canvasBounds=new Rectangle(0,0,0,0);
  private final Rectangle imagePanelBounds=new Rectangle(0,0,0,0);

  private final Point mousePosition=new Point();
  
  private boolean gridVisible=false;
  private boolean svgModified=false;
  private boolean scrollChangeIgnored=false;
  private boolean alignWithRulersEnabled=false;
  private boolean rulersEnabled=true;
  
  /**
   * 
   * @param studio
   * @param svgTab
   * @param productImage
   * @description 
   */
  public SVGScrollPane(Studio studio,SVGTab svgTab, Image productImage) {
    try {

      this.studio=studio;
      this.svgTab=svgTab;
      this.svgCanvas= new SVGCanvas();
      this.imagePanel=new ProductImagePanel(this.studio,productImage);
      this.svgCanvas.init(studio, this);
      this.svgCanvas.displayWaitCursor();
      jbInit();
      otherInit();
      
      this.svgCanvas.returnToLastCursor();
      
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @throws java.lang.Exception
   * @description 
   */
  private void jbInit() throws Exception {
    setLayout(layout4This);
    setBackground(new Color(255,255,255,255));
    
    panel4HorizontalRuler.setLayout(layout4Panel4HorizontalRuler);
    
    panel4VerticalRuler.setLayout(layout4Panel4VerticalRuler);
    
    panel4VerticalScrollBar.setLayout(layout4Panel4VerticalScrollBar);
    
    panel4HorizontalScrollBar.setLayout(layout4Panel4HorizontalScrollBar);
    
    panel4ImagePanel.setLayout(layout4Panel4ImagePanel);
    
    verticalScrollBar.setOrientation(JScrollBar.VERTICAL);
    
    horizontalScrollBar.setOrientation(JScrollBar.HORIZONTAL);
    
    // Scroll Bar listeners
		hsbListener=new SBListener(this,SBListener.HORIZONTAL);
		horizontalScrollBar.addAdjustmentListener(hsbListener);

		vsbListener=new SBListener(this,SBListener.VERTICAL);
		verticalScrollBar.addAdjustmentListener(vsbListener);
    
    panel4HorizontalScrollBar.add(horizontalScrollBar, BorderLayout.CENTER);
    panel4VerticalScrollBar.add(verticalScrollBar, BorderLayout.CENTER);
    
    panel4HorizontalRuler.add(horizontalRuler, BorderLayout.CENTER);
    panel4VerticalRuler.add(verticalRuler, BorderLayout.CENTER);
    
    this.add(panel4HorizontalRuler, BorderLayout.NORTH);
    this.add(panel4VerticalRuler, BorderLayout.WEST);
    this.add(panel4VerticalScrollBar, BorderLayout.EAST);
    this.add(panel4HorizontalScrollBar, BorderLayout.SOUTH);
    this.add(panel4ImagePanel, BorderLayout.CENTER);
  }
  
  /**
   * 
   * @throws java.lang.Exception
   * @description 
   */
  private void otherInit() throws Exception {
    northEastCornerBox=Box.createRigidArea(new Dimension(verticalScrollBar.getPreferredSize().width, horizontalScrollBar.getPreferredSize().height));
    panel4HorizontalRuler.add(northEastCornerBox, BorderLayout.EAST);
    
    northWestCornerBox=Box.createRigidArea(new Dimension(verticalScrollBar.getPreferredSize().width, horizontalScrollBar.getPreferredSize().height));
    panel4HorizontalRuler.add(northWestCornerBox, BorderLayout.WEST);
    
    southEastCornerBox=Box.createRigidArea(new Dimension(verticalScrollBar.getPreferredSize().width, horizontalScrollBar.getPreferredSize().height));
    panel4HorizontalScrollBar.add(southEastCornerBox, BorderLayout.EAST);
    
    southWestCornerBox=Box.createRigidArea(new Dimension(verticalScrollBar.getPreferredSize().width, horizontalScrollBar.getPreferredSize().height));        
    panel4HorizontalScrollBar.add(southWestCornerBox, BorderLayout.WEST);
		
		//sets the the properties and adds the rulers to the panels
		verticalRuler.setPreferredSize(verticalScrollBar.getPreferredSize());
    
		horizontalRuler.setPreferredSize(horizontalScrollBar.getPreferredSize());
    
    //creates the paint listener to draw the grid
		gridPaintListener=new GridPaintListener(this);
    
    //Adding SVGDocumentLoaderListener
    final SVGDocumentLoaderAdapter documentLoaderAdapter=new SVGDocumentLoaderAdapter() {
		    
			public void documentLoadingStarted(SVGDocumentLoaderEvent e){
			}
			
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
        initScrollPane();
			}
		};
		this.svgCanvas.addSVGDocumentLoaderListener(documentLoaderAdapter);
    
    //Adding MouseWheelListener
    final MouseWheelListener mouseWheelListener=new MouseWheelListener(){
	        public void mouseWheelMoved(MouseWheelEvent evt) {
	            if(verticalScrollBar!=null){
	                if(evt.getWheelRotation()<0){
		                synchronized(this){verticalScrollBar.setValue(verticalScrollBar.getValue()-verticalScrollBar.getUnitIncrement());}
	                }else if(evt.getWheelRotation()>0){
	                  synchronized(this){verticalScrollBar.setValue(verticalScrollBar.getValue()+verticalScrollBar.getUnitIncrement());}
	                }
	            }
	        }
		};
    this.svgCanvas.addMouseWheelListener(mouseWheelListener);
		
    //Adding MouseMotionListener
		final MouseMotionAdapter mouseMotionAdapter=new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent evt) {
                if(evt.getPoint().x<renderedBounds.x){
                  synchronized(this){horizontalScrollBar.setValue(horizontalScrollBar.getValue()-(renderedBounds.x-evt.getPoint().x));}
                }else if(evt.getPoint().x>renderedBounds.x+renderedBounds.width){
                  synchronized(this){horizontalScrollBar.setValue(horizontalScrollBar.getValue()+evt.getPoint().x-(renderedBounds.x+renderedBounds.width));}
                }
                
                if(evt.getPoint().y<renderedBounds.y){
                  synchronized(this){verticalScrollBar.setValue(verticalScrollBar.getValue()-(renderedBounds.y-evt.getPoint().y));}
                }else if(evt.getPoint().y>renderedBounds.y+renderedBounds.height){
                  synchronized(this){verticalScrollBar.setValue(verticalScrollBar.getValue()+evt.getPoint().y-(renderedBounds.y+renderedBounds.height));}
                }
            }
		};
    this.svgCanvas.addMouseMotionListener(mouseMotionAdapter);
		
    
    //adds the listener of the resizement of the panel4ImagePanel
		final ComponentAdapter componentAdapter=new ComponentAdapter(){
		    
			public void componentResized(ComponentEvent evt){
			  changeBoundsAfterResize();
				resizeScrollBarsAfterResize();
			}
		};
		this.panel4ImagePanel.addComponentListener(componentAdapter);
    
    getSvgTab().addDisposeRunnable(new Runnable(){
        public void run() {
          rulersRangesMap.clear();
          svgCanvas.removeSVGDocumentLoaderListener(documentLoaderAdapter);
          svgCanvas.removeMouseWheelListener(mouseWheelListener);
          imagePanel.removeMouseWheelListener(mouseWheelListener);
          svgCanvas.removeMouseMotionListener(mouseMotionAdapter);
          horizontalScrollBar.removeAdjustmentListener(hsbListener);
          verticalScrollBar.removeAdjustmentListener(vsbListener);
          panel4ImagePanel.removeComponentListener(componentAdapter);
          panel4ImagePanel.removeAll();
          scrollPane.removeAll();
        }
		});
  }
  
  
  /**
   * 
   * @description 
   */
  private synchronized void changeBoundsAfterResize(){
    int imageTransformX=imageTranslateValues.width; 
    int imageTransformY=imageTranslateValues.height;
    
    int canvasTransformX=canvasTranslateValues.width; 
    int canvasTransformY=canvasTranslateValues.height;
    
    int imagePanelX=0;
    int imagePanelY=0;
    int imagePanelWidth=0;
    int imagePanelHeight=0;
    
    int canvasX=0;
    int canvasY=0;
    int canvasWidth=0;
    int canvasHeight=0;
    
    int renderedWidth=0;
    int renderedHeight=0;
    
    
    //storing the old canvas bounds
    Rectangle oldRenderedBounds=new Rectangle(renderedBounds);
    
		Dimension imagePanelSize=imagePanel.getScaledImagePanelSize();
    Dimension canvasSize=svgCanvas.getScaledCanvasSize();
    
    canvasWidth=canvasSize.width;
    canvasHeight=canvasSize.height;
    
    imagePanelWidth=imagePanelSize.width;
    imagePanelHeight=imagePanelSize.height;
    
		
		//computes the bound of the Image Panel
		if(imagePanelWidth<=panel4ImagePanel.getSize().width){
			imagePanelX=(int)(panel4ImagePanel.getSize().width/2-imagePanelWidth/2);
      imageTransformX=0;
		}else{
			imagePanelX=0;
		}
		
		if(imagePanelHeight<=panel4ImagePanel.getSize().height){
			imagePanelY=(int)(panel4ImagePanel.getSize().height/2-imagePanelHeight/2);
      imageTransformY=0;
		}else{
			imagePanelY=0;
		}
    
    //computes the bound of the SVG Canvas
    if(canvasWidth<=panel4ImagePanel.getSize().width) {
      canvasX=(int)(panel4ImagePanel.getSize().width/2-canvasWidth/2);
      renderedWidth=canvasWidth; 
      canvasTransformX=0;
    }else{
      canvasX=0;
      renderedWidth=panel4ImagePanel.getSize().width;
      if(renderedWidth>oldRenderedBounds.width){
        canvasTransformX=canvasTransformX+(renderedWidth-oldRenderedBounds.width);
			}
    }
    
    if(canvasHeight<=panel4ImagePanel.getSize().height){
      canvasY=(int)(panel4ImagePanel.getSize().height/2-canvasHeight/2);
      renderedHeight=canvasHeight; 
      canvasTransformY=0;
    }else{
      canvasY=0;
      renderedHeight=panel4ImagePanel.getSize().height;
      if(renderedHeight>oldRenderedBounds.height){
        canvasTransformY=canvasTransformY+(renderedHeight-oldRenderedBounds.height);
			}
    }
    
		
    if(canvasTransformX>0){
		  canvasTransformX=0;
		}else if(canvasTransformX<=-panel4ImagePanel.getSize().width){
		  canvasTransformX=-panel4ImagePanel.getSize().width;
		}
		
		if(canvasTransformY>0){
		  canvasTransformY=0;
		}else if(canvasTransformY<=-panel4ImagePanel.getSize().height){
		  canvasTransformY=-panel4ImagePanel.getSize().height;
		}
    
    //the rectangle to be rendered
    renderedBounds.x=0;
		renderedBounds.y=0;
		renderedBounds.width=renderedWidth;
		renderedBounds.height=renderedHeight;
    
    //the bounds of the ImagePanel
    imagePanelBounds.x=imagePanelX;
		imagePanelBounds.y=imagePanelY;
		imagePanelBounds.width=imagePanelWidth;
		imagePanelBounds.height=imagePanelHeight;
    
    imagePanel.setBounds(imagePanelBounds); 
    
		//the bounds of the canvas
		canvasBounds.x=canvasX;
		canvasBounds.y=canvasY;
		canvasBounds.width=canvasWidth;
		canvasBounds.height=canvasHeight;
		
		svgCanvas.setBounds(canvasBounds);

		//the new translate values
		imageTranslateValues.width=imageTransformX;
		imageTranslateValues.height=imageTransformY;
		
    canvasTranslateValues.width=canvasTransformX;
		canvasTranslateValues.height=canvasTransformY;
		
		paintingValues.width=0;
		paintingValues.height=0;

		//sets the scrolling values
		hsbListener.setStartValue(-canvasTransformX);
		vsbListener.setStartValue(-canvasTransformY);
		
		panel4ImagePanel.revalidate();
			
		// sets the rendering transform for imagePanel
    AffineTransform affineTransform4ImagePanel=AffineTransform.getTranslateInstance(imageTranslateValues.width, imageTranslateValues.height);
		affineTransform4ImagePanel.concatenate(AffineTransform.getScaleInstance(imagePanel.getScale(), imagePanel.getScale()));
		imagePanel.setAffineTransform(affineTransform4ImagePanel);
    
    // sets the rendering transform for svgCanvas
    AffineTransform affineTransform4SvgCanvas=AffineTransform.getTranslateInstance(canvasTranslateValues.width, canvasTranslateValues.height);
		affineTransform4SvgCanvas.concatenate(AffineTransform.getScaleInstance(svgCanvas.getScale(), svgCanvas.getScale()));
		svgCanvas.setRenderingTransform(affineTransform4SvgCanvas);
    updateRendering();
  }
  
  /**
   * 
   * @description 
   */
  private synchronized void resizeScrollBarsAfterResize(){
  
    scrollChangeIgnored=true;
		Dimension canvasSize=svgCanvas.getScaledCanvasSize();

		//the values of the scrollbars
		int maxWidth=canvasSize.width;
		int maxHeight=canvasSize.height;
		int	minWidth=0;
		int minHeight=0;
		int extentImageTransformX=panel4ImagePanel.getSize().width;
		int extentImageTrasformY=panel4ImagePanel.getSize().height;

		verticalScrollBar.setValues(vsbListener.getStartValue(), extentImageTrasformY, minHeight, maxHeight);
		horizontalScrollBar.setValues(hsbListener.getStartValue(), extentImageTransformX,  minWidth, maxWidth);

		//enables or disables the scrollbars
		horizontalScrollBar.setEnabled(canvasBounds.width>panel4ImagePanel.getSize().width);
		verticalScrollBar.setEnabled(canvasBounds.height>panel4ImagePanel.getSize().height);
   
		//sets the increments
		verticalScrollBar.setBlockIncrement((int)(0.9*canvasSize.width));
		horizontalScrollBar.setBlockIncrement((int)(0.9*canvasSize.width));

		verticalScrollBar.setUnitIncrement((int)(0.1*canvasSize.width));
		horizontalScrollBar.setUnitIncrement((int)(0.1*canvasSize.width));

		scrollChangeIgnored=false;
  }
  /**
   * 
   * @description 
   * @param transformY
   * @param transformX
   */
  private synchronized void changeBounds(int imageTransformX, int imageTransformY,int canvasTransformX, int canvasTransformY){
    
    int imagePanelX=0;
    int imagePanelY=0;
    int imagePanelWidth=0;
    int imagePanelHeight=0;
    
    int canvasX=0;
    int canvasY=0;
    int canvasWidth=0;
    int canvasHeight=0;
    
    int renderedWidth=0;
    int renderedHeight=0;
   
    //storing the old canvas bounds
		Dimension imagePanelSize=imagePanel.getScaledImagePanelSize();
    Dimension canvasSize=svgCanvas.getScaledCanvasSize();

    canvasWidth=canvasSize.width;
    canvasHeight=canvasSize.height;
    
    imagePanelWidth=imagePanelSize.width;
    imagePanelHeight=imagePanelSize.height;
    
		//computes the bound of the Image Panel
		if(imagePanelWidth<=panel4ImagePanel.getSize().width){
			imagePanelX=(int)(panel4ImagePanel.getSize().width/2-imagePanelWidth/2);
      imageTransformX=0;
		}else{
			imagePanelX=0;
		}
		
		if(imagePanelHeight<=panel4ImagePanel.getSize().height){
			imagePanelY=(int)(panel4ImagePanel.getSize().height/2-imagePanelHeight/2);
      imageTransformY=0;
		}else{
			imagePanelY=0;
		}
    
    if(canvasWidth<=panel4ImagePanel.getSize().width){
      canvasX=(int)(panel4ImagePanel.getSize().width/2-canvasWidth/2);
      canvasTransformX=0;
      renderedWidth=canvasWidth; 
    }else{
      canvasX=0;
      renderedWidth=panel4ImagePanel.getSize().width;
    }
    
    
    if(canvasHeight<=panel4ImagePanel.getSize().height){
      canvasY=(int)(panel4ImagePanel.getSize().height/2-canvasHeight/2);
      canvasTransformY=0;
      renderedHeight=canvasHeight; 
    }else{
      canvasY=0;
      renderedHeight=panel4ImagePanel.getSize().height;
    }
    
    //the rectangle to be rendered
		renderedBounds.x=0;
		renderedBounds.y=0;
		renderedBounds.width=renderedWidth;
		renderedBounds.height=renderedHeight;
    
    //the bounds of the ImagePanel
    imagePanelBounds.x=imagePanelX;
		imagePanelBounds.y=imagePanelY;
		imagePanelBounds.width=imagePanelWidth;
		imagePanelBounds.height=imagePanelHeight;
    
    imagePanel.setBounds(imagePanelBounds); 
    
		//the bounds of the canvas
		canvasBounds.x=canvasX;
		canvasBounds.y=canvasY;
		canvasBounds.width=canvasWidth;
		canvasBounds.height=canvasHeight;
		
		svgCanvas.setBounds(canvasBounds);

		//the new translate values
		imageTranslateValues.width=imageTransformX;
		imageTranslateValues.height=imageTransformY;
    
    canvasTranslateValues.width=canvasTransformX;
		canvasTranslateValues.height=canvasTransformY;

		//sets the scrolling values
		hsbListener.setStartValue(canvasTransformX);
		vsbListener.setStartValue(canvasTransformY);
		
			
		// sets the rendering transform for imagePanel
    AffineTransform affineTransform4ImagePanel=AffineTransform.getTranslateInstance(imageTranslateValues.width, imageTranslateValues.height);
		affineTransform4ImagePanel.concatenate(AffineTransform.getScaleInstance(imagePanel.getScale(), imagePanel.getScale()));
		imagePanel.setAffineTransform(affineTransform4ImagePanel);
    
    // sets the rendering transform for svgCanvas
    AffineTransform affineTransform4SvgCanvas=AffineTransform.getTranslateInstance(canvasTranslateValues.width, canvasTranslateValues.height);
		affineTransform4SvgCanvas.concatenate(AffineTransform.getScaleInstance(svgCanvas.getScale(), svgCanvas.getScale()));
		svgCanvas.setRenderingTransform(affineTransform4SvgCanvas);
    
    updateRendering();
  }
  
  /**
   * 
   * @description 
   * @param vsPosition
   * @param hsPosition
   */
  private synchronized void resizeScrollBars(int hsPosition, int vsPosition){
    scrollChangeIgnored=true;
		Dimension canvasSize=svgCanvas.getScaledCanvasSize();

		//thevalues of the scrollbars
		int maxWidth=canvasSize.width;
		int maxHeight=canvasSize.height;
		int minWidth=0;
		int minHeight=0;
		int extentImageTransformX=panel4ImagePanel.getSize().width;
		int extentImageTrasformY=panel4ImagePanel.getSize().height;

		//sets the values
		hsbListener.setStartValue(hsPosition);
		vsbListener.setStartValue(vsPosition);
    
		horizontalScrollBar.setValue(hsPosition);
		verticalScrollBar.setValue(vsPosition);
		
    horizontalScrollBar.setValues(hsPosition, extentImageTransformX,  minWidth, maxWidth);
		verticalScrollBar.setValues(vsPosition,extentImageTrasformY, minHeight, maxHeight);
		
		//enables or disables the scrollbars
		horizontalScrollBar.setEnabled(canvasBounds.width>panel4ImagePanel.getSize().width);
    verticalScrollBar.setEnabled(canvasBounds.height>panel4ImagePanel.getSize().height);
		    
		//sets the increments
		verticalScrollBar.setBlockIncrement((int)(0.9*canvasSize.width));
		horizontalScrollBar.setBlockIncrement((int)(0.9*canvasSize.width));

		verticalScrollBar.setUnitIncrement((int)(0.1*canvasSize.width));
		horizontalScrollBar.setUnitIncrement((int)(0.1*canvasSize.width));

		scrollChangeIgnored=false;
  }


 /**
   * 
   * @description 
   */
  public void initScrollPane(){
    
    this.panel4ImagePanel.add(this.svgCanvas);
    this.panel4ImagePanel.add(this.imagePanel);
    
    //used to convert numbres to strings
		DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		final DecimalFormat format=new DecimalFormat("######",symbols);
    
    //sets the mouse listeners for the canvas
		final MouseMotionListener mouseMotionListener=new MouseMotionListener(){
			protected void displayCoordinates(double x ,double y){
				mousePosition.x=(int)x;
				mousePosition.y=(int)y;
				
				//updates the display of the mouse coordinates
        studio.getMainStatusBar().setX(format.format(Math.floor((x-imageTranslateValues.width)/svgCanvas.getScale()))); 
        studio.getMainStatusBar().setY(format.format(Math.floor((y-imageTranslateValues.height)/svgCanvas.getScale()))); 
				
				horizontalRuler.repaint();
				verticalRuler.repaint();	
			}
			
			public void mouseDragged(MouseEvent e){
				displayCoordinates(e.getX(),e.getY());
			}
			
			public void mouseMoved(MouseEvent e){
				displayCoordinates(e.getX(),e.getY());
			}
		};
		svgCanvas.addMouseMotionListener(mouseMotionListener);
		
		final MouseAdapter mouseAdapter=new MouseAdapter(){
			public void mouseExited(MouseEvent e){
				studio.getMainStatusBar().setX("");
				studio.getMainStatusBar().setY("");				
			}
		};
		svgCanvas.addMouseListener(mouseAdapter);
		
		//adds a dispose runnable
		getSvgTab().addDisposeRunnable(new Runnable(){
        public void run() {
          svgCanvas.removeMouseMotionListener(mouseMotionListener);
          svgCanvas.removeMouseListener(mouseAdapter);
          //gridDialog.getContentPane().removeAll();
          //gridDialog.dispose();
        }
		});
    
    changeBounds(0,0,0,0);
		resizeScrollBars(0,0);
    
    repaint();
  }
  
  /**
   * 
   * @description 
   */
  public void updateRendering(){
    //enqueueing an update rendering runnable to update the rendering of the canvas
		if(svgCanvas.getUpdateManager()!=null  
		        && (lastUpdateRenderingRunnable==null 
                  || lastUpdateRenderingRunnable.isAlreadyExecuted())){
      UpdateRenderingRunnable runnable=new UpdateRenderingRunnable(){
        public void run() {
          super.run();
          //computing the area of insterest
          Rectangle rectangle=getRenderedBounds();
          AffineTransform affineTransform=null;
          AffineTransform renderingTransform=svgCanvas.getRenderingTransform();
          try{
            affineTransform=renderingTransform.createInverse();
            if(affineTransform!=null){
              rectangle=affineTransform.createTransformedShape(rectangle).getBounds();
            }
          }catch (Exception ex){affineTransform=null;}
          
          //updating the canvas
          imagePanel.repaint();
          svgCanvas.getUpdateManager().updateRendering(
                  renderingTransform, true, rectangle, 
                  (int)rectangle.getWidth(), (int)rectangle.getHeight());
                  
        }
      };
			synchronized(scrollPane){
        lastUpdateRenderingRunnable=runnable;
        svgTab.enqueue(runnable);
			}
		}
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Dimension getTranslateValues(){
    return new Dimension(canvasTranslateValues.width-paintingValues.width, canvasTranslateValues.height-paintingValues.height);
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Point getRulersRange(){
    double scale=getSvgCanvas().getScale();
		//the range of the rulers
		int range=5;
		Integer iRange=null;
		try{
			iRange=(Integer)rulersRangesMap.get(new Double(scale));
		}catch (Exception ex){iRange=null;}
		if(iRange!=null){
		    range=iRange.intValue();
		}
		return new Point(range, range);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param point
   */
  public Point getAlignedWithRulersPoint(Point point){
    if(point!=null){
			Point2D.Double point2D=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(point.x, point.y), true);
			if(alignWithRulersEnabled){
				//the range of the rulers
				int range=5;
				Integer iRange=null;

				try{
					iRange=(Integer)rulersRangesMap.get(new Double(getSvgCanvas().getScale()));
				}catch (Exception ex){iRange=null;}
				
        if(iRange!=null)range=iRange.intValue();

				//computes the new point values
				if(point2D!=null && range!=0){
					int x=(int)Math.floor(point2D.x/range);
					int y=(int)Math.floor(point2D.y/range);
					point2D=new Point2D.Double(x*range, y*range);
					if(point2D!=null){
						return new Point((int)point2D.x, (int)point2D.y);
					}
				}
			}else{
				return new Point((int)point2D.x, (int)point2D.y);
			}
		}
		
		return null;
  }
    
  /**
   * 
   * @description 
   * @param image
   * @param svgDocument
   */
  public void changeOrientation(SVGDocument svgDocument, Image  image){
  
  }
  
  /**
   * 
   * @description 
   * @param g
   */
  public void paintComponent(Graphics g){
    super.paintComponent(g);
		this.svgCanvas.setBounds(canvasBounds);
    this.imagePanel.setBounds(imagePanelBounds);
  }
  
  /**
   * 
   * @description 
   * @param zoomScale
   */
  public void renderZoom(double zoomScale){
    //sets the new scale for the image panel
    imagePanel.setScale(zoomScale);
		
    int imageTransformX=imageTranslateValues.width;
    int imageTransformY=imageTranslateValues.height;
		
		//the translation value to be recentered on the point the scrollpane was centered before the canvas was scaled
		int scaledImageTransformX=(int)((-imageTransformX+panel4ImagePanel.getSize().width/2)*(zoomScale/imagePanel.getLastScale())-panel4ImagePanel.getSize().width/2);
		int	scaledImageTransformY=(int)((-imageTransformY+panel4ImagePanel.getSize().height/2)*(zoomScale/imagePanel.getLastScale())-panel4ImagePanel.getSize().height/2);
    
		if((scaledImageTransformX+panel4ImagePanel.getSize().width)>imagePanel.getScaledImagePanelSize().width){
			scaledImageTransformX=imagePanel.getScaledImagePanelSize().width-panel4ImagePanel.getSize().width;
		}else if(scaledImageTransformX<0){
			scaledImageTransformX=0;
		}

		if((scaledImageTransformY+panel4ImagePanel.getSize().height)>imagePanel.getScaledImagePanelSize().height){
			scaledImageTransformY=imagePanel.getScaledImagePanelSize().height-panel4ImagePanel.getSize().height;
		}else if(scaledImageTransformY<0){
			scaledImageTransformY=0;
		}
    
    //sets the new scale for the canvas
    svgCanvas.setScale(zoomScale);
    
    int canvasTransformX=canvasTranslateValues.width;
    int canvasTransformY=canvasTranslateValues.height;
    
    //the translation value to be recentered on the point the scrollpane was centered before the canvas was scaled
    int scaledCanvasTransformX=(int)((-canvasTransformX+panel4ImagePanel.getSize().width/2)*(zoomScale/svgCanvas.getLastScale())-panel4ImagePanel.getSize().width/2);
		int	scaledCanvasTransformY=(int)((-canvasTransformY+panel4ImagePanel.getSize().height/2)*(zoomScale/svgCanvas.getLastScale())-panel4ImagePanel.getSize().height/2);

    if((scaledCanvasTransformX+panel4ImagePanel.getSize().width)>svgCanvas.getScaledCanvasSize().width){
			scaledCanvasTransformX=svgCanvas.getScaledCanvasSize().width-panel4ImagePanel.getSize().width;
		}else if(scaledCanvasTransformX<0){
			scaledCanvasTransformX=0;
		}

		if((scaledCanvasTransformY+panel4ImagePanel.getSize().height)>svgCanvas.getScaledCanvasSize().height){
			scaledCanvasTransformY=svgCanvas.getScaledCanvasSize().height-panel4ImagePanel.getSize().height;
		}else if(scaledCanvasTransformY<0){
			scaledCanvasTransformY=0;
		}
		
		//modifies the bounds and the renderig transform
		changeBounds(-scaledImageTransformX, -scaledImageTransformY, -scaledCanvasTransformX,-scaledCanvasTransformY);
    imagePanel.repaint(); 
    svgCanvas.immediateRepaint();
		resizeScrollBars(scaledCanvasTransformX , scaledCanvasTransformY);
  }

  /**
   * 
   * @return 
   * @description 
   */
  public Studio getStudio() {
    return studio;
        
  }


  /**
   * 
   * @return 
   * @description 
   */
  public SVGTab getSvgTab() {
    return svgTab;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public SVGCanvas getSvgCanvas() {
    return svgCanvas;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public ProductImagePanel getImagePanel() {
    return imagePanel;
  }
  
  /**
   * 
   * @return 
   * @description 
   */
  public Rectangle getRenderedBounds() {
    return this.renderedBounds;
  }

  /**
   * 
   * @param translateValues
   * @description 
   */
  public void setCanvasTranslateValues(Dimension canvasTranslateValues) {
    this.canvasTranslateValues.setSize(canvasTranslateValues.getSize()); 
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Dimension getCanvasTranslateValues() {
    return this.canvasTranslateValues;
  }

  /**
   * 
   * @param translateValues
   * @description 
   */
  public void setImageTranslateValues(Dimension imageTranslateValues) {
    this.imageTranslateValues.setSize(imageTranslateValues.getSize()); 
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Dimension getImageTranslateValues() {
    return this.imageTranslateValues;
  }


  /**
   * 
   * @param canvaseBounds
   * @description 
   */
  public void setCanvasBounds(Rectangle canvasBounds) {
    this.canvasBounds.x = canvasBounds.x;
    this.canvasBounds.y = canvasBounds.y;
    this.canvasBounds.width = canvasBounds.width;
    this.canvasBounds.height = canvasBounds.height;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Rectangle getCanvasBounds() {
    return this.canvasBounds;
  }


  /**
   * 
   * @param paintingValues
   * @description 
   */
  public void setPaintingValues(Dimension paintingValues) {
    this.paintingValues.setSize(paintingValues.getSize()); 
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Dimension getPaintingValues() {
    return this.paintingValues;
  }


  /**
   * 
   * @param mousePosition
   * @description 
   */
  public void setMousePosition(Point mousePosition) {
    this.mousePosition.x = mousePosition.x;
    this.mousePosition.y = mousePosition.y;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Point getMousePosition() {
    return this.mousePosition;
  }


  /**
   * 
   * @param imagePanelBounds
   * @description 
   */
  public void setImagePanelBounds(Rectangle imagePanelBounds) {
    this.imagePanelBounds.x = imagePanelBounds.x;
    this.imagePanelBounds.y = imagePanelBounds.y;
    this.imagePanelBounds.width = imagePanelBounds.width;
    this.imagePanelBounds.height = imagePanelBounds.height;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public Rectangle getImagePanelBounds() {
    return this.imagePanelBounds;
  }


  /**
   * 
   * @param gridVisible
   * @description 
   */
  public void setGridVisible(boolean gridVisible) {
    if(this.gridVisible && ! gridVisible){
			svgCanvas.removePaintListener(gridPaintListener, true);
		}else if(!this.gridVisible && gridVisible){
			svgCanvas.addGridLayerPaintListener(gridPaintListener, true);
		}
    svgCanvas.repaint();
    this.gridVisible = gridVisible;
  }


  /**
   * 
   * @param svgModified
   * @description 
   */
  public void setSvgModified(boolean svgModified) {
    this.svgModified = svgModified;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public boolean isSvgModified() {
    return this.svgModified;
  }


  /**
   * 
   * @param alignWithRulersEnabled
   * @description 
   */
  public void setAlignWithRulersEnabled(boolean alignWithRulersEnabled) {
    this.alignWithRulersEnabled = alignWithRulersEnabled;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public boolean isAlignWithRulersEnabled() {
    return this.alignWithRulersEnabled;
  }


  /**
   * 
   * @param rulersEnabled
   * @description 
   */
  public void setRulersEnabled(boolean rulersEnabled) {
    //disables the rulers
		if(this.rulersEnabled && ! rulersEnabled){
			remove(panel4HorizontalRuler); 
			remove(panel4VerticalRuler); 
			panel4HorizontalScrollBar.remove(southWestCornerBox);
			
			changeBounds(imageTranslateValues.width, imageTranslateValues.height,canvasTranslateValues.width,canvasTranslateValues.height  );
			resizeScrollBars(canvasTranslateValues.width, canvasTranslateValues.height);

			doLayout();
			
		//enables the rulers
		}else if(!this.rulersEnabled && rulersEnabled){
		    
			add(panel4HorizontalRuler, BorderLayout.NORTH);
			add(panel4VerticalRuler, BorderLayout.WEST);
			panel4HorizontalScrollBar.add(southWestCornerBox, BorderLayout.WEST);
			
			panel4ImagePanel.repaint();

			changeBounds(imageTranslateValues.width, imageTranslateValues.height,canvasTranslateValues.width,canvasTranslateValues.height);
			resizeScrollBars(canvasTranslateValues.width, canvasTranslateValues.height);

			verticalRuler.repaint();
			horizontalRuler.repaint();

			doLayout();
		}
    this.rulersEnabled=rulersEnabled;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public boolean areRulersEnabled() {
    return this.rulersEnabled;
  }
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isGridVisible() {
    return gridVisible;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isScrollChangeIgnored() {
    return scrollChangeIgnored;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public JScrollBar getVerticalScrollBar() {
    return verticalScrollBar;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JScrollBar getHorizontalScrollBar() {
    return horizontalScrollBar;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public SVGRuler getHorizontalRuler() {
    return horizontalRuler;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGRuler getVerticalRuler() {
    return verticalRuler;
  }
  
  
  /**
   * 
   * @description 
   * @return 
   */
  public HashTable getRulersRangesMap() {
    return rulersRangesMap;
  }
  
  /**
   * 
   * @description 
   * @version 1.0 17-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class UpdateRenderingRunnable implements Runnable{
	    
    /**
     * whether the boolean has already been executed
     */
    private boolean alreadyExecuted=false;
	    
	  /**
     * 
     * @see java.lang.Runnable#run()
     * @description 
     */
    public void run() {
      synchronized(this){
        alreadyExecuted=true;
      }
    }

    /**
     * @return Returns the hasBeenExecuted.
     */
    public boolean isAlreadyExecuted() {
        return alreadyExecuted;
    }
    
	}
}