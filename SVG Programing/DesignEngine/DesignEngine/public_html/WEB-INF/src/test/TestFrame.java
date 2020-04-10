package test;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class TestFrame extends JFrame  {

  private BorderLayout borderLayout1 = new BorderLayout();
  private JToolBar jToolBar1 = new JToolBar();
  private JMenuBar jMenuBar1 = new JMenuBar();
  
  private JMenu jMenu1 = new JMenu();
  private FlowLayout flowLayout1 = new FlowLayout();
  
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JPanel jPanel1 = new JPanel();
  private JButton jButton3 = new JButton();

  /**
   * 
   * @description 
   */
  public TestFrame() {
    try {
      jbInit();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setBounds(new Rectangle(0,0,300,200));
    jButton3.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED,new Color(253,235,212),new Color(253,235,212),new Color(247,161,90),new Color(247,161,90)));
    jButton3.setBackground(new Color(253,235,212));
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
    jPanel1.setBorder(BorderFactory.createTitledBorder("ABC"));
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
    jButton3.setText("jButton3");
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
    jPanel1.add(jButton3, null);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
  }

 
  private void jButton2_actionPerformed(ActionEvent e) {
    jMenu1.setPopupMenuVisible(true);
  }
  
 
  /*
    
  private JTable myTable ; 
  private Vector dataRow = new Vector();
  private Vector dataColumn = null;
  private JTableHeader jTableHeader1 = new JTableHeader();
  
  private void jbInit() throws Exception {
    
    JComboBox comboBox = new JComboBox();
    comboBox.addItem("Rowing");
    comboBox.addItem("Snowboarding");
    
    
    dataColumn = new Vector();
    
    dataColumn.add("Opacity");
    dataColumn.add(new JSlider(1,100,10));
    dataRow.add(dataColumn);
    
    dataColumn = new Vector();
    dataColumn.add("Line Style");
    dataColumn.add(comboBox);
    dataRow.add(dataColumn);
    
    dataColumn = new Vector();
    dataColumn.add("Line Width");
    dataColumn.add(new JSpinner(new SpinnerNumberModel(10, 0, 10000000, 1)));
    dataRow.add(dataColumn);
    

    String[] colNames= {"Property Name","Property Value"};
    
    myTable=new JTable(new MyTableModel(colNames,dataRow));
    myTable.setAutoCreateColumnsFromModel(false); 
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    myTable.setDefaultRenderer(Object.class, new MyCellRenderer());
    myTable.setDefaultEditor(Object.class, new MyCellEditor());
    myTable.setRowHeight(18);
    
    setSize(400,300); 
    JScrollPane myScrollPane = new JScrollPane(myTable);
    
    this.getContentPane().add(myScrollPane, BorderLayout.CENTER);
  
  }
  
  
  private class MyTableModel extends AbstractTableModel{
    
    private String[] columnNames;
    private Vector data; 
    
    public MyTableModel(String[] columnNames,Vector data){
      this.columnNames=columnNames;
      this.data=data;
    }
    
    public int getRowCount(){
       return dataRow.size();
    }

    public int getColumnCount(){
      return columnNames.length;
    }

    public Object getValueAt(int row, int column){
      return ((Vector)data.get(row)).get(column);
    } 
    
    public String getColumnName(int column){
      return columnNames[column];
    }
    
    public boolean isCellEditable(int row, int column) {
      //Note that the data/cell address is constant,
      //no matter where the cell appears onscreen.
      if (column ==1) {
          return true;
      } else {
          return false;
      }
    }
  }
  
  private class MyCellRenderer extends DefaultTableCellRenderer{
    public MyCellRenderer(){
      super();
    }
    public  void setValue(Object object){
      if (object instanceof JLabel){
        JLabel label = (JLabel)object;
        setText(label.getText());
        setFont(new Font (label.getFont().getName(),0,label.getFont().getSize()));
        setHorizontalAlignment(label.getHorizontalAlignment());
      }else if (object instanceof JTextField){
        JTextField txtFld = (JTextField)object;
        setText(txtFld.getText());
        setFont(new Font (txtFld.getFont().getName(),0,txtFld.getFont().getSize()));
      }else if(object instanceof JComboBox) {
        JComboBox combo =  (JComboBox)object;
        setText(combo.getSelectedItem().toString());
      }else if(object instanceof JSlider) {
        JSlider slider =  (JSlider)object;
        setText(Integer.toString(slider.getValue()));
      }else if(object instanceof JSpinner) {
        JSpinner spinner =  (JSpinner)object;
        setText(spinner.getModel().getValue()+"");
      }else{
        super.setValue(object);
      }
    }
  } 
  
  private class MyCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    public MyCellEditor(){
      super();
    }
    
    public Object getCellEditorValue(){
      return "dddd";
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
      if (value instanceof JComboBox){
        return (JComboBox)value;
      }else if(value instanceof JTextField){
        return (JTextField)value;
      }else if(value instanceof JSlider){
        return (JSlider)value;
      }else if(value instanceof JSpinner){
        return (JSpinner)value;
      }else{
        return null;
      }
    }    
  }
  */
}