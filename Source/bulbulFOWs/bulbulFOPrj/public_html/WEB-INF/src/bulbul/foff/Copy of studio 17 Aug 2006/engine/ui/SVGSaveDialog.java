package bulbul.foff.studio.engine.ui;

import bulbul.foff.studio.engine.comm.HttpBrowser;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;


/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGSaveDialog extends JDialog {

  private Studio studio;

  private JPanel panel4This = new JPanel();

  private BorderLayout layout4Panel4This = new BorderLayout();

  private JPanel widgetPanel = new JPanel();

  private JPanel buttonPanel = new JPanel();

  private FlowLayout layout4ButtonPanel = new FlowLayout();

  private JButton btnSave = new JButton();

  private JButton btnCancel = new JButton();

  private GridBagLayout layout4WidgetPanel = new GridBagLayout();

  private JLabel lblCustomerDesignName = new JLabel();

  private JLabel lblCustomerEmailId = new JLabel();

  private JTextField txtCustomerDesignName = new JTextField();

  private JTextField txtCustomerEmailId = new JTextField();

  private String customerDesignName = null;

  private String customerEmailId = null;


  public static final int SAVE_YES = JOptionPane.YES_OPTION;

  public static final int SAVE_NO = JOptionPane.NO_OPTION;

  public static final int SAVE_CANCEL = JOptionPane.CANCEL_OPTION;

  private int saveDialogResult = SAVE_CANCEL;

  /**
   * 
   * @description 
   */
  public SVGSaveDialog(Studio studio) {
    super();
    try {
      this.studio = studio;
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setModal(true);
    setResizable(false);
    setTitle("Save Design");


    int x = (int)(getStudio().getLocationOnScreen().getX() + getStudio().getWidth() / 2 - getSize().getWidth() / 2);
    int y = (int)(getStudio().getLocationOnScreen().getY() + getStudio().getHeight() / 2 - getSize().getHeight() / 2);

    setLocation(x, y);

    layout4ButtonPanel.setAlignment(FlowLayout.RIGHT);
    layout4ButtonPanel.setHgap(4);
    layout4ButtonPanel.setVgap(4);

    panel4This.setLayout(layout4Panel4This);

    widgetPanel.setLayout(layout4WidgetPanel);

    buttonPanel.setLayout(layout4ButtonPanel);

    lblCustomerDesignName.setText("Design Name");

    lblCustomerEmailId.setText("Your Email Id");

    txtCustomerDesignName.setPreferredSize(new Dimension(200, 18));

    txtCustomerEmailId.setPreferredSize(new Dimension(200, 18));

    // Temp code 
    //txtCustomerDesignName.setText("sudheer");
    //txtCustomerEmailId.setText("sudheer@dbsentry.com");
    //

    btnSave.setText("Save");
    btnSave.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    btnSave.setPreferredSize(new Dimension(70, 18));
    btnSave.setMnemonic('s');
    btnSave.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                  if (areEnteredValuesValid()) {
                                    setVisible(false);
                                    checkForExistanceOfDesignName();
                                    if (saveDialogResult == SAVE_NO) {
                                      setVisible(true);
                                      txtCustomerDesignName.requestFocus();
                                    }
                                  }
                                }
                              }
    );

    btnCancel.setText("Cancel");
    btnCancel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    btnCancel.setPreferredSize(new Dimension(70, 18));
    btnCancel.setMnemonic('c');
    btnCancel.addActionListener(new ActionListener() {
                                  public void actionPerformed(ActionEvent evt) {
                                    setVisible(false);
                                  }
                                }
    );

    final AWTEventListener keyListener = new AWTEventListener() {
        public void eventDispatched(AWTEvent e) {
          if (e instanceof KeyEvent && isVisible()) {
            KeyEvent kev = (KeyEvent)e;
            if (kev.getID() == KeyEvent.KEY_PRESSED) {
              if (kev.getKeyCode() == KeyEvent.VK_ENTER) {
                btnSave.doClick();
              } else if (kev.getKeyCode() == KeyEvent.VK_CANCEL) {
                btnCancel.doClick();
              }
            }
          }
        }
      }
    ;

    try {
      studio.getToolkit().addAWTEventListener(keyListener, AWTEvent.KEY_EVENT_MASK);
    } catch (Exception e) {
      ;
    }

    widgetPanel
    .add(lblCustomerDesignName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints
                                                                  .NONE, new Insets(4, 4, 4, 4), 0, 0));
    widgetPanel
    .add(txtCustomerDesignName, new GridBagConstraints(1, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints
                                                                  .WEST, GridBagConstraints.NONE,
                                                                  new Insets(4, 0, 4, 4), 0, 0));
    if (getStudio().getCustomerInfo().isCustomerNew()) {
      widgetPanel
      .add(lblCustomerEmailId, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints
                                                                 .NONE, new Insets(4, 4, 4, 4), 0, 0));
      widgetPanel
      .add(txtCustomerEmailId, new GridBagConstraints(1, 1, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints
                                                                 .WEST, GridBagConstraints.NONE,
                                                                 new Insets(4, 0, 4, 4), 0, 0));
    } else {
      txtCustomerEmailId.setText(getStudio().getCustomerInfo().getCustomerEmailId());
      setCustomerEmailId(getStudio().getCustomerInfo().getCustomerEmailId());
    }
    panel4This.add(widgetPanel, BorderLayout.CENTER);

    buttonPanel.add(btnSave, null);
    buttonPanel.add(btnCancel, null);

    panel4This.add(buttonPanel, BorderLayout.SOUTH);

    this.getContentPane().add(panel4This, BorderLayout.CENTER);

    pack();
  }

  private boolean areEnteredValuesValid() {
    String designNameValue = txtCustomerDesignName.getText();
    String yourEmailIdValue = txtCustomerEmailId.getText();

    if (designNameValue.trim().length() == 0) {
      showErrorMessage("Enter Design Name");
      txtCustomerDesignName.requestFocus();
      return false;
    }

    if (designNameValue.trim().length() < 5) {
      showErrorMessage("Enter Design Name More Than 5 Characters");
      txtCustomerDesignName.requestFocus();
      return false;
    }

    if (getStudio().getCustomerInfo().isCustomerNew()) {
      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
      Matcher m = p.matcher(yourEmailIdValue);
      if (!m.matches()) {
        showErrorMessage("Enter Valid Email Id");
        txtCustomerEmailId.requestFocus();
        return false;
      }
    }

    this.customerDesignName = designNameValue;
    if (getStudio().getCustomerInfo().isCustomerNew()) {
      this.customerEmailId = yourEmailIdValue;
    }

    return true;
  }

  private void checkForExistanceOfDesignName() {
    //Check for Design Name Whether Already Exist or Not
    HttpBrowser newBrowser = null;
    ObjectInputStream objInStream = null;
    try {
      getStudio().getMainStatusBar().setInfo("Connecting .......");
      getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
      newBrowser = new HttpBrowser(getStudio(), "designNameCheckAction.do");
      Properties args = new Properties();
      args.put("customerDesignName", getCustomerDesignName());
      args.put("customerEmailId", getCustomerEmailId());
      boolean designNameExists = false;
      objInStream = new ObjectInputStream(newBrowser.sendPostMessage(args));
      designNameExists = objInStream.readBoolean();
      System.out.println("responseFromServer " + designNameExists);
      if (designNameExists) {

        saveDialogResult =
          JOptionPane.showConfirmDialog(getStudio(), "Design Name Already Exist ! Do You Want To OverWrite",
                                                         "Save Design Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE);

        getStudio().getCustomerInfo().setDesign2OverWrite(saveDialogResult == SAVE_YES);
      } else {
        saveDialogResult = SAVE_YES;
      }
      getStudio().getCustomerInfo().setCustomerDesignName(getCustomerDesignName());
      if (getStudio().getCustomerInfo().isCustomerNew()) {
        getStudio().getCustomerInfo().setCustomerEmailId(getCustomerEmailId());
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      showErrorMessage("Unable To Connect To Server Retry ");
    } finally {
      try {
        if (objInStream != null) {
          objInStream.close();
        }
      } catch (IOException ioe) {
        ;
      } finally {
        getStudio().getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
        getStudio().getMainStatusBar().setInfo("");
      }
    }
  }

  private void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(studio, message, "Save Error Message", JOptionPane.ERROR_MESSAGE);
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  /**
   * 
   * @description 
   * @return 
   */
  public Studio getStudio() {
    return studio;
  }


  /**
   * 
   * @description 
   * @param customerDesignName
   */
  public void setcustomerDesignName(String customerDesignName) {
    this.customerDesignName = customerDesignName;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerDesignName() {
    return customerDesignName;
  }


  /**
   * 
   * @description 
   * @param customerEmailId
   */
  public void setCustomerEmailId(String customerEmailId) {
    this.customerEmailId = customerEmailId;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public String getCustomerEmailId() {
    return customerEmailId;
  }


  public int getSaveDialogResult() {
    return saveDialogResult;
  }
}
