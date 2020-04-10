<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<bean:define id="preOrderBean" name="preOrderBean" />
<bean:define id="minQuantity" name="preOrderBean" property="minQuantity" />
<head>
<title><bean:message key="title.OrderModify" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<script>
<!--
  function btnclick(){
    var thisForm=document.forms[0];
    thisForm.target="_self";
    var isAllQtyZero=true;
    var index;
    var totalQty=0;
    if(typeof thisForm.txtQty.length != 'undefined'){
      for(index = 0 ; index < (thisForm.txtQty.length) ; index++ ){
        if((thisForm.txtQty[index].value !=0) && (trim(thisForm.txtQty[index].value).length!=0) ){
          totalQty+=parseInt(thisForm.txtQty[index].value);
          isAllQtyZero=false;
        }
      }
    }else{
      if((thisForm.txtQty.value != 0) && (trim(thisForm.txtQty.value).length) != 0){
        totalQty+=parseInt(thisForm.txtQty.value);
        isAllQtyZero=false;
      }
    }
    if(isAllQtyZero){
      alert("<bean:message key="alert.SetQtyGTZero" />");
       return false;
    }
    if(totalQty < <%=(String)minQuantity%>){
      alert('<bean:message key="alert.TotlaQty<ThresholdQty" arg0="<%=(String)minQuantity%>" />');
      return false;
    }
    thisForm.submit();
  }
-->
</script>
</head>
<body>
<html:form action="orderModifyAction.do">
<bean:define id="customerDesignTblPk" name="preOrderBean" property="customerDesignTblPk" />
<html:hidden property="hdnCustomerDesignTblPk" value="<%=(String)customerDesignTblPk%>" />
<bean:define id="orderDetailTblPk" name="preOrderBean" property="orderDetailTblPk" />
<html:hidden property="hdnOrderDetailTblPk" value="<%=(String)orderDetailTblPk%>" />
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.OrderModify" /> :</td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5" height="17px"></td>
    </tr>
	  <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">
        <!-- Customized Product Image and Size & Qty Table Starts -->
        <table width="100%"  border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td align="right" width="48%" valign="top">
              <!-- Customized Product Image Table Starts -->
              <table width="250px"  border="0" cellpadding="3" cellspacing="1">
                <tr>
                  <td colspan="3" align="center" class="text_CC6A00"><bean:write name="preOrderBean" property="designName" />- <bean:write name="preOrderBean" property="productName" /></td>
                </tr>
                <tr>
                  <td colspan="3" >
                    <div align="center">
                        <img name="display" id="display" class="bdrColor_CC6A00" border="0" style="width:200px; height:200px; background-color:#FFFFFF" src='imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="preOrderBean" property="designOId" />&imageContentType=<bean:write name="preOrderBean" property="designContentType" />&imageContentSize=<bean:write name="preOrderBean" property="designContentSize" />' ></img>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td width="33%" class="text_CC6A00" align="right" >Color: </td>
                  <td width="36px">
                    <div style="height:18px;width:36px;align:center" class="bdrColor_CC6A00">
                      <logic:notEqual  name="preOrderBean" property="color2Name" value="">
                        <div style="height:18px;float:left;width:18px;background-color:<bean:write name="preOrderBean" property="color1Value" />;"></div>
                        <div style="height:18px;float:left;width:18px;background-color:<bean:write name="preOrderBean" property="color2Value" />;"></div>
                      </logic:notEqual>
                      <logic:equal  name="preOrderBean" property="color2Name" value="">
                        <div style="height:18px;float:left;width:36px;background-color:<bean:write name="preOrderBean" property="color1Value" />;"></div>
                      </logic:equal>
                    </div>
                  </td>
                  <td>
                      <logic:notEqual  name="preOrderBean" property="color2Name" value="">
                        <bean:write name="preOrderBean" property="color1Name" />/<bean:write name="preOrderBean" property="color2Name" />      
                      </logic:notEqual>
                      <logic:equal  name="preOrderBean" property="color2Name" value="">
                        <bean:write name="preOrderBean" property="color1Name" />
                      </logic:equal>
                  </td>
                </tr>
                <tr>
                  <td width="33%"  class="text_CC6A00" align="right">Min. Qty:</td>
                  <td  colspan="2">
                    <bean:write name="preOrderBean" property="minQuantity" />
                  </td>
                </tr>
              </table>
              <!-- Customized Product Image Table Ends -->
            </td>
            <td width="52%" valign="top">
              <!-- Size And Qty Container Table Starts -->
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr><td>&nbsp;</td></tr>
                <tr>
                  <td>
                    <!-- Size And Qty Table Header Starts -->
                    <table width="238"  border="0" cellspacing="1" cellpadding="2" class="bgColor_CC6A00">
                        <tr>
                          <td align="center" class="th_F89627" width="55%"><bean:message key="tbl.Size" /></td>
                          <td align="center" class="th_F89627" width="45%"><bean:message key="tbl.Quantity" /></td>
                        </tr>
                    </table>
                    <!-- Size And Qty Table Header Ends -->
                    <!-- Size And Qty Table Container Starts -->
                    <div style="width:255px; height:202px; overflow:auto" >
                      <table width="238"  border="0" cellspacing="1" cellpadding="2" class="bgColor_CC6A00">
                      <bean:define id="sizeArrayList" name="preOrderBean" property="sizeArrayList"/>
                        <logic:iterate id="preOrderSizeBean" name="sizeArrayList" >
                        <tr bgcolor="#FFFFFF">
                          <td align="center" width="55%"><bean:write name="preOrderSizeBean" property="sizeTypeId" />-<bean:write name="preOrderSizeBean" property="sizeId" /></td>
                          <td align="center" width="45%">
                          <logic:notEmpty name="preOrderSizeBean" property="quantity" >
                            <bean:define id="quantity" name="preOrderSizeBean" property="quantity" />
                            <html:text property="txtQty" style="width:60px; height:20px" value="<%=(String)quantity%>"/>
                          </logic:notEmpty>
                          <logic:empty name="preOrderSizeBean" property="quantity" >
                            <html:text property="txtQty" style="width:60px; height:20px" onkeypress="return integerOnly(event);" maxlength="3"/>
                          </logic:empty>
                          </td>
                        </tr>
                        <bean:define id="merchandiseSizeTblPk" name="preOrderSizeBean" property="merchandiseSizeTblPk" />
                        <html:hidden property="hdnMerchandiseSizeTblPk" value="<%=(String)merchandiseSizeTblPk%>" />
                        <bean:define id="orderItemDetailTblPk" name="preOrderSizeBean" property="orderItemDetailTblPk" />
                        <html:hidden property="hdnOrderItemDetailTblPk" value="<%=(String)orderItemDetailTblPk%>" />
                        </logic:iterate>
                                                          
                      </table>      
                    </div>
                    <!-- Size And Qty Table Container Ends -->
                  </td>
                </tr>
                <tr>
                  <td height="30px" align="center">
                    <html:button styleClass="buttons" style="width:85px; height:20px" property="btnModify" onclick="btnclick();" ><bean:message key="btn.ModifyCart" /></html:button>
                    <html:cancel styleClass="buttons" style="width:85px; height:20px; margin-right:43px;" ><bean:message key="btn.Cancel" /></html:cancel>
                  </td>
                </tr>
              </table>
              <!-- Size And Qty Container Table Ends -->
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
</html:form>
</body>
</html>
