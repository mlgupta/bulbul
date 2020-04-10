<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"  %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="currentCategoryPk" name="currentCategoryPk"  />
<bean:define id="jsFileLinks" name="merchandiseTreeView" property="jsFileLinks" />
<bean:define id="jsFileName" name="merchandiseTreeView" property="jsFileName" />
<bean:define id="treeOperation" name="treeOperation"  />

<html>
<head>
<title>Merchandise Tree View</title>
<link href="main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="useragent.js"></script>
<script language="JavaScript" type="text/JavaScript" src="treeview.js"></script>
<script src="<%=jsFileName%>"></script>
<%=jsFileLinks%>
<script> 
function window_onload(){

  <logic:equal name="treeOperation"  value="merchandise_tree_view">
    //To bring the Selected folder on the Top in the tree view
    var  currentFolderObj=findObj("<%=currentCategoryPk%>");
    if (currentFolderObj!=null){
      document.body.scrollTop=currentFolderObj.navObj.offsetTop;
    } 
  </logic:equal>
  
}

function userDefinedClickOnFolder(folderId){
  window.top.location.replace("merchandiseRelayAction.do?hdnSearchOperation=merchand_list&merchandiseOrCategoryTblPk="+ folderId  );		     
}

function userDefinedClickOnNode(folderId){
  window.location.replace('merchandiseRelayAction.do?hdnSearchOperation=merchand_tree_view_append&selectedCategoryPk='+ folderId + '&currentCategoryPk=<%=currentCategoryPk%>'  );	
}

</script> 
</head>
<body onload="return window_onload();" style="background-color:#ffffff">
<script>
SetCookie("clickedFolder4List", "<%=currentCategoryPk%>");
SetCookie("highlightedTreeviewLink", "<%=currentCategoryPk%>");
initializeDocument();  
</script>
</body>
</html>
