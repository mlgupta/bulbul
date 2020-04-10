package bulbul.foff.studio.engine.ui;
import bulbul.foff.common.Base64;
import bulbul.foff.common.ContentType;
import bulbul.foff.common.ImageFormat;
import bulbul.foff.studio.common.ImageBean;
import bulbul.foff.studio.engine.comm.HttpBrowser;
import bulbul.foff.studio.engine.shapes.SVGClipart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.URL;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Properties;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.SVGConstants;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

/**
 * 
 * @description 
 * @version 1.0 21-Oct-2005
 * @author Sudheer V Pujar
 */
public class SVGImages extends JPanel{
  
  private String imageFileFilter="\".svg\" or \".png\" or \".jpg\" or \".jpeg\" or \".gif\" files";
   
  private final Dimension buttonSize = new Dimension(48,24);
  private final Dimension sizeOfPanel4ImagePreview = new Dimension(130,75);
  private final Dimension sizeOfPanel4Buttons =new Dimension(245,30);
  private final Dimension sizeOfPanel4ThumbnailView =new Dimension(245,123);
  private final Dimension sizeOfPanel4ImageUpload =new Dimension(245,220);
  private final Dimension sizeOfTxtFile = new Dimension(130,18);
  private final Dimension sizeOfTxtTitle = new Dimension(130,18);
  private final Dimension sizeOfTxaDescription = new Dimension(130,44);
  private final Dimension sizeOfBtnBrowse = new Dimension(30,18);
  private final Dimension sizeOfBtnUpload = new Dimension(68,18);
  private final Dimension sizeOfBtnClear = new Dimension(60,18);
  private final Dimension sizeOfPanel4UploadClearButtons =new Dimension(130,20);
  
  private BoxLayout layout4This = new BoxLayout(this,BoxLayout.Y_AXIS);
  private BorderLayout layout4Panel4ThumbnailView = new BorderLayout();
  private FlowLayout layout4Panel4Buttons = new FlowLayout(FlowLayout.LEFT,1,1);
  private GridBagLayout layout4Panel4ImageUpload = new GridBagLayout();
  
  private JPanel panel4Buttons = new JPanel();
  private JPanel panel4ThumbnailView = new JPanel();
  private JPanel thumbnailView4Image = new JPanel();
  private JPanel panel4ImageUpload = new JPanel();
  private JPanel panel4Preview = new JPanel();
  private JPanel panel4UploadClearButtons = new JPanel();

  private JScrollPane scrollPane4ThumbnailView;
  private JScrollPane scrollPane4TxaDescritpion;
  
  private JLabel label4ThumbnailView = new JLabel();  
  private JLabel label4File = new JLabel();
  private JLabel label4Title = new JLabel();
  private JLabel label4Description = new JLabel();
  private JLabel label4Preview = new JLabel();
  
  private JButton btnUpload = new JButton();
  private JButton btnClear = new JButton();
  private JButton btnBrowse = new JButton();
  private JButton btnAll = new JButton();
  private JButton btnJpeg = new JButton();
  private JButton btnGif = new JButton();
  private JButton btnPng = new JButton();
  private JButton btnSvg = new JButton();
  
  private JTextField txtFile = new JTextField();
  private JTextField txtTitle = new JTextField();
  
  private JTextArea txaDescription = new JTextArea();
  
  private JFileChooser imageFileChooser=new JFileChooser();
  
  private Studio studio;
  
  TitledBorder viewBorder= new TitledBorder("View");
  
