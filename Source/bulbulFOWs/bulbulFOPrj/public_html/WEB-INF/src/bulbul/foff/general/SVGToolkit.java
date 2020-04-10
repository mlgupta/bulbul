package bulbul.foff.general;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGDocument;

import bulbul.foff.studio.resource.StudioResource;


public final class SVGToolkit {

  private StudioResource studioResource = new StudioResource();
  /**
   * @description 
   * @return 
   * @param value
   */
  private static SVGTransformMatrix getTransformMatrix(String value) {
    SVGTransformMatrix matrix = new SVGTransformMatrix(1, 0, 0, 1, 0, 0);
    if (value != null && !value.equals("")) {
      int range = value.indexOf("matrix");
      //computes the double values of the matrix in the transform attribute
      if (range > -1) {
        String subValue = "";
        subValue = value.substring(range, value.length());
        subValue = subValue.substring(0, subValue.indexOf(")") + 1);
        value = value.replaceAll("[" + subValue + "]", "");
        subValue = subValue.substring(subValue.indexOf("("), subValue.length());

        //cleans the string
        value = cleanTransformString(value);
        subValue = subValue.replaceAll("[(]", "");
        subValue = subValue.replaceAll("[)]", "");
        subValue = subValue.concat(",");

        int i = subValue.indexOf(',');
        int j = 0;
        double[] matrixDb = new double[6];

        while (i != -1) {
          try {
            matrixDb[j] = new Double(subValue.substring(0, i)).doubleValue();
          } catch (Exception ex) {
            return new SVGTransformMatrix(1, 0, 0, 1, 0, 0);
          }
          subValue = subValue.substring(subValue.indexOf(',') + 1, subValue.length());
          i = subValue.indexOf(',');
          j++;
        }
        matrix = new SVGTransformMatrix(matrixDb[0], matrixDb[1], matrixDb[2], matrixDb[3], matrixDb[4], matrixDb[5]);
      }
    }
    return matrix;
  }

  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  private  static String cleanTransformString(String value) {
    String newValue = new String(value);
    newValue = newValue.replaceAll("0\\s", "0,");
    newValue = newValue.replaceAll("1\\s", "1,");
    newValue = newValue.replaceAll("2\\s", "2,");
    newValue = newValue.replaceAll("3\\s", "3,");
    newValue = newValue.replaceAll("4\\s", "4,");
    newValue = newValue.replaceAll("5\\s", "5,");
    newValue = newValue.replaceAll("6\\s", "6,");
    newValue = newValue.replaceAll("7\\s", "7,");
    newValue = newValue.replaceAll("8\\s", "8,");
    newValue = newValue.replaceAll("9\\s", "9,");
    newValue = newValue.replaceAll("\\s*[,]\\s*[,]\\s*", ",");
    newValue = newValue.replaceAll("\\s+", "");
    return newValue;
  }

  /**
   * 
   * @description 
   * @param node
   */
  private  void translateFromAttributesToStyle(Node node) {
    if (node != null /*&& node instanceof Element*/) {
      String style = ((Element)node).getAttribute("style");

      if (style == null) {
        style = "";
      }

      //the list of the attributes to be removed and added to the style attribute
      HashSet styleProperties = studioResource.getAttributesToTranslate();
      String name = "";
      String value = "";

      NamedNodeMap attributes = node.getAttributes();
      LinkedList attToBeRemoved = new LinkedList();
      Node attribute = null;

      for (int i = 0; i < attributes.getLength(); i++) {
        attribute = attributes.item(i);

        if (attribute.getNodeName() != null && styleProperties.contains(attribute.getNodeName())) {
          name = attribute.getNodeName();
          value = attribute.getNodeValue();

          if (value != null && !value.equals("") && style.indexOf(name + ":") == -1) {
            //if the attribute is not in the style value, it is added to the style value
            if (style.length() > 0 && !style.endsWith(";")) {
              style = style.concat(";");
            }

            style = style + name + ":" + value + ";";
          }
          attToBeRemoved.add(new String(name));
        }
      }

      //removes the attributes that have been added to the style attribute
      String str = "";

      for (Iterator it = attToBeRemoved.iterator(); it.hasNext(); ) {
        try {
          str = (String)it.next();
        } catch (Exception ex) {
          str = null;
        }

        if (str != null && !str.equals("")) {
          ((Element)node).removeAttribute(str);
        }
      }

      if (style.equals("")) {
        //removes the style attribute
        ((Element)node).removeAttribute("style");
      } else {
        //modifies the style attribute
        ((Element)node).setAttribute("style", style);
      }
      
      attToBeRemoved.clear();
      
    }
  }

