package bulbul.foff.studio.engine.handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import bulbul.foff.studio.common.Constants;
import bulbul.foff.studio.engine.DesignCanvas;
import bulbul.foff.studio.engine.elements.AbstractDesignElement;


public class DesignElementMouseHandler implements MouseListener, MouseMotionListener {
  private boolean dragged = false;

  private Point pressedPoint;

  private void selection(MouseEvent e, boolean holded) {

    try {
      AbstractDesignElement designElement = (AbstractDesignElement)e.getSource();
      DesignCanvas parent = (DesignCanvas)designElement.getParent();
      if (!designElement.isSelected()) {       
        parent.clearSelectedElement(designElement);
        parent.setSelectedElement(designElement);
        designElement.setBorder(AbstractDesignElement.SELECTED_BORDER);
        designElement.accessPropertySheet(true);
      }
      if (holded) {
        parent.setBorder(AbstractDesignElement.SELECTED_BORDER);
      } else {
        parent.setBorder(AbstractDesignElement.UNSELECTED_BORDER);
      }
    } catch (Exception ex) {
      ;
    }

  }

  private void move(MouseEvent e) {
    AbstractDesignElement designElement = (AbstractDesignElement)e.getSource();

    double margin = (Constants.CANVAS_MARGIN * designElement.getScale() / designElement.getLastScale());

    double srcBoundX = margin;
    double srcBoundY = margin;

    double srcBoundW = designElement.getParent().getSize().getWidth() - designElement.getSize().getWidth();
    double srcBoundH = designElement.getParent().getSize().getHeight() - designElement.getSize().getHeight();

    srcBoundW = srcBoundW - margin;
    srcBoundH = srcBoundH - margin;

    double oldLocX = designElement.getLocation().getX();
    double oldLocY = designElement.getLocation().getY();

    double newLocX = oldLocX + (e.getPoint().getX() - pressedPoint.getX());
    double newLocY = oldLocY + (e.getPoint().getY() - pressedPoint.getY());


    if (newLocX < srcBoundX) {
      newLocX = srcBoundX;
    }

    if (newLocY < srcBoundY) {
      newLocY = srcBoundY;
    }

    if (newLocX > srcBoundW) {
      newLocX = srcBoundW;
    }

    if (newLocY > srcBoundH) {
      newLocY = srcBoundH;
    }
    Point location = new Point((int)newLocX, (int)newLocY);
    designElement.setLocation(location);
    designElement.getElementBounds().setLocation(location);

  }

  public void mousePressed(MouseEvent e) {
    dragged = false;
    pressedPoint = e.getPoint();
    selection(e, true);
  }

  public void mouseReleased(MouseEvent e) {
    if (dragged) {
      move(e);      
      //Design Saved flag of the studio is set false if any element is moved
      AbstractDesignElement designElement = (AbstractDesignElement)e.getSource();
      designElement.getStudio().setDesignSaved(false);
    }
    selection(e, false);
    dragged = false;
  }

  public void mouseDragged(MouseEvent e) {
    dragged = true;
    move(e);
  }

  public void mouseClicked(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }

  public void mouseMoved(MouseEvent e) {

  }
}
