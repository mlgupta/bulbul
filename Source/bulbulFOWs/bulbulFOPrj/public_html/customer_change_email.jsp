<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><html>
<head>
<title><bean:message key="title.ChangeEmailId" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<html:javascript formName="changeEmailIdForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<SCRIPT>
<!--
  function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.target="_self";
      if(validateChangeEmailIdForm(thisForm)){
        if(thisForm.txtNewEmailId.value==thisForm.txtConfirmEmailId.value){
          thisForm.submit();
        }
        else{
          alert('<bean:message key="alert.Error.EmailIdsNotMatch" />');
          return false
        }
      }
  }
-->
</SCRIPT>
</head>
<body>
<html:form action="changeEmailIdAction.do" >
<html:hidden property="hdnCustomerEmailIdTblPk" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Change Email Address Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ChangeEmailId" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
    <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold12">
        <div align="right">
          <a class="linkMenu" href="customerProfileB4Action.do">&nbsp;<bean:message key="menu.customer.Profile" />&nbsp;</a>|
          <a class="linkMenu" href="changePasswordB4Action.do?hdnCustomerEmailIdTblPk=<bean:write name="customerInfo" property="customerEmailIdTblPk" />">&nbsp;<bean:message key="menu.customer.ChangePassword" />&nbsp;</a> | 
          &nbsp;<span class="linkMenu"><bean:message key="menu.customer.ChangeEmail" /></span>&nbsp; 
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
            <td colspan="3" align="center" >
              <div style="color:#ff0000">
                <b>
                  <html:messages id="messageEmailIdInUse" message="true" property="messageEmailIdInUse" >
                    <bean:write name="messageEmailIdInUse" />
                  </html:messages>
                  <html:messages id="messagWrongOldEmailId" message="true" property="messagWrongOldEmailId" >
                    <bean:write name="messagWrongOldEmailId" />
                  </html:messages>                  
                </b>
              </div>                
            </td>
          </tr>
          <tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.OldEmailId" />:</div></td>
            <td width="63%"><html:text property="txtOldEmailId" style="width:200px" maxlength="50"/></td>
          </tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.NewEmailId" />:</div></td>
            <td width="63%"><html:text  property="txtNewEmailId"   style="width:200px" maxlength="50" /></td>
          </tr>
          <tr>
            <td width="37%" valign="top"><div align="right"><span class="text_FF3300">*</span> <bean:message key="txt.ConfirmNewEmailId" />:</div></td>
            <td width="63%"><html:text property="txtConfirmEmailId" style="width:200px" maxlength="50" /></td>
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
  <!-- Change Email Address Table Ends-->
</html:form>  
</body>
</html>