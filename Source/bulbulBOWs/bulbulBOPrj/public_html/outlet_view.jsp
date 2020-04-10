<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
  <HEAD>  
    <TITLE><bean:message  key="title.OutletView" /> </TITLE>
  </HEAD>
  <BODY >
  <html:form action="/outletViewAction">
  <html:hidden property="hdnSearchPageNo" /> 
    <jsp:include page="header.jsp" />
    <!-- Tab Table Starts -->
    <table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200px" class="tab" height="20px">
          <div align="center"><bean:message key="tab.Outlet" /></div>
        </td>
        <td width="770px" >
        </td>
      </tr>
    </table>
    <!-- Tab Table Ends -->      
    <!-- Main Table Starts Here-->
    <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
      <td height="505px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <!-- View Table Starts Here-->
      <table width="500px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="4"><div align="center"><bean:message  key="page.OutletView"/></div></th>
        </tr>
        <tr>
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.OutletId"/>: </div></td>
          <td colspan="2">  
            <html:text property="txtOutletId" styleClass="bdrColor_336666" style="width:300px"  maxlength="10" readonly="true" />   
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td width="137px"><div align="right"><bean:message  key="txt.OutletName" />:</div></td>
          <td colspan="2"> 
            <html:text property="txtOutletName" styleClass="bdrColor_336666"  style="width:300px" maxlength="25" readonly="true"/>  
          </td>
          <td width="44px">&nbsp;</td>
        </tr>
        <tr>
          <td valign="top"><div align="right"><bean:message  key="txa.OutletDesc" />:</div></td>
          <td colspan="2">
            <html:textarea property="txaOutletDesc" cols="" rows="4" styleClass="bdrColor_336666"   style="width:300px" readonly="true"/> 
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.Address1" />: </div></td>
          <td colspan="2"> 
            <html:text property="txtAddress1" styleClass="bdrColor_336666" style="width:300px" maxlength="25" readonly="true"/>   
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.Address2" />:</div></td>
          <td colspan="2"> 
            <html:text property="txtAddress2" styleClass="bdrColor_336666" style="width:300px" maxlength="25" readonly="true"/>    
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.Address3" />:</div></td>
          <td colspan="2">   
            <html:text property="txtAddress3" styleClass="bdrColor_336666" style="width:300px" maxlength="25" readonly="true"/>  
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.City" />:</div></td>
          <td width="123px"> 
            <html:text property="txtCity" styleClass="bdrColor_336666" style="width:100px" maxlength="25" readonly="true"/> 
          </td>
          <td width="176px">
           <div align="right">
            <bean:message  key="cbo.State" />:
            <html:select property="cboState" styleClass="bdrColor_336666" style="width:120px" disabled="true">
             <html:option value="1">
              Maharashta
             </html:option>
             <html:option value="2">
              Uttar Pradesh
             </html:option>
            </html:select>
           </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message  key="txt.Pincode" />:</div></td>
          <td>
            <html:text property="txtPincode" styleClass="bdrColor_336666" style="width:100px" maxlength="10" readonly="true"/> 
          </td>
          <td> 
           <div align="right">
            <bean:message  key="cbo.Country"/> 
            <html:select property="cboCountry" styleClass="bdrColor_336666" style="width:120px" disabled="true">
             <html:option value="1">
              India
             </html:option>
            </html:select>
            </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="2">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="2"><div align="right">
            <html:cancel  styleClass="buttons" tabindex="1" ><bean:message  key="btn.Ok" /></html:cancel>
            </div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td colspan="4">&nbsp;</td>
        </tr>
      </table>
      <!-- View Table Ends Here-->

      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="500px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
        <tr bgcolor="#FFFFFF">
          <td width="30px" ><div class="imgStatusMsg"></td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->
      
      </td>
      </tr>
    </table>
    <!-- Main Table Ends Here-->
  </html:form>
  </BODY>
</HTML>
