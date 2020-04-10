<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><html>
<head>
<title><bean:message key="title.ChangePassword" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<html:javascript formName="changePasswordForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<SCRIPT>
<!--
  function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.target="_self";
      if(validateChangePasswordForm(thisForm)){
        if(thisForm.txtNewPassword.value==thisForm.txtConfirmPassword.value){
          thisForm.submit();
        }
        else{
          alert('<bean:message key="alert.Error.PasswordsNotMatch" />');
          return false
        }
      }
  }
-->
</SCRIPT>
</head>
<body>
<html:form action="changePasswordAction.do">
<html:hidden property="hdnCustomerEmailIdTblPk" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Changepassword Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ChangePassword" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold12">
        <div align="right">
          <a class="linkMenu" href="customerProfileB4Action.do">&nbsp;<bean:message key="menu.customer.Profile" />&nbsp;</a> | 
          &nbsp;<span class="linkMenu"><bean:message key="menu.customer.ChangePassword" /></span>&nbsp; | 
          <a class="linkMenu" href="changeEmailIdB4Action.do?hdnCustomerEmailIdTblPk=<bean:write name="customerInfo" property="customerEmailIdTblPk" />">&nbsp;<bean:message key="menu.customer.ChangeEmail" />&nbsp;</a>&nbsp;
        </div>
      </td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td colspan="2" class="bdrTopColor_006600">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <div style="color:#ff0000">
                <b>
                  <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
                </b>
              </div>
            </td>
          </tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.OldPassword" />:</div></td>
            <td width="63%">
              <html:password  property="txtOldPassword" style="width:200px" maxlength="20" />
            </td>
          </tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.NewPassword" />:</div></td>
            <td width="63%"><html:password property="txtNewPassword" style="width:200px" maxlength="20" /></td>
          </tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.ConfirmNewPassword" />:</div></td>
            <td width="63%"><html:password property="txtConfirmPassword" style="width:200px" maxlength="20" /></td>
          </tr>
          <tr>
            <td height="30px"></td>
            <td>
              <html:button property="btnSave" styleClass="buttons" style="margin-left:5px; width:62px" onclick="btnclick();"><bean:message key="btn.Save" /></html:button>
              <html:cancel  styleClass="buttons" style="width:62px"><bean:message key="btn.Cancel" /></html:cancel>
              <html:reset property="btnClear" styleClass="buttons" style="width:62px"><bean:message key="btn.Clear" /></html:reset>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Changepassword Table Ends -->
</html:form>  
</body>
</html>
