<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="productDetail" name="productDetail" />
<bean:define id="productColors" name="productDetail" property="colors"/>
<html>
<head>
<title><bean:message key="title.ProductDetails" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="catalogue.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<SCRIPT LANGUAGE=javascript>
<!--
function  btnAddFav_onclick(){
	openWindow('addToFavouriteB4Action.do?merchandiseTblPk=<bean:write name="productDetail" property="merchandiseTblPk" />','add2fav',350,200,0,0,true,'');
}
var lastVisibleLyrId=null;
function radioClick(me){

  MM_showHideLayers(me.alt,'','show');
  if (lastVisibleLyrId!=me.alt && lastVisibleLyrId!=null){
    MM_showHideLayers(lastVisibleLyrId,'','hide');
  }  
  lastVisibleLyrId=me.alt;
}

function window_onload(){
  var thisForm=document.forms[0];
  var objRadioColor=thisForm.radColor ;
  if(typeof objRadioColor.length != 'undefined' ){
    objRadioColor=objRadioColor[0];
  }
  objRadioColor.checked=true;
  radioClick(objRadioColor);
}

function btnCustomise_onclick(){
  var thisForm=document.forms[0];
  var objRadioColor=thisForm.radColor ; 
  
  var merchandiseColorTblPk=null;
  if(typeof objRadioColor.length != 'undefined' ){
    for(var count=0; count<objRadioColor.length; count++ ) {
      if (objRadioColor[count].checked){
        merchandiseColorTblPk=objRadioColor[count].value;
        break;
      }
    }
  }else{
    merchandiseColorTblPk=objRadioColor.value;
  }
  
  var studioFrameSrc=escape('productCustomiseB4Action.do?merchandiseCategoryTblPk=<bean:write name="merchandiseCategoryTblPk"/>&merchandiseTblPk=<bean:write name="productDetail" property="merchandiseTblPk"/>&merchandiseColorTblPk='+ merchandiseColorTblPk);
  window.top.location='studioAction.do?studioFrameSrc='+studioFrameSrc;
}
//-->
</SCRIPT>
</head>
<body onload="return window_onload();">
  <form id="form1" name="form1" action="" >
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="24px" height="17px"><img src="images/catalogue_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/catalogue_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ProductDetails" /> :</td>
      <td width="5px"><img src="images/catalogue_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94 bdrTopColor_007C94 bdrBottomColor_007C94">
        &nbsp;<a class="linkMenu" href="productListAction.do?morcTblPk=<bean:write name="merchandiseCategoryTblPk"/>&morcFlag=<bean:write name="morcFlag" />&pageNumber=<bean:write name="pageNumber" />">&nbsp;&lt; <bean:message key="nav.Back" />&nbsp;</a>
      </td>
  	</tr>
	  <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94 bdrBottomColor_007C94">        
        <!-- Product , Size and Color Container Table Starts-->
        <table width="100%"  border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td valign="top">	
              <!-- Product Table Starts-->
              <table width="450"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td width="100%" height="20px" class="text_007C94"><div align="center"><bean:write name="productDetail" property="name" /></div></td>
                </tr>
                <tr>
                  <td height="20px">
                      <div align="center">
                        <img name="display" id="display" class="bdrColor_007C94" border="0" style="width:200px; height:200px; background-color:#FFFFFF" src="imageDisplayAction.do?dataSourceKey=BOKey&imageOid=<bean:write name="productDetail" property="productOId"/>&imageContentType=<bean:write name="productDetail" property="productContentType"/>&imageContentSize=<bean:write name="productDetail" property="productContentSize"/>" ></img>
                      </div>
                  </td>
                </tr>
                <tr>
                  <td valign="top">
                    <div align="center">
                      <logic:equal name="customerInfo" property="customerRegistered"  value="1">   
                        <html:button styleClass="buttons" style="width:100px; height:20px"  property="AddToFavorite" onclick="return btnAddFav_onclick();" ><bean:message key="btn.AddToFavorite" /></html:button>
                      </logic:equal>  
                      <html:button styleClass="buttons" style="width:100px; height:20px"  property="Customize" onclick="return btnCustomise_onclick();" ><bean:message key="btn.Customize" /></html:button>
                    </div>
                  </td>
                </tr>
              </table>
              <!-- Product Table Ends -->
            </td>
            <td align="right" valign="top">
              <!-- Color And Size Container Table Starts-->
              <table width="255px"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td height="10px">
                    <!-- Color Table Starts-->      
                    <table width="250px"  border="0" cellspacing="1" cellpadding="1" class="bgColor_007C94">
                      <tr>
                        <td class="th_01A0BF" colspan="3"><div align="center"><bean:message key="tbl.Colors" /></div></td>
                      </tr>
                      <logic:iterate id="productColor" name="productColors">
                        <tr bgcolor="#FFFFFF">
                          <td width="10%"><input id="radColor" name="radColor" onclick="return radioClick(this);" type="radio" alt="lyr<bean:write name="productColor" property="merchandiseColorTblPk" />" value="<bean:write name="productColor" property="merchandiseColorTblPk" />" ></td>
                          <td width="14%">
                            <div style="height:18px;width:36px;align:center" class="bdrColor_007C94">
                              <logic:notEqual  name="productColor" property="color2Name" value="">
                                <div style="height:18px;float:left;width:18px;background-color:<bean:write name="productColor" property="color1Value" />;"></div>
                                <div style="height:18px;float:left;width:18px;background-color:<bean:write name="productColor" property="color2Value" />;"></div>
                              </logic:notEqual>
                              <logic:equal  name="productColor" property="color2Name" value="">
                                <div style="height:18px;float:left;width:36px;background-color:<bean:write name="productColor" property="color1Value" />;"></div>
                              </logic:equal>
                            </div>
                          </td>
                          <td width="76%">
                            <bean:write name="productColor" property="color1Name" />
                            <logic:notEqual  name="productColor" property="color2Name" value="">
                              /<bean:write name="productColor" property="color2Name" />
                            </logic:notEqual>
                          </td>
                        </tr>
                      </logic:iterate>                      
                    </table>
                    <!-- Color Table Ends-->      
                  </td>
                </tr>
                <tr>
                  <td height="7px"></td>
                </tr>
                <tr>
                  <td height="20px" >
                    <logic:iterate id="productColor" name="productColors">
                      <!-- Size Table Starts-->
                      <div style="position:relative;display:none;" id="lyr<bean:write name="productColor" property="merchandiseColorTblPk" />" name="lyr<bean:write name="productColor" property="merchandiseColorTblPk" />">
                        <table width="250px"  border="0" cellspacing="1" cellpadding="2" class="bgColor_007C94">
                          <tr>
                            <td class="th_01A0BF" width="33%"><div align="center"><bean:message key="tbl.Size" /></div></td>
                            <td class="th_01A0BF" width="36%"><div align="center"><bean:message key="tbl.Price/Quantity" /></div></td>
                            <td class="th_01A0BF" width="31%"><div align="center"><bean:message key="tbl.Discount" /></div></td>
                          </tr>
                          <bean:define id="productSizes" name="productColor" property="sizes"/>
                          <logic:iterate id="productSize" name="productSizes">
                          <tr bgcolor="#FFFFFF">
                            <td><bean:write name="productSize" property="sizeTypeId" /> <bean:write name="productSize" property="sizeId" /></td>
                            <td><div align="right">Rs. <bean:write name="productSize" property="price" />/-</div></td>
                            <td><div align="center"><a class="linkTableRow" href="#">...</a></div></td>
                          </tr>
                          </logic:iterate>
                        </table>
                      </div>
                      <!-- Size Table Ends-->
                    </logic:iterate>
                  </td>
                </tr>
              </table>
              <!-- Color And Size Container Table Ends-->
            </td>
          </tr>
        </table>
        <!-- Product , Size and Color Container Table Ends-->
        <!-- Product Description and Details Table Starts-->
        <table width="98%"  border="0" align="center" cellpadding="2" cellspacing="1" class="bdrTopColor_007C94">
          <tr>
            <td width="20%" valign="top" class="text_007C94"><div align="right"><bean:message key="info.ProductDescription" />:</div></td>
            <td width="80%"><bean:write name="productDetail" property="description" /></td>
          </tr>
          <tr>
            <td valign="top" class="text_007C94"><div align="right"><bean:message key="info.MaterialDetail" />:</div></td>
            <td><bean:write name="productDetail" property="materialDetail" /></td>
          </tr>
          <tr>
            <td valign="top" class="text_007C94"><div align="right"><bean:message key="info.DeliveryNote" />:</div></td>
            <td><bean:write name="productDetail" property="deliveryNote" /></td>
          </tr>
          <tr>
            <td valign="top" class="text_007C94"><div align="right"><bean:message key="info.MinimumQuantity" />:</div></td>
            <td><bean:write name="productDetail" property="minQty" /></td>
          </tr>
          <tr>
            <td valign="top" class="text_007C94"><div align="right"><bean:message key="info.Comment" />:</div></td>
            <td><bean:write name="productDetail" property="comment" /></td>
          </tr>
        </table>
        <!-- Product Description and Details Table Ends-->
      </td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94">&nbsp;</td>
    </tr>
    <tr>
      <td height="3px"><img src="images/catalogue_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/catalogue_pan_tile3px.gif" bgcolor="#FFFFFF"></td>
      <td><img src="images/catalogue_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</form>
</body>
</html>
