<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.MyPrintoonIntroduction" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Changepassword Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyPrintoonIntroduction" /></td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
    <tr>
      <td align="center" height="400px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">&nbsp;
            <a class="linkMenu" href="customerRegisterLoginAction.do?operation=register">&nbsp;<bean:message key="opt.Register" />&nbsp;</a> | 
            <a class="linkMenu" href="customerRegisterLoginAction.do?operation=login">&nbsp;<bean:message key="opt.SignIn" />&nbsp;</a> 
            
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Changepassword Table Ends -->
</body>
</html>
