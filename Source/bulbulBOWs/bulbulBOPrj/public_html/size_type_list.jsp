<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="sizeTypeListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="sizeTypeListForm" property="hdnSearchPageNo" />        
<bean:define id="sizeTypes" name="sizeTypes" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />      
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.SizeTypeList" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <script language="JavaScript" type="text/JavaScript" src="general.js"></script>
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
    //-->
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
        thisForm.txtSearchSizeTypeId.value='';
        thisForm.txtSearchSizeTypeDesc.value='';
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
<html:form action="/sizeTypeRelayAction.do">
  <html:hidden property="hdnSearchOperation" />
  <html:hidden property="hdnSearchCurrentStatus" />
  <html:hidden property="hdnSearchPageNo" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.SizeType" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<!-- Main Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td  valign="top" >
      <!-- Search and Button Table Starts -->
      <table id="tblSearchNdButtons" width="950px"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="86%" height="30px" valign="bottom">
            <!-- Search Table Starts -->
            <table id="tblSearch" width="100%"  border="0" class="bdrColor_336666" id="blue" style="display:none ">
              <tr>
                <td width="22%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <bean:message key="txt.SizeTypeId" />:
                      </td>
                      <td>
                        <html:text property="txtSearchSizeTypeId" styleClass="bdrColor_336666" />
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="28%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <bean:message key="txa.SizeTypeDesc" />:
                      </td>
                      <td>
                        <html:text property="txtSearchSizeTypeDesc" styleClass="bdrColor_336666" />
                      </td>
                    </tr>
                  </table>
                </td>
                
                <td width="25%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <bean:message key="tbl.Status" />:
                      </td>
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
                </td>
                <td width="15%" >&nbsp;</td>
                <td width="10%" align="right">
                  <a href="#"><img src="images/icon_search.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onClick="return btnclick('search');"></a>
                  <a href="#"><img src="images/icon_close.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onClick="btnSearchCloseOnclick();" ></a>
                </td>
              </tr>
            </table>			
            <!-- Search Table Ends -->
            <!-- Button Table Starts -->
            <table id="tblButtons" width="100%"   border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td>
                  <html:button property="btnSearch" value="Search" styleClass="buttons" tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                  <logic:iterate id="module" name="userRights">          
                    <logic:equal name="module" property="name" value="Size Type">
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
        <tr><td height="5px" ></td></tr>
      </table>
      <!-- Search and Button Table Ends -->   
    </td>
  </tr>
  <tr>
    <td align="center">  
      <!-- List Table Stars-->
      <table border="0" width="98%" cellpadding="0" cellspacing="0" >
        <tr>
          <td>
            <!-- List Table Starts-->
            <div style="overflow:auto;height:416px;">
              <table width="100%"  border="0" cellspacing="1" bgcolor="#80A0B2">
                <tr bgcolor="#FFFFFF">
                  <th width="3%">&nbsp;</th>
                  <th width="16%"><bean:message key="tbl.SizeTypeId" /> </th>
                  <th width="61%"><bean:message key="tbl.SizeTypeDesc" /></th>
                  <th width="20%"><bean:message key="tbl.Status" /></th>
                </tr>
                <%if(sizeTypes.size()>0){ %>
                  <logic:iterate id="sizeType" name="sizeTypes">  
                    <bean:define id="SizeTypeTblPk" name="sizeType" property="sizeTypeTblPk" />
                      <tr bgcolor="#FFFFFF">
                        <td><html:radio property="radSearchSelect" onclick="return radioOnlick(this);" value="<%=(String)SizeTypeTblPk%>"></html:radio></td>
                        <td>  
                          <bean:write name="sizeType" property="sizeTypeId" />
                        </td>          
                        <td><bean:write name="sizeType" property="sizeTypeDescription" /></td>
                        <td><bean:write name="sizeType" property="isActiveDisplay" /></td>
                        <bean:define id="intStatus" name="sizeType" property="isActive" />
                        <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
                      </tr>
                  </logic:iterate>
                <%}%>
              </table>
              <% if(sizeTypes.size()==0) {%>
                <div align="center" style="width:99%;height:15px;top:175px;" class="noItemFound" >
                  <bean:message    key="alert.no_item_found" />
                </div> 
              <%}%>
             </div>
            <!-- List Table Ends-->
          </td>    
        </tr>    
        <tr><td height="3px" ></td></tr>    
        <tr>
          <td>
            <!-- Status Bar Table Starts-->
            <table id="tblStatusBar" align="center" width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
            <tr bgcolor="#FFFFFF">
            <td width="30px" ><div class="imgStatusMsg"></td>
            <td>&nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
            <td width="200px" ><script>navBar.render();</script></td>
            </tr>
            </table>
            <!-- Status Bar Table Ends-->
          <td>
        </tr>
        <tr><td height="6px" ></td></tr>    
      </table> 
      <!-- List Table Ends-->      
    </td>
  </tr>  
</table>
<!-- Main Table Ends -->
</html:form>
</BODY>
</HTML>
