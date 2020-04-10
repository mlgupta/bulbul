<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
<head>
<title><bean:message key="title.DesignEngine" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">

<script language="javascript" type="text/javascript" src="general.js"></script>
<script language="JavaScript" type="text/JavaScript" src="navigationbar.js" ></script>
<script language="JavaScript" type="text/JavaScript" src="studio_color_picker.js" ></script>

<script language="JavaScript" type="text/JavaScript" >
  
  var clipartNavBar=new NavigationBar("clipartNavBar");
  clipartNavBar.setPageNumber(1);
  clipartNavBar.setPageCount(1);
  clipartNavBar.className="bdrColor_CC6A00";
  clipartNavBar.onclick="clipartPageOnClick()";

  clipartNavBar.msgPageNotExist="<bean:message  key="page.pageNumberNotExists" />";
  clipartNavBar.msgNumberOnly="<bean:message   key="page.enterNumbersOnly"/>";    
  clipartNavBar.msgEnterPageNo="<bean:message   key="page.enterPageNo"/>";
  clipartNavBar.msgOnFirst="<bean:message    key="page.onFirst"/>";
  clipartNavBar.msgOnLast="<bean:message    key="page.onLast"/>";

  clipartNavBar.lblPage="<bean:message    key="lbl.Page"/>";
  clipartNavBar.lblOf="<bean:message    key="lbl.Of"/>";

  clipartNavBar.tooltipPageNo="<bean:message    key="tooltips.PageNo"/>";
  clipartNavBar.tooltipFirst="<bean:message    key="tooltips.First"/>";
  clipartNavBar.tooltipNext="<bean:message    key="tooltips.Next"/>";
  clipartNavBar.tooltipPrev="<bean:message    key="tooltips.Previous"/>";
  clipartNavBar.tooltipLast="<bean:message    key="tooltips.Last"/>";
  clipartNavBar.tooltipGo="<bean:message    key="btn.Go"/>";
  
  var fontNavBar=new NavigationBar("fontNavBar");
  fontNavBar.setPageNumber(1);
  fontNavBar.setPageCount(1);
  fontNavBar.className="bdrColor_CC6A00";
  fontNavBar.onclick="fontPageOnClick()";

  fontNavBar.msgPageNotExist="<bean:message  key="page.pageNumberNotExists" />";
  fontNavBar.msgNumberOnly="<bean:message   key="page.enterNumbersOnly"/>";    
  fontNavBar.msgEnterPageNo="<bean:message   key="page.enterPageNo"/>";
  fontNavBar.msgOnFirst="<bean:message    key="page.onFirst"/>";
  fontNavBar.msgOnLast="<bean:message    key="page.onLast"/>";

  fontNavBar.lblPage="<bean:message    key="lbl.Page"/>";
  fontNavBar.lblOf="<bean:message    key="lbl.Of"/>";

  fontNavBar.tooltipPageNo="<bean:message    key="tooltips.PageNo"/>";
  fontNavBar.tooltipFirst="<bean:message    key="tooltips.First"/>";
  fontNavBar.tooltipNext="<bean:message    key="tooltips.Next"/>";
  fontNavBar.tooltipPrev="<bean:message    key="tooltips.Previous"/>";
  fontNavBar.tooltipLast="<bean:message    key="tooltips.Last"/>";
  fontNavBar.tooltipGo="<bean:message    key="btn.Go"/>";

</script>

<script language="javascript" type="text/javascript">
  var clipartStrokeColorPicker = new ColorPicker('clipartStrokeColorPicker',100,200);
  clipartStrokeColorPicker.pickerClassName='bdrColor_CC6600';
  clipartStrokeColorPicker.onclick='setStroke(clipartStrokeColorPicker.selectedColor)';
  
  
  var clipartFillColorPicker = new ColorPicker('clipartFillColorPicker',100,200);
  clipartFillColorPicker.pickerClassName='bdrColor_CC6600';
  clipartFillColorPicker.onclick='setFill(clipartFillColorPicker.selectedColor)';
  
  
  var textStrokeColorPicker = new ColorPicker('textStrokeColorPicker',100,200);
  textStrokeColorPicker.pickerClassName='bdrColor_CC6600';
  textStrokeColorPicker.onclick='setStroke(textStrokeColorPicker.selectedColor)';
  
  
  var textFillColorPicker = new ColorPicker('textFillColorPicker',100,200);
  textFillColorPicker.pickerClassName='bdrColor_CC6600';
  textFillColorPicker.onclick='setFill(textFillColorPicker.selectedColor)';
  
</script>
    
<script language="javascript" type="text/javascript">

//Global Variables

//Common variables
var selectedTab=null;
var lastSelectedTab=null;
var lastSelectedTabImageSrc=null;
var lastSelectedLayerName=null;
var isAppletReady=false;

var customerDesignTblPk=-1;


//Graphics variables [Image and Clipart]
var gElementWidth=60;
var gElementHeight=65;
var gSizeRatio=gElementWidth/gElementHeight;

//Product Related Variables
var currentMerchandiseCategoryTblPk=<bean:write name="merchandiseCategoryTblPk"/>;
var selectedMerchandiseTblPk=<bean:write name="merchandiseTblPk"/>;
var selectedMerchandiseColorTblPk=<bean:write name="merchandiseColorTblPk"/>;
var selectedProductImage=null;
var selectedProductColorDiv=null;

var productSelection4FirstTime=true;
var productColorSelection4FirstTime=true;


//Text Variables 
var fontSize=20; 
var selectedFontFamilyName='Arial';
var currentFontCategoryTblPk=-1;

//Clipart Related Variables
var currentClipartCategoryTblPk=-1;
var clipartSearchFlag=false;
var clipartSearchKeyword="";
var clipartTabClicked=false;

//Common Functions Starts

function getStyleProperty(styleString,propertyName){
  var propertyValue='default';
  
  //check this if statement problem with trim(styleString).length //TODO
  if(styleString!=null && (escape(trim(styleString)).length>0)){
    var styleArray=styleString.split(';');
    for(var i=0;i<styleArray.length; i++){
      var propertyArray=styleArray[i].split(':');
      if (trim(propertyArray[0])==trim(propertyName)){
        propertyValue=propertyArray[1];
        break;
      }
    }
  }
  return propertyValue;
}

function window_onload(){
  initalize();
}

function initalize(){
  // To handle Keyboard commands
  document.onkeydown=documentOnKeyDown;
  window.parent.document.onkeydown=documentOnKeyDown;
  
  // To Preload Require Images 
  MM_preloadImages(
  'images/tab_choose.gif','images/tab_choose_active.gif',
  'images/tab_art.gif','images/tab_art_active.gif',
  'images/tab_image.gif','images/tab_image_active.gif',
  'images/tab_text.gif','images/tab_text_active.gif',
  'images/tab_save.gif','images/tab_save_active.gif',
  'images/tab_cart.gif','images/tab_cart_active.gif',
  'images/tab_buy.gif','images/tab_buy_active.gif',
  'images/tab_open.gif','images/tab_open_active.gif',
  'images/indicator.gif','images/default_color.gif'
  );
  
  // To Select initial tabs                   
  selectedTab=MM_findObj('tabProduct');
  lastSelectedTab=selectedTab;
  lastSelectedLayerName='productList';
  lastSelectedTabImageSrc='images/tab_choose.gif'
  
}

function initializeProductList(){
  // To List Products
  var productsQueryString='merchandiseTblPk='+escape(selectedMerchandiseTblPk);
  ajaxRequest('POST','merchandiseListAction.do',true,productsQueryString,displayProducts,true);
}

function documentOnKeyDown(e){

  var key;
	var keychar;
  var sourceElement=null;
	if (window.event){
	   key = window.event.keyCode;
     sourceElement=window.event.srcElement;
	}else if (e){
	   key = e.which;
     sourceElement=e.target;
	}else{
	   return true;
  }
	keychar = String.fromCharCode(key);
  
  if((typeof(sourceElement.type))== 'undefined'){
    if(key==46){ // Handle Delete Key
      deleteElement();
    }
    //Handle Other Keys
  }
  
}

function imageMouseOver(obj){
  obj.className='bdrColor_CC6A00'
}

function imageMouseOut(obj){
  obj.className='bdrColor_E8E8E8';
  
}

function tabOnClick(obj,selectedLayerName,activeImage, inActiveImage){
  if(isAppletReady && selectedTab!=obj){
    lastSelectedTab=selectedTab;
    selectedTab=obj;
    selectedTab.src=activeImage;
    selectedTab.style.cursor='default';
    if(lastSelectedTab!=null){
      lastSelectedTab.style.cursor='hand';
      lastSelectedTab.style.cursor='pointer';
      lastSelectedTab.src=lastSelectedTabImageSrc;
    }
    MM_showHideLayers(lastSelectedLayerName,'','hide',selectedLayerName,'','show');
    lastSelectedLayerName=selectedLayerName;  
    lastSelectedTabImageSrc=inActiveImage;
    document.applets[0].refreshPrintableArea();
  }
  
}

function zoomOnChange(zoomObject){
  document.applets[0].renderZoom(zoomObject.value);
}

function selectViewOnChange(selectedVeiwObject){
  var thisForm=selectedVeiwObject.form;
  document.applets[0].selectePrintableArea(selectedVeiwObject.value);
  thisForm.cboZoom.value=document.applets[0].getZoomScale(selectedVeiwObject.value);
}

function setTextSize(textSize){
  document.applets[0].setProperties("font-size:"+textSize+";");
}

function setFill(fill){
  document.applets[0].setProperties("fill:"+fill+";");
}

function setStroke(stroke){
  document.applets[0].setProperties("stroke:"+stroke+";");
}

function setStrokeWidth(strokeWidth){
  document.applets[0].setProperties("stroke-width:"+strokeWidth+";");
}

function setRotate(rotate){
  document.applets[0].setRotate(parseFloat(rotate));
}

function setFontFamily(fontFamily){
  document.applets[0].setProperties("font-family:"+fontFamily+";");
}

function deleteElement(){
  document.applets[0].deleteElement();
}

function deleteElementServer(customerDesignDetailTblPk,elementId){
  var deleteElementQueryString='customerDesignDetailTblPk='+escape(customerDesignDetailTblPk);
  deleteElementQueryString+='&elementId='+escape(elementId);
  ajaxRequest('POST','deleteDesignElementAction.do',true,deleteElementQueryString,deleteElementServerReply,true);
}

function deleteElementServerReply(httpRequest){
  var objStudioTracking=MM_findObj('studioTracking');
  objStudioTracking.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var deleteElementNodes=xmldoc.getElementsByTagName('delete_element');
      var result=false;
      if(deleteElementNodes.length==1){
        var deleteElementNode=deleteElementNodes.item(0);
        var resultNodes = trim(deleteElementNode.getElementsByTagName('result'));
        if(resultNodes.length==1){
          result=eval(resultNodes.item(0).childNodes[0].nodeValue);
        }
      }
      if(result){
        //alert('Element Deleted Sucessfully');
      }else{
        //alert('Element Deletion Not Sucessful');
      }
      
      
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Element Deleting',1);
      objStudioTracking.appendChild(statusObj);
  }
}


function getLockedWidth(width,height,locked){
  width=parseInt(width);
  height=parseInt(height);
  if(locked){
    var tempWidth = parseInt(height * gSizeRatio);
    if (tempWidth > 0) {
      width = tempWidth;
    }
  }
  return width;
}

function getLockedHeight(width,height,locked){
  if(locked){
    var tempHeight = parseInt(width / gSizeRatio);
    if (tempHeight > 0) {
      height = tempHeight;
    }
  }
  return height;
}

function getStatusDisplay(textString,alignValue){
  var objDiv=document.createElement('DIV');
  objDiv.innerHTML=textString+'...';
  objDiv.className='text_008000';
  objDiv.style.fontStyle='italic';
  objDiv.style.marginLeft='5px';
  
  var objImage=document.createElement('IMG');
  objImage.src='images/indicator.gif';
  
  var objTable=document.createElement('TABLE');
  objTable.border='0';
  objTable.cellSpacing='0';
  objTable.cellPadding='0';
  objTable.style.align='center';
  
  var objTr=objTable.insertRow(0);
  var objTd=objTr.insertCell(0);
  objTd.align='center';
  objTd.appendChild(objImage);
  if(alignValue==0){
    objTr=objTable.insertRow(1);
    objTd=objTr.insertCell(0);
  }else{
    objTd=objTr.insertCell(1);
  }
  objTd.align='center';
  objTd.appendChild(objDiv);
  
  return objTable;
}

function studioProcessing(processing){
  var objStudioTracking=MM_findObj('studioTracking');
  objStudioTracking.innerHTML='';
  if(eval(processing)==true){
    var statusObj=getStatusDisplay('Processing',1);
    objStudioTracking.appendChild(statusObj);
  }
}

//Common Functions Ends



//Product Related Functions Starts

