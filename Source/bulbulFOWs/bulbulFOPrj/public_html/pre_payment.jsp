<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.PrePayment" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
</head>
<body>
<% 
//global  Variable for Address Display
boolean firstTime=true;
int rowSpanValue=1;
String oldCustomerAddressBookTblPk="";
%>
<bean:define id="orderHeaderBean" name="customerInfo" property="currentOrder" />
<bean:define id="isMultipleAddress" name="orderHeaderBean" property="isMultipleAddress" />

  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.PrePayment" /> :</td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">
        <!-- Order Header Table Starts -->
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
          <tr bgcolor="#FFFFFF">
            <td colspan="4" align="center" height="30px">
            <html:button style="width:130px; height:20px;" value="Proceed To Payment" property="btnProceed" onclick="window.location='prePaymentAction.do';" styleClass="buttons"><bean:message key="btn.ProceedToPayment" /></html:button>
            </td>
          </tr>
        </table>
        <!-- Order Header Table Ends -->
	    </td>
	  </tr>
	  <tr>
	    <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 text_bold12">
        <!-- Order Detail Attached  With Shipping Address Table Starts --> 
        <table width="600px" border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_CC6A00" style="margin-bottom:15px ">
          <tr bgcolor="#FFFFFF">
            <td width="7%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.SlNo" /> </td>
            <td width="49%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.MerchandiseName" /> </td>
            <td width="44%" align="center" valign="bottom" class="th_F89627" ><bean:message key="tbl.ShippingAddresses" /></td>
          </tr>
          <bean:define id="orderDetailArrayList" name="orderHeaderBean" property="orderDetail" type="java.util.ArrayList" />
          <logic:iterate id="orderDetail" name="orderDetailArrayList" indexId="detailIndex">
          <tr bgcolor="#FFFFFF">
            <td align="center" valign="top"><%= detailIndex.intValue() + 1 %></td>
            <td align="center" valign="top">
              <!-- Order Item 1 Table Starts -->
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td align="center" class="text_008000"><bean:write name="orderDetail" property="designName" /> - <bean:write name="orderDetail" property="merchandiseName" /></td>
                </tr>
                <tr>
                  <td align="center">
                    <!-- Color for Order Item 1 Table Starts --> 
                    <table width="100%" height="18px" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="center">
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
                    <!-- Color for Order Item 1 Table Ends --> 
                  </td>
                </tr>
                <tr>
                  <td>
                   <bean:write name="orderDetail" property="merchandiseDesc" />
                  </td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <bean:define id="orderItemDetailArrayList" name="orderDetail" property="orderItemDetail" type="java.util.ArrayList" />
                <logic:iterate id="orderItemDetail" name="orderItemDetailArrayList"  indexId="itemIndex">
                <tr>
                  <td align="right" class="bdrTopColor_CC6A00">
                  <bean:write name="orderItemDetail" property="sizeTypeId" /> - <bean:write name="orderItemDetail" property="sizeId" /> 
                    :: <bean:write name="orderItemDetail" property="price" /> x <bean:write name="orderItemDetail" property="quantity" /> = <bean:write name="orderItemDetail" property="amount" />
                  </td>
                </tr>
                <logic:notEqual name="orderItemDetail" property="discount" value="-0.00">
                <tr>
                  <td align="right" style="color:#ff0000"><bean:write name="orderItemDetail" property="discount" /> % = - <bean:write name="orderItemDetail" property="less" /></td>
                </tr>
                </logic:notEqual>
                <tr>
                  <td align="right"><span class="text_008000"><bean:write name="orderItemDetail" property="total" /></span></td>
                </tr>
                </logic:iterate> 
                <tr>
                  <td align="right" class="bdrTopColor_CC6A00">
                    <b><bean:write name="orderDetail" property="orderDetailTotal" /></b>
                  </td>
                </tr>
              </table>
              <!-- Order Item 1 Table  Ends-->
            </td>
            <bean:define id="shippingAddress4OrderHeader" name="orderHeaderBean" property="shippingAddress" />
            <bean:define id="shippingAddress4OrderDetail" name="orderDetail" property="shippingAddress" />
            <bean:define id="customerAddressBookTblPk" name="shippingAddress4OrderDetail" property="customerAddressBookTblPk" type="java.lang.String" />
            <bean:define id="shippingAddressCount" name="orderDetail" property="shippingAddressCount" type="java.lang.String" />
            <% 
             if (isMultipleAddress.equals("1")){
              rowSpanValue=Integer.parseInt(shippingAddressCount);
              if(customerAddressBookTblPk.equals(oldCustomerAddressBookTblPk )){
                firstTime=false;
              }else{
                firstTime=true;
              }
              oldCustomerAddressBookTblPk=customerAddressBookTblPk;
             }else{
              rowSpanValue=orderDetailArrayList.size();
             }    
             
              if (firstTime) {
              firstTime=false;
            %>
            <td align="center" rowspan="<%=rowSpanValue%>"  >
              <!-- Shipping Address for Order Item 1 Starts -->
              <table width="95%"  border=0 cellpadding=1 cellspacing=1 class="bdrColor_CC6A00 bgColor_FFEDDA">
                <tr>
                  <td   valign="top">
                    <logic:equal name="isMultipleAddress" value="0" >
                      <bean:write name="shippingAddress4OrderHeader" property="fullName" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="addressLine1" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="addressLine2" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="city" /> - <bean:write name="shippingAddress4OrderHeader" property="pincode" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="state" /> - <bean:write name="shippingAddress4OrderHeader" property="country" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="phoneNumber" /><br>
                      <bean:write name="shippingAddress4OrderHeader" property="emailId" /><br>
                    </logic:equal>
                    <logic:equal name="isMultipleAddress"  value="1" >
                      <bean:write name="shippingAddress4OrderDetail" property="fullName" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="addressLine1" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="addressLine2" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="city" /> - <bean:write name="shippingAddress4OrderDetail" property="pincode" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="state" /> - <bean:write name="shippingAddress4OrderDetail" property="country" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="phoneNumber" /><br>
                      <bean:write name="shippingAddress4OrderDetail" property="emailId" /><br>
                    </logic:equal>
                    
                  </td>
                </tr>
              </table>
              <!-- Shipping Address for Order Item 1 Ends -->
            </td>
            <%
              }
            %>
          </tr>
          </logic:iterate>
        </table>
        <!-- Order Detail Attached  With Shipping Address Table Ends --> 
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
