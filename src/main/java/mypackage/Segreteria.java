package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Segreteria")
public class Segreteria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Segreteria() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Connection conn = Connessione.getCon();

		try {
			// 1. Prendiamo tutti gli studenti per Giuseppe Russo
			Statement smt1 = conn.createStatement();
			ResultSet rsStudenti = smt1.executeQuery("select * from studente");
			request.setAttribute("listaStudenti", rsStudenti);

			// 2. Prendiamo tutti i corsi e chi li insegna (JOIN)
			Statement smt2 = conn.createStatement();
			ResultSet rsCorsi = smt2.executeQuery("select c.materia, p.nome, p.cognome from corso c "
					+ "join professore p on c.cattedra = p.idprofessore");
			request.setAttribute("listaCorsi", rsCorsi);

			// 3. Andiamo alla pagina JSP
			RequestDispatcher rd = request.getRequestDispatcher("segreteria.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}