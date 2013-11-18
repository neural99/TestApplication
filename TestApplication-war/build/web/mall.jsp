<%-- 
    Document   : mallsida
    Created on : 2012-maj-08, 12:06:51
    Author     : Team Kappa
--%>

<%-- IMPORTERA FASADEN --%>
<%@page import="bean.Facade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- LADDA DE BÖNOR SOM BEHÖVS, ALLTID UserBeam --%>
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<jsp:setProperty name="user" property="*" />

<%-- KONTROLLOGIK. EXEMPEL FÖR LOGIN NEDAN --%>

<%--

<%
    if (user != null && user.getSSN() != null) {
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
                <h1>Felaktig inloggning</h1>

<%
        }
    }
%>

--%>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>@JSPMALLSIDA@</title>
  </head>
  <body bgcolor="#ffffdd" link="#ffff00" vlink="#ffff00" alink="#ffff00">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 800px;">
      <tbody>
	<tr>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <h2 style="text-align: center;">
	      <span style="color:#ffff00;">@SIDRUBRIK@</span></h2>
	  </td>
	  <td style="background-color: rgb(51, 102, 0); width: 2px;">
	    &nbsp;</td>
	</tr>
	<tr>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	  <td>
	    <div id="datadiv">
	      @INFO / DATA@
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
	      @FORM / SUBMIT@
	    </div>
	  </td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
	<tr>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	  <td style="background-color: rgb(51, 102, 0);">
	    <span style="color:#ffff00;">@HELP@</span></td>
	  <td style="background-color: rgb(51, 102, 0);">
	    &nbsp;</td>
	</tr>
      </tbody>
    </table>
  </body>
</html>
