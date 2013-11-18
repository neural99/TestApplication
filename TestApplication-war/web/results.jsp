
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- IMPORTERA FASADEN --%>
<%@page import="bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Resultat</title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">Resultat</span>
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
	List<CourseBean> courses = Facade.getCourses(user);
	if(courses.isEmpty()){
		if(user.getStatus().equalsIgnoreCase("teacher")){
			out.println("Du är inte kursadminestratör över några kurser");
		}else if(user.getStatus().equalsIgnoreCase("admin")){
			out.println("Det finns inte några kurser");
		}else{
			out.println("Du går inte några kurser");
		}
	}else if(user.getStatus().equalsIgnoreCase("teacher") || user.getStatus().equalsIgnoreCase("admin")){
		for(CourseBean course : courses){
			List<TestBean> tests = Facade.getTests(course);
			if(tests.isEmpty()){
				continue;
			}
			out.println("<h3>Kurs: " + course.getName() + " (" + course.getCourseCode() + ") ges av " + course.getInstitution() + ".</h3>");
			out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 780px;\">");
			out.println("<tbody><tr><td style=\"background-color: rgb(255, 255, 221); width: 20px;\">&nbsp;</td>");
			out.println("<td style=\"background-color: rgb(255, 255, 221);\">");
			for(TestBean test : tests){
				List<UserBean> users = Facade.getUsers(test);
				for(UserBean ub : users){
					String[] gradeAndSubmitDate = Facade.getGradeAndSubmitedDate(ub, test);
					if(gradeAndSubmitDate.length == 2 && !gradeAndSubmitDate[0].isEmpty() && !gradeAndSubmitDate[1].isEmpty()){
						out.println(""+ test.getName() +" Student: <b>"+ub.getSSN()+"</b> "+ub.getFirstName()+" "+ub.getLastName()+" <b>Betyg: "+gradeAndSubmitDate[0]+"</b> Inskickat:"+gradeAndSubmitDate[1]+".<br>");
					}
				}
			}
			out.println("</td></tr></tbody></table>");
		}
	}else{
		for(CourseBean course : courses){
			List<TestBean> tests = Facade.getTests(course);
			if(tests.isEmpty()){
				continue;
			}
			out.println("<h3>Kurs: " + course.getName() + " (" + course.getCourseCode() + ") ges av " + course.getInstitution() + ".</h3>");
			out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 780px;\">");
			out.println("<tbody><tr><td style=\"background-color: rgb(255, 255, 221); width: 20px;\">&nbsp;</td>");
			out.println("<td style=\"background-color: rgb(255, 255, 221);\">");
			for(TestBean test : tests){
				String[] gradeAndSubmitDate = Facade.getGradeAndSubmitedDate(user, test);
				if(gradeAndSubmitDate.length == 2 && !gradeAndSubmitDate[0].isEmpty() && !gradeAndSubmitDate[1].isEmpty()){
					out.println(""+ test.getName() +" <b>Betyg: "+gradeAndSubmitDate[0]+"</b> Inskickat:"+gradeAndSubmitDate[1]+".<br>");
				}
			}
			out.println("</td></tr></tbody></table>");
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
	  <td style="background-color: rgb(51, 102, 0);">
	    <span style="color:#ffff00;"><a href="start.jsp">Tillbaka</a></span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>