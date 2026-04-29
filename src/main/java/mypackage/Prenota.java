package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Prenota
 */
@WebServlet("/Prenota")
public class Prenota extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Prenota() {
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
		HttpSession session = request.getSession();
		String appello = request.getParameter("appello");
		String matricola = (String) session.getAttribute("matricola");
		String idMateria = (String) session.getAttribute("idMateriaSessione");
		Connection conn = Connessione.getCon();
		try {
			// CHECK
			PreparedStatement check = conn
					.prepareStatement("SELECT * FROM prenotazione WHERE stud_prenotato=? AND app_prenotato=?");

			check.setString(1, matricola);
			check.setString(2, appello);

			ResultSet rsCheck = check.executeQuery();

			if (rsCheck.next()) {
				request.setAttribute("messaggio", "Sei già prenotato!");

				PreparedStatement psApp = conn.prepareStatement(
						"SELECT idAppello, Data FROM appello WHERE materia=CAST(? AS UNSIGNED INTEGER)");
				psApp.setString(1, idMateria); // idMateria è quello preso dalla sessione a inizio metodo
				request.setAttribute("elenco_appelli", psApp.executeQuery());

				RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
				rd.forward(request, response);
				return;
			}

			// 1. CONTROLLO SE ESISTE L'APPELLO
			PreparedStatement recuperoData = conn
					.prepareStatement("select data, materia from appello where idAppello=CAST(? AS UNSIGNED INTEGER)");

			recuperoData.setString(1, appello);
			ResultSet data = recuperoData.executeQuery();

			if (!data.next()) {
				request.setAttribute("messaggio", "Appello non trovato!");

				// --- RICARICHIAMO LA TABELLA USANDO L'ID IN SESSIONE ---
				PreparedStatement psApp = conn.prepareStatement(
						"SELECT idAppello, Data FROM appello WHERE materia=CAST(? AS UNSIGNED INTEGER)");
				psApp.setString(1, idMateria);

				// Rimettiamo i dati nella request per la JSP
				request.setAttribute("elenco_appelli", psApp.executeQuery());

				RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
				rd.forward(request, response);
				return;
			}

			// INSERT
			PreparedStatement smt2 = conn.prepareStatement(
					"insert into prenotazione (stud_prenotato,app_prenotato) values (CAST(? AS UNSIGNED INTEGER),CAST(? AS UNSIGNED INTEGER))");

			smt2.setString(1, matricola);
			smt2.setString(2, appello);
			smt2.executeUpdate();

			String dataScelta = data.getString("data");
			String idMateriaAppello = data.getString("materia");
			PreparedStatement recuperoMateria = conn
					.prepareStatement("select materia from corso where idcorso=CAST(? AS UNSIGNED INTEGER)");
			recuperoMateria.setString(1, idMateriaAppello);
			ResultSet materia = recuperoMateria.executeQuery();

			if (!materia.next()) {
				request.setAttribute("messaggio", "Materia non trovata!");

				PreparedStatement psC = conn.prepareStatement(
						"select idcorso,materia,nome,cognome from corso join professore on cattedra=idprofessore");
				request.setAttribute("tabella_corso", psC.executeQuery());

				RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
				rd.forward(request, response);
				return;
			}

			String nomeMateria = materia.getString(1);

			// AGGIUNTA 3: Ricarichiamo i corsi per permettere nuove prenotazioni
			PreparedStatement psC = conn.prepareStatement(
					"select idcorso,materia,nome,cognome from corso join professore on cattedra=idprofessore");
			request.setAttribute("tabella_corso", psC.executeQuery());

			RequestDispatcher rd1 = request.getRequestDispatcher("studente.jsp");
			request.setAttribute("data", dataScelta);
			request.setAttribute("materia2", nomeMateria);
			rd1.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
