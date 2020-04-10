<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<HEAD>
<TITLE><bean:message key="title.MerchandiseAdd" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <html:javascript formName="merchandiseForm" dynamicJavascript="true" staticJavascript="false" />
  <script language="javascript1.1" src="staticJavascript.jsp"></script>
  <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
  <script language="JavaScript" type="text/JavaScript" src="colorslider.js"></script>
  
  <!-- Color Picker Script -->
  <script>
  <!--
    var colorSlider1= new ColorSlider('colorSlider1');
    colorSlider1.type=H_TYPE;
    colorSlider1.okOnClick=btnOkColorSlider1_onclick;
    colorSlider1.cancelOnClick=btnCancelColorSlider1_onclick;
    colorSlider1.borderClass='bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333';
    colorSlider1.backGroundClass='bgColor_C6D4DC';
    colorSlider1.buttonClass='buttons';
    
    var colorSlider2= new ColorSlider('colorSlider2');
    colorSlider2.type=H_TYPE;
    colorSlider2.okOnClick=btnOkColorSlider2_onclick;
    colorSlider2.cancelOnClick=btnCancelColorSlider2_onclick;
    colorSlider2.borderClass='bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333';
    colorSlider2.backGroundClass='bgColor_C6D4DC';
    colorSlider2.buttonClass='buttons';

    var colorPickerColor1Obj=null;
    var colorPickerColor2Obj=null;

    
    function showColorSlider1(){
      var objLyrColorSlider1=MM_findObj('lyrColorSlider1');
      objLyrColorSlider1.style.display='';      
      colorSlider1.setHexColor(document.forms[0].hdnColor1Value.value);
      colorSlider1.init();      
    }
    function hideColorSlider1(){
      var objLyrColorSlider1=MM_findObj('lyrColorSlider1');
      objLyrColorSlider1.style.display='none';      
    }
    function btnOkColorSlider1_onclick(){
      colorPickerColor1Obj.bgColor=colorSlider1.getHexColor();
      document.forms[0].hdnColor1Value.value=colorSlider1.getHexColor();
      hideColorSlider1();
    }
    function btnCancelColorSlider1_onclick(){
      hideColorSlider1();
    }
    
    function showColorSlider2(){
      var thisForm=document.forms[0];
      if (!(thisForm.txtColor2Name.disabled)){
        var objLyrColorSlider2=MM_findObj('lyrColorSlider2');
        objLyrColorSlider2.style.display='';      
        colorSlider2.setHexColor(document.forms[0].hdnColor2Value.value);
        colorSlider2.init();      
      }      
    }
    function hideColorSlider2(){
      var objLyrColorSlider2=MM_findObj('lyrColorSlider2');
      objLyrColorSlider2.style.display='none';
    }
    function btnOkColorSlider2_onclick(){
      colorPickerColor2Obj.bgColor=colorSlider2.getHexColor();
      document.forms[0].hdnColor2Value.value=colorSlider2.getHexColor();
      hideColorSlider2();
    }
    function btnCancelColorSlider2_onclick(){
      hideColorSlider2();
    }
    
    -->
  </script>
  
  <script>
  <!--
    var ADD=0;
    var EDIT=1;
    var DELETE=2;
    var sNoCSP=1;
    var DEFAULT_OP_ROW_INDEX='-1';

    var CELL_RADIO=0;
    var CELL_SNO=1;
    var CELL_COLOR=2;
    var CELL_SIZE_PRICE=3;
    var isAnyPrintableAreaSelected=false;
    
    function btnclick(operation){
      var thisForm=document.forms[0];
      
      if (operation=='save'  && validateMerchandiseForm(thisForm)){
        if(thisForm.displayImgFile.value==''){
          alert('<bean:message key="alert.MerchandDisplayImg.Select.Required" />');
        }else if(!(((thisForm.displayImgFile.value).indexOf(".jpg")!=-1)
             || ((thisForm.displayImgFile.value).indexOf(".gif")!=-1)
             || ((thisForm.displayImgFile.value).indexOf(".png")!=-1)
             || ((thisForm.displayImgFile.value).indexOf(".jpeg")!=-1)
             )){
          alert('<bean:message key="alert.imagesonly" />');
          thisForm.displayImgFile.focus();
        //}else if(thisForm.designImgFile.value==''){
         // alert('<bean:message key="alert.MerchandDesignImg.Select.Required" />');
        //}else if((thisForm.designImgFile.value).indexOf(".svg")==-1){
        //  alert('<bean:message key="alert.choosesvgonly" />');
        //  thisForm.designImgFile.focus();
        }else if(MM_findObj('tblCSPDetail').rows.length<=1){
          alert('<bean:message key="alert.MerchandColorAndSize.Required" />');
        }else if(!isAnyPrintableAreaSelected){
            alert("Select Any Printable Area.");
        }else{
          thisForm.action="merchandiseAddAction.do"
          thisForm.target="_self";
          thisForm.submit();
        }
      }
    }

    function btnCancel_onClick(){
      var thisForm=document.forms[0];
      thisForm.action="merchandiseAddAction.do"
      thisForm.target="_self";
      thisForm.submit();
    }

  
    function displayImgFile_onChange(){
      var thisForm = document.forms[0];
      if(!(((thisForm.displayImgFile.value).indexOf(".jpg")!=-1)
         || ((thisForm.displayImgFile.value).indexOf(".gif")!=-1)
         || ((thisForm.displayImgFile.value).indexOf(".png")!=-1)
         || ((thisForm.displayImgFile.value).indexOf(".jpeg")!=-1)
         )){
        alert('<bean:message key="alert.imagesonly" />');
        thisForm.displayImgFile.focus();
        return false;
      }
      thisForm.target="displayPreview";
      thisForm.action="merchandiseDisplayImgPreviewAction.do";
      thisForm.submit();
    }
    
    //function designImgFile_onChange(){
    //  var thisForm = document.forms[0];
    //  if((thisForm.designImgFile.value).indexOf(".svg")==-1){
    //    alert('<bean:message key="alert.choosesvgonly" />');
    //    thisForm.designImgFile.focus();
    //    return false;
    //  }
    //  thisForm.target="designPreview";
    //  thisForm.action="merchandiseDesignImgPreviewAction.do";
    //  thisForm.submit();
    //}

    function window_onload(){
      var thisForm=document.forms[0];
      colorPickerColor1Obj=MM_findObj('colorPickerColor1');
      colorPickerColor2Obj=MM_findObj('colorPickerColor2');
      colorPickerColor1Obj.bgColor=thisForm.hdnColor1Value.value;
      colorPickerColor2Obj.bgColor=thisForm.hdnColor2Value.value;
    }

    function chkColor2_onclick(){
      var thisForm=document.forms[0];
      thisForm.txtColor2Name.disabled=(!(thisForm.chkColor2.checked));
      thisForm.txtColor2Name.value="white";
      document.forms[0].hdnColor2Value.value="#ffffff";
      colorPickerColor2Obj.bgColor="#ffffff";
    }

    function btnOk_onclick(){

      var thisForm=document.forms[0];
      var isAnySizeSeleted=false;
      
      //Validating Color1 Name 
      if(trim(thisForm.txtColor1Name.value).length<=0) {
        alert('<bean:message key="alert.Color1Name.Enter.Required" />');
        thisForm.txtColor1Name.focus();
        return false;
      }

      //Validating Color2 Name 
      if(thisForm.chkColor2.checked && trim(thisForm.txtColor2Name.value).length<=0) {
        alert('<bean:message key="alert.Color2Name.Enter.Required" />');
        thisForm.txtColor2Name.focus();
        return false;
      }
      
      //Color1 Name , Value  and Color2 Name Value cannot be same 
      if(thisForm.chkColor2.checked){
        if((trim(thisForm.txtColor1Name.value))==(trim(thisForm.txtColor2Name.value))){
          alert('<bean:message key="alert.Error.SameColorName" />');
          thisForm.txtColor1Name.focus();
          return false;
        }
        if((trim(thisForm.hdnColor1Value.value))==(trim(thisForm.hdnColor2Value.value))){
          alert('<bean:message key="alert.Error.SameColorValue" />');
          return false;
        }
      }

      //Validating Selected Sizes                     
      if (typeof thisForm.chkSize.length !='undefined'){
        for(var chkIndex=0; chkIndex<thisForm.chkSize.length;  chkIndex++){
          if(thisForm.chkSize[chkIndex].checked){
            isAnySizeSeleted=true;  
            if ((trim(thisForm.txtPriceInTbl[chkIndex].value)).length<=0){
              alert('<bean:message key="alert.Error.PriceCannotBeEmpty" />' + thisForm.hdnSizeTextInTbl[chkIndex].value);
              thisForm.txtPriceInTbl[chkIndex].focus();
              return false;
            }else if (eval((trim(thisForm.txtPriceInTbl[chkIndex].value)))==0){
              alert('<bean:message key="alert.Error.PriceCannotBeZero" />' + thisForm.hdnSizeTextInTbl[chkIndex].value);
              thisForm.txtPriceInTbl[chkIndex].select(1,trim(thisForm.txtPriceInTbl[chkIndex].value).length);
              thisForm.txtPriceInTbl[chkIndex].focus();
              return false;
            }            
          }
        }
      }else{
        if(thisForm.chkSize.checked){
          isAnySizeSeleted=true;  
           if ((trim(thisForm.txtPriceInTbl.value)).length<=0){
              alert('<bean:message key="alert.Error.PriceCannotBeEmpty" />' + thisForm.hdnSizeTextInTbl.value);
              thisForm.txtPriceInTbl.focus();
              return false;
            }else if (eval((trim(thisForm.txtPriceInTbl.value)))==0){
              alert('<bean:message key="alert.Error.PriceCannotBeZero" /> ' + thisForm.hdnSizeTextInTbl.value);
              thisForm.txtPriceInTbl.select(1,trim(thisForm.txtPriceInTbl.value).length);
              thisForm.txtPriceInTbl.focus();
              return false;
            }  
        }
      }
      
      if(!(isAnySizeSeleted)){
        alert('<bean:message key="alert.MerchandSize.Select.Required" />');
        return false;
      }

      if (!(uniqueColor(thisForm.hdnOpRowIndex.value))){
        alert('<bean:message key="alert.Error.SameColorComb" />')
        return false;
      }
      operation();
      btnDetailCancel_onclick();
    }

    function btnDetailCancel_onclick(){
      var thisForm=document.forms[0];
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');
      var opRowIndex=thisForm.hdnOpRowIndex.value;
      if (opRowIndex!=DEFAULT_OP_ROW_INDEX){
        //To set the bg color of the cells back to normal
        var objRow2Edit =objCSPDetailTbl.rows[opRowIndex];
        for(var index=0; index<objRow2Edit.cells.length;index++){
          objRow2Edit.cells[index].bgColor="#ffffff";
        }
        //To Deselect the Radio
        if(typeof thisForm.radioSelect.length != 'undefined' ){
          thisForm.radioSelect[opRowIndex-1].checked=false;
        }else{
          thisForm.radioSelect.checked=false;
        }
      }

      //Clearing Color1
      thisForm.txtColor1Name.value="white";
      thisForm.hdnColor1Value.value="#ffffff";
      colorPickerColor1Obj.bgColor="#ffffff";
      
      //Clearing Color2
      thisForm.chkColor2.checked=false;
      chkColor2_onclick(); 

      //Clearing Selected Size and Price              
      thisForm.chkSizeAll.checked=false;
      chkSizeAll_onclick(thisForm.chkSizeAll);
      
      //setting hdnCSPFlag to default
      thisForm.hdnCSPFlag.value=ADD;


      //setting hdnOpRowIndex  to default
      thisForm.hdnOpRowIndex.value=DEFAULT_OP_ROW_INDEX;

      //Enabling Delete, Edit button
      thisForm.btnDelete.disabled=false;
      thisForm.btnEdit.disabled=false;

      //Show Radio buttons
      hideShowRadio(true);

    }

    function operation(){
      var thisForm=document.forms[0];  
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');
      var objCell=null;
      var objRow=null;

      var color1Value;
      var color2Value;
      var color1Name;
      var color2Name;
      
      var colorAllName;
      var colorHTMLString='';
      var sizePriceHTMLString='';

      var sizeValueString='';
      var sizeTextString='';
      var priceValueString='';
      var priceTextString='';

      var sizeValue;
      var sizeText;
      var priceValue;
      var priceText;

      var opRowIndex;
      var opSNo;

      

      if(thisForm.hdnCSPFlag.value==ADD){
        opRowIndex=objCSPDetailTbl.rows.length;
        opSNo=sNoCSP;
        
        objRow =objCSPDetailTbl.insertRow(opRowIndex);
        objRow.bgColor='#FFFFFF';
        objRow.height='20px';
        

        //Radio Button
        objCell=objRow.insertCell(CELL_RADIO);
        objCell.align='center';
        objCell.innerHTML='<input type="radio" id="radioSelect" name="radioSelect"  onclick="return radioSelect_onclick(this);" />';
        

        // S.No.
        objCell=objRow.insertCell(CELL_SNO);
        objCell.align='right';
        objCell.innerHTML=opSNo;


        //Color
        colorHTMLString=colorHTMLString+'<table border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >';
        colorHTMLString=colorHTMLString+'<tr><td id="tdColor'+ opRowIndex +'" name="tdColor'+ opRowIndex +'" height="18px" width="36px" >';
        colorHTMLString=colorHTMLString+'<div id="lyrColor1'+ opRowIndex +'" name="lyrColor1'+ opRowIndex +'" style="float:left;height:100%;width:0%;"></div>';
        colorHTMLString=colorHTMLString+'<div id="lyrColor2'+ opRowIndex +'" name="lyrColor2'+ opRowIndex +'" style="float:left;height:100%;width:0%;"></div>';
        colorHTMLString=colorHTMLString+'</td></tr></table>';
        
        colorHTMLString=colorHTMLString+'<input type="hidden" id="hdnColorOneValue" name="hdnColorOneValue" value="" />';
        colorHTMLString=colorHTMLString+'<input type="hidden" id="hdnColorTwoValue" name="hdnColorTwoValue" value="" />';        
        colorHTMLString=colorHTMLString+'<input type="hidden" id="hdnColorOneName" name="hdnColorOneName" value="" />';
        colorHTMLString=colorHTMLString+'<input type="hidden" id="hdnColorTwoName" name="hdnColorTwoName" value="" />';        


        objCell=objRow.insertCell(CELL_COLOR);
        objCell.align='center';
        objCell.innerHTML=colorHTMLString;
        
        //Size/Price

        sizePriceHTMLString=sizePriceHTMLString+'<table id="tblSizePrice'+ opRowIndex +'" name="tblSizePrice'+ opRowIndex +'" border="0" cellpadding="3" cellspacing="1" bgcolor="#80A0B2" >';
        sizePriceHTMLString=sizePriceHTMLString+'<tr></tr></table>';

        sizePriceHTMLString=sizePriceHTMLString+'<input type="hidden" id="hdnSizeValue" name="hdnSizeValue" value="" />'
        sizePriceHTMLString=sizePriceHTMLString+'<input type="hidden" id="hdnSizeText" name="hdnSizeText" value="" />'
        sizePriceHTMLString=sizePriceHTMLString+'<input type="hidden" id="hdnPriceValue" name="hdnPriceValue" value="" />'
        sizePriceHTMLString=sizePriceHTMLString+'<input type="hidden" id="hdnPriceText" name="hdnPriceText" value="" />'



        objCell=objRow.insertCell(CELL_SIZE_PRICE);
        objCell.align='left';
        objCell.innerHTML=sizePriceHTMLString;
      
        sNoCSP++;
        
      }else if(thisForm.hdnCSPFlag.value==EDIT){
        opRowIndex=thisForm.hdnOpRowIndex.value;
      } 
      
            
      var objRadioSelect=thisForm.radioSelect;
      var objColorOneValue=thisForm.hdnColorOneValue;
      var objColorTwoValue=thisForm.hdnColorTwoValue;
      var objColorOneName=thisForm.hdnColorOneName;
      var objColorTwoName=thisForm.hdnColorTwoName;
      var objSizeValue=thisForm.hdnSizeValue;
      var objSizeText=thisForm.hdnSizeText;
      var objPriceValue=thisForm.hdnPriceValue;
      var objPriceText=thisForm.hdnPriceText;
      var objLyrColor1=MM_findObj('lyrColor1'+opRowIndex);
      var objLyrColor2=MM_findObj('lyrColor2'+opRowIndex);
      var objTblSizePrice=MM_findObj('tblSizePrice'+opRowIndex);
      var objTdColor=MM_findObj('tdColor'+opRowIndex);

      if(typeof objColorOneValue.length != 'undefined'){
        objRadioSelect=objRadioSelect[opRowIndex-1];
        objColorOneValue=objColorOneValue[opRowIndex-1];
        objColorTwoValue=objColorTwoValue[opRowIndex-1];
        objColorOneName=objColorOneName[opRowIndex-1];
        objColorTwoName=objColorTwoName[opRowIndex-1];
        
        objSizeValue=objSizeValue[opRowIndex-1];
        objSizeText=objSizeText[opRowIndex-1];
        objPriceValue=objPriceValue[opRowIndex-1];
        objPriceText=objPriceText[opRowIndex-1];
      }

      color1Value=thisForm.hdnColor1Value.value;
      color2Value=thisForm.hdnColor2Value.value;
      color1Name=thisForm.txtColor1Name.value;
      color2Name=thisForm.txtColor2Name.value;

      objRadioSelect.value=opRowIndex;
      objColorOneValue.value=color1Value;
      objColorOneName.value=color1Name;

      objLyrColor1.style.backgroundColor=color1Value;
      
      if(!(thisForm.txtColor2Name.disabled)){
        colorAllName=color1Name+'/'+color2Name;
        objColorTwoValue.value=color2Value;
        objColorTwoName.value=color2Name;
        objLyrColor1.style.width='50%';
        objLyrColor2.style.width='50%';
        objLyrColor2.style.backgroundColor=color2Value;
      }else{
        colorAllName=color1Name;
        objColorTwoValue.value="";
        objColorTwoName.value="";
        objLyrColor1.style.width='100%';
        objLyrColor2.style.width='0%';
      }
      
      objTdColor.title=colorAllName;

      sizePriceHTMLString='';                         
      if (typeof thisForm.chkSize.length !='undefined'){
        for(var chkIndex=0; chkIndex<thisForm.chkSize.length;  chkIndex++){
          if(thisForm.chkSize[chkIndex].checked){
            sizeValue=thisForm.hdnSizeValueInTbl[chkIndex].value;
            sizeText=thisForm.hdnSizeTextInTbl[chkIndex].value;
            priceValue=thisForm.txtPriceInTbl[chkIndex].value;
            priceText=thisForm.txtPriceInTbl[chkIndex].value;
            
            var cellIndex=objTblSizePrice.rows[0].cells.length;
            objTblSizePrice.rows[0].insertCell(cellIndex);
            objTblSizePrice.rows[0].cells[cellIndex].bgColor='#ffffff';
            objTblSizePrice.rows[0].cells[cellIndex].innerHTML=sizeText+'/'+priceText;
            
        
            sizeValueString=sizeValueString+sizeValue+'/';
            sizeTextString=sizeTextString+sizeText+'/';
            priceValueString=priceValueString+priceValue+'/';
            priceTextString=priceTextString+priceText+'/';
          }
        }
      }else{
        if(thisForm.chkSize.checked){
          sizeValue=thisForm.hdnSizeValueInTbl.value;
          sizeText=thisForm.hdnSizeTextInTbl.value;
          priceValue=thisForm.txtPriceInTbl.value;
          priceText=thisForm.txtPriceInTbl.value;

          var cellIndex=objTblSizePrice.rows[0].cells.length;
          objTblSizePrice.rows[0].insertCell(cellIndex);
          objTblSizePrice.rows[0].cells[cellIndex].bgColor='#ffffff';
          objTblSizePrice.rows[0].cells[cellIndex].innerHTML=sizeText+'/'+priceText;
        
          sizeValueString=sizeValueString+sizeValue+'/';
          sizeTextString=sizeTextString+sizeText+'/';
          priceValueString=priceValueString+priceValue+'/';
          priceTextString=priceTextString+priceText+'/';
        }
      }

      //objTblSizePrice.rows[0].innerHTML=sizePriceHTMLString;

      objSizeValue.value=sizeValueString;
      objSizeText.value=sizeTextString;
      objPriceValue.value=priceValueString;
      objPriceText.value=priceTextString;

    }

    function uniqueColor(exceptIndex){
      var thisForm = document.forms[0];
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');
      var objHdnColor1Value=null;
      var objHdnColor2Value=null;
      var colorValueLt=null;
      var colorValueRt=null;
      
      if(!(thisForm.txtColor2Name.disabled)){
        colorValueRt=(thisForm.hdnColor1Value.value + thisForm.hdnColor2Value.value);
      }else{
        colorValueRt=thisForm.hdnColor1Value.value;
      }

      for(var index=1;index<objCSPDetailTbl.rows.length;index++){
        if (exceptIndex!=index){

          objHdnColor1Value=thisForm.hdnColorOneValue;
          objHdnColor2Value=thisForm.hdnColorTwoValue;

          if (typeof objHdnColor1Value.length != 'undefined'){
            objHdnColor1Value=objHdnColor1Value[index-1];
            objHdnColor2Value=objHdnColor2Value[index-1];
          }
          
          if (((trim(objHdnColor1Value.value)).length>0) && ((trim(objHdnColor2Value.value)).length>0)){
            colorValueLt=(objHdnColor1Value.value + objHdnColor2Value.value);
          }else if((trim(objHdnColor1Value.value)).length>0){
            colorValueLt=objHdnColor1Value.value; 
          }
          if (colorValueLt==colorValueRt){
            return false;
            break;
          }
        }
      }

      return true;
    }

    function btnEdit_onclick(){
      var thisForm=document.forms[0];
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');
      var opRowIndex;
      
      if(thisForm.hdnOpRowIndex.value==DEFAULT_OP_ROW_INDEX){
        alert('<bean:message key="alert.ForEdit.Select.Required" />');
        return false;
      }

      opRowIndex=thisForm.hdnOpRowIndex.value;
      
      
      var objRow =objCSPDetailTbl.rows[opRowIndex];
      for(var index=0; index<objRow.cells.length;index++){
        objRow.cells[index].bgColor="#e8e8e8";
      }
      

      var objColor1Name=thisForm.hdnColorOneName; 
      var objColor1Value=thisForm.hdnColorOneValue; 
      
      var objColor2Name=thisForm.hdnColorTwoName; 
      var objColor2Value=thisForm.hdnColorTwoValue; 

      if(typeof objColor1Name.length != 'undefined'){
        objColor1Name=objColor1Name[opRowIndex-1];
        objColor1Value=objColor1Value[opRowIndex-1];
        objColor2Name=objColor2Name[opRowIndex-1];
        objColor2Value=objColor2Value[opRowIndex-1];
      }
      
      thisForm.txtColor1Name.value=objColor1Name.value;
      thisForm.hdnColor1Value.value=objColor1Value.value;
      thisForm.txtColor2Name.value=objColor2Name.value;
      thisForm.hdnColor2Value.value=objColor2Value.value;

      
      colorPickerColor1Obj.bgColor=thisForm.hdnColor1Value.value;

      if(((trim(thisForm.txtColor2Name.value)).length==0) && ((trim(thisForm.hdnColor2Value.value)).length==0)){
        thisForm.txtColor2Name.value='white';
        thisForm.hdnColor2Value.value='#ffffff';        
      }else{
        thisForm.txtColor2Name.value=objColor2Name.value;
        thisForm.hdnColor2Value.value=objColor2Value.value;
        colorPickerColor2Obj.bgColor=thisForm.hdnColor2Value.value;
        thisForm.chkColor2.checked=true;
        thisForm.txtColor2Name.disabled=false;
      }

      objSizeValue=thisForm.hdnSizeValue;
      objSizeText=thisForm.hdnSizeText;
      objPriceValue=thisForm.hdnPriceValue;
      objPriceText=thisForm.hdnPriceText;
      
      if(typeof objSizeValue.length != 'undefined'){
        objSizeValue=objSizeValue[opRowIndex-1];
        objSizeText=objSizeText[opRowIndex-1];
        objPriceValue=objPriceValue[opRowIndex-1];
        objPriceText=objPriceText[opRowIndex-1];
      }

      sizeValueArray=(objSizeValue.value).split('/');
      sizeTextArray=(objSizeText.value).split('/');
      priceValueArray=(objPriceValue.value).split('/');
      priceTextArray=(objPriceText.value).split('/');
      
      for(var index=0; index<(sizeValueArray.length-1); index++){
        if (typeof thisForm.chkSize.length !='undefined'){
          for(var chkIndex=0; chkIndex<thisForm.chkSize.length;  chkIndex++){
            if (sizeValueArray[index]==thisForm.hdnSizeValueInTbl[chkIndex].value){
              thisForm.chkSize[chkIndex].checked=true;
              chkSize_onclick(thisForm.chkSize[chkIndex]);
              thisForm.txtPriceInTbl[chkIndex].value=priceTextArray[index];
              break;
            }
          }
        }else{
          if (sizeValueArray[index]==thisForm.hdnSizeValueInTbl.value){
            thisForm.chkSize.checked=true;
            chkSize_onclick(thisForm.chkSize);
            thisForm.txtPriceInTbl.value=priceTextArray[index];
          }
        }
      }
      
      thisForm.hdnCSPFlag.value=EDIT;
      thisForm.btnDelete.disabled=true;
      thisForm.btnEdit.disabled=true;
      hideShowRadio(false);
    }


    function btnDelete_onclick(){
      var thisForm=document.forms[0];
      if(thisForm.hdnOpRowIndex.value==DEFAULT_OP_ROW_INDEX){
        alert('<bean:message key="alert.ForDelete.Select.Required" />');
        return false;
      }
      var deleteRow = confirm("Are You Sure to Delete");
      if(!(deleteRow)){
          return false;
      }

      var objCSPDetailTbl=MM_findObj('tblCSPDetail');  
      objCSPDetailTbl.deleteRow(thisForm.hdnOpRowIndex.value);
      rearrangeSNo();
      btnDetailCancel_onclick();
    }
    
    function radioSelect_onclick(objMe){
      var thisForm=document.forms[0];
      thisForm.hdnOpRowIndex.value=objMe.value;
    }

    function rearrangeSNo(){
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');  
      var thisForm = document.forms[0];
      var opRowIndex=thisForm.hdnOpRowIndex.value;
      for(var tableIndex=1;tableIndex<objCSPDetailTbl.rows.length;tableIndex++){
        if (tableIndex>=opRowIndex){
          var objLyrColor1=MM_findObj('lyrColor1'+(tableIndex+1));
          objLyrColor1.id=('lyrColor1'+tableIndex);
          objLyrColor1.name=('lyrColor1'+tableIndex);
          var objLyrColor2=MM_findObj('lyrColor2'+(tableIndex+1));
          objLyrColor2.id=('lyrColor2'+tableIndex);
          objLyrColor2.name=('lyrColor2'+tableIndex);
          var objTblSizePrice=MM_findObj('tblSizePrice'+(tableIndex+1));
          objTblSizePrice.id=('tblSizePrice'+tableIndex);
          objTblSizePrice.name=('tblSizePrice'+tableIndex);
          var objTdColor=MM_findObj('tdColor'+(tableIndex+1));
          objTdColor.id=('tdColor'+tableIndex);
          objTdColor.name=('tdColor'+tableIndex);
        }
        objCSPDetailTbl.rows[tableIndex].cells[1].innerHTML=tableIndex;
        if(typeof thisForm.radioSelect.length != 'undefined' ){
          thisForm.radioSelect[tableIndex-1].value=tableIndex;
          thisForm.radioSelect[tableIndex-1].checked=false;
        }else{
          thisForm.radioSelect.value=tableIndex;
          thisForm.radioSelect.checked=false;
        }
        
      }
      sNoCSP=objCSPDetailTbl.rows.length;
    }

    function hideShowRadio(showTheRadio){
      var objCSPDetailTbl=MM_findObj('tblCSPDetail');
      var showHide;
      if(showTheRadio){
        showHide='';
      }else{
        showHide='none';
      }      
      for (var index=0; index<objCSPDetailTbl.rows.length;index++){
        objCSPDetailTbl.rows[index].cells[0].style.display=showHide;
      }      
    }

    function chkSize_onclick(me){
      var thisForm = me.form;
      var index=me.alt;
      var objTxtPriceInTbl=thisForm.txtPriceInTbl;
      if(typeof thisForm.txtPriceInTbl.length !='undefined'){
        objTxtPriceInTbl=thisForm.txtPriceInTbl[index];
      }
      objTxtPriceInTbl.disabled=!(me.checked);
      objTxtPriceInTbl.value='0.00';
      if(!objTxtPriceInTbl.disabled){
        objTxtPriceInTbl.focus();
      }
      objTxtPriceInTbl.select();
    }

    function chkSizeAll_onclick(me){
      var thisForm=me.form;
      if(typeof thisForm.chkSize.length !='undefined'){
        for(var chkIndex=0;  chkIndex<thisForm.chkSize.length; chkIndex++){
          thisForm.chkSize[chkIndex].checked=me.checked;
          chkSize_onclick(thisForm.chkSize[chkIndex]);
        }
      }else{
        thisForm.chkSize.checked=me.checked;
        chkSize_onclick(thisForm.chkSize);
      }
    }
    
    function chkPrintableArea_onclick(me){
      var thisForm=me.form;
      var isAllPrintableAreaChecked=false;
      var noOfCheckedRows=0;
      if(typeof thisForm.chkPrintableArea.length !='undefined'){
        for(var chkIndex=0;  chkIndex<thisForm.chkPrintableArea.length; chkIndex++){
          if(thisForm.chkPrintableArea[chkIndex].checked==true){
            noOfCheckedRows=noOfCheckedRows+1;
          }
        }
        //alert("noOfCheckedRows:"+noOfCheckedRows+"  Total:"+thisForm.chkPrintableArea.length);
        if (noOfCheckedRows==thisForm.chkPrintableArea.length){
          isAllPrintableAreaChecked=true;
        }
        if(!noOfCheckedRows==0){
          isAnyPrintableAreaSelected=true;
        }else{
          isAnyPrintableAreaSelected=false;
        }
      }
      if(isAllPrintableAreaChecked){
        thisForm.chkPrintableAreaAll.checked=true;
      }else{
        thisForm.chkPrintableAreaAll.checked=false;
      }
    }
    function chkPrintableAreaAll_onclick(me){
      var thisForm=me.form;
      
      if(typeof thisForm.chkPrintableArea.length !='undefined'){
        for(var chkIndex=0;  chkIndex<thisForm.chkPrintableArea.length; chkIndex++){
          thisForm.chkPrintableArea[chkIndex].checked=me.checked;
          isAnyPrintableAreaSelected=true;
          //chkSize_onclick(thisForm.chkSize[chkIndex]);
        }
      }else{
        thisForm.chkPrintableArea.checked=me.checked;
      }
      if(me.checked==true){
        isAnyPrintableAreaSelected=true;
      }else{
        isAnyPrintableAreaSelected=false;
      }
    }
    
    //-->
  </script>
