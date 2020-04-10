package bulbul.foff.studio.engine.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * @author Sudheer V. Pujar
 */
public class MainToolBar extends JToolBar  {
  
  private Dimension buttonSize = new Dimension(24,24);
  private Dimension seperatorSize = new Dimension(2,24);
  private Dimension alignPanelSize = new Dimension(26*7,28);
  private Dimension rotatePanelSize = new Dimension(26*3,28);
  private Dimension orderPanelSize = new Dimension(26*4,28);
  private Dimension set2SamePanelSize = new Dimension(26*3,28);
  private Dimension dropButtonSize = new Dimension(12,24);
  private Dimension menuBarSize = new Dimension(0,24);
  private Dimension menuSize = new Dimension(0,0);
  
  private JToolBar fileToolBar=new JToolBar();
  private JToolBar editToolBar=new JToolBar();
  private JToolBar groupToolBar=new JToolBar();
  private JToolBar alignToolBar=new JToolBar();
  private JToolBar set2SameToolBar=new JToolBar();
  private JToolBar rotateToolBar=new JToolBar();
  private JToolBar orderToolBar=new JToolBar();
  private JToolBar zoomToolBar=new JToolBar();
  private JToolBar hvToolBar=new JToolBar();
  
  
  private JMenuBar menuBarAlign = new JMenuBar();
  private JMenu menuAlign = new JMenu();
  
  private JMenuBar menuBarRotate = new JMenuBar();
  private JMenu menuRotate = new JMenu();
  
  private JMenuBar menuBarOrder = new JMenuBar();
  private JMenu menuOrder = new JMenu();
  
  private JMenuBar menuBarSet2Same = new JMenuBar();
  private JMenu menuSet2Same = new JMenu();
  
  private JMenuBar menuBarZoom = new JMenuBar();
  private JMenu menuZoom = new JMenu();
  
  private JMenuItem menuItemAlignLeft = new JMenuItem();
  private JMenuItem menuItemAlignRight = new JMenuItem();
  private JMenuItem menuItemAlignTop = new JMenuItem();
  private JMenuItem menuItemAlignBottom = new JMenuItem();
  private JMenuItem menuItemAlignCenter = new JMenuItem();
  private JMenuItem menuItemAlignCenterXAxis = new JMenuItem();
  private JMenuItem menuItemAlignCenterYAxis = new JMenuItem();

  private JMenuItem menuItemSet2SameWidth = new JMenuItem(); 
  private JMenuItem menuItemSet2SameHeight = new JMenuItem(); 
  private JMenuItem menuItemSet2SameSize = new JMenuItem(); 
  
  private JMenuItem menuItemOrder2Top = new JMenuItem(); 
  private JMenuItem menuItemOrder2Bottom = new JMenuItem(); 
  private JMenuItem menuItemOrder2Up = new JMenuItem(); 
  private JMenuItem menuItemOrder2Down = new JMenuItem(); 
  
  private JMenuItem menuItemRotate90 = new JMenuItem(); 
  private JMenuItem menuItemRotate180 = new JMenuItem(); 
  private JMenuItem menuItemRotate270 = new JMenuItem(); 
  
  private JButton mtbSave = new JButton();
  private JButton mtbSaveAs = new JButton();

  private JButton mtbDelete = new JButton();
  private JButton mtbCopy = new JButton();
  private JButton mtbCut = new JButton();
  private JButton mtbPaste = new JButton();
  
  private JButton mtbUndo = new JButton();
  private JButton mtbRedo = new JButton();
  
  private JButton mtbSelectAll = new JButton();
  private JButton mtbDeselectAll = new JButton();
  
  private JButton mtbLock = new JButton();
  private JButton mtbUnLock = new JButton();
  
  private JButton mtbGroup = new JButton();
  private JButton mtbUnGroup = new JButton();
  
  private JButton mtbGroupEnter = new JButton();
  private JButton mtbGroupExit = new JButton();
  
  private JButton mtbAlign = new JButton();
  private JButton mtbAlignDrop = new JButton();
  
  private JButton mtbSet2Same = new JButton(); 
  private JButton mtbSet2SameDrop = new JButton(); 
  
  private JButton mtbSpaceHorizontal = new JButton(); 
  private JButton mtbSpaceVertical = new JButton(); 
  
  private JButton mtbCenterHorizontal = new JButton(); 
  private JButton mtbCenterVertical = new JButton(); 
  
  private JButton mtbOrder = new JButton(); 
  private JButton mtbOrderDrop = new JButton(); 
  
  private JButton mtbRotate=new JButton(); 
  private JButton mtbRotateDrop=new JButton(); 
  
  private JButton mtbFlipHorizontal = new JButton();
  private JButton mtbFlipVertical = new JButton();
  
  private JToggleButton mtbGrid = new JToggleButton();
  private JToggleButton mtbRulers = new JToggleButton();
  
  private JButton mtbZoom = new JButton();
  private JButton mtbZoomDrop = new JButton();
  private JButton mtbZoomIn = new JButton();
  private JButton mtbZoomOut = new JButton();
  private JButton mtbZoom11 = new JButton();

  private Studio studio;
  private FlowLayout layout4This = new FlowLayout();
  private FlowLayout layout4AllToolBars = new FlowLayout();
  
  private Border mouseExitedBorder=BorderFactory.createEmptyBorder(0, 0, 0, 0);
  private Border mouseEnteredBorder=new LineBorder(new Color(247,161,90),1);
  private Border toolBarBorder=new LineBorder(new Color(247,161,90),1);
  
  private MouseAdapter mtbMouseListener;
  private MouseAdapter mtbMouseListener4Align;
  private MouseAdapter mtbMouseListener4Rotate;
  private MouseAdapter mtbMouseListener4Order;
  private MouseAdapter mtbMouseListener4Set2Same;
  private MouseAdapter mtbMouseListener4Zoom;
  
