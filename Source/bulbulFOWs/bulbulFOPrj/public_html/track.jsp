<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="title.TrackDesignOrder" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
</head>
<body>
<html:form action="/trackOrderAction.do">
  <input type="hidden" name="operation" id="operation" value="<bean:write name="operation" />">
  <!-- Seperator Table -->
  <table border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="10px"></td>
    </tr>
  </table>
  <!-- Order Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panOrderTrack">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="lbl.TrackOrder" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="35px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold14">
        &nbsp;&nbsp;<bean:message key="lbl.TrackOrder" />  <a href="#" onClick="MM_findObj('orderTrackDrop').style.display=='none'?MM_showHideLayers('orderTrackDrop','','show'):MM_showHideLayers('orderTrackDrop','','hide')"><bean:message key="link.Here" /></a>
      </td>
    </tr>
    <logic:equal name="operation" value="order">
    <tr id="orderTrackDrop" style="display:'';">
    </logic:equal>
    <logic:notEqual name="operation" value="order">
    <tr id="orderTrackDrop" style="display:none;">
    </logic:notEqual>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00">
        <table width="100%"  border="0">
          <tr>
            <td colspan="3" align="center" >
              <div style="color:#ff0000">
                <b>
                  <html:messages id="messageNoOrder" message="true"  property="messageNoOrder" >
                    <bean:write name="messageNoOrder" />
                  </html:messages>
                </b>
              </div>                
            </td>
          </tr>
          <logic:notEmpty name="customerInfo" property="customerEmailId" >
            <tr style="display:none;" >
              <td width="23%"><div align="right"><bean:message key="txt.YourEmailId" />: </div></td>
              <td width="200px">
                <bean:define id="customerEmailId4Order" name="customerInfo" property="customerEmailId" />
                <html:text property="txtYourEmailId" style="width:200px" value="<%=(String)customerEmailId4Order%>"></html:text>
              </td>
              <td ></td>
            </tr>
          </logic:notEmpty>
          <logic:empty name="customerInfo" property="customerEmailId" >
            <tr>
              <td width="23%"><div align="right"><bean:message key="txt.YourEmailId" />: </div></td>
              <td width="200px"><html:text property="txtYourEmailId" style="width:200px"></html:text></td>
              <td ></td>
            </tr>
          </logic:empty>
          <tr>
            <td width="23%"><div align="right"><bean:message key="txt.OrderCode" /></div></td>
            <td width="200px">
              <html:text property="txtOrderCode" style="width:200px" onkeypress="return integerOnly(event);" maxlength="6"></html:text>
            </td>
            <td ></td>
          </tr>
          <tr>
            <td height="30px" align="right" colspan="2">
              <html:button property="btnTrack" styleClass="buttons"  onclick="this.form.operation.value='order'; this.form.submit();" ><bean:message key="btn.Track" /></html:button>
            </td>
            <td></td>
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
</html:form>
<html:form action="/trackDesignAction.do">
  <input type="hidden" name="operation" id="operation" value="<bean:write name="operation" />">
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panDesignTrack">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="lbl.TrackDesign" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="35px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold14">
        <span class="text_bold14">&nbsp;&nbsp;<bean:message key="lbl.TrackDesign" />  </span><a href="#" onClick="MM_findObj('designTrackDrop').style.display=='none'?MM_showHideLayers('designTrackDrop','','show'):MM_showHideLayers('designTrackDrop','','hide')"><bean:message key="link.Here" /></a>
      </td>
    </tr>
    <logic:equal name="operation" value="design">
    <tr id="designTrackDrop" style="display:'';">
    </logic:equal>
    <logic:notEqual name="operation" value="design">
    <tr id="designTrackDrop" style="display:none;">
    </logic:notEqual>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00">
        <table width="100%"  border="0">
          <tr>
            <td colspan="3" align="center" >
              <div style="color:#ff0000">
                <b>
                  <html:messages id="messageNoDesign" message="true"  property="messageNoDesign" >
                    <bean:write name="messageNoDesign" />
                  </html:messages>
                </b>
              </div>                
            </td>
          </tr>
            <logic:notEmpty name="customerInfo" property="customerEmailId" >
              <tr style="display:none;" >
                <td width="23%"><div align="right"><bean:message key="txt.YourEmailId" />: </div></td>
                <td width="200px">
                  <bean:define id="customerEmailId4Design" name="customerInfo" property="customerEmailId" />
                  <html:text property="txtYourEmailId" style="width:200px" value="<%=(String)customerEmailId4Design%>"></html:text>
                </td>
                <td ></td>
              </tr>
            </logic:notEmpty>
            <logic:empty name="customerInfo" property="customerEmailId" >
              <tr>
                <td width="23%"><div align="right"><bean:message key="txt.YourEmailId" />: </div></td>
                <td width="200px"><html:text property="txtYourEmailId" style="width:200px"></html:text></td>
                <td ></td>
              </tr>
            </logic:empty>           
          <tr>
            <td width="23%"><div align="right"><bean:message key="txt.DesignCode" />:</div></td>
            <td width="200px"><html:text property="txtDesignCode" style="width:200px" maxlength="6"></html:text></td>
            <td></td>
          </tr>
          <tr>
            <td colspan="2" height="30px" align="right">
              <html:button property="btnTrack" styleClass="buttons"  onclick="this.form.operation.value='design';this.form.submit();" ><bean:message key="btn.Track" /></html:button>
            </td>
            <td ></td>
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
</html:form>
</body>
</html>
