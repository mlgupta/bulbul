<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.UserChangePwd" /></TITLE>
    <html:javascript formName="changePasswordForm" dynamicJavascript="true" staticJavascript="false" />
    <script language="javascript1.1" src="staticJavascript.jsp"></script>
    <script>
     function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.target="_self";
      if ((operation=='save' ) &&validateChangePasswordForm(thisForm)){
        if(thisForm.txtNewPassword.value==thisForm.txtConfirmPassword.value){
          thisForm.submit();
        }
        else{
          alert('<bean:message key="alert.Error.PasswordsNotMatch" />');
        }
      }
    }
    </script>
  </HEAD>
  <BODY>
    <html:form action="/changePasswordAction">
      <html:hidden property="hdnUserProfileTblPk"/>
      <jsp:include page="header.jsp" />
      <!-- Tab Table Starts -->
      <table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
        <tr>
          <td width="200px" class="tab" height="20px">
            <div align="center"><bean:message key="tab.UserProfile" /></div>
          </td>
          <td width="770px" >
          </td>
        </tr>
      </table>
      <!-- Tab Table Ends -->
      <!--Main Table Starts Here-->
      <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="501px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
            <!--Change Password Table Starts Here-->
            <table width="442px"  border="0" align="center" class="bdrColor_336666">
              <tr>
                <th height="20px" colspan="3"><div align="center">
                  <bean:message key="page.UserChangePwd" /></div>
                </th>
              </tr>
              <tr>
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr>
                <td width="150px"><div align="right"><bean:message key="txt.OldPassword" />:</div></td>
                <td width="230px">
                  <html:password property="txtOldPassword"  styleClass="bdrColor_336666" style="width:230px" tabindex="1" maxlength="10" />
                </td>
                <td width="46px">&nbsp;</td>
              </tr>
              <tr>
                <td valign="top"><div align="right"><bean:message key="txt.NewPassword" />:</div></td>
                <td>
                  <html:password property="txtNewPassword" styleClass="bdrColor_336666" style="width:230px" tabindex="2" maxlength="10" />
                </td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><div align="right"><bean:message key="txt.ConfirmPassword" />:</div></td>
                <td>
                  <html:password property="txtConfirmPassword" styleClass="bdrColor_336666" style="width:230px" tabindex="3" maxlength="10" />
                </td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><div align="right">
                  <html:button property="btnSave" styleClass="buttons"  tabindex="4" onclick="btnclick('save')"><bean:message key="btn.Save" /></html:button>
                  <html:cancel styleClass="buttons" tabindex="5" ><bean:message key="btn.Cancel" /></html:cancel>
                  <html:reset property="btnClear" styleClass="buttons" tabindex="6" ><bean:message key="btn.Clear" /></html:reset>
                </div></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
              <td colspan="3">&nbsp;</td>
              </tr>
               <tr border="0"><td><html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td></tr>
            </table>
            <!--Change Password Table Ends Here-->
          </td>
        </tr>
      </table>
      <!--Main Table Ends Here-->
    </html:form>
  </BODY>
</HTML>
