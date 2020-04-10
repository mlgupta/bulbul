package bulbul.foff.studio.engine.ui;
import bulbul.foff.studio.common.ColorBean;
import bulbul.foff.studio.common.MerchandiseViewBean;
import bulbul.foff.studio.common.SizeBean;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.merchandise.MerchandiseNode;
import bulbul.foff.studio.engine.merchandise.MerchandiseTreeModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

/**
 * 
 * @description 
 * @version 1.0 02-Nov-2005
 * @author Sudheer V. Pujar
 */
public class Merchandise extends JPanel  {
  
	private Dimension sizeOfPanel4TreeView =new Dimension(245,182);
	private Dimension sizeOfPanel4MerchandiseView =new Dimension(245,196);
  
	private BoxLayout layout4This = new BoxLayout(this,BoxLayout.Y_AXIS);
	private BorderLayout layout4Panel4TreeView = new BorderLayout();
	private BorderLayout layout4Panel4MerchandiseView = new BorderLayout();
  
	private JPanel panel4TreeView = new JPanel();
	private JPanel panel4MerchandiseView = new JPanel();
 
	private JTree treeView4Merchandise; 
  
	private JScrollPane scrollPane4TreeView;
	private JLabel label4MerchandiseView = new JLabel();
  
	private Studio studio;
  private boolean treeInitialized=false;
  