  /**
   * 
   * @description 
   */
  public SVGImages(Studio studio) {
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
    
    btnAll.setText("All");
    btnAll.setSize(buttonSize);
    btnAll.setPreferredSize(buttonSize);
    btnAll.setMaximumSize(buttonSize);
    btnAll.setMinimumSize(buttonSize);
    btnAll.setToolTipText("Click To See All Images");
    
    btnAll.setEnabled(false);
    btnAll.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Thread listImagesThread=new Thread(){
          public void run(){
            listImages(ImageFormat.ALL.toString());
          }
        };
        listImagesThread.start();
      }
    });
    
    btnJpeg.setText(".jpeg");
    btnJpeg.setSize(buttonSize);
    btnJpeg.setPreferredSize(buttonSize);
    btnJpeg.setMaximumSize(buttonSize);
    btnJpeg.setMinimumSize(buttonSize);
    btnJpeg.setToolTipText("Click To See .jpeg/jpg Images");
    btnJpeg.setEnabled(false);
    btnJpeg.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Thread listImagesThread=new Thread(){
          public void run(){
            listImages(ImageFormat.JPG.toString());      
          }
        };
        listImagesThread.start();
      }
    });
    
    btnGif.setText(".gif");
    btnGif.setSize(buttonSize);
    btnGif.setPreferredSize(buttonSize);
    btnGif.setMaximumSize(buttonSize);
    btnGif.setMinimumSize(buttonSize);
    btnGif.setToolTipText("Click To See .gif Images");
    btnGif.setEnabled(false);
    btnGif.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Thread listImagesThread=new Thread(){
          public void run(){
            listImages(ImageFormat.GIF.toString());
          }
        };
        listImagesThread.start();
      }
    });
    
    btnPng.setText(".png");
    btnPng.setSize(buttonSize);
    btnPng.setPreferredSize(buttonSize);
    btnPng.setMaximumSize(buttonSize);
    btnPng.setMinimumSize(buttonSize);
    btnPng.setToolTipText("Click To See .png Images");
    btnPng.setEnabled(false);
    btnPng.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Thread listImagesThread=new Thread(){
          public void run(){
            listImages(ImageFormat.PNG.toString());
          }
        };
        listImagesThread.start();
      }
    });
    
    btnSvg.setText(".svg");
    btnSvg.setSize(buttonSize);
    btnSvg.setPreferredSize(buttonSize);
    btnSvg.setMaximumSize(buttonSize);
    btnSvg.setMinimumSize(buttonSize);
    btnSvg.setToolTipText("Click To See .svg Images");
    btnSvg.setEnabled(false);
    btnSvg.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        Thread listImagesThread=new Thread(){
          public void run(){
            listImages(ImageFormat.SVG.toString());
          }
        };
        listImagesThread.start();
      }
    });

    panel4Buttons.setLayout(layout4Panel4Buttons);
    panel4Buttons.setSize(sizeOfPanel4Buttons);
    panel4Buttons.setPreferredSize(sizeOfPanel4Buttons);
    panel4Buttons.setMaximumSize(sizeOfPanel4Buttons);
    panel4Buttons.setMinimumSize(sizeOfPanel4Buttons);
    
    panel4Buttons.add(btnAll,null);
    panel4Buttons.add(btnJpeg,null);
    panel4Buttons.add(btnGif,null);
    panel4Buttons.add(btnPng,null);
    panel4Buttons.add(btnSvg,null);
    
    panel4ThumbnailView.setLayout(layout4Panel4ThumbnailView);
    panel4ThumbnailView.setBorder(viewBorder);
    panel4ThumbnailView.setPreferredSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setMaximumSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setMinimumSize(sizeOfPanel4ThumbnailView);
    panel4ThumbnailView.setSize(sizeOfPanel4ThumbnailView);
    
    label4ThumbnailView.setHorizontalAlignment(JLabel.CENTER);
    label4ThumbnailView.setText("Click On Above Buttons To View Images");    
    
    thumbnailView4Image.setLayout(new BorderLayout());
    thumbnailView4Image.setBorder(BorderFactory.createEmptyBorder());
    thumbnailView4Image.add(label4ThumbnailView, BorderLayout.CENTER);
    
    scrollPane4ThumbnailView =new JScrollPane(thumbnailView4Image);
    scrollPane4ThumbnailView.setBorder(BorderFactory.createEmptyBorder());
    panel4ThumbnailView.add(scrollPane4ThumbnailView, BorderLayout.CENTER);
    
    TitledBorder imageUploadBorder= new TitledBorder("Insert Images");
    
    if(getStudio().getCustomerInfo().isCustomerNew()){
      sizeOfPanel4ImageUpload.height=160;
    }
    
    panel4ImageUpload.setLayout(layout4Panel4ImageUpload);
    panel4ImageUpload.setBorder(imageUploadBorder);
    panel4ImageUpload.setSize(sizeOfPanel4ImageUpload);
    panel4ImageUpload.setPreferredSize(sizeOfPanel4ImageUpload);
    panel4ImageUpload.setMaximumSize(sizeOfPanel4ImageUpload);
    panel4ImageUpload.setMinimumSize(sizeOfPanel4ImageUpload);
    
    label4File.setText("File");
    
    label4Title.setText("Title");
    
    label4Description.setText("Description");
    
    txtFile.setSize(sizeOfTxtFile);
    txtFile.setPreferredSize(sizeOfTxtFile);
    txtFile.setMaximumSize(sizeOfTxtFile);
    txtFile.setMinimumSize(sizeOfTxtFile);
    txtFile.setEditable(false);
    txtFile.setToolTipText("Click on the Button On the Left to Choose Image File");
    
    txtTitle.setSize(sizeOfTxtTitle);
    txtTitle.setPreferredSize(sizeOfTxtTitle);
    txtTitle.setMaximumSize(sizeOfTxtTitle);
    txtTitle.setMinimumSize(sizeOfTxtTitle);
    txtTitle.setToolTipText("Enter Title For The Image");
    
    txaDescription.setBorder(BorderFactory.createEmptyBorder());
    txaDescription.setToolTipText("Enter Description  For The Image");
    txaDescription.setLineWrap(true);
    txaDescription.setWrapStyleWord(true);

    scrollPane4TxaDescritpion= new JScrollPane(txaDescription);
    scrollPane4TxaDescritpion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane4TxaDescritpion.setSize(sizeOfTxaDescription);
    scrollPane4TxaDescritpion.setPreferredSize(sizeOfTxaDescription);
    scrollPane4TxaDescritpion.setMaximumSize(sizeOfTxaDescription);
    scrollPane4TxaDescritpion.setMinimumSize(sizeOfTxaDescription);
    
    
    imageFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
      public boolean accept(File imageFile) {
        String fileName=imageFile.getName();
        fileName=fileName.toLowerCase();
        return (imageFile.isDirectory() || fileName.endsWith(".svg") 
              || fileName.endsWith(".png") || fileName.endsWith(".jpg") 
              || fileName.endsWith(".jpeg") || fileName.endsWith(".gif"));
          
      }
  
      public String getDescription(){
        return imageFileFilter;
      }
		});
		imageFileChooser.setMultiSelectionEnabled(false); 
    
    btnBrowse.setText("...");
    btnBrowse.setSize(sizeOfBtnBrowse);
    btnBrowse.setPreferredSize(sizeOfBtnBrowse);
    btnBrowse.setMaximumSize(sizeOfBtnBrowse);
    btnBrowse.setMinimumSize(sizeOfBtnBrowse);
    btnBrowse.setToolTipText("Click to Choose Image File");
    btnBrowse.setEnabled(false);
    btnBrowse.addActionListener(new ActionListener(){
      public  void actionPerformed(ActionEvent event){
        int returnVal=imageFileChooser.showOpenDialog(getStudio());  
        if(returnVal==JFileChooser.APPROVE_OPTION) {
          panel4Preview.removeAll();
          File selectedFile=imageFileChooser.getSelectedFile();
          String fileName=selectedFile.getName().toLowerCase();
          txtFile.setText(selectedFile.getPath());
          txtFile.setToolTipText(selectedFile.getPath());
          if(fileName.endsWith(".svg")){
            try {
              
              final SVGIconCanvas previewSvgIconCanvas = new SVGIconCanvas(getStudio());
              final SVGClipart svgClipart = (SVGClipart)getStudio().getShapeObject("clipart");
              svgClipart.addClipartAction(previewSvgIconCanvas);
              previewSvgIconCanvas.setPreferredSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setMaximumSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setMinimumSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.init();
              
              final MouseAdapter mouseAdapter=new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  previewSvgIconCanvas.setSelected(true);
                }
              };
          
              previewSvgIconCanvas.addMouseListener(mouseAdapter);
              previewSvgIconCanvas.setURI(selectedFile.toURL().toString());
              panel4Preview.add(previewSvgIconCanvas,BorderLayout.CENTER);
              
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
          }else if(fileName.endsWith(".png") || fileName.endsWith(".jpg") 
              || fileName.endsWith(".jpeg") || fileName.endsWith(".gif")) {
            
            try {
              ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
              double imageWidth=imageIcon.getIconWidth();
              double imageHeight=imageIcon.getIconHeight();

              FileInputStream fis = new FileInputStream(selectedFile);
              String contentType=null;
              byte[] content = null;
              
              if(fileName.endsWith(".gif")){
                contentType=ContentType.GIF.toString() ;
              }else if (fileName.endsWith(".png")){
                contentType=ContentType.PNG.toString() ;
              }else if (fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")){
                contentType=ContentType.JPG.toString() ;
              }
              
              if(fileName.endsWith(".gif")){
                ByteArrayOutputStream pngBaos = null;
                BufferedImage bufferedImage =null;
                try {
                  bufferedImage = ImageIO.read(fis);
                  if(bufferedImage!=null){
                    pngBaos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage,ImageFormat.PNG.toString(),pngBaos);
                    bufferedImage.flush();
                    content=pngBaos.toByteArray();
                    pngBaos.flush();
                  }
                }catch (Exception ex) {
                  ex.printStackTrace();
                }
                finally {
                  if (pngBaos!=null){
                    pngBaos.close();  
                  }
                }
                fis.close();
              }else{
                content = new byte[(int)selectedFile.length()];
                fis.read(content);
                fis.close();
              }
              
              DecimalFormatSymbols symbols=new DecimalFormatSymbols();
              symbols.setDecimalSeparator('.');
              DecimalFormat format=new DecimalFormat("######.#",symbols);
              
              
              DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();    
              SVGDocument document = (SVGDocument)domImpl.createDocument(SVGConstants.SVG_NAMESPACE_URI,
                                                      SVGConstants.SVG_SVG_TAG, null);
            
              SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
                
              Element svgRoot = svgGenerator.getRoot(document.getDocumentElement());   
              svgRoot.setAttributeNS(null, SVGConstants.SVG_VIEW_BOX_ATTRIBUTE,
                                     "" + format.format(0) + " " + format.format(0) + " " +
                                     format.format(imageWidth) + " " + format.format(imageHeight));
              svgRoot.setAttributeNS(null, SVGConstants.SVG_WIDTH_ATTRIBUTE, format.format(imageWidth));
              svgRoot.setAttributeNS(null, SVGConstants.SVG_HEIGHT_ATTRIBUTE, format.format(imageHeight));
                                       
              final Element image = document.createElementNS(document.getDocumentElement().getNamespaceURI(), "image");
          
              image.setAttributeNS("http://www.w3.org/1999/xlink","xlink:href", "data:" + contentType + ";base64," + Base64.encodeBytes(content));
              image.setAttributeNS(null,"x", format.format(0));
              image.setAttributeNS(null,"y", format.format(0));
              image.setAttributeNS(null,"width", format.format(imageWidth==0?1:imageWidth));
              image.setAttributeNS(null,"height", format.format(imageHeight==0?1:imageHeight));
               
              svgRoot.appendChild(image);
              
              final SVGIconCanvas previewSvgIconCanvas = new SVGIconCanvas(getStudio());
              final SVGClipart svgClipart = (SVGClipart)getStudio().getShapeObject("clipart");
              svgClipart.addClipartAction(previewSvgIconCanvas);
              previewSvgIconCanvas.setPreferredSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setMaximumSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setMinimumSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.setSize(sizeOfPanel4ImagePreview);
              previewSvgIconCanvas.init();
              
              final MouseAdapter mouseAdapter=new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                  previewSvgIconCanvas.setSelected(true);
                }
              };

              previewSvgIconCanvas.addMouseListener(mouseAdapter);
              previewSvgIconCanvas.setDocumentSet(true);
              previewSvgIconCanvas.setSVGDocument(document);          
              panel4Preview.add(previewSvgIconCanvas,BorderLayout.CENTER);
            }catch (Exception e){
              e.printStackTrace();
            }finally {
              
            }
            
          }else{
            panel4Preview.add(label4Preview,BorderLayout.CENTER);
            panel4Preview.repaint();
          }
          
				}
      }
    });
    
    
    panel4UploadClearButtons.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
    panel4UploadClearButtons.setSize(sizeOfPanel4UploadClearButtons);
    panel4UploadClearButtons.setPreferredSize(sizeOfPanel4UploadClearButtons);
    panel4UploadClearButtons.setMaximumSize(sizeOfPanel4UploadClearButtons);
    panel4UploadClearButtons.setMinimumSize(sizeOfPanel4UploadClearButtons);
    
    btnClear.setText("Clear");
    btnClear.setSize(sizeOfBtnClear);
    btnClear.setPreferredSize(sizeOfBtnClear);
    btnClear.setMaximumSize(sizeOfBtnClear);
    btnClear.setMinimumSize(sizeOfBtnClear);
    btnClear.setToolTipText("Click to Upload the Selected Image File");
    btnClear.setEnabled(false);
    btnClear.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        txtFile.setText("");
        txtTitle.setText("");
        txaDescription.setText("");
        panel4Preview.removeAll();
        panel4Preview.add(label4Preview,BorderLayout.CENTER);
        panel4Preview.repaint();
      }
    });

    
    btnUpload.setText("Upload");
    btnUpload.setSize(sizeOfBtnUpload);
    btnUpload.setPreferredSize(sizeOfBtnUpload);
    btnUpload.setMaximumSize(sizeOfBtnUpload);
    btnUpload.setMinimumSize(sizeOfBtnUpload);
    btnUpload.setToolTipText("Click to Upload the Selected Image File");
    btnUpload.setEnabled(false);
    btnUpload.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        if(areEnteredValuesValid()){
          File selectedFile = null;;
          FileInputStream fis = null;
          ObjectInputStream objInStream=null; 
          String fileName=null;
          String contentType=null;
          String category=null;
          int contentSize=0;
          try {
            selectedFile = imageFileChooser.getSelectedFile();
            contentSize=(int)selectedFile.length();
            fileName=selectedFile.getName();
            fis = new FileInputStream(selectedFile);
            byte content[] = new byte[contentSize];
            fis.read(content);
            fis.close();
            
            if(fileName.endsWith(".gif")){
              contentType=ContentType.GIF.toString() ;
              category=ImageFormat.GIF.toString();
            }else if (fileName.endsWith(".png")){
              contentType=ContentType.PNG.toString() ;
              category=ImageFormat.PNG.toString();
            }else if (fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")){
              contentType=ContentType.JPG.toString() ;
              category=ImageFormat.JPG.toString();
            }else if (fileName.endsWith(".svg")){
              contentType=ContentType.SVG.toString() ;
              category=ImageFormat.SVG.toString();
            }
              
            HttpBrowser newBrowser = null;
            newBrowser = new HttpBrowser(studio, "imageUploadAction.do");
            Properties args = new Properties();
            args.put("customerEmailIdTblPk",getStudio().getCustomerInfo().getCustomerEmailIdTblPk());
            args.put("fileTitle",txtTitle.getText().trim());
            args.put("fileDescription",txaDescription.getText().trim());
            args.put("content",Base64.encodeBytes(content));
            args.put("contentSize",Integer.toString(contentSize));
            args.put("contentType",contentType);
            args.put("category",category);
            objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
            boolean response = objInStream.readBoolean();
            if (response){
              btnClear.doClick();
              listImages(category);
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }finally{
            try {
              if(objInStream!=null){ 
                objInStream.close();
              }
            }catch (IOException  ioe) {
              ioe.printStackTrace();
            }
          
          }
        }
      }
      
    });
    
    panel4Preview.setLayout(new BorderLayout());
    panel4Preview.setSize(sizeOfPanel4ImagePreview);
    panel4Preview.setPreferredSize(sizeOfPanel4ImagePreview);
    panel4Preview.setMaximumSize(sizeOfPanel4ImagePreview);
    panel4Preview.setMinimumSize(sizeOfPanel4ImagePreview);
   
    label4Preview.setBorder(new LineBorder(new Color(247,161,90),1));
    label4Preview.setText("Preview Of The Image"); 
    label4Preview.setFont(getStudio().STUDIO_FONT);
    label4Preview.setHorizontalAlignment(JLabel.CENTER);
    
    panel4Preview.add(label4Preview,BorderLayout.CENTER);
    
    if (!getStudio().getCustomerInfo().isCustomerNew()){ 
      panel4UploadClearButtons.add(btnUpload);
    }
    
    panel4UploadClearButtons.add(btnClear);
    
    Insets insets4Lables =new Insets(2, 2, 2, 2);
    Insets insets4Components =new Insets(2, 0, 2, 2);
    
    panel4ImageUpload.add(label4File, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets4Lables, 0, 0));
    panel4ImageUpload.add(txtFile, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));
    panel4ImageUpload.add(btnBrowse, new GridBagConstraints(2, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));
    
    if (!getStudio().getCustomerInfo().isCustomerNew()){
      panel4ImageUpload.add(label4Title, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets4Lables, 0, 0));
      panel4ImageUpload.add(txtTitle, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));
      
      panel4ImageUpload.add(label4Description, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets4Lables, 0, 0));
      panel4ImageUpload.add(scrollPane4TxaDescritpion, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));
    }        
        
    panel4ImageUpload.add(panel4Preview, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));
    panel4ImageUpload.add(panel4UploadClearButtons, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets4Components, 0, 0));    
    
    if (!getStudio().getCustomerInfo().isCustomerNew()){
      this.add(panel4Buttons,null);
      this.add(panel4ThumbnailView,null);
    }
    
    this.add(panel4ImageUpload,null);
  }
  /**
   * 
   * @description 
   * @return 
   * @param name
   */
  private ImageIcon getIcon(String name){
    return getStudio().getSvgResource().getIcon(name);
  }
  
  /**
   * 
   * @description 
   * @param message
   */
  private void showErrorMessage(String message){
    JOptionPane.showMessageDialog(studio, 
			        										message, 
																  "Upload Error Message", 
																  JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  private boolean areEnteredValuesValid(){
    String imageFileName=txtFile.getText();
    String imageTitleValue=txtTitle.getText().trim();
    String imageDescriptionValue=txaDescription.getText().trim();
    
    if(imageFileName.length()<=0){
      showErrorMessage("Select Image File To Upload");
      return false;
    }
    
    if(!(imageFileName.endsWith(".svg") 
              || imageFileName.endsWith(".png") || imageFileName.endsWith(".jpg") 
              || imageFileName.endsWith(".jpeg") || imageFileName.endsWith(".gif"))){
      showErrorMessage("Select Image Files Only");
      return false;
    }
    
    if(imageTitleValue.length()<5){
      showErrorMessage("Enter Image Title More Than 5 Characters");
      txtTitle.requestFocus();
      return false;
    }
    
    if(imageTitleValue.length()>15){
      showErrorMessage("Enter Image Title Less Than 15 Characters");
      txtTitle.requestFocus();
      return false;
    }
    
    if(imageDescriptionValue.length()>100){
      showErrorMessage("Enter Image Description Less Than 100 Characters");
      txaDescription.requestFocus();
      return false;
    }
    
    return true;
  }
  /**
   * 
   * @description 
   * @param format
   */
  private void listImages(String format){
    HttpBrowser newBrowser = null;
    ObjectInputStream objInStream=null; 
    Dimension seperatorSize=new Dimension(2,2);
    Dimension thumbnailSize=new Dimension(75,75);
    //System.out.println("Inside listImages ");
    final SVGClipart svgClipart = (SVGClipart)getStudio().getShapeObject("clipart");
    try {
      viewBorder.setTitle("View [ for "+ format +" images ]");
      panel4ThumbnailView.repaint();
      studio.getMainStatusBar().setInfo("Connecting .......");
      studio.getSvgTabManager().getCurrentSVGTab().getScrollPane().getSvgCanvas().displayWaitCursor();
      newBrowser = new HttpBrowser(studio, "imageListAction.do");
      Properties args = new Properties();
      args.put("customerEmailIdTblPk",getStudio().getCustomerInfo().getCustomerEmailIdTblPk());
      args.put("format",format);
      objInStream =new ObjectInputStream(newBrowser.sendPostMessage(args));
      ImageBean[] images=(ImageBean[])objInStream.readObject();
      int index=0;
      int numberOfImages=images.length;
      svgClipart.removeClipartActions();
      thumbnailView4Image.removeAll();
      thumbnailView4Image.setLayout(new BoxLayout(thumbnailView4Image,BoxLayout.X_AXIS));
      
      while(index<numberOfImages){
        Properties svgArgs = new Properties();
        svgArgs.put("dataSourceKey","FOKey");
        svgArgs.put("contentOId",Integer.toString(images[index].getContentOId()));
        svgArgs.put("contentType",images[index].getContentType());
        svgArgs.put("contentSize",Integer.toString(images[index].getContentSize()));
        
        String urlAction="svgImageDisplayAction.do";
        //System.out.println("Content Type " + images[index].getContentType()); 
        //System.out.println("Content Type check" + images[index].getContentType().indexOf((ContentType.SVG.toString()))); 
        if (images[index].getContentType().indexOf((ContentType.SVG.toString()))>-1){
          urlAction="svgDisplayAction.do";
        }
        
        URL svgUrl=new URL(getStudio().getSvgResource().getContextPath() + urlAction);
        svgUrl=new URL(svgUrl.toExternalForm()+"?"+ HttpBrowser.toEncodedString(svgArgs));
        
        //System.out.println("URL Is : " + svgUrl);
        
        final SVGIconCanvas thumb = new SVGIconCanvas(getStudio());
        svgClipart.addClipartAction(thumb);
        
        thumb.setPreferredSize(thumbnailSize);
        thumb.setMaximumSize(thumbnailSize);
        thumb.setMinimumSize(thumbnailSize);
        thumb.setSize(thumbnailSize);
        thumb.setToolTipText(images[index].getTitle() + " - [ " + images[index].getDescription() + "]");
        thumb.init();
        
        final MouseAdapter mouseAdapter=new MouseAdapter(){
          public void mouseClicked(MouseEvent e){
            thumb.setSelected(true);
          }
        };
    
        thumb.addMouseListener(mouseAdapter);
        thumb.setURI(svgUrl.toString());
        
        JPanel thumbPanel = new JPanel();
        thumbPanel.setLayout(new BorderLayout());
        thumbPanel.setPreferredSize(thumbnailSize);
        thumbPanel.setMaximumSize(thumbnailSize);
        thumbPanel.setMinimumSize(thumbnailSize);
        thumbPanel.setSize(thumbnailSize);
        thumbPanel.add(thumb,BorderLayout.CENTER);
        
        
        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(seperatorSize);
        separatorPanel.setMaximumSize(seperatorSize);
        separatorPanel.setMinimumSize(seperatorSize);
        separatorPanel.setSize(seperatorSize);
        thumbnailView4Image.add(separatorPanel);

        thumbnailView4Image.add(thumbPanel); 
        index++;
      }
      
      if (numberOfImages!=0){
        JPanel lastSeparatorPanel = new JPanel();
        lastSeparatorPanel.setPreferredSize(seperatorSize);
        lastSeparatorPanel.setMaximumSize(seperatorSize);
        lastSeparatorPanel.setMinimumSize(seperatorSize);
        lastSeparatorPanel.setSize(seperatorSize);
        thumbnailView4Image.add(lastSeparatorPanel);
      }else{
        label4ThumbnailView.setText("No Images Available");
        thumbnailView4Image.setLayout(new BorderLayout());
        thumbnailView4Image.setBorder(BorderFactory.createEmptyBorder());
        thumbnailView4Image.add(label4ThumbnailView, BorderLayout.CENTER);
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
   * @return 
   */
  public JButton getBtnUpload() {
    return btnUpload;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnBrowse() {
    return btnBrowse;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnAll() {
    return btnAll;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnJpeg() {
    return btnJpeg;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnGif() {
    return btnGif;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnPng() {
    return btnPng;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnSvg() {
    return btnSvg;
  }


  /**
   * 
   * @description 
   * @param btnClear
   */
  public void setBtnClear(JButton btnClear) {
    this.btnClear = btnClear;
  }


  /**
   * 
   * @description 
   * @return 
   */
  public JButton getBtnClear() {
    return btnClear;
  }

}