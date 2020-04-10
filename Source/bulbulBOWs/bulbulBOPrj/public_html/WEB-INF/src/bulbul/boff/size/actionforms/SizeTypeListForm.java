package bulbul.boff.size.actionforms;

import org.apache.struts.validator.ValidatorForm;


public class SizeTypeListForm extends ValidatorForm  {
  private String hdnSearchOperation;
  private String txtSearchSizeTypeId;
  private String txtSearchSizeTypeDesc;
  private String radSearchStatus;
  private String radSearchSelect;
  private String hdnSearchPageNo;
  private String hdnSearchStatus;
  private String hdnSearchCurrentStatus;
  private String hdnSearchPageCount;

  public String getHdnSearchOperation() {
    return hdnSearchOperation;
  }

  public void setHdnSearchOperation(String newHdnSearchOperation) {
    hdnSearchOperation = newHdnSearchOperation;
  }

  public String getTxtSearchSizeTypeId() {
    return txtSearchSizeTypeId;
  }

  public void setTxtSearchSizeTypeId(String newTxtSearchSizeTypeId) {
    txtSearchSizeTypeId = newTxtSearchSizeTypeId;
  }

  public String getTxtSearchSizeTypeDesc() {
    return txtSearchSizeTypeDesc;
  }

  public void setTxtSearchSizeTypeDesc(String newTxtSearchSizeTypeDesc) {
    txtSearchSizeTypeDesc = newTxtSearchSizeTypeDesc;
  }



  public String getRadSearchStatus() {
    return radSearchStatus;
  }

  public void setRadSearchStatus(String newRadSearchStatus) {
    radSearchStatus = newRadSearchStatus;
  }

  public String getRadSearchSelect() {
    return radSearchSelect;
  }

  public void setRadSearchSelect(String newRadSearchSelect) {
    radSearchSelect = newRadSearchSelect;
  }

  public String getHdnSearchPageNo() {
    return hdnSearchPageNo;
  }

  public void setHdnSearchPageNo(String newHdnSearchPageNo) {
    hdnSearchPageNo = newHdnSearchPageNo;
  }

  public String getHdnSearchStatus() {
    return hdnSearchStatus;
  }

  public void setHdnSearchStatus(String newHdnSearchStatus) {
    hdnSearchStatus = newHdnSearchStatus;
  }

  public String getHdnSearchCurrentStatus() {
    return hdnSearchCurrentStatus;
  }

  public void setHdnSearchCurrentStatus(String newHdnSearchCurrentStatus) {
    hdnSearchCurrentStatus = newHdnSearchCurrentStatus;
  }

  public String getHdnSearchPageCount() {
    return hdnSearchPageCount;
  }

  public void setHdnSearchPageCount(String newHdnSearchPageCount) {
    hdnSearchPageCount = newHdnSearchPageCount;
  }


  
}