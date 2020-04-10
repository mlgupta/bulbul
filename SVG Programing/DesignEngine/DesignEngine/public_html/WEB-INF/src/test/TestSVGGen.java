package test;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import javax.swing.*;
import org.apache.batik.swing.*;
import org.w3c.dom.svg.*;
import java.awt.*;
import org.w3c.dom.*;
import java.awt.geom.*;
public class TestSVGGen {

    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.red);
        g2d.fill(new Rectangle(10, 10, 100, 100));
    }

    public static void main(String [] args) throws IOException {

        // Get a DOMImplementation
        DOMImplementation domImpl =
            SVGDOMImplementation.getDOMImplementation();
         String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        // Create an instance of org.w3c.dom.Document
        SVGDocument document = (SVGDocument)domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator
       
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
/*
        Shape circle = new Ellipse2D.Double(0,0,50,50);
        svgGenerator.setPaint(Color.red);
        svgGenerator.fill(circle);
        svgGenerator.translate(60,0);
        svgGenerator.setPaint(Color.green);
        svgGenerator.fill(circle);
        svgGenerator.translate(60,0);
        svgGenerator.setPaint(Color.blue);
        svgGenerator.fill(circle);
        */
        svgGenerator.setSVGCanvasSize(new Dimension(180,50));

        
        // Ask the test to render into the SVG Graphics2D implementation
        TestSVGGen test = new TestSVGGen();
        test.paint(svgGenerator);

        //Element root = document.getDocumentElement();
        svgGenerator.getRoot(document.getDocumentElement());
        

      JSVGCanvas canvas = new JSVGCanvas();
      JFrame f = new JFrame();
      f.getContentPane().add(canvas);
      canvas.setSVGDocument(document);
      f.pack();
      f.setVisible(true);

        // Finally, stream out SVG to the standard output using UTF-8
        // character to byte encoding
        boolean useCSS = true; // we want to use CSS style attribute
        Writer out = new OutputStreamWriter(System.out, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }
}