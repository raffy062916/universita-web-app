<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Benvenuto</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<%
	String messaggio = (String) request.getAttribute("messaggio");
	%>
	<%
	if (messaggio != null) {
	%>
	<p align="center">
		<a style="font-family: helvetica; color: yellow; font-size: 20px">
			<%
			out.print(messaggio);
			%>
		</a>
	</p>
	<%
	}
	%>
	<div class="card">
		<form action="login" method="post">
			<h3>inserisci nome utente</h3>
			<input type="text" name="username">
			<p>
			<h3>inserisci password</h3>
			<input type="password" name="password">
			<p>
				<input type="submit" value="Accedi">
			<p>
		</form>
		<div style="margin-top: 15px; text-align: center;">
		    <p style="color: var(--dark-blue); font-size: 0.9rem; margin-bottom: 5px;">Non hai un account?</p>
		    <a href="registrazione.jsp" style="width: 100%; padding: 0; margin: 0;">
		        <input type="button" value="Registrati" style="background: transparent; border: 2px solid var(--primary-color); color: var(--primary-color); cursor: pointer; width: 100%; padding: 14px; border-radius: 10px; font-weight: bold; text-transform: uppercase; transition: 0.3s;">
		    </a>
		</div>
	</div>
</body>
</html>