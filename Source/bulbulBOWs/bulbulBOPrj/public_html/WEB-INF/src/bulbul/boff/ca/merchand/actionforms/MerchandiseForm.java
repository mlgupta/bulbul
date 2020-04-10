package bulbul.boff.ca.merchand.actionforms;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


public class MerchandiseForm extends ValidatorForm  {
  private int hdnMerchandiseTblPk;
  private int hdnMerchandiseCategoryTblPk;
  private String txtMerchandise;
  private String txaMerchandiseDesc;
  private String txaMerchandiseComm;
  private int txtThreshQty;
  private int hdnDisplayImgOid;
  private int hdnPrintableAreaImgOid;
  private String txaDeliveryNote;
  private int hdnCreatedBy;
  private String txaMaterialDetail;
  private int txtMinimumQuantity;
  private FormFile displayImgFile;
  private String hdnDisplayImgContentType;
  private int hdnDisplayImgContentSize;
  private String hdnPrintableAreaImgContentType;
  private String hdnPrintableAreaImgContentSize;
  private String[] hdnColorOneValue;
  private String[] hdnColorOneName;
  private String[] hdnColorTwoValue;
  private String[] hdnColorTwoName;
  private String[] hdnSizeValue;
  private String[] hdnPriceValue;
  private String[] hdnColorIsActive;
  private String[] hdnSizeIsActive;
  private String[] hdnSizeText;
  private String[] hdnMerchandiseColorTblPk;
  private String[] hdnColorSNo;
  private String[] hdnColorIsActiveDisplay;
  private String[] hdnSizeIsActiveDisplay;
  private String[] hdnMerchandiseSizeTblPk;
  private String[] hdnMerchandiseColorOperation;
  private String[] hdnMerchandiseSizeOperation;
  private String hdnSearchPageNo;
  private int cboMerchandiseDecoration;
  private String txtParentCategory;
  private String[] chkPrintableArea;
  private String txtPrintableAreaName;
  
 
  
  private Map maphdnMerchandisePrintableAreaTblPk=new HashMap();
  private Map mapPrintableAreaHeight=new HashMap();
  private Map mapPrintableAreaWidth=new HashMap();
  private Map mapPrintableAreaImg=new HashMap();
  private Map mapHdnImageOid=new HashMap();
  private Map mapHdnContentType=new HashMap();
  private Map mapHdnContentSize=new HashMap();
  
  private String hdnPreviouslyChecked;
  private String hdnPrintableAreaTblPkString;
  private String hdnPrintableAreaTblPkFlagString;

  public int getHdnMerchandiseTblPk() {
    return hdnMerchandiseTblPk;
  }

  public void setHdnMerchandiseTblPk(int newHdnMerchandiseTblPk) {
    hdnMerchandiseTblPk = newHdnMerchandiseTblPk;
  }

  public int getHdnMerchandiseCategoryTblPk() {
    return hdnMerchandiseCategoryTblPk;
  }

  public void setHdnMerchandiseCategoryTblPk(int newHdnMerchandiseCategoryTblPk) {
    hdnMerchandiseCategoryTblPk = newHdnMerchandiseCategoryTblPk;
  }

  public String getTxtMerchandise() {
    return txtMerchandise;
  }

  public void setTxtMerchandise(String newTxtMerchandise) {
    txtMerchandise = newTxtMerchandise;
  }

  public String getTxaMerchandiseDesc() {
    return txaMerchandiseDesc;
  }

  public void setTxaMerchandiseDesc(String newTxaMerchandiseDesc) {
    txaMerchandiseDesc = newTxaMerchandiseDesc;
  }

  public String getTxaMerchandiseComm() {
    return txaMerchandiseComm;
  }

  public void setTxaMerchandiseComm(String newTxaMerchandiseComm) {
    txaMerchandiseComm = newTxaMerchandiseComm;
  }

  public int getTxtThreshQty() {
    return txtThreshQty;
  }

  public void setTxtThreshQty(int newTxtThreshQty) {
    txtThreshQty = newTxtThreshQty;
  }

