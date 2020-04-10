<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="pricePromotionListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="pricePromotionListForm" property="hdnSearchPageNo" />
<bean:define id="pricePromotions" name="pricePromotions" type="java.util.ArrayList" /> 
<bean:define id="merchandiseSizeColors" name="merchandiseSizeColors" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />      

<HTML>
<HEAD>  
  <TITLE><bean:message key="title.PricePromotionList" /></TITLE>
    <link href="main.css" rel="stylesheet" type="text/css">
    <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
    <script src="datepicker.js"></script>
    <!-- Date Related Scripts-->
    <script>
    <!--  
      var startDatePicker = new Calendar("startDatePicker",0,0);

      startDatePicker.noTimezone=true;
      startDatePicker.noTime=true;
      startDatePicker.onclick="selectedValues(startDatePicker,'txtSearchStartDate')";
      startDatePicker.onclear="clearValues('txtSearchStartDate')";
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
      endDatePicker.onclick="selectedValues(endDatePicker,'txtSearchEndDate')";
      endDatePicker.onclear="clearValues('txtSearchEndDate')";
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
        var dateString=dtPickerObj.getMonth();
        dateString+="/" +dtPickerObj.getDay();
        dateString+="/" +dtPickerObj.getYear();
        eval("document.forms[0]."+txtDateTimeName).value=dateString;
      }  

      function  clearValues(txtDateTimeName){
        eval("document.forms[0]."+txtDateTimeName).value="";
      }
    -->
    </script>      
    <script language="JavaScript" type="text/JavaScript" src="navigationbar.js" ></script>
    <script>
    <!--
      var pageCount=parseInt(<%=pageCount%>);
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNo%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="btnclick('page')";

      navBar.msgPageNotExist="<bean:message  key="page.pageNumberNotExists" />";
      navBar.msgNumberOnly="<bean:message   key="page.enterNumbersOnly"/>";    
      navBar.msgEnterPageNo="<bean:message   key="page.enterPageNo"/>";
      navBar.msgOnFirst="<bean:message    key="page.onFirst"/>";
      navBar.msgOnLast="<bean:message    key="page.onLast"/>";

      navBar.lblPage="<bean:message    key="lbl.Page"/>";
      navBar.lblOf="<bean:message    key="lbl.Of"/>";

      navBar.tooltipPageNo="<bean:message    key="tooltips.PageNo"/>";
      navBar.tooltipFirst="<bean:message    key="tooltips.First"/>";
      navBar.tooltipNext="<bean:message    key="tooltips.Next"/>";
      navBar.tooltipPrev="<bean:message    key="tooltips.Previous"/>";
      navBar.tooltipLast="<bean:message    key="tooltips.Last"/>";
      navBar.tooltipGo="<bean:message    key="btn.Go"/>";
    -->
    </script>
    
    <script language="JavaScript" type="text/JavaScript"  >
    <!--
      function radioOnlick(me){
        var thisForm = me.form;
        var index = getRadioSelectedIndex(thisForm.radSearchSelect);
        var isActive =false; 
        if (index>-2) {
          if (index==-1){
            isActive=(thisForm.hdnSearchStatus.value==ACTIVE_VAL);
          }else{
            isActive=(thisForm.hdnSearchStatus[index].value==ACTIVE_VAL);
          }
        }
        if(isActive){
          thisForm.btnActDeact.value='<bean:message   key="btn.Deactivate" />';
          thisForm.hdnSearchCurrentStatus.value=INACTIVE_VAL;
        }else{
          thisForm.btnActDeact.value='<bean:message   key="btn.Activate" />';
          thisForm.hdnSearchCurrentStatus.value=ACTIVE_VAL;
        } 
      
      }
      function btnclick(operation){
        var thisForm=document.forms[0];
        thisForm.target="_self";
        thisForm.hdnSearchOperation.value=operation;
        var index=0;
  
        if (operation!='page' && operation!='search' && operation!='add'){
          index=getRadioSelectedIndex(thisForm.radSearchSelect);
          if (index>-2){
            if( operation=='delete'){
              if (confirm('<bean:message   key="alert.delete.confirm" />')){
                thisForm.submit();
              }else{
                return false;
              }
            }
            thisForm.submit();
          }else{
            alert('<bean:message   key="errors.selection.required" />');
          }
        }else{
          if(operation=='add'){
            if(thisForm.hdnMerchandiseSizeTblPk.value=='-1'){
              alert('<bean:message   key="alert.Selection.Size.Required4Promotion" />');
              return false;
            }
          }
          thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
          thisForm.submit();
        } 
      }

      function window_onload(){
        var currentDate=null;
        if (typeof document.forms[0].txtSearchStartDate !='undefined'){
            if(document.forms[0].txtSearchStartDate.value!=""){
                currentDate=new Date(document.forms[0].txtSearchStartDate.value);
            }else{
                currentDate=new Date();
            }
            startDatePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                        currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
            if(document.forms[0].txtSearchStartDate.value!=""){
                startDatePicker.click();
            }
        }   

        if (typeof document.forms[0].txtSearchEndDate !='undefined'){
            if(document.forms[0].txtSearchEndDate.value!=""){
                currentDate=new Date(document.forms[0].txtSearchEndDate.value);
            }else{
                currentDate=new Date();
            }
            endDatePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
            if(document.forms[0].txtSearchEndDate.value!=""){
                endDatePicker.click();
            }
        }
        
        var thisForm=document.forms[0];
        if(thisForm.hdnSearchOperation.value=='search'){
          MM_showHideLayers('tblSearch','','show','tblButtons','','hide');
        }
      } 

      function hideShowSizeLyr(imgObj, imgSrc4Hide,imgSrc4Show, sizeLyrId){
        var objSizeLyr=MM_findObj(sizeLyrId);
        if (objSizeLyr.style.display=='none'){
          MM_showHideLayers(sizeLyrId,'','show');
          imgObj.src=imgSrc4Show;
        }else{
          MM_showHideLayers(sizeLyrId,'','hide');
          imgObj.src=imgSrc4Hide;
        }
      }
      
      function btnSearchCloseOnclick(){
        var thisForm=document.forms[0];
        startDatePicker.clear();
        endDatePicker.clear();
        thisForm.radSearchExclusiveByPass[2].checked=true;
        thisForm.radSearchStatus[2].checked=true;
        MM_showHideLayers('tblSearch','','hide','tblButtons','','show');
      }
      
      function document_onkeypress(e){
        var key;
        if (window.event){
          key = window.event.keyCode;
        }else if (e){
          key = e.which;
        }else{
          return false;
        }      
        if(key==13 && MM_findObj('tblSearch').style.display!='none'){
          btnclick('search');
        }
      }
      document.onkeypress=document_onkeypress;
      
      //-->
    </script> 
