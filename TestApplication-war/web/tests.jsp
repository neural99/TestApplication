<%-- 
    Document   : tests
    Created on : May 6, 2012, 7:29:17 PM
    Author     : x1x
--%>

<%@page import="bean.TestBean"%>
<%@page import="bean.Facade"%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <pre>
<%
            for(TestBean t : Facade.getTests(user)) {
               out.println(t.getName() + " " + t.getDescription());
            }
%>
        </pre>
    </body>
</html>