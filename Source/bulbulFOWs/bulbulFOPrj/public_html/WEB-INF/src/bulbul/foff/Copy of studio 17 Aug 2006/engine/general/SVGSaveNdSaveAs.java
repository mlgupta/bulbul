package bulbul.foff.studio.engine.general;

import bulbul.foff.studio.common.SaveDesignBean;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.ui.SVGSaveDialog;
import bulbul.foff.studio.engine.ui.SVGTab;
import bulbul.foff.studio.engine.ui.Studio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;

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
public class SVGSaveNdSaveAs implements SVGClassObject {

  private static final int SAVE = 0;

  private static final int SAVE_AS = 1;

  private final String idsavendsaveas = "SaveNdSaveAs";
  //final private String	idsave="Save";
  //final private String	idsaveas="SaveAs";

  //private String labelsave="";
  //private String labelsaveas="";

  private JButton save = null, saveAs = null;

  private Studio studio;

  Hashtable designContentTable = null;

  /**
   * 
   * @description 
   */
  public SVGSaveNdSaveAs(Studio studio) {
    this.studio = studio;
    save = getStudio().getMainToolBar().getMtbSave();
    saveAs = getStudio().getMainToolBar().getMtbSaveAs();
    //a listener that listens to the changes of the SVGFrames
    final ActionListener svgTabChangedListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          LinkedList svgTabs = getStudio().getSvgTabManager().getSvgTabs();
          boolean modified = false;
          SVGTab svgTab = null;
          if (svgTabs != null) {
            for (Iterator it = svgTabs.iterator(); it.hasNext(); ) {
              svgTab = (SVGTab)it.next();
              if ((svgTab != null) && (!modified)) {
                modified = svgTab.isModified();
              }
            }
            //enables the toolItems
            saveAs.setEnabled(modified);
            saveAs.setEnabled(true);
          } else {
            //disables the toolItems
            saveAs.setEnabled(false);
            saveAs.setEnabled(false);
          }
        }
      }
    ;

    //adds the SVGFrame change listener
    getStudio().getSvgTabManager().addSVGTabChangedListener(svgTabChangedListener);

    //adds a listener to the menu item
    save.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent e) {
                               LinkedList svgTabs = getStudio().getSvgTabManager().getSvgTabs();
                               if (svgTabs != null) {
                                 saveNdSaveAs(SAVE, svgTabs);
                               }
                             }
                           }
    );

    //adds a listener to the menu item
    saveAs.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                 LinkedList svgTabs = getStudio().getSvgTabManager().getSvgTabs();
                                 if (svgTabs != null) {
                                   saveNdSaveAs(SAVE_AS, svgTabs);
                                 }
                               }
                             }
    );

  }


  private void saveNdSaveAs(int mode, LinkedList svgTabs) {
    try {
      boolean saveIt = false;
      SVGSaveDialog saveDialog = new SVGSaveDialog(getStudio());
      if (getStudio().getCustomerInfo().isDesignNew() || mode == SAVE_AS) {
        saveDialog.setVisible(true);
        if (saveDialog.getSaveDialogResult() == SVGSaveDialog.SAVE_YES) {
          saveIt = true;
        }
      } else {
        saveIt = true;
      }

      getStudio().getMainStatusBar()
      .setInfo("Saving " + getStudio().getCustomerInfo().getCustomerDesignName() + " .......");
      getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();

      if (saveIt) {
        designContentTable = new Hashtable();
        SVGTab svgTab;
        for (Iterator it = svgTabs.iterator(); it.hasNext(); ) {
          svgTab = (SVGTab)it.next();
          save(svgTab);
        }

        /*
        while(designContentTable.size()!=svgTabs.size()){
          try {
            Thread.sleep(1000);
          }catch (InterruptedException e) {}
        }
        */

        /*
        for(Iterator it=svgTabs.iterator(); it.hasNext();){
          svgTab=(SVGTab)it.next();
          byte[] svgbytes=(byte[])designContentTable.get(svgTab.getName()); 
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          try {
            out.write(svgbytes);
            System.out.println(out.toString());
          }catch (IOException e) {}
        }
        */

        System.out.println("Size of designContentTable is : " + designContentTable.size());
        System.out.println("Ready To Save");

        getStudio().getMainStatusBar()
        .setInfo("Saving " + getStudio().getCustomerInfo().getCustomerDesignName() + " .......");
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();

        //Creatine a Serializable object
        SaveDesignBean saveDesignObject = new SaveDesignBean();
        saveDesignObject.setDesignNew(getStudio().getCustomerInfo().isDesignNew());
        saveDesignObject.setCustomerNew(getStudio().getCustomerInfo().isCustomerNew());
        saveDesignObject.setDesign2OverWrite(getStudio().getCustomerInfo().isDesign2OverWrite());
        saveDesignObject.setCustomerEmailIdTblPk(getStudio().getCustomerInfo().getCustomerEmailIdTblPk());
        saveDesignObject.setCustomerEmailId(getStudio().getCustomerInfo().getCustomerEmailId());
        saveDesignObject.setCustomerDesignTblPk(getStudio().getCustomerInfo().getCustomerDesignTblPk());
        saveDesignObject.setCustomerDesignName(getStudio().getCustomerInfo().getCustomerDesignName());
        saveDesignObject.setMerchandiseTblPk(getStudio().getCustomerInfo().getMerchandiseTblPk());
        saveDesignObject.setMerchandiseColorTblPk(getStudio().getCustomerInfo().getMerchandiseColorTblPk());
        saveDesignObject.setDesignContentTable(designContentTable);
        saveDesignObject.setSaveNdSaveAs(mode);

        //Create Object Stream to send Data to the Server      
        boolean resultFromServer = false;
        int customerDesignTblPk=-1;
        HttpBrowser newBrowser = null;
        ObjectInputStream objInStream = null;
        try {
          newBrowser = new HttpBrowser(getStudio(), "productCustomiseAction.do");
          objInStream = new ObjectInputStream(newBrowser.sendPostMessage(saveDesignObject));
          customerDesignTblPk = objInStream.readInt();
          System.out.println("Response From Product Customise Action " + resultFromServer);
          if(customerDesignTblPk!=-1 ){
            resultFromServer=true;
          }
        } catch (IOException ioe) {
          ioe.printStackTrace();
        } finally {
          try {
            if (objInStream != null) {
              objInStream.close();
            }
          } catch (IOException ioe) {
            ;
          }
        }

        
        getStudio().getCustomerInfo().setCustomerNew(!resultFromServer);
        getStudio().getCustomerInfo().setDesignNew(!resultFromServer);
        getStudio().getCustomerInfo().setCustomerDesignTblPk(String.valueOf(customerDesignTblPk));

      }
    } catch (Exception e) {
      ;
    } finally {
      getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
      getStudio().getMainStatusBar().setInfo("");
    }
  }

  private void save(SVGTab svgTab) {
    final SVGDocument document = svgTab.getScrollPane().getSvgCanvas().getSVGDocument();
    final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
    final Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());
    final SVGTab finalSvgTab = svgTab;
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Runnable runnable = new Runnable() {
        public void run() {
          try {
            svgGenerator.stream(svgRoot, new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));
            finalSvgTab.getScrollPane().getSvgCanvas().setSVGDocument(document);
            designContentTable.put(finalSvgTab.getMerchandisePrintableAreaTblPk(), byteArrayOutputStream.toByteArray());
            finalSvgTab.setModified(false);
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println(e);
          } catch (SVGGraphics2DIOException e) {
            e.printStackTrace();
            System.out.println(e);
          } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
          }
        }
      }
    ;

    final RunnableQueue queue = svgTab.getScrollPane().getSvgCanvas().getUpdateManager().getUpdateRunnableQueue();
    if (queue.getThread() != Thread.currentThread()) {
      queue.invokeLater(runnable);
    } else {
      runnable.run();
    }
  }

  /**
   * 
   * @description 
   * @return 
   */
  public String getName() {
    return idsavendsaveas;
  }

  /**
   * 
   * @description 
   */
  public void cancelActions() {
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Collection getPopupMenuItems() {

    return null;
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }
}
