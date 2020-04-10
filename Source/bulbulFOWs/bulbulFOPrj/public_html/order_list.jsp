<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><html>
<head>
<title><bean:message key="title.OrderList" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script>
  function btnRemoveDetail_onclick(orderDetailTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='orderDetailDeleteAction.do?orderDetailTblPk='+orderDetailTblPk;
  }
  function btnRemoveItemDetail_onclick(orderItemDetailTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='orderItemDetailDeleteAction.do?orderItemDetailTblPk='+orderItemDetailTblPk;
  }
</script>
</head>
<body>
<bean:define id="orderHeaderBean" name="customerInfo" property="currentOrder" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.OrderList" /> :</td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5" height="17px"></td>
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
              <html:button styleClass="buttons" style="width:130px; height:20px" property="btnShopping" onclick="window.parent.location='catalogue.jsp'"><bean:message key="btn.ContinueShopping" /></html:button>
              <logic:empty name="orderHeaderBean" property="orderDetail">
                <html:button styleClass="buttons" style="width:130px; height:20px" disabled="true" property="btnShipping" onclick="window.location='shippingAddressB4Action.do';" ><bean:message key="btn.ProceedToShipping" /></html:button>
              </logic:empty>
              <logic:notEmpty name="orderHeaderBean" property="orderDetail">
                <html:button styleClass="buttons" style="width:130px; height:20px" property="btnShipping" onclick="window.location='shippingAddressB4Action.do';" ><bean:message key="btn.ProceedToShipping" /></html:button>
              </logic:notEmpty>
            </td>
          </tr>
        </table>
        <!-- Order Header Table Ends -->
      </td>
	  </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 text_bold12">
        <!-- Order List Table Starts -->
        <table width="96%"  border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_CC6A00" style="margin-bottom:15px ">
          <tr bgcolor="#FFFFFF">
            <td width="6%" align="center" class="th_F89627"><bean:message key="tbl.SlNo" /></td>
            <td width="31%" align="center" class="th_F89627"><bean:message key="tbl.MerchandiseName" /></td>
            <td width="5%" align="center" class="th_F89627"><bean:message key="tbl.Color" /></td>
            <td width="58%" class="th_F89627">
              <!-- Order Detail Header Tbl Starts -->
              
              <table width="400px" border="0" cellspacing="0" cellpadding="0" class="bgColor_CC6A00">
                <tr bgcolor="#FFFFFF">
                  <td width="97px" align="center" class="th_F89627 bdrRightColor_CC6A00"><bean:message key="tbl.Size" /></td>
                  <td width="60px" align="center" class="th_F89627 bdrRightColor_CC6A00"><bean:message key="tbl.Quantity" /></td>
                  <td width="115px" align="center" class="th_F89627 bdrRightColor_CC6A00"><bean:message key="tbl.Price" /></td>
                  <td width="115px" align="center" class="th_F89627"><bean:message key="tbl.Total" /></td>
                </tr>
              </table>
              <!-- Order Detail Header Tbl Ends -->
            </td>
          </tr>
          <!-- Order Item 1 Row Starts -->
          <logic:empty name="orderHeaderBean" property="orderDetail">
            <tr bgcolor="#FFFFFF">
              <td colspan="4" align="center" class="text_CC6A00">
                <bean:message key="info.EmptyShoppingCart" />
              </td>
            </tr>
          </logic:empty>
          <logic:notEmpty name="orderHeaderBean" property="orderDetail">
          <bean:define id="orderDetailArrayList" name="orderHeaderBean" property="orderDetail" />
          <logic:iterate id="orderDetail" name="orderDetailArrayList" indexId="detailIndex">
          <tr bgcolor="#FFFFFF">
            <td align="center" valign="top"><%= detailIndex.intValue() + 1 %>
            <td valign="top">
            
              <!-- Item Description Table Starts -->
              <table width="100%" height="100%"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td align="center" height="20px"><a class="linkHeading" href="orderModifyB4Action.do?orderDetailTblPk=<bean:write name="orderDetail" property="orderDetailTblPk"/>"><bean:write name="orderDetail" property="designName" /> - <bean:write name="orderDetail" property="merchandiseName" /></a></td>
                </tr>
                <tr>
                  <td><bean:write name="orderDetail" property="merchandiseDesc" /></td>
                </tr>
                <tr>
                  <td height="30px" align="center" valign="bottom">
                    <bean:define id="orderDetailTblPk" name="orderDetail" property="orderDetailTblPk" />
                    <img src="images/btn_remove.gif" width="60px" height="16px" border="0" title="Remove this item" style="cursor:pointer;cursor:hand" onclick="return btnRemoveDetail_onclick('<bean:write name="orderDetail" property="orderDetailTblPk"/>');">
                  </td>
                </tr>
              </table>
              <!-- Item Description Table Ends -->
            </td>
            <td valign="top">
              <!-- Item Color Table Starts -->
              <table width="100%" height="18px" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
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
              <!-- Item Color Table Ends -->
            </td>
            <td>	
              <bean:define id="orderItemDetailArrayList" name="orderDetail" property="orderItemDetail" type="java.util.ArrayList" />
              
              <logic:iterate id="orderItemDetail" name="orderItemDetailArrayList"  indexId="itemIndex">
              <!-- Itme Size 1 Table Starts -->
              <table width="400px" border="0" cellspacing="1" cellpadding="1" class="bdrBottomColor_CC6A00">
                <tr>
                  <td width="97px" rowspan="3" align="center" valign="top"><bean:write name="orderItemDetail" property="sizeTypeId" /> - <bean:write name="orderItemDetail" property="sizeId" />
                    <img src="images/btn_remove.gif" border="0" style="margin-top:5px;cursor:pointer;cursor:hand;" title="Remove this size" onclick="return btnRemoveItemDetail_onclick('<bean:write  name="orderItemDetail" property="orderItemDetailTblPk" />');">
                  </td>
                  <td width="60px" rowspan="3" align="center" valign="top"><bean:write name="orderItemDetail" property="quantity" /></td>
                  <td width="115px" align="right"><bean:write name="orderItemDetail" property="price" /></td>
                  <td width="115px" align="right"><bean:write name="orderItemDetail" property="amount" /></td>
                </tr>
                <logic:notEqual name="orderItemDetail" property="discount" value="-0.00">
                <tr bgcolor="#FFFFFF">                
                  <td align="right" style="color:#ff0000"><bean:write name="orderItemDetail" property="discount" /> %</td>
                  <td align="right" style="color:#ff0000"><bean:write name="orderItemDetail" property="less" /></td>
                </tr>
                </logic:notEqual>
                
                <tr bgcolor="#FFFFFF">
                  <td align="right">You Pay: </td>
                  <td align="right"><span class="text_008000"><bean:write name="orderItemDetail" property="total" /></span></td>
                </tr>
              </table>
              <!-- Itme Size 1 Table Ends -->
              </logic:iterate> 
              <table width="400px" border="0" cellspacing="1" cellpadding="1" >
              <tr bgcolor="#FFFFFF">
                  <td align="right">
                  <b><bean:write name="orderDetail" property="orderDetailTotal" /></b>
                  </td>
              </tr>
              </table>
            </td>
          </tr>
          </logic:iterate>
          <!-- Order Item 1 Row Ends -->
          </logic:notEmpty>
        </table>
        <!-- Order List Table Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/studio_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/studio_pan_tile3px.gif"></td>
      <td><img src="images/studio_pan_corner_rb.gif" width="5" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->

</body>
</html>
