<%-- 
    Document   : start
    Created on : May 7, 2012, 8:30:50 PM
    Author     : x1x
--%>

<%@page import="bean.TestBean"%>
<%@page import="bean.CourseBean"%>
<%@page import="bean.Facade" %>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <%
        if (user.getSSN() == null) {
    %>
			<script type="text/javascript">
					window.location = "login.jsp"
			</script>        
    <%
        return;
        }
    %>
    
    
	<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Startsida</title>
    </head>
	<body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
        <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
            <tbody>
                <tr>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                    <td style="background-color: rgb(51, 102, 0);">
<!--                        <h2 style="text-align: center;">-->
                            <span style="color:#ffff00;"><%= user.getFirstName() %> [<%= user.getStatus() %>] | <a href="login.jsp">Logga in som annan användare</a>
							<%
								if(user.getStatus().equalsIgnoreCase("admin")){
									%>
									| <a href="results.jsp">Alla studenters resultat</a>
									| <a href="createUser.jsp">Skapa användare</a>
									| <a href="createCourse.jsp">Skapa kurser</a>
									<%
								}else if(user.getStatus().equalsIgnoreCase("teacher")){
									%>
									| <a href="results.jsp">Studenters resultat</a>
									<%
								}else if(user.getStatus().equalsIgnoreCase("student")){
									%>
									| <a href="results.jsp">Mina resultat</a>
									<%
								}
							%>
							</span>
<!--						</h2>-->
                    </td>
                    <td style="background-color: rgb(51, 102, 0); width: 2px;">
                        &nbsp;</td>
                </tr>
                <tr>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                    <td>
                        <div id="datadiv">
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
                            <%
			if (user.getStatus().equalsIgnoreCase("admin")){
				%><h2>Alla kurser och prov listas nedan</h2><%
			}else if (user.getStatus().equalsIgnoreCase("teacher")){
				%><h2>Du är kursadministratör för följande kurser</h2><%
			}else{
				%><h2>Aktuella kurser och prov</h2><%
			}
			
			boolean createTest = false;
			if(user != null && (user.getStatus().equalsIgnoreCase("admin") || user.getStatus().equalsIgnoreCase("teacher"))) createTest = true;
			
            for (CourseBean c : Facade.getCourses(user)) {
						out.println("<h3>Kurs: " + c.getName() + " (" + c.getCourseCode() + ") ges av " + c.getInstitution() + ".</h3>");
						boolean first = true;
						boolean firstA = true;
						out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 780px;\">");
						out.println("<tbody><tr><td style=\"background-color: rgb(255, 255, 221); width: 20px;\">&nbsp;</td>");
						out.println("<td style=\"background-color: rgb(255, 255, 221);\">");
						for (TestBean tb : Facade.getTests(c)) {
							boolean isTestAvalible = Facade.isTestAvalible(user, tb);
							if(createTest){
								if(isTestAvalible){
									if (!firstA) out.println("<br>");
										firstA = false;
									out.println("<div id=\"formdivremove " + c.getId() + "\">");
										out.println("<form method=\"POST\" action=\"viewTest.jsp?testID=" + tb.getId() + "\">");
											//out.println("<b>Prov:</b> " + tb.getName() + " " + tb.getDescription() + " " + tb.getPointsAwarded() + " poäng , " + tb.getStartDate() + " till " + tb.getStartDate() + ".");
											%>
	<!--										<a href=<%= "viewTest.jsp?testID="+tb.getId() %> >-->
												<b>Test: <%= tb.getName() %></b> | Maxpoäng: <%= tb.getPointsAwarded() %><br>
												<% if(tb.getStopDate() != null) out.println("<b>Tillgängligt till: "+tb.getStopDate()+"</b>. ");%><%= tb.getDescription() %>
	<!--										</a>-->
											<%
											out.println("<br><input type=\"submit\" value=\"Visa eller ändra Test\" />");
										out.println("</form>");
									out.println("</div>");
								}
							} else {
								if(isTestAvalible){
									if (!first) out.println("<br>");
										first = false;
									out.println("<div>");
										out.println("<form method=\"POST\" action=\"doTest.jsp?testID=" + tb.getId() + "\">");
											%>
<!--											<a href=<%= "doTest.jsp?testID="+tb.getId() %> >-->
												<b>Test: <%= tb.getName() %></b> | Maxpoäng: <%= tb.getPointsAwarded() %><br>
												<% if(tb.getStopDate() != null) out.println("<b>Tillgängligt till: "+tb.getStopDate()+"</b>. ");%><%= tb.getDescription() %>
<!--											</a>-->
											<%
											out.println("<br><input type=\"submit\" value=\"Starta test\" />");
										out.println("</form>");
									out.println("</div>");
								}
							}
						}
					out.println("</td></tr></tbody></table>");
					if(createTest){
						out.println("<div id=\"formdiv " + c.getId() + "\">");
							out.println("<form method=\"POST\" action=\"createTest.jsp?courseID=" + c.getId() + "\">");
								out.println("<br>");
								out.println("<input type=\"submit\" value=\"Skapa Test\" />");
							out.println("</form>");
						out.println("</div>");
					}
				out.println("<hr>");
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
                    <td style="background-color: rgb(51, 102, 0);">
                        <span style="color:#ffff00;"></span></td>
                    <td style="background-color: rgb(51, 102, 0);">
                        &nbsp;</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>