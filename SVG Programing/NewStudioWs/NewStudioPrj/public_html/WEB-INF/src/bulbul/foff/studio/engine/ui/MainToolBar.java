package bulbul.foff.studio.engine.ui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * @author Sudheer V. Pujar
 */
public class MainToolBar extends JToolBar  {
  
  private Dimension buttonSize = new Dimension(24,24);
  private Dimension cboSize = new Dimension(66,22);
  private Dimension seperatorSize = new Dimension(2,24);
  
  private JToolBar fileToolBar=new JToolBar();
  private JToolBar editToolBar=new JToolBar();
  private JToolBar groupToolBar=new JToolBar();
  private JToolBar alignToolBar=new JToolBar();
  private JToolBar set2SameToolBar=new JToolBar();
  private JToolBar rotateToolBar=new JToolBar();
  private JToolBar orderToolBar=new JToolBar();
  private JToolBar zoomToolBar=new JToolBar();
  private JToolBar hvToolBar=new JToolBar();
  
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
  
  private JButton mtbAlignLeft = new JButton();
  private JButton mtbAlignRight = new JButton();
  private JButton mtbAlignTop = new JButton();
  private JButton mtbAlignBottom = new JButton();
  private JButton mtbAlignCenter = new JButton();
  private JButton mtbAlignCenterXAxis = new JButton();
  private JButton mtbAlignCenterYAxis = new JButton();
  
  private JButton mtbSet2SameWidth = new JButton(); 
  private JButton mtbSet2SameHeight = new JButton(); 
  private JButton mtbSet2SameSize = new JButton(); 
  
  private JButton mtbSpaceHorizontal = new JButton(); 
  private JButton mtbSpaceVertical = new JButton(); 
  
  private JButton mtbCenterHorizontal = new JButton(); 
  private JButton mtbCenterVertical = new JButton(); 
  
  private JButton mtbOrder2Top = new JButton(); 
  private JButton mtbOrder2Bottom = new JButton(); 
  private JButton mtbOrder2Up = new JButton(); 
  private JButton mtbOrder2Down = new JButton(); 
  
  private JButton mtbRotate90 = new JButton(); 
  private JButton mtbRotate180 = new JButton(); 
  private JButton mtbRotate270 = new JButton(); 
  
  private JButton mtbFlipHorizontal = new JButton();
  private JButton mtbFlipVertical = new JButton();
  
  private JToggleButton mtbGrid = new JToggleButton();
  private JToggleButton mtbRulers = new JToggleButton();
  
  private JButton mtbZoomIn = new JButton();
  private JButton mtbZoomOut = new JButton();
  private JComboBox cboZoom = new JComboBox();
  
  private SVGApplet studio;
  private FlowLayout layout4This = new FlowLayout();
  private FlowLayout layout4AllToolBars = new FlowLayout();
  
