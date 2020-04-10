package bulbul.foff.studio.engine.listeners;
import bulbul.foff.studio.engine.ui.SVGScrollPane;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Sudheer V. Pujar
 */
public class GridPaintListener implements SVGPaintListener  {
  
  private SVGScrollPane scrollPane;
  private Hashtable rangeYMap=new Hashtable();
  private Hashtable rangeXMap=new Hashtable();
  
  /**
   * 
   * @param scrollPane
   */
  public GridPaintListener(SVGScrollPane scrollPane) {
    this.scrollPane=scrollPane;
    
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
   * 
   * @param height
   * @param width
   * @param rangeY
   * @param rangeX
   * @param initValueY
   * @param initValueX
   * @param g
   */
  private void drawGrid(Graphics g, int initValueX, int initValueY, int rangeX, int rangeY, int width, int height){
    Graphics2D g2=(Graphics2D)g;
    if(g2!=null){
      double scale=this.scrollPane.getSvgCanvas().getScale();
      Stroke stroke=g2.getStroke();
      int realX=0;
      int i=0;
      int position=0;

      //draws the vertical lines
      while(position<width){
          
        position=initValueX+(int)Math.round(i*rangeX*scale+0.5);
        realX=(int)((position-initValueX)/scale);
        
        if(realX%rangeX==1){
          position=initValueX+(int)Math.round(i*rangeX*scale);
        }else if(realX%rangeX==(rangeX-1)){
          position=initValueX+(int)Math.floor(i*rangeX*scale+1);
        }
        
        realX=(int)((position-initValueX)/scale);
        i++;
        
        //draws the line
        g2.setColor(Color.darkGray);
        g2.setStroke(stroke);
        g2.drawLine(position, 0, position, height);
        
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{2,2},0.0f));
        g2.drawLine(position, 0, position, height);
      }
      
      int realY=0;
      i=0; 
      position=0;
      
      //draws the horizontal lines
      while(position<height){
          
        position=initValueY+(int)Math.round(i*rangeY*scale+0.5);
        realY=(int)((position-initValueY)/scale);
        
        if(realY%rangeY==1){
          position=initValueY+(int)Math.round(i*rangeY*scale);
        }else if(realY%rangeY==(rangeY-1)){
          position=initValueY+(int)Math.floor(i*rangeY*scale+1);
        }
        
        realY=(int)((position-initValueY)/scale);
        i++;
        
        //draws the line
        g2.setColor(Color.darkGray);
        g2.setStroke(stroke);
        g2.drawLine(0, position, width, position);
        
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{2,2},0.0f));
        g2.drawLine(0, position, width, position);
      }
      g2.setColor(Color.black);
      g2.setStroke(stroke);
    }
  }
  /**
   * 
   * @param rangeY
   * @param rangeX
   * @param scale
   */
  public void setRanges(double scale, int rangeX, int rangeY){
    rangeXMap.put(new Double(scale), new Integer(rangeX));
		rangeYMap.put(new Double(scale), new Integer(rangeY));
		this.scrollPane.getSvgCanvas().repaint();
  }
  /**
   * 
   * @return 
   * @param scale
   */
  public Dimension getRanges(double scale){
    int rangeX=50;
    int rangeY=50;
		Double objScale=null;
    try{
      objScale=new Double(scale);
    }catch (Exception ex){objScale=null;}

    if(objScale!=null){
      Integer iRangeX=null, iRangeY=null;
      try{
        iRangeX=(Integer)rangeXMap.get(objScale);
        iRangeY=(Integer)rangeYMap.get(objScale);
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
  
  /**
   * 
   * @param g
   */
  public void paintToBeDone(Graphics g){
    //computes the intervals between each line and draws the grid
			double scale=this.scrollPane.getSvgCanvas().getScale();
			int width=this.scrollPane.getCanvasBounds().width;
      int height=this.scrollPane.getCanvasBounds().height;
			
			if(this.scrollPane.getCanvasBounds().x==0){
			  width=this.scrollPane.getImagePanel().getWidth();
			}
			
			if(this.scrollPane.getCanvasBounds().y==0){
			  height=this.scrollPane.getImagePanel().getHeight();
			}
			
			Double objScale=new Double(scale);
			Integer iRangeX=null;
      Integer iRangeY=null;

			try{
				iRangeX=(Integer)rangeXMap.get(objScale);
				iRangeY=(Integer)rangeYMap.get(objScale);
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
			
			if(this.scrollPane.getCanvasBounds().x==0){
			  initValueX=this.scrollPane.getImageTranslateValues().width;
			}
			
			if(this.scrollPane.getCanvasBounds().y==0){
        initValueY=this.scrollPane.getImageTranslateValues().height;
			}
			
			drawGrid(g, initValueX, initValueY, rangeX, rangeY, width, height);
  }
}