<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%@page import="bean.GradeBean"%>
<%@page import="bean.QuestionBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.TestBean"%>
<%@page import="bean.AnswerBean"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:setProperty name="user" property="*" />

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

//        TestBean tb = new TestBean();
//        tb.setId(testID);
TestBean tb = Facade.buildTestBeanFromID(testID);
List<GradeBean> gbs = Facade.getGrades(tb);
if(gbs.size()>0){
	%>
		<script type="text/javascript">
			window.location = 'addGrades.jsp?testID=<%= tb.getId() %>'
		</script>
	<%
		out.println("<div>");
			out.println("<form method=\"POST\" action=\"addGrades.jsp?testID=" + tb.getId() + "\">");
				out.println("Du har påbörjat betygssättning av kursen, du kan inte längre modifiera testet.<br>Tryck på knappen nedan för att fortsätta.<br>");
				out.println("<input type=\"submit\" value=\"-- Lägg till betyg och slutför test --\" />");
			out.println("</form>");
		out.println("</div>");
	return;
}
List<QuestionBean> questions = Facade.getQuestions(tb);
for (QuestionBean q : questions) {
	String s = request.getParameter("correct-" + q.getId());
	if (s != null) {
		long answerID = -1;
		try{
			answerID = Long.parseLong(s);
			Facade.setCorrectAnswer(user, q, answerID);
			q.setCorrectAnswerId(answerID);
		} catch(NumberFormatException e){
			%>
				<script type="text/javascript">
					window.location = "start.jsp"
				</script>
			<%
			out.println("Error, there is no such answer!");
			return;
		}
	}
}
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
	      <span style="color:#ffff00;"><%= tb.getName() %></span>
		</h2>
		<h3>
			<span style="color:#ffff00;"><%= tb.getDescription() %></span>
		</h3>
	  </td>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	</tr>
	<tr>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	  <td>
		<div id="datadiv">
	      <a href="addQuestion.jsp?testID=<%= tb.getId() %>" style="color: rgb(0,0,255)">Lägg till fråga</a>
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
	if(Facade.isTestAvalible(user, tb)){
        for (QuestionBean qb : questions) {
            out.println("<div>");
				out.println("<form method=\"POST\" action=\"addAnswer.jsp?testID=" + tb.getId() + "&questionID=" + qb.getId() + "\">");
					out.println("<b>Fråga:</b> " + qb.getIndex() + " " + qb.getQuestionText() + " " + qb.getPointValue() + " poäng ");
					out.println("<input type=\"submit\" value=\"Add answer\" />");
				out.println("</form>");
            out.println("</div>");
            
            
            out.println("<form method=\"POST\" action=\"viewTest.jsp?testID=" + testID + "\">");
            for (AnswerBean ae : qb.getAnswers()) {
                
                if (qb.getCorrectAnswerId() == ae.getId()) {
                    out.println("<input type=\"radio\" name=\"correct-" + qb.getId() +  "\" value=\"" + ae.getId() + "\" checked >");
                } else {
                    out.println("<input type=\"radio\" name=\"correct-" + qb.getId() +  "\" value=\"" + ae.getId() + "\">");
                } 

                out.println(ae.getIndex() + " " + ae.getText() + "<br>");
            }
            out.println("<input type=\"submit\" value=\"Set as correct answer\"></form>");
        }
		
		out.println("<div>");
			out.println("<form method=\"POST\" action=\"addGrades.jsp?testID=" + tb.getId() + "\">");
				out.println("<input type=\"submit\" value=\"-- Lägg till betyg och slutför test --\" />");
				out.println("<b>OBS: </b>Du kan inte gå tillbaka då du har börjat sätta betygs graderingar!");
			out.println("</form>");
		out.println("</div>");
		
		
//			out.println("<div>");
//				out.println("<form method=\"POST\" action=\"finalizeTest.jsp?testID=" + tb.getId() + "\">");
//					out.println("<input type=\"submit\" value=\"-- Slutför Test --\" />");
//				out.println("</form>");
//			out.println("</div>");
	}else{
		out.println("Testet är inte längre tilljängligt.");
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
	    <span style="color:#ffff00;"><a href="start.jsp">Tillbaka</a></span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>
