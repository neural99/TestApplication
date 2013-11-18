<%-- 
    Document   : grades
    Created on : May 6, 2012, 8:18:36 PM
    Author     : x1x
--%>

<%@page import="bean.Facade"%>
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
    for (String g : Facade.getGrades()) { 
        out.println(g);
    }
%>
        </pre>
    </body>
</html>
