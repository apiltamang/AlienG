<%@page import="mail.TLSGmail"%>
<%@ page import="ui.ali.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Send Password Reminder</title>
    </head>
    <body>
        <%
        String username=request.getParameter("email");
        String passOfUser=user.findUser(username);
        if(passOfUser==null)
        {
            session.setAttribute("message","Error: Could not find an account with given username.");
            
            response.sendRedirect("Message.jsp");
        }
        else
        {
        	  //user found
        	  TLSGmail.sendGmail(username, "Password Reminder for your AlienG Account", "Your password is: "+passOfUser);
            response.sendRedirect("index.html");
        }
        %>
    </body>
</html>
