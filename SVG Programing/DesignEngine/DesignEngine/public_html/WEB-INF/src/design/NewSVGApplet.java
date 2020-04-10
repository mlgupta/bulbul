package design;

import javax.swing.JApplet;
import org.apache.batik.swing.*;
import org.apache.batik.bridge.*;
import java.io.*;
import java.net.MalformedURLException;
import javax.swing.*;
import java.awt.BorderLayout;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.event.*;
import org.w3c.dom.Node;
import java.awt.Point;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.svg.SVGDocument;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import org.apache.batik.util.RunnableQueue;
import java.awt.*;

public class NewSVGApplet extends JApplet  {
  private BJSVGCanvas svgCanvas=null;
  private JButton btnTranslate=null;
  private JButton btnRepaint=null;
  private GraphicsNode gNode=null;
  private Node node=null;
  private Rectangle rect =null;
  public void init() {
    svgCanvas = new BJSVGCanvas();
    svgCanvas.addMouseListener( new MouseAdapter(){
      public void mousePressed(MouseEvent e){
        node = getSelectedNode(e.getPoint());
        gNode = svgCanvas.getBridgeContext().getGraphicsNode((Element)node);
      }
    }); 
      

    btnTranslate=new JButton("Translate");
    btnRepaint=new JButton("Repaint");
    btnTranslate.addActionListener(new ActionListener(){
      public  void actionPerformed(ActionEvent e){
        translate();
      }
    });
    btnRepaint.addActionListener(new ActionListener(){
      public  void actionPerformed(ActionEvent e){
        System.out.println(" Rect : " + rect);
        if (rect!=null){
          svgCanvas.repaint(rect);
          rect=null;
        }
      }
    });
    try{
      svgCanvas.setURI((new File("c:\\b.svg")).toURL().toString());;
    }catch(MalformedURLException mue){
      mue.printStackTrace();
    }
    getContentPane().setLayout(new BorderLayout());
    
    getContentPane().add(btnTranslate,BorderLayout.NORTH);
    getContentPane().add(svgCanvas,BorderLayout.CENTER);
    getContentPane().add(btnRepaint,BorderLayout.SOUTH);

  }

  private void translate(){
        System.out.println("GNode : " + gNode);
        if (gNode!=null){
          RunnableQueue queue=svgCanvas.getUpdateManager().getUpdateRunnableQueue();
          /*setCanRepaintSelection(false);
          BJSVGCanvas.PaintListener paintListener=null;
          paintListener=thisPaintListner;
          if (paintListener!=null){
            svgCanvas.removePaintListener(paintListener,true);
            thisPaintListner=null;
          }*/
          final int tX=273; 
          final int tY=3;
          final AffineTransform atNew = gNode.getTransform();
          rect=atNew.createTransformedShape(gNode.getOutline()).getBounds();
          Runnable runnable=new Runnable(){                    
            public void run(){              
              if (atNew!=null){
                atNew.preConcatenate(AffineTransform.getTranslateInstance(tX,tY));
                double[] matrix = new double[6]; 
                atNew.getMatrix(matrix);
                String trasformValue= "matrix(" + matrix[0] + ",";
                trasformValue=trasformValue+ matrix[1] + ",";
                trasformValue=trasformValue+ matrix[2] + ",";
                trasformValue=trasformValue+ matrix[3] + ",";
                trasformValue=trasformValue+ matrix[4] + ",";
                trasformValue=trasformValue+ matrix[5] + ")";
                NamedNodeMap attributes=node.getAttributes();
                if(attributes!=null){
                  ((Element)node).setAttributeNS(null,"transform", trasformValue);
                }
                //setCanRepaintSelection(true);
              }
            }
          };
          if(queue.getThread()!=Thread.currentThread()){
            queue.invokeLater(runnable);
          }else{
             runnable.run();

          }
        }

  }
  protected Node getSelectedNode(Point point){
            
      if(point!=null){
                
          BridgeContext ctxt=svgCanvas.getBridgeContext();
          SVGDocument  doc=svgCanvas.getSVGDocument();
                
          if(ctxt!=null && doc!=null){
                    
              Element root=doc.getRootElement();
                    
              if(root!=null){
                        
                  if(root.hasChildNodes()){
                            
                      Node current=root.getLastChild();
                      GraphicsNode gnode=null;
                      AffineTransform af=null;
                      Point2D pt0=point, pt1=null;
                            
                      while(current!=null){
                                
                          pt1=pt0;
                                
                          //gets the graphics node linked with the current node
                          if(current instanceof Element)gnode=ctxt.getGraphicsNode((Element)current);
                          else gnode=null;
                                
                          if(gnode!=null){
                              //the tranform of the current node
                              af=gnode.getTransform();
                                    
                              if(af!=null){
                                        
                                  try{
                                      //puts the pt point in the plan of the untransformed nodes
                                      pt1=af.inverseTransform(point,null);
                                  }catch (Exception e){}
                              }
                                    
                                    
                              if(gnode.contains(pt1))return current;
                              
                          }
                                
                          af=null;
                          gnode=null;
                          current=current.getPreviousSibling();
                      }
                  }
              }
          }
      }
            
    return null;
  }

  private class BJSVGCanvas extends JSVGCanvas {
    public  BJSVGCanvas(){
      super();
      setDocumentState(ALWAYS_DYNAMIC);
    }

    public BridgeContext getBridgeContext(){
      return bridgeContext;
    }
  }
}