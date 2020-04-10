<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<bean:define id="categories" name="categories" type="java.util.ArrayList" /> 
<html>
<head>
<title><bean:message key="title.MyFavouriteList" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<script>
  function btnDelete_onclick(customerFavouriteTblPk){
    if (confirm('<bean:message key="confirm.AreYouSure2Delete" />')==0){
      return false;
    }
    window.location='myFavouriteDeleteAction.do?customerFavouriteTblPk='+customerFavouriteTblPk;
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
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyFavouriteList" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
    <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 bdrBottomColor_006600 text_bold12">&nbsp;</td>
    </tr>	
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
        <!-- Category Tree and List Table Starts-->      
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="33%" valign="top">
              <div class="bdrColor_006600 bgColor_EBFFE9" style="width:210px; height:370px; margin-top:12px; margin-left:15px; margin-bottom:12px;" title="">                
                      <table style="margin-left:3px;margin-top:3px;" border="0" cellpadding="0" cellspacing="0" with="100%">
                        <tr>
                          <logic:equal name="category" value="-1" >
                            <td colspan="3">
                              <span class="bgColor_006600" style="margin-left:3px;color:#ffffff" >All</span></a>
                            </td>
                          </logic:equal> 
                          <logic:notEqual name="category" value="-1" >
                            <td colspan="3">
                              <a  class="tree" style="margin-left:3px" title="All" href="myFavouriteListAction.do?category=-1">All</a>
                            </td>
                          </logic:notEqual> 
                        </tr>
                        <logic:iterate id="categoryname" name="categories" indexId="categoryIndex" >
                          <tr>
                            <%
                            if(categoryIndex.intValue() ==(categories.size()-1)){
                            %>
                              <td valign="top"><img src="images/ftv2lastnode.gif" ></td>
                            <%
                            }else{
                            %>
                              <td valign="top"><img src="images/ftv2node.gif" ></td>                                             
                            <%
                            }
                            %>
                            <logic:equal name="category" value="<%=(String)categoryname%>" >
                              <td valign="top"></td>
                              <td>
                                <span class="bgColor_006600" style="margin-left:3px;color:#ffffff" ><bean:write name="categoryname" /></span>
                              </td>
                            </logic:equal> 
                            <logic:notEqual name="category"  value="<%=(String)categoryname%>" >
                              <td valign="top" ></td>
                              <td>
                                <a class="tree" style="margin-left:3px"  href="myFavouriteListAction.do?category=<bean:write name="categoryname" />"><bean:write name="categoryname" /></a>
                              </td>
                            </logic:notEqual>
                          </tr>
                        </logic:iterate>
                      </table>
              </div>
            </td>
            <td width="67%" valign="top">
              <!--List Table Starts-->
              <table width="485px"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td height="10px" colspan="3"></td>
                </tr>
                <% int count=0;%>
                <logic:iterate id="product" name="products" indexId="gIndex" >              
                  <%
                    count=gIndex.intValue()+1 ;
                    if(((gIndex.intValue())%3)==0){
                  %>
                    <tr>
                  <%
                    }
                  %>
                      <td width="33%" valign="top">
                        <div align="center">
                          <!-- Product 1 Table Starts -->
                          <table width="135px" border=0 cellpadding=1 cellspacing=1 class="bdrColor_006600 bgColor_EBFFE9">
                            <tr>
                              <td align="right" class="text_006600"><div align="center"><bean:write name="product" property="productName" /> </div></td>
                            </tr>
                            <tr>
                              <td align="center"> 
                                <a href="myFavouriteProductAction.do?merchandiseTblPk=<bean:write name="product" property="morcTblPk" />&category=<bean:write name="category" />&pageNumber=<bean:write name="pageNumber" />">                                  
                                  <img name="display" id="display" class="bdrColor_006600" border="0" alt="<bean:write name="product" property="productName" />" style="width:100px; height:100px; background-color:#FFFFFF" title="See Details..."  src="imageDisplayAction.do?dataSourceKey=BOKey&imageOid=<bean:write name="product" property="productOId" />&imageContentType=<bean:write name="product" property="productContentType" />&imageContentSize=<bean:write name="product" property="productContentSize" />" ></img>
                                </a>
                              </td>
                            </tr>
                            <tr>
                              <td align="center" class="text_normal10"><bean:message key="img.Colors" /> : <bean:write name="product" property="noOfColors" /></td>
                            </tr>
                            <tr>
                              <td height="18px" align="center" class="text_normal10"><bean:write name="product" property="productDescription" /></td>
                            </tr>
                            <tr>
                              <td align="center">
                                <img src="images/btn_delete.gif" width="60px" height="16px" border="0" style="margin-left:1px; cursor:pointer;cursor:hand" alt="Delete" title="Delete This Image" onclick="return btnDelete_onclick('<bean:write name="product" property="customerFavouriteTblPk" />');">                                
                              </td>
                            </tr>
                          </table>
                          <!-- Product 1 Table Ends -->
                        </div>
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
            </td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF"  colspan="3" class="">
              <table width="100%"    border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td align=right class="bdrTopColor_006600" height="10px" colspan="3" valign="top">
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
                            <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>                      
                      <%if ((endCounter)<=pageCount){%>
                        <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                      <%}%>                                           
                      <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                    <%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
                      <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=1%>">First(1)</a>
                      <%if(!((startCounter-1)<=0)){%>
                        <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                      <%}%>
                      <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                          <%if(counter==pageNumber){%>
                            <span class="linkPageNavCurrentPage"><%=counter%></span>
                          <%}else{%>
                            <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>
                    <%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
                      <a class="linkPageNav" href="myFavouriteListAction.do?category=&pageNumber=<%=1%>">First(1)</a>
                      <%if(!((startCounter-1)<=0)){%>
                        <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                      <%}%>
                      <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                          <%if(counter==pageNumber){%>
                            <span class="linkPageNavCurrentPage"><%=counter%></span>
                          <%}else{%>
                            <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>
                      <%if ((endCounter)<=pageCount){%>
                        <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                      <%}%>                                           
                      <a class="linkPageNav" href="myFavouriteListAction.do?category=<bean:write name="category" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                    <%}%>
                    &nbsp;&nbsp;
                    <!-- Navigation Layer Ends -->     
                  </td>
                </tr>
              </table>
              <!--List Table Ends-->
            </td>
          </tr>
        </table>
        <!-- Category Tree and List Table Ends-->      
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
</body>
</html>
