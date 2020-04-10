package studio.manager;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import studio.canvas.SVGScrollPane;
import studio.general.SVGCursors;

public class SVGClassManager  {

  /**
	 * the SVGScrollPane
	 */
	private SVGScrollPane theSVGScrollPane;
	/**
	 * the list of the modules
	 */
	private LinkedList classObjects=new LinkedList();
	
	/**
	 * the list of the classes of the modules
	 */
	private LinkedList classFullNames=new LinkedList();
  
  	/**
	 * the manager of the cursors
	 */
	private SVGCursors svgCursors;
  
  private SVGManager svgManager;
  
  public SVGClassManager(SVGScrollPane theSVGScrollPane) {
    this.theSVGScrollPane=theSVGScrollPane;
  }
  /**
	 * initializes the object
	 */
	public void init(){
    //the object that manages the different SVGFrames in the desktop pane
		svgManager=new SVGManager(theSVGScrollPane);
    //the cursor manager
		svgCursors=new SVGCursors();
    
    //creates list of Class Full Names
    createListOfClassFullNames();
  
		//creates the static classes
		createClassObjects();
		
	}
  /**
	 * creates list of Class Full Names
	 */
	protected void createListOfClassFullNames(){
    //classFullNames.add("studio.shape.SVGRectangle");
    //classFullNames.add("studio.shape.SVGCircle");
    //classFullNames.add("studio.shape.SVGEllipse");
    //classFullNames.add("studio.shape.SVGImage");
    //classFullNames.add("studio.shape.SVGLine");
    //classFullNames.add("studio.shape.SVGText");
    //classFullNames.add("studio.shape.SVGPolyline");
    //classFullNames.add("studio.shape.SVGPolygon");
    //classFullNames.add("studio.shape.SVGPath");
    classFullNames.add("studio.shape.SVGAny");
    classFullNames.add("studio.canvas.SVGSelection");
	}
  /**
	 * creates the objects corresponding to the Class Full Names
	 */
	protected void createClassObjects(){
		
		Iterator iterator=classFullNames.iterator();
		String theClassName=null;
		Object theClassObject=null;
		int i=0;
		while(iterator.hasNext()){
			
			theClassName=(String)iterator.next();
			
			if(theClassName!=null && !theClassName.equals("")){
				try{
					Class[] classargs={SVGScrollPane.class};
					Object[] args={theSVGScrollPane};
					//creates instances of each static class
					theClassObject=Class.forName(theClassName).getConstructor(classargs).newInstance(args);
					synchronized(this){
						classObjects.add(theClassObject);
					}
          i++;
				}catch (Exception ex){
          //ex.printStackTrace();
        }	
			}
		}		
	}

	/**
	 * gets the module given its name
	 * @param name the module's name
	 * @return a module
	 */
	public Object getClassObject(String name){
		
		Iterator iterator=classObjects.iterator();
		Object theClassObject=null;
		String theClassFullName=null;
		
		while(iterator.hasNext()){
			theClassObject=iterator.next();
			try{
				theClassFullName=(String)theClassObject.getClass().getMethod("getName",null).invoke(theClassObject,null);
			}catch (Exception e){
        //e.printStackTrace();
      }
			if(theClassFullName!=null && theClassFullName.equals(name))return theClassObject;
		}
		return null;
	}
	
	/**
	 * @return the collection the objects corresponding to the classObjects
	 */
	public Collection getClassObjects(){
		return classObjects;
	}
  
  /**
	 * @return the manager of the cursors
	 */
	public SVGCursors getCursors(){
		return svgCursors;
	}
  
  public SVGManager getSVGManager(){
		return svgManager;
	}
  
}