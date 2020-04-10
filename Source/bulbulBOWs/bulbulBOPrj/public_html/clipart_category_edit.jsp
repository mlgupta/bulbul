<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.ClipartCategoryEdit" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <html:javascript formName="clipartCategoryForm" dynamicJavascript="true" staticJavascript="false" />
  <script language="javascript1.1" src="staticJavascript.jsp"></script>
 
  <script>
  <!--
    function btnclick(operation){
    var thisForm=document.forms[0];
    thisForm.target="_self";
      if ((operation=='save'&& validateClipartCategoryForm(thisForm)) ){
        thisForm.submit();
      }
    }
  -->
  </script>
</HEAD>
<BODY>
<html:form action="/clipartCategoryEditAction">
<html:hidden property="hdnClipartCategoryTblPk" />
<html:hidden property="hdnClipartCategoryTblFk" />
<html:hidden property="hdnSearchPageNo" />
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Clipart" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="500px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.ClipartCategoryEdit" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="190x"><div align="right"><bean:message key="txt.ClipartCategoryName"/> :</div></td>
          <td width="300px">
            <div align="right">
              <html:text property="txtClipartCategory" styleClass="bdrColor_336666" style="width:300px" maxlength="10" />
            </div>
          </td>
          <td width="10px">&nbsp;</td>
        </tr>
        <tr>
          <td width="190x"><div align="right"><bean:message key="txt.ClipartCategory"/> :</div></td>
          <td width="300px">
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:300px" readonly="true" />
            </div>
          </td>
          <td width="10px">&nbsp;</td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.ClipartCategoryDesc"/>:</div></td>
          <td>
            <div align="right">
              <html:textarea property="txaClipartCategoryDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:300px" />
            </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
              <div align="right">
                <html:button property="btnSave" styleClass="buttons" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
                <html:cancel  styleClass="buttons"  ><bean:message  key="btn.Cancel" /></html:cancel>
                <html:reset property="btnClear" styleClass="buttons"  ><bean:message  key="btn.Clear" /></html:reset>
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
