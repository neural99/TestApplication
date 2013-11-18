<%-- 
    Document   : testlogin
    Created on : May 6, 2012, 6:15:24 PM
    Author     : x1x
--%>
<%@page import="bean.Facade"%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:setProperty name="user" property="*" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (user != null && user.getSSN() != null) {
        user = Facade.login(user.getSSN(), user.getPassHash());
    }
%>

<% 
    if (user != null && user.getFirstName() != null) {
        %>
        Hej, <%= user.getFirstName() %>
<%
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
		<form method="post" action="testlogin.jsp">
			SSN: <input type="input" name="SSN"><br/>
			Password: <input type="input" name="passHash">
			<input type="submit" value="Logga in">
		</form>
	</body>
</html>
