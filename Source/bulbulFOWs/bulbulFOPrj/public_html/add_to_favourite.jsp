<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<HTML>
<HEAD>
<TITLE><bean:message key="title.AddToFavourite" /></TITLE>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="catalogue.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js"></script>
<SCRIPT LANGUAGE=javascript>
<!--
  function btnNewSelect_onclick(){
    if (MM_findObj('lyrSelect').style.display=='none'){
      MM_showHideLayers('lyrSelect','','show','lyrNew','','hide');
      document.forms[0].btnNewSelect.value='New';
      document.forms[0].txtCategory.value='';
    }else{
      MM_showHideLayers('lyrSelect','','hide','lyrNew','','show');
      document.forms[0].btnNewSelect.value='Select';
      document.forms[0].cboCategory.value='0';
    }
  }
  
  function btnclick(){
    var thisForm=document.forms[0];
    thisForm.target="_self";
    thisForm.submit();
  }
//-->
</SCRIPT>
</HEAD>
<BODY>
<html:form action="addToFavouriteAction.do" >
<html:hidden property="hdnMerchandiseTblPk" />
<html:hidden property="hdnCustomerEmailIdTblPk" />
  <!-- Main Table Starts -->
  <table width="100%" height="100%"  border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td valign="middle">
        <!-- Sub Table Starts -->
        <table width="300"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="24px" height="17px"><img src="images/catalogue_pan_corner_lt.gif" width="24px" height="17px"></td>
            <td width="271px" background="images/catalogue_pan_tile17px.gif" class="heading_pan"><bean:message key="page.AddToFavourite" /> :</td>
            <td width="5px"><img src="images/catalogue_pan_corner_rt.gif" width="5px" height="17px"></td>
          </tr>
          <tr>
            <td height="30px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94 bdrTopColor_007C94 text_007C94"><div align="center"><bean:message key="tbl.ProductName" /> </div></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94">
              <!-- Select/New Table Starts -->
              <table width="260px" height="75px"  border="0" align="center" cellpadding="1" cellspacing="1" class="bdrColor_007C94 bgColor_DDF3FF">
                <tr>
                  <td align="center" id="lyrSelect" name="lyrSelect" style="display:''"><bean:message key="cbo.SelectCategory" />:<br><br>
                    <html:select property="cboCategory" style="width:190px">
                      <html:option value="0" >*****Select Category*****</html:option>
                      <logic:iterate id="category" name="categories">
                        
                        <option ><bean:write name="category" /></option>
                        
                      </logic:iterate>
                      <!--<html:option value="1" >Sports</html:option>
                      <html:option value="2" >Mobile</html:option>-->
                    </html:select>
                  </td>
                </tr>
                <tr>
                  <td align="center" id="lyrNew" name="lyrNew" style="display:none"><bean:message key="txt.NewCategory" />:<br><br>
                    <html:text property="txtCategory" style="width:190px" />
                  </td>
                </tr>
              </table>
              <!-- Select/New Table Ends -->
            </td>
          </tr>
          <tr>
            <td height="30px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_007C94 bdrRightColor_007C94">
              <div align="center">
                <html:button  property="btnNewSelect" styleClass="buttons" style="width:50px; height:20px" onclick="return btnNewSelect_onclick();" title="Create New Category"><bean:message key="btn.New" /></html:button>
                <html:button property="btnAdd" styleClass="buttons" style="width:50px; height:20px" title="Add to category list" onclick="btnclick();"><bean:message key="btn.Add" /></html:button>
                <html:button  property="btnClose" styleClass="buttons" style="width:50px; height:20px" onclick="return window.close();" title="Close Window"><bean:message key="btn.Close" /></html:button>
              </div>
            </td>
          </tr>
          <tr>
            <td height="3px"><img src="images/catalogue_pan_corner_lb.gif" width="24" height="3"></td>
            <td bgcolor="#FFFFFF" background="images/catalogue_pan_tile3px.gif"></td>
            <td><img src="images/catalogue_pan_corner_rb.gif" width="5" height="3"></td>
          </tr>
        </table>
        <!-- Sub Table Ends -->
      </td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</html:form>
</BODY>
</HTML>
