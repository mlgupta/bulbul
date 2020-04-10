<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<bean:define id="targetForward" name="targetForward" />
<html>
<head>
<title><bean:message key="title.CustomerRegisterSuccess" /></title>
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
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.CustomerRegisterSuccess" /></td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>

    <tr>
      <td align="center" height="400px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">&nbsp;
      <bean:message key="info.CustomerRegisterSuccess" /><br>
      <logic:empty name="targetForward" >
        <a class="linkMenu" target="_parent" href="home.jsp">&nbsp;Continue&nbsp;</a> 
      </logic:empty> 
      <logic:notEmpty name="targetForward" >
        <a class="linkMenu" href="<bean:write name="targetForward" />">&nbsp;Continue&nbsp;</a> 
      </logic:notEmpty> 
      
            <!--
            <a class="linkMenu" href="customerRegisterLoginAction.do?operation=register">&nbsp;Register&nbsp;</a> | 
            <a class="linkMenu" href="customerRegisterLoginAction.do?operation=login">&nbsp;Sign-In&nbsp;</a> |
            <a class="linkMenu" href="customerRegisterLoginAction.do?operation=revisit">&nbsp;Revisting?&nbsp;</a> &nbsp;
            --> 
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
