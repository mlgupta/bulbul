<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="title.MyCreativityList" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<script>
  function btnDelete_onclick(customerDesignTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='myCreativityDeleteAction.do?customerDesignTblPk='+customerDesignTblPk;    
  }
  function btnModify_onclick(){
    window.parent.location='studioAction.do?studioFrameSrc=design_engine.jsp';  
  }
  function btnBuy_onclick(customerDesignTblPk){
    window.parent.location='studioAction.do?studioFrameSrc=orderCreateB4Action.do?customerDesignTblPk='+customerDesignTblPk;  
  }
</script>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts-->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyCreativityList" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
    
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" valign="top" height ="393px" class="bdrLeftColor_006600 bdrTopColor_006600 bdrRightColor_006600">
        <!-- List Table Starts-->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td align="center" colspan="3">
            <div style="color:#ff0000">
                <b>
                  <html:messages id="messageDesignInOrder" message="true"  property="messageDesignInOrder" >
                    <bean:write name="messageDesignInOrder" />
                  </html:messages>
                </b>
              </div> 
            </td>
          </tr>
          <tr>
            <td height="10px" colspan="3"></td>
          </tr>
          <% int count=0;%>
            <logic:iterate id="design" name="designs" indexId="dIndex" >  
              <%
                count=dIndex.intValue()+1 ;
                if(((dIndex.intValue())%3)==0){
              %>
                <tr>
              <%
                }
              %>
                <td width="33%" valign="top">
                  <!-- Image Layer Starts -->
                  
                  <div align="center">
                    <table width="85%" border="0" cellpadding=1 cellspacing=1 class="bdrColor_006600 bgColor_EBFFE9">
                      <tr>
                        <td align="center" class="text_006600"><bean:write name="design" property="designName" /></td>
                      </tr>
                      <tr>
                        <td align="center">
                            <img name="display" id="display" border="0" class="bdrColor_006600" alt="<bean:write name="design" property="designName" />"  style="width:100px; height:100px; margin-top:5px; background-color:#FFFFFF" title="Modify This Image"  src="imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="design" property="designOId" />&imageContentType=<bean:write name="design" property="designContentType" />&imageContentSize=<bean:write name="design" property="designContentSize" />" ></img>
                        </td>
                      </tr>
                      <tr>
                        <td align="center" class="text_normal10"><bean:write name="design" property="productName" /></td>
                      </tr>
                      <tr>
                        <td align="center" class="text_normal10"><bean:write name="design" property="createdOn" /></td>
                      </tr>
                      <tr>
                        <td align="center">
                            <img src="images/btn_buy.gif" alt="Buy" title="Buy This Merchandise" style="cursor:pointer;cursor:hand;" width="60px" height="16px" border="0" onclick="return btnBuy_onclick('<bean:write name="design" property="customerDesignTblPk" />');" >
                            <img src="images/btn_custom.gif" alt="Customize" title="Cutomize This Design" style="cursor:pointer;cursor:hand;" width="60px" height="16px" border="0" onclick="return btnModify_onclick();" >
                            <img src="images/btn_delete.gif" width="60px" height="16px" border="0" style="margin-left:1px;cursor:pointer;cursor:hand;" alt="Delete" title="Delete This Image" onclick="return btnDelete_onclick('<bean:write name="design" property="customerDesignTblPk" />');">
                        </td>
                      </tr>
                    </table>
                  </div>                  
                  <!-- Image Layer Ends -->
                </td>
              <%if(((dIndex.intValue())%3)==2){%>
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
        <!-- List Table Ends-->
      </td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
        <!-- Navigation Table Starts -->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="10px" width="33%" class="bdrTopColor_006600">&nbsp;</td>
            <td align="center" width="33%" class="bdrTopColor_006600">
              <html:button styleClass="buttons" style="width:64px; height:20px; " property="btnCreate" onclick="window.parent.location='studioAction.do?studioFrameSrc=design_engine.jsp';" ><bean:message key="btn.Create" /></html:button>
            </td>
            <td align="right" width="33%" height="10px" valign="top" class="bdrTopColor_006600">
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
                      <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=counter%>"><%=counter%></a>
                    <%}%>
                <%}%>                      
                <%if ((endCounter)<=pageCount){%>
                  <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                <%}%>                                           
                <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
              <%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
                <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=1%>">First(1)</a>
                <%if(!((startCounter-1)<=0)){%>
                  <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                <%}%>
                <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                    <%if(counter==pageNumber){%>
                      <span class="linkPageNavCurrentPage"><%=counter%></span>
                    <%}else{%>
                      <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=counter%>"><%=counter%></a>
                    <%}%>
                <%}%>
              <%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
                <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=1%>">First(1)</a>
                <%if(!((startCounter-1)<=0)){%>
                  <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                <%}%>
                <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                    <%if(counter==pageNumber){%>
                      <span class="linkPageNavCurrentPage"><%=counter%></span>
                    <%}else{%>
                      <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=counter%>"><%=counter%></a>
                    <%}%>
                <%}%>
                <%if ((endCounter)<=pageCount){%>
                  <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                <%}%>                                           
                <a class="linkPageNav" href="myCreativityListAction.do?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
              <%}%>
              &nbsp;&nbsp;
              <!-- Navigation Layer Ends -->  
            </td>
          </tr>
        </table>
        <!-- Navigation Table Ends -->
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
