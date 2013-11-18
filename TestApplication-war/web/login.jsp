<%-- 
    Document   : login.jsp
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%-- BEAN FACADE COMMUNICATION SETUP --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- IMPORT ALL NEEDED BEANS HERE, userBean always needed --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:setProperty name="user" property="*" />

<%-- JSP/JAVASCRIPT CONTROL LOGIC HERE, LOGIN EXAMPLE BELOW --%>
<%
    
    
    if (user != null && user.getSSN() != null && request.getParameter("SSN") != null) {     
        
        user = Facade.login(user.getSSN(), user.getPassHash());
        
        // JSP automagic doesn't work without it
        session.setAttribute("user", user);
        
        // Successful login
        if (user != null) { 
%>
        <script type="text/javascript">
                window.location = "start.jsp"
        </script>
<%
        } else {
            session.invalidate();
%>          
        <script type="text/javascript">
                window.location = "login.jsp?msg=Felaktig%20Inloggning"
        </script>
<%
        }
    }
%>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Logga in</title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    
	      
			<span style="color:#ffff00;">
<%
				if (user != null && user.getFirstName() != null) {
					out.println("Du är redan inloggad som ");
%>
				<%= user.getFirstName() %> [<%= user.getStatus() %>] | <a href="start.jsp">Till startsidan</a>
				<%
					if(user.getStatus().equalsIgnoreCase("admin")){
						%>
						| <a href="createUser.jsp">Skapa användare</a>
						| <a href="createCourse.jsp">Skapa kurser</a>
						<%
					}
				%>
			</span>
<%
			}else{
%>
			<h2 style="text-align: center;">
				<span style="color:#ffff00;">Logga in</span>
			</h2>
<%
			}
%>
	  </td>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	</tr>
	<tr>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	  <td>
	    <div id="datadiv">
<h1><% if (request.getParameter("msg") != null) out.println(request.getParameter("msg")); %> </h1>
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
	      <form action="login.jsp" method="post">
	        <table border="0" cellpadding="0" cellspacing="0">
		  <tbody>
		    <tr>
		      <td>
			Personnummer:</td>
		      <td>
			<input name="SSN" type="input" /></td>
		    </tr>
		    <tr>
		      <td>
			L&ouml;senord:</td>
		      <td>
			<input name="passHash" type="input" /></td>
		    </tr>
		    <tr>
		      <td>
			<input type="submit" value="Logga in" /></td>
		      <td>
			&nbsp;</td>
		    </tr>
		  </tbody>
		</table>
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
	    <span style="color:#ffff00;"></span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>
