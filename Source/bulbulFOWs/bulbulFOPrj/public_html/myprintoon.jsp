<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.MyPrintoon" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
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
                <td width="163px"><div align="right"><a href="home.jsp"><img src="images/logo_myprntn.gif" alt="Home Icon" width="141px" height="62px" border="0"></a></div></td>
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
            <td width="75px"><a href="studioAction.do?studioFrameSrc=productCustomiseB4Action.do"><img src="images/tab_studio.gif" alt="Studio" width="73px" height="39px" border="0"></a></td>
            <td width="84px"><img src="images/tab_myprtn_active.gif" alt="My Printoon" width="82px" height="39px" border="0"></td>
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
          <td width="47%" background="images/myprntn_tile_grad45px.gif">
            <table width="770px" height="45px" border="0" align="center" cellspacing="0" cellpadding="0">
              <tr>
                <td width="361px" align="right">
                <a class="tree" href="customerProfileB4Action.do" target="framMyPrintoon"><bean:message key="lnk.MyProfile" /></a>
                &nbsp; &nbsp; <a class="tree" href="myGraphicsListAction.do" target="framMyPrintoon"><bean:message key="lnk.MyGraphics" /></a>
                &nbsp; &nbsp; <a class="tree" href="myCreativityListAction.do" target="framMyPrintoon"><bean:message key="lnk.MyCreativity" /></a>
                &nbsp; &nbsp; <a class="tree" href="myFavouriteListAction.do" target="framMyPrintoon"><bean:message key="lnk.MyFavorites" /></a>
                </td>
                <td width="409px" >
                  <table width="409px" height="45px" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tr>
                      <!-- 
                      <td width="75px">&nbsp;</td>
                      <td width="75px">&nbsp;</td> 
                      <td width="75px">&nbsp;</td>
                      <td width="84px" background="images/myprntn_tile_45px.gif">&nbsp;</td>
                      <td width="75px">&nbsp;</td>
                      <td width="25px">&nbsp;</td>
                      -->
                      <td colspan="3" width="225px">&nbsp; &nbsp;<a class="tree" href="myShippingAddressBookB4Action.do" target="framMyPrintoon"><bean:message key="lnk.MyAddressBook" /></a></td>
                      <td width="84px" background="images/myprntn_tile_45px.gif">&nbsp;</td>
                      <td colspan="2" width="100px" >&nbsp; &nbsp;<a class="tree" href="customerLogoutAction.do" target="framMyPrintoon"><bean:message key="lnk.Logout" /></a></td>                      
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
            <iframe id="framMyPrintoon" name="framMyPrintoon" src='<bean:write name="myPrintoonFrameSrc" />' width="100%" height="100%"  style="overflow:auto;position:relative; left:0px; top:0px;" frameborder="0">
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
          <td background="images/myprntn_tile_14px.gif"><div align="center">Copy Right mesage Here</div></td>
        </tr>
      </table>
      <!-- Footer Table Ends -->
    </td>
  </tr>
</table>
</body>
</html>