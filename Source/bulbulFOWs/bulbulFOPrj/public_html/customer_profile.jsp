<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="title.CustomerProfile" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
  <script language="JavaScript" type="text/JavaScript" src="general.js"></script>
  <html:javascript formName="customerProfileForm" dynamicJavascript="true" staticJavascript="false" />
  <script language="javascript1.1" src="staticJavascript.jsp"></script>
  <script>
  <!--
    function imgFile_onChange(){
      var thisForm = document.forms[0];
      if((thisForm.fleProfileImage.value.toLowerCase()).indexOf(".jpg")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".jpeg")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".gif")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".png")==-1){
        alert('<bean:message key="alert.Error.SelectJpgGifPngOnly" />');
        thisForm.fleProfileImage.focus();
        return false;
      }
      thisForm.target="preview";
      thisForm.action="imagePreviewAction.do";
      thisForm.submit();
    }
    
    function btnCancel_onClick(){
      var thisForm=document.forms[0];
      thisForm.action="customerProfileAction.do";
      thisForm.target="_self";
      thisForm.submit();
    }
    
    function chkNewImage_onClick(){
      var thisForm = document.forms[0];
      var chkNewImageState = thisForm.chkNewImage.checked;

      if (chkNewImageState){
        thisForm.fleProfileImage.disabled=false;
        MM_showHideLayers('display','','hide');
        MM_showHideLayers('imgPreviewLayer','','show');
        MM_showHideLayers('noImgLayer','','hide');
      } else{
        thisForm.fleProfileImage.disabled=true;
        MM_showHideLayers('display','','show');
        MM_showHideLayers('imgPreviewLayer','','hide');
        MM_showHideLayers('noImgLayer','','show');
      }
    } 
    
    function stateCountrySelect(){
      if(document.forms[0].cboCountry.value!='0'){
        document.forms[0].txtCountry.value='';
        document.forms[0].hdnIsCountryListed.value='1';
      }
      if(document.forms[0].cboState.value!='0'){
        document.forms[0].txtState.value='';
        document.forms[0].hdnIsStateListed.value='1';
      }
    }
    function stateCountryNew(){
      if(trim(document.forms[0].txtCountry.value).length!=0){
        document.forms[0].cboCountry.value='0';
        document.forms[0].hdnIsCountryListed.value='0';
      }
      if(trim(document.forms[0].txtState.value).length!=0){
        document.forms[0].cboState.value='0';
        document.forms[0].hdnIsStateListed.value='0';
      }
    }
    
    function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.action="customerProfileAction.do";
      
      if(thisForm.chkUseAddress4Shipping.checked){
        thisForm.chkUseAddress4Shipping.value="1";
      }else{
        thisForm.chkUseAddress4Shipping.value="0";
      }
      
      if(thisForm.chkNewsLetter.checked){
        thisForm.chkNewsLetter.value="1";
      }else{
        thisForm.chkNewsLetter.value="0";
      }
      
      thisForm.target="_self";
      if(validateCustomerProfileForm(thisForm)){
        if(thisForm.fleProfileImage.disabled==false && thisForm.fleProfileImage.value==''){
          alert('<bean:message key="alert.SelectImage" />');
          return false;
        }else if(thisForm.fleProfileImage.disabled==false && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".jpg")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".jpeg")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".gif")==-1 && (thisForm.fleProfileImage.value.toLowerCase()).indexOf(".png")==-1){
          alert('<bean:message key="alert.Error.SelectJpgGifPngOnly" />');
          thisForm.imgFile.focus();
          return false;
        }
        
        thisForm.submit();
      }
    }
  -->
  </script>
</head>

