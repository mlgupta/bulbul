package bulbul.foff.studio.engine.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Sudheer V. Pujar
 */
public class SVGRuler extends JPanel  {

  public static final int HORIZONTAL=0;
  public static final int VERTICAL=1;
  
  private int type=HORIZONTAL;
  
  private SVGScrollPane scrollPanel; 
  
  private Font rulersFont=new Font("myFont",Font.ROMAN_BASELINE,9);
  
  private Hashtable valuesToDiplayMap=new Hashtable();
  
  /**
   * 
   * @param type
   * @param scrollPanel
   */
  public SVGRuler(SVGScrollPane scrollPanel,int type) {
    this.scrollPanel=scrollPanel;
    this.type=type;
    
    setBorder(new LineBorder(Color.lightGray));
    
    //creates the maps for the range and the values to display//
    ArrayList list=null;
    
    //scale 0.05
    this.scrollPanel.getRulersRangesMap().put(new Double(0.05), new Integer(100));
    list=new ArrayList();
    list.add(new Integer(1000));
    valuesToDiplayMap.put(new Double(0.05), list);
    
    //scale 0.1
    this.scrollPanel.getRulersRangesMap().put(new Double(0.1), new Integer(100));
    list=new ArrayList();
    list.add(new Integer(1000));
    valuesToDiplayMap.put(new Double(0.1), list);
    
    //scale 0.2
    this.scrollPanel.getRulersRangesMap().put(new Double(0.2), new Integer(50));
    list=new ArrayList();
    list.add(new Integer(200));
    valuesToDiplayMap.put(new Double(0.2), list);
    
    //scale 0.5
    this.scrollPanel.getRulersRangesMap().put(new Double(0.5), new Integer(10));
    list=new ArrayList();
    list.add(new Integer(100));
    valuesToDiplayMap.put(new Double(0.5), list);
    
    //scale 0.75
    this.scrollPanel.getRulersRangesMap().put(new Double(0.75), new Integer(10));
    list=new ArrayList();
    list.add(new Integer(100));
    valuesToDiplayMap.put(new Double(0.75), list);

    //scale 1.0
    this.scrollPanel.getRulersRangesMap().put(new Double(1.0), new Integer(5));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    valuesToDiplayMap.put(new Double(1.0), list);
    
    //scale 1.25
    this.scrollPanel.getRulersRangesMap().put(new Double(1.25), new Integer(5));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    valuesToDiplayMap.put(new Double(1.25), list);
    
    //scale 1.5
    this.scrollPanel.getRulersRangesMap().put(new Double(1.5), new Integer(5));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    valuesToDiplayMap.put(new Double(1.5), list);
          
    //scale 2.0
    this.scrollPanel.getRulersRangesMap().put(new Double(2.0), new Integer(2));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(20));
    valuesToDiplayMap.put(new Double(2.0), list);					

    //scale 4.0
    this.scrollPanel.getRulersRangesMap().put(new Double(4.0), new Integer(1));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    list.add(new Integer(10));
    valuesToDiplayMap.put(new Double(4.0), list);	
    
    //scale 5.0
    this.scrollPanel.getRulersRangesMap().put(new Double(5.0), new Integer(1));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    list.add(new Integer(10));
    valuesToDiplayMap.put(new Double(5.0), list);	
    
    //scale 8.0
    this.scrollPanel.getRulersRangesMap().put(new Double(8.0), new Integer(1));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    list.add(new Integer(10));
    list.add(new Integer(5));
    valuesToDiplayMap.put(new Double(8.0), list);
    
