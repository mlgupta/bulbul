<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="userRights" name="userProfile" property="userRights" />        
<html>
<head>
<title><bean:message key="title.PrintoonAdmin" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
</head>
<body>
<!--Main Table Starts Here-->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="10%" valign="top">
      <!--Image Table Starts Here-->
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="220px" height="41px"><img src="images/admin_logo.jpg" width="220px" height="41px"></td>
          <td background="images/tile5x41.gif">&nbsp;</td>
        </tr>
      </table>
      <!--Image Table Ends Here-->
    </td>
  </tr>
  <tr ><td></td></tr>
  <tr>
    <td height="90%">
      <table border="0" width="700px" height="500px" align="center" cellpadding="0" cellspacing="2" class="bdrColor_336666" >
        <tr>
          <td height="20px">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="left">
                  <span class="menuDisabled" >
                  &nbsp;&nbsp;<bean:message key="info.Welcome" />&nbsp;<bean:write name="userProfile" property="userName" />
                  </span> 
                </td>
                <td align="right">
                  <a class="menuLink" href="main.jsp"><bean:message key="menu.link.Home" /></a>&nbsp;|
                  <a class="menuLink" href="userProfileRelayAction.do?hdnSearchOperation=preferences"><bean:message key="menu.link.Preferences" /></a>&nbsp;|
                  <a class="menuLink" href=""><bean:message key="menu.link.Help" /></a>&nbsp;|
                  <a class="menuLink" href="logoutAction.do"><bean:message key="menu.link.Logout" /></a>&nbsp;&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td >
            <table border="0"  align="center" cellpadding="0" cellspacing="0"  >
              <logic:iterate id="module" name="userRights">  
                <!-- User Link --> 
                <logic:equal name="module" property="name" value="User">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="userProfileRelayAction.do?hdnSearchOperation=list&hdnUserCategoryId=-1"><bean:message key="menu.module.User" /></a>
                          </td>
                        <tr>  
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.User" /></div>
                          </td>
                        <tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
          
                <!-- Merchandise Link --> 
                <logic:equal name="module" property="name" value="Merchandise">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="merchandiseRelayAction.do?hdnSearchOperation=merchand_list"><bean:message key="menu.module.Merchandise" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Merchandise" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
                <!-- Printable Area Link --> 
                <logic:equal name="module" property="name" value="Printable Area">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="printableAreaRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.PrintableArea" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.PrintableArea" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>

                <!-- Clipart Link --> 
                <logic:equal name="module" property="name" value="Clipart">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                          <tr>
                          <td height="40px" align="left">
                              <a class="menuLink" href="clipartRelayAction.do?hdnSearchOperation=clipart_list"><bean:message key="menu.module.Clipart" /></a>
                            </td>
                          </tr>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Clipart" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
          
                <!-- Font Link --> 
                <logic:equal name="module" property="name" value="Font">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="fontRelayAction.do?hdnSearchOperation=font_list"><bean:message key="menu.module.Font" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Font" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>

                <!-- Internet Bank Link --> 
                <logic:equal name="module" property="name" value="Internet Bank">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="inetBankRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.InternetBank" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        </tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.InternetBank" /></div> 
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal> 
                  </logic:iterate>  
                </logic:equal>
          
                <!-- Outlet Link --> 
                <logic:equal name="module" property="name" value="Outlet">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="outletRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Outlet" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Outlet" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
          
                <!-- Size Type Link --> 
                <logic:equal name="module" property="name" value="Size Type">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="sizeTypeRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.SizeType" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.SizeType" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
                
                <!-- Size Link --> 
                <logic:equal name="module" property="name" value="Size">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="sizeRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Size" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        <tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Size" /></div>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
                
                <!-- Decoration Link --> 
                <logic:equal name="module" property="name" value="Decoration">
                  <logic:iterate id="permission" name="module" property="permissions">  
                    <logic:equal name="permission" property="name" value="View">
                      <logic:equal name="permission" property="value" value="1">
                        <tr>
                          <td height="40px" align="left">
                            <a class="menuLink" href="merchandiseDecorationRelayAction.do?hdnSearchOperation=list"><bean:message key="menu.module.Decoration" /></a>
                          </td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="permission" property="value" value="0">
                        </tr>
                          <td height="40px" align="left">
                            <div class="menuDisabled"><bean:message key="menu.module.Decoration" /></div> 
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                  </logic:iterate>  
                </logic:equal>
                
              </logic:iterate>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!--Main Table Ends Here-->
</body>
</html>
