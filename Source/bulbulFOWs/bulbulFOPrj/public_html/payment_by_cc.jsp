<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.Outlet" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<html:javascript formName="paymentByCCForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
</head>
<body  style="margin:0px">
  <table bgcolor="#FFFFFF" width=100% height=100%  align="center" border=0 cellspacing="1" cellpadding="1">
    <tr>
      <td colspan="2">
        <span class="text_008000"><bean:message key="info.PayByCreditCard" /></span></td>
      <td width="49%" rowspan="7" align="center"><img src="images/creditcards_collage.gif" width="199px" height="68px"> </td>
    </tr>
    <html:form action="paymentByCCAction.do" onsubmit="this.target='_parent'; return validatePaymentByCCForm(this);" >
    <tr>
      <td width="24%" align="right"> <bean:message key="cbo.CardType" />:</td>
      <td width="27%">
        <html:select property="cboCCType" style="width:150px">
          <html:option value="Visa" >Visa</html:option>
          <html:option value="American Express" >American Express</html:option>
          <html:option value="ICICI" >ICICI</html:option>
          <html:option value="Citi Bank">Citi Bank</html:option>
        </html:select> 
      </td>
    </tr>
    <tr>
      <td align="right"><bean:message key="txt.CardNumber" />:</td>
      <td>
        <html:text property="txtCCNumber" style="width:150px" onkeypress="return integerOnly(event)" maxlength="16" />
      </td>
    </tr>
    <tr>
      <td align="right"><bean:message key="txt.CVV/CVCCode" />:</td>
      <td>
        <html:text property="txtCVVCode4CC"  style="width:150px" onkeypress="return integerOnly(event)" maxlength="3" />
      </td>
    </tr>
    <tr>
      <td align="right"><bean:message key="dt.ExpireYear" /></td>
      <td>
        <html:select property="cboExpMonth4CC" style="width:73px">
          <html:option value="1">Jan</html:option>
          <html:option value="2">Feb</html:option>
          <html:option value="3">Mar</html:option>
          <html:option value="4">Apr</html:option>
          <html:option value="5">May</html:option>
          <html:option value="6">Jun</html:option>
          <html:option value="7">Jul</html:option>
          <html:option value="8">Aug</html:option>
          <html:option value="9">Sep</html:option>
          <html:option value="10">Oct</html:option>
          <html:option value="11">Nov</html:option>
          <html:option value="12">Dec</html:option>
        </html:select>
        <html:select property="cboExpYear4CC" style="width:73px">
          <logic:iterate id="year" name="years" >
            <html:option value="<%=(String)year%>" ><%=(String)year%></html:option>
          </logic:iterate>
        </html:select>
      </td>
    </tr>
    <tr>
      <td align="right"><bean:message key="txt.NameOfCardHolder" />:</td>
      <td>
        <html:text property="txtCCHolderName"  style="width:150px" />
      </td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="right">
        <html:submit property="btnClick2Pay" styleClass="buttons" style="width:90px; margin-right:8px" onclick="" ><bean:message key="btn.ClickToPay" /></html:submit>
      </td>
    </tr>
    </html:form>
  </table>
  
</body>
</html>