    //scale 10.0
    this.scrollPanel.getRulersRangesMap().put(new Double(10.0), new Integer(1));
    list=new ArrayList();
    list.add(new Integer(100));
    list.add(new Integer(50));
    list.add(new Integer(10));
    list.add(new Integer(5));
    valuesToDiplayMap.put(new Double(10.0), list);
  }
  
  /**
   * 
   * @param g
   */
  public void paintComponent(Graphics g){
    super.paintComponents(g);
			
    if(this.scrollPanel.areRulersEnabled()){
        
      Graphics2D g2=(Graphics2D)g;
    
      //gets the values that will be used to draw the rulers
      double scale=this.scrollPanel.getSvgCanvas().getScale();
      Double objScale=new Double(scale);
      Integer iRange=null;
      ArrayList list=null;

      try{
        iRange=(Integer)this.scrollPanel.getRulersRangesMap().get(objScale);
        list=(ArrayList)valuesToDiplayMap.get(objScale);
      }catch (Exception ex){iRange=null; list=null;}

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
      int mousePosition=0;
      

      if(this.type==VERTICAL){
      
        //computes the values for the vertical ruler
        begin=this.scrollPanel.getCanvasBounds().y;
        end=(begin==0)?getHeight():(begin+this.scrollPanel.getCanvasBounds().height);
        mousePosition=this.scrollPanel.getMousePosition().y+this.scrollPanel.getCanvasBounds().y;

        //draws the bounds and the line representing the position of the mouse
        g.setColor(Color.black);
        g.drawLine(0, begin, getWidth(), begin);
        g.drawLine(0, end, getWidth(), end);
    
        g.setColor(Color.red);
        g.drawLine(0, mousePosition, getWidth(), mousePosition);

        //the interval that corresponds to the space between the minValue and the first drawned line
        
        int position=0;
        int realY=0;
        int i=0;
        int j;
        int k;
        int l;
        int ratio=6;
        int length=(int)(getWidth()/ratio);
        
        AttributedString as=null;
        String number="";

        //draws the rulers
        g.setColor(Color.black);
        while(position<end){
          //draws each line of the ruler
          position=begin+(int)Math.floor(i*range*scale);
          //realY=(int)(((position-begin)-this.scrollPanel.getTranslateValues().height)/scale);
          realY=(int)(((position-begin))/scale);
          if(realY%range==1){
            position=begin+(int)Math.round(i*range*scale-0.5);
          }else if(realY%range==(range-1)){
            position=begin+(int)Math.floor(i*range*scale+0.5);
          }
          //realY=(int)(((position-begin)-this.scrollPanel.getTranslateValues().height)/scale);
          realY=(int)(((position-begin))/scale);
          //display a longer line according to the value the line is associated with
          for(j=0;j<valuesToDisplay.length;j++){
            if(realY%(valuesToDisplay[j])==0){
              number=""+realY;
              l=1;
              for(k=0;k<number.length();k++){
                g.drawString(""+number.charAt(k), 2, position+l*rulersFont.getSize());
                l++;
              }
              break;
            }
          }
          i++;
          //draws the line
          g.drawLine(getWidth()-((ratio-j)+1)*length/2, position, getWidth(), position);
        }
      }else{

        //computes the values for the horizontal ruler
        begin=this.scrollPanel.getCanvasBounds().x;
        end=(begin==0)?getWidth():(begin+this.scrollPanel.getCanvasBounds().width);
        mousePosition=this.scrollPanel.getMousePosition().x+this.scrollPanel.getCanvasBounds().x;
    
        //draws the bounds and the line representing the position of the mouse
        g.setColor(Color.black);
        g.drawLine(begin, 0, begin, getHeight());
        g.drawLine(end, 0, end, getHeight());
    
        g.setColor(Color.red);
        g.drawLine(mousePosition, 0, mousePosition, getHeight());
        
        //the interval that corresponds to the space between the minValue and the first drawned line
        int position=0;
        int realX=0;
        int i=0;
        int j;
        int ratio=6;
        int length=(int)(getHeight()/ratio);
        
        //draws the rulers
        g.setColor(Color.black);
        while(position<end){
          //draws each line of the ruler
          position=begin+(int)Math.floor(i*range*scale);
          //realX=(int)(((position-begin)-this.scrollPanel.getTranslateValues().width)/scale);
          realX=(int)(((position-begin))/scale);
          if(realX%range==1){
            position=begin+(int)Math.round(i*range*scale-0.5);
          }else if(realX%range==(range-1)){
            position=begin+(int)Math.floor(i*range*scale+0.5);
          }
          //realX=(int)(((position-begin)-this.scrollPanel.getTranslateValues().width)/scale);
          realX=(int)(((position-begin))/scale);
          
          //display a longer line according to the value the line is associated with
          for(j=0;j<valuesToDisplay.length;j++){
            if(realX%(valuesToDisplay[j])==0){
              g.drawString(""+realX, position, 9);
              break;
            }
          }
          i++;
          //draws the line
          g.drawLine(position, getHeight()-((ratio-j)+1)*length/2, position, getHeight());
        }
      }
    }
  }
}