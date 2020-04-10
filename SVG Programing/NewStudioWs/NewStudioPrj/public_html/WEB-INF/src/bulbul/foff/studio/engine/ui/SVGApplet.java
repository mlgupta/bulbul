package bulbul.foff.studio.engine.ui;

import bulbul.foff.studio.engine.general.DBConnection;
import bulbul.foff.studio.engine.general.SVGClassObject;
import bulbul.foff.studio.engine.general.SVGCursors;
import bulbul.foff.studio.engine.general.SVGToolkit;
import bulbul.foff.studio.engine.managers.SVGClassManager;
import bulbul.foff.studio.engine.managers.SVGTabManager;
import bulbul.foff.studio.engine.selection.SVGSelection;
import bulbul.foff.studio.engine.shapes.SVGShape;
import bulbul.foff.studio.engine.undoredo.SVGUndoRedo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * 
 * @description 
 * @version 1.0 16-Aug-2005
 * @author Sudheer V. Pujar
 */
public class SVGApplet extends JApplet  {
  
  private BorderLayout layout4Panel4This = new BorderLayout();
  private BorderLayout layout4CenterPanel = new BorderLayout();
  private BorderLayout layout4MainStatusBar = new BorderLayout();
  private BorderLayout layout4MainToolBar = new BorderLayout();
  private BorderLayout layout4SVGToolBar = new BorderLayout();
  private BorderLayout layout4MainTabSet = new BorderLayout();
  private BorderLayout layout4SVGTabSet = new BorderLayout();
  
  private JPanel panel4This = new JPanel();
  private JPanel panel4MainToolBar = new JPanel();
  private JPanel panel4SVGToolBar = new JPanel();
  private JPanel panel4MainTabSet = new JPanel();
  private JPanel panel4MainStatusBar = new JPanel();
  private JPanel panel4SVGTabSet = new JPanel();
  private JPanel centerPanel = new JPanel();
  
  private JTabbedPane svgTabSet = new JTabbedPane();
  private MainStatusBar mainStatusBar;
  private MainToolBar mainToolBar;
  private SVGToolBar svgToolBar;
  private MainTabSet mainTabSet;
  private SVGToolkit svgToolkit;
  private SVGClassManager svgClassManager;
  private SVGUndoRedo  svgUndoRedo;
  private SVGSelection  svgSelection;
  private SVGApplet studio=this;
  
  private String connectionString="";