	/**
	 * 
	 * @description 
	 * @param studio
	 */
	public Merchandise(Studio studio) {
		try {
			this.studio=studio;
			jbInit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
  
	/**
	 * 
	 * @description 
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {
		setLayout(layout4This);
    
		TitledBorder MerchandisesBorder= new TitledBorder("Merchandises");
		panel4TreeView.setBorder(MerchandisesBorder);
		panel4TreeView.setLayout(layout4Panel4TreeView);
		panel4TreeView.setPreferredSize(sizeOfPanel4TreeView);
		panel4TreeView.setMaximumSize(sizeOfPanel4TreeView);
		panel4TreeView.setMinimumSize(sizeOfPanel4TreeView);
		panel4TreeView.setSize(sizeOfPanel4TreeView);
   	
		panel4MerchandiseView.setLayout(layout4Panel4MerchandiseView);
		panel4MerchandiseView.setPreferredSize(sizeOfPanel4MerchandiseView);
		panel4MerchandiseView.setMaximumSize(sizeOfPanel4MerchandiseView);
		panel4MerchandiseView.setMinimumSize(sizeOfPanel4MerchandiseView);
		panel4MerchandiseView.setSize(sizeOfPanel4MerchandiseView);
    
		label4MerchandiseView.setHorizontalAlignment(JLabel.CENTER);
		label4MerchandiseView.setText("Click On Merchandise To View Details");    
    
    panel4MerchandiseView.add(label4MerchandiseView,BorderLayout.CENTER);
    
		this.add(panel4TreeView);
		this.add(panel4MerchandiseView);
    
    
	}


	/**
	 * 
	 * @description 
	 */
	public void initTree(){
		if (!isTreeInitialized()){
			treeView4Merchandise= new JTree(new MerchandiseTreeModel(getStudio())) {       
				public String convertValueToText(Object value, 
						boolean selected,
						boolean expanded, 
						boolean leaf, 
						int row,
						boolean hasFocus){
					return ((MerchandiseNode)value).getName();
				} 
			};
			treeView4Merchandise.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			treeView4Merchandise.setShowsRootHandles(true);
			treeView4Merchandise.addTreeSelectionListener(new TreeSelectionListener(){
				public void valueChanged( TreeSelectionEvent e ){
					MerchandiseNode merchandise = (MerchandiseNode)e.getPath().getLastPathComponent();          
					if (merchandise.isCategory()){  
						panel4MerchandiseView.removeAll();
            label4MerchandiseView.setText("Click On Merchandise To View Details");
						panel4MerchandiseView.add(label4MerchandiseView, BorderLayout.CENTER);
            panel4MerchandiseView.repaint();
					}else{
						viewMerchandise(merchandise.getPk());          
					} 
				}
			}); 
			scrollPane4TreeView =new JScrollPane(treeView4Merchandise);
			scrollPane4TreeView.setBorder(BorderFactory.createEmptyBorder());
			panel4TreeView.add(scrollPane4TreeView, BorderLayout.CENTER);
			repaint();
		}
	}
  
	/**
	 * 
	 * @description 
	 * @param merchandiseTblPk
	 */
	private void viewMerchandise(String merchandiseTblPk){
		HttpBrowser newBrowser = null;
		ObjectInputStream objInStream=null; 
    ProductImagePanel imagePanel =null;
    JPanel panel4ImagePanel = new JPanel();
    JPanel viewPanel = new JPanel();
    JPanel detailPanel = new JPanel();
    JPanel colorsAndSizesPanel = new JPanel();
    JPanel colorsPanel = new JPanel();
    JPanel sizesPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    
    JButton btnCustomise = new JButton();
    
    JScrollPane scrollPanel4ColorsPanel = new JScrollPane(colorsPanel);
    JScrollPane scrollPanel4SizesPanel = new JScrollPane(sizesPanel);
    JScrollPane scrollPanel4DetailPanel = new JScrollPane(detailPanel);
    
    JTabbedPane merchandiseTabset = new JTabbedPane();
    
    Dimension sizeOfBtnCustomise = new Dimension(75,24);
    Dimension sizeOfImagePanel = new Dimension(110,110);
    Dimension sizeOfColorPanel = new Dimension(235,72);
    Dimension sizeOfSizePanel = new Dimension(235,57);
      
		//System.out.println("Inside viewMerchandise ");
		try {
			studio.getMainStatusBar().setInfo("Connecting .......");
			studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
			newBrowser = new HttpBrowser(studio, "merchandiseViewAction.do");
			Properties args = new Properties();
			args.put("merchandiseTblPk",merchandiseTblPk);
			objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
			MerchandiseViewBean merchandise=(MerchandiseViewBean)objInStream.readObject();
      objInStream.close();
      if (merchandise!=null) {
        panel4MerchandiseView.removeAll();
  
        Dimension sizeOfColorButton = new Dimension(22,22);
        Dimension sizeOfColorThumb = new Dimension(16,16);
        Dimension sizeOfHalfColorThumb = new Dimension(8,16);
        
        JPanel colorRowPanel = new JPanel();
        JPanel color1 = null;
        JPanel color2 = null;
        
        ButtonGroup colorButtonGroup = new ButtonGroup();
        JToggleButton colorButton = null;
        
        String colorString = "";
        
        detailPanel.setLayout(new BoxLayout(detailPanel,BoxLayout.Y_AXIS));
        colorsPanel.setLayout(new BoxLayout(colorsPanel,BoxLayout.Y_AXIS));
        sizesPanel.setLayout(new BoxLayout(sizesPanel,BoxLayout.Y_AXIS));
        colorRowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
        
        btnCustomise.setText("Customise");
        btnCustomise.setSize(sizeOfBtnCustomise);
        btnCustomise.setPreferredSize(sizeOfBtnCustomise);
        btnCustomise.setMaximumSize(sizeOfBtnCustomise);
        btnCustomise.setMinimumSize(sizeOfBtnCustomise);
        
        scrollPanel4ColorsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel4ColorsPanel.setBorder(new TitledBorder("Colors"));
        scrollPanel4ColorsPanel.setSize(sizeOfColorPanel);
        scrollPanel4ColorsPanel.setPreferredSize(sizeOfColorPanel);
        scrollPanel4ColorsPanel.setMaximumSize(sizeOfColorPanel);
        scrollPanel4ColorsPanel.setMinimumSize(sizeOfColorPanel);
        
        scrollPanel4SizesPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel4SizesPanel.setBorder(new TitledBorder("Sizes"));
        scrollPanel4SizesPanel.setSize(sizeOfSizePanel);
        scrollPanel4SizesPanel.setPreferredSize(sizeOfSizePanel);
        scrollPanel4SizesPanel.setMaximumSize(sizeOfSizePanel);
        scrollPanel4SizesPanel.setMinimumSize(sizeOfSizePanel);
        
        scrollPanel4DetailPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel4DetailPanel.setBorder(BorderFactory.createEmptyBorder());
        
        System.out.println("Merchandise Name" + merchandise.getMerhandiseName());
        System.out.println("Image Byte Length " + merchandise.getImageData().length);
                
        ImageIcon viewImageIcon = new ImageIcon(merchandise.getImageData());
        
        imagePanel = new ProductImagePanel(getStudio(),viewImageIcon.getImage());
        double scaleX=sizeOfImagePanel.getWidth()/viewImageIcon.getIconWidth();
        double scaleY=sizeOfImagePanel.getHeight()/viewImageIcon.getIconHeight();
        
        AffineTransform affineTransform = AffineTransform.getScaleInstance(scaleX,scaleY);
        imagePanel.setAffineTransform(affineTransform);
        panel4ImagePanel.setSize(sizeOfImagePanel);
        panel4ImagePanel.setPreferredSize(sizeOfImagePanel);
        panel4ImagePanel.setMaximumSize(sizeOfImagePanel);
        panel4ImagePanel.setMinimumSize(sizeOfImagePanel);
        panel4ImagePanel.setBorder(BorderFactory.createLineBorder(new Color(247,161,90),1));
        panel4ImagePanel.setLayout(new BorderLayout());
        panel4ImagePanel.add(imagePanel,BorderLayout.CENTER);
        
        viewPanel.setLayout(new FlowLayout());
        viewPanel.add(panel4ImagePanel);

        JPanel namePanel = new JPanel();
        namePanel.setBorder(new TitledBorder("Merchandise Name"));
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel nameLabel = new JLabel(merchandise.getMerhandiseName());
        namePanel.add(nameLabel);
        
        JPanel quantityPanel = new JPanel();
        quantityPanel.setBorder(new TitledBorder("Min. Order Quantity"));
        quantityPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel quantityLabel = new JLabel(merchandise.getMinimumQuantity());
        quantityPanel.add(quantityLabel);
        
        JPanel merchandiseDescriptionPanel = new JPanel();
        merchandiseDescriptionPanel.setBorder(new TitledBorder("Merchandise Description"));
        merchandiseDescriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel merchandiseDescriptionLabel = new JLabel(merchandise.getMerchandiseDescription());
        merchandiseDescriptionPanel.add(merchandiseDescriptionLabel);
        
        JPanel merchandiseCommentPanel = new JPanel();
        merchandiseCommentPanel.setBorder(new TitledBorder("Merchandise Comment"));
        merchandiseCommentPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel merchandiseCommentLabel = new JLabel(merchandise.getMerchandiseComment());
        merchandiseCommentPanel.add(merchandiseCommentLabel);
        
        JPanel materialDescriptionPanel = new JPanel();
        materialDescriptionPanel.setBorder(new TitledBorder("Material Description"));
        materialDescriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel materialDescriptionLabel = new JLabel(merchandise.getMaterialDescription());
        materialDescriptionPanel.add(materialDescriptionLabel);
        
        JPanel deliveryNotePanel = new JPanel();
        deliveryNotePanel.setBorder(new TitledBorder("Deliverty Note"));
        deliveryNotePanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        JLabel deliveryNoteLabel = new JLabel(merchandise.getDeliveryNote());
        deliveryNotePanel.add(deliveryNoteLabel);
        
        detailPanel.add(namePanel);
        detailPanel.add(quantityPanel);
        detailPanel.add(merchandiseDescriptionPanel);
        detailPanel.add(merchandiseCommentPanel);
        detailPanel.add(materialDescriptionPanel);
        detailPanel.add(deliveryNotePanel);
        
        final Hashtable colorSizeTable=merchandise.getColor();
        int index=1;
        for(Iterator it=colorSizeTable.values().iterator();it.hasNext();index++){
          colorButton = new JToggleButton();
          color1 = new JPanel();
          color2 = new JPanel();
          final ColorBean color = (ColorBean) it.next();
          
          final JPanel finalSizesPanel=sizesPanel; 
          colorButton.setLayout(new GridBagLayout());
          colorButton.setSize(sizeOfColorButton);
          colorButton.setPreferredSize(sizeOfColorButton);
          colorButton.setMaximumSize(sizeOfColorButton);
          colorButton.setMinimumSize(sizeOfColorButton);
          colorButton.setBorder(new LineBorder(new Color(247,161,90),1));
          colorButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
              ArrayList sizeBeanArray= ((ColorBean)colorSizeTable.get(color.getMerchandiseColorTblPk())).getSize();
              viewSizes(finalSizesPanel,sizeBeanArray);
            }          
          });
    
          if (color.getColorTwoValue().trim().length()>0){
            colorString=color.getColorOneName() + " / " + color.getColorTwoName();
            color1.setSize(sizeOfHalfColorThumb);
            color1.setPreferredSize(sizeOfHalfColorThumb);
            color1.setMaximumSize(sizeOfHalfColorThumb);
            color1.setMinimumSize(sizeOfHalfColorThumb);
            color1.setBackground(hex2Color(color.getColorOneValue()));
            color2.setSize(sizeOfHalfColorThumb);
            color2.setPreferredSize(sizeOfHalfColorThumb);
            color2.setMaximumSize(sizeOfHalfColorThumb);
            color2.setMinimumSize(sizeOfHalfColorThumb);
            color2.setBackground(hex2Color(color.getColorTwoValue()));
            colorButton.add(color1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
            colorButton.add(color2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));    
          }else{
            colorString=color.getColorOneName();
            color1.setSize(sizeOfColorThumb);
            color1.setPreferredSize(sizeOfColorThumb);
            color1.setMaximumSize(sizeOfColorThumb);
            color1.setMinimumSize(sizeOfColorThumb);
            color1.setBackground(hex2Color(color.getColorOneValue()));
            colorButton.add(color1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
          }
          colorButton.setToolTipText(colorString);
          colorButtonGroup.add(colorButton);
          colorRowPanel.add(colorButton);
          if((index%9)==0){
            colorsPanel.add(colorRowPanel);
            colorRowPanel = new JPanel();
            colorRowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
          }
        }
        
        colorsPanel.add(colorRowPanel);
        
            
        colorsAndSizesPanel.setLayout(new BoxLayout(colorsAndSizesPanel,BoxLayout.Y_AXIS));
        
        colorsAndSizesPanel.add(scrollPanel4ColorsPanel);
        colorsAndSizesPanel.add(scrollPanel4SizesPanel);
        
        merchandiseTabset.add("View",viewPanel);
        merchandiseTabset.add("Description",scrollPanel4DetailPanel);
        merchandiseTabset.add("Colors & Sizes",colorsAndSizesPanel);
            
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnCustomise);
        
        panel4MerchandiseView.add(merchandiseTabset,BorderLayout.CENTER);
        panel4MerchandiseView.add(buttonPanel,BorderLayout.SOUTH);
        
        panel4MerchandiseView.repaint();
      }
      
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(objInStream!=null){ 
					objInStream.close();
				}
			}catch (IOException  ioe) {
				ioe.printStackTrace();
			}finally{
				studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().returnToLastCursor();
				studio.getMainStatusBar().setInfo("");
			}
		}
	}
 
 private void viewSizes(JPanel sizesPanel, ArrayList sizeBeanArray){
  Dimension sizeOfSizeLabel = new Dimension(100,15);
  sizesPanel.removeAll();
  JPanel sizeRowPanel = new JPanel();
  sizeRowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
  for(int i=0;i<sizeBeanArray.size();i++){
    JLabel sizeLabel = new JLabel();
    SizeBean size = (SizeBean)sizeBeanArray.get(i);
    sizeLabel.setText(size.getSizeTypeId()  + " - " + size.getSizeId());
    sizeLabel.setBorder(new LineBorder(new Color(247,161,90),1));
    sizeLabel.setSize(sizeOfSizeLabel);
    sizeLabel.setPreferredSize(sizeOfSizeLabel);
    sizeLabel.setMaximumSize(sizeOfSizeLabel);
    sizeLabel.setMinimumSize(sizeOfSizeLabel);
    sizeRowPanel.add(sizeLabel);
    
    if(((i+1)%2)==0){
      sizesPanel.add(sizeRowPanel);
      sizeRowPanel = new JPanel();
      sizeRowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
    }
  }
  sizesPanel.add(sizeRowPanel);
  panel4MerchandiseView.repaint();
 } 
	/**
	 * 
	 * @return 
	 * @description 
	 */
	public Studio getStudio() {
		return studio;
	}


	/**
	 * 
	 * @description 
	 * @param treeInitialized
	 */
	public void setTreeInitialized(boolean treeInitialized) {
		this.treeInitialized = treeInitialized;
	}


	/**
	 * 
	 * @description 
	 * @return 
	 */
	public boolean isTreeInitialized() {
		return treeInitialized;
	}
  
  
  private static final String HEXCHARS="0123456789ABCDEF";
  
    /**
     * 
     * @description 
     * @return 
     * @param decValue
     */
  private static String hexValue(int decValue){
    return String.valueOf(HEXCHARS.charAt((decValue>>4)&0xf)) + String.valueOf(HEXCHARS.charAt(decValue&0xf));
  }
    /**
     * 
     * @description 
     * @return 
     * @param hexValue
     */
  private static int decValue(String hexValue){
    return Integer.parseInt(hexValue.toUpperCase(),16);
  }
  
    /**
     * 
     * @description 
     * @return 
     * @param decValue
     */
  private static int decFixed(int decValue){
    return Math.min((int)Float.parseFloat(String.valueOf(Math.abs(Math.floor(decValue)))), 255);
  }
    /**
     * 
     * @description 
     * @return 
     * @param hexValue
     */
  private static String hexFixed(String hexValue){
    return hexValue((int)Math.min(Float.parseFloat(String.valueOf(Math.abs(Math.floor(decValue(hexValue))))), 255));
  }
  
    /**
     * 
     * @description 
     * @return 
     * @param hexColorEq like #ffffff
     */
  private static Color hex2Color(String hexColorEq ){
    int red=decValue(hexColorEq.substring(1,3));
    int green=decValue(hexColorEq.substring(3,5));
    int blue=decValue(hexColorEq.substring(5,7));
    //System.out.println("Decimal Color Value are " + red + "," + green + "," + blue);
    //System.out.println("Hex Color Value are " + hexColorEq.substring(1,3) + "," + hexColorEq.substring(3,5) + "," + hexColorEq.substring(5,7));
    return new Color(red,green,blue);
  }
    
}