  private Border mouseExitedBorder=BorderFactory.createEmptyBorder(0, 0, 0, 0);
  private Border mouseEnteredBorder=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  private Border toolBarBorder=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  
  private MouseAdapter mtbMouseListener;
  
  
  /**
   * 
   * @param studio
   */
  public MainToolBar(SVGApplet studio) {
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
    
    layout4This.setAlignment(0);
    layout4This.setHgap(0);
    layout4This.setVgap(0);
    
    setLayout(layout4This);
    
    layout4AllToolBars.setAlignment(0);
    layout4AllToolBars.setHgap(0);
    layout4AllToolBars.setVgap(0);
    
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
    
    
    
    //mtbSave.setText("SV");
    mtbSave.setSize(buttonSize);
    mtbSave.setPreferredSize(buttonSize);
    mtbSave.setMaximumSize(buttonSize);
    mtbSave.setMinimumSize(buttonSize);
    mtbSave.setToolTipText("Design Save");
    mtbSave.setEnabled(false);
    mtbSave.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/save.png")));
    mtbSave.setBorder(mouseExitedBorder);
    mtbSave.addMouseListener(mtbMouseListener);
    
    //mtbSaveAs.setText("Sa");
    mtbSaveAs.setSize(buttonSize);
    mtbSaveAs.setPreferredSize(buttonSize);
    mtbSaveAs.setMaximumSize(buttonSize);
    mtbSaveAs.setMinimumSize(buttonSize);
    mtbSaveAs.setToolTipText("Design Save As");
    mtbSaveAs.setEnabled(false);
    mtbSaveAs.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/saveAs.png")));
    mtbSaveAs.setBorder(mouseExitedBorder);
    mtbSaveAs.addMouseListener(mtbMouseListener);
    
    
    //mtbDelete.setText("D");
    mtbDelete.setSize(buttonSize);
    mtbDelete.setPreferredSize(buttonSize);
    mtbDelete.setMaximumSize(buttonSize);
    mtbDelete.setMinimumSize(buttonSize);
    mtbDelete.setToolTipText("Delete - DELETE");
    mtbDelete.setEnabled(false);
    mtbDelete.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/delete.png")));
    mtbDelete.setBorder(mouseExitedBorder);
    mtbDelete.addMouseListener(mtbMouseListener);
    
    //mtbCopy.setText("Cp");
    mtbCopy.setSize(buttonSize);
    mtbCopy.setPreferredSize(buttonSize);
    mtbCopy.setMaximumSize(buttonSize);
    mtbCopy.setMinimumSize(buttonSize);
    mtbCopy.setToolTipText("Copy - CTRL-C");
    mtbCopy.setEnabled(false);
    mtbCopy.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/copy.png")));
    mtbCopy.setBorder(mouseExitedBorder);
    mtbCopy.addMouseListener(mtbMouseListener);
    
    //mtbCut.setText("Ct");
    mtbCut.setSize(buttonSize);
    mtbCut.setPreferredSize(buttonSize);
    mtbCut.setMaximumSize(buttonSize);
    mtbCut.setMinimumSize(buttonSize);
    mtbCut.setToolTipText("Cut - CTRL-X");
    mtbCut.setEnabled(false);
    mtbCut.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/cut.png")));
    mtbCut.setBorder(mouseExitedBorder);
    mtbCut.addMouseListener(mtbMouseListener);
    
    //mtbPaste.setText("Ps");
    mtbPaste.setSize(buttonSize);
    mtbPaste.setPreferredSize(buttonSize);
    mtbPaste.setMaximumSize(buttonSize);
    mtbPaste.setMinimumSize(buttonSize);
    mtbPaste.setToolTipText("Paste - CTRL-V");
    mtbPaste.setEnabled(false);
    mtbPaste.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/paste.png")));
    mtbPaste.setBorder(mouseExitedBorder);
    mtbPaste.addMouseListener(mtbMouseListener);
    
    //mtbUndo.setText("U");
    mtbUndo.setSize(buttonSize);
    mtbUndo.setPreferredSize(buttonSize);
    mtbUndo.setMaximumSize(buttonSize);
    mtbUndo.setMinimumSize(buttonSize);
    mtbUndo.setToolTipText("Undo - CTRL-Z");
    mtbUndo.setEnabled(false);
    mtbUndo.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/undo.png")));
    mtbUndo.setBorder(mouseExitedBorder);
    mtbUndo.addMouseListener(mtbMouseListener);
    
    //mtbRedo.setText("R");
    mtbRedo.setSize(buttonSize);
    mtbRedo.setPreferredSize(buttonSize);
    mtbRedo.setMaximumSize(buttonSize);
    mtbRedo.setMinimumSize(buttonSize);
    mtbRedo.setToolTipText("Redo - CTRL-Y");
    mtbRedo.setEnabled(false);
    mtbRedo.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/redo.png")));
    mtbRedo.setBorder(mouseExitedBorder);
    mtbRedo.addMouseListener(mtbMouseListener);
    
    //mtbLock.setText("L");
    mtbLock.setSize(buttonSize);
    mtbLock.setPreferredSize(buttonSize);
    mtbLock.setMaximumSize(buttonSize);
    mtbLock.setMinimumSize(buttonSize);
    mtbLock.setToolTipText("Lock");
    mtbLock.setEnabled(false);
    mtbLock.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/lock.png")));
    mtbLock.setBorder(mouseExitedBorder);
    mtbLock.addMouseListener(mtbMouseListener);
    
    //mtbUnLock.setText("UL");
    mtbUnLock.setSize(buttonSize);
    mtbUnLock.setPreferredSize(buttonSize);
    mtbUnLock.setMaximumSize(buttonSize);
    mtbUnLock.setMinimumSize(buttonSize);
    mtbUnLock.setToolTipText("UnLock");
    mtbUnLock.setEnabled(false);
    mtbUnLock.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/unlock.png")));
    mtbUnLock.setBorder(mouseExitedBorder);
    mtbUnLock.addMouseListener(mtbMouseListener);
    
    //mtbSelectAll.setText("SA");
    mtbSelectAll.setSize(buttonSize);
    mtbSelectAll.setPreferredSize(buttonSize);
    mtbSelectAll.setMaximumSize(buttonSize);
    mtbSelectAll.setMinimumSize(buttonSize);
    mtbSelectAll.setToolTipText("Select All - CTRL-A");
    mtbSelectAll.setEnabled(false);
    mtbSelectAll.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/selectAll.png")));
    mtbSelectAll.setBorder(mouseExitedBorder);
    mtbSelectAll.addMouseListener(mtbMouseListener);
    
    //mtbDeselectAll.setText("DS");
    mtbDeselectAll.setSize(buttonSize);
    mtbDeselectAll.setPreferredSize(buttonSize);
    mtbDeselectAll.setMaximumSize(buttonSize);
    mtbDeselectAll.setMinimumSize(buttonSize);
    mtbDeselectAll.setToolTipText("Deselect All - CTRL-D");
    mtbDeselectAll.setEnabled(false);
    mtbDeselectAll.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/deselectAll.png")));
    mtbDeselectAll.setBorder(mouseExitedBorder);
    mtbDeselectAll.addMouseListener(mtbMouseListener);
    
    //mtbGroup.setText("Gp");
    mtbGroup.setSize(buttonSize);
    mtbGroup.setPreferredSize(buttonSize);
    mtbGroup.setMaximumSize(buttonSize);
    mtbGroup.setMinimumSize(buttonSize);
    mtbGroup.setToolTipText("Group - CTRL-G");
    mtbGroup.setEnabled(false);
    mtbGroup.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/group.png")));
    mtbGroup.setBorder(mouseExitedBorder);
    mtbGroup.addMouseListener(mtbMouseListener);
    
    //mtbUnGroup.setText("Ug");
    mtbUnGroup.setSize(buttonSize);
    mtbUnGroup.setPreferredSize(buttonSize);
    mtbUnGroup.setMaximumSize(buttonSize);
    mtbUnGroup.setMinimumSize(buttonSize);
    mtbUnGroup.setToolTipText("UnGroup - CTRL-U ");
    mtbUnGroup.setEnabled(false);
    mtbUnGroup.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/ungroup.png")));
    mtbUnGroup.setBorder(mouseExitedBorder);
    mtbUnGroup.addMouseListener(mtbMouseListener);
    
    //mtbGroupEnter.setText("Ge");
    mtbGroupEnter.setSize(buttonSize);
    mtbGroupEnter.setPreferredSize(buttonSize);
    mtbGroupEnter.setMaximumSize(buttonSize);
    mtbGroupEnter.setMinimumSize(buttonSize);
    mtbGroupEnter.setToolTipText("Group Enter");
    mtbGroupEnter.setEnabled(false);
    mtbGroupEnter.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/groupEnter.png")));
    mtbGroupEnter.setBorder(mouseExitedBorder);
    mtbGroupEnter.addMouseListener(mtbMouseListener);
    
    //mtbGroupExit.setText("Gx");
    mtbGroupExit.setSize(buttonSize);
    mtbGroupExit.setPreferredSize(buttonSize);
    mtbGroupExit.setMaximumSize(buttonSize);
    mtbGroupExit.setMinimumSize(buttonSize);
    mtbGroupExit.setToolTipText("Group Exit");
    mtbGroupExit.setEnabled(false);
    mtbGroupExit.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/groupExit.png")));
    mtbGroupExit.setBorder(mouseExitedBorder);
    mtbGroupExit.addMouseListener(mtbMouseListener);
    
    //mtbAlignLeft.setText("L");
    mtbAlignLeft.setSize(buttonSize);
    mtbAlignLeft.setPreferredSize(buttonSize);
    mtbAlignLeft.setMaximumSize(buttonSize);
    mtbAlignLeft.setMinimumSize(buttonSize);
    mtbAlignLeft.setToolTipText("Align Left - SHIFT-LEFT");
    mtbAlignLeft.setEnabled(false);
    mtbAlignLeft.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignLeft.png")));
    mtbAlignLeft.setBorder(mouseExitedBorder);
    mtbAlignLeft.addMouseListener(mtbMouseListener);
    
    //mtbAlignRight.setText("R");
    mtbAlignRight.setSize(buttonSize);
    mtbAlignRight.setPreferredSize(buttonSize);
    mtbAlignRight.setMaximumSize(buttonSize);
    mtbAlignRight.setMinimumSize(buttonSize);
    mtbAlignRight.setToolTipText("Align Right - SHIFT-RIGHT");
    mtbAlignRight.setEnabled(false);
    mtbAlignRight.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignRight.png")));
    mtbAlignRight.setBorder(mouseExitedBorder);
    mtbAlignRight.addMouseListener(mtbMouseListener);
    
    //mtbAlignTop.setText("T");
    mtbAlignTop.setSize(buttonSize);
    mtbAlignTop.setPreferredSize(buttonSize);
    mtbAlignTop.setMaximumSize(buttonSize);
    mtbAlignTop.setMinimumSize(buttonSize);
    mtbAlignTop.setToolTipText("Align Top - SHIFT-UP");
    mtbAlignTop.setEnabled(false);
    mtbAlignTop.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignTop.png")));
    mtbAlignTop.setBorder(mouseExitedBorder);
    mtbAlignTop.addMouseListener(mtbMouseListener);
    
    //mtbAlignBottom.setText("B");
    mtbAlignBottom.setSize(buttonSize);
    mtbAlignBottom.setPreferredSize(buttonSize);
    mtbAlignBottom.setMaximumSize(buttonSize);
    mtbAlignBottom.setMinimumSize(buttonSize);
    mtbAlignBottom.setToolTipText("Align Bottom - SHIFT-DOWN");
    mtbAlignBottom.setEnabled(false);
    mtbAlignBottom.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignBottom.png")));
    mtbAlignBottom.setBorder(mouseExitedBorder);
    mtbAlignBottom.addMouseListener(mtbMouseListener);
    
    //mtbAlignCenter.setText("C");
    mtbAlignCenter.setSize(buttonSize);
    mtbAlignCenter.setPreferredSize(buttonSize);
    mtbAlignCenter.setMaximumSize(buttonSize);
    mtbAlignCenter.setMinimumSize(buttonSize);
    mtbAlignCenter.setToolTipText("Align Center - SHIFT-C ");
    mtbAlignCenter.setEnabled(false);
    mtbAlignCenter.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignCenter.png")));
    mtbAlignCenter.setBorder(mouseExitedBorder);
    mtbAlignCenter.addMouseListener(mtbMouseListener);
    
    //mtbAlignCenterXAxis.setText("Cx");
    mtbAlignCenterXAxis.setSize(buttonSize);
    mtbAlignCenterXAxis.setPreferredSize(buttonSize);
    mtbAlignCenterXAxis.setMaximumSize(buttonSize);
    mtbAlignCenterXAxis.setMinimumSize(buttonSize);
    mtbAlignCenterXAxis.setToolTipText("Align - Center Along X-Axis");
    mtbAlignCenterXAxis.setEnabled(false);
    mtbAlignCenterXAxis.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignHorizontalCenter.png")));
    mtbAlignCenterXAxis.setBorder(mouseExitedBorder);
    mtbAlignCenterXAxis.addMouseListener(mtbMouseListener);
    
    //mtbAlignCenterYAxis.setText("Cy");
    mtbAlignCenterYAxis.setSize(buttonSize);
    mtbAlignCenterYAxis.setPreferredSize(buttonSize);
    mtbAlignCenterYAxis.setMaximumSize(buttonSize);
    mtbAlignCenterYAxis.setMinimumSize(buttonSize);
    mtbAlignCenterYAxis.setToolTipText("Align - Center Along Y-Axis");
    mtbAlignCenterYAxis.setEnabled(false);
    mtbAlignCenterYAxis.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/alignVerticalCenter.png")));
    mtbAlignCenterYAxis.setBorder(mouseExitedBorder);
    mtbAlignCenterYAxis.addMouseListener(mtbMouseListener);
    
    //mtbSet2SameWidth.setText("W");
    mtbSet2SameWidth.setSize(buttonSize);
    mtbSet2SameWidth.setPreferredSize(buttonSize);
    mtbSet2SameWidth.setMaximumSize(buttonSize);
    mtbSet2SameWidth.setMinimumSize(buttonSize);
    mtbSet2SameWidth.setToolTipText("Set To Same Width");
    mtbSet2SameWidth.setEnabled(false);
    mtbSet2SameWidth.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/sameWidth.png")));
    mtbSet2SameWidth.setBorder(mouseExitedBorder);
    mtbSet2SameWidth.addMouseListener(mtbMouseListener);
    
    //mtbSet2SameHeight.setText("H");
    mtbSet2SameHeight.setSize(buttonSize);
    mtbSet2SameHeight.setPreferredSize(buttonSize);
    mtbSet2SameHeight.setMaximumSize(buttonSize);
    mtbSet2SameHeight.setMinimumSize(buttonSize);
    mtbSet2SameHeight.setToolTipText("Set To Same Height");
    mtbSet2SameHeight.setEnabled(false);
    mtbSet2SameHeight.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/sameHeight.png")));
    mtbSet2SameHeight.setBorder(mouseExitedBorder);
    mtbSet2SameHeight.addMouseListener(mtbMouseListener);
    
    //mtbSet2SameSize.setText("Sz");
    mtbSet2SameSize.setSize(buttonSize);
    mtbSet2SameSize.setPreferredSize(buttonSize);
    mtbSet2SameSize.setMaximumSize(buttonSize);
    mtbSet2SameSize.setMinimumSize(buttonSize);
    mtbSet2SameSize.setToolTipText("Set To Same Size");
    mtbSet2SameSize.setEnabled(false);
    mtbSet2SameSize.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/sameSize.png")));
    mtbSet2SameSize.setBorder(mouseExitedBorder);
    mtbSet2SameSize.addMouseListener(mtbMouseListener);
    
    //mtbSpaceHorizontal.setText("Sh");
    mtbSpaceHorizontal.setSize(buttonSize);
    mtbSpaceHorizontal.setPreferredSize(buttonSize);
    mtbSpaceHorizontal.setMaximumSize(buttonSize);
    mtbSpaceHorizontal.setMinimumSize(buttonSize);
    mtbSpaceHorizontal.setToolTipText("Space - Horizontal");
    mtbSpaceHorizontal.setEnabled(false);
    mtbSpaceHorizontal.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/horizontalSpacing.png")));
    mtbSpaceHorizontal.setBorder(mouseExitedBorder);
    mtbSpaceHorizontal.addMouseListener(mtbMouseListener);
    
    //mtbSpaceVertical.setText("Sv");
    mtbSpaceVertical.setSize(buttonSize);
    mtbSpaceVertical.setPreferredSize(buttonSize);
    mtbSpaceVertical.setMaximumSize(buttonSize);
    mtbSpaceVertical.setMinimumSize(buttonSize);
    mtbSpaceVertical.setToolTipText("Space - Vertical");
    mtbSpaceVertical.setEnabled(false);
    mtbSpaceVertical.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/verticalSpacing.png")));
    mtbSpaceVertical.setBorder(mouseExitedBorder);
    mtbSpaceVertical.addMouseListener(mtbMouseListener);
    
    //mtbCenterHorizontal.setText("Ch");
    mtbCenterHorizontal.setSize(buttonSize);
    mtbCenterHorizontal.setPreferredSize(buttonSize);
    mtbCenterHorizontal.setMaximumSize(buttonSize);
    mtbCenterHorizontal.setMinimumSize(buttonSize);
    mtbCenterHorizontal.setToolTipText("Center - Horizontal");
    mtbCenterHorizontal.setEnabled(false);
    mtbCenterHorizontal.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/horizontalCenter.png")));
    mtbCenterHorizontal.setBorder(mouseExitedBorder);
    mtbCenterHorizontal.addMouseListener(mtbMouseListener);
    
    //mtbCenterVertical.setText("Cv");
    mtbCenterVertical.setSize(buttonSize);
    mtbCenterVertical.setPreferredSize(buttonSize);
    mtbCenterVertical.setMaximumSize(buttonSize);
    mtbCenterVertical.setMinimumSize(buttonSize);
    mtbCenterVertical.setToolTipText("Center - Vertical");
    mtbCenterVertical.setEnabled(false);
    mtbCenterVertical.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/verticalCenter.png")));
    mtbCenterVertical.setBorder(mouseExitedBorder);
    mtbCenterVertical.addMouseListener(mtbMouseListener);
    
    //mtbOrder2Top.setText("oT");
    mtbOrder2Top.setSize(buttonSize);
    mtbOrder2Top.setPreferredSize(buttonSize);
    mtbOrder2Top.setMaximumSize(buttonSize);
    mtbOrder2Top.setMinimumSize(buttonSize);
    mtbOrder2Top.setToolTipText("Send to Top - CTRL+SHIFT-UP");
    mtbOrder2Top.setEnabled(false);
    mtbOrder2Top.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/orderTop.png")));
    mtbOrder2Top.setBorder(mouseExitedBorder);
    mtbOrder2Top.addMouseListener(mtbMouseListener);
    
    //mtbOrder2Bottom.setText("oB");
    mtbOrder2Bottom.setSize(buttonSize);
    mtbOrder2Bottom.setPreferredSize(buttonSize);
    mtbOrder2Bottom.setMaximumSize(buttonSize);
    mtbOrder2Bottom.setMinimumSize(buttonSize);
    mtbOrder2Bottom.setToolTipText("Send to Bottom - CTRL+SHIFT-DOWN");
    mtbOrder2Bottom.setEnabled(false);
    mtbOrder2Bottom.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/orderBottom.png")));
    mtbOrder2Bottom.setBorder(mouseExitedBorder);
    mtbOrder2Bottom.addMouseListener(mtbMouseListener);

    //mtbOrder2Up.setText("oU");
    mtbOrder2Up.setSize(buttonSize);
    mtbOrder2Up.setPreferredSize(buttonSize);
    mtbOrder2Up.setMaximumSize(buttonSize);
    mtbOrder2Up.setMinimumSize(buttonSize);
    mtbOrder2Up.setToolTipText("Send to Front - CTRL-UP");
    mtbOrder2Up.setEnabled(false);
    mtbOrder2Up.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/orderUp.png")));
    mtbOrder2Up.setBorder(mouseExitedBorder);
    mtbOrder2Up.addMouseListener(mtbMouseListener);
    
    //mtbOrder2Down.setText("oD");
    mtbOrder2Down.setSize(buttonSize);
    mtbOrder2Down.setPreferredSize(buttonSize);
    mtbOrder2Down.setMaximumSize(buttonSize);
    mtbOrder2Down.setMinimumSize(buttonSize);
    mtbOrder2Down.setToolTipText("Send to Back - CTRL-DOWN");
    mtbOrder2Down.setEnabled(false);    
    mtbOrder2Down.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/orderDown.png")));
    mtbOrder2Down.setBorder(mouseExitedBorder);
    mtbOrder2Down.addMouseListener(mtbMouseListener);
    
    //mtbRotate90.setText("90°");
    mtbRotate90.setSize(buttonSize);
    mtbRotate90.setPreferredSize(buttonSize);
    mtbRotate90.setMaximumSize(buttonSize);
    mtbRotate90.setMinimumSize(buttonSize);
    mtbRotate90.setToolTipText("Rotate 90°");
    mtbRotate90.setEnabled(false);    
    mtbRotate90.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/rotate90.png")));
    mtbRotate90.setBorder(mouseExitedBorder);
    mtbRotate90.addMouseListener(mtbMouseListener);
    
    //mtbRotate180.setText("180°");
    mtbRotate180.setSize(buttonSize);
    mtbRotate180.setPreferredSize(buttonSize);
    mtbRotate180.setMaximumSize(buttonSize);
    mtbRotate180.setMinimumSize(buttonSize);
    mtbRotate180.setToolTipText("Rotate 180°");
    mtbRotate180.setEnabled(false);    
    mtbRotate180.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/rotate180.png")));
    mtbRotate180.setBorder(mouseExitedBorder);
    mtbRotate180.addMouseListener(mtbMouseListener);
    
    //mtbRotate270.setText("270°");
    mtbRotate270.setSize(buttonSize);
    mtbRotate270.setPreferredSize(buttonSize);
    mtbRotate270.setMaximumSize(buttonSize);
    mtbRotate270.setMinimumSize(buttonSize);
    mtbRotate270.setToolTipText("Rotate 270°");
    mtbRotate270.setEnabled(false);    
    mtbRotate270.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/rotate270.png")));
    mtbRotate270.setBorder(mouseExitedBorder);
    mtbRotate270.addMouseListener(mtbMouseListener);
    
    //mtbFlipHorizontal.setText("Fh");
    mtbFlipHorizontal.setSize(buttonSize);
    mtbFlipHorizontal.setPreferredSize(buttonSize);
    mtbFlipHorizontal.setMaximumSize(buttonSize);
    mtbFlipHorizontal.setMinimumSize(buttonSize);
    mtbFlipHorizontal.setToolTipText("Flip - Horizontal");
    mtbFlipHorizontal.setEnabled(false);    
    mtbFlipHorizontal.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/horizontalFlip.png")));
    mtbFlipHorizontal.setBorder(mouseExitedBorder);
    mtbFlipHorizontal.addMouseListener(mtbMouseListener);
    
    //mtbFlipVertical.setText("Fv");
    mtbFlipVertical.setSize(buttonSize);
    mtbFlipVertical.setPreferredSize(buttonSize);
    mtbFlipVertical.setMaximumSize(buttonSize);
    mtbFlipVertical.setMinimumSize(buttonSize);
    mtbFlipVertical.setToolTipText("Flip - Vertical");
    mtbFlipVertical.setEnabled(false);    
    mtbFlipVertical.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/verticalFlip.png")));
    mtbFlipVertical.setBorder(mouseExitedBorder);
    mtbFlipVertical.addMouseListener(mtbMouseListener);
    
    //mtbGrid.setText("Gd");
    mtbGrid.setSize(buttonSize);
    mtbGrid.setPreferredSize(buttonSize);
    mtbGrid.setMaximumSize(buttonSize);
    mtbGrid.setMinimumSize(buttonSize);
    mtbGrid.setToolTipText("Grids On/Off");
    mtbGrid.setEnabled(false);
    mtbGrid.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/grid.png")));
    mtbGrid.setBorder(mouseExitedBorder);
    mtbGrid.addMouseListener(mtbMouseListener);
    mtbGrid.addMouseListener(mtbMouseListener);
    
    //mtbRulers.setText("Rr");
    mtbRulers.setSize(buttonSize);
    mtbRulers.setPreferredSize(buttonSize);
    mtbRulers.setMaximumSize(buttonSize);
    mtbRulers.setMinimumSize(buttonSize);
    mtbRulers.setToolTipText("Rulers On/Off");
    mtbRulers.setSelected(true);
    mtbRulers.setEnabled(false);
    mtbRulers.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/rulers.png")));
    mtbRulers.setBorder(mouseExitedBorder);
    mtbRulers.addMouseListener(mtbMouseListener);
    
    //mtbZoomIn.setText("+");
    mtbZoomIn.setSize(buttonSize);
    mtbZoomIn.setPreferredSize(buttonSize);
    mtbZoomIn.setMaximumSize(buttonSize);
    mtbZoomIn.setMinimumSize(buttonSize);
    mtbZoomIn.setToolTipText("ZoomIn   +");
    mtbZoomIn.setEnabled(false);
    mtbZoomIn.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/zoom+.png")));
    mtbZoomIn.setBorder(mouseExitedBorder);
    mtbZoomIn.addMouseListener(mtbMouseListener);
    
    //mtbZoomOut.setText("-");;
    mtbZoomOut.setSize(buttonSize);
    mtbZoomOut.setPreferredSize(buttonSize);
    mtbZoomOut.setMaximumSize(buttonSize);
    mtbZoomOut.setMinimumSize(buttonSize);
    mtbZoomOut.setToolTipText("ZoomOut   -");
    mtbZoomOut.setEnabled(false);
    mtbZoomOut.setIcon(new ImageIcon(new URL(getStudio().getCodeBase(),"icons/zoom-.png")));
    mtbZoomOut.setBorder(mouseExitedBorder);
    mtbZoomOut.addMouseListener(mtbMouseListener);
    
    cboZoom.setSize(cboSize);
    cboZoom.setPreferredSize(cboSize);
    cboZoom.setMaximumSize(cboSize);
    cboZoom.setMinimumSize(cboSize);
    cboZoom.setToolTipText("Zoom %");
    cboZoom.setEnabled(false);
    cboZoom.setBorder(mouseExitedBorder);
    cboZoom.addMouseListener(mtbMouseListener);
    
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
    
    
    set2SameToolBar.add(mtbSet2SameWidth);
    set2SameToolBar.add(mtbSet2SameHeight);
    set2SameToolBar.add(mtbSet2SameSize);
    
    alignToolBar.add(mtbAlignLeft);
    alignToolBar.add(mtbAlignRight);
    alignToolBar.add(mtbAlignTop);
    alignToolBar.add(mtbAlignBottom);
    alignToolBar.add(mtbAlignCenter);
    alignToolBar.add(mtbAlignCenterXAxis);
    alignToolBar.add(mtbAlignCenterYAxis);
    
    orderToolBar.add(mtbOrder2Top);
    orderToolBar.add(mtbOrder2Bottom);
    orderToolBar.add(mtbOrder2Up);
    orderToolBar.add(mtbOrder2Down);
    
    rotateToolBar.add(mtbRotate90);
    rotateToolBar.add(mtbRotate180);
    rotateToolBar.add(mtbRotate270);
    
    hvToolBar.add(mtbSpaceHorizontal);
    hvToolBar.add(mtbSpaceVertical);
    hvToolBar.add(mtbCenterHorizontal);
    hvToolBar.add(mtbCenterVertical);
    hvToolBar.add(mtbFlipHorizontal);
    hvToolBar.add(mtbFlipVertical);
    
    zoomToolBar.add(mtbZoomIn);
    zoomToolBar.add(mtbZoomOut);
    zoomToolBar.addSeparator(seperatorSize); 
    zoomToolBar.add(cboZoom); 
    zoomToolBar.addSeparator(seperatorSize); 
    
    this.add(fileToolBar);
    this.add(editToolBar);
    this.add(groupToolBar);
    this.add(set2SameToolBar);
    this.add(hvToolBar);
    this.add(alignToolBar);
    this.add(rotateToolBar);
    this.add(orderToolBar);
    this.add(zoomToolBar);
    
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
  public SVGApplet getStudio() {
    return studio;
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
  public JComboBox getCboZoom() {
    return cboZoom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignLeft() {
    return mtbAlignLeft;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignRight() {
    return mtbAlignRight;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignTop() {
    return mtbAlignTop;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignBottom() {
    return mtbAlignBottom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignCenter() {
    return mtbAlignCenter;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignCenterXAxis() {
    return mtbAlignCenterXAxis;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbAlignCenterYAxis() {
    return mtbAlignCenterYAxis;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSet2SameWidth() {
    return mtbSet2SameWidth;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSet2SameHeight() {
    return mtbSet2SameHeight;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbSet2SameSize() {
    return mtbSet2SameSize;
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
  public JButton getMtbOrder2Top() {
    return mtbOrder2Top;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbOrder2Bottom() {
    return mtbOrder2Bottom;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbOrder2Up() {
    return mtbOrder2Up;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbOrder2Down() {
    return mtbOrder2Down;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRotate90() {
    return mtbRotate90;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRotate180() {
    return mtbRotate180;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getMtbRotate270() {
    return mtbRotate270;
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
 
}