  /**
   * 
   * @description 
   */
  public void init() {
    try {
      jbInit();
      otherInit();
      
      //creating the toolkit object
      svgToolkit=new SVGToolkit(this);
      
      svgClassManager=new SVGClassManager(this);
      svgClassManager.init();
      
      try{
        svgUndoRedo=(SVGUndoRedo)svgClassManager.getClassObject("UndoRedo");
      }catch (Exception ex){svgUndoRedo=null;}
      
      try{
        svgSelection=(SVGSelection)svgClassManager.getClassObject("Selection");
      }catch (Exception ex){svgSelection=null;}	
      
      createDynamicSvgTabs();
      
      /* Temp code starts*/ 
      
      URL myURL= new URL(getCodeBase().toString().substring(0,getCodeBase().toString().indexOf("applets")) + "sample.do" ); 
      System.out.println(" URL " + myURL);
      URLConnection myCon= myURL.openConnection();
      myCon.setDoOutput(true);
      myCon.setDoInput(true);
      myCon.setUseCaches(false); 
      InputStream in =myCon.getInputStream();
      byte[] bytes = new byte[in.available()]; 
      in.read(bytes);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      out.write(bytes);
      connectionString=out.toString();
      while(connectionString.trim().length()==0){
        System.out.println(new Date());
        connectionString=out.toString().trim();
        Thread.sleep(1000);
      }
      
      System.out.println("connectionString after while : " + connectionString);
      
      DBConnection conn = new DBConnection(this);
      String strSQL ="Select * from customer_email_id_tbl";
      ResultSet rs = conn.executeQuery(strSQL); 
      while (rs.next()){
        System.out.println(rs.getString(2));
      }
      
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
    
    mainToolBar = new MainToolBar(this);
    svgToolBar = new SVGToolBar(this);
    mainStatusBar = new MainStatusBar(this);
    mainTabSet = new MainTabSet(this);
    
    panel4MainToolBar.setLayout(layout4MainToolBar);
    panel4MainToolBar.add(mainToolBar, BorderLayout.CENTER);
    
    panel4SVGToolBar.setLayout(layout4SVGToolBar);
    panel4SVGToolBar.add(svgToolBar, BorderLayout.CENTER);
    
    svgTabSet.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    
    panel4SVGTabSet.setBorder(BorderFactory.createEtchedBorder());
    panel4SVGTabSet.setLayout(layout4SVGTabSet);
    panel4SVGTabSet.add(svgTabSet);
    
    svgTabSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent evt){
        SVGTab svgTab=(SVGTab)((JTabbedPane)evt.getSource()).getSelectedComponent();
        if(	svgTab!=null && getStudio().getSvgTabManager().getCurrentSVGTab()!=svgTab){
          getStudio().getSvgTabManager().setCurrentSVGTab(svgTab.getName());
        }
      }
    });
    
    centerPanel.setLayout(layout4CenterPanel);
    centerPanel.add(panel4SVGTabSet, BorderLayout.CENTER);
    
    panel4MainStatusBar.setLayout(layout4MainStatusBar);
    panel4MainStatusBar.add(mainStatusBar, BorderLayout.CENTER);
    
    panel4MainTabSet.setLayout(layout4MainTabSet);
    panel4MainTabSet.setBorder(BorderFactory.createEtchedBorder());
    panel4MainTabSet.add(mainTabSet, BorderLayout.CENTER);
    
    panel4This.setLayout(layout4Panel4This);
    panel4This.setBorder(BorderFactory.createEtchedBorder());
    
    panel4This.add(panel4MainToolBar, BorderLayout.NORTH);
    panel4This.add(panel4MainStatusBar, BorderLayout.SOUTH);
    panel4This.add(centerPanel, BorderLayout.CENTER);
    panel4This.add(panel4SVGToolBar, BorderLayout.EAST);
    panel4This.add(panel4MainTabSet, BorderLayout.WEST);
    this.getContentPane().add(panel4This,null);
  }

  /**
   * 
   * @description 
   * @throws java.lang.Exception
   */
  private void otherInit()throws Exception{
    svgToolBar.setPreferredSize(new Dimension(30,this.getPreferredSize().height));
    mainToolBar.setPreferredSize(new Dimension(700,56));
    mainStatusBar.setPreferredSize(new Dimension(this.getPreferredSize().width,18));
    mainTabSet.setPreferredSize(new Dimension(240,this.getPreferredSize().height));
    repaint();
  }
  
  private void createDynamicSvgTabs(){
    try {
      String[] svgTabNames= new String[2];
      Image[] productImages = new Image[2];
      String[] svgPaths = new String[2];
      SVGTab dynamicSvgTab=null;
      svgTabNames[0]="Front";
      svgTabNames[1]="Back";
      
      productImages[0]=(new ImageIcon(new URL(getCodeBase(),"images/shirts_front.gif"))).getImage();
      productImages[1]=(new ImageIcon(new URL(getCodeBase(),"images/shirts_back.gif"))).getImage();
      
      URL frontPath=new URL(getCodeBase(),"svg/svgFront.svg");
      URL backtPath=new URL(getCodeBase(),"svg/svgBack.svg");
      
      svgPaths[0]=frontPath.toString();
      svgPaths[1]=backtPath.toString();
      
      //theSVGCanvas.setURI(path.toString());
      for (int i=0; i<svgTabNames.length; i++){
        dynamicSvgTab= getSvgTabManager().createSVGTab(svgTabNames[i],productImages[i]);
        if (dynamicSvgTab!=null){

          //dynamicSvgTab.getScrollPane().getSvgCanvas().newDocument(100,150);
          dynamicSvgTab.getScrollPane().getSvgCanvas().setURI(svgPaths[i]);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
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
   public MainTabSet getMainTabSet(){
    return this.mainTabSet;
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
  public SVGApplet getStudio() {
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
  public String getConnectionString() {
    return connectionString;
  }
  
  
}


  