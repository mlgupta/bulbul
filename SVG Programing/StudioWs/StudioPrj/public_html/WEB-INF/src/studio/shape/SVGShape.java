package studio.shape;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Node;
import studio.canvas.SVGCanvas;
import studio.canvas.SVGScrollPane;
import studio.canvas.SVGSelection;
import studio.canvas.SVGTransformMatrix;
import studio.undoredo.SVGUndoRedoAction;
import studio.undoredo.SVGUndoRedoActionList;

public class SVGShape  {
  /**
	 * the variable describing the regular selection
	 */
	public static final int REGULAR_SELECTION=0;
	
	/**
	 * the variable describing the rotate selection
	 */
	public static final int ROTATE_SELECTION=1;
	
	/**
	 * the variable describing the do action
	 */
	public static final int DO_ACTION=0;

	/**
	 * the variable describing the validate action
	 */
	public static final int VALIDATE_ACTION=1;

	/**
	 * the variable describing the do translate action
	 */
	public static final int DO_TRANSLATE_ACTION=2;
	
	/**
	 * the variable describing the validate translate action
	 */
	public static final int VALIDATE_TRANSLATE_ACTION=3;
  
  /**
	 * the colors used
	 */
	protected final Color SQUARE_SELECTION_COLOR1=new Color(141, 168, 255);
  protected final Color SQUARE_SELECTION_COLOR2=new Color(38, 76, 135);
  protected final Color LINE_SELECTION_COLOR=new Color(75, 100, 200);
  protected final Color LOCK_COLOR=new Color(100,100,100);
  protected final Color GHOST_COLOR=new Color(0,0,0);
  protected final Color OUTLINE_COLOR=Color.BLUE;
  protected final Color OUTLINE_FILL_COLOR=new Color(0, 0, 255, 50);
	
  /**
	 * the boolean telling if the selection can be repainted or not
	 */
	protected boolean canRepaintSelection=true;
  
  /**
	 * the hashtable associating a key to its label
	 */
	protected Hashtable labels=new Hashtable();
  
  protected SVGScrollPane scrollPane;
  
  /**
	 * the array describing the accurate order for the selection types
	 */
	protected String[] selectionsOrder=new String[4];
  
  protected Hashtable selectionSquares=new Hashtable();
  
  
	protected SVGCanvas.PaintListener paintListener=null;
  protected SVGCanvas.PaintListener translatePaintListener=null;
  protected SVGCanvas.PaintListener resizePaintListener=null;
  protected SVGCanvas.PaintListener rotateSkewPaintListener=null;
  
  /**
	 * the hashtable associating a key to its id
	 */
	protected Hashtable ids=new Hashtable();
  
  /**
	 * the constructor of the class
	 * @param editor the editor of the class
	 */
	public SVGShape(SVGScrollPane scrollPane) {
		this.scrollPane=scrollPane;
		selectionsOrder[0]="level1";
		selectionsOrder[1]="level2";
		selectionsOrder[2]="level3";
		selectionsOrder[3]="lock";
	}
  /**
	 * @return the editor
	 */
	public SVGScrollPane getScrollPane(){
		return scrollPane;
	}
  
  /**
	 * selects the nodes and renders them graphically
	 * @param frame the current SVGFrame
	 * @param table the nodes to be selected
	 */
	public void select(Hashtable table){

		selectionSquares.clear();
    
    //removes the paint listener
		if(paintListener!=null){
			getScrollPane().getSVGCanvas().removePaintListener(paintListener, false);
		}
		
		if(table!=null && table.size()>0){
			manageSelection(table, getScrollPane().getSVGCanvas().getGraphics());	

			final Hashtable finalTable=new Hashtable(table);
			paintListener=new SVGCanvas.PaintListener(){
				public void paintToBeDone(Graphics g) {
					if(canRepaintSelection()){
						manageSelection(finalTable, g);
						if(getScrollPane().getSVGSelection()!=null){
							getScrollPane().getSVGSelection().addSelectionSquares(getSelectionSquares());
						}
					}
				}
			};
			getScrollPane().getSVGCanvas().addPaintListener(paintListener, true);			
		}

	}
  
  /**
	 * calls the selection methods for each node in the hashtable
	 * @param table the table containing the nodes
	 * @param g the graphics used to paint
	 */
	protected void manageSelection(Hashtable table, Graphics g){
		if(selectionSquares!=null){
      selectionSquares.clear();
    }else{
			selectionSquares=new Hashtable();			
		}
		if(table!=null && table.size()>0){
			Iterator it=table.keySet().iterator();
			Node node=null;
			LinkedList squares=null;
			String type="";
			//for each node in the table, the accurate "drawSelection" method is called given the type of the selection
			while(it.hasNext()){
				try{
					node=(Node)it.next();
				}catch (Exception e){
					return;
				}
				if(node!=null){
					type=(String)table.get(node);
					if(type!=null && !type.equals("")){
						if(type.equals(selectionsOrder[0])){
							squares=drawSelection(g,node);
						}else if(type.equals(selectionsOrder[1])){
							squares=drawRotateSelection(g,node);
						}else if(type.equals(selectionsOrder[2])){
							squares=drawModifyPointsSelection(g,node);	
						}else if(type.equals(selectionsOrder[3])){
							squares=drawLockedSelection(g,node);
						}						
						if(squares!=null && squares.size()>0)selectionSquares.put(node,squares);
					}
				}
			}
		}
	}
 
