package bulbul.foff.studio.engine.managers;
import bulbul.foff.studio.engine.general.SVGCursors;
import bulbul.foff.studio.engine.shapes.SVGShape;
import bulbul.foff.studio.engine.ui.SVGApplet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @description 
 * @version 1.0 20-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGClassManager  {

  private SVGTabManager svgTabManager;
  private SVGCursors svgCursors;
  private SVGApplet studio;
  
  private LinkedList classObjects=new LinkedList();
  private LinkedList classQualifiedNames=new LinkedList();
  private LinkedList shapeObjects=new LinkedList();

  /**
   * 
   * @description 
   */
  public SVGClassManager(SVGApplet studio) {
    this.studio = studio;
  }
  
  /**
   * 
   * @description 
   */
  protected void createListOfClassFullNames(){
    
    //other objects
    classQualifiedNames.add("bulbul.foff.studio.engine.general.SVGGrid");
    classQualifiedNames.add("bulbul.foff.studio.engine.general.SVGRulers");
    classQualifiedNames.add("bulbul.foff.studio.engine.general.SVGZoom");
    classQualifiedNames.add("bulbul.foff.studio.engine.general.SVGClipboard");
    classQualifiedNames.add("bulbul.foff.studio.engine.general.SVGSaveNdSaveAs");
    
    classQualifiedNames.add("bulbul.foff.studio.engine.undoredo.SVGUndoRedo");
    
    classQualifiedNames.add("bulbul.foff.studio.engine.selection.SVGSelection");
    
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGGroupUnGroup");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGAlign");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGOrder");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGRotate");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGSame");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGSpacing");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGFlip");
    classQualifiedNames.add("bulbul.foff.studio.engine.domactions.SVGCenter");
    
    //shape objects
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGRectangle");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGCircle");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGEllipse");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGImage");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGLine");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGText");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGPolyLine");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGPolygon");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGPath");
    classQualifiedNames.add("bulbul.foff.studio.engine.shapes.SVGAny");
  }
  
  /**
   * 
   * @description 
   */
  protected void createClassObjects(){
    String current=null;
		Object object=null;
		for(Iterator it=classQualifiedNames.iterator(); it.hasNext();){
			current=(String)it.next();
			if(current!=null && !current.equals("")){
				try{
					Class[] classargs={SVGApplet.class};
					Object[] args={studio};
					//creates instances of each static module
					object=Class.forName(current).getConstructor(classargs).newInstance(args);
					synchronized(this){
						//if it is a shape module, it is added to the list of the shape module
						if(object instanceof SVGShape){
							shapeObjects.add(object);
						}
						classObjects.add(object);
					}
				}catch (Exception ex){System.out.println(ex);}	
			}
		}		
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public Object getClassObject(String name){
    Object current=null;
		String className=null;
		
		for(Iterator it=classObjects.iterator(); it.hasNext();){
			current=it.next();
			try{
				className=(String)current.getClass().getMethod("getName",null).invoke(current,null);
			}catch (Exception e){className=null;}
			if(className!=null && className.equals(name)){
			  return current;
			}
		}
		return null;
  }
 
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public SVGShape getShapeObject(String name){
    SVGShape current=null;
		String className=null;
		for(Iterator it=shapeObjects.iterator(); it.hasNext();){
			try{
				current=(SVGShape)it.next();
				className=current.getName();
			}catch (Exception e){className=null;}
			if(className!=null && className.equals(name)){
			  return current;
			}
		}
		return null;
  }
  
  
  /**
   * 
   * @description 
   */
  public void init(){
    
    //the object that manages the different SVGFrames in the desktop pane
		svgTabManager=new SVGTabManager(studio);
    
    //the cursor manager
		svgCursors=new SVGCursors();
    
    //creates list of Class Full Names
    createListOfClassFullNames();
  
		//creates the static classes
		createClassObjects();
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Collection getShapeObjects(){
    return shapeObjects;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public Collection getClassObjects() {
    return classObjects;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGCursors  getSvgCursors(){
    return svgCursors;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGTabManager getSVGTabManager(){
    return svgTabManager;
  }
}