function displayProducts(httpRequest){
  MM_showHideLayers('productDisplay','','show','productCategoryDisplay','','hide');   
  var objProductDisplay=MM_findObj('productDisplay');
  objProductDisplay.innerHTML='';
  
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var merchandiseNodes = xmldoc.getElementsByTagName('merchandise'); 
      for(var productIndex=0;productIndex<merchandiseNodes.length;productIndex++){
        var merchandiseNode=merchandiseNodes.item(productIndex);
        var objProductTable=document.createElement('TABLE');
        objProductTable.border='0';
        objProductTable.cellSpacing='1';
        objProductTable.cellPadding='0';
        objProductTable.width='190px'
        objProductTable.style.align='center';
        var noOfProductTableRows=3;
        for(var rowIndex=0; rowIndex<noOfProductTableRows; rowIndex++){
          var objTr=objProductTable.insertRow(rowIndex);
          objTr.insertCell(0);
        }
        
        if(productIndex==0){
          
          var merchandiseCategoryTblPk=merchandiseNode.getElementsByTagName('categorypk')[0].childNodes[0].nodeValue;
          if(currentMerchandiseCategoryTblPk!=-1){
            merchandiseCategoryTblPk=currentMerchandiseCategoryTblPk;
          }
          //Product Category Navigation Listing
          var productCategoryNavListQueryString='merchandiseCategoryTblPk='+escape(merchandiseCategoryTblPk);
          ajaxRequest('POST','productCategoryNavigationListAction.do',true,productCategoryNavListQueryString,createProductCategoryLink,true);
          
        }
        
        var merchandiseTblPk=merchandiseNode.getElementsByTagName('pk')[0].childNodes[0].nodeValue;
        
        //Product Name
        
        var nameCell=objProductTable.rows[0].cells[0];
        nameCell.className='text_008000';
        nameCell.innerHTML=merchandiseNode.getElementsByTagName('name')[0].childNodes[0].nodeValue;
        
        //Horizontal Line
        var objHorizontalDiv=document.createElement('DIV');
        objHorizontalDiv.style.fontSize='0px';
        objHorizontalDiv.style.width='100%';
        objHorizontalDiv.style.height='1px';
        objHorizontalDiv.className='bdrTopColor_CC6A00';
        
        var horzinatalLineCell=objProductTable.rows[1].cells[0];
        horzinatalLineCell.appendChild(objHorizontalDiv);
        
        // Product Details
        var objProductDetailTable=document.createElement('TABLE');
        objProductDetailTable.border='0';
        objProductDetailTable.cellSpacing='1';
        objProductDetailTable.cellPadding='0';
        objProductDetailTable.width='100%'
        objProductDetailTable.style.align='center';

        var objTr=objProductDetailTable.insertRow(0);
        objTr.insertCell(0);
        objTr.insertCell(1);

        //Product Image 
        var objProductImage=document.createElement('IMG');

        objProductImage.className='bdrColor_E8E8E8';
        objProductImage.style.cursor='hand';
        objProductImage.style.cursor='pointer';
        objProductImage.pk=merchandiseTblPk;
        objProductImage.onmouseover=function(){
          if(this.pk!=selectedMerchandiseTblPk){
            imageMouseOver(this);
          }
        };
        objProductImage.onmouseout=function(){
          if(this.pk!=selectedMerchandiseTblPk){
            imageMouseOut(this);
          }
        };
        objProductImage.onclick=function(){
          if(this.pk!=selectedMerchandiseTblPk){
            productSelectOnClick(this);
            this.colorObj.onclick();
          }
        };

        objProductImage.style.width="95px";
        objProductImage.style.height="105px";
        
        var imageOid=merchandiseNode.getElementsByTagName('contentid')[0].childNodes[0].nodeValue;
        var imageContentType=merchandiseNode.getElementsByTagName('contenttype')[0].childNodes[0].nodeValue;
        var imageContentSize=merchandiseNode.getElementsByTagName('contentsize')[0].childNodes[0].nodeValue;
        var imageSrc='imageDisplayAction.do';
        imageSrc+='?dataSourceKey=BOKey';
        imageSrc+='&imageOid='+imageOid;
        imageSrc+='&imageContentType='+imageContentType;
        imageSrc+='&imageContentSize='+imageContentSize;
        objProductImage.src=imageSrc;
        
        //Prodcut Sub Details
        var objProductSubDetailTable=document.createElement('TABLE');
        objProductSubDetailTable.border='0';
        objProductSubDetailTable.cellSpacing='1';
        objProductSubDetailTable.cellPadding='0';
        objProductSubDetailTable.width='100%'
        objProductTable.style.align='center';
        var noOfProductSubDetailTableRows=3;
        for(var rowIndex=0; rowIndex<noOfProductSubDetailTableRows; rowIndex++){
          var objTr=objProductSubDetailTable.insertRow(rowIndex);
          objTr.insertCell(0);
        }     
        
        //Description
        var productDescriptionNode=merchandiseNode.getElementsByTagName('desc')[0]; 
        var descCell=objProductSubDetailTable.rows[0].cells[0];
        descCell.className='text_CC6600';
        if(productDescriptionNode.childNodes.length>0){
          descCell.innerHTML=productDescriptionNode.childNodes[0].nodeValue;
        }
        
        // Delivery Note
        var productDeliveryNoteNode=merchandiseNode.getElementsByTagName('note')[0];
        var noteCell=objProductSubDetailTable.rows[1].cells[0];
        noteCell.className='text_CC6600';
        if(productDeliveryNoteNode.childNodes.length>0){
          noteCell.innerHTML=productDeliveryNoteNode.childNodes[0].nodeValue;
        }
        
        // Colors
        var objColorTable=document.createElement('TABLE');
        objColorTable.border='0';
        objColorTable.cellSpacing='2';
        objColorTable.cellPadding='0';
        objColorTable.style.align='center';
        var colorNodes=merchandiseNode.getElementsByTagName('color');
        
        for (var colorNodeIndex=0;colorNodeIndex<colorNodes.length;colorNodeIndex++){
          var colorNode=colorNodes.item(colorNodeIndex);
          
          var merchandiseColorTblPk=colorNode.getElementsByTagName('pk')[0].childNodes[0].nodeValue;
          
          var objColorDiv=document.createElement('DIV');
          objColorDiv.className='bdrColor_E8E8E8';
          objColorDiv.style.width='20px';
          objColorDiv.style.height='18px';
          objColorDiv.pk=merchandiseColorTblPk;
          objColorDiv.productImageObj=objProductImage;
          
          objColorDiv.onmouseover=function(){
            if(this.pk!=selectedMerchandiseColorTblPk){
              imageMouseOver(this);
            }
          };
          objColorDiv.onmouseout=function(){
            if(this.pk!=selectedMerchandiseColorTblPk){
              imageMouseOut(this);
            }
          };
          objColorDiv.onclick=function(){
            if(this.pk!=selectedMerchandiseColorTblPk){
              productColorSelectOnClick(this);
              productSelectOnClick(this.productImageObj);
            }
          };
          
          if(colorNodeIndex==0){
            objProductImage.colorObj=objColorDiv;
          }
          
          //Selecting Product Color
          if(productColorSelection4FirstTime){
            if (selectedMerchandiseColorTblPk==-1){
              if(colorNodeIndex==0){
                productColorSelectOnClick(objColorDiv);
                productColorSelection4FirstTime=false
              }
            }else{
              if(selectedMerchandiseColorTblPk==merchandiseColorTblPk){
                productColorSelection4FirstTime=false
                productColorSelectOnClick(objColorDiv);
              }
            }
          }
                  
          var colorOneName=colorNode.getElementsByTagName('one')[0].attributes.item(0).value;
          var colorOneValue=colorNode.getElementsByTagName('one')[0].attributes.item(1).value;
          
          var colorTwoName=colorNode.getElementsByTagName('two')[0].attributes.item(0).value;
          var colorTwoValue=colorNode.getElementsByTagName('two')[0].attributes.item(1).value;
        
          if(colorTwoValue.length>0){
            var objColorOneDiv=document.createElement('DIV');
            objColorOneDiv.style.fontSize='0px';
            objColorOneDiv.style.width='20px';
            objColorOneDiv.style.height='9px';
            objColorOneDiv.style.backgroundColor=colorOneValue;
            
            var objColorTwoDiv=document.createElement('DIV');
            objColorTwoDiv.style.fontSize='0px';
            objColorTwoDiv.style.width='20px';
            objColorTwoDiv.style.height='9px';
            objColorTwoDiv.style.backgroundColor=colorTwoValue;
            
            objColorDiv.appendChild(objColorOneDiv);
            objColorDiv.appendChild(objColorTwoDiv);
            
            objColorDiv.title=colorOneName +'/' + colorTwoName;
            
          }else{
            var objColorOneDiv=document.createElement('DIV');
            objColorOneDiv.style.fontSize='0px';
            objColorOneDiv.style.width='20px';
            objColorOneDiv.style.height='18px';
            objColorOneDiv.style.float='left';
            objColorOneDiv.style.backgroundColor=colorOneValue;
                        
            objColorDiv.appendChild(objColorOneDiv);
            objColorDiv.title=colorOneName; 
          }
          
          var sizeString=" ";
          var sizeNodes=colorNode.getElementsByTagName('size');
          
          for(var sizeNodeIndex=0;sizeNodeIndex<sizeNodes.length;sizeNodeIndex++){
            var sizeNode=sizeNodes.item(sizeNodeIndex);
            sizeString+=sizeNode.getElementsByTagName('id')[0].childNodes[0].nodeValue;
            if(sizeNodeIndex<sizeNodes.length-1){
              sizeString+=" , " ;
            }
          }
          
          
          var objTr=objColorTable.insertRow(colorNodeIndex);
          
          var objColorTd=objTr.insertCell(0);
          objColorTd.appendChild(objColorDiv);
          
          var objSizeTd=objTr.insertCell(1);
          objSizeTd.className='text_CC6600';
          objSizeTd.innerHTML=sizeString;
                    
        }
        
        var colorCell=objProductSubDetailTable.rows[2].cells[0];
        colorCell.appendChild(objColorTable)
        
//        //Comment
//        var commentLableCell=objProductDetailTable.rows[2].cells[0];
//        var commentCell=objProductDetailTable.rows[2].cells[1];
//        commentLableCell.innerHTML='Comment';
//        commentCell.innerHTML=merchandiseNode.getElementsByTagName('comment')[0].childNodes[0].nodeValue;
//        
//        // Material
//        var materialLableCell=objProductDetailTable.rows(3).cells(0);
//        var materialCell=objProductDetailTable.rows(3).cells(1);
//        materialLableCell.innerHTML='Material';
//        materialCell.innerHTML=merchandiseNode.getElementsByTagName('material')[0].childNodes[0].nodeValue;
//        

//        // Minimum Quantity
//        var qtyLableCell=objProductDetailTable.rows[5].cells[0];
//        var qtyCell=objProductDetailTable.rows[5].cells[1];
//        qtyLableCell.innerHTML='Min. Qty.';
//        qtyCell.innerHTML=merchandiseNode.getElementsByTagName('qty')[0].childNodes[0].nodeValue;
//        
        var imageCell=objProductDetailTable.rows[0].cells[0];
        imageCell.width='100px';
        imageCell.align='center';
        imageCell.appendChild(objProductImage);
        
        //Selecting Product
        if(productSelection4FirstTime){
          if (selectedMerchandiseTblPk==-1){
            if(productIndex==0){              
              productSelection4FirstTime=false
              productSelectOnClick(objProductImage);
            }
          }else{
            if(selectedMerchandiseTblPk==merchandiseTblPk){
              productSelection4FirstTime=false
              productSelectOnClick(objProductImage);
            }
          }
        }
        
        var objProductSubDetailsCell=objProductDetailTable.rows[0].cells[1];
        objProductSubDetailsCell.align='center';
        objProductSubDetailsCell.appendChild(objProductSubDetailTable);
        
        objProductTable.rows[2].cells[0].appendChild(objProductDetailTable);
        
        objProductDisplay.appendChild(objProductTable);        
      }
      
    }else{
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  }else{
    // still not ready
    var statusObj=getStatusDisplay('Loading',0);
    objProductDisplay.appendChild(statusObj);
  }
}

function productSelectOnClick(productImage){
  if(selectedProductImage!=null){
    selectedProductImage.className='bdrColor_E8E8E8';
    selectedProductImage.style.cursor='hand';
    selectedProductImage.style.cursor='pointer';
  }
  selectedProductImage=productImage;
  selectedProductImage.className='bdrColor_00FF00';
  selectedProductImage.style.cursor='default';
  selectedMerchandiseTblPk=productImage.pk;
}

function productColorSelectOnClick(productColorDiv){
  isAppletReady=false;
  if(selectedProductColorDiv!=null){
    selectedProductColorDiv.className='bdrColor_E8E8E8';
    selectedProductColorDiv.style.cursor='hand';
    selectedProductColorDiv.style.cursor='pointer';
  }
  selectedProductColorDiv=productColorDiv;
  selectedProductColorDiv.className='bdrColor_00FF00';
  selectedProductColorDiv.style.cursor='default';
  selectedMerchandiseColorTblPk=productColorDiv.pk;
  
  //New Design If Customer Choose Different Colro Merchandise
  customerDesignTblPk=-1;
  
  var currentDesignQueryString='customerDesignTblPk='+escape(customerDesignTblPk);
  currentDesignQueryString+='&merchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
  ajaxRequest('POST','getCurrentDesignAction.do',true,currentDesignQueryString,currentDesignReply,true);
  
  var printablAreaQueryString='merchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
  ajaxRequest('POST','getPrintableAreaAction.do',true,printablAreaQueryString,generatePrintableArea,true);
  
}



function generatePrintableArea(httpRequest){
 var objStudioTracking=MM_findObj('studioTracking');
 objStudioTracking.innerHTML='';
 if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      
      // perfect!
      httpRequest.responseXML
      var xmldoc = httpRequest.responseXML;
      var printablesAreaNodes = xmldoc.getElementsByTagName('printablearea'); 
      var objCboSelectView=document.forms[0].cboSelectView;
      
      //Clear cboSelectView
      while(objCboSelectView.options.length>0){
        objCboSelectView.remove(0);
      }
      var uniqueNumberString='';
      var printablAreaPkString='';
      var printablAreaNameString='';
      var contentIdString='';
      var contentTypeString='';
      var contentSizeString='';
      var areaWidthString='';
      var areaHeightString='';
      var merchandisePrintablAreaPkString='';
      var customerDesignDetailTblPkString='';
      
      var stringSeperator='~';
      var dataSourceKey='BOKey'; 
      
      for(var printableAreaIndex=0;printableAreaIndex<printablesAreaNodes.length;printableAreaIndex++){
        var printableAreaNode=printablesAreaNodes.item(printableAreaIndex);
        var printablAreaPk=printableAreaNode.getElementsByTagName('papk')[0].childNodes[0].nodeValue;
        var printablAreaName=printableAreaNode.getElementsByTagName('name')[0].childNodes[0].nodeValue;
        var contentId=printableAreaNode.getElementsByTagName('contentid')[0].childNodes[0].nodeValue;
        var contentType=printableAreaNode.getElementsByTagName('contenttype')[0].childNodes[0].nodeValue;
        var contentSize=printableAreaNode.getElementsByTagName('contentsize')[0].childNodes[0].nodeValue;
        var areaWidth=printableAreaNode.getElementsByTagName('width')[0].childNodes[0].nodeValue;
        var areaHeight=printableAreaNode.getElementsByTagName('height')[0].childNodes[0].nodeValue;
        var merchandisePrintablAreaPk=printableAreaNode.getElementsByTagName('mpapk')[0].childNodes[0].nodeValue;
        var customerDesignDetailTblPk="-1";
        var objOption=document.createElement('OPTION');
        objOption.text=printablAreaName;
        objOption.value=printableAreaIndex;
        objCboSelectView.options[objCboSelectView.options.length]=objOption;
        
        uniqueNumberString+=printableAreaIndex;
        printablAreaPkString+=printablAreaPk;
        printablAreaNameString+=printablAreaName;
        contentIdString+=contentId;
        contentTypeString+=contentType;
        contentSizeString+=contentSize;
        areaWidthString+=areaWidth;
        areaHeightString+=areaHeight;
        merchandisePrintablAreaPkString+=merchandisePrintablAreaPk;
        customerDesignDetailTblPkString+=customerDesignDetailTblPk;
        
        if(printableAreaIndex<(printablesAreaNodes.length-1)){
          uniqueNumberString+=stringSeperator;
          printablAreaPkString+=stringSeperator;
          printablAreaNameString+=stringSeperator;
          contentIdString+=stringSeperator;
          contentTypeString+=stringSeperator;
          contentSizeString+=stringSeperator;
          areaWidthString+=stringSeperator;
          areaHeightString+=stringSeperator;
          merchandisePrintablAreaPkString+=stringSeperator;
          customerDesignDetailTblPkString+=stringSeperator;
        }        
      }
      
      document.applets[0].generatePrintableArea(uniqueNumberString,
               printablAreaPkString,
               printablAreaNameString,
               contentIdString, 
               contentTypeString, 
               contentSizeString, 
               areaWidthString,
               areaHeightString,
               merchandisePrintablAreaPkString,
               customerDesignDetailTblPkString,
               stringSeperator,
               dataSourceKey
              );
    
      isAppletReady=true;
    
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
    // still not ready
    var statusObj=getStatusDisplay('Loading',1);
    objStudioTracking.appendChild(statusObj);
  }
}


