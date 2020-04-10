<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<head>
<title><bean:message key="title.MyGraphicsUpload" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<script language="javascript1.1" src="general.js"></script>

<script>
<!--
  var intFileUploadIndex=0;
  var blnNotFirstTime= false;
  var fileIdPrefix='fleGraphics';
  var textAreaIdPrefix='txaGraphicsDescription';
  var textIdPrefix='txtGraphicsTitle';

  function init(){
    addFiles();
    blnNotFirstTime=true;
  }

  function upload(){
    var thisForm = document.forms[0];
    if (thisForm.lstGraphicsFile.options.length>0){
      thisForm.submit();
    }else{
      alert('<bean:message key="alert.NothingToUpload" />');
    }
    
  }
  
  function addFiles(){

    var fileId=fileIdPrefix+'[' + intFileUploadIndex +']';
    var textAreaId=textAreaIdPrefix+'[' + intFileUploadIndex +']';
    var textId=textIdPrefix + '[' + intFileUploadIndex + ']';
    
    //alert(fileId);
    
    var objLyrFileUpload=MM_findObj('lyrFileUpload');
    var objLyrFileDesc=MM_findObj('lyrFileDesc');
    var objLyrFileTitle=MM_findObj('lyrFileTitle');

    if (blnNotFirstTime){

      var index2Hide= intFileUploadIndex-1;
      var objListFileUpload=MM_findObj('lstGraphicsFile');
      
      //Code For File Control
      var fileId2Hide=fileIdPrefix+'[' + index2Hide +']';
      var objFileId2Hide=MM_findObj(fileId2Hide) ;
      var fileName=objFileId2Hide.value;
      
      if (fileName.length==0){
        alert('<bean:message key="alert.NothingToAttach" />');
        return false;
      }
      
      //Code For Text Control
      var textId2Hide=textIdPrefix+'[' + index2Hide +']';
      var objTextId2Hide=MM_findObj(textId2Hide) ;
      var fileTitle=objTextId2Hide.value;
      
      
           
      for(var openerIndex=0;openerIndex<objListFileUpload.options.length; openerIndex++){
          if(objListFileUpload.options[openerIndex].id==fileName){                  
            alert("<bean:message key="alert.FileAlreadyInList" />");
            return false;
          } 
      }
      
      if (trim(fileTitle).length==0){
        alert('<bean:message key='error.Title.Required' />');
        objTextId2Hide.value=trim(fileTitle);
        objTextId2Hide.focus();
        return false;
      }
      if (trim(fileTitle).length<5){
        alert('<bean:message key='error.Title.Minlength' />');
        objTextId2Hide.value=trim(fileTitle);
        objTextId2Hide.focus();
        return false;
      }
      if (trim(fileTitle).length>15){
        alert('<bean:message key='error.Title.Maxlength' />');
        objTextId2Hide.value=trim(fileTitle);
        objTextId2Hide.focus();
        return false;
      }
       

      //Code For Text Area Control
      var textAreaId2Hide=textAreaIdPrefix+'[' + index2Hide +']';
      var objTextAreaId2Hide=MM_findObj(textAreaId2Hide) ;
      var fileDesc=objTextAreaId2Hide.value;

            
      for(var openerIndex=0;openerIndex<objListFileUpload.options.length; openerIndex++){
          if(objListFileUpload.options[openerIndex].id==fileName){                  
            alert('<bean:message key="alert.FileAlreadyInList" />');
            return false;
          } 
      }

      if (trim(fileDesc).length>100){
        alert('<bean:message key='error.Description.Maxlength' />');
        return false;
      }

      //To set display none for File control
      objFileId2Hide.style.display='none';
      
      
      //To set display none for Text control
      objTextId2Hide.style.display='none';
      
      //To set display none for Text Area control
      objTextAreaId2Hide.style.display='none';
      
    
      var objOption = document.createElement('OPTION'); 
      objOption.value=fileId2Hide+','+textId2Hide +',' + textAreaId2Hide; 
      objOption.id=fileName;
      objOption.title=fileDesc;
      
      
      //To Get File Name Alone  
      while(fileName!=(fileName=fileName.replace("\\","/")));
      var strArray=fileName.split('/');
      fileName=strArray[strArray.length-1];

      objOption.text=fileName;
      var newIndex = objListFileUpload.options.length;
      objListFileUpload.options[newIndex]=objOption;
      
    }

    //Following Piece of Code Generates File Control
    var objFileUpload= document.createElement ('INPUT'); 
    objFileUpload.id=fileId;
    objFileUpload.name=fileId;
    objFileUpload.type='file';
    objFileUpload.size='44';
    objFileUpload.onkeypress=fleOnKeypress;
    objLyrFileUpload.appendChild(objFileUpload);
    
    //Following Piece of Code Generates Text Control
    var objText= document.createElement ('INPUT'); 
    objText.type='text';
    objText.id=textId;
    objText.name=textId;
    objText.maxLength='15';
    objText.size='55';
    objText.className='borderClrLvl_2 componentStyle';
    objLyrFileTitle.appendChild(objText);

    //Following Piece of Code Generates Text Area Control
    var objTextArea= document.createElement ('TEXTAREA'); 
    objTextArea.id=textAreaId;
    objTextArea.name=textAreaId;
    objTextArea.className='borderClrLvl_2 componentStyle';
    objTextArea.cols='54';
    objTextArea.rows='5';
    objLyrFileDesc.appendChild(objTextArea);

    intFileUploadIndex++; 
    
  }
  
  function removeFiles(){
    var thisForm = document.forms[0];
    var index=0;
    var length=thisForm.lstGraphicsFile.length;
    var controlIdString;
    var controlIdArray;
    var fileId;
    var textId;
    var textAreaId;
    var objLyrFileUpload=MM_findObj('lyrFileUpload');
    var objLyrFileDesc=MM_findObj('lyrFileDesc');
    var objLyrFileTitle=MM_findObj('lyrFileTitle');

    
    var objFileUpload;
    var objFileDesc;
    var objFileTitle;
    while(index <length){     
      if(thisForm.lstGraphicsFile[index].selected){

        controlIdString=thisForm.lstGraphicsFile[index].value;
        controlIdArray=controlIdString.split(',');
        
        fileId=controlIdArray[0];
        textId=controlIdArray[1];
        textAreaId=controlIdArray[2];
        
        
        //To Remove File Control  
        objFileUpload=MM_findObj(fileId);
        objLyrFileUpload.removeChild(objFileUpload) ;
        
        //To Remove Text Control
        
        objFileTitle=MM_findObj(textId);
        objLyrFileTitle.removeChild(objFileTitle) ;
        
        //To Remove Text Area Control
        objFileDesc=MM_findObj(textAreaId);
        objLyrFileDesc.removeChild(objFileDesc) ;
        
        //To Remove list item 
        thisForm.lstGraphicsFile.remove(index);
        
        length=thisForm.lstGraphicsFile.length;   
        
      }else{
        index++;
      }
    }
  }
  
  function fleOnKeypress(){
    return false;
  }
  
  function btnclick(){
    var thisForm=document.forms[0];
    thisForm.target="_self";
    
    if (thisForm.lstGraphicsFile.options.length<=0){
      alert('<bean:message key="alert.AttachFiles2Upload" />');
      return false;
    }
    
    var fileId=fileIdPrefix+'[' + (intFileUploadIndex - 1) +']';
    var textAreaId=textAreaIdPrefix+'[' + (intFileUploadIndex - 1) +']';
    var textId=textIdPrefix + '[' + (intFileUploadIndex - 1) + ']';

    var objFileId=MM_findObj(fileId);
    var objTextAreaId=MM_findObj(textAreaId) ;
    var objTextId=MM_findObj(textId);
    
    objFileId.disabled=true;
    objTextAreaId.disabled=true;
    objTextId.disabled=true;
    
    thisForm.submit();
  }
