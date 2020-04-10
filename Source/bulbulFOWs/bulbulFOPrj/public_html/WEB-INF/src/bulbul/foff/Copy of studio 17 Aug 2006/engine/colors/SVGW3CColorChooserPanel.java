package bulbul.foff.studio.engine.colors;
import bulbul.foff.studio.engine.ui.Studio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * 
 * @description 
 * @version 1.0 dd-Sep-2005
 * @author Sudheer V Pujar
 */
public class SVGW3CColorChooserPanel extends AbstractColorChooserPanel  {
  
    private String label="Safe Colors";
    private String memoryLabel="Recent";

    private Studio studio;
    
  /**
   * 
   * @description 
   * @param studio
   */
  public SVGW3CColorChooserPanel(Studio studio)  {
    this.studio=studio;
  }
  
  /**
   * 
   * @see javax.swing.colorchooser.AbstractColorChooserPanel#buildChooser()
   * @description 
   */
  protected void buildChooser() {
    final Dimension tinnyPanelSize = new Dimension(12,12);
    final Border tinnyPanelBorder = new SoftBevelBorder(SoftBevelBorder.RAISED);
    
    //the panel containing the color and memory panels
    JPanel colorsAndMemoryPanel=new JPanel();
    
    //the panel containing the panels displaying the colors
    JPanel colorsPanel=new JPanel();
    int numberOfColorsPerLine=23;
    
    //the elements for the last colors panel functionnality
    final int memoryNumber=30;
    final int memoryRowNumber=6;
    
    //creating the memory panel
    final JPanel memoryPanel=new JPanel();
    
    //creating the list of the panels that will be contained in the memory panel
    final LinkedList lastColorPanels=new LinkedList();
    
    //creating the list of the lastly selected colors
    final LinkedList lastSelectedColors=new LinkedList();
    
    //the label panel displaying the name and the corresponding rgb values of a color
    final JLabel colorLabel=new JLabel("", JLabel.CENTER);
    
    //the list of the colors
    java.util.List colorsList=studio.getSvgResource().getW3CColors();
    
    colorsPanel.setLayout(new GridLayout((int)(Math.floor(colorsList.size()/numberOfColorsPerLine)+1), numberOfColorsPerLine, 1, 1));
    colorsPanel.setBorder(BorderFactory.createEtchedBorder());
    SVGW3CColor color=null;
    JPanel panel=null;
    
    for(Iterator it=colorsList.iterator(); it.hasNext();){
      color=(SVGW3CColor)it.next();
      
      if(color!=null){
        panel=new JPanel();
        
        //setting the properties of the panel
        panel.setBorder(tinnyPanelBorder);
        panel.setBackground(color);
        panel.setPreferredSize(tinnyPanelSize);
        panel.setToolTipText(color.getStringRepresentation());

        final SVGW3CColor finalColor=color;
        
        //adding a listener to the clicks on the color panels
        panel.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent evt) {
            SVGW3CColor color=null;
            SVGW3CColor selectedColor=finalColor;
            JPanel panel=null;
            
            //sets the new selected color
            getColorSelectionModel().setSelectedColor(selectedColor);
            
            //removes the last color of the last selected colors, if the list is full
            if(lastSelectedColors.size()>0 && lastSelectedColors.size()>=memoryNumber){
              lastSelectedColors.removeLast();
            }
            
            //adds the new selected color to the list
            lastSelectedColors.addFirst(selectedColor);
  
            //for each panel contained in the memory, sets its new color and tooltip
            for(int i=0; i<lastColorPanels.size(); i++){
              panel=(JPanel)lastColorPanels.get(i);

              if(panel!=null){
                if(i<lastSelectedColors.size()){
                  color=(SVGW3CColor)lastSelectedColors.get(i);
                  
                  if(color!=null){
                    panel.setBorder(tinnyPanelBorder);
                    panel.setBackground(color);
                    panel.setToolTipText(color.getStringRepresentation());
                  }
                }else{
                  panel.setBorder(tinnyPanelBorder);
                  panel.setBackground(getParent().getBackground());
                  panel.setToolTipText(null);
                }
              }
            }
            memoryPanel.repaint();
          }

          public void mouseEntered(MouseEvent arg0){
            colorLabel.setText(finalColor.getStringRepresentation());
          }

          public void mouseExited(MouseEvent arg0){
            colorLabel.setText("");
          }
        });
        