function displayProductCategory(httpRequest){
  MM_showHideLayers('productCategoryDisplay','','show','productDisplay','','hide');   
  var objProductCategoryDisplay=MM_findObj('productCategoryDisplay');
  objProductCategoryDisplay.innerHTML='';
  if (httpRequest.readyState == 4){
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var categoryNodes = xmldoc.getElementsByTagName('category');
     
      
      
      //Displaying Product Catergory Link Table;
      
      for(var categoryIndex=0;categoryIndex<categoryNodes.length;categoryIndex++){
        var categoryNode=categoryNodes.item(categoryIndex);
        var objProductCategoryTable=document.createElement('TABLE');
        objProductCategoryTable.border='0';
        objProductCategoryTable.cellSpacing='1';
        objProductCategoryTable.cellPadding='0';
        objProductCategoryTable.width='180px'
        objProductCategoryTable.style.align='center';
        var noOfProductCategoryTableRows=4;
        for(var rowIndex=0; rowIndex<noOfProductCategoryTableRows; rowIndex++){
          var objTr=objProductCategoryTable.insertRow(rowIndex);
          objTr.insertCell(0);
        }
        
        var productCategoryTblPk=categoryNode.getElementsByTagName('pk')[0].childNodes[0].nodeValue;
        var productCategoryTblFk=categoryNode.getElementsByTagName('fk')[0].childNodes[0].nodeValue;
        var productCategoryName=categoryNode.getElementsByTagName('name')[0].childNodes[0].nodeValue;
        
        var productCategoryDesc='';
        
        if(categoryNode.getElementsByTagName('desc')[0].childNodes.length>0){
          productCategoryDesc=categoryNode.getElementsByTagName('desc')[0].childNodes[0].nodeValue;
        }
        
        var morc=categoryNode.getElementsByTagName('morc')[0].childNodes[0].nodeValue;
        
        var imageOid=categoryNode.getElementsByTagName('contentid')[0].childNodes[0].nodeValue;
        var imageContentType=categoryNode.getElementsByTagName('contenttype')[0].childNodes[0].nodeValue;
        var imageContentSize=categoryNode.getElementsByTagName('contentsize')[0].childNodes[0].nodeValue;
        
        //Category Name
        var nameCell=objProductCategoryTable.rows[0].cells[0];
        nameCell.className='text_008000';
        nameCell.innerHTML=productCategoryName;
        
        //Horizontal Line
        var objHorizontalDiv=document.createElement('DIV');
        objHorizontalDiv.style.fontSize='0px';
        objHorizontalDiv.style.width='100%';
        objHorizontalDiv.style.height='1px';
        objHorizontalDiv.className='bdrTopColor_CC6A00';
        
        var horzinatalLineCell=objProductCategoryTable.rows[1].cells[0];
        horzinatalLineCell.appendChild(objHorizontalDiv);
        
        //Product Category Image 
        var objProductCategoryImage=document.createElement('IMG');

        objProductCategoryImage.className='bdrColor_E8E8E8';
        objProductCategoryImage.style.cursor='hand';
        objProductCategoryImage.style.cursor='pointer';
        objProductCategoryImage.pk=productCategoryTblPk;
        objProductCategoryImage.fk=productCategoryTblFk;
        objProductCategoryImage.morc=morc;
        objProductCategoryImage.onmouseover=function(){
          imageMouseOver(this);
        };
        objProductCategoryImage.onmouseout=function(){
          imageMouseOut(this);
        };        
        objProductCategoryImage.onclick=function(){
          if(this.morc==0){  
            //Product List
            var productsQueryString='merchandiseCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','merchandiseListAction.do',true,productsQueryString,displayProducts,true);
            currentMerchandiseCategoryTblPk=this.pk;
          }else{
            //Product Category List
            var productCategoryListQueryString='merchandiseCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','productCategoryListAction.do',true,productCategoryListQueryString,displayProductCategory,true);  
            
            //Product Category Navigation Listing
            var productCategoryNavListQueryString='merchandiseCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','productCategoryNavigationListAction.do',true,productCategoryNavListQueryString,createProductCategoryLink,true);           
          }
        };

        objProductCategoryImage.style.width="95px";
        objProductCategoryImage.style.height="105px";
        
        var imageSrc='imageDisplayAction.do';
        imageSrc+='?dataSourceKey=BOKey';
        imageSrc+='&imageOid='+imageOid;
        imageSrc+='&imageContentType='+imageContentType;
        imageSrc+='&imageContentSize='+imageContentSize;
        objProductCategoryImage.src=imageSrc;
        
        var imageCell=objProductCategoryTable.rows[2].cells[0];
        imageCell.appendChild(objProductCategoryImage);
        
        //Category Description
        var descCell=objProductCategoryTable.rows[3].cells[0];
        descCell.className='text_CC6A00';
        descCell.innerHTML=productCategoryDesc;

        objProductCategoryDisplay.appendChild(objProductCategoryTable);
      }
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
    // still not ready    
    // still not ready
      var statusObj=getStatusDisplay('Loading',0);
      objProductCategoryDisplay.appendChild(statusObj);
  }
  
}

function createProductCategoryLink(httpRequest){
  var objProductCategoryLink=MM_findObj('productCategoryLink');
  objProductCategoryLink.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      httpRequest.responseXML
      var xmldoc = httpRequest.responseXML;
      var nameNodes = xmldoc.getElementsByTagName('name');
      for(var i=0;i<nameNodes.length;i++){
        var productCategory=nameNodes.item(i).childNodes[0].nodeValue;
        var productCategoryTblPk=eval(nameNodes.item(i).attributes.item(0).value);
        var morc=eval(nameNodes.item(i).attributes.item(1).value);
        var objLinkSpan=document.createElement('SPAN');
        objLinkSpan.title=productCategory;
        if(productCategory.length>7){
          productCategory=productCategory.substring(0,5)+'..';
        }
        objLinkSpan.innerHTML=productCategory;
        objLinkSpan.className='text_CC6A00';
        objLinkSpan.style.margin='2px';
        objLinkSpan.style.height='20px';
        objLinkSpan.style.verticalAlign='middle';
        if(i<nameNodes.length-1){
          objLinkSpan.style.textDecoration='underline';
          objLinkSpan.style.cursor='hand';
          objLinkSpan.style.cursor='pointer';
          objLinkSpan.pk=productCategoryTblPk;  
          objLinkSpan.morc=morc;  
          objLinkSpan.onclick=function(){            
            //Product Category List
            var productCategoryListQueryString='merchandiseCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','productCategoryListAction.do',true,productCategoryListQueryString,displayProductCategory,true);                       
            
            //Product Category Navigation List
            var productCategoryNavListQueryString='merchandiseCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','productCategoryNavigationListAction.do',true,productCategoryNavListQueryString,createProductCategoryLink,true);                       
          }
          
          objProductCategoryLink.appendChild(objLinkSpan);
          var objLinkSeperator=document.createElement('SPAN');
          objLinkSeperator.style.verticalAlign='middle';
          objLinkSeperator.innerHTML='<img src="images/link_nav_right.gif" height="18px" >';
          objProductCategoryLink.appendChild(objLinkSeperator);
        }else{
          objLinkSpan.style.fontStyle='italic';
          objProductCategoryLink.appendChild(objLinkSpan);
        }
      }
    }
  }
}
//Product Related Functions Ends


//Clipart Related Functions Starts

//Click on Clipart Tab
function clipartTabOnClick(obj){  
  if(!clipartTabClicked){
    clipartTabClicked=true;
    ajaxRequest('POST','getClipartsAction.do',true,null,displayCliparts,true); 
    ajaxRequest('POST','clipartCategoryNavigationListAction.do',true,null,createClipartCategoryLink,true);
  } 
  tabOnClick(obj,'clipartList','images/tab_art_active.gif','images/tab_art.gif'); 
}

//To Display Cliparts
function displayCliparts(httpRequest){
  var objClipartDisplay=MM_findObj('clipartDisplay');
  objClipartDisplay.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var metaNodes = xmldoc.getElementsByTagName('meta');      
      if(metaNodes.length==1){
        clipartNavBar.setPageNumber(metaNodes.item(0).attributes.item(0).value);
        clipartNavBar.setPageCount(metaNodes.item(0).attributes.item(1).value);        
        currentClipartCategoryTblPk=metaNodes.item(0).attributes.item(2).value;
        clipartSearchFlag=eval(metaNodes.item(0).attributes.item(3).value);
        clipartSearchKeyword=metaNodes.item(0).attributes.item(4).value;
      }
      clipartNavBar.refresh();
      
      document.forms[0].txtClipartSearch.value=clipartSearchKeyword;
      
      var nameNodes = xmldoc.getElementsByTagName('name');
      var objClipartTable=document.createElement('TABLE');
      objClipartTable.border='0';
      objClipartTable.cellSpacing='1';
      objClipartTable.cellPadding='0';
      objClipartTable.width='180px'
      objClipartTable.style.align='center';
      var objTr=null;
      var clipartCategoryCount=0;
      //Displaying Clipart Catergory Link Table;
      for(var i=0;i<nameNodes.length;i++){
        var clipartCategoryName=nameNodes.item(i).childNodes[0].nodeValue;
        var isClipart=eval(nameNodes.item(i).attributes.item(0).value);
        if(!isClipart){
          var clipartCategoryTblPk=nameNodes.item(i).attributes.item(1).value;
          var clipartCategoryTblFk=nameNodes.item(i).attributes.item(2).value;
          var objLinkDiv=document.createElement('DIV');
          objLinkDiv.title=clipartName;
          var displayLength=10;
          if(clipartCategoryName.length>displayLength){
            clipartCategoryName=clipartCategoryName.substring(0,displayLength-2)+'..';
          }
          objLinkDiv.innerHTML=clipartCategoryName;
          objLinkDiv.className='text_CC6A00';
          objLinkDiv.style.marginLeft='3px';
          objLinkDiv.style.textDecoration='underline';
          objLinkDiv.style.cursor='hand';
          objLinkDiv.style.cursor='pointer';
          objLinkDiv.pk=clipartCategoryTblPk;
          objLinkDiv.fk=clipartCategoryTblFk;
          objLinkDiv.onclick=function(){
            var clipartsQueryString='clipartCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','getClipartsAction.do',true,clipartsQueryString,displayCliparts,true);
            var clipartCategoryNavListQueryString='clipartCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','clipartCategoryNavigationListAction.do',true,clipartCategoryNavListQueryString,createClipartCategoryLink,true);           
          };
          
          if((clipartCategoryCount%2)==0){
            objTr=objClipartTable.insertRow(objClipartTable.rows.length);
          }
          if(objTr!=null){
            var objTd=objTr.insertCell(objTr.cells.length);
            objTd.appendChild(objLinkDiv);
          }
          clipartCategoryCount++
        }        
      }
      
      objClipartDisplay.appendChild(objClipartTable);
      
      if(objClipartTable.rows.length>0){
        var objSeperator=document.createElement('DIV');
        objSeperator.style.width="80%";
        objSeperator.className='bdrBottomColor_CC6A00';
        objClipartDisplay.appendChild(objSeperator);
      }
      
      //Displaying Cliparts;
      for(var i=0;i<nameNodes.length;i++){
        var clipartName=nameNodes.item(i).childNodes[0].nodeValue;
        var isClipart=eval(nameNodes.item(i).attributes.item(0).value);
        if(isClipart){
          var clipartTblPk=nameNodes.item(i).attributes.item(1).value;
          var clipartCategoryTblPk=nameNodes.item(i).attributes.item(2).value;
          var contentOId=nameNodes.item(i).attributes.item(3).value;
          var contentType=nameNodes.item(i).attributes.item(4).value;
          var contentSize=nameNodes.item(i).attributes.item(5).value;
          var stdContentOId=nameNodes.item(i).attributes.item(6).value;
          var stdContentType=nameNodes.item(i).attributes.item(7).value;
          var stdContentSize=nameNodes.item(i).attributes.item(8).value;
          var dataSourceKey='BOKey'
          var objImage=document.createElement('IMG');
          var srcUrl='imageDisplayAction.do?imageOid='+escape(stdContentOId);
          srcUrl+='&imageContentType='+escape(stdContentType);
          srcUrl+='&imageContentSize='+escape(stdContentSize);
          srcUrl+='&dataSourceKey='+escape(dataSourceKey);
          objImage.title=clipartName;
          if(typeof window.event != 'undefined'){
            objImage.src='blank.gif';
            objImage.style.filter='progid:DXImageTransform.Microsoft.AlphaImageLoader(src="'+srcUrl+'", sizingMethod="scale")'; 
          }else{
            objImage.src=srcUrl;          
          }
          objImage.width='60';
          objImage.height='65';
          objImage.className='bdrColor_E8E8E8';
          objImage.style.margin='5px';
          objImage.contentOId=contentOId;
          objImage.contentType=contentType;
          objImage.contentSize=contentSize;
          objImage.style.cursor='hand';
          objImage.style.cursor='pointer';
          objImage.onmouseover=function(){imageMouseOver(this);};
          objImage.onmouseout=function(){imageMouseOut(this);};
          objImage.onclick=function(){
            pickClipart(this.contentOId,this.contentType,this.contentSize,dataSourceKey);
          };
          objClipartDisplay.appendChild(objImage);
        }
      }
      
    } else {
        // there was a problem with the request,
        // for example the response may be a 404 (Not Found)
        // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Loading',0);
      objClipartDisplay.appendChild(statusObj);
  }
}