  /**
	 * 
	 * @return the table of the selection squares
	 */
	public Hashtable getSelectionSquares(){
		return selectionSquares;
	}
  
  /**
	 * @return the boolean telling if the selection can be repainted or not
	 */
	public boolean canRepaintSelection(){
		return canRepaintSelection;
	}
  
  /**
	 * sets the canRepaintSelection boolean
	 * @param canRepaintSelection true to enable the selections to be repainted
	 */
	public synchronized void setCanRepaintSelection(boolean canRepaintSelection){
		if(this.canRepaintSelection!=canRepaintSelection){
			this.canRepaintSelection=canRepaintSelection;
			getScrollPane().getSVGCanvas().delayedRepaint();
		}
	}
	
	/**
	 * gets the nexts level after the given selection level
	 * @param type a selection level
	 * @return the next selection level
	 */
	public String getNextLevel(String type){
		if(type!=null){
			if(type.equals("level1"))return "level2";
			else if(type.equals("level2"))return "level1";
			else if(type.equals("default"))return "level1";
		}
		return "level1";
	}
  
  /**
	 * draws the selection around the node
	 * @param graphics the graphics 
	 * @param node the node to be selected
	 * @return the list of the selection squares
	 */
	protected LinkedList drawSelection(Graphics graphics, Node node){
		LinkedList squarelist=new LinkedList();
		Graphics2D g=null;

		try{
			g=(Graphics2D)graphics;
		}catch (Exception e){g=null;}
		
		if(g!=null && node!=null){
			int sqd=5;
			Rectangle rectangle=getScrollPane().getSVGToolkit().getNodeBounds(node);
			if(rectangle!=null){
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double scaledRectangle=getScrollPane().getSVGToolkit().getScaledRectangle(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height), false);
				if(scaledRectangle!=null){
					
          double x=scaledRectangle.getX(); 
          double y=scaledRectangle.getY(); 
          double width=scaledRectangle.getWidth(); 
          double height=scaledRectangle.getHeight();
          
					int[] sqx=new int[8];
					int[] sqy=new int[8];
					//the coordinates of the selection points
					sqx[0]=(int)x-2*sqd;
					sqx[1]=(int)(x+width/2)-sqd;
					sqx[2]=(int)(x+width);
					sqx[3]=sqx[2];
					sqx[4]=sqx[2];
					sqx[5]=sqx[1];
					sqx[6]=sqx[0];
					sqx[7]=sqx[0];

					sqy[0]=(int)y-2*sqd;
					sqy[3]=(int)(y+height/2)-sqd;
					sqy[4]=(int)(y+height);
					sqy[1]=sqy[0];
					sqy[2]=sqy[0];
					sqy[5]=sqy[4];
					sqy[6]=sqy[4];
					sqy[7]=sqy[3];
			
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
					int[] tin=null;
					if(width>2*sqd && height>2*sqd){
						int[] tmp=new int[8];
						for(int i=0;i<8;i++)tmp[i]=i;
						tin=tmp;
					}else if((width<=2*sqd && height>2*sqd) 
                    || (width>2*sqd && height<=2*sqd) 
                      || (width<=2*sqd && height<=2*sqd)){
						int[] tmp={1,3,5,7};
						tin=tmp;
					}
			
					//draws the graphic elements
					Shape shape=null;
					GradientPaint gradient=null;
					for(int i=0;i<tin.length;i++){
						if(scrollPane.getSVGSelection()!=null){
							squarelist.add(	scrollPane.getSVGSelection().new SelectionSquare(node, types[tin[i]], 
														new Rectangle2D.Double(sqx[tin[i]],sqy[tin[i]],2*sqd,2*sqd), 
														cursors[tin[i]]));
						}
						
						shape=getArrow(new Point(sqx[tin[i]]+sqd, sqy[tin[i]]+sqd), types[tin[i]], REGULAR_SELECTION);
						
						if(shape!=null){
							gradient=new GradientPaint(sqx[tin[i]], sqy[tin[i]], SQUARE_SELECTION_COLOR1, sqx[tin[i]]+2*sqd, sqy[tin[i]]+2*sqd, SQUARE_SELECTION_COLOR2, true);
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
	 * draws the selection around the node
	 * @param graphics the graphics 
	 * @param node the node to be selected
	 * @return the list of the selection squares
	 */
	protected LinkedList drawRotateSelection(Graphics graphics, Node node){
		LinkedList squarelist=new LinkedList();
		
		Graphics2D g=null;
		try{
			g=(Graphics2D)graphics;
		}catch (Exception e){g=null;}
		
		if(g!=null && node!=null){
			int sqd=5;
			Rectangle rectangle=getScrollPane().getSVGToolkit().getNodeBounds(node);
			if(rectangle!=null){
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double scaledRectangle=getScrollPane().getSVGToolkit().getScaledRectangle(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height), false);
				double x=scaledRectangle.getX(); 
        double y=scaledRectangle.getY(); 
        double width=scaledRectangle.getWidth(); 
        double height=scaledRectangle.getHeight();
				int[] sqx=new int[5];
				int[] sqy=new int[5];
				//the coordinates of the selection points
				sqx[0]=(int)(x+width/2)-sqd;
				sqx[1]=(int)(x+width);
				sqx[2]=sqx[0];
				sqx[3]=(int)x-2*sqd;
				sqx[4]=(int)(x+width/2-sqd);

				sqy[0]=(int)y-2*sqd;
				sqy[1]=(int)(y+height/2)-sqd;
				sqy[2]=(int)(y+height);
				sqy[3]=sqy[1];
				sqy[4]=(int)(y+height/2-sqd);

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
				int[] tin=null;
				if(width>2*sqd && height>2*sqd){
					int[] tmp=new int[5];
					for(int i=0;i<5;i++)tmp[i]=i;
					tin=tmp;
				}else{
					int[] tmp={0,1,2,3};
					tin=tmp;
				}
			
				//draws the graphic elements
				Shape shape=null;
				GradientPaint gradient=null;
			
				for(int i=0;i<tin.length;i++){
					if(scrollPane.getSVGSelection()!=null){
						squarelist.add(	scrollPane.getSVGSelection().new SelectionSquare(node,types[tin[i]],
													new Rectangle2D.Double(sqx[tin[i]],sqy[tin[i]],2*sqd,2*sqd),
													cursors[tin[i]]));
					}		
					shape=getArrow(new Point(sqx[tin[i]]+sqd, sqy[tin[i]]+sqd), types[tin[i]], ROTATE_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(sqx[tin[i]], sqy[tin[i]], SQUARE_SELECTION_COLOR1, sqx[tin[i]]+2*sqd, sqy[tin[i]]+2*sqd, SQUARE_SELECTION_COLOR2, true);
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
	 * draws the selection around the node
	 * @param graphics the graphics 
	 * @param node the node to be selected
	 * @return the list of the selection squares
	 */
	protected LinkedList drawModifyPointsSelection(Graphics graphics, Node node){
		return null;
	}
	
	/**
	 * draws the selection around the node
	 * @param frame the current SVGFrame
	 * @param graphics the graphics 
	 * @param node the node to be selected
	 * @return the list of the selection squares
	 */
	protected LinkedList drawLockedSelection(Graphics graphics, Node node){
		Graphics2D g=null;
		try{
			g=(Graphics2D)graphics;
		}catch (Exception e){g=null;}
		if(g!=null && node!=null){
			int sqd=5;
			Rectangle rectangle=getScrollPane().getSVGToolkit().getNodeBounds(node);
			if(rectangle!=null){
				//computes and draws the new awt rectangle to be displayed
				Rectangle2D.Double scaledRectangle=getScrollPane().getSVGToolkit().getScaledRectangle(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height), false);
				double x=scaledRectangle.getX();
        double y=scaledRectangle.getY();
        double width=scaledRectangle.getWidth();
        double height=scaledRectangle.getHeight();
				int[] sqx=new int[8];
				int[] sqy=new int[8];
				//the coordinates of the selection points
				sqx[0]=(int)x-2*sqd;
				sqx[1]=(int)(x+width/2)-sqd;
				sqx[2]=(int)(x+width);
				sqx[3]=sqx[2];
				sqx[4]=sqx[2];
				sqx[5]=sqx[1];
				sqx[6]=sqx[0];
				sqx[7]=sqx[0];

				sqy[0]=(int)y-2*sqd;
				sqy[3]=(int)(y+height/2)-sqd;
				sqy[4]=(int)(y+height);
				sqy[1]=sqy[0];
				sqy[2]=sqy[0];
				sqy[5]=sqy[4];
				sqy[6]=sqy[4];
				sqy[7]=sqy[3];
				
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
				int[] tin=null;
				if(width>2*sqd && height>2*sqd){
					int[] tmp=new int[8];
					for(int i=0;i<8;i++)tmp[i]=i;
					tin=tmp;
				}else if((width<=2*sqd && height>2*sqd) || (width>2*sqd && height<=2*sqd) || (width<=2*sqd && height<=2*sqd)){
					int[] tmp={1,3,5,7};
					tin=tmp;
				}
				//draws the graphic elements
				Shape shape=null;
				GradientPaint gradient=null;
				for(int i=0;i<tin.length;i++){
					shape=getArrow(new Point(sqx[tin[i]]+sqd, sqy[tin[i]]+sqd), types[tin[i]], REGULAR_SELECTION);
					if(shape!=null){
						gradient=new GradientPaint(sqx[tin[i]], sqy[tin[i]], LOCK_COLOR, sqx[tin[i]]+2*sqd, sqy[tin[i]]+2*sqd, Color.white, true);
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
	 * @param originPoint the origin point
	 * @param place the place of the selection square
	 * @param type the type of the selection
	 * @return the shape of the selection square
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
	 * clears the table of the selected nodes
	 * @param frame the current SVGFrame
	 * @param makeRepaint the Boolean telling if a repaint action should be done or not
	 */
	public void deselectAll(Boolean makeRepaint){
	//removes the paint listener
		if(paintListener!=null){
			getScrollPane().getSVGCanvas().removePaintListener(paintListener, makeRepaint.booleanValue());
		}	
	}
  
  /**
	 * does the action that are linked with a special selection
	 * @param frame the current SVGFrame
	 * @param type the type of the selection
	 * @param action the type of the action (do or validate)
	 * @param args the array of the arguments
	 */
	public void doActions(String type, int action, Object[] args){
		RunnableQueue queue=getScrollPane().getSVGCanvas().getUpdateManager().getUpdateRunnableQueue();
		if(type!=null && ! type.equals("")){
			//if it is a translation action
			if(action==DO_TRANSLATE_ACTION || action==VALIDATE_TRANSLATE_ACTION){
				if(action==DO_TRANSLATE_ACTION){
					setCanRepaintSelection(false);
					try{
						translateNode((LinkedList)args[0],(Point)args[1]);
					}catch (Exception e){return;}
				}else if(action==VALIDATE_TRANSLATE_ACTION){
					setCanRepaintSelection(false);
					try{
						validateTranslateNode((LinkedList)args[0], (Point)args[1]);
						//sets that the svg has been modified
						scrollPane.setModified(true);
					}catch (Exception e){return;}
					setCanRepaintSelection(true);
				}
			}else{
				//make a different action given the current type of selection
				if(type.equals("level1")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						try{
							resizeNode((SVGSelection.SelectionSquare)args[0],(Point)args[1],(Point)args[2]);
						}catch (Exception e){return;}
					}else if(action==VALIDATE_ACTION){
						try{
							validateResizeNode((SVGSelection.SelectionSquare)args[0],(Point)args[1],(Point)args[2]);
							//sets that the svg has been modified
							scrollPane.setModified(true);
						}catch (Exception e){return;}
						setCanRepaintSelection(true);		
					}		
				}else if(type.equals("level2")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						try{
							rotateSkewNode((SVGSelection.SelectionSquare)args[0],(Point)args[3],(Point)args[4]);
						}catch (Exception e){return;}
					}else if(action==VALIDATE_ACTION){
						try{
							validateRotateSkewNode((SVGSelection.SelectionSquare)args[0],(Point)args[3],(Point)args[4]);
							//sets that the svg has been modified
							scrollPane.setModified(true);
						}catch (Exception e){return;}
						setCanRepaintSelection(true);		
					}				
				}else if(type.equals("level3")){
					if(action==DO_ACTION){
						setCanRepaintSelection(false);
						if(queue.getThread()!=Thread.currentThread()){
							final Object[] fargs=args;
							queue.invokeLater(new Runnable(){
								public void run(){
									try{
										getScrollPane().getSVGCanvas().setEnableWaitCursor(false);
										modifyPoint((SVGSelection.SelectionSquare)fargs[0],(Point)fargs[1],(Point)fargs[2]);
									}catch (Exception e){return;}
								}
							});
						}else{
							try{
								getScrollPane().getSVGCanvas().setEnableWaitCursor(false);
								modifyPoint((SVGSelection.SelectionSquare)args[0],(Point)args[1],(Point)args[2]);
							}catch (Exception e){return;}
						}
					}else if(action==VALIDATE_ACTION){
						try{
							validateModifyPoint((SVGSelection.SelectionSquare)args[0],(Point)args[1],(Point)args[2]);
							getScrollPane().getSVGCanvas().setEnableWaitCursor(true);
							//sets that the svg has been modified
							scrollPane.setModified(true);
						}catch (Exception e){return;}
						setCanRepaintSelection(true);
					}
				}else if(type.equals("locked")){}		
			}
		}
	}

  /**
	 * the method to translate a node
	 * @param frame the current SVGFrame
	 * @param list the list of the nodes to be translated
	 * @param tValues the absolute x and y of the translation
	 */
	public void translateNode(LinkedList list, Point tValues){
		if(list!=null && list.size()>0 && tValues!=null){
			if(tValues!=null){
				Shape outline=null;
				Node node=null;
				Iterator it=list.iterator();
				AffineTransform affineTransform=null;
				SVGTransformMatrix matrix=null;
				final LinkedList outlines=new LinkedList();
				//for each node, creates the outline
				while(it.hasNext()){
					try{
						node=(Node)it.next();
					}catch (Exception ex){node=null;}
					if(node!=null){
						outline=getScrollPane().getSVGToolkit().getOutline(node);
						matrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
						if(matrix!=null && matrix.getTransform()!=null){
							//concatenates the transforms to draw the outline
							affineTransform=matrix.getTransform();
							affineTransform.preConcatenate(AffineTransform.getTranslateInstance(tValues.x, tValues.y));
							
							double scale=getScrollPane().getSVGCanvas().getScale();
							affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
							
							Dimension translateValues=getScrollPane().getTranslateValues();
							affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));
							
							outline=affineTransform.createTransformedShape(outline);
						}
						outlines.add(outline);
					}
				}
				if(translatePaintListener!=null){
					getScrollPane().getSVGCanvas().removePaintListener(translatePaintListener, false);
				}
				//creates and sets the paint listener
				translatePaintListener=new SVGCanvas.PaintListener(){
					public void paintToBeDone(Graphics g) {
						Graphics2D g2=(Graphics2D)g;
						Color oldColor=g.getColor();
						Shape outline=null;
						for(int i=0;i<outlines.size();i++){
							try{
								outline=(Shape)outlines.get(i);
							}catch (Exception ex){outline=null;}
							if(outline!=null){
								g.setColor(OUTLINE_FILL_COLOR);
								g2.fill(outline);
								g.setColor(OUTLINE_COLOR);
								g2.draw(outline);
							}
						}
						g.setColor(oldColor);
					}
				};
				getScrollPane().getSVGCanvas().addPaintListener(translatePaintListener, true);
			}
		}
	}  
  /**
	 * validates the translateNode method
	 * @param list the list of the nodes to be translated
	 * @param tValues the absolute x and y of the translation
	 */
	public void validateTranslateNode(LinkedList list, Point tValues){
		if(list!=null && list.size()>0 && tValues!=null){
			//removes the paint listener
			if(translatePaintListener!=null){
				getScrollPane().getSVGCanvas().removePaintListener(translatePaintListener, true);
				translatePaintListener=null;
			}
			final Hashtable oldMatrices=new Hashtable();
			final Hashtable newMatrices=new Hashtable();
			SVGTransformMatrix oldMatrix=null, newMatrix=null;
			Iterator it=list.iterator();
			Node node=null;
			//for each node, sets the new matrix transform
			while(it.hasNext()){
				try{
					node=(Node)it.next();
				}catch (Exception ex){}
				if(node!=null){
					oldMatrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
					//creates the new matrix transform
					newMatrix=oldMatrix.cloneMatrix();
					newMatrix.concatenateTranslate(tValues.x, tValues.y);
					oldMatrices.put(node, oldMatrix);
					newMatrices.put(node, newMatrix);
					getScrollPane().getSVGToolkit().setTransformMatrix(node, newMatrix);
				}
			}
			//creates the undo/redo action and insert it into the undo/redo stack
			if(scrollPane.getUndoRedo()!=null){
				SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredotranslate")){
					/**
					 * used to call all the actions that have to be done to undo an action
					 */
					public void undo(){
						SVGTransformMatrix oldMatrix=null;
						Iterator it=oldMatrices.keySet().iterator();
						Node node=null;
						while(it.hasNext()){
							try{
								node=(Node)it.next();
							}catch (Exception ex){}
							if(node!=null){
								oldMatrix=(SVGTransformMatrix)oldMatrices.get(node);
								if(oldMatrix!=null){
									getScrollPane().getSVGToolkit().setTransformMatrix(node, oldMatrix);
								}
							}
						}
					}

					/**
					 * used to call all the actions that have to be done to redo an action
					 */
					public void redo(){
						SVGTransformMatrix newMatrix=null;
						Iterator it=newMatrices.keySet().iterator();
						Node node=null;
						while(it.hasNext()){
							try{
								node=(Node)it.next();
							}catch (Exception ex){}
							if(node!=null){
								newMatrix=(SVGTransformMatrix)newMatrices.get(node);
								if(newMatrix!=null){
									getScrollPane().getSVGToolkit().setTransformMatrix(node, newMatrix);
								}
							}
						}
					}
				};
				//gets or creates the undo/redo list and adds the action into it
				SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredotranslate"));
				actionlist.add(action);
				scrollPane.getUndoRedo().addAction(actionlist);	
				actionlist=null;
			}
		}
	}  
  
  /**
	 * resizes a node
	 * @param square the Selection square that is used by the user to resize the node
	 * @param point1 the first point clicked
	 * @param point2 the second point clicked
	 */
	public void resizeNode(SVGSelection.SelectionSquare square, Point point1, Point point2){
		if(square!=null && square.getNode()!=null && point1!=null && point2!=null){
			Node node=square.getNode();
      //the outline
			Shape outline=getScrollPane().getSVGToolkit().getOutline(node);
			Point difference=new Point(point2.x-point1.x, point2.y-point1.y);
			//the transform
			AffineTransform affineTransform=new AffineTransform();
			SVGTransformMatrix matrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
			if(matrix!=null && matrix.getTransform()!=null){
				affineTransform=matrix.getTransform();
			}
			//computes the scale and translate values taking the type of the selection square into account
			if(outline!=null){
				Rectangle bounds=affineTransform.createTransformedShape(outline).getBounds();
				if(bounds!=null){
					double sx=1.0; 
          double sy=1.0;
          double tx=0;
          double ty=0;
					if(square.getType().equals("NW")){
						sx=1-difference.x/bounds.getWidth();
						sy=1-difference.y/bounds.getHeight();
						tx=(bounds.getX()+bounds.getWidth())*(1-sx);
						ty=(bounds.getY()+bounds.getHeight())*(1-sy);
					}else if(square.getType().equals("N")){
						sy=1-difference.y/bounds.getHeight();
						ty=(bounds.getY()+bounds.getHeight())*(1-sy);
					}else if(square.getType().equals("NE")){
						sx=1+difference.x/bounds.getWidth();
						sy=1-difference.y/bounds.getHeight();
						tx=(bounds.getX())*(1-sx);
						ty=(bounds.getY()+bounds.getHeight())*(1-sy);		
					}else if(square.getType().equals("E")){			
						sx=1+difference.x/bounds.getWidth();
						tx=bounds.getX()*(1-sx);				
					}else if(square.getType().equals("SE")){
						sx=1+difference.x/bounds.getWidth();
						sy=1+difference.y/bounds.getHeight();
						tx=bounds.getX()*(1-sx);
						ty=bounds.getY()*(1-sy);
					}else if(square.getType().equals("S")){
						sy=1+difference.y/bounds.getHeight();
						ty=bounds.getY()*(1-sy);
					}else if(square.getType().equals("SW")){
						sx=1-difference.x/bounds.getWidth();
						sy=1+difference.y/bounds.getHeight();
						tx=(bounds.getX()+bounds.getWidth())*(1-sx);
						ty=(bounds.getY())*(1-sy);			
					}else if(square.getType().equals("W")){
						sx=1-difference.x/bounds.getWidth();
						tx=(bounds.getX()+bounds.getWidth())*(1-sx);			
					}
					if(resizePaintListener!=null){
						getScrollPane().getSVGCanvas().removePaintListener(resizePaintListener, false);
					}
									
					//concatenates the transforms to draw the outline
					affineTransform.preConcatenate(AffineTransform.getScaleInstance(sx, sy));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(tx, ty));
					
					double scale=getScrollPane().getSVGCanvas().getScale();
					affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
					
					Dimension translateValues=getScrollPane().getTranslateValues();
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));
					
					outline=affineTransform.createTransformedShape(outline);

					final Shape finalOutline=outline;
					
					//creates and sets the paint listener
					resizePaintListener=new SVGCanvas.PaintListener(){
						public void paintToBeDone(Graphics g) {
							Graphics2D g2=(Graphics2D)g;
							Color oldColor=g.getColor();
							g.setColor(OUTLINE_FILL_COLOR);
							g2.fill(finalOutline);
							g.setColor(OUTLINE_COLOR);
							g2.draw(finalOutline);
							g.setColor(oldColor);
						}
					};
					getScrollPane().getSVGCanvas().addPaintListener(resizePaintListener, true);
				}
			}
		}
	}
  
  /**
	 * validates the resize transform
	 * @param square the Selection square that is used by the user to resize the node
	 * @param point1 the first point clicked
	 * @param point2 the second point clicked
	 */
	public void validateResizeNode(SVGSelection.SelectionSquare square, Point point1, Point point2){
		if(square!=null && square.getNode()!=null && point1!=null && point2!=null){
			if(resizePaintListener!=null){
				getScrollPane().getSVGCanvas().removePaintListener(resizePaintListener, true);
				resizePaintListener=null;
			}
			Node node=square.getNode();
			Point difference=new Point(point2.x-point1.x, point2.y-point1.y);
			//the bounds of the node
			Rectangle bounds=getScrollPane().getSVGToolkit().getNodeBounds(node);
			if(bounds!=null){
				double sx=1.0;
        double sy=1.0;
        double tx=0;
        double ty=0;
				if(square.getType().equals("NW")){
					sx=1-difference.x/bounds.getWidth();
					sy=1-difference.y/bounds.getHeight();
					tx=(bounds.getX()+bounds.getWidth())*(1-sx);
					ty=(bounds.getY()+bounds.getHeight())*(1-sy);
				}else if(square.getType().equals("N")){
					sy=1-difference.y/bounds.getHeight();
					ty=(bounds.getY()+bounds.getHeight())*(1-sy);
				}else if(square.getType().equals("NE")){
					sx=1+difference.x/bounds.getWidth();
					sy=1-difference.y/bounds.getHeight();
					tx=(bounds.getX())*(1-sx);
					ty=(bounds.getY()+bounds.getHeight())*(1-sy);		
				}else if(square.getType().equals("E")){			
					sx=1+difference.x/bounds.getWidth();
					tx=bounds.getX()*(1-sx);				
				}else if(square.getType().equals("SE")){
					sx=1+difference.x/bounds.getWidth();
					sy=1+difference.y/bounds.getHeight();
					tx=bounds.getX()*(1-sx);
					ty=bounds.getY()*(1-sy);
				}else if(square.getType().equals("S")){
					sy=1+difference.y/bounds.getHeight();
					ty=bounds.getY()*(1-sy);
				}else if(square.getType().equals("SW")){
					sx=1-difference.x/bounds.getWidth();
					sy=1+difference.y/bounds.getHeight();
					tx=(bounds.getX()+bounds.getWidth())*(1-sx);
					ty=(bounds.getY())*(1-sy);			
				}else if(square.getType().equals("W")){
					sx=1-difference.x/bounds.getWidth();
					tx=(bounds.getX()+bounds.getWidth())*(1-sx);			
				}
				//gets the transform matrix
				final SVGTransformMatrix oldMatrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
				final SVGTransformMatrix newMatrix=oldMatrix.cloneMatrix();
				newMatrix.concatenateScale(sx, sy);
				newMatrix.concatenateTranslate(tx, ty);
				if(newMatrix.isMatrixCorrect()){
					//sets the new transform matrix
					getScrollPane().getSVGToolkit().setTransformMatrix(square.getNode(), newMatrix);
					//create the undo/redo action and insert it into the undo/redo stack
					if(scrollPane.getUndoRedo()!=null){
						final Node finalNode=node;
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredoresize")){
							/**
							 * used to call all the actions that have to be done to undo an action
							 */
							public void undo(){
								//sets the matrix of the node with the old matrix
								getScrollPane().getSVGToolkit().setTransformMatrix(finalNode, oldMatrix);
							}
							/**
							 * used to call all the actions that have to be done to redo an action
							 */
							public void redo(){
								//sets the matrix of the node with the new matrix
								getScrollPane().getSVGToolkit().setTransformMatrix(finalNode, newMatrix);
							}
						};
						//gets or creates the undo/redo list and adds the action into it
						SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredoresize"));
						actionlist.add(action);
            scrollPane.getUndoRedo().addAction(actionlist);	
						actionlist=null;
					}
				}		
			}
		}
	}
  
  /**
	 * the method to rotate a node
	 * @param square the current selection square
	 * @param point1 the first point clicked
	 * @param point2 the second point clicked
	 */
	public void rotateSkewNode(SVGSelection.SelectionSquare square, Point point1, Point point2){
		if(square!=null && square.getNode()!=null && point1!=null && point2!=null){
			Node node=square.getNode();
      //the outline and the bounds of the node
			Shape outline=getScrollPane().getSVGToolkit().getOutline(node);
			Rectangle bounds=null;
			//the transform of the node
			AffineTransform affineTransform=new AffineTransform();
			SVGTransformMatrix matrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
			if(matrix!=null && matrix.getTransform()!=null){
				affineTransform=matrix.getTransform();
			}
			if(outline!=null){
				bounds=affineTransform.createTransformedShape(outline).getBounds();
			}
			//computes the scale and translate values taking the type of the selection square into account
			if(bounds!=null && point1!=null && point2!=null){
				double angle=0;
        double cx=0;
        double cy=0;
				Point2D.Double centerpoint=null;
				if(square.getType().equals("C")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2, bounds.y+bounds.height/2);
					double x2=0; 
          double y2=0; 
          double n2=0;
					n2=Math.sqrt(Math.pow(point2.x-centerpoint.x,2)+Math.pow(point2.y-centerpoint.y,2));
					x2=(point2.x-centerpoint.x)/n2;
					y2=(point2.y-centerpoint.y)/n2;
					angle=(y2>=0)?Math.acos(x2):-Math.acos(x2);
					cx=centerpoint.x; 
					cy=centerpoint.y;
					//sets the new rotation values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-cx, -cy));
					affineTransform.preConcatenate(AffineTransform.getRotateInstance(angle));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));
				}else if(square.getType().equals("N")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2,bounds.y);
					angle=Math.toRadians(point2.x-point1.x);
					cx=centerpoint.x; 
					cy=centerpoint.y;
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-cx, -cy));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));			
				}else if(square.getType().equals("S")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2,bounds.y+bounds.height);
					angle=Math.toRadians(point2.x-point1.x);
					cx=centerpoint.x; 
					cy=centerpoint.y;
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-cx, -cy));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));		
				}else if(square.getType().equals("E")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width,bounds.y+bounds.height/2);
					angle=Math.toRadians(point2.y-point1.y);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-cx, -cy));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));		
				}else if(square.getType().equals("W")){
					centerpoint=new Point2D.Double(bounds.x,bounds.y+bounds.height/2);
					angle=Math.toRadians(point2.y-point1.y);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new skew values
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(-cx, -cy));
					affineTransform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
					affineTransform.preConcatenate(AffineTransform.getTranslateInstance(cx, cy));									
				}
				if(rotateSkewPaintListener!=null){
					getScrollPane().getSVGCanvas().removePaintListener(rotateSkewPaintListener, false);
				}
				//concatenates the transforms to draw the outline
				double scale=getScrollPane().getSVGCanvas().getScale();
				affineTransform.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
				Dimension translateValues=getScrollPane().getTranslateValues();
				affineTransform.preConcatenate(AffineTransform.getTranslateInstance(translateValues.width, translateValues.height));
				outline=affineTransform.createTransformedShape(outline);
				final Shape finalOutline=outline;
				//creates and sets the paint listener
				rotateSkewPaintListener=new SVGCanvas.PaintListener(){
					public void paintToBeDone(Graphics g) {
						Graphics2D g2=(Graphics2D)g;
						Color oldColor=g.getColor();
						g.setColor(OUTLINE_COLOR);
						g.setColor(OUTLINE_FILL_COLOR);
						g2.fill(finalOutline);
						g.setColor(OUTLINE_COLOR);
						g2.draw(finalOutline);
            g.setColor(oldColor);
					}
				};
			  getScrollPane().getSVGCanvas().addPaintListener(rotateSkewPaintListener, true);
			}
		}		
	}
	
	/**
	 * validates the rotateNode method
	 * @param square the current selection square
	 * @param point1 the first point clicked
	 * @param point2 the second point clicked
	 */
	public void validateRotateSkewNode(SVGSelection.SelectionSquare square, Point point1, Point point2){
		if(square!=null && square.getNode()!=null){
			if(rotateSkewPaintListener!=null){
				getScrollPane().getSVGCanvas().removePaintListener(rotateSkewPaintListener, false);
        rotateSkewPaintListener=null;
			}
			Node node=square.getNode();
			Rectangle bounds=getScrollPane().getSVGToolkit().getNodeBounds(node);
			if(point1!=null && point2!=null && bounds!=null){
				//computes the transform matrix
				final SVGTransformMatrix oldMatrix=getScrollPane().getSVGToolkit().getTransformMatrix(node);
				final SVGTransformMatrix newMatrix=oldMatrix.cloneMatrix();
				//the values used for computing the rotate or skew values
				double angle=0; 
        double cx=0;
        double cy=0;
        double angle1=0;
        double angle2=0;
				Point2D.Double centerpoint=null;
				if(square.getType().equals("C")){	
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2,bounds.y+bounds.height/2);
					double x2=0; 
          double y2=0; 
          double n2=0;
					n2=Math.sqrt(Math.pow(point2.x-centerpoint.x, 2)+Math.pow(point2.y-centerpoint.y, 2));
					x2=(point2.x-centerpoint.x)/n2;
					y2=(point2.y-centerpoint.y)/n2;
					angle=(y2>=0)?Math.acos(x2):-Math.acos(x2);
					cx=centerpoint.x; 
					cy=centerpoint.y;			
					//sets the new rotation values
					newMatrix.concatenateTranslate(-cx, -cy);
					newMatrix.concatenateRotate(angle);
					newMatrix.concatenateTranslate(cx, cy);
				}else if(square.getType().equals("N")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2,bounds.y);
					angle=Math.toRadians(point2.x-point1.x);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new rotation values
					newMatrix.concatenateTranslate(-cx, -cy);
					newMatrix.concatenateSkewX(angle);
					newMatrix.concatenateTranslate(cx, cy);			
				}else if(square.getType().equals("S")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width/2,bounds.y+bounds.height);
					angle=Math.toRadians(point2.x-point1.x);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new rotation values
					newMatrix.concatenateTranslate(-cx, -cy);
					newMatrix.concatenateSkewX(angle);
					newMatrix.concatenateTranslate(cx, cy);
				}else if(square.getType().equals("E")){
					centerpoint=new Point2D.Double(bounds.x+bounds.width,bounds.y+bounds.height/2);
					angle=Math.toRadians(point2.y-point1.y);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new rotation values
					newMatrix.concatenateTranslate(-cx, -cy);
					newMatrix.concatenateSkewY(angle);
					newMatrix.concatenateTranslate(cx, cy);
				}else if(square.getType().equals("W")){
					centerpoint=new Point2D.Double(bounds.x,bounds.y+bounds.height/2);
					angle=Math.toRadians(point2.y-point1.y);
					cx=centerpoint.x; 
					cy=centerpoint.y;	
					//sets the new rotation values
					newMatrix.concatenateTranslate(-cx, -cy);
					newMatrix.concatenateSkewY(angle);
					newMatrix.concatenateTranslate(cx, cy);									
				}
				if(newMatrix.isMatrixCorrect()){
					getScrollPane().getSVGToolkit().setTransformMatrix(node,newMatrix);
					final Node finalNode=node;
          //creates the undo/redo action and insert it into the undo/redo stack
					if(scrollPane.getUndoRedo()!=null){
						SVGUndoRedoAction action=new SVGUndoRedoAction((String)labels.get("undoredorotate")){
							/**
							 * used to call all the actions that have to be done to undo an action
							 */
							public void undo(){
								//sets the matrix of the node with the old matrix
								getScrollPane().getSVGToolkit().setTransformMatrix(finalNode, oldMatrix);
							}
							/**
							 * used to call all the actions that have to be done to redo an action
							 */
							public void redo(){
								//sets the matrix of the node with the new matrix
								getScrollPane().getSVGToolkit().setTransformMatrix(finalNode,newMatrix);
							}
						};
						//gets or creates the undo/redo list and adds the action into it
						SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList((String)labels.get("undoredorotate"));
						actionlist.add(action);
						scrollPane.getUndoRedo().addAction(actionlist);	
						actionlist=null;	
					}
				}		
			}
		}
	}
  
  /**
	 * the method to modify a point of a node
	 * @param square the selection square
	 * @param point1 the first point clicked
	 * @param point2 the second point clicked
	 */
	public void modifyPoint(SVGSelection.SelectionSquare square, Point point1, Point point2){
		
	}
	
	/**
	 * validates the modifyPoint method
	 * @param square the selection square
	 * @param pt1 the first point clicked
	 * @param pt2 the second point clicked
	 */
	public void validateModifyPoint(SVGSelection.SelectionSquare square, Point pt1, Point pt2){
		
	}
  
  /**
	 * gets the name associated with the classObject
	 * @return the classObject's name
	 */
	public String getName(){
		return (String)ids.get("id");
	}
}