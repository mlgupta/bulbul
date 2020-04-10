package bulbul.foff.studio.engine.listeners;
import bulbul.foff.studio.engine.ui.Studio;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Sudheer V. Pujar
 */
public class SVGZoomActionListener implements ActionListener {
 
  private Studio studio;
  private double scale=1.0;
  String undoredozoom="";

  /**
   * 
   * @param studio
   */
  public SVGZoomActionListener(Studio studio) {
    this.studio=studio;
  }

  /**
   * 
   * @param scale
   * @param svgTab
   */
  private void setToScale(SVGTab svgTab, double scale){
    if (svgTab.getScrollPane().getSvgCanvas().getGVTRoot()==null) {
			return;
		}
    svgTab.getScrollPane().renderZoom(scale);
    //sets the piece of information in the state bar
    svgTab.getStudio().getMainStatusBar().setZoom(new Integer((int)(100*scale)).toString()+" %");	
  }
  
  /**
   * 
   * @param evt
   */
  public void actionPerformed(ActionEvent evt){
    final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
			if(svgTab!=null){
				getStudio().cancelActions(true);

				//create the undo/redo action and insert it into the undo/redo stack
				if(getStudio().getSvgUndoRedo()!=null){
					SVGUndoRedoAction action=new SVGUndoRedoAction(undoredozoom){
						/**
						 * the last scale
						 */
						private final double lastScale=svgTab.getScrollPane().getSvgCanvas().getScale();
					
						/**
						 * the new scale
						 */
						private final double currentScale=scale;
						public void undo(){
							setToScale(svgTab, lastScale);
						}

						public void redo(){		
							setToScale(svgTab, currentScale);
						}
					};
					
					SVGUndoRedoActionList actionlist=new SVGUndoRedoActionList(undoredozoom);
					actionlist.add(action);
					getStudio().getSvgUndoRedo().addActionList(svgTab, actionlist);	
				}

				//scales the picture
				setToScale(svgTab, scale);
			}
  }

  /**
   * 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }


  /**
   * 
   * @param scale
   */
  public void setScale(double scale) {
    this.scale = scale;
  }
}