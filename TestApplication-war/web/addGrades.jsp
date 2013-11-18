<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="grade" class="bean.GradeBean" scope="request" />
<jsp:setProperty name="grade" property="*" />

<%
    long testID = -1;
    try {
        testID = Long.parseLong(request.getParameter("testID"));
    } catch (NumberFormatException e) {
%>
<script type="text/javascript">
    window.location = "start.jsp"
</script>
<%
		out.println("Error, there is no such test!");
        return;
    }
%>

<!DOCTYPE html>
<html>
	<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lägg till betyg</title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">Lägg till betyg</span>
		</h2>
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
    if (grade.getGrade() != null) {
        boolean res = Facade.addGrade(user, grade, testID);
        if (res) {
//            out.println("Betyget har lagts till");
        } else {
            out.println("Betyget kunde ej läggas till");
        }
    }
	TestBean tb = Facade.buildTestBeanFromID(testID);
	List<GradeBean> gbs = Facade.getGrades(tb);
	gbs = Facade.sortOnPoints(gbs);
	out.println("<br><b>Maxpoäng på testet: "+Facade.getGradePoints(tb)+"</b><br>");
//	out.println("grades: "+ gbs.size() +" <br>");
	for(GradeBean gb : gbs){
		if(gb.getGrade()==null){
			out.println("gb.getGrade()==null");
		}else{
			out.println(gb.getGrade() + " : " + gb.getLevelPoints() + "<br>");
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
<form method="POST" action="addGrades.jsp?testID=<%= testID%>">
    Betyg: <input type="text" name="grade" /><br>
	Motsvarande poängsumma: <input type="text" name="levelPoints" /><br>
    <input type="submit" value="Lägg till betyg">
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
	    <span style="color:#ffff00;">
			<%
			if(gbs.size()>=2){
				out.println("<div>");
					out.println("<form method=\"POST\" action=\"finalizeTest.jsp?testID=" + tb.getId() + "\">");
						out.println("<input type=\"submit\" value=\"-- Slutför Test --\" />");
					out.println("</form>");
				out.println("</div>");
			}else{
				out.println("Du måste lägga till minst "+((int)2-gbs.size())+" betyg till för att kunna slutföra testet.");
			}
			if(gbs.size()==0){
				%>
				<span style="color:#ffff00;"><a href="viewTest.jsp?testID=<%= testID %>">Tillbaka</a></span>
				<%
			}
			%>
		</span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>