</HEAD>
<BODY onload="return window_onload();">
<html:form action="/merchandiseAddAction" enctype="multipart/form-data">
<html:hidden property="hdnMerchandiseCategoryTblPk" />
<html:hidden property="hdnSearchPageNo" /> 
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
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" align="center" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <!-- Add Table Starts -->
      <table width="98%" border="0" cellpadding="0" cellspacing="2" class="bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333" >
        <tr>
          <th height="25px" ><bean:message key="page.MerchandiseAdd" /></th>
        <tr>
        <tr><td height="5px" ></td></tr>
        <tr>
          <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td width="36%" align="center" >
                  <!-- Merchandise Details Starts-->
                  <table width="333px" height="400px" border="0" cellpadding="1" cellspacing="2" class="bdrColor_336666">
                    <tr>
                      <th height="15px" colspan="2"><div align="center"><bean:message key="tbl.MerchandiseDetails" /></div></th>
                    </tr>
                    <tr>
                      <td height="15px" width="118px"><div align="right" valign="top"><bean:message key="txt.Merchandise" />:</div></td>
                      <td width="200px">                
                        <html:text property="txtMerchandise" styleClass="bdrColor_336666" style="width:200px" maxlength="25" />              
                      </td>
                    </tr>
                    <tr>
                      <td height="15px" width="118px" ><div align="right" valign="top"><bean:message key="txt.MerchandiseCategory" />:</div></td>
                      <td width="200px">                
                        <html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:200px" readonly="true" />              
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MerchandiseDesc" />:</div></td>
                      <td>
                        <html:textarea property="txaMerchandiseDesc" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px"></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MerchandiseComm" />:</div></td>
                      <td>
                        <html:textarea property="txaMerchandiseComm" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px"></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.MaterialDetail" />:</div></td>
                      <td>
                        <html:textarea property="txaMaterialDetail" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px"></html:textarea>
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right"><bean:message key="txt.MinimumQuantity" />:</div></td>
                      <td height="15px">
                        <html:text property="txtMinimumQuantity" styleClass="bdrColor_336666" onkeypress="return integerOnly(event);" style="width:200px" />
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><div align="right"><bean:message key="txa.DeliveryNote" />: </div></td>
                      <td>
                        <html:textarea property="txaDeliveryNote" cols="" rows="3" styleClass="bdrColor_336666" style="width:200px; height:65px"></html:textarea>              
                      </td>
                    </tr>
                    <tr>
                      <td><div align="right"><bean:message key="cbo.MerchandiseDecoration" />:</div></td>
                      <td height="15px">
                        <html:select property="cboMerchandiseDecoration" style="width:200px" styleClass="bdrColor_336666">
                          <logic:iterate id="decoration" name="merchandiseDecorations" >
                            <bean:define id="merchandiseDecorationTblPk" name="decoration" property="merchandiseDecorationTblPk" />
                              <html:option  value="<%=(String)merchandiseDecorationTblPk%>"><bean:write name="decoration" property="decorationName" /></html:option>
                          </logic:iterate>
                        </html:select>
                      </td>
                    </tr>
                  </table>
                  <!-- Merchandise Details Ends-->
                </td>
                <td>
                  <!-- Merchandise Images Starts --> 
                  <table width="175px" height="400px" border="0" cellpadding="0" cellspacing="1" class="bdrColor_336666" >
                    <tr>
                      <th><bean:message key="tbl.DisplayImage" /></th>
                    </tr>
                    <tr>
                      <td align="center">
                        <div align="center" style="overflow:auto;height:147px;width:164px;">
                          <iframe name="displayPreview" id="displayPreview" frameborder="0" align="top" class="bdrColor_336666" height="95%" width="95%"  ></iframe>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td align="center">
                        <input type="file" id="displayImgFile" name="displayImgFile" class="bdrColor_336666" size="13" onkeypress="return false;" onchange="return displayImgFile_onChange();"/>
                      </td>
                    </tr>
                    <tr>
                      <th><bean:message key="tbl.PrintableArea" /></th>
                    </tr>
                    <tr>
                      <td valign="top">
                        <div style="overflow:auto;width:164px%;height:150px">
                          <table border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2" width="100%">
                            <tr>
                              <th width="6%"><input type="checkbox" id="chkPrintableAreaAll" name="chkPrintableAreaAll" onclick="return chkPrintableAreaAll_onclick(this);" /></th>
                              <th width="69%"><bean:message key="tbl.PrintableArea" /></th>
                            </tr>
                            <logic:iterate id="printableArea" name="printableAreas" indexId="currentIndex">   
                            
                            <bean:define id="PrintableAreaTblPk" name="printableArea" property="printableAreaTblPk" />
                            <tr bgcolor="#ffffff" >
                              <td>
                                <html:multibox property="chkPrintableArea" onclick="return chkPrintableArea_onclick(this);" alt="<%=Integer.toString(currentIndex.intValue())%>" value="<%=(String)PrintableAreaTblPk %>" />
                              </td>
                              <td>
                                <bean:write name="printableArea" property="printableAreaName"/>
                              </td>
                            </tr>
                            </logic:iterate> 
                          </table>
                        </div>
                      </td>
                    </tr>                  
                  </table>
                  <!-- Merchandise Images Ends --> 
                </td>
                <td width="45%" >
                  <!-- Merchandise Color Size and Price Details Starts -->
                  <table width="99%" height="400px" border="0" cellpadding="0" cellspacing="2" class="bdrColor_336666" >
                    <tr>
                      <th height="15px" ><bean:message key="tbl.Color/Size/PriceList" /></th>
                    </tr>
                    <tr>
                      <td height="15px">
                      <html:button property="btnEdit" styleClass="buttons" onclick="return btnEdit_onclick();"><bean:message key="btn.Edit" /></html:button>
                      <html:button property="btnDelete" styleClass="buttons" onclick="return btnDelete_onclick();"><bean:message key="btn.Delete" /></html:button>
                      <input type="hidden" id="hdnCSPFlag" name="hdnCSPFlag" value="0" />
                      <input type="hidden" id="hdnOpRowIndex" name="hdnOpRowIndex" value="-1" />
                      </td>
                    </tr>
                    <tr>
                      <td valign="top" >
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td  valign="top"  >
                              <div id="lyrColorSlider1" style="position:absolute;display:none">
                                <!--Script below generates a color Slider -->
                                <SCRIPT language=JavaScript>
                                  colorSlider1.render();
                                </SCRIPT>	
                              </div>
                            </td>
                            <td valign="top" >
                              <div id="lyrColorSlider2" style="position:absolute;display:none">
                                <!--Script below generates a color Slider -->
                                <SCRIPT language=JavaScript>
                                  colorSlider2.render();
                                </SCRIPT>	
                              </div>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top" align="left">
                        <div align="center" style="overflow:auto;height:190px;width:410px;">
                            <table id="tblCSPDetail" name="tblCSPDetail" width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#80A0B2">
                              <tr>
                                <th width="5%" height="15px">&nbsp;</th>
                                <th width="5%" height="15px"><bean:message key="tbl.Sno" /></th>
                                <th width="15%" height="15px"><bean:message key="tbl.Color" /></th>
                                <th align="left" height="15px">&nbsp;&nbsp;&nbsp;<bean:message key="tbl.SizePrice" /></th>
                              </tr>
                            </table>
                          </div>
                      </td>
                    </tr>
                    <tr>
                      <td height="160px" valign="top">
                        <table width="420px" border="0" align="center" cellpadding="1" cellspacing="2" class="bdrColor_336666">
                          <tr>
                            <td ><div align="right"><bean:message key="txt.Color1" /></div></td>
                            <td >
                              <table border="0" cellpadding="0" cellspacing="1" >
                                <tr>
                                  <td >
                                    <input type="text" id="txtColor1Name" name="txtColor1Name" class="bdrColor_336666" style="width:100px" value="white"/>
                                    <input type="hidden" id="hdnColor1Value" name="hdnColor1Value" value="#ffffff" />
                                  </td>
                                  <td align="center">
                                  <table id="colorPickerColor1" style="width:17px;height:17px" class="bdrColor_336666" ><tr><td></td></tr></table>
                                  </td>
                                  <td align="center">
                                    <div onclick="return showColorSlider1();" class="imgClrSwatch" ></div>                   
                                  </td>
                                </tr>
                              </table>
                            </td>
                            <td><div align="right"><bean:message key="txt.Color2" /></div></td>
                            <td >
                              <table border="0" cellpadding="0" cellspacing="1" >
                                <tr>
                                  <td>
                                    <input type="checkbox" id="chkColor2" name="chkColor2" onclick="return chkColor2_onclick();"/>
                                  </td>
                                  <td >
                                    <input type="text" id="txtColor2Name" name="txtColor2Name" class="bdrColor_336666" style="width:100px" value="white" disabled />
                                    <input type="hidden" id="hdnColor2Value" name="hdnColor2Value" value="#ffffff" />
                                  </td>
                                  <td align="center">
                                    <table id="colorPickerColor2" style="width:17px;height:17px" class="bdrColor_336666" ><tr><td></td></tr></table>
                                  </td>
                                  <td align="center">
                                    <div onclick="return showColorSlider2();" class="imgClrSwatch" ></div> 
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>                          
                          <tr>
                            <td colspan="4" >
                              <div style="overflow:auto;width:100%;height:100px">
                                <table border="0" cellpadding="1" cellspacing="1" bgcolor="#80A0B2" width="100%">
                                  <tr>
                                    <th width="6%"><input type="checkbox" id="chkSizeAll" name="chkSizeAll" onclick="return chkSizeAll_onclick(this);" /></th>
                                    <th width="69%"><bean:message key="tbl.Size" /></th>
                                    <th width="25%" ><bean:message key="tbl.Price" /></th>
                                  </tr>
                                  <logic:iterate id="size" name="size4List" indexId="currentIndex">   
                                  <input type="hidden" id="hdnSizeValueInTbl" name="hdnSizeValueInTbl" value="<bean:write name="size" property="sizeValue"/>" >
                                  <input type="hidden" id="hdnSizeTextInTbl" name="hdnSizeTextInTbl" value="<bean:write name="size" property="sizeText"/>" >
                                  <tr bgcolor="#ffffff" title="<bean:write name="size" property="sizeDescription"/>">
                                    <td><input type="checkbox" id="chkSize" name="chkSize"  onclick="return chkSize_onclick(this);" alt="<%=currentIndex.intValue()%>" /></td>
                                    <td><bean:write name="size" property="sizeTypeSizeText"/></td>
                                    <td><input type="text" id="txtPriceInTbl" name="txtPriceInTbl" class="bdrColor_336666" style="width:100%;height:100%;text-align:right;" value="0.00" disabled maxlength="10"  onkeypress="return decimalOnly(this,event,2);" /></td>
                                  </tr>
                                  </logic:iterate> 
                                </table>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td height="3px" colspan="4" align="right">
                              <html:button property="btnOk" styleClass="buttons" onclick="return btnOk_onclick();" ><bean:message key="btn.Ok" /></html:button>
                              <html:reset property="btnCancel" styleClass="buttons" onclick="return btnDetailCancel_onclick();" ><bean:message key="btn.Cancel" /></html:reset>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                  <!-- Merchandise Color Size and Price Details Ends -->   
                </td>
              </tr>
            </table>
          </td>
        <tr>
        <tr>
          <td align="right">
            <html:button property="btnSave" styleClass="buttons" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
            <html:cancel styleClass="buttons" onclick="return btnCancel_onClick();"><bean:message key="btn.Cancel" /></html:cancel>
            <html:reset property="btnReset" styleClass="buttons" ><bean:message key="btn.Clear" /></html:reset>
          </td>
        </tr>
        <tr><td height="2px"></td></tr>
        
      </table>
      <!-- Add Table Ends -->
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="950px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
        <tr bgcolor="#FFFFFF">
          <td width="30px" ><div class="imgStatusMsg"></td>
          <td><html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->
    </td>
  </tr>
</table>
<!-- Main Table Ends -->
</html:form>
</BODY>
</HTML>
