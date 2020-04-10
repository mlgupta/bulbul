package bulbul.foff.studio.engine.general;
import bulbul.foff.studio.engine.ui.SVGApplet;
import bulbul.foff.studio.engine.ui.SVGTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 19-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGSaveNdSaveAs implements SVGClassObject  {
  
  private final static int SAVE=0;
  private final static int SAVE_AS=1;
    
  final private String	idsavendsaveas="SaveNdSaveAs";
  final private String	idsave="Save";
  final private String	idsaveas="SaveAs";
	
  private String labelsave="";
  private String labelsaveas="";
	
  private JButton save=null, saveAs=null;
  
  private SVGApplet studio;  
  /**
   * 
   * @description 
   */
  public SVGSaveNdSaveAs(SVGApplet studio) {
    this.studio=studio;
    save=getStudio().getMainToolBar().getMtbSave();
    saveAs=getStudio().getMainToolBar().getMtbSaveAs();
    //a listener that listens to the changes of the SVGFrames
		final ActionListener svgTabChangedListener=new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				final SVGTab svgTab=getStudio().getSvgTabManager().getCurrentSVGTab();
				
				if(svgTab!=null){
					//enables the menuitems
					save.setEnabled(true);
					saveAs.setEnabled(true);
				}else{
					//disables the menuitems
					save.setEnabled(false);
					saveAs.setEnabled(false);
				}
			}	
		};
		
		//adds the SVGFrame change listener
		getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);
    
    //adds a listener to the menu item
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
          LinkedList svgTabs=getStudio().getSvgTabManager().getSvgTabs();
          if(svgTabs!=null){
            saveNdSaveAs(SAVE, svgTabs);
          }
				}
			}
		);
		
		//adds a listener to the menu item
		saveAs.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
          LinkedList svgTabs=getStudio().getSvgTabManager().getSvgTabs();
          if(svgTabs!=null){
            saveNdSaveAs(SAVE_AS, svgTabs);	
          }
				}
			}
		);	
    
  }


  protected void saveNdSaveAs(int mode, LinkedList svgTabs){
    
    
    String designName=null;
    
    if(designName==null || mode==SAVE_AS){
      designName = JOptionPane.showInputDialog("Please Enter Design Name");
    }else{
    
    }
    
    if (designName!=null){
      SVGTab svgTab;
      for(Iterator it=svgTabs.iterator(); it.hasNext();){
        svgTab=(SVGTab)it.next();
        if (mode==SAVE_AS){
          save(svgTab);   
        }else{
          if(svgTab.isModified()){
            save(svgTab);   
          }  
        }
          
      }
    }
	}
  
  protected void save(SVGTab svgTab){
    final SVGDocument document = svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
    final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
    final Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
    final SVGTab finalSvgTab=svgTab;                
    Runnable runnable=new Runnable(){
      public void run(){
        try {
          svgGenerator.stream(svgRoot, new OutputStreamWriter(System.out,"UTF-8"));
          finalSvgTab.getScrollPane().getSvgCanvas().setSVGDocument(document);
        }
        catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          System.out.println(e);
        }catch (SVGGraphics2DIOException e) {
          e.printStackTrace();
          System.out.println(e);
        }catch (IOException e) {
          e.printStackTrace();
          System.out.println(e);
        }
      }
    };
          
    final RunnableQueue queue=svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
    if(queue.getThread()!=Thread.currentThread()){
      queue.invokeLater(runnable);
    }else{
      runnable.run();
    }  
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getName(){
		return idsavendsaveas;
	}
	
  /**
   * 
   * @description 
   */
	public void cancelActions(){
	}
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGApplet getStudio() {
    return studio;
  }
}