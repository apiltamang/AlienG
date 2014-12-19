<%@ page import="ui.ali.*" %>
<%@ page import="java.util.ArrayList"%>




<html>
<head>
<title>Results</title>
 <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="my-template.css" rel="stylesheet">

</head>
<BODY>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
<div class="container">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">AlienG2</a>
  </div>
  <div class="collapse navbar-collapse">
    <ul class="nav navbar-nav">
	<%@ include file="NavigationPane.jsp" %>
    </ul>
  </div><!--/.nav-collapse -->
</div>
</div>

<div class="container">

 <%
	           String user=session.getAttribute("username").toString();
    			ArrayList<result> res = result.readResult(user);	            
%>

<form action="ViewOrderDetails.jsp" method="POST">
<table class="table table-hover table-bordered">

<tr>
<td><h4>Order Number</h4></td>
<td><h4>User</h4></td>
<td><h4>Status</h4></td>
</tr>
<%
int count=0;
for(result i: res){
	count++;
	String printOrdNum=null;
	if(count==res.size())
		printOrdNum=String.format("<td><input type='radio' name='ordNum'"+ 
							"value='%d' checked='true'>%d</td>",i.getOrdNum(),i.getOrdNum());
	else
		printOrdNum=String.format("<td><input type='radio' name='ordNum'"+ 
				"value='%d'>%d</td>",i.getOrdNum(),i.getOrdNum());
	
	String printUser="<td>" + i.getUser()+ "</td>";
	
	if(i.getProcessStatus().equals("In Queue")){
		//nothing has begun yet, so just display statuses
		out.println("<tr class=\"warning\">");
		
		out.println(printOrdNum);out.println(printUser);
		
	}else if(i.getProcessStatus().equals("Started")){
		out.println("<tr class=\"danger\">");
		out.println(printOrdNum);out.println(printUser);
		
	//The process has completed. I.e. Both AlienG and PhyloG are completed.		
	}else if(i.getProcessStatus().equals("Completed")){
		out.println("<tr class=\"success\">");
		out.println(printOrdNum);out.println(printUser);
	}else{
		out.println("<tr class=\"danger\">");
		out.println(printOrdNum);out.println(printUser);
	}
	
	//Now close the table
	out.println("<td>" + i.getProcessStatus() + "</td>");		//display Current Status 
	out.println("</tr>");
}
%>
<tr>
	<td colspan="3" align="center"><input type="submit" value="View Order Details"></td>
</tr>
</table>
</form>

</div>
</BODY>
</html>



