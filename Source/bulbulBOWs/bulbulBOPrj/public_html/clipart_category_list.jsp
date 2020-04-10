<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
 
<html>
<head>
  <title><bean:message key="title.ClipartCategoryList" /></title>
  <link href="main.css" rel="stylesheet" type="text/css">
  <script>
    function btnclick(operation){
      var thisForm=document.forms[0];
      thisForm.target=opener.name;
      if ((operation=='ok' ) ){
        if(thisForm.hdnTrgCategoryTblPk.value == ''){
          alert('<bean:message key="alert.select.target.category" />');
          return false;
        }
        if(thisForm.hdnSrcCategoryTblPk.value == thisForm.hdnTrgCategoryTblPk.value){
          alert('<bean:message key="alert.same.category.move" />');
          return false;
        }
        if(thisForm.hdnOperation.value == 'clipart_move' && thisForm.hdnTrgCategoryTblPk.value=='-1'){
          alert('<bean:message key="alert.ClipartMoveToCategoryOnly" />');
          return false;
        }
        thisForm.submit();
      }
      window.close();
    }

    function btnCancel_onClick(){
      window.close();
    }
  </script>
</head>
<body style="margin:15px">
<html:form action="/clipartCategoryRelayAction.do" >
<html:hidden property="hdnOperation" />
<html:hidden property="hdnSrcClipartTblPk" />
<html:hidden property="hdnSrcCategoryTblPk" />
<html:hidden property="hdnTrgCategoryTblPk" />
  <table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">
        <table width="350px" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="20px">
              <!--Tab Table Starts-->
              <table id="tab" width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="44%" height="20px" class="tab">
                    <div align="center">
                      <bean:message key="tab.ClipartCategoryList" />
                    </div>
                  </td>
                  <td width="56%" ></td>
                </tr>
              </table>
              <!--Tab Table Ends-->
            </td>
          </tr>
          <tr>
            <td height="10px" class="bdrLeftColor_333333 bdrRightColor_333333 bdrTopColor_333333 bgColor_DFE7EC">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td class="bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333 bgColor_DFE7EC">
              <!--Treeview Table Starts-->
              <table width="90%"  border="0" cellpadding="0" cellspacing="0" align="center">
                <tr>
                  <td>
                    <div id="treeview" align="center" class="bdrColor_336666" style="overflow:auto;height:325px;width:320px;">
                    <div style="width:100%;" >
                      <!-- Tree View here -->
                      <iframe src="clipartRelayAction.do?hdnSearchOperation=clipart_category_tree_view" scrolling="yes" height="100%" width="100%"  frameborder="0"></iframe>
                    </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td align="right">
                    <html:button property="btnOk" onclick="btnclick('ok')" style="width:70px" styleClass="buttons"><bean:message key="btn.Ok" /></html:button>
                    <html:cancel style="width:70px" styleClass="buttons" onclick="btnCancel_onClick();" ><bean:message key="btn.Cancel" /></html:cancel>
                  </td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
              </table>
              <!--Treeview Table Ends-->
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <table cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td height="3px">
      </td>
    </tr>
  </table>
  <!-- statusBar table ends above-->
</html:form>  
</body>

</html>
