<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
String nome= (String)session.getAttribute("nome");
String cognome= (String)session.getAttribute("cognome");
String materia=(String)session.getAttribute("materia");
ResultSet appelli=(ResultSet)request.getAttribute("appelli");
ResultSet elenco=(ResultSet)request.getAttribute("elenco_studenti");
String nomeMateria= (String)request.getAttribute("Materia");
String Data= (String)request.getAttribute("Data");
%>

<% if(nome==null || cognome==null){
    response.sendRedirect("index.jsp");
} %>

<div class="container">

<p>Bentornato professore <%=nome%> <%=cognome%></p>

<%
String messaggio = (String) request.getAttribute("messaggio");
%>

<% if(messaggio != null){ %>
    <p style="color:red;"><%= messaggio %></p>
<% } %>

<% if(appelli!=null){ %>
	
	<p>Per la sua materia: <%=materia %> sono disponibili i seguenti appelli</p>
	
	<table>
	<tr>
	<th>ID Appello</th>
	<th>Data</th>
	</tr>
	
	<% while(appelli.next()){ %>
		<tr>
		<td><%=appelli.getInt(1)%></td>
		<td><%=appelli.getDate("Data") %></td>
		</tr>
	<% } %>
	
	</table>
	
	<form action="StampaStudenti" method="post">
	    <p>Inserisci ID appello:</p>
	    <input type="number" name="ID_appello">
	    <input type="submit" name="azione" value="vai">
	</form>
	
	<h3>Inserisci nuovo appello</h3>
	
	<form action="StampaStudenti" method="post">
	    <p>Data nuovo appello:</p>
	    <input type="date" name="data" required>
	    <input type="submit" name="azione" value="Aggiungi">
	</form>
	
<% } %>

<% if(elenco!=null){ %>

	<p>Per l'esame <%=nomeMateria %> in data <%=Data %> si sono prenotati i seguenti studenti:</p>
	
	<table>
	<tr>
	<th>Nome</th>
	<th>Cognome</th>
	<th>Matricola</th>
	</tr>
	
	<% while(elenco.next()){ %>
		<tr>
		<td><%=elenco.getString("nome")%></td>
		<td><%=elenco.getString("cognome")%></td>
		<td><%=elenco.getString("Matricola") %></td>
		</tr>
	<% } %>
	
	</table>

<% } %>


<a href="logout.jsp">logout</a>

</div>

</body>