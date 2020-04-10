package bulbul.foff.studio.engine.selection;
import bulbul.foff.studio.engine.listeners.SVGPaintListener;
import bulbul.foff.studio.engine.shapes.SVGShape;
import bulbul.foff.studio.engine.ui.SVGCanvas;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoActionList;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGSelectionManager  {
  
  private final Map selectionSquares=new Hashtable();
  
  private SVGPaintListener paintListener=null;
  private SVGSelectionListener selectionListener=null;
  private SVGSelectionManager selectionManager=this;
  private SVGSelection selection=null;
  private SVGUndoRedoActionList undoRedoList=null;
  private SVGTab svgTab=null;
  
  private Cursor currentCursor=null;
  private Element parentElement=null;
  private Map currentSelectionType=new LinkedHashMap();
  private Map selectedItemsByModule=new Hashtable();
  private LinkedList lockedNodes=new LinkedList();

  private final Color outOfParentAreaColor=new Color(100, 100, 100, 50);
  private final Color outOfParentAreaBorderColor=new Color(100, 100, 100);

  
  private Thread refreshManager=null;
  
  private boolean keepRunning=true;
  private boolean shouldRefresh=false;
  /**
   * 
   * @description 
   */
  public SVGSelectionManager(SVGSelection selection, SVGTab svgTab) {
    this.selection=selection;
    this.svgTab=svgTab;
            
    //the parent
    Document document=svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
            
    if(document!=null){
      parentElement=document.getDocumentElement();
    }
    
    //adding the paint listener that will paint the area 
    paintListener=new SVGPaintListener(){
      public void paintToBeDone(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        if(getParentElement()!=null && getParentElement().getNodeName().equals("g")){
          
          //the bounds of the parent element
          Rectangle2D parentBounds=getSvgSelection().getStudio().getSvgToolkit().getNodeBounds(selectionManager.svgTab, getParentElement());
          Rectangle2D.Double objparentBounds=new Rectangle2D.Double(parentBounds.getX(), parentBounds.getY(), parentBounds.getWidth(), parentBounds.getHeight());
          Rectangle2D.Double scaledParentBounds=getSvgSelection().getStudio().getSvgToolkit().getScaledRectangle(getSVGTab(), objparentBounds, false);
          
          //the size of the canvas
          Dimension canvasSize=selectionManager.svgTab.getScrollPane().getSvgCanvas().getScaledCanvasSize();
          
          //the area to be painted
          Area area=new Area(new Rectangle(0, 0, canvasSize.width, canvasSize.height));
          area.subtract(new Area(new Rectangle((int)scaledParentBounds.x, (int)scaledParentBounds.y, (int)scaledParentBounds.width, (int)scaledParentBounds.height)));
          
          //painting the area
          g2.setColor(outOfParentAreaColor);
          g2.fill(area);
          
          g2.setColor(outOfParentAreaBorderColor);
          g2.draw(area);
        }
      }
    };
    
    svgTab.getScrollPane().getSvgCanvas().addBottomLayerPaintListener(paintListener, false);
    
    //the mouse and key listeners
    selectionListener=new SVGSelectionListener(this);
    
    svgTab.getScrollPane().getSvgCanvas().addMouseListener(selectionListener);
    svgTab.getScrollPane().getSvgCanvas().addMouseMotionListener(selectionListener);
    svgTab.getScrollPane().getSvgCanvas().addKeyListener(selectionListener);
    
    //the refresh manager
    refreshManager=new Thread(){
      public void run() {
        while(keepRunning){
          try{
            sleep((long)200);
          }catch (Exception ex){}
          if(shouldRefresh){
            redrawSelectionMethod();
            synchronized(this){shouldRefresh=false;}
          }
        }
      }
    };

    refreshManager.start();
  }
  
  /**
   * 
   * @description 
   */
  protected void dispose(){
    if(selectionListener!=null){
      synchronized(this){
        keepRunning=false;
      }
      svgTab.getScrollPane().getSvgCanvas().removeMouseListener(selectionListener);
      svgTab.getScrollPane().getSvgCanvas().removeMouseMotionListener(selectionListener);
      svgTab.getScrollPane().getSvgCanvas().removeKeyListener(selectionListener);
      selectedItemsByModule.clear();
      currentSelectionType.clear();
      selectionSquares.clear();
      lockedNodes.clear();
      if(undoRedoList!=null){
        undoRedoList.clear();
      }
    }
  }
  
  
  
  /**
   * 
   * @description 
   */
  protected void redrawSelectionMethod(){
    //stopping refreshing the canvas
    svgTab.getScrollPane().getSvgCanvas().setRepaintEnabled(false);
    //deselects all the shape modules
    SVGShape svgShape=null;
    for(Iterator it=getSvgSelection().getStudio().getShapeObjects().iterator(); it.hasNext();){
      try{
        svgShape=(SVGShape)it.next();
      }catch (Exception ex){svgShape=null;}
  
      if(svgShape!=null){
        svgShape.deselectAll(svgTab, false);
      }
    }
    clearSelectionSquares();
    Hashtable dSelectedItemsByModule=new Hashtable(selectedItemsByModule), selectedItems=null, map;
    LinkedHashMap dCurrentSelectionType=new LinkedHashMap(currentSelectionType);
    Iterator it2;
    LinkedList list=null;
    String current=null;
    Object object=null;

    //invokes on each module the "select" method passing the linked list containing the nodes to be selected for each module as an argument
    for(Iterator it=dSelectedItemsByModule.keySet().iterator(); it.hasNext();){
      try{
        current=(String)it.next();
        list=(LinkedList)dSelectedItemsByModule.get(current);
      }catch (Exception ex){list=null;}

      if(list!=null && list.size()>0){
        selectedItems=new Hashtable();
        for(it2=list.iterator(); it2.hasNext();){
          object=it2.next();
          if(object!=null){
            String type=(String)dCurrentSelectionType.get(object);
            if(type!=null){
              selectedItems.put(object, type);
            }
          }
        }

        //getting the shape module
        svgShape=getShapeObject(current);
        if(svgShape!=null){
          //selecting the items
          svgShape.select(svgTab, selectedItems);
          //getting the selection squares
          map=svgShape.getSelectionSquares(svgTab);
          if(map!=null){
            selectionSquares.putAll(map);
          }
        }
      }
    }

    //enabling refreshing the canvas
    svgTab.getScrollPane().getSvgCanvas().setRepaintEnabled(true);
  }
  
  /**
   * 
   * @description 
   */
  protected synchronized void redrawSelection(){
    shouldRefresh=true;
  }
  /**
   * 
   * @description 
   * @param isMultiSelectionEnabled
   * @param parent
   * @param rect
   */
  protected void select(Rectangle rectangle, Element parentElement, boolean isMultiSelectionEnabled){
    if(! isMultiSelectionEnabled){
      deselectAll(false, false);
    }

    if(rectangle!=null && parentElement!=null){
      Rectangle2D rectangle2D=null;
      for(Node current=parentElement.getFirstChild(); current!=null; current=current.getNextSibling()){
        if(current instanceof Element && selection.getStudio().getSvgToolkit().isElementAShape((Element)current)){
          rectangle2D=getSvgSelection().getStudio().getSvgToolkit().getNodeBounds(svgTab, current);
          if(rectangle2D!=null){
            Rectangle2D.Double rectangle2DD=new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth()+1, rectangle2D.getHeight()+1);
            //if the node is contained in the rectangle, it is selected
            if(rectangle2DD!=null && rectangle.contains(rectangle2DD)){
              handleNodeSelection((Element)current, isMultiSelectionEnabled, true, true);
            }
          }
        }
      }
      refreshSelection();
    }
  }
  
  /**
   * 
   * @description 
   * @param validateAction
   */
  protected void selectAll(boolean validateAction){
    final LinkedList nodesThatWereSelected=new LinkedList(currentSelectionType.keySet());

    //deselects all the selected nodes
    deselectAll(false, false);

    final LinkedList nodesToBeSelected=new LinkedList();

    if(getParentElement()!=null){
      for(Node current=getParentElement().getFirstChild(); current!=null; current=current.getNextSibling()){
        if(current instanceof Element && ! current.getNodeName().equals("defs")){
          handleNodeSelection((Element)current, false, true, false);
          nodesToBeSelected.add(current);
        }
      }
    }
    
    if(undoRedoList==null){
      undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredodeselectall);
    }
    
    //adding the undo/redo action
    undoRedoList.addLast(new SVGUndoRedoAction(getSvgSelection().undoredoselectall){

      public void undo() {
        redrawSelection();
      }

      public void redo() {
        redrawSelection();
      }
    });
    
    undoRedoList.setName(getSvgSelection().undoredoselectall);

    //refreshes the selection
    if(validateAction){
      refreshSelection();
    }
  }
  
  /**
   * 
   * @description 
   * @param executeWhenNoNodesSelected
   * @param validateAction
   */
  protected void deselectAll(boolean validateAction, boolean executeWhenNoNodesSelected){
    //creates the undo/redo action
    if(getSvgSelection().getStudio().getSvgUndoRedo()!=null && (isSmoothSelected() || executeWhenNoNodesSelected)){
      //creates a copy of the list of the selected items
      final LinkedList oldSelection=new LinkedList(currentSelectionType.keySet());
      SVGUndoRedoAction action=new SVGUndoRedoAction(getSvgSelection().undoredodeselectall){
        public void undo(){
          selectedItemsByModule.clear();
          currentSelectionType.clear();
          selectionSquares.clear();
          
          //reselects all the nodes that have been deselected
          Node current=null;
          for(Iterator it=oldSelection.iterator(); it.hasNext();){
            try{
              current=(Node)it.next();
            }catch (Exception e){current=null;}

            if(current!=null && current instanceof Element){
              handleNodeSelection((Element)current, false, false, false);
            }
          }
          
          redrawSelection();
          getSvgSelection().selectionChanged(false);
        }

        public void redo(){
          selectedItemsByModule.clear();
          currentSelectionType.clear();
          selectionSquares.clear();

            //deselects all the shape modules
          SVGShape svgShape=null;
          for(Iterator it=getSvgSelection().getStudio().getShapeObjects().iterator(); it.hasNext();){
            try{svgShape=(SVGShape)it.next();}catch (Exception ex){svgShape=null;}
            if(svgShape!=null){
                svgShape.deselectAll(svgTab, false);
            }
          }
          
          svgTab.getScrollPane().getSvgCanvas().delayedRepaint();
        }
      };
      
      //creates or gets the current undo/redo list and adds the new action into it
      if(undoRedoList==null){
        undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredodeselectall);
      }
      
      undoRedoList.addLast(action);
      undoRedoList.setName(getSvgSelection().undoredodeselectall);
    }
    
    //removing all the nodes from the selection 
    selectedItemsByModule.clear();
    currentSelectionType.clear();
    selectionSquares.clear();
  
    //deselects all the shape modules
    SVGShape svgShape=null;
    for(Iterator it=getSvgSelection().getStudio().getShapeObjects().iterator(); it.hasNext();){
      try{svgShape=(SVGShape)it.next();}catch (Exception ex){svgShape=null;}
      if(svgShape!=null){
          svgShape.deselectAll(svgTab, true);
      }
    }
  
    //refreshes the selection
    if(validateAction){
      refreshSelection();
    } 
  }
  
  /**
   * 
   * @description 
   * @param scaledPoint
   * @param scaledOriginPoint
   * @param point
   * @param originPoint
   * @param square
   */
  protected void doActions(SVGSelectionSquare square, Point originPoint, Point point, Point scaledOriginPoint, Point scaledPoint){
   if(square!=null && originPoint!=null && point!=null && scaledOriginPoint!=null && scaledPoint!=null){
      final String type=(String)currentSelectionType.get(square.getNode());
      final SVGShape shape=getShapeObject(square.getNode().getNodeName());
      final SVGSelectionSquare fsquare=square;
      RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
      
      if(type!=null && shape!=null && ! type.equals("lock") ){
        final Object[] args={square, new Point(originPoint.x, originPoint.y), new Point(point.x, point.y), new Point(scaledOriginPoint.x, scaledOriginPoint.y), new Point(scaledPoint.x, scaledPoint.y)};
        shape.doActions(svgTab, type, SVGShape.DO_ACTION, args);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param scaledPoint
   * @param scaledOriginPoint
   * @param point
   * @param originPoint
   * @param square
   */
  protected void validateDoActions(SVGSelectionSquare square, Point originPoint, Point point, Point scaledOriginPoint, Point scaledPoint){
    if(square!=null && originPoint!=null && point!=null && scaledOriginPoint!=null && scaledPoint!=null){
      final String type=(String)currentSelectionType.get(square.getNode());
      final SVGShape shape=getShapeObject(square.getNode().getNodeName());
      final SVGSelectionSquare fsquare=square;
      RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
      
      if(type!=null && shape!=null && ! type.equals("lock") ){
        final Object[] args={square, new Point(originPoint.x, originPoint.y), new Point(point.x, point.y), new Point(scaledOriginPoint.x, scaledOriginPoint.y), new Point(scaledPoint.x, scaledPoint.y)};
        Runnable runnable=new Runnable(){
          public void run() {
            shape.doActions(svgTab, type, SVGShape.VALIDATE_ACTION, args);
            getSvgSelection().selectionChanged(true);
          } 
        };

        if(queue.getThread()!=Thread.currentThread()){
          queue.invokeLater(runnable);
        }else{
          runnable.run();
        }
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param squares
   * @param node
   */
  protected void addSelectionSquares(Node node, LinkedList squares){
   if(selectionSquares.containsKey(node)){
      selectionSquares.remove(node);
    }
    selectionSquares.put(node, squares);
  }
  
  /**
   * 
   * @description 
   * @param squares
   * @param node
   */
  protected void removeSelectionSquares(Node node, LinkedList squares){
    if(selectionSquares.containsKey(node)){
      selectionSquares.remove(node);
    }
  }
  
  /**
   * 
   * @description 
   */
  protected void clearSelectionSquares(){
    LinkedList currentList=null;
    for(Iterator it=selectionSquares.keySet().iterator(); it.hasNext();){
      currentList=(LinkedList)selectionSquares.get(it.next());
      if(currentList!=null){
        currentList.clear();
      }
    }
    selectionSquares.clear();
  }

  /**
   * 
   * @description 
   */
  protected void lock(){
    //the hashtable of the current selection and the hashtable after the lock action
    final LinkedHashMap oldSelection=new LinkedHashMap(), newSelection=new LinkedHashMap();
    
    //the list of the nodes to be locked
    final LinkedList nodesToBeLocked=new LinkedList();

    //copies the hashtable of the current selection
    LinkedList list=new LinkedList(currentSelectionType.keySet());
    Object object=null;
    for(Iterator it0=currentSelectionType.keySet().iterator(); it0.hasNext();){
      object=it0.next();
      oldSelection.put(object, currentSelectionType.get(object));
    }

    //modifies the map of the current selection
    Node current=null;
    currentSelectionType.clear();
    for(Iterator it=list.iterator(); it.hasNext();){
      try{
        current=(Node)it.next();
      }catch (Exception ex){current=null;}
      if(current!=null){
        currentSelectionType.put(current,"lock");
        newSelection.put(current,"lock");
        if(! lockedNodes.contains(current)){
          lockedNodes.add(current);
          nodesToBeLocked.add(current);
        }
      }
    }

    SVGUndoRedoAction action=new SVGUndoRedoAction(getSvgSelection().undoredounlock){
      public void undo() {
        //restores the old selection
        lockedNodes.removeAll(nodesToBeLocked);
        currentSelectionType.clear();
        currentSelectionType.putAll(oldSelection);
        redrawSelection();
        
        //notifies that the selection has changed
        getSvgSelection().selectionChanged(false);
      }

      public void redo(){
        //restores the selection that had been modified
        lockedNodes.addAll(nodesToBeLocked);
        currentSelectionType.clear();
        currentSelectionType.putAll(newSelection);
        redrawSelection();
        
        //notifies that the selection has changed
        getSvgSelection().selectionChanged(false);
      }
    };
    
    //creates or gets the current undo/redo list and adds the new action into it
    if(undoRedoList==null){
      undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredolock);
    }
    
    undoRedoList.addLast(action);
    undoRedoList.setName(getSvgSelection().undoredolock);
    refreshSelection();
  }
  
  /**
   * 
   * @description 
   */
  protected void unlock(){
    //the hashtable of the current selection and the hashtable after the lock action
    final LinkedHashMap oldSelection=new LinkedHashMap(), newSelection=new LinkedHashMap();
    LinkedList list=new LinkedList(currentSelectionType.keySet());
  
    //copies the hashtable of the current selection
    Object object=null;
    for(Iterator it0=currentSelectionType.keySet().iterator(); it0.hasNext();){
      object=it0.next();
      oldSelection.put(object, currentSelectionType.get(object));
    }
  
    //modifies the hastable of the current selection
    Node current=null;
    currentSelectionType.clear();
    for(Iterator it=list.iterator(); it.hasNext();){
      try{
        current=(Node)it.next();
      }catch (Exception ex){current=null;}
      if(current!=null){
        currentSelectionType.put(current,"level1");
        newSelection.put(current,"level1");
        lockedNodes.remove(current);
      }
    }
  
    //the list of the nodes to be unlocked
    final LinkedList fnodes=new LinkedList(list);
  
    SVGUndoRedoAction action=new SVGUndoRedoAction(getSvgSelection().undoredounlock){
      public void undo() {
        //restores the old selection
        lockedNodes.addAll(fnodes);
        currentSelectionType.clear();
        currentSelectionType.putAll(oldSelection);
        redrawSelection();
        
        //notifies that the selection has changed
        getSvgSelection().selectionChanged(false);
      }

      public void redo(){
        //restores the selection that had been modified
        lockedNodes.removeAll(fnodes);
        currentSelectionType.clear();
        currentSelectionType.putAll(newSelection);
        redrawSelection();
        
        //notifies that the selection has changed
        getSvgSelection().selectionChanged(false);
      }
    };
    
    //creates or gets the current undo/redo list and adds the new action into it
    if(undoRedoList==null){
      undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredounlock);
    }
    
    undoRedoList.addLast(action);
    undoRedoList.setName(getSvgSelection().undoredounlock);
    refreshSelection();
  }
  
  
  /**
   * 
   * @description 
   * @param translationValues
   */
  protected void translateSelectedNodes(Dimension translationValues){
    if(translationValues!=null){
      Iterator it2;
      LinkedList list=null, notLockedNodes=null;
      SVGShape shape=null;
      String name="";
      Node node=null;
      for(Iterator it=selectedItemsByModule.keySet().iterator(); it.hasNext();){
        try{
          name=(String)it.next();
        }catch (Exception ex){name=null;}
        if(name!=null && ! name.equals("")){
          shape=getShapeObject(name);
          if(shape!=null){
            try{
                list=(LinkedList)selectedItemsByModule.get(name);
            }catch (Exception ex){list=null;}
            if(list!=null && list.size()>0){
              notLockedNodes=new LinkedList();
              //creates the list that does not contain the locked nodes
              for(it2=list.iterator(); it2.hasNext();){
                try{
                  node=(Node)it2.next();
                }catch (Exception ex){node=null;}
                if(node!=null && ! isLocked(node)){
                  notLockedNodes.add(node);
                }
              }

              if(notLockedNodes!=null && notLockedNodes.size()>0){
                Object[] args={notLockedNodes, translationValues};
                //invokes the "doActions" method on the nodes to translate them
                shape.doActions(svgTab, "level1", SVGShape.DO_TRANSLATE_ACTION, args);
              }
            }
          }
        }
      }
    }
  }
    
  /**
   * 
   * @description 
   * @param translationValues
   */
  protected void validateTranslateSelectedNodes(Dimension translationValues){
    final Dimension finalTranslationValues=translationValues;
    if(finalTranslationValues!=null){
      RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
      Runnable runnable=new Runnable(){
        public void run(){
          Iterator it2;
          LinkedList list=null, notLockedNodes=null;
          SVGShape shape=null;
          String name="";
          Node node=null;
          //for each type of modules
          for(Iterator it=selectedItemsByModule.keySet().iterator(); it.hasNext();){
            try{
              name=(String)it.next();
            }catch (Exception ex){name=null;}
            if(name!=null && ! name.equals("")){
              //gets the module
              shape=getShapeObject(name);
              if(shape!=null){
                try{
                  list=(LinkedList)selectedItemsByModule.get(name);
                }catch (Exception ex){list=null;}
                if(list!=null && list.size()>0){
                  notLockedNodes=new LinkedList();
                  //creates the list that does not contain the locked nodes
                  for(it2=list.iterator(); it2.hasNext();){
                    try{
                      node=(Node)it2.next();
                    }catch (Exception ex){node=null;}
                    if(node!=null && ! isLocked(node)){
                      notLockedNodes.add(node);
                    }
                  }

                  if(notLockedNodes.size()>0){
                    Object[] args={notLockedNodes, finalTranslationValues};
                    //invokes the "doActions" method on the nodes to translate them
                    shape.doActions(svgTab, "level1", SVGShape.VALIDATE_TRANSLATE_ACTION,args);
                  }
                }
              }
            }
          }

          //notifies that the selection has changed
          refreshSelection();
          getSvgSelection().selectionChanged(true);
        }
      };

      if(queue.getThread()!=Thread.currentThread()){
        queue.invokeLater(runnable);
      }else{
        runnable.run();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  protected boolean isSmoothSelected(){
    return (currentSelectionType.size()>0);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  protected boolean isSelected(Node node){
     return currentSelectionType.containsKey(node);
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  protected boolean isLocked(Node node){
    return (node!=null && lockedNodes.contains(node));
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  protected SVGSelection getSvgSelection() {
    return this.selection;
  }
  
  
  
  /**
   * 
   * @description 
   * @return 
   */
  protected SVGTab getSVGTab(){
    return this.svgTab;
  }
  

  /**
   * 
   * @description 
   * @return 
   */
  protected LinkedList getCurrentSelection(){
    LinkedList list=new LinkedList();
    Node current=null;
    for(Iterator it=currentSelectionType.keySet().iterator(); it.hasNext();){
      try{
        current=(Node)it.next();
      }catch (Exception ex){current=null;}
      if(current!=null){
        String type=(String)currentSelectionType.get(current);
        if(type!=null && ! type.equals("lock")){
          list.add(current);
        }
      }
    }
    return list;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  protected Map getCurrentSelectionTypeMap(){
    return new Hashtable(currentSelectionType); 
  }
  
  /**
   * 
   * @description 
   * @param point
   */
  protected void setCursor(Point point){
    SVGSelectionSquare square=getSelectionSquare(point);
    SVGCanvas canvas=svgTab.getScrollPane().getSvgCanvas();
    if(square!=null){
      //if the selection square is not null, sets the canvas cursor with the cursor associated with the square
      currentCursor=square.getCursor();
      canvas.setSVGCursor(currentCursor);
    }else{
      //if the selection square is null, 
      //if the node (on which the mouse event has been done) is selected, 
      //sets the new cursor, otherwise sets the default cursor
      Node node=getSvgSelection().getStudio().getSvgToolkit().getNodeAt(svgTab, parentElement, point);
      if(node!=null && isSelected(node) && ! isLocked(node)){
        currentCursor=getSvgSelection().getStudio().getSvgCursors().getCursor("translate");
      }else{
        currentCursor=getSvgSelection().getStudio().getSvgCursors().getCursor("default");
      }
      canvas.setSVGCursor(currentCursor);
    }
  }
  
  /**
   * 
   * @description 
   */
  public void enterGroup(){
    LinkedList currentSelection=new LinkedList(currentSelectionType.keySet());
    if(currentSelection.size()==1){
      final Element lastParent=getParentElement();
      //getting the selected g node
      Element currentParent=null;
      try{
          currentParent=(Element)currentSelection.getFirst();
      }catch (Exception ex){currentParent=null;}
      if(currentParent!=null){
        //sets the new parent
        setParentElement(currentParent);
        final Element newParentElement=currentParent;
        //adding the undo/redo action
        if(getSvgSelection().getStudio().getSvgUndoRedo()!=null){
          if(undoRedoList==null){
            undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredogroupenter);
          }

          //adding the undo/redo action
          undoRedoList.addLast(new SVGUndoRedoAction(getSvgSelection().undoredogroupenter){
            public void undo() {
                setParentElement(lastParent);
                redrawSelection();
            }
            public void redo() {
                setParentElement(newParentElement);
                redrawSelection();
            }
          });
            
          undoRedoList.setName(getSvgSelection().undoredogroupenter);
        }
        //deselects all the nodes
        deselectAll(false, false);
        refreshSelection();
      }
    }
  }
  
  /**
   * 
   * @description 
   */
  public void exitGroup(){
    final Element lastParent=getParentElement();
    if(lastParent!=null){
      //getting the parent element of the last parent element
      Element currentParent=null;
      try{
        currentParent=(Element)lastParent.getParentNode();
      }catch (Exception ex){}
  
      if(currentParent!=null){
        //sets the new parent
        setParentElement(currentParent);
        
        final Element newParentElement=currentParent;

        //adding the undo/redo action
        if(getSvgSelection().getStudio().getSvgUndoRedo()!=null){
          if(undoRedoList==null){
            undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredogroupexit);
          }
          
          //adding the undo/redo action
          undoRedoList.addLast(new SVGUndoRedoAction(getSvgSelection().undoredogroupexit){
            public void undo() {
              setParentElement(lastParent);
              redrawSelection();
            }
            public void redo() {
              setParentElement(newParentElement);
              redrawSelection();
            }
          });
          undoRedoList.setName(getSvgSelection().undoredogroupexit);
        }
        //deselects all the nodes
        deselectAll(false, false);
        refreshSelection();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param point
   */
  public SVGSelectionSquare getSelectionSquare(Point point){
    Point2D.Double pt=getSvgSelection().getStudio().getSvgToolkit().getScaledPoint(svgTab, new Point2D.Double(point.x, point.y), false);
    
    Iterator it2=null;
    LinkedList currentList=null;
    SVGSelectionSquare square=null;
    Rectangle2D.Double rectangle=null;
    for(Iterator it=selectionSquares.values().iterator(); it.hasNext();){
      currentList=(LinkedList)it.next();
      if(currentList!=null){
        for(it2=currentList.iterator(); it2.hasNext();){
          square=(SVGSelectionSquare)it2.next();
          rectangle=square.getRectangle();
          if(rectangle!=null && rectangle.contains(pt)){
              return square;
          }
        }
      }
    }
    return null;
  }
  
  
  /**
   * 
   * @description 
   * @param table
   */
  public void addSelectionSquares(Hashtable table){
    if(table!=null){
      selectionSquares.putAll(table);
    }
  }
  /**
   * 
   * @param element
   * @param isMultiSelectionEnabled
   * @param enableUndoRedoAction
   * @param alwaysSelect
   * @description 
   */
  public void handleNodeSelection(Element element, boolean isMultiSelectionEnabled, boolean enableUndoRedoAction, boolean alwaysSelect){
    if(		element!=null && ! element.getNodeName().equals("svg") && 
            ! element.equals(element.getOwnerDocument().getDocumentElement())){
      
      final Element finalElement=element;
      //gets the list of the selected nodes managed by one module
      LinkedList list=(LinkedList)selectedItemsByModule.get(element.getNodeName());
        
        if(list==null){
          list=new LinkedList();
        }

        //if the list does not contain a node, the node is added, otherwise, it is removed
        if(alwaysSelect || ! isMultiSelectionEnabled || (isMultiSelectionEnabled && ! list.contains(element))){
          
         //sets the selection level
          String type="default";
          if(! list.contains(element)){
            list.add(element);
          }else{
            type=(String)currentSelectionType.get(element);
          }

          selectedItemsByModule.put(element.getNodeName(), list);

          //gets the default first selection level for the node
          SVGShape shape=getShapeObject(element.getNodeName());
          if(shape!=null && ! isLocked(element)){
            type=shape.getNextLevel(type);
          }else if(isLocked(element)){
            type="lock";
          }

          currentSelectionType.put(element, type);

          //create the undo/redo action and insert it into the undo/redo stack
          if(enableUndoRedoAction && getSvgSelection().getStudio().getSvgUndoRedo()!=null){
            final String ftype=type;
            
            SVGUndoRedoAction action=new SVGUndoRedoAction(getSvgSelection().undoredoselect){
              public void undo(){
                //gets the list of the selected nodes managed by one module
                LinkedList list=(LinkedList)selectedItemsByModule.get(finalElement.getNodeName());
                if(list==null){
                  list=new LinkedList();
                }

                //removes the node from the list
                list.remove(finalElement);
                
                //removes the list from the map if it is empty
                if(list.size()==0){
                  selectedItemsByModule.remove(finalElement.getNodeName());
                }
                
                //removes the node from the map of the types
                currentSelectionType.remove(finalElement);
              }

              public void redo(){
                  //gets the list of the selected nodes managed by one module
                  LinkedList list=(LinkedList)selectedItemsByModule.get(finalElement.getNodeName());

                  if(list==null){
                    list=new LinkedList();
                  }

                  //removes the node from the list
                  list.add(finalElement);
                  
                  //removes the list from the map if it is empty
                  if(list.size()==1){
                    selectedItemsByModule.put(finalElement.getNodeName(), list);
                  }
                  
                  //removes the node from the map of the types
                  currentSelectionType.put(finalElement, ftype);
              }
            };
              
            //creates a undoredo list into which the action will be inserted
            if(undoRedoList==null){
              undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredoselect);
            }
              
            undoRedoList.add(action);
            undoRedoList.setName(getSvgSelection().undoredoselect);
          }
        }else if(isMultiSelectionEnabled && list.contains(element)){
          list.remove(element);
          //removes this list from the map if it is empty
          if(list.size()==0){
            selectedItemsByModule.remove(element.getNodeName());
          }
          
          //getting the type associated with this node
          final String type=(String)currentSelectionType.get(element);
          currentSelectionType.remove(element);
          
          //create the undo/redo action and insert it into the undo/redo stack
          if(enableUndoRedoAction && getSvgSelection().getStudio().getSvgUndoRedo()!=null){

            SVGUndoRedoAction action=new SVGUndoRedoAction(getSvgSelection().undoredodeselect){
              public void undo() {
                
                //gets the list of the selected nodes managed by one module
                LinkedList list=(LinkedList)selectedItemsByModule.get(finalElement.getNodeName());
                
                if(list==null){
                  list=new LinkedList();
                }

                //removes the node from the list
                list.add(finalElement);
                
                //removes the list from the map if it is empty
                if(list.size()==1){
                  selectedItemsByModule.put(finalElement.getNodeName(), list);
                }
                
                //removes the node from the map of the types
                currentSelectionType.put(finalElement, type);
              }

              public void redo(){
                //gets the list of the selected nodes managed by one module
                LinkedList list=(LinkedList)selectedItemsByModule.get(finalElement.getNodeName());
                
                if(list==null){
                  list=new LinkedList();
                }

                //removes the node from the list
                list.remove(finalElement);
                
                //removes the list from the map if it is empty
                if(list.size()==0){
                  selectedItemsByModule.remove(finalElement.getNodeName());
                }
                
                //removes the node from the map of the types
                currentSelectionType.remove(finalElement);
              }
            };

            //creates a undoredo list into which the action will be inserted
            if(undoRedoList==null){
                
                undoRedoList=new SVGUndoRedoActionList(getSvgSelection().undoredodeselect);
            }
            
            undoRedoList.addLast(action);
            undoRedoList.setName(getSvgSelection().undoredodeselect);
        }
      }
    } 
  }
  
  /**
   * 
   * @description 
   */
  public void refreshSelection(){
    //redraws the selections of the selected nodes
    redrawSelection();

    //adds the current undo/redo list into the undo/redo stack
    if(getSvgSelection().getStudio().getSvgUndoRedo()!=null && undoRedoList!=null && undoRedoList.size()>0){

      SVGUndoRedoAction action=new SVGUndoRedoAction(""){
        public void undo() {
          redrawSelection();
          getSvgSelection().selectionChanged(false);
        }

        public void redo(){
          redrawSelection();
          getSvgSelection().selectionChanged(false);
        }
      };

      undoRedoList.addLast(action);
      getSvgSelection().getStudio().getSvgUndoRedo().addActionList(svgTab, undoRedoList);
      undoRedoList=null;
    }

    //notifies that the selection has changed
    getSvgSelection().selectionChanged(false); 
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public SVGShape getShapeObject(String name){
    SVGShape shape=null;
    if(name!=null && ! name.equals("")){
      shape=getSvgSelection().getStudio().getShapeObject(name);
    }
    if(shape==null){
      shape=getSvgSelection().getStudio().getShapeObject("any");
    }
    return shape;
  }

  /**
   * 
   * @description 
   * @param action
   */
  public void addUndoRedoAction(SVGUndoRedoAction action){
    if(action!=null && getSvgSelection().getStudio().getSvgUndoRedo()!=null){
      if(undoRedoList==null){
        undoRedoList=new SVGUndoRedoActionList(action.getName());
      }
      undoRedoList.addLast(action);
      undoRedoList.setName(action.getName());
    } 
  }


  /**
   * 
   * @description 
   * @param parentElement
   */
  public synchronized void setParentElement(Element parentElement) {
    this.parentElement = parentElement;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Element getParentElement() {
    return parentElement;
  }
}