//-->
</script>

</head>

<body>
<html:form action="myGraphicsUploadAction.do" enctype="multipart/form-data">
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyGraphicsUpload" /> :</td>
      <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold12">&nbsp;</td>
    </tr>
	  <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
        <!-- Inner Table Starts -->
        <table width="100%"  border="0" cellpadding="1" cellspacing="1" style="background-image:url(images/icon_uploading.gif); background-repeat:no-repeat; background-position: 20px 0px; ">
          <tr>
            <td height="10px" colspan="3">&nbsp;</td>
          </tr>
          <tr>
            <td height="30px">&nbsp;</td>
            <td valign="top"><bean:message key="info.ImageSize" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="fle.Graphics" />:</div></td>
            <td>
              <div id="lyrFileUpload" ></div>
              <!--<input type="file" id="fleGraphics" name="fleGraphics" onkeypress="return false;" size="50">-->
            </td>
          </tr>
          <tr>
            <td width="25%"><div align="right"><bean:message key="txt.GraphicsTitle" />:</div></td>
            <td width="75%">
              <div id="lyrFileTitle" ></div>
              <!--<html:text property="txtGraphicsTitle" style="width:400px" maxlength="15" />-->
            </td>
          </tr>
          <tr>
            <td valign="top"><div align="right"><bean:message key="txa.GraphicsDescription" />:</div></td>
            <td valign="top">
              <div id="lyrFileDesc" ></div>
              <!--<html:textarea property="txaGraphicsDescription" rows="5" style="width:400px; height:75px"></html:textarea>-->
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <html:button property="btnAttach" styleClass="buttons" style="width:64px; height:20px; margin-left:268px" onclick="return addFiles();"><bean:message key="btn.Attach" /></html:button>
              <html:button property="btnRemove" styleClass="buttons" style="width:64px; height:20px" onclick="return removeFiles();"><bean:message key="btn.Remove" /></html:button>
            </td>
          </tr>
          <tr>
            <td valign="top">
              <div align="right"><bean:message key="lst.Files" />: </div>
            </td>
            <td>
              <select size="7" multiple id="lstGraphicsFile" name="lstGraphicsFile" style="width:400px;"></select>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <html:button property="btnUpload" styleClass="buttons" style="width:64px; height:20px; margin-left:268px" onclick="btnclick()"><bean:message key="btn.Upload" /></html:button>
              <html:cancel styleClass="buttons" style="width:64px; height:20px"><bean:message key="btn.Cancel" /></html:cancel>
            </td>
          </tr>
          <tr>
            <td></td>
            <td><bean:message key="info.UploadTime" /> </td>
          </tr>
          <tr>
            <td height="20px" colspan="3">&nbsp;</td>
          </tr>
        </table>
        <!-- Inner Table Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/myprntn_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td background="images/myprntn_pan_tile3px.gif"></td>
      <td><img src="images/myprntn_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
  
</html:form>
<script>init()</script>
</body>
</html>
