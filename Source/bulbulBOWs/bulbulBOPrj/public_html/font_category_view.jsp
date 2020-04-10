<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.FontCategoryView" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  
</HEAD>
<BODY>
<html:form action="/fontCategoryViewAction">
<html:hidden property="hdnFontCategoryTblFk" />
<html:hidden property="hdnSearchPageNo" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Font" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="505px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="500px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.FontCategoryView" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="137px"><div align="right"><bean:message key="txt.FontCategoryName"/> :</div></td>
          <td width="307px">
            <div align="right">
              <html:text property="txtFontCategory" styleClass="bdrColor_336666" style="width:300px" maxlength="25" readonly="true"/>
            </div>
          </td>
          <td width="40px">&nbsp;</td>
        </tr>
        <tr>
          <td width="137px"><div align="right"><bean:message key="txt.FontCategory"/> :</div></td>
          <td width="307px">
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:300px" maxlength="10" readonly="true"/>
            </div>
          </td>
          <td width="40px">&nbsp;</td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.FontCategoryDesc"/> :</div></td>
          <td>
            <div align="right">
              <html:textarea property="txaFontCategoryDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:300px" readonly="true"/>
            </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
              <div align="right">
                <html:cancel  styleClass="buttons"  ><bean:message  key="btn.Ok" /></html:cancel>
              </div>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="500px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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
