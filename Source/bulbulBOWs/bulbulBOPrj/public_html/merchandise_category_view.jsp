<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="merchandiseCategoryTblPk" name="merchandiseCategoryForm" property="hdnMerchandiseCategoryTblPk" />
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.MerchandiseCategoryEdit" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">

</HEAD>
<BODY>
<html:form action="/merchandiseCategoryViewAction" enctype="multipart/form-data" >
<html:hidden property="hdnMerchandiseCategoryTblFk" />
<html:hidden property="hdnSearchPageNo" /> 
<html:hidden property="hdnCategoryImgOid" />
<html:hidden property="hdnCategoryImgContentType" />
<html:hidden property="hdnCategoryImgContentSize" />
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message  key="tab.Merchandise" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->      
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="730px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.MerchandiseCategoryView" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="280px">
            <div align="right">
              <bean:message key="txt.MerchandiseCategoryName" />:
            </div>
          </td>
          <td width="310px">
            <div align="right">
              <html:text property="txtMerchandiseCategory" styleClass="bdrColor_336666" style="width:100%" maxlength="10" readonly="true"/>
            </div>
          </td>
          <td width="140px"></td>
        </tr>
        <tr>
          <td width="280px">
            <div align="right">
              <bean:message key="txt.MerchandiseCategory" />:
            </div>
          </td>
          <td width="310px">
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:100%" readonly="true"/>
            </div>
          </td>
          <td width="140px"></td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.MerchandiseCategoryDesc" />:</div></td>
          <td>
            <div align="right">
              <html:textarea property="txaMerchandiseCategoryDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:100%" readonly="true"/>
            </div>
          </td>
        </tr>
        <tr>
					<td valign="top"><div align="right"><bean:message key="fle.MerchandiseCategoryImage" />:</div></td>
					<td height="50px">
						<table border="0" cellpadding="0" cellspacing="1" >
        			<tr>
								<td>
                  <iframe name="display" id="display" src="imageDisplayAction.do?imageOid=<bean:write name="merchandiseCategoryForm" property="hdnCategoryImgOid" />&imageContentType=<bean:write name="merchandiseCategoryForm" property="hdnCategoryImgContentType" />&imageContentSize=<bean:write name="merchandiseCategoryForm" property="hdnCategoryImgContentSize" />" frameborder="0" align="center" class="bdrColor_336666" height="95%" width="56%" ></iframe>
                </td>
							</tr>
						</table>
					</td>
					<td>&nbsp;</td>
				</tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <table cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td>
                  <html:radio property="radMOrC" value="1" onclick="return radMOrC_onclick();" disabled="true" />
                </td>
                <td><bean:message key="rad.MerchandiseCategoryOnly" /></td>
                <td>
                  <html:radio property="radMOrC" value="0" onclick="return radMOrC_onclick();" disabled="true" />
                </td>
                <td><bean:message key="rad.MerchandiseOnly" /></td>
              <tr>          
            </table>
          </td>
        </tr>
        <logic:equal name="merchandiseCategoryForm" property="radMOrC" value="1">
        <tr id="sizeTypeRow" style="visibility:hidden">
        </logic:equal>
        <logic:equal name="merchandiseCategoryForm" property="radMOrC" value="0">
        <tr id="sizeTypeRow" style="visibility:visible">
        </logic:equal>
					<td align="right" valign="top"><bean:message key="lst.SizeType" />:</td>
					<td>
            <table border="0" cellpadding="0" cellspacing="1" width="100%" >
              <tr>
                <td width="100%">
                  <select id="lstSizeType" name="lstSizeType" size="4" style="width:100%;" multiple >
                  <logic:iterate id="sizeType" name="SizeType"  type="java.lang.String[]" >
                  <option value="<%=sizeType[0]%>" ><%=sizeType[1]%></optiond>
                  </logic:iterate>
                  </select>
                </td>
              </tr>
            </table>
					</td>
					<td>&nbsp;</td>
				</tr>
        <tr><td colspan="3" height="5px"></td></tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <div align="right">
              <html:cancel  styleClass="buttons"  ><bean:message  key="btn.Ok" /></html:cancel>
            </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr><td colspan="3" height="5px"></td></tr>
      </table>
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="730px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
        <tr bgcolor="#FFFFFF">
          <td width="30px" ><div class="imgStatusMsg"></div></td>
          <td>
            <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
          </td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->      
    </td>
  </tr>
</table>
</html:form>
</BODY>
</HTML>