<html:form action="/customerProfileAction" enctype="multipart/form-data">
<html:hidden property="hdnCustomerEmailIdTblPk" />
<html:hidden property="hdnImgOId" />
<html:hidden property="hdnContentType" />
<html:hidden property="hdnContentSize" />
<html:hidden property="hdnIsStateListed" />
<html:hidden property="hdnIsCountryListed" />
<html:hidden property="hdnIsNewProfile" />

  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Profile Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyProfile" />:</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold12">
        <div align="right">
          <span class="linkMenu"><bean:message key="menu.customer.Profile" /></span>&nbsp; | 
          <a class="linkMenu" href="changePasswordB4Action.do?hdnCustomerEmailIdTblPk=<bean:write name="customerInfo" property="customerEmailIdTblPk" />">&nbsp;<bean:message key="menu.customer.ChangePassword" />&nbsp;</a> | 
          <a class="linkMenu" href="changeEmailIdB4Action.do?hdnCustomerEmailIdTblPk=<bean:write name="customerInfo" property="customerEmailIdTblPk" />">&nbsp;<bean:message key="menu.customer.ChangeEmail" />&nbsp;</a>&nbsp;
        </div>
      </td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
      <!-- Table 1 Starts -->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td colspan="3" class="bdrTopColor_006600">&nbsp;</td>
          </tr>
          <tr>
            <td width="24%"><div align="right"><bean:message key="cbo.CustomerTitle" />:</div></td>
            <td width="50%">
              <html:select property="cboCustomerTitle" style="width:60px">
                <html:option value="Mr" >Mr</html:option>
                <html:option value="Mrs">Mrs</html:option>
                <html:option value="Miss">Miss</html:option>
              </html:select>
            </td>
            <td width="26%" rowspan="5" valign="middle">
              <logic:notEqual name="customerProfileForm" property="hdnContentSize" value="-1">
                <img name="display" id="display" style="position:relative; width:125px; height:125px;" class="bdrColor_006600" src="imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="customerProfileForm" property="hdnImgOId" />&imageContentType=<bean:write name="customerProfileForm" property="hdnContentType" />&imageContentSize=<bean:write name="customerProfileForm" property="hdnContentSize" />" ></img>
              </logic:notEqual>
              <logic:equal name="customerProfileForm" property="hdnContentSize" value="-1">
                <div id="noImgLayer" align="center" valign="middle" style="position:relative; width:125px; height:125px;" class="bdrColor_006600">
                  <bean:message key="img.location.msg" />
                </div>
              </logic:equal>
              <div id="imgPreviewLayer" style="display:none">
                <iframe name="preview" id="preview" style="position:relative; width:125px; height:125px;" marginwidth=0 marginheight=0 frameborder="0" align="top" class="bdrColor_006600"  ></iframe>
              </div>
            </td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.FirstName" />:</div></td>
            <td><html:text property="txtFirstName" style="width:200px" maxlength="20" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.LastName" />:</div></td>
            <td><html:text property="txtLastName" style="width:200px" maxlength="20" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.Age" />:</div></td>
            <td>
              <table border="0" cellpadding="0" cellspacing="0" width="200px">
                <tr>
                  <td width="60px">
                    <html:text property="txtAge" style="width:60px" />
                  </td>
                  <td>&nbsp;yrs.</td>
                  <td width="30px"></td>
                  <td align="right"><bean:message key="cbo.Gender" />:&nbsp;</td>
                  <td align="right">
                    <html:select  property="cboGender" style="width:60px">
                      <html:option value="M" >Male</html:option>
                      <html:option value="F">Female</html:option>
                    </html:select>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td ><div align="right"><bean:message key="img.UploadYourPhoto" />:</div></td>
            <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <input id="fleProfileImage" name="fleProfileImage" type="file" size="16" onchange="return imgFile_onChange();" disabled>
                  </td>
                  <td>
                    <input id="chkNewImage" type="checkbox" name="chkNewImage" onclick="return chkNewImage_onClick();"> 
                  </td>
                  <td><bean:message key="img.UploadNewImage" /></td>
                </tr>
              </table>                
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td colspan="2"><bean:message key="info.PhotoType" /></td>
          </tr>
          <tr>
            <td colspan="3">&nbsp;</td>
          </tr>
        </table>
        <!-- Table 1 Ends -->
        <!-- Table 2 Starts -->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td colspan="4" class="bdrTopColor_006600">&nbsp;</td>
          </tr>
          <tr>
            <td width="24%" valign="top"><div align="right"><bean:message key="txt.Address1" />:</div></td>
            <td width="28%"><html:text property="txtAddress1" style="width:200px" maxlength="100" /></td>
            <td width="8%">&nbsp;</td>
            <td width="40%">&nbsp;</td>
          </tr>
          <tr>
            <td width="24%" valign="top"><div align="right"><bean:message key="txt.Address2"  />:</div></td>
            <td width="28%"><html:text property="txtAddress2" style="width:200px" maxlength="100" /></td>
            <td width="8%">&nbsp;</td>
            <td width="40%">&nbsp;</td>
          </tr>
          <tr>
            <td width="24%" valign="top"><div align="right"><bean:message key="txt.Address3" />:</div></td>
            <td width="28%"><html:text property="txtAddress3" style="width:200px" maxlength="100" /></td>
            <td width="8%">&nbsp;</td>
            <td width="40%">&nbsp;</td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.City" />: </div></td>
            <td><html:text property="txtCity" style="width:200px" maxlength="25" /></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="cbo.State" />:</div></td>
            <td>
              <html:select property="cboState" style="width:200px" onchange="stateCountrySelect();" onclick="stateCountrySelect();">
                <html:option value="0">Select Your State</html:option>
                <html:option value="Goa">Goa</html:option>
                <html:option value="Punjab">Punjab</html:option>
              </html:select>                
            </td>
            <td><div align="right"><bean:message key="txt.OtherState" />:</div></td>
            <td><html:text property="txtState" style="width:200px" onchange="stateCountryNew();" maxlength="25" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="cbo.Country" />:</div></td>
            <td>
              <html:select property="cboCountry" style="width:200px" onchange="stateCountrySelect();" onclick="stateCountrySelect();">
                <html:option value="0" >Select Your Country</html:option>
                <html:option value="India">India</html:option>
              </html:select>
            </td>
            <td><div align="right"><bean:message key="txt.OtherCountry" />: </div></td>
            <td><html:text property="txtCountry" style="width:200px" onchange="stateCountryNew();" maxlength="25" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.Pincode" />: </div></td>
            <td><html:text property="txtPincode" style="width:200px" maxlength="10" /></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td colspan="3">
            <%--<logic:equal name="customerProfileForm" property="chkUseAddress4Shipping" value="0">
              <html:checkbox property="chkUseAddress4Shipping" value="0" />
            </logic:equal>
            <logic:notEqual  name="customerProfileForm" property="chkUseAddress4Shipping" value="0">
              <html:checkbox property="chkUseAddress4Shipping" value="1" />
            </logic:notEqual>--%>
            <logic:equal name="customerProfileForm" property="chkUseAddress4Shipping" value="1">
              <input type="CHECKBOX" id="chkUseAddress4Shipping" name="chkUseAddress4Shipping" value="1" checked/>
            </logic:equal>
            <logic:notEqual  name="customerProfileForm" property="chkUseAddress4Shipping" value="1">
              <input type="CHECKBOX" id="chkUseAddress4Shipping" name="chkUseAddress4Shipping" value="0" />
            </logic:notEqual>
              <bean:message key="chk.UseAddress4Shipping" />
            </td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.Phone1" />:</div></td>
            <td><html:text property="txtPhone1" style="width:200px" maxlength="15" /></td>
            <td colspan="2" rowspan="3" valign="top">
              <bean:message key="info.StringType" />
            </td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.Phone2" />:</div></td>
            <td><html:text property="txtPhone2" style="width:200px" maxlength="15" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.Mobile" />:</div></td>
            <td><html:text property="txtMobile" style="width:200px" maxlength="15" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td colspan="3">
            <logic:equal name="customerProfileForm" property="chkNewsLetter" value="1" >
              <input type="CHECKBOX" id="chkNewsLetter" name="chkNewsLetter" value="1" checked />
            </logic:equal>
            <logic:equal name="customerProfileForm" property="chkNewsLetter" value="0" >
              <input type="CHECKBOX" id="chkNewsLetter" name="chkNewsLetter" value="0"  />
            </logic:equal>
                <bean:message key="chk.NewsLetter" />
            </td>
          </tr>
          <tr>
            <td height="30px">&nbsp;</td>
            <td colspan="3">
              <html:button  property="btnSave" styleClass="buttons" style="margin-left:0px; width:63px" onclick="btnclick();"><bean:message key="btn.Save" /></html:button>
              <html:cancel styleClass="buttons" style="width:63px"><bean:message  key="btn.Cancel" /></html:cancel>
              <html:reset  property="btnClear" styleClass="buttons"  style="width:63px"><bean:message  key="btn.Reset" /></html:reset>
            </td>
          </tr>
        </table>
        <!-- Table 2 Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Profile Table Ends -->
</html:form>
</body>
</html>