  public int getHdnDisplayImgOid() {
    return hdnDisplayImgOid;
  }

  public void setHdnDisplayImgOid(int newHdnDisplayImgOid) {
    hdnDisplayImgOid = newHdnDisplayImgOid;
  }

  public int getHdnPrintableAreaImgOid() {
    return hdnPrintableAreaImgOid;
  }

  public void setHdnPrintableAreaImgOid(int hdnPrintableAreaImgOid) {
    this.hdnPrintableAreaImgOid = hdnPrintableAreaImgOid;
  }

  public String getTxaDeliveryNote() {
    return txaDeliveryNote;
  }

  public int getHdnCreatedBy() {
    return hdnCreatedBy;
  }

  public void setHdnCreatedBy(int newHdnCreatedBy) {
    hdnCreatedBy = newHdnCreatedBy;
  }

  public String getTxaMaterialDetail() {
    return txaMaterialDetail;
  }

  public void setTxaMaterialDetail(String newTxaMaterialDetail) {
    txaMaterialDetail = newTxaMaterialDetail;
  }

  public int getTxtMinimumQuantity() {
    return txtMinimumQuantity;
  }

  public void setTxtMinimumQuantity(int newTxtMinimumQuantity) {
    txtMinimumQuantity = newTxtMinimumQuantity;
  }

  public void setTxaDeliveryNote(String newTxaDeliveryNote) {
    txaDeliveryNote = newTxaDeliveryNote;
  }

  public FormFile getDisplayImgFile() {
    return displayImgFile;
  }

  public void setDisplayImgFile(FormFile newDisplayImgFile) {
    displayImgFile = newDisplayImgFile;
  }

  public String getHdnDisplayImgContentType() {
    return hdnDisplayImgContentType;
  }

  public void setHdnDisplayImgContentType(String newHdnDisplayImgContentType) {
    hdnDisplayImgContentType = newHdnDisplayImgContentType;
  }

  public int getHdnDisplayImgContentSize() {
    return hdnDisplayImgContentSize;
  }

  public void setHdnDisplayImgContentSize(int newHdnDisplayImgContentSize) {
    hdnDisplayImgContentSize = newHdnDisplayImgContentSize;
  }

  public String getHdnPrintableAreaImgContentType() {
    return hdnPrintableAreaImgContentType;
  }

  public void setHdnPrintableAreaImgContentType(String hdnPrintableAreaImgContentType) {
    this.hdnPrintableAreaImgContentType = hdnPrintableAreaImgContentType;
  }

  public String getHdnPrintableAreaImgContentSize() {
    return hdnPrintableAreaImgContentSize;
  }

  public void setHdnPrintableAreaImgContentSize(String hdnPrintableAreaImgContentSize) {
    this.hdnPrintableAreaImgContentSize = hdnPrintableAreaImgContentSize;
  }

  public String[] getHdnColorOneValue() {
    return hdnColorOneValue;
  }

  public void setHdnColorOneValue(String[] newHdnColorOneValue) {
    hdnColorOneValue = newHdnColorOneValue;
  }

  public String[] getHdnColorOneName() {
    return hdnColorOneName;
  }

  public void setHdnColorOneName(String[] newHdnColorOneName) {
    hdnColorOneName = newHdnColorOneName;
  }

  public String[] getHdnColorTwoValue() {
    return hdnColorTwoValue;
  }

  public void setHdnColorTwoValue(String[] newHdnColorTwoValue) {
    hdnColorTwoValue = newHdnColorTwoValue;
  }

  public String[] getHdnColorTwoName() {
    return hdnColorTwoName;
  }

  public void setHdnColorTwoName(String[] newHdnColorTwoName) {
    hdnColorTwoName = newHdnColorTwoName;
  }

  public String[] getHdnSizeValue() {
    return hdnSizeValue;
  }

  public void setHdnSizeValue(String[] newHdnSizeValue) {
    hdnSizeValue = newHdnSizeValue;
  }

  public String[] getHdnPriceValue() {
    return hdnPriceValue;
  }

