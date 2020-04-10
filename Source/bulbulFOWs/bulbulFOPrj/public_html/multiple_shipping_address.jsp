<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.MultipleShippingAddress" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
  function btnContinue_onclick(){
    var thisForm=document.forms[0];
    thisForm.submit();
  }
  //-->
</script>
</head>
<bean:define id="orderHeaderBean" name="customerInfo" property="currentOrder" />
<body>
<html:form action="multipleAddressAction.do">
<!-- Seperator Table -->                                    
<table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MultipleShippingAddress" /> :</td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">
        <!-- Order Header Table Starts-->
        <table width="600px"  border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_CC6A00" style="margin-top:15px; margin-bottom:15px">
          <tr bgcolor="#FFFFFF">
            <td colspan="4" align="center" class="th_F89627"><bean:message key="tbl.MyOrder" /></td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td width="27%" align="right"><bean:message key="tbl.OrderCode" /> :</td>
            <td width="23%"><bean:write name="orderHeaderBean" property="orderCode" /></td>
            <td width="33%" align="right"><bean:message key="tbl.OrderAmount" /> :</td>
            <td width="17%" align="right"><span class="text_008000"><bean:write name="orderHeaderBean" property="orderAmount" /></span></td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td align="right"><bean:message key="tbl.OrderGenDate" /> :</td>
            <td><bean:write name="orderHeaderBean" property="orderGenDate" /></td>
            <td align="right"><bean:message key="tbl.EstimatedShippingCost" /> :</td>
            <td align="right">Rs.100.00</td>
          </tr>
        </table>
        <!-- Order Header Table Ends-->
      </td>
	  </tr>
	  <tr>
	    <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 text_bold12">
        <!-- Order Detail With Shipping Addresses -  Table Starts-->
        <table width="600px" border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_CC6A00" style="margin-bottom:15px ">
          <tr bgcolor="#FFFFFF">
            <td width="7%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.SlNo" /> </td>
            <td width="44%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.MerchandiseName" /> </td>
            <td width="7%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.Colors" /></td>
            <td width="42%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.ShippingAddresses" /></td>
          </tr>
          <bean:define id="orderDetailArrayList" name="orderHeaderBean" property="orderDetail" />
          <logic:iterate id="orderDetail" name="orderDetailArrayList" indexId="detailIndex">
          <!-- Order Item Row 1 Starts -->
          <tr bgcolor="#FFFFFF">
            <td align="center" valign="top">
            <%= detailIndex.intValue() + 1 %>
            </td>
            <td align="center" valign="top">
              <!-- Order Detail 1 Table  Starts --> 
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td align="center" class="text_008000"><bean:write name="orderDetail" property="designName" /> - <bean:write name="orderDetail" property="merchandiseName" /></td>
                  <bean:define id="orderDetailTblPk" name="orderDetail" property="orderDetailTblPk" />
                  <html:hidden property="hdnOrderDetailTblPk" value="<%=(String)orderDetailTblPk%>" />
                </tr>
                <tr>
                  <td><bean:write name="orderDetail" property="merchandiseDesc" /></td>
                </tr>
              </table>
              <!-- Order Detail 1 Table  Ends --> 
            </td>
            <td align="center" valign="top">
              <!-- Color of Product Table Starts  1 -->
              <table width="100%" height="18px" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td >
                    <div style="height:18px;width:36px;align:center" class="bdrColor_CC6A00">
                      <logic:notEqual  name="orderDetail" property="color2Name" value="">
                        <div style="height:18px;float:left;width:18px;background-color:<bean:write name="orderDetail" property="color1Value" />;"></div>
                        <div style="height:18px;float:left;width:18px;background-color:<bean:write name="orderDetail" property="color2Value" />;"></div>
                      </logic:notEqual>
                      <logic:equal  name="orderDetail" property="color2Name" value="">
                        <div style="height:18px;float:left;width:36px;background-color:<bean:write name="orderDetail" property="color1Value" />;"></div>
                      </logic:equal>
                    </div>                                    
                  </td>
                </tr>
              </table>
              <!-- Color of Product Table Ends  1 -->
            </td>
            <td align="center" valign="top">
              <html:select  property="cboShippingAddress"  style="width:240px">
                <logic:iterate id="address" name="addresses" >
                  <bean:define id="customerAddressBookTblPk" name="address" property="customerAddressBookTblPk" />
                  <html:option  value="<%=(String)customerAddressBookTblPk%>">
                    <bean:write name="address" property="fullName" /> ..... <bean:write name="address" property="city" />
                  </html:option>                
                </logic:iterate> 
              </html:select>
            </td>
          </tr>
          <!-- Order Item Row 1 Ends -->
          </logic:iterate> 
        </table>
        <!-- Order Detail With Shipping Addresses -  Table Ends-->
      </td>
    </tr>
	  <tr>
	    <td height="30px" align="center" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 text_bold12">
        <html:button styleClass="buttons" style="width:75px; height:20px" property="btnContinue" onclick="btnContinue_onclick();" title="Continue To Pay..." ><bean:message key="btn.Continue" /></html:button>
        <html:button styleClass="buttons" style="width:75px; height:20px" property="btnCancel" onclick="window.location='shipping_address.jsp';"><bean:message key="btn.Cancel" /></html:button>
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/studio_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/studio_pan_tile3px.gif"></td>
      <td><img src="images/studio_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
<!-- Main Table Ends -->
</html:form>
</body>
</html>
