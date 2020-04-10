<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message key="title.ProductCatalogueList" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="catalogue.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<SCRIPT LANGUAGE=javascript>
<!--  
function  btnAddFav_onclick(merchandiseTblPk){
	openWindow('addToFavouriteB4Action.do?merchandiseTblPk='+merchandiseTblPk,'add2fav',350,200,0,0,true,'');
}
//-->
</SCRIPT>
</head>
<body>
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr><td height="10px"></td></tr>
  </table>
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="24px" height="17px"><img src="images/catalogue_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/catalogue_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ProductCatalogueList" />  :</td>
      <td width="5px"><img src="images/catalogue_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="18px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94 bdrTopColor_007C94 bdrBottomColor_007C94 text_bold12">        
        <table border="0"  cellpadding="0" cellspacing="0" >
          <tr>
            <logic:iterate id="catNdPkArray" name="catNavList" type="String[]" indexId="catIndex" >
              <% if(catIndex.intValue()==0&&!(catNdPkArray[0].equals("-1"))){%>
                <td><a  href="productListAction.do?morcTblPk=<%=catNdPkArray[0]%>" title="<%=catNdPkArray[1]%>" ><img border="0" src="images/link_nav_left.gif" height="18px" ></a></td>            
              <%}else{%>
                <%if(catIndex.intValue()!=0){%><td><img src="images/link_nav_right.gif" height="18px" ></td><%}%>
                <td><a class="linkMenu" href="productListAction.do?morcTblPk=<%=catNdPkArray[0]%>&morcFlag=<%=catNdPkArray[2]%>">&nbsp;<%=catNdPkArray[1]%>&nbsp;</a></td>                            
              <%}%>
            </logic:iterate>         
          </tr>
        </table>
      </td>
    </tr>	 
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94">		
        <!-- Tree and List Table Starts-->      
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="33%" align="center" valign="top">
              <div class="bdrColor_007C94 bgColor_DDF3FF" style="width:210px; height:380px; margin-top:12px; margin-left:15px;margin-bottom:12px;" title="" >
                <iframe src="productTreeViewAction.do?currentCategoryPk=<bean:write name="merchandiseCategoryTblPk"/>" scrolling="yes" height="380px" width="100%"  frameborder="0"></iframe>
              </div>
            </td>
            <td width="67%" valign="top" height="393px" colspan="3">
              <!--List Table Starts-->            
              <table width="485px"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td height="10px" colspan="3"></td>
                </tr>
                
                <% int count=0;%>
                <logic:iterate id="product" name="products" indexId="gIndex">
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
                      <!-- Product/Category Table Starts -->                        
                      <table width="135px" border="0" cellpadding=1 cellspacing=1 class="bdrColor_007C94 bgColor_DDF3FF">
                        <tr>
                          <td  align="right" class="text_007C94"><div align="center"><bean:write   name="product" property="productName"  /></div></td>
                        </tr>
                        <tr>
                        
                        <logic:equal name="product" property="productOrCategoryFlag" value="0"><!-- Merchandise -->
                          <td  align="center">
                            <a href="productAction.do?merchandiseTblPk=<bean:write name="product" property="morcTblPk" />&merchandiseCategoryTblPk=<bean:write name="merchandiseCategoryTblPk"/>&morcFlag=<bean:write name="product" property="morcFlag" />&pageNumber=<bean:write name="pageNumber" />">
                              <img border="0" class="bdrColor_007C94" name="display" id="display" alt="<bean:write name="product" property="productName" />"  style="width:100px; height:100px; background-color:#FFFFFF" title="See Details..."  src="imageDisplayAction.do?dataSourceKey=BOKey&imageOid=<bean:write name="product" property="productOId" />&imageContentType=<bean:write name="product" property="productContentType" />&imageContentSize=<bean:write name="product" property="productContentSize" />" ></img>
                            </a> 
                          </td>
                        </logic:equal>
                        <logic:equal name="product" property="productOrCategoryFlag" value="1"><!-- Merchandise Category-->
                          <td  align="center">
                            <a href="productListAction.do?morcTblPk=<bean:write name="product" property="morcTblPk" />&morcFlag=<bean:write name="product" property="morcFlag" />">
                              <img border="0" class="bdrColor_007C94" name="display" id="display" alt="<bean:write name="product" property="productName" />"  style="width:100px; height:100px; background-color:#FFFFFF" title="Category"  src="imageDisplayAction.do?dataSourceKey=BOKey&imageOid=<bean:write name="product" property="productOId" />&imageContentType=<bean:write name="product" property="productContentType" />&imageContentSize=<bean:write name="product" property="productContentSize" />" ></img>
                            </a> 
                          </td>
                        </logic:equal>
                        </tr>
                        <tr>
                          <logic:equal name="product" property="productOrCategoryFlag" value="0"><!-- Merchanise  -->
                            <td align="center" >
                              <span class="text_normal10" ><b><bean:message key="img.Colors" /> :</b></span>
                              <span class="text_007C94"><bean:write name="product" property="noOfItemsOrColors" /></span>
                            </td>                            
                          </logic:equal>
                          <logic:equal name="product" property="productOrCategoryFlag" value="1"><!-- Merchanise Category -->
                            <td align="center" >
                              <span class="text_normal10"><b><bean:message key="img.NoOfItems" /> :</b></span>
                              <span class="text_007C94"><bean:write name="product" property="noOfItemsOrColors" /></span>
                            </td>                            
                          </logic:equal>
                        </tr>
                        <tr>
                          <td align="center" class="text_normal10" height="16px"><bean:write name="product" property="productDescription" /> </td>
                        </tr>
                        <tr>
                          <td align="center" height="16px">
                            <logic:equal name="product" property="productOrCategoryFlag"  value="0">
                              <logic:equal name="customerInfo" property="customerRegistered"  value="1">    
                                <img src="images/btn_add2fav.gif" width="105px" height="16px" border="0" onclick="return btnAddFav_onclick(<bean:write name="product" property="morcTblPk" />);" style="cursor:pointer; cursor:hand;">
                              </logic:equal>
                            </logic:equal>
                          </td>
                        </tr>
                      </table>
                      <!-- Product/Category Table Ends -->
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
          <!-- List Table Ends-->
          
          <tr>
            <td class="bdrTopColor_007C94" height="10px" colspan="3" valign="top">
              <table width="100%"    border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td align="right" class="bdrTopColor_006600" height="10px" colspan="3" valign="top">
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
                            <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>                      
                      <%if ((endCounter)<=pageCount){%>
                        <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                      <%}%>                                           
                      <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                    <%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
                      <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=1%>">First(1)</a>
                      <%if(!((startCounter-1)<=0)){%>
                        <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                      <%}%>
                      <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                          <%if(counter==pageNumber){%>
                            <span class="linkPageNavCurrentPage"><%=counter%></span>
                          <%}else{%>
                            <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>
                    <%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
                      <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=1%>">First(1)</a>
                      <%if(!((startCounter-1)<=0)){%>
                        <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
                      <%}%>
                      <%for(int counter=startCounter; counter<endCounter; counter++){ %>
                          <%if(counter==pageNumber){%>
                            <span class="linkPageNavCurrentPage"><%=counter%></span>
                          <%}else{%>
                            <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=counter%>"><%=counter%></a>
                          <%}%>
                      <%}%>
                      <%if ((endCounter)<=pageCount){%>
                        <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
                      <%}%>                                           
                      <a class="linkPageNav" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk" />&morcFlag=<bean:write name="morcFlag" />&pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
                    <%}%>
                    &nbsp;&nbsp;
                    <!-- Navigation Layer Ends -->
                  </td>                  
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!-- Tree and List Table Ends-->      
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/catalogue_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/catalogue_pan_tile3px.gif"></td>
      <td><img src="images/catalogue_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</body>
</html>
