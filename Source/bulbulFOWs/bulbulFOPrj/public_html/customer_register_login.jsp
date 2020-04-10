<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
  <head>
    <title><bean:message key="title.CustomerRegisterLogin" /></title>
    <link href="main.css" rel="stylesheet" type="text/css" >
    <link href="myprntn.css" rel="stylesheet" type="text/css" >
    <script language="JavaScript" type="text/JavaScript" src="general.js"></script>
    <script language="JavaScript" type="text/JavaScript" src="datepicker.js"></script>
    <html:javascript formName="customerRegisterForm" dynamicJavascript="true" staticJavascript="false" />
    <script language="javascript1.1" src="staticJavascript.jsp"></script>
    <!-- Date Related Scripts-->
    <script>
    <!--
     var dateOfBirth = new Calendar("dateOfBirth",190,0);

      dateOfBirth.noTimezone=true;
      dateOfBirth.noTime=true;
      dateOfBirth.textClassName='';
      dateOfBirth.boldClassName='th_0A8A09';
      dateOfBirth.borderClassName='bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 bdrBottomColor_006600';
      dateOfBirth.bgClassName='bgColor_EBFFE9';
      dateOfBirth.onclick="selectedValues(dateOfBirth,'txtDateOfBirth')";
      dateOfBirth.onclear="clearValues('txtDateOfBirth')";
      dateOfBirth.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
      dateOfBirth.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
      dateOfBirth.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
      dateOfBirth.tooltipDay='<bean:message key="tooltips.cal.Day" />';
      dateOfBirth.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
      dateOfBirth.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
      dateOfBirth.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
      dateOfBirth.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
      dateOfBirth.tooltipNow='<bean:message key="tooltips.cal.Now" />';
      dateOfBirth.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
      dateOfBirth.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
      dateOfBirth.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
      dateOfBirth.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
      dateOfBirth.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

      function  selectedValues(dtPickerObj, txtDateTimeName){
        var dateString=dtPickerObj.getYear();
        dateString+="-"+dtPickerObj.getMonth();
        dateString+="-"+dtPickerObj.getDay();
        eval("document.forms[1]."+txtDateTimeName).value=dateString;
      }  

      function  clearValues(txtDateTimeName){
        eval("document.forms[1]."+txtDateTimeName).value="";
      }
    //-->
    </script>  

    <script>
    <!--
    function btnclick(){
      var thisForm=document.forms[1];
      thisForm.target="_self";
      if(validateCustomerRegisterForm(thisForm)){
        if(thisForm.txtCustomerPassword.value!=thisForm.txtConfirmPassword.value){
          alert("<bean:message key="alert.Error.PasswordsNotMatch" />");
          return false;
        }
        if(thisForm.cboPwdHintQuestion[0].selected){
          alert('<bean:message key="alert.SelectQuestion" />');
          return false;
        }
        if(thisForm.chkDesignCode.checked && (trim(thisForm.txtDesignCode.value)).length==0){
          alert('<bean:message key="alert.DesignCode" />');
          return false;
        }
        thisForm.operation.value="register";
        thisForm.submit();
      }
    }
    function window_onload(){
      var thisForm=document.forms[1];
      var currentDate=new Date();
          dateOfBirth.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                      currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
          dateOfBirth.initialize();
          dateOfBirth.click();
          
          thisForm.txtDesignCode.disabled=!(thisForm.chkDesignCode.checked);
          thisForm.chkNewsLetter.checked=true;
    }
    function chkDesign_onclick(){
      var thisForm=document.forms[1];
      thisForm.txtDesignCode.disabled=!(thisForm.chkDesignCode.checked);
      thisForm.txtDesignCode.value="";
    }
    //-->
    </script>
  </head>
  <body onload="return window_onload();">
  
    <html:form action="customerLoginAction.do">
    <input type="hidden" name="operation" id="operation" value="<bean:write name="operation" />">
     <!-- Seperator Table -->
      <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10px"></td>
        </tr>
      </table>
      <!-- Login Table Starts-->
      <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panLogin">
        <tr>
          <td width="24px" height="17px">
            <img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"/>
          </td>
          <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.CustomerLogin" /> :</td>
          <td width="5px">
            <img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"/>
          </td>
        </tr>
        <tr>
          <td height="35px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold14">
            <span style="margin-left:10px"><bean:message key="q.AlreadyAPrintoonite" /> </span>
            <a href="#" onClick="MM_findObj('loginDrop').style.display=='none'?MM_showHideLayers('loginDrop','','show'):MM_showHideLayers('loginDrop','','hide')"><bean:message key="link.SignIn" /></a> 
          </td>
        </tr>
          <logic:equal name="operation" value="login">
          <tr id="loginDrop" style="display:'';" >
          </logic:equal>
          <logic:notEqual name="operation" value="login">
          <tr id="loginDrop" style="display:none;" >
          </logic:notEqual>
          <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 ">
            <table width="100%" border="0">
              <tr>
                <td colspan="3" align="center" >
                  <div style="color:#ff0000">
                    <b>
                      <html:messages id="messageLoginFailed" message="true"  property="messageLoginFailed" >
                        <bean:write name="messageLoginFailed" />
                      </html:messages>
                    </b>
                  </div>                
                </td>
              </tr>
              <tr>
                <td width="23%">
                  <div align="right"><bean:message key="txt.LoginEmailId" />:</div>
                </td>
                <td width="200px">
                  <html:text property="txtLoginEmailId" style="width:200px"/>
                </td>
                </td></td>
              </tr>
              <tr>
                <td>
                  <div align="right"><bean:message key="txt.LoginPassword" />:</div>
                </td>
                <td>
                  <html:password  property="txtLoginPassword" style="width:200px"/>
                </td>
                <td></td>
              </tr>
              <tr>
                <td height="30px"></td>
                <td align="right">
                  <html:button property="btnLogin" onclick="this.form.operation.value='login'; this.form.submit();" styleClass="buttons" style="margin-left:0px;width:63px"><bean:message key="btn.SignIn" /></html:button>
                </td>
                <td></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td height="3px">
            <img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"/>
          </td>
          <td background="images/myprntn_pan_tile3px.gif"></td>
          <td>
            <img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"/>
          </td>
        </tr>
      </table>
      <!-- Login Table Ends-->
    </html:form>
    
    
    <html:form action="/customerRegisterAction.do">
      <input type="hidden" name="operation" id="operation" value="<bean:write name="operation" />">
      <!-- Registration Table Starts-->
      <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
        <tr>
          <td width="24px" height="17px">
            <img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"/>
          </td>
          <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.CustomerRegister" /> :</td>
          <td width="5px">
            <img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"/>
          </td>
        </tr>
        <tr>
          <td height="35px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold14">
            <span style="margin-left:10px"><bean:message key="q.Want2Register" /></span> 
            <a href="#" onClick="MM_findObj('registeringDrop').style.display=='none'?MM_showHideLayers('registeringDrop','','show'):MM_showHideLayers('registeringDrop','','hide')"><bean:message key="link.Here" /> </a>
          </td>
        </tr>
        
        <logic:equal name="operation" value="register">
        <tr id="registeringDrop" style="display:'';">
        </logic:equal>
        <logic:notEqual name="operation" value="register">
        <tr id="registeringDrop" style="display:none;">
        </logic:notEqual>
          <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
            <table width="100%" border="0" >
              <tr>
                <td colspan="3" align="center" >
                  <div style="color:#ff0000">
                    <b>
                      <html:messages id="messageEmailIdAlreadyRegistered" message="true" property="messageEmailIdAlreadyRegistered" >
                        <bean:write name="messageEmailIdAlreadyRegistered" />
                      </html:messages>
                    </b>
                    <b>
                      <html:messages id="messageEmailIdInUse" message="true" property="messageEmailIdInUse" >
                        <bean:write name="messageEmailIdInUse" />
                      </html:messages>
                    </b>
                    <b>
                      <html:messages id="messageInvalidDesignCode" message="true" property="messageInvalidDesignCode" >
                        <bean:write name="messageInvalidDesignCode" />
                      </html:messages>
                    </b>
                  </div>                
                </td>
              </tr>
              <tr>
                <td width="23%">
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="txt.CustomerEmailId" />:
                  </div>
                </td>
                <td>
                  <html:text property="txtCustomerEmailId" style="width:200px" maxlength="50"/>
                </td>
                <td>
                </td>
              </tr>
              <tr>
                <td>
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="txt.CustomerPassword" />:
                  </div>
                </td>
                <td>
                  <html:password  property="txtCustomerPassword" style="width:200px" maxlength="20"/>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="txt.ConfirmPassword" />:
                  </div>
                </td>
                <td>
                  <html:password property="txtConfirmPassword" style="width:200px" maxlength="20"/>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="cbo.PwdHintQuestion" />:
                  </div>
                </td>
                <td>
                  <html:select property="cboPwdHintQuestion" style="width:200px">
                    <html:option value="0">-- Select a hint question --</html:option>
                    <html:option value="What is your first school name?" > What is your first school name? </html:option>
                    <html:option value="What is your father's name?" > What is your father's name? </html:option>
                    <html:option value="What is your friend's name?" > What is your friend's name? </html:option>
                  </html:select>
                </td>
                <td></td>                
              </tr>
              <tr>
                <td>
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="txt.PwdHintAnswer" />:
                  </div>
                </td>
                <td>
                  <html:password property="txtPwdHintAnswer" style="width:200px"/>
                </td>
                <td></td>                
              </tr>
              <tr>
                <td>
                  <div align="right">
                    <span class="text_FF3300">*</span> <bean:message key="txt.DateOfBirth" />:
                  </div>
                </td>
                <td>
                  <html:hidden property="txtDateOfBirth" style="width:200px"/>
                  <script>dateOfBirth.render();</script>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <div align="right"><bean:message key="txt.DesignCode" />:</div>
                </td>
                <td>
                  <html:text property="txtDesignCode"style="width:200px" /> 
                </td>
                <td>
                  <table border="0" cellpadding="1" cellspacing="1" > 
                    <tr>
                      <td>
                        <html:checkbox  property="chkDesignCode" value="1" onclick="return chkDesign_onclick();"/>
                      </td>
                      <td>
                        <bean:message key="chk.DesignCode" />
                      </td>
                    </tr>
                  </table>
                </td>                
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td colspan="2">
                  <html:checkbox  property="chkNewsLetter" value="1"  />
                  <bean:message key="chk.NewsLetter" />
                </td>
                
              </tr>
              <tr>
                <td height="30px"></td>
                <td>
                  <html:button property="btnRegister" styleClass="buttons" onclick="btnclick()" style="margin-left:0px;width:63px" ><bean:message key="btn.Register" /></html:button>
                  <html:cancel styleClass="buttons" style="width:63px"><bean:message key="btn.RegisterCancel" /></html:cancel>
                  <html:reset property="btnClear" styleClass="buttons"  style="width:63px"><bean:message  key="btn.Clear" /></html:reset>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="3px">
            <img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"/>
          </td>
          <td background="images/myprntn_pan_tile3px.gif"></td>
          <td>
            <img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"/>
          </td>
        </tr>
      </table>
      <!-- Registration Table Ends-->
    </html:form>
  </body>
</html>
