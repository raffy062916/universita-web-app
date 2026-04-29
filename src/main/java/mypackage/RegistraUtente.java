package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistraUtente
 */
@WebServlet("/RegistraUtente")
public class RegistraUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistraUtente() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1. Recuperiamo i dati dal form
		String ruolo = request.getParameter("ruolo");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Connection conn = Connessione.getCon();
		String query = "";

		// 2. Decidiamo in quale tabella scrivere in base al ruolo scelto
		if (ruolo.equals("studente")) {
			query = "INSERT INTO studente (nome, cognome, username, password) VALUES (?, ?, ?, ?)";
		} else if (ruolo.equals("professore")) {
			query = "INSERT INTO professore (nome, cognome, username, password) VALUES (?, ?, ?, ?)";
		} else if (ruolo.equals("segreteria")) {
			query = "INSERT INTO segreteria (nome, cognome, username, password) VALUES (?, ?, ?, ?)";
		}

		try {
			// 3. Prepariamo ed eseguiamo la query
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, nome);
			ps.setString(2, cognome);
			ps.setString(3, username);
			ps.setString(4, password);

			int result = ps.executeUpdate(); // Questo salva i dati nel DB

			if (result > 0) {
				// Se il salvataggio è riuscito, torniamo al login
				response.sendRedirect("index.jsp?msg=success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// In caso di errore (es: username già esistente), torniamo alla registrazione
			response.sendRedirect("registrazione.jsp?error=true");
		}
	}
}
