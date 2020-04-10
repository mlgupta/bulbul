<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.Payment" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<html:javascript formName="paymentByInetForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
</head>
<body>
<bean:define id="orderHeaderBean" name="customerInfo" property="currentOrder" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.Payment" /> :</td>
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
        </table>
        <!-- Order Header Table Ends --> 
	    </td>
	  </tr>
	  <tr>
	    <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 text_bold12">
        <!-- Payment Options Container Table Starts -->
        <table width="600px" border="0" align="center" cellpadding="2" cellspacing="1" class="bgColor_CC6A00" style="margin-bottom:15px ">
          <tr bgcolor="#FFFFFF">
            <td width="49%" align="center" class="th_F89627" ><bean:message key="tbl.PaymentOption" /></td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td height="180px">
              <!-- Credit Card Payment Option Table Starts -->
              <iframe id="paymentByCC" name="paymentByCC" src="paymentByCCB4Action.do" width="100%" frameborder="0" height="100%"  > </iframe>
              <!-- Credit Card Payment Option Table Ends -->
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td height="180px">
              <!-- Debit Card Payment Option Table Starts -->
              <iframe id="paymentByDC" name="paymentByDC" src="paymentByDCB4Action.do" width="100%" frameborder="0" height="100%"  > </iframe>
              <!-- Debit Card Payment Option Table Ends -->
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td>
              <html:form action="paymentByInetAction.do" onsubmit="return validatePaymentByInetForm(this);">
              <!-- Internet Banking Payment Option Table Starts -->
              <table width=100% align="center" border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td>
                  <span class="text_008000"><bean:message key="info.PayByInternetBanking" /></span>
                  </td>
                  <td width="50%" rowspan="3" align="center"><img src="images/logo_icici.gif" width="199px" height="67px"></td>
                </tr>
                <tr>
                  <td width="50%">
                  <html:select property="lstInetBank" size="5" style="width:100%">
                    <logic:iterate id="inetBank" name="inetBanks"  type="java.lang.String[]">
                        <html:option value="<%=inetBank[0]%>" ><%=inetBank[1]%></html:option>
                    </logic:iterate>
                  </html:select>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                  <html:submit property="btnClick2Pay" styleClass="buttons" style="width:90px; margin-right:8px" onclick="" ><bean:message key="btn.ClickToPay" /></html:submit>
                  </td>
                </tr>
              </table>
              <!-- Internet Banking Payment Option Table Ends -->
              </html:form >
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td>
              <html:form action="paymentByChequeOrDDAction.do" >
              <!-- Cheque/Demand Drafts  Payment Option Table Starts -->
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td>
                    <span class="text_008000"><bean:message key="info.PayByCheque/DemandDraft(DD)" /></span>
                  </td>
                  <td width="50%" rowspan="3" align="center"><img src="images/dd.jpg" width="199px" height="75px"></td>
                </tr>
                <tr>
                  <td width="50%"><bean:message key="msg.PayByChq/DD" /></td>
                </tr>
                <tr>
                  <td align="right" style="margin-right:8px">
                    <html:submit property="btnClick2Pay" styleClass="buttons" style="width:90px; margin-right:8px" ><bean:message key="btn.ClickToPay" /></html:submit>
                  </td>
                </tr>
              </table>
              <!-- Cheque/Demand Drafts  Payment Option Table Ends -->
              </html:form >
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td>
              <!-- Cash Payment @ Cash Points Option Table Starts -->
              
              <table width=100% align="center" border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td colspan="2">
                    <span class="text_008000"><bean:message key="info.PayAtCashPoints" /></span> 
                    <bean:message key="msg.PayAtCashPoints" />
                  </td>
                </tr>
                <tr>
                  <td width="50%">
                    <select id="cboCity4CashPt" name="cboCity4CashPt" style="width:100%" onchange="return window.outletList.location='changeOutletCityAction.do?city='+this.value;">
                      <option value="-1" >---Select City Here --</option>
                      <logic:iterate id="outletCity" name="outletCities" indexId="cityIndex" type="java.lang.String" >
                          <option value="<bean:write name="outletCity" />" ><bean:write name="outletCity" /></option>
                      </logic:iterate>
                    </select>                    
                  </td>
                  <td width="50%" align="center"></td>
                </tr>
                <tr>
                 <td colspan="2" height="111px">
                   <iframe id="outletList" name="outletList" src="changeOutletCityAction.do" width="100%" frameborder="0" height="100%"  > </iframe>
                 </td>
                </tr>
              </table>
              <!-- Cash Payment @ Cash Points Option Table Ends -->
            </td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td>
              <html:form action="paymentByCashOnDeliveryAction.do" >
              <!-- Cash Payment Option Table Starts -->
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td>
                    <span class="text_008000"><bean:message key="info.CashOnDelivery" /></span>
                  </td>
                  <td width="50%" rowspan="3" align="center"><img src="images/cash_on_delivery.jpg" width="106px" height="67px"></td>
                </tr>
                <tr>
                  <td width="50%"><bean:message key="msg.CashOnDelivery" /></td>
                </tr>
                <tr>
                  <td align="right" style="margin-right:8px">
                    <html:submit property="btnClick2Pay" styleClass="buttons" style="width:90px; margin-right:8px" ><bean:message key="btn.ClickToPay" /></html:submit>
                  </td>
                </tr>
              </table>
              <!-- Cash Payment Option Table Ends -->
              </html:form >
            </td>
          </tr>
        </table>
        <!-- Payment Options Container Table Ends -->
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
