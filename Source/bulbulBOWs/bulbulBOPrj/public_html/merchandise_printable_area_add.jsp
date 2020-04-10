<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="printableAreaDetailArray" name="printableAreaDetails" type="java.util.ArrayList" />

<HTML>
<HEAD>  
  <TITLE><bean:message key="title.MerchandisePrintableAreaAdd" /></TITLE>
  <link href="main.css" rel="stylesheet" type="text/css">
  <html:javascript formName="merchandisePrintableAreaForm" dynamicJavascript="true" staticJavascript="false" />
  <script language="javascript1.1" src="staticJavascript.jsp"></script>
  <script language="javascript1.1" src="general.js"></script>
  <script>
  <!--
    var fileCount=<%=printableAreaDetailArray.size()%>
    
    function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.action="merchandisePrintableAreaAddAction.do"
      thisForm.target="_self";
      if (operation=='save'  ){
        for(var index=0;index<fileCount;index++){
          var fileId="printableAreaImgFile["+index+"]";
          var heightId="txtPrintableAreaHeight["+index+"]";
          var widthId="txtPrintableAreaWidth["+index+"]";
          
          var objFile=MM_findObj(fileId);
          var objHeight=MM_findObj(heightId);
          var objWidth=MM_findObj(widthId);
          
          if(objFile.value.length<=0){
            alert('<bean:message key="alert.MerchandisePrintableArea.Select.Required" />');
            saveFlag=false;
            objFile.focus();
            return false;
          }else if((objFile.value).indexOf(".jpg")==-1 && objFile.value != '' ){
            if((objFile.value).indexOf(".gif")==-1 && objFile.value != '' ){
              if((objFile.value).indexOf(".png")==-1 && objFile.value != '' ){
                alert('<bean:message key="alert.chooseJpgPngGifOnly" />');
                objFile.focus();
                return false;  
              }
            }
          }
          if(objHeight.value.length<=0){
            alert('<bean:message key="alert.MerchandisePrintableAreaHeight.Required" />');
            objHeight.focus();
            return false;
          }
          if(objWidth.value.length<=0){
            alert('<bean:message key="alert.MerchandisePrintableAreaWidth.Required" />');
            objWidth.focus();
            return false;
          }
        }
        thisForm.submit();
      }
    }

    function btnCancel_onClick(){
      var thisForm=document.forms[0];
      thisForm.action="merchandisePrintableAreaAddAction.do"
      thisForm.target="_self";
      thisForm.submit();
    }

    function printableAreaImgFile_onChange(file,index){
      var thisForm = document.forms[0];
      var imgFile=file;
      
      if((imgFile.value).indexOf(".jpg")==-1 && imgFile.value != '' ){
        if((imgFile.value).indexOf(".gif")==-1 && imgFile.value != '' ){
          if((imgFile.value).indexOf(".png")==-1 && imgFile.value != '' ){
            alert('<bean:message key="alert.chooseJpgPngGifOnly" />');
            return false;  
          }
        }
      }
      thisForm.target="preview["+index+"]";
      thisForm.action="merchandiseDesignImgPreviewAction.do?index="+index;
      thisForm.submit();
    }
  -->
  </script> 
</HEAD>
<BODY >
<html:form action="/merchandisePrintableAreaAddAction" enctype="multipart/form-data">
<html:hidden property="hdnMerchandiseCategoryTblPk" />
<html:hidden property="hdnMerchandiseTblPk" />
<html:hidden property="hdnSearchPageNo" />
<html:hidden property="chkPrintableArea" />
<jsp:include page="header.jsp" />
<!-- Tab Table Starts -->
<table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200px" class="tab" height="20px">
      <div align="center"><bean:message key="tab.MerchandisePrintableArea" /></div>
    </td>
    <td width="770px">
    </td>
  </tr>