  private Icon dropButtonIcon=null; 
  
  /**
   * 
   * @param studio
   */
  public MainToolBar(Studio studio) {
    try {
      this.studio=studio;
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    setFocusable(false);
    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    setBackground(new Color(255,255,255,255));
    
    dropButtonIcon=getIcon("drop.png");
    
    layout4This.setAlignment(0);
    layout4This.setHgap(1);
    layout4This.setVgap(1);
    
    setLayout(layout4This);
    
    layout4AllToolBars.setAlignment(0);
    layout4AllToolBars.setHgap(1);
    layout4AllToolBars.setVgap(1);
    
    
    fileToolBar.setBorder(toolBarBorder);
    fileToolBar.setLayout(layout4AllToolBars);
    fileToolBar.setFocusable(false);
    
    editToolBar.setBorder(toolBarBorder);
    editToolBar.setLayout(layout4AllToolBars);
    editToolBar.setFocusable(false);
    
    groupToolBar.setBorder(toolBarBorder);
    groupToolBar.setLayout(layout4AllToolBars);
    groupToolBar.setFocusable(false);
    
    alignToolBar.setBorder(toolBarBorder);
    alignToolBar.setLayout(layout4AllToolBars);
    alignToolBar.setFocusable(false);
    
    set2SameToolBar.setBorder(toolBarBorder);
    set2SameToolBar.setLayout(layout4AllToolBars);
    set2SameToolBar.setFocusable(false);
    
    rotateToolBar.setBorder(toolBarBorder);
    rotateToolBar.setLayout(layout4AllToolBars);
    rotateToolBar.setFocusable(false);
    
    orderToolBar.setBorder(toolBarBorder);
    orderToolBar.setLayout(layout4AllToolBars);
    orderToolBar.setFocusable(false);
    
    hvToolBar.setBorder(toolBarBorder);
    hvToolBar.setLayout(layout4AllToolBars);
    hvToolBar.setFocusable(false);
    
    zoomToolBar.setBorder(toolBarBorder);
    zoomToolBar.setLayout(layout4AllToolBars);
    zoomToolBar.setFocusable(false);
    
    mtbMouseListener=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        mtbMouseEntered(e);
      }

      public void mouseExited(MouseEvent e) {
        mtbMouseExited(e);
      }        
    };
    