function clipartPageOnClick(){
  var clipartsQueryString='clipartCategoryTblPk='+escape(currentClipartCategoryTblPk);
  clipartsQueryString+='&searchKeywords='+escape(clipartSearchKeyword);
  clipartsQueryString+='&search='+escape(clipartSearchFlag);
  clipartsQueryString+='&pageNumber='+escape(clipartNavBar.getPageNumber());
  ajaxRequest('POST','getClipartsAction.do',true,clipartsQueryString,displayCliparts,true);
}

function createClipartCategoryLink(httpRequest){
  
  var objClipartCategoryLink=MM_findObj('clipartCategoryLink');
  objClipartCategoryLink.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      httpRequest.responseXML
      var xmldoc = httpRequest.responseXML;
      var nameNodes = xmldoc.getElementsByTagName('name');
      for(var i=0;i<nameNodes.length;i++){
        var clipartCategory=nameNodes.item(i).childNodes[0].nodeValue;
        var clipartCategoryTblPk=eval(nameNodes.item(i).attributes.item(0).value);
        var objLinkSpan=document.createElement('SPAN');
        objLinkSpan.title=clipartCategory;
        if(clipartCategory.length>7){
          clipartCategory=clipartCategory.substring(0,5)+'..';
        }
        objLinkSpan.innerHTML=clipartCategory;
        objLinkSpan.className='text_CC6A00';
        objLinkSpan.style.margin='2px';
        objLinkSpan.style.height='20px';
        objLinkSpan.style.verticalAlign='middle';
        if(clipartSearchFlag || i<nameNodes.length-1){
          objLinkSpan.style.textDecoration='underline';
          objLinkSpan.style.cursor='hand';
          objLinkSpan.style.cursor='pointer';
          objLinkSpan.pk=clipartCategoryTblPk;          
          objLinkSpan.onclick=function(){
            var clipartsQueryString='clipartCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','getClipartsAction.do',true,clipartsQueryString,displayCliparts,true);
            var clipartCategoryNavListQueryString='clipartCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','clipartCategoryNavigationListAction.do',true,clipartCategoryNavListQueryString,createClipartCategoryLink,true);           
          }
          objClipartCategoryLink.appendChild(objLinkSpan);
          var objLinkSeperator=document.createElement('SPAN');
          objLinkSeperator.style.verticalAlign='middle';
          objLinkSeperator.innerHTML='<img src="images/link_nav_right.gif" height="18px" >';
          objClipartCategoryLink.appendChild(objLinkSeperator);
        }else{
          objLinkSpan.style.fontStyle='italic';
          objClipartCategoryLink.appendChild(objLinkSpan);
        }
      }
    }
  }
}


function clipartSearchOnClick(thisForm){
  var searchKeywords=thisForm.txtClipartSearch.value;
  if(trim(searchKeywords).length<1){
    return false;
  }
  
  var clipartsQueryString='searchKeywords='+escape(searchKeywords);
  clipartsQueryString+='&search='+escape(true);
  ajaxRequest('POST','getClipartsAction.do',true,clipartsQueryString,displayCliparts,true);
  ajaxRequest('POST','clipartCategoryNavigationListAction.do',true,null,createClipartCategoryLink,true);           
}

function pickClipart(contentOId,contentType,contentSize,dataSourceKey){
  studioProcessing(true);
  document.applets[0].addClipart(contentOId,contentType,contentSize,dataSourceKey,gElementWidth,gElementHeight);
  
}

function updateClipart(thisForm){
  
  var width=thisForm.txtClipartWidth.value;
  var height=thisForm.txtClipartHeight.value;
   if(trim(width).length<=0){
    thisForm.txtClipartWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtClipartHeight.focus();
    alert('Enter Height');
    return;
  }
  width=parseInt(width);
  height=parseInt(height);
  studioProcessing(true);
  document.applets[0].setProperties(width,height);
  
}

function clipartWidthOnChange(thisForm){
  var width=thisForm.txtClipartWidth.value;
  var height=thisForm.txtClipartHeight.value;
  var locked=thisForm.chkClipartLocked.checked;
  if(trim(width).length<=0){
    thisForm.txtClipartWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtClipartHeight.focus();
    alert('Enter Height');
    return;
  }
  thisForm.txtClipartHeight.value=getLockedHeight(width,height,locked);
}

function clipartHeightOnChange(thisForm){
  var width=thisForm.txtClipartWidth.value;
  var height=thisForm.txtClipartHeight.value;
  var locked=thisForm.chkClipartLocked.checked;
  if(trim(width).length<=0){
    thisForm.txtClipartWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtClipartHeight.focus();
    alert('Enter Height');
    return;
  }
  thisForm.txtClipartWidth.value=getLockedWidth(width,height,locked);
}

function showClipartPropertySheet(width,height,styleString,rotate){
  clipartTabOnClick(MM_findObj('tabClipart'));
  MM_showHideLayers(lastSelectedLayerName,'','hide','clipartPropertySheet','','show');
  document.forms[0].txtClipartWidth.value=width;
  document.forms[0].txtClipartHeight.value=height;
  clipartFillColorPicker.select(getStyleProperty(styleString,'fill'));
  clipartStrokeColorPicker.select(getStyleProperty(styleString,'stroke'));
  document.forms[0].cboClipartRotate.value=parseInt(rotate); 
}

function hideClipartPropertySheet(){
  MM_showHideLayers('clipartPropertySheet','','hide',lastSelectedLayerName,'','show');
}
//Clipart Related Functions Ends

//Image Related Functions Starts


function customImageFileOnChange(obj){
  var thisForm=obj.form;
  if(validateImagesOnly(obj)){
    thisForm.action='customImagePreviewAction.do';
    thisForm.target='customImagePreview'
    thisForm.submit();
  }
}

function validateImagesOnly(obj){
  if(trim(obj.value).length>0){
    if(!(((obj.value).indexOf(".jpg")!=-1)
       || ((obj.value).indexOf(".gif")!=-1)
       || ((obj.value).indexOf(".png")!=-1)
       || ((obj.value).indexOf(".jpeg")!=-1)
       )) {
      alert('Images Only');
      obj.focus();
      return false;
    }else{
      return true;
    }
  }else{
    return false;
  }
}

function imageUploadOnClick(obj){
  var thisForm=obj.form;
  if(validateImagesOnly(thisForm.customImage)){
    thisForm.action='customImageUploadAction.do';
    thisForm.target='customImageUpload';
    thisForm.submit();
  }
}


function addImage(){
  if(typeof customImageUpload.document.forms[0] != 'undefined'){
    studioProcessing(true);
    var theForm=customImageUpload.document.forms[0];
    var customerGraphicsTblPk=theForm.hdnCustomerGraphicsTblPk.value;
    var contentOId=theForm.hdnContentOId.value;
    var contentType=theForm.hdnContentType.value;
    var contentSize=theForm.hdnContentSize.value;
    var dataSourceKey='FOKey';
    document.applets[0].addImage(customerGraphicsTblPk,contentOId,contentType,contentSize,dataSourceKey,gElementWidth,gElementHeight);
  }
  document.forms[0].customImage.parentNode.innerHTML='<input id="customImage" name="customImage" type="file" size="16" class="buttons" onkeydown="return false;"  onchange="return customImageFileOnChange(this);" >'
  customImagePreview.document.open();
  customImageUpload.document.open();
}

function updateImage(thisForm){
  var width=thisForm.txtImageWidth.value;
  var height=thisForm.txtImageHeight.value;
  if(trim(width).length<=0){
    thisForm.txtImageWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtImageHeight.focus();
    alert('Enter Height');
    return;
  }
  studioProcessing(true);
  width=parseInt(width);
  height=parseInt(height);
  document.applets[0].setProperties(width,height);
}

function imageWidthOnChange(thisForm){
  var width=thisForm.txtImageWidth.value;
  var height=thisForm.txtImageHeight.value;
  var locked=thisForm.chkImageLocked.checked;
  if(trim(width).length<=0){
    thisForm.txtImageWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtImageHeight.focus();
    alert('Enter Height');
    return;
  }
  thisForm.txtImageHeight.value=getLockedHeight(width,height,locked);
}

function imageHeightOnChange(thisForm){
  var width=thisForm.txtImageWidth.value;
  var height=thisForm.txtImageHeight.value;
  var locked=thisForm.chkImageLocked.checked;
  if(trim(width).length<=0){
    thisForm.txtImageWidth.focus();
    alert('Enter Width');
    return;
  }
  if(trim(height).length<=0){
    thisForm.txtImageHeight.focus();
    alert('Enter Height');
    return;
  }
  thisForm.txtImageWidth.value=getLockedWidth(width,height,locked);
}


function showImagePropertySheet(width,height,styleString,rotate){
  imageTabOnClick(MM_findObj('tabImage'));
  MM_showHideLayers(lastSelectedLayerName,'','hide','imagePropertySheet','','show');
  document.forms[0].txtImageWidth.value=width;
  document.forms[0].txtImageHeight.value=height;
  document.forms[0].cboImageRotate.value=parseInt(rotate); 
  
}

function hideImagePropertySheet(){
  MM_showHideLayers('imagePropertySheet','','hide',lastSelectedLayerName,'','show');
}

function imageTabOnClick(obj){  
  tabOnClick(obj,'imageLayer','images/tab_image_active.gif','images/tab_image.gif')
}
//Image Related Functions Ends

//Text Related Functions Starts
function textTabOnClick(obj) {
  tabOnClick(obj,'textEntry','images/tab_text_active.gif','images/tab_text.gif');
}  
function addText(thisForm){
  var textValue= thisForm.txaTextAdd.value;
  if(trim(textValue).length<=0){
    thisForm.txaTextAdd.focus();
    alert('Enter Text');
    return;
  }
  studioProcessing(true);
  var styleString="font-size:"+fontSize;
  document.applets[0].addText(textValue,styleString);
  thisForm.txaTextAdd.value='';
}

function updateText(thisForm){
  var textValue=thisForm.txaTextEdit.value;
  if(trim(textValue).length<=0){
    thisForm.txaTextEdit.focus();
    alert('Enter Text');
    return;
  }
  studioProcessing(true);
  document.applets[0].editText(textValue);
}

function showTextPropertySheet(textValue,styleString,rotate){
  
  textTabOnClick(MM_findObj('tabText'));
  
  MM_showHideLayers(lastSelectedLayerName,'','hide','fontList','','hide','textPropertySheet','','show');
  document.forms[0].txaTextEdit.value=textValue;
  
  var newFontSize=getStyleProperty(styleString,'font-size');
  document.forms[0].cboTextSize.value=newFontSize;
  
  var newFill=getStyleProperty(styleString,'fill');
  textFillColorPicker.select(newFill);
  
  var newStroke=getStyleProperty(styleString,'stroke');
  textStrokeColorPicker.select(newStroke);
  
  var newStrokeWidth=getStyleProperty(styleString,'stroke-width');
  document.forms[0].cboTextStrokeWidth.value=newStrokeWidth;
  
  document.forms[0].cboTextRotate.value=parseInt(rotate); 
  
  var newFontFamilyName=getStyleProperty(styleString,'font-family');
  
  var objSelectedFont=MM_findObj('imgSelectedFont');
  var srcUrl='textDisplayAction.do'
  
  if(newFontFamilyName!='default'){
    srcUrl+='?text='+escape(newFontFamilyName)+'&style='+ escape('font-size:'+fontSize+';font-family:'+newFontFamilyName+';');
  }else{
    srcUrl+='?text='+escape(selectedFontFamilyName)+'&style=' + escape('font-size:'+fontSize+';font-family:'+selectedFontFamilyName+';');
  }  
  
  objSelectedFont.src=srcUrl;  
}

function hideTextPropertySheet(){
  MM_showHideLayers('textPropertySheet','','hide','fontList','','hide',lastSelectedLayerName,'','show');
}

function chooseFontOnclick(){
  MM_showHideLayers('textPropertySheet','','hide','fontList','','show');
  var fontsQueryString='fontCategoryTblPk='+escape(currentFontCategoryTblPk);
  ajaxRequest('POST','getFontFamilyAction.do',true,fontsQueryString,displayFontFamily,true);
}

function fontBackOnclick(){
  MM_showHideLayers('fontList','','hide','textPropertySheet','','show');
}

