<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="customerDesign" name="customerInfo" property="customerDesign"  />
<html>
<head>
<title><bean:message key="title.TrackedDesign" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script>
<!--
  function btnBuy_onclick(){
    window.location='orderCreateB4Action.do?customerDesignTblPk=<bean:write name="customerDesign" property="customerDesignTblPk" />';    
  }
  function btnDelete_onclick(){  
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='deleteTrackedDesignAction.do?customerDesignTblPk=<bean:write name="customerDesign" property="customerDesignTblPk" />'
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
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="lbl.TrackedDesign" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">&nbsp;</td>
    </tr>
	  <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00">
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td width="57%" height="10px"></td>
          </tr>
          <tr>
            <td align="center">
            <div style="color:#ff0000">
                <b>
                  <html:messages id="messageDesignInOrder" message="true"  property="messageDesignInOrder" >
                    <bean:write name="messageDesignInOrder" />
                  </html:messages>
                </b>
              </div> 
            </td>
          </tr>
          <tr>
            <td height="20px">
              <div align="center">
                <bean:message key="info.TrackedDesign.Part1"/> <span class="text_CC6A00" ><bean:write name="customerDesign" property="designName" /></span> <bean:message key="info.TrackedDesign.Part2"/>. <br>
                <bean:message key="info.TrackedDesign.Part3"/>  <span class="text_CC6A00" ><bean:write name="customerDesign" property="designCode" /></span>.                       
              </div>
            </td>
          </tr>
          <tr>
            <td height="20px">
              <div align="center" >
                <img name="display" id="display" class="bdrColor_CC6A00" border="0" style="width:200px; height:200px; background-color:#FFFFFF" src='imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="customerDesign" property="designOId" />&imageContentType=<bean:write name="customerDesign" property="designContentType" />&imageContentSize=<bean:write name="customerDesign" property="designContentSize" />' ></img>
              </div>
            </td>
          </tr>
          <tr><td height="10px" ></td></tr>
          <tr>
            <td  valign="top" class="bdrTopColor_CC6A00 bdrBottomColor_CC6A00">
              <table style="margin-left:200px" border="0" cellpadding="2" cellspacing="1" >
                <tr><td height="5px"  colspan="2"></td></tr>
                <tr>
                  <td class="text_CC6A00"  align="right">
                    <bean:message key="lbl.ProductName" /> : 
                  </td>
                  <td>       
                    <bean:write name="customerDesign" property="productName" />
                  </td>
                </tr>
                <tr>
                  <td class="text_CC6A00" align="right">
                    <bean:message key="lbl.DesignName" /> :
                  </td>
                  <td>
                    <bean:write name="customerDesign" property="designName" />
                  </td>
                </tr>
                <tr>
                  <td class="text_CC6A00" align="right">
                    <bean:message key="lbl.DesignCode" />:
                  </td>
                  <td>
                    <bean:write name="customerDesign" property="designCode" />
                  </td>
                </tr>
                <tr><td height="5px"  colspan="2"></td></tr>
              </table>
            </td>
          </tr>
          <tr><td height="10px"  ></td></tr>
          <tr>
            <td height="20px">
              <div align="center">
                <html:button property="btnBuy" styleClass="buttons" style="width:64px; height:20px" onclick="return btnBuy_onclick();" ><bean:message key="btn.Buy" /></html:button>
                <html:button property="btnCustomise" styleClass="buttons" style="width:74px; height:20px" onclick="window.parent.location='studioAction.do?studioFrameSrc=design_engine.jsp';"  ><bean:message key="btn.Customise" /></html:button >
                <html:button property="btnDelete" styleClass="buttons" style="width:64px; height:20px"  onclick="return btnDelete_onclick();"><bean:message key="btn.Delete" /></html:button>
                <html:button property="btnTrackAgain" styleClass="buttons"  onclick="window.location='trackB4Action.do?operation=design';" ><bean:message key="btn.TrackAgain" /></html:button>
              </div>
            </td>
          </tr>
          <tr>
            <td height="20px"></td>
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
