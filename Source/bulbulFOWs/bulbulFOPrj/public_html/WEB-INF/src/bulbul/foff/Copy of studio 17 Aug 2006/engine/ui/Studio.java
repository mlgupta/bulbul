package bulbul.foff.studio.engine.ui;

import bulbul.foff.studio.common.PrintableAreaBean;
import bulbul.foff.studio.engine.colors.SVGColorChooser;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.general.Customer;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGCursors;
import bulbul.foff.studio.engine.general.SVGToolkit;
import bulbul.foff.studio.engine.managers.SVGClassManager;
import bulbul.foff.studio.engine.managers.SVGColorManager;
import bulbul.foff.studio.engine.managers.SVGTabManager;
import bulbul.foff.studio.engine.resource.SVGResource;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.shapes.SVGShape;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * 
 * @description 
 * @version 1.0 16-Aug-2005
 * @author Sudheer V. Pujar
 */
public class Studio extends JApplet  {

  public static final Font STUDIO_FONT=new Font("studioFont", Font.ROMAN_BASELINE, 10);

  private BorderLayout layout4Panel4This = new BorderLayout();
  private BorderLayout layout4CenterPanel = new BorderLayout();
  private BorderLayout layout4WestPanel = new BorderLayout();
  private BorderLayout layout4MainStatusBar = new BorderLayout();
  private BorderLayout layout4MainToolBar = new BorderLayout();
  private BorderLayout layout4MainTabSet = new BorderLayout();
  private BorderLayout layout4SVGToolBar = new BorderLayout();
  private BorderLayout layout4SVGTabSet = new BorderLayout();
  
  private JPanel panel4This = new JPanel();
  private JPanel panel4MainToolBar = new JPanel();
  private JPanel panel4SVGToolBar = new JPanel();
  private JPanel panel4PropertySheet = new JPanel();
  private JPanel panel4Cliparts = new JPanel();
  private JPanel panel4Images = new JPanel();
  private JPanel panel4MainStatusBar = new JPanel();
  private JPanel panel4MainTabSet = new JPanel();
  private JPanel panel4SVGTabSet = new JPanel();
  private JPanel panel4Merchandise = new JPanel();
  private JPanel panel4Designs = new JPanel();
  private JPanel centerPanel = new JPanel();
  private JPanel westPanel = new JPanel();
  
  private JTabbedPane mainTabSet =null;
  private JTabbedPane svgTabSet = null;
  
  private MainStatusBar mainStatusBar;
  private MainToolBar mainToolBar;
  private SVGToolBar svgToolBar;
  private SVGToolkit svgToolkit;
  private SVGResource svgResource;
  private SVGClassManager svgClassManager;
  private SVGUndoRedo  svgUndoRedo;
  private SVGSelection  svgSelection;
  private SVGColorChooser colorChooser=null;
  private Studio studio=this;
  
  private Customer customerInfo=null;
  
  private JComponent desktop;
  
