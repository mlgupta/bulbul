<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<title><bean:message key="title.TrackedDesignDeleted" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panTrackedDesign">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="lbl.TrackedDesignDeleted" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">&nbsp;</td>
    </tr>
	  <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00">
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="200px" align="center">
              <bean:message key="info.TrackedDesignDeleted.Part1"/><span class="text_CC6A00" ><bean:write name="designName" /> </span><bean:message key="info.TrackedDesignDeleted.Part2"/> <span class="text_CC6A00" ><bean:write name="designCode" /></span> <bean:message key="info.TrackedDesignDeleted.Part3"/> <br><br>
              <bean:message key="info.TrackedDesignDeleted.Part4"/><a class="linkMenu" href="trackB4Action.do?operation=design"><bean:message key="link.Here"/></a>
            </td>
          </tr>          
        </table>
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/studio_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/studio_pan_tile3px.gif"></td>
      <td><img src="images/studio_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
</body>
</html>
