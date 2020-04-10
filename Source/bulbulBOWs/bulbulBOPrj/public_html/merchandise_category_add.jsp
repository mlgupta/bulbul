<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<HEAD>  
	<TITLE><bean:message key="title.MerchandiseCategoryAdd" /></TITLE>
	<link href="main.css" rel="stylesheet" type="text/css">
	<html:javascript formName="merchandiseCategoryForm" dynamicJavascript="true" staticJavascript="false" />
	<script language="javascript1.1" src="staticJavascript.jsp"></script>
	<script language="javascript1.1" src="general.js"></script>
	<script>
	<!--
		var DEL='D';
		function btnclick(operation){
			var thisForm=document.forms[0];
			thisForm.action="merchandiseCategoryAddAction.do"
			thisForm.target="_self";
			if ((operation=='save' && validateMerchandiseCategoryForm(thisForm)) ){
				if(thisForm.categoryImgFile.value==''){
					alert('<bean:message key="alert.Merchandise.Select.Required" />');
        }else if(!(((thisForm.categoryImgFile.value).indexOf(".jpg")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".gif")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".png")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".jpeg")!=-1)
         )){
          alert('<bean:message key="alert.imagesonly" />');
          thisForm.categoryImgFile.focus();
				}else{
					thisForm.submit();
        }
			}
		}

		function btnCancel_onClick(){
			var thisForm=document.forms[0];
			thisForm.action="merchandiseCategoryAddAction.do"
			thisForm.target="_self";
			thisForm.submit();
		}

  
		function imgFile_onChange(){
			var thisForm = document.forms[0];
      if(!(((thisForm.categoryImgFile.value).indexOf(".jpg")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".gif")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".png")!=-1)
         || ((thisForm.categoryImgFile.value).indexOf(".jpeg")!=-1)
         )){
        alert('<bean:message key="alert.imagesonly" />');
        //Saving the form values b4 reset starts.
        var objradMOrC=thisForm.radMOrC;
        var index;
        if(objradMOrC[1].checked) {
          index=1;
        }else{
          index=0;
        }
        var txtMerchandiseCategory=thisForm.txtMerchandiseCategory.value;
        var txaMerchandiseCategoryDesc=thisForm.txaMerchandiseCategoryDesc.value;
        //Saving the form values b4 reset ends.
        
        thisForm.reset();
        
        //Putting back the saved form values starts.
        if(index==0){
          objradMOrC[0].checked=true;
        }else{
          objradMOrC[1].checked=true;
        }
        thisForm.txtMerchandiseCategory.value=txtMerchandiseCategory;
        thisForm.txaMerchandiseCategoryDesc.value=txaMerchandiseCategoryDesc;
        //Putting back the saved form values ends.
        
        thisForm.categoryImgFile.focus();
        return false;
      }
			thisForm.target="preview";
			thisForm.action="merchandiseCategoryPreviewAction.do";
			thisForm.submit();
		}
    
		function btnSizeTypeAdd_onClick(){
			var theUrl='merchandiseCategorySizeTypeSelectListAction.do';
			theUrl=theUrl+'?hdnControlId4SizeTypeList=lstSizeType';
			theUrl=theUrl+'&hdnControlId4SizeTypeTblPk=hdnSizeTypeTblPk';
      theUrl=theUrl+'&hdnControlId4SizeTypeId=hdnSizeTypeId';
			theUrl=theUrl+'&hdnControlId4MerchandiseCategorySizeTypeTblPk=hdnMerchandiseCategorySizeTypeTblPk';
			theUrl=theUrl+'&hdnControlId4SizeTypeOperation=hdnSizeTypeOperation';
			openWindow(theUrl,'merchandiseCategorySizeTypeSelectList',475,550,0,0,true,'');
		}

		function btnSizeTypeRemove_onClick(){
			var thisForm = document.forms[0];
			var listIndex=0;
			var sizeTypeTblPkArray=thisForm.hdnSizeTypeTblPk.value.split(',');
      var sizeTypeIdArray=thisForm.hdnSizeTypeId.value.split(',');
			var merchadiseCategorySizeTypeTblPkArray=thisForm.hdnMerchandiseCategorySizeTypeTblPk.value.split(',');
			var sizeTypeOperationArray=thisForm.hdnSizeTypeOperation.value.split(',');
			var sizeTypeTblPkString='';
      var sizeTypeIdString='';
			var merchadiseCategorySizeTypeTblPkString='';
			var sizeTypeOperationString='';
			var sizeTypeTblPk=null;
			while(listIndex<thisForm.lstSizeType.options.length){
				if(thisForm.lstSizeType.options[listIndex].selected) {
					sizeTypeTblPk=thisForm.lstSizeType.options[listIndex].value;
					thisForm.lstSizeType.remove(listIndex);
					for(var arrayIndex=0; arrayIndex<sizeTypeTblPkArray.length;arrayIndex++){
						if( sizeTypeTblPk==sizeTypeTblPkArray[arrayIndex]){
							if (merchadiseCategorySizeTypeTblPkArray[arrayIndex]!='-1'){
								sizeTypeTblPkString=sizeTypeTblPkString+sizeTypeTblPkArray[arrayIndex]+',';
                sizeTypeIdString=sizeTypeIdString+sizeTypeIdArray[arrayIndex]+',';
								merchadiseCategorySizeTypeTblPkString=merchadiseCategorySizeTypeTblPkString+merchadiseCategorySizeTypeTblPkArray[arrayIndex] + ',';
								sizeTypeOperationString=sizeTypeOperationString+DEL+',';
							}
						}else{
								sizeTypeTblPkString=sizeTypeTblPkString+sizeTypeTblPkArray[arrayIndex]+',';
                sizeTypeIdString=sizeTypeIdString+sizeTypeIdArray[arrayIndex]+',';
								merchadiseCategorySizeTypeTblPkString=merchadiseCategorySizeTypeTblPkString+merchadiseCategorySizeTypeTblPkArray[arrayIndex]+',';
								sizeTypeOperationString=sizeTypeOperationString+sizeTypeOperationArray[arrayIndex]+',';
						}
					}
					thisForm.hdnSizeTypeTblPk.value=sizeTypeTblPkString.substring(0,(sizeTypeTblPkString.length-1));
          thisForm.hdnSizeTypeId.value=sizeTypeIdString.substring(0,(sizeTypeIdString.length-1));
					thisForm.hdnMerchandiseCategorySizeTypeTblPk.value=merchadiseCategorySizeTypeTblPkString.substring(0,(merchadiseCategorySizeTypeTblPkString.length-1));
					thisForm.hdnSizeTypeOperation.value=sizeTypeOperationString.substring(0,(sizeTypeOperationString.length-1));
				}else{
					listIndex++
				}
			}
		}    

		function radMOrC_onclick(){
			var thisForm=document.forms[0];
			var objradMOrC=thisForm.radMOrC;
			if(objradMOrC[0].checked) {
        MM_findObj('sizeTypeRow').style.visibility='hidden';
        var listIndex=0;
        while(listIndex<thisForm.lstSizeType.options.length){
          thisForm.lstSizeType.remove(listIndex);
        }
        thisForm.hdnSizeTypeTblPk.value='';
        thisForm.hdnSizeTypeId.value='';
				thisForm.hdnMerchandiseCategorySizeTypeTblPk.value='';
				thisForm.hdnSizeTypeOperation.value='';
			}else{
        MM_findObj('sizeTypeRow').style.visibility='visible';
			}
		}
    function btnClear_onclick(){
      var thisForm=document.forms[0];
			var objradMOrC=thisForm.radMOrC;
      objradMOrC[0].checked=true;
			radMOrC_onclick();
    }
	-->    
	</script>  
