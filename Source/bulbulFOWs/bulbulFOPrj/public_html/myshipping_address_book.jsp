<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.ShippingAddressBook" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<script>
  function btnDelete_onclick(customerAddressBookTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='myAddressBookDeleteAction.do?customerAddressBookTblPk='+customerAddressBookTblPk;
  }
  function btnModify_onclick(customerAddressBookTblPk){
    window.location='myAddressBookModifyB4Action.do?customerAddressBookTblPk='+customerAddressBookTblPk;
  }  
</script>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ShippingAddressBook" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" valign="top" height ="393px" class="bdrLeftColor_006600 bdrTopColor_006600 bdrRightColor_006600">
        <!-- Address Book Table Starts -->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="10px" colspan="3"></td>
          </tr>
          <% int count=0;%>
            <logic:iterate id="address" name="addresses" indexId="gIndex" >
              <%
                count=gIndex.intValue()+1 ;
                if(((gIndex.intValue())%3)==0){
              %>
                <tr>
              <%
                }
              %>
                  <td width="33%" align="center" valign="top">
                    <!-- Address Label Table Starts -->
                    <table width="225px" height="185px" border=0 cellpadding=1 cellspacing=1 class="bdrColor_006600 bgColor_EBFFE9 ">
                      <tr>
                        <td  height="155px" valign="top">
                          <bean:write name="address" property="fullName" /><br>
                          <bean:write name="address" property="addressLine1" /><br>
                          <bean:write name="address" property="addressLine2" /><br>
                          <bean:write name="address" property="city" /> - <bean:write name="address" property="pincode" /><br>
                          <bean:write name="address" property="state" /> - <bean:write name="address" property="country" /><br>
                          <bean:write name="address" property="phoneNumber" /><br>
                          <bean:write name="address" property="emailId" /><br>
                        </td>
                      </tr>
                      <tr>
                        <td height="25px" align="center" valign="bottom"> 
                          <img src="images/btn_modify.gif" alt="Modify" title="Modify This Address" width="60px" height="16px" border="0" style="cursor:pointer; cursor:hand; margin-left:1px" onclick="return btnModify_onclick('<bean:write name="address" property="customerAddressBookTblPk" />');"> 
                          <img src="images/btn_delete.gif" width="60px" height="16px" border="0" alt="Delete" title="Delete This Address" style="cursor:pointer; cursor:hand; margin-left:1px" onclick="return btnDelete_onclick('<bean:write name="address" property="customerAddressBookTblPk" />');">  
                        </td> 
                      </tr>
                    </table>
                    <!-- Address Label Table Ends -->
                  </td>
              <%if(((gIndex.intValue())%3)==2){%>
                </tr>
                <tr><td height="5px" colspan="3" valign="top"></td></tr>
              <%}%>
            </logic:iterate>
            <%
              if(count==1){
            %>
            <td width="33%" valign="top"></td>
            <td width="33%" valign="top"></td>
            <%
              }
            %>
            <%
              if(count==2){
            %>
              <td width="33%" valign="top"></td>              
            <%
              }
            %>
            
            <%if (((count)%3)!=0){%>
              </tr>
              <tr><td height="10px" colspan="3" valign="top"></td></tr>
            <%}%>
          </table>
          <!-- Address Book Table Ends -->
        </td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600  bdrRightColor_006600">
          <!--Navigation Table Starts-->
          <table width="100%"    border="0" cellpadding="1" cellspacing="1">
            <tr>
              <td height="10px" width="33%" class="bdrTopColor_006600">&nbsp;</td>
              <td align="center" width="33%" class="bdrTopColor_006600">
                <html:button styleClass="buttons" style="width:60px; height:20px" property="btnNew" onclick="window.location.replace('myAddressBookNewB4Action.do');" title="Create New Address..."><bean:message key="btn.New" /></html:button>
              </td>
              <td align="right" width="33%" class="bdrTopColor_006600">
                <!-- Navigation Layer Starts -->
                <bean:define id="strPageNumber" name="pageNumber" type="String" />
                <bean:define id="strPageCount" name="pageCount" type="String" />
                <%
                  int pageNumber=Integer.parseInt(strPageNumber);;
                  int pageCount=Integer.parseInt(strPageCount);
                  int noOfPageNumbers=3;
                  int startCounter = Math.round(pageNumber/noOfPageNumbers)*noOfPageNumbers;
                  if((pageNumber%noOfPageNumbers)!=0){
                    startCounter+=1;
                  }
                  if((pageNumber==pageCount) ||((pageCount-pageNumber)<noOfPageNumbers) ){    
                    startCounter=(((pageCount-noOfPageNumbers)+1)<=0)?startCounter:((pageCount-noOfPageNumbers)+1);
                  }
                  
                  int endCounter = startCounter+noOfPageNumbers;
                  if ((startCounter+noOfPageNumbers)>pageCount){
                    endCounter=pageCount+1; 
                  }
                %>
                <%if((pageNumber==1 && pageNumber!=pageCount)){%>
                  <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                      <%if(counter==pageNumber){%>
                        <span class="linkPageNavCurrentPage"><%=counter%></span>
                      <%}else{%>
                        <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=counter%>"><%=counter%></a>
                      <%}%>
                  <%}%>                      
                  <%if ((endCounter)<=pageCount){%>
                    <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                  <%}%>                                           
                  <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                <%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
                  <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=1%>">First(1)</a>
                  <%if(!((startCounter-1)<=0)){%>
                    <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                  <%}%>
                  <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                      <%if(counter==pageNumber){%>
                        <span class="linkPageNavCurrentPage"><%=counter%></span>
                      <%}else{%>
                        <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=counter%>"><%=counter%></a>
                      <%}%>
                  <%}%>
                <%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
                  <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=1%>">First(1)</a>
                  <%if(!((startCounter-1)<=0)){%>
                    <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                  <%}%>
                  <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                      <%if(counter==pageNumber){%>
                        <span class="linkPageNavCurrentPage"><%=counter%></span>
                      <%}else{%>
                        <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=counter%>"><%=counter%></a>
                      <%}%>
                  <%}%>
                  <%if ((endCounter)<=pageCount){%>
                    <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                  <%}%>                                           
                  <a class="linkPageNav" href="myShippingAddressBookB4Action.do?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                <%}%>
                &nbsp;&nbsp;
                <!-- Navigation Layer Ends -->                
              </td>
            </tr>
          </table>
          <!--Navigation Table Ends-->
        </td>
      </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</body>
</html>
