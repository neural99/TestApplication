
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="answer" class="bean.AnswerBean" scope="request" />
<jsp:setProperty name="answer" property="*" />

<%
    long testID = -1;
    long questionID = -1;
    try {
        testID = Long.parseLong(request.getParameter("testID"));
        questionID = Long.parseLong(request.getParameter("questionID"));
    } catch (NumberFormatException e) {
%>
			<script type="text/javascript">
					window.location = "start.jsp"
			</script>
<%
		out.println("Error, there is no such question!");
        return;
    }
%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lägg till svar</title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">Lägg till svar</span>
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
    if (answer.getText() != null) {
        boolean res = Facade.addAnswer(user, answer, questionID);
        if (res) {
            out.println("Svaret har lagts till");
        } else {
            out.println("Svaret kunde ej läggas till");
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
<form method="POST" action="addAnswer.jsp?testID=<%= testID%>&questionID=<%= questionID %>">
    Text:<input type="text" name="text" /><br>

    <input type="submit" value="Lägg till svar">
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
	    <span style="color:#ffff00;"><a href="viewTest.jsp?testID=<%= testID %>">Tillbaka</a></span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>