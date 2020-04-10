package test;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PaintMe extends JFrame  {
  public PaintMe() {
  }

  public static void main(String[] args) {
    PaintMe paintMe = new PaintMe();
    JButton btn = new JButton("Hello") ;
    btn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JButton b = (JButton)e.getSource();
                b.setSelected(true);
                b.repaint();            
            }

            public void mouseReleased(MouseEvent e) {
                JButton b = (JButton)e.getSource();
                b.setSelected(false);
                b.repaint();            
            }
        });
    paintMe.getContentPane().add(btn);
    paintMe.setBounds(0,0,200,250);
    paintMe.setVisible(true);
  }

  public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();
        // diameter
        int d = Math.min(size.width, size.height); 
        int x = (size.width - d)/2;
        int y = (size.height - d)/2;

        // draw circle (color already set to foreground)
        g.fillOval(x, y, d, d);
        g.setColor(Color.red);
        g.drawOval(x, y, d, d);
    }

   
}