        colorsPanel.add(panel);
      }
    }
    
    //filling the memory panel
    memoryPanel.setLayout(new GridLayout(memoryRowNumber, (int)(Math.floor(memoryNumber/memoryRowNumber))+1, 1, 1));
    memoryPanel.setBorder(BorderFactory.createEtchedBorder());
    
    JPanel memoryPallette=null;
    
    for(int i=0; i<memoryNumber; i++){
        
      memoryPallette=new JPanel();
      memoryPallette.setBorder(tinnyPanelBorder);
      memoryPallette.setPreferredSize(tinnyPanelSize);
      memoryPallette.setBackground(getParent().getBackground());
      
      lastColorPanels.add(memoryPallette);
      memoryPanel.add(memoryPallette);
      
      final int finalI=i;
  
      //adding a mouse listener to the panel
      memoryPallette.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent evt) {
          SVGW3CColor color=null;
    
          if(finalI<lastSelectedColors.size()){
            color=(SVGW3CColor)lastSelectedColors.get(finalI);
            if(color!=null){
              getColorSelectionModel().setSelectedColor(color);
            }
          }
        }
    
        public void mouseEntered(MouseEvent arg0){
          SVGW3CColor color=null;
          if(finalI<lastSelectedColors.size()){
            color=(SVGW3CColor)lastSelectedColors.get(finalI);
            if(color!=null){
              colorLabel.setText(color.getStringRepresentation());
            }
          }
        }
    
        public void mouseExited(MouseEvent arg0){
          SVGW3CColor color=null;
          if(finalI<lastSelectedColors.size()){
              color=(SVGW3CColor)lastSelectedColors.get(finalI);
              if(color!=null){
                      colorLabel.setText("");
              }
          }
        }
      });
    }
    
    JPanel container4memoryPanel= new JPanel();
    container4memoryPanel.setLayout(new BorderLayout());
    container4memoryPanel.add(new JLabel(memoryLabel.concat(":")),BorderLayout.NORTH);
    container4memoryPanel.add(memoryPanel,BorderLayout.CENTER);
    
    //adding the two panels
    colorsAndMemoryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    colorsAndMemoryPanel.add(colorsPanel);
    colorsAndMemoryPanel.add(container4memoryPanel);
    
    //adding the colors and memory panel and the color label widget to the color chooser panel
    setLayout(new BorderLayout(10, 10));
    add(colorsAndMemoryPanel, BorderLayout.CENTER);
    
    //the panel containing the label
    JPanel colorLabelPanel=new JPanel();
    colorLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    colorLabelPanel.add(colorLabel);
    colorLabelPanel.setPreferredSize(new Dimension(25, 30));
    colorLabelPanel.setBorder(BorderFactory.createEtchedBorder());
    
    add(colorLabelPanel, BorderLayout.SOUTH);
  }
  
  /**
   * 
   * @see javax.swing.colorchooser.AbstractColorChooserPanel#getDisplayName()
   * @description 
   * @return 
   */
  public String getDisplayName() {
  
      return label;
  }
    
  /**
   * 
   * @see javax.swing.colorchooser.AbstractColorChooserPanel#getLargeDisplayIcon()
   * @description 
   * @return 
   */
  public Icon getLargeDisplayIcon() {
  
      return null;
  }
    
  /**
   * 
   * @see javax.swing.colorchooser.AbstractColorChooserPanel#getSmallDisplayIcon()
   * @description 
   * @return 
   */
  public Icon getSmallDisplayIcon() {
  
      return null;
  }
    
  /**
   * 
   * @see javax.swing.colorchooser.AbstractColorChooserPanel#updateChooser()
   * @description 
   */
  public void updateChooser() {
  
  }
}