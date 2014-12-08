<%@ page import="ui.ali.*" %>
<%@ page import="al.ali.main.*" %>
<%@ page import="al.ali.blast.*" %>
<%@ page import="filehandler.FileHandler" %>
<%@ page import="al.ali.mysql.MySQLAccess" %>



<jsp:useBean id="oo" class="ui.ali.Config" scope="session"/>
<jsp:setProperty name="oo" property="*"/> 

<HTML>
<head>
<title>Confirmation Page</title>
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
            out.println("<H1>Dear  "+user +"</h1>\n");
%>

<h6>Your order with following parameters has been added to the server</h6>
<h6>Results will be available in few hours</h6><BR>
<h6>As soon as the results get ready you will be notified by an email</h6>

<h6>infile: <%= oo.getAlienG_infile() %></h6>
<h6>AlienG_EvalueMax: <%= oo.getAlienG_EvalueMax() %></h6>
<h6>AlienG_Coverage: <%= oo.getAlienG_Coverage() %></h6>
<h6>AlienG_ScoreMin: <%= oo.getAlienG_ScoreMin() %></h6>
<h6>AIEVALUE: <%= oo.getAlienG_AIEvalue() %></h6>
<h6>ratio: <%= oo.getAlienG_ratioScore() %></h6>
<h6>param: <%= oo.getAlienG_blastparam() %></h6>
<h6>group 1: <%= oo.getAlienG_group1() %></h6>
<h6>group 2: <%= oo.getAlienG_group2() %></h6>
<%


order.addRequest(user, oo);
String userUploadedFile=session.getAttribute("userUploadedFile").toString();
MySQLAccess.modifyUserRequest(user,userUploadedFile);
%>
</div>
</BODY>
</HTML>