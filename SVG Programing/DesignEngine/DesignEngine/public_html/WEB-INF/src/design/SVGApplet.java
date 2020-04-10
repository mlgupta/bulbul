package design;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class SVGApplet extends JApplet  {
	
  private BorderLayout borderLayout1 = new BorderLayout();
  private JToolBar jToolBar1 = new JToolBar();
  private JMenuBar jMenuBar1 = new JMenuBar();
  
  private JMenu jMenu1 = new JMenu();
  private FlowLayout flowLayout1 = new FlowLayout();
  
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton(); 

  public void init(){
    try {
      jbInit();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
 private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setBounds(new Rectangle(0,0,300,200));
    
    jToolBar1.setLayout(flowLayout1);
    flowLayout1.setAlignment(0);
    flowLayout1.setHgap(1);
    flowLayout1.setVgap(1);
    jButton1.setText("A");
    jButton1.setPreferredSize(new Dimension(24,24));
    jButton1.setBorder(BorderFactory.createEtchedBorder());
    
    jMenu1.setPreferredSize(new Dimension(0,0));
    final JPanel alignPanel = new JPanel();
    alignPanel.setPreferredSize(new Dimension(120,34));
    alignPanel.setBorder(BorderFactory.createEtchedBorder());
    alignPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
    
    JButton left = new JButton("L");
    left.setPreferredSize(new Dimension(24,24));
    left.setBorder(BorderFactory.createEtchedBorder());
    
    JButton right = new JButton("R");
    right.setPreferredSize(new Dimension(24,24));
    right.setBorder(BorderFactory.createEtchedBorder());
    
    JButton top = new JButton("T");
    top.setPreferredSize(new Dimension(24,24));
    top.setBorder(BorderFactory.createEtchedBorder());
    
    final JButton bottom = new JButton("B");
    bottom.setPreferredSize(new Dimension(24,24));
    bottom.setBorder(BorderFactory.createEtchedBorder());
    bottom.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1.setText(bottom.getText()); 
        ActionListener[] oldActionListeners = jButton1.getActionListeners();
        for(int i=0; i<oldActionListeners.length; i++){
          jButton1.removeActionListener(oldActionListeners[i]); 
        }
        ActionListener[] newActionListeners = bottom.getActionListeners();
        for(int i=0; i<newActionListeners.length; i++){
          if (!newActionListeners[i].equals(this)){
            jButton1.addActionListener(newActionListeners[i]);
          }
        }
        jMenu1.setPopupMenuVisible(false);
        System.out.println("Hello");
      }
    });
    
    bottom.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("Hi");
        }  
    });
    alignPanel.add(left);
    alignPanel.add(right);
    alignPanel.add(top);
    alignPanel.add(bottom);
    
    jMenu1.add(alignPanel);
    
    jButton2.setText("V");
    jButton2.setPreferredSize(new Dimension(15,24));
    jButton2.setBorder(BorderFactory.createEtchedBorder());
    jButton2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton2_actionPerformed(e);
        }
      });
      
    jMenuBar1.add(jMenu1);
    jMenuBar1.setBorder(BorderFactory.createEmptyBorder());
    jMenuBar1.setSize(new Dimension(0,25));
    jMenuBar1.setPreferredSize(new Dimension(0,25));
    jMenuBar1.setMaximumSize(new Dimension(0,25));
    jMenuBar1.setMinimumSize(new Dimension(0,25));
    jToolBar1.add(jMenuBar1, null);
    jToolBar1.add(jButton1, null);
    jToolBar1.add(jButton2,null);
    
    this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
  }

 
  private void jButton2_actionPerformed(ActionEvent e) {
    jMenu1.setPopupMenuVisible(true);
  }
}