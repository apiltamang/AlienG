<%@ page import="ui.ali.*" %>
<%@ page import="al.ali.mysql.MySQLAccess" %>




<html>
<head>
<title>Order Details</title>
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
	Integer ordNum=Integer.parseInt(request.getParameter("ordNum"));
    
	session.setAttribute("ordNum", ordNum);
    Config res=MySQLAccess.readOneRequest("OrderDetails", ordNum);
    
    session.setAttribute("orderHomeDir",res.getOrderHomeDir());
    System.out.println("orderHomeDir: ViewOrderDetails.jsp: "+res.getOrderHomeDir());
%>
<table class="table table-hover table-bordered">
	<tr>
		<td><h4>Name</h4></td>
		<td><h4>Value</h4></td>
	</tr>
	<tr>
		<td>Order#</td>
		<td><%=res.getOrdNum() %></td>
	</tr>
	<tr>
		<td>User</td>
		<td><%=res.getUser() %></td>
	</tr>
	<tr>
		<td>AlienG EvalueMax</td>
		<td><%=res.getAlienG_AIEvalue().toString()%></td>
	</tr>
	
	<tr>
		<td>AlienG Coverage</td>
		<td><%=res.getAlienG_Coverage() %></td>
	</tr>
	
	<tr>
		<td>AlienG ScoreMin</td>
		<td><%=res.getAlienG_ScoreMin()%></td>
	</tr>

	<tr>
		<td>AlienG Group-1</td>
		<td><%=res.getAlienG_group1() %></td>
	</tr>

	<tr>
		<td>AlienG Group-2</td>
		<td><%=res.getAlienG_group2() %></td>
	</tr>
	
	<tr>
		<td>AlienG AI Evaluate</td>
		<td><%=res.getAlienG_AIEvalue() %></td>
	</tr>
	
	<tr>
		<td>AlienG Ratio Score</td>
		<td><%=res.getAlienG_ratioScore() %></td>
	</tr>
	
	<tr>
		<td>AlienG InFile</td>
		<td><%=res.getAlienG_infile() %></td>
	</tr>
	
	<tr>
		<td>AlienG Exclusion</td>
		<td><%=res.getAlienG_exclusion() %></td>
	</tr>
	
	<tr>
		<td>AlienG blast parameters</td>
		<td><%=res.getAlienG_blastparam() %></td>
	</tr>
	
	
	<tr>
		<td>AlienG blast input </td>
		<td><%=res.getAlienG_infile() %></td>
	</tr>
	
	<tr>
		<td>AlienG Number of Tables</td>
		<td><%=res.getAlienG_numberOfTables() %></td>
	</tr>
	
	<tr>
		<td>Process Status </td>
		<%  
			
			if(res.getProcessStatus().equals("Completed")){
				//temporarily testing download of a file from different directories.
				out.println("<td><a href=\"DownloadAlienGServlet\">Download</a></td>");
				
			}else{
				out.println("<td>"+res.getProcessStatus()+"</td>");
				//out.println("<td><a href=\"DownloadAlienGServlet\">Download</a></td>");
			}
		%>
	</tr>	
	
	
	<tr>
		<td>Drop Order</td>
		<td><%
			if(!res.getProcessStatus().equals("Started")){
				out.println("<a href=\"DropOrder.jsp\">Drop Order [Please review carefully!]</a>");
			
			}else{
				out.println("res.getProcessStatus()");
			}
			%></td>
			
	</tr>
	<tr>
		<td colspan="2" align="center"><a href="results.jsp">Go back</a></td>
	</tr>	
</table>
</div>
</BODY>
</html>

