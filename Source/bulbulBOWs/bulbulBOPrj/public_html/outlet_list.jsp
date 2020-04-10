<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="outletListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="outletListForm" property="hdnSearchPageNo" />        
<bean:define id="outlets" name="outlets" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />        
<HTML>
  <HEAD>  
    <TITLE><bean:message  key="title.OutletList" /></TITLE>
    <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
    <script language="JavaScript" type="text/JavaScript" src="navigationbar.js" ></script>
    <script>
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
          thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
          thisForm.submit();
        } 

      }
      
      function btnSearchCloseOnclick(){
        var thisForm=document.forms[0];
        thisForm.txtSearchOutletId.value='';
        thisForm.txtSearchOutletName.value='';
        thisForm.txtSearchAddress.value='';
        thisForm.txtSearchCity.value='';
        thisForm.txtSearchPincode.value='';
        thisForm.cboSearchState.value='';
        thisForm.cboSearchCountry.value='';
        thisForm.radSearchStatus[2].checked=true;
        MM_showHideLayers('tblSearch','','hide','tblButtons','','show');
      }
      function window_onload(){
        var thisForm=document.forms[0];
        if(thisForm.hdnSearchOperation.value=='search'){
          
          MM_showHideLayers('tblSearch','','show','tblButtons','','hide');
        }
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
<BODY onload="window_onload();"> 
<html:form action="/outletRelayAction.do">
<html:hidden property="hdnSearchOperation" />
<html:hidden property="hdnSearchCurrentStatus" />
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
<!-- Main Table Starts -->
<table id="tblMain" width="970px"  border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td>
      <!-- Search and Button Table Starts-->
      <table id="tblSearchNdButtons" width="955px"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="87%" height="55px" valign="bottom">
            <!-- Search Table Starts -->
            <table width="100%"  border="0" class="bdrColor_336666" id="tblSearch" style="display:none">
              <tr>
                <td width="8%">
                  <div align="right"><bean:message  key="txt.OutletId" />: </div>
                </td>
                <td width="11%">
                  <html:text property="txtSearchOutletId"  styleClass="bdrColor_336666" style="width:100px" tabindex="2"/>
                </td>
                <td width="10%">
                  <div align="right"><bean:message  key="txt.OutletName" />: </div>
                </td>
                <td width="11%">
                  <html:text property="txtSearchOutletName" styleClass="bdrColor_336666" style="width:100px" tabindex="3" />
                </td>
                <td width="6%">
                  <div align="right"><bean:message  key="txt.Address" />: </div>
                </td>
                <td width="11%">
                  <html:text property="txtSearchAddress"  styleClass="bdrColor_336666" style="width:100px" tabindex="4" />
                </td>
                <td width="4%">
                  <div align="right"><bean:message  key="txt.City" />: </div>
                </td>
                <td width="11%">
                  <html:text  property="txtSearchCity" styleClass="bdrColor_336666" style="width:100px" tabindex="5" />
                </td>
                <td width="6%">
                  <div align="right"><bean:message  key="txt.Pincode" />:</div>
                </td>
                <td width="11%" >
                  <html:text property="txtSearchPincode" styleClass="bdrColor_336666" style="width:100px" tabindex="8" /></div>
                </td>
                <td width="8%"  align="right">
                  <html:link href="#" tabindex="12" ><img src="images/icon_search.gif" alt="" width="18px" height="18px" border="0" title="Search Now" tabindex="12" onclick="return btnclick('search');"></html:link>
                  <html:link href="#" tabindex="12" ><img src="images/icon_close.gif" alt="" width="18px" height="18px" border="0" title="Search Now" tabindex="12" onclick="btnSearchCloseOnclick();" ></html:link>
                </td>
              </tr>
              <tr>
                 <td >
                  <div align="right"><bean:message  key="cbo.State"/>:</div>
                </td>
                <td>
                  <html:text property="cboSearchState" styleClass="bdrColor_336666" style="width:100px" tabindex="6" />
                </td>
                <td>
                  <div align="right"><bean:message  key="cbo.Country" />:</div>
                </td>
                <td>
                  <html:text property="cboSearchCountry" styleClass="bdrColor_336666" style="width:100px" tabindex="7" />
                </td>
                <td  valign="top">
                  <div align="right"><bean:message  key="tbl.Status" />:</div>
                </td>
                <td valign="top" colspan="6">
                  <!-- Radio Table Stars -->
                  <table cellpadding="0" cellspacing="0" >
                    <tr>
                      <td>
                        <html:radio property="radSearchStatus" value="1" tabindex="9" ></html:radio>
                      </td>  
                      <td>
                        <bean:message  key="rad.Status.Active" /> 
                      </td>
                      <td>
                        <html:radio property="radSearchStatus" value="0" tabindex="10" ></html:radio>
                      </td>
                      <td>
                        <bean:message  key="rad.Status.InActive" /> 
                      </td>
                      <td>
                        <html:radio property="radSearchStatus" value="2" tabindex="11" ></html:radio>
                      </td>
                      <td>
                        <bean:message  key="rad.Status.Both" />  
                      </td>
                    </tr>
                  </table>
                  <!-- Radio Table Stars -->
                </td>
              </tr>
            </table>	
            <!-- Search Table Ends -->
            <!-- Button Table Starts -->
            <table id="tblButtons" width="100%"   border="0" align="center" >
              <tr>
                <td>
                  <html:button property="btnSearch" value="Search" styleClass="buttons" tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                  <logic:iterate id="module" name="userRights">          
                    <logic:equal name="module" property="name" value="Outlet">
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
        <tr><td height="5px"></td></tr>
      </table>
      <!-- Search and Button Table Ends -->
    </td>
  </tr>
  <tr>
    <td align="center">
      <!-- List Table Starts-->
      <div style="overflow:auto;height:397px;">
        <table id="tblList"  width="98%"  border="0" cellspacing="1" bgcolor="#80A0B2">
          <tr bgcolor="#FFFFFF">
            <th width="3%">&nbsp;</th>
            <th width="14%"><bean:message  key="tbl.OutletId" /> </th>
            <th width="27%"><bean:message  key="tbl.OutletName" /></th>
            <th width="26%"><bean:message  key="tbl.Address" /></th>
            <th width="13%"><bean:message  key="tbl.City" /></th>
            <th width="17%"><bean:message  key="tbl.Status" /></th>
          </tr>
          <%if(outlets.size()>0){ %>
            <logic:iterate id="outlet" name="outlets">  
              <bean:define id="OutletTblPk" name="outlet" property="outletTblPk" />
              <tr bgcolor="#FFFFFF">
                <td><html:radio property="radSearchSelect" tabindex="18" onclick="return radioOnlick(this);" value="<%=(String)OutletTblPk %>"></html:radio></td>
                <td><bean:write name="outlet" property="outletId" /></td>
                <td><bean:write name="outlet" property="outletName" /></td>
                <td><bean:write name="outlet" property="address1" />&nbsp;&nbsp;&nbsp;<bean:write name="outlet" property="address2" />&nbsp;&nbsp;&nbsp;<bean:write name="outlet" property="address3" /></td>
                <td><bean:write name="outlet" property="city" /></td>
                <td><bean:write name="outlet" property="isActiveDisplay" />
                <bean:define id="intStatus" name="outlet" property="isActive" />
                <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
                </td>
              </tr>
            </logic:iterate>
          <%}%>
        </table>
        <% if(outlets.size()==0) {%>
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
  <tr><td height="5px"></td></tr>
</table>
<!-- Main Table Ends-->
</html:form>
</BODY>
</HTML>
