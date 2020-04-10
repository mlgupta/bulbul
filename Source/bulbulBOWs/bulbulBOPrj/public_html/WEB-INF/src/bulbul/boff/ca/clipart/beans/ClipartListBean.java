package bulbul.boff.ca.clipart.beans;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClipartListBean extends Action  {
  private String clipartOrCategoryTblPk;
  private String clipartOrCategoryName;
  private String clipartOrCategoryType;
  private String clipartOrCategoryDesc;
  private String isActive;
  private String isActiveDisplay;

  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward("success");
  }

  public String getClipartOrCategoryTblPk() {
    return clipartOrCategoryTblPk;
  }

  public void setClipartOrCategoryTblPk(String newClipartOrCategoryTblPk) {
    clipartOrCategoryTblPk = newClipartOrCategoryTblPk;
  }

  public String getClipartOrCategoryName() {
    return clipartOrCategoryName;
  }

  public void setClipartOrCategoryName(String newClipartOrCategoryName) {
    clipartOrCategoryName = newClipartOrCategoryName;
  }

  public String getClipartOrCategoryType() {
    return clipartOrCategoryType;
  }

  public void setClipartOrCategoryType(String newClipartOrCategoryType) {
    clipartOrCategoryType = newClipartOrCategoryType;
  }

  public String getClipartOrCategoryDesc() {
    return clipartOrCategoryDesc;
  }

  public void setClipartOrCategoryDesc(String newClipartOrCategoryDesc) {
    clipartOrCategoryDesc = newClipartOrCategoryDesc;
  }

  public String getIsActive() {
    return isActive;
  }

  public void setIsActive(String newIsActive) {
    isActive = newIsActive;
  }

  public String getIsActiveDisplay() {
    return isActiveDisplay;
  }

  public void setIsActiveDisplay(String newIsActiveDisplay) {
    isActiveDisplay = newIsActiveDisplay;
  }
}