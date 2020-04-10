<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><HTML>
<head>
<TITLE><bean:message key="title.AddToFavouriteSuccess" /></TITLE>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="catalogue.css" rel="stylesheet" type="text/css">
</head>
<body>
  <!-- Main Table Starts -->
  <table width="100%" height="100%"  border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td valign="middle">
        <!-- Sub Table Starts -->    
        <table width="300" height="50px" border="0" align="center" cellpadding="1" cellspacing="1">
          <tr>
            <td align="center" span class="text_007C94"><bean:message key="info.AddToFavouriteSuccess" /></td>
          </tr>
          <tr>
            <td align="center">
              <html:button styleClass="buttons" property="btnClose"  onclick="window.close();" ><bean:message key="btn.CloseWindow" /></html:button>
            </td>
          </tr>
        </table>
        <!-- Sub Table Starts --> 
      </td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</body>
</HTML>
