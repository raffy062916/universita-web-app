<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Area Studenti</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
String matricola=(String)session.getAttribute("matricola");
ResultSet res=(ResultSet) request.getAttribute("tabella_corso");
ResultSet res1=(ResultSet) request.getAttribute("elenco_appelli");
String materia=(String) request.getAttribute("materia");
String messaggio = (String) request.getAttribute("messaggio");
String data = (String) request.getAttribute("data");
String materia2 = (String) request.getAttribute("materia2");
String nome = (String)session.getAttribute("nome");
String cognome = (String)session.getAttribute("cognome");
%>

<% if(matricola==null){
    response.sendRedirect("index.jsp");
} %>

<div class="container">

<p>Benvenuto studente: <%=nome%> <%=cognome%></p> 

<% if(res!=null) { %>

<table>
<tr>
<th>ID corso</th>
<th>materia</th>
<th>nome docente</th>
<th>cognome docente</th>
</tr>

<% while(res.next()){ %>
<tr>
<td><%=res.getInt("idcorso") %></td>
<td><%=res.getString("materia") %></td>
<td><%=res.getString("nome") %></td>
<td><%=res.getString("cognome") %></td>
</tr>
<% } %>

</table>

<form action="Prenotazione" method="post">
<p>Inserisci la prenotazione che vuoi effettuare</p>
<input type="number" name="materia">
<input type="submit" value="Prenota">
</form>

<% } %>

<% if(res1!=null) { %>

<p>Per l'esame di <%=materia%> sono disponibili i seguenti appelli:</p>

<table>
<tr>
<th>ID Appello</th>
<th>Data</th>
</tr>

<% while(res1.next()){ %>
<tr>
<td><%=res1.getInt(1)%></td>
<td><%=res1.getDate("Data") %></td>
</tr>
<% } %>

</table>

<form action="Prenota" method="post">
<p>Inserisci la prenotazione che vuoi effettuare</p>
<input type="number" name="appello">
<input type="submit" value="Prenota">
</form>

<% } %>

<%--
<%= messaggio != null ? messaggio : "" %>
 --%>
<% if(messaggio != null){ %>
    <p style="color:red; font-weight:bold;"><%= messaggio %></p>
<% } %>

<% if(materia2!=null && data!=null){ %>
<p>Prenotazione effettuata con successo in data <%=data %> per il corso <%=materia2 %></p>
<% } %>

<a href="logout.jsp">logout</a>

</div>

</body>