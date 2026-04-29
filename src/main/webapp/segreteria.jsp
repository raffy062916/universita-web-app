<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard Segreteria - Giuseppe Russo</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%
// Recuperiamo i dati passati dalla Servlet Segreteria.java
ResultSet rsStudenti = (ResultSet) request.getAttribute("listaStudenti");
ResultSet rsCorsi = (ResultSet) request.getAttribute("listaCorsi");

// Recuperiamo nome e cognome dalla sessione (messi dal login)
String nomeAdmin = (String) session.getAttribute("nome");
String cognomeAdmin = (String) session.getAttribute("cognome");
%>

<div class="container">
    <h1>Area Riservata Segreteria</h1>
    <p>Operatore collegato: <strong><%= (nomeAdmin != null) ? nomeAdmin : "Giuseppe" %> <%= (cognomeAdmin != null) ? cognomeAdmin : "Russo" %></strong></p>

    <hr>

    <h3>Elenco Studenti Iscritti</h3>
    <table>
        <thead>
            <tr>
                <th>Matricola</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Email/User</th>
            </tr>
        </thead>
        <tbody>
            <% if(rsStudenti != null) { 
                while(rsStudenti.next()) { %>
                <tr>
                    <td><%= rsStudenti.getString("matricola") %></td>
                    <td><%= rsStudenti.getString("nome") %></td>
                    <td><%= rsStudenti.getString("cognome") %></td>
                    <td><%= rsStudenti.getString("username") %></td>
                </tr>
            <% } } else { %>
                <tr><td colspan="4">Nessun dato studenti trovato.</td></tr>
            <% } %>
        </tbody>
    </table>

    <br>

    <h3>Associazione Materie e Professori</h3>
    <table>
        <thead>
            <tr>
                <th>Insegnamento</th>
                <th>Docente Titolare</th>
            </tr>
        </thead>
        <tbody>
            <% if(rsCorsi != null) { 
                while(rsCorsi.next()) { %>
                <tr>
                    <td><%= rsCorsi.getString("materia") %></td>
                    <td><%= rsCorsi.getString("nome") %> <%= rsCorsi.getString("cognome") %></td>
                </tr>
            <% } } else { %>
                <tr><td colspan="2">Nessun dato corsi trovato.</td></tr>
            <% } %>
        </tbody>
    </table>

    <br>
    <form action="logout.jsp" method="post">
        <input type="submit" value="Logout" style="background-color: #f44336; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer;">
    </form>
</div>

</body>
</html>