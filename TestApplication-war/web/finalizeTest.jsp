<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%@page import="bean.GradeBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.TestBean"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:setProperty name="user" property="*" />

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

<%
TestBean tb = Facade.buildTestBeanFromID(testID);
List<GradeBean> gbs = Facade.getGrades(tb);
if(gbs.size()<2){
	%>
		<script type="text/javascript">
				window.location = "addGrades.jsp?testID=" + testID + "\""
		</script>
	<%
		out.println("<div>");
			out.println("<form method=\"POST\" action=\"addGrades.jsp?testID=" + tb.getId() + "\">");
				out.println("Du måste lägga till minst två betyg för att kunna slutföra testet.");
				out.println("<input type=\"submit\" value=\"-- Lägg till betyg och slutför test --\" />");
			out.println("</form>");
		out.println("</div>");
	return;
}
%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Slutför <%= tb.getName() %></title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">Slutför <%= tb.getName() %></span></h2>
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
if(Facade.isTestAvalible(user, tb)){
	if(Facade.finalizeTest(user, tb)){
		out.println("Testet kommer nu att vara synligt för studenter under tidsperioden.<br>");
		out.println("Testet kan inte längre modifieras.");
	}else{
		out.println("Det gick inte att slutföra testet.");
	}
}else{
%>
	Tested är inte längre tilljängligt. Det gick inte att slutföra testet.
<%
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
