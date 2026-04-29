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

/**
 * Servlet implementation class StampaStudenti
 */
@WebServlet("/StampaStudenti")
public class StampaStudenti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StampaStudenti() {
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

		String idAppello = request.getParameter("ID_appello");
		String azione = request.getParameter("azione");
		Connection conn = Connessione.getCon();
		String materiaProf = (String) request.getSession().getAttribute("materia");

		if ("Aggiungi".equals(azione)) {
			// CREA APPELLO
			String data = request.getParameter("data");
			try {
				// prendi idMateria
				PreparedStatement sm = conn.prepareStatement("SELECT idcorso FROM corso WHERE Materia = ?");
				sm.setString(1, materiaProf);
				ResultSet rsm = sm.executeQuery();

				if (!rsm.next()) {
					request.setAttribute("messaggio", "Materia non valida!");
					RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
					rd.forward(request, response);
					return;
				}

				String idMateria = rsm.getString(1);

				// INSERT nuovo appello
				PreparedStatement insert = conn.prepareStatement("INSERT INTO appello (Materia, Data) VALUES (?, ?)");
				insert.setString(1, idMateria);
				insert.setString(2, data);
				insert.executeUpdate();

				// ricarica appelli aggiornati
				PreparedStatement smtAppelli = conn
						.prepareStatement("SELECT idAppello, Data FROM appello WHERE Materia = ?");
				smtAppelli.setString(1, idMateria);

				ResultSet rsAppelli = smtAppelli.executeQuery();
				request.setAttribute("appelli", rsAppelli);

				request.setAttribute("messaggio", "Appello creato con successo!");

				RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
				rd.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("vai".equals(azione)) {
			// STAMPA STUDENTI
			try {
				// 1. prendi ID materia dal nome
				PreparedStatement sm = conn.prepareStatement("SELECT idcorso FROM corso WHERE Materia = ?");
				sm.setString(1, materiaProf);
				ResultSet rsm = sm.executeQuery();

				if (!rsm.next()) {
					request.setAttribute("messaggio", "Materia non valida!");
					RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
					rd.forward(request, response);
					return;
				}

				String idMateria = rsm.getString(1);

				// 2. ORA usa idMateria (non materiaProf!)
				PreparedStatement smtAppelli = conn.prepareStatement(
						"select idAppello, Data from appello where Materia = CAST(? AS UNSIGNED INTEGER)");
				smtAppelli.setString(1, idMateria);

				ResultSet rsAppelli = smtAppelli.executeQuery();
				request.setAttribute("appelli", rsAppelli);

				PreparedStatement smt = conn.prepareStatement(
						"select Materia, Data from appello WHERE idAppello = CAST(? AS UNSIGNED INTEGER) AND Materia = CAST(? AS UNSIGNED INTEGER)");
				smt.setString(1, idAppello);
				smt.setString(2, idMateria);

				ResultSet rs = smt.executeQuery();

				if (!rs.next()) {
					request.setAttribute("messaggio", "Appello non valido per la tua materia!");

					RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
					rd.forward(request, response);
					return;
				}

				String Materia = rs.getString("Materia");
				String Data = rs.getString("Data");
				PreparedStatement smt2 = conn
						.prepareStatement("select Materia from corso where idcorso=CAST(? AS UNSIGNED INTEGER)");
				smt2.setString(1, Materia);
				ResultSet rs2 = smt2.executeQuery();

				if (!rs2.next()) {
					request.setAttribute("messaggio", "Materia non trovata!");

					RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
					rd.forward(request, response);
					return;
				}

				String nomeMateria = rs2.getString(1);

				PreparedStatement smt1 = conn.prepareStatement("SELECT s.nome, s.cognome, s.matricola "
						+ "FROM studente s " + "JOIN prenotazione p ON s.matricola = p.stud_prenotato "
						+ "WHERE p.app_prenotato = CAST(? AS UNSIGNED INTEGER)");
				smt1.setString(1, idAppello);
				ResultSet rs1 = smt1.executeQuery();

				request.setAttribute("Materia", nomeMateria);
				request.setAttribute("Data", Data);
				request.setAttribute("elenco_studenti", rs1);

				// forward
				RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
				rd.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
