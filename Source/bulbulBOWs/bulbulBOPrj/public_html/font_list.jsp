<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="fontListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="fontListForm" property="hdnSearchPageNo" />        
<bean:define id="fonts" name="fonts" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />
<bean:define id="currentCategoryPk" name="fontListForm" property="hdnSearchFontOrCategoryTblPk" />
<HTML>
  <HEAD>  
    <TITLE><bean:message key="title.FontList" /></TITLE>
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
        var isFont=false;
        if (operation!='page' && operation!='search' && operation!='font_add' && operation!='cat_add'){
          index=getRadioSelectedIndex(thisForm.radSearchSelect);
          if (index>-2){
            if (index==-1){
              isFont=(thisForm.hdnSearchFontOrCategoryType.value=='Font');
            }else{
              isFont=(thisForm.hdnSearchFontOrCategoryType[index].value=='Font');
            }
            if (isFont){
              if (operation=='view') {
                operation='font_view';
              }else if(operation=='edit') {
                operation='font_edit';
              }else if(operation=='delete') {
                operation='font_delete';
              }else if(operation=='act_deact') {
                operation='font_act_deact';              
              }else if(operation=='move') {
                operation='font_moveB4';
              }
            }else{
              if (operation=='view') {
                operation='cat_view';
              }else if(operation=='edit') {
                operation='cat_edit';
              }else if(operation=='delete') {
                operation='cat_delete';
              }else if(operation=='act_deact') {
                operation='cat_act_deact';              
              }else if(operation=='move') {
                operation='cat_moveB4';              
              }
            }
            thisForm.hdnSearchOperation.value=operation;
            if( operation=='font_delete' || operation=='cat_delete'){
              if (confirm('<bean:message   key="alert.delete.confirm" />')){
                thisForm.submit();
              }else{
                return false;
              }
            }
            if( operation=='font_moveB4' || operation=='cat_moveB4'){
              openWindow('','fontCatList',375,425,0,0,true,'')
              thisForm.target='fontCatList';
            }
            thisForm.submit();
          }else{
            alert('<bean:message   key="errors.selection.required" />');
          }
        }else{
          if(operation=='font_add'){
            if(thisForm.hdnSearchFontOrCategoryTblPk.value=="-1"){
              alert('<bean:message key="alert.Font.Category.Select.Required" />');
              return false;
            }
          }
          thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
          thisForm.submit();
        } 

      }

      function radioSearchOnclick(me){
        var thisForm=me.form;
        var index = getRadioSelectedIndex(thisForm.radSearchFontOrCategory);
        if(index==0){
          thisForm.txtSearchFontCategory.readOnly=false;
          thisForm.txtSearchFontName.readOnly=true;
          thisForm.txtSearchFontName.value='';
          thisForm.txtSearchFontCategory.focus();
        }else if(index==1){
          thisForm.txtSearchFontName.readOnly=false;
          thisForm.txtSearchFontCategory.readOnly=true;
          thisForm.txtSearchFontCategory.value='';
          thisForm.txtSearchFontName.focus();
 
        }

      }

      var theAddButtonTimer;
      function btnAdd_onclick() {
        MM_showHideLayers('lyrAddButton','','show')
      }

      function btnAdd_onmouseover() {
        clearTimeout(theAddButtonTimer);
      }

      function btnAdd_onmouseout() {
        theAddButtonTimer=setTimeout("MM_showHideLayers('lyrAddButton','','hide')",500);
      }
      
      function btnSearchCloseOnclick(){
        var thisForm=document.forms[0];
        thisForm.radSearchFontOrCategory[0].checked=false;
        thisForm.radSearchFontOrCategory[1].checked=false;
        thisForm.txtSearchFontCategory.value='';
        thisForm.txtSearchFontName.value='';
        thisForm.radSearchStatus[2].checked=true;
        MM_showHideLayers('tblSearch','','hide','tblButtons','','show');
      }
      function window_onload(){
        var thisForm=document.forms[0];
        if(thisForm.hdnSearchOperation.value=='search'){
          if(thisForm.radSearchFontOrCategory[0].checked){
           radioSearchOnclick(thisForm.radSearchFontOrCategory[0]);
           // thisForm.txtSearchFontCategory.focus();
          }
          if(thisForm.radSearchFontOrCategory[1].checked){
            radioSearchOnclick(thisForm.radSearchFontOrCategory[1]);
            //thisForm.txtSearchFontName.focus();
          }
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
<html:form action="/fontRelayAction.do" > 
<html:hidden property="hdnSearchOperation" />
<html:hidden property="hdnSearchCurrentStatus" />
<html:hidden property="hdnSearchPageNo" /> 
<html:hidden property="hdnSearchFontOrCategoryTblPk" />
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Font" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<!--Main Table Starts -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC  bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td valign="top" >
      <!--Search and Button Table Starts-->
      <table id="tblSearchNdButtons" width="950px"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="86%" height="30px" valign="bottom">  
            <!--Search Table Starts Here-->
            <table id="tblSearch" width="100%"  border="0" class="bdrColor_336666"  style="display:none">
              <tr>
                <td width="30%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <html:radio property="radSearchFontOrCategory" onclick="return radioSearchOnclick(this);" value="categorySelect" ></html:radio>
                      </td>
                      <td>
                        <bean:message key="txt.FontCategory" />:
                      </td>
                      <td>
                        <html:text property="txtSearchFontCategory" styleClass="bdrColor_336666" readonly="true"/>
                      </td>
                    </tr>
                  </table>                  
                </td>
                <td width="30%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <html:radio property="radSearchFontOrCategory" onclick="return radioSearchOnclick(this);" value="fontSelect" ></html:radio>
                      </td>
                      <td>
                        <bean:message key="txt.FontName" />:
                      </td>
                      <td>
                        <html:text property="txtSearchFontName" styleClass="bdrColor_336666" readonly="true"/>
                      </td>
                    </tr>
                  </table>                  
                </td>
                <td width="30%">
                  <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <bean:message key="tbl.Status" />:
                      </td>
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
                    </tr>
                  </table>                  
                </td>
                <td width="10%" align="right">
                  <a href="#"><img src="images/icon_search.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onClick="return btnclick('search');"></a>
                  <a href="#"><img src="images/icon_close.gif" alt="" width="18px" height="18px" border="0" title="Search Now" onclick="btnSearchCloseOnclick();" ></a>
                </td>
              </tr>
            </table>
            <!--Search Table Ends Here-->
            <!--Button Table Starts Here-->
            <table id="tblButtons" width="98%" border="0"  cellpadding="0" cellspacing="0">
              <tr>
                <td>
                  <table border="0" cellpadding="0" cellspacing="2">
                    <tr>
                      <td>
                        <html:button property="btnSearch" value="Search " styleClass="buttons" tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                      </td>
                      <logic:iterate id="module" name="userRights">          
                        <logic:equal name="module" property="name" value="Font">
                          <logic:iterate id="permission" name="module" property="permissions">  

                            <!--View Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="View">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnView" styleClass="buttons"  onclick="btnclick('view')" ><bean:message  key="btn.View" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnView" styleClass="buttonDisabled" tabindex="9" disabled="true"  ><bean:message  key="btn.View" /></html:button>                                            
                                </td>
                              </logic:equal>
                            </logic:equal>
                          
                            <!--Add Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Add">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnAdd" styleClass="buttons" onclick="return btnAdd_onclick();" onmouseout="return btnAdd_onmouseout();" onmouseover="return btnAdd_onmouseover();">
                                    <bean:message  key="btn.Add" />
                                  </html:button>
                                  <div>
                                    <div id="lyrAddButton" style="display:none;position:absolute; ">
                                      <table border="0" cellpadding="0" cellspacing="1" class="bgColor_DFE7EC  bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333" >
                                        <tr>
                                          <td>
                                            <html:button property="btnAddCategory" style="width:65px;" styleClass="buttons" onclick="btnclick('cat_add')" onmouseout="return btnAdd_onmouseout();" onmouseover="return btnAdd_onmouseover();">
                                              <bean:message  key="btn.Add.Category" />
                                            </html:button>
                                          </td>		
                                        </tr>		
                                        <tr>			
                                          <td>
                                            <html:button property="btnAddFont" style="width:65px;" styleClass="buttons" onclick="btnclick('font_add')" onmouseout="return btnAdd_onmouseout();" onmouseover="return btnAdd_onmouseover();" >
                                              <bean:message  key="btn.Add.Font" />
                                            </html:button>                  
                                          </td>		
                                        </tr>
                                      </table>
                                    </div>
                                  </div>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnAdd" styleClass="buttonDisabled" disabled="true"><bean:message  key="btn.Add" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>

                            <!--Edit Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Edit">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnEdit" styleClass="buttons" onclick="btnclick('edit')" ><bean:message  key="btn.Edit" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnEdit" styleClass="buttonDisabled" tabindex="11" disabled="true" ><bean:message  key="btn.Edit" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>

                            <!--Delete Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Delete">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnDelete" styleClass="buttons" onclick="btnclick('delete')"><bean:message  key="btn.Delete" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnDelete" styleClass="buttonDisabled" tabindex="12" disabled="true"><bean:message  key="btn.Delete" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>
                          
                            <!--Activate/Deactivate Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Activate/Deactivate">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnActDeact" styleClass="buttons" onclick="btnclick('act_deact')" ><bean:message  key="btn.Activate" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnActDeact" styleClass="buttonDisabled" tabindex="13" disabled="true"><bean:message  key="btn.Activate" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>

                            <!--Move Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Move">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnMove" styleClass="buttons"  onclick="btnclick('move')" ><bean:message  key="btn.Move" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnMove" styleClass="buttonDisabled" tabindex="13" disabled="true"  ><bean:message  key="btn.Move" /></html:button>                                            
                                </td>
                              </logic:equal>
                            </logic:equal>
                            
                          </logic:iterate>                          
                        </logic:equal>
                      </logic:iterate>  
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!--Button Table Ends Here-->
          </td>
        </tr>
        <tr><td height="5px"></td></tr>
      </table>
      <!--Search and Button Table Ends-->
    </td>
  </tr>
  <tr>
    <td align="center">  
      <!-- Tree View and List Table Stars-->
      <table id="tblTreeNdList" border="0" width="98%" height="455px" cellpadding="0" cellspacing="0"  >
        <tr>
          <td valign="top" width="25%" >
            <!-- Treeview Table Starts-->
            <table id="tblTreeview"  width="100%"    border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2">
              <tr bgcolor="#FFFFFF">
                <th><bean:message key="tree.Category" /></th>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td >
                  <iframe src="fontRelayAction.do?hdnSearchOperation=font_tree_view&currentCategoryPk=<%=currentCategoryPk%>" scrolling="yes" height="422px" width="100%"  frameborder="0"></iframe>
                </td>
              </tr>
            </table>
            <!-- Treeview Table Ends-->
          </td>
          <td width="2px"></td>
          <td valign="top">
            <table border="0" width="100%" cellpadding="0" cellspacing="0" >
              <tr>
                <td>
                  <!-- List Table Starts-->
                  <div style="overflow:auto;height:416px;">
                    <table width="100%"  border="0" cellspacing="1" bgcolor="#80A0B2">
                      <tr bgcolor="#FFFFFF">
                        <th width="2%">
                        <% if(!currentCategoryPk.equals("-1")){ %>
                          <a style="margin-left:5px;" href="fontRelayAction.do?hdnSearchOperation=font_up&fontOrCategoryTblPk=<%=currentCategoryPk%>" class="imgGoUpDataTbl"  title="<bean:message key="tooltips.Up" />" ></a>
                        <%}else{%>
                          <div  style="margin-left:5px;" class="imgGoUpDataTblDisable"></div>
                        <%}%>
                        </th>
                        <th width="29%"><bean:message key="tbl.Name" /></th>
                        <th width="34%"><bean:message key="tbl.Description" /></th>
                        <th width="15%"><bean:message key="tbl.Type" /></th>
                        <th width="20%"><bean:message key="tbl.Status" /></th>
                      </tr>
                      <%if(fonts.size()>0){ %>
                        <logic:iterate id="font" name="fonts">  
                          <bean:define id="FontOrCategoryTblPk" name="font" property="fontOrCategoryTblPk" />
                          <tr bgcolor="#FFFFFF">
                            <td><html:radio property="radSearchSelect" onclick="return radioOnlick(this);" value="<%=(String)FontOrCategoryTblPk%>"></html:radio></td>
                            <logic:equal name="font" property="fontOrCategoryType" value="Category">
                              <td>
                                <a class="tree" onclick="document.forms[0].hdnSearchPageNo.value='1'" style="margin-left:3px" href="fontRelayAction.do?hdnSearchOperation=font_list&fontOrCategoryTblPk=<bean:write name="font" property="fontOrCategoryTblPk" />">
                                  <b><bean:write name="font" property="fontOrCategoryName" /></b>
                                </a>
                              </td>
                            </logic:equal>
                            <logic:equal name="font" property="fontOrCategoryType" value="Font">
                              <td><bean:write name="font" property="fontOrCategoryName" /></td>
                            </logic:equal>
                            <td><bean:write name="font" property="fontOrCategoryDesc" /></td>
                            <td><bean:write name="font" property="fontOrCategoryType" /></td>
                            <td><bean:write name="font" property="isActiveDisplay" /></td>
                            <bean:define id="intStatus" name="font" property="isActive" />
                            <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
                            <bean:define id="FontOrCategoryType" name="font" property="fontOrCategoryType" />
                            <html:hidden property="hdnSearchFontOrCategoryType" value="<%=(String)FontOrCategoryType%>" />
                          </tr>
                        </logic:iterate>
                      <%}%>
                    </table>
                    <% if(fonts.size()==0) {%>
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
            </table> 
          </td>
        </tr>
      </table>
      <!-- Tree View and List Table Ends-->
    </td>
  </tr>
</table>
<!--Main Table Ends-->
</html:form>
</BODY>
</HTML>