  public void setHdnPriceValue(String[] newHdnPriceValue) {
    hdnPriceValue = newHdnPriceValue;
  }

  public String[] getHdnColorIsActive() {
    return hdnColorIsActive;
  }

  public void setHdnColorIsActive(String[] newHdnColorIsActive) {
    hdnColorIsActive = newHdnColorIsActive;
  }

  public String[] getHdnSizeIsActive() {
    return hdnSizeIsActive;
  }

  public void setHdnSizeIsActive(String[] newHdnSizeIsActive) {
    hdnSizeIsActive = newHdnSizeIsActive;
  }

  public String[] getHdnSizeText() {
    return hdnSizeText;
  }

  public void setHdnSizeText(String[] newHdnSizeText) {
    hdnSizeText = newHdnSizeText;
  }  

  public String[] getHdnMerchandiseColorTblPk() {
    return hdnMerchandiseColorTblPk;
  }

  public void setHdnMerchandiseColorTblPk(String[] newHdnMerchandiseColorTblPk) {
    hdnMerchandiseColorTblPk = newHdnMerchandiseColorTblPk;
  }

  public String[] getHdnColorSNo() {
    return hdnColorSNo;
  }

  public void setHdnColorSNo(String[] newHdnColorSNo) {
    hdnColorSNo = newHdnColorSNo;
  }

  public String[] getHdnColorIsActiveDisplay() {
    return hdnColorIsActiveDisplay;
  }

  public void setHdnColorIsActiveDisplay(String[] newHdnColorIsActiveDisplay) {
    hdnColorIsActiveDisplay = newHdnColorIsActiveDisplay;
  }

  public String[] getHdnSizeIsActiveDisplay() {
    return hdnSizeIsActiveDisplay;
  }

  public void setHdnSizeIsActiveDisplay(String[] newHdnSizeIsActiveDisplay) {
    hdnSizeIsActiveDisplay = newHdnSizeIsActiveDisplay;
  }

  public String[] getHdnMerchandiseSizeTblPk() {
    return hdnMerchandiseSizeTblPk;
  }

  public void setHdnMerchandiseSizeTblPk(String[] newHdnMerchandiseSizeTblPk) {
    hdnMerchandiseSizeTblPk = newHdnMerchandiseSizeTblPk;
  }

  public String[] getHdnMerchandiseColorOperation() {
    return hdnMerchandiseColorOperation;
  }

  public void setHdnMerchandiseColorOperation(String[] newHdnMerchandiseColorOperation) {
    hdnMerchandiseColorOperation = newHdnMerchandiseColorOperation;
  }

  public String[] getHdnMerchandiseSizeOperation() {
    return hdnMerchandiseSizeOperation;
  }

