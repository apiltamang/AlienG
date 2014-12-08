<%@ page import="al.ali.mysql.MySQLAccess" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
	<%
	int ordNum=Integer.parseInt(session.getAttribute("ordNum").toString());
	MySQLAccess.deleteRecordInOrdersTable(ordNum);
    response.sendRedirect("results.jsp");		
	%>
    </body>
</html>
