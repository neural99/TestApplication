
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="bean.CourseBean"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="newperson" class="bean.UserBean" scope="page" />
<jsp:setProperty name="newperson" property="*" />

<%-- KONTROLLOGIK. EXEMPEL FÖR LOGIN NEDAN --%>
 

<%
    List<CourseBean> courses = Facade.getCourses(user);
%>


<%
	if(user.getStatus() == null || !user.getStatus().equalsIgnoreCase("admin")){
		out.println("Du har inte behörighet att skapa användare, kontakta admin.");
                return;
	}
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Skapa användare</title>
    </head>
    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
            <tbody>
                <tr>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                    <td style="background-color: rgb(51, 102, 0);">
                        <h2 style="text-align: center;">
                            <span style="color:#ffff00;">Skapa användare</span></h2>
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
    if (newperson != null && newperson.getSSN() != null) {

        ArrayList<CourseBean> list2 = new ArrayList<CourseBean>();

        String[] coursesChosen = request.getParameterValues("c");
        if (coursesChosen != null) {
            for (int i = 0; i < coursesChosen.length; i++) {

                String course = coursesChosen[i];
                int id = 0;
                try {
                    id = Integer.parseInt(course);
                    CourseBean cb = new CourseBean();
                    cb.setId(id);
                    list2.add(cb);
                } catch (NumberFormatException e) {
                    out.println("Något gick fel1");
                    return;
                }
            }
        }
        boolean res = Facade.register(user, newperson, list2);
        if (res) {
            out.println("Användare skapad");
        } else {
            out.println("Något gick fel2");
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
                            <form method="POST" action="createUser.jsp">
                                SSN: <input type="text" name="SSN" /> <br>
                                Password: <input type="text" name="passHash" /><br>
                                First name: <input type="text" name="firstName" /><br>
                                Last name: <input type="text" name="lastName" /><br>
                                Status: <select name="status"><option>student</option><option>teacher</option></select><br>
                                Start year: <input type="text" name="startYear" /><br>
                                Gender: <input type="radio" name="gender" value="male" /> Male <input type="radio" name="gender" value="female" /> Female<br>
                                Address: <input type="text" name="address" /><br>
                                Tel: <input type="text" name="tel" /><br>
                                Email: <input type="text" name="email" /><br>
                                Homepage: <input type="text" name="homepage" /><br>


                                <%
                                    for (CourseBean cb : courses) {
                                        out.println("<input type=\"checkbox\" name=\"c\" value=\"" + cb.getId() + "\"> " + cb.getName() + "<br>");
                                    }
                                %>


                                <input type="submit" value="Skapa användare" />



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
