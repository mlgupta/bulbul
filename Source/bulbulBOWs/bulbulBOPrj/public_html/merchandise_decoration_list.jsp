<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="merchandiseDecorationListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="merchandiseDecorationListForm" property="hdnSearchPageNo" />        
<bean:define id="decorations" name="decorations" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />        
<HTML>
<HEAD>  
<TITLE><bean:message key="title.MerchandiseDecorationList" /></TITLE>
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
    thisForm.txtSearchDecorationName.value='';
    thisForm.txaSearchDecorationDescription.value='';
    thisForm.radSearchStatus[2].checked=true;
    MM_showHideLayers('tblSearch','','hide','tblButtons','','show');
  }
  function window_onload(){
    var thisForm=document.forms[0];
    if(thisForm.hdnSearchOperation.value=='search'){
      
      MM_showHideLayers('tblSearch','','show','tblButtons','','hide');
    }
  }
 
//-->
</script> 
</HEAD>
<BODY onload="window_onload();">   
<html:form action="/merchandiseDecorationRelayAction.do">
<html:hidden property="hdnSearchOperation" />
<html:hidden property="hdnSearchCurrentStatus" />
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
<!--Main Table Starts Here -->
<table id="tblMain" width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td >
      <!--Search and Button Table Starts-->
      <table id="tblSearchNdButtons" width="950px"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="87%" height="30px" valign="bottom" >
            <!--Search Table Starts-->
            <table width="100%"  border="0" class="bdrColor_336666" id="tblSearch" style="display:none;">
              <tr>
               <td> <div align="right"><bean:message key="txt.DecorationName" />:</div></td>
               <td >
                 <html:text property="txtSearchDecorationName" styleClass="bdrColor_336666" tabindex="2" />
               </td>
               <td> <div align="right"><bean:message key="txa.DecorationDescription" />:</div></td>
               <td >  
                 <html:text property="txaSearchDecorationDescription"  styleClass="bdrColor_336666" tabindex="3" />
               </td>
               <td><div align="right"><bean:message key="tbl.Status" />:</div></td>
               <td>
               <!--Radio Table Starts-->
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                       <html:radio property="radSearchStatus" value="1" tabindex="5" ></html:radio>
                       </td>
                       <td><bean:message key="rad.Status.Active" /></td>
                       <td>
                       <html:radio property="radSearchStatus" value="0" tabindex="6" ></html:radio>
                       </td>
                       <td><bean:message key="rad.Status.InActive" /></td>
                       <td>
                       <html:radio property="radSearchStatus" value="2" tabindex="7" ></html:radio>
                       </td>
                       <td><bean:message key="rad.Status.Both" /></td>
                      </tr>
                    </table>
                  <!--Radio Table Ends-->
                </td>
                <td align="right">
                  <html:link href="#" tabindex="7"><img src="images/icon_search.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onclick="return btnclick('search');"></html:link>
                  <html:link href="#" tabindex="7"><img src="images/icon_close.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onclick="btnSearchCloseOnclick();"></html:link>
                </td>
              </tr>
            </table>			
            <!--Search Table Ends-->
            <!--Button Table Starts-->
            <table id="tblButtons" width="100%"   border="0" align="center"  cellpadding="0" cellspacing="0">
              <tr>
                <td>
                    <html:button property="btnSearch" value="Search" styleClass="buttons"  tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                    <logic:iterate id="module" name="userRights">          
                      <logic:equal name="module" property="name" value="Decoration">
                        <logic:iterate id="permission" name="module" property="permissions">  

                          <!--View Button Enable Disable-->
                          <logic:equal name="permission" property="name" value="View">
                            <logic:equal name="permission" property="value" value="1">
                              <html:button property="btnView" styleClass="buttons"  tabindex="8" onclick="btnclick('view')" ><bean:message  key="btn.View" /></html:button>
                            </logic:equal>
                            <logic:equal name="permission" property="value" value="0">
                              <html:button property="btnView" styleClass="buttonDisabled" tabindex="8" disabled="true"  ><bean:message  key="btn.View" /></html:button>                                            
                            </logic:equal>
                          </logic:equal>
                          
                          <!--Add Button Enable Disable-->
                          <logic:equal name="permission" property="name" value="Add">
                            <logic:equal name="permission" property="value" value="1">
                              <html:button property="btnAdd" styleClass="buttons" tabindex="9" onclick="btnclick('add')"><bean:message  key="btn.Add" /></html:button>
                            </logic:equal>
                            <logic:equal name="permission" property="value" value="0">
                              <html:button property="btnAdd" styleClass="buttonDisabled" tabindex="9" disabled="true" ><bean:message  key="btn.Add" /></html:button>                              
                            </logic:equal>
                          </logic:equal>

                          <!--Edit Button Enable Disable-->
                          <logic:equal name="permission" property="name" value="Edit">
                            <logic:equal name="permission" property="value" value="1">
                              <html:button property="btnEdit" styleClass="buttons" tabindex="10" onclick="btnclick('edit')" ><bean:message  key="btn.Edit" /></html:button>
                            </logic:equal>
                            <logic:equal name="permission" property="value" value="0">
                              <html:button property="btnEdit" styleClass="buttonDisabled" tabindex="10" disabled="true" ><bean:message  key="btn.Edit" /></html:button>
                            </logic:equal>
                          </logic:equal>

                          <!--Delete Button Enable Disable-->
                          <logic:equal name="permission" property="name" value="Delete">
                            <logic:equal name="permission" property="value" value="1">
                              <html:button property="btnDelete" styleClass="buttons" tabindex="11" onclick="btnclick('delete')"><bean:message  key="btn.Delete" /></html:button>
                            </logic:equal>
                            <logic:equal name="permission" property="value" value="0">
                              <html:button property="btnDelete" styleClass="buttonDisabled" tabindex="11" disabled="true"><bean:message  key="btn.Delete" /></html:button>
                            </logic:equal>
                          </logic:equal>
                          
                          <!--Activate/Deactivate Button Enable Disable-->
                          <logic:equal name="permission" property="name" value="Activate/Deactivate">
                            <logic:equal name="permission" property="value" value="1">
                              <html:button property="btnActDeact" styleClass="buttons" tabindex="12" onclick="btnclick('act_deact')" ><bean:message  key="btn.Activate" /></html:button>
                            </logic:equal>
                            <logic:equal name="permission" property="value" value="0">
                              <html:button property="btnActDeact" styleClass="buttonDisabled" tabindex="12" disabled="true"><bean:message  key="btn.Activate" /></html:button>
                            </logic:equal>
                          </logic:equal>
                          
                        </logic:iterate>                          
                      </logic:equal>
                    </logic:iterate>    
                </td>
              </tr>
            </table>
            <!--Button Table Ends-->
          </td>
        </tr>
        <tr><td height="5px"></td></tr>
      </table>
      <!--Search and Button Table Ends-->
    </td>
  </tr>
  <tr>
    <td align="center">
      <!--List Table Starts-->
      <div style="overflow:auto;height:416px;">
        <table id="tblList"  width="98%"  border="0" cellspacing="1" bgcolor="#80A0B2">
          <tr bgcolor="#FFFFFF">
            <th width="3%">&nbsp;</th>
            <th width="30%"><bean:message key="tbl.DecorationName" /></th>
            <th width="47%"><bean:message key="tbl.DecorationDescription" /></th>
            <th width="20%"><bean:message key="tbl.Status" /></th>
          </tr>
          <%if(decorations.size()>0){ %>
            <logic:iterate id="decoration" name="decorations">  
              <bean:define id="merchandiseDecorationTblPk" name="decoration" property="merchandiseDecorationTblPk" />
              <tr bgcolor="#FFFFFF">
                <td><html:radio property="radSearchSelect" tabindex="13" onclick="return radioOnlick(this);" value="<%=(String)merchandiseDecorationTblPk %>"></html:radio></td>
                <td><bean:write name="decoration" property="decorationName" /></td>
                <td><bean:write name="decoration" property="decorationDescription" /></td>
                <td><bean:write name="decoration" property="isActiveDisplay" />
                <bean:define id="intStatus" name="decoration" property="isActive" />
                <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
              </tr>
              </logic:iterate>
          <%}%>
        </table>
        <% if(decorations.size()==0) {%>
          <div style="width:96%;height:15px;top:175px;" class="noItemFound" >
            <bean:message    key="alert.no_item_found" />
          </div> 
        <%}%>
      </div>          
      <!--List Table Ends-->
    </td>
  </tr>
  <tr><td height="3px"></td></tr>
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
<!--Main Table Ends Here-->      
</html:form>
</BODY>
</HTML>
