package bulbul.foff.studio.engine.shapes;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGTransformMatrix;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.selection.SVGSelectionSquare;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGCanvas;
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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JToggleButton;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 dd-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGShape implements SVGClassObject {
  
  protected SVGApplet studio;
  
  protected String[] selectionsOrder=new String[4];
  
  protected Hashtable ids=new Hashtable();
  protected Hashtable labels=new Hashtable();
  
  protected static final Color LOCK_COLOR=new Color(100,100,100);
  protected static final Color OUTLINE_COLOR=Color.BLUE;
  protected static final Color SQUARE_SELECTION_COLOR1=new Color(141, 168, 255);
  protected static final Color LINE_SELECTION_COLOR=new Color(75, 100, 200);
  protected static final Color GHOST_COLOR=new Color(0,0,0);
  protected static final Color OUTLINE_FILL_COLOR=new Color(0, 0, 255, 50);
  protected static final Color SQUARE_SELECTION_COLOR2=new Color(38, 76, 135);
  
  protected final Map paintListenerTable=Collections.synchronizedMap(new Hashtable());
  protected Map translateSvgTabTable=Collections.synchronizedMap(new Hashtable());
  protected Map selectionSquaresTable=Collections.synchronizedMap(new Hashtable());
  protected Map rotateSvgTabTable=Collections.synchronizedMap(new Hashtable());
  protected Map modifyPointSvgTabTable=Collections.synchronizedMap(new Hashtable());
  protected Map resizeSvgTabTable=Collections.synchronizedMap(new Hashtable());
  
  protected boolean canRepaintSelection=true;
  
  public static final int  DO_ACTION=0;
  public static final int  VALIDATE_ACTION=1;
  public static final int  DO_TRANSLATE_ACTION=2;
  public static final int  VALIDATE_TRANSLATE_ACTION=3;
  
  public static final int  REGULAR_SELECTION=0;
  public static final int  ROTATE_SELECTION=1;
  
  protected JToggleButton svgToolItem=null;

  /**
   * 
   * @description 
   */
  public SVGShape(SVGApplet studio) {
    this.studio=studio;
    selectionsOrder[0]="level1";
		selectionsOrder[1]="level2";
		selectionsOrder[2]="level3";
		selectionsOrder[3]="lock";
		
		//a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabListener=new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        SVGTab svgTab=null;
        Collection currentTabs=getStudio().getSvgTabManager().getSvgTabs();
        
        //creating the list of all the frames stored in one of the maps
        HashSet frames=new HashSet();
        
        frames.addAll(paintListenerTable.keySet());
        frames.addAll(selectionSquaresTable.keySet());
        frames.addAll(translateSvgTabTable.keySet());
        frames.addAll(resizeSvgTabTable.keySet());
        frames.addAll(rotateSvgTabTable.keySet());
        frames.addAll(modifyPointSvgTabTable.keySet());
        
        //adds the new mouse motion and key listeners
        for(Iterator it=frames.iterator(); it.hasNext();){
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}
          if(svgTab!=null && ! currentTabs.contains(svgTab)){
            paintListenerTable.remove(svgTab);
            selectionSquaresTable.remove(svgTab);
            translateSvgTabTable.remove(svgTab);
            resizeSvgTabTable.remove(svgTab);
            rotateSvgTabTable.remove(svgTab);
            modifyPointSvgTabTable.remove(svgTab);
          }
        }
  			svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
        if(svgTab==null){
					getStudio().cancelActions(true);
				}
			}
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabListener);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param originPoint
   * @param place
   * @param type
   */
  protected Shape getArrow(Point originPoint, String place, int type){
    Shape shape=null;
		AffineTransform affineTransform=new AffineTransform();
		if(place!=null && place.equals("C")){
			GeneralPath path=new GeneralPath();
			path.moveTo(0, -4);
			path.lineTo(-3, -4);
			path.lineTo(-4, -3);
			path.lineTo(-4, 3);
			path.lineTo(-3, 4);
			path.lineTo(4, 4);
			path.lineTo(4, 0);
			path.lineTo(6, 0);
			path.lineTo(3, -2);
			path.lineTo(0, 0);
			path.lineTo(2, 0);
			path.lineTo(2, 2);
			path.lineTo(-1, 2);
			path.lineTo(-2, 1);
			path.lineTo(-2, -2);
			path.lineTo(0, -2);
			path.lineTo(0, -4);
			affineTransform.preConcatenate(AffineTransform.getTranslateInstance(originPoint.x, originPoint.y));
			shape=path.createTransformedShape(affineTransform);
		}else if(place!=null && (place.equals("P") || place.equals("Begin") || place.equals("End"))){
			Rectangle2D.Double rect=new Rectangle2D.Double(-2.5, -2.5, 5, 5);
			affineTransform.preConcatenate(AffineTransform.getTranslateInstance(originPoint.x, originPoint.y));
			shape=affineTransform.createTransformedShape(rect);
		}else if(place!=null && place.equals("Ctrl")){
			GeneralPath path=new GeneralPath();
			path.moveTo(0, -4);
			path.lineTo(-3, 0);
			path.lineTo(0, 4);
			path.lineTo(3, 0);
			path.lineTo(0, -4);
			affineTransform.preConcatenate(AffineTransform.getTranslateInstance(originPoint.x, originPoint.y));
			shape=path.createTransformedShape(affineTransform);
		}else if(place!=null){
			GeneralPath path=new GeneralPath();
			path.moveTo(0, -5);
			path.lineTo(-3, -2);
			path.lineTo(-1, -2);
			path.lineTo(-1, 2);	
			path.lineTo(-3, 2);		
			path.lineTo(0, 5);
			path.lineTo(3, 2);
			path.lineTo(1, 2);
			path.lineTo(1, -2);
			path.lineTo(3, -2);
			path.lineTo(0, -5);
			double angle=0;
			if(place.equals("N") || place.equals("S")){
				if(type==REGULAR_SELECTION){
					angle=0;
				}else if(type==ROTATE_SELECTION){
					angle=Math.PI/2;
				}
			}else if(place.equals("E") || place.equals("W")){
				if(type==REGULAR_SELECTION){
					angle=Math.PI/2;
				}else if(type==ROTATE_SELECTION){
					angle=0;
				}
			}else if(place.equals("NW") || place.equals("SE")){
				angle=-Math.PI/4;
			}else if(place.equals("NE") || place.equals("SW")){
				angle=Math.PI/4;
			}
			affineTransform.preConcatenate(AffineTransform.getRotateInstance(angle));
			affineTransform.preConcatenate(AffineTransform.getTranslateInstance(originPoint.x, originPoint.y));
			shape=path.createTransformedShape(affineTransform);
		}
		return shape;
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param table
   * @param g
   */
  protected void manageSelection(SVGTab svgTab, Hashtable table, Graphics g){
    //gets the hashtable associated with the frame
		Hashtable selectionSquares=null;
		try{
			selectionSquares=(Hashtable)selectionSquaresTable.get(svgTab);
		}catch (Exception ex){selectionSquares=null;}
			
		if(selectionSquares!=null){
		    selectionSquares.clear();
		}else{
			selectionSquares=new Hashtable();
			selectionSquaresTable.put(svgTab, selectionSquares);
		}
		if(table!=null && table.size()>0){
			Node node=null;
			LinkedList squares=null;
			String type="";
			//for each node in the table, the accurate "drawSelection" method is called given the type of the selection
			for(Iterator it=table.keySet().iterator(); it.hasNext();){
				try{node=(Node)it.next();}catch (Exception e){node=null;}
				if(node!=null){
					type=(String)table.get(node);
					if(type!=null && ! type.equals("")){
						if(type.equals(selectionsOrder[0])){
							squares=drawSelection(svgTab, g, node);
						}else if(type.equals(selectionsOrder[1])){
							squares=drawRotateSelection(svgTab, g, node);
						}else if(type.equals(selectionsOrder[2])){
							squares=drawModifyPointsSelection(svgTab, g, node);	
						}else if(type.equals(selectionsOrder[3])){
							squares=drawLockedSelection(svgTab, g, node);
						}
            if(squares!=null && squares.size()>0){
						  selectionSquares.put(node, squares);
						}
					}
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param graphics
   * @param node
   */
  protected LinkedList drawSelection(SVGTab svgTab, Graphics graphics, Node node){
    LinkedList squarelist=new LinkedList();
		Graphics2D g=(Graphics2D)graphics;
		if(svgTab!=null && g!=null && node!=null){
			int squareDistance=5;
			Rectangle2D rectangle2D=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			if(rectangle2D!=null){
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
				Rectangle2D.Double scaledRectangle2DD=getStudio().getSvgToolkit().getScaledRectangle(svgTab, rectangle2DD, false);
				if(scaledRectangle2DD!=null){
					double x=scaledRectangle2DD.getX(); 
          double y=scaledRectangle2DD.getY();
          double width=scaledRectangle2DD.getWidth();
          double height=scaledRectangle2DD.getHeight();
          
					int[] squareX=new int[8];
					int[] squareY=new int[8];
					
					//the coordinates of the selection points
					squareX[0]=(int)x-2*squareDistance;
					squareX[1]=(int)(x+width/2)-squareDistance;
					squareX[2]=(int)(x+width);
					squareX[3]=squareX[2];
					squareX[4]=squareX[2];
					squareX[5]=squareX[1];
					squareX[6]=squareX[0];
					squareX[7]=squareX[0];

					squareY[0]=(int)y-2*squareDistance;
					squareY[3]=(int)(y+height/2)-squareDistance;
					squareY[4]=(int)(y+height);
					squareY[1]=squareY[0];
					squareY[2]=squareY[0];
					squareY[5]=squareY[4];
					squareY[6]=squareY[4];
					squareY[7]=squareY[3];
			
					//the ids of the selection squares
					String[] types=new String[8];
					types[0]="NW";
					types[1]="N";
					types[2]="NE";
					types[3]="E";
					types[4]="SE";
					types[5]="S";
					types[6]="SW";
					types[7]="W";
			
					//the cursors associated with the selection square
					Cursor[] cursors=new Cursor[8];
					cursors[0]=new Cursor(Cursor.NW_RESIZE_CURSOR);
					cursors[1]=new Cursor(Cursor.N_RESIZE_CURSOR);
					cursors[2]=new Cursor(Cursor.NE_RESIZE_CURSOR);
					cursors[3]=new Cursor(Cursor.E_RESIZE_CURSOR);
					cursors[4]=new Cursor(Cursor.SE_RESIZE_CURSOR);
					cursors[5]=new Cursor(Cursor.S_RESIZE_CURSOR);
					cursors[6]=new Cursor(Cursor.SW_RESIZE_CURSOR);
					cursors[7]=new Cursor(Cursor.W_RESIZE_CURSOR);

					//an array of indices
					int i;
					int[] tin=null;
					if(width>2*squareDistance && height>2*squareDistance){
						int[] tmp=new int[8];
						for(i=0;i<8;i++){
						  tmp[i]=i;
						}
						tin=tmp;
					}else if((width<=2*squareDistance && height>2*squareDistance) || (width>2*squareDistance && height<=2*squareDistance) || (width<=2*squareDistance && height<=2*squareDistance)){
						int[] tmp={1,3,5,7};
						tin=tmp;
					}
			
					//draws the graphic elements
					Shape shape=null;
					GradientPaint gradient=null;
					
					for(i=0;i<tin.length;i++){
						if(getStudio().getSvgSelection()!=null){
							squarelist.add(new SVGSelectionSquare(node, types[tin[i]], 
														new Rectangle2D.Double(squareX[tin[i]],squareY[tin[i]],2*squareDistance,2*squareDistance), 
														cursors[tin[i]]));
            }
						shape=getArrow(new Point(squareX[tin[i]]+squareDistance, squareY[tin[i]]+squareDistance), types[tin[i]], REGULAR_SELECTION);
						if(shape!=null){
							gradient=new GradientPaint(squareX[tin[i]], squareY[tin[i]], SQUARE_SELECTION_COLOR1, squareX[tin[i]]+2*squareDistance, squareY[tin[i]]+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
							g.setPaint(gradient);
							g.fill(shape);
							g.setColor(LINE_SELECTION_COLOR);
							g.draw(shape);
						}
					}
				}
			}
		}
		return squarelist;		
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param graphics
   * @param node
   */
  protected LinkedList drawLockedSelection(SVGTab svgTab, Graphics graphics, Node node){
    Graphics2D g=(Graphics2D)graphics;
		if(svgTab!=null && g!=null && node!=null){
			int sqareDistance=5;
			Rectangle2D rectangle2D=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			if(rectangle2D!=null){
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
				Rectangle2D.Double scaledRectangle2DD=getStudio().getSvgToolkit().getScaledRectangle(svgTab, rectangle2DD, false);

				double x=scaledRectangle2DD.getX(); 
        double y=scaledRectangle2DD.getY();
        double width=scaledRectangle2DD.getWidth();
        double height=scaledRectangle2DD.getHeight();

				int[] squareX=new int[8];
				int[] squareY=new int[8];
				
				//the coordinates of the selection points
				squareX[0]=(int)x-2*sqareDistance;
				squareX[1]=(int)(x+width/2)-sqareDistance;
				squareX[2]=(int)(x+width);
				squareX[3]=squareX[2];
				squareX[4]=squareX[2];
				squareX[5]=squareX[1];
				squareX[6]=squareX[0];
				squareX[7]=squareX[0];

				squareY[0]=(int)y-2*sqareDistance;
				squareY[3]=(int)(y+height/2)-sqareDistance;
				squareY[4]=(int)(y+height);
				squareY[1]=squareY[0];
				squareY[2]=squareY[0];
				squareY[5]=squareY[4];
				squareY[6]=squareY[4];
				squareY[7]=squareY[3];
				
				//the ids of the selection squares
				String[] types=new String[8];
				types[0]="NW";
				types[1]="N";
				types[2]="NE";
				types[3]="E";
				types[4]="SE";
				types[5]="S";
				types[6]="SW";
				types[7]="W";

				//an array of indices
				int i;
				int[] tin=null;
				if(width>2*sqareDistance && height>2*sqareDistance){
					int[] tmp=new int[8];
					for(i=0;i<8;i++){
					  tmp[i]=i;
					}
					tin=tmp;
				}else if((width<=2*sqareDistance && height>2*sqareDistance) || (width>2*sqareDistance && height<=2*sqareDistance) || (width<=2*sqareDistance && height<=2*sqareDistance)){
					int[] tmp={1,3,5,7};
					tin=tmp;
				}

				//draws the graphic elements
				Shape shape=null;
				GradientPaint gradient=null;
				
				for(i=0;i<tin.length;i++){
					shape=getArrow(new Point(squareX[tin[i]]+sqareDistance, squareY[tin[i]]+sqareDistance), types[tin[i]], REGULAR_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(squareX[tin[i]], squareY[tin[i]], LOCK_COLOR, squareX[tin[i]]+2*sqareDistance, squareY[tin[i]]+2*sqareDistance, Color.white, true);
						g.setPaint(gradient);
						g.fill(shape);
						g.setColor(LINE_SELECTION_COLOR);
						g.draw(shape);
					}
				}
			}
		}	
		
		return null;
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param graphics
   * @param node
   */
  protected LinkedList drawRotateSelection(SVGTab svgTab, Graphics graphics, Node node){
    LinkedList squarelist=new LinkedList();
		Graphics2D g=(Graphics2D)graphics;
		if(svgTab!=null && g!=null && node!=null){
			int squareDistance=5;
			Rectangle2D rectangle2D=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			if(rectangle2D!=null){
      
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
				Rectangle2D.Double scaledRectangle2DD=getStudio().getSvgToolkit().getScaledRectangle(svgTab, rectangle2DD, false);
			
				double x=scaledRectangle2DD.getX(); 
        double y=scaledRectangle2DD.getY();
        double width=scaledRectangle2DD.getWidth();
        double height=scaledRectangle2DD.getHeight();

				int[] squareX=new int[5];
				int[] squareY=new int[5];
				
				//the coordinates of the selection points
				squareX[0]=(int)(x+width/2)-squareDistance;
				squareX[1]=(int)(x+width);
				squareX[2]=squareX[0];
				squareX[3]=(int)x-2*squareDistance;
				squareX[4]=(int)(x+width/2-squareDistance);

				squareY[0]=(int)y-2*squareDistance;
				squareY[1]=(int)(y+height/2)-squareDistance;
				squareY[2]=(int)(y+height);
				squareY[3]=squareY[1];
				squareY[4]=(int)(y+height/2-squareDistance);

				//the ids of the selection points			
				String[] types=new String[5];
				types[0]="N";
				types[1]="E";
				types[2]="S";
				types[3]="W";
				types[4]="C";
			
				Cursor[] cursors=new Cursor[5];
				cursors[0]=new Cursor(Cursor.HAND_CURSOR);
				cursors[1]=new Cursor(Cursor.HAND_CURSOR);
				cursors[2]=new Cursor(Cursor.HAND_CURSOR);
				cursors[3]=new Cursor(Cursor.HAND_CURSOR);
				cursors[4]=new Cursor(Cursor.HAND_CURSOR);

				//an array of indices
				int i;
				int[] tin=null;
				
				if(width>2*squareDistance && height>2*squareDistance){
					int[] tmp=new int[5];
					for(i=0;i<5;i++){
					    tmp[i]=i;
					}
					tin=tmp;
				}else{
					int[] tmp={0,1,2,3};
					tin=tmp;
				}
			
				//draws the graphic elements
				Shape shape=null;
				GradientPaint gradient=null;
				for(i=0;i<tin.length;i++){
					if(getStudio().getSvgSelection()!=null){
						squarelist.add(new SVGSelectionSquare(node,types[tin[i]],
													new Rectangle2D.Double(squareX[tin[i]],squareY[tin[i]],2*squareDistance,2*squareDistance),
													cursors[tin[i]]));
					}		
					shape=getArrow(new Point(squareX[tin[i]]+squareDistance, squareY[tin[i]]+squareDistance), types[tin[i]], ROTATE_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(squareX[tin[i]], squareY[tin[i]], SQUARE_SELECTION_COLOR1, squareX[tin[i]]+2*squareDistance, squareY[tin[i]]+2*squareDistance, SQUARE_SELECTION_COLOR2, true);
						g.setPaint(gradient);
						g.fill(shape);
						g.setColor(LINE_SELECTION_COLOR);
						g.draw(shape);
					}
				}
			}
		}
		
		return squarelist;		
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   * @param graphics
   * @param node
   */
  protected LinkedList drawModifyPointsSelection(SVGTab svgTab, Graphics graphics, Node node){
    return null;
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
    
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param square
   * @param point1   
   * @param point2
   */
  public void rotateSkewNode(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && point1!=null && point2!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			try{
				paintListener=(SVGPaintListener)rotateSvgTabTable.get(svgTab);
			}catch (Exception ex){paintListener=null;}

			Node node=square.getNode();
			//the outline and the bounds of the node
			Shape outline=getStudio().getSvgToolkit().getTransformedOutline(svgTab, node);
			Rectangle2D bounds=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			//the transform of the action
			AffineTransform affineTransform=new AffineTransform();

			//computes the scale and translate values taking the type of the selection square into account
			if(bounds!=null && point1!=null && point2!=null){
				double angle=0;
        double centerX=0;
        double centerY=0;
				Point2D.Double centerpoint=null;
				if(square.getType().equals("C")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY()+bounds.getHeight()/2);
					double radialX=0; 
          double radialY=0;
          double radialDifference=0;
					radialDifference=Math.sqrt(Math.pow(point2.x-centerpoint.x,2)+Math.pow(point2.y-centerpoint.y,2));
					radialX=(point2.x-centerpoint.x)/radialDifference;
					radialY=(point2.y-centerpoint.y)/radialDifference;
					if(radialY>=0){
						angle=Math.acos(radialX);
					}else{
						angle=-Math.acos(radialX);
					}
					centerX=centerpoint.x; 
					centerY=centerpoint.y;
					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getRotateInstance(angle));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}else if(square.getType().equals("N")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getHeight()/2,bounds.getY());
					angle=Math.toRadians(point2.x-point1.x);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));			
				}else if(square.getType().equals("S")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth()/2,bounds.getY()+bounds.getHeight());
					angle=Math.toRadians(point2.x-point1.x);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));		
				}else if(square.getType().equals("E")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth(), bounds.getY()+bounds.getHeight()/2);
					angle=Math.toRadians(point2.y-point1.y);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));		
				}else if(square.getType().equals("W")){
					centerpoint=new Point2D.Double(bounds.getX() ,bounds.getY()+bounds.getHeight()/2);
					angle=Math.toRadians(point2.y-point1.y);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}

				//concatenates the transforms to draw the outline
				double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
				affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));

				Dimension translateValues=svgTab.getScrollPane().getTranslateValues();
				affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));
				outline=affineTransform.createTransformedShape(outline);
	
				final Shape finalOutline=outline;
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
        rotateSvgTabTable.put(svgTab, paintListener);
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
  public void validateRotateSkewNode(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			SVGTab svgTab2=null;
			
			for(Iterator it=new LinkedList(rotateSvgTabTable.keySet()).iterator(); it.hasNext();){
				try{
				  svgTab2=(SVGTab)it.next();
					paintListener=(SVGPaintListener)rotateSvgTabTable.get(svgTab2);
				}catch (Exception ex){paintListener=null;}

				if(paintListener!=null){
					rotateSvgTabTable.remove(svgTab);
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
				}
			}

			final Node node=square.getNode();
			Rectangle2D bounds=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			final AffineTransform affineTransform=new AffineTransform();
			
			if(point1!=null && point2!=null && bounds!=null){
				//the values used for computing the rotate or skew values
				double angle=0;
        double centerX=0;
        double centerY=0;
        double angle1=0;
        double angle2=0;
        
				Point2D.Double centerpoint=null;
				if(square.getType().equals("C")){	
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY()+bounds.getHeight()/2);
					double radialX=0;
          double radialY=0;
          double radialDifference=0;
					radialDifference=Math.sqrt(Math.pow(point2.x-centerpoint.x, 2)+Math.pow(point2.y-centerpoint.y, 2));
					radialX=(point2.x-centerpoint.x)/radialDifference;
					radialY=(point2.y-centerpoint.y)/radialDifference;
					if(radialY>=0){
						angle=Math.acos(radialX);
					}else{
						angle=-Math.acos(radialX);
					}
					centerX=centerpoint.x; 
					centerY=centerpoint.y;			

					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getRotateInstance(angle));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}else if(square.getType().equals("N")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth()/2,bounds.getY());
					angle=Math.toRadians(point2.x-point1.x);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	

					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}else if(square.getType().equals("S")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth()/2,bounds.getY()+bounds.getHeight());
					angle=Math.toRadians(point2.x-point1.x);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	

					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}else if(square.getType().equals("E")){
					centerpoint=new Point2D.Double(bounds.getX()+bounds.getWidth(), bounds.getY()+bounds.getHeight()/2);
					angle=Math.toRadians(point2.y-point1.y);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	

					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));
				}else if(square.getType().equals("W")){
					centerpoint=new Point2D.Double(bounds.getX(), bounds.getY()+bounds.getHeight()/2);
					angle=Math.toRadians(point2.y-point1.y);
					centerX=centerpoint.x; 
					centerY=centerpoint.y;	

					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-centerX, -centerY));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(centerX, centerY));									
				}
				if(! affineTransform.isIdentity()){
					//gets, modifies and sets the matrix
				  SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
					matrix.concatenateTransform(affineTransform);
					if(matrix.isMatrixCorrect()){
						getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
						//creates the undo/redo action and insert it into the undo/redo stack
						if(getStudio().getSvgUndoRedo()!=null){
							SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredorotate")){
								public void undo(){
									//gets, modifies and sets the matrix
								  SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									try{
                    matrix.concatenateTransform(affineTransform.createInverse());
                  }catch (Exception ex){}

									if(matrix.isMatrixCorrect()){
										getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
									}
									
									//notifies that the selection has changed
									if(getStudio().getSvgSelection()!=null){
										getStudio().getSvgSelection().selectionChanged(true);
									}
								}

								public void redo(){
									//gets, modifies and sets the matrix
								  SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									matrix.concatenateTransform(affineTransform);
									
									if(matrix.isMatrixCorrect()){
										getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
									}
									
									//notifies that the selection has changed
									if(getStudio().getSvgSelection()!=null){
										getStudio().getSvgSelection().selectionChanged(true);
									}
								}
							};
							
							//gets or creates the undo/redo list and adds the action into it
							SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredorotate"));
							actionlist.add(action);
			
							getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);	
							actionlist=null;	
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
   * @param list   
   * @param translationValues
   */
  public void translateNodes(SVGTab svgTab, LinkedList list, Dimension translationValues){
    
    if(svgTab!=null && list!=null && list.size()>0 && translationValues!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=(SVGPaintListener)translateSvgTabTable.get(svgTab);
			if(translationValues!=null){
				Shape outline=null;
				Node node=null;
				AffineTransform affineTransform=null;
				SVGTransformMatrix matrix=null;
				final LinkedList outlines=new LinkedList();
				double scale=1.0;
				Dimension translateValues=null;
				
				//for each node, creates the outline
				for(Iterator it=list.iterator(); it.hasNext();){
					try{
            node=(Node)it.next();
          }catch (Exception ex){node=null;}
					
					if(node!=null){
						//the outline and the bounds of the node
						outline=getStudio().getSvgToolkit().getTransformedOutline(svgTab, node);
						affineTransform=AffineTransform.getTranslateInstance(translationValues.width, translationValues.height);
						
						//concatenates the transforms to draw the outline
						scale=svgTab.getScrollPane().getSvgCanvas().getScale();
						affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
						
						translateValues=svgTab.getScrollPane().getTranslateValues();
						affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));	

						outline=affineTransform.createTransformedShape(outline);
						outlines.add(outline);
					}
					
					//removes the paint listener
					if(paintListener!=null){
            translateSvgTabTable.remove(svgTab);
            svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
					}

					//creates and sets the paint listener
					paintListener=new SVGPaintListener(){
						public void paintToBeDone(Graphics g) {
							Graphics2D g2D=(Graphics2D)g;
							Color color=g2D.getColor();
							Shape outline=null;
							for(int i=0;i<outlines.size();i++){
								try{
									outline=(Shape)outlines.get(i);
								}catch (Exception ex){outline=null;}
								if(outline!=null){
									g2D.setColor(OUTLINE_FILL_COLOR);
									g2D.fill(outline);
									g2D.setColor(OUTLINE_COLOR);
									g2D.draw(outline);
								}
							}
							
							g2D.setColor(color);
						}
					};
					svgTab.getScrollPane().getSvgCanvas().addDrawLayerPaintListener(paintListener, true);
					translateSvgTabTable.put(svgTab, paintListener);
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param list   
   * @param translationValues
   */
  public void validateTranslateNodes(SVGTab svgTab, LinkedList list, Dimension translationValues){
    if(svgTab!=null && list!=null && list.size()>0 && translationValues!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			SVGTab svgTab2=null;
			
			for(Iterator it=new LinkedList(translateSvgTabTable.keySet()).iterator(); it.hasNext();){
				try{
				    svgTab2=(SVGTab)it.next();
					paintListener=(SVGPaintListener)translateSvgTabTable.get(svgTab2);
				}catch (Exception ex){paintListener=null;}
				//removes the paint listener
				if(paintListener!=null){
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, true);
					translateSvgTabTable.remove(svgTab);
				}
			}
			
			final Hashtable transformMap=new Hashtable();
			SVGTransformMatrix matrix;
			AffineTransform affineTransform=null;
			Node node=null;
			//for each node, sets the new matrix transform
			for(Iterator it=list.iterator(); it.hasNext();){
				try{node=(Node)it.next();}catch (Exception ex){node=null;}
				if(node!=null){
					affineTransform=AffineTransform.getTranslateInstance(translationValues.width, translationValues.height);
					transformMap.put(node, affineTransform);
					if(! affineTransform.isIdentity()){
						//gets, modifies and sets the matrix
						matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
						matrix.concatenateTransform(affineTransform);
            getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
					}
				}
			}

			//creates the undo/redo action and insert it into the undo/redo stack
			if(getStudio().getSvgUndoRedo()!=null){
				SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredotranslate")){
					public void undo(){
						SVGTransformMatrix matrix=null;
						AffineTransform affineTransform=null;
						Node node=null;
						for(Iterator it=transformMap.keySet().iterator(); it.hasNext();){
							try{node=(Node)it.next();}catch (Exception ex){node=null;}
							if(node!=null){
								affineTransform=(AffineTransform)transformMap.get(node);
								if(affineTransform!=null && ! affineTransform.isIdentity()){
									//gets, modifies and sets the matrix
									matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									try{
                    matrix.concatenateTransform(affineTransform.createInverse());
                  }catch (Exception ex){}
									getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
								}
							}
						}
						
						//notifies that the selection has changed
						if(getStudio().getSvgSelection()!=null){
							getStudio().getSvgSelection().selectionChanged(true);
						}
					}

					public void redo(){
						SVGTransformMatrix matrix=null;
						AffineTransform affineTransform=null;
						Node node=null;
						for(Iterator it=transformMap.keySet().iterator(); it.hasNext();){
							try{
                node=(Node)it.next();
              }catch (Exception ex){node=null;}
							if(node!=null){
								affineTransform=(AffineTransform)transformMap.get(node);
								
								if(affineTransform!=null && ! affineTransform.isIdentity()){
									//gets, modifies and sets the matrix
									matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									matrix.concatenateTransform(affineTransform);
									getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
								}
							}
						}
						
						//notifies that the selection has changed
						if(getStudio().getSvgSelection()!=null){
							getStudio().getSvgSelection().selectionChanged(true);
						}
					}
				};

				if(getStudio().getSvgSelection()!=null){
          getStudio().getSvgSelection().addUndoRedoAction(svgTab, action);
				}else{
					//gets or creates the undo/redo list and adds the action into it
					SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredotranslate"));
					actionlist.add(action);
					getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);	
					actionlist=null;
				}
			}
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param type
   * @param action
   * @param args
   */
  public void doActions(SVGTab svgTab, String type, int action, Object[] args){
    RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
    final SVGTab finalSvgTab=svgTab;
    final Object[] fargs=args;
	    
		svgTab.getScrollPane().getSvgCanvas().setWaitCursorEnabled(true);
	    
		if(type!=null && ! type.equals("")){
			//if it is a translation action
			if(action==DO_TRANSLATE_ACTION || action==VALIDATE_TRANSLATE_ACTION){
				if(action==DO_TRANSLATE_ACTION){
					setCanRepaintSelection(false);
					try{
						translateNodes(svgTab, (LinkedList)args[0], (Dimension)args[1]);
					}catch (Exception ex){return;}
				}else if(action==VALIDATE_TRANSLATE_ACTION){
          Runnable runnable=new Runnable(){
            public void run() {
              setCanRepaintSelection(false);
              try{
                validateTranslateNodes(finalSvgTab, (LinkedList)fargs[0], (Dimension)fargs[1]);
                //sets that the svg has been modified
                finalSvgTab.setModified(true);
              }catch (Exception ex){return;}
              setCanRepaintSelection(true);
            }
          };
				    
					if(queue.getThread()!=Thread.currentThread()){
						queue.invokeLater(runnable);
					}else{
					  runnable.run();
					}
				}	
			}else{
				//make a different action given the current type of selection
				if(type.equals("level1")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						try{
							resizeNode(svgTab, (SVGSelectionSquare)args[0],(Point)args[1],(Point)args[2]);
						}catch (Exception e){return;}
					}else if(action==VALIDATE_ACTION){
					  Runnable runnable=new Runnable(){
              public void run(){
              try{
                validateResizeNode(finalSvgTab, (SVGSelectionSquare)fargs[0],(Point)fargs[1],(Point)fargs[2]);
                //sets that the svg has been modified
                finalSvgTab.setModified(true);
              }catch (Exception e){return;}
        				setCanRepaintSelection(true);		
              }
					  };
					    
						if(queue.getThread()!=Thread.currentThread()){
							queue.invokeLater(runnable);
						}else{
						  runnable.run();
						}
					}		
				}else if(type.equals("level2")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						try{
							rotateSkewNode(svgTab, (SVGSelectionSquare)args[0],(Point)args[3],(Point)args[4]);
						}catch (Exception e){return;}
					}else if(action==VALIDATE_ACTION){
					  Runnable runnable=new Runnable(){
              public void run() {
                try{
                  validateRotateSkewNode(finalSvgTab, (SVGSelectionSquare)fargs[0],(Point)fargs[3],(Point)fargs[4]);
                  //sets that the svg has been modified
                  finalSvgTab.setModified(true);
                }catch (Exception e){return;}
                setCanRepaintSelection(true);		
              }
					  };
						if(queue.getThread()!=Thread.currentThread()){
							queue.invokeLater(runnable);
						}else{
						  runnable.run();
						}
					}				
				}else if(type.equals("level3")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						try{
							modifyPoint(svgTab, (SVGSelectionSquare)args[0],(Point)args[1],(Point)args[2]);
						}catch (Exception e){return;}
					}else if(action==VALIDATE_ACTION){
					  Runnable runnable=new Runnable(){
              public void run() {
                try{
                  validateModifyPoint(finalSvgTab, (SVGSelectionSquare)fargs[0], (Point)fargs[1], (Point)fargs[2]);
        					//sets that the svg document has been modified
        					finalSvgTab.setModified(true);
        				}catch (Exception e){return;}
                  setCanRepaintSelection(true);
              }
					  };
					    
						if(queue.getThread()!=Thread.currentThread()){
							queue.invokeLater(runnable);
						}else{
						  runnable.run();
						}
					}
				}else if(type.equals("locked")){
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
  public void resizeNode(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && point1!=null && point2!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			try{
				paintListener=(SVGPaintListener)resizeSvgTabTable.get(svgTab);
			}catch (Exception ex){paintListener=null;}
			Node node=square.getNode();
			//the outline and the bounds of the node
			Shape outline=getStudio().getSvgToolkit().getTransformedOutline(svgTab, node);
			Point difference=new Point(point2.x-point1.x, point2.y-point1.y);
			//computes the scale and translate values taking the type of the selection square into account
			if(outline!=null){
				Rectangle2D bounds=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
				if(bounds!=null){
					double squareX=1.0;
          double squareY=1.0;
          double transformX=0;
          double transformY=0;
					if(square.getType().equals("NW")){
						squareX=1-difference.x/bounds.getWidth();
						squareY=1-difference.y/bounds.getHeight();
						transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);
						transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);
					
					}else if(square.getType().equals("N")){
						squareY=1-difference.y/bounds.getHeight();
						transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);
					}else if(square.getType().equals("NE")){
						squareX=1+difference.x/bounds.getWidth();
						squareY=1-difference.y/bounds.getHeight();
						transformX=(bounds.getX())*(1-squareX);
						transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);		
						
					}else if(square.getType().equals("E")){			
						squareX=1+difference.x/bounds.getWidth();
						transformX=bounds.getX()*(1-squareX);				
					}else if(square.getType().equals("SE")){
						squareX=1+difference.x/bounds.getWidth();
						squareY=1+difference.y/bounds.getHeight();
						transformX=bounds.getX()*(1-squareX);
						transformY=bounds.getY()*(1-squareY);
					}else if(square.getType().equals("S")){
						squareY=1+difference.y/bounds.getHeight();
						transformY=bounds.getY()*(1-squareY);
					}else if(square.getType().equals("SW")){
						squareX=1-difference.x/bounds.getWidth();
						squareY=1+difference.y/bounds.getHeight();
						transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);
						transformY=(bounds.getY())*(1-squareY);			
					}else if(square.getType().equals("W")){
						squareX=1-difference.x/bounds.getWidth();
						transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);
					}

					//concatenates the transforms to draw the outline
					AffineTransform affineTransform=AffineTransform.getScaleInstance(squareX, squareY);
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(transformX, transformY));
					
					double scale=svgTab.getScrollPane().getSvgCanvas().getScale();
					affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
					
					Dimension translateValues=svgTab.getScrollPane().getTranslateValues();
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));
					
					outline=affineTransform.createTransformedShape(outline);

					final Shape finalOutline=outline;
					
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
          resizeSvgTabTable.put(svgTab, paintListener);
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
  public void validateResizeNode(SVGTab svgTab, SVGSelectionSquare square, Point point1, Point point2){
    if(svgTab!=null && square!=null && square.getNode()!=null && point1!=null && point2!=null){
			//gets the paintlistener associated with the frame
			SVGPaintListener paintListener=null;
			SVGTab svgTab2=null;

			for(Iterator it=new LinkedList(resizeSvgTabTable.keySet()).iterator(); it.hasNext();){
				try{
				  svgTab2=(SVGTab)it.next();
					paintListener=(SVGPaintListener)resizeSvgTabTable.get(svgTab2);
				}catch (Exception ex){paintListener=null;}
				if(paintListener!=null){
					svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, true);
					resizeSvgTabTable.remove(svgTab);
				}
			}
	
			final Node node=square.getNode();
			Point difference=new Point(point2.x-point1.x, point2.y-point1.y);

			//the bounds of the node
			Rectangle2D bounds=getStudio().getSvgToolkit().getNodeBounds(svgTab, node);
			if(bounds!=null){
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
      
        double squareX=1.0;
        double squareY=1.0;
        double transformX=0;
        double transformY=0;
        
        if(square.getType().equals("NW")){
          squareX=1-difference.x/bounds.getWidth();
          squareY=1-difference.y/bounds.getHeight();
          transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);
          transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);
        }else if(square.getType().equals("N")){
          squareY=1-difference.y/bounds.getHeight();
          transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);
        }else if(square.getType().equals("NE")){
          squareX=1+difference.x/bounds.getWidth();
          squareY=1-difference.y/bounds.getHeight();
          transformX=(bounds.getX())*(1-squareX);
          transformY=(bounds.getY()+bounds.getHeight())*(1-squareY);		
        }else if(square.getType().equals("E")){			
          squareX=1+difference.x/bounds.getWidth();
          transformX=bounds.getX()*(1-squareX);				
        }else if(square.getType().equals("SE")){
          squareX=1+difference.x/bounds.getWidth();
          squareY=1+difference.y/bounds.getHeight();
          transformX=bounds.getX()*(1-squareX);
          transformY=bounds.getY()*(1-squareY);
        }else if(square.getType().equals("S")){
          squareY=1+difference.y/bounds.getHeight();
          transformY=bounds.getY()*(1-squareY);
        }else if(square.getType().equals("SW")){
          squareX=1-difference.x/bounds.getWidth();
          squareY=1+difference.y/bounds.getHeight();
          transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);
          transformY=(bounds.getY())*(1-squareY);			
        }else if(square.getType().equals("W")){
          squareX=1-difference.x/bounds.getWidth();
          transformX=(bounds.getX()+bounds.getWidth())*(1-squareX);	
        }
        final AffineTransform affineTransform=AffineTransform.getScaleInstance(squareX, squareY);
        affineTransform.preConcatenate(AffineTransform.getTranslateInstance(transformX, transformY));
        if(! affineTransform.isIdentity()){
					//gets, modifies and sets the matrix
					SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
					matrix.concatenateTransform(affineTransform);

					if(matrix.isMatrixCorrect()){
						getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
						//create the undo/redo action and insert it into the undo/redo stack
						if(getStudio().getSvgUndoRedo()!=null){
							SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredoresize")){
								public void undo(){
									//gets, modifies and sets the matrix
								    SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									try{
                    matrix.concatenateTransform(affineTransform.createInverse());
                  }catch (Exception ex){}

									if(matrix.isMatrixCorrect()){
										getStudio().getSvgToolkit().setTransformMatrix(node, matrix);
									}
									
									//notifies that the selection has changed
									if(getStudio().getSvgSelection()!=null){
										getStudio().getSvgSelection().selectionChanged(true);
									}
								}

								public void redo(){
									//gets, modifies and sets the matrix
								  SVGTransformMatrix matrix=getStudio().getSvgToolkit().getTransformMatrix(node);
									matrix.concatenateTransform(affineTransform);
									if(matrix.isMatrixCorrect()){
										getStudio().getSvgToolkit().setTransformMatrix(node, matrix);  
									}
									
									//notifies that the selection has changed
									if(getStudio().getSvgSelection()!=null){
										getStudio().getSvgSelection().selectionChanged(true);
									}
								}
							};
							
							//gets or creates the undo/redo list and adds the action into it
							SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredoresize"));
							actionlist.add(action);

							getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);	
							actionlist=null;
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
   * @param table
   */
  public void select(SVGTab svgTab, Hashtable table){
    //gets the hashtables associated with the frame
		Hashtable selectionSquares=null;
		
		try{
			selectionSquares=(Hashtable)selectionSquaresTable.get(svgTab);
		}catch (Exception ex){selectionSquares=null;}
		
		if(selectionSquares==null){
		    
			selectionSquares=new Hashtable();
			selectionSquaresTable.put(svgTab, selectionSquares);
		}

		LinkedList currentList=null;
		
		for(Iterator it=selectionSquares.keySet().iterator(); it.hasNext();){
		    
			currentList=(LinkedList)selectionSquares.get(it.next());
			
			if(currentList!=null){
			    
			    currentList.clear();
			}
		}
		
		//removes the paint listener
		SVGPaintListener paintListener=(SVGPaintListener)paintListenerTable.get(svgTab);
		
		if(paintListener!=null){
		    
			svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, false);
			paintListenerTable.remove(svgTab);
		}
		
		if(table!=null && table.size()>0){
		    
			manageSelection(svgTab, table, svgTab.getScrollPane().getSvgCanvas().getGraphics());	
			
			final SVGTab finalSvgTab=svgTab;
			final Hashtable ftable=new Hashtable(table);
			
			paintListener=new SVGPaintListener(){
			    
				public void paintToBeDone(Graphics g) {
					
					if(canRepaintSelection()){
					    
						manageSelection(finalSvgTab, ftable, g);
						
						if(getStudio().getSvgSelection()!=null){
						    
							getStudio().getSvgSelection().addSelectionSquares(finalSvgTab, getSelectionSquares(finalSvgTab));
						}
					}
				}
			};
			
			svgTab.getScrollPane().getSvgCanvas().addSelectionLayerPaintListener(paintListener, false);
			paintListenerTable.put(svgTab, paintListener);
		}
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   * @param makeRepaint   
   */
  public void deselectAll(SVGTab svgTab, boolean makeRepaint){
    //removes the paint listener
		SVGPaintListener paintListener=(SVGPaintListener)paintListenerTable.get(svgTab);
		if(paintListener!=null){
			svgTab.getScrollPane().getSvgCanvas().removePaintListener(paintListener, makeRepaint);
			paintListenerTable.remove(paintListener);
		}	
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   */
  public Hashtable getSelectionSquares(SVGTab svgTab){
    Hashtable selectionSquares=null;
		try{
			selectionSquares=(Hashtable)selectionSquaresTable.get(svgTab);
		}catch (Exception ex){selectionSquares=null;}
		return selectionSquares;
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
			  return "level1";
			}else if(type.equals("default")){
			  return "level1";
			}
		}
		return "level1";
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return (String)ids.get("id");
	}

  /**
   * 
   * @description 
   */
  public void cancelActions(){
	
  }
  /**
   * 
   * @description 
   * @return 
   */
  public SVGApplet getStudio() {
    return studio;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean canRepaintSelection(){
    return this.canRepaintSelection;
  }

  /**
   * 
   * @description 
   * @param canRepaintSelection
   */
  public synchronized void setCanRepaintSelection(boolean canRepaintSelection) {
    if(this.canRepaintSelection!=canRepaintSelection){
			this.canRepaintSelection=canRepaintSelection;
			getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().delayedRepaint();
		}
  }
}