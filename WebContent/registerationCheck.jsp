<%@page import="mail.TLSGmail"%>
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
        String password_enter=request.getParameter("password_enter");
        String password_confirm=request.getParameter("password_confirm");
        if(!password_enter.equals(password_confirm)){
        	//password entered does  not match password confirmation field
            session.setAttribute("message","Error : Passowrd confirmation failed. \nPlease try again");
            response.sendRedirect("Message.jsp");
        	
        }
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        
        if(user.newUser(username) == true)
        {
        	  user.addUser(username, password_enter,firstname,lastname);        
            session.setAttribute("message","User  "+ username + " is successfully created!");
            TLSGmail.sendGmail(username, "Account Info for AlienG", "Your username is: "+username+" and your password is: "+password_enter);
            response.sendRedirect("Message.jsp");
        }
        else{
            session.setAttribute("message","Error : This user is already available. \nPlease choose another User Name");
            response.sendRedirect("Message.jsp");
        }
        %>
    </body>
</html>