  /**
   * 
   * @description 
   * @param node
   */
  private  static void transformToMatrix(Node node) {
    if (node != null) {
      NamedNodeMap attributes = node.getAttributes();
      if (attributes != null) {
        //if the node has the transform atrribute
        Node att = attributes.getNamedItem("transform");
        if (att != null) {
          //gets the value of the transform attribute
          String value = new String(att.getNodeValue());

          if (value != null && !value.equals("")) {
            //converts the transforms contained in the string to a single matrix transform
            SVGTransformMatrix matrix = transformToMatrix(value);

            //if the matrix is not the identity matrix, it is set as the new transform matrix
            if (matrix != null && !matrix.isIdentity()) {
              //if the node has the transform attribute         
              ((Element)node).setAttributeNS(null, "transform", matrix.getMatrixRepresentation());
            }
          }
        }
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  private static SVGTransformMatrix transformToMatrix(String value) {
    //creates the matrix that will replace the transforms
    SVGTransformMatrix matrix = new SVGTransformMatrix(1, 0, 0, 1, 0, 0);

    if (value != null && !value.equals("")) {
      String transformName = "";
      String transformValues = "";

      //cleans the string
      value = cleanTransformString(value);

      AffineTransform af = new AffineTransform();

      //for each transform found in the value of the transform attribute
      while (value.length() != 0 && value.indexOf('(') != -1) {

        //the name of the transform
        transformName = value.substring(0, value.indexOf('('));
        transformValues = value.substring(0, value.indexOf(')'));

        //removes the current transform from the value of the transform attribute
        value = value.substring(transformValues.length() + 1, value.length());

        //the numeric value of the transform
        transformValues = transformValues.substring(transformValues.indexOf('(') + 1, transformValues.length());

        //for each kind of transform, gets the numeric values and concatenates the transform to the matrix
        if (transformName.equals("translate")) {
          double e = 0;
          double f = 0;

          try {
            e = new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
            f =
              new Double(transformValues.substring(transformValues.indexOf(',') + 1, transformValues.length())).doubleValue();
          } catch (Exception ex) {
            e = 0;
            f = 0;
          }

          af.concatenate(AffineTransform.getTranslateInstance(e, f));
        } else if (transformName.equals("scale")) {
          double a = 0;
          double d = 0;

          if (transformValues.indexOf(',') == -1) {
            try {
              a = new Double(transformValues).doubleValue();
              d = a;
            } catch (Exception ex) {
              a = 0;
              d = 0;
            }
          } else {
            try {
              a = new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
              d =
                new Double(transformValues.substring(transformValues.indexOf(',') + 1, transformValues.length())).doubleValue();
            } catch (Exception ex) {
              a = 0;
              d = 0;
            }
          }

          af.concatenate(AffineTransform.getScaleInstance(a, d));
        } else if (transformName.equals("rotate")) {
          double angle = 0;
          double e = 0;
          double f = 0;

          if (transformValues.indexOf(',') == -1) {
            try {
              angle = new Double(transformValues).doubleValue();
            } catch (Exception ex) {
              angle = 0;
            }

            af.concatenate(AffineTransform.getRotateInstance(Math.toRadians(angle)));
          } else {
            try {
              angle = new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
              transformValues = transformValues.substring(transformValues.indexOf(',') + 1, transformValues.length());
              e = new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
              transformValues = transformValues.substring(transformValues.indexOf(',') + 1, transformValues.length());
              f = new Double(transformValues.substring(0, transformValues.indexOf(','))).doubleValue();
            } catch (Exception ex) {
              angle = 0;
              e = 0;
              f = 0;
            }

            af.concatenate(AffineTransform.getTranslateInstance(e, f));
            af.concatenate(AffineTransform.getRotateInstance(Math.toRadians(angle)));
            af.concatenate(AffineTransform.getTranslateInstance(-e, -f));
          }
        } else if (transformName.equals("skewX")) {
          double angle = 0;

          try {
            angle = new Double(transformValues).doubleValue();
          } catch (Exception ex) {
            angle = 0;
          }

          af.concatenate(AffineTransform.getShearInstance(Math.tan(Math.toRadians(angle)), 0));
        } else if (transformName.equals("skewY")) {
          double angle = 0;

          try {
            angle = new Double(transformValues).doubleValue();
          } catch (Exception ex) {
            angle = 0;
          }

          af.concatenate(AffineTransform.getShearInstance(0, Math.tan(Math.toRadians(angle))));
        } else if (transformName.equals("matrix")) {
          double[] m = new double[6];
          int j = 0;
          int i = transformValues.indexOf(',');
          transformValues = transformValues.concat(",");

          while (i != -1) {
            try {
              m[j] = new Double(transformValues.substring(0, i)).doubleValue();
            } catch (Exception ex) {
              ;
            }

            transformValues = transformValues.substring(transformValues.indexOf(',') + 1, transformValues.length());

            i = transformValues.indexOf(',');

            j++;
          }

          af.concatenate(new AffineTransform(m[0], m[1], m[2], m[3], m[4], m[5]));
        } else {
          break;
        }
      }
      matrix.concatenateTransform(af);
    }
    return matrix;
  }

  /**
   * 
   * @description 
   * @param node
   */
  private  void removeTspans(Node node) {
    if (node != null && node.getNodeName().equals("text")) {
      String value = getText(node);
      if (value == null) {
        value = "";
      }

      //removes all the text children from the node
      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) != null &&
            (children.item(i) instanceof Text || children.item(i).getNodeName().equals("tspan"))) {
          node.removeChild(children.item(i));
        }
      }

      children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) != null &&
            (children.item(i) instanceof Text || children.item(i).getNodeName().equals("tspan"))) {
          node.removeChild(children.item(i));
        }
      }

      //adds a #text node
      Document doc = node.getOwnerDocument();
      if (doc != null) {
        Text txt = doc.createTextNode(value);
        node.appendChild(txt);
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  private  String getText(Node node) {
    String value = "";
    if (node != null && (node.getNodeName().equals("text") || node.getNodeName().equals("tspan"))) {
      //for each child of the given node, computes the text it contains and concatenates it to the current value
      for (Node cur = node.getFirstChild(); cur != null; cur = cur.getNextSibling()) {
        if (cur.getNodeName().equals("#text")) {
          value = value + cur.getNodeValue();
        } else if (cur.getNodeName().equals("tspan")) {
          value = value + getText(cur);
        }
      }
    }
    value = normalizeTextNodeValue(value);
    return value;
  }

  /**
   * 
   * @description 
   * @param g
   */
  public  void normalizeGroupNode(Element g) {
    if (g != null && g.getNodeName().equals("g")) {
      SVGTransformMatrix gMatrix = getTransformMatrix(g);
      SVGTransformMatrix matrix = null;
      if (!gMatrix.isIdentity()) {
        for (Node currentNode = g.getFirstChild(); currentNode != null; currentNode = currentNode.getNextSibling()) {
          if (currentNode instanceof Element) {
            //getting, modifying and setting the matrix of this node
            matrix = getTransformMatrix(currentNode);
            matrix.concatenateMatrix(gMatrix);
            setTransformMatrix(currentNode, matrix);
          }
        }
        //setting the matrix of the g element to identity
        g.removeAttribute("transform");
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param value
   */
  public static String normalizeTextNodeValue(String value) {
    String textValue = "";
    if (value != null && !value.equals("")) {
      textValue = new String(value);
      textValue = textValue.replaceAll("\\t+", " ");
      textValue = textValue.replaceAll("\\s+", " ");
      textValue.trim();
      if (!textValue.equals("") && textValue.charAt(0) == ' ') {
        textValue = textValue.substring(1, textValue.length());
      }
      if (!textValue.equals("") && textValue.charAt(textValue.length() - 1) == ' ') {
        textValue = textValue.substring(0, textValue.length() - 1);
      }
    }
    return textValue;
  }

  /**
   * 
   * @description 
   * @return 
   * @param node
   */
  public  static SVGTransformMatrix getTransformMatrix(Node node) {
    if (node != null) {
      NamedNodeMap attributes = node.getAttributes();
      if (attributes != null) {
        //if the node has the transform atrribute
        Node attribute = attributes.getNamedItem("transform");
        if (attribute != null) {
          //gets the value of the transform attribute
          String value = attribute.getNodeValue();
          //creating the matrix transform
          return getTransformMatrix(value);
        }
      }
    }
    //otherwise returns the identity matrix
    return new SVGTransformMatrix(1, 0, 0, 1, 0, 0);
  }

  /**
   * 
   * @description 
   * @param matrix
   * @param node
   */
  public  void setTransformMatrix(Node node, SVGTransformMatrix matrix) {
    if (node != null /*&& node instanceof Element*/ && matrix != null) {
      if (node.getNodeName().equals("g")) {
        SVGTransformMatrix currentNodeMatrix = null;
        //for each child of the g element, gets its matrix, modifies and sets it
        for (Node currentNode = node.getFirstChild(); currentNode != null;
             currentNode = currentNode.getNextSibling()) {
          if (currentNode instanceof Element) {
            currentNodeMatrix = getTransformMatrix(currentNode);
            currentNodeMatrix.concatenateMatrix(matrix);
            setTransformMatrix(currentNode, currentNodeMatrix);
          }
        }
        ((Element)node).removeAttribute("transform");
      } else {
        //if the node has the transform attribute         
        ((Element)node).setAttributeNS(null, "transform", matrix.getMatrixRepresentation());
      }
    }
  }

  /**
   * 
   * @description 
   * @return 
   * @param string
   */
  public  double getPixelledNumber(String string) {
    double pixelledNumber = 0;
    if (string != null && !string.equals("")) {
      string = string.trim();
      if (!Character.isDigit(string.charAt(string.length() - 1))) {
        String unit = string.substring(string.length() - 2, string.length());
        String number = string.substring(0, string.length() - 2);

        try {
          pixelledNumber = Double.parseDouble(number);
        } catch (Exception ex) {
          ;
        }

        if (unit.equals("pt")) {
          pixelledNumber *= 1.25;
        } else if (unit.equals("pc")) {
          pixelledNumber *= 15;
        } else if (unit.equals("mm")) {
          pixelledNumber *= 3.543307;
        } else if (unit.equals("cm")) {
          pixelledNumber *= 35.43307;
        } else if (unit.equals("in")) {
          pixelledNumber *= 90;
        }
      } else {
        try {
          pixelledNumber = Double.parseDouble(string);
        } catch (Exception ex) {
          ;
        }
      }
    }
    return pixelledNumber;
  }

  public  LinkedHashMap convertPathValues(LinkedHashMap map) {
    if (map.size() > 0) {
      //the map that will be returned
      LinkedHashMap map2 = new LinkedHashMap();
      Iterator it2 = null;
      LinkedList list = null, nlist = null;
      Point2D.Double lastPoint = null, point = null, beforeLastPoint = null;
      Double[] dtab = new Double[6];
      int rg = 0, i;
      String command = "";
      char cmd = ' ';
      char lastCmd = ' ';

      //for each command in the map
      for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {

        try {
          command = (String)it.next();
        } catch (Exception ex) {
          return null;
        }

        //gets the list of the Double values
        if (!command.equals("")) {
          try {
            list = (LinkedList)map.get(command);
          } catch (Exception ex) {
            return null;
          }
        }

        if (list != null) {

          cmd = command.charAt(0);
          it2 = list.iterator();

          //according to the command
          if (cmd == 'M' || cmd == 'm' || cmd == 'L' || cmd == 'l') {

            while (it2.hasNext()) {
              //the list of the points of the command
              nlist = new LinkedList();
              try {
                dtab[0] = (Double)it2.next();
                dtab[1] = (Double)it2.next();
              } catch (Exception ex) {
                return null;
              }

              //adds the point created with the two double values to the list
              if (Character.isLowerCase(cmd)) {
                if (lastPoint == null) {
                  lastPoint = new Point2D.Double(0, 0);
                }
                point = new Point2D.Double(lastPoint.x + dtab[0].doubleValue(), lastPoint.y + dtab[1].doubleValue());
              } else {
                point = new Point2D.Double(dtab[0].doubleValue(), dtab[1].doubleValue());
              }

              //sets the value of the lastPoint
              beforeLastPoint = lastPoint;
              lastPoint = point;
              nlist.add(point);
              //adds the command to the list
              map2.put(Character.toUpperCase(cmd) + new Integer(rg++).toString(), nlist);
            }
          } else if (cmd == 'T' || cmd == 't') {
            while (it2.hasNext()) {

              //the list of the points of the command
              nlist = new LinkedList();

              //computing the control point 
              if (lastPoint == null) {
                lastPoint = new Point2D.Double(0, 0);
              }

              //adding the first control point
              if (lastCmd == 'Q' && beforeLastPoint != null) {
                point = new Point2D.Double(2 * lastPoint.x - beforeLastPoint.x, 2 * lastPoint.y - beforeLastPoint.y);
                nlist.add(point);
                beforeLastPoint = lastPoint;
                lastPoint = point;
              } else {
                nlist.add(lastPoint);
                beforeLastPoint = lastPoint;
              }

              //computing the new current point
              try {
                dtab[0] = (Double)it2.next();
                dtab[1] = (Double)it2.next();
              } catch (Exception ex) {
                return null;
              }

              //adds the point created with the two double values to the list
              if (Character.isLowerCase(cmd)) {
                if (lastPoint == null) {
                  lastPoint = new Point2D.Double(0, 0);
                }
                point = new Point2D.Double(lastPoint.x + dtab[0].doubleValue(), lastPoint.y + dtab[1].doubleValue());
              } else {
                point = new Point2D.Double(dtab[0].doubleValue(), dtab[1].doubleValue());
              }

              //sets the value of the lastPoint
              beforeLastPoint = lastPoint;
              lastPoint = point;
              nlist.add(point);

              //adds the command to the list
              cmd = 'Q';
              map2.put(cmd + new Integer(rg++).toString(), nlist);
            }
          } else if (cmd == 'Q' || cmd == 'q') {

            while (it2.hasNext()) {
              //the list of the points of the command
              nlist = new LinkedList();
              try {
                dtab[0] = (Double)it2.next();
                dtab[1] = (Double)it2.next();
                dtab[2] = (Double)it2.next();
                dtab[3] = (Double)it2.next();
              } catch (Exception ex) {
                return null;
              }

              for (i = 0; i < 4; i += 2) {
                //adds the point created with the two double values to the list
                if (Character.isLowerCase(cmd)) {
                  if (lastPoint == null) {
                    lastPoint = new Point2D.Double(0, 0);
                  }
                  point =
                    new Point2D.Double(lastPoint.x + dtab[i].doubleValue(), lastPoint.y + dtab[i + 1].doubleValue());
                } else {
                  point = new Point2D.Double(dtab[i].doubleValue(), dtab[i + 1].doubleValue());
                }

                //sets the value of the lastPoint
                beforeLastPoint = lastPoint;
                lastPoint = point;
                nlist.add(point);
              }
              //adds the command to the list
              cmd = Character.toUpperCase(cmd);
              map2.put(cmd + new Integer(rg++).toString(), nlist);
            }
          } else if (cmd == 'C' || cmd == 'c') {
            while (it2.hasNext()) {
              //the list of the points of the command
              nlist = new LinkedList();
              try {
                dtab[0] = (Double)it2.next();
                dtab[1] = (Double)it2.next();
                dtab[2] = (Double)it2.next();
                dtab[3] = (Double)it2.next();
                dtab[4] = (Double)it2.next();
                dtab[5] = (Double)it2.next();
              } catch (Exception ex) {
                return null;
              }

              for (i = 0; i < 6; i += 2) {
                //adds the point created with the two double values to the list
                if (Character.isLowerCase(cmd)) {
                  if (lastPoint == null) {
                    lastPoint = new Point2D.Double(0, 0);
                  }
                  point =
                    new Point2D.Double(lastPoint.x + dtab[i].doubleValue(), lastPoint.y + dtab[i + 1].doubleValue());
                } else {
                  point = new Point2D.Double(dtab[i].doubleValue(), dtab[i + 1].doubleValue());
                }
                //sets the value of the lastPoint
                beforeLastPoint = lastPoint;
                lastPoint = point;
                nlist.add(point);
              }
              //adds the command to the list
              cmd = Character.toUpperCase(cmd);
              map2.put(cmd + new Integer(rg++).toString(), nlist);
            }
          } else if (cmd == 'S' || cmd == 's') {
            while (it2.hasNext()) {
              //the list of the points of the command
              nlist = new LinkedList();

              //computing the control point 
              if (lastPoint == null) {
                lastPoint = new Point2D.Double(0, 0);
              }

              //adding the first control point
              if (lastCmd == 'Q' && beforeLastPoint != null) {
                point = new Point2D.Double(2 * lastPoint.x - beforeLastPoint.x, 2 * lastPoint.y - beforeLastPoint.y);
                nlist.add(point);
                beforeLastPoint = lastPoint;
                lastPoint = point;
              } else {
                nlist.add(lastPoint);
                beforeLastPoint = lastPoint;
              }

              //computing the two next points
              try {
                dtab[0] = (Double)it2.next();
                dtab[1] = (Double)it2.next();
                dtab[2] = (Double)it2.next();
                dtab[3] = (Double)it2.next();
              } catch (Exception ex) {
                return null;
              }

              for (i = 0; i < 4; i += 2) {
                //adds the point created with the two double values to the list
                if (Character.isLowerCase(cmd)) {
                  if (lastPoint == null) {
                    lastPoint = new Point2D.Double(0, 0);
                  }
                  point =
                    new Point2D.Double(lastPoint.x + dtab[i].doubleValue(), lastPoint.y + dtab[i + 1].doubleValue());
                } else {
                  point = new Point2D.Double(dtab[i].doubleValue(), dtab[i + 1].doubleValue());
                }

                //sets the value of the lastPoint
                beforeLastPoint = lastPoint;
                lastPoint = point;
                nlist.add(point);
              }

              //adds the command to the list
              cmd = 'C';
              map2.put(cmd + new Integer(rg++).toString(), nlist);
            }
          } else if (cmd == 'H' || cmd == 'h') {
            if (list != null && list.size() > 0) {
              while (it2.hasNext()) {
                //the list of the values of the command
                nlist = new LinkedList();
                try {
                  dtab[0] = (Double)it2.next();
                } catch (Exception e) {
                  dtab[0] = null;
                }

                if (dtab[0] != null) {
                  if (Character.isLowerCase(cmd)) {
                    if (lastPoint == null) {
                      lastPoint = new Point2D.Double(0, 0);
                    }
                    point = new Point2D.Double(lastPoint.x + dtab[0].doubleValue(), lastPoint.y);
                  } else {
                    point = new Point2D.Double(dtab[0].doubleValue(), lastPoint.y);
                  }

                  beforeLastPoint = lastPoint;
                  lastPoint = point;
                  nlist.add(point);
                }

                //adds the command to the list
                cmd = 'L';
                map2.put(cmd + new Integer(rg++).toString(), nlist);
              }
            }

          } else if (cmd == 'V' || cmd == 'v') {

            if (list != null && list.size() > 0) {
              while (it2.hasNext()) {
                //the list of the values of the command
                nlist = new LinkedList();
                try {
                  dtab[0] = (Double)it2.next();
                } catch (Exception e) {
                  dtab[0] = null;
                }
                if (dtab[0] != null) {
                  if (Character.isLowerCase(cmd) && lastPoint != null) {
                    if (lastPoint == null) {
                      lastPoint = new Point2D.Double(0, 0);
                    }
                    point = new Point2D.Double(lastPoint.x, lastPoint.y + dtab[0].doubleValue());
                  } else {
                    point = new Point2D.Double(lastPoint.x, dtab[0].doubleValue());
                  }
                  beforeLastPoint = lastPoint;
                  lastPoint = point;
                  nlist.add(point);
                }

                //adds the command to the list
                cmd = 'L';
                map2.put(cmd + new Integer(rg++).toString(), nlist);
              }
            }

          } else if (cmd == 'A' || cmd == 'a') {

            if (list.size() % 7 == 0) {
              while (it2.hasNext()) {
                //the list of the values of the command
                nlist = new LinkedList();

                //adds the values to the list
                nlist.add(it2.next());
                if (it2.hasNext()) {
                  nlist.add(it2.next());
                }

                if (it2.hasNext()) {
                  nlist.add(it2.next());
                }

                if (it2.hasNext()) {
                  dtab[0] = (Double)it2.next();
                  if (dtab[0] != null) {
                    nlist.add(new Integer((int)dtab[0].doubleValue()));
                  }
                }

                if (it2.hasNext()) {
                  dtab[0] = (Double)it2.next();
                  if (dtab[0] != null) {
                    nlist.add(new Integer((int)dtab[0].doubleValue()));
                  }
                }

                //creates the point with the two last double values
                if (it2.hasNext()) {
                  try {
                    dtab[0] = (Double)it2.next();
                    if (it2.hasNext())
                      dtab[1] = (Double)it2.next();
                  } catch (Exception ex) {
                    return null;
                  }

                  if (dtab[0] != null && dtab[1] != null) {
                    if (Character.isLowerCase(cmd)) {
                      if (lastPoint == null) {
                        lastPoint = new Point2D.Double(0, 0);
                      }
                      point =
                        new Point2D.Double(dtab[0].doubleValue() + lastPoint.x, dtab[1].doubleValue() + lastPoint.y);
                    } else {
                      point = new Point2D.Double(dtab[0].doubleValue(), dtab[1].doubleValue());
                    }
                    beforeLastPoint = lastPoint;
                    lastPoint = point;
                    nlist.add(point);
                  }
                }

                //adds the command to the list
                cmd = 'A';
                map2.put(cmd + new Integer(rg++).toString(), nlist);
              }
            }
          } else if (cmd == 'Z' || cmd == 'z') {
            nlist = new LinkedList();
            nlist.add(new Point2D.Double());
            //adds the command to the list
            map2.put(cmd + new Integer(rg++).toString(), nlist);
          } else {
            return null;
          }
          lastCmd = cmd;
        }
      }
      return map2;
    }
    return null;
  }

  public  String getStyleProperty(Element element, String name) {
    String value = "";

    if (element != null && name != null && !name.equals("")) {
      //gets the value of the style attribute
      String styleValue = element.getAttribute("style");
      styleValue = styleValue.replaceAll("\\s*[;]\\s*", ";");
      styleValue = styleValue.replaceAll("\\s*[:]\\s*", ":");

      int rg = styleValue.indexOf(";".concat(name.concat(":")));
      if (rg != -1) {
        rg++;
      }

      if (rg == -1) {
        rg = styleValue.indexOf(name.concat(":"));
        if (rg != 0) {
          rg = -1;
        }
      }

      //if the value of the style attribute contains the property
      if (styleValue != null && !styleValue.equals("") && rg != -1) {
        //computes the value of the property
        value = styleValue.substring(rg + name.length() + 1, styleValue.length());
        rg = value.indexOf(";");
        value = value.substring(0, rg == -1 ? value.length() : rg);
      }
    }
    return value;
  }

  public  void setStyleProperty(Element element, String name, String value) {
    if (element != null && name != null && !name.equals("") && value != null) {

      //gets the value of the style attribute
      String styleValue = element.getAttribute("style");
      styleValue = styleValue.replaceAll("\\s*[;]\\s*", ";");
      styleValue = styleValue.replaceAll("\\s*[:]\\s*", ":");

      int rg = styleValue.indexOf(";".concat(name.concat(":")));

      if (rg != -1) {
        rg++;
      }

      if (rg == -1) {
        rg = styleValue.indexOf(name.concat(":"));
        if (rg != 0)
          rg = -1;
      }

      //if the value contains the property
      if (styleValue != null && !styleValue.equals("") && rg != -1) {

        //removes the property from the style attribute value 
        String pValue = styleValue.substring(rg + name.length() + 1, styleValue.length());
        String bValue = styleValue.substring(0, rg);

        int end = pValue.indexOf(";");

        if (end == -1) {
          end = pValue.length();
        } else {
          end++;
        }

        String eValue = pValue.substring(end, pValue.length());

        //tests if the value of the style attribute, from which the current property has been removed, ends with a coma
        boolean endsWithComa = false;

        if (!eValue.equals("")) {
          if (eValue.charAt(eValue.length() - 1) != ';') {
            endsWithComa = true;
          }
        } else if (!bValue.equals("")) {
          if (bValue.charAt(bValue.length() - 1) != ';') {
            endsWithComa = true;
          }
        }

        //the new vlue of the style attribute
        styleValue = ((bValue.concat(eValue)).concat(endsWithComa ? ";" : "")).concat(name.concat(":" + value + ";"));
      } else {
        if (styleValue.length() > 0 && styleValue.charAt(styleValue.length() - 1) != ';') {
          styleValue = styleValue.concat(";");
        }
        styleValue = styleValue.concat(name.concat((":".concat(value)).concat(";")));
      }

      //sets the value of the style attribute
      if (styleValue != null && !styleValue.equals("")) {
        element.setAttribute("style", styleValue);
      }
    }
  }

  /**
   * 
   * @description 
   * @param node
   */
  public  void normalizeNode(Node node) {
    removeTspans(node);
    transformToMatrix(node);
    translateFromAttributesToStyle(node);
  }

  public  Hashtable getResourcesFromDefs(SVGDocument document, LinkedList resourceTagNames) {
    Hashtable idResources = new Hashtable();
    Element defs = getDefsElement(document);
    if (defs != null && resourceTagNames != null && resourceTagNames.size() > 0) {
      String id = "";
      //for each children of the "defs" element, adds its id to the map
      for (Node current = defs.getFirstChild(); current != null; current = current.getNextSibling()) {
        if (current instanceof Element && resourceTagNames.contains(current.getNodeName())) {
          id = ((Element)current).getAttribute("id");
          if (id != null && !id.equals("")) {
            idResources.put(id, current);
          }
        }
      }
    }
    return idResources;
  }

  public  Element getDefsElement(SVGDocument document) {
    Element defs = null;
    if (document != null) {
      Element root = document.getDocumentElement();
      if (root != null) {
        NodeList defsNodes = document.getElementsByTagName("defs");
        if (defsNodes != null && defsNodes.getLength() > 0) {
          try {
            defs = (Element)defsNodes.item(0);
          } catch (Exception ex) {
            ;
          }
        }
        if (defs == null) {
          //adds a "defs" node to the svg document
          defs = document.createElementNS(null, "defs");
          if (root.getFirstChild() != null) {
            root.insertBefore(defs, root.getFirstChild());
          } else {
            root.appendChild(defs);
          }
        }
      }
    }

    return defs;
  }

  /**
   * 
   * @description 
   * @return 
   * @param baseString
   * @param doc
   */
  public  String getId(SVGDocument doc, String baseString) {
    LinkedList ids = new LinkedList();
    Node current = null;
    String attId = "";

    //adds to the list all the ids found among the children of the root element
    for (NodeIterator it = new NodeIterator(doc.getDocumentElement()); it.hasNext(); ) {
      try {
        current = (Node)it.next();
      } catch (Exception ex) {
        current = null;
      }

      if (current != null && current instanceof Element) {
        attId = ((Element)current).getAttribute("id");
        if (attId != null && !attId.equals("")) {
          ids.add(attId);
        }
      }
    }

    int i = 0;
    //tests for each integer string if it is already an id
    while (ids.contains(baseString.concat(i + ""))) {
      i++;
    }
    return new String(baseString.concat(i + ""));

  }
}

