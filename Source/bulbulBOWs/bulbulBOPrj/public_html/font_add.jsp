<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.FontAdd" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <html:javascript formName="fontForm" dynamicJavascript="true" staticJavascript="false" />
  <script type="text/javascript" language="javascript1.1" src="staticJavascript.jsp"></script>
  <script type="text/javascript" language="javascript1.1" src="general.js"></script>
  <script type="text/javascript" language="javascript1.1">
  <!--
    function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.action="fontAddAction.do"
      thisForm.target="_self";
      if ((operation=='save' && validateFontForm(thisForm)) ){
//        if(thisForm.imgFile.value==''){
//          alert('<bean:message key="alert.Font.Select.Required" />');
//        }else if((thisForm.imgFile.value).indexOf(".svg")==-1){
//          alert('<bean:message key="alert.choosesvgonly" />');
//          thisForm.imgFile.focus();
//        }else{
          thisForm.submit();
//        }
      }
    }

    function btnCancel_onClick(){
      var thisForm=document.forms[0];
      thisForm.action="fontAddAction.do"
      thisForm.target="_self";
      thisForm.submit();
    }

//    function imgFile_onChange(){
//      var thisForm = document.forms[0];
//      if((thisForm.imgFile.value).indexOf(".svg")==-1){
//        alert('<bean:message key="alert.choosesvgonly" />');
//        var txtFontName=thisForm.txtFont.value;
//        var txaFontDesc=thisForm.txaFontDesc.value;
//        thisForm.reset();
//        thisForm.txtFont.value=txtFontName;
//        thisForm.txaFontDesc.value=txaFontDesc;
//        thisForm.imgFile.focus();
//        return false;
//      }
//      thisForm.target="preview";
//      thisForm.action="fontPreviewAction.do";
//      thisForm.submit();
//    }
  -->
  </script>
</HEAD>
<BODY>
<html:form action="/fontAddAction" enctype="multipart/form-data">
<html:hidden property="hdnFontCategoryTblPk" />
<html:hidden property="hdnSearchPageNo" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Font" /></div>
    </td>
    <td width="770px">
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="600px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.FontAdd" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="130px"><div align="right"><bean:message key="txt.FontName"/> :</div></td>
          <td width="280px">
            <div align="right">
              <html:text property="txtFont" styleClass="bdrColor_336666" style="width:100%" maxlength="25" />
            </div>
          </td>
          <td width="180px" height="150px" rowspan="4" align="center" >
            <!--<iframe name="preview" id="preview" frameborder="0" align="center" class="bdrColor_336666" height="95%" width="95%" ></iframe>-->
          </td>
        </tr>
        <tr>
          <td width="130px"><div align="right"><bean:message key="txt.FontCategory"/> :</div></td>
          <td width="280px">
            <div align="right">
              <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:100%"  readonly="true"/>
            </div>
          </td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message key="txa.FontDesc"/> :</div></td>
          <td>
            <div align="right">
              <html:textarea property="txaFontDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:100%" />
            </div>
          </td>
        </tr>
        <!--<tr>
          <td align="right"><bean:message key="fle.FontFile"/>:</td>
          <td>
            <div >
              <input type="file" id="imgFile" name="imgFile" class="bdrColor_336666" size="35" onkeypress="return false;" onchange="return imgFile_onChange();"/>
            </div>
          </td>
        </tr>-->
        <tr>
          <td>&nbsp;</td>
          <td>
            <div align="right">
              <html:button property="btnSave" styleClass="buttons" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
              <html:cancel styleClass="buttons" onclick="return btnCancel_onClick();" ><bean:message  key="btn.Cancel" /></html:cancel>
              <html:reset property="btnClear" styleClass="buttons"  ><bean:message  key="btn.Clear" /></html:reset>
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
            <td width="30px" ><div class="imgStatusMsg"></td>
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
