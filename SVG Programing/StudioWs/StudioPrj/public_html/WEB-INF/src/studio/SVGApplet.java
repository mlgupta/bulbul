package studio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.net.MalformedURLException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.swing.gvt.InteractorAdapter;

import studio.canvas.SVGCanvas;
import studio.canvas.SVGScrollPane;
import studio.canvas.SVGStatusBar;
public class SVGApplet extends JApplet  {

  private JPanel westPanel = new JPanel();
  private JPanel centerPanel = new JPanel();
  private JTabbedPane studioTab = new JTabbedPane();
  private BorderLayout westPanelLayout = new BorderLayout();
  private BorderLayout centerPanelLayout = new BorderLayout();
  private JPanel componentPanel = new JPanel();
  private JPanel productsPanel = new JPanel();
  private JPanel buttonPanel = new JPanel();
  private JButton btnLoad = new JButton();
  private SVGScrollPane theScrollPane4Canvas = new SVGScrollPane(this);
  private SVGCanvas theSVGCanvas;
  private JPanel southPanel = new JPanel();
  private BorderLayout southPanelLayout = new BorderLayout();
  private SVGStatusBar statusBar = new SVGStatusBar();
  
  public SVGApplet() {
    try {
      jbInit();
      rtInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    westPanel.setMinimumSize(new Dimension(200, 200));
    westPanel.setMaximumSize(new Dimension(300, 300));
    westPanel.setLayout(westPanelLayout);
    westPanel.setSize(new Dimension(100, 391));
    westPanel.setBounds(new Rectangle(0, 0, 200, 391));
    westPanel.setPreferredSize(new Dimension(200, 5));
    centerPanel.setBackground(new Color(66, 101, 212));
    centerPanel.setMaximumSize(new Dimension(800, 800));
    centerPanel.setMinimumSize(new Dimension(700, 700));
    centerPanel.setBounds(new Rectangle(200, 0, 500, 391));
    centerPanel.setLayout(centerPanelLayout);
    studioTab.setMinimumSize(new Dimension(200, 200));
    studioTab.setMaximumSize(new Dimension(200, 32767));
    buttonPanel.setSize(new Dimension(200, 10));
    buttonPanel.setPreferredSize(new Dimension(200, 25));
    btnLoad.setText("Load");
    btnLoad.setPreferredSize(new Dimension(100, 20));
    btnLoad.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnLoad_actionPerformed(e);
        }
      });
    southPanel.setLayout(southPanelLayout);
    buttonPanel.add(btnLoad, null);
    componentPanel.add(buttonPanel, null);
    studioTab.addTab("Component", componentPanel);
    studioTab.addTab("Products", productsPanel);
    westPanel.add(studioTab, BorderLayout.CENTER);
    this.getContentPane().add(westPanel, BorderLayout.WEST);
    centerPanel.add(theScrollPane4Canvas, BorderLayout.CENTER);
    this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    southPanel.add(statusBar, BorderLayout.SOUTH);
    this.getContentPane().add(southPanel, BorderLayout.SOUTH);
  }

  private void rtInit() throws Exception{
    theSVGCanvas=theScrollPane4Canvas.getSVGCanvas();
    statusBar.setPreferredSize(new Dimension(this.getPreferredSize().width,25));
    repaint();
  }
  
  private void btnLoad_actionPerformed(ActionEvent e) {
    try{
      String path="c:\\b.svg";
      path="D:\\Sudheer\\SVG\\batik-1.6\\batik-1.6\\samples\\mines.svg";
      path="C:\\Documents and Settings\\Administrator\\Desktop\\svg\\axe_peterm.svg";
      theSVGCanvas.setURI((new File(path)).toURL().toString());
      
    }catch(MalformedURLException mue){
      mue.printStackTrace();
    }
  }
  public SVGStatusBar getStatusBar(){
    return this.statusBar;
  }  
}