<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>

<bean:define id="fontTblPk" name="fontForm" property="hdnFontTblPk" />
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.FontView" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
<html:form action="/fontViewAction">
<html:hidden property="hdnFontCategoryTblPk" />
<html:hidden property="hdnImgOid" />
<html:hidden property="hdnContentType" />
<html:hidden property="hdnContentSize" />
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
      <table width="600px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.FontView" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="130px"><div align="right"><bean:message key="txt.FontName"/> :</div></td>
          <td width="280px">
            <div align="right">
              <html:text property="txtFont" styleClass="bdrColor_336666" style="width:300px" maxlength="25" readonly="true"/>
            </div>
          </td>
          <td width="180px" rowspan="3" align="center">
            <!--<iframe name="display" id="display" src="imageDisplayAction.do?imageOid=<bean:write name="fontForm" property="hdnImgOid" />&imageContentType=<bean:write name="fontForm" property="hdnContentType" />&imageContentSize=<bean:write name="fontForm" property="hdnContentSize" />" frameborder="0" align="center" class="bdrColor_336666" height="95%" width="95%" ></iframe>-->
          </td>
        </tr>
        <tr>
          <td width="130px"><div align="right"><bean:message key="txt.FontCategory"/> :</div></td>
          <td width="280px">
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:300px" maxlength="10" readonly="true"/>
            </div>
          </td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.FontDesc"/> :</div></td>
          <td valign="top">
            <div align="right">
              <html:textarea property="txaFontDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:300px" readonly="true"/>
            </div>
          </td>
          
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
              <div align="right">
                <html:cancel  styleClass="buttons"  ><bean:message  key="btn.Ok" /></html:cancel>
              </div>
            </td>
            
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="600px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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
