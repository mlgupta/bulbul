<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<title><bean:message key="title.Studio" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- Main Table Starts-->
<table width="100%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td height="75px">
      <!-- Header Table Starts-->
      <table width="770px" height="75px" border="0" align="center" cellspacing="0" cellpadding="0">
        <tr>
          <td width="361px" valign="bottom">
            <table width="361px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="163px"><div align="right"><a href="home.jsp"><img src="images/logo_studio.gif" alt="Home Icon" width="141px" height="62px" border="0"></a></div></td>
                <td width="30px">&nbsp;</td>
                <td width="45px"><div align="center"><a href="studioAction.do?studioFrameSrc=shoppingCartAction.do"><img src="images/cart.gif" alt="Shopping Cart" width="36px" height="36px" border="0" title="Shopping Cart"></a></div></td>
                <td width="45px"><div align="center"><a href="#"><img src="images/mailus.gif" alt="Mail Us" width="36px" height="36px" border="0" title="Mail Us"></a></div></td>
                <td width="45px"><div align="center"><a href="#"><img src="images/callus.gif" alt="Call Us" width="36px" height="36px" border="0" title="Call Us"></a></div></td>
                <td width="30px">&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="409px" valign="bottom">
            <table width="409px" border="0" cellpadding="0" cellspacing="0">
              <tr>
              <td width="75px"><a href="home.jsp"><img src="images/tab_home.gif" alt="Home" width="73px" height="39px" border="0"></a></td>
              <td width="75px"><a href="catalogue.jsp"><img src="images/tab_catalogue.gif" alt="Catalogue" width="73px" height="39px" border="0"></a></td>
              <td width="75px"><img src="images/tab_studio_active.gif" alt="Studio" width="73px" height="39px" border="0"></td>
              <td width="84px"><a href="myPrintoonAction.do?myPrintoonFrameSrc=myprintoon_intro.jsp"><img src="images/tab_myprtn.gif" alt="My Printoon" width="82px" height="39px" border="0"></a></td>
              <td width="75px"><a href="helpdsk.jsp"><img src="images/tab_hlpdsk.gif" alt="Help Desk" width="73px" height="39px" border="0"></a></td>
              <td width="25px">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- Header Table Ends-->
    </td>
  </tr>
  <tr>
    <td height="45px">
      <!-- Color Bar Table Starts -->
      <table width="100%" height="45px"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="47%" background="images/studio_tile_grad45px.gif">
            <table width="770px" height="45px" border="0" align="center" cellspacing="0" cellpadding="0">
              <tr>
                <td width="361px">&nbsp;</td>
                <td width="409px">
                  <table width="409px" height="45px" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="75px">&nbsp;</td>
                      <td width="75px">&nbsp;</td>
                      <td width="75px" background="images/studio_tile_45px.gif">&nbsp;</td>
                      <td width="84px">&nbsp;</td>
                      <td width="75px">&nbsp;</td>
                      <td width="25px">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- Color Bar Table Ends -->
    </td>
  </tr>
  <tr>
    <td>      
      <!-- Content Table Starts -->
      <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>
              <iframe id="framStudio" name="framStudio" src='<bean:write name="studioFrameSrc" />' width="100%" height="100%"  style="overflow:auto;position:relative; left:0px; top:0px;" frameborder="0">
              </iframe>
          </td>
        </tr>
      </table>
      <!-- Content Table Ends -->
    </td>
  </tr>
  <tr>
    <td height="14px">
      <!-- Footer Table Starts -->
      <table width="100%" height="14px" border="0" style="position:absolute; bottom:0;" cellpadding="0" cellspacing="0">
        <tr>
          <td background="images/studio_tile_14px.gif"><div align="center">Copy Right mesage Here</div></td>
        </tr>
      </table>
      <!-- Footer Table Ends -->
    </td>
  </tr>
</table>
<!-- Main Table Ends-->
</body>
</html>
