import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 ***************************************************************
 * Sample program which shows how to replace the RepaintManager
 ***************************************************************
 */
public class RepaintManagerDemo {
    public static void main(String[] args) {
        // Replace the RepaintManager globally....
        RepaintManager.setCurrentManager(new VerboseRepaintManager());

        JFrame f = new JFrame("Verbose Repaint Manager Demo");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Name:");
        label.setName("label");
        JTextField field = new JTextField(20);
        field.setName("field");
        JButton button = new JButton("Submit");
        button.setName("button");
        panel.add(label);
        panel.add(field);
        panel.add(button);

        f.getContentPane().add(panel, BorderLayout.CENTER);
        f.pack();
        f.show();
    }
}

class VerboseRepaintManager extends RepaintManager {

    public synchronized void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
        System.out.println("adding DirtyRegion: "+c.getName()+", "+x+","+y+" "+w+"x"+h);
        super.addDirtyRegion(c,x,y,w,h);
    }

    public void paintDirtyRegions() {
       // Unfortunately most of the RepaintManager state is package
       // private and not accessible from the subclass at the moment,
       // so we can't print more info about what's being painted.
        System.out.println("painting DirtyRegions");
        super.paintDirtyRegions();
    }
}