  /**
   * 
   * @description 
   */
  public void init() {
    try {
      
      String customerEmailIdTblPk=getParameter("customerEmailIdTblPk");
      String customerEmailId=getParameter("customerEmailId");
      String customerDesignTblPk=getParameter("customerDesignTblPk");
      String customerDesignName=getParameter("customerDesignName");
      String merchandiseTblPk=getParameter("merchandiseTblPk");
      String merchandiseColorTblPk=getParameter("merchandiseColorTblPk");
      
      System.out.println(" Customer Email Tbl Pk : " + customerEmailIdTblPk);
      System.out.println(" Customer Email Id : " + customerEmailId);
      System.out.println(" Customer Design Tbl Pk : " +  customerDesignTblPk);
      System.out.println(" Customer Design Name : " +  customerDesignName);
      System.out.println(" Merchandise Tbl Pk : " + merchandiseTblPk);
      System.out.println(" Merchandise Color Tbl Pk : " + merchandiseColorTblPk);
      
      customerInfo = new Customer(); 
      
      if (customerEmailIdTblPk!=null ){
        if(customerEmailIdTblPk.trim().length()!=0 && !(customerEmailIdTblPk.trim().equals("-1"))){
          customerInfo.setCustomerEmailIdTblPk(customerEmailIdTblPk);
          customerInfo.setCustomerNew(false);
        }
      }
      
      if (customerEmailId!=null ){
        if(customerEmailId.trim().length()!=0){
          customerInfo.setCustomerEmailId(customerEmailId);
        }
      }
      
      if (customerDesignTblPk!=null ){
        if(customerDesignTblPk.trim().length()!=0){
          customerInfo.setCustomerDesignTblPk(customerDesignTblPk);
        }
      }
      
      if (customerDesignName!=null ){
        if(customerDesignName.trim().length()!=0){
          customerInfo.setCustomerDesignName(customerDesignName);
        }
      }else{
        customerInfo.setCustomerDesignName("Untitled");
      }
      
      if (merchandiseTblPk!=null ){
        if(merchandiseTblPk.trim().length()!=0){
          customerInfo.setMerchandiseTblPk(merchandiseTblPk);
        }else{
          customerInfo.setMerchandiseTblPk("-1");
        }
      }
      
      if (merchandiseColorTblPk!=null ){
        if(merchandiseColorTblPk.trim().length()!=0){
          customerInfo.setMerchandiseColorTblPk(merchandiseColorTblPk);
        }
      }
      
      //creating the toolkit object
      svgToolkit=new SVGToolkit(this);
      svgResource=new SVGResource(this);
      
      uiManagerInit();
      jbInit();
      otherInit();

      Thread initThread = new Thread(){
        public void run(){
          svgClassManager=new SVGClassManager(studio);
          svgClassManager.init();
          
          //sets the default color chooser
          if(getColorChooser()==null){
            setColorChooser(new SVGColorChooser(studio));
          }
          
          try{
            svgUndoRedo=(SVGUndoRedo)svgClassManager.getClassObject("UndoRedo");
          }catch (Exception ex){svgUndoRedo=null;}
          
          try{
            svgSelection=(SVGSelection)svgClassManager.getClassObject("Selection");
          }catch (Exception ex){svgSelection=null;}	
          
          svgToolBar.init();
          createDynamicSvgTabs();
        }
      };
      
      initThread.start();
      
      
      
//      Thread dynamicTabThread = new Thread(){
//        public void run(){
//          createDynamicSvgTabs();
//        }
//      };
//      
//      dynamicTabThread.start();
      
      /* Temp code starts*/ 
      /*
      HttpBrowser newBrowser = new HttpBrowser(this, "designNameCheckAction.do");

      Properties args = new Properties();
      args.put("designName","mirror100");
      args.put("yourEmailId",getParameter("customerEmailId"));
      InputStream in =newBrowser.sendPostMessage(args);
      byte[] outBytes = new byte[in.available()]; 
      in.read(outBytes);
      in.close();

      ByteArrayOutputStream byteArrayout = new ByteArrayOutputStream();
      byteArrayout.write(outBytes);
      byteArrayout.flush();
      byteArrayout.close(); 
      String result=byteArrayout.toString();
      
      System.out.println("Result is  : " + result);
      */
      /* Temp code ends*/ 
      
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
    setBackground(new Color(255,255,255,255));
   
    final Rectangle studioBounds=new Rectangle(0, 0, getWidth(), getHeight());
    
    desktop = new JDesktopPane();
    mainToolBar = new MainToolBar(this);
    svgToolBar = new SVGToolBar(this);
    mainStatusBar = new MainStatusBar(this);
    
    panel4MainToolBar.setLayout(layout4MainToolBar);
    panel4MainToolBar.setBackground(new Color(255,255,255,255));
    panel4MainToolBar.setBorder(BorderFactory.createEmptyBorder(0,1,0,0));
    panel4MainToolBar.add(mainToolBar, BorderLayout.CENTER);
    
    panel4SVGToolBar.setLayout(layout4SVGToolBar);
    panel4SVGToolBar.setBackground(new Color(255,255,255,255));
    panel4SVGToolBar.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
    panel4SVGToolBar.add(svgToolBar, BorderLayout.CENTER);
    
    svgTabSet=new JTabbedPane();
    
    panel4SVGTabSet.setLayout(layout4SVGTabSet);
    panel4SVGTabSet.setBackground(new Color(255,255,255,255)); 
    panel4SVGTabSet.setBorder(BorderFactory.createEmptyBorder(0,2,0,0));
    panel4SVGTabSet.add(svgTabSet, BorderLayout.CENTER);
    
    mainTabSet=new JTabbedPane();
    mainTabSet.addTab("Properties",getSvgResource().getIcon("properties.png"),panel4PropertySheet);
    mainTabSet.addTab("Cliparts",getSvgResource().getIcon("image.png"),panel4Cliparts);
    mainTabSet.addTab("Images",getSvgResource().getIcon("image.png"),panel4Images);
    mainTabSet.addTab("Merchandise",getSvgResource().getIcon("image.png"),panel4Merchandise);
    mainTabSet.addTab("Designs",getSvgResource().getIcon("image.png"),panel4Designs);
    
    panel4MainTabSet.setBackground(new Color(255,255,255,255));
    panel4MainTabSet.setLayout(layout4MainTabSet);
    panel4MainTabSet.add(mainTabSet, BorderLayout.CENTER);
    
    svgTabSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent evt){
        JTabbedPane source=(JTabbedPane)evt.getSource();
        SVGTab svgTab=(SVGTab)source.getSelectedComponent();
        if(	svgTab!=null && getStudio().getSvgTabManager().getCurrentSVGTab()!=svgTab){
          getStudio().getSvgTabManager().setCurrentSVGTab(svgTab.getName());
        }
      }
    });
    
    centerPanel.setLayout(layout4CenterPanel);
    centerPanel.setBackground(new Color(255,255,255,255));
    centerPanel.add(panel4MainToolBar, BorderLayout.NORTH);
    centerPanel.add(panel4SVGTabSet, BorderLayout.CENTER);
    centerPanel.add(panel4SVGToolBar, BorderLayout.EAST);
    
    panel4MainStatusBar.setLayout(layout4MainStatusBar);
    panel4MainStatusBar.setBackground(new Color(255,255,255,255));
    panel4MainStatusBar.setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
    panel4MainStatusBar.add(mainStatusBar, BorderLayout.CENTER);
    
    westPanel.setLayout(layout4WestPanel);
    westPanel.setBackground(new Color(255,255,255,255));
    westPanel.add(panel4MainTabSet,BorderLayout.CENTER);
    
    panel4This.setLayout(layout4Panel4This);
    panel4This.setBackground(new Color(255,255,255,255));
    
    panel4This.add(panel4MainStatusBar, BorderLayout.SOUTH);
    panel4This.add(centerPanel, BorderLayout.CENTER);
    panel4This.add(westPanel, BorderLayout.WEST);
    
    panel4This.setBounds(studioBounds);
    this.getContentPane().add(panel4This);
    
    
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   */
  private void otherInit()throws Exception{
    svgToolBar.setPreferredSize(new Dimension(30,this.getPreferredSize().height));
    mainToolBar.setPreferredSize(new Dimension(700,58));
    mainStatusBar.setPreferredSize(new Dimension(this.getPreferredSize().width,18));
    
    panel4PropertySheet.setPreferredSize(new Dimension(250,this.getPreferredSize().height));
    panel4Cliparts.setPreferredSize(new Dimension(250,this.getPreferredSize().height));
    panel4Images.setPreferredSize(new Dimension(250,this.getPreferredSize().height));
    
    repaint();
  }
  
  /**
   * 
   * @description 
   * @throws java.lang.Exception
   */
  private void uiManagerInit() throws Exception{
    
    UIManager.put("Button.background", new Color(253,235,212));
    UIManager.put("Button.border", new SoftBevelBorder(SoftBevelBorder.RAISED));
    UIManager.put("Button.darkShadow", new Color(247,161,90));
    UIManager.put("Button.focus", new Color(247,161,90));
    UIManager.put("Button.font", STUDIO_FONT);
    UIManager.put("Button.highlight", new Color(253,235,212));
    UIManager.put("Button.light", new Color(253,235,212));
    UIManager.put("Button.select", new Color(247,161,90));
    UIManager.put("Button.shadow", new Color(247,161,90));
  
    UIManager.put("ComboBox.background", new Color(253,235,212));
    UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());
    UIManager.put("ComboBox.buttonBackground", new Color(247,161,90));
    UIManager.put("ComboBox.buttonDarkShadow", new Color(247,161,90));
    UIManager.put("ComboBox.buttonHighlight", new Color(247,161,90));
    UIManager.put("ComboBox.buttonShadow", new Color(247,161,90));
    UIManager.put("ComboBox.font", STUDIO_FONT);	
    UIManager.put("ComboBox.selectionBackground", new Color(247,161,90));	
  
    UIManager.put("EditorPane.border", new LineBorder(new Color(247,161,90),1));
    
    
    
    UIManager.put("FormattedTextField.border", new LineBorder(new Color(247,161,90),1));  
    UIManager.put("FormattedTextField.darkShadow", new Color(247,161,90));
    UIManager.put("FormattedTextField.font", STUDIO_FONT);	
    UIManager.put("FormattedTextField.selectionBackground", new Color(247,161,90));
    UIManager.put("FormattedTextField.shadow", new Color(247,161,90));
    
    
    
    UIManager.put("Label.font", STUDIO_FONT);	
    
    UIManager.put("List.focusCellHighlightBorder", new LineBorder(new Color(247,161,90),1));
    UIManager.put("List.font", STUDIO_FONT);
    UIManager.put("List.selectionBackground", new Color(247,161,90));
  
    UIManager.put("Menu.background", new Color(253,235,212));
    UIManager.put("Menu.border", BorderFactory.createEmptyBorder(1,1,1,1));
    UIManager.put("Menu.font", STUDIO_FONT);
    UIManager.put("Menu.selectionBackground", new Color(247,161,90));
    
    UIManager.put("MenuItem.acceleratorFont", STUDIO_FONT);
    UIManager.put("MenuItem.acceleratorForeground", new Color(247,161,90));
    UIManager.put("MenuItem.background", new Color(253,235,212));
    UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(1,1,1,1));
    UIManager.put("MenuItem.font", STUDIO_FONT);
    UIManager.put("MenuItem.selectionBackground", new Color(247,161,90));
  
    UIManager.put("OptionPane.background", new Color(253,235,212));
    
    UIManager.put("Panel.background", new Color(253,235,212));	
  
    UIManager.put("PopupMenu.border", new LineBorder(new Color(247,161,90),1));
    UIManager.put("PopupMenu.background", new Color(253,235,212));
    
    UIManager.put("RadioButton.background", new Color(253,235,212));
    UIManager.put("RadioButton.font", STUDIO_FONT);
  
    UIManager.put("ScrollBar.background", new Color(253,235,212));
    UIManager.put("ScrollBar.darkShadow", new Color(247,161,90));
    UIManager.put("ScrollBar.foreground", new Color(247,161,90));
    UIManager.put("ScrollBar.shadow", new Color(253,235,212));
    UIManager.put("ScrollBar.thumb", new Color(247,161,90));
    UIManager.put("ScrollBar.thumbDarkShadow", new Color(247,161,90));
    UIManager.put("ScrollBar.thumbHighlight", new Color(253,235,212));
    UIManager.put("ScrollBar.thumbShadow", new Color(247,161,90));
    UIManager.put("ScrollBar.track", new Color(253,235,212));
    UIManager.put("ScrollBar.trackHighlight", new Color(247,161,90));
    UIManager.put("ScrollBar.width",new Integer(15));
    
    UIManager.put("ScrollPane.background", new Color(253,235,212)); 
    UIManager.put("ScrollPane.border", new LineBorder(new Color(247,161,90),1));
    UIManager.put("ScrollPane.foreground",new Color(247,161,90));
    
    UIManager.put("Separator.background", new Color(253,235,212));	
    UIManager.put("Separator.foreground", new Color(247,161,90));	
  
    UIManager.put("Slider.background", new Color(253,235,212));
    UIManager.put("Slider.focus", new Color(247,161,90));
    UIManager.put("Slider.foreground", new Color(247,161,90));
    UIManager.put("Slider.highlight", new Color(247,161,90));
    UIManager.put("Slider.shadow", new Color(247,161,90));
    
    UIManager.put("Spinner.arrowButtonSize",new Dimension(15,3));
    UIManager.put("Spinner.background", new Color(253,235,212));
    UIManager.put("Spinner.border", BorderFactory.createEmptyBorder());    
    UIManager.put("Spinner.font", STUDIO_FONT);
    UIManager.put("Spinner.foreground", new Color(247,161,90));
    
    
    UIManager.put("TabbedPane.background", new Color(253,235,212));
    UIManager.put("TabbedPane.contentBorderInsets", new Insets(2,2,1,1));
    UIManager.put("TabbedPane.font", STUDIO_FONT);
    UIManager.put("TabbedPane.focus", new Color(253,235,212));
    UIManager.put("TabbedPane.darkShadow", new Color(247,161,90) );
    UIManager.put("TabbedPane.highlight", new Color(247,161,90)); 
    UIManager.put("TabbedPane.light", new Color(253,235,212));
    UIManager.put("TabbedPane.lightHighlight", new Color(247,161,90));
    UIManager.put("TabbedPane.selected", new Color(247,161,90));	
    UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(2,2,1,1));
    UIManager.put("TabbedPane.selectHighlight", new Color(253,235,212));
    UIManager.put("TabbedPane.shadow", new Color(253,235,212) );
    
    UIManager.put("Table.font", STUDIO_FONT);
    UIManager.put("Table.focusCellHighlightBorder", new LineBorder(new Color(247,161,90),1));
    UIManager.put("Table.gridColor", new Color(247,161,90));
    UIManager.put("Table.selectionBackground", new Color(247,161,90));
    
    UIManager.put("TableHeader.background", new Color(247,161,90));
    UIManager.put("TableHeader.font", STUDIO_FONT);

    UIManager.put("TextArea.font", STUDIO_FONT);
    UIManager.put("TextArea.selectionBackground", new Color(247,161,90));
    
    UIManager.put("TextField.border", new LineBorder(new Color(247,161,90),1));    
    UIManager.put("TextField.darkShadow", new Color(247,161,90));
    UIManager.put("TextField.font", STUDIO_FONT);	
    UIManager.put("TextField.inactiveBackground", new Color(253,235,212));
    UIManager.put("TextField.selectionBackground", new Color(247,161,90));
    UIManager.put("TextField.shadow", new Color(247,161,90));
    
    UIManager.put("TextPane.border", new LineBorder(new Color(247,161,90),1));
    
    UIManager.put("TitledBorder.border", new LineBorder(new Color(247,161,90),1));	
    UIManager.put("TitledBorder.font", STUDIO_FONT);	
    
    UIManager.put("ToggleButton.background", new Color(253,235,212));
    UIManager.put("ToggleButton.border", new SoftBevelBorder(SoftBevelBorder.RAISED));
    UIManager.put("ToggleButton.darkShadow", new Color(247,161,90));
    UIManager.put("ToggleButton.focus", new Color(247,161,90));
    UIManager.put("ToggleButton.select", new Color(247,161,90));
    UIManager.put("ToggleButton.shadow", new Color(247,161,90));
    
    UIManager.put("ToolBar.background", new Color(253,235,212));
    UIManager.put("ToolBar.dockingBackground", new Color(253,235,212));
    UIManager.put("ToolBar.dockingForeground", new Color(247,161,90));
    UIManager.put("ToolBar.floatingBackground", new Color(253,235,212));
    UIManager.put("ToolBar.floatingForeground", new Color(247,161,90));
    
    UIManager.put("ToolTip.background", new Color(253,235,212)); 
    UIManager.put("ToolTip.backgroundInactive", new Color(253,235,212)); 
    UIManager.put("ToolTip.border", new LineBorder(new Color(247,161,90),1));
    UIManager.put("ToolTip.borderInactive", new LineBorder(new Color(247,161,90),1));
    UIManager.put("ToolTip.font", STUDIO_FONT);
  
    UIManager.put("Tree.background", new Color(253,235,212));
    UIManager.put("Tree.closedIcon", getSvgResource().getIcon("folderclosed.png"));
    UIManager.put("Tree.font", STUDIO_FONT);
    UIManager.put("Tree.hash", new Color(247,161,90));
    UIManager.put("Tree.leafIcon", getSvgResource().getIcon("folderclosed.png"));
    UIManager.put("Tree.line", new Color(247,161,90));
    UIManager.put("Tree.openIcon", getSvgResource().getIcon("folderopen.png"));
    UIManager.put("Tree.selectionBackground", new Color(247,161,90));		
    UIManager.put("Tree.selectionBorderColor", new Color(253,235,212));
    UIManager.put("Tree.textBackground", new Color(253,235,212));	
    
    UIManager.put("Viewport.background", new Color(253,235,212));
    
  }
  
  private void createDynamicSvgTabs(){
    String svgTabName=null; 
    Image productImage = null;
    SVGTab dynamicSvgTab=null;
    PrintableAreaBean [] printableAreas=null;
    PrintableAreaBean  printableArea=null;
    HttpBrowser newBrowser=null;
    ObjectInputStream objInStream=null; 
    int width=0;
    int height=0;
    String merchandisePritableAreaTblPk=null;
    try {
        newBrowser = new HttpBrowser(studio, "getPrintableAreaAction.do");
        Properties args = new Properties();
        args.put("merchandiseTblPk",customerInfo.getMerchandiseTblPk());
        objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
        printableAreas=(PrintableAreaBean[])objInStream.readObject();
        for (int i=0; i<printableAreas.length; i++){
          printableArea=printableAreas[i];
          productImage=(new ImageIcon(printableArea.getImage())).getImage();
          merchandisePritableAreaTblPk=printableArea.getMerchandisePrintableAreaTblPk();
          width=printableArea.getWidth();
          height=printableArea.getHeight();
          svgTabName=printableArea.getName();
          dynamicSvgTab= getSvgTabManager().createSVGTab(svgTabName,productImage);
          if (dynamicSvgTab!=null){
            dynamicSvgTab.setMerchandisePrintableAreaTblPk(merchandisePritableAreaTblPk);
            dynamicSvgTab.getScrollPane().getSvgCanvas().newDocument(width,height);
          }
        }
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      try {
        if(objInStream!=null){ 
          objInStream.close();
        }
      }catch (IOException  ioe) {
        ioe.printStackTrace();
      }
    }
  }
  
  /**
   * 
   * @description 
   * @param enableRegularMode
   */
  public void cancelActions(boolean enableRegularMode){
	  	    
		//invokes the "cancelActions" on each module
		Collection classObjects=getSVGClassLoader().getClassObjects();
		SVGClassObject classObject=null;
		
		for(Iterator it=classObjects.iterator(); it.hasNext();){
			try{
				classObject=(SVGClassObject)it.next();
			}catch (Exception ex){classObject=null;}
			if(classObject!=null){
				classObject.cancelActions();
			}
		}
		
		if(getSvgSelection()!=null){
			getSvgSelection().setSelectionEnabled(enableRegularMode);
		}		
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGPopupMenu getPopupManager() {
		return svgClassManager.getPopupManager();
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGClassManager getSVGClassLoader(){
		return svgClassManager;
	}
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public Object getClassObject(String name){
		return svgClassManager.getClassObject(name);
	}
  
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  public SVGShape getShapeObject(String name){
		return svgClassManager.getShapeObject(name);
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public Collection getShapeObjects(){
		return svgClassManager.getShapeObjects();
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGTabManager getSvgTabManager(){
    return svgClassManager.getSVGTabManager(); 
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGCursors getSvgCursors(){
    return svgClassManager.getSvgCursors();
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public MainStatusBar getMainStatusBar(){
    return this.mainStatusBar;
    
  }
  

  /**
   * 
   * @description 
   * @return 
   */
  public MainToolBar getMainToolBar(){
    return this.mainToolBar;
  }
  

  

  /**
   * 
   * @description 
   * @return 
   */
  public SVGToolkit getSvgToolkit() {
    return svgToolkit;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGUndoRedo getSvgUndoRedo() {
    return svgUndoRedo;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGSelection getSvgSelection() {
    return svgSelection;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JTabbedPane getSvgTabSet() {
    return svgTabSet;
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
  public SVGToolBar getSvgToolBar() {
    return svgToolBar;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public Customer getCustomerInfo() {
    return customerInfo;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGResource getSvgResource() {
    return svgResource;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JPanel getPanel4PropertySheet() {
    return panel4PropertySheet;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public JPanel getPanel4Cliparts() {
    return panel4Cliparts;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JPanel getPanel4Images() {
    return panel4Images;
  }


  /**
   * 
   * @description 
   * @param colorChooser
   */
  public void setColorChooser(SVGColorChooser colorChooser) {
    synchronized(this){
      this.colorChooser = colorChooser;
    }
  }


  /**
   * 
   * @description 
   * @return 
   */
  public SVGColorChooser getColorChooser() {
    return colorChooser;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGColorManager getSvgColorManager(){
		return svgClassManager.getColorManager();
	}


  /**
   * 
   * @description 
   * @return 
   */
  public JComponent getDesktop() {
    return desktop;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public JTabbedPane getMainTabSet() {
    return mainTabSet;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JPanel getPanel4Merchandise() {
    return panel4Merchandise;
  }
  
}


  