</table>
<!-- Tab Table Ends -->
<table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="507px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
      <table width="600px"  border="0" align="center" class="bdrColor_336666">
          <tr>
            <th height="20px" colspan="3"><div align="center"><bean:message key="page.MerchandisePrintableAreaAdd" /></div></th>
          </tr>
          <tr>
            <td colspan="3">&nbsp;</td>
          </tr>
          <tr>
            <td  height="305px" colspan="3" >
              <div style="overflow:auto;height:305px" >
              <%
              String strMerchandiseColorTblPk="-1";
              %>
                <!-- Printable Area Iterator  Start-->
                <logic:iterate id="printableAreaDetail" name="printableAreaDetails" type="bulbul.boff.ca.merchand.beans.PrintableAreaDetailBean" indexId="index" >
                  <% 
                    if(!strMerchandiseColorTblPk.equals(printableAreaDetail.getMerchandiseColorTblPk())){                   
                    strMerchandiseColorTblPk=printableAreaDetail.getMerchandiseColorTblPk();
                  %>
                  
                  <br>
                  <table border="0" >
                    <tr>
                      <td>
                        <b>
                          <bean:write name="printableAreaDetail" property="color1Name"/>
                          <logic:notEqual  name="printableAreaDetail" property="color2Name" value="">
                            <span>/</span><bean:write name="printableAreaDetail" property="color2Name" />
                          </logic:notEqual>
                        </b>
                      </td>
                      <td>
                        <logic:notEqual  name="printableAreaDetail" property="color2Name" value="">
                          <div style="height:18px;float:left;width:18px;background-color:<bean:write name="printableAreaDetail" property="color1Value" />;"></div>
                          <div style="height:18px;float:left;width:18px;background-color:<bean:write name="printableAreaDetail" property="color2Value" />;"></div>
                        </logic:notEqual>
                        <logic:equal  name="printableAreaDetail" property="color2Name" value="">
                          <div style="height:18px;float:left;width:36px;background-color:<bean:write name="printableAreaDetail" property="color1Value" />;"></div>
                        </logic:equal>
                      </td>
                    </tr>
                  </table>
                             
                  <%}%>
                
                <table border="0" style="width:100%" class="bdrBottomColor_333333">
                  <tr>
                    <td align="right" height="18px">&nbsp;</td>
                    <td >
                      <div>
                        <b><bean:write name="printableAreaDetail" property="printableAreaName"/></b>
                        <bean:define id="MerchandisePrintableAreaTblPk" name="printableAreaDetail" property="merchandisePrintableAreaTblPk"/>
                        <html:hidden property='<%="hdnMerchandisePrintableAreaTblPk["+index+"]"%>' value="<%= (String)MerchandisePrintableAreaTblPk %>" />
                      </div>
                    </td>
                    <td width="180px" height="150px" rowspan="7" align="center" >
                      <iframe name='<%="preview["+index+"]"%>' id='<%="preview["+index+"]"%>' frameborder="0" align="middle" class="bdrColor_336666" height="95%" width="95%" ></iframe>
                    </td>
                  </tr>
                  <tr>
                    <td align="right" height="18px"><bean:message key="fle.PrintableAreaImage" /> : </td>
                    <td width="280px">
                      <div>
                        <input type="file" id='<%="printableAreaImgFile["+index+"]"%>' name='<%="printableAreaImgFile["+index+"]"%>' class="bdrColor_336666" size="35" onkeypress="return false;" onchange="return printableAreaImgFile_onChange(this,<%=index%>);"/>
                      </div>
                    </td>                    
                  </tr>
                  <tr>
                    <td valign="top" height="18px"><div align="right"><bean:message key="txt.PrintableAreaWidth" /> :</div></td>
                    <td>
                      <div align="left">
                        <input type="text"  id='<%="txtPrintableAreaWidth["+index+"]"%>' name='<%="txtPrintableAreaWidth["+index+"]"%>' class="bdrColor_336666" style="width:10%" onkeypress="return integerOnly(event);" maxlength="3" /> px.
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td height="18px"><div align="right"><bean:message key="txt.PrintableAreaHeight" /> :</div></td>
                    <td >
                      <div align="left">
                        <input type="text"  id='<%="txtPrintableAreaHeight["+index+"]"%>' name='<%="txtPrintableAreaHeight["+index+"]"%>' class="bdrColor_336666" style="width:10%" onkeypress="return integerOnly(event);" maxlength="3" /> px.
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td height="18px">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="18px">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="18px">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
              </logic:iterate>
              <!-- Printable Area Iterator End-->              
             </div>
            </td>
          </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <div align="right">
              <html:button property="btnSave" styleClass="buttons" onclick="btnclick('save');" ><bean:message  key="btn.Save" /></html:button>
              <html:cancel styleClass="buttons" onclick="return btnCancel_onClick();" ><bean:message  key="btn.Cancel" /></html:cancel>
              <html:reset property="btnClear" styleClass="buttons"  ><bean:message  key="btn.Clear" /></html:reset>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="3">&nbsp;</td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="600px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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
