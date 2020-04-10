package bulbul.foff.studio.engine.listeners;
import bulbul.foff.studio.engine.ui.SVGScrollPane;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;

/**
 * @author Sudheer V. Pujar
 */
public class SBListener implements AdjustmentListener  {
  
  public static final int HORIZONTAL=0;
  public static final int  VERTICAL=1;
  
  private SVGScrollPane scrollPane;
  
  private boolean wasAdjusting=false;
  
  private int type=HORIZONTAL;
  private int startValue=0;

  /**
   * 
   * @param type
   * @param scrollPane
   */
  public SBListener(SVGScrollPane scrollPane, int type) {
    this.scrollPane=scrollPane;
    this.type=type;
  }

  /**
   * 
   * @return 
   */
  public int getStartValue() {
    return startValue;
  }

  /**
   * 
   * @param startValue
   */
  public void setStartValue(int startValue) {
    this.startValue = startValue;
  }
  
  /**
   * 
   * @param evt
   */
  public synchronized void adjustmentValueChanged(AdjustmentEvent evt){
			if(! this.scrollPane.isScrollChangeIgnored()){
        boolean isAdjusting=(this.type==VERTICAL)?this.scrollPane.getVerticalScrollBar().getValueIsAdjusting():this.scrollPane.getHorizontalScrollBar().getValueIsAdjusting();
        if(! wasAdjusting || ! isAdjusting){
					this.scrollPane.setPaintingValues(new Dimension(0,0));
				}

				wasAdjusting=isAdjusting;
        
				//the translation values for the rendering transform and the painting transform
				int transformX=this.scrollPane.getCanvasTranslateValues().width;
        int transformY=this.scrollPane.getCanvasTranslateValues().height;
        int paintingTransformX=this.scrollPane.getPaintingValues().width;
        int paintingTransformY=this.scrollPane.getPaintingValues().height;
				
				int newValue=evt.getValue();
				int differenceValue=startValue-newValue;
			
				//computes the new translation values giving the variation of the value of the scrollbars
				if (this.type==VERTICAL) {
					if(differenceValue<0){
						transformY=transformY-Math.abs(differenceValue);
            paintingTransformY=paintingTransformY-Math.abs(differenceValue);
					}else{
					  transformY=transformY+Math.abs(differenceValue);
            paintingTransformY=paintingTransformY+Math.abs(differenceValue);
					}
				} else {
					if(differenceValue<0){
						transformX=transformX-Math.abs(differenceValue);
            paintingTransformX=paintingTransformX-Math.abs(differenceValue);
					}else{
						transformX=transformX+Math.abs(differenceValue);
            paintingTransformX=paintingTransformX+Math.abs(differenceValue);
					}
				}
        
				//if the scrollbars are dragged
        if(isAdjusting){
         //computes the painting transform
          this.scrollPane.setPaintingValues(new Dimension(paintingTransformX,paintingTransformY));
          this.scrollPane.setCanvasTranslateValues(new Dimension(transformX,transformY));
          this.scrollPane.getSvgCanvas().setPaintingTransform(AffineTransform.getTranslateInstance(this.scrollPane.getPaintingValues().width, this.scrollPane.getPaintingValues().height));
				}else{
        //otherwise
					if(this.scrollPane.getPaintingValues().width!=0 && this.scrollPane.getPaintingValues().height!=0){
						//removes the painting transform
						this.scrollPane.getPaintingValues().width=0;
						this.scrollPane.getPaintingValues().height=0;
						this.scrollPane.getSvgCanvas().setPaintingTransform(AffineTransform.getTranslateInstance(this.scrollPane.getPaintingValues().width, this.scrollPane.getPaintingValues().height));
					}
          
          this.scrollPane.setCanvasTranslateValues(new Dimension(transformX,transformY));
          AffineTransform affineTransform4SvgCanvas=AffineTransform.getTranslateInstance(this.scrollPane.getCanvasTranslateValues().width, this.scrollPane.getCanvasTranslateValues().height);
          affineTransform4SvgCanvas.concatenate(AffineTransform.getScaleInstance(this.scrollPane.getSvgCanvas().getScale(), this.scrollPane.getSvgCanvas().getScale()));
          this.scrollPane.getSvgCanvas().setRenderingTransform(affineTransform4SvgCanvas);
          this.scrollPane.updateRendering();
        }
				startValue=newValue;
        
				//repaints the rulers
				this.scrollPane.getHorizontalRuler().repaint();
				this.scrollPane.getVerticalRuler().repaint();
			}
  }
  
}