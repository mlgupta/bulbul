package bulbul.foff.studio.engine.selection;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedoAction;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @description 
 * @version 1.0 22-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGSelection implements SVGClassObject {

  private final Color LINE_SELECTION_COLOR=new Color(75, 100, 200);
  
  private LinkedList selectionListeners=new LinkedList();
  
  private Map selectionManagers=Collections.synchronizedMap(new Hashtable());
  
  private final java.util.List lastSelectedNodes=Collections.synchronizedList(new LinkedList());
  
  private final String idregularmode="RegularMode";
  private final String idselection="Selection";
  private final String idselectall="SelectAll";
  private final String iddeselectall="DeselectAll";
  private final String idlock="Lock";
  private final String idunlock="UnLock";
  private final String idgroupenter="EnterGroup";
  private final String idgroupexit="ExitGroup";
  private final String idgroupmenu="GroupMenu";
  
  protected String undoredoselect=""; 
  protected String undoredodeselect=""; 
  protected String undoredodeselectall="";
  protected String undoredoselectall=""; 
	protected String undoredolock="";
  protected String undoredounlock="";
  protected String undoredogroupenter="";
  protected String undoredogroupexit="";
  
  private DecimalFormat format;
  
  private boolean selectionEnabled=true;
  
  private SVGApplet studio;
  private final SVGSelection svgSelection=this;
  
  private JToggleButton regularModeTool=null;
  
  private JButton selectAll;
  private JButton deselectAll;
  
  private JButton lock;
  private JButton unlock;
  
  private JButton groupEnter;
  private JButton groupExit;
 
  

  /**
   * 
   * @description 
   */
  public SVGSelection(SVGApplet studio) {
    this.studio=studio;
    //sets the format used to convert numbers into a string
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("######.#",symbols);
    
    regularModeTool=getStudio().getSvgToolBar().getStbRegular();
    
    selectAll=getStudio().getMainToolBar().getMtbSelectAll();
    deselectAll=getStudio().getMainToolBar().getMtbDeselectAll();
    
    lock=getStudio().getMainToolBar().getMtbLock();
    unlock=getStudio().getMainToolBar().getMtbUnLock();
    
    groupEnter=getStudio().getMainToolBar().getMtbGroupEnter();
    groupExit=getStudio().getMainToolBar().getMtbGroupExit();
    
        //a listener that listens to the changes of the SVGFrames
    final ActionListener svgTabListener=new ActionListener(){
      
      private ActionListener selectionListener=null;
      
      public void actionPerformed(ActionEvent e) {
        
        //clears the last selection
        lastSelectedNodes.clear();
        
        //deals with the state of the Tool items
        if(getStudio().getSvgTabManager().getSvgTabNumber()>0){
          selectAll.setEnabled(true);
          deselectAll.setEnabled(true);
          regularModeTool.setEnabled(true);
          //regularModeTool.setIcon(regularModeIcon);
        }else{
          selectAll.setEnabled(false);
          deselectAll.setEnabled(false);
          regularModeTool.setEnabled(false);
          //regularModeTool.setIcon(regularModeDisabledIcon);
        }
        //disables the Tool items
        lock.setEnabled(false);
        unlock.setEnabled(false);
        groupEnter.setEnabled(false);
        groupExit.setEnabled(false);

        SVGSelectionManager selectionManager=null;
        Collection svgTabs=getStudio().getSvgTabManager().getSvgTabs();

        for(Iterator it=new LinkedList(selectionManagers.values()).iterator(); it.hasNext();){
          try{
            selectionManager=(SVGSelectionManager)it.next();
          }catch (Exception ex){selectionManager=null;}
          
          if(selectionManager!=null && ! svgTabs.contains(selectionManager.getSVGTab())){
            selectionManagers.remove(selectionManager.getSVGTab());
            selectionManager.dispose();
          }
        }

        SVGTab svgTab=null;
        
        //adds the new mouse motion and key listeners
        for(Iterator it=svgTabs.iterator(); it.hasNext();){
          try{
            svgTab=(SVGTab)it.next();
          }catch (Exception ex){svgTab=null;}

          if(svgTab!=null && ! selectionManagers.containsKey(svgTab)){
            selectionManagers.put(svgTab, new SVGSelectionManager(svgSelection, svgTab));
          }
        }

        svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
        if(svgTab!=null){
          manageSelection();
          //the listener to the selection changes
          selectionListener=new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              manageSelection();
            }
          };
          
          //adds the selection listener
          if(selectionListener!=null){
            svgSelection.addSelectionListener(selectionListener);
          }
        }
      }

      /**
       * updates the selected items and the state of the menu items
       */
      protected void manageSelection(){
        SVGTab currentSvgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
        
        //disables the tool items
        deselectAll.setEnabled(false);
        groupEnter.setEnabled(false);
        
        
        lock.setEnabled(false);
        unlock.setEnabled(false);
        
        //getting the current parent node for the current frame
        Element parent=getCurrentParentElement(currentSvgTab);
        
        //handling the enablement of the groupExit menuItem
        
        groupExit.setEnabled(parent!=null && parent.getNodeName().equals("g"));
        
        LinkedList list=null;
        Node current=null;
        String type="";

        //gets the currently selected nodes list
        Map selectedNodes=getSelectionMap(currentSvgTab);

        if(selectedNodes.size()>0){
          lock.setEnabled(true);
          deselectAll.setEnabled(true);
          
          //if the node is already locked
          if(selectedNodes.size()==1){
            try{
              current=(Node)selectedNodes.keySet().iterator().next();
              type=(String)selectedNodes.get(current);
              if(type.equals("lock")){
                lock.setEnabled(false);
              }else if(current.getNodeName().equals("g")){
                groupEnter.setEnabled(true);
              }
            }catch (Exception ex){}
          }

          for(Iterator it=selectedNodes.keySet().iterator(); it.hasNext();){
            try{current=(Node)it.next();}catch (Exception ex){current=null;}
            if(current!=null){
              type=(String)selectedNodes.get(current);
              if(type!=null && type.equals("lock")){
                unlock.setEnabled(true);
                break;
              }
            }
          }
        }
      }
    };

    //adds the SVGFrame change listener
    studio.getSvgTabManager().addSVGTabChangedListener(svgTabListener);


    //adds the listener on the menu items and tool items
    regularModeTool.addActionListener(new ActionListener(){

        public void actionPerformed(ActionEvent e) {

            getStudio().cancelActions(true);
            
            if(e.getSource() instanceof JToggleButton){
                
                regularModeTool.setSelected(true);
            }
        }
    });
    
    
    selectAll.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        selectAll(getStudio().getSvgTabManager().getCurrentSVGTab(), true);
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
    
    deselectAll.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        deselectAll(getStudio().getSvgTabManager().getCurrentSVGTab(), true, false);
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
  
  
    lock.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        lock(getStudio().getSvgTabManager().getCurrentSVGTab());
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
    
    

    unlock.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        unlock(getStudio().getSvgTabManager().getCurrentSVGTab());
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
    
    
    groupEnter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        enterGroup(getStudio().getSvgTabManager().getCurrentSVGTab());
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
    
    groupExit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
        getStudio().cancelActions(true);
        exitGroup(getStudio().getSvgTabManager().getCurrentSVGTab());
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      }
    });
    
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   */
  private Map getSelectionMap(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}

      if(selectionManager!=null){
        return selectionManager.getCurrentSelectionTypeMap();
      }
    }
    return null;
  }
  
  /**
   * 
   * @description 
   * @param action
   * @param svgTab
   */
  public void addUndoRedoAction(SVGTab svgTab, SVGUndoRedoAction action){
    if(svgTab!=null && action!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.addUndoRedoAction(action);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param listener
   */
  public void addSelectionListener(ActionListener listener){
    if(! selectionListeners.contains(listener)){
      selectionListeners.add(listener);
    }
  }
  
  /**
   * 
   * @description 
   * @param listener
   */
  public void removeSelectionListener(ActionListener listener){
    selectionListeners.remove(listener);
  }
  
  /**
   * 
   * @description 
   * @param pushUndoRedoAction
   * @param svgTab
   */
  public void selectAll(SVGTab svgTab, boolean pushUndoRedoAction){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}

      if(selectionManager!=null){
        selectionManager.selectAll(pushUndoRedoAction);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param executeWhenNoNodesSelected
   * @param pushUndoRedoAction
   * @param svgTab
   */
  public void deselectAll(SVGTab svgTab, boolean pushUndoRedoAction, boolean executeWhenNoNodesSelected){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}

      if(selectionManager!=null){
        selectionManager.deselectAll(pushUndoRedoAction, executeWhenNoNodesSelected);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param forceToRefresh
   */
  public synchronized void selectionChanged(boolean forceToRefresh){
    //gets the currently selected nodes list 
    LinkedList list=getCurrentSelection(getStudio().getSvgTabManager().getCurrentSVGTab());
		if(! lastSelectedNodes.equals(list) || forceToRefresh){
      lastSelectedNodes.clear();
      lastSelectedNodes.addAll(list);
      for(Iterator it=selectionListeners.iterator(); it.hasNext();){
        ((ActionListener)it.next()).actionPerformed(null);
      }
		}
  }
  
  
  /**
   * 
   * @description 
   * @param bounds
   * @param svgTab
   * @param g
   * @param bounds
   */
  public void drawSelectionGhost(SVGTab svgTab, Graphics g, Rectangle2D.Double bounds){
    Graphics2D g2D=(Graphics2D)g;
      if(g2D!=null && bounds!=null){
          //draws the new awt rectangle to be displayed
          g2D.setXORMode(Color.white);
          g2D.setColor(LINE_SELECTION_COLOR);
          g2D.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
          getStudio().getMainStatusBar().setWidth(format.format(bounds.width));
          getStudio().getMainStatusBar().setHeight(format.format(bounds.height));
      }
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   */
  public Element getCurrentParentElement(SVGTab svgTab){
   if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        return selectionManager.getParentElement();
      }
    }
    return null;
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void refreshSelection(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.refreshSelection();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param map
   * @param svgTab
   */
  public void addSelectionSquares(SVGTab svgTab, Hashtable map){
    if(svgTab!=null && map!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.addSelectionSquares(map);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void enterGroup(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.enterGroup();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void exitGroup(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.exitGroup();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param element
   * @param svgTab
   */
  public void handleNodeSelection(SVGTab svgTab, Element element){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.handleNodeSelection(element, false, true, false);
      }
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param svgTab
   */
  public LinkedList getCurrentSelection(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        return selectionManager.getCurrentSelection();
      }
    }
    return null;
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void lock(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
          selectionManager.lock();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param svgTab
   */
  public void unlock(SVGTab svgTab){
    if(svgTab!=null){
      SVGSelectionManager selectionManager=null;
      try{
        selectionManager=(SVGSelectionManager)selectionManagers.get(svgTab);
      }catch (Exception ex){selectionManager=null;}
      if(selectionManager!=null){
        selectionManager.unlock();
      }
    }
  }
  
  /**
   * 
   * @description 
   */
  public void cancelActions(){
    if(getStudio().getSvgTabManager().getCurrentSVGTab()!=null){
      //resets the help information displayed
      getStudio().getMainStatusBar().setInfo("");
      getStudio().getMainStatusBar().setWidth("");
      getStudio().getMainStatusBar().setHeight("");
      getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().setWaitCursorEnabled(true);
    }
  }
  /**
   * 
   * @description 
   * @return 
   */
  public SVGApplet getStudio() {
    return studio;
  }


  /**
   * 
   * @description 
   * @param isSelectionEnabled
   */
  public synchronized void setSelectionEnabled(boolean selectionEnabled) {
    this.selectionEnabled = selectionEnabled;
    regularModeTool.setSelected(selectionEnabled);
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean isSelectionEnabled() {
    return selectionEnabled;
  }
 
 /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
    return idselection;
  } 
  
}