</HEAD>
<BODY>
<html:form action="/merchandiseCategoryAddAction" enctype="multipart/form-data" >
<html:hidden property="hdnMerchandiseCategoryTblFk" />
<html:hidden property="hdnSearchPageNo" /> 
<html:hidden property="hdnSizeTypeTblPk" /> 
<html:hidden property="hdnSizeTypeId" /> 
<html:hidden property="hdnMerchandiseCategorySizeTypeTblPk" /> 
<html:hidden property="hdnSizeTypeOperation" /> 
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
	<tr>
		<td width="200px" class="tab" height="20px">
			<div align="center"><bean:message  key="tab.Merchandise" /></div>
		</td>
		<td width="770px" >
		</td>
	</tr>
</table>
<!-- Tab Table Ends -->      
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
			<table width="730px"  border="0" align="center" class="bdrColor_336666">
				<tr>
					<th height="20px" colspan="3"><div align="center"><bean:message key="page.MerchandiseCategoryAdd" /></div></th>
				</tr>
				<tr>
					<td colspan="3" height="5px"></td>
				</tr>
				<tr>
					<td width="280px">
						<div align="right">
							<bean:message key="txt.MerchandiseCategoryName" />:
						</div>
					</td>
					<td width="310px">
						<div >
							<html:text property="txtMerchandiseCategory" styleClass="bdrColor_336666" style="width:100%" maxlength="10" />
						</div>
					</td>
					<td width="140px">&nbsp;</td>
				</tr>
        <tr>
					<td width="280px">
						<div align="right">
							<bean:message key="txt.MerchandiseCategory" />:
						</div>
					</td>
					<td width="310px">
						<div >
							<html:text property="txtParentCategory" styleClass="bdrColor_336666" style="width:100%" readonly="true" />
						</div>
					</td>
					<td width="140px">&nbsp;</td>
				</tr>
				<tr>
					<td valign="top"><div align="right"><bean:message key="txa.MerchandiseCategoryDesc" />:</div></td>
					<td>
						<div>
							<html:textarea property="txaMerchandiseCategoryDesc" cols="" rows="4" styleClass="bdrColor_336666" style="width:100%" />
						</div>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td valign="top" align="right"><bean:message key="fle.MerchandiseCategoryImage" /></td>
					<td height="50px">
						<table border="0" cellpadding="0" cellspacing="1" >
							<tr>
								<td>
									<iframe name="preview" id="preview" frameborder="0" align="center" class="bdrColor_336666" height="95%" width="56%" ></iframe>
								</td>
							</tr>
							<tr>
								<td>
								<input type="file" id="categoryImgFile" name="categoryImgFile" class="bdrColor_336666" size="40" onkeypress="return false;" onchange="return imgFile_onChange();"/>
								</td>
							</tr>
						</table>
					</td>
					<td>&nbsp;</td>
				</tr>
        <tr>
					<td>&nbsp;</td>
					<td>
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
							<td><html:radio property="radMOrC" value="1" onclick="return radMOrC_onclick();" /></td>
							<td><bean:message key="rad.MerchandiseCategoryOnly" /></td>
							<td><html:radio property="radMOrC" value="0" onclick="return radMOrC_onclick();" /></td>
							<td><bean:message key="rad.MerchandiseOnly" /></td>
							<tr>          
						</table>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr id="sizeTypeRow" style="visibility:hidden">
					<td align="right" valign="top"><bean:message key="lst.SizeType" />:</td>
					<td>
					<table border="0" cellpadding="0" cellspacing="1" width="100%" >
						<tr>
							<td width="100%">
								<select id="lstSizeType" name="lstSizeType" size="4" style="width:100%;" multiple >
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								<html:button property="btnAdd" styleClass="buttons" onclick="return btnSizeTypeAdd_onClick();" ><bean:message key="btn.Add"/></html:button>
								<html:button property="btnRemove" styleClass="buttons" onclick="return btnSizeTypeRemove_onClick();" ><bean:message key="btn.Remove"/></html:button>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
					</td>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td colspan="3" height="5px" ></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td >
						<div align="right">
							<html:button property="btnSave" styleClass="buttons" onclick="btnclick('save')" ><bean:message  key="btn.Save" /></html:button>
							<html:cancel  styleClass="buttons" onclick="return btnCancel_onClick();" ><bean:message  key="btn.Cancel" /></html:cancel>
							<html:reset property="btnClear" styleClass="buttons" onclick="btnClear_onclick();" ><bean:message  key="btn.Clear" /></html:reset>
						</div>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" height="5px"></td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        
			<!-- Status Bar Table Starts-->
			<table id="tblStatusBar" align="center" width="730px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
				<tr bgcolor="#FFFFFF">
					<td width="30px" ><div class="imgStatusMsg"></div></td>
					<td>
						<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
					</td>
				</tr>
			</table>
			<!-- Status Bar Table Ends-->      
		</td>
	</tr>
</table>
</html:form>
</BODY>
</HTML>