//To Display Font Family
function displayFontFamily(httpRequest){
  var objFontDisplay=MM_findObj('fontDisplay');
  objFontDisplay.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      httpRequest.responseXML
      var xmldoc = httpRequest.responseXML;
      var metaNodes = xmldoc.getElementsByTagName('meta');      
      if(metaNodes.length==1){
        fontNavBar.setPageNumber(metaNodes.item(0).attributes.item(0).value);
        fontNavBar.setPageCount(metaNodes.item(0).attributes.item(1).value);        
        currentFontCategoryTblPk=metaNodes.item(0).attributes.item(2).value;
      }
      fontNavBar.refresh();
      
      var nameNodes = xmldoc.getElementsByTagName('name');
      var objFontTable=document.createElement('TABLE');
      objFontTable.border='0';
      objFontTable.cellSpacing='1';
      objFontTable.cellPadding='0';
      objFontTable.width='180px'
      objFontTable.style.align='center';
      var objTr=null;
      var fontCategoryCount=0;
      //Displaying Font Catergory Link Table;
      for(var i=0;i<nameNodes.length;i++){
        var fontCategoryName=nameNodes.item(i).childNodes[0].nodeValue;
        var isFont=eval(nameNodes.item(i).attributes.item(0).value);
        if(!isFont){
          var fontCategoryTblPk=nameNodes.item(i).attributes.item(1).value;
          var fontCategoryTblFk=nameNodes.item(i).attributes.item(2).value;
          var objLinkDiv=document.createElement('DIV');
          objLinkDiv.title=fontCategoryName;
          var displayLength=10;
          if(fontCategoryName.length>displayLength){
            fontCategoryName=fontCategoryName.substring(0,displayLength-2)+'..';
          }
          objLinkDiv.innerHTML=fontCategoryName;
          objLinkDiv.className='text_CC6A00';
          objLinkDiv.style.marginLeft='3px';
          objLinkDiv.style.textDecoration='underline';
          objLinkDiv.style.cursor='hand';
          objLinkDiv.style.cursor='pointer';
          objLinkDiv.pk=fontCategoryTblPk;
          objLinkDiv.fk=fontCategoryTblFk;
          objLinkDiv.onclick=function(){
            var fontsQueryString='fontCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','getFontFamilyAction.do',true,fontsQueryString,displayFontFamily,true);
            var fontCategoryNavListQueryString='fontCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','fontCategoryNavigationListAction.do',true,fontCategoryNavListQueryString,createFontCategoryLink,true);           
          };
          
          if((fontCategoryCount%2)==0){
            objTr=objFontTable.insertRow(objFontTable.rows.length);
          }
          if(objTr!=null){
            var objTd=objTr.insertCell(objTr.cells.length);
            objTd.appendChild(objLinkDiv);
          }
          fontCategoryCount++
        }        
      }
      
      objFontDisplay.appendChild(objFontTable);
      
      if(objFontTable.rows.length>0){
        var objSeperator=document.createElement('DIV');
        objSeperator.style.width="80%";
        objSeperator.className='bdrBottomColor_CC6A00';
        objFontDisplay.appendChild(objSeperator);
      }
      
      //Displaying Fonts;
      for(var i=0;i<nameNodes.length;i++){
        var fontFamilyName=nameNodes.item(i).childNodes[0].nodeValue;
        var isFont=eval(nameNodes.item(i).attributes.item(0).value);
        if(isFont){
          var fontTblPk=nameNodes.item(i).attributes.item(1).value;
          var fontCategoryTblPk=nameNodes.item(i).attributes.item(2).value;
          var dataSourceKey='BOKey'
          var objImage=document.createElement('IMG');
          var srcUrl='textDisplayAction.do?text='+escape(fontFamilyName)+'&style=font-size:'+fontSize+';font-family:'+fontFamilyName+';';
          objImage.title=fontFamilyName;
          objImage.alt=fontFamilyName;
          objImage.src=srcUrl;   
          objImage.style.margin='5px';
          if(trim(selectedFontFamilyName)==trim(fontFamilyName)){
            objImage.className='bdrColor_00FF00';
            objImage.style.cursor='default';
          }else{
            objImage.className='bdrColor_E8E8E8';
            objImage.style.cursor='hand';
            objImage.style.cursor='pointer';
            objImage.onmouseover=function(){imageMouseOver(this);};
            objImage.onmouseout=function(){imageMouseOut(this);};
            objImage.onclick=function(){
              selectedFontFamilyName=this.alt;
              setFontFamily(this.alt);
            };
          }
          objFontDisplay.appendChild(objImage);
        }
      }      
    } else {
        // there was a problem with the request,
        // for example the response may be a 404 (Not Found)
        // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Loading',0);
      objFontDisplay.appendChild(statusObj);
  }
}

function fontPageOnClick(){
  var fontsQueryString='fontCategoryTblPk='+escape(currentFontCategoryTblPk);
  fontsQueryString+='&pageNumber='+escape(fontNavBar.getPageNumber());
  ajaxRequest('POST','getFontFamilyAction.do',true,fontsQueryString,displayFontFamily,true);
}

function createFontCategoryLink(httpRequest){
  
  var objFontCategoryLink=MM_findObj('fontCategoryLink');
  objFontCategoryLink.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      httpRequest.responseXML
      var xmldoc = httpRequest.responseXML;
      var nameNodes = xmldoc.getElementsByTagName('name');
      for(var i=0;i<nameNodes.length;i++){
        var fontCategory=nameNodes.item(i).childNodes[0].nodeValue;
        var fontCategoryTblPk=eval(nameNodes.item(i).attributes.item(0).value);
        var objLinkSpan=document.createElement('SPAN');
        objLinkSpan.title=fontCategory;
        if(fontCategory.length>7){
          fontCategory=fontCategory.substring(0,5)+'..';
        }
        objLinkSpan.innerHTML=fontCategory;
        objLinkSpan.className='text_CC6A00';
        objLinkSpan.style.margin='2px';
        objLinkSpan.style.height='20px';
        objLinkSpan.style.verticalAlign='middle';
        if(i<nameNodes.length-1){
          objLinkSpan.style.textDecoration='underline';
          objLinkSpan.style.cursor='hand';
          objLinkSpan.style.cursor='pointer';
          objLinkSpan.pk=fontCategoryTblPk;          
          objLinkSpan.onclick=function(){
            var fontsQueryString='fontCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','getFontFamilyAction.do',true,fontsQueryString,displayFontFamily,true);
            var fontCategoryNavListQueryString='fontCategoryTblPk='+escape(this.pk);
            ajaxRequest('POST','fontCategoryNavigationListAction.do',true,fontCategoryNavListQueryString,createFontCategoryLink,true);           
          }
          objFontCategoryLink.appendChild(objLinkSpan);
          var objLinkSeperator=document.createElement('SPAN');
          objLinkSeperator.style.verticalAlign='middle';
          objLinkSeperator.innerHTML='<img src="images/link_nav_right.gif" height="18px" >';
          objFontCategoryLink.appendChild(objLinkSeperator);
        }else{
          objLinkSpan.style.fontStyle='italic';
          objFontCategoryLink.appendChild(objLinkSpan);
        }
      }
    }
  }
}


//Text Related Functions Ends

//Design Save Related Function Starts

function saveTabOnClick(obj){
  var thisForm=document.forms[0];
  thisForm.chkSaveAsAbove.checked=false;
  
  if (customerDesignTblPk==-1){
    MM_showHideLayers('saveAsAboveLayer','','hide');
    thisForm.txtDesignName.value='';
    thisForm.txtEmailId.value='';
    thisForm.txtDesignName.disabled=false;
    thisForm.txtEmailId.disabled=false;
  }
  MM_showHideLayers('saveMessage','','hide','saveWidget','','show');
  tabOnClick(obj,'saveDesign','images/tab_save_active.gif','images/tab_save.gif');
}

function saveButtonOnClick(){
  var thisForm=document.forms[0];
  if (customerDesignTblPk==-1 || thisForm.chkSaveAsAbove.checked ){
    if(validateDesignSave()){
      var designNameCheckQueryString='customerDesignName=' + escape(thisForm.txtDesignName.value);
      designNameCheckQueryString+='&customerEmailId=' + escape(thisForm.txtEmailId.value);
      ajaxRequest('POST','designNameCheckAction.do',true,designNameCheckQueryString,designNameCheckReply,true);
    }
  }else{
    MM_showHideLayers('saveMessage','','show','saveWidget','','hide');
    document.applets[0].saveDesign(thisForm.chkSaveAsAbove.checked);
  }
}

function validateDesignSave(){
  var thisForm=document.forms[0];
  if(trim(thisForm.txtDesignName.value).length<=0){
    alert('Enter Design Name');
    thisForm.txtDesignName.focus();
    return false;
  }
  
  if(trim(thisForm.txtEmailId.value).length<=0){
    alert('Enter Email Id');
    thisForm.txtEmailId.focus();
    return false;
  }
  
  return true;
  
}

function designNameCheckReply(httpRequest){
  var thisForm=document.forms[0];
  var objSaveMessageDisplay=MM_findObj('saveMessage');
  objSaveMessageDisplay.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
    // perfect!
      var xmldoc = httpRequest.responseXML;
      var existsNodes = xmldoc.getElementsByTagName('exists');
      var thisForm=document.forms[0];
      if(existsNodes.length==1){
        var exists=eval(existsNodes.item(0).childNodes[0].nodeValue);
        if(exists){
          alert('Design Name Aready Exists - Change Design Name To Save');
          thisForm.txtDesignName.focus();
        }else{
          MM_showHideLayers('saveMessage','','show','saveWidget','','hide');   
          document.applets[0].saveDesign(thisForm.chkSaveAsAbove.checked);
        }
      }
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Verifying Design Name',0);
      objSaveMessageDisplay.appendChild(statusObj);
      
  }
}


function saveDesign(uniqueNumberString,
                    merchandisePrintableAreaPkString,
                    customerDesignDetailPkString,
                    elementIdString,
                    elementTypeString,
                    posXString,
                    posYString,
                    widthString,
                    heightString,
                    styleString,
                    rotateString,
                    propertyNameString,
                    propertyValueString,
                    elementNewString,
                    printableAreaDelimiter,
                    elementWiseDelimiter,
                    propertyWiseDelimiter){
  
  
  var thisForm=document.forms[0];
  
  var saveDesignQueryString='customerDesignName=' + escape(thisForm.txtDesignName.value);
  saveDesignQueryString+='&customerEmailId=' + escape(thisForm.txtEmailId.value);
  saveDesignQueryString+='&customerDesignTblPk='+escape((thisForm.chkSaveAsAbove.checked)?'-1':customerDesignTblPk);
  saveDesignQueryString+='&selectedMerchandiseTblPk='+escape(selectedMerchandiseTblPk);
  saveDesignQueryString+='&selectedMerchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
  saveDesignQueryString+='&uniqueNumberString='+escape(uniqueNumberString);
  saveDesignQueryString+='&merchandisePrintableAreaPkString='+escape(merchandisePrintableAreaPkString);
  saveDesignQueryString+='&customerDesignDetailPkString='+escape(customerDesignDetailPkString);
  saveDesignQueryString+='&elementIdString='+escape(elementIdString);
  saveDesignQueryString+='&elementTypeString='+escape(elementTypeString);
  saveDesignQueryString+='&posXString='+escape(posXString);
  saveDesignQueryString+='&posYString='+escape(posYString);
  saveDesignQueryString+='&widthString='+escape(widthString);
  saveDesignQueryString+='&heightString='+escape(heightString);
  saveDesignQueryString+='&styleString='+escape(styleString);
  saveDesignQueryString+='&rotateString='+escape(rotateString);
  saveDesignQueryString+='&propertyNameString='+escape(propertyNameString);
  saveDesignQueryString+='&propertyValueString='+escape(propertyValueString);
  saveDesignQueryString+='&elementNewString='+escape(elementNewString);
  saveDesignQueryString+='&printableAreaDelimiter='+escape(printableAreaDelimiter);
  saveDesignQueryString+='&elementWiseDelimiter='+escape(elementWiseDelimiter);
  saveDesignQueryString+='&propertyWiseDelimiter='+escape(propertyWiseDelimiter);

  ajaxRequest('POST','productCustomiseAction.do',true,saveDesignQueryString,saveDesignReply,true);                    
}

function saveDesignReply(httpRequest){
  var thisForm=document.forms[0];
  var objSaveMessageDisplay=MM_findObj('saveMessage');
  objSaveMessageDisplay.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
    // perfect!
      
      var xmldoc = httpRequest.responseXML;
      
      var customerDesignName;
      var customerEmailId;
      var customerDesignCode;
      
      var headerNodes=xmldoc.getElementsByTagName('header');
      
      if(headerNodes.length==1){
        var headerNode=headerNodes.item(0);
        var pkNodes = trim(headerNode.getElementsByTagName('pk'));
        
        if(pkNodes.length==1){
          customerDesignTblPk=pkNodes.item(0).childNodes[0].nodeValue;
        }
        
        var nameNodes = headerNode.getElementsByTagName('name');
        if(nameNodes.length==1){
          customerDesignName=trim(nameNodes.item(0).childNodes[0].nodeValue);
        }
        
        var codeNodes = headerNode.getElementsByTagName('code');
        if(codeNodes.length==1){
          customerDesignCode=trim(codeNodes.item(0).childNodes[0].nodeValue);
        }
        
        var emailNodes = headerNode.getElementsByTagName('email');
        if(emailNodes.length==1){
          customerEmailId=trim(emailNodes.item(0).childNodes[0].nodeValue);
        }
      }
    
      thisForm.txtDesignName.value=customerDesignName;
      thisForm.txtEmailId.value=customerEmailId;
      thisForm.chkSaveAsAbove.checked=false;
      MM_showHideLayers('saveAsAboveLayer','','show');
      thisForm.txtDesignName.disabled=true;
      thisForm.txtEmailId.disabled=true;
      
      var detailDelimiter="~";
      var uniqueNumberString="";
      var customerDesignDetailPkString="";
      
      var detailNodes = xmldoc.getElementsByTagName('detail');
      if(detailNodes.length==1){
        var detailNode=detailNodes.item(0);
        var uniqueNumberNodes=detailNode.getElementsByTagName("unique");
        var customerDesignDetailPkNodes=detailNode.getElementsByTagName("pk");
        for(var uniqueNumberIndex=0;uniqueNumberIndex<uniqueNumberNodes.length;uniqueNumberIndex++){
          var uniqueNumberNode=uniqueNumberNodes.item(uniqueNumberIndex);
          var customerDesignDetailPkNode=customerDesignDetailPkNodes.item(uniqueNumberIndex);
          
          var uniqueNumber=trim(uniqueNumberNode.childNodes[0].nodeValue);
          var customerDesignDetailPk=trim(customerDesignDetailPkNode.childNodes[0].nodeValue);
          uniqueNumberString+=uniqueNumber;
          customerDesignDetailPkString+=customerDesignDetailPk;
          if(uniqueNumberIndex<(uniqueNumberNodes.length-1)){
            uniqueNumberString+=detailDelimiter;
            customerDesignDetailPkString+=detailDelimiter;
          }
        }
      }
     
      //Message
      var objDiv=document.createElement('DIV');
      objDiv.innerHTML='Design is Saved Sucessfully';
      objDiv.className='text_008000';
      objDiv.style.fontStyle='italic';
      objSaveMessageDisplay.appendChild(objDiv);
      
      //Design Name
      objDiv=document.createElement('DIV');
      objDiv.innerHTML='Design Name : ' + customerDesignName;
      objDiv.className='text_CC6600' ;
      objSaveMessageDisplay.appendChild(objDiv);
      
      //Design Code
      objDiv=document.createElement('DIV');
      objDiv.innerHTML='Design Code : ' + customerDesignCode;
      objDiv.className='text_CC6600';
      objSaveMessageDisplay.appendChild(objDiv);
      
      //Email Id
      objDiv=document.createElement('DIV');
      objDiv.innerHTML='Email : ' + customerEmailId ;
      objDiv.className='text_CC6600';
      objSaveMessageDisplay.appendChild(objDiv);
      
      //Buy Design Link
      objDiv=document.createElement('DIV');
      objDiv.innerHTML='Buy' 
      objDiv.className='text_008000';
      objDiv.style.cursor='hand';
      objDiv.style.cursor='pointer';
      objDiv.style.textDecoration='underline';
      objDiv.onclick=function(){
        var buyTabObj=MM_findObj('tabBuy');
        buyTabOnClick(buyTabObj);
      }
      
      objSaveMessageDisplay.appendChild(objDiv);
      
      document.applets[0].updatePrintableArea(uniqueNumberString,customerDesignDetailPkString,detailDelimiter);
      
      var currentDesignQueryString='customerDesignTblPk='+escape(customerDesignTblPk);
      currentDesignQueryString+='&merchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
      ajaxRequest('POST','getCurrentDesignAction.do',true,currentDesignQueryString,currentDesignReply,true);  
      document.applets[0].setDesignSaved(true);
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Saving Design',0);
      objSaveMessageDisplay.appendChild(statusObj);
  }
  
}


