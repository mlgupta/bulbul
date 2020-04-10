<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="merchandiseListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="merchandiseListForm" property="hdnSearchPageNo" />        
<bean:define id="merchandises" name="merchandises" type="java.util.ArrayList" /> 
<bean:define id="userRights" name="userProfile" property="userRights" />      
<bean:define id="currentCategoryPk" name="merchandiseListForm" property="hdnSearchMerchandiseOrCategoryTblPk" />
<HTML>
<HEAD>  
  <TITLE><bean:message key="title.MerchandiseList" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <script language="JavaScript" type="text/JavaScript" src="general.js"></script>
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

      thisForm.hdnMerchandiseTblPk.value=me.value;
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
      var isMerchandise=false;
      if (operation!='page' && operation!='search' && operation!='add' ){
        index=getRadioSelectedIndex(thisForm.radSearchSelect);
        if (index>-2){
          if (index==-1){
            isMerchandise=(thisForm.hdnSearchMerchandiseOrCategoryType.value=='Merchandise');
          }else{
            isMerchandise=(thisForm.hdnSearchMerchandiseOrCategoryType[index].value=='Merchandise');
          }
          if (isMerchandise){
            if (operation=='view') {
              operation='merchand_view';
            }else if(operation=='edit') {
              operation='merchand_edit';
            }else if(operation=='delete') {
              operation='merchand_delete';
            }else if(operation=='act_deact') {
              operation='merchand_act_deact';              
            }else if(operation=='move') {
              operation='merchand_moveB4';              
            }else if(operation=='copy') {
              operation='merchand_copyB4';              
            }else if(operation=='promotion') {
              operation='merchand_promotion';              
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
            }else if(operation=='copy') {
              alert('<bean:message   key="alert.Selection.Merchandise.Required4Copy" />');
              return false;
            }else if(operation=='promotion') {
              alert('<bean:message   key="alert.Selection.Merchandise.Required4Promotion" />');
              return false;              
            }
          }
          thisForm.hdnSearchOperation.value=operation;
          if( operation=='merchand_delete' || operation=='cat_delete'){
            if (confirm('<bean:message   key="alert.delete.confirm" />')){
              thisForm.submit();
            }else{
              return false;
            }
          }
          if( operation=='merchand_moveB4' || operation=='cat_moveB4' || operation=='merchand_copyB4'){
              var theWindow=openWindow('','merchandiseCatList',375,425,0,0,true,'')
              thisForm.target='merchandiseCatList';

          }
          thisForm.submit();
        }else{
          alert('<bean:message   key="errors.selection.required" />');
        }
      }else{
        if(operation=='add'){
          if(thisForm.hdnSearchFlag.value=="1"){
            operation='cat_add'
          }else if(thisForm.hdnSearchFlag.value=="0"){
            operation='merchand_add'
          }
          thisForm.hdnSearchOperation.value=operation;
        }
        thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
        thisForm.submit();
      } 

    }

    function radioSearchOnclick(me){
      var thisForm=me.form;
      var index = getRadioSelectedIndex(thisForm.radSearchMerchandiseOrCategory);
      if(index==0){
        thisForm.txtSearchMerchandiseCategory.readOnly=false;
        thisForm.txtSearchMerchandiseName.readOnly=true;
        thisForm.txtSearchMerchandiseName.value='';
        thisForm.txtSearchMerchandiseCategory.focus();
      }else if(index==1){
        thisForm.txtSearchMerchandiseName.readOnly=false;
        thisForm.txtSearchMerchandiseCategory.readOnly=true;
        thisForm.txtSearchMerchandiseCategory.value='';
        thisForm.txtSearchMerchandiseName.focus();
      }
    }
    
    function btnSearchCloseOnclick(){
      var thisForm=document.forms[0];
      thisForm.radSearchMerchandiseOrCategory[0].checked=false;
      thisForm.radSearchMerchandiseOrCategory[1].checked=false;
      thisForm.txtSearchMerchandiseCategory.value='';
      thisForm.txtSearchMerchandiseName.value='';
      thisForm.radSearchStatus[2].checked=true;
      MM_showHideLayers('tblSearch','','hide','tblButtons','','show');
    }
    function window_onload(){
      var thisForm=document.forms[0];
      if(thisForm.hdnSearchOperation.value=='search'){
        if(thisForm.radSearchMerchandiseOrCategory[0].checked){
         radioSearchOnclick(thisForm.radSearchMerchandiseOrCategory[0]);
        }
        if(thisForm.radSearchMerchandiseOrCategory[1].checked){
          radioSearchOnclick(thisForm.radSearchMerchandiseOrCategory[1]);
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
<html:form action="/merchandiseRelayAction.do" >
<html:hidden property="hdnSearchOperation" />
<html:hidden property="hdnSearchCurrentStatus" />
<html:hidden property="hdnSearchPageNo" /> 
<html:hidden property="hdnSearchMerchandiseOrCategoryTblPk" />
<html:hidden property="hdnSearchFlag" />

<html:hidden property="hdnMerchandiseTblPk" value=""/><!-- This hidden is ment for price Promotion Table Only -->
<html:hidden property="hdnMerchandiseCategoryTblPk" value="<%=currentCategoryPk.toString()%>"/><!-- This hidden is ment for price Promotion Table Only -->
<html:hidden property="hdnMerchandiseSizeTblPk" value="-1"/><!-- This hidden is ment for price Promotion Table Only -->

<jsp:include page="header.jsp" /> 
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.Merchandise" /></div>
    </td>
    <td width="770px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<!-- Main Table Starts -->
<table id="tblMain" width="970px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td  valign="top" >
      <!-- Search and Button Table Starts -->
      <table id="tblSearchNdButtons" width="955px"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr><td height="10px"></td></tr>
        <tr>
          <td width="86%" height="30px" valign="bottom">
            <!--Search Table Starts -->
            <table id="tblSearch" width="100%"  border="0" class="bdrColor_336666"  style="display:none ">
              <tr>
                <td width="30%">
                  <table border="0" cellpadding="0" cellspacing="0"> 
                    <tr>
                      <td>
                        <html:radio property="radSearchMerchandiseOrCategory" onclick="return radioSearchOnclick(this);" value="categorySelect" ></html:radio>
                      </td>
                      <td>
                        <bean:message key="txt.MerchandiseCategory" />:
                      </td>
                      <td>
                        <html:text property="txtSearchMerchandiseCategory" styleClass="bdrColor_336666" readonly="true"/>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="30%">
                  <table border="0" cellpadding="0" cellspacing="0"> 
                    <tr>
                      <td>
                        <html:radio property="radSearchMerchandiseOrCategory" onclick="return radioSearchOnclick(this);" value="merchandiseSelect" ></html:radio>
                      </td>
                      <td>
                        <bean:message key="txt.MerchandiseName" />:
                      </td>
                      <td>
                        <html:text property="txtSearchMerchandiseName" styleClass="bdrColor_336666" readonly="true"/>
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
            <!--Search Table Ends -->
            
            <!-- Button Table Starts -->  
            <table id="tblButtons" width="100%" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td >
                  <table border="0" cellpadding="0" cellspacing="2">
                    <tr>
                      <td>
                        <html:button property="btnSearch" value="Search " styleClass="buttons" tabindex="1" onclick="MM_showHideLayers('tblSearch','','show','tblButtons','','hide')"/>
                      </td>
                      <logic:iterate id="module" name="userRights">          
                        <logic:equal name="module" property="name" value="Merchandise">
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
                                  <html:button property="btnAdd" styleClass="buttons" style="width:85px;" onclick="return btnclick('add');"  >
                                    <bean:message  key="btn.Add" />
                                  </html:button>
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
                                  <html:button property="btnMove" styleClass="buttons" onclick="btnclick('move')" ><bean:message  key="btn.Move" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnMove" styleClass="buttons" onclick="btnclick('move')" disabled="true"><bean:message  key="btn.Move" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>

                            <!--Copy Button Enable Disable-->
                            <logic:equal name="permission" property="name" value="Copy">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnCopy" styleClass="buttons" onclick="btnclick('copy')" ><bean:message  key="btn.Copy" /></html:button>
                                </td>
                              </logic:equal>
                              <logic:equal name="permission" property="value" value="0">
                                <td>
                                  <html:button property="btnCopy" styleClass="buttons" onclick="btnclick('copy')" disabled="true"><bean:message  key="btn.Copy" /></html:button>
                                </td>
                              </logic:equal>
                            </logic:equal>
                            
                          </logic:iterate>                          
                        </logic:equal>
                      </logic:iterate>    
                      
                      <!--Price Promotion Button Visible Invisible-->
                      <logic:iterate id="module" name="userRights">          
                        <logic:equal name="module" property="name" value="Price Promotion">
                          <logic:iterate id="permission" name="module" property="permissions">  
                            <logic:equal name="permission" property="name" value="View">
                              <logic:equal name="permission" property="value" value="1">
                                <td>
                                  <html:button property="btnPricePromotion" styleClass="buttons" onclick="btnclick('promotion')" style="width:100px;" ><bean:message  key="btn.PricePromotion" /></html:button>
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
            <!-- Button Table Ends -->     
          </td>
        </tr>
        <tr>
          <td height="10px" colspan="2" valign="bottom"></td>
        </tr>
      </table>
      <!-- Search and Button Table Starts -->        
    </td>
  </tr>
  <tr>
    <td align="center">
      <!-- Tree View and List Table Stars-->
      <table id="tblTreeNdList" border="0" width="98%" height="445px" cellpadding="0" cellspacing="0"  >
        <tr>
          <td valign="top" width="25%" >
            <!-- Treeview Table Starts-->
            <table id="tblTreeview"  width="100%"    border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2">
              <tr bgcolor="#FFFFFF">
                <th><bean:message key="tree.Category" /></th>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td >
                  <iframe src="merchandiseRelayAction.do?hdnSearchOperation=merchand_tree_view&currentCategoryPk=<%=currentCategoryPk%>" scrolling="yes" height="415px" width="100%"  frameborder="0"></iframe>
                </td>
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
                  <div style="overflow:auto;height:408px;">
                    <table width="100%"  border="0" cellspacing="1" bgcolor="#80A0B2">
                      <tr bgcolor="#FFFFFF">
                        <th width="2%">
                        <% if(!currentCategoryPk.equals("-1")){ %>
                          <a style="margin-left:5px;" href="merchandiseRelayAction.do?hdnSearchOperation=merchand_up&merchandiseOrCategoryTblPk=<%=currentCategoryPk%>" class="imgGoUpDataTbl"  title="<bean:message key="tooltips.Up" />" ></a>
                        <%}else{%>
                          <div  style="margin-left:5px;" class="imgGoUpDataTblDisable"></div>
                        <%}%>
                        </th>                      
                        <th width="30%"><bean:message key="tbl.Name" /></th>
                        <th width="35%"><bean:message key="tbl.Description" /></th>
                        <th width="10%"><bean:message key="tbl.Type" /></th>
                        <th width="23%"><bean:message key="tbl.Flag" /></th>
                        <th width="6%"><bean:message key="tbl.Status" /></th>
                      </tr>
                      <%if(merchandises.size()>0){ %>
                        <logic:iterate id="merchandise" name="merchandises">  
                          <bean:define id="MerchandiseOrCategoryTblPk" name="merchandise" property="merchandiseOrCategoryTblPk" />
                          <tr bgcolor="#FFFFFF">
                            <td><html:radio property="radSearchSelect" onclick="return radioOnlick(this);" value="<%=(String)MerchandiseOrCategoryTblPk%>"></html:radio></td>
                            <logic:equal name="merchandise" property="merchandiseOrCategoryType" value="Category">
                              <td>
                                <a class="tree" onclick="document.forms[0].hdnSearchPageNo.value='1'" style="margin-left:3px" href="merchandiseRelayAction.do?hdnSearchOperation=merchand_list&merchandiseOrCategoryTblPk=<bean:write name="merchandise" property="merchandiseOrCategoryTblPk"/>">
                                  <b><bean:write name="merchandise" property="merchandiseOrCategoryName" /></b>
                                </a>
                              </td>
                            </logic:equal>
                            <logic:equal name="merchandise" property="merchandiseOrCategoryType" value="Merchandise">
                              <td><bean:write name="merchandise" property="merchandiseOrCategoryName" /></td>
                            </logic:equal>
                            <td><bean:write name="merchandise" property="merchandiseOrCategoryDesc" /></td>
                            <td><bean:write name="merchandise" property="merchandiseOrCategoryType" /></td>
                            <td><bean:write name="merchandise" property="flag" /></td>
                            <td><bean:write name="merchandise" property="isActiveDisplay" /></td>
                            <bean:define id="intStatus" name="merchandise" property="isActive" />
                            <html:hidden property="hdnSearchStatus" value="<%=(String)intStatus%>" />
                            <bean:define id="MerchandiseOrCategoryType" name="merchandise" property="merchandiseOrCategoryType" />
                            <html:hidden property="hdnSearchMerchandiseOrCategoryType" value="<%=(String)MerchandiseOrCategoryType%>" />
                          </tr>
                        </logic:iterate>
                      <%}%>
                    </table>
                    <% if(merchandises.size()==0) {%>
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
                      <td width="30px" ><div class="imgStatusMsg"></div></td>
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
      <!-- Tree View and List Table Ends-->
    </td>
  </tr>
</table>
</html:form>
</BODY>
</HTML>
