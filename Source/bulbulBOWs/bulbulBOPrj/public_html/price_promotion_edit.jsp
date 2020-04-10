<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.PricePromotionEdit" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
  <script src="datepicker.js"></script>

  <!-- Date Related Scripts-->
  <script>
  <!--
    var startDatePicker = new Calendar("startDatePicker",0,0);

    startDatePicker.noTimezone=true;
    startDatePicker.noTime=true;
    startDatePicker.onclick="selectedValues(startDatePicker,'txtStartDate')";
    startDatePicker.onclear="clearValues('txtStartDate')";
    startDatePicker.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    startDatePicker.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    startDatePicker.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    startDatePicker.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    startDatePicker.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    startDatePicker.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    startDatePicker.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    startDatePicker.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    startDatePicker.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    startDatePicker.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    startDatePicker.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    startDatePicker.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    startDatePicker.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    startDatePicker.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

    var endDatePicker = new Calendar("endDatePicker",0,0);

    endDatePicker.noTimezone=true;
    endDatePicker.noTime=true;
    endDatePicker.onclick="selectedValues(endDatePicker,'txtEndDate')";
    endDatePicker.onclear="clearValues('txtEndDate')";
    endDatePicker.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    endDatePicker.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    endDatePicker.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    endDatePicker.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    endDatePicker.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    endDatePicker.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    endDatePicker.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    endDatePicker.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    endDatePicker.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    endDatePicker.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    endDatePicker.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    endDatePicker.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    endDatePicker.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    endDatePicker.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';  

    function  selectedValues(dtPickerObj, txtDateTimeName){
      var dateString=dtPickerObj.getYear();
      dateString+="-" +dtPickerObj.getMonth();
      dateString+="-" +dtPickerObj.getDay();
      eval("document.forms[0]."+txtDateTimeName).value=dateString;
    }  

    function  clearValues(txtDateTimeName){
      eval("document.forms[0]."+txtDateTimeName).value="";
    }
  -->
  </script>  
  
  <html:javascript formName="pricePromotionForm" dynamicJavascript="true" staticJavascript="false" />
  <script language="javascript1.1" src="staticJavascript.jsp"></script>
  <script>
  <!--
    function btnclick(operation){
      var thisForm=document.forms[0];
      var currentDate=new Date();
      startDate=new Date(thisForm.txtStartDate.value);
      endDate=new Date(thisForm.txtEndDate.value);
      thisForm.target="_self";
      if ((operation=='save' && validatePricePromotionForm(thisForm)) ){
        if(thisForm.txtStartDate.value=='' || thisForm.txtEndDate.value==''){
          if(thisForm.txtStartDate.value==''){
            alert('<bean:message key="alert.Error.StartDateSelect" />');
            return 'false';
          }
          if(thisForm.txtEndDate.value==''){
            alert('<bean:message key="alert.Error.EndDateSelect" />');
            return 'false';
          }
            
        }else if((startDate-endDate)>0){
          alert('<bean:message key="alert.Error.StartDateNGTEndDate" />');
          return 'false';
        }else if((currentDate-startDate)>0){
          alert('<bean:message key="alert.Error.StartDateNLTCurrDate" />');
          return 'false';
        }else
          thisForm.submit();
      }
    }

    function window_onload(){
      var currentDate=null;
      if (typeof document.forms[0].txtStartDate !='undefined'){
          if(document.forms[0].txtStartDate.value!=""){
              currentDate=new Date(document.forms[0].txtStartDate.value);
          }else{
              currentDate=new Date();
          }
          startDatePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                      currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
          if(document.forms[0].txtStartDate.value!=""){
              startDatePicker.click();
          }
      }   

      if (typeof document.forms[0].txtEndDate !='undefined'){
          if(document.forms[0].txtEndDate.value!=""){
              currentDate=new Date(document.forms[0].txtEndDate.value);
          }else{
              currentDate=new Date();
          }
          endDatePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                          currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
          if(document.forms[0].txtEndDate.value!=""){
              endDatePicker.click();
          }
      } 
    }    
    -->
  </script>
</HEAD>
<BODY onload="return window_onload();">
<html:form action="/pricePromotionEditAction" >
<html:hidden property="hdnPricePromotionTblPk" />
<html:hidden property="hdnMerchandiseTblPk" /> 
<html:hidden property="hdnMerchandiseSizeTblPk" /> 
<html:hidden property="hdnMerchandiseCategoryTblPk" /> 
<html:hidden property="hdnSearchPageNo" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.PricePromotion" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="505px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="373px"  border="0" align="center" class="bdrColor_336666">
        <tr>
          <th height="20px" colspan="3"><div align="center"><bean:message key="page.PricePromotionEdit" /></div></th>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="80px"><div align="right"><bean:message key="txt.StartDate" /> :</div></td>
          <td width="201px">                
            <html:hidden property="txtStartDate" />
            <script>startDatePicker.render();</script>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message key="txt.EndDate" /> :</div></td>
          <td>                
            <html:hidden property="txtEndDate" />
            <script>endDatePicker.render();</script>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message key="txt.ThresholdQty" /> :</div></td>
          <td>
            <html:text property="txtThresholdQty" styleClass="bdrColor_336666" onkeypress="return integerOnly(event);" style="width:50px;text-align:right"  maxlength="3"/>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message key="txt.Discount" /> :</div></td>
          <td>                
            <html:text property="txtDiscount" styleClass="bdrColor_336666" style="width:50px;text-align:right" maxlength="5" onkeypress="return decimalOnly(this,event,2);" />(%)
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><bean:message key="tbl.Flag" /> :</div></td>
          <td>
            <html:radio property="radExclusiveByPass" value="1" />
            <bean:message key="rad.Exclusive" />
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <html:radio property="radExclusiveByPass" value="0" />
            <bean:message key="rad.ByPass" />
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td colspan="4">
            <div align="center">
              <html:button property="btnSave" styleClass="buttons" tabindex="11" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
              <html:cancel  styleClass="buttons" tabindex="12" ><bean:message  key="btn.Cancel" /></html:cancel>
              <html:reset property="btnClear" tabindex="13" styleClass="buttons"  ><bean:message  key="btn.Clear" /></html:reset>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="374px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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
