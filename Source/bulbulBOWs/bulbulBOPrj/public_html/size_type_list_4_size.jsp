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
        window.top.document.forms[0].hdnSizeTypePageNo.value=navBar.getPageNumber();
        thisForm.submit();
      }
      //-->
    </script> 
</HEAD>
<BODY bgcolor="#FFFFFF">
<!-- Main Table Ends -->
<table height="100%" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
<html:form action="/sizeTypeList4SizeAction">
  <html:hidden property="hdnSearchPageNo" />
</html:form>
  <tr>
    <td align="center">  
      <!-- List Table Stars-->
      <table  height="100%" width="100%" cellpadding="0" cellspacing="0" >
        <tr>
          <td valign="top">
            <!-- List Table Starts-->
            <div style="overflow:auto;height:375px;">
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" >                
                <%if(sizeTypes.size()>0){ %>
                  <tr bgcolor="#FFFFFF">
                    <td colspan="3" valign="bottom" height="18px" style="background-image:url('images/ftv2folderclosed.gif');background-repeat:no-repeat" ></td>
                  </tr>
                  
                  <bean:define id="sizeTypesArray" name="sizeTypes" type="java.util.ArrayList" />  
                  <logic:iterate id="sizeType" name="sizeTypes" indexId="sizeTypeIndex">  
                    <bean:define id="sizeTypeTblPk" name="sizeType" property="sizeTypeTblPk" />
                    <bean:define id="sizeTypeId" name="sizeType" property="sizeTypeId" />
                    <bean:define id="currentSizeTypeTblPk" name="currentSizeTypeTblPk" />
                    
                      <tr bgcolor="#FFFFFF">
                         
                          <%
                          if(sizeTypeIndex.intValue() ==(sizeTypesArray.size()-1)){
                          %>
                            <td width="5px" valign="top"><img src="images/ftv2lastnode.gif" ></td>
                          <%
                          }else{
                          %>
                            <td width="5px" valign="top"><img src="images/ftv2node.gif" ></td>                                             
                          <%
                          }
                          %>
                          <%if (currentSizeTypeTblPk.equals(sizeTypeTblPk)) {%>
                          <td width="5px" ><img src="images/ftv2folderopen.gif" ></td>
                          <td>                          
                            <div style="background-color:#80A0B2;color:#ffffff" ><bean:write name="sizeType" property="sizeTypeId" /></div>
                          </td>
                          <%}else{%>
                          <td width="5px"><img src="images/ftv2folderclosed.gif" style="cursor:pointer;cursor:hand;" onclick="document.forms[0].hdnSearchPageNo.value='1'; window.top.location='sizeRelayAction.do?hdnSearchOperation=list&hdnSizeTypeTblPk=<bean:write name="sizeType" property="sizeTypeTblPk" />&hdnSizeTypePageNo=<bean:write name="sizeTypeListForm" property="hdnSearchPageNo"/>'"></td>
                          <td>
                            <a class="tree" target="_top" onclick="document.forms[0].hdnSearchPageNo.value='1'" style="margin-left:3px" href="sizeRelayAction.do?hdnSearchOperation=list&hdnSizeTypeTblPk=<bean:write name="sizeType" property="sizeTypeTblPk" />&hdnSizeTypePageNo=<bean:write name="sizeTypeListForm" property="hdnSearchPageNo" />">
                              <bean:write name="sizeType" property="sizeTypeId" />
                            </a>
                          </td> 
                          <%}%>                          
                                 
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
        <tr>
          <td align="center" class="bdrTopColor_333333">
            <!-- Status Bar Table Starts-->
            <table id="tblStatusBar" align="center" width="100%" border="0" cellpadding="0" cellspacing="0"  >
            <tr bgcolor="#FFFFFF">
              <td align="center"><script>navBar.render();</script></td>
            </tr>
            </table>
            <!-- Status Bar Table Ends-->
          <td>
        </tr>        
      </table> 
      <!-- List Table Ends-->      
    </td>
  </tr>  

</table>
<!-- Main Table Ends -->
</BODY>
</HTML>