    mtbMouseListener4Align=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbAlign.setBorder(mouseEnteredBorder);
          mtbAlignDrop.setBorder(mouseEnteredBorder);
        }
      }

      public void mouseExited(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbAlign.setBorder(mouseExitedBorder);
          mtbAlignDrop.setBorder(mouseExitedBorder);
        }
      }        
    };
    
    mtbMouseListener4Rotate=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbRotate.setBorder(mouseEnteredBorder);
          mtbRotateDrop.setBorder(mouseEnteredBorder);
        }
      }

      public void mouseExited(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbRotate.setBorder(mouseExitedBorder);
          mtbRotateDrop.setBorder(mouseExitedBorder);
        }
      }        
    };
    
    mtbMouseListener4Order=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbOrder.setBorder(mouseEnteredBorder);
          mtbOrderDrop.setBorder(mouseEnteredBorder);
        }
      }

      public void mouseExited(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbOrder.setBorder(mouseExitedBorder);
          mtbOrderDrop.setBorder(mouseExitedBorder);
        }
      }        
    };
    
    mtbMouseListener4Set2Same=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbSet2Same.setBorder(mouseEnteredBorder);
          mtbSet2SameDrop.setBorder(mouseEnteredBorder);
        }
      }

      public void mouseExited(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbSet2Same.setBorder(mouseExitedBorder);
          mtbSet2SameDrop.setBorder(mouseExitedBorder);
        }
      }        
    };
    
    mtbMouseListener4Zoom=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbZoom11.setBorder(mouseEnteredBorder);
          mtbZoomDrop.setBorder(mouseEnteredBorder);
        }
      }

      public void mouseExited(MouseEvent e) {
        JComponent srcButton = (JComponent)e.getSource();
        if(srcButton.isEnabled()){
          mtbZoom11.setBorder(mouseExitedBorder);
          mtbZoomDrop.setBorder(mouseExitedBorder);
        }
      }        
    };
    
    mtbSave.setSize(buttonSize);
    mtbSave.setPreferredSize(buttonSize);
    mtbSave.setMaximumSize(buttonSize);
    mtbSave.setMinimumSize(buttonSize);
    mtbSave.setToolTipText("Design Save");
    mtbSave.setEnabled(false);
    mtbSave.setIcon(getIcon("save.png"));
    mtbSave.setBorder(mouseExitedBorder);
    mtbSave.addMouseListener(mtbMouseListener);
    
    mtbSaveAs.setSize(buttonSize);
    mtbSaveAs.setPreferredSize(buttonSize);
    mtbSaveAs.setMaximumSize(buttonSize);
    mtbSaveAs.setMinimumSize(buttonSize);
    mtbSaveAs.setToolTipText("Design Save As");
    mtbSaveAs.setEnabled(false);
    mtbSaveAs.setIcon(getIcon("saveAs.png"));
    mtbSaveAs.setBorder(mouseExitedBorder);
    mtbSaveAs.addMouseListener(mtbMouseListener);
    
    
    mtbDelete.setSize(buttonSize);
    mtbDelete.setPreferredSize(buttonSize);
    mtbDelete.setMaximumSize(buttonSize);
    mtbDelete.setMinimumSize(buttonSize);
    mtbDelete.setToolTipText("Delete - DELETE");
    mtbDelete.setEnabled(false);
    mtbDelete.setIcon(getIcon("delete.png"));
    mtbDelete.setBorder(mouseExitedBorder);
    mtbDelete.addMouseListener(mtbMouseListener);
    
    mtbCopy.setSize(buttonSize);
    mtbCopy.setPreferredSize(buttonSize);
    mtbCopy.setMaximumSize(buttonSize);
    mtbCopy.setMinimumSize(buttonSize);
    mtbCopy.setToolTipText("Copy - CTRL-C");
    mtbCopy.setEnabled(false);
    mtbCopy.setIcon(getIcon("copy.png"));
    mtbCopy.setBorder(mouseExitedBorder);
    mtbCopy.addMouseListener(mtbMouseListener);
    
    mtbCut.setSize(buttonSize);
    mtbCut.setPreferredSize(buttonSize);
    mtbCut.setMaximumSize(buttonSize);
    mtbCut.setMinimumSize(buttonSize);
    mtbCut.setToolTipText("Cut - CTRL-X");
    mtbCut.setEnabled(false);
    mtbCut.setIcon(getIcon("cut.png"));
    mtbCut.setBorder(mouseExitedBorder);
    mtbCut.addMouseListener(mtbMouseListener);
    
    mtbPaste.setSize(buttonSize);
    mtbPaste.setPreferredSize(buttonSize);
    mtbPaste.setMaximumSize(buttonSize);
    mtbPaste.setMinimumSize(buttonSize);
    mtbPaste.setToolTipText("Paste - CTRL-V");
    mtbPaste.setEnabled(false);
    mtbPaste.setIcon(getIcon("paste.png"));
    mtbPaste.setBorder(mouseExitedBorder);
    mtbPaste.addMouseListener(mtbMouseListener);
    
    mtbUndo.setSize(buttonSize);
    mtbUndo.setPreferredSize(buttonSize);
    mtbUndo.setMaximumSize(buttonSize);
    mtbUndo.setMinimumSize(buttonSize);
    mtbUndo.setToolTipText("Undo - CTRL-Z");
    mtbUndo.setEnabled(false);
    mtbUndo.setIcon(getIcon("undo.png"));
    mtbUndo.setBorder(mouseExitedBorder);
    mtbUndo.addMouseListener(mtbMouseListener);
    
    mtbRedo.setSize(buttonSize);
    mtbRedo.setPreferredSize(buttonSize);
    mtbRedo.setMaximumSize(buttonSize);
    mtbRedo.setMinimumSize(buttonSize);
    mtbRedo.setToolTipText("Redo - CTRL-Y");
    mtbRedo.setEnabled(false);
    mtbRedo.setIcon(getIcon("redo.png"));
    mtbRedo.setBorder(mouseExitedBorder);
    mtbRedo.addMouseListener(mtbMouseListener);
    
    mtbLock.setSize(buttonSize);
    mtbLock.setPreferredSize(buttonSize);
    mtbLock.setMaximumSize(buttonSize);
    mtbLock.setMinimumSize(buttonSize);
    mtbLock.setToolTipText("Lock");
    mtbLock.setEnabled(false);
    mtbLock.setIcon(getIcon("lock.png"));
    mtbLock.setBorder(mouseExitedBorder);
    mtbLock.addMouseListener(mtbMouseListener);
    
    mtbUnLock.setSize(buttonSize);
    mtbUnLock.setPreferredSize(buttonSize);
    mtbUnLock.setMaximumSize(buttonSize);
    mtbUnLock.setMinimumSize(buttonSize);
    mtbUnLock.setToolTipText("UnLock");
    mtbUnLock.setEnabled(false);
    mtbUnLock.setIcon(getIcon("unlock.png"));
    mtbUnLock.setBorder(mouseExitedBorder);
    mtbUnLock.addMouseListener(mtbMouseListener);
    
    mtbSelectAll.setSize(buttonSize);
    mtbSelectAll.setPreferredSize(buttonSize);
    mtbSelectAll.setMaximumSize(buttonSize);
    mtbSelectAll.setMinimumSize(buttonSize);
    mtbSelectAll.setToolTipText("Select All - CTRL-A");
    mtbSelectAll.setEnabled(false);
    mtbSelectAll.setIcon(getIcon("selectAll.png"));
    mtbSelectAll.setBorder(mouseExitedBorder);
    mtbSelectAll.addMouseListener(mtbMouseListener);
    
    mtbDeselectAll.setSize(buttonSize);
    mtbDeselectAll.setPreferredSize(buttonSize);
    mtbDeselectAll.setMaximumSize(buttonSize);
    mtbDeselectAll.setMinimumSize(buttonSize);
    mtbDeselectAll.setToolTipText("Deselect All - CTRL-D");
    mtbDeselectAll.setEnabled(false);
    mtbDeselectAll.setIcon(getIcon("deselectAll.png"));
    mtbDeselectAll.setBorder(mouseExitedBorder);
    mtbDeselectAll.addMouseListener(mtbMouseListener);
    
    mtbGroup.setSize(buttonSize);
    mtbGroup.setPreferredSize(buttonSize);
    mtbGroup.setMaximumSize(buttonSize);
    mtbGroup.setMinimumSize(buttonSize);
    mtbGroup.setToolTipText("Group - CTRL-G");
    mtbGroup.setEnabled(false);
    mtbGroup.setIcon(getIcon("group.png"));
    mtbGroup.setBorder(mouseExitedBorder);
    mtbGroup.addMouseListener(mtbMouseListener);
    
    mtbUnGroup.setSize(buttonSize);
    mtbUnGroup.setPreferredSize(buttonSize);
    mtbUnGroup.setMaximumSize(buttonSize);
    mtbUnGroup.setMinimumSize(buttonSize);
    mtbUnGroup.setToolTipText("UnGroup - CTRL-U ");
    mtbUnGroup.setEnabled(false);
    mtbUnGroup.setIcon(getIcon("ungroup.png"));
    mtbUnGroup.setBorder(mouseExitedBorder);
    mtbUnGroup.addMouseListener(mtbMouseListener);
    
    mtbGroupEnter.setSize(buttonSize);
    mtbGroupEnter.setPreferredSize(buttonSize);
    mtbGroupEnter.setMaximumSize(buttonSize);
    mtbGroupEnter.setMinimumSize(buttonSize);
    mtbGroupEnter.setToolTipText("Group Enter");
    mtbGroupEnter.setEnabled(false);
    mtbGroupEnter.setIcon(getIcon("groupEnter.png"));
    mtbGroupEnter.setBorder(mouseExitedBorder);
    mtbGroupEnter.addMouseListener(mtbMouseListener);
    
    mtbGroupExit.setSize(buttonSize);
    mtbGroupExit.setPreferredSize(buttonSize);
    mtbGroupExit.setMaximumSize(buttonSize);
    mtbGroupExit.setMinimumSize(buttonSize);
    mtbGroupExit.setToolTipText("Group Exit");
    mtbGroupExit.setEnabled(false);
    mtbGroupExit.setIcon(getIcon("groupExit.png"));
    mtbGroupExit.setBorder(mouseExitedBorder);
    mtbGroupExit.addMouseListener(mtbMouseListener);
    
    
    menuBarAlign.setPreferredSize(menuBarSize);
    menuBarAlign.setBorder(BorderFactory.createEmptyBorder());

    menuAlign.setPreferredSize(menuSize);
    
    mtbAlign.setSize(buttonSize);
    mtbAlign.setPreferredSize(buttonSize);
    mtbAlign.setMaximumSize(buttonSize);
    mtbAlign.setMinimumSize(buttonSize);
    mtbAlign.setToolTipText("Align Left - SHIFT-LEFT");
    mtbAlign.setEnabled(false);
    mtbAlign.setIcon(getIcon("alignLeft.png"));
    mtbAlign.setBorder(mouseExitedBorder);
    mtbAlign.addMouseListener(mtbMouseListener4Align);
    
    mtbAlignDrop.setSize(dropButtonSize);
    mtbAlignDrop.setPreferredSize(dropButtonSize);
    mtbAlignDrop.setMaximumSize(dropButtonSize);
    mtbAlignDrop.setMinimumSize(dropButtonSize);
    mtbAlignDrop.setToolTipText("Drop To See Align Methods");
    mtbAlignDrop.setEnabled(false);
    mtbAlignDrop.setIcon(dropButtonIcon);
    mtbAlignDrop.setBorder(mouseExitedBorder);
    mtbAlignDrop.addMouseListener(mtbMouseListener4Align);
    mtbAlignDrop.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        menuAlign.doClick();
      }
    });
    
    menuItemAlignLeft.setEnabled(false);
    menuItemAlignLeft.setIcon(getIcon("alignLeft.png"));
    menuItemAlignLeft.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignRight.setEnabled(false);
    menuItemAlignRight.setIcon(getIcon("alignRight.png"));
    menuItemAlignRight.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignTop.setEnabled(false);
    menuItemAlignTop.setIcon(getIcon("alignTop.png"));
    menuItemAlignTop.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignBottom.setEnabled(false);
    menuItemAlignBottom.setIcon(getIcon("alignBottom.png"));
    menuItemAlignBottom.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignCenter.setEnabled(false);
    menuItemAlignCenter.setIcon(getIcon("alignCenter.png"));
    menuItemAlignCenter.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignCenterXAxis.setEnabled(false);
    menuItemAlignCenterXAxis.setIcon(getIcon("alignHorizontalCenter.png"));
    menuItemAlignCenterXAxis.addActionListener(new ToolBarActionListener(mtbAlign));
    
    menuItemAlignCenterYAxis.setEnabled(false);
    menuItemAlignCenterYAxis.setIcon(getIcon("alignVerticalCenter.png"));
    menuItemAlignCenterYAxis.addActionListener(new ToolBarActionListener(mtbAlign));
    
    
    menuBarSet2Same.setPreferredSize(menuBarSize);
    menuBarSet2Same.setBorder(BorderFactory.createEmptyBorder());

    menuSet2Same.setPreferredSize(menuSize);
    
    mtbSet2Same.setSize(buttonSize);
    mtbSet2Same.setPreferredSize(buttonSize);
    mtbSet2Same.setMaximumSize(buttonSize);
    mtbSet2Same.setMinimumSize(buttonSize);
    mtbSet2Same.setToolTipText("Set To Same Width");
    mtbSet2Same.setEnabled(false);
    mtbSet2Same.setIcon(getIcon("sameWidth.png"));
    mtbSet2Same.setBorder(mouseExitedBorder);
    mtbSet2Same.addMouseListener(mtbMouseListener4Set2Same);
    
    mtbSet2SameDrop.setSize(dropButtonSize);
    mtbSet2SameDrop.setPreferredSize(dropButtonSize);
    mtbSet2SameDrop.setMaximumSize(dropButtonSize);
    mtbSet2SameDrop.setMinimumSize(dropButtonSize);
    mtbSet2SameDrop.setToolTipText("Drop To See Set To Same Methods");
    mtbSet2SameDrop.setEnabled(false);
    mtbSet2SameDrop.setIcon(dropButtonIcon);
    mtbSet2SameDrop.setBorder(mouseExitedBorder);
    mtbSet2SameDrop.addMouseListener(mtbMouseListener4Set2Same);
    mtbSet2SameDrop.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        menuSet2Same.doClick();        
      }
    });
    
    
    menuItemSet2SameWidth.setEnabled(false);
    menuItemSet2SameWidth.setIcon(getIcon("sameWidth.png"));
    menuItemSet2SameWidth.addActionListener(new ToolBarActionListener(mtbSet2Same));
    
    
    menuItemSet2SameHeight.setEnabled(false);
    menuItemSet2SameHeight.setIcon(getIcon("sameHeight.png"));
    menuItemSet2SameHeight.addActionListener(new ToolBarActionListener(mtbSet2Same));
    
    menuItemSet2SameSize.setEnabled(false);
    menuItemSet2SameSize.setIcon(getIcon("sameSize.png"));
    menuItemSet2SameSize.addActionListener(new ToolBarActionListener(mtbSet2Same));
    
    mtbSpaceHorizontal.setSize(buttonSize);
    mtbSpaceHorizontal.setPreferredSize(buttonSize);
    mtbSpaceHorizontal.setMaximumSize(buttonSize);
    mtbSpaceHorizontal.setMinimumSize(buttonSize);
    mtbSpaceHorizontal.setToolTipText("Space - Horizontal");
    mtbSpaceHorizontal.setEnabled(false);
    mtbSpaceHorizontal.setIcon(getIcon("horizontalSpacing.png"));
    mtbSpaceHorizontal.setBorder(mouseExitedBorder);
    mtbSpaceHorizontal.addMouseListener(mtbMouseListener);
    
    mtbSpaceVertical.setSize(buttonSize);
    mtbSpaceVertical.setPreferredSize(buttonSize);
    mtbSpaceVertical.setMaximumSize(buttonSize);
    mtbSpaceVertical.setMinimumSize(buttonSize);
    mtbSpaceVertical.setToolTipText("Space - Vertical");
    mtbSpaceVertical.setEnabled(false);
    mtbSpaceVertical.setIcon(getIcon("verticalSpacing.png"));
    mtbSpaceVertical.setBorder(mouseExitedBorder);
    mtbSpaceVertical.addMouseListener(mtbMouseListener);
    
    mtbCenterHorizontal.setSize(buttonSize);
    mtbCenterHorizontal.setPreferredSize(buttonSize);
    mtbCenterHorizontal.setMaximumSize(buttonSize);
    mtbCenterHorizontal.setMinimumSize(buttonSize);
    mtbCenterHorizontal.setToolTipText("Center - Horizontal");
    mtbCenterHorizontal.setEnabled(false);
    mtbCenterHorizontal.setIcon(getIcon("horizontalCenter.png"));
    mtbCenterHorizontal.setBorder(mouseExitedBorder);
    mtbCenterHorizontal.addMouseListener(mtbMouseListener);
    
    mtbCenterVertical.setSize(buttonSize);
    mtbCenterVertical.setPreferredSize(buttonSize);
    mtbCenterVertical.setMaximumSize(buttonSize);
    mtbCenterVertical.setMinimumSize(buttonSize);
    mtbCenterVertical.setToolTipText("Center - Vertical");
    mtbCenterVertical.setEnabled(false);
    mtbCenterVertical.setIcon(getIcon("verticalCenter.png"));
    mtbCenterVertical.setBorder(mouseExitedBorder);
    mtbCenterVertical.addMouseListener(mtbMouseListener);
    
    menuBarOrder.setPreferredSize(menuBarSize);
    menuBarOrder.setBorder(BorderFactory.createEmptyBorder());

    menuOrder.setPreferredSize(menuSize);
    
    mtbOrder.setSize(buttonSize);
    mtbOrder.setPreferredSize(buttonSize);
    mtbOrder.setMaximumSize(buttonSize);
    mtbOrder.setMinimumSize(buttonSize);
    mtbOrder.setToolTipText("Send to Top - CTRL+SHIFT-UP");
    mtbOrder.setEnabled(false);
    mtbOrder.setIcon(getIcon("orderTop.png"));
    mtbOrder.setBorder(mouseExitedBorder);
    mtbOrder.addMouseListener(mtbMouseListener4Order);
    
    mtbOrderDrop.setSize(dropButtonSize);
    mtbOrderDrop.setPreferredSize(dropButtonSize);
    mtbOrderDrop.setMaximumSize(dropButtonSize);
    mtbOrderDrop.setMinimumSize(dropButtonSize);
    mtbOrderDrop.setToolTipText("Drop To See Order Methods");
    mtbOrderDrop.setEnabled(false);
    mtbOrderDrop.setIcon(dropButtonIcon);
    mtbOrderDrop.setBorder(mouseExitedBorder);
    mtbOrderDrop.addMouseListener(mtbMouseListener4Order);
    mtbOrderDrop.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        menuOrder.doClick();
      }
    });
    
    menuItemOrder2Top.setEnabled(false);
    menuItemOrder2Top.setIcon(getIcon("orderTop.png"));
    menuItemOrder2Top.addActionListener(new ToolBarActionListener(mtbOrder));
    
    menuItemOrder2Bottom.setEnabled(false);
    menuItemOrder2Bottom.setIcon(getIcon("orderBottom.png"));
    menuItemOrder2Bottom.addActionListener(new ToolBarActionListener(mtbOrder));
    
    menuItemOrder2Up.setEnabled(false);
    menuItemOrder2Up.setIcon(getIcon("orderUp.png"));
    menuItemOrder2Up.addActionListener(new ToolBarActionListener(mtbOrder));
    
    menuItemOrder2Down.setEnabled(false);    
    menuItemOrder2Down.setIcon(getIcon("orderDown.png"));
    menuItemOrder2Down.addActionListener(new ToolBarActionListener(mtbOrder));
    
    menuBarRotate.setPreferredSize(menuBarSize);
    menuBarRotate.setBorder(BorderFactory.createEmptyBorder());

    menuRotate.setPreferredSize(menuSize);
    
    mtbRotate.setSize(buttonSize);
    mtbRotate.setPreferredSize(buttonSize);
    mtbRotate.setMaximumSize(buttonSize);
    mtbRotate.setMinimumSize(buttonSize);
    mtbRotate.setToolTipText("Rotate 90°");
    mtbRotate.setEnabled(false);
    mtbRotate.setIcon(getIcon("rotate90.png"));
    mtbRotate.setBorder(mouseExitedBorder);
    mtbRotate.addMouseListener(mtbMouseListener4Rotate);
    
    //mtbRotateDrop.setText("v");
    mtbRotateDrop.setSize(dropButtonSize);
    mtbRotateDrop.setPreferredSize(dropButtonSize);
    mtbRotateDrop.setMaximumSize(dropButtonSize);
    mtbRotateDrop.setMinimumSize(dropButtonSize);
    mtbRotateDrop.setToolTipText("Drop To See Rotate Methods");
    mtbRotateDrop.setEnabled(false);
    mtbRotateDrop.setIcon(dropButtonIcon);
    mtbRotateDrop.setBorder(mouseExitedBorder);
    mtbRotateDrop.addMouseListener(mtbMouseListener4Rotate);
    mtbRotateDrop.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        menuRotate.doClick();
      }
    });
    
    menuItemRotate90.setEnabled(false);    
    menuItemRotate90.setIcon(getIcon("rotate90.png"));
    menuItemRotate90.addActionListener(new ToolBarActionListener(mtbRotate));
    
    menuItemRotate180.setEnabled(false);    
    menuItemRotate180.setIcon(getIcon("rotate180.png"));
    menuItemRotate180.addActionListener(new ToolBarActionListener(mtbRotate));
    
    menuItemRotate270.setEnabled(false);    
    menuItemRotate270.setIcon(getIcon("rotate270.png"));
    menuItemRotate270.addActionListener(new ToolBarActionListener(mtbRotate));
    
    mtbFlipHorizontal.setSize(buttonSize);
    mtbFlipHorizontal.setPreferredSize(buttonSize);
    mtbFlipHorizontal.setMaximumSize(buttonSize);
    mtbFlipHorizontal.setMinimumSize(buttonSize);
    mtbFlipHorizontal.setToolTipText("Flip - Horizontal");
    mtbFlipHorizontal.setEnabled(false);    
    mtbFlipHorizontal.setIcon(getIcon("horizontalFlip.png"));
    mtbFlipHorizontal.setBorder(mouseExitedBorder);
    mtbFlipHorizontal.addMouseListener(mtbMouseListener);
    
    mtbFlipVertical.setSize(buttonSize);
    mtbFlipVertical.setPreferredSize(buttonSize);
    mtbFlipVertical.setMaximumSize(buttonSize);
    mtbFlipVertical.setMinimumSize(buttonSize);
    mtbFlipVertical.setToolTipText("Flip - Vertical");
    mtbFlipVertical.setEnabled(false);    
    mtbFlipVertical.setIcon(getIcon("verticalFlip.png"));
    mtbFlipVertical.setBorder(mouseExitedBorder);
    mtbFlipVertical.addMouseListener(mtbMouseListener);
    
    mtbGrid.setSize(buttonSize);
    mtbGrid.setPreferredSize(buttonSize);
    mtbGrid.setMaximumSize(buttonSize);
    mtbGrid.setMinimumSize(buttonSize);
    mtbGrid.setToolTipText("Grids On/Off");
    mtbGrid.setEnabled(false);
    mtbGrid.setIcon(getIcon("grid.png"));
    mtbGrid.setBorder(mouseExitedBorder);
    mtbGrid.addMouseListener(mtbMouseListener);
    mtbGrid.addMouseListener(mtbMouseListener);
    
    mtbRulers.setSize(buttonSize);
    mtbRulers.setPreferredSize(buttonSize);
    mtbRulers.setMaximumSize(buttonSize);
    mtbRulers.setMinimumSize(buttonSize);
    mtbRulers.setToolTipText("Rulers On/Off");
    mtbRulers.setSelected(true);
    mtbRulers.setEnabled(false);
    mtbRulers.setIcon(getIcon("rulers.png"));
    mtbRulers.setBorder(mouseExitedBorder);
    mtbRulers.addMouseListener(mtbMouseListener);
    
    menuBarZoom.setPreferredSize(menuBarSize);
    menuBarZoom.setBorder(BorderFactory.createEmptyBorder());

    menuZoom.setPreferredSize(menuSize);
    
    mtbZoom11.setSize(buttonSize);
    mtbZoom11.setPreferredSize(buttonSize);
    mtbZoom11.setMaximumSize(buttonSize);
    mtbZoom11.setMinimumSize(buttonSize);
    mtbZoom11.setToolTipText("100% Or Original Size");
    mtbZoom11.setEnabled(false);
    mtbZoom11.setIcon(getIcon("zoom11.png"));
    mtbZoom11.setBorder(mouseExitedBorder);
    mtbZoom11.addMouseListener(mtbMouseListener4Zoom);
   
    mtbZoom.setIcon(getIcon("zoom.png"));
    
    mtbZoomDrop.setSize(dropButtonSize);
    mtbZoomDrop.setPreferredSize(dropButtonSize);
    mtbZoomDrop.setMaximumSize(dropButtonSize);
    mtbZoomDrop.setMinimumSize(dropButtonSize);
    mtbZoomDrop.setToolTipText("Drop To See Zoom Methods");
    mtbZoomDrop.setEnabled(false);
    mtbZoomDrop.setIcon(dropButtonIcon);
    mtbZoomDrop.setBorder(mouseExitedBorder);
    mtbZoomDrop.addMouseListener(mtbMouseListener4Zoom);
    mtbZoomDrop.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        menuZoom.doClick();
      }
    });
    
    mtbZoomIn.setSize(buttonSize);
    mtbZoomIn.setPreferredSize(buttonSize);
    mtbZoomIn.setMaximumSize(buttonSize);
    mtbZoomIn.setMinimumSize(buttonSize);
    mtbZoomIn.setToolTipText("ZoomIn   +");
    mtbZoomIn.setEnabled(false);
    mtbZoomIn.setIcon(getIcon("zoom+.png"));
    mtbZoomIn.setBorder(mouseExitedBorder);
    mtbZoomIn.addMouseListener(mtbMouseListener);
    
    mtbZoomOut.setSize(buttonSize);
    mtbZoomOut.setPreferredSize(buttonSize);
    mtbZoomOut.setMaximumSize(buttonSize);
    mtbZoomOut.setMinimumSize(buttonSize);
    mtbZoomOut.setToolTipText("ZoomOut   -");
    mtbZoomOut.setEnabled(false);
    mtbZoomOut.setIcon(getIcon("zoom-.png"));
    mtbZoomOut.setBorder(mouseExitedBorder);
    mtbZoomOut.addMouseListener(mtbMouseListener);
    
    
    menuAlign.add(menuItemAlignLeft);
    menuAlign.add(menuItemAlignRight);
    menuAlign.add(menuItemAlignTop);
    menuAlign.add(menuItemAlignBottom);
    menuAlign.add(menuItemAlignCenter);
    menuAlign.add(menuItemAlignCenterXAxis);
    menuAlign.add(menuItemAlignCenterYAxis);
    
    menuBarAlign.add(menuAlign);
    
    menuSet2Same.add(menuItemSet2SameWidth);
    menuSet2Same.add(menuItemSet2SameHeight);
    menuSet2Same.add(menuItemSet2SameSize);
    
    menuBarSet2Same.add(menuSet2Same);
    
    menuOrder.add(menuItemOrder2Top);
    menuOrder.add(menuItemOrder2Bottom);
    menuOrder.add(menuItemOrder2Up);
    menuOrder.add(menuItemOrder2Down);
    
    menuBarOrder.add(menuOrder);
    
    menuRotate.add(menuItemRotate90);
    menuRotate.add(menuItemRotate180);
    menuRotate.add(menuItemRotate270);
    
    menuBarRotate.add(menuRotate);
    
    fileToolBar.add(mtbSave);
    fileToolBar.add(mtbSaveAs);
    fileToolBar.add(mtbGrid, null);
    fileToolBar.add(mtbRulers, null);
    
    editToolBar.add(mtbDelete, null);
    editToolBar.add(mtbCopy, null);
    editToolBar.add(mtbCut, null);
    editToolBar.add(mtbPaste, null);
    editToolBar.add(mtbUndo, null);
    editToolBar.add(mtbRedo, null);
    editToolBar.add(mtbLock, null);
    editToolBar.add(mtbUnLock, null);
    editToolBar.add(mtbSelectAll, null);
    editToolBar.add(mtbDeselectAll, null);

    groupToolBar.add(mtbGroup, null);
    groupToolBar.add(mtbUnGroup, null);
    groupToolBar.add(mtbGroupEnter, null);
    groupToolBar.add(mtbGroupExit, null);
    
    
    set2SameToolBar.add(menuBarSet2Same);
    set2SameToolBar.add(mtbSet2Same);
    set2SameToolBar.add(mtbSet2SameDrop);
    
    alignToolBar.add(menuBarAlign);
    alignToolBar.add(mtbAlign);
    alignToolBar.add(mtbAlignDrop);
    
    orderToolBar.add(menuBarOrder);
    orderToolBar.add(mtbOrder);
    orderToolBar.add(mtbOrderDrop);
    
    rotateToolBar.add(menuBarRotate);
    rotateToolBar.add(mtbRotate);
    rotateToolBar.add(mtbRotateDrop);
    
    hvToolBar.add(mtbSpaceHorizontal);
    hvToolBar.add(mtbSpaceVertical);
    hvToolBar.add(mtbCenterHorizontal);
    hvToolBar.add(mtbCenterVertical);
    hvToolBar.add(mtbFlipHorizontal);
    hvToolBar.add(mtbFlipVertical);
    
    menuBarZoom.add(menuZoom);
    zoomToolBar.add(mtbZoomIn);
    zoomToolBar.add(mtbZoomOut);
    zoomToolBar.add(menuBarZoom);
    zoomToolBar.add(mtbZoom11);
    zoomToolBar.add(mtbZoomDrop);
    
    this.add(fileToolBar);
    this.add(editToolBar);
    this.add(zoomToolBar);
    this.add(hvToolBar);
    this.add(alignToolBar);
    this.add(set2SameToolBar);
    this.add(rotateToolBar);
    this.add(orderToolBar);
    this.add(groupToolBar);
    
    //Temporary Code To show memory Monitor Starts
    /*
    JButton memoryMonitor = new JButton("M");
    memoryMonitor.setSize(buttonSize);
    memoryMonitor.setPreferredSize(buttonSize);
    memoryMonitor.setMaximumSize(buttonSize);
    memoryMonitor.setMinimumSize(buttonSize);
    memoryMonitor.setToolTipText("Memory Monitor");
    memoryMonitor.setBorder(mouseEnteredBorder);
    memoryMonitor.addActionListener(new ActionListener(){
        private org.apache.batik.util.gui.MemoryMonitor memoryMonitorDialog=new org.apache.batik.util.gui.MemoryMonitor();
				public void actionPerformed(ActionEvent arg0) {
					
					if(! memoryMonitorDialog.isVisible()){
						//sets the location of the dialog box
						memoryMonitorDialog.setLocation(0,0);
						memoryMonitorDialog.setVisible(true);
					}
				}
			});
      this.add(memoryMonitor);
      */
    //Temporary Code To show memory Monitor Ends
  }

 /**
   * 
   * @description 
   * @param e
   */
  private void mtbMouseEntered(MouseEvent e) {
    JComponent srcButton = (JComponent)e.getSource();
    if(srcButton.isEnabled()){
      srcButton.setBorder(mouseEnteredBorder);
    }
  }

  /**
   * 
   * @description 
   * @param e
   */
  private void mtbMouseExited(MouseEvent e) {
    JComponent srcButton = (JComponent)e.getSource();
    if(srcButton.isEnabled()){
      srcButton.setBorder(mouseExitedBorder);
    }
  }
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  private ImageIcon getIcon(String name){
    return getStudio().getSvgResource().getIcon(name);
  }
  
  
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbDelete() {
    return mtbDelete;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbCopy() {
    return mtbCopy;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbCut() {
    return mtbCut;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbPaste() {
    return mtbPaste;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getMtbRulers() {
    return mtbRulers;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JToggleButton getMtbGrid() {
    return mtbGrid;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public JButton getMtbLock(){
    return mtbLock;
  }


  /**
   * 
   * @return 
   * @description 
   */
  public JButton getMtbUnLock(){
    return mtbUnLock;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbUndo() {
    return mtbUndo;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRedo() {
    return mtbRedo;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSelectAll() {
    return mtbSelectAll;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbDeselectAll() {
    return mtbDeselectAll;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbGroup() {
    return mtbGroup;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbUnGroup() {
    return mtbUnGroup;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbGroupEnter() {
    return mtbGroupEnter;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbGroupExit() {
    return mtbGroupExit;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JMenu getMenuZoom() {
    return menuZoom;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbZoom() {
    return mtbZoom;
  }
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbZoom11() {
    return mtbZoom11;
  }
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbZoomDrop() {
    return mtbZoomDrop;
  }
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbZoomIn() {
    return mtbZoomIn;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbZoomOut() {
    return mtbZoomOut;
  }

 

  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlign() {
    return mtbAlign;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignDrop() {
    return mtbAlignDrop;
  }
  
  
/**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSet2Same() {
    return mtbSet2Same;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSet2SameDrop() {
    return mtbSet2SameDrop;
  }
  
 
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSpaceHorizontal() {
    return mtbSpaceHorizontal;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSpaceVertical() {
    return mtbSpaceVertical;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbCenterHorizontal() {
    return mtbCenterHorizontal;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbCenterVertical() {
    return mtbCenterVertical;
  }


 /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbOrder() {
    return mtbOrder;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbOrderDrop() {
    return mtbOrderDrop;
  }

 
/**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRotate() {
    return mtbRotate;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRotateDrop() {
    return mtbRotateDrop;
  }
  
 
  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbFlipHorizontal() {
    return mtbFlipHorizontal;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbFlipVertical() {
    return mtbFlipVertical;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSave() {
    return mtbSave;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSaveAs() {
    return mtbSaveAs;
  }


  


  /**
   * 
   * @description 
   * @version 1.0 18-Oct-2005
   * @author Sudheer V Pujar
   */
  private class ToolBarActionListener implements ActionListener{
    private JButton target;
  
    /**
     * 
     * @description 
     * @param menu
     * @param target
     */
    public ToolBarActionListener(JButton target){
      super();
      this.target=target;
    }
    
    /**
     * 
     * @description 
     * @param source
     */
    private void mtbActionPerformed(JMenuItem source){
      
      target.setToolTipText(source.getToolTipText()); 
      target.setIcon(source.getIcon()); 
      target.setEnabled(source.isEnabled()); 
  
      ActionListener[] oldActionListeners = target.getActionListeners();
      for(int i=0; i<oldActionListeners.length; i++){
        target.removeActionListener(oldActionListeners[i]); 
      }
  
      ActionListener[] newActionListeners = source.getActionListeners();
      for(int i=0; i<newActionListeners.length; i++){
        if (!newActionListeners[i].equals(this)){
          target.addActionListener(newActionListeners[i]);
        }
      }
    }
  
    /**
     * 
     * @description 
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
      System.out.println("Inside Toolbar Action Listener");
      mtbActionPerformed((JMenuItem)e.getSource());
      
    }
  
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignLeft() {
    return menuItemAlignLeft;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignRight() {
    return menuItemAlignRight;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignTop() {
    return menuItemAlignTop;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignBottom() {
    return menuItemAlignBottom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignCenter() {
    return menuItemAlignCenter;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignCenterXAxis() {
    return menuItemAlignCenterXAxis;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemAlignCenterYAxis() {
    return menuItemAlignCenterYAxis;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemSet2SameWidth() {
    return menuItemSet2SameWidth;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemSet2SameHeight() {
    return menuItemSet2SameHeight;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemSet2SameSize() {
    return menuItemSet2SameSize;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemOrder2Top() {
    return menuItemOrder2Top;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemOrder2Bottom() {
    return menuItemOrder2Bottom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemOrder2Up() {
    return menuItemOrder2Up;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemOrder2Down() {
    return menuItemOrder2Down;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemRotate90() {
    return menuItemRotate90;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemRotate180() {
    return menuItemRotate180;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JMenuItem getMenuItemRotate270() {
    return menuItemRotate270;
  }


}