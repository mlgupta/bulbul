<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<title><bean:message key="title.ProductCustomise" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<link href="studio.css" rel="stylesheet" type="text/css">
<html:javascript formName="designSaveForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
</head>
<body>

<html:form action="productCustomiseAction.do" onsubmit="return validateDesignSaveForm(this)" >
<html:hidden property="hdnDesignOId" />
<html:hidden property="hdnMerchandiseColorTblPk" />
<html:hidden property="hdnDesignContentType" />
<html:hidden property="hdnDesignContentSize" />
<html:hidden property="hdnProductName" />
 
  <!-- Seperator Table -->                                    
  <table border="0" align="center" cellpadding="0" cellspacing="0" ><tr><td height="10px"></td></tr></table>    
  <!-- Main Table Starts -->
  <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0" id="panRevisiting">
    <tr>
      <td width="24px" height="17px"><img src="images/studio_pan_corner_lt.gif" width="24px" height="17px"></td>
      <td width="696px" background="images/studio_pan_tile17px.gif" class="heading_pan"><bean:message key="page.ProductCustomise" /></td>
      <td width="5px"><img src="images/studio_pan_corner_rt.gif" width="5px" height="17px"></td>
    </tr>
	  <tr>
      <td height="20px" bgcolor="#FFFFFF" colspan="3" class="bdrLeftColor_CC6A00 bdrRightColor_CC6A00 bdrTopColor_CC6A00 text_bold12">
        <!-- Customized Product Image and Size & Qty Table Starts -->
        <table width="100%"  border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td align="right" width="48%" valign="top">
              <!-- Customized Product Image Table Starts -->
              <table width="250px"  border="0" cellpadding="1" cellspacing="1">
                <tr>
                  <td width="100%" height="20px" class="text_CC6A00"><div align="center"><bean:write name="productBean" property="productName" /></div></td>
                </tr>
                <tr>
                  <td height="20px">
                    <div align="center">
                      <img name="display" id="display" class="bdrColor_CC6A00" border="0" style="width:200px; height:200px; background-color:#FFFFFF" src='imageDisplayAction.do?dataSourceKey=BOKey&imageOid=<bean:write name="productBean" property="productOId"/>&imageContentType=<bean:write name="productBean" property="productContentType"/>&imageContentSize=<bean:write name="productBean" property="productContentSize"/>' ></img>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td align="center" valign="top">&nbsp;</td>
                </tr>
              </table>
              <!-- Customized Product Image Table Ends -->
            </td>
            <td width="52%" valign="top">
              <!-- Save Design Table Starts -->
              <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                <tr>
                  <td height="20px" >
                    <div style="color:#ff0000">
                      <b>
                        <html:messages id="messageDesignNameExists" message="true" property="messageDesignNameExists" >
                          <bean:write name="messageDesignNameExists" />
                        </html:messages>
                      </b>
                    </div>   
                  </td>
                </tr>
                <tr>
                  <td >
                    <table width="250px"  border="0" cellspacing="1" cellpadding="2" class="bgColor_CC6A00">
                      <tr>
                        <td colspan="2" align="center" class="th_F89627" >Save Design</td>
                      </tr>
                      <tr>
                        <td bgcolor="#FFFFFF" >
                          <table width="100%"  border="0" cellspacing="1" cellpadding="1">                          
                            <tr>
                              <td colspan="2" height="10px" align="right"></td>    
                            </tr>
                            <tr>
                              <td align="right" bgcolor="#FFFFFF" width="40%" ><bean:message key="txt.DesignName" /></td>
                              <td bgcolor="#FFFFFF" width="60%">
                                <html:text  property="txtDesignName"  maxlength="15" style="width:100%; height:20px"></html:text>
                              </td>
                            </tr>
                            <logic:notEmpty name="customerInfo" property="customerEmailId" >
                              <tr style="visibility:hidden;" >
                                <td align="right" bgcolor="#FFFFFF" ><bean:message key="txt.YourEmailId" /></td>
                                <td bgcolor="#FFFFFF" >
                                  <bean:define id="customerEmailId" name="customerInfo" property="customerEmailId" />
                                  <html:text property="txtYourEmailId" maxlength="50" style="width:100%; height:20px" value="<%=(String)customerEmailId%>"></html:text>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            <logic:empty name="customerInfo" property="customerEmailId" >
                              <tr>
                                <td align="right" bgcolor="#FFFFFF" ><bean:message key="txt.YourEmailId" /></td>
                                <td bgcolor="#FFFFFF" ><html:text property="txtYourEmailId" maxlength="50" style="width:100%; height:20px"></html:text></td>
                              </tr>
                            </logic:empty>

                            <tr>
                              <td colspan="2" height="15px" align="right"></td>    
                            </tr>
                            <tr>
                              <td colspan="2" >
                              <div style="margin-left:5px;margin-right:5px; text-align:justify">
                              <bean:message key="info.ForSavingDesign" />                              
                              </div>
                              </td>    
                            </tr>
                            <tr>
                              <td colspan="2" height="15px" align="right"></td>    
                            </tr>
                            <tr>
                              <td colspan="2" height="30px" align="center">
                                <html:submit styleClass="buttons" style="width:85px; height:20px;" property="btnSave"><bean:message key="btn.Save" /></html:submit>
                                <html:reset styleClass="buttons" style="width:85px; height:20px;" property="btnClear" ><bean:message key="btn.Clear" /></html:reset>
                              </td>    
                            </tr>
                          </table> 
                        </td>
                      </tr>                      
                    </table>
                  </td>
                </tr>
              </table>
              <!-- Save Design Table Ends -->
            </td>
          </tr>
        </table>
        <!-- Customized Product Image and Size & Qty Table Ends -->
      </td>
    </tr>
    <tr>
      <td height="3px"><img src="images/studio_pan_corner_lb.gif" width="24px" height="3px"></td>
      <td bgcolor="#FFFFFF" background="images/studio_pan_tile3px.gif"></td>
      <td><img src="images/studio_pan_corner_rb.gif" width="5px" height="3px"></td>
    </tr>
  </table>
  <!-- Main Table Ends -->
</html:form>
</body>
</html>
