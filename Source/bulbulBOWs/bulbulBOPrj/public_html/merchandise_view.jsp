<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="merchandiseTblPk" name="merchandiseForm" property="hdnMerchandiseTblPk" />
<HTML>
<HEAD>
<TITLE><bean:message key="title.MerchandiseView" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
 <script language="javascript" type="text/javascript" >
 function window_onload(){
    var thisForm=document.forms[0];
    
    if(typeof thisForm.chkPrintableArea.length !='undefined'){
      for(var chkIndex=0;  chkIndex<thisForm.chkPrintableArea.length; chkIndex++){
        if(thisForm.hdnPreviouslyChecked[chkIndex].value=="Yes"){
          thisForm.chkPrintableArea[chkIndex].checked=true;
        }
      }
    }else if(thisForm.hdnPreviouslyChecked.value=="Yes"){
      thisForm.chkPrintableArea.checked=true;
    }
      
  }
 </script>
</HEAD>
<BODY onload="return window_onload();"  >
<html:form action="/merchandiseViewAction" enctype="multipart/form-data">
<html:hidden property="hdnMerchandiseCategoryTblPk" />

<html:hidden property="hdnDisplayImgContentType" />
<html:hidden property="hdnDisplayImgContentSize" />
<html:hidden property="hdnDisplayImgOid" />

