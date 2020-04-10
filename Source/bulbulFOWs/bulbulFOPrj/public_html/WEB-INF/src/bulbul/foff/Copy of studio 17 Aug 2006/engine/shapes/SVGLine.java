package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.selection.SVGSelectionSquare;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JToggleButton;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGLine extends SVGShape  {
  
  private String shapelinelabel="Line";
  private String shapelinetoolitemlabel="Line - Press \"CTRL\" for drawing horizontal, vertical and oblique (45 degrees) lines";
  
  private String shapelineundoredocreate="Create Line";
  private String shapelineundoredotranslate="Translate Line";
  private String shapelineundoredoresize="Resize Line";
  private String shapelineundoredorotate="Rotate Line";
  private String shapelineundoredomodifypoint="Modify Point";
  
  private String shapelinehelpcreate="Draw a line";

  private LineActionListener  lineAction=null;
  private DecimalFormat  format=null;
  private final SVGLine  svgLine=this;
  
  /**
   * 
   * @description 
   */
  public SVGLine(Studio studio) {
    super(studio);
    ids.put("id","line");
    
    try{
      labels.put("label", shapelinelabel);
      labels.put("toolitemlabel", shapelinetoolitemlabel);
      labels.put("undoredocreate", shapelineundoredocreate);
      labels.put("undoredotranslate", shapelineundoredotranslate);
      labels.put("undoredoresize", shapelineundoredoresize);
      labels.put("undoredomodifypoint", shapelineundoredomodifypoint);
      labels.put("undoredorotate", shapelineundoredorotate);
      labels.put("helpcreate", shapelinehelpcreate);
    }catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    lineAction=new LineActionListener();
    final ActionListener svgTabChangedListener=new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
					svgToolItem.setEnabled(true);
					//svgToolItem.setIcon(icon);
				}else{
					svgToolItem.setEnabled(false);
					//svgToolItem.setIcon(disabledIcon);
				}
				lineAction.reset();
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbLine();
    svgToolItem.addActionListener(lineAction);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param g
   * @param node
   */
  protected LinkedList drawModifyPointsSelection(SVGTab svgTab, Graphics g, Node node){
    LinkedList squareList=new LinkedList();
		Element elementNode=(Element)node;
		Graphics2D g2D=(Graphics2D)g;
		if(g2D!=null && node!=null){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			Element root=null;
			if(document!=null){
        root=document.getRootElement();
			}
			if(root!=null){
				int squareDistance=5;
				double x1=0;
        double y1=0;
        double x2=0;
        double y2=0;
				try{
					x1=new Double(elementNode.getAttributeNS(null,"x1")).doubleValue();
					y1=new Double(elementNode.getAttributeNS(null,"y1")).doubleValue();
					x2=new Double(elementNode.getAttributeNS(null,"x2")).doubleValue();
					y2=new Double(elementNode.getAttributeNS(null,"y2")).doubleValue();
				}catch (Exception ex){}

				//computes the transformed points
				BridgeContext bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
				
				if(bridgeContext!=null){
				    
					Point2D pt1=new Point2D.Double(), pt2=new Point2D.Double();
					GraphicsNode gnode=bridgeContext.getGraphicsNode(elementNode);
					
					if(gnode!=null){
					    
						AffineTransform affineTransform=gnode.getTransform();
						
						if(affineTransform!=null){
						    
							try{
								pt2=affineTransform.transform(new Point2D.Double(x2,y2), null);
								pt1=affineTransform.transform(new Point2D.Double(x1,y1), null);
							}catch (Exception e){}
							
							if(pt1!=null && pt2!=null){
							    
								x1=pt1.getX();
								y1=pt1.getY();
								x2=pt2.getX();
								y2=pt2.getY();
							}
						}
					}
				}
				
				//computes the coordinates of the selection squares
				Point2D.Double point1=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(x1,y1),false); 
				Point2D.Double point2=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(x2,y2),false);
			
				double scaledX1=point1.x;
        double scaledY1=point1.y;
        double scaledX2=point2.x;
        double scaledY2=point2.y; 
			
				int[] squareX=new int[2];
				int[] squareY=new int[2];
			
				double sin=(scaledY2-scaledY1)/(2*Math.sqrt(Math.pow((scaledX2-scaledX1)/2,2)+Math.pow((scaledY2-scaledY1)/2,2)));
				double cos=(scaledX2-scaledX1)/(2*Math.sqrt(Math.pow((scaledX2-scaledX1)/2,2)+Math.pow((scaledY2-scaledY1)/2,2)));

				//computes the coordinates of the two selection points
				squareX[0]=(int)(scaledX1+(-squareDistance/2*cos-squareDistance));
				squareX[1]=(int)(scaledX2+(squareDistance/2*cos-squareDistance));

				squareY[0]=(int)(scaledY1+(-squareDistance/2*sin-squareDistance));
				squareY[1]=(int)(scaledY2+(squareDistance/2*sin-squareDistance));
			
				//the ids of the selection points
				String[] types=new String[2];
				types[0]="Begin";
				types[1]="End";
			
				//the cursors of the selection points
				Cursor[] cursors=new Cursor[2];
				cursors[0]=new Cursor(Cursor.HAND_CURSOR);
				cursors[1]=new Cursor(Cursor.HAND_CURSOR);
			
				//draws the graphic elements for the selection
				Shape shape=null;
				GradientPaint gradient=null;

				for(int i=0;i<2;i++){
					if(getStudio().getSvgSelection()!=null){
						squareList.add(new SVGSelectionSquare(node, types[i], 
													new Rectangle2D.Double(squareX[i],squareY[i],2*squareDistance,2*squareDistance), 
													cursors[i]));
					}
						
					shape=getArrow(new Point(squareX[i]+squareDistance, squareY[i]+squareDistance), types[i], REGULAR_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(squareX[i], squareY[i], SQUARE_SELECTION_COLOR1, squareX[i]+2*squareDistance, squareY[i]+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
				}				
			}
		}	
		
		return squareList;	
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param g
   * @param node   
   */
  protected LinkedList drawRotateSelection(SVGTab svgTab, Graphics g, Node node){
    LinkedList squarelist=new LinkedList();
		Graphics2D g2D=(Graphics2D)g;
		if(g2D!=null && node!=null){
			int squareDistance=5;
			Rectangle2D rectangle2D=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			if(rectangle2D!=null){
				//computes and draws the new awt line to be displayed
				Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
				Rectangle2D.Double scaledRectangle2DD=getStudio().getSvgToolkit().getScaledRectangle(svgTab, rectangle2DD, false);
				double scaledX=scaledRectangle2DD.getX();
        double scaledY=scaledRectangle2DD.getY();
				//the coordinates of the selection point
				int[] squareX=new int[1];
				int[] squareY=new int[1];

				squareX[0]=(int)(scaledX+scaledRectangle2DD.getWidth()/2)-squareDistance;
				squareY[0]=(int)(scaledY+scaledRectangle2DD.getHeight()/2)-squareDistance;
			
				//the id for the selection point
				String[] types=new String[1];
				types[0]="C";
			
				//the cursor associated with the selection point
				Cursor[] cursors=new Cursor[1];
				cursors[0]=new Cursor(Cursor.HAND_CURSOR);
			
				//draws the graphic elements
				Shape shape=null;
				GradientPaint gradient=null;
				
				for(int i=0;i<1;i++){
					if(getStudio().getSvgSelection()!=null){
						squarelist.add(	new SVGSelectionSquare(node,types[i],
													new Rectangle2D.Double(squareX[i],squareY[i],2*squareDistance,2*squareDistance),
													cursors[i]));
					}		
					shape=getArrow(new Point(squareX[i]+squareDistance, squareY[i]+squareDistance), types[i], ROTATE_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(squareX[i], squareY[i], SQUARE_SELECTION_COLOR1, squareX[i]+2*squareDistance, squareY[i]+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
				}
			}
		}
		
		return squarelist;
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param point1
   * @param point2
   */
  protected void drawLine(SVGTab svgTab, Point point1, Point point2){
    if(svgTab!=null && point1!=null && point2!=null){
		    
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			
			if(getStudio().getSvgSelection()!=null && document!=null){
			    
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				
				if(parent!=null){

					//creates the line
					Element line = document.createElementNS(document.getDocumentElement().getNamespaceURI(),"line");
					line.setAttributeNS(null,"x1", format.format(point1.x));
					line.setAttributeNS(null,"y1", format.format(point1.y));
					line.setAttributeNS(null,"x2", format.format(point2.x));
					line.setAttributeNS(null,"y2", format.format(point2.y));
					String colorString=getStudio().getColorChooser().getColorString(getStudio().getSvgColorManager().getCurrentColor());
					line.setAttributeNS(null, "style", "stroke:".concat(colorString.concat(";")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
			
					//creates final variables
					final Node finalLine=line;
					

					//attaches the circle to the svg root element
					parent.appendChild(finalLine);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){
								parent.removeChild(finalLine);
							}

							public void redo(){
								// attaches the circle to the svg root element
								parent.appendChild(finalLine);
							}
						};
				
						SVGSelection selection=getStudio().getSvgSelection();
				
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, line);
							selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction((String)labels.get("undoredocreate")){});
							selection.refreshSelection(svgTab);
						}else{
							SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredocreate"));
							actionlist.add(action);
							getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param g2D
   * @param point1
   * @param point2
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Point point1, Point point2){
    if(svgTab!=null && g2D!=null && point1!=null && point2!=null){
			//computes and draws the new awt rline to be displayed
			g2D.setXORMode(Color.white);
			g2D.setColor(GHOST_COLOR);
			g2D.drawLine(point1.x, point1.y, point2.x, point2.y);
		}		
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param square
   * @param point1
   * @param point2
   */
  public void modifyPoint(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && square.getNode() instanceof Element && point2!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			try{
				paintListener=(SVGPaintListener)modifyPointSvgTabTable.get(svgTab);
			}catch (Exception ex){paintListener=null;}
	
			//getting the node and the two points
			Element elementNode=(Element)square.getNode();
			double x1=0;
      double y1=0;
      double x2=0;
      double y2=0;
			
			try{
			    x1=Double.parseDouble(elementNode.getAttribute("x1"));
			    y1=Double.parseDouble(elementNode.getAttribute("y1"));
			    x2=Double.parseDouble(elementNode.getAttribute("x2"));
			    y2=Double.parseDouble(elementNode.getAttribute("y2"));
			}catch (Exception ex){}
			
			final double initX1=x1;
      final double initY1=y1;
      final double initX2=x2;
      final double initY2=y2;
			    
			//the matrix transform
			SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(elementNode);

			//the path
			GeneralPath path=new GeneralPath();
			path.moveTo((float)x1, (float)y1);
			path.lineTo((float)x2, (float)y2);
			
			//the transform
			AffineTransform affineTransform=null;
			
			//transforming the outline
			if(matrix!=null && matrix.getTransform()!=null){
				affineTransform=matrix.getTransform();
				try{
				    path=(GeneralPath)affineTransform.createTransformedShape(path);
				}catch (Exception ex){}

				//modifying the moved point
				PathIterator it=path.getPathIterator(new AffineTransform());
				double[] segment=new double[6];
				
				if(square.getType().equals("Begin")){
				    
				    //the first point
				    x1=point2.x;
				    y1=point2.y;
				    
				    //getting the second point
				    it.next();
				    it.currentSegment(segment);
				    x2=segment[0];
				    y2=segment[1];
				    
				}else if(square.getType().equals("End")){
				    
				    //getting the first point
				    it.currentSegment(segment);
				    x1=segment[0];
				    y1=segment[1];
				    
				    //the second point
				    x2=point2.x;
				    y2=point2.y;
				}
			}
			
			//storing the new coordinates values
			final double fx1=x1, fy1=y1, fx2=x2, fy2=y2;
			
			//the outline
			GeneralPath endPath=new GeneralPath();
			endPath.moveTo((float)x1, (float)y1);
			endPath.lineTo((float)x2, (float)y2);

			//computes the scale and translate values
			if(endPath!=null){

				//concatenates the transforms to draw the outline
				double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
				affineTransform=AffineTransform.getScaleInstance(scale, scale);
				
				Dimension translateValues=svgTab.getScrollPane().getTranslateValues();
				affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));

				//computing the outline
				final Shape finalOutline=affineTransform.createTransformedShape(endPath);
					
				//removes the paint listener
				if(paintListener!=null){
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
				}

				//creates and sets the paint listener
				paintListener=new SVGPaintListener(){
					public void paintToBeDone(Graphics g) {
						Graphics2D g2D=(Graphics2D)g;
						Color color=g2D.getColor();
						g2D.setColor(OUTLINE_FILL_COLOR);
						g2D.fill(finalOutline);
						g2D.setColor(OUTLINE_COLOR);
						g2D.draw(finalOutline);

						g2D.setColor(color);
					}
				};

			   svgTab.getScrollPane().getSvgCanvas().addDrawLayerPaintListener(paintListener, true);
			   modifyPointSvgTabTable.put(svgTab, paintListener);
			}
		}
  }

  /**
   * 
   * @description 
   * @param svgTab
   * @param square
   * @param point1
   * @param point2
   */
  public void validateModifyPoint(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    	if(svgTab!=null && square!=null && square.getNode()!=null && square.getNode() instanceof Element && point2!=null){
		    
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			SVGTab svgTab2=null;
			
			for(Iterator it=new LinkedList(modifyPointSvgTabTable.keySet()).iterator(); it.hasNext();){
				try{
				  svgTab2=(SVGTab)it.next();
					paintListener=(SVGPaintListener)modifyPointSvgTabTable.get(svgTab2);
				}catch (Exception ex){paintListener=null;}
				
				if(paintListener!=null){
				  modifyPointSvgTabTable.remove(svgTab);
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
				}
			}

			//getting the node and the two points
			final Element elementNode=(Element)square.getNode();
			double x1=0;
      double y1=0;
      double x2=0;
      double y2=0;
			
			try{
        x1=Double.parseDouble(elementNode.getAttribute("x1"));
        y1=Double.parseDouble(elementNode.getAttribute("y1"));
        x2=Double.parseDouble(elementNode.getAttribute("x2"));
        y2=Double.parseDouble(elementNode.getAttribute("y2"));
			}catch (Exception ex){}
			
			final double initX1=x1;
      final double initY1=y1;
      final double initX2=x2;
      final double initY2=y2;
			    
			//the matrix transform
			final SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(elementNode);

			//the path
			GeneralPath path=new GeneralPath();
			path.moveTo((float)x1, (float)y1);
			path.lineTo((float)x2, (float)y2);
			
			//the transform
			AffineTransform affineTransform=null;
			
			//transforming the outline
			if(matrix!=null && matrix.getTransform()!=null){
				affineTransform=matrix.getTransform();
				try{
				  path=(GeneralPath)affineTransform.createTransformedShape(path);
				}catch (Exception ex){}

				//modifying the moved point
				PathIterator it=path.getPathIterator(new AffineTransform());
				double[] segment=new double[6];
				
				if(square.getType().equals("Begin")){
				    //the first point
				    x1=point2.x;
				    y1=point2.y;
				    
				    //getting the second point
				    it.next();
				    it.currentSegment(segment);
				    x2=segment[0];
				    y2=segment[1];
				}else if(square.getType().equals("End")){
				    //getting the first point
				    it.currentSegment(segment);
				    x1=segment[0];
				    y1=segment[1];
				    
				    //the second point
				    x2=point2.x;
				    y2=point2.y;
				}
			}
			
			//storing the new coordinates values
			final double fx1=x1;
      final double fy1=y1;
      final double fx2=x2;
      final double fy2=y2;
			
			//applying the modifications
			elementNode.setAttributeNS(null,"x1", format.format(x1));
			elementNode.setAttributeNS(null,"y1", format.format(y1));
			elementNode.setAttributeNS(null,"x2", format.format(x2));
			elementNode.setAttributeNS(null,"y2", format.format(y2));
			getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));

			//create the undo/redo action and insert it into the undo/redo stack
			if(getStudio().getSvgUndoRedo()!=null){
				SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredomodifypoint")){
					public void undo(){
						//sets the matrix of the node with the old matrix
						getStudio().getSvgToolkit().setTransformMatrix(elementNode, matrix);
						
						elementNode.setAttributeNS(null,"x1", format.format(initX1));
						elementNode.setAttributeNS(null,"y1", format.format(initY1));
						elementNode.setAttributeNS(null,"x2", format.format(initX2));
						elementNode.setAttributeNS(null,"y2", format.format(initY2));
						
						//notifies that the selection has changed
						if(getStudio().getSvgSelection()!=null){
							
							getStudio().getSvgSelection().selectionChanged(true);
						}
					}

					public void redo(){
						//sets the matrix of the node with the old matrix
						getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));
					    
						elementNode.setAttributeNS(null,"x1", format.format(fx1));
						elementNode.setAttributeNS(null,"y1", format.format(fy1));
						elementNode.setAttributeNS(null,"x2", format.format(fx2));
						elementNode.setAttributeNS(null,"y2", format.format(fy2));
						
						//notifies that the selection has changed
						if(getStudio().getSvgSelection()!=null){
							getStudio().getSvgSelection().selectionChanged(true);
						}
					}

				};
				
				//gets or creates the undo/redo list and adds the action into it
				SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredomodifypoint"));
				actionlist.add(action);
				getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);
			}
		}
  }

  /**
   * 
   * @description 
   * @return 
   * @param type
   */
  public String getNextLevel(String type){
    if(type!=null){
			if(type.equals("level1")){
			    return "level2";
			}else if(type.equals("level2")){
			    return "level3";
			}else if(type.equals("level3")){
			    return "level1";
			}
		}
		return "level1";

  }


  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(lineAction!=null){
		  lineAction.cancelActions();
		}
  }
  
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class LineActionListener implements ActionListener{
  
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final LineActionListener action=this;
    
    private boolean  isActive=false;
    
    private Cursor  createCursor;
    private Object  source=null;
    
  
    /**
     * 
     * @description 
     */
    protected LineActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("line");
      
    }
  
    /**
     * 
     * @description 
     */
    protected void reset(){
      if(isActive){
				Collection svgTabs=getStudio().getSvgTabManager().getSvgTabs();
				Iterator it;
				SVGTab svgTab=null;
				LinkedList toBeRemoved=new LinkedList();
				Object mouseListener=null;
				
				//removes all the motion adapters from the frames
				for(it=mouseAdapterSvgTabs.keySet().iterator(); it.hasNext();){
					try{svgTab=(SVGTab)it.next();}catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! svgTabs.contains(svgTab)){
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
						}catch (Exception ex){}
						toBeRemoved.add(svgTab);
					}
				}
					
				//removes the frames that have been closed
				for(it=toBeRemoved.iterator(); it.hasNext();){
					try{mouseAdapterSvgTabs.remove(it.next());}catch (Exception ex){}
				}
		
				
				LineMouseListener lineMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						lineMouseListener=new LineMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(lineMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(lineMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, lineMouseListener);
					}
				}				
			}
    }
  
    /**
     * 
     * @description 
     */
    protected void cancelActions(){
      if(isActive){
				//removes the listeners
				Iterator it;
				SVGTab svgTab=null;
				LinkedList toBeRemoved=new LinkedList();
				Object mouseListener=null;
				
				//removes all the motion adapters from the frames
				for(it=mouseAdapterSvgTabs.keySet().iterator(); it.hasNext();){
				    
					try{svgTab=(SVGTab)it.next();}catch (Exception ex){svgTab=null;}
					
					if(svgTab!=null){
					    
						//resets the information displayed
						svgTab.getStudio().getMainStatusBar().setWidth("");
						svgTab.getStudio().getMainStatusBar().setHeight("");
						svgTab.getScrollPane().getSvgCanvas().setSVGCursor(svgTab.getStudio().getSvgCursors().getCursor("default"));
						
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
							
							if(mouseListener!=null && ((LineMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((LineMouseListener)mouseListener).paintListener, true);
							}
						}catch (Exception ex){}
						
						toBeRemoved.add(svgTab);
					}
				}
				
				//removes the frames that have been closed
				for(it=toBeRemoved.iterator(); it.hasNext();){
				    
					try{mouseAdapterSvgTabs.remove(it.next());}catch (Exception ex){}
				}
				svgToolItem.setSelected(false); 
				isActive=false;
			}
    }
  
    /**
     * 
     * @description 
     * @param evt
     */
    public void actionPerformed(ActionEvent evt){

      getStudio().cancelActions(false);
      
      if(getStudio().getSvgTabManager().getCurrentSVGTab()!=null){
        
        //the listener is active
        isActive=true;
        source=evt.getSource();

        Collection frames=getStudio().getSvgTabManager().getSvgTabs();
        Iterator it;
        SVGTab svgTab=null;
        LineMouseListener lineMouseListener=null;
            
        //adds the new motion adapters
        for(it=frames.iterator(); it.hasNext();){
            
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){

            lineMouseListener=new LineMouseListener(svgTab);

            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(lineMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(lineMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            
            mouseAdapterSvgTabs.put(svgTab, lineMouseListener);
          }
        }
      }
    }
  }
  
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class LineMouseListener extends MouseAdapter implements MouseMotionListener{
  
    private Point  point1=null;
    private Point  point2=null;
    private SVGTab  svgTab;
    private SVGPaintListener  paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public LineMouseListener(SVGTab svgTab){
      this.svgTab=svgTab;
      final SVGTab finalSvgTab=svgTab;
      //adds a paint listener
      paintListener=new SVGPaintListener(){
        public void paintToBeDone(Graphics g) {
          if(point1!=null && point2!=null){
            Point2D.Double 	pt1=getStudio().getSvgToolkit().getScaledPoint(finalSvgTab, new Point2D.Double(point1.x, point1.y), false);
            Point2D.Double 	pt2=getStudio().getSvgToolkit().getScaledPoint(finalSvgTab, new Point2D.Double(point2.x, point2.y), false);
            if(pt1!=null && pt2!=null){
              //draws the shape of the element that will be created if the user released the mouse button
              svgLine.drawGhost(finalSvgTab, (Graphics2D)g, new Point((int)pt1.x, (int)pt1.y), new Point((int)pt2.x, (int)pt2.y));
            }
          }
        }
      };      
      svgTab.getScrollPane().getSvgCanvas().addDrawLayerPaintListener(paintListener, false);
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseDragged(MouseEvent evt){
      if(evt.isControlDown()){
        point2=computeLinePointWhenCtrlDown(getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint()));
			}else{
				//sets the second point of the element
        point2=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
			}
      //asks the canvas to be repainted to draw the shape of the future element
      svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseMoved(MouseEvent evt){
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mousePressed(MouseEvent evt){
      //sets the first point of the area corresponding to the future element
				point1=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseReleased(MouseEvent evt){
      Point point=null;
      if(evt.isControlDown()){
        point=computeLinePointWhenCtrlDown(getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint()));
      }else{
        //sets the second point of the element
        point=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      }
			final Point finalPoint=point;
      //creates the element in the SVG document
      if(point1!=null && finalPoint!=null){
        final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
        if(queue.getThread()!=Thread.currentThread()){
          final Point finalPoint1=new Point(point1.x, point1.y);
          queue.invokeLater(new Runnable(){
            public void run(){
              svgLine.drawLine(svgTab, finalPoint1, finalPoint);
            }
          });
        }else{
          svgLine.drawLine(svgTab, point1, finalPoint);
        }
      }
      getStudio().cancelActions(true);
      point1=null;
      point2=null;
    }
    
    /**
     * 
     * @description 
     * @return 
     * @param point
     */
    public Point computeLinePointWhenCtrlDown(Point point){
      Point pt=new Point();
      if(point1!=null && point!=null){
        pt.x=point.x;
        pt.y=point.y;
        Point pt1=new Point(point1.x, point1.y);
        Point pt2=new Point(point.x, point.y);

        //the norme
        double n=Math.sqrt(Math.pow((pt2.x-pt1.x), 2)+Math.pow((pt2.y-pt1.y), 2));

        //the x-distance and the y-distance
        double xDistance=Math.abs(pt2.x-pt1.x), yDistance=Math.abs(pt2.y-pt1.y);

        //the angle
        double cosinus=(pt2.x-pt1.x)/n;

        //computing the new point
        if(pt1.x<=pt2.x && pt1.y>=pt2.y){
          if(cosinus<=1 && cosinus>Math.cos(Math.PI/8)){
             pt.x=(int)(pt1.x+xDistance);
             pt.y=pt1.y;
          }else if(cosinus<=Math.cos(Math.PI/8) && cosinus>Math.cos(3*Math.PI/8)){
             pt.x=(int)(pt1.x+xDistance);
             pt.y=(int)(pt1.y-xDistance);
          }else if(cosinus<=Math.cos(3*Math.PI/8) && cosinus>0){
             pt.x=pt1.x;
             pt.y=(int)(pt1.y-yDistance);
          }
        }else if(pt1.x>pt2.x && pt1.y>=pt2.y){
          if(cosinus<=0 && cosinus>Math.cos(5*Math.PI/8)){
             pt.x=pt1.x;
             pt.y=(int)(pt1.y-yDistance);
          }else if(cosinus<=Math.cos(5*Math.PI/8) && cosinus>=Math.cos(7*Math.PI/8)){
             pt.x=(int)(pt1.x-xDistance);
             pt.y=(int)(pt1.y-xDistance);
          }else if(cosinus<=Math.cos(7*Math.PI/8) && cosinus>=-1){
             pt.x=(int)(pt1.x-xDistance);
             pt.y=pt1.y;
          }
        }else if(pt1.x>=pt2.x && pt1.y<pt2.y){
          if(cosinus>=-1 && cosinus<Math.cos(7*Math.PI/8)){
             pt.x=(int)(pt1.x-xDistance);
             pt.y=pt1.y;
          }else if(cosinus>=Math.cos(7*Math.PI/8) && cosinus<Math.cos(5*Math.PI/8)){
             pt.x=(int)(pt1.x-xDistance);
             pt.y=(int)(pt1.y+xDistance);
          }else if(cosinus>=Math.cos(5*Math.PI/8) && cosinus<0){
             pt.x=pt1.x;
             pt.y=(int)(pt1.y+yDistance);
          }
        }else if(pt1.x<=pt2.x && pt1.y<=pt2.y){
          if(cosinus>=0 && cosinus<Math.cos(3*Math.PI/8)){
             pt.x=pt1.x;
             pt.y=(int)(pt1.y+yDistance);
          }else if(cosinus>=Math.cos(3*Math.PI/8) && cosinus<Math.cos(Math.PI/8)){
             pt.x=(int)(pt1.x+xDistance);
             pt.y=(int)(pt1.y+xDistance);
          }else if(cosinus>=Math.cos(Math.PI/8) && cosinus<1){
             pt.x=(int)(pt1.x+xDistance);
             pt.y=pt1.y;
          }
        }
      }
      return pt;
    }
  }
}