</HEAD>
<BODY onload="return window_onload();">   
<html:form action="/pricePromotionRelayAction.do">
<html:hidden property="hdnSearchOperation" />
<html:hidden property="hdnSearchCurrentStatus" />
<html:hidden property="hdnSearchPageNo" /> 
<html:hidden property="hdnMerchandiseTblPk" /> 
<html:hidden property="hdnMerchandiseSizeTblPk" /> 
<html:hidden property="hdnMerchandiseCategoryTblPk" /> 
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
<!-- Main Table Starts -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td valign="top" >
      <!-- Search and buttons Table -->
		  <table id="tblSearchNdbuttons" width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="87%" height="60px" valign="bottom">
            <!-- Search Table starts -->
            <table id="tblSearch" width="100%"  border="0" class="bdrColor_336666" style="display:none ">
              <tr>
                <td><div align="right"><bean:message key="txt.StartDate" />:</div></td>
                <td>
                  <html:hidden property="txtSearchStartDate" />
                  <script>startDatePicker.render();</script>
                </td>
                <td><div align="right"><bean:message key="txt.EndDate" />:</div></td>
                <td>
                  <html:hidden property="txtSearchEndDate" />
                  <script>endDatePicker.render();</script>
                </td>
                <td>
                  <div align="right"><bean:message key="tbl.Flag" />:</div>
                </td>
                <td>
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <html:radio property="radSearchExclusiveByPass" value="1" /> 
                      </td>
                      <td>
                        <bean:message key="rad.Exclusive" />
                      </td>
                      <td>
                        <html:radio property="radSearchExclusiveByPass" value="0" /> 
                      </td>
                      <td>
                        <bean:message key="rad.ByPass" />
                      </td>
                      <td>
                        <html:radio property="radSearchExclusiveByPass" value="2" />
                      </td>
                      <td>
                        <bean:message key="rad.Both" />
                      </td>
                    <tr>
                  </table>
                </td>
                <td valign="top" align="right">
                  <a href="#"><img src="images/icon_search.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onClick="return btnclick('search');"></a>
                  <a href="#"><img src="images/icon_close.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onClick="btnSearchCloseOnclick();" ></a>
                </td>
              </tr>
              <tr>
                <td><div align="right"><bean:message key="tbl.Status" />:</div></td>
                <td colspan="6">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <html:radio property="radSearchStatus" value="1"  ></html:radio>
                      </td>
                      <td>
                        <bean:message  key="rad.Status.Active" /> 
                      </td>
                      <td>
                        <html:radio property="radSearchStatus" value="0"  ></html:radio>
                      </td>
                      <td>
                        <bean:message  key="rad.Status.InActive" />
                      </td>
                      <td>
                        <html:radio property="radSearchStatus" value="2"  ></html:radio>
                      </td>
                      <td>
                        <bean:message  key="rad.Status.Both" />  
                      </td>
                    <tr>
                  </table>
                </td>
              </tr>
            </table>			
            <!-- Search Table ends -->
            <!-- Button Table Starts -->
            <table id="tblButtons" width="100%"   border="0" cellpadding="0" cellspacing="0" align="center" >
              <tr>
                <td>
                  <html:button property="btnSearch" value="Search" styleClass="buttons" tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                  <logic:iterate id="module" name="userRights">          
                    <logic:equal name="module" property="name" value="Price Promotion">
                      <logic:iterate id="permission" name="module" property="permissions">  
                      
                        <!--View Button Enable Disable-->
                        <logic:equal name="permission" property="name" value="View">
                          <logic:equal name="permission" property="value" value="1">
                            <html:button property="btnView" styleClass="buttons"  tabindex="13" onclick="btnclick('view')" ><bean:message  key="btn.View" /></html:button>
                          </logic:equal>
                          <logic:equal name="permission" property="value" value="0">
                            <html:button property="btnView" styleClass="buttonDisabled" tabindex="13" disabled="true"  ><bean:message  key="btn.View" /></html:button>                                            
                          </logic:equal>
                        </logic:equal>
                          
                        <!--Add Button Enable Disable-->
                        <logic:equal name="permission" property="name" value="Add">
                          <logic:equal name="permission" property="value" value="1">
                            <html:button property="btnAdd" styleClass="buttons" tabindex="14" onclick="btnclick('add')"><bean:message  key="btn.Add" /></html:button>
                          </logic:equal>
                          <logic:equal name="permission" property="value" value="0">
                            <html:button property="btnAdd" styleClass="buttonDisabled" tabindex="14" disabled="true" ><bean:message  key="btn.Add" /></html:button>                              
                          </logic:equal>
                        </logic:equal>

                        <!--Edit Button Enable Disable-->
                        <logic:equal name="permission" property="name" value="Edit">
                          <logic:equal name="permission" property="value" value="1">
                            <html:button property="btnEdit" styleClass="buttons" tabindex="15" onclick="btnclick('edit')" ><bean:message  key="btn.Edit" /></html:button>
                          </logic:equal>
                          <logic:equal name="permission" property="value" value="0">
                            <html:button property="btnEdit" styleClass="buttonDisabled" tabindex="15" disabled="true" ><bean:message  key="btn.Edit" /></html:button>
                          </logic:equal>
                        </logic:equal>
                      
                        <!--Delete Button Enable Disable-->
                        <logic:equal name="permission" property="name" value="Delete">
                          <logic:equal name="permission" property="value" value="1">
                            <html:button property="btnDelete" styleClass="buttons" tabindex="16" onclick="btnclick('delete')"><bean:message  key="btn.Delete" /></html:button>
                          </logic:equal>
                          <logic:equal name="permission" property="value" value="0">
                            <html:button property="btnDelete" styleClass="buttonDisabled" tabindex="16" disabled="true"><bean:message  key="btn.Delete" /></html:button>
                          </logic:equal>
                        </logic:equal>
                          
                        <!--Activate/Deactivate Button Enable Disable-->
                        <logic:equal name="permission" property="name" value="Activate/Deactivate">
                          <logic:equal name="permission" property="value" value="1">
                            <html:button property="btnActDeact" styleClass="buttons" tabindex="17" onclick="btnclick('act_deact')" ><bean:message  key="btn.Activate" /></html:button>
                          </logic:equal>
                          <logic:equal name="permission" property="value" value="0">
                            <html:button property="btnActDeact" styleClass="buttonDisabled" tabindex="17" disabled="true"><bean:message  key="btn.Activate" /></html:button>
                          </logic:equal>
                        </logic:equal>
                          
                      </logic:iterate>                          
                    </logic:equal>
                  </logic:iterate>    
                </td>
              </tr>
            </table>
            <!-- Button Table Ends -->
          </td>
        </tr>
        <tr>
          <td height="5px" ></td>
        </tr>
      </table>
      <!-- Search and Button Table Ends -->
    </td>
  </tr>
  <tr>
    <td align="center">
      <!-- Size Color Tree and List Table Starts-->
      <table border="0" cellpadding="0" cellspacing="0" width="98%" height="422px">
        <tr>
          <td valign="top" width="25%" >
            <!-- Treeview Table Starts-->
            <table id="tblTreeview"  width="236px"    border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2">
              <tr bgcolor="#FFFFFF">
                <th><bean:message key="tbl.ColorNdSize" /></th>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td >
                  <div style="overflow:auto;height:402px;width:100%">
                    <table border="0" cellpadding="0" cellspacing="0" >
                    <tr>
                      <td valign="top" height="18px" style="background-image:url('images/ftv2folderclosed.gif')"></td>
                      <td>
                        <a style="margin-left:3px" class="tree" href="merchandiseRelayAction.do?hdnSearchOperation=merchand_list&merchandiseOrCategoryTblPk=<bean:write name="pricePromotionListForm" property="hdnMerchandiseCategoryTblPk" />" >
                          <b><bean:write name="pricePromotionListForm" property="hdnMerchandiseName"/><b>
                        </a>
                      </td>
                    </tr>
                      <logic:iterate id="merchandiseSizeColor" name="merchandiseSizeColors" indexId="colorIndex" >
                        <bean:define id="color1Value" name="merchandiseSizeColor" property="color1Value" type="java.lang.String" />
                        <bean:define id="color2Value" name="merchandiseSizeColor" property="color2Value" type="java.lang.String" />
                        <bean:define id="color1Name" name="merchandiseSizeColor" property="color1Name" type="java.lang.String" />
                        <bean:define id="color2Name" name="merchandiseSizeColor" property="color2Name" type="java.lang.String" />
                        <tr>
                          <%
                          if(colorIndex.intValue() ==(merchandiseSizeColors.size()-1)){
                          %>
                            <td valign="top"><img src="images/ftv2mlastnode.gif" style="cursor:pointer;cursor:hand;" onclick="return hideShowSizeLyr(this,'images/ftv2plastnode.gif','images/ftv2mlastnode.gif','<bean:write name="merchandiseSizeColor" property="merchandiseColorTblPk" />')" ></td>
                          <%
                          }else{
                          %>
                            <td valign="top"><img src="images/ftv2mnode.gif" style="cursor:pointer;cursor:hand;" onclick="return hideShowSizeLyr(this,'images/ftv2pnode.gif','images/ftv2mnode.gif','<bean:write name="merchandiseSizeColor" property="merchandiseColorTblPk" />')" ></td>                                             
                          <%
                          }
                          %>
                          <td valign="top" >                 
                            <table border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" style="float:left;">
                              <tr>
                                <td>
                                <%
                                  if(color2Name.trim().length()>0){
                                %>
                                    <div style="float:left;width:18px;height:18px;background-color:<%=color1Value%>;"></div>
                                    <div style="float:left;width:18px;height:18px;background-color:<%=color2Value%>;"></div>
                                <%
                                  }else{
                                %>
                                    <div style="float:left;width:36px;height:18px;background-color:<%=color1Value%>;"></div>
                                <%
                                  }
                                %>
                                </td>
                              </tr>
                            </table>
                            <%
                              if(color2Name.trim().length()>0){
                            %>
                              <div style="float:left;" >&nbsp;&nbsp;<%=color1Name%>/<%=color2Name%></div>
                            <%
                              }else{
                            %>
                                <div style="float:left;" >&nbsp;&nbsp;<%=color1Name%></div>
                            <%
                              }
                            %>
                          </td>
                        </tr>
                        <tr>
                        <%
                          if(colorIndex.intValue() ==(merchandiseSizeColors.size()-1)){
                          %>
                            <td valign="top"></td>
                          <%
                          }else{
                          %>
                            <td valign="top" style="background-image:url('images/ftv2vertline.gif')"></td>
                          <%
                          }
                          %>
                          <td valign="top">
                          <div id="<bean:write name="merchandiseSizeColor" property="merchandiseColorTblPk" />" name="<bean:write name="merchandiseSizeColor" property="merchandiseColorTblPk" />"  style="display:''" >
                            <table border="0" cellpadding="0" cellspacing="0" >  
                              <bean:define id="merchandiseSizes" name="merchandiseSizeColor" property="merchandiseSizes" type="java.util.ArrayList" /> 
                              <logic:iterate id="merchandiseSize" name="merchandiseSizeColor" property="merchandiseSizes" indexId="sizeIndex">  
                                  <tr>
                                    <%
                                    if(sizeIndex.intValue() ==(merchandiseSizes.size()-1)){
                                    %>
                                      <td valign="top"><img src="images/ftv2lastnode.gif" ></td>
                                    <%
                                    }else{
                                    %>
                                      <td valign="top"><img src="images/ftv2node.gif" ></td>                                             
                                    <%
                                    }
                                    %>
                                    <bean:define id="merchandiseSizeTblPk" name="merchandiseSize" property="merchandiseSizeTblPk"/>                                    
                                    <logic:equal name="pricePromotionListForm" property="hdnMerchandiseSizeTblPk" value="<%=(String)merchandiseSizeTblPk%>">
                                      <td><img src="images/ftv2folderopen.gif" ></td>
                                      <td>
                                        <div style="background-color:#80A0B2;color:#ffffff" ><bean:write name="merchandiseSize" property="sizeId"/> - <bean:write name="merchandiseSize" property="sizeDescription"/></div>
                                      </td>
                                    </logic:equal>
                                    <logic:notEqual name="pricePromotionListForm" property="hdnMerchandiseSizeTblPk" value="<%=(String)merchandiseSizeTblPk%>">
                                      <td><img src="images/ftv2folderclosed.gif" style="cursor:pointer;cursor:hand;" onclick="window.location.replace('pricePromotionRelayAction.do?hdnSearchOperation=list&hdnMerchandiseTblPk=<bean:write name="pricePromotionListForm" property="hdnMerchandiseTblPk"/>&hdnMerchandiseSizeTblPk=<bean:write name="merchandiseSize" property="merchandiseSizeTblPk"/>&hdnMerchandiseCategoryTblPk=<bean:write name="pricePromotionListForm" property="hdnMerchandiseCategoryTblPk" />')"></td>
                                      <td>
                                        <a class="tree" href="pricePromotionRelayAction.do?hdnSearchOperation=list&hdnMerchandiseTblPk=<bean:write name="pricePromotionListForm" property="hdnMerchandiseTblPk"/>&hdnMerchandiseSizeTblPk=<bean:write name="merchandiseSize" property="merchandiseSizeTblPk"/>&hdnMerchandiseCategoryTblPk=<bean:write name="pricePromotionListForm" property="hdnMerchandiseCategoryTblPk" />" ><bean:write name="merchandiseSize" property="sizeId"/> - <bean:write name="merchandiseSize" property="sizeDescription"/></a>
                                      </td>
                                    </logic:notEqual>
                                  <tr>
                              </logic:iterate>
                            </table>
                          </div>
                          </td>
                        <tr>
                      </logic:iterate>
                    </table>
                  </div>
                </td>
                </td>
              </tr>
            </table>
            <!-- Treeview Table Ends-->
          </td>
          <td width="2px"></td>
          <td>
            <table border="0" cellpadding="0" width="100%" cellspacing="0" >
              <tr>
                <td>
                  <!-- List Table Starts-->
                  <div style="overflow:auto;height:397px;">
                    <table width="100%"  border="0" align="center" cellspacing="1" bgcolor="#80A0B2">
                      <tr bgcolor="#FFFFFF">
                        <th width="2%">&nbsp;</th>
                        <th width="11%"><bean:message key="tbl.StartDate" /> </th>
                        <th width="11%"><bean:message key="tbl.EndDate" /> </th>
                        <th width="10%"><bean:message key="tbl.ThresholdQty" /></th>
                        <th width="12%"><bean:message key="tbl.Discount" /></th>
                        <th width="11%"><bean:message key="tbl.Exclusive" /></th>
                        <th width="10%"><bean:message key="tbl.Bypass" /></th>
                        <th width="10%"><bean:message key="tbl.Status" /></th>
                      </tr>
                      <tr bgcolor="#FFFFFF">
                        <%if(pricePromotions.size()>0){ %>
                          <logic:iterate id="pricePromotion" name="pricePromotions">  
                            <bean:define id="PricePromotionTblPk" name="pricePromotion" property="pricePromotionTblPk" />
                            <tr bgcolor="#FFFFFF">
                              <td><html:radio property="radSearchSelect" onclick="return radioOnlick(this);" value="<%=(String)PricePromotionTblPk%>"></html:radio></td>
                              <td><bean:write name="pricePromotion" property="startDate" /></td>
                              <td><bean:write name="pricePromotion" property="endDate" /></td>
                              <td><bean:write name="pricePromotion" property="thresholdQty" /></td>
                              <td><bean:write name="pricePromotion" property="discount" /></td>
                              <td><bean:write name="pricePromotion" property="exclusive" /></td>
                              <td><bean:write name="pricePromotion" property="byPass" /></td>
                              <td><bean:write name="pricePromotion" property="isActiveDisplay" /></td>
                              <bean:define id="intStatus" name="pricePromotion" property="isActive" />
                              <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
                            </tr>
                          </logic:iterate>
                        <%}%>
                    </table>
                    <% if(pricePromotions.size()==0) {%>
                      <div style="width:96%;height:15px;top:175px;" class="noItemFound" >
                        <bean:message    key="alert.no_item_found" />
                      </div> 
                    <%}%>
                  </div>
                  <!-- List Table Ends-->      
                </td>
              </tr>
              <tr>
                <td height="3px"></td>
              </tr>
              <tr>
                <td>
                  <!-- Status Bar Table Starts-->
                  <table id="tblStatusBar" align="center" width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
                    <tr bgcolor="#FFFFFF">
                      <td width="30px" ><div class="imgStatusMsg"></td>
                      <td>&nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
                      <td width="200px" ><script>navBar.render();</script></td>
                    </tr>
                  </table>
                  <!-- Status Bar Table Ends-->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- Size Color Tree and List Table Ends-->
    </td>
  <tr>
  <tr><td height="5px"></td></tr>
</table>
<!-- Main Table Ends -->
</html:form>
</BODY>
</HTML>
