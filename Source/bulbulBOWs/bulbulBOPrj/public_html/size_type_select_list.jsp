<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="sizeTypeListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="sizeTypeListForm" property="hdnSearchPageNo" />        
<bean:define id="sizeTypes" name="sizeTypes" type="java.util.ArrayList" /> 
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
      navBar.onclick="btnclick()";

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
      function btnclick(){
        var thisForm=document.forms[0];
        thisForm.target="_self";
        thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
        thisForm.submit();
      }
      
      function btnOkClick(){
        var thisForm=document.forms[0];
        var index=0;
        var intSizeTypeTblPk=-1;
        var strSizeTypeId="";
        index=getRadioSelectedIndex(thisForm.radSearchSelect);
        if (index>-2){
          if (index==-1){
            intSizeTypeTblPk=thisForm.radSearchSelect.value;
            strSizeTypeId=thisForm.hdnSizeTypeId.value;
          }else{
            intSizeTypeTblPk=thisForm.radSearchSelect[index].value;
            strSizeTypeId=thisForm.hdnSizeTypeId[index].value;
          }
        }else{
          alert('<bean:message   key="errors.selection.required" />');
          return false;
        }
        (eval('opener.document.forms[0].'+thisForm.hdnControlId4SizeTypePk.value)).value=intSizeTypeTblPk;
        (eval('opener.document.forms[0].'+thisForm.hdnControlId4SizeTypeId.value)).value=strSizeTypeId;
        window.close();
      }
      //-->
    </script> 
</HEAD>
<BODY>
<html:form action="/sizeTypeSelectListAction.do">
<html:hidden property="hdnSearchPageNo" />
<input type="hidden" name="hdnControlId4SizeTypePk" id="hdnControlId4SizeTypePk" value="<bean:write name="hdnControlId4SizeTypePk" />" />
<input type="hidden" name="hdnControlId4SizeTypeId" id="hdnControlId4SizeTypeId" value="<bean:write name="hdnControlId4SizeTypeId" />" />
<!-- Tab Table Starts -->
<table  width="450px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2"  height="20px">
    </td>
  </tr>
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.SizeType" /></div>
    </td>
    <td width="250px" >
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<!-- Main Table Ends -->
<table width="450px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
  <tr>
    <td align="center">  
      <!-- List Table Stars-->
      <table border="0" width="98%" cellpadding="0" cellspacing="0" >
        <tr><td height="6px" ></td></tr>
        <tr>
          <td>
            <!-- List Table Starts-->
            <div style="overflow:auto;height:416px;">
              <table width="100%"  border="0" cellspacing="1" bgcolor="#80A0B2">
                <tr bgcolor="#FFFFFF">
                  <th width="3%">&nbsp;</th>
                  <th width="26%"><bean:message key="tbl.SizeTypeId" /> </th>
                  <th width="61%"><bean:message key="tbl.SizeTypeDesc" /></th>
                </tr>
                <%if(sizeTypes.size()>0){ %>
                  <logic:iterate id="sizeType" name="sizeTypes">  
                    <bean:define id="sizeTypeTblPk" name="sizeType" property="sizeTypeTblPk" />
                    <bean:define id="sizeTypeId" name="sizeType" property="sizeTypeId" />
                      <tr bgcolor="#FFFFFF">
                        <td><html:radio property="radSearchSelect"  value="<%=(String)sizeTypeTblPk%>"></html:radio></td>
                        <td>  
                          <bean:write name="sizeType" property="sizeTypeId" />
                          <input type="hidden" id="hdnSizeTypeId" name="hdnSizeTypeId" value="<%=(String)sizeTypeId%>" >
                        </td>          
                        <td><bean:write name="sizeType" property="sizeTypeDescription" /></td>
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
        <tr>
          <td align="right">
            <html:button property="btnOk" styleClass="buttons" onclick="return btnOkClick();" ><bean:message key="btn.Ok" /></html:button>
            <html:button property="btnCancel" styleClass="buttons" onclick="return window.close();"><bean:message key="btn.Cancel" /></html:button>
          </td>
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