  public void setHdnMerchandiseSizeOperation(String[] newHdnMerchandiseSizeOperation) {
    hdnMerchandiseSizeOperation = newHdnMerchandiseSizeOperation;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public int getCboMerchandiseDecoration() {
    return cboMerchandiseDecoration;
  }

  public void setCboMerchandiseDecoration(int cboMerchandiseDecoration) {
    this.cboMerchandiseDecoration = cboMerchandiseDecoration;
  }

  public String getTxtParentCategory() {
    return txtParentCategory;
  }

  public void setTxtParentCategory(String txtParentCategory) {
    this.txtParentCategory = txtParentCategory;
  }

  public String[] getChkPrintableArea() {
    return chkPrintableArea;
  }

  public void setChkPrintableArea(String[] chkPrintableArea) {
    this.chkPrintableArea = chkPrintableArea;
  }

  public String getTxtPrintableAreaName() {
    return txtPrintableAreaName;
  }

  public void setTxtPrintableAreaName(String txtPrintableAreaName) {
    this.txtPrintableAreaName = txtPrintableAreaName;
  }


  public String getHdnMerchandisePrintableAreaTblPk(int index) {
    return (String) maphdnMerchandisePrintableAreaTblPk.get(new Integer(index));
  }
   
  public String[] getHdnMerchandisePrintableAreaTblPk() {
    return (String[]) maphdnMerchandisePrintableAreaTblPk.values().toArray(new String[maphdnMerchandisePrintableAreaTblPk.size()]);
  }

  public void setHdnMerchandisePrintableAreaTblPk(int index,String hdnMerchandisePrintableAreaTblPk) {
    maphdnMerchandisePrintableAreaTblPk.put(new Integer (index),hdnMerchandisePrintableAreaTblPk);
  }

  public String getTxtPrintableAreaHeight(int index) {
    return (String) mapPrintableAreaHeight.get(new Integer(index));
  }
   
  public String[] getTxtPrintableAreaHeight() {
    return (String[]) mapPrintableAreaHeight.values().toArray(new String[mapPrintableAreaHeight.size()]);
  }

  public void setTxtPrintableAreaHeight(int index,String txtPrintableAreaHeight) {
    mapPrintableAreaHeight.put(new Integer (index),txtPrintableAreaHeight);
  }


  public String getTxtPrintableAreaWidth(int index) {
    return (String) mapPrintableAreaWidth.get(new Integer(index));
  }
  
  public String[] getTxtPrintableAreaWidth() {
    return (String[]) mapPrintableAreaWidth.values().toArray(new String[mapPrintableAreaWidth.size()]);
  }

  public void setTxtPrintableAreaWidth(int index,String txtPrintableAreaWidth) {
    mapPrintableAreaWidth.put(new Integer (index),txtPrintableAreaWidth);
  }
  public FormFile getPrintableAreaImgFile(int index) {
    return (FormFile) mapPrintableAreaImg.get(new Integer(index));
  }
  
  public FormFile[] getPrintableAreaImgFile() {
    return (FormFile[]) mapPrintableAreaImg.values().toArray(new FormFile[mapPrintableAreaImg.size()]);
  }

  public void setPrintableAreaImgFile(int index,FormFile printableAreaImgFile) {
    mapPrintableAreaImg.put(new Integer (index),printableAreaImgFile);
  }
  
  public String getHdnImageOid(int index) {
    return (String) mapHdnImageOid.get(new Integer(index));
  }
   
  public String[] getHdnImageOid() {
    return (String[]) mapHdnImageOid.values().toArray(new String[mapHdnImageOid.size()]);
  }

  public void setHdnImageOid(int index,String hdnImageOid) {
    mapHdnImageOid.put(new Integer (index),hdnImageOid);
  }
   
  public String getHdnContentType(int index) {
    return (String) mapHdnContentType.get(new Integer(index));
  }
   
  public String[] getHdnContentType(){
    return (String[]) mapHdnContentType.values().toArray(new String[mapHdnContentType.size()]);
  }

  public void setHdnContentType(int index,String hdnContentType) {
    mapHdnContentType.put(new Integer (index),hdnContentType);
  }

 //contentSize
  public String getHdnContentSize(int index) {
    return (String) mapHdnContentSize.get(new Integer(index));
  }
   
  public String[] getHdnContentSize() {
    return (String[]) mapHdnContentSize.values().toArray(new String[mapHdnContentSize.size()]);
  }

  public void setHdnContentSize(int index,String hdnContentSize) {
    mapHdnContentSize.put(new Integer (index),hdnContentSize);
  }


  public String getHdnPreviouslyChecked() {
    return hdnPreviouslyChecked;
  }

  public void setHdnPreviouslyChecked(String hdnPreviouslyChecked) {
    this.hdnPreviouslyChecked = hdnPreviouslyChecked;
  }

  public String getHdnPrintableAreaTblPkString() {
    return hdnPrintableAreaTblPkString;
  }

  public void setHdnPrintableAreaTblPkString(String hdnPrintableAreaTblPkString) {
    this.hdnPrintableAreaTblPkString = hdnPrintableAreaTblPkString;
  }

  public String getHdnPrintableAreaTblPkFlagString() {
    return hdnPrintableAreaTblPkFlagString;
  }

  public void setHdnPrintableAreaTblPkFlagString(String hdnPrintableAreaTblPkFlagString) {
    this.hdnPrintableAreaTblPkFlagString = hdnPrintableAreaTblPkFlagString;
  }
}