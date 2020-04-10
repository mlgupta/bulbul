package test;

import org.apache.batik.swing.*;
import org.apache.batik.svggen.*;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.*;
import org.w3c.dom.svg.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;
class TestSVGGen2  {

  public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.red);
        g2d.fill(new Rectangle(10, 10, 100, 100));
  }
  public static void main(String [] args) throws IOException {

    DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    SVGDocument doc = (SVGDocument)impl.createDocument(svgNS, "svg", null);

    SVGGraphics2D g = new SVGGraphics2D(doc);

    // Draw into g. For example:
    // 
    Shape circle = new Ellipse2D.Double(0,0,50,50);
    g.setPaint(Color.red);
    g.fill(circle);
    g.translate(60,0);
    g.setPaint(Color.green);
    g.fill(circle);
    g.translate(60,0);
    g.setPaint(Color.blue);
    g.fill(circle);
    g.setSVGCanvasSize(new Dimension(180,50));

    // The following populates the document root with the 
    // generated SVG content.
    Element root = doc.getDocumentElement();
    g.getRoot(root);

    //TestSVGGen2 test = new TestSVGGen2();
    //test.paint(g);


    // Now, display the document
    JSVGCanvas canvas = new JSVGCanvas();
    JFrame f = new JFrame();
    f.getContentPane().add(canvas);
    canvas.setSVGDocument(doc);
    f.pack();
    f.setVisible(true);
  
  }

}



