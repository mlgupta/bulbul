<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.Home" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="home.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- Header Table Starts-->
<table width="770px" height="75px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="361px" valign="bottom">
      <table width="361px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="163px"><div align="right"><img src="images/logo_home.gif" alt="Home Icon" width="141px" height="62px" border="0"></div></td>
          <td width="30px">&nbsp;</td>
          <td width="45px"><div align="center"><a href="studioAction.do?studioFrameSrc=shoppingCartAction.do" ><img src="images/cart.gif" alt="Shopping Cart" width="36px" height="36px" border="0" title="Shopping Cart"></a></div></td>
          <td width="45px"><div align="center"><a href="#"><img src="images/mailus.gif" alt="Mail Us" width="36px" height="36px" border="0" title="Mail Us"></a></div></td>
          <td width="45px"><div align="center"><a href="#"><img src="images/callus.gif" alt="Call Us" width="36px" height="36px" border="0" title="Call Us"></a></div></td>
          <td width="30px">&nbsp;</td>
        </tr>
      </table>
    </td>
    <td width="409px" valign="bottom">
      <table width="409px" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="75px"><img src="images/tab_home_active.gif" alt="Home" width="73px" height="39px"></td>
          <td width="75px"><a href="catalogue.jsp"><img src="images/tab_catalogue.gif" alt="Catalogue" width="73px" height="39px" border="0"></a></td>
          <td width="75px"><a href="studioAction.do?studioFrameSrc=productCustomiseB4Action.do"><img src="images/tab_studio.gif" alt="Studio" width="73px" height="39px" border="0"></a></td>
          <td width="84px"><a href="myPrintoonAction.do?myPrintoonFrameSrc=myprintoon_intro.jsp"><img src="images/tab_myprtn.gif" alt="My Printoon" width="82px" height="39px" border="0"></a></td>
          <td width="75px"><a href="helpdsk.jsp"><img src="images/tab_hlpdsk.gif" alt="Help Desk" width="73px" height="39px" border="0"></a></td>
          <td width="25px">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- Header Table Ends -->
