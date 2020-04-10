<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<title><bean:message key="title.TrackedOrder" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script>
<!--
function btnShowOrder_onclick(){
  if(<bean:write name="orderStatusCheck"/>==<bean:write name="orderBean" property="orderStatus" />){
    window.location='showOrderAction.do?orderHeaderTblPk=<bean:write name="orderBean" property="orderHeaderTblPk" />';
  }else{
    alert('<bean:message key="alert.OrderIsRegistered" />');
  }
} 
function btnDelete_onclick(){
  if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
    return false;
  }
  if(<bean:write name="orderStatusCheck"/>==<bean:write name="orderBean" property="orderStatus" />){
    window.location='deleteTrackedIncompleteOrderAction.do?orderHeaderTblPk=<bean:write name="orderBean" property="orderHeaderTblPk" />&orderCode=<bean:write name="orderBean" property="orderCode" />&orderGenDate=<bean:write name="orderBean" property="orderGenDate" />';
  }else{
    alert('<bean:message key="alert.OrderIsRegistered" />');
  }
}
//-->
</script>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panTrackedDesign">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="lbl.TrackedOrder" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">&nbsp;</td>
    </tr>
	  <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00">
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          
          <tr>
            <td align="center" >
              <bean:message key="info.TrackedOrder" /> : <span class="text_CC6A00"><bean:write name="orderBean" property="orderCode" /></span>  
            </td>
          </tr>
          <tr><td height="10px"></td></tr>
          <tr>
            <td align="center">
              <table width="250px"  border="0" cellspacing="1" cellpadding="2" class="bgColor_CC6A00">
                <tr>
                  <td align="right" class="th_F89627" width="45%"><bean:message key="tbl.OrderCode" /></td>
                  <td width="55%" bgcolor="#ffffff"><bean:write name="orderBean" property="orderCode" /></td>
                </tr>
                <tr>
                  <td align="right" class="th_F89627" ><bean:message key="tbl.OrderGenDate" /></td>
                  <td bgcolor="#ffffff"><bean:write name="orderBean" property="orderGenDate" /></td>
                </tr>                
                <tr>
                  <td align="right" class="th_F89627" ><bean:message key="tbl.OrderPlacedDate" /></td>
                  <td bgcolor="#ffffff"><bean:write name="orderBean" property="orderPlacedDate" /></td>
                </tr>
                <tr>
                  <td align="right" class="th_F89627" ><bean:message key="tbl.OrderItems" /></td>
                  <td bgcolor="#ffffff"><bean:write name="orderBean" property="orderItems" /></td>
                </tr>
                <tr>
                  <td align="right" class="th_F89627" ><bean:message key="tbl.OrderAmount" /></td>
                  <td bgcolor="#ffffff"><bean:write name="orderBean" property="orderAmount" /></td>
                </tr>
                <tr>                  
                  <td align="right" class="th_F89627" ><bean:message key="tbl.OrderStatus" /></td>
                  <td bgcolor="#ffffff"><bean:write name="orderBean" property="orderStatusString" /></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="40px" align="center" >
              <html:button property="btnShowOrder" styleClass="buttons"  onclick="return btnShowOrder_onclick();" ><bean:message key="btn.ShowOrder" /></html:button>
              <html:button property="btnDelete" styleClass="buttons" style="width:64px; height:20px"  onclick="return btnDelete_onclick();"><bean:message key="btn.Delete" /></html:button>
              <html:button property="btnTrackAgain" styleClass="buttons"  onclick="window.location='trackB4Action.do?operation=order';" ><bean:message key="btn.TrackAgain" /></html:button>
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
