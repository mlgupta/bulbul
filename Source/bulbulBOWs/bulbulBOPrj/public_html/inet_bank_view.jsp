<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.InetBankView" /></TITLE>
  </HEAD>
  <BODY>
    <html:form action="/inetBankViewAction">
    <html:hidden property="hdnSearchPageNo" /> 
      <jsp:include page="header.jsp" />
      <!-- Tab Table Starts -->
      <table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
        <tr>
          <td width="200px" class="tab" height="20px">
            <div align="center"><bean:message key="tab.InternetBank"/></div>
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
                <th height="20px" colspan="3"><div align="center"><bean:message key="page.InetBankView" /> </div></th>
              </tr>
              <tr>
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr>
                <td width="137px"><div align="right"><bean:message key="txt.BankName" />:</div></td>
                <td width="307px"><div align="right">
                <html:text property="txtBankName"  styleClass="bdrColor_336666"  style="width:300px"  maxlength="25" readonly="true" />
                </div></td>
                <td width="40px">&nbsp;</td>
              </tr>
              <tr>
                <td valign="top"><div align="right"><bean:message key="txt.BankAcNo" /> :</div></td>
                <td><div align="right">
                <html:text property="txtBankAcNo"  styleClass="bdrColor_336666"  style="width:300px"  maxlength="20" readonly="true" />
                </div></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td valign="top"><div align="right"><bean:message key="txt.BankRoutingNo" />:</div></td>
                <td><div align="right">
                <html:text property="txtBankRoutingNo"  styleClass="bdrColor_336666"  style="width:300px"  maxlength="10" readonly="true"/>
                </div></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><div align="right">
                <html:cancel  styleClass="buttons" tabindex="1" ><bean:message  key="btn.Ok" /></html:cancel>
                </div></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td colspan="3">&nbsp;</td>
              </tr>
            </table>
            <!-- ADD Table Ends -->
          </td>
        </tr>
      </table>
      <!-- Main Table Ends -->
    </html:form>
  </BODY>
</HTML>
