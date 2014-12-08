<%@ page import="ui.ali.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        if(user.checkUser(username, password) == true)
            {
            session.setAttribute("username",username);
            //TODO: 
            //remove the following!
            
            response.sendRedirect("UploadFile.jsp");
            }
        else{
            session.setAttribute("message","Error : The user and password are not valid. \nPlease try again");
            response.sendRedirect("Message.jsp");
        }
        %>
    </body>
</html>


