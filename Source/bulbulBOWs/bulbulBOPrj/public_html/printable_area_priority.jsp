<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
  <HEAD>  
    <TITLE><bean:message   key="title.PrintableAreaAdd" /> </TITLE>


    <script language="javascript" type="text/javascript">
    <!--
       function btncSavelick(){
        var thisForm=document.forms[0];
        thisForm.lstPrintableArea.multiple=true;
        for(var index=0;index<thisForm.lstPrintableArea.options.length;index++){
          thisForm.lstPrintableArea.options[index].selected=true;
        }
        thisForm.target="_self";          
        thisForm.submit();
          
      }//end of btnClick function

      function btnUpClick(){
        var thisForm=document.forms[0];
        var selectedValue;
        var selectedText;
        //alert(thisForm.lstPrintableArea.selectedIndex);
//        alert(thisForm.cboPrintAreaName.options.length);
//        alert(thisForm.cboPrintAreaName.options[1].text);
        var selectedIndex=thisForm.lstPrintableArea.selectedIndex;
        var previousIndex=selectedIndex-1;
        
          if(selectedIndex!=-1){
            selectedValue=thisForm.lstPrintableArea.options[selectedIndex].value;
            selectedText=thisForm.lstPrintableArea.options[selectedIndex].text;
            
            if(selectedIndex>0){
            thisForm.lstPrintableArea.options[selectedIndex].value=thisForm.lstPrintableArea.options[previousIndex].value;
            thisForm.lstPrintableArea.options[selectedIndex].text=thisForm.lstPrintableArea.options[previousIndex].text;
            
            thisForm.lstPrintableArea.options[previousIndex].value=selectedValue;
            thisForm.lstPrintableArea.options[previousIndex].text=selectedText;
            thisForm.lstPrintableArea.options[previousIndex].selected="true";
          }
        }
      }
      
      function btnDnclick(){
        var thisForm=document.forms[0];
        var selectedValue;
        var selectedText;
      
        var selectedIndex=thisForm.lstPrintableArea.selectedIndex;
        var nextIndex=selectedIndex+1;
        var maxLenght=thisForm.lstPrintableArea.options.length;
      
          if(selectedIndex!=-1){
            selectedValue=thisForm.lstPrintableArea.options[selectedIndex].value;
            selectedText=thisForm.lstPrintableArea.options[selectedIndex].text;
            
            if(selectedIndex<((maxLenght)-1)){
            thisForm.lstPrintableArea.options[selectedIndex].value=thisForm.lstPrintableArea.options[nextIndex].value;
            thisForm.lstPrintableArea.options[selectedIndex].text=thisForm.lstPrintableArea.options[nextIndex].text;
            
            thisForm.lstPrintableArea.options[nextIndex].value=selectedValue;
            thisForm.lstPrintableArea.options[nextIndex].text=selectedText;
            thisForm.lstPrintableArea.options[nextIndex].selected="true";
          }
        }
      }
    -->
    </script>
  </HEAD>
  <BODY >

  <html:form action="/printableAreaPriorityAction" >
  <html:hidden property="hdnSearchPageNo" />
  <!--<html:hidden property="printableAreaTblPk" />-->
    <jsp:include page="header.jsp" />
    <!-- Tab Table Starts -->
    <table  width="970px" border="0" align="center" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200px" class="tab" height="20px">
          <div align="center"><bean:message key="tab.PrintableArea" /></div>
        </td>
        <td width="770px" >
        </td>
      </tr>
    </table>
    <!-- Tab Table Ends -->
    <!--Main Table Starts Here -->
    <table width="970px" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
      <td height="505px" class="bgColor_DFE7EC bdrTopColor_333333 bdrLeftColor_333333 bdrBottomColor_333333 bdrRightColor_333333">
        <!--Add Table Starts Here -->
        <table width="500px"  border="0" align="center" class="bdrColor_336666">
          <tr>
            <th height="20px" colspan="2"><div align="center">
              <bean:message  key="page.PrintableAreaPriority"/></div>
            </th>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td >
              <div align="center">
                <html:select property="lstPrintableArea" name="printableAreaPriorityForm" size="4" tabindex="13" style="width:80%" styleId="orderList">
                  <html:options name="printableAreaPriorityForm" labelProperty="printableAreaName" property="printableAreaTblPk"/>
                </html:select>
              </div>
            </td>
            <td width="100px">
              <div align="center">
                <table border="0" align="center">
                  <tr>
                    <td >
                      <html:button property="btnUp" styleClass="buttons" onclick="btnUpClick();" tabindex="11"><bean:message  key="btn.Up" /></html:button>
                    </td>
                  </tr>
                  <tr>
                    <td >&nbsp;</td>
                  </tr>
                  <tr>
                    <td >
                    <html:button property="btnDown" styleClass="buttons" onclick="btnDnclick();" tabindex="12"><bean:message  key="btn.Down" /></html:button>
                    </td>
                  </tr>
                </table>
                </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
              <div align="right">
                <html:button property="btnSave" styleClass="buttons" tabindex="14" onclick="btncSavelick();"><bean:message  key="btn.Save" /></html:button>
                <html:cancel  styleClass="buttons" style="margin-right:5px" tabindex="15" ><bean:message  key="btn.Cancel" /></html:cancel>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
        </table>
        <!--Add Table Ends Here -->

        <table cellpadding="0" cellspacing="0" ><tr><td height="3px"></td></tr></table>        

        <!-- Status Bar Table Starts-->
        <table id="tblStatusBar" align="center" width="500px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2" >
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
    <!--Main Table Ends Here -->
  </html:form>
  </BODY>
</HTML>
