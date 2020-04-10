<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="userCategories" name="userCategories" type="java.util.ArrayList" /> 
<HTML>
  <HEAD>
      <TITLE><bean:message key="title.MyPreferences" /></TITLE>
      <link href="main.css" rel="stylesheet" type="text/css">
      <html:javascript formName="userPreferencesForm" dynamicJavascript="true" staticJavascript="false" />
      <script language="javascript1.1" src="staticJavascript.jsp"></script>
      <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
      <script>
      function btnclick(operation){
        var thisForm=document.forms[0];
        thisForm.target="_self";
        if ((operation=='save' && validateUserPreferencesForm(thisForm)) ){
          thisForm.submit();
        }
      }   
      function changePasswordClick(){
        var thisForm = document.forms[0];
        //thisForm.target="_self";
        thisForm.action="changePasswordB4Action.do";
        thisForm.submit();
      }
  </script>
  </HEAD>
  <BODY>
  <html:form action="/userPreferencesAction">
  <html:hidden property="hdnUserProfileTblPk" />
  <jsp:include page="/header.jsp" />
    <!-- Tab Table Starts Here -->
    <table width="970px" align="center" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="200px" height="20px" class="tab">
          <div align="center"><bean:message key="tab.MyPreferences" /> </div>
        </td>
        <td width="770px"></td>
      </tr>
    </table>
    <!-- Tab Table Ends Here -->
    <!-- Main Table Starts Here -->
    <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <tr>
        <td height="501px" >
          <!--Sub Main Table Starts Here-->
          <table align="center" class="bdrLeftColor_333333 bdrTopColor_333333 bdrRightColor_333333 bdrBottomColor_333333" width="600px" border="0" cellspacing="0" cellpading="0" >
            <tr>
              <td>
                <!-- Information Table Starts Here -->
                <table border="0" align="center" width="100%" >
                  <tr>
                    <th colspan="4" height="20px"><div align="center"><bean:message key="tbl.MyInformation" /></div></th>
                  </tr>
                  <tr>
                    <td colspan="4">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="25%"><div align="right"><bean:message key="txt.UserId" /> :</div></td>
                    <td width="25%"><html:text property="txtUserId" styleClass="bdrColor_336666" style="width:130px" readonly="true" /></td>
                    <td width="25%"><div align="right"><bean:message key="txt.UserCategory" /> :</div></td>
                    <td width="25%">
                      <html:select property="cboUserCategory" styleClass="bdrColor_336666" style="width:130px" disabled="true">
                        <logic:iterate id="userCategory" name="userCategories">  
                          <option  value="<bean:write name="userCategory" property="id" />">
                            <bean:write name="userCategory" property="name" />
                          </option>
                        </logic:iterate>  
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td ><div align="right"><bean:message key="txt.UserFirstName" /> :</div></td>
                    <td><html:text property="txtUserFirstName" styleClass="bdrColor_336666" style="width:130px" readonly="true"/></td>
                    <td><div align="right"><bean:message key="txt.UserLastName" /> :</div></td>
                    <td><html:text property="txtUserLastName" styleClass="bdrColor_336666" style="width:130px" readonly="true"/></td>
                  </tr>
                  <tr>
                    <td colspan="4">&nbsp;</td>
                  </tr>
                </table>
                <!-- Information Table Ends Here -->
              </td>
            </tr>
            <tr>
              <td>
                <!-- Preference Table Starts Here -->
                <table border="0" align="center" width="100%">
                  <tr>
                    <th colspan="4" height="20px"><div align="center"><bean:message key="tbl.MyPreferences" /></div></th>
                  </tr>
                  <tr>
                    <td colspan="4">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="25%"><div align="right">* <bean:message key="txt.TreeViewLevel" /> :</div></td>
                    <td width="25%"><html:text property="txtTreeViewLevel" styleClass="bdrColor_336666" onkeypress="return integerOnly(event);" style="width:35px" /></td>
                    <td width="50%" colspan="2">&nbsp;</td>
                  </tr>
                  <tr>
                    <td><div align="right"> <bean:message key="txt.NumberOfRecords" /> :</div></td>
                    <td><html:text property="txtNumberOfRecords" styleClass="bdrColor_336666" onkeypress="return integerOnly(event);" style="width:35px" /></td>
                    <td colspan="2">&nbsp;</td>
                  </tr>
                  
                </table>
                <!-- Preferences Table Ends Here -->
              </td>
            </tr>
            <tr>
              <td>
                <!-- Password Table Starts Here -->
                <table border="0" width="100%" align="center">
                  <tr>
                    <th colspan="4" height="20px"><div align="center"><bean:message key="tbl.MyPasswords" /></div></th>
                  </tr>
                  <tr>
                    <td colspan="4">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="25%"><div align="right"><bean:message key="btn.LoginPassword" /> :</div></td>
                    <td width="25%"><html:button property="btnChangePassword" styleClass="buttons" style="width:110px" onclick="return changePasswordClick();"><bean:message key="btn.ChangePassword" /></html:button></td>
                    <td width="50%" colspan="2">&nbsp;</td>
                  </tr>
                </table>
                <!-- Password Table Ends Here -->
              </td>
            </tr>
            <tr>
              <td>
                <!--Button Table Starts Here-->
                <table width="100%"  border="0" align="center" >
                    <tr>
                      <td><bean:message key="info.UserPreference" /></td>
                      <td height="30px"><div align="right">
                        <html:button property="btnSave" styleClass="buttons" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
                        <html:cancel  styleClass="buttons" ><bean:message  key="btn.Cancel" /></html:cancel>
                        <html:reset property="btnClear" styleClass="buttons" style="margin-right:1px" ><bean:message  key="btn.Clear" /></html:reset>
                      </div>
                      </td>
                    </tr>
                </table>
                <!--Button Table Ends Here-->
              </td>
            </tr>
          </table>
          <!--Sub Main Table Ends Here-->
          <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        
          <!-- Status Bar Table Starts-->
          <table id="tblStatusBar" align="center" width="600px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
            <tr bgcolor="#FFFFFF">
              <td width="30px" ><div class="imgStatusMsg"></div></td>
              <td>
                <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
              </td>
            </tr>
          </table>
          <!-- Status Bar Table Ends-->    
        </td>
      </tr>
    </table>
    <!-- Main Table Ends Here -->
  </html:form>
  </BODY>
</HTML>