function currentDesignReply(httpRequest){

  var objLyrBelowEngine=MM_findObj('lyrBelowEngine');
  objLyrBelowEngine.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var currentDesignNodes=xmldoc.getElementsByTagName('currentDesign');
      
      if(currentDesignNodes.length==1){
       var currentDesignNode=currentDesignNodes.item(0);
       
       var detailTbl = document.createElement("TABLE");
       detailTbl.border='0';
       detailTbl.cellpadding='0';
       detailTbl.cellspacing='0';
       detailTbl.style.width='80%';
      
       var objTr=detailTbl.insertRow(0);
       objTr.insertCell(0);
       objTr.insertCell(1);
       objTr.insertCell(2);
      
        //Merchandise
        var merchandiseNodes = currentDesignNode.getElementsByTagName('merchandise');
        if(merchandiseNodes.length==1){
          var merchandiseName=trim(merchandiseNodes.item(0).childNodes[0].nodeValue);
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          objDiv.innerHTML=merchandiseName;
        }
        
         //Minimum Quantity 
        var qtyNodes = currentDesignNode.getElementsByTagName('qty');
        if(qtyNodes.length==1){
          var qty=trim(qtyNodes.item(0).childNodes[0].nodeValue);
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          objDiv.innerHTML='Quantity Minimum '+qty+' Piece' + '    ';
        }
        
        //Merchandise Decoration 
        var decorationNodes = currentDesignNode.getElementsByTagName('decoration');
        if(decorationNodes.length==1){
          var decoration=trim(decorationNodes.item(0).childNodes[0].nodeValue);
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          objDiv.innerHTML=decoration;
        }
        
      
        //Customer Email
        var emailNodes = currentDesignNode.getElementsByTagName('email');
        if(emailNodes.length==1){
          var email=trim(emailNodes.item(0).childNodes[0].nodeValue);
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          objDiv.innerHTML=email;
          detailTbl.rows[0].cells[0].appendChild(objDiv);
        }
        
         //Design Name
        var designNodes = currentDesignNode.getElementsByTagName('design');
        if(designNodes.length==1){
          var designName=trim(designNodes.item(0).childNodes[0].nodeValue);
          
          //Design Name Span
          var objSpan=document.createElement('SPAN');
          objSpan.align='center';
          objSpan.className='text_CC6600' ;
          objSpan.innerHTML=designName;
          
          //Asteric Span
          var objSpanAsteric=document.createElement('SPAN');
          objSpanAsteric.id='spanAsteric'
          objSpanAsteric.align='center';
          objSpanAsteric.className='text_CC6600' ;
          
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          
          objDiv.appendChild(objSpan);
          objDiv.appendChild(objSpanAsteric);
          
          detailTbl.rows[0].cells[1].appendChild(objDiv);
        }
        
        //Design Code
        var codeNodes = currentDesignNode.getElementsByTagName('code');
        if(codeNodes.length==1){
          var code=trim(codeNodes.item(0).childNodes[0].nodeValue);
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6600' ;
          objDiv.innerHTML=code;
          detailTbl.rows[0].cells[2].appendChild(objDiv);
        }
        
        objLyrBelowEngine.appendChild(detailTbl);          
      }
    
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Loading Details',1);
      objLyrBelowEngine.appendChild(statusObj);
  }
}

function chkSaveAsAboveOnclick(obj){
  var thisForm=obj.form;
  var ctlEnable=!(obj.checked);
  thisForm.txtDesignName.disabled=ctlEnable;
  thisForm.txtEmailId.disabled=ctlEnable;
}

function setDesignStatus(designSaved){
  var objSpanAsteric=MM_findObj('spanAsteric');
  if(objSpanAsteric!=null){
    if(eval(designSaved)==true){
      objSpanAsteric.innerHTML='';
    }else{
      objSpanAsteric.innerHTML='*';
    }
  }
}
//Design Save Related Function Ends

// Buy Related Functions Starts

function buyTabOnClick(obj){
  if(eval(document.applets[0].isDesignSaved())==true){
    tabOnClick(obj,'buyLayer','images/tab_buy_active.gif','images/tab_buy.gif');
  }else{
    alert('Please Save Design and Click Buy');
    var saveTabObj=MM_findObj('tabSave');
    saveTabOnClick(saveTabObj);
  }
}

function buyingOnClick(){
  window.location='orderCreateB4Action.do?customerDesignTblPk='+customerDesignTblPk;
}

// Buy Related Functions Ends

// Open Related Functions Starts

function btnDesignCodeOpenOnClick(){
 var thisForm=document.forms[0];
  if(trim(thisForm.txtOpenDesignCode.value).length<=0){
    alert('Enter Design Code');
    thisForm.txtOpenDesignCode.focus();
    return false;
  }
  
  var designNameOpenQueryString='customerDesignCode=' + escape(thisForm.txtOpenDesignCode.value);
  ajaxRequest('POST','openDesignAction.do',true,designNameOpenQueryString,openDesignReply,true); 
  MM_showHideLayers('lyrOpenDesignView','','show','lyrOpenDesign','','hide');
}

function btnDesignNameOpenOnClick(){

  var thisForm=document.forms[0];
  if(trim(thisForm.txtOpenDesignName.value).length<=0){
    alert('Enter Design Name');
    thisForm.txtOpenDesignName.focus();
    return false;
  }
  
  if(trim(thisForm.txtOpenEmailId.value).length<=0){
    alert('Enter Email Id');
    thisForm.txtOpenEmailId.focus();
    return false;
  }  
  
  var designNameOpenQueryString='customerDesignName=' + escape(thisForm.txtOpenDesignName.value);
  designNameOpenQueryString+='&customerEmailId=' + escape(thisForm.txtOpenEmailId.value);
  ajaxRequest('POST','openDesignAction.do',true,designNameOpenQueryString,openDesignReply,true);
  
  MM_showHideLayers('lyrOpenDesignView','','show','lyrOpenDesign','','hide');
  
}

function openDesignReply(httpRequest){
  var objLyrOpenDesignView=MM_findObj('lyrOpenDesignView');
  objLyrOpenDesignView.innerHTML='';
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var designNodes=xmldoc.getElementsByTagName('design');
      
      if(designNodes.length==1){        
        var designNode=designNodes.item(0);
        if(designNode.childNodes.length>0){
          var pkNodes = designNode.getElementsByTagName('pk');
          var mColorPkNodes = designNode.getElementsByTagName('mcolorpk');
          
          if(pkNodes.length==1){
            var pk=trim(pkNodes.item(0).childNodes[0].nodeValue);
            var mColorPk=trim(mColorPkNodes.item(0).childNodes[0].nodeValue);
            
            var objDiv=document.createElement('DIV');
            objDiv.align='center';
            objDiv.className='text_008000' ;
            objDiv.innerHTML='Click to Open Design';
            objDiv.style.cursor='hand';
            objDiv.style.cursor='pointer';
            objDiv.style.textDecoration='underline';
            objDiv.onclick=function(){
              
              //Design To Edit and Save 
              customerDesignTblPk=pk;
              selectedMerchandiseColorTblPk=mColorPk;
              
              var currentDesignQueryString='customerDesignTblPk='+escape(customerDesignTblPk);
              currentDesignQueryString+='&merchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
              ajaxRequest('POST','getCurrentDesignAction.do',true,currentDesignQueryString,currentDesignReply,true);
              
              var printablAreaQueryString='merchandiseColorTblPk='+escape(selectedMerchandiseColorTblPk);
              ajaxRequest('POST','getPrintableAreaAction.do',true,printablAreaQueryString,generatePrintableArea,true);
              
              var populateDesignQueryString='customerDesignTblPk='+escape(customerDesignTblPk);
              ajaxRequest('POST','populateDesignAction.do',true,populateDesignQueryString,populateDesignReply,true);
              
            }; 
            objLyrOpenDesignView.appendChild(objDiv);
          }
  
          var designNameNodes = designNode.getElementsByTagName('name');
          var name='';
          if(designNameNodes.length==1){
            name=trim(designNameNodes.item(0).childNodes[0].nodeValue);
            var objDiv=document.createElement('DIV');
            objDiv.align='center';
            objDiv.className='text_CC6600' ;
            objDiv.innerHTML='Design Name : ' + name;
            objLyrOpenDesignView.appendChild(objDiv);
          }
          
          var emailIdNodes = designNode.getElementsByTagName('email');
          var email='';
          if(emailIdNodes.length==1){
            email=trim(emailIdNodes.item(0).childNodes[0].nodeValue);
            var objDiv=document.createElement('DIV');
            objDiv.align='center';
            objDiv.className='text_CC6600' ;
            objDiv.innerHTML='Email Id : ' + email;
            objLyrOpenDesignView.appendChild(objDiv);
          }
          
          var designCodeNodes = designNode.getElementsByTagName('code');
          var code='';
          if(designCodeNodes.length==1){
            code=trim(designCodeNodes.item(0).childNodes[0].nodeValue);
            var objDiv=document.createElement('DIV');
            objDiv.align='center';
            objDiv.className='text_CC6600' ;
            objDiv.innerHTML='Design Code : ' + code;
            objLyrOpenDesignView.appendChild(objDiv);
          }
          
          var thisForm=document.forms[0];
          thisForm.txtDesignName.value=name;
          thisForm.txtEmailId.value=email;
          thisForm.chkSaveAsAbove.checked=false;
          MM_showHideLayers('saveAsAboveLayer','','show');
          thisForm.txtDesignName.disabled=true;
          thisForm.txtEmailId.disabled=true;
        }else{
          var objDiv=document.createElement('DIV');
          objDiv.align='center';
          objDiv.className='text_CC6A00' ;
          objDiv.innerHTML='No Such Design Exists';
          objLyrOpenDesignView.appendChild(objDiv);
        }
      }
      var objDiv=document.createElement('DIV');
      objDiv.align='center';
      objDiv.className='text_008000' ;
      objDiv.innerHTML='Back';
      objDiv.style.cursor='hand';
      objDiv.style.cursor='pointer';
      objDiv.style.textDecoration='underline';
      objDiv.onclick=function(){
        MM_showHideLayers('lyrOpenDesign','','show','lyrOpenDesignView','','hide');
      };
      objLyrOpenDesignView.appendChild(objDiv);
    } else {
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  } else {
      // still not ready
      var statusObj=getStatusDisplay('Loading Details',1);
      objLyrOpenDesignView.appendChild(statusObj);
  }
}



function populateDesignReply(httpRequest){
  if (httpRequest.readyState == 4) {
    if (httpRequest.status == 200) {
      // perfect!
      var xmldoc = httpRequest.responseXML;
      var detailNodes=xmldoc.getElementsByTagName('detail');
      
      //alert(detailNodes.length);
      var merchandisePrintableAreaPkString='';
      var customerDesignDetailPkString='';
      var elementIdString='';
      var elementTypeString='';
      var posXString='';
      var posYString='';
      var widthString='';
      var heightString='';
      var styleString='';
      var rotateString='';
      var propertyNameString='';
      var propertyValueString='';
      var elementNewString='';
      var printableAreaDelimiter='~~~';
      var elementWiseDelimiter='~~';
      var propertyWiseDelimiter='~';
      
      for(var detailIndex=0; detailIndex<detailNodes.length ; detailIndex++){
        var detailNode=detailNodes.item(detailIndex);
        var mtblPk=detailNode.getElementsByTagName('mptblpk').item(0).childNodes[0].nodeValue;
        var detailPk=detailNode.getElementsByTagName('detailpk').item(0).childNodes[0].nodeValue;
        merchandisePrintableAreaPkString+=mtblPk;
        customerDesignDetailPkString+=detailPk;
        
        var elementNodes=detailNode.getElementsByTagName('element');
        
        for(var elementIndex=0; elementIndex<elementNodes.length ; elementIndex++){
          
          var elementNode=elementNodes.item(elementIndex);
          var elementId=elementNode.getElementsByTagName('id').item(0).childNodes[0].nodeValue;
          var elementType=elementNode.getElementsByTagName('type').item(0).childNodes[0].nodeValue;
          var posX=elementNode.getElementsByTagName('posx').item(0).childNodes[0].nodeValue;
          var posY=elementNode.getElementsByTagName('posy').item(0).childNodes[0].nodeValue;
          var width=elementNode.getElementsByTagName('width').item(0).childNodes[0].nodeValue;
          var height=elementNode.getElementsByTagName('height').item(0).childNodes[0].nodeValue;
          var style=' '; //Keep Space for style to avoid  confits amont delimiters
          if (elementNode.getElementsByTagName('style').item(0).childNodes.length>0){
            style=elementNode.getElementsByTagName('style').item(0).childNodes[0].nodeValue;
          }

          var rotate=elementNode.getElementsByTagName('rotate').item(0).childNodes[0].nodeValue;
          
          elementIdString+=elementId;
          elementTypeString+=elementType;
          posXString+=posX;
          posYString+=posY;
          widthString+=width;
          heightString+=height;
          styleString+=style;
          rotateString+=rotate;
          elementNewString+=false;
          
          var propertyNodes=elementNode.getElementsByTagName('property');   

          for(var propertyIndex=0; propertyIndex<propertyNodes.length; propertyIndex++){
            var propertyNode=propertyNodes.item(propertyIndex);
            var propertyName=propertyNode.getElementsByTagName('name').item(0).childNodes[0].nodeValue;
            var propertyValue=propertyNode.getElementsByTagName('value').item(0).childNodes[0].nodeValue;

            propertyNameString+=propertyName;
            propertyValueString+=propertyValue;
            if(propertyIndex<(propertyNodes.length-1)){
              propertyNameString+=propertyWiseDelimiter;
              propertyValueString+=propertyWiseDelimiter;
            }
          } 
          
          if(elementIndex<(elementNodes.length-1)){
            elementIdString+=elementWiseDelimiter;
            elementTypeString+=elementWiseDelimiter;
            posXString+=elementWiseDelimiter;
            posYString+=elementWiseDelimiter;
            widthString+=elementWiseDelimiter;
            heightString+=elementWiseDelimiter;
            styleString+=elementWiseDelimiter;
            rotateString+=elementWiseDelimiter;
            propertyNameString+=elementWiseDelimiter;
            propertyValueString+=elementWiseDelimiter;
            elementNewString+=elementWiseDelimiter;
          }          
        }
        
        if(detailIndex<(detailNodes.length-1)){
          merchandisePrintableAreaPkString+=printableAreaDelimiter;
          customerDesignDetailPkString+=printableAreaDelimiter;
          elementIdString+=printableAreaDelimiter;
          elementTypeString+=printableAreaDelimiter;
          posXString+=printableAreaDelimiter;
          posYString+=printableAreaDelimiter;
          widthString+=printableAreaDelimiter;
          heightString+=printableAreaDelimiter;
          styleString+=printableAreaDelimiter;
          rotateString+=printableAreaDelimiter;
          propertyNameString+=printableAreaDelimiter;
          propertyValueString+=printableAreaDelimiter;
          elementNewString+=printableAreaDelimiter;
        }
      }
      
      
      document.applets[0].populateDesign(
       merchandisePrintableAreaPkString,
       customerDesignDetailPkString,
       elementIdString,
       elementTypeString,
       posXString,
       posYString,
       widthString,
       heightString,
       styleString,
       rotateString,
       propertyNameString,
       propertyValueString,
       elementNewString,
       printableAreaDelimiter,
       elementWiseDelimiter,
       propertyWiseDelimiter);
      
      
      
    }else{
      // there was a problem with the request,
      // for example the response may be a 404 (Not Found)
      // or 500 (Internal Server Error) response codes
    }
  }else{
      // still not ready
      //var statusObj=getStatusDisplay('Loading Details',1);
      //objLyrOpenDesignView.appendChild(statusObj);
  }
  
}

