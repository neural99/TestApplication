<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%@page import="bean.TestedBean"%>
<%@page import="bean.AnswerBean"%>
<%@page import="bean.TestBean"%>
<%@page import="bean.QuestionBean"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:useBean id="tested" class="bean.TestedBean" scope="session" />
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
    
    
    TestBean tb = Facade.getTestFromId(testID);
    List<QuestionBean> questions = Facade.getQuestions(tb);
    
    tested = Facade.beginTest(user, tb);
    session.setAttribute("tested", tested);
    
%>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%= tb.getName() %></title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;"><%= tb.getName() %></span></h2>
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
					<form method="POST" action="endTest.jsp?testID=<%= testID %>">
					<%
						if(Facade.isTestAvalible(user, tb)){
							boolean first=true;
							for (QuestionBean qb : questions) {
								if(qb.getQuestionText() != null){
									if(!first)
										out.println("<br>");
									out.println("<h3>" + qb.getQuestionText() + "</h3>");

									for (AnswerBean ae : qb.getAnswers()) {
										out.println("<input type=\"radio\" name=\"question-" + qb.getId() +  "\" value=\"" + ae.getId() + "\">" + ae.getText() + "<br>");
									}
									first=false;
								}
							}
						}else{
							out.println("Testet är inte längre tilljängligt.");
						}
					%>
                    <input type="submit" value="Skicka in prov">
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
	    <span style="color:#ffff00;"><a href="start.jsp">Gå tillbaka utan att skicka in dina svar</a></span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>
