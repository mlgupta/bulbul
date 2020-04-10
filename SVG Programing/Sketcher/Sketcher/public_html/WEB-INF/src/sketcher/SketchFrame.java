package sketcher;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.image.ImageProducer;
import java.io.File;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class SketchFrame extends JFrame implements Constants  {
  private Sketcher  theApp;
  private JMenuBar menuBar = new JMenuBar();
  private JToolBar toolBar = new JToolBar();
  
  private Color elementColor = DEFAULT_ELEMENT_COLOR;
  private int elementType = DEFAULT_ELEMENT_TYPE;
   
  
  private FileAction newAction, openAction, closeAction, saveAction, saveAsAction, printAction;
  private TypeAction lineAction, rectangleAction, circleAction, curveAction;  
  private ColorAction redAction, yellowAction, greenAction, blueAction;
  
  private static String imagePath="/images/";
  private static URL imageUrl;
  public SketchFrame(String title, Sketcher theApp) {
      
      imagePath=this.getClass().getResource(imagePath).toString();
      setTitle(title);
      this.theApp=theApp;
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
      JMenu elementMenu = new JMenu("Elements");
      fileMenu.setMnemonic('F');
      elementMenu.setMnemonic('E');
      
      //Construct the file pull down Menu
      addMenuItem(fileMenu,newAction = new FileAction("New","Create new Sketch"),KeyStroke.getKeyStroke('N',Event.CTRL_MASK)); //New
      addMenuItem(fileMenu,openAction = new FileAction("Open","Open existing Sketch"),KeyStroke.getKeyStroke('O',Event.CTRL_MASK));//Open
      addMenuItem(fileMenu,closeAction = new FileAction("Close","Close Sketch"),KeyStroke.getKeyStroke('C',Event.CTRL_MASK));//Close
      addMenuItem(fileMenu,saveAction = new FileAction("Save","Save Sketch"),KeyStroke.getKeyStroke('S',Event.CTRL_MASK));//Save
      addMenuItem(fileMenu,saveAsAction = new FileAction("Save As ...","Save as new Sketch"));//Save As
      fileMenu.addSeparator();
      addMenuItem(fileMenu,printAction = new FileAction("Print","Pring Sketch"),KeyStroke.getKeyStroke('P',Event.CTRL_MASK));//Print
      
      //Construct the Element pull down menu
      addMenuItem(elementMenu,lineAction = new TypeAction("Line",LINE,"Draw Line")); //Line
      addMenuItem(elementMenu,rectangleAction = new TypeAction("Rectangle",RECTANGLE,"Draw Rectangle")); //Rectangle
      addMenuItem(elementMenu,circleAction = new TypeAction("Circle",CIRCLE,"Draw Circle")); //Circle
      addMenuItem(elementMenu,curveAction = new TypeAction("Curve",CURVE,"Draw Curve")); //Curve
      
      elementMenu.addSeparator();
      
      JMenu colorMenu = new JMenu("Color");
      elementMenu.add(colorMenu);
      
      //Construct the Color pull out menu
      addMenuItem(colorMenu,redAction = new ColorAction("Red",Color.red,"Draw in Red")); //Red
      addMenuItem(colorMenu,yellowAction = new ColorAction("Yellow",Color.yellow,"Draw in Yellow")); //Yellow
      addMenuItem(colorMenu,greenAction = new ColorAction("Green",Color.green,"Draw in Green")); //Green
      addMenuItem(colorMenu,blueAction = new ColorAction("Blue",Color.blue,"Draw in blue")); //Blue
      
      menuBar.add(fileMenu);
      menuBar.add(elementMenu);
      
      //All File buttons
      
      addToolBarButton(newAction);
      addToolBarButton(openAction);
      addToolBarButton(saveAction);
      addToolBarButton(printAction);
      
      //All Element Type Buttons
      toolBar.addSeparator();
      addToolBarButton(lineAction);
      addToolBarButton(rectangleAction);
      addToolBarButton(circleAction);
      addToolBarButton(curveAction);
      
      //All Element Color Buttons
      toolBar.addSeparator();
      addToolBarButton(redAction);
      addToolBarButton(yellowAction);
      addToolBarButton(greenAction);
      addToolBarButton(blueAction);
      
      
      toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray),BorderFactory.createEmptyBorder(2,2,4,2)));
      toolBar.setFloatable(false);
      
      getContentPane().add(toolBar, BorderLayout.NORTH);
      
      //Disable Actions
      saveAction.setEnabled(false);
      closeAction.setEnabled(false);
      printAction.setEnabled(false);
  }
  
  
  
  //Handles File menu items
  class FileAction extends AbstractAction{
    public FileAction(String name){
      super(name);
      setIcon(this,name);
    }
    
    public FileAction(String name, String toolTip){
      this(name);
      if (toolTip!=null){
        putValue(SHORT_DESCRIPTION,toolTip);
      }
    }
    
    public void actionPerformed(ActionEvent e){
    
    }
  }
  
  //Handles Element menu items
  class TypeAction extends AbstractAction{
    private int typeID;
    public TypeAction(String name, int typeID){
      super(name);
      this.typeID=typeID;
      setIcon(this,name);
    }
    public TypeAction(String name, int typeID, String toolTip){
      this(name,typeID);
      if (toolTip!=null){
        putValue(SHORT_DESCRIPTION,toolTip);
      }
    }
    
    public void actionPerformed(ActionEvent e){
      elementType = typeID;
    }
  }
  
  //Handles color menu items
  class ColorAction extends AbstractAction{
    private Color color;
    public ColorAction(String name, Color color){
      super(name);
      this.color=color;
      setIcon(this,name);
    } 
    
    public ColorAction(String name, Color color, String toolTip){
      this(name,color);
      if (toolTip!=null){
        putValue(SHORT_DESCRIPTION,toolTip);
      }
    }
    
    public void actionPerformed(ActionEvent e){
      elementColor=color;
      getContentPane().setBackground(color);
    }
  }
  
  private JButton addToolBarButton(Action action){
    JButton button = toolBar.add(action);
    button.setToolTipText((String)action.getValue(action.SHORT_DESCRIPTION));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    return button;
  }

  private void setIcon(Action action, String actionName){
    String iconFileName = imagePath + actionName.toLowerCase() +".gif";
    try{
      imageUrl=new URL(iconFileName);
      action.putValue(Action.SMALL_ICON, new ImageIcon(createImage( ( ImageProducer ) imageUrl.getContent() )));
    
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  private JMenuItem addMenuItem(JMenu menu, Action action){
    JMenuItem item = menu.add(action);
    item.setIcon(null);
    return item;
  }
  
  private JMenuItem addMenuItem(JMenu menu, Action action, KeyStroke keystroke){
    JMenuItem item = addMenuItem(menu,action);
    item.setAccelerator(keystroke);
    return item;
  }
}