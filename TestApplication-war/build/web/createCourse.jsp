<%@page import="bean.UserBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="bean.CourseBean"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="course" class="bean.CourseBean" scope="request" />
<jsp:setProperty name="course" property="*" />

<%-- KONTROLLOGIK. EXEMPEL FÖR LOGIN NEDAN --%>

<%
    List<UserBean> teachers = Facade.getUsersByStatus("admin", "teacher");     
%>


<%
	if(user.getStatus() == null || !user.getStatus().equalsIgnoreCase("admin")){
		out.println("Du har inte behörighet att skapa kurser, kontakta admin.");
                return;
	}
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Skapa kurs</title>
    </head>
    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
            <tbody>
                <tr>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                    <td style="background-color: rgb(51, 102, 0);">
                        <h2 style="text-align: center;">
                            <span style="color:#ffff00;">Skapa kurs</span></h2>
                    </td>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                </tr>
                <tr>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                    <td>
                        <div id="datadiv">
<%
        if (course != null && course.getName() != null) {
  
            String ssn = request.getParameter("owner");                 
            boolean res = Facade.createCourse(user, course, ssn);
            if (res) {
                out.println("Kurs lades till");
            } else {
                out.println("Kurs kunde ej läggas till");
            }
        }
%>
                        </div>
                    </td>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                </tr>
                <tr>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                    <td>
                        <div id="formdiv">
                            <form method="POST" action="createCourse.jsp">
                                Name: <input type="text" name="name" /><br>
                                Course code: <input type="text" name="courseCode" /><br>
                                Institution: <input type="text" name="institution" /><br>
  
                                Owner: <select name="owner">
                                    <%
                                        for (UserBean ub : teachers) {
                                            out.println("<option value=\"" + ub.getSSN() + "\">" + ub.getFirstName() + " " + ub.getLastName() + "</option>");
                                        }
                                    %>
                                    
                                </select>
                                    <BR>
                                <input type="submit" value="Skapa kurs" />

                            </form>
                        </div>
                    </td>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                </tr>
                <tr>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                    <td style="background-color: rgb(51, 102, 0);">
                        <span style="color:#ffff00;"><a href="start.jsp">Tillbaka</a></span></td>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
