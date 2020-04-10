<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="userCategories" name="userCategories" type="java.util.ArrayList" /> 
<bean:define id="modules" name="modules" type="java.util.ArrayList" /> 
<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.UserProfileView" /></TITLE>
    <script src="user_profile.js"></script>
  </HEAD>

  <BODY onload="return getRights(document.forms[0]);">
    <html:form action="/userProfileViewAction">
    <html:hidden property="hdnUserCategoryId" /> 
    <html:hidden property="hdnSearchPageNo" />
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
      <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
        <tr>
          <td height="501px" >
            <!--Sub Main Table Starts Here-->
            <table align="center" class="bdrLeftColor_333333 bdrTopColor_333333 bdrRightColor_333333 bdrBottomColor_333333" width="600px" border="0" cellspacing="0" cellpading="0" >
              <tr>
                <td>
                  <!--View Table Starts Here-->
                  <table width="100%"  border="0" align="center" >
                    <tr>
                      <th height="20px" colspan="4"><div align="center"><bean:message key="page.UserProfileView" /> </div></th>
                    </tr>
                    <tr>
                      <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="76px"><div align="right"><bean:message key="txt.UserId" />:</div></td>
                      <td width="137px">
                        <html:text property="txtUserId" styleClass="bdrColor_336666" style="width:130px" maxlength="10" readonly="true"/>
                      </td>
                      <td width="72px"><div align="right"><bean:message key="txt.UserPassword" />:</div></td>
                      <td width="137px">
                        <html:password property="txtUserPassword" styleClass="bdrColor_336666" style="width:130px" maxlength="10" readonly="true"/>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txt.UserFirstName" />:</div></td>
                      <td>
                        <html:text property="txtUserFirstName" styleClass="bdrColor_336666" style="width:130px"  maxlength="25" readonly="true"/></td>
                      <td><div align="right"><bean:message key="txt.UserLastName" />:</div></td>
                      <td>
                        <html:text property="txtUserLastName" styleClass="bdrColor_336666" style="width:130px"  maxlength="25" readonly="true"/>
                      </td>
                    </tr>
                    <tr>
                      <td width="72px"><div align="right"><bean:message key="cbo.UserCategory" />:</div></td>
                      <td width="137px">
                        <bean:define id="userCategoryIdLHS"  name="userProfileForm" property="cboUserCategory" />
                        <select id="cboUserCategory" name="cboUserCategory" class="bdrColor_336666" style="width:130px" disabled>
                          <logic:iterate id="userCategory" name="userCategories">  
                          <bean:define id="userCategoryIdRHS"  name="userCategory" property="id" />
                            <% 
                              if (userCategoryIdLHS.equals(userCategoryIdRHS)){
                            %>
                                <option  selected value="<bean:write name="userCategory" property="id" />">
                            <%
                              }else{
                            %>
                              <option  value="<bean:write name="userCategory" property="id" />">
                            <%
                              }
                            %>
                              <bean:write name="userCategory" property="name" />
                            </option>
                          </logic:iterate>  
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right"></div></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td colspan="4">&nbsp;</td>
                    </tr>
                  </table>
                  <!--View Table Ends Here-->
                </td>
              </tr>
              <tr>
                <td>
                  <!--Access Control Table Starts Here-->
                  <table width="100%"  border="0" align="center" >
                    <tr><td height="3px" colspan="2"></td></tr>
                    <tr>
                      <th height="20px" colspan="2"><div align="center"><bean:message key="tbl.AccessControlList" /></div></th>
                    </tr>
                    <tr><td height="3px" colspan="2"></td></tr>
                    <tr bgcolor="#FFFFFF">
                      <th width="150px"><bean:message key="tbl.Modules" /></th>
                      <th ><bean:message key="tbl.Permissions" /></th>
                    </tr>  
                    <tr>
                      <td colspan="2">
                        <!-- Div Tag for Modules and Permissions Starts Here-->
                        <div align="center" bgcolor="#80A0B2" style="height:225px;width:100%;overflow:auto;">
                          <table width="100%" border="0" cellspacing="0" cellpadding="1" >
                            <logic:iterate id="userAccessTblPk" name="userAccessTblPks">
                                <html:hidden property="hdnUserAccessTblPk" value="<%=(String)userAccessTblPk%>" />
                            </logic:iterate>
                            
                            <logic:iterate id="permissionValue" name="permissionValues">
                              <html:hidden property="hdnPermissionValues" value="<%=(String)permissionValue%>" />
                            </logic:iterate> 
                            
                            <logic:iterate id="module" name="modules">
                              <tr>
                                <td ><div style="margin-left:1px;width:150px" ><bean:write name="module" property="name" /></div></td>
                                <bean:define id="moduleId" name="module" property="id" /> 
                                <html:hidden property="hdnModules" value="<%=(String)moduleId%>" />
                                <bean:define id="permissionIds" name="module"  property="pipedPermissionIds"/> 
                                <html:hidden property="hdnPermissions" value="<%=(String)permissionIds%>" />
                                <bean:define id="permissions" name="module" property="permissions" type="java.util.ArrayList" /> 
                                  <logic:iterate id="permission" name="permissions">
                                    <td >
                                      <table cellpading="0" cellspacing="0" style="margin-left:4px;">
                                        <tr>
                                          <td>
                                            <input id="permission<bean:write name="module" property="id" />" name="permission<bean:write name="module" property="id" />" type="checkbox" disabled >
                                          </td>
                                          <td>
                                            <bean:write name="permission" property="name" />
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </logic:iterate>
                              </tr>
                            </logic:iterate>
                          </table>
                        </div>
                        <!-- Div Tag for Modules and Permissions Starts Here-->                  
                      </td>
                    </tr>
                  </table>
                  <!--Access Control Table Ends Here-->
                </td>
              </tr>
              <tr>
                <td>
                  <!--Button Table Starts Here-->
                  <table width="100%"  border="0" align="center" >
                    <tr>
                      <td height="30px"><div align="right">
                        <html:cancel  styleClass="buttons"  tabindex="1" ><bean:message  key="btn.Ok" /></html:cancel>
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
                <td width="30px" ><div class="imgStatusMsg"></td>
                <td><html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
              </tr>
            </table>
            <!-- Status Bar Table Ends-->
          </td>    
        </tr>
      </table>
      <!--Main Table Ends Here-->
    </html:form>
  </BODY>
</HTML>
