
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="test" class="bean.TestBean" scope="page" />
<jsp:setProperty name="test" property="*" />


<% if (user.getStatus() == null || !( user.getStatus().equals("admin") || user.getStatus().equals("teacher"))) {
%>
Måste vara lärarare eller admin
<%
    return;
}
%>


<% 
	long courseID = -1;
	try{
		courseID = Long.parseLong(request.getParameter("courseID"));
	}catch(NumberFormatException e){
		%>
			<script type="text/javascript">
					window.location = "start.jsp"
			</script>
		<%
		out.println("Error, there is no such course!");
		return;
	}
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Skapa Test</title>
    </head>
    <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
            <tbody>
                <tr>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                    <td style="background-color: rgb(51, 102, 0);">
                        <h2 style="text-align: center;">
                            <span style="color:#ffff00;">Skapa Test</span></h2>
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
    if (test != null && test.getName() != null && courseID >= 0) {
		if(Facade.createTest(user, test, courseID)){
			out.println("Test skapat.");
		}else{
			out.println("Det gick inte att skapa testet.");
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
                            <form method="POST" action="createTest.jsp?courseID=<%=courseID%>">
                                Name: <input type="text" name="name" /> <br>
                                Description: <input type="text" name="description" /><br>
                                Obligatory: <input type="radio" name="obligatory" value="true" checked /> Yes <input type="radio" name="obligatory" value="false" /> No<br>
								<%
								Date d = new Date();
								Date dd = new Date(d.getTime() + ((int)24*3600*1000));
								%>
								Test avaliable from: <input type="text" name="startDate" value="<%= Facade.toTimestampFormate(d) %>"/> to: <input type="text" name="stopDate" value="<%= Facade.toTimestampFormate(dd) %>"/><br>
                                <input type="submit" value="Skapa Test" />
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
