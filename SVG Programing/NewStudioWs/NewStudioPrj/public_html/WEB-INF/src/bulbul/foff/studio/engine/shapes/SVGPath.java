package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.selection.SVGSelectionSquare;
import bulbul.foff.studio.engine.ui.SVGApplet;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.JToggleButton;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.ext.awt.geom.ExtendedPathIterator;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGPath extends SVGShape  {
 
  private final SVGPath svgPath=this;
  private DecimalFormat format=null;
  private ActionListener cubicListener;
  private LinkedList selectedNodes=new LinkedList();
  private ActionListener  subtractionListener;
  private ActionListener quadraticListener;
  private PathActionListener pathActionCubic=null;
  private PathActionListener pathActionQuadratic=null;
  private ActionListener unionListener;
  private JToggleButton quadraticTool;
  private JToggleButton cubicTool;
  
  protected static final int QUADRATIC_BEZIER=0;
  protected static final int CUBIC_BEZIER=1;

  
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGPath(SVGApplet studio) {
    super(studio);
    ids.put("id","path");
		ids.put("idmenupathoperations","PathOperations");
		ids.put("idconvert","ConvertToPath");
		ids.put("idunion","PathUnion");
		ids.put("idsubtraction","PathSubtraction");
		ids.put("idintersection","PathIntersection");
		ids.put("idquadratic","QuadraticBezier");
		ids.put("idcubic","CubicBezier");
    
    //the object used to convert double values into strings
		DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    
    final SVGApplet finalStudio=studio;
		
		//a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabChangedListener=new ActionListener(){
      private ActionListener selectionListener=null;
			private SVGSelection svgSelection=null;

			public void actionPerformed(ActionEvent e) {
				//clears the list of the selected items
				selectedNodes.clear();
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
					quadraticTool.setEnabled(true);
					//quadraticTool.setIcon(quadraticIcon);
					cubicTool.setEnabled(true);
					//cubicTool.setIcon(cubicIcon);
				}else{
					quadraticTool.setEnabled(false);
					//quadraticTool.setIcon(quadraticDisabledIcon);
					cubicTool.setEnabled(false);
					//cubicTool.setIcon(cubicDisabledIcon);
				}
				
				pathActionQuadratic.reset();
				pathActionCubic.reset();
				
				final SVGTab svgTab=finalStudio.getSvgTabManager().getCurrentSVGTab();
				
				//if a selection listener is already registered on a selection module, it is removed	
				if(svgSelection!=null && selectionListener!=null){
					svgSelection.removeSelectionListener(selectionListener);
				}

				//gets the current selection module	
				if(svgTab!=null){
					svgSelection=finalStudio.getSvgSelection();
				}
				
				if(svgTab!=null && svgSelection!=null){
					manageSelection();
					//the listener of the selection changes
					selectionListener=new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							manageSelection();
						}
					};
					
					//adds the selection listener
					if(selectionListener!=null){
						svgSelection.addSelectionListener(selectionListener);
					}
				}
			}
			
			/**
			 * updates the selected items and the state of the menu items
			 */
			protected void manageSelection(){
				//disables the menuitems
			  /*
        convert.setEnabled(false);
				union.setEnabled(false);
				subtraction.setEnabled(false);
				intersection.setEnabled(false);
        */

				LinkedList list=null;
				
				//gets the currently selected nodes list 
				if(svgSelection!=null){
					list=svgSelection.getCurrentSelection(finalStudio.getSvgTabManager().getCurrentSVGTab());
				}
				selectedNodes.clear();

				//refresh the selected nodes list
				if(list!=null){
				  selectedNodes.addAll(list);
				}
				
				if(selectedNodes.size()>0){
				  //convert.setEnabled(true);
					if(selectedNodes.size()>1){
						//union.setEnabled(true);
						//intersection.setEnabled(true);
						if(selectedNodes.size()==2){
						//	subtraction.setEnabled(true);
						}
					}
				}
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    quadraticTool=getStudio().getSvgToolBar().getStbQBCurve();
    pathActionQuadratic=new PathActionListener(QUADRATIC_BEZIER);
    quadraticTool.addActionListener(pathActionQuadratic);
    
    cubicTool=getStudio().getSvgToolBar().getStbCBCurve();
		pathActionCubic=new PathActionListener(CUBIC_BEZIER);
    cubicTool.addActionListener(pathActionCubic);
  }
  
  /**
   * 
   * @description 
   * @param nodes
   * @param svgTab
   */
  protected void subtraction(SVGTab svgTab, LinkedList nodes){
    
  }
  
  /**
   * 
   * @description 
   * @param nodes
   * @param svgTab
   */
  protected void union(SVGTab svgTab, LinkedList nodes){
    
  }
  
  /**
   * 
   * @description 
   * @param nodes
   * @param svgTab
   */
  protected void intersection(SVGTab svgTab, LinkedList nodes){
    
  }
  
  /**
   * 
   * @description 
   * @param nodes
   * @param svgTab
   */
  protected void convertToPath(SVGTab svgTab, LinkedList nodes){
   
  }
  
  /**
   * 
   * @description 
   * @param points
   * @param g
   * @param svgTab
   */
  protected void drawGhost(SVGTab svgTab, Graphics g, Point2D.Double[] points){
    Graphics2D g2D=(Graphics2D)g;
		if(svgTab!=null && points!=null && points.length>1 && g2D!=null){
			//computes and draws the new awt polygon to be displayed
			g2D.setXORMode(Color.white);
			g2D.setColor(GHOST_COLOR);
			for(int i=1;i<points.length;i++){
				g2D.drawLine((int)points[i-1].x, (int)points[i-1].y, (int)points[i].x, (int)points[i].y);
			}
		}		
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   * @param g
   * @param svgTab
   */
  protected LinkedList drawModifyPointsSelection(SVGTab svgTab, Graphics g, Node node){
    LinkedList squareList=new LinkedList();
		Graphics2D g2D=(Graphics2D)g;
		
		if(svgTab!=null && g2D!=null && node!=null){
			
			int squareDistance=5;
			final Element elementNode=(Element)node;
      //getting the path that will be analysed
      ExtendedGeneralPath path=getStudio().getSvgToolkit().getGeneralPath(svgTab, elementNode);

			//the cursor associated with the selection points
			Cursor cursor=new Cursor(Cursor.HAND_CURSOR);
			Shape shape=null;
			int type=-1;
			double[] values=new double[7];
			int index=0;
      int squareX=0;
      int squareY=0;
      
			Point2D.Double scaledPoint=null;
      Point2D.Double scaledCtrlPoint1=null;
      Point2D.Double scaledCtrlPoint2=null;
      Point2D.Double lastScaledPoint=new Point2D.Double(0, 0);
			GradientPaint gradient=null;

			//draws the selection
			for(ExtendedPathIterator pit=path.getExtendedPathIterator(); ! pit.isDone(); pit.next()){

				type=pit.currentSegment(values);
				if(type==ExtendedPathIterator.SEG_CLOSE){

				}else if(type==ExtendedPathIterator.SEG_CUBICTO){
					scaledCtrlPoint1=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[0], values[1]), false);
					scaledCtrlPoint2=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[2], values[3]), false);
					scaledPoint=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[4], values[5]), false);

					g2D.setColor(LINE_SELECTION_COLOR);
					g2D.drawLine((int)lastScaledPoint.x, (int)lastScaledPoint.y, (int)scaledCtrlPoint1.x, (int)scaledCtrlPoint1.y);
				
					squareX=(int)(scaledCtrlPoint1.x-squareDistance);
					squareY=(int)(scaledCtrlPoint1.y-squareDistance);
				
					if(getStudio().getSvgSelection()!=null){
					  squareList.add(new SVGSelectionSquare(node,"Cctrl"+new Integer(index++).toString(),new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),cursor));
					}
						
					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "Ctrl", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					
					squareX=(int)(scaledCtrlPoint2.x-squareDistance);
					squareY=(int)(scaledCtrlPoint2.y-squareDistance);
					
					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node,"Cctrl"+new Integer(index++).toString(),new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "Ctrl", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					
					g2D.setColor(LINE_SELECTION_COLOR);
					g2D.drawLine((int)scaledCtrlPoint2.x, (int)scaledCtrlPoint2.y, (int)scaledPoint.x, (int)scaledPoint.y);
					
					squareX=(int)(scaledPoint.x-squareDistance);
					squareY=(int)(scaledPoint.y-squareDistance);

					if(getStudio().getSvgSelection()!=null){
					  squareList.add(new SVGSelectionSquare(node,"C"+new Integer(index++).toString(),new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					
					lastScaledPoint=scaledPoint;

				}else if(type==ExtendedPathIterator.SEG_LINETO){
					scaledPoint=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[0], values[1]), false);
					lastScaledPoint=scaledPoint;
					squareX=(int)(scaledPoint.x-squareDistance);
					squareY=(int)(scaledPoint.y-squareDistance);

					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node, "L"+new Integer(index++).toString(), 
					            				new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance), cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					
				}else if(type==ExtendedPathIterator.SEG_ARCTO){
					
					scaledPoint=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[5], values[6]), false);
					lastScaledPoint=scaledPoint;
					
					squareX=(int)(scaledPoint.x-squareDistance);
					squareY=(int)(scaledPoint.y-squareDistance);

					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node, "A"+new Integer(index++).toString(), 
					            				new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance), cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
				}else if(type==ExtendedPathIterator.SEG_MOVETO){

					scaledPoint=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[0], values[1]), false);
					lastScaledPoint=scaledPoint;
					
					squareX=(int)(scaledPoint.x-squareDistance);
					squareY=(int)(scaledPoint.y-squareDistance);

					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node, "M"+new Integer(index++).toString(), 
					            				new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance), cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}

				}else if(type==ExtendedPathIterator.SEG_QUADTO){

					scaledCtrlPoint1=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[0], values[1]), false);
					scaledPoint=getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(values[2], values[3]), false);

					g2D.setColor(LINE_SELECTION_COLOR);
					g2D.drawLine((int)lastScaledPoint.x, (int)lastScaledPoint.y, (int)scaledCtrlPoint1.x, (int)scaledCtrlPoint1.y);
					
					squareX=(int)(scaledCtrlPoint1.x-squareDistance);
					squareY=(int)(scaledCtrlPoint1.y-squareDistance);
					
					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node,"Qctrl"+new Integer(index++).toString(),new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "Ctrl", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					
					squareX=(int)(scaledPoint.x-squareDistance);
					squareY=(int)(scaledPoint.y-squareDistance);

					if(getStudio().getSvgSelection()!=null){
					    squareList.add(new SVGSelectionSquare(node,"Q"+new Integer(index++).toString(),new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),cursor));
					}

					shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);

					if(shape!=null){
						gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g2D.setPaint(gradient);
						g2D.fill(shape);
						g2D.setColor(LINE_SELECTION_COLOR);
						g2D.draw(shape);
					}
					lastScaledPoint=scaledPoint;
				}
			}
		}	
		
		return squareList;
  }
  
  /**
   * 
   * @description 
   * @param points
   * @param svgTab
   */
  protected void drawCubicBezier(SVGTab svgTab, Point[] points){
		if(svgTab!=null && points!=null && points.length>1){

			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();

			if(getStudio().getSvgSelection()!=null && document!=null){

				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);

				if(parent!=null && points.length>1){
					//creates the string of the values for the attribute
					String value="M "+format.format(points[0].getX())+" "+format.format(points[0].getY())+" C ";
			
					for(int i=1;i<points.length;i++){
						value=value.concat(format.format(points[i-1].x+(points[i].x-points[i-1].x)/4)+" "+format.format(points[i-1].y+(points[i].y-points[i-1].y)/4)+" ");
						value=value.concat(format.format(points[i].x-(points[i].x-points[i-1].x)/4)+" "+format.format(points[i].y-(points[i].y-points[i-1].y)/4)+" ");
						value=value.concat(format.format(points[i].x)+" "+format.format(points[i].y)+" ");
					}

					//creates the path
					final Element path=document.createElementNS(document.getDocumentElement().getNamespaceURI(),"path");
			
					path.setAttributeNS(null,"d",value);
					String colorString="black";//getStudio().getColorChooser().getColorString(frame, getSVGEditor().getSVGColorManager().getCurrentColor());
					path.setAttributeNS(null, "style", "stroke:".concat(colorString.concat("; fill:none")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
			
					// attaches the element to the svg root element
					parent.appendChild(path);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreatecubic")){
							public void undo(){
							  parent.removeChild(path);
							}

							public void redo(){
							  parent.appendChild(path);
							}
						};
				
						//adds the undo/redo actions into the stack
						SVGSelection selection=getStudio().getSvgSelection();
				
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, path);
							selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction((String)labels.get("undoredocreatecubic")){});
							selection.refreshSelection(svgTab);
						}else{
							SVGUndoRedoActionList actionList=new SVGUndoRedoActionList((String)labels.get("undoredocreatecubic"));
							actionList.add(action);
							getStudio().getSvgUndoRedo().addActionList(svgTab, actionList);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param points
   * @param svgTab
   */
  protected void drawQuadraticBezier(SVGTab svgTab, Point[] points){
    if(svgTab!=null && points!=null && points.length>1){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null && points.length>1){
					//creates the string of the values for the attribute
					String value="M "+format.format(points[0].x)+" "+format.format(points[0].y)+" Q ";
			
					for(int i=1;i<points.length;i++){
						value=value.concat(format.format(points[i-1].x+(points[i].x-points[i-1].x)/2)+" "+format.format(points[i-1].y+(points[i].y-points[i-1].y)/2)+" ");
						value=value.concat(format.format(points[i].x)+" "+format.format(points[i].y)+" ");
					}

					//creates the path
					final Element path = document.createElementNS(document.getDocumentElement().getNamespaceURI(),"path");
			
					path.setAttributeNS(null, "d", value);
					String colorString="black";//getStudio().getColorChooser().getColorString(frame, getSVGEditor().getSVGColorManager().getCurrentColor());
					path.setAttributeNS(null, "style", "stroke:".concat(colorString.concat("; fill:none")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);
			
					// attaches the element to the svg root element
					parent.appendChild(path);

					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreatequadratic")){
							public void undo(){
							  parent.removeChild(path);
							}
							public void redo(){
							  parent.appendChild(path);
							}
						};
				
						//adds the undo/redo actions into the stack
						SVGSelection selection=getStudio().getSvgSelection();
				
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, path);
							selection.addUndoRedoAction(svgTab, new SVGUndoRedoAction((String)labels.get("undoredocreatequadratic")){});
							selection.refreshSelection(svgTab);
						}else{
							SVGUndoRedoActionList actionList=new SVGUndoRedoActionList((String)labels.get("undoredocreatequadratic"));
							actionList.add(action);
							getStudio().getSvgUndoRedo().addActionList(svgTab, actionList);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param point2
   * @param point1
   * @param square
   * @param svgTab
   */
  public void modifyPoint(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && point2!=null){

			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;

			try{
				paintListener=(SVGPaintListener)modifyPointSvgTabTable.get(svgTab);
			}catch (Exception ex){paintListener=null;}

			final Element elementNode=(Element)square.getNode();
      //the new path
      ExtendedGeneralPath newPath=new ExtendedGeneralPath();
      //getting the path that will be analysed
      ExtendedGeneralPath tmpPath=getStudio().getSvgToolkit().getGeneralPath(svgTab, elementNode);
			int type=-1;
			int index=-1;
      double[] values=new double[7];

			//gets the index of the point that has to be modified 
			try{
				//if the point is a control point
				if(square.getType().indexOf("ctrl")==-1){
					index=new Integer(square.getType().substring(1,square.getType().length())).intValue();
				}else{
					index=new Integer(square.getType().substring(square.getType().indexOf("ctrl")+4, square.getType().length())).intValue();
				}
			}catch(Exception ex){}

			//for each command in the path, the command and its values are added to the string value
			int i=0;
      for(ExtendedPathIterator pit=tmpPath.getExtendedPathIterator(); ! pit.isDone(); pit.next()){
				type=pit.currentSegment(values);
				if(type==ExtendedPathIterator.SEG_CLOSE){
				    newPath.closePath();
				}else if(type==ExtendedPathIterator.SEG_CUBICTO){
					if(index==i){
					  newPath.curveTo((float)point2.x, (float)point2.y, (float)values[2], (float)values[3], (float)values[4], (float)values[5]);
					}else if(index==i+1){
					  newPath.curveTo((float)values[0], (float)values[1], (float)point2.x, (float)point2.y, (float)values[4], (float)values[5]);
					}else if(index==i+2){
					  newPath.curveTo((float)values[0], (float)values[1], (float)values[2], (float)values[3], (float)point2.x, (float)point2.y);
					}else{
					  newPath.curveTo((float)values[0], (float)values[1], (float)values[2], (float)values[3], (float)values[4], (float)values[5]);
					}
					i+=3;
				}else if(type==ExtendedPathIterator.SEG_LINETO){
					if(index==i){
					  newPath.lineTo((float)point2.x, (float)point2.y);
					}else{
					  newPath.lineTo((float)values[0], (float)values[1]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_ARCTO){
					if(index==i){
					  newPath.arcTo((float)values[0], (float)values[1], (float)values[2], values[3]==0?false:true, values[4]==0?false:true, (float)point2.x, (float)point2.y);
					}else{
					  newPath.arcTo((float)values[0], (float)values[1], (float)values[2], values[3]==0?false:true, values[4]==0?false:true, (float)values[5], (float)values[6]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_MOVETO){
					if(index==i){
					  newPath.moveTo((float)point2.x, (float)point2.y);
					}else{
					  newPath.moveTo((float)values[0], (float)values[1]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_QUADTO){
					if(index==i){
					  newPath.quadTo((float)point2.x, (float)point2.y, (float)values[2], (float)values[3]);
					}else if(index==i+1){
					  newPath.quadTo((float)values[0], (float)values[1], (float)point2.x, (float)point2.y);
					}else{
					  newPath.quadTo((float)values[0], (float)values[1], (float)values[2], (float)values[3]);
					}
					i+=2;
				}
			}
			
			//computes the scale and translate values
			if(newPath!=null){
				//concatenates the transforms to draw the outline
				double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
				AffineTransform affineTransform=AffineTransform.getScaleInstance(scale, scale);
				Dimension translateValues=svgTab.getScrollPane().getTranslateValues();
				affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));

				//computing the outline
				final Shape outline=affineTransform.createTransformedShape(newPath);

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
						g2D.fill(outline);
						g2D.setColor(OUTLINE_COLOR);
						g2D.draw(outline);
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
   * @param point2
   * @param point1
   * @param square
   * @param svgTab
   */
  public void validateModifyPoint(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && point2!=null){

			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			
			
			for(Iterator it=new LinkedList(modifyPointSvgTabTable.keySet()).iterator(); it.hasNext();){
				try{
					paintListener=(SVGPaintListener)modifyPointSvgTabTable.get((SVGTab)it.next());
				}catch (Exception ex){paintListener=null;}
				
				if(paintListener!=null){
				  modifyPointSvgTabTable.remove(svgTab);
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
				}  
			}
			
			final Element elementNode=(Element)square.getNode();
			    
      //the new path
      ExtendedGeneralPath newPath=new ExtendedGeneralPath();
      //getting the path that will be analysed
      ExtendedGeneralPath tmpPath=getStudio().getSvgToolkit().getGeneralPath(svgTab, elementNode);

			int type=-1;
      int index=-1;
			double[] values=new double[7];

			//gets the index of the point that has to be modified 
			try{
				//if the point is a control point
				if(square.getType().indexOf("ctrl")==-1){
					index=new Integer(square.getType().substring(1,square.getType().length())).intValue();
				}else{
					index=new Integer(square.getType().substring(square.getType().indexOf("ctrl")+4, square.getType().length())).intValue();
				}
			}catch(Exception ex){}

			//for each command in the path, the command and its values are added to the string value
			int i=0;
      for(ExtendedPathIterator pit=tmpPath.getExtendedPathIterator(); ! pit.isDone(); pit.next()){
				type=pit.currentSegment(values);
				if(type==ExtendedPathIterator.SEG_CLOSE){
          newPath.closePath();
				}else if(type==ExtendedPathIterator.SEG_CUBICTO){
					if(index==i){
					  newPath.curveTo((float)point2.x, (float)point2.y, (float)values[2], (float)values[3], (float)values[4], (float)values[5]);
					}else if(index==i+1){
					  newPath.curveTo((float)values[0], (float)values[1], (float)point2.x, (float)point2.y, (float)values[4], (float)values[5]);
					}else if(index==i+2){
					  newPath.curveTo((float)values[0], (float)values[1], (float)values[2], (float)values[3], (float)point2.x, (float)point2.y);
					}else{
					  newPath.curveTo((float)values[0], (float)values[1], (float)values[2], (float)values[3], (float)values[4], (float)values[5]);
					}
					i+=3;
				}else if(type==ExtendedPathIterator.SEG_LINETO){
					if(index==i){
					  newPath.lineTo((float)point2.x, (float)point2.y);
					}else{
					  newPath.lineTo((float)values[0], (float)values[1]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_ARCTO){
					if(index==i){
					  newPath.arcTo((float)values[0], (float)values[1], (float)values[2], values[3]==0?false:true, values[4]==0?false:true, (float)point2.x, (float)point2.y);
					}else{
					  newPath.arcTo((float)values[0], (float)values[1], (float)values[2], values[3]==0?false:true, values[4]==0?false:true, (float)values[5], (float)values[6]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_MOVETO){
					if(index==i){
					  newPath.moveTo((float)point2.x, (float)point2.y);
					}else{
					  newPath.moveTo((float)values[0], (float)values[1]);
					}
					i++;
				}else if(type==ExtendedPathIterator.SEG_QUADTO){
					if(index==i){
					  newPath.quadTo((float)point2.x, (float)point2.y, (float)values[2], (float)values[3]);
					}else if(index==i+1){
					  newPath.quadTo((float)values[0], (float)values[1], (float)point2.x, (float)point2.y);
					}else{
					  newPath.quadTo((float)values[0], (float)values[1], (float)values[2], (float)values[3]);
					}
					i+=2;
				}
			}
			
			if(newPath!=null){
				values=new double[7];
				String d="";
				
				//creating the new string value for the "d" attribute of the element
				for(ExtendedPathIterator pit=newPath.getExtendedPathIterator(); ! pit.isDone(); pit.next()){
					type=pit.currentSegment(values);
					if(type==ExtendedPathIterator.SEG_CLOSE){
						d=d.concat("Z ");
					}else if(type==ExtendedPathIterator.SEG_CUBICTO){
						pit.currentSegment(values);
						d=d.concat("C "+format.format(values[0])+" "+format.format(values[1])+" "+
						        			format.format(values[2])+" "+format.format(values[3])+" "+
						        			format.format(values[4])+" "+format.format(values[5])+" ");
					}else if(type==ExtendedPathIterator.SEG_LINETO){
						pit.currentSegment(values);
						d=d.concat("L "+format.format(values[0])+" "+format.format(values[1])+" ");
					}else if(type==ExtendedPathIterator.SEG_ARCTO){
						pit.currentSegment(values);
						d=d.concat("A "+format.format(values[0])+" "+format.format(values[1])+" "+format.format(values[2])+" "+
						        			(values[3]==0?"0":"1")+" "+(values[4]==0?"0":"1")+" "+
						        			format.format(values[5])+" "+format.format(values[6]));
					}else if(type==ExtendedPathIterator.SEG_MOVETO){
						pit.currentSegment(values);
						d=d.concat("M "+format.format(values[0])+" "+format.format(values[1])+" ");
					}else if(type==ExtendedPathIterator.SEG_QUADTO){
						pit.currentSegment(values);
						d=d.concat("Q "+format.format(values[0])+" "+format.format(values[1])+" "+
			        						format.format(values[2])+" "+format.format(values[3])+" ");
					}
				}
				
				//getting the current matrix transform
				final SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(elementNode);
				
				//getting the current node value
				final String oldD=elementNode.getAttributeNS(null, "d");
				
				//the new value for the node
				final String newD=d;

				//setting the new values for the node
				elementNode.setAttributeNS(null, "d", d);
				getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));

				//create the undo/redo action and insert it into the undo/redo stack
				if(getStudio().getSvgUndoRedo()!=null){
					SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredomodifypoint")){
						public void undo(){
							if(matrix!=null && oldD!=null){
                elementNode.setAttributeNS(null, "d", oldD);
                getStudio().getSvgToolkit().setTransformMatrix(elementNode, matrix);

								//notifies that the selection has changed
								if(getStudio().getSvgSelection()!=null){
                  getStudio().getSvgSelection().selectionChanged(true);
								}
							}
						}

						public void redo(){
							if(newD!=null){
                elementNode.setAttributeNS(null, "d", newD);
                getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));
							    
								//notifies that the selection has changed
								if(getStudio().getSvgSelection()!=null){
									getStudio().getSvgSelection().selectionChanged(true);
								}
							}
						}
					};
					
					//gets or creates the undo/redo list and adds the action into it
					SVGUndoRedoActionList actionList=new SVGUndoRedoActionList((String)labels.get("undoredomodifypoint"));
					actionList.add(action);
					getStudio().getSvgUndoRedo().addActionList(svgTab, actionList);
				}
			}
		}
  }

  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(pathActionQuadratic!=null){
		  pathActionQuadratic.cancelActions();
		}
		
		if(pathActionCubic!=null){
		  pathActionCubic.cancelActions();
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
   * @version 1.0 26-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class PathActionListener implements ActionListener{
    
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final PathActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
    private JToggleButton svgPathTool;
    private int type;
    /**
     * 
     * @description 
     */
    protected PathActionListener(int type){
      this.type=type;
			createCursor=getStudio().getSvgCursors().getCursor("path");
			
			if(type==QUADRATIC_BEZIER){
			    svgPathTool=quadraticTool;
			}else if(type==CUBIC_BEZIER){
			    svgPathTool=cubicTool;
			}
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
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
			
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
					try{
            mouseAdapterSvgTabs.remove(it.next());
          }catch (Exception ex){}
				}

				PathMouseListener pathMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
			
					if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						pathMouseListener=new PathMouseListener(svgTab,type);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(pathMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(pathMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, pathMouseListener);
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
				    
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					
					if(svgTab!=null){
					    
						//resets the information displayed
						svgTab.getStudio().getMainStatusBar().setWidth("");
						svgTab.getStudio().getMainStatusBar().setHeight("");
						svgTab.getScrollPane().getSvgCanvas().setSVGCursor(svgTab.getStudio().getSvgCursors().getCursor("default"));
						
						try{
							mouseListener=mouseAdapterSvgTabs.get(svgTab);
							svgTab.getScrollPane().getSvgCanvas().removeMouseListener((MouseAdapter)mouseListener);
							svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener((MouseMotionListener)mouseListener);
							
							if(mouseListener!=null && ((PathMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((PathMouseListener)mouseListener).paintListener, true);
							}
						}catch (Exception ex){}
						toBeRemoved.add(svgTab);
					}
				}
			
				//removes the frames that have been closed
				for(it=toBeRemoved.iterator(); it.hasNext();){
					try{
            mouseAdapterSvgTabs.remove(it.next());
          }catch (Exception ex){}
				}
				svgPathTool.setSelected(false);
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
        PathMouseListener pathMouseListener=null;
            
        //adds the new motion adapters
        for(it=frames.iterator(); it.hasNext();){
            
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){
            pathMouseListener=new PathMouseListener(svgTab,type);
            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(pathMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(pathMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, pathMouseListener);
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
  protected class PathMouseListener extends MouseAdapter implements MouseMotionListener{
  
    private Point  lastPoint=null;
    private ArrayList  points=new ArrayList();
    private SVGTab  svgTab;
    private SVGPaintListener  paintListener;
    private int type;
    /**
     * 
     * @description 
     * @param svgTab
     */
    public PathMouseListener(SVGTab svgTab,int type){
      this.svgTab=svgTab;
      this.type=type;
      final SVGTab finalSvgTab=svgTab;
      
      //adds a paint listener
      paintListener=new SVGPaintListener(){
        public void paintToBeDone(Graphics g) {
          if(points.size()>1){
            //draws the shape of the path that will be created if the user released the mouse button
            Point2D.Double[] scaledPoints=new Point2D.Double[points.size()];
            Point point=null;
            
            for(int i=0;i<scaledPoints.length;i++){
              point=(Point)points.get(i);
              scaledPoints[i]=getStudio().getSvgToolkit().getScaledPoint(finalSvgTab, new Point2D.Double(point.x, point.y), false);
            }
            if(g!=null && scaledPoints!=null){
              svgPath.drawGhost(finalSvgTab, (Graphics2D)g, scaledPoints);
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
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseMoved(MouseEvent evt){
      Point point=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      if(points.size()>0){
        points.set(points.size()-1, point);
      }
      
      //asks the canvas to be repainted to draw the shape of the future path
      svgTab.getScrollPane().getSvgCanvas().repaintCanvas();
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mousePressed(MouseEvent evt){
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseReleased(MouseEvent evt){
      
    }
    
    /**
     * 
     * @description 
     * @param evt
     */
    public void mouseClicked(MouseEvent evt) {
      
      Point point=getStudio().getSvgToolkit().getAlignedWithRulersPoint(svgTab, evt.getPoint());
      
      if(point!=null){
        boolean isDoubleClick=false;
        if(lastPoint!=null && Math.abs(lastPoint.getX()-evt.getPoint().getX())<2 && Math.abs(lastPoint.getY()-evt.getPoint().getY())<2){
          isDoubleClick=true;
        }
        
        lastPoint=evt.getPoint();
        if(isDoubleClick){
          if(points.size()>0){
            final Point[] finalPoints=new Point[points.size()-1];
            for(int i=0;i<finalPoints.length;i++){
              finalPoints[i]=(Point)points.get(i);
            }
          
            final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
            if(type==QUADRATIC_BEZIER){
              if(queue.getThread()!=Thread.currentThread()){
                
                queue.invokeLater(new Runnable(){
                  public void run(){
                    svgPath.drawQuadraticBezier(svgTab, finalPoints);
                  }
                });
              }else{
                svgPath.drawQuadraticBezier(svgTab, finalPoints);
              }
            }else if(type==CUBIC_BEZIER){
              if(queue.getThread()!=Thread.currentThread()){
                queue.invokeLater(new Runnable(){
                  public void run(){
                    svgPath.drawCubicBezier(svgTab, finalPoints);
                  }
                });
              }else{
                svgPath.drawCubicBezier(svgTab, finalPoints);
              }
            }
            getStudio().cancelActions(true);
            //clears the array list
            points.clear();
            lastPoint=null;
          }
        }else{
          if(points.size()==0){
            points.add(point);
          }
          points.set(points.size()-1, point);
          points.add(point);
        }
      }
    }
  }
}