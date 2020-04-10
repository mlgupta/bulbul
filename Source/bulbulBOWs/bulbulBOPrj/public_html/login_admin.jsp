<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.Login" /></TITLE>
    <link href="main.css" rel="stylesheet" type="text/css">
    <script>window.name="printoon";</script>
  </HEAD>
  <BODY onload="document.forms[0].txtLoginId.focus();" >
    <html:form action="/loginAction" >
      <!--Main Table Starts Here-->
      <table width="100%" height="95%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td  valign="top">
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
        <tr>
          <td >
            <table width="292px" border="0" align="center" cellpadding="2" cellspacing="2" class="bdrColor_336666 bgColor_DFE7EC">
              <tr>
                <td>
                  <!--Login Table Starts Here-->
                  <table width="282px" border="0" align="center" cellpadding="2" cellspacing="2" class="bdrColor_336666 bgColor_DFE7EC">
                    <tr>
                      <td colspan="2">&nbsp; </td>
                      <td width="25px" rowspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="87px"><div align="right"><bean:message key="txt.LoginId" />: </div></td>
                      <td width="148px"><div align="right">
                        <html:text property="txtLoginId" styleClass="bdrColor_336666" style="width:145px"  />
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right">
                        <bean:message key="txt.LoginPassword" />:
                        </div>
                      </td>
                      <td><div align="right">
                        <html:password property="txtLoginPassword" styleClass="bdrColor_336666" style="width:145px" />
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td><div align="right">
                        <html:submit property="btnLogin"  styleClass="buttons"  style="width:70px" ><bean:message key="btn.Login" /></html:submit>
                        <html:reset property="btnReset" styleClass="buttons" style="width:70px"><bean:message key="btn.Reset" /></html:reset>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="3">&nbsp;</td>
                    </tr>
                  </table>
                  <!--Login Table Ends Here-->
                  <table cellpadding="0" cellspacing="0" border="0"><tr><td height="2px"><td><tr></table>
                  <!-- Status Bar Table Starts-->
                  <table id="tblStatusBar" align="center" width="282px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
                    <tr bgcolor="#FFFFFF">
                      <td width="30px" ><div class="imgStatusMsg"></div></td>
                      <td>
                        <div style="color:#ff0000"><b><html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></b></div>
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
    </html:form>  
  </BODY>
</HTML>
