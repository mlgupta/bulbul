<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="userRights" name="userProfile" property="userRights" />        

<link href="main.css" rel="stylesheet" type="text/css">
<!-- Logo Table Starts -->
  <table id="tblLogo" width="100%"  border="0" cellspacing="0" cellpadding="0" class="bdrBottomColor_333333">
    <tr>
      <td background="images/tile5x41.gif" height="41px"><img src="images/admin_logo.jpg" width="220px" height="41px"></td>
      <td background="images/tile5x41.gif">
        <span class="menuDisabled" >
          &nbsp;&nbsp;<bean:message key="info.Welcome" />&nbsp;<bean:write name="userProfile" property="userName" />
        </span> 
      </td>
      <td align="right" background="images/tile5x41.gif">
        <a class="menuLink" href="main.jsp"><bean:message key="menu.link.Home" /></a>&nbsp;|
        <a class="menuLink" href="userProfileRelayAction.do?hdnSearchOperation=preferences"><bean:message key="menu.link.Preferences" /></a>&nbsp;|
        <a class="menuLink" href=""><bean:message key="menu.link.Help" /></a>&nbsp;|
        <a class="menuLink" href="logoutAction.do"><bean:message key="menu.link.Logout" /></a>&nbsp;&nbsp;
      </td>
    </tr>
    <tr>
      <td height="20px"  colspan="3" align="right" style="color:#333333" >
        <logic:iterate id="module" name="userRights">  
          <!-- User Link --> 
          <logic:equal name="module" property="name" value="User">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="userProfileRelayAction.do?hdnSearchOperation=list&hdnUserCategoryId=-1"><bean:message key="menu.module.User" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Merchandise Link --> 
          <logic:equal name="module" property="name" value="Merchandise">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="merchandiseRelayAction.do?hdnSearchOperation=merchand_list"><bean:message key="menu.module.Merchandise" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>

          <!-- Clipart Link --> 
          <logic:equal name="module" property="name" value="Clipart">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="clipartRelayAction.do?hdnSearchOperation=clipart_list"><bean:message key="menu.module.Clipart" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Font Link --> 
          <logic:equal name="module" property="name" value="Font">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="fontRelayAction.do?hdnSearchOperation=font_list"><bean:message key="menu.module.Font" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>

          <!-- Internet Bank Link --> 
          <logic:equal name="module" property="name" value="Internet Bank">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="inetBankRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.InternetBank" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Outlet Link --> 
          <logic:equal name="module" property="name" value="Outlet">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="outletRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Outlet" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Size Type Link --> 
          <logic:equal name="module" property="name" value="Size Type">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="sizeTypeRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.SizeType" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Size Link --> 
          <logic:equal name="module" property="name" value="Size">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="sizeRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Size" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Decoration Link --> 
          <logic:equal name="module" property="name" value="Decoration">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="merchandiseDecorationRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Decoration" /></a>&nbsp;|
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
          <!-- Printable Area Link --> 
          <logic:equal name="module" property="name" value="Printable Area">
            <logic:iterate id="permission" name="module" property="permissions">  
              <logic:equal name="permission" property="name" value="View">
                <logic:equal name="permission" property="value" value="1">
                  <a class="menuLink" href="printableAreaRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.PrintableArea" /></a>&nbsp;
                </logic:equal>
              </logic:equal>
            </logic:iterate>  
          </logic:equal>
          
        </logic:iterate>
      </td>
    </tr>
  </table>
<!-- Logo Table Ends -->  
<logic:iterate id="module" name="userRights">  
  <!--<bean:write name="module" property="name" />-->
  <logic:iterate id="permission" name="module" property="permissions">  
    <!--<bean:write name="permission" property="name" />-->
  </logic:iterate>  
</logic:iterate>
 
<table border="0" cellspacing="0" cellpadding="0">
  <tr><td height="10px"></td></tr>
</table>


  
