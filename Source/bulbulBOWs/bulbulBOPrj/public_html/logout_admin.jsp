<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.Logout" /></TITLE>
    <link href="main.css" rel="stylesheet" type="text/css">
    <script language="javascript" >
      var theTimer;
      function window_onload(){
        theTimer=setTimeout('goLogin()',30000);
      }
      function goLogin(){
        clearTimeout(theTimer);
        window.location.replace("index.jsp");
      }
    </script>
  </HEAD>
  <BODY onload="return window_onload();">
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
            <table border="0" align="center" cellpadding="0" cellspacing="2" class="bdrColor_336666 bgColor_DFE7EC" >
              <tr>
                <td>
                  <table width="282px" border="0" align="center" cellpadding="2" cellspacing="2" style="background-color:#80A0B2" class="bdrColor_336666 ">
                    <tr><td >&nbsp; </td></tr>
                    <tr>
                      <th><bean:message key="info.Logout" /></th>
                    </tr>
                    <tr><td >&nbsp; </td></tr>
                    <tr>
                      <td align="center"><a class="menuLink" href="index.jsp" ><bean:message key="info.ReLogin" /></a></td>
                    </tr>
                    <tr><td >&nbsp;</td></tr>
                  </table>
                  <table cellpadding="0" cellspacing="0" border="0"><tr><td height="2px"><td><tr></table>
                  <!-- Status Bar Table Starts-->
                  <table id="tblStatusBar" align="center" width="282px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
                    <tr bgcolor="#FFFFFF">
                      <td width="30px" ><div class="imgStatusMsg"></td>
                      <td>
                        <logic:present parameter="sessionExpired" >
                          <div style="color:#ff0000"><b><bean:message key="info.SessionExpired" /></b></div>
                        </logic:present>
                      </td>
                    </tr>
                  </table>
                  <!-- Status Bar Table Ends-->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!--Main Table Ends Here-->
  </BODY>
</HTML>
