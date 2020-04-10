<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
<head>
<title><bean:message key="title.MyGraphicsModify" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<link href="myprntn.css" rel="stylesheet" type="text/css">
<html:javascript formName="myGraphicsModifyForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<script>
<!--
  function btnclick(){
    var thisForm=document.forms[0];
    thisForm.target="_self";
    if(validateMyGraphicsModifyForm(thisForm)){
      thisForm.submit();  
    }
  }
  
-->
</script>
</head>
<body>
<html:form action="myGraphicsModifyAction.do" enctype="multipart/form-data">
<html:hidden property="hdnCutomerGraphicsTblPk"/>
<html:hidden property="hdnContentOId"/>
<html:hidden property="hdnContentType"/>
<html:hidden property="hdnContentSize"/>

  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts --> 
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
        <td width="24px" height="17px"><img src="images/myprntn_pan_corner_lt.gif" width="24px" height="17px"></td>
        <td width="696px" background="images/myprntn_pan_tile17px.gif" class="heading_pan"><bean:message key="page.MyGraphicsModify" /> :</td>
        <td width="5px"><img src="images/myprntn_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
	    <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600 bdrTopColor_006600 text_bold12">&nbsp;</td>
    </tr>
   <tr>
      <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_006600 bdrRightColor_006600">
        <!-- Inner Table Starts --> 
        <table width="100%"  border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="10px" colspan="3"></td>
          </tr>
          <tr>
            <td width="26%"><div align="right"><bean:message key="txt.GraphicsTitle" />:</div></td>
            <td width="31%">
              <html:text  property="txtGraphicsTitle" style="width:200px" maxlength="15" />
            </td>
            <td width="43%" rowspan="3" valign="top">
              <!--<div class="bdrColor_006600" style="width:170px; height:175px; background-color:#FFFFFF">
                <div align="center">Image Here</div>
              </div>-->              					
              <img name="display" id="display" alt="<bean:write name="myGraphicsModifyForm" property="txtGraphicsTitle" />" class="bdrColor_006600" style="width:170px; height:175px; background-color:#FFFFFF"  src="imageDisplayAction.do?dataSourceKey=FOKey&imageOid=<bean:write name="myGraphicsModifyForm" property="hdnContentOId" />&imageContentType=<bean:write name="myGraphicsModifyForm" property="hdnContentType" />&imageContentSize=<bean:write name="myGraphicsModifyForm" property="hdnContentSize" />" ></img>
              
            </td>
          </tr>
          <tr>
            <td valign="top"><div align="right"><bean:message key="txa.GraphicsDescription" />:</div></td>
            <td valign="top">
              <html:textarea property="txaGraphicsDescription" rows="5" style="width:200px; height:125px"></html:textarea>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <html:button property="btnSave" styleClass="buttons" style="width:64px; height:20px" onclick="btnclick()"><bean:message key="btn.Save" /></html:button>
              <html:cancel styleClass="buttons" style="width:64px; height:20px"><bean:message key="btn.Cancel" /></html:cancel>
              <html:reset property="btnClear" styleClass="buttons" style="width:64px; height:20px"><bean:message key="btn.Clear" /></html:reset>
            </td>
          </tr>
          <tr>
            <td height="20px" colspan="3"></td>
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
</body>
</html>