// Open Related Functions Ends


</script>

</head>
<body onload="return window_onload();">
<html:form action="/customImageUploadAction" enctype="multipart/form-data">
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="217px">
        <table width="217px" border="0" cellspacing="0" cellpadding="0">
          <!-- Top Toolbar -->
          <tr>
            <td>
              <div style="float:left;"><img src="images/tab_choose_active.gif" id="tabProduct" style="cursor:hand; cursor:pointer;"  onclick="tabOnClick(this,'productList','images/tab_choose_active.gif','images/tab_choose.gif')" alt="Choose" width="55px" height="39px"></div> 
              <div style="float:left;"><img src="images/tab_art.gif" id="tabClipart" style="cursor:hand; cursor:pointer;text-decoration:underline" onclick="return clipartTabOnClick(this);" alt="Art" width="55px" height="39px"></div> 
              <div style="float:left;"><img src="images/tab_image.gif" id="tabImage" style="cursor:hand; cursor:pointer; text-decoration:underline" onclick="return imageTabOnClick(this);" alt="Image" width="55px" height="39px"></div> 
              <div style="float:left;"><img src="images/tab_text.gif" id="tabText" style="cursor:hand; cursor:pointer; text-decoration:underline" onclick="return textTabOnClick(this);" alt="Text" width="52px" height="39px"></div> 
            </td>
          </tr>
          <tr>
            <td>
              <table width="217px" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" class="bdrLeftColor_CC6600 bdrRightColor_CC6600">
                <tr>
                  <td valign="top">
                    <!-- middleLayer Starts -->
                      <div id="middleLayer" style="height:390px;"  >
                        <!-- productList Starts -->
                        <div id="productList" style="width:100%;" >
                          <div style="margin-left:3px" class="TxtBold_1">Product List</div> 
                          <div id="productCategoryLink" style="width:210px;height:25px;margin-left:5px;"></div>          
                          <div id="productCategoryDisplay" align="center" style="display:none;overflow:auto;width:210px;height:320px;"></div> 
                          <div id="productDisplay" align="center" style="display:none;overflow:auto;width:210px;height:320px;"></div> 
                        </div>
                        <!-- productList Ends -->
                        <!-- clipartList Starts -->
                        <div id="clipartList" style="display:none;width:100%" >
                          <div style="margin-left:3px" class="TxtBold_1">Insert Cliparts</div> 
                          <div id="clipartSearch" align="center"  style="width:100%px;height:20px;">
                            <table border="0" cellpadding="0" cellspacing="1">
                              <tr>
                                <td>
                                  <span class="text_CC6600">Search :</span> 
                                </td>
                                <td>
                                  <input type="text" class="cmbStd" id="txtClipartSearch" name="txtClipartSearch" style="width:85px" > 
                                </td>
                                <td>
                                  <input class="buttons" type="button" id="btnClipartSearch" name="btnClipartSearch" style="width:65px" value="Search" onclick="return clipartSearchOnClick(this.form);" >
                                </td>
                              </tr>
                            </table>
                          </div>
                          <div id="clipartCategoryLink" style="width:210px;height:25px;margin-left:5px;"></div>                            
                          <div id="clipartDisplay" align="center" style="overflow:auto;width:210px;height:295px;" ></div> 
                          <div id="clipartPageLink" align="center" style="width:210px;height:25px;">
                            <script language="javascript" type="text/javascript">clipartNavBar.render();</script>
                          </div>
                        </div>
                        <!-- clipartList Ends -->
                        <!-- clipartPropertySheet Starts --> 
                        <div id="clipartPropertySheet" style="display:none" >
                            <div style="margin-left:3px" class="TxtBold_1" >Dimension</div>
                            <table border="0" cellpadding="1" cellspacing="1"  style="width:100%" >
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Width</td> 
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Height</td>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Locked</td>
                              </tr>  
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="txtClipartWidth" name="txtClipartWidth" type="text" style="width:36px;height:20px" onchange="return clipartWidthOnChange(this.form);" onkeypress="return decimalOnly(this,event,2);" />px
                                </td>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="txtClipartHeight" name="txtClipartHeight" type="text" style="width:36px;height:20px" onchange="return clipartHeightOnChange(this.form);" onkeypress="return decimalOnly(this,event,2);" />px
                                </td>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="chkClipartLocked" name="chkClipartLocked" type="checkbox" checked  />
                                </td>
                              </tr>
                              <tr>
                                <td align="right" colspan="3" class="bdrColor_E8E8E8">
                                  <input class="buttons" id="btnClipartUpdate" name="btnClipartUpdate" type="button" style="width:70px;" value="Update" onclick="updateClipart(this.form)"/>
                                </td> 
                              </tr>  
                            </table>
                            <div style="margin-left:3px" class="TxtBold_1">&nbsp;</div>
                            <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                              <tr>
                                <td width="100px" align="center" class="bdrColor_E8E8E8 text_CC6600">Rotate</td>
                                <td width="100px" align="center" class="bdrColor_E8E8E8">
                                  <select class="cmbStd" id="cboClipartRotate" name="cboClipartRotate" onchange="return setRotate(this.value);"  style="width:100px;height:20px" >
                                    <option value="0" selected >0</option>
                                    <option value="10">10</option>
                                    <option value="20">20</option>
                                    <option value="30">30</option>
                                    <option value="40">40</option>
                                    <option value="50">50</option>
                                    <option value="60">60</option>
                                    <option value="70">70</option>
                                    <option value="80">80</option>
                                    <option value="90">90</option>
                                    <option value="100">100</option>
                                    <option value="110">110</option>
                                    <option value="120">120</option>
                                    <option value="130">130</option>
                                    <option value="140">140</option>
                                    <option value="150">150</option>
                                    <option value="160">160</option>
                                    <option value="170">170</option>
                                    <option value="180">180</option>
                                    <option value="190">190</option>
                                    <option value="200">200</option>
                                    <option value="210">210</option>
                                    <option value="220">220</option>
                                    <option value="230">230</option>
                                    <option value="240">240</option>
                                    <option value="250">250</option>
                                    <option value="260">260</option>
                                    <option value="270">270</option>
                                    <option value="280">280</option>
                                    <option value="290">290</option>
                                    <option value="300">300</option>
                                    <option value="310">310</option>
                                    <option value="320">320</option>
                                    <option value="330">330</option>
                                    <option value="340">340</option>
                                    <option value="350">350</option>
                                    <option value="360">360</option>
                                  </select>
                                </td>
                              </tr>
                            </table>
                            <div style="margin-left:3px" class="TxtBold_1">Color</div>
                            <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Outline</td>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Color</td> 
                              </tr>
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <script language="javascript" type="text/javascript">clipartStrokeColorPicker.render();</script>
                                </td>                  
                                <td align="center" class="bdrColor_E8E8E8">
                                  <script language="javascript" type="text/javascript">clipartFillColorPicker.render();</script>
                                </td>
                              </tr>
                            </table>
                            <div style="margin-left:3px" class="TxtBold_1">&nbsp;</div>
                            <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                            <tr>
                              <td  align="center" class="bdrColor_E8E8E8">
                                <input class="buttons" id="btnClipartDelete" name="btnClipartDelete" type="button" style="width:70px;" value="Delete" onclick="deleteElement()" />
                              </td>
                            <tr>
                          </table>
                        </div>
                        <!-- clipartPropertySheet Ends -->
                        <!-- ImageLayer Starts -->
                        <div id="imageLayer" style="display:none;width:100%" >
                          <div style="margin-left:3px;" class="TxtBold_1">Images</div>
                          <div style="margin-left:3px;" class="text_CC6600">Upload Images(gif,jpeg/jpg or png)</div> 
                          <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8">
                                <iframe id="customImagePreview" name="customImagePreview"  style="width:70px; height:75px;"  marginwidth=0 marginheight=0 frameborder="0"  class="bdrColor_CC6A00"></iframe>
                                <iframe id="customImageUpload" name="customImageUpload"  style="width:70px; height:75px;display:none;"  marginwidth=0 marginheight=0 frameborder="0"  class="bdrColor_CC6A00"></iframe>
                              </td>
                            </tr>
                            <tr>
                              <td align="right" class="bdrColor_E8E8E8">
                                <input id="customImage" name="customImage" type="file" size="16" class="buttons" onkeydown="return false;"  onchange="return customImageFileOnChange(this);" >
                              </td>
                            </tr>
                            <tr>
                              <td align="right" class="bdrColor_E8E8E8">
                                <input class="buttons" id="btnImageUpload" name="btnImageUpload" type="button" style="width:70px;" value="Upload" onclick="return imageUploadOnClick(this);" />
                              </td>
                            </tr>
                          </table>
                        </div>
                        <!-- ImageLayer Ends -->
                         <!-- imagePropertySheet Starts --> 
                        <div id="imagePropertySheet" style="display:none" >
                            <div style="margin-left:3px" class="TxtBold_1" >Dimension</div>
                            <table border="0" cellpadding="1" cellspacing="1"  style="width:100%" >
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Width</td> 
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Height</td>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Locked</td>
                              </tr>  
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="txtImageWidth" name="txtImageWidth" type="text" style="width:36px;height:20px" onchange="return imageWidthOnChange(this.form);" onkeypress="return decimalOnly(this,event,2);" />px
                                </td>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="txtImageHeight" name="txtImageHeight" type="text" style="width:36px;height:20px" onchange="return imageHeightOnChange(this.form);" onkeypress="return decimalOnly(this,event,2);" />px
                                </td>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <input class="cmbStd" id="chkImageLocked" name="chkImageLocked" type="checkbox" checked/>
                                </td>
                              </tr>
                              <tr>
                                <td align="right" colspan="3" class="bdrColor_E8E8E8">
                                  <input class="buttons" id="btnImageUpdate" name="btnImageUpdate" type="button" style="width:70px;" value="Update" onclick="updateImage(this.form)"/>
                                </td> 
                              </tr>  
                            </table>
                            <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">Rotate</td>
                                <td align="center" class="bdrColor_E8E8E8 text_CC6600">&nbsp;</td>
                              </tr>
                              <tr>
                                <td align="center" class="bdrColor_E8E8E8">
                                  <select class="cmbStd" id="cboImageRotate" name="cboImageRotate" onchange="return setRotate(this.value);"  style="width:100px;height:20px" >
                                    <option value="0" selected >0</option>
                                    <option value="10">10</option>
                                    <option value="20">20</option>
                                    <option value="30">30</option>
                                    <option value="40">40</option>
                                    <option value="50">50</option>
                                    <option value="60">60</option>
                                    <option value="70">70</option>
                                    <option value="80">80</option>
                                    <option value="90">90</option>
                                    <option value="100">100</option>
                                    <option value="110">110</option>
                                    <option value="120">120</option>
                                    <option value="130">130</option>
                                    <option value="140">140</option>
                                    <option value="150">150</option>
                                    <option value="160">160</option>
                                    <option value="170">170</option>
                                    <option value="180">180</option>
                                    <option value="190">190</option>
                                    <option value="200">200</option>
                                    <option value="210">210</option>
                                    <option value="220">220</option>
                                    <option value="230">230</option>
                                    <option value="240">240</option>
                                    <option value="250">250</option>
                                    <option value="260">260</option>
                                    <option value="270">270</option>
                                    <option value="280">280</option>
                                    <option value="290">290</option>
                                    <option value="300">300</option>
                                    <option value="310">310</option>
                                    <option value="320">320</option>
                                    <option value="330">330</option>
                                    <option value="340">340</option>
                                    <option value="350">350</option>
                                    <option value="360">360</option>
                                  </select>
                                </td>
                                <td align="center" class="bdrColor_E8E8E8">&nbsp;</td>
                              </tr>
                            </table>
                            <br>
                            <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                            <tr>
                              <td  align="center" class="bdrColor_E8E8E8">
                                <input class="buttons" id="btnImageDelete" name="btnImageDelete" type="button" style="width:70px;" value="Delete" onclick="deleteElement()" />
                              </td>
                            <tr>
                          </table>
                        </div>
                        <!-- imagePropertySheet Ends -->
                        
                        <!-- textEntry Starts -->
                        <div id="textEntry" style="display:none;width:100%" >
                          <div style="margin-left:3px" class="TxtBold_1">Insert Text</div> 
                          <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8">
                                <textarea class="cmbStd" id="txaTextAdd" name="txaTextAdd" style="width:180px;height:50px"  ></textarea>
                              </td>
                            </tr>
                            <tr>
                              <td align="right" class="bdrColor_E8E8E8">
                                <input class="buttons" id="btnTextAdd" name="btnTextAdd" type="button" style="width:70px;" value="Insert" onclick="addText(this.form)" />
                              </td>
                            <tr>
                          </table>
                        </div>
                        <!-- textEntry Ends -->
                        
                        <!-- textPropertySheet Starts --> 
                        <div id="textPropertySheet" style="display:none" >
                          <div style="margin-left:3px" class="TxtBold_1">Change Text</div> 
                          <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                            <tr>
                              <td colspan="2">
                                <table border="0" cellpadding="1" cellspacing="1" style="width:100%" >
                                  <tr>
                                    <td align="center" class="bdrColor_E8E8E8">
                                      <textarea class="cmbStd" id="txaTextEdit" name="txaTextEdit" style="width:180px;height:50px" ></textarea>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td align="right" class="bdrColor_E8E8E8">
                                      <input class="buttons" id="btnTextUpdate" name="btnTextUpdate" type="button" style="width:70px;" value="Update" onclick="updateText(this.form)" />
                                      <input class="buttons" id="btnTextDelete" name="btnTextDelete" type="button" style="width:70px;" value="Delete" onclick="deleteElement()" />
                                    </td>
                                  <tr>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td width="100px" align="center" class="bdrColor_E8E8E8 text_CC6600">Rotate</td>
                              <td width="100px" align="center" class="bdrColor_E8E8E8">
                                <select class="cmbStd" id="cboTextRotate" name="cboTextRotate" onchange="return setRotate(this.value);"  style="width:60px;height:20px" >
                                  <option value="0" selected >0</option>
                                  <option value="10">10</option>
                                  <option value="20">20</option>
                                  <option value="30">30</option>
                                  <option value="40">40</option>
                                  <option value="50">50</option>
                                  <option value="60">60</option>
                                  <option value="70">70</option>
                                  <option value="80">80</option>
                                  <option value="90">90</option>
                                  <option value="100">100</option>
                                  <option value="110">110</option>
                                  <option value="120">120</option>
                                  <option value="130">130</option>
                                  <option value="140">140</option>
                                  <option value="150">150</option>
                                  <option value="160">160</option>
                                  <option value="170">170</option>
                                  <option value="180">180</option>
                                  <option value="190">190</option>
                                  <option value="200">200</option>
                                  <option value="210">210</option>
                                  <option value="220">220</option>
                                  <option value="230">230</option>
                                  <option value="240">240</option>
                                  <option value="250">250</option>
                                  <option value="260">260</option>
                                  <option value="270">270</option>
                                  <option value="280">280</option>
                                  <option value="290">290</option>
                                  <option value="300">300</option>
                                  <option value="310">310</option>
                                  <option value="320">320</option>
                                  <option value="330">330</option>
                                  <option value="340">340</option>
                                  <option value="350">350</option>
                                  <option value="360">360</option>
                                </select>
                              </td>
                            </tr>
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8 text_CC6600">Size</td>
                              <td align="center" class="bdrColor_E8E8E8 text_CC6600">Outline</td>
                            </tr>
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8">
                                <select class="cmbStd" id="cboTextSize" name="cboTextSize"  onchange="return setTextSize(this.value);" style="width:60px;height:20px" >
                                  <option value="5" >5</option>
                                  <option value="10">10</option>
                                  <option value="15">15</option>
                                  <option value="20" selected >20</option>
                                  <option value="25">25</option>
                                  <option value="30">30</option>
                                  <option value="35">35</option>
                                  <option value="40">40</option>
                                  <option value="45">45</option>
                                  <option value="50">50</option>
                                </select>
                              </td>
                              <td align="center" class="bdrColor_E8E8E8">
                                <select class="cmbStd" id="cboTextStrokeWidth" name="cboTextStrokeWidth" onchange="return setStrokeWidth(this.value);"  style="width:60px;height:20px" >
                                  <option value="default" selected >--</option>
                                  <option value="1.0">1.0</option>
                                  <option value="1.5">1.5</option>
                                  <option value="2.0">2.0</option>
                                  <option value="2.5">2.5</option>
                                  <option value="3.0">3.0</option>
                                  <option value="3.5">3.5</option>
                                  <option value="4.0">4.0</option>
                                </select>
                              </td>
                            </tr>
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8 text_CC6600">Color</td>
                              <td align="center" class="bdrColor_E8E8E8 text_CC6600">Outline color</td>
                            </tr>
                            <tr>
                              <td align="center" class="bdrColor_E8E8E8">
                                <script language="javascript" type="text/javascript">textFillColorPicker.render();</script>
                              </td>
                              <td align="center" class="bdrColor_E8E8E8">
                                <script language="javascript" type="text/javascript">textStrokeColorPicker.render();</script>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" align="center" class="bdrColor_E8E8E8 text_CC6600">Choose Font</td>
                            </tr>
                            <tr>
                              <td colspan="3" align="center" class="bdrColor_E8E8E8">
                                <table border="0" cellpadding="0" cellspacing="1" width="100%">
                                  <tr>
                                    <td width="90%" align="center">
                                      <img src="images/indicator.gif" id="imgSelectedFont" style="cursor:hand; cursor:pointer;"  onclick="return chooseFontOnclick();" alt="Choose Font" >
                                    </td>
                                    <td width="10%">
                                      <input class="buttons" id="btnChooseFont" name="btnChooseFont" type="button" value="..." onclick="return chooseFontOnclick();"  />
                                    </td>
                                  </tr>
                                </table> 
                              </td>
                            </tr>
                          </table>
                        </div>
                        <!-- textPropertySheet Ends -->
                        
                        <!-- fontList Starts -->
                        <div id="fontList" style="display:none;width:100%" >
                          <div style="margin-left:3px" class="TxtBold_1">Select Font Here</div> 
                          <div onclick="return fontBackOnclick();" class="text_CC6A00" style="cursor:hand;cursor:pointer;text-decoration:underline"> [BACK] </div>                            
                          <div id="fontCategoryLink" style="width:210px;height:25px;margin-left:5px;"></div>
                          <div id="fontDisplay" align="center" style="overflow:auto;width:210px;height:310px;"></div> 
                          <div id="fontPageLink" align="center" style="width:210px;height:25px;">
                            <script language="javascript" type="text/javascript">fontNavBar.render();</script>
                          </div>
                        </div>
                        <!-- fontList Ends -->
                
                        <!-- saveDesign Starts -->
                        <div id="saveDesign" style="display:none;width:100%" >
                            <div style="margin-left:3px;" class="TxtBold_1">Save Design</div>
                            <!-- saveWidget Starts -->
                            <div id="saveWidget" style="display:'';height:300px;margin-top:25px" align="center" >
                              <table border="0" width="85%" cellpadding="0" cellspacing="3">
                                <tr>
                                  <td align="right" class="text_CC6600">
                                    Design Name
                                  </td>
                                  <td>
                                    <input class="cmbStd" id="txtDesignName" name="txtDesignName" type="text" maxlength="15"  style="width:70px;"/>
                                  </td>
                                </tr>
                                <tr>
                                  <td align="right" class="text_CC6600">
                                    Email Id
                                  </td>
                                  <td>
                                    <input class="cmbStd" id="txtEmailId" name="txtEmailId" type="text" style="width:70px;"  />
                                  </td>
                                </tr>
                                <tr>
                                  <td colspan="2" align="center">
                                    <div id="saveAsAboveLayer" class="text_CC6600" style="display:none;" > <input id="chkSaveAsAbove" name="chkSaveAsAbove" type="checkbox" onclick="return chkSaveAsAboveOnclick(this);" /> Save As Above </div>
                                  </td>
                                </tr>
                                <tr>
                                  <td colspan="2" align="center">
                                    <input class="buttons" id="btnSaveDesign" name="btnSaveDesign" type="button" style="width:70px;" value="Save" onclick="return saveButtonOnClick();" />
                                    <input class="buttons" id="btnSaveCancle" name="btnSaveCancel" type="button" style="width:70px;" value="Cancel" onclick="alert('Save Cancel')" />
                                  </td>
                                </tr>
                              </table>
                            </div>
                            <!-- saveWidget Ends -->
                            <!-- saveMessage Starts -->
                            <div id="saveMessage" style="display:none;height:300px;margin-top:25px" align="center" >
                            </div>                    
                            <!-- saveMessage Ends -->
                        </div>
                        <!-- saveDesign Ends -->
                        
                        <!-- buyLayer Starts -->
                        <div id="buyLayer" style="display:none;width:100%" >
                          <div style="margin-left:3px" class="TxtBold_1"> Buy Design</div> 
                          <div id="lyrSizeList" class="text_008000" align="center" style="margin-top:140px;width:100%;cursor:hand;cursor:pointer;text-decoration:underline;" onclick="return buyingOnClick();">Continue Buying</div>                         
                        </div>
                        <!-- buyLayer Ends -->
                       
                        <!-- cartLayer Starts -->
                        <div id="cartLayer" style="display:none;width:100%;" >
                          <div style="margin-left:3px" class="TxtBold_1"> Shoping Cart </div>
                          <div style="margin-top:150px;width:100%"  align="center" class="text_008000"> Comming Soon ...</div>
                        </div>
                        <!-- cartLayer Ends -->
                        
                        <!-- openDesign Starts -->
                        <div id="openDesign" style="display:none;width:100%" >
                          <div style="margin-left:3px" class="TxtBold_1"> Open Design</div>
                          <!--<div style="margin-top:150px;width:100%"  align="center" class="text_008000"> Comming Soon ...</div>-->
                          <div id="lyrOpenDesign" style="display:'';width:210px;margin-top:25px" align="center">
                            <table border="0" cellpadding="0" cellspacing="2" >
                              <tr>
                                <td class="text_CC6600" align="right">Design Code : </td>
                                <td>
                                  <input class="cmbStd" id="txtOpenDesignCode" name="txtSearchDesignCode" type="text" style="width:70px;"  />
                                  <input class="buttons" id="btnDesignCodeOpen" name="btnDesignCodeOpen" type="button" style="width:25px;" value="..." title="Open Design" onclick="return btnDesignCodeOpenOnClick();" />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="2" align="center" class="TxtBold_1">OR</td>
                              </tr>
                              <tr>
                                <td class="text_CC6600" align="right">Design Name : </td>
                                <td>
                                  <input class="cmbStd" id="txtOpenDesignName" name="txtSearchDesignName" type="text" maxlength="15"  style="width:100px;"/>
                                </td>
                              </tr>
                              <tr>
                                <td class="text_CC6600" align="right">Email Id : </td>
                                <td>
                                  <input class="cmbStd" id="txtOpenEmailId" name="txtSearchEmailId" type="text" style="width:100px;"  />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="2" align="right">
                                  <input class="buttons" id="btnDesignNameOpen" name="btnDesignNameOpen" type="button" style="width:70px;" value="Open" title="Open Design" onclick="return btnDesignNameOpenOnClick();" />
                                </td>
                              </tr>
                            </table>
                          </div>
                          <div id="lyrOpenDesignView" style="display:none;height:300px;margin-top:25px" align="center" ></div>
                        </div>
                        <!-- openDesign Ends -->
                        
                      </div>
                    <!-- middleLayer Ends -->
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <!-- Bottom Toolbar -->
          <tr>
            <td>
              <div style="float:left;"><img src="images/tab_save.gif" id="tabSave" style="cursor:hand;cursor:pointer;text-decoration:underline" onclick="return saveTabOnClick(this);" alt="Save" width="55px" height="39px"></div>
              <div style="float:left;"><img src="images/tab_buy.gif" id="tabBuy"   style="cursor:hand;cursor:pointer;text-decoration:underline" onclick="return buyTabOnClick(this);" alt="Buy" width="55px" height="39px"></div>
              <div style="float:left;"><img src="images/tab_cart.gif" id="tabCart"  style="cursor:hand;cursor:pointer;text-decoration:underline" onclick="tabOnClick(this,'cartLayer','images/tab_cart_active.gif','images/tab_cart.gif')" alt="Cart" width="55px" height="39px"></div>              
              <div style="float:left;"><img src="images/tab_open.gif" id="tabOpen"  style="cursor:hand;cursor:pointer;text-decoration:underline" onclick="tabOnClick(this,'openDesign','images/tab_open_active.gif','images/tab_open.gif')" alt="Open" width="52px" height="39px"></div>
            </td>
          </tr>
        </table>
      </td>
      <td width="508px" valign="top">
        <table width="505px" align="right" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" class="bdrColor_CC6600">
          <tr>
            <td height="20px" class="bdrBtmColor_CC6600">
              <table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td width="10%" align="right">View:</td>
                  <td width="17%">
                    <select style="width:100%" id="cboSelectView" name="cboSelectView" class="cmbStd" onchange="return selectViewOnChange(this);">
                     <option>View</option>
                    </select>
                  </td>
                  <td width="10%" align="right">Zoom:</td>
                  <td width="13%">
                    <select style="width:100%" id="cboZoom" name="cboZoom" onchange="return zoomOnChange(this);" class="cmbStd">
                      <option value="0.75">75%</option>
                      <option selected value="1">100%</option>
                      <option value="1.25">125%</option>
                      <option value="1.5">150%</option>
                      <option value="1.75">175%</option>
                      <option value="2">200%</option>
                    </select>
                  </td>
                  <td width="50%" align="right"><div id="studioTracking"></div></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="422px" align="center">
              <div id="appletLayer" style="height:422px;">
                <APPLET CODE="bulbul.foff.studio.engine.Studio" CODEBASE="applets/" ARCHIVE="lib/bulbul-design-engine.jar" HEIGHT="422px" WIDTH="500px"  ALIGN="middle" MAYSCRIPT >
                  This browser does not support Applets.
                </APPLET> 
              </div>
            </td>
          </tr>
          <tr>
            <td height="20px" align="center" class="bdrTopColor_CC6600">
            <div id="lyrBelowEngine">
            </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</html:form>
</body>
</html>