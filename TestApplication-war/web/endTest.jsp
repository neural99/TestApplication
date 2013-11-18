<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%@page import="java.util.HashMap"%>
<%@page import="bean.QuestionBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.TestBean"%>
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
%>

<%
TestBean tb = Facade.getTestFromId(testID);
%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Resultat <%= tb.getName() %></title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">Resultat <%= tb.getName() %></span></h2>
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
	List<QuestionBean> questions = Facade.getQuestions(tb);
	HashMap<Long, Long> answersGiven = new HashMap<Long, Long>();
	for (QuestionBean q : questions) {
		String s = request.getParameter("question-" + q.getId());
		if (s != null) {
			long answerID = -1;
			try{
					answerID = Long.parseLong(s);
					answersGiven.put(q.getId(), answerID);
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
	String grade = Facade.submitTest(user, tested, answersGiven);
	request.setAttribute("tested", null);
	out.println("Du fick " + grade);
}else{
%>
	Tested är inte längre tilljängligt. Ditt svar har <b>inte</b> registrerats!
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