<html:hidden property="hdnSearchPageNo" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Merchandise" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<!-- Main Table Starts -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" align="center" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <!-- Add Table Starts -->
      <table width="98%" border="0" cellpadding="0" cellspacing="2" class="bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333" >
        <tr>
          <th height="25px" ><bean:message key="page.MerchandiseView" /></th>
        <tr>
        <tr><td height="5px" ></td></tr>
        <tr>
          <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td width="36%" align="center" >
                  <!-- Merchandise Details Starts-->
                  <table width="333px" height="400px" border="0" cellpadding="1" cellspacing="2" class="bdrColor_336666">
                    <tr>
                      <th height="15px" colspan="2"><div align="center"><bean:message key="tbl.MerchandiseDetails" /></div></th>
                    </tr>
                    <tr>
                      <td width="118px"><div align="right"><bean:message key="txt.Merchandise" />:</div></td>
                      <td width="200px">                
                        <html:text property="txtMerchandise" styleClass="bdrColor_336666" style="width:200px" readonly="true" maxlength="25" />              
                      </td>
                    </tr>
                    <tr>
                      <td width="118px"><div align="right"><bean:message key="txt.MerchandiseCategory" />:</div></td>
                      <td width="200px">                
                        <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:200px" readonly="true" />              
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MerchandiseDesc" />:</div></td>
                      <td>
                        <html:textarea property="txaMerchandiseDesc" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px" readonly="true" ></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MerchandiseComm" />:</div></td>
                      <td>
                        <html:textarea property="txaMerchandiseComm" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px" readonly="true" ></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MaterialDetail" />:</div></td>
                      <td>
                        <html:textarea property="txaMaterialDetail" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px" readonly="true" ></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right"><bean:message key="txt.MinimumQuantity" />:</div></td>
                      <td>
                        <html:text property="txtMinimumQuantity" styleClass="bdrColor_336666" onkeypress="return integerOnly(event);" style="width:200px" readonly="true"/>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.DeliveryNote" />: </div></td>
                      <td>
                        <html:textarea property="txaDeliveryNote" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px" readonly="true" ></html:textarea>              
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right"><bean:message key="cbo.MerchandiseDecoration" />:</div></td>
                      <td height="15px">
                        <html:select property="cboMerchandiseDecoration" style="width:200px" styleClass="bdrColor_336666" disabled="true">
                          <logic:iterate id="decoration" name="merchandiseDecorations" >
                            <bean:define id="merchandiseDecorationTblPk" name="decoration" property="merchandiseDecorationTblPk" />
                              <html:option  value="<%=(String)merchandiseDecorationTblPk%>"><bean:write name="decoration" property="decorationName" /></html:option>
                          </logic:iterate>
                        </html:select>
                      </td>
                    </tr>
                  </table>
                  <!-- Merchandise Details Ends-->
                </td>
                  <td>
                  <!-- Merchandise Images Starts --> 
                  <table width="175px" height="400px" border="0" cellpadding="0" cellspacing="1" class="bdrColor_336666" >
                    <tr>
                      <th height="15px" ><bean:message key="tbl.DisplayDesignImages" /></th>
                    </tr>
                    <tr>
                      <th><bean:message key="tbl.DisplayImage" /></th>
                    </tr>
                    <tr>
                      <td align="center">
                        <div align="center" style="overflow:auto;height:160px;width:164px;">
                          <iframe name="displayImgDisplay" id="displayImgDisplay" src="imageDisplayAction.do?imageOid=<bean:write name="merchandiseForm" property="hdnDisplayImgOid" />&imageContentType=<bean:write name="merchandiseForm" property="hdnDisplayImgContentType" />&imageContentSize=<bean:write name="merchandiseForm" property="hdnDisplayImgContentSize" />" frameborder="0" align="center" class="bdrColor_336666" height="95%" width="95%" ></iframe>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <th><bean:message key="tbl.PrintableArea" /></th>
                    </tr>
                    <tr>
                      <td align="center">
                       <div style="overflow:auto;width:164px%;height:150px">
                          <table border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2" width="100%">
                            <tr>
                              <th width="6%"></th>
                              <th width="69%"><bean:message key="tbl.PrintableArea" /></th>
                            </tr>
                            <logic:iterate id="printableArea" name="printableAreas" indexId="currentIndex">   
                            
                              <bean:define id="PrintableAreaTblPk" name="printableArea" property="printableAreaTblPk" />
                              <tr bgcolor="#ffffff" >
                                <td>
                                  <html:multibox  disabled="true" property="chkPrintableArea" onclick="return chkPrintableArea_onclick(this);" alt="<%=Integer.toString(currentIndex.intValue())%>" value="<%=(String)PrintableAreaTblPk %>"  />
                                </td>
                                <td>
                                  <input type="hidden" id="hdnPreviouslyChecked" value="<bean:write name="printableArea" property="previouslyChecked" />" />
                                  <input type="hidden" id="hdnPrintableAreaTblPk" value="<bean:write name="printableArea" property="printableAreaTblPk" />" />
                                  <bean:write name="printableArea" property="printableAreaName"/>
                                </td>
                              </tr>
                            </logic:iterate> 
                          </table>
                        </div>
                        
                      </td>
                    </tr>                    
                  </table>
                  <!-- Merchandise Images Ends --> 
                </td>
                <td width="45%">
                  <!-- Merchandise Color Size and Price Details Starts -->
                  <table width="99%" height="400px" border="0" cellpadding="0" cellspacing="2" class="bdrColor_336666" >
                    <tr>
                      <th height="15px" ><bean:message key="tbl.Color/Size/PriceList" /></th>
                    <tr>
                    <tr>
                      <td valign="top" align="center">
                        <div align="center" style="overflow:auto;height:190px;width:410px;">
                          <table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#80A0B2">
                            <tr>
                              <th width="5%" height="15px"><bean:message key="tbl.Sno" /></th>
                              <th width="15%" height="15px"><bean:message key="tbl.Color" /></th>
                              <th width="15%" height="15px"><bean:message key="tbl.Status" /></th>
                              <th height="15px"><bean:message key="tbl.SizePrice" /></th>
                            </tr>
                            <bean:define id="colorOneValue" name="hdnColorOneValue" type="java.lang.String[]" />
                            <bean:define id="colorTwoValue" name="hdnColorTwoValue" type="java.lang.String[]"/>
                            <bean:define id="colorOneName" name="hdnColorOneName" type="java.lang.String[]" />
                            <bean:define id="colorTwoName" name="hdnColorTwoName" type="java.lang.String[]"/>
                            <bean:define id="colorIsActiveDisplay" name="hdnColorIsActiveDisplay" type="java.lang.String[]"/>
                            <bean:define id="sizeText" name="hdnSizeText" type="java.lang.String[]"/>                              
                            <bean:define id="priveValue" name="hdnPriceValue" type="java.lang.String[]"/>                              
                            <bean:define id="sizeIsActive" name="hdnSizeIsActive" type="java.lang.String[]"/>                              
                            <logic:iterate id="sno" name="hdnColorSNo" indexId="currentIndex" >
                              <tr bgcolor="#FFFFFF">
                                <td align="right"><bean:write name="sno"/></td>
                                <td align="center" title="<%=colorOneName[currentIndex.intValue()]%><%=(colorTwoName[currentIndex.intValue()]).trim().length()>0?"/" + colorTwoName[currentIndex.intValue()]:""%>" >
                                  <table border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
                                  <tr>
                                    <%if((colorTwoValue[currentIndex.intValue()]).trim().length()>0){%>
                                      <td bgcolor="<%=colorOneValue[currentIndex.intValue()]%>" height="18px" width="18px" >
                                      <td bgcolor="<%=colorTwoValue[currentIndex.intValue()]%>" height="18px" width="18px" >
                                    <%}else{%>
                                      <td bgcolor="<%=colorOneValue[currentIndex.intValue()]%>" height="18px" width="36px" >
                                    <%}%>
                                  </tr>
                                  </table>
                                </td>
                                <td><%=colorIsActiveDisplay[currentIndex.intValue()]%></td>
                                <td>
                                  <table border="0" cellpadding="3" cellspacing="1" bgcolor="#80A0B2" >
                                    <tr>
                                    <%
                                      final String ACTIVE="1";
                                      final String INACTIVE="0";
                                      String[] sizeTextArray=sizeText[currentIndex.intValue()].split("/");
                                      String[] priceTextArray=priveValue[currentIndex.intValue()].split("/");
                                      String[] sizeIsActiveArray=sizeIsActive[currentIndex.intValue()].split("/");
                                      for(int index=0; index<sizeTextArray.length;index++){
                                        if(sizeIsActiveArray[index].equals(ACTIVE)){
                                    %>
                                          <td bgcolor="#ffffff"><%=sizeTextArray[index]%>/<%=priceTextArray[index]%></td>
                                    <%
                                        }else{
                                    %>
                                          <td bgcolor="#ffffff" style="color:#ff0000"><%=sizeTextArray[index]%>/<%=priceTextArray[index]%></td>
                                    <%
                                        }
                                      }    
                                    %>  
                                    </tr>
                                  </table> 
                                </td>
                              </tr>
                             </logic:iterate>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                  <!-- Merchandise Color Size and Price Details Ends -->   
                </td>
              </tr>
            </table>
          </td>
        <tr>
        <tr>
          <td align="right">
            <html:cancel styleClass="buttons" ><bean:message key="btn.Ok" /></html:cancel>&nbsp;
          </td>
        </tr>
        <tr><td height="2px"></td></tr>        
      </table>
      <!-- Add Table Ends -->
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="950px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
        <tr bgcolor="#FFFFFF">
          <td width="30px" ><div class="imgStatusMsg"></td>
          <td><html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->
    </td>
  </tr>
</table>
<!-- Main Table Ends -->
</html:form>
</BODY>
</HTML>
