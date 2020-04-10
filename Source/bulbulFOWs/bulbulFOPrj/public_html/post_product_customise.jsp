<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="customerDesign" name="customerInfo" property="customerDesign"  />
<html>
<head>
<title><bean:message key="title.PostProductCustomise" /> </title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script>
<!--
  function btnBuy_onclick(){
    window.location='orderCreateB4Action.do?customerDesignTblPk=<bean:write name="customerDesign" property="customerDesignTblPk" />';    
  }
//-->
</script>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.PostProductCustomise" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">
        <!-- Customized Product Image and Size & Qty Table Starts -->
        <table width="100%"  border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td align="center"  valign="top">
              <!-- Customized Product Image Table Starts -->
              <table width="99%"  border="0" cellpadding="1" cellspacing="1">
                <tr><td height="10px" ></td></tr>
                <tr>
                  <td width="100%" height="20px" >
                    <div align="center">
                      <bean:message key="info.SavedDesign.Part1" /> <span class="text_CC6A00" ><bean:write name="customerDesign" property="designName" /></span> <bean:message key="info.SavedDesign.Part2" /> <br>
                      <bean:message key="info.SavedDesign.Part3" /> <span class="text_CC6A00" ><bean:write name="customerDesign" property="designCode" /></span>.                       
                    </div>
                  </td>
                </tr>
                <tr><td height="10px" ></td></tr>
                <tr>
                  <td height="20px" align="center">
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
                          <bean:message key="lbl.DesignCode" /> :
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
                  <td align="center" valign="top"> 
                    <html:button styleClass="buttons" style="width:85px; height:20px;" property="btnBuy" onclick="return btnBuy_onclick();"><bean:message key="btn.Buy" /></html:button>
                    <html:button styleClass="buttons" style="width:125px; height:20px;" property="btnShopping" onclick="window.parent.location='catalogue.jsp'"><bean:message key="btn.ContinueShopping" /></html:button>
                    <html:button styleClass="buttons" style="width:85px; height:20px;" property="btnCustomise" onclick="window.parent.location='studioAction.do?studioFrameSrc=design_engine.jsp';" ><bean:message key="btn.Customise" /></html:button>                    
                  </td>
                </tr>
                <tr><td height="10px" ></td></tr>
              </table>
              <!-- Customized Product Image Table Ends -->
            </td>            
          </tr>
        </table>
        <!-- Customized Product Image and Size & Qty Table Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/studio_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/studio_pan_tile3px.gif"></td>
      <td><img src="images/studio_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</body>
</html>
