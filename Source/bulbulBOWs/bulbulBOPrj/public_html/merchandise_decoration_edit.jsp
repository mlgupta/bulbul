<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.DecorationEdit" /></TITLE>
    <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
    
     <html:javascript formName="merchandiseDecorationForm" dynamicJavascript="true" staticJavascript="false" />
      <script language="javascript1.1" src="staticJavascript.jsp"></script>
      <script>
      <!--
       function btnclick(operation){
          var thisForm=document.forms[0];
          thisForm.target="_self";
          if ((operation=='save') && validateMerchandiseDecorationForm(thisForm) ){
            thisForm.submit();
          }
        }
      -->
      </script>
  </HEAD>
  <BODY>
    <html:form action="/merchandiseDecorationEditAction">
    <html:hidden property="hdnMerchandiseDecorationTblPk" />
    <html:hidden property="hdnSearchPageNo" /> 
      <jsp:include page="header.jsp" />
      <!-- Tab Table Starts -->
      <table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
        <tr>
          <td width="200px" class="tab" height="20px">
            <div align="center"><bean:message key="tab.Decoration"/></div>
          </td>
          <td width="770px" >
          </td>
        </tr>
      </table>
      <!-- Tab Table Ends -->            
      <!-- Main Table Starts -->
      <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="494px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
            <!-- ADD Table Starts -->
            <table width="500px"  border="0" align="center" class="bdrColor_336666">
              <tr>
                <th height="20px" colspan="3"><div align="center"><bean:message key="page.DecorationEdit" /> </div></th>
              </tr>
              <tr>
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr>
                <td width="137px"><div align="right"><bean:message key="txt.DecorationName" />:</div></td>
                <td width="307px"><div align="right">
                <html:text property="txtDecorationName"  styleClass="bdrColor_336666"  style="width:300px" tabindex="1" maxlength="25" />
                </div></td>
                <td width="40px">&nbsp;</td>
              </tr>
              <tr>
                <td valign="top"><div align="right"><bean:message key="txa.DecorationDescription" /> :</div></td>
                <td><div align="right">
                <html:textarea property="txaDecorationDescription" cols="" rows="4" styleClass="bdrColor_336666"  style="width:300px" />
                </div></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><div align="right">
                  <html:button property="btnSave" styleClass="buttons" tabindex="3" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
                  <html:cancel  styleClass="buttons" tabindex="4" ><bean:message  key="btn.Cancel" /></html:cancel>
                  <html:reset property="btnClear" styleClass="buttons"  tabindex="5" ><bean:message  key="btn.Clear" /></html:reset>
                  </div>
                </td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td colspan="3">&nbsp;</td>
              </tr>
            </table>
            <!-- ADD Table Ends -->
             <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="500px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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