<!-- Color Bar Table Starts -->
<table width="100%" height="45"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="47%" background="images/home_tile_grad45px.gif">
      <table width="770px" height="45px" border="0" align="center" cellspacing="0" cellpadding="0">
        <tr>
          <td width="361px"><img src="images/enjoy_text.gif" width="220px" height="25px"></td>
          <td width="409px">
            <table width="409px" height="45px" border="0" align="right" cellpadding="0" cellspacing="0">
              <tr>
                <td width="75px" background="images/home_tile_45px.gif">&nbsp;</td>
                <td width="75px">&nbsp;</td>
                <td width="75px">&nbsp;</td>
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
<!-- Content Table Starts -->
<table width="770px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25px" colspan="2"  >
      <!-- Login Table Starts -->
      <logic:equal name="customerInfo" property="customerRegistered" value="0" >
        <html:form action="/homeLoginAction" onsubmit="this.action='homeLoginAction.do'">
          <table  width="100%" border="0" cellspacing="1" cellpadding="1" style="margin-left:8px" >
            <tr>
              <td>
                <div align="right"><bean:message key="txt.LoginEmailId" />:</div>
              </td>
              <td>
                <html:text styleClass="bdrColor_6054BB"  property="txtLoginEmailId" style="width:100px" value="" />
              </td>
              <td>
                <div align="right"><bean:message key="txt.LoginPassword" />:</div>
              </td>
              <td>
                <html:password  styleClass="bdrColor_6054BB" property="txtLoginPassword" style="width:100px" value="" />
              </td>
              <td >
                <img src="images/btn_signin.gif" width="73px" height="15px" border="0" onclick="document.forms[0].submit();" style="cursor:pointer;cursor:hand">
              </td>
              <td >
                  &nbsp; &nbsp; &nbsp;<bean:message key="info.SignIn.Part1" /> <a class="linkMenu" href="myPrintoonAction.do?myPrintoonFrameSrc=customerRegisterLoginAction.do?operation=register">&nbsp;<bean:message key="info.SignIn.Part2" />&nbsp;</a> <bean:message key="info.SignIn.Part3" />
              </td>
            </tr>
          </table>
        </html:form>
      </logic:equal>
      <logic:equal name="customerInfo" property="customerRegistered" value="1" >
        <span style="margin-left:8px">
          <table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>
                <bean:message key="msg.Welcome.Part1" />
                <span class="text_6054BB" >'<bean:write name="customerInfo" property="customerFirstName" />'.</span> 
                <bean:message key="msg.Welcome.Part2" /> 
                <span class="text_6054BB" ><bean:write name="customerInfo" property="customerEmailId" /></span> 
                <bean:message key="msg.Welcome.Part3" /> 
              </td>
              <td>
                <a  href="homeLogoutAction.do" ><img src="images/btn_signout.gif" width="73px" height="15px" border="0" style="margin-left:2px;cursor:pointer;cursor:hand"></a>
              </td>
            </tr>
          </table>
        </span>
      </logic:equal>
    <!-- Login Table Ends -->
    </td>
    <td width="195px">
      <div align="center">
        <table border="0" cellpadding="0" cellspacing="1">
          <tr>
            <td>
              <a  href="studioAction.do?studioFrameSrc=trackB4Action.do?operation=design"><img src="images/btn_track_design.gif" width="73px" height="15px" border="0" ></a>&nbsp;&nbsp;
            </td>
            <td>
              <a  href="studioAction.do?studioFrameSrc=trackB4Action.do?operation=order"><img src="images/btn_track_order.gif" width="73px" height="15px" border="0"></a>&nbsp;&nbsp;              
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="3" height="15px" align="center" >
      <!-- Login Error Layer Starts-->
      <div style="color:#ff0000">
        <b>
          <html:messages id="messageLoginFailed" message="true"  property="messageLoginFailed" >
            <bean:write name="messageLoginFailed" />
          </html:messages>
        </b>
      </div>                
      <!-- Login Error Layer Ends -->
    </td>
  </tr>  
  <tr>
    <td width="195px" >
      <div align="right">
        <table id="pan" width="186px" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="24px" height="17px"><img src="images/home_pan_corner_lt.gif" width="24px" height="17px"></td>
            <td width="157px" background="images/home_pan_tile17px.gif" class="heading_pan"><bean:message key="tbl.NewToPrintoon" /></td>
            <td width="5px"><img src="images/home_pan_corner_rt.gif" width="5px" height="17px"></td>
          </tr>
          <tr valign="top">
            <td height="183px" colspan="3" bgcolor="#FFFFFF" class="bdrLeftColor_6054BB bdrRightColor_6054BB bdrTopColor_6054BB">
              <table width="100%" height="183px"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="54px" width="16%"><div align="right"><img src="images/btn_1.gif" width="16px" height="16px"></div></td>
                  <td width="84%">&nbsp;</td>
                </tr>
                <tr>
                  <td height="54px"><div align="right"><img src="images/btn_2.gif" width="16px" height="16px"></div></td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td height="54px"><div align="right"><img src="images/btn_3.gif" width="16px" height="16px"></div></td>
                  <td >&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><div align="right"><a href="#"><img src="images/btn_see_demo.gif" width="73px" height="15px" border="0" style="margin-right:5px"></a></div></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="3px"><img src="images/home_pan_corner_lb.gif" width="24px" height="3px"></td>
            <td background="images/home_pan_tile3px.gif"></td>
            <td><img src="images/home_pan_corner_rb.gif" width="5px" height="3px"></td>
          </tr>
        </table>
      </div>	
    </td>
    <td width="380px" ><div align="center"><img src="images/home_occasion.gif" width="335px" height="200px"></div></td>
    <td width="195px" >
      <table id="pan" width="186px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="24px" height="17px"><img src="images/home_pan_corner_lt.gif" width="24px" height="17px"></td>
          <td width="157px" background="images/home_pan_tile17px.gif" class="heading_pan"><bean:message key="tbl.CustomerReviews" />:</td>
          <td width="5"px><img src="images/home_pan_corner_rt.gif" width="5px" height="17px"></td>
        </tr>
        <tr>
          <td height="183px" align="center" valign="top" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_6054BB bdrRightColor_6054BB bdrTopColor_6054BB">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td height="3px"><img src="images/home_pan_corner_lb.gif" width="24px" height="3px"></td>
          <td background="images/home_pan_tile3px.gif"></td>
          <td><img src="images/home_pan_corner_rb.gif" width="5px" height="3px"></td>
        </tr>
      </table>	
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right">
      <table id="pan" width="186px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="24px" height="17px"><img src="images/home_pan_corner_lt.gif" width="24px" height="17px"></td>
          <td width="157px" background="images/home_pan_tile17px.gif" class="heading_pan"><bean:message key="tbl.News" />:</td>
          <td width="5px"><img src="images/home_pan_corner_rt.gif" width="5px" height="17px"></td>
        </tr>
        <tr>
          <td height="183px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_6054BB bdrRightColor_6054BB bdrTopColor_6054BB">&nbsp;</td>
        </tr>
        <tr>
          <td height="3px"><img src="images/home_pan_corner_lb.gif" width="24px" height="3px"></td>
          <td background="images/home_pan_tile3px.gif"></td>
          <td><img src="images/home_pan_corner_rb.gif" width="5px" height="3px"></td>
        </tr>
      </table>	
    </td>
    <td valign="top">
      <div align="right">
        <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><img src="images/home_products.gif" width="335px" height="100px"></div></td>
          </tr>
          <tr>
            <td height="100px" valign="bottom"><div align="right"><a href="#"><img src="images/logo_varisign.gif" width="70px" height="70px" border="0"></a></div></td>
          </tr>
        </table>
      </div>
    </td>
    <td>
      <table id="pan" width="186px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="24px" height="17px"><img src="images/home_pan_corner_lt.gif" width="24px" height="17px"></td>
          <td width="157px" background="images/home_pan_tile17px.gif" class="heading_pan"><bean:message key="tbl.FeaturedCategories" />: </td>
          <td width="5px"><img src="images/home_pan_corner_rt.gif" width="5px" height="17px"></td>
        </tr>
        <tr>
          <td height="183px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_6054BB bdrRightColor_6054BB bdrTopColor_6054BB">&nbsp;</td>
        </tr>
        <tr>
          <td height="3px"><img src="images/home_pan_corner_lb.gif" width="24px" height="3px"></td>
          <td background="images/home_pan_tile3px.gif"></td>
          <td><img src="images/home_pan_corner_rb.gif" width="5px" height="3px"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<!-- Content Table Ends -->
<!-- Footer Table Starts -->
<table width="100%" height="14px" border="0" style="position:absolute; bottom:0;" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/home_tile_14px.gif"><div align="center" class="text_copyright">Copy Right mesage Here</div></td>
  </tr>
</table>
<!-- Footer Table Ends -->


</body>
</html>
