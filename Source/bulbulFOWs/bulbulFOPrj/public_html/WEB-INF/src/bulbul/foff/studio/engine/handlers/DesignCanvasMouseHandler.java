package bulbul.foff.studio.engine.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import bulbul.foff.studio.engine.DesignCanvas;


public class DesignCanvasMouseHandler implements MouseListener,MouseMotionListener {
  
  private void selection(MouseEvent e){
    DesignCanvas designCanvas=(DesignCanvas)e.getSource();
    designCanvas.clearSelectedElement(null);    
  }
  
  public void mousePressed(MouseEvent e){
    selection(e);
  }
  
  public void mouseReleased(MouseEvent e){    
    selection(e);     
  }
  
  public void mouseDragged(MouseEvent e){
   
  }
  
  public void mouseClicked(MouseEvent e){
  
  }
  
  public void mouseEntered(MouseEvent e){
  
  }
  
  public void mouseExited(MouseEvent e){
  
  }
  
  public void mouseMoved(MouseEvent e){
  
  }

}
