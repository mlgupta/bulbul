package studio.general;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class SVGCursors  {
  /**
   * the hashtable of the cursors
   */
  private Hashtable svgCursors=new Hashtable();
  
  public SVGCursors() {
    Hashtable cursorBundle=new Hashtable();
    cursorBundle.put("default_default","DEFAULT_CURSOR");
    cursorBundle.put("default_wait","WAIT_CURSOR");
    cursorBundle.put("default_selection","HAND_CURSOR");
    cursorBundle.put("default_line","CROSSHAIR_CURSOR");
    cursorBundle.put("default_rectangle","CROSSHAIR_CURSOR");
    cursorBundle.put("default_circle","CROSSHAIR_CURSOR");
    cursorBundle.put("default_ellipse","CROSSHAIR_CURSOR");
    cursorBundle.put("default_polygon","CROSSHAIR_CURSOR");
    cursorBundle.put("default_polyline","CROSSHAIR_CURSOR");
    cursorBundle.put("default_path","CROSSHAIR_CURSOR");
    cursorBundle.put("default_text","TEXT_CURSOR");
    cursorBundle.put("default_image","CROSSHAIR_CURSOR");
    cursorBundle.put("default_rtdaview","CROSSHAIR_CURSOR");
    cursorBundle.put("default_rtdawidget","CROSSHAIR_CURSOR");
    cursorBundle.put("default_n_resize","N_RESIZE_CURSOR");
    cursorBundle.put("default_ne_resize","NE_RESIZE_CURSOR"); 
    cursorBundle.put("default_e_resize","E_RESIZE_CURSOR");
    cursorBundle.put("default_se_resize","SE_RESIZE_CURSOR");
    cursorBundle.put("default_s_resize","S_RESIZE_CURSOR");
    cursorBundle.put("default_sw_resize","SW_RESIZE_CURSOR");
    cursorBundle.put("default_w_resize","W_RESIZE_CURSOR");
    cursorBundle.put("default_nw_resize","NW_RESIZE_CURSOR");
    cursorBundle.put("default_modifypoint","HAND_CURSOR");
    cursorBundle.put("default_rotate","HAND_CURSOR");
    cursorBundle.put("default_translate","MOVE_CURSOR");
    cursorBundle.put("default_skewX","HAND_CURSOR");
    cursorBundle.put("default_skewY","HAND_CURSOR");
    cursorBundle.put("default_pointchooser","CROSSHAIR_CURSOR");
    
    
    //the keys enumeration of the resources
		Enumeration keys=null;
    String key="";
    String value="";
    String shortKey="";
    
    Cursor theCursor=null;
		if(cursorBundle!=null){
			keys=cursorBundle.keys();
		}
    
    if(keys!=null){
      while(keys.hasMoreElements()){
        key=(String)keys.nextElement();
        if(key!=null){
          //the key value without the prefix that tells whether the cursor is a default or a custom cursor
					shortKey=key.substring(key.indexOf('_')+1,key.length());
          if( ! svgCursors.containsKey(shortKey) && key.startsWith("default_")){
						value=(String)cursorBundle.get(key);
						if(value!=null && ! value.equals("") ){
							//gets the cursor that is defined by default in the Java implementation
							theCursor=getDefaultCursor(value);
							if(theCursor!=null){
								svgCursors.put(shortKey, theCursor);
							}
						}
					}
        }
      }
    }
  }
  /**
	 * @param name the name of the cursor
	 * @return the accurate cursor, the returned value is never null
	 */
	public Cursor getCursor(String name){
		Cursor cursor=null;
		if(name!=null && ! name.equals("") && svgCursors.containsKey(name)){
			try{
				cursor=(Cursor)svgCursors.get(name);
			}catch (Exception ex){cursor=null;}
		}
		//if the cursor found is valid, the cursor is returned 
    //otherwise the default cursor is returned
		return (cursor==null)?new Cursor(Cursor.DEFAULT_CURSOR):cursor ;
	}
  /**
	 * gets the accurate cursor given a name
	 * @param name the name of a cursor
	 * @return the cursor
	 */
	protected Cursor getDefaultCursor(String name){
		
		if(name!=null && ! name.equals("")){
			if(name.equals("CROSSHAIR_CURSOR")){
				return new Cursor(Cursor.CROSSHAIR_CURSOR);
			}else if(name.equals("DEFAULT_CURSOR")){
				return new Cursor(Cursor.DEFAULT_CURSOR);
			}else if(name.equals("E_RESIZE_CURSOR")){
				return new Cursor(Cursor.E_RESIZE_CURSOR);
			}else if(name.equals("HAND_CURSOR")){
				return new Cursor(Cursor.HAND_CURSOR);
			}else if(name.equals("MOVE_CURSOR")){
				return new Cursor(Cursor.MOVE_CURSOR);
			}else if(name.equals("N_RESIZE_CURSOR")){
				return new Cursor(Cursor.N_RESIZE_CURSOR);
			}else if(name.equals("NE_RESIZE_CURSOR")){
				return new Cursor(Cursor.NE_RESIZE_CURSOR);
			}else if(name.equals("NW_RESIZE_CURSOR")){
				return new Cursor(Cursor.NW_RESIZE_CURSOR);
			}else if(name.equals("S_RESIZE_CURSOR")){
				return new Cursor(Cursor.S_RESIZE_CURSOR);
			}else if(name.equals("SE_RESIZE_CURSOR")){
				return new Cursor(Cursor.SE_RESIZE_CURSOR);
			}else if(name.equals("SW_RESIZE_CURSOR")){
				return new Cursor(Cursor.SW_RESIZE_CURSOR);
			}else if(name.equals("TEXT_CURSOR")){
				return new Cursor(Cursor.TEXT_CURSOR);
			}else if(name.equals("W_RESIZE_CURSOR")){	
				return new Cursor(Cursor.W_RESIZE_CURSOR);
			}else if(name.equals("WAIT_CURSOR")){
				return new Cursor(Cursor.WAIT_CURSOR);
			}
		}
		
		return null;
	}
  
}