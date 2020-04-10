<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="title.MyGraphicsList" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<script>
  function btnDelete_onclick(customerGraphicsTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='myGraphicsDeleteAction.do?customerGraphicsTblPk='+customerGraphicsTblPk;
  }
  function btnModify_onclick(customerGraphicsTblPk){
    window.location='myGraphicsModifyB4Action.do?customerGraphicsTblPk='+customerGraphicsTblPk;
  }
</script>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyGraphicsList" /></td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="25px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 bdrBottomColor_006600 text_bold12">
        <!-- Tool Bar Table Starts -->
        <table width="100%"  border="0">
          <tr>
            <td width="18%">
              <a href="mygraphics_upload.jsp"><img src="images/btn_upload.gif" alt="Upload Image" width="93px" height="16px" border="0" title="Upload Image"></a>
            </td>
            <td width="82%">
              <div align="right">
                <a class="linkMenu" href="myGraphicsListAction.do?format=all">&nbsp;<bean:message key="lnk.AllFormats" />&nbsp;</a> | 
                <a class="linkMenu" href="myGraphicsListAction.do?format=jpg">&nbsp;<bean:message key="lnk.Jpg" />&nbsp;</a> | 
                <a class="linkMenu" href="myGraphicsListAction.do?format=gif">&nbsp;<bean:message key="lnk.Gif" />&nbsp;</a> | 
                <a class="linkMenu" href="myGraphicsListAction.do?format=png">&nbsp;<bean:message key="lnk.Png" />&nbsp;</a> | 
                <a class="linkMenu" href="myGraphicsListAction.do?format=svg">&nbsp;<bean:message key="lnk.Svg" />&nbsp;</a>
              </div>
            </td>
          </tr>
        </table>
        <!-- Tool Bar Table Ends -->
		  </td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF" valign="top" height="393px" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
          <!-- List Of Images Table Starts-->
          <table width="100%"    border="0" cellpadding="1" cellspacing="1">
            <tr>
              <td height="5px" colspan="3"></td>
            </tr>
            <% int count=0;%>
            <logic:iterate id="graphic" name="graphics" indexId="gIndex" >              
              <%
                count=gIndex.intValue()+1 ;
                if(((gIndex.intValue())%3)==0){
              %>
                <tr>
              <%
                }
              %>
                <td width="33%" valign="top">
                  <!-- Image Layer Starts -->
                  <div align="center">
                    <table width="75%" border=0 cellpadding=1 cellspacing=1 class="bdrColor_006600 bgColor_EBFFE9">
                      <tr>
                        <td align="center" class="text_006600"><bean:write name="graphic" property="graphicsTitle" /></td>
                      </tr>
                      <tr>
                        <td align="center">
                          <a href="myGraphicsModifyB4Action.do?customerGraphicsTblPk=<bean:write name="graphic" property="customerGraphicsTblPk" />">
                            <img name="display" id="display" border="0" class="bdrColor_006600" alt="<bean:write name="graphic" property="graphicsTitle" />"  style="width:100px; height:100px; margin-top:5px; background-color:#FFFFFF" title="Modify This Image"  src="imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="graphic" property="graphicsOid" />&imageContentType=<bean:write name="graphic" property="graphicsContentType" />&imageContentSize=<bean:write name="graphic" property="graphicsContentSize" />" ></img>
                          </a> 
                        </td>
                      </tr>
                      <tr>
                        <td align="center" class="text_normal10"><bean:write name="graphic" property="createdOn" /></td>
                      </tr>
                      <tr>
                        <td align="center" class="text_normal10" ><bean:write name="graphic" property="graphicsCategory" /></td>
                      </tr>
                      <tr>
                        <td align="center">
                            <img src="images/btn_modify.gif" alt="Modify" title="Modify This Image" style="cursor:pointer;cursor:hand;"width="60px" height="16px" border="0" onclick="return btnModify_onclick('<bean:write name="graphic" property="customerGraphicsTblPk" />');">
                            <img src="images/btn_delete.gif" width="60px" height="16px" border="0" style="margin-left:1px;cursor:pointer;cursor:hand;" alt="Delete" title="Delete This Image" onclick=" return btnDelete_onclick('<bean:write name="graphic" property="customerGraphicsTblPk" />');">
                        </td>
                      </tr>
                    </table>
                  </div>
                  
                  <!-- Image Layer Ends -->
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
        <!-- List Of Images Table Ends-->
      </td>
    </tr>
    <tr>
    <td bgcolor="#FFFFFF"  colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
      <table width="100%"    border="0" cellpadding="1" cellspacing="1">
        <tr>
          <td align="right" class="bdrTopColor_006600" height="10px" colspan="3" valign="top">
            <!-- Navigation Layer Starts -->
            <bean:define id="strPageNumber" name="pageNumber" type="String" />
            <bean:define id="strPageCount" name="pageCount" type="String" />
            <%
              int pageNumber=Integer.parseInt(strPageNumber);
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
                    <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=counter%>"><%=counter%></a>
                  <%}%>
              <%}%>                      
              <%if ((endCounter)<=pageCount){%>
                <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
              <%}%>                                           
              <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
            <%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
              <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=1%>">First(1)</a>
              <%if(!((startCounter-1)<=0)){%>
                <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
              <%}%>
              <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                  <%if(counter==pageNumber){%>
                    <span class="linkPageNavCurrentPage"><%=counter%></span>
                  <%}else{%>
                    <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=counter%>"><%=counter%></a>
                  <%}%>
              <%}%>
            <%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
              <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=1%>">First(1)</a>
              <%if(!((startCounter-1)<=0)){%>
                <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
              <%}%>
              <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                  <%if(counter==pageNumber){%>
                    <span class="linkPageNavCurrentPage"><%=counter%></span>
                  <%}else{%>
                    <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=counter%>"><%=counter%></a>
                  <%}%>
              <%}%>
              <%if ((endCounter)<=pageCount){%>
                <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
              <%}%>                                           
              <a class="linkPageNav" href="myGraphicsListAction.do?format=<bean:write name="format" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
            <%}%>
            &nbsp;&nbsp;
            <!-- Navigation Layer Ends -->     
          </td>
        </tr> 
      </table>
    </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends-->
</body>
</html>