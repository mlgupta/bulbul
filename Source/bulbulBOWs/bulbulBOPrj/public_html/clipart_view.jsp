<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="clipartTblPk" name="clipartForm" property="hdnClipartTblPk" />
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.ClipartView" /> </TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">

</HEAD>
<BODY>
<html:form action="/clipartViewAction" enctype="multipart/form-data" >
<html:hidden property="hdnClipartCategoryTblPk" />
<html:hidden property="hdnImgOid" />
<html:hidden property="hdnContentType" />
<html:hidden property="hdnContentSize" />
<html:hidden property="hdnStdImgOid" />
<html:hidden property="hdnStdContentType" />
<html:hidden property="hdnStdContentSize" />
<html:hidden property="hdnSearchPageNo" />
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message  key="tab.Clipart" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->      
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="600px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.ClipartView" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="100px">
            <div align="right">
              <bean:message key="txt.ClipartName" />:
            </div>
          </td>
          <td width="350px">
            <div align="right">
              <html:text property="txtClipart" styleClass="bdrColor_336666" style="width:100%" maxlength="10" readonly="true"/>
            </div>
          </td>
          <td width="90px" rowspan="3" align="center">
            <iframe name="display" id="display" src="imageDisplayAction.do?imageOid=<bean:write name="clipartForm" property="hdnStdImgOid" />&imageContentType=<bean:write name="clipartForm" property="hdnStdContentType" />&imageContentSize=<bean:write name="clipartForm" property="hdnStdContentSize" />" frameborder="0" align="center" class="bdrColor_336666" style="background-color:#ffffff" height="107px" width="80px" ></iframe>
          </td>
        </tr>
        <tr>
          <td >
            <div align="right">
              <bean:message key="txt.ClipartCategory" />:
            </div>
          </td>
          <td >
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:100%" readonly="true"/>
            </div>
          </td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.ClipartKeywords" />:</div></td>
          <td>
            <div align="right">
              <html:textarea property="txaClipartKeywords" cols="" rows="4" styleClass="bdrColor_336666" style="width:100%" readonly="true"/>
            </div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="2">
            <div align="right">
              <html:cancel  styleClass="buttons" style="margin-right:2px" ><bean:message  key="btn.Ok" /></html:cancel>
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
