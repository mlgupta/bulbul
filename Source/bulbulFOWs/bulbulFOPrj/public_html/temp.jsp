<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<title>Hello World</title>
<script>
 var theAddButtonTimer;
  function btnAdd_onclick() {
    MM_showHideLayers('lyrAddButton','','show')
  }

  function btnAdd_onmouseover() {
    clearTimeout(theAddButtonTimer);
  }

  function btnAdd_onmouseout() {
    theAddButtonTimer=setTimeout("MM_showHideLayers('lyrAddButton','','hide')",500);
  }
  function window_onload(){
  alert(getParameter("src"));
  }
  
  function getParameter(param){
    var queryString=window.location.search.substring(1);
    var valuePairArray=queryString.split("&");
    
    for(var i=0;i<valuePairArray.length;i++){
      var valuePair=valuePairArray[i].split("=");
      if(param==valuePair[0]){
        return valuePair[1];
      }
    }    
  }
</script>

</head>
<body onload="return window_onload()" >
<%
String strPageNumber="1";
if(request.getParameter("pageNumber")!=null){
strPageNumber=request.getParameter("pageNumber");
}

int pageNumber=Integer.parseInt(strPageNumber);
int pageCount=2;
int noOfPageNumbers=5;
  int startCounter = Math.round(pageNumber/noOfPageNumbers)*noOfPageNumbers;
  if((pageNumber%noOfPageNumbers)!=0){
		startCounter+=1;
	}
  if((pageNumber==pageCount) ||((pageCount-pageNumber)<noOfPageNumbers) ){    
    startCounter=(((pageCount-noOfPageNumbers)+1)<=0)?startCounter:((pageCount-noOfPageNumbers)+1);
  }
  
  int endCounter = startCounter+noOfPageNumbers;
  if ((startCounter+noOfPageNumbers)>pageCount){
    endCounter=pageCount+1; 
  }
%>

<%if (pageNumber==1 && pageNumber==pageCount){%>
<span>First</span>
<span>Prev</span>
<span>[1]</span>
<span>Next</span>
<span>Last</span>
<%}else if((pageNumber==1 && pageNumber!=pageCount)){%>
<span>First</span>
<span>Prev</span>
<%	for(int counter=startCounter; counter<endCounter; counter++){ %>
    <%if(counter==pageNumber){%>
      <span>[<%=counter%>]</span>
    <%}else{%>
      <a href="temp.jsp?pageNumber=<%=counter%>"><%=counter%></a>
    <%}%>
<%	}%>

<%if ((endCounter)<=pageCount){%>
  <a href="temp.jsp?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
<%}else{%>
<span>Next</span>
<%}%>
<a href="temp.jsp?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
<%}else if((pageNumber!=1 && pageNumber==pageCount)){%>
<a href="temp.jsp?pageNumber=<%=1%>">First(1)</a>
<%if((startCounter-1)<=0){%>
<span>Prev</span>
<%}else{%>
<a href="temp.jsp?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
<%}%>
<%	for(int counter=startCounter; counter<endCounter; counter++){ %>
    <%if(counter==pageNumber){%>
      <span>[<%=counter%>]</span>
    <%}else{%>
      <a href="temp.jsp?pageNumber=<%=counter%>"><%=counter%></a>
    <%}%>
<%	}%>
<span>Next</span>
<span>Last</span>
<%}else if((pageNumber!=1 && pageNumber!=pageCount)){%>
<a href="temp.jsp?pageNumber=<%=1%>">First(1)</a>
<%if((startCounter-1)<=0){%>
<span>Prev</span>
<%}else{%>
<a href="temp.jsp?pageNumber=<%=startCounter-1%>">Prev(<%=startCounter-1%>)</a>
<%}%>
<%	for(int counter=startCounter; counter<endCounter; counter++){ %>
    <%if(counter==pageNumber){%>
      <span>[<%=counter%>]</span>
    <%}else{%>
      <a href="temp.jsp?pageNumber=<%=counter%>"><%=counter%></a>
    <%}%>
<%	}%>
<%if ((endCounter)<=pageCount){%>
  <a href="temp.jsp?pageNumber=<%=endCounter%>">Next(<%=endCounter%>)</a>
<%}else{%>
<span>Next</span>
<%}%>
<a href="temp.jsp?pageNumber=<%=pageCount%>">Last(<%=pageCount%>)</a>
<%}%>

</body>
</html>