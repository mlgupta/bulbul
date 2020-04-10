<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.ShippingAddressNew" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<html:javascript formName="shippingAddressForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<SCRIPT LANGUAGE=javascript>
<!--
  function stateCountrySelect(){
    if(document.forms[0].cboCountry.value!='0'){
      document.forms[0].txtCountry.value='';
      document.forms[0].hdnIsCountryListed.value='1';
      //alert("1"+document.forms[0].hdnIsCountryListed.value);
    }
    if(document.forms[0].cboState.value!='0'){
      document.forms[0].txtState.value='';
      document.forms[0].hdnIsStateListed.value='1';
      //alert("2"+document.forms[0].hdnIsStateListed.value);
    }
  }
  function stateCountryNew(){
    if(trim(document.forms[0].txtCountry.value).length!=0){
      document.forms[0].cboCountry.value='0';
      document.forms[0].hdnIsCountryListed.value='0';
      //alert("3"+document.forms[0].hdnIsCountryListed.value);
    }
    if(trim(document.forms[0].txtState.value).length!=0){
      document.forms[0].cboState.value='0';
      document.forms[0].hdnIsStateListed.value='0';
      //alert("4"+document.forms[0].hdnIsStateListed.value);
    }
  }
  
  function btnclick(){
    var thisForm=document.forms[0];

    thisForm.target="_self";
    if(validateShippingAddressForm(thisForm)){
      
      if(thisForm.cboCountry.value=='0' && trim(thisForm.txtCountry.value).length==0){
        alert("<bean:message key="alert.SelectCountry" />");
        return false;
      }
      if(thisForm.cboState.value=='0' && trim(thisForm.txtState.value).length==0){
        alert("<bean:message key="alert.SelectState" />");
        return false;
      }
      
      thisForm.submit();
    }
  }
//-->
</SCRIPT>
</head>
<body>
<html:form action="myAddressBookNewAction.do">
<html:hidden property="hdnCustomerEmailIdTblPk" />
<html:hidden property="hdnIsStateListed" />
<html:hidden property="hdnIsCountryListed" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ShippingAddressNew" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 text_bold12">
        <!-- Shipping Address Container Table Starts -->
        <table width="600px" border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_006600" style="margin-top:15px; margin-bottom:15px ">
          <tr bgcolor="#FFFFFF">
            <td width="6%" align="center" valign="bottom" class="th_0A8A09" >
              <bean:message key="tbl.EnterNewAddress" />
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td align="center" valign="top">
              <!-- Shipping Address Entry Table 1 Starts -->      
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td align="right">* <bean:message key="txt.FullName" /> :				</td>
                  <td><html:text property="txtFullName" style="width:478px" maxlength="100" /></td>
                </tr>
                <tr>
                  <td align="right" width="18%">* <bean:message key="txt.AddressLine1" /> :</td>
                  <td width="82%"><html:text property="txtAddressLine1" style="width:478px" maxlength="100" /></td>
                  </tr>
                <tr>
                  <td align="right"> <bean:message key="txt.AddressLine2" /> : </td>
                  <td><html:text property="txtAddressLine2" style="width:478px" maxlength="100" /></td>
                </tr>
              </table>
              <!-- Shipping Address Entry Table 1 Ends -->      
              <!-- Shipping Address Entry Table 2 Starts -->      
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td align="right">* <bean:message key="txt.City" /> : </td>
                  <td><html:text property="txtCity" style="width:200px" maxlength="50"/></td>
                  <td align="right">* <bean:message key="txt.Pincode" /> : </td>
                  <td><html:text  property="txtPincode" style="width:200px" onkeypress="return integerOnly(event);" maxlength="10"/></td>
                </tr>
                <tr>
                  <td align="right" width="18%">* <bean:message key="cbo.Country" /> :</td>
                  <td width="37%">
                  <html:select property="cboCountry" style="width:200px" onchange="stateCountrySelect();" onclick="stateCountrySelect();">
                  <html:option value="0">****Select Any Country****</html:option>
                  <html:option value="India">India</html:option>
                  </html:select>
                    &gt;</td>
                  <td width="10%" align="right"><bean:message key="lbl.Or" /> : </td>
                  <td width="35%"><html:text property="txtCountry" style="width:200px" onchange="stateCountryNew();" maxlength="50"/></td>
                </tr>
                <tr>
                  <td align="right">* <bean:message key="cbo.State" /> : </td>
                  <td>
                    <html:select property="cboState" style="width:200px" onchange="stateCountrySelect();" onclick="stateCountrySelect();">
                    <html:option value="0">****Select Any State****</html:option>
                    <html:option value="UttarPradesh" >UttarPradesh</html:option>
                    <html:option value="Maharashtra">Maharashtra</html:option>
                    </html:select>
                    &gt;
                  </td>
                  <td width="10%" align="right"><bean:message key="lbl.Or" /> : </td>
                  <td><html:text property="txtState" style="width:200px" onchange="stateCountryNew();" maxlength="50"/></td>
                </tr>
                <tr>
                  <td align="right">* <bean:message key="txt.Phone" /> : </td>
                  <td><html:text property="txtPhone" style="width:200px" maxlength="20"/></td>
                  <td align="right">* <bean:message key="txt.EmailId" /> : </td>
                  <td><html:text property="txtEmailId" style="width:200px" maxlength="100"/></td>
                </tr>
                <tr valign="bottom">
                  <td height="30px" colspan="4" align="center">
                    <html:button style="width:70px; height:20px" styleClass="buttons" property="btnSave" onclick="btnclick();"><bean:message key="btn.Save" /></html:button>
                    <html:cancel style="width:70px; height:20px" styleClass="buttons" ><bean:message key="btn.Cancel" /></html:cancel>
                  </td>
                </tr>
              </table>
              <!-- Shipping Address Entry 2 Table Ends -->      
            </td>
          </tr>
        </table>
        <!-- Shipping Address Container Table Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</html:form>
</body>
</html>
