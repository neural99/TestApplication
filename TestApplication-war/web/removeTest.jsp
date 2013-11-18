
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />

<% 
	long testID = -1;
	try{
		testID = Long.parseLong(request.getParameter("testID"));
	}catch(NumberFormatException e){
		%>
			<script type="text/javascript">
					window.location = "start.jsp"
			</script>
		<%
		out.println("Error, there is no such test!");
		return;
	}
	Facade.removeTest(user, testID);
%>
<script type="text/javascript">
		window.location = "start.jsp"
</script>