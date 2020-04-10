<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.Outlet" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<html:javascript formName="paymentByCashAtCashPtForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<script>
<!--
var prefix='lyrOutlet';
var previousPt=null;
function lstCashPt_onchange(me){
  var currentPt=prefix+me.value;
  if(previousPt!=null){
    MM_showHideLayers(previousPt,'','hide',currentPt,'','show');
  }else{
    MM_showHideLayers(currentPt,'','show');
  }
  previousPt=currentPt;
}

//-->
</script>
</head>
<body  style="margin:0px">
<!-- Outlet List Table Starts -->
<table bgcolor="#FFFFFF" width=100% height=100% align="center" border="0" cellspacing="1" cellpadding="1">
<html:form action="paymentByCashAtCashPtAction.do" onsubmit="this.target='_parent'; return validatePaymentByCashAtCashPtForm(this);" >
  <tr>
    <td width="50%">
      <html:select  property="lstCashPt" style="width:100%" size="5" onchange="return lstCashPt_onchange(this);" >
        <logic:iterate id="outlet" name="outlets" >
          <option value="<bean:write name="outlet" property="outletTblPk" />" ><bean:write name="outlet" property="outletName" /></option>
        </logic:iterate>
      </html:select>                    
    </td>
    <td width="50%" align="center" rowspan="2" valign="top">
      <!-- Outlet Address Table Starts -->
      <logic:iterate id="outlet" name="outlets" >
      <div id="lyrOutlet<bean:write name="outlet" property="outletTblPk" />" style="display:none" >
        <table width="95%" height="90px"  border=0 cellpadding=1 cellspacing=1 class="bdrColor_CC6A00 bgColor_FFEDDA">
          <tr>
            <td valign="top">
              <table border="0" cellpadding="0" cellspacing="1">
                <tr>
                  <td>
                    <bean:write name="outlet" property="outletName" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:write name="outlet" property="address1" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:write name="outlet" property="address2" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:write name="outlet" property="address3" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:write name="outlet" property="city" /> - <bean:write name="outlet" property="pincode" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:write name="outlet" property="state" /> - <bean:write name="outlet" property="country" />
                  </td>
                </tr>
             </table>
            </td>
          </tr>
        </table>
      </div>
      </logic:iterate>
      <!-- Outlet Address Table Starts -->
    </td>
  </tr>
  <tr>
   <td align="right">
     <html:submit property="btnClick2Pay" styleClass="buttons" style="width:90px; margin-right:8px" onclick="" ><bean:message key="btn.ClickToPay" /></html:submit>
   </td>
  </tr>
  </html:form >
</table>
<!-- Outlet List Table Ends -->
</body>
</html>
