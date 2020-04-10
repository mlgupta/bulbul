package bulbul.foff.studio.engine.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class Dummy extends JApplet {

  private BorderLayout borderLayout1 = new BorderLayout();

  private JToolBar jToolBar1 = new JToolBar();

  private FlowLayout flowLayout1 = new FlowLayout();

  private JButton jButton1 = new JButton();

  private JButton jButton2 = new JButton();

  private JButton jButton3 = new JButton();

  public Dummy() {
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jToolBar1.setLayout(flowLayout1);
    flowLayout1.setAlignment(0);
    jButton1.setText("Open");
    jButton2.setText("Close");
    jButton3.setText("Exit");
    jToolBar1.add(jButton1, null);
    jToolBar1.add(jButton2, null);
    jToolBar1.add(jButton3, null);
    this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
  }

  public void init() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  static {
    try {
    } catch (Exception e) {
    }
  }
}
