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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 25-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGPolygon extends SVGShape  {
  
  private String shapepolygonlabel="Polygon";
  
  private String shapepolygonundoredocreate="Create Polygon";
  private String shapepolygonundoredotranslate="Translate Polygon";
  private String shapepolygonundoredoresize="Resize Polygon";
  private String shapepolygonundoredorotate="Rotate Polygon";
  private String shapepolygonundoredomodifypoint="Modify Point";
  
  private String shapepolygonhelpcreate="Draw a polygon";


  private PolygonActionListener polygonAction=null;
  private final SVGPolygon svgPolygon=this;
  private DecimalFormat format=null;

  /**
   * 
   * @description 
   * @param studio
   */
  public SVGPolygon(Studio studio) {
    super(studio);
    ids.put("id","polygon");
    try{
      labels.put("label", shapepolygonlabel);
      labels.put("undoredocreate", shapepolygonundoredocreate);
      labels.put("undoredotranslate", shapepolygonundoredotranslate);
      labels.put("undoredoresize", shapepolygonundoredoresize);
      labels.put("undoredomodifypoint", shapepolygonundoredomodifypoint);
      labels.put("undoredorotate", shapepolygonundoredorotate);
      labels.put("helpcreate", shapepolygonhelpcreate);
    }catch (Exception ex){}
    
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format=new DecimalFormat("######.#",symbols);
    polygonAction=new PolygonActionListener();
    
    //a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabChangedListener=new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
					svgToolItem.setEnabled(true);
					//svgToolItem.setIcon(icon);
				}else{
					svgToolItem.setEnabled(false);
					//svgToolItem.setIcon(disabledIcon);
				}
				polygonAction.reset();
			}
		};
		//adds the SVGTab change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    svgToolItem=getStudio().getSvgToolBar().getStbPolygon();
    svgToolItem.addActionListener(polygonAction);
  }
  
  /**
   * 
   * @description 
   * @param points
   * @param svgTab
   */
  protected void drawPolygon(SVGTab svgTab, Point[] points){
    if(svgTab!=null && points!=null && points.length>1){
			SVGDocument document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
			if(getStudio().getSvgSelection()!=null && document!=null){
				final Element parent=getStudio().getSvgSelection().getCurrentParentElement(svgTab);
				if(parent!=null){
					String value="";
					for(int i=0;i<points.length;i++){
						value=value.concat(format.format(points[i].getX())+","+format.format(points[i].getY())+" ");
					}
			
					//creates the polygon
					final Element polygon=document.createElementNS(document.getDocumentElement().getNamespaceURI(),"polygon");

					polygon.setAttributeNS(null,"points",value);
					String colorString=getStudio().getColorChooser().getColorString(getStudio().getSvgColorManager().getCurrentColor());
					polygon.setAttributeNS(null, "style", "fill:".concat(colorString.concat(";stroke:none;")));
			
					//sets that the svg has been modified
					svgTab.setModified(true);

					// attaches the element to the svg parent element	
					parent.appendChild(polygon);
			
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredocreate")){
							public void undo(){
							  parent.removeChild(polygon);
							}
							
							public void redo(){
							  parent.appendChild(polygon);
							}
						};
				
						SVGSelection selection=getStudio().getSvgSelection();
				
						if(selection!=null){
							selection.deselectAll(svgTab, false, true);
							selection.addUndoRedoAction(svgTab, action);
							selection.handleNodeSelection(svgTab, polygon);
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
   * @param points
   */
  protected void drawGhost(SVGTab svgTab, Graphics2D g2D, Point2D.Double[] points){
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
   * @param svgTab
   * @param g
   * @param node
   */
  protected LinkedList drawModifyPointsSelection(SVGTab svgTab, Graphics g, Node node){
    LinkedList squareList=new LinkedList();
		Element elementNode=(Element)node;
		Graphics2D g2D=(Graphics2D)g;
		if(svgTab!=null && g2D!=null && elementNode!=null){
			int squareDistance=5;
			String pointsString=elementNode.getAttributeNS(null,"points");
			if(pointsString!=null && !pointsString.equals("")){
				double px=0;
        double py=0;
				//gets the coordinates of the points
				Point2D.Double[] points=null;
				ArrayList list=new ArrayList();
				
				pointsString=pointsString.replaceAll("[,]"," ");
				//removes the first space characters from the string
				if(!pointsString.equals("") && pointsString.charAt(0)==' '){
				    pointsString=pointsString.replaceFirst("[\\s]+","");
				}
				while(! pointsString.equals("")){
					try{
						px=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
						  pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						py=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
						  pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						list.add(new Point2D.Double(px, py));
					}catch (Exception ex){break;}
				}
				
				//the values of the coordinates of the points
				points=new Point2D.Double[list.size()];
				for(int i=0;i<list.size();i++){
				  points[i]=(Point2D.Double)list.get(i);
				}
				
				if(points!=null && points.length>0){
					//computes the transformed points
					BridgeContext bridgeContext=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getBridgeContext();
					if(bridgeContext!=null){
						GraphicsNode gnode=bridgeContext.getGraphicsNode(elementNode);
						if(gnode!=null){
							AffineTransform affineTransform=gnode.getTransform();
							if(affineTransform!=null){
								try{affineTransform.transform(points,0,points,0,points.length);}catch (Exception e){}
							}
						}
					}
					
					//computes the coordinates of the selection squares
					Point2D.Double[] scaledPoints=new Point2D.Double[points.length];
					for(int i=0;i<points.length;i++){
						scaledPoints[i]=getStudio().getSvgToolkit().getScaledPoint(svgTab, points[i], false);
					}

					//the cursor associated with the selection points
					Cursor cursor=new Cursor(Cursor.HAND_CURSOR);
		
					//draws the selection
					Shape shape=null;
					GradientPaint gradient=null;
					int squareX=0;
          int squareY=0;
					
					for(int i=0;i<scaledPoints.length;i++){
			
						squareX=(int)(scaledPoints[i].getX()-squareDistance);
						squareY=(int)(scaledPoints[i].getY()-squareDistance);
			
						if(getStudio().getSvgSelection()!=null){
							squareList.add(new SVGSelectionSquare(elementNode, new Integer(i).toString(),
														new Rectangle2D.Double(squareX, squareY, 2*squareDistance, 2*squareDistance),
														cursor));
						}
            
						shape=getArrow(new Point(squareX+squareDistance, squareY+squareDistance), "P", REGULAR_SELECTION);
						
            if(shape!=null){
							gradient=new GradientPaint(squareX, squareY, SQUARE_SELECTION_COLOR1, squareX+2*squareDistance, squareY+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
							g2D.setPaint(gradient);
							g2D.fill(shape);
							g2D.setColor(LINE_SELECTION_COLOR);
							g2D.draw(shape);
						}
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
			
			Element elementNode=(Element)square.getNode();
			String pointsString=elementNode.getAttributeNS(null,"points");
			
			if(pointsString!=null && ! pointsString.equals("")){
			
				double px=0;
        double py=0;
				
				//gets the coordinates of the points
				ArrayList listpt=new ArrayList();
				
        pointsString=pointsString.replaceAll("[,]"," ");
				
				//removes the first space characters from the string
				if(!pointsString.equals("") && pointsString.charAt(0)==' '){
				  pointsString=pointsString.replaceFirst("[\\s]+","");
				}
				 
				while(! pointsString.equals("")){
					try{
						px=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
              pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						
						py=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
						  pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						listpt.add(new Point2D.Double(px, py));
					}catch (Exception ex){break;}
				}
				
				//the transform matrix
				SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(elementNode);
				
				//the affine transform
				AffineTransform affineTransform=matrix.getTransform();
				
				//the path will be displayed
				GeneralPath path=new GeneralPath();
				Point2D.Double pt0=(Point2D.Double)listpt.get(0), pt=null;
				
				//the initial point
				path.moveTo((float)pt0.x, (float)pt0.y);
				
				//building the path
				for(int i=1;i<listpt.size();i++){
				    
				    pt=(Point2D.Double)listpt.get(i);
				    path.lineTo((float)pt.x, (float)pt.y);
				}
				
				//transforming the path//
				try{
				    path=(GeneralPath)affineTransform.createTransformedShape(path);
				}catch (Exception ex){}
				
				//the index of the moved point
				int index=-1;
				
				try{
				    index=Integer.parseInt(square.getType());
				}catch(Exception ex){}
				
				//creating the new path
				GeneralPath endPath=new GeneralPath();
				float[] segment=new float[6];
				
        int i=0; 
				for(PathIterator it=path.getPathIterator(new AffineTransform()); ! it.isDone(); i++){
          //getting the current segment
          it.currentSegment(segment);
          it.next();
          
          try{
            if(i==0 && i!=index){
              endPath.moveTo(segment[0], segment[1]);
            }else if(i>0 && i!=index){
              endPath.lineTo(segment[0], segment[1]);
            }else if(index>=0 && index==i){ //modifying the moved point
              if(i==0){
                endPath.moveTo(point2.x, point2.y);
              }else if(i>0){
                endPath.lineTo(point2.x, point2.y);
              }
            }
          }catch (Exception ex){}
				}

				//computes the scale and translate values
				if(endPath!=null){
				  //closing the path
				  endPath.closePath();
				   
					//concatenates the transforms to draw the outline
					double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
					affineTransform=AffineTransform.getScaleInstance(scale, scale);
					
					Dimension translateValues=svgTab.getScrollPane().getTranslateValues();
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));

					//computing the outline
					final Shape outline=affineTransform.createTransformedShape(endPath);

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
			
			final Element elementNode=(Element)square.getNode();
			final String finalPointsString=elementNode.getAttributeNS(null,"points");
			String pointsString=new String(finalPointsString);
			
			if(pointsString!=null && ! pointsString.equals("")){
				double px=0;
        double py=0;
				
        //gets the coordinates of the points
				ArrayList listpt=new ArrayList();
				pointsString=pointsString.replaceAll("[,]"," ");
				
				//removes the first space characters from the string
				if(!pointsString.equals("") && pointsString.charAt(0)==' '){
				  pointsString=pointsString.replaceFirst("[\\s]+","");
				}
				 
				while(!pointsString.equals("")){
					try{
						px=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
						  pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						
						py=new Double(pointsString.substring(0,pointsString.indexOf(' '))).doubleValue();
						pointsString=pointsString.substring(pointsString.indexOf(' ')+1,pointsString.length());
						
						if(!pointsString.equals("") && pointsString.charAt(0)==' '){
						  pointsString=pointsString.replaceFirst("[\\s]+","");
						}
						
						listpt.add(new Point2D.Double(px, py));
					}catch (Exception ex){break;}
				}
				
				//the transform matrix
				final SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(elementNode);
				
				//the affine transform
				AffineTransform affineTransform=matrix.getTransform();
				
				//the path will be displayed
				GeneralPath path=new GeneralPath();
				Point2D.Double pt0=(Point2D.Double)listpt.get(0);
        Point2D.Double pt=null;
				
				//the initial point
				path.moveTo((float)pt0.x, (float)pt0.y);
				
				//building the path
				for(int i=1;i<listpt.size();i++){
				    pt=(Point2D.Double)listpt.get(i);
				    path.lineTo((float)pt.x, (float)pt.y);
				}
				
				//transforming the path//
				try{
				    path=(GeneralPath)affineTransform.createTransformedShape(path);
				}catch (Exception ex){}
				
				//the index of the moved point
				int index=-1;
				
				try{
				  index=Integer.parseInt(square.getType());
				}catch(Exception ex){}
				
				//creating the new string path
				String points="";
				float[] segment=new float[6];
				int i=0;

				for(PathIterator it=path.getPathIterator(new AffineTransform()); ! it.isDone();){
          //getting the current segment
          it.currentSegment(segment);
          it.next();
          if(index!=i){
            points=points.concat(format.format(segment[0]).concat(" , ").concat(format.format(segment[1]).concat(" ")));
          }else{
            points=points.concat(format.format(point2.x).concat(" , ").concat(format.format(point2.y).concat(" ")));
          }
          
          i++;
				}

				//computes the scale and translate values
				if(points!=null && ! points.equals("")){

				  //applying the modifications
					getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));
					elementNode.setAttributeNS(null, "points", points);
					
					//create the undo/redo action and insert it into the undo/redo stack
					if(getStudio().getSvgUndoRedo()!=null){
					  final String finalPoints=points;
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredomodifypoint")){
							public void undo(){
								getStudio().getSvgToolkit().setTransformMatrix(elementNode, matrix);
								elementNode.setAttributeNS(null, "points", finalPointsString);
								
								//notifies that the selection has changed
								if(getStudio().getSvgSelection()!=null){
									getStudio().getSvgSelection().selectionChanged(true);
								}
							}

							public void redo(){
								getStudio().getSvgToolkit().setTransformMatrix(elementNode, new SVGTransformMatrix(1, 0, 0, 1, 0, 0));
								elementNode.setAttributeNS(null, "points", finalPoints);
								
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
						actionlist=null;
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(polygonAction!=null)polygonAction.cancelActions();
  }
  /**
   * 
   * @description 
   * @version 1.0 25-Aug-2005
   * @author Sudheer V. Pujar
   */
  protected class PolygonActionListener implements ActionListener{
    
    private final Hashtable  mouseAdapterSvgTabs=new Hashtable();
    private final PolygonActionListener  action=this;
    private Cursor  createCursor;
    private Object  source=null;
    private boolean isActive=false;
  
    /**
     * 
     * @description 
     */
    protected PolygonActionListener(){
      createCursor=getStudio().getSvgCursors().getCursor("polygon");
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

				PolygonMouseListener polygonMouseListener=null;
				
				//adds the new motion adapters
				for(it=svgTabs.iterator(); it.hasNext();){
					try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
					
          if(svgTab!=null && ! mouseAdapterSvgTabs.containsKey(svgTab)){
						polygonMouseListener=new PolygonMouseListener(svgTab);
						try{
							svgTab.getScrollPane().getSvgCanvas().addMouseListener(polygonMouseListener);
							svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(polygonMouseListener);
							svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
						}catch (Exception ex){}
						mouseAdapterSvgTabs.put(svgTab, polygonMouseListener);
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
							
							if(mouseListener!=null && ((PolygonMouseListener)mouseListener).paintListener!=null){
								//removes the paint listener
								svgTab.getScrollPane().getSvgCanvas().removePaintListener(((PolygonMouseListener)mouseListener).paintListener, true);
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
        PolygonMouseListener polygonMouseListener=null;
        
        //adds the new motion adapters
        for(it=frames.iterator(); it.hasNext();){
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
        
          if(svgTab!=null){
            polygonMouseListener=new PolygonMouseListener(svgTab);
            try{
              svgTab.getScrollPane().getSvgCanvas().addMouseListener(polygonMouseListener);
              svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(polygonMouseListener);
              svgTab.getScrollPane().getSvgCanvas().setSVGCursor(createCursor);
            }catch (Exception ex){}
            mouseAdapterSvgTabs.put(svgTab, polygonMouseListener);
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
  protected class PolygonMouseListener extends MouseAdapter implements MouseMotionListener{
  
    private Point lastPoint=null;
    private ArrayList points=new ArrayList();
    private SVGTab svgTab;
    private SVGPaintListener paintListener;
  
    /**
     * 
     * @description 
     * @param svgTab
     */
    public PolygonMouseListener(SVGTab svgTab){
      this.svgTab=svgTab;
      final SVGTab finalSvgTab=svgTab;
      //adds a paint listener
      paintListener=new SVGPaintListener(){
        public void paintToBeDone(Graphics g) {
          if(points.size()>1){
            //draws the shape of the polygon that will be created if the user released the mouse button
            Point2D.Double[] scaledPoints=new Point2D.Double[points.size()];
            Point point=null;
            for(int i=0;i<scaledPoints.length;i++){
              point=(Point)points.get(i);
              scaledPoints[i]=getStudio().getSvgToolkit().getScaledPoint(finalSvgTab, new Point2D.Double(point.x, point.y), false);
            }
            
            if(g!=null && scaledPoints!=null){
              svgPolygon.drawGhost(finalSvgTab, (Graphics2D)g, scaledPoints);
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
      //asks the canvas to be repainted to draw the shape of the future polygon
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
            //creates the polygon in the SVG document
            final Point[] finalPoints=new Point[points.size()-1];
            for(int i=0;i<finalPoints.length;i++){
              finalPoints[i]=(Point)points.get(i);
            }
            final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
            if(queue.getThread()!=Thread.currentThread()){
              queue.invokeLater(new Runnable(){
                public void run(){
                  svgPolygon.drawPolygon(svgTab, finalPoints);
                }
              });
            }else{
              svgPolygon.drawPolygon(svgTab, finalPoints);
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