package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.common.ClipartBean;
import bulbul.foff.studio.engine.cliparts.ClipartCategoryNode;
import bulbul.foff.studio.engine.cliparts.ClipartCategoryTreeModel;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.shapes.SVGClipart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.URL;

import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 * 
 * @description 
 * @version 1.0 03-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGCliparts extends JPanel  {
  
  private Dimension sizeOftxtSearch =new Dimension(205,22);
  private Dimension sizeOfButton =new Dimension(22,22);
  private Dimension sizeOfPanel4TreeView =new Dimension(245,200);
  private Dimension sizeOfPanel4Search =new Dimension(245,50);
  private Dimension sizeOfPanel4ThumbnailView =new Dimension(245,123);
  
  private BoxLayout layout4This = new BoxLayout(this,BoxLayout.Y_AXIS);
  private BorderLayout layout4Panel4TreeView = new BorderLayout();
  private FlowLayout layout4Panel4Search = new FlowLayout(FlowLayout.LEFT,2,0);
  private BorderLayout layout4Panel4ThumbnailView = new BorderLayout();
  
  
  private JPanel panel4TreeView = new JPanel();
  private JPanel panel4Search = new JPanel();
  private JPanel panel4ThumbnailView = new JPanel();
  private JPanel thumbnailView4Clipart=new JPanel();
  
  private JTree treeView4Category; 
  
  private JScrollPane scrollPane4TreeView;
  private JScrollPane scrollPane4ThumbnailView;
  
  private JLabel label4ThumbnailView = new JLabel();

  private JTextField txtSearch = new JTextField();
  
  private JButton btnSearch=new JButton();

  private Studio studio;
  
  private Border mouseExitedBorder=BorderFactory.createEmptyBorder(0, 0, 0, 0);
  private Border mouseEnteredBorder=BorderFactory.createLineBorder(new Color(247,161,90),1);
  
  private MouseAdapter buttonMouseListener;
  
  private boolean treeInitialized=false;
  
  
  /**
   * 
   * @description 
   */
   
  public SVGCliparts(Studio studio) {
    try {
      this.studio=studio;
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    
    setLayout(layout4This);
    
    txtSearch.setPreferredSize(sizeOftxtSearch);
    txtSearch.setMaximumSize(sizeOftxtSearch);
    txtSearch.setMinimumSize(sizeOftxtSearch);
    txtSearch.setSize(sizeOftxtSearch);
    txtSearch.setToolTipText("Enter Keyword To Search");
    
    buttonMouseListener=new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        mtbMouseEntered(e);
      }

      public void mouseExited(MouseEvent e) {
        mtbMouseExited(e);
      }        
    };
    
    btnSearch.setPreferredSize(sizeOfButton);
    btnSearch.setMaximumSize(sizeOfButton);
    btnSearch.setMinimumSize(sizeOfButton);
    btnSearch.setSize(sizeOfButton);
    btnSearch.setToolTipText("Press To Search");
    btnSearch.setIcon(getIcon("zoom.png"));
    btnSearch.setBorder(mouseExitedBorder);
    btnSearch.addMouseListener(buttonMouseListener);
    btnSearch.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        final String searchKeywords=txtSearch.getText().trim(); 
        if (searchKeywords.length()!=0){
          Thread listClipartsThread = new Thread(){
            public void run(){
              listCliparts("-1",true,searchKeywords);
            }
          };
          listClipartsThread.start();
        }
      }
    });
    
    TitledBorder categoryBorder= new TitledBorder("Category");
    panel4TreeView.setBorder(categoryBorder);
    panel4TreeView.setLayout(layout4Panel4TreeView);
    panel4TreeView.setPreferredSize(sizeOfPanel4TreeView);
    panel4TreeView.setMaximumSize(sizeOfPanel4TreeView);
    panel4TreeView.setMinimumSize(sizeOfPanel4TreeView);
    panel4TreeView.setSize(sizeOfPanel4TreeView);
    
    TitledBorder searchBorder= new TitledBorder("Search By Keywords");
    panel4Search.setBorder(searchBorder);
    panel4Search.setLayout(layout4Panel4Search);
    panel4Search.setPreferredSize(sizeOfPanel4Search);
    panel4Search.setMaximumSize(sizeOfPanel4Search);
    panel4Search.setMinimumSize(sizeOfPanel4Search);
    panel4Search.setSize(sizeOfPanel4Search);
    
    panel4Search.add(txtSearch);
    panel4Search.add(btnSearch);
    
    TitledBorder viewBorder= new TitledBorder("View");
    panel4ThumbnailView.setLayout(layout4Panel4ThumbnailView);
    panel4ThumbnailView.setBorder(viewBorder);
    panel4ThumbnailView.setPreferredSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setMaximumSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setMinimumSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setSize(sizeOfPanel4ThumbnailView);
    
    label4ThumbnailView.setHorizontalAlignment(JLabel.CENTER);
    label4ThumbnailView.setText("Click On Category To View Cliparts");    
    
    thumbnailView4Clipart.setLayout(new BorderLayout());
    thumbnailView4Clipart.add(label4ThumbnailView, BorderLayout.CENTER);
    
    scrollPane4ThumbnailView =new JScrollPane(thumbnailView4Clipart);
    scrollPane4ThumbnailView.setBorder(BorderFactory.createEmptyBorder());
    
    panel4ThumbnailView.add(scrollPane4ThumbnailView, BorderLayout.CENTER);
    
    this.add(panel4TreeView);
    this.add(panel4Search);
    this.add(panel4ThumbnailView);
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
   * @param searchKeywords
   * @param search
   * @param clipartCategoryTblPk
   */
  private void listCliparts(String clipartCategoryTblPk,boolean search,String searchKeywords){
    HttpBrowser newBrowser = null;
    ObjectInputStream objInStream=null; 
    Dimension seperatorSize=new Dimension(2,2);
    Dimension thumbnailSize=new Dimension(75,75);
    //System.out.println("Inside listCliparts ");
    final SVGClipart svgClipart = (SVGClipart)getStudio().getShapeObject("clipart");
    try {
      studio.getMainStatusBar().setInfo("Connecting .......");
      studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
      newBrowser = new HttpBrowser(studio, "clipartListAction.do");
      Properties args = new Properties();
      args.put("clipartCategoryTblPk",clipartCategoryTblPk);
      args.put("search",Boolean.toString(search));
      args.put("searchKeywords",searchKeywords);
      objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
      ClipartBean[] cliparts=(ClipartBean[])objInStream.readObject();
      int index=0;
      int numberOfClipars=cliparts.length;
      svgClipart.removeClipartActions();
      thumbnailView4Clipart.removeAll();
      thumbnailView4Clipart.setLayout(new BoxLayout(thumbnailView4Clipart,BoxLayout.X_AXIS));
      
      while(index<numberOfClipars){
        Properties svgArgs = new Properties();
        svgArgs.put("dataSourceKey","BOKey");
        svgArgs.put("contentOId",Integer.toString(cliparts[index].getContentOId()));
        svgArgs.put("contentType",cliparts[index].getContentType());
        svgArgs.put("contentSize",Integer.toString(cliparts[index].getContentSize()));
        URL svgUrl=new URL(getStudio().getSvgResource().getContextPath() + "svgDisplayAction.do");
        svgUrl=new URL(svgUrl.toExternalForm()+"?"+ HttpBrowser.toEncodedString(svgArgs));
        //System.out.println("URL Is : " + svgUrl);
        
        final SVGIconCanvas thumb = new SVGIconCanvas(getStudio());
        svgClipart.addClipartAction(thumb);
        
        thumb.setPreferredSize(thumbnailSize);
        thumb.setMaximumSize(thumbnailSize);
        thumb.setMinimumSize(thumbnailSize);
        thumb.setSize(thumbnailSize);
        thumb.setToolTipText(cliparts[index].getName() + " - [ " + cliparts[index].getKeywords() + "]");
        thumb.init();
        
        final MouseAdapter mouseAdapter=new MouseAdapter(){
          public void mouseClicked(MouseEvent e){
            thumb.setSelected(true);
          }
        };
    
        thumb.addMouseListener(mouseAdapter);
        thumb.setURI(svgUrl.toString());
        
        
        JPanel thumbPanel = new JPanel();
        thumbPanel.setLayout(new BorderLayout());
        thumbPanel.setPreferredSize(thumbnailSize);
        thumbPanel.setMaximumSize(thumbnailSize);
        thumbPanel.setMinimumSize(thumbnailSize);
        thumbPanel.setSize(thumbnailSize);
        thumbPanel.add(thumb,BorderLayout.CENTER);
        
        
        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(seperatorSize);
        separatorPanel.setMaximumSize(seperatorSize);
        separatorPanel.setMinimumSize(seperatorSize);
        separatorPanel.setSize(seperatorSize);
        thumbnailView4Clipart.add(separatorPanel);

        thumbnailView4Clipart.add(thumbPanel); 
        index++;
      }
      
      if (numberOfClipars!=0){
        JPanel lastSeparatorPanel = new JPanel();
        lastSeparatorPanel.setPreferredSize(seperatorSize);
        lastSeparatorPanel.setMaximumSize(seperatorSize);
        lastSeparatorPanel.setMinimumSize(seperatorSize);
        lastSeparatorPanel.setSize(seperatorSize);
        thumbnailView4Clipart.add(lastSeparatorPanel);
      }else{
        label4ThumbnailView.setText("No Cliparts Available");
        thumbnailView4Clipart.setLayout(new BorderLayout());
        thumbnailView4Clipart.setBorder(BorderFactory.createEmptyBorder());
        thumbnailView4Clipart.add(label4ThumbnailView, BorderLayout.CENTER);
      }

    }catch (IOException e) {
      e.printStackTrace();
    }catch (ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      try {
        if(objInStream!=null){ 
          objInStream.close();
        }
      }catch (IOException  ioe) {
        ioe.printStackTrace();
      }finally{
        studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
        studio.getMainStatusBar().setInfo("");
      }
    }
  }
  
  public void initTree(){
    if (!isTreeInitialized()){
      treeView4Category= new JTree(new ClipartCategoryTreeModel(getStudio())) {       
        public String convertValueToText(Object value, 
            boolean selected,
            boolean expanded, 
            boolean leaf, 
            int row,
            boolean hasFocus){
          return ((ClipartCategoryNode)value).getName();
        } 
      };
      treeView4Category.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      treeView4Category.setShowsRootHandles(true);
      treeView4Category.addTreeSelectionListener(new TreeSelectionListener(){
        public void valueChanged( TreeSelectionEvent e ){
          final ClipartCategoryNode category = (ClipartCategoryNode)e.getPath().getLastPathComponent();
          if (category.getPk().equals("-1")){
            thumbnailView4Clipart.removeAll();
            label4ThumbnailView.setText("Click On Category To View Cliparts");
            thumbnailView4Clipart.setLayout(new BorderLayout());
            thumbnailView4Clipart.setBorder(BorderFactory.createEmptyBorder());
            thumbnailView4Clipart.add(label4ThumbnailView, BorderLayout.CENTER);
          }else{
            Thread listClipartsThread = new Thread(){
              public void run(){
                listCliparts(category.getPk(),false,""); 
              }
            };
            listClipartsThread.start();
          }
          
        }
      }); 
      scrollPane4TreeView =new JScrollPane(treeView4Category);
      scrollPane4TreeView.setBorder(BorderFactory.createEmptyBorder());
      panel4TreeView.add(scrollPane4TreeView, BorderLayout.CENTER);
      repaint();
    }
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
   * @param treeInitialized
   */
  public void setTreeInitialized(boolean treeInitialized) {
    this.treeInitialized = treeInitialized;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public boolean isTreeInitialized() {
    return treeInitialized;
